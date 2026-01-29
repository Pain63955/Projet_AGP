package api.visitor;

import java.sql.SQLException;

import api.operators.JoinOperator;
import api.operators.SqlOperator;
import api.operators.TextOperator;

public class CloseVisitor implements OperatorVisitor {
	
	@Override
	public void visit(SqlOperator operator) throws Exception {
		if(operator!=null) {
			operator.close();
		}		
	}

	@Override
	public void visit(TextOperator operator) {
		operator.close();
	}

	@Override
	public void visit(JoinOperator operator) throws Exception {
		operator.getRight().accept(this);
		operator.getLeft().accept(this);
		
		operator.close();
	}

}
