package business.offer;

public class SpeedPaceStrategy implements ComfortStrategy{

	@Override
    public double calculeScore(StayOffer offer) {
        // Un rythme soutenu valorise le nombre de sites visités
        int totalSites = offer.getExcursions().stream()
                              .mapToInt(e -> e.getSites().size()).sum();
        return totalSites * 10.0;
    }

}
