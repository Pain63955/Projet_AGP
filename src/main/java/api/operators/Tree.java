package api.operators;

import api.visitor.OperatorVisitor;

public interface Tree{
	
	Tree getLeft();

	Tree getRight();
	
	void accept(OperatorVisitor visitor); 
}
