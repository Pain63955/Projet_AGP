package api.operators;

import api.engine.BDeCurrentRow;

public abstract class Operator implements Tree {
	
    private Tree left;
    private Tree right;
    protected BDeCurrentRow currentRow;
    
    public Operator() {
        this(null, null);
    }

    public Operator(Tree left, Tree right) {
        this.left = left;
        this.right = right;
    }

    public void open() throws Exception{};
    
    public boolean next() throws Exception{
    	return false;
    };
    
    public BDeCurrentRow current() {
    	return currentRow;
    };
    
    public void close() throws Exception{};
    @Override
    public Tree getLeft() {
        return left;
    }

    @Override
    public Tree getRight() {
        return right;
    }
}
