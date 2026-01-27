package business.trajet;

import business.excursion.ElementTarifiable;

public class Trajet implements ElementTarifiable{

	private TransportStrategy mode;
	private double distance;

    public Trajet(TransportStrategy mode2, double distance) {
        this.mode = mode2;
        this.distance = distance;
    }

    
    
    public TransportStrategy getMode() {
		return mode;
	}



	public double getDistance() {
		return distance;
	}



	@Override
    public double getPrix() { 
    	double prix = 0;
    	
    	prix = mode.calculerPrix(distance);
    	
    	return prix;
    	}
	
}
