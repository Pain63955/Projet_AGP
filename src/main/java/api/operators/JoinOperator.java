package api.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import api.core.BDeActualRow;
import api.visitor.OperatorVisitor;

public class JoinOperator extends Operator {
	
	private SqlOperator sqlOperator;
	private TextOperator textOperator;
	
	private HashMap<String, Double> scorebyKey;
	private ArrayList<String> keysOrdered;
	
	private ArrayList<BDeActualRow> queryResults;
	private int cursor;
	private BDeActualRow currentRow;
	private String keyColumn;
	private boolean opened;
	
	public JoinOperator(Tree left, Tree right) {
		super(left, right);
		if(!(left instanceof SqlOperator)) {
			throw new IllegalArgumentException("left operator should be an SqlOperator");
		}
		if(!(right instanceof TextOperator)) {
			throw new IllegalArgumentException("right operator should be a TextOperator");
		}
		this.sqlOperator = (SqlOperator) left;
		this.textOperator = (TextOperator) right;
		this.keyColumn = sqlOperator.getKeyColumn();
	}
	
	public void open() throws Exception {
		// On ouvre texte operator pour créer la hashmap de (key -> score) et l'arraylist (key) triée dans l'ordre
		// La hashmap scorebyKey permet de faire la jointure (vérifier que clé c correspond bien à la ligne de la table T)
		// l'arrayList keysOrdered permet de trier la jointure à la fin
		textOperator.open();
		scorebyKey = textOperator.getScoreByKey();
		keysOrdered = textOperator.getKeysOrdered();
		
		sqlOperator.open();
		
		HashMap<String, ArrayList<BDeActualRow>> joinMap = new HashMap<>();
		while(sqlOperator.next()){
			currentRow = sqlOperator.current();
			String key = currentRow.getObject(keyColumn).toString();
			
			if(scorebyKey.containsKey(key)) {
				currentRow.setScore(scorebyKey.get(key));
				ArrayList<BDeActualRow> rows = joinMap.get(key);
				if(rows == null) {
					rows = new ArrayList<BDeActualRow>();
					joinMap.put(key, rows);
				}
				rows.add(currentRow);
			}
		}
		
		queryResults = new ArrayList<BDeActualRow>();
		Iterator<String> ite = keysOrdered.iterator();
		while(ite.hasNext()) {
			String key = ite.next();
			if(joinMap.containsKey(key)){
				queryResults.addAll(joinMap.get(key));
			}
		}
		
		cursor = -1;
		currentRow = null;
		opened = true;
	}
	
	public boolean next() throws Exception {
		if(!opened) {
			open();
		}
		
		if((cursor + 1) >= queryResults.size()) {
			cursor = queryResults.size();
			return false;
		}
		
		cursor++;
		currentRow = queryResults.get(cursor);
		return true;
		
	}
	
	public void close() {
		scorebyKey = null;
		keysOrdered = null;
		queryResults.clear();
		cursor = -1;
		currentRow = null;
		opened = false;
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
