package business.excursion;

public class Adresse {
	
	private int adresseId;
	private  String rue;
    private  String ville;
    private  String codePostal;
    private double latitude;
    private double longitude;
    
	public Adresse() {
		
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getAdressId() {
		return adresseId;
	}

	public void setAdresssId(int adresseId) {
		this.adresseId = adresseId;
	}
    
}
