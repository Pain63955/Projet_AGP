package business.path;

public class Bus implements TransportStrategy {
	
	private double fixedPrice;
	
	public Bus() {
		
	}
	
	public Bus(double price) {
		this.fixedPrice = price;
	}

	public void setFixedPrice(double fixedPrice) {
        this.fixedPrice = fixedPrice;
    }
	
	@Override
    public double calculePrice(double distance) {
        return fixedPrice; 
    }

    @Override
    public String toString() {
        return "BUS";
    }

}
