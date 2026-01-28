package business.path;

public class Feet implements TransportStrategy{
	
	public Feet() {
		
	}
	
	@Override
	public double calculePrice(double distance) { 
		return 0.0; // Gratuit
		} 
	
	@Override
    public String toString() { 
		return "MARCHE"; 
		}
}
