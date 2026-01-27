package business.trajet;

public class Bateau implements TransportStrategy{
	private double prixKm; // Exemple : 2.5€ par km
	
	

    public Bateau() {
		super();
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
    public String getMode() {
        return "BATEAU";
    }
}
