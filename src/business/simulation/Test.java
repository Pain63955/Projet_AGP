package business.simulation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import business.excursion.Excursion;
import business.excursion.Hotel;
import business.excursion.SiteHisto;
import business.excursion.SiteTouristique;
import business.offre.OffreSejour;
import business.trajet.Trajet;
import business.trajet.TransportFactory;

import org.springframework.context.ApplicationContext;

public class Test {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("business/spring/spring.xml");
        
        OffreSejour offre = (OffreSejour) context.getBean("offreBali");
        Hotel baliHotel = (Hotel) context.getBean("hotelBali");
        TransportFactory transportFactory = (TransportFactory) context.getBean("transportFactory");

        offre.setHotel(baliHotel);

        // JOUR 1
        Excursion jour1 = (Excursion) context.getBean("excursion");
        jour1.setFactory(transportFactory);
        
        SiteHisto temple = (SiteHisto) context.getBean("siteTemple");
        SiteHisto palais = (SiteHisto) context.getBean("sitePalaisUbud");
        
        jour1.ajouterSite(temple);
        jour1.ajouterSite(palais);
        
        // Génère le circuit automatique (Hôtel -> Temple -> Palais -> Hôtel)
        jour1.genererCircuit(baliHotel, "BATEAU");
        offre.ajouterExcursion(jour1);

        // JOUR 2
        Excursion jour2 = (Excursion) context.getBean("excursion");
        jour2.setFactory(transportFactory);
        
        SiteHisto rizieres = (SiteHisto) context.getBean("siteRizieres");
        jour2.ajouterSite(rizieres);
        
        // Circuit simple (Hôtel -> Rizières -> Hôtel)
        jour2.genererCircuit(baliHotel, "AUTOBUS");
        offre.ajouterExcursion(jour2);

        // AFFICHAGE
        System.out.println("RECAPITULATIF DU SEJOUR : BALI");
        System.out.println("Hotel : " + offre.getHotel().getNom());
        System.out.println("");

        int i = 1;
        for (Excursion ex : offre.getExcursions()) {
            System.out.println("JOUR " + i);
            
            for (SiteTouristique s : ex.getSites()) {
                System.out.println("  Site : " + s.getNom());
            }
            
            // On affiche chaque segment du trajet généré
            for (Trajet t : ex.getTrajets()) {
                System.out.println("  Trajet : " + t.getMode() + " (" + String.format("%.2f", t.getDistance()) + " km)");
            }
            
            System.out.println("  Prix de la journee : " + ex.getPrix() + " EUR");
            System.out.println("");
            i++;
        }

        System.out.println("PRIX TOTAL DU SEJOUR : " + offre.getPrix() + " EUR");
    }
}