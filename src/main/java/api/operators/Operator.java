package api.operators;

<<<<<<< HEAD
public abstract class Operator implements Tree {
=======
import api.core.BDeRow;
import api.visitor.OperatorVisitor;
>>>>>>> branch 'master' of https://github.com/Pain63955/Projet_AGP.git

<<<<<<< HEAD
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
=======
public interface Operator {
	void init();
	boolean next();
	BDeRow current();
	void accept(OperatorVisitor visitor); 
>>>>>>> branch 'master' of https://github.com/Pain63955/Projet_AGP.git
}
