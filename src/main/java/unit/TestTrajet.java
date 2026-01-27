package unit;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import business.excursion.*;
import business.trajet.*;

public class TestTrajet {
    
    private Trajet trajet;
    
    @Before
    public void setUp() {
        trajet = new Trajet(new Bus(5.0), 10.0);
    }
    
    @Test
    public void testCreationTrajet() {
        assertNotNull(trajet);
        assertEquals("AUTOBUS", trajet.getMode());
        assertEquals(5.0, trajet.getPrix(), 0.0);
        assertEquals(10.0, trajet.getDistance(), 0.0);
    }
    
    @Test
    public void testTrajetPied() {
        Trajet trajetPied = new Trajet(new Pied(), 5.0);
        
        assertEquals("MARCHE", trajetPied.getMode());
        assertEquals(0.0, trajetPied.getPrix(), 0.0);
        assertEquals(5.0, trajetPied.getDistance(), 0.0);
    }
    
    @Test
    public void testTrajetBateau() {
        Trajet trajetBateau = new Trajet(new Bateau(2), 10);
        
        assertEquals("BATEAU", trajetBateau.getMode());
        assertEquals(20.0, trajetBateau.getPrix(), 0.0);
        assertEquals(10.0, trajetBateau.getDistance(), 0.0);
    }
    
    @Test
    public void testElementTarifableInterface() {
        assertTrue(trajet instanceof ElementTarifiable);
        assertEquals(5.0, ((ElementTarifiable) trajet).getPrix(), 0.0);
    }
    
    @Test
    public void testGetPrix() {
        assertEquals(5.0, trajet.getPrix(), 0.0);
    }
    
    @Test
    public void testGetMode() {
        assertEquals("AUTOBUS", trajet.getMode());
    }
    
    @Test
    public void testGetDistance() {
        assertEquals(10.0, trajet.getDistance(), 0.0);
    }
    
    @Test
    public void testTrajetAvecDistanceZero() {
        Trajet trajetSurPlace = new Trajet(new Bus(), 0.0);
        assertEquals(0.0, trajetSurPlace.getDistance(), 0.0);
    }
    
    @Test
    public void testTrajetParametresNegatifs() {
        Trajet trajetNegatif = new Trajet(new Bus(), -10.0);
        assertTrue("Le système accepte une distance négative", trajetNegatif.getDistance() < 0);
    }
    
    @Test
    public void testCoherencePrixDistance() {
        // Pour un trajet en bateau à 2€/km
        Trajet trajetBateau1 = new Trajet(new Bateau(), 10.0);
        Trajet trajetBateau2 = new Trajet(new Bateau(), 20.0);
        
        // La distance double devrait doubler le prix (cohérence avec le tarif au km)
        assertEquals(trajetBateau1.getDistance() * 2, trajetBateau2.getDistance(), 0.001);
        assertEquals(trajetBateau1.getPrix() * 2, trajetBateau2.getPrix(), 0.001);
    }
}
