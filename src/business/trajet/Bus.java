package business.trajet;

public class Bus implements TransportStrategy {
	
	private double prixFixe;
	
	public Bus() {
		
	}

	public void setPrixFixe(double prixFixe) {
        this.prixFixe = prixFixe;
    }
	
	@Override
    public double calculerPrix(double distance) {
        return prixFixe; 
    }

    @Override
    public String getMode() {
        return "AUTOBUS";
    }

}
