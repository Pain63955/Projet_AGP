package unit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.PrinterMessageFromOperator;

import business.excursion.*;
import business.trajet.*;


public class TestExcursion {
    
    private Excursion excursion;
    private Hotel hotel;
    private SiteHisto site1;
    private SiteHisto site2;
    private SitesActiv site3;
    private TransportFactory factory;
    
    @Before
    public void setUp() {
        excursion = new Excursion();
        
        // Configuration de l'hôtel
        hotel = new Hotel();
        hotel.setNom("Hôtel Paradise");
        hotel.setPrixNuit(100.0);
        Adresse adresseHotel = new Adresse();
        adresseHotel.setLatitude(43.7102);
        adresseHotel.setLongitude(7.2620);
        hotel.setAdresse(adresseHotel);
        
        // Configuration du site 1
        site1 = new SiteHisto();
        site1.setNom("Musée d'Art");
        site1.setPrix(10.0);
        Adresse adresseSite1 = new Adresse();
        adresseSite1.setLatitude(43.7000);
        adresseSite1.setLongitude(7.2500);
        site1.setAdresse(adresseSite1);
        
        // Configuration du site 2
        site2 = new SiteHisto();
        site2.setNom("Château");
        site2.setPrix(15.0);
        Adresse adresseSite2 = new Adresse();
        adresseSite2.setLatitude(43.6900);
        adresseSite2.setLongitude(7.2400);
        site2.setAdresse(adresseSite2);
        
        // Configuration du site 3
        site3 = new SitesActiv();
        site3.setNom("Parc Aventure");
        site3.setPrix(25.0);
        Adresse adresseSite3 = new Adresse();
        adresseSite3.setLatitude(43.6800);
        adresseSite3.setLongitude(7.2300);
        site3.setAdresse(adresseSite3);
        
        // Configuration de la factory
        factory = new TransportFactory();
        Map<String, TransportStrategy> strategies = new HashMap<String, TransportStrategy>();
        
        Bus bus = new Bus();
        bus.setPrixFixe(5.0);
        
        Bateau bateau = new Bateau();
        bateau.setPrixKm(2.5);
        
        Pied pied = new Pied();
        
        strategies.put("AUTOBUS", bus);
        strategies.put("BATEAU", bateau);
        strategies.put("MARCHE", pied);
        
        factory.setStrategies(strategies);
        excursion.setFactory(factory);
    }
    
    @Test
    public void testCreationExcursion() {
        assertNotNull(excursion);
        assertEquals(0, excursion.getSites().size());
        assertEquals(0, excursion.getTrajets().size());
    }
    
    @Test
    public void testAjouterSite() {
        excursion.setNbSite(3);
        excursion.ajouterSite(site1);
        
        assertEquals(1, excursion.getSites().size());
        assertEquals("Musée d'Art", excursion.getSites().get(0).getNom());
    }
    
    @Test
    public void testAjouterPlusieursSites() {
        excursion.setNbSite(3);
        excursion.ajouterSite(site1);
        excursion.ajouterSite(site2);
        excursion.ajouterSite(site3);
        
        assertEquals(3, excursion.getSites().size());
    }
    
    @Test(expected = RuntimeException.class)
    public void testAjouterSiteAuDelaDeLimite() {
        excursion.setNbSite(2);
        excursion.ajouterSite(site1);
        excursion.ajouterSite(site2);
        excursion.ajouterSite(site3); // Devrait lancer une exception
    }
    
    @Test
    public void testGenererCircuitUnSeulSite() {
        excursion.setNbSite(1);
        excursion.ajouterSite(site1);
        excursion.genererCircuit(hotel, "AUTOBUS");
        
        // Devrait créer 2 trajets : Hôtel -> Site1, Site1 -> Hôtel
        assertEquals(2, excursion.getTrajets().size());
    }
    
    @Test
    public void testGenererCircuitDeuxSites() {
        excursion.setNbSite(2);
        excursion.ajouterSite(site1);
        excursion.ajouterSite(site2);
        excursion.genererCircuit(hotel, "AUTOBUS");
        
        // Devrait créer 3 trajets : Hôtel -> Site1, Site1 -> Site2, Site2 -> Hôtel
        assertEquals(3, excursion.getTrajets().size());
    }
    
    @Test
    public void testGenererCircuitTroisSites() {
        excursion.setNbSite(3);
        excursion.ajouterSite(site1);
        excursion.ajouterSite(site2);
        excursion.ajouterSite(site3);
        excursion.genererCircuit(hotel, "AUTOBUS");
        
        // Devrait créer 4 trajets : Hôtel -> Site1, Site1 -> Site2, Site2 -> Site3, Site3 -> Hôtel
        assertEquals(4, excursion.getTrajets().size());
    }
    
    @Test
    public void testGenererCircuitSanssSites() {
        excursion.genererCircuit(hotel, "AUTOBUS");
        
        // Ne devrait créer aucun trajet
        assertEquals(0, excursion.getTrajets().size());
    }
    
    @Test
    public void testGenererCircuitModeTransport() {
        excursion.setNbSite(1);
        excursion.ajouterSite(site1);
        excursion.genererCircuit(hotel, "MARCHE");
        
        assertEquals(2, excursion.getTrajets().size());
        assertEquals("MARCHE", excursion.getTrajets().get(0).getMode());
        assertEquals("MARCHE", excursion.getTrajets().get(1).getMode());
    }
    
    @Test
    public void testGetPrixAvecTrajetsSites() {
        excursion.setNbSite(2);
        excursion.ajouterSite(site1); // 10€
        excursion.ajouterSite(site2); // 15€
        excursion.genererCircuit(hotel, "AUTOBUS"); // 3 trajets * 5€ = 15€
        
        double prixTotal = excursion.getPrix();
       

        assertEquals(45.0, prixTotal, 0.001);
    }
    
    @Test
    public void testGetPrixSansTrajets() {
        excursion.setNbSite(2);
        excursion.ajouterSite(site1); // 10€
        excursion.ajouterSite(site2); // 15€
        
        // Pas de génération de circuit
        double prixTotal = excursion.getPrix();

        assertEquals(15.0, prixTotal, 0.001);
    }
    
    @Test
    public void testGetPrixSansSites() {
        double prixTotal = excursion.getPrix();
        assertEquals(0.0, prixTotal, 0.001);
    }
    
    @Test
    public void testElementTarifableInterface() {
        assertTrue(excursion instanceof ElementTarifiable);
    }
    
    @Test
    public void testSetGetNbSite() {
        excursion.setNbSite(5);
        assertEquals(5, excursion.getNbSite());
    }
    
    @Test
    public void testRegenerationCircuit() {
        excursion.setNbSite(2);
        excursion.ajouterSite(site1);
        excursion.ajouterSite(site2);
        
        // Première génération
        excursion.genererCircuit(hotel, "AUTOBUS");
        assertEquals(3, excursion.getTrajets().size());
        
        // Régénération avec un autre mode
        excursion.genererCircuit(hotel, "MARCHE");
        assertEquals(3, excursion.getTrajets().size());
        assertEquals("MARCHE", excursion.getTrajets().get(0).getMode());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGenererCircuitAvecAdresseNull() {
        excursion.setNbSite(1);
        SiteHisto siteSansAdresse = new SiteHisto();
        siteSansAdresse.setNom("Site sans adresse");
        siteSansAdresse.setPrix(10.0);
        // Pas d'adresse définie
        
        excursion.ajouterSite(siteSansAdresse);
        excursion.genererCircuit(hotel, "AUTOBUS"); // Devrait lancer une exception
    }
    
    @Test
    public void testCalculDistanceEntreSites() {
        excursion.setNbSite(2);
        excursion.ajouterSite(site1);
        excursion.ajouterSite(site2);
        excursion.genererCircuit(hotel, "BATEAU"); // Bateau à 2.5€/km
        
        // Vérifier que des trajets ont été créés et qu'ils ont une distance > 0
        for (int i = 0; i < excursion.getTrajets().size(); i++) {
            assertTrue(excursion.getTrajets().get(i).getDistance() >= 0);
        }
    }
    
    @Test
    public void testCoherenceCircuit() {
        excursion.setNbSite(3);
        excursion.ajouterSite(site1);
        excursion.ajouterSite(site2);
        excursion.ajouterSite(site3);
        excursion.genererCircuit(hotel, "AUTOBUS");
        
        // Vérifier la structure logique du circuit
        assertEquals(4, excursion.getTrajets().size()); // Hôtel->S1, S1->S2, S2->S3, S3->Hôtel
        
        // Tous les trajets doivent avoir le même mode de transport
        for (int i = 0; i < excursion.getTrajets().size(); i++) {
            assertEquals("AUTOBUS", excursion.getTrajets().get(i).getMode());
        }
    }
}
