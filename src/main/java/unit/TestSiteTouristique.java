package unit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import business.excursion.*;

public class TestSiteTouristique {
    
    private HistoricSite siteHisto;
    private ActivitySite siteActiv;
    private Address adresse;
    
    @Before
    public void setUp() {
        siteHisto = new HistoricSite();
        siteActiv = new ActivitySite();
        
        adresse = new Address();
        adresse.setStreet("1 Place du Monument");
        adresse.setTown("Paris");
        adresse.setPostCode("75007");
        adresse.setLatitude(48.8584);
        adresse.setLongitude(2.2945);
    }
    
    @Test
    public void testCreationSiteHistorique() {
        assertNotNull(siteHisto);
        assertNull(siteHisto.getName());
        assertNull(siteHisto.getDescription());
        assertNull(siteHisto.getAddress());
        assertEquals(0.0, siteHisto.getPrice(), 0.0);
    }
    
    @Test
    public void testCreationSiteActivite() {
        assertNotNull(siteActiv);
        assertNull(siteActiv.getName());
        assertNull(siteActiv.getDescription());
        assertNull(siteActiv.getAddress());
        assertEquals(0.0, siteActiv.getPrice(), 0.0);
        assertEquals(0.0f, siteActiv.getDurationRatio(), 0.0f);
    }
    
    @Test
    public void testSiteHistoriqueSettersGetters() {
        siteHisto.setName("Tour Eiffel");
        siteHisto.setDescription("Monument emblématique de Paris");
        siteHisto.setAddress(adresse);
        siteHisto.setPrice(25.0);
        siteHisto.setGuideName("Jean Dupont");
        
        assertEquals("Tour Eiffel", siteHisto.getName());
        assertEquals("Monument emblématique de Paris", siteHisto.getDescription());
        assertEquals(adresse, siteHisto.getAddress());
        assertEquals(25.0, siteHisto.getPrice(), 0.0);
        assertEquals("Jean Dupont", siteHisto.getGuideName());
    }
    
    @Test
    public void testSiteActiviteSettersGetters() {
        siteActiv.setName("Parc Aquatique");
        siteActiv.setDescription("Parc avec toboggans et piscines");
        siteActiv.setAddress(adresse);
        siteActiv.setPrice(35.0);
        siteActiv.setDurationRatio(2.5f);
        
        assertEquals("Parc Aquatique", siteActiv.getName());
        assertEquals("Parc avec toboggans et piscines", siteActiv.getDescription());
        assertEquals(adresse, siteActiv.getAddress());
        assertEquals(35.0, siteActiv.getPrice(), 0.0);
        assertEquals(2.5f, siteActiv.getDurationRatio(), 0.0f);
    }
    
    @Test
    public void testElementTarifableInterface() {
        siteHisto.setPrice(20.0);
        assertTrue(siteHisto instanceof PriceableElement);
        assertEquals(20.0, ((PriceableElement) siteHisto).getPrice(), 0.0);
        
        siteActiv.setPrice(30.0);
        assertTrue(siteActiv instanceof PriceableElement);
        assertEquals(30.0, ((PriceableElement) siteActiv).getPrice(), 0.0);
    }
    
    @Test
    public void testLanguesSiteHistorique() {
        siteHisto.getLanguages().add("Français");
        siteHisto.getLanguages().add("Anglais");
        siteHisto.getLanguages().add("Espagnol");
        
        assertEquals(3, siteHisto.getLanguages().size());
        assertTrue(siteHisto.getLanguages().contains("Français"));
        assertTrue(siteHisto.getLanguages().contains("Anglais"));
    }
    
    @Test
    public void testHeritageSiteHistorique() {
        assertTrue(siteHisto instanceof TouristSite);
        siteHisto.setPrice(15.0);
        assertEquals(15.0, siteHisto.getPrice(), 0.0);
    }
    
    @Test
    public void testHeritageSiteActivite() {
        assertTrue(siteActiv instanceof TouristSite);
        siteActiv.setPrice(40.0);
        assertEquals(40.0, siteActiv.getPrice(), 0.0);
    }
    
    @Test
    public void testDurationRatioNegatif() {
        siteActiv.setDurationRatio(-1.0f);
        assertTrue("Le système accepte un ratio négatif - validation métier à ajouter", 
                   siteActiv.getDurationRatio() < 0);
    }
}
