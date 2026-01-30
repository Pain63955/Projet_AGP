package unitAPI;

import org.junit.Test;

import api.utils.BDeQueryParser;

import static org.junit.Assert.*;

public class BDeQueryParserTest {

    @Test
    public void sqlPart_withWith_returnsLeftPart() {
        String q = "SELECT * FROM T WHERE id=1 WITH Forest";
        assertEquals("SELECT * FROM T WHERE id=1 ", BDeQueryParser.sqlPart(q));
    }

    @Test
    public void textPart_withWith_returnsRightPart() {
        String q = "SELECT * FROM T WHERE id=1 WITH Forest";
        assertEquals(" Forest", BDeQueryParser.textPart(q));
    }

    @Test
    public void textPart_withoutWith_returnsNull() {
        String q = "SELECT * FROM T";
        assertNull(BDeQueryParser.textPart(q));
    }

    @Test
    public void sqlPart_withoutWith_returnsWholeQuery() {
        String q = "SELECT * FROM T";
        assertEquals("SELECT * FROM T", BDeQueryParser.sqlPart(q));
    }

    @Test
    public void sqlPart_multipleWith_returnsBeforeFirstWith_only() {
        String q = "SELECT * FROM T WITH Forest WITH Ocean";
        assertEquals("SELECT * FROM T ", BDeQueryParser.sqlPart(q));
    }

    @Test
    public void textPart_multipleWith_returnsBetweenFirstAndSecondWith() {
        // Important: split("WITH") découpe sur chaque occurrence
        // textPart() renvoie parts[1], donc ici " Forest "
        String q = "SELECT * FROM T WITH Forest WITH Ocean";
        assertEquals(" Forest ", BDeQueryParser.textPart(q));
    }

    @Test(expected = NullPointerException.class)
    public void sqlPart_nullQuery_throwsNullPointerException() {
        BDeQueryParser.sqlPart(null);
    }

    @Test(expected = NullPointerException.class)
    public void textPart_nullQuery_throwsNullPointerException() {
        BDeQueryParser.textPart(null);
    }

    @Test
    public void emptyQuery_sqlPart_returnsEmptyString() {
        // Remarque: actuellement, la méthode ne lance pas IllegalArgumentException,
        // elle retourne "" car split("WITH") sur "" donne [""].
        assertEquals("", BDeQueryParser.sqlPart(""));
    }

    @Test
    public void emptyQuery_textPart_returnsNull() {
        assertNull(BDeQueryParser.textPart(""));
    }
}
