package business.trajet;

public interface TransportStrategy {
	
	double calculerPrix(double distance);
    String toString();
    
}
