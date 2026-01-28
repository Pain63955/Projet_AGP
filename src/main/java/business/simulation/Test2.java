package business.simulation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import business.excursion.Excursion;
import business.offer.SearchCriteria;
import business.offer.OfferBuilder;
import business.offer.StayOffer;

public class Test2 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring.xml");

        // --- CRITÈRES DE BASE ---
        SearchCriteria crit = new SearchCriteria();
        crit.setNbDays(4);
        crit.setKeywords("spirituel nature historique");

        // --- GÉNÉRATION OFFRE 1 (LUXE/BATEAU) ---
        crit.setBudgetMax(200.0);
        crit.setAskedTransport("BATEAU");
        StayOffer offre1 = new OfferBuilder(context, crit).genererIA().build();
        afficherOffre("OFFRE A (PREMIUM)", offre1, crit);

        // --- GÉNÉRATION OFFRE 2 (ÉCO/BUS) ---
        // On reset l'offre dans le builder pour une nouvelle simulation
        crit.setBudgetMax(100.0);
        crit.setAskedTransport("AUTOBUS");
        StayOffer offre2 = new OfferBuilder(context, crit).genererIA().build();
        afficherOffre("OFFRE B (ÉCONOMIQUE)", offre2, crit);
    }

    private static void afficherOffre(String titre, StayOffer o, SearchCriteria c) {
        System.out.println("\n=== " + titre + " ===");
        System.out.println("Prix Total : " + o.getPrice() + " EUR (Budget max: " + c.getBudgetMax() + ")");
        for (int i = 0; i < c.getNbDays(); i++) {
            System.out.print("Jour " + (i + 1) + " : ");
            if (i < o.getExcursions().size()) {
                Excursion e = o.getExcursions().get(i);
                e.getSites().forEach(s -> System.out.print("[" + s.getName() + "] "));
                System.out.println();
            } else {
                System.out.println("Journée libre");
            }
        }
    }
}