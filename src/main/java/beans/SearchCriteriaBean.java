package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import business.offer.SearchCriteria;

@ManagedBean
@SessionScoped
public class SearchCriteriaBean {
	
	private int nbJours;
	private double budgetMax;
	
}