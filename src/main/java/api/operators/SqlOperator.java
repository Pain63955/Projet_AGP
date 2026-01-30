package api.operators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import api.iterator.BDeCurrentRow;
import api.visitor.OperatorVisitor;

public class SqlOperator extends Operator {

	private Connection connection;
    private String sqlPart;
    private String keyColumn;
    private HashMap<Integer, Object> queryParams;
    
    //JDBC
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;
    
//    private BDeActualRow currentRow;
    
	public SqlOperator(Connection connection, String sqlPart, String keyColumn, HashMap<Integer, Object> queryParams) {
		super(null,null);
		this.connection = connection;
		this.sqlPart = sqlPart;
		this.keyColumn = keyColumn;
		this.queryParams = queryParams;
	}
	
	@Override
	public void open() throws Exception {
		this.preparedStatement = connection.prepareStatement(sqlPart);
		bindParams();
		this.resultSet = preparedStatement.executeQuery();
		this.resultSetMetaData = resultSet.getMetaData();
		currentRow = null;
	}
	
	@Override
	public boolean next() throws Exception {
		if(resultSet == null) {
			throw new IllegalStateException("SqlOperator not opened, call open() ! :)");
		}
		if(!resultSet.next()) {
			currentRow = null;
			return false;
		}
		
		BDeCurrentRow row = new BDeCurrentRow();
		int numberRow = resultSetMetaData.getColumnCount();
		for(int columnIndex = 1; columnIndex<= numberRow; columnIndex++) {
			String label = resultSetMetaData.getColumnLabel(columnIndex);
			Object value = resultSet.getObject(columnIndex);
			row.put(label, value);
		}
		this.currentRow = row;
		return true;
	}
	
	@Override
	public void close() throws Exception {
		if (resultSet != null) {
			resultSet.close();
		}
		if(preparedStatement != null) {
			preparedStatement.close();
		}
		resultSet = null;
		preparedStatement = null;
		resultSetMetaData = null;
		currentRow = null;
	}
	
	@Override
	public BDeCurrentRow current() {
		if(currentRow == null) {
			throw new IllegalStateException("Current row is null. You need to call open().");
		}
		return currentRow;
	}
	
	public String getSqlPart() {
		return sqlPart;
	}

	public String getKeyColumn() {
		return keyColumn;
	}

	@Override
	public void accept(OperatorVisitor visitor) throws Exception  {
		visitor.visit(this);
	}
	
	private void bindParams() throws SQLException{
		if(queryParams != null) {
			for(int index=0; index< queryParams.size(); index++) {
				Object value = queryParams.get(index);
				if(value == null) {
					throw new IllegalStateException("Missing parameters");
				}
				preparedStatement.setObject(index + 1, value);
			}
		}
	}
	
}
