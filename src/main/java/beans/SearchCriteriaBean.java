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
import business.offer.SpeedPaceStrategy;
import business.offer.ComfortStrategy;
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
    private String paceStrategy = "calm"; // "calm" ou "speed"
    
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
        
        // Choisir la stratégie en fonction du choix de l'utilisateur
        ComfortStrategy strategy;
        if ("speed".equals(this.paceStrategy)) {
            strategy = new SpeedPaceStrategy();
        } else {
            strategy = new CalmPaceStrategy();
        }
        
        // Générer plusieurs offres et garder les meilleures
        int maxAttempts = 20; // Maximum de tentatives pour éviter les boucles infinies
        int attempts = 0;
        
        while (validOffers.size() < 3 && attempts < maxAttempts) {
            attempts++;
            OfferBuilder builder = new OfferBuilder(context, this.srct);
            StayOffer candidate = builder.setStrategy(strategy).generateOptimizedStay(factory).build();

            // Vérifier que l'offre respecte les critères de confort
            if (candidate.getScoreComfort() >= this.srct.getConfort() * 15) { // Échelle 1-5 devient 15-75
                validOffers.add(candidate);
            }
        }

        // Si moins de 3 offres trouvées, ajouter les meilleures tentatives restantes
        if (validOffers.size() < 3 && attempts >= maxAttempts) {
            // Générer quelques offres supplémentaires sans critère strict
            for (int i = validOffers.size(); i < 3; i++) {
                OfferBuilder builder = new OfferBuilder(context, this.srct);
                StayOffer candidate = builder.setStrategy(strategy).generateOptimizedStay(factory).build();
                validOffers.add(candidate);
            }
        }

        // Trier par score de confort décroissant
        validOffers.sort((o1, o2) -> Double.compare(o2.getScoreComfort(), o1.getScoreComfort()));
        
        // Retourner seulement les 3 meilleures
        return validOffers.size() > 3 ? validOffers.subList(0, 3) : validOffers;
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

	public void validateKeywords(String kwds) throws ValidatorException{
		if (kwds.isEmpty()) {
			FacesMessage message = new FacesMessage("La liste ne peut pas être vide.");
			throw new ValidatorException(message);
		}
	}
	
	public void validatePrice(long prixMin, long prixMax) throws ValidatorException {
		if (prixMin < 0 || prixMax > 50000 || prixMin > prixMax) {
			FacesMessage message = new FacesMessage("Le prix ne peut pas être négatif ni supérieur à 50000€");
			throw new ValidatorException(message);
		}
	}
	
	public void validateDays(int days) throws ValidatorException {
		if (days < 1 || days > 100) {
			FacesMessage message = new FacesMessage("La durée doit être entre 1 et 100 jours");
			throw new ValidatorException(message);
		}
	}

	// Getters et Setters
	
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

	public String getPaceStrategy() {
		return paceStrategy;
	}

	public void setPaceStrategy(String paceStrategy) {
		this.paceStrategy = paceStrategy;
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
	
	public String getPaceStrategyLabel() {
		return "speed".equals(paceStrategy) ? "Rythme Soutenu" : "Rythme Calme";
	}
}