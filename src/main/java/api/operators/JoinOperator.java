package api.operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import api.iterator.BDeCurrentRow;
import api.visitor.OperatorVisitor;

public class JoinOperator extends Operator {
	
	private SqlOperator sqlOperator;
	private TextOperator textOperator;
	
	private HashMap<String, Double> scorebyKey;
	private ArrayList<String> keysOrdered;
	
	private ArrayList<BDeCurrentRow> queryResults;

	private int cursor;
	private BDeCurrentRow currentRow;
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
		// On ouvre sqlOperator et textOperator directement dans le visitor (visite des fils gauches et droits)
		scorebyKey = textOperator.getScoreByKey();
		keysOrdered = textOperator.getKeysOrdered();

		if (scorebyKey == null || keysOrdered == null) {
			throw new IllegalStateException("TextOperator not opened (InitVisitor must open children before JoinOperator.open()).");
		}
		
		HashMap<String, ArrayList<BDeCurrentRow>> joinMap = new HashMap<>();
		while(sqlOperator.next()){
			currentRow = sqlOperator.current();
			System.out.println(currentRow);
			String key = currentRow.getObject(keyColumn).toString();
			if(scorebyKey.containsKey(key)) {
				currentRow.setScore(scorebyKey.get(key));
				System.out.println(currentRow);
				ArrayList<BDeCurrentRow> rows = joinMap.get(key);
				if(rows == null) {
					rows = new ArrayList<BDeCurrentRow>();
					joinMap.put(key, rows);
				}
				rows.add(currentRow);
			}
		}
		
		queryResults = new ArrayList<BDeCurrentRow>();
		Iterator<String> ite = keysOrdered.iterator();
		while(ite.hasNext()) {
			String key = ite.next();
			if(joinMap.containsKey(key)){
				queryResults.addAll(joinMap.get(key));
			}
		}
		
		System.out.println(queryResults);
		
		cursor = -1;
		currentRow = null;
		opened = true;
	}
	
	public boolean next() throws Exception {
		if (!opened) {
	        throw new IllegalStateException("JoinOperator not opened, call open() (via InitVisitor).");
	    }
		
		if((cursor + 1) >= queryResults.size()) {
			cursor = queryResults.size();
			return false;
		}
		
		cursor++;
		currentRow = queryResults.get(cursor);
		return true;
		
	}
	
	public BDeCurrentRow current() {
		if(currentRow == null) {
			throw new IllegalStateException("Current row is null. You need to call open().");
		}
		return currentRow;
	}
	
	public void close() {
		scorebyKey = null;
		keysOrdered = null;
		cursor = -1;
		currentRow = null;
		opened = false;
		if(queryResults != null) {
			queryResults.clear();
		}
	}
	
	
	public ArrayList<BDeCurrentRow> getQueryResults() {
		return queryResults;
	}
	
	@Override
	public void accept(OperatorVisitor visitor) throws Exception{
		visitor.visit(this);
	}

}
