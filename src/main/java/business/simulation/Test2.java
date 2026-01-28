package business.simulation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import business.excursion.*;
import business.path.TransportFactory;
import business.offer.*;
import java.util.*;

public class Test2 {
    public static void main(String[] args) {
        // 1. Initialisation du contexte Spring
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring.xml");

        // 2. Récupération des catalogues et de la factory
        List<TouristSite> sites = new ArrayList<>(context.getBeansOfType(TouristSite.class).values());
        List<Hotel> hotels = new ArrayList<>(context.getBeansOfType(Hotel.class).values());
        TransportFactory factory = (TransportFactory) context.getBean("transportFactory");

        System.out.println("--- CATALOGUE : " + sites.size() + " sites, " + hotels.size() + " hôtels ---");

        // 3. Définition des critères de recherche
        SearchCriteria crit = new SearchCriteria();
        crit.setNbDays(3);
        crit.setKeywords("temple surf zen sport"); // Mots-clés pour le filtrage
        crit.setBudgetMax(2500);       // Budget incluant Hôtel + Excursions
        crit.setAskedTransport("BOAT");  // Mode de transport envoyé à la factory

        // 4. Utilisation du Builder pour générer l'offre
        OfferBuilder builder = new OfferBuilder(context, crit);
        StayOffer result = builder
                .setStrategy(new CalmPaceStrategy()) // Stratégie de score (Calme ou Soutenu)
                .generateOptimizedStay(sites, hotels, factory)
                .build();

        // 5. Affichage des résultats détaillés
        printResult(result, crit);
    }

    private static void printResult(StayOffer o, SearchCriteria c) {
        System.out.println("\n==========================================");
        System.out.println("          RÉSULTAT DE LA SIMULATION         ");
        System.out.println("==========================================");
        
        if (o.getHotel() != null) {
            System.out.println("HÔTEL FINAL   : " + o.getHotel().getName());
            System.out.println("LOCALISATION  : " + o.getHotel().getAddress().getTown());
        }
        
        System.out.println("PRIX TOTAL    : " + String.format("%.2f", o.getPrice()) + " € / " + c.getBudgetMax() + " €");
        System.out.println("SCORE CONFORT : " + o.getScoreComfort() + "/100");
        System.out.println("------------------------------------------");

        double totalDistance = 0;
        List<Excursion> excursions = o.getExcursions();

        for (int i = 0; i < c.getNbDays(); i++) {
            System.out.print("JOUR " + (i + 1) + " : ");
            
            if (i < excursions.size()) {
                Excursion e = excursions.get(i);
                
                if (e.getSites().isEmpty()) {
                    System.out.println("Journée détente à l'hôtel.");
                } else {
                    // Affichage des noms des sites visités
                    StringJoiner sj = new StringJoiner(", ");
                    for (TouristSite s : e.getSites()) {
                        sj.add("[" + s.getName() + "]");
                    }
                    System.out.print(sj.toString());

                    // Affichage de la distance parcourue dans la journée (Hôtel -> Sites -> Hôtel)
                    double dayDist = e.getTotalDistance();
                    System.out.println(String.format(" | Distance: %.2f km", dayDist));
                    totalDistance += dayDist;
                }
            } else {
                System.out.println("Libre (Aucune excursion générée).");
            }
        }

        System.out.println("------------------------------------------");
        System.out.println(String.format("DISTANCE TOTALE PARCOURUE : %.2f km", totalDistance));
        System.out.println("==========================================\n");
    }
}