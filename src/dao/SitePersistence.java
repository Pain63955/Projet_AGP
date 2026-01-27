package dao;

import business.excursion.Adresse;
import business.excursion.SiteTouristique;

public interface SitePersistence {

	void dataInit();
	
	SiteTouristique fetchKeywords(String keywords);
	
	SiteTouristique fetchNear(Adresse adresse);

	SiteTouristique fetchGamme(String range); 

	SiteTouristique fetchPrice(double lowPrice, double highPrice);
	
}
