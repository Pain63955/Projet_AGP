package tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import business.excursion.*;


public class TestHotel {
    
    private Hotel hotel;
    private Address adresse;
    
    @Before
    public void setUp() {
        hotel = new Hotel();
        adresse = new Address();
        adresse.setStreet("10 Avenue des Champs");
        adresse.setTown("Nice");
        adresse.setPostCode("06000");
        adresse.setLatitude(43.7102);
        adresse.setLongitude(7.2620);
    }
    
    @Test
    public void testCreationHotelVide() {
        assertNotNull(hotel);
        assertNull(hotel.getName());
        assertEquals(0.0, hotel.getNightRate(),0.0);
        assertNull(hotel.getAddress());
        assertNull(hotel.getDescription());
        assertNull(hotel.getGrade());
    }
    
    @Test
    public void testSettersGetters() {
        hotel.setId(1);
        hotel.setName("Hôtel Paradise");
        hotel.setNightRate(150.0);
        hotel.setBeach("Plage des Anges");
        hotel.setAddress(adresse);
        hotel.setDescription("Hôtel 4 étoiles en bord de mer");
        hotel.setGrade("Luxe");
        
        assertEquals(1, hotel.getId());
        assertEquals("Hôtel Paradise", hotel.getName());
        assertEquals(150.0, hotel.getNightRate(),0.0);
        assertEquals("Plage des Anges", hotel.getBeach());
        assertEquals(adresse, hotel.getAddress());
        assertEquals("Hôtel 4 étoiles en bord de mer", hotel.getDescription());
        assertEquals("Luxe", hotel.getGrade());
    }
    
    @Test
    public void testGetPrixRetournePrixNuit() {
        hotel.setNightRate(200.0);
        assertEquals(200.0, hotel.getPrice(),0.0);
    }
    
    @Test
    public void testElementTarifableInterface() {
        hotel.setNightRate(100.0);
        assertTrue(hotel instanceof PriceableElement);
        assertEquals(100.0, ((PriceableElement) hotel).getPrice(),0.0);
    }
    
    @Test
    public void testPrixNuitNegatif() {
        // Test cohérence : le prix ne devrait pas être négatif
        hotel.setNightRate(-50.0);
        assertTrue(hotel.getNightRate() < 0);
    }
    
    @Test
    public void testPrixNuitZero() {
        hotel.setNightRate(0.0);
        assertEquals(0.0, hotel.getNightRate(),0.0);
    }
    
    @Test
    public void testAssociationAvecAdresse() {
        hotel.setAddress(adresse);
        assertNotNull(hotel.getAddress());
        assertEquals("Nice", hotel.getAddress().getTown());
        assertEquals(43.7102, hotel.getAddress().getLatitude(), 0.0001);
    }
}
