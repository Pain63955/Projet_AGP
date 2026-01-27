package business.path;

import java.util.HashMap;
import java.util.Map;

public class TransportFactory {
	
	private Map<String, TransportStrategy> strategies;

    public void setStrategies(Map<String, TransportStrategy> strategies) {
        this.strategies = strategies;
    }

    public Path createPath(String mode, double distance) {
    	TransportStrategy calc = strategies.get(mode.toUpperCase());
        
        if (calc == null) {
        	
            return new Path(calc, distance);
        }
        
        return new Path(calc, distance);
    }
}
