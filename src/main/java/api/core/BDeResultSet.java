package api.core;

import api.operators.Operator;
import api.visitor.CloseVisitor;

public class BDeResultSet {
	private final Operator op;
	private BDeActualRow currentRow;
	
	public BDeResultSet(Operator op) {
		super();
		this.op = op;
	}
	
	public boolean next() throws Exception {
		boolean ok = op.next();
		if(!ok) {
			currentRow = null;
			return false;
		}
		currentRow = op.current();
		return true;
	}
	
	public BDeActualRow current() {
        return currentRow;
    }
	
	public void close()throws Exception {
		op.accept(new CloseVisitor());
		currentRow = null;
	}
	
	public Object getObject(String columnLabel) {
        return current().getObject(columnLabel);
    }

    public String getString(String columnLabel) {
        return current().getString(columnLabel);
    }

    public Integer getInt(String columnLabel) {
        return current().getInt(columnLabel);
    }

    public Long getLong(String columnLabel) {
        return current().getLong(columnLabel);
    }

    public Double getDouble(String columnLabel) {
        return current().getDouble(columnLabel);
    }

    public Boolean getBoolean(String columnLabel) {
        return current().getBoolean(columnLabel);
    }
    
    public Float getFloat(String columnLabel) {
    	return current().getFloat(columnLabel);
    }

    public double getScore() {
        return current().getScore();
    }
    
    

}
