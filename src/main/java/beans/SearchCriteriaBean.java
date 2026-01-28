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
	 * 
	 */
	private static final long serialVersionUID = -2204740088413345478L;
	private int nbJours;
	private double budgetMax;
	
	private SearchCriteria srct;

	public String search() {
		return null;
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

	public int getNbJours() {
		return nbJours;
	}

	public void setNbJours(int nbJours) {
		this.nbJours = nbJours;
	}

	public double getBudgetMin() {
		return this.srct.getBudgetMax();
		
	}
	
	public void setBudgetMin() {
		
	}
	
	public double getBudgetMax() {
		return budgetMax;
	}

	public void setBudgetMax(double budgetMax) {
		this.budgetMax = budgetMax;
	}
}