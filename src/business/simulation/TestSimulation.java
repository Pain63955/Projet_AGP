package business.simulation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import business.excursion.Excursion;
import business.excursion.Hotel;
import business.excursion.SitesHisto;
import business.offre.OffreSejour;

import org.springframework.context.ApplicationContext;

public class TestSimulation {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		// Récupération de l'offre et de l'hôtel via IoC
		        OffreSejour offre = context.getBean(OffreSejour.class);
		        Hotel baliHotel = (Hotel) context.getBean("hotelBali");
		        offre.setHotel(baliHotel);

		        // Création d'une excursion
		        Excursion jour1 = context.getBean(Excursion.class);
		        SitesHisto temple = (SitesHisto) context.getBean("templeBesakih");
		        
		        jour1.ajouterSite(temple);
		        
		        // Simulation d'un trajet basé sur les coordonnées de l'adresse
		        // Ici on pourrait calculer la distance entre hotel.getAdresse() et temple.getAdresse()
		        jour1.ajouterTrajet(baliHotel, temple, "BATEAU");
		        
		        offre.ajouterExcursion(jour1);

		        System.out.println("--- OFFRE BALI ---");
		        System.out.println("Hôtel : " + offre.getHotel().getNom());
		        System.out.println("Coordonnées Hôtel : " + offre.getHotel().getAdresse().getLat() + " / " + offre.getHotel().getAdresse().getLon());
		        System.out.println("Prix total : " + offre.getPrix() + " €");
		    }

	}

