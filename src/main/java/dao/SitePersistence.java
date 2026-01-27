package dao;

import java.util.List;

import business.excursion.Adresse;
import business.excursion.SiteTouristique;

public interface SitePersistence {

	void dataInit();
	
	SiteTouristique fetchKeywords(String keywords);
	
	List<SiteTouristique> fetchNear(int adresseID, double km);

	SiteTouristique fetchGamme(String range); 

	SiteTouristique fetchPrice(double lowPrice, double highPrice);
	
}
