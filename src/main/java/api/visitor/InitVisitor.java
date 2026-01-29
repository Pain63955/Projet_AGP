package api.visitor;

import api.operators.JoinOperator;
import api.operators.SqlOperator;
import api.operators.TextOperator;

public class InitVisitor implements OperatorVisitor {

	@Override
	public void visit(SqlOperator operator) throws Exception{
		operator.open();		
	}

	@Override
	public void visit(TextOperator operator) throws Exception{
		operator.open();		
	}

	@Override
	public void visit(JoinOperator operator) throws Exception{
		operator.getLeft().accept(this);
		operator.getRight().accept(this);
		
		operator.open();		
	}

}
