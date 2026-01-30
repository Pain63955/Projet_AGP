package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.validator.ValidatorException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import business.excursion.TouristSite;
import business.offer.CalmPaceStrategy;
import business.offer.OfferBuilder;
import business.offer.SearchCriteria;
import business.offer.StayOffer;
import business.path.TransportFactory;
import dao.HotelPersistence;
import dao.SitePersistence;

@ManagedBean(name = "searchCriteriaBean")
@SessionScoped
public class SearchCriteriaBean implements Serializable{
	
	private static final long serialVersionUID = -2204740088413345478L;
	private SearchCriteria srct;

	private int nbDays;       
    private long budgetMin;
    private long budgetMax;
	private int grade;
    private int confort;
    private String keywords;
    
    private ApplicationContext context;
    
    private Map<TouristSite, Double> simpleResultsWithScores;
    private List<StayOffer> complexResults;

	public SearchCriteriaBean() {
		this.context = new ClassPathXmlApplicationContext("/spring/spring.xml");
		this.confort = 1;
		this.grade = 1;
	}

	public String simpleSearch() {
		complexResults = null;
		
		this.srct = new SearchCriteria();
		this.srct.setKeywords(this.keywords);
		this.simpleResultsWithScores = this.srct.simpleSearch(
				(SitePersistence)context.getBean("sitePersistence"));
		
		return "resultsS";		
	}
	
	public String complexSearch() { 
		simpleResultsWithScores = null;
		validatePrice(this.budgetMin, this.budgetMax);
		validateDays(this.nbDays);
		validateConfort(this.confort);
		validateGrade(this.grade);
		  
		this.srct = new SearchCriteria();
		this.srct.setKeywords(keywords);
		this.srct.prepareComplexSearch(this.nbDays, this.budgetMin, this.budgetMax, this.grade, this.confort);
		this.srct.complexSearch(
				(SitePersistence) context.getBean("sitePersistence"),
				(HotelPersistence) context.getBean("hotelPersistence"));
		
		this.complexResults = generateStayList();
		
		return "resultsC";
	}
	
	public List<StayOffer> generateStayList() {
		TransportFactory factory = (TransportFactory) context.getBean("transportFactory");
        List<StayOffer> validOffers = new ArrayList<>();
        
        while (validOffers.size() < 3) {
            OfferBuilder builder = new OfferBuilder(context, this.srct);
            StayOffer candidate = builder.setStrategy(new CalmPaceStrategy()).generateOptimizedStay(factory).build();

            if (candidate.getScoreComfort() >= this.srct.getConfort()) {
                validOffers.add(candidate);
            }
        }

        validOffers.sort((o1, o2) -> Double.compare(o2.getScoreComfort(), o1.getScoreComfort()));
        
		return validOffers;
	}
	
	public void validateConfort(int conf) {
		if (conf < 1 || conf > 5) {
			FacesMessage message = new FacesMessage("Nombre de confort invalide.");
			throw new ValidatorException(message);
		}
	}
	
	public void validateGrade(int star) {
		if (star < 1 || star > 5) {
			FacesMessage message = new FacesMessage("Nombre d'Ã©toiles invalide.");
			throw new ValidatorException(message);
		}
	}

	public void validateKeywords(String kwds) throws ValidatorException{
		if (kwds.isEmpty()) {
			FacesMessage message = new FacesMessage("La liste ne peut pas Ãªtre vide.");
			throw new ValidatorException(message);
		}
	}
	
	public void validatePrice(long prixMin, long prixMax) throws ValidatorException {
		if (prixMin < 0 || prixMax > 50000 || prixMin > prixMax) {
			FacesMessage message = new FacesMessage("The prix cannot be negative nor over 50000â‚¬");
			throw new ValidatorException(message);
		}
	}
	
	public void validateDays(int days) throws ValidatorException {
		if (days < 1 || days > 100) {
			FacesMessage message = new FacesMessage("The amount of days cannot be less than 1 and above 7");
			throw new ValidatorException(message);
		}
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

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Map<TouristSite, Double> getSimpleResultsWithScores() {
		return simpleResultsWithScores;
	}

	public void setSimpleResultsWithScores(Map<TouristSite, Double> simpleResultsWithScores) {
		this.simpleResultsWithScores = simpleResultsWithScores;
	}

	public List<Map.Entry<TouristSite, Double>> getSimpleResultsList() {
		if (simpleResultsWithScores == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(simpleResultsWithScores.entrySet());
	}

	public List<StayOffer> getComplexResults() {
		return complexResults;
	}

	public void setComplexResults(List<StayOffer> complexResults) {
		this.complexResults = complexResults;
	}
}