package business.trajet;

import business.excursion.ElementTarifiable;

public class Trajet implements ElementTarifiable{

	private String mode;
    private double prix;
    private double distance;

    public Trajet(String mode2, double prix, double distance) {
        this.mode = mode2;
        this.prix = prix;
        this.distance = distance;
    }

    @Override
    public double getPrix() { 
    	return this.prix;
    	}
	
}
