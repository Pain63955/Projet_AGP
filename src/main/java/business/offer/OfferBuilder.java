package business.offer;

import org.springframework.context.ApplicationContext;
import business.excursion.*;
import business.path.TransportFactory;
import java.util.*;
import java.util.stream.Collectors;

public class OfferBuilder {
    private ApplicationContext context;
    private SearchCriteria criteria;
    private StayOffer offer;
    private ComfortStrategy strategy;
    private Random random = new Random();

    public OfferBuilder(ApplicationContext context, SearchCriteria criteria) {
        this.context = context;
        this.criteria = criteria;
        this.offer = (StayOffer) context.getBean("offerBali");
        this.offer.setNbNights(criteria.getNbDays());
        this.offer.getHotels().clear();
        this.offer.getExcursions().clear();
    }

    public OfferBuilder setStrategy(ComfortStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public OfferBuilder generateOptimizedStay(TransportFactory factory) {
        // Créer une liste pondérée des sites en fonction de leur score de pertinence
        List<TouristSite> matchedSites = createWeightedSiteList();
        Collections.shuffle(matchedSites);
        
        List<Hotel> allHotels = this.criteria.getListHotels();
        double runningTotal = 0.0;
        Hotel cheapestHotel = findCheapestHotel(allHotels);
        Hotel currentHotel = allHotels.get(random.nextInt(allHotels.size()));

        int nbJoursTotal = criteria.getNbDays();

        boolean estDetente = (strategy instanceof CalmPaceStrategy);
        int maxSitesParJour = estDetente ? 2 : 3;
        double seuilChangementHotel = estDetente ? 50.0 : 25.0;
        double ratioActiviteBase = estDetente ? 0.5 : 0.8; 

        for (int i = 0; i < nbJoursTotal; i++) {
            int joursRestants = nbJoursTotal - i;
            double reserveObligatoire = (joursRestants - 1) * cheapestHotel.getPrice();
            double budgetMaxJour = criteria.getBudgetMax() - runningTotal - reserveObligatoire;

            Excursion dailyEx = (Excursion) context.getBean("excursion");
            dailyEx.setFactory(factory);
            if (dailyEx.getTrajets() != null) dailyEx.getTrajets().clear();

            double ratioDynamique = Math.min(ratioActiviteBase, (double) matchedSites.size() / nbJoursTotal);
            boolean faireActivite = !matchedSites.isEmpty() && (Math.random() <= ratioDynamique || matchedSites.size() >= joursRestants);

            if (faireActivite) {
                // Choisir un site pivot en privilégiant les sites avec un meilleur score
                TouristSite pivot = selectBestScoredSite(currentHotel.getAddress(), matchedSites);
                
                if (calculateDistance(currentHotel.getAddress(), pivot.getAddress()) > seuilChangementHotel) {
                    Hotel potential = findRandomBestHotel(pivot.getAddress(), allHotels, budgetMaxJour / 2);
                    if (potential.getPrice() < budgetMaxJour) {
                        currentHotel = potential;
                    }
                }

                dailyEx.addSite(pivot);
                matchedSites.remove(pivot);

                // Ajouter des sites supplémentaires proches
                while (dailyEx.getSites().size() < maxSitesParJour && matchedSites.size() > joursRestants) {
                    TouristSite extra = selectBestScoredSite(pivot.getAddress(), matchedSites);
                    if (calculateDistance(pivot.getAddress(), extra.getAddress()) < 20.0) {
                        dailyEx.addSite(extra);
                        matchedSites.remove(extra);
                    } else break;
                }
                
                dailyEx.generateTour(currentHotel);

                // Ajuster en fonction du budget
                while ((currentHotel.getPrice() + dailyEx.getPrice()) > budgetMaxJour && !dailyEx.getSites().isEmpty()) {
                    matchedSites.add(dailyEx.getSites().remove(dailyEx.getSites().size() - 1));
                    if (dailyEx.getSites().isEmpty()) dailyEx.getTrajets().clear();
                    else dailyEx.generateTour(currentHotel);
                }
            }

            if (dailyEx.getSites().isEmpty()) {
                if (dailyEx.getTrajets() != null) dailyEx.getTrajets().clear();
                if (currentHotel.getPrice() > budgetMaxJour) currentHotel = cheapestHotel;
            }

            runningTotal += currentHotel.getPrice() + dailyEx.getPrice();
            offer.addHotel(currentHotel);
            offer.addExcursion(dailyEx);
        }

        if (strategy != null) offer.setScoreComfort(strategy.calculeScore(offer));
        return this;
    }
    
    /**
     * Crée une liste pondérée des sites basée sur leur score de pertinence
     * Les sites avec un meilleur score apparaissent plus souvent dans la liste
     */
    private List<TouristSite> createWeightedSiteList() {
        List<TouristSite> weightedList = new ArrayList<>();
        Map<TouristSite, Double> sitesWithScores = criteria.getSitesWithScores();
        
        if (sitesWithScores == null || sitesWithScores.isEmpty()) {
            return new ArrayList<>(criteria.getListSites());
        }
        
        for (Map.Entry<TouristSite, Double> entry : sitesWithScores.entrySet()) {
            TouristSite site = entry.getKey();
            Double score = entry.getValue();
            
            // Ajouter le site plusieurs fois en fonction de son score
            // Score > 0.8 : 3 fois
            // Score > 0.5 : 2 fois
            // Score <= 0.5 : 1 fois
            int weight = 1;
            if (score > 0.8) {
                weight = 3;
            } else if (score > 0.5) {
                weight = 2;
            }
            
            for (int i = 0; i < weight; i++) {
                weightedList.add(site);
            }
        }
        
        return weightedList;
    }
    
    /**
     * Sélectionne le meilleur site en tenant compte à la fois de la distance et du score de pertinence
     */
    private TouristSite selectBestScoredSite(Address addr, List<TouristSite> sites) {
        if (sites.isEmpty()) {
            return null;
        }
        
        Map<TouristSite, Double> sitesWithScores = criteria.getSitesWithScores();
        
        // Si pas de scores disponibles, utiliser la méthode classique
        if (sitesWithScores == null || sitesWithScores.isEmpty()) {
            return findClosestSite(addr, sites);
        }
        
        // Calculer un score combiné (distance + pertinence)
        TouristSite bestSite = sites.get(0);
        double bestCombinedScore = Double.NEGATIVE_INFINITY;
        
        for (TouristSite site : sites) {
            double distance = calculateDistance(addr, site.getAddress());
            double relevanceScore = sitesWithScores.getOrDefault(site, 0.0);
            
            // Score combiné : privilégier la pertinence mais pénaliser les grandes distances
            // Plus la pertinence est élevée, moins la distance compte
            double combinedScore = (relevanceScore * 100) - (distance * (1 - relevanceScore));
            
            if (combinedScore > bestCombinedScore) {
                bestCombinedScore = combinedScore;
                bestSite = site;
            }
        }
        
        return bestSite;
    }
    
    private TouristSite findClosestSite(Address addr, List<TouristSite> sites) {
        return sites.stream()
            .min(Comparator.comparingDouble(s -> calculateDistance(addr, s.getAddress())))
            .orElse(sites.get(0));
    }

    private Hotel findRandomBestHotel(Address addr, List<Hotel> hotels, double targetPrice) {
        List<Hotel> candidates = hotels.stream()
            .filter(h -> calculateDistance(addr, h.getAddress()) < 30.0)
            .sorted(Comparator.comparingDouble(h -> Math.abs(h.getPrice() - targetPrice)))
            .limit(3)
            .collect(Collectors.toList());

        if (candidates.isEmpty()) return findCheapestHotel(hotels);
        return candidates.get(random.nextInt(candidates.size()));
    }

    private Hotel findCheapestHotel(List<Hotel> hotels) {
        return hotels.stream().min(Comparator.comparingDouble(Hotel::getPrice)).orElse(hotels.get(0));
    }

    private double calculateDistance(Address a1, Address a2) {
        if (a1 == null || a2 == null) return 0;
        double theta = a1.getLongitude() - a2.getLongitude();
        double dist = Math.sin(Math.toRadians(a1.getLatitude())) * Math.sin(Math.toRadians(a2.getLatitude())) 
                    + Math.cos(Math.toRadians(a1.getLatitude())) * Math.cos(Math.toRadians(a2.getLatitude())) * Math.cos(Math.toRadians(theta));
        return Math.toDegrees(Math.acos(dist)) * 60 * 1.1515 * 1.609344;
    }

    public StayOffer build() {
        return this.offer;
    }
}