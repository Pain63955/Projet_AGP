package api.operators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import api.core.BDeActualRow;
import api.visitor.OperatorVisitor;

public class SqlOperator extends Operator {

	private Connection connection;
    private String sqlPart;
    private String keyColumn;
    
    //JDBC
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;
    
    private BDeActualRow currentRow;
    
	public SqlOperator(Connection connection, String sqlPart, String keyColumn) {
		super();
		this.connection = connection;
		this.sqlPart = sqlPart;
		this.keyColumn = keyColumn;
	}

	public void open() throws SQLException {
		this.preparedStatement = connection.prepareStatement(sqlPart);
		this.resultSet = preparedStatement.executeQuery();
		this.resultSetMetaData = preparedStatement.getMetaData();
		this.currentRow = null;
	}
	
	public boolean next() throws SQLException {
		if(resultSet == null) {
			throw new IllegalStateException("SqlOperator not opened, call open() ! :)");
		}
		if(!resultSet.next()) {
			currentRow = null;
			return false;
		}
		
		BDeActualRow row = new BDeActualRow();
		int numberRow = resultSetMetaData.getColumnCount();
		for(int columnIndex = 1; columnIndex<= numberRow; columnIndex++) {
			String label = resultSetMetaData.getColumnLabel(columnIndex);
			Object value = resultSet.getObject(columnIndex);
		}
		this.currentRow = row;
		return true;
	}
	
	public void close() throws SQLException {
		resultSet.close();
		preparedStatement.close();
		resultSet = null;
		preparedStatement = null;
		resultSetMetaData = null;
		currentRow = null;
	}
	
	public BDeActualRow current() {
		return currentRow;
	}
	
	
	public String getSqlPart() {
		return sqlPart;
	}

	public String getKeyColumn() {
		return keyColumn;
	}

	@Override
	public void accept(OperatorVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Tree getLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tree getRight() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
