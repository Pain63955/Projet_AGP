package dao;

import java.util.List;
import business.excursion.TouristSite;
import business.excursion.Address;

public interface SitePersistence {

	void dataInit();
	
	List<TouristSite> fetchKeywords(String keywords);
	
	List<TouristSite> fetchNear(int adresseID, double km);
	
	TouristSite fetchNear(Address adresse);

	TouristSite fetchGrade(String range); 

	TouristSite fetchPrice(double lowPrice, double highPrice);
	
}
