package api.operators;

import java.util.ArrayList;
import java.util.HashMap;

import api.text.LuceneIndexService;
import api.visitor.OperatorVisitor;

public class TextOperator extends Operator {
	
	private String textPart;
	private LuceneIndexService lucenneIndex;
	
	private HashMap<String, Double> scoreByKey;
	private ArrayList<String> keysOrdered;
	
	public TextOperator(String textPart, LuceneIndexService lucenneIndex) {
		super(null,null);
		this.textPart = textPart;
		this.lucenneIndex = lucenneIndex;
	} 
	
	public void open() throws Exception {
		this.scoreByKey = lucenneIndex.search(textPart);
		this.keysOrdered = lucenneIndex.sortScores(scoreByKey); 
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
