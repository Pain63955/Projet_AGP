package dao;

import java.util.List;
import java.util.Map;

import business.excursion.TouristSite;

public interface SitePersistence {

	void dataInit();
	
	Map<TouristSite, Double> fetchKeywords(String keywords);

	List<TouristSite> fetchByInput();
}
