package unit;

import static org.junit.Assert.*;
import org.junit.Test;

import business.excursion.Address;
import business.excursion.Hotel;
import persistence.jdbc.JdbcHotelPersistence;

public class JdbcHotelPersistenceTest {

	@Test
    public void testFetchName_existingHotel_returnsHotel() {
		
        String existingName = "Seminyak Beach Retreat";

        JdbcHotelPersistence dao = new JdbcHotelPersistence();
        Hotel h = dao.fetchName(existingName);
        Address a = new Address();
        assertNotNull("fetchName doit retourner un Hotel pour un nom existant", h);
        assertNotNull("L'id ne doit pas être null", h.getId());
        assertEquals("Nom incorrect", existingName, h.getName());
        h.setAddress(a);
        assertEquals("Adresse incorrect",a, h.getAddress());

        // Optionnel si c’est stable en BD :
        assertEquals("Gamme incorrecte", "4 etoiles", h.getGrade());
        assertEquals("Plage incorrecte", "Plage de Petitenget", h.getBeach());
    }

    @Test
    public void testFetchName_unknownHotel_returnsNull() {
        JdbcHotelPersistence dao = new JdbcHotelPersistence();

        String unknownName = "___HOTEL_INEXISTANT___";
        Hotel h = dao.fetchName(unknownName);

        assertNull("fetchName doit retourner null si le nom n'existe pas", h);
    }

}
