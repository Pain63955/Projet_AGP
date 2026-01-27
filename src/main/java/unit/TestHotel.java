package unit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import business.excursion.*;


public class TestHotel {
    
    private Hotel hotel;
    private Adresse adresse;
    
    @Before
    public void setUp() {
        hotel = new Hotel();
        adresse = new Adresse();
        adresse.setRue("10 Avenue des Champs");
        adresse.setVille("Nice");
        adresse.setCodePostal("06000");
        adresse.setLatitude(43.7102);
        adresse.setLongitude(7.2620);
    }
    
    @Test
    public void testCreationHotelVide() {
        assertNotNull(hotel);
        assertNull(hotel.getNom());
        assertEquals(0.0, hotel.getPrixNuit(),0.0);
        assertNull(hotel.getAdresse());
        assertNull(hotel.getDescription());
        assertNull(hotel.getGamme());
    }
    
    @Test
    public void testSettersGetters() {
        hotel.setId(1);
        hotel.setNom("Hôtel Paradise");
        hotel.setPrixNuit(150.0);
        hotel.setPlage("Plage des Anges");
        hotel.setAdresse(adresse);
        hotel.setDescription("Hôtel 4 étoiles en bord de mer");
        hotel.setGamme("Luxe");
        
        assertEquals(1, hotel.getId());
        assertEquals("Hôtel Paradise", hotel.getNom());
        assertEquals(150.0, hotel.getPrixNuit(),0.0);
        assertEquals("Plage des Anges", hotel.getPlage());
        assertEquals(adresse, hotel.getAdresse());
        assertEquals("Hôtel 4 étoiles en bord de mer", hotel.getDescription());
        assertEquals("Luxe", hotel.getGamme());
    }
    
    @Test
    public void testGetPrixRetournePrixNuit() {
        hotel.setPrixNuit(200.0);
        assertEquals(200.0, hotel.getPrix(),0.0);
    }
    
    @Test
    public void testElementTarifableInterface() {
        hotel.setPrixNuit(100.0);
        assertTrue(hotel instanceof ElementTarifiable);
        assertEquals(100.0, ((ElementTarifiable) hotel).getPrix(),0.0);
    }
    
    @Test
    public void testPrixNuitNegatif() {
        // Test cohérence : le prix ne devrait pas être négatif
        hotel.setPrixNuit(-50.0);
        assertTrue(hotel.getPrixNuit() < 0);
    }
    
    @Test
    public void testPrixNuitZero() {
        hotel.setPrixNuit(0.0);
        assertEquals(0.0, hotel.getPrixNuit(),0.0);
    }
    
    @Test
    public void testAssociationAvecAdresse() {
        hotel.setAdresse(adresse);
        assertNotNull(hotel.getAdresse());
        assertEquals("Nice", hotel.getAdresse().getVille());
        assertEquals(43.7102, hotel.getAdresse().getLatitude(), 0.0001);
    }
}
