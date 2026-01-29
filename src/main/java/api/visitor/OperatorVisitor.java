package api.visitor;

import api.operators.*;

public interface OperatorVisitor {
	public void visit(SqlOperator operator) throws Exception;
	public void visit(TextOperator operator) throws Exception;
	public void visit(JoinOperator operator) throws Exception;
} 
