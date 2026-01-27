package unit;

import static org.junit.Assert.*;
import org.junit.Test;

import business.excursion.Adresse;
import business.excursion.Hotel;
import persistence.jdbc.JdbcHotelPersistence;

public class JdbcHotelPersistenceTest {

	@Test
    public void testFetchName_existingHotel_returnsHotel() {
		
        String existingName = "Seminyak Beach Retreat";

        JdbcHotelPersistence dao = new JdbcHotelPersistence();
        Hotel h = dao.fetchName(existingName);
        Adresse a = new Adresse(1,-8.686075,115.154228,"Jl. Kayu Aya, Pantai","Seminyak","80361");
        assertNotNull("fetchName doit retourner un Hotel pour un nom existant", h);
        assertNotNull("L'id ne doit pas être null", h.getId());
        assertEquals("Nom incorrect", existingName, h.getNom());
        h.setAdresse(a);
        assertEquals("Adresse incorrect",a, h.getAdresse());

        // Optionnel si c’est stable en BD :
        assertEquals("Gamme incorrecte", "4 etoiles", h.getGamme());
        assertEquals("Plage incorrecte", "Plage de Petitenget", h.getPlage());
    }

    @Test
    public void testFetchName_unknownHotel_returnsNull() {
        JdbcHotelPersistence dao = new JdbcHotelPersistence();

        String unknownName = "___HOTEL_INEXISTANT___";
        Hotel h = dao.fetchName(unknownName);

        assertNull("fetchName doit retourner null si le nom n'existe pas", h);
    }

}
