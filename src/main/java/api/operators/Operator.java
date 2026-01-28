package api.operators;

import api.core.BDeRow;
import api.visitor.OperatorVisitor;

public interface Operator {
	void init();
	boolean next();
	BDeRow current();
	void accept(OperatorVisitor visitor); 
}
