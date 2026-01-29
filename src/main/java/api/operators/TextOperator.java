package api.operators;

import java.util.ArrayList;
import java.util.HashMap;

import api.core.BDeConfig;
import api.text.LuceneIndexService;
import api.visitor.OperatorVisitor;

public class TextOperator extends Operator {
	
	private BDeConfig bdeConfig;
	private String textPart;
	
	private HashMap<String, Double> scoreByKey;
	private ArrayList<String> keysOrdered;
	
	public LuceneIndexService lucenneIndex;
	
	public TextOperator(String textPart, BDeConfig bdeConfig) {
		super();
		this.textPart = textPart;
		this.bdeConfig = bdeConfig;
	} 
	
	public void open() throws Exception {
		lucenneIndex=new LuceneIndexService(bdeConfig);
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
		scoreByKey.clear();
		keysOrdered.clear();
	}
	
	public String getTextPart() {
		return textPart;
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
