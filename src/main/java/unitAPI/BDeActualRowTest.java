package unitAPI;

import api.core.BDeActualRow;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BDeActualRowTest {

    @Test
    public void emptyConstructor_createsEmptyRow() {
        BDeActualRow row = new BDeActualRow();
        assertNotNull(row.asMap());
        assertTrue(row.asMap().isEmpty());
    }

    @Test
    public void constructorWithMap_copiesValues() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "Hotel");

        BDeActualRow row = new BDeActualRow(map);

        assertEquals(1, row.getInt("id").intValue());
        assertEquals("Hotel", row.getString("name"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithNullMap_throwsException() {
        new BDeActualRow(null);
    }

    @Test
    public void put_and_getObject_work() {
        BDeActualRow row = new BDeActualRow();
        row.put("col", "value");

        assertEquals("value", row.getObject("col"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void put_withNullColumn_throwsException() {
        BDeActualRow row = new BDeActualRow();
        row.put(null, "x");
    }

    @Test(expected = IllegalArgumentException.class)
    public void put_withEmptyColumn_throwsException() {
        BDeActualRow row = new BDeActualRow();
        row.put("   ", "x");
    }

    @Test
    public void getString_convertsValue() {
        BDeActualRow row = new BDeActualRow();
        row.put("a", 123);

        assertEquals("123", row.getString("a"));
    }

    @Test
    public void getInt_fromNumber_andString() {
        BDeActualRow row = new BDeActualRow();
        row.put("n1", 42);
        row.put("n2", "84");

        assertEquals(42, row.getInt("n1").intValue());
        assertEquals(84, row.getInt("n2").intValue());
    }

    @Test
    public void getLong_fromNumber_andString() {
        BDeActualRow row = new BDeActualRow();
        row.put("l1", 123L);
        row.put("l2", "456");

        assertEquals(123L, row.getLong("l1").longValue());
        assertEquals(456L, row.getLong("l2").longValue());
    }

    @Test
    public void getDouble_fromNumber_andString() {
        BDeActualRow row = new BDeActualRow();
        row.put("d1", 1.5);
        row.put("d2", "2.5");

        assertEquals(1.5, row.getDouble("d1"), 0.0001);
        assertEquals(2.5, row.getDouble("d2"), 0.0001);
    }

    @Test
    public void getBoolean_fromBoolean_andString() {
        BDeActualRow row = new BDeActualRow();
        row.put("b1", true);
        row.put("b2", "false");

        assertTrue(row.getBoolean("b1"));
        assertFalse(row.getBoolean("b2"));
    }

    @Test
    public void getters_returnNull_whenColumnMissing() {
        BDeActualRow row = new BDeActualRow();

        assertNull(row.getString("x"));
        assertNull(row.getInt("x"));
        assertNull(row.getLong("x"));
        assertNull(row.getDouble("x"));
        assertNull(row.getBoolean("x"));
    }

    @Test
    public void score_canBeSetAndRetrieved() {
        BDeActualRow row = new BDeActualRow();
        row.setScore(0.87);

        assertEquals(0.87, row.getScore(), 0.0001);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void asMap_isUnmodifiable() {
        BDeActualRow row = new BDeActualRow();
        row.put("a", 1);

        row.asMap().put("b", 2);
    }

    @Test
    public void toString_containsValuesAndScore() {
        BDeActualRow row = new BDeActualRow();
        row.put("id", 1);
        row.setScore(0.5);

        String s = row.toString();
        assertTrue(s.contains("id"));
        assertTrue(s.contains("score"));
    }
}
