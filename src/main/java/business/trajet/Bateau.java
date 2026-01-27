package business.trajet;

public class Bateau implements TransportStrategy{
	private double prixKm; // Exemple : 2.5€ par km
	
	

	public Bateau() {
		super();
	}
	public Bateau(double prix) {
		super();
		this.prixKm=prix;
	}



	@Override
    public double calculerPrix(double distance) {
        return distance * prixKm;
    }

    
    
	public double getPrixKm() {
		return prixKm;
	}



	public void setPrixKm(double prixKm) {
		this.prixKm = prixKm;
	}



	@Override
    public String toString() {
        return "BATEAU";
    }
}
