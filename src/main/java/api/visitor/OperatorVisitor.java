package api.visitor;

import api.operators.*;

public interface OperatorVisitor {
	public void visit(SqlOperator operator);
	public void visit(TextOperator operator);
	public void visit(JoinOperator operator);
} 
