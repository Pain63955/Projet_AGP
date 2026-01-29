package dao;

import java.util.List;
import business.excursion.TouristSite;

public interface SitePersistence {

	void dataInit();
	
	List<TouristSite> fetchKeywords(String keywords);
	
	List<TouristSite> fetchNear(int adresseID, double km);

	List<TouristSite> fetchByInput(int days, long bi, long ba, int grade, int confort);
	
	TouristSite fetchGrade(String range); 

	TouristSite fetchPrice(long lowPrice, long highPrice);
	
}
