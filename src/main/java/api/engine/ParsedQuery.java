package api.engine;

public class ParsedQuery {
	
	private String sqlQuery;
    private String textQuery;

    public ParsedQuery(String sqlQuery, String textQuery) {
        if (sqlQuery == null) {
            throw new IllegalArgumentException("SQL query cannot be null");
        }
        this.sqlQuery = sqlQuery;
        this.textQuery = textQuery;
    }
    
    public String getSqlQuery() {
        return sqlQuery;
    }

    public String getTextQuery() {
        return textQuery;
    }

    public boolean hasTextQuery() {
        return textQuery != null;
    }
}
