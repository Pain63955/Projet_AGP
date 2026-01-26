package business.excursion;

public class Adresse {
	
	private  String rue;
    private  String ville;
    private  String codePostal;
    private double lat;
    private double lon;
    
	public Adresse(String rue, String ville, String codePostal, double lat, double lon) {
		this.rue = rue;
		this.ville = ville;
		this.codePostal = codePostal;
		this.lat = lat;
		this.lon = lon;
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

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
    
}
