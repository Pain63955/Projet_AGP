package unitAPI;

import api.core.ParsedQuery;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParsedQueryTest {

    @Test
    public void constructor_setsSqlAndTextQuery() {
        ParsedQuery pq = new ParsedQuery(
                "SELECT * FROM T",
                "Forest river"
        );

        assertEquals("SELECT * FROM T", pq.getSql());
        assertEquals("Forest river", pq.getTextQuery());
    }

    @Test
    public void constructor_allowsNullTextQuery() {
        ParsedQuery pq = new ParsedQuery(
                "SELECT * FROM T",
                null
        );

        assertEquals("SELECT * FROM T", pq.getSql());
        assertNull(pq.getTextQuery());
        assertFalse(pq.hasTextQuery());
    }

    @Test
    public void hasTextQuery_returnsTrueWhenTextQueryNotNull() {
        ParsedQuery pq = new ParsedQuery(
                "SELECT * FROM T",
                "Forest"
        );

        assertTrue(pq.hasTextQuery());
    }

    @Test
    public void hasTextQuery_returnsFalseWhenTextQueryIsNull() {
        ParsedQuery pq = new ParsedQuery(
                "SELECT * FROM T",
                null
        );

        assertFalse(pq.hasTextQuery());
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_nullSql_throwsException() {
        new ParsedQuery(null, "Forest");
    }

    @Test
    public void emptySql_isAccepted_byCurrentImplementation() {
        ParsedQuery pq = new ParsedQuery("", "Forest");

        assertEquals("", pq.getSql());
        assertTrue(pq.hasTextQuery());
    }
}
