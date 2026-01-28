package api.core;

public class BDeConfig {
	private String tableName;
	private String keyColumn;
	private String directoryPath;
		
	public BDeConfig(String tableName, String keyColumn, String directoryPath) {
		if (tableName == null || keyColumn == null || directoryPath == null) {
            throw new IllegalArgumentException("BDeConfig arguments cannot be null");
        }
		
		this.tableName = tableName;
		this.keyColumn = keyColumn;
		this.directoryPath = directoryPath;
	}
	
	public BDeConfig() {
		
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getKeyColumn() {
		return keyColumn;
	}
	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}
	public String getDirectoryPath() {
		return directoryPath;
	}
	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

    public String getIndexPath() {
        if (directoryPath == null) return null;
        String base = directoryPath.endsWith("/") || directoryPath.endsWith("\\")
                ? directoryPath.substring(0, directoryPath.length() - 1)
                : directoryPath;
        return base + "/index";
    }
	
}
