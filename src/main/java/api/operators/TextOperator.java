package api.operators;

import java.util.ArrayList;
import java.util.HashMap;

import api.engine.BDeConnection;
import api.text.LuceneService;
import api.visitor.OperatorVisitor;

public class TextOperator extends Operator {
	
	private String textPart;
	private BDeConnection connection;
	
	private HashMap<String, Double> scoreByKey;
	private ArrayList<String> keysOrdered;
	
	public TextOperator(String textPart, BDeConnection connection) {
		super(null,null);
		this.textPart = textPart;
		this.connection = connection;
	} 
	
	public void open() throws Exception {
		this.scoreByKey = connection.search(textPart);
		this.keysOrdered = connection.sortScores(scoreByKey); 
		System.out.println("TO scoreByKey size=" + (scoreByKey==null ? -1 : scoreByKey.size()));
		System.out.println("TO keysOrdered size=" + (keysOrdered==null ? -1 : keysOrdered.size()));
	}
	
	public HashMap<String, Double> getScoreByKey(){
		return scoreByKey;
	}

	public ArrayList<String> getKeysOrdered(){
		return keysOrdered;
	}
	
	public void close() {
		if(scoreByKey != null) {
			scoreByKey.clear();
		}
		if(keysOrdered !=null) {
			keysOrdered.clear();
		}
	}
	
	public String getTextPart() {
		return textPart;
	}
	
	@Override
	public void accept(OperatorVisitor visitor) throws Exception{
		visitor.visit(this);
	}

}
