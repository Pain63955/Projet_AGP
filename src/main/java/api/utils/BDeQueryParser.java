package api.utils;

import api.core.ParsedQuery;

public class BDeQueryParser {
		
	public static String sqlPart(String query) {
		String parts[] = query.split("WITH");

	    if (parts[0]==null) {
	        throw new NullPointerException("La requête ne contient qu'une partie textuelle ????");
	    }

	    return parts[0];
	}
	
	public static String textPart(String query) {
		String parts[] = query.split("WITH");
		
		if(parts.length>=2) {
			return parts[1];
		}
		else {
			return null;
		}
	}
	
	public static ParsedQuery parse(String query) {
	    String sql = sqlPart(query);
	    String text = textPart(query);
	    return new ParsedQuery(sql, text);
	}
	
	
}
