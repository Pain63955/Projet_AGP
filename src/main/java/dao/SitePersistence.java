package dao;

<<<<<<< HEAD
import java.util.List;

import business.excursion.Adresse;
import business.excursion.SiteTouristique;
=======
import business.excursion.Address;
import business.excursion.TouristSite;
>>>>>>> branch 'master' of https://github.com/Pain63955/Projet_AGP.git

public interface SitePersistence {

	void dataInit();
	
	TouristSite fetchKeywords(String keywords);
	
<<<<<<< HEAD
	List<SiteTouristique> fetchNear(int adresseID, double km);
=======
	TouristSite fetchNear(Address adresse);
>>>>>>> branch 'master' of https://github.com/Pain63955/Projet_AGP.git

	TouristSite fetchGrade(String range); 

	TouristSite fetchPrice(double lowPrice, double highPrice);
	
}
