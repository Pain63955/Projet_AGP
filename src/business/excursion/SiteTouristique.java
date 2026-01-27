package business.excursion;


public abstract class SiteTouristique implements ElementTarifiable{
	
	private String nom;
    private String description;
    private Adresse adresse;
    private double prix;
    private int id;
    


	public SiteTouristique(String nom, String description, Adresse adresse, double prix, int id) {
		super();
		this.nom = nom;
		this.description = description;
		this.adresse = adresse;
		this.prix = prix;
		this.id = id;
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setPrix(double prix) {
		this.prix = prix;
	}


	@Override
	public double getPrix() {
		return this.prix;
	}

}
