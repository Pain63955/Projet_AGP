package business.offer;

import java.util.List;

import business.excursion.Hotel;
import business.excursion.TouristSite;
import dao.HotelPersistence;
import dao.SitePersistence;

public class SearchCriteria {
    private int nbDays;           // Utilise la boucle i < nbJours dans le Builder
    private long budgetMin;
    private long budgetMax;      // La limite pour la méthode estDansLeBudget() de OffreSejour
    private String keywords;       // La chaîne de texte analysée par l'IA (ex: "zen temple surf")
    private int grade;
    private int confort;
    private List<TouristSite> listSites;
    private List<Hotel> listHotels;
    
    
    public SearchCriteria() {}

    
    //constructeur a supprimé éventuellement)
    public SearchCriteria(int nbJours, long budgetMin, long budgetMax, String motsCles, String transportSouhaite) {
        this.nbDays = nbJours;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.keywords = motsCles;
    } 
    
    public void prepareComplexSearch(int days, long bi, long ba, int grade, int confort) {
    	this.nbDays= days;
    	this.budgetMin = bi;
    	this.budgetMax = ba;
    	this.grade = grade;
    	this.confort = confort;
    }

	public boolean complexSearch(SitePersistence sitepersistence, HotelPersistence hotelpersistence) {
		//Va demander les ressources a DAO, renvoie true si l'execution c'est passé sans souçis.
		this.listSites = sitepersistence.fetchKeywords(keywords);
		this.listHotels = hotelpersistence.fetchGrade(this.grade);
    	return true;
    }
    
    public List<TouristSite> simpleSearch(SitePersistence sitepersistence) {
    	//go ask dao with this.keywords
    	
    	
    	return(sitepersistence.fetchKeywords(keywords));
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


	public List<TouristSite> getListSites() {
		return listSites;
	}


	public void setListSites(List<TouristSite> listSites) {
		this.listSites = listSites;
	}


	public List<Hotel> getListHotels() {
		return listHotels;
	}


	public void setListHotels(List<Hotel> listHotels) {
		this.listHotels = listHotels;
	}
	
}