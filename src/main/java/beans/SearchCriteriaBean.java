package beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import business.offer.SearchCriteria;

@ManagedBean
@SessionScoped
public class SearchCriteriaBean implements Serializable{
	
	/**
	 *	Proxy de search criteria, on check tout
	 */
	private static final long serialVersionUID = -2204740088413345478L;
	private SearchCriteria srct;
	
	private int nbDays;       
    private long budgetMin;
    private long budgetMax;
	private int grade;
    private int confort;
    private String keywords;       
    private String askedTransport;
	

	public String simpleSearch(FacesContext context, UIComponent componentToValidate, Object value) {
		String kwds = (String)value;
		validateKeywords(kwds);

		this.srct.setKeywords(kwds);
		//this.srct.simpleSearch(null);
		//TODO rajouter le sitepersistence implémenté
		
		//return le résultat de la recherche, le formater comme il faut ici avant de le renvoyer
		
		return "Une liste de site touristique pour l'exemple";		
	}
	
	public String complexSearch() {
		validatePrice(this.budgetMin, this.budgetMax);
		validateDays(this.nbDays);
		validateConfort(this.confort);
		validateGrade(this.grade);
		validateTransport(this.askedTransport);
		
		this.srct.prepareComplexSearch(this.budgetMin, this.budgetMax, this.nbDays, this.confort, this.grade, this.askedTransport);
		this.srct.complexSearch();
		//TODO Ajouter sitepersistence et tt
		//return le résu de la recherche complex formaté correctement pour le web et tt t'as vu
		
		return "Ceci est un résultat de recherche complexe.";
	}
	public void validateConfort(int conf) {
		if (conf < 1 || conf > 5) {
			FacesMessage message = new FacesMessage("Nombre de confort invalide.");
			throw new ValidatorException(message);
		}
	}
	
	public void validateGrade(int star) {
		if (star < 1 || star > 5) {
			FacesMessage message = new FacesMessage("Nombre d'étoiles invalide.");
			throw new ValidatorException(message);
		}
	}
	
	public void validateTransport(String transport) {
		if (transport.isEmpty()) {
			FacesMessage message = new FacesMessage("Il faut au minimum 1 type de transport.");
			throw new ValidatorException(message);
		}
	}
	public void validateKeywords(String kwds) throws ValidatorException{
		if (kwds.isEmpty()) {
			FacesMessage message = new FacesMessage("La liste ne peut pas être vide.");
			throw new ValidatorException(message);
		}
	}
	
	public void validatePrice(long prixMin, long prixMax) throws ValidatorException {
		
		if (prixMin < 0 || prixMax > 50000 || prixMin > prixMax) {
			FacesMessage message = new FacesMessage("The prix cannot be negative nor over 50000€");
			throw new ValidatorException(message);
		}
	}
	
	public void validateDays(int days) throws ValidatorException {
		
		if (days < 1 || days > 7) {
			FacesMessage message = new FacesMessage("The amount of days cannot be less than 1 and above 7");
			throw new ValidatorException(message);
		}
	}
	
	public SearchCriteriaBean() {
		
	}

	public SearchCriteria getSrct() {
		return srct;
	}

	public void setSrct(SearchCriteria srct) {
		this.srct = srct;
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
	public int getStarRating() {
		return grade;
	}

	public void setStarRating(int grade) {
		this.grade = grade;
	}

	public int getConfort() {
		return confort;
	}

	public void setConfort(int confort) {
		this.confort = confort;
	}
}