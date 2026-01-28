package api.core;

public class BDeQueryParser {
	
	BDeQueryParser(){
		
	}
	
	public String sqlPart(String input) {
		String parts[] = input.split("WITH");
		return parts[0];
	}
	
	public String textPart(String input) {
		String parts[] = input.split("WITH");
		return parts[1];
	}
}
