package api.operators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

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
    
//    private BDeActualRow currentRow;
    
	public SqlOperator(Connection connection, String sqlPart, String keyColumn) {
		super(null,null);
		this.connection = connection;
		this.sqlPart = sqlPart;
		this.keyColumn = keyColumn;
	}
	
	@Override
	public void open() throws Exception {
		this.preparedStatement = connection.prepareStatement(sqlPart);
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
		
		BDeActualRow row = new BDeActualRow();
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
	public BDeActualRow current() {
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
	
}
