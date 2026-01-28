package api.operators;

import api.core.BDeRow;

public abstract class AbstractOperator implements Operator{
	private BDeRow currentBDeRow;
	
	BDeRow current() {
		return null;
	}
}
