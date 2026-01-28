package business.path;

public class Boat implements TransportStrategy{
	private double priceKm; // Exemple : 2.5€ par km
	
	

	public Boat() {
		super();
	}
	public Boat(double price) {
		super();
		this.priceKm=price;
	}



	@Override
    public double calculePrice(double distance) {
        return distance * priceKm;
    }

    
    
	public double getPriceKm() {
		return priceKm;
	}



	public void setPriceKm(double priceKm) {
		this.priceKm = priceKm;
	}



	@Override
    public String toString() {
        return "BOAT";
    }
}
