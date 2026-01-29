package api.visitor;

import java.sql.SQLException;

import api.operators.*;

public interface OperatorVisitor {
	public void visit(SqlOperator operator) throws SQLException;
	public void visit(TextOperator operator) throws Exception;
	public void visit(JoinOperator operator) throws Exception;
} 
