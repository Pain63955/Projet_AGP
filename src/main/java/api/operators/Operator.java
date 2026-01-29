package api.operators;

public abstract class Operator implements Tree {

    private final Tree left;
    private final Tree right;

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
