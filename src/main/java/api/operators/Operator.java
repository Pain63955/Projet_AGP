package api.operators;

public abstract class Operator implements Tree {
	
    private Tree left;
    private Tree right;

    public Operator() {
        this(null, null);
    }

    public Operator(Tree left, Tree right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Tree getLeft() {
        return left;
    }

    @Override
    public Tree getRight() {
        return right;
    }
}
