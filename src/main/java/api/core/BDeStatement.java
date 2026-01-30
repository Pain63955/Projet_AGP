package api.core;

import java.util.HashMap;
import java.util.Map;

import api.operators.*;
import api.utils.BDeQueryParser;
import api.visitor.InitVisitor;

public class BDeStatement {
    private final BDeConnection connection;
    private final String query;
    private final HashMap<Integer, Object> queryParams = new HashMap<>();
    
    public BDeStatement(BDeConnection connection, String query) {
        this.connection = connection;
        this.query = query;
    }
    
    public BDeResultSet executeQuery(){
        ParsedQuery parsedQuery = BDeQueryParser.parse(query);
        String sqlPart = parsedQuery.getSqlQuery();
        String textPart = parsedQuery.getTextQuery();
        
        InitVisitor initVisitor = new InitVisitor();
        
        if(textPart==null) {
        	SqlOperator sqlOperator = new SqlOperator(connection.getJdbcConnection(), sqlPart, connection.getConfig().getKeyColumn(), queryParams);
        	try {
        		sqlOperator.accept(initVisitor);
        	}catch (Exception e) {
        		throw new RuntimeException("Failed to initialize operators for query: " + query, e);
        	}
        	return new BDeResultSet(sqlOperator);
        	
        }
        
        SqlOperator sqlOperator = new SqlOperator(connection.getJdbcConnection(), sqlPart, connection.getConfig().getKeyColumn(), queryParams);
        TextOperator textOperator = new TextOperator(textPart, connection.getLuceneIndexService());
        JoinOperator joinOperator = new JoinOperator(sqlOperator, textOperator);
        try {
        	joinOperator.accept(initVisitor);
    	} catch (Exception e) {
    		throw new RuntimeException("Failed to initialize operators for query: " + query, e);
    	}
        
        return new BDeResultSet(joinOperator);
        }
    
    public void setString(int index, String value) {
    	queryParams.put(index, value);
    }
    
    public void setInt(int index, int value) {
    	queryParams.put(index, value);
    }

}
