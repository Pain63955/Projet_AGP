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
    private double budgetMin;
    private double budgetMax;      
    private String keywords;       
    private String askedTransport;
	

	public String search() {
		return null;
		//euh bah la va falloir vérifier que tt est bon avant de faire l'appel chez search criteria sinon on est un mauvais proxy
		
	}
	
	public void validatePrice(FacesContext context, UIComponent componentToValidate, Object value) throws ValidatorException {
		int prix = (int)value;
		
		if (prix < 0 || prix > 50000) {
			FacesMessage message = new FacesMessage("The prix cannot be negative nor over 50000€");
			throw new ValidatorException(message);
		}
	}
	
	public void validateJours(FacesContext context, UIComponent componentToValidate, Object value) throws ValidatorException {
		int days = (int)value;
		
		if (days < 0 || days > 100) {
			FacesMessage message = new FacesMessage("The amount of days cannot be less than 1 and above 100");
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
		this.srct.setNbDays(nbDays);
	}

	public double getBudgetMax() {
		return budgetMax;
	}

	public void setBudgetMax(double budgetMax) {
		this.srct.setBudgetMax(budgetMax);
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.srct.setKeywords(keywords);
	}

	public String getAskedTransport() {
		return askedTransport;
	}

	public void setAskedTransport(String askedTransport) {
		this.srct.setAskedTransport(askedTransport);
	}

	public double getBudgetMin() {
		return this.budgetMin;
		
	}
	
	public void setBudgetMin() {
		this.srct.setBudgetMin(budgetMin);
	}
}