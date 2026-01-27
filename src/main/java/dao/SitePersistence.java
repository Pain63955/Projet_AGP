package dao;

import business.excursion.Address;
import business.excursion.TouristSite;

public interface SitePersistence {

	void dataInit();
	
	TouristSite fetchKeywords(String keywords);
	
	TouristSite fetchNear(Address adresse);

	TouristSite fetchGrade(String range); 

	TouristSite fetchPrice(double lowPrice, double highPrice);
	
}
