package business.offer;

import java.util.List;

import business.excursion.TouristSite;
import dao.SitePersistence;

public class SearchCriteria {
    private String destination;     // Pour filtrer la zone géographique
    private int nbDays;           // Utilise la boucle i < nbJours dans le Builder
    private long budgetMin;
    private long budgetMax;      // La limite pour la méthode estDansLeBudget() de OffreSejour
    private String keywords;       // La chaîne de texte analysée par l'IA (ex: "zen temple surf")
    private int grade;
    private int confort;
    private String askedTransport; // Le mode envoyé à la factory (BATEAU, BUS, etc.)

    public SearchCriteria() {}

    
    //constructeur a supprimé éventuellement)
    public SearchCriteria(String destination, int nbJours, long budgetMin, long budgetMax, String motsCles, String transportSouhaite) {
        this.destination = destination;
        this.nbDays = nbJours;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.keywords = motsCles;
        this.askedTransport = transportSouhaite;
    }
    
    public void prepareComplexSearch(int days, long bi, long ba, int grade, int confort, String trans) {
    	this.nbDays= days;
    	this.budgetMin = bi;
    	this.budgetMax = ba;
    	this.grade = grade;
    	this.confort = confort;
    	this.askedTransport = trans;
    }

	public List<StayOffer> complexSearch() {
    	//go ask offre builder ou excursion builder jsp with this.*
    	//return PAS(enfin ptet que la liste va etre return jsp faut capter hein) la list pcq elle va aller dans son ptit proxy bean qui va passé sa à l'xhtml
    	return null;
    }
    
    public List<TouristSite> simpleSearch(SitePersistence sitepersistence) {
    	//go ask dao with this.keywords
    	return(sitepersistence.fetchKeywords(keywords));
    }
    
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getNbDays() {
		return nbDays;
	}

	public void setNbDays(int nbDays) {
		this.nbDays = nbDays;
	}

	public long getBudgetMin() {
		return budgetMin;
	}

	public void setBudgetMin(long budgetMin) {
		this.budgetMin = budgetMin;
	}

	public long getBudgetMax() {
		return budgetMax;
	}

	public void setBudgetMax(long budgetMax) {
		this.budgetMax = budgetMax;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAskedTransport() {
		return askedTransport;
	}

	public void setAskedTransport(String askedTransport) {
		this.askedTransport = askedTransport;
	}

    public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}


	public int getConfort() {
		return confort;
	}


	public void setConfort(int confort) {
		this.confort = confort;
	}
}