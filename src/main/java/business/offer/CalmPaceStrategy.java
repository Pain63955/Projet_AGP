package business.offer;

public class CalmPaceStrategy implements ComfortStrategy {
	
    @Override
    public double calculeScore(StayOffer offer) {
        double score = 0;
        int nbJours = offer.getNbNights();
        
        // 1. Bonus pour une densité modérée de sites (On vise 1 à 2 sites par jour)
        double totalSites = offer.getExcursions().stream().mapToInt(e -> e.getSites().size()).sum();
        double ratioSites = totalSites / nbJours;
        
        // Optimal entre 1 et 2 sites/jour
        if (ratioSites >= 1.0 && ratioSites <= 2.0) {
            score += 40; // Score maximum si dans la plage idéale
        } else if (ratioSites < 1.0) {
            score += ratioSites * 40; // Proportionnel si moins de 1
        } else {
            score += 40 - ((ratioSites - 2.0) * 10); // Pénalité si plus de 2
        }

        // 2. Bonus pour limiter les transports (Moins de déplacements = plus de repos)
        double totalDist = offer.getExcursions().stream()
                                .flatMap(e -> e.getTrajets().stream())
                                .mapToDouble(p -> p.getDistance()).sum();
        
        double avgDistPerDay = totalDist / nbJours;
        // Pénalité progressive : -1 point tous les 20km en moyenne par jour
        score -= (avgDistPerDay / 20); 

        // 3. Bonus pour la stabilité hôtelière (Rester au même endroit = détente)
        long nbHotelsDifferents = offer.getHotels().stream().distinct().count();
        double stabilityBonus = Math.max(0, 30 - (nbHotelsDifferents * 5)); // -5 points par hôtel différent
        score += stabilityBonus;

        // 4. Bonus pour les journées peu chargées (repos)
        long joursAvecPeuActivites = offer.getExcursions().stream()
                                          .filter(e -> e.getSites().size() <= 1)
                                          .count();
        score += (joursAvecPeuActivites * 5); // +5 points par jour de repos

        return Math.max(0, Math.min(100, score + 20)); // Base de 20 pour compenser
    }
}