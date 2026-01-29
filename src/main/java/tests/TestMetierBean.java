package tests;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import business.excursion.*;
import business.path.Path;
import business.path.TransportFactory;
import business.offer.*;
import java.util.*;

public class TestMetierBean {

    public static void main(String[] args) {
        System.out.println("\n\n==========================================");
        System.out.println("TEST 1 : CONSTRUCTION MANUELLE (STATIQUE)");
        System.out.println("==========================================");
        test1();

        System.out.println("\n\n==========================================");
        System.out.println("TEST 2 : GÉNÉRATION IA (NOMADISME & LISSAGE)");
        System.out.println("==========================================");
        test2();

        System.out.println("\n\n==========================================");
        System.out.println("TEST 3 : DÉMONSTRATION CHANGEMENT HÔTEL");
        System.out.println("==========================================");
        test3();
        
        
    }

    public static void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring.xml");
        StayOffer offer = (StayOffer) context.getBean("offerBali");
        Hotel baliHotel = (Hotel) context.getBean("hotelBali");
        TransportFactory transportFactory = (TransportFactory) context.getBean("transportFactory");

        offer.addHotel(baliHotel);
        offer.setNbNights(2);

        Excursion day1 = (Excursion) context.getBean("excursion");
        day1.setNbSite(3);
        day1.setFactory(transportFactory);
        day1.addSite((TouristSite) context.getBean("siteTemple"));
        day1.addSite((TouristSite) context.getBean("sitePalaisUbud"));
        day1.generateTour(baliHotel);
        offer.addExcursion(day1);

        printResultDetaillé(offer);
    }

    public static void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring.xml");

        TransportFactory factory = (TransportFactory) context.getBean("transportFactory");

        SearchCriteria crit = new SearchCriteria();
        crit.setNbDays(6);
        crit.setKeywords("temple surf zen sport cascade jungle"); 
        crit.setBudgetMax(3500); 

        List<StayOffer> validOffers = new ArrayList<>();
        
        System.out.println("Recherche d'offres de qualité supérieure (Confort > 60)...");

        // On boucle tant qu'on n'a pas trouvé nos 3 pépites
        while (validOffers.size() < 3) {
            OfferBuilder builder = new OfferBuilder(context ,crit);
            StayOffer candidate = builder
                .setStrategy(new CalmPaceStrategy())
                .generateOptimizedStay(factory)
                .build();

            // On n'ajoute que si le critère de confort est atteint
            if (candidate.getScoreComfort() > 40) {
                validOffers.add(candidate);
                System.out.print(" [Offre " + validOffers.size() + " trouvée!] ");
            }
        }

        // Tri par score de confort décroissant
        validOffers.sort((o1, o2) -> Double.compare(o2.getScoreComfort(), o1.getScoreComfort()));

        System.out.println("\n\nAFFICHAGE DES 3 MEILLEURES OFFRES PAR NIVEAU DE CONFORT :");
        for (int j = 0; j < validOffers.size(); j++) {
            StayOffer o = validOffers.get(j);
            System.out.println("\n==================================================");
            System.out.println(" RANG #" + (j + 1) + " | SCORE CONFORT : " + String.format("%.2f", o.getScoreComfort()));
            System.out.println("==================================================");
            printResultDetaillé(o);
        }
    }
    public static void test3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring.xml");
        TransportFactory factory = (TransportFactory) context.getBean("transportFactory");
        StayOffer offer = (StayOffer) context.getBean("offerBali");
        offer.getHotels().clear();

        Hotel ubud = (Hotel) context.getBean("hotelBali");
        Hotel seminyak = (Hotel) context.getBean("hotelSeminyak");

        offer.addHotel(ubud);
        Excursion d1 = (Excursion) context.getBean("excursion");
        d1.addSite((TouristSite) context.getBean("sitePalaisUbud"));
        d1.generateTour(ubud);
        offer.addExcursion(d1);

        offer.addHotel(seminyak);
        Excursion d2 = (Excursion) context.getBean("excursion");
        d2.addSite((TouristSite) context.getBean("siteSurf"));
        d2.generateTour(seminyak);
        offer.addExcursion(d2);

        printResultDetaillé(offer);
    }

    /**
     * Affiche l'hôtel, les sites visités et les transports par jour.
     */
    private static void printResultDetaillé(StayOffer o) {
        System.out.println("------------------------------------------");
        List<Excursion> excursions = o.getExcursions();
        List<Hotel> hotels = o.getHotels();

        for (int i = 0; i < excursions.size(); i++) {
            Excursion ex = excursions.get(i);
            Hotel h = (i < hotels.size()) ? hotels.get(i) : o.getHotel();
            
            System.out.println("JOUR " + (i + 1) + " | HÔTEL : " + (h != null ? h.getName() : "Non défini"));
            
            // --- NOUVEAU : Affichage des sites ---
            if (!ex.getSites().isEmpty()) {
                StringJoiner sj = new StringJoiner(", ");
                for (TouristSite s : ex.getSites()) sj.add(s.getName());
                System.out.println("  [SITES]     : " + sj.toString());
            }

            // Affichage des transports
            for (Path p : ex.getTrajets()) {
                System.out.println("  [TRANSPORT] : " + p.getMode() + " (" + String.format("%.2f", p.getDistance()) + " km)");
            }
            System.out.println("  Prix Journée : " + String.format("%.2f", ex.getPrice()) + " €");
            System.out.println("------------------------------------------");
        }
        System.out.println("PRIX TOTAL OFFRE : " + String.format("%.2f", o.getPrice()) + " €");
        if (o.getScoreComfort() > 0) {
            System.out.println("SCORE CONFORT    : " + String.format("%.1f", o.getScoreComfort()) + "/100");
        }
        System.out.println("==========================================\n");
    }
}