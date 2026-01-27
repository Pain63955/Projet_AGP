package unit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import business.excursion.*;

public class TestSiteTouristique {
    
    private SiteHisto siteHisto;
    private SitesActiv siteActiv;
    private Adresse adresse;
    
    @Before
    public void setUp() {
        siteHisto = new SiteHisto();
        siteActiv = new SitesActiv();
        
        adresse = new Adresse();
        adresse.setRue("1 Place du Monument");
        adresse.setVille("Paris");
        adresse.setCodePostal("75007");
        adresse.setLatitude(48.8584);
        adresse.setLongitude(2.2945);
    }
    
    @Test
    public void testCreationSiteHistorique() {
        assertNotNull(siteHisto);
        assertNull(siteHisto.getNom());
        assertNull(siteHisto.getDescription());
        assertNull(siteHisto.getAdresse());
        assertEquals(0.0, siteHisto.getPrix(), 0.0);
    }
    
    @Test
    public void testCreationSiteActivite() {
        assertNotNull(siteActiv);
        assertNull(siteActiv.getNom());
        assertNull(siteActiv.getDescription());
        assertNull(siteActiv.getAdresse());
        assertEquals(0.0, siteActiv.getPrix(), 0.0);
        assertEquals(0.0f, siteActiv.getDurationRatio(), 0.0f);
    }
    
    @Test
    public void testSiteHistoriqueSettersGetters() {
        siteHisto.setNom("Tour Eiffel");
        siteHisto.setDescription("Monument emblématique de Paris");
        siteHisto.setAdresse(adresse);
        siteHisto.setPrix(25.0);
        siteHisto.setGuideName("Jean Dupont");
        
        assertEquals("Tour Eiffel", siteHisto.getNom());
        assertEquals("Monument emblématique de Paris", siteHisto.getDescription());
        assertEquals(adresse, siteHisto.getAdresse());
        assertEquals(25.0, siteHisto.getPrix(), 0.0);
        assertEquals("Jean Dupont", siteHisto.getGuideName());
    }
    
    @Test
    public void testSiteActiviteSettersGetters() {
        siteActiv.setNom("Parc Aquatique");
        siteActiv.setDescription("Parc avec toboggans et piscines");
        siteActiv.setAdresse(adresse);
        siteActiv.setPrix(35.0);
        siteActiv.setDurationRatio(2.5f);
        
        assertEquals("Parc Aquatique", siteActiv.getNom());
        assertEquals("Parc avec toboggans et piscines", siteActiv.getDescription());
        assertEquals(adresse, siteActiv.getAdresse());
        assertEquals(35.0, siteActiv.getPrix(), 0.0);
        assertEquals(2.5f, siteActiv.getDurationRatio(), 0.0f);
    }
    
    @Test
    public void testElementTarifableInterface() {
        siteHisto.setPrix(20.0);
        assertTrue(siteHisto instanceof ElementTarifiable);
        assertEquals(20.0, ((ElementTarifiable) siteHisto).getPrix(), 0.0);
        
        siteActiv.setPrix(30.0);
        assertTrue(siteActiv instanceof ElementTarifiable);
        assertEquals(30.0, ((ElementTarifiable) siteActiv).getPrix(), 0.0);
    }
    
    @Test
    public void testLanguesSiteHistorique() {
        siteHisto.getLangues().add("Français");
        siteHisto.getLangues().add("Anglais");
        siteHisto.getLangues().add("Espagnol");
        
        assertEquals(3, siteHisto.getLangues().size());
        assertTrue(siteHisto.getLangues().contains("Français"));
        assertTrue(siteHisto.getLangues().contains("Anglais"));
    }
    
    @Test
    public void testHeritageSiteHistorique() {
        assertTrue(siteHisto instanceof SiteTouristique);
        siteHisto.setPrix(15.0);
        assertEquals(15.0, siteHisto.getPrix(), 0.0);
    }
    
    @Test
    public void testHeritageSiteActivite() {
        assertTrue(siteActiv instanceof SiteTouristique);
        siteActiv.setPrix(40.0);
        assertEquals(40.0, siteActiv.getPrix(), 0.0);
    }
    
    @Test
    public void testDurationRatioNegatif() {
        siteActiv.setDurationRatio(-1.0f);
        assertTrue("Le système accepte un ratio négatif - validation métier à ajouter", 
                   siteActiv.getDurationRatio() < 0);
    }
}
