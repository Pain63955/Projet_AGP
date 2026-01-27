package business.excursion;

public class Adresse {
	
	private int adresseId;
	private double latitude;
	private double longitude;
	private  String rue;
    private  String ville;
    private  String codePostal;

	public Adresse(int adresseId, double latitude, double longitude, String rue, String ville, String codePostal) {
		super();
		this.adresseId = adresseId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.rue = rue;
		this.ville = ville;
		this.codePostal = codePostal;
	}

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

	public int getAdresseId() {
		return adresseId;
	}

	public void setAdresseId(int adresseId) {
		this.adresseId = adresseId;
	}
    
}
