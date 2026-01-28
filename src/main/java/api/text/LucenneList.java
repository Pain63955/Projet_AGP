package api.text;

public class LucenneList {
    private int key;
    private double score;

    public LucenneList(int key, double score) {
        this.key = key;
        this.score = score;
    }

    public int getKey(){ 
    	return key; 
    }
    public double getScore(){ 
    	return score; 
    }
}
