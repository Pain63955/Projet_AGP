package business.excursion;

public abstract class SiteTouristique implements ElementTarifiable{
	
	private String nom;
    private String description;
    private Adresse adresse;
    private double prix;
    private int id;
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SiteTouristique() {
    	
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	
	
	public void setPrix(double prix) {
		this.prix = prix;
	}

	@Override
	public double getPrix() {
		return this.prix;
	}

}
