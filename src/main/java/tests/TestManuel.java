package tests;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import business.excursion.*;
import business.path.Path;
import business.path.TransportFactory;
import business.offer.*;
import java.util.*;

public class TestManuel {

    public static void main(String[] args) {
        test1();
           
    }

    public static void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring.xml");
        TransportFactory transportFactory = (TransportFactory) context.getBean("transportFactory");
        printResultDetaillé(offer);
        List<StayOffer> validOffers = new ArrayList<>();
        SearchCriteria crit = new SearchCriteria();
     // On boucle tant qu'on n'a pas trouvé nos 3 pépites
        while (validOffers.size() < 3) {
            OfferBuilder builder = new OfferBuilder(context, crit);
            StayOffer candidate = builder
                .setStrategy(new CalmPaceStrategy())
                .generateOptimizedStay(sites, hotels, factory)
                .build();

            // On n'ajoute que si le critère de confort est atteint
            if (candidate.getScoreComfort() > 40) {
                validOffers.add(candidate);
                System.out.print(" [Offre " + validOffers.size() + " trouvée!] ");
             // Tri par score de confort décroissant
                validOffers.sort((o1, o2) -> Double.compare(o2.getScoreComfort(), o1.getScoreComfort()));

                System.out.println("\n\nAFFICHAGE DES 3 MEILLEURES OFFRES PAR NIVEAU DE CONFORT :");
                for (int j = 0; j < validOffers.size(); j++) {
                    StayOffer o = validOffers.get(j);
                    
              }
            }
        }
    }
}