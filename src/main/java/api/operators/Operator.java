package api.operators;

import api.visitor.OperatorVisitor;

public interface Operator {
		  void accept(OperatorVisitor visitor); 
}
