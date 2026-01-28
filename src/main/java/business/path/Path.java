package business.path;

import business.excursion.PriceableElement;

public class Path implements PriceableElement{

	private TransportStrategy mode;
	private double distance;

    public Path(TransportStrategy mode2, double distance) {
        this.mode = mode2;
        this.distance = distance;
    }

    
    
    public String getMode() {
		return mode.toString();
	}



	public double getDistance() {
		return distance;
	}



	@Override
    public double getPrice() { 
    	double price = 0;
    	
    	price = mode.calculePrice(distance);
    	
    	return price;
    	}
	
}
