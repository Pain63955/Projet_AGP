package business.trajet;

import java.util.HashMap;
import java.util.Map;

public class TransportFactory {
	
	private Map<String, TransportStrategy> strategies;

    // Spring va injecter la Map définie dans le XML
    public void setStrategies(Map<String, TransportStrategy> strategies) {
        this.strategies = strategies;
    }

    public Trajet creerTrajet(String mode, double distance) {
    	TransportStrategy calc = strategies.get(mode.toUpperCase());
        
        if (calc == null) {
            return new Trajet("PIED", 0.0, distance);
        }
        
        double prixTotal = calc.calculerPrix(distance);
        return new Trajet(calc.getMode(), prixTotal, distance);
    }
}
