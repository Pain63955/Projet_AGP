package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import business.offer.SearchCriteria;

@ManagedBean
@SessionScoped
public class SearchCriteriaBean {
	
	private int nbJours;
	private double budgetMax;
	
	public SearchCriteriaBean() {
		
	}

	public int getNbJours() {
		return nbJours;
	}

	public void setNbJours(int nbJours) {
		this.nbJours = nbJours;
	}

	public double getBudgetMax() {
		return budgetMax;
	}

	public void setBudgetMax(double budgetMax) {
		this.budgetMax = budgetMax;
	}
}