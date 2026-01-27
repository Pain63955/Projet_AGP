package persistence.jdbc;

import business.excursion.Adresse;
import business.excursion.SiteTouristique;
import dao.SitePersistence;

public class JdbcSitePersistence implements SitePersistence {

	@Override
	public void dataInit() {
		System.err.println("Please don't forget to create tables manually by importing creation.sql in your database !");
		
	}

	@Override
	public SiteTouristique fetchKeywords(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SiteTouristique fetchNear(Adresse adresse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SiteTouristique fetchGamme(String range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SiteTouristique fetchPrice(double lowPrice, double highPrice) {
		// TODO Auto-generated method stub
		return null;
	}

}
