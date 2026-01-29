package api.core;

import java.util.Collections;
import java.util.List;

public class BDeResultSet {

	private List<BDeActualRow> rows;
	private int cursor = -1;
	
	public BDeResultSet(List<BDeActualRow> rows) {
		super();
		this.rows = rows;
	}
	
	public boolean next() {
		if(cursor + 1 >= rows.size()) {
			cursor = rows.size();
			return false;
		}
		cursor++;
		return false;
	}
	
	public BDeActualRow current() {
        if (cursor < 0) {
            throw new IllegalStateException("Cursor is before first row. Call next() first.");
        }
        if (cursor >= rows.size()) {
            throw new IllegalStateException("Cursor is after last row.");
        }
        return rows.get(cursor);
    }
	
	public Object getObject(String columnLabel) {
        return current().getObject(columnLabel);
    }

    public String getString(String columnLabel) {
        return current().getString(columnLabel);
    }

    public Integer getInt(String columnLabel) {
        return current().getInt(columnLabel);
    }

    public Long getLong(String columnLabel) {
        return current().getLong(columnLabel);
    }

    public Double getDouble(String columnLabel) {
        return current().getDouble(columnLabel);
    }

    public Boolean getBoolean(String columnLabel) {
        return current().getBoolean(columnLabel);
    }

    public double getScore() {
        return current().getScore();
    }

    public int size() {
        return rows.size();
    }

    public List<BDeActualRow> asList() {
        return Collections.unmodifiableList(rows);
    }
}
