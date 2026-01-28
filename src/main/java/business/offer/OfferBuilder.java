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
    private static final double MAX_DIST_BEFORE_MOVE = 50.0;

    public OfferBuilder(ApplicationContext context, SearchCriteria criteria) {
        this.context = context;
        this.criteria = criteria;
        this.offer = (StayOffer) context.getBean("offerBali");
        this.offer.setNbNights(criteria.getNbDays());
    }

    public OfferBuilder setStrategy(ComfortStrategy strategy) {
        this.strategy = strategy;
        return this;
    }

    public OfferBuilder generateOptimizedStay(List<TouristSite> allSites, List<Hotel> allHotels, TransportFactory factory) {
        // 1. Filtrage sémantique
        String[] keywords = criteria.getKeywords().toLowerCase().split(" ");
        List<TouristSite> matchedSites = allSites.stream()
            .filter(s -> {
                String content = (s.getName() + " " + (s.getDescription() != null ? s.getDescription() : "")).toLowerCase();
                return Arrays.stream(keywords).anyMatch(kw -> kw.length() > 2 && content.contains(kw));
            })
            .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("[IA] Sites trouvés : " + matchedSites.size());

        // 2. Boucle de génération
        for (int i = 0; i < criteria.getNbDays(); i++) {
            if (matchedSites.isEmpty()) break;

            Excursion dailyEx = new Excursion();
            dailyEx.setFactory(factory);
            dailyEx.setNbSite(3); 

            TouristSite bestSite = findNearestSite(offer.getHotel().getAddress(), matchedSites);

            if (bestSite != null) {
                // Changement d'hôtel si nécessaire
                if (allHotels.size() > 1) {
                    double dist = calculateDistance(offer.getHotel().getAddress(), bestSite.getAddress());
                    if (dist > MAX_DIST_BEFORE_MOVE) {
                        Hotel newHotel = findNearestHotel(bestSite.getAddress(), allHotels);
                        this.offer.setHotel(newHotel);
                    }
                }

                dailyEx.addSite(bestSite);
                matchedSites.remove(bestSite);

                // Ajout de sites satellites
                for (int slot = 0; slot < 2; slot++) {
                    if (matchedSites.isEmpty()) break;
                    TouristSite last = dailyEx.getSites().get(dailyEx.getSites().size() - 1);
                    TouristSite nearby = findNearestSite(last.getAddress(), matchedSites);
                    if (nearby != null && calculateDistance(last.getAddress(), nearby.getAddress()) < 25.0) {
                        dailyEx.addSite(nearby);
                        matchedSites.remove(nearby);
                    }
                }

                // Génération du circuit
                try {
                    dailyEx.generateTour(offer.getHotel(), criteria.getAskedTransport());
                    
                    // Vérification du prix (Sécurisée contre NPE)
                    double currentPrice = offer.getPrice();
                    double excursionPrice = dailyEx.getPrice();
                    
                    if (currentPrice + excursionPrice <= criteria.getBudgetMax()) {
                        offer.addExcursion(dailyEx);
                    }
                } catch (Exception e) {
                    System.out.println("[Erreur] Impossible de calculer le trajet pour le jour " + (i+1) + " : " + e.getMessage());
                }
            }
        }

        if (strategy != null) {
            offer.setScoreComfort(strategy.calculeScore(offer));
        }
        return this;
    }

    private double calculateDistance(Address a1, Address a2) {
        if (a1 == null || a2 == null) return 0;
        double theta = a1.getLongitude() - a2.getLongitude();
        double dist = Math.sin(Math.toRadians(a1.getLatitude())) * Math.sin(Math.toRadians(a2.getLatitude())) 
                    + Math.cos(Math.toRadians(a1.getLatitude())) * Math.cos(Math.toRadians(a2.getLatitude())) * Math.cos(Math.toRadians(theta));
        return Math.toDegrees(Math.acos(dist)) * 60 * 1.1515 * 1.609344;
    }

    private TouristSite findNearestSite(Address current, List<TouristSite> sites) {
        return sites.stream().min(Comparator.comparingDouble(s -> calculateDistance(current, s.getAddress()))).orElse(null);
    }

    private Hotel findNearestHotel(Address addr, List<Hotel> hotels) {
        return hotels.stream().min(Comparator.comparingDouble(h -> calculateDistance(addr, h.getAddress()))).orElse(hotels.get(0));
    }

    public StayOffer build() {
        return this.offer;
    }
}