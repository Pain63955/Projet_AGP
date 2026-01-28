package business.simulation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import business.excursion.Excursion;
import business.excursion.Hotel;
import business.excursion.HistoricSite;
import business.excursion.TouristSite;
import business.offer.StayOffer;
import business.path.Path;
import business.path.TransportFactory;

import org.springframework.context.ApplicationContext;

public class Test {
	
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/spring.xml");
        
        StayOffer offer = (StayOffer) context.getBean("offerBali");
        Hotel baliHotel = (Hotel) context.getBean("hotelBali");
        TransportFactory transportFactory = (TransportFactory) context.getBean("transportFactory");

        offer.setHotel(baliHotel);

        // JOUR 1
        Excursion day1 = (Excursion) context.getBean("excursion");
        day1.setFactory(transportFactory);
        
        HistoricSite temple = (HistoricSite) context.getBean("siteTemple");
        HistoricSite palace = (HistoricSite) context.getBean("sitePalaisUbud");
        
        day1.addSite(temple);
        day1.addSite(palace);
        
        // Génère le circuit automatique (Hôtel -> Temple -> Palais -> Hôtel)
        day1.generateTour(baliHotel, "BOAT");
        offer.addExcursion(day1);

        // JOUR 2
        Excursion day2 = (Excursion) context.getBean("excursion");
        day2.setFactory(transportFactory);
        
        HistoricSite riceField = (HistoricSite) context.getBean("siteRizieres");
        day2.addSite(riceField);
        
        // Circuit simple (Hôtel -> Rizières -> Hôtel)
        day2.generateTour(baliHotel, "WALK");
        offer.addExcursion(day2);

        // AFFICHAGE
        System.out.println("RECAPITULATIF DU SEJOUR : BALI");
        System.out.println("Hotel : " + offer.getHotel().getName());
        System.out.println("");

        int i = 1;
        for (Excursion ex : offer.getExcursions()) {
            System.out.println("JOUR " + i);
            
            for (TouristSite s : ex.getSites()) {
                System.out.println("  Site : " + s.getName());
            }
            
            // On affiche chaque segment du trajet généré
            for (Path t : ex.getTrajets()) {
                System.out.println("  Trajet : " + t.getMode() + " (" + String.format("%.2f", t.getDistance()) + " km)");
            }
            
            System.out.println("  Prix de la journee : " + ex.getPrice() + " EUR");
            System.out.println("");
            i++;
        }

        System.out.println("PRIX TOTAL DU SEJOUR : " + offer.getPrice() + " EUR");
    }
}