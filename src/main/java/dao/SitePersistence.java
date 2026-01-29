package dao;

import java.util.List;
import business.excursion.TouristSite;

public interface SitePersistence {

	void dataInit();
	
	List<TouristSite> fetchKeywords(String keywords);

	List<TouristSite> fetchByInput();
}
