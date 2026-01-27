package business.trajet;

public class Pied implements TransportStrategy{
	
	public Pied() {
		
	}
	
	@Override
	public double calculerPrix(double distance) { 
		return 0.0; // Gratuit
		} 
	
	@Override
    public String toString() { 
		return "MARCHE"; 
		}
}
