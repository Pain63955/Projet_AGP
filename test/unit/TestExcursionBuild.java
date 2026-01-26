package unit;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;


import business.excursion.*;
import java.util.ArrayList;

public class TestExcursionBuild {

    private Adresse adresseHotel;
    private Adresse adresseSite1;
    private Adresse adresseSite2;
    private Adresse adresseSite3;
    private Adresse adresseSite4;
    private Hotel hotel;
    private SiteTouristique siteHisto;
    private SiteTouristique siteActiv;
    private Excursion excursion;

    @Before
    public void setUp() {
        // Création des adresses avec coordonnées GPS
        adresseHotel = new Adresse("10 Rue de la Plage", "Nice", "06000", 43.7102, 7.2620);
        adresseSite1 = new Adresse("5 Avenue des Musées", "Nice", "06000", 43.7034, 7.2663);
        adresseSite2 = new Adresse("15 Boulevard Victor Hugo", "Nice", "06000", 43.6961, 7.2669);
        adresseSite3 = new Adresse("20 Promenade des Anglais", "Nice", "06000", 43.6950, 7.2650);
        adresseSite4 = new Adresse("30 Rue Rossini", "Nice", "06000", 43.6980, 7.2700);
        
        // Création d'un hôtel
        Plage plage = new Plage(50);
        hotel = new Hotel("Hôtel Paradis", 150.0, plage, adresseHotel, "Hôtel de luxe", "5 étoiles", 1);
        
        // Création de sites touristiques
        ArrayList<String> langues = new ArrayList<>();
        langues.add("Français");
        langues.add("Anglais");
        siteHisto = new SitesHisto("Musée Matisse", "Musée d'art moderne", adresseSite1, 12.0, 1, "Jean Dupont", langues);
        siteActiv = new SitesActiv("Parc d'aventure", "Activités en plein air", adresseSite2, 25.0, 2, 3.5f);
        
        // Création d'une excursion
        excursion = new Excursion();
    }

    @Test
    public void testAjouterUnSite() {
        excursion.ajouterSite(siteHisto);
        assertEquals("Le prix devrait être celui du site", 12.0, excursion.getPrix(), 0.01);
    }

    @Test
    public void testAjouterDeuxSites() {
        excursion.ajouterSite(siteHisto);
        excursion.ajouterSite(siteActiv);
        
        double prixAttendu = siteHisto.getPrix() + siteActiv.getPrix();
        assertEquals("Le prix devrait être la somme des deux sites", prixAttendu, excursion.getPrix(), 0.01);
    }

    @Test
    public void testAjouterTroisSitesMaximum() {
        SiteTouristique site3 = new SitesActiv("Plage privée", "Accès plage", adresseSite3, 15.0, 3, 4.0f);
        
        excursion.ajouterSite(siteHisto);
        excursion.ajouterSite(siteActiv);
        excursion.ajouterSite(site3);
        
        double prixAttendu = siteHisto.getPrix() + siteActiv.getPrix() + site3.getPrix();
        assertEquals("Le prix devrait être la somme des 3 sites", prixAttendu, excursion.getPrix(), 0.01);
    }

    @Test(expected = RuntimeException.class)
    public void testAjouterQuatreSitesLanceException() {
        SiteTouristique site3 = new SitesActiv("Plage privée", "Accès plage", adresseSite3, 15.0, 3, 4.0f);
        SiteTouristique site4 = new SitesHisto("Cathédrale", "Monument historique", adresseSite4, 8.0, 4, "Marie Martin", new ArrayList<>());
        
        excursion.ajouterSite(siteHisto);
        excursion.ajouterSite(siteActiv);
        excursion.ajouterSite(site3);
        excursion.ajouterSite(site4); // Doit lancer RuntimeException
    }

    @Test
    public void testMessageExceptionQuatreSites() {
        SiteTouristique site3 = new SitesActiv("Plage privée", "Accès plage", adresseSite3, 15.0, 3, 4.0f);
        SiteTouristique site4 = new SitesHisto("Cathédrale", "Monument", adresseSite4, 8.0, 4, "Guide", new ArrayList<>());
        
        excursion.ajouterSite(siteHisto);
        excursion.ajouterSite(siteActiv);
        excursion.ajouterSite(site3);
        
        try {
            excursion.ajouterSite(site4);
            fail("Une RuntimeException aurait dû être levée");
        } catch (RuntimeException e) {
            assertTrue("Le message doit contenir 'Maximum'", e.getMessage().contains("Maximum"));
            assertTrue("Le message doit contenir '3'", e.getMessage().contains("3"));
        }
    }

    @Test
    public void testAjouterTrajetAugmentePrix() {
        excursion.ajouterSite(siteHisto);
        double prixAvantTrajet = excursion.getPrix();
        
        excursion.ajouterTrajet(hotel, siteHisto, "voiture");
        
        assertTrue("Le prix avec trajet doit être supérieur au prix sans trajet", 
                   excursion.getPrix() > prixAvantTrajet);
    }

    @Test
    public void testAjouterPlusieursTrajets() {
        excursion.ajouterSite(siteHisto);
        excursion.ajouterSite(siteActiv);
        
        excursion.ajouterTrajet(hotel, siteHisto, "voiture");
        excursion.ajouterTrajet(hotel, siteActiv, "bus");
        
        double prixSites = siteHisto.getPrix() + siteActiv.getPrix();
        assertTrue("Le prix total doit inclure les trajets", excursion.getPrix() > prixSites);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTrajetHotelNull() {
        excursion.ajouterTrajet(null, siteHisto, "voiture");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAjouterTrajetSiteNull() {
        excursion.ajouterTrajet(hotel, null, "voiture");
    }

    @Test
    public void testMessageExceptionHotelNull() {
        try {
            excursion.ajouterTrajet(null, siteHisto, "voiture");
            fail("IllegalArgumentException attendue");
        } catch (IllegalArgumentException e) {
            assertTrue("Le message doit mentionner l'hôtel", 
                      e.getMessage().toLowerCase().contains("hôtel") || 
                      e.getMessage().toLowerCase().contains("hotel"));
        }
    }

    @Test
    public void testMessageExceptionSiteNull() {
        try {
            excursion.ajouterTrajet(hotel, null, "voiture");
            fail("IllegalArgumentException attendue");
        } catch (IllegalArgumentException e) {
            assertTrue("Le message doit mentionner le site", 
                      e.getMessage().toLowerCase().contains("site"));
        }
    }

    @Test
    public void testExcursionCompleteAvecTrajets() {
        SiteTouristique site3 = new SitesActiv("Zoo", "Parc animalier", adresseSite3, 18.0, 3, 5.0f);
        
        excursion.ajouterSite(siteHisto);
        excursion.ajouterSite(siteActiv);
        excursion.ajouterSite(site3);
        
        excursion.ajouterTrajet(hotel, siteHisto, "voiture");
        excursion.ajouterTrajet(hotel, siteActiv, "bus");
        excursion.ajouterTrajet(hotel, site3, "taxi");
        
        double prixSites = siteHisto.getPrix() + siteActiv.getPrix() + site3.getPrix();
        double prixTotal = excursion.getPrix();
        
        assertTrue("Le prix total doit inclure les sites", prixTotal >= prixSites);
        assertTrue("Le prix total doit être supérieur aux sites seuls", prixTotal > prixSites);
    }

    @Test
    public void testExcursionVidePrixZero() {
        assertEquals("Une excursion vide doit avoir un prix de 0", 0.0, excursion.getPrix(), 0.01);
    }

    @Test
    public void testPrixExcursionAvecSeulementTrajets() {
        excursion.ajouterTrajet(hotel, siteHisto, "voiture");
        
        assertTrue("Une excursion avec seulement un trajet doit avoir un prix positif", 
                   excursion.getPrix() > 0);
    }

    @Test
    public void testCreationSitesHisto() {
        ArrayList<String> langues = new ArrayList<>();
        langues.add("Italien");
        langues.add("Espagnol");
        
        SitesHisto site = new SitesHisto("Château Nice", "Monument historique", adresseSite1, 10.0, 5, "Pierre Martin", langues);
        
        assertEquals("Le nom doit correspondre", "Château Nice", site.getNom());
        assertEquals("Le prix doit correspondre", 10.0, site.getPrix(), 0.01);
        assertEquals("Le nom du guide doit correspondre", "Pierre Martin", site.getGuideName());
        assertEquals("Le nombre de langues doit être 2", 2, site.getLangues().size());
        assertTrue("Les langues doivent contenir 'Italien'", site.getLangues().contains("Italien"));
    }

    @Test
    public void testCreationSitesActiv() {
        SitesActiv site = new SitesActiv("Randonnée Mont Boron", "Activité sportive", adresseSite2, 30.0, 6, 2.5f);
        
        assertEquals("Le nom doit correspondre", "Randonnée Mont Boron", site.getNom());
        assertEquals("Le prix doit correspondre", 30.0, site.getPrix(), 0.01);
        assertEquals("La durée doit correspondre", 2.5f, site.getDurationRatio(), 0.01);
        assertEquals("L'ID doit correspondre", 6, site.getId());
    }

    @Test
    public void testModificationPrixSite() {
        SitesActiv site = new SitesActiv("Activité", "Description", adresseSite1, 20.0, 7, 1.5f);
        assertEquals("Prix initial", 20.0, site.getPrix(), 0.01);
        
        site.setPrix(35.0);
        assertEquals("Prix modifié", 35.0, site.getPrix(), 0.01);
    }

    @Test
    public void testGettersSettersHotel() {
        assertEquals("Nom de l'hôtel", "Hôtel Paradis", hotel.getNom());
        assertEquals("Prix par nuit", 150.0, hotel.getPrixNuit(), 0.01);
        assertEquals("Gamme", "5 étoiles", hotel.getGamme());
        assertNotNull("L'adresse ne doit pas être null", hotel.getAdresse());
        assertNotNull("La plage ne doit pas être null", hotel.getPlage());
    }

    @Test
    public void testGettersSettersAdresse() {
        assertEquals("Rue", "10 Rue de la Plage", adresseHotel.getRue());
        assertEquals("Ville", "Nice", adresseHotel.getVille());
        assertEquals("Code postal", "06000", adresseHotel.getCodePostal());
        assertEquals("Latitude", 43.7102, adresseHotel.getLat(), 0.0001);
        assertEquals("Longitude", 7.2620, adresseHotel.getLon(), 0.0001);
    }

    @Test
    public void testModificationAdresse() {
        adresseHotel.setRue("25 Avenue Jean Médecin");
        assertEquals("Rue modifiée", "25 Avenue Jean Médecin", adresseHotel.getRue());
        
        adresseHotel.setLat(43.7000);
        assertEquals("Latitude modifiée", 43.7000, adresseHotel.getLat(), 0.0001);
    }

    @Test
    public void testPlageDisponibiliteTransat() {
        Plage plage = new Plage(100);
        assertEquals("Disponibilité initiale", 100, plage.getDispoTransat());
        
        plage.setDispoTransat(75);
        assertEquals("Disponibilité modifiée", 75, plage.getDispoTransat());
    }

    @Test
    public void testHotelImplementeElementTarifiable() {
        assertTrue("Hotel doit implémenter ElementTarifiable", 
                   hotel instanceof ElementTarifiable);
        assertEquals("getPrix() doit retourner le prix par nuit", 
                    150.0, hotel.getPrix(), 0.01);
    }

    @Test
    public void testSiteTouristiqueImplementeElementTarifiable() {
        assertTrue("SiteTouristique doit implémenter ElementTarifiable", 
                   siteHisto instanceof ElementTarifiable);
        assertTrue("SiteTouristique doit implémenter ElementTarifiable", 
                   siteActiv instanceof ElementTarifiable);
    }

    @Test
    public void testExcursionImplementeElementTarifiable() {
        assertTrue("Excursion doit implémenter ElementTarifiable", 
                   excursion instanceof ElementTarifiable);
    }

    @After
    public void tearDown() {
        excursion = null;
        hotel = null;
        siteHisto = null;
        siteActiv = null;
        adresseHotel = null;
        adresseSite1 = null;
        adresseSite2 = null;
        adresseSite3 = null;
        adresseSite4 = null;
    }
}