package business.simulation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import business.excursion.Excursion;
import business.offre.CritereRecherche;
import business.offre.OffreBuilder;
import business.offre.OffreSejour;

public class Test2 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("business/spring/spring.xml");

        // --- CRITÈRES DE BASE ---
        CritereRecherche crit = new CritereRecherche();
        crit.setNbJours(4);
        crit.setMotsCles("spirituel nature historique");

        // --- GÉNÉRATION OFFRE 1 (LUXE/BATEAU) ---
        crit.setBudgetMax(200.0);
        crit.setTransportSouhaite("BATEAU");
        OffreSejour offre1 = new OffreBuilder(context, crit).genererIA().build();
        afficherOffre("OFFRE A (PREMIUM)", offre1, crit);

        // --- GÉNÉRATION OFFRE 2 (ÉCO/BUS) ---
        // On reset l'offre dans le builder pour une nouvelle simulation
        crit.setBudgetMax(100.0);
        crit.setTransportSouhaite("AUTOBUS");
        OffreSejour offre2 = new OffreBuilder(context, crit).genererIA().build();
        afficherOffre("OFFRE B (ÉCONOMIQUE)", offre2, crit);
    }

    private static void afficherOffre(String titre, OffreSejour o, CritereRecherche c) {
        System.out.println("\n=== " + titre + " ===");
        System.out.println("Prix Total : " + o.getPrix() + " EUR (Budget max: " + c.getBudgetMax() + ")");
        for (int i = 0; i < c.getNbJours(); i++) {
            System.out.print("Jour " + (i + 1) + " : ");
            if (i < o.getExcursions().size()) {
                Excursion e = o.getExcursions().get(i);
                e.getSites().forEach(s -> System.out.print("[" + s.getNom() + "] "));
                System.out.println();
            } else {
                System.out.println("Journée libre");
            }
        }
    }
}