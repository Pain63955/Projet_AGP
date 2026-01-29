package api.operators;

import java.util.ArrayList;
import java.util.HashMap;

import api.core.BDeConfig;
import api.core.ParsedQuery;
import api.visitor.OperatorVisitor;

public class JoinOperator extends Operator {
	
	private SqlOperator sqlOperator;
	private TextOperator textOperator;
	
	private HashMap<Integer, Double> scorebyKey;
	private ArrayList<Integer> keysOrdered;
	
	public JoinOperator(SqlOperator sqlOperator, TextOperator textOperator) {
		super(sqlOperator, textOperator);
		this.config = config;
		this.parsedQuery = parsedQuery;
	}
	
	public void test() {
		
	}

	@Override
	public void accept(OperatorVisitor visitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Tree getLeft() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tree getRight() {
		// TODO Auto-generated method stub
		return null;
	}

}
