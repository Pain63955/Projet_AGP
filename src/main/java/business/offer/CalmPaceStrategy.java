package business.offer;

import java.util.HashSet;
import java.util.Set;

public class CalmPaceStrategy implements ComfortStrategy {
	
    @Override
    public double calculeScore(StayOffer offer) {
        double score = 100;

        // 1. Malus pour les changements d'hôtels (La stabilité est la clé du confort)
        int changementsHotel = 0;
        for (int i = 0; i < offer.getHotels().size() - 1; i++) {
            if (!offer.getHotels().get(i).equals(offer.getHotels().get(i+1))) {
                changementsHotel++;
            }
        }
        score -= (changementsHotel * 15);

        // 2. Malus pour l'épuisement (Trop de sites par jour)
        long joursSurchargés = offer.getExcursions().stream().filter(e -> e.getSites().size() > 2).count();
        score -= (joursSurchargés * 10);

        // 3. Bonus pour les jours de repos (Plage/Hôtel sans excursion)
        long joursRepos = offer.getExcursions().stream().filter(e -> e.getSites().isEmpty()).count();
        score += (joursRepos * 10);

        // 4. Malus pour les longs trajets
        double totalDist = offer.getExcursions().stream()
                                .flatMap(e -> e.getTrajets().stream())
                                .mapToDouble(p -> p.getDistance()).sum();
        score -= (totalDist / 20);

        return Math.max(0, Math.min(100, score));
    }
}