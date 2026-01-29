package api.core;

import java.sql.Connection;

public class BDeStatement {
	private final BDeConnection connection;
    private final BDeConfig config;

    public BDeStatement(BDeConnection connection, BDeConfig config) {
        this.connection = connection;
        this.config = config;
    }
    
    
}
