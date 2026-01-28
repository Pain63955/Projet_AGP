package api.core;

public class ParsedQuery {
	
	private String sql;
    private String textQuery;

    public ParsedQuery(String sql, String textQuery) {
        if (sql == null) {
            throw new IllegalArgumentException("SQL query cannot be null");
        }
        this.sql = sql;
        this.textQuery = textQuery;
    }

    public String getSql() {
        return sql;
    }

    public String getTextQuery() {
        return textQuery;
    }

    public boolean hasTextQuery() {
        return textQuery != null;
    }
}
