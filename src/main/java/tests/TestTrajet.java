package tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import business.excursion.*;
import business.path.*;

public class TestTrajet {
     
    private Path trajet;
    
    @Before
    public void setUp() {
        trajet = new Path(new Bus(5.0), 10.0);
    }
    
    @Test
    public void testCreationTrajet() {
        assertNotNull(trajet);
        assertEquals("BUS", trajet.getMode());
        assertEquals(5.0, trajet.getPrice(), 0.0);
        assertEquals(10.0, trajet.getDistance(), 0.0);
    }
    
    @Test
    public void testTrajetPied() {
        Path trajetPied = new Path(new Feet(), 5.0);
        
        assertEquals("WALK", trajetPied.getMode());
        assertEquals(0.0, trajetPied.getPrice(), 0.0);
        assertEquals(5.0, trajetPied.getDistance(), 0.0);
    }
    
    @Test
    public void testTrajetBateau() {
        Path trajetBateau = new Path(new Boat(2), 10);
        
        assertEquals("BOAT", trajetBateau.getMode());
        assertEquals(20.0, trajetBateau.getPrice(), 0.0);
        assertEquals(10.0, trajetBateau.getDistance(), 0.0);
    }
    
    @Test
    public void testElementTarifableInterface() {
        assertTrue(trajet instanceof PriceableElement);
        assertEquals(5.0, ((PriceableElement) trajet).getPrice(), 0.0);
    }
    
    @Test
    public void testGetPrix() {
        assertEquals(5.0, trajet.getPrice(), 0.0);
    }
    
    @Test
    public void testGetMode() {
        assertEquals("BUS", trajet.getMode());
    }
    
    @Test
    public void testGetDistance() {
        assertEquals(10.0, trajet.getDistance(), 0.0);
    }
    
    @Test
    public void testTrajetAvecDistanceZero() {
        Path trajetSurPlace = new Path(new Bus(), 0.0);
        assertEquals(0.0, trajetSurPlace.getDistance(), 0.0);
    }
    
    @Test
    public void testTrajetParametresNegatifs() {
        Path trajetNegatif = new Path(new Bus(), -10.0);
        assertTrue("Le système accepte une distance négative", trajetNegatif.getDistance() < 0);
    }
    
    @Test
    public void testCoherencePrixDistance() {
        // Pour un trajet en bateau à 2€/km
        Path trajetBateau1 = new Path(new Boat(), 10.0);
        Path trajetBateau2 = new Path(new Boat(), 20.0);
        
        // La distance double devrait doubler le prix (cohérence avec le tarif au km)
        assertEquals(trajetBateau1.getDistance() * 2, trajetBateau2.getDistance(), 0.001);
        assertEquals(trajetBateau1.getPrice() * 2, trajetBateau2.getPrice(), 0.001);
    }
}
