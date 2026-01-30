package api.core;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class BDeCurrentRow {

    private final Map<String, Object> valuesByColumn;
    private double score;
    
    public BDeCurrentRow() {
        this.valuesByColumn = new LinkedHashMap<>();
    }

    public BDeCurrentRow(Map<String, Object> valuesByColumn) {
        if (valuesByColumn == null) {
            throw new IllegalArgumentException("valuesByColumn is null");
        }
        this.valuesByColumn = new LinkedHashMap<>(valuesByColumn);
    }

    public void put(String columnLabel, Object value) {
        if (columnLabel == null || columnLabel.trim().isEmpty()) {
            throw new IllegalArgumentException("columnLabel is empty");
        }
        valuesByColumn.put(columnLabel, value);
    }

    public Object getObject(String columnLabel) {
        return valuesByColumn.get(columnLabel);
    }

    public String getString(String columnLabel) {
        Object v = valuesByColumn.get(columnLabel);
        if (v == null) return null;
        return String.valueOf(v);
    }

    public Integer getInt(String columnLabel) {
        Object v = valuesByColumn.get(columnLabel);
        if (v == null) return null;
        if (v instanceof Number) return ((Number) v).intValue();
        return Integer.parseInt(v.toString());
    }

    public Long getLong(String columnLabel) {
        Object v = valuesByColumn.get(columnLabel);
        if (v == null) return null;
        if (v instanceof Number) return ((Number) v).longValue();
        return Long.parseLong(v.toString());
    }

    public Double getDouble(String columnLabel) {
        Object v = valuesByColumn.get(columnLabel);
        if (v == null) return null;
        if (v instanceof Number) return ((Number) v).doubleValue();
        return Double.parseDouble(v.toString());
    }

    public Boolean getBoolean(String columnLabel) {
        Object v = valuesByColumn.get(columnLabel);
        if (v == null) return null;
        if (v instanceof Boolean) return (Boolean) v;
        return Boolean.parseBoolean(v.toString());
    }

    /** Score Lucene pour requêtes mixtes (null pour SQL pure) */
    public double getScore() {
        return score;
    }
    
    public Float getFloat(String columnLabel) {
    	 Object v = valuesByColumn.get(columnLabel);
         if (v == null) return null;
         if (v instanceof Float) return (Float) v;
         return Float.parseFloat(v.toString());
    }
    public void setScore(Double score) {
        this.score = score;
    }
}
