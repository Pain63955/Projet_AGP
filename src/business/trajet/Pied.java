package business.trajet;

public class Pied implements TransportStrategy{
	
	@Override
	public double calculerPrix(double distance) { 
		return 0.0; // Gratuit
		} 
	
	@Override
    public String getMode() { 
		return "MARCHE"; 
		}
}
