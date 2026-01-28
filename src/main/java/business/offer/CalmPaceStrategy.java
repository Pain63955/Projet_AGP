package business.offer;


public class CalmPaceStrategy implements ComfortStrategy {

	@Override
    public double calculeScore(StayOffer offer) {
        // Un séjour calme a peu de sites par excursion (ex: 1 ou 2)
        double totalSites = offer.getExcursions().stream()
                                 .mapToInt(e -> e.getSites().size()).sum();
        // Plus il y a de sites, plus le score baisse
        return Math.max(0, 100 - (totalSites * 5));
    }

}
