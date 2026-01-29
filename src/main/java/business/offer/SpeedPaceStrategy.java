package business.offer;

public class SpeedPaceStrategy implements ComfortStrategy {
	
    @Override
    public double calculeScore(StayOffer offer) {
        double score = 0;
        int nbJours = offer.getNbNights();
        
        // 1. Bonus pour la densité de sites (On veut entre 2 et 3 sites par jour)
        double totalSites = offer.getExcursions().stream().mapToInt(e -> e.getSites().size()).sum();
        double ratioSites = totalSites / nbJours;
        score += Math.min(50, ratioSites * 20); 

        // 2. Malus modéré pour les transports (Le voyageur soutenu accepte de bouger)
        double totalDist = offer.getExcursions().stream()
                                .flatMap(e -> e.getTrajets().stream())
                                .mapToDouble(p -> p.getDistance()).sum();
        score -= (totalDist / 50); // -1 point tous les 50km

        // 3. Bonus pour la diversité des hôtels (Nomadisme)
        long nbHotelsDifferents = offer.getHotels().stream().distinct().count();
        score += (nbHotelsDifferents * 10);

        return Math.max(0, Math.min(100, score + 30)); // Base de 30 pour compenser l'effort
    }
}