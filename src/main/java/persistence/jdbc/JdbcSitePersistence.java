package persistence.jdbc;

import business.excursion.TouristSite;
import dao.SitePersistence;

import java.util.List;

public class JdbcSitePersistence implements SitePersistence {

	@Override
	public void dataInit() {
		System.err.println("Please don't forget to create tables manually by importing creation.sql in your database !");
		
	}

	@Override
	public List<TouristSite> fetchKeywords(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TouristSite fetchGrade(String range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TouristSite fetchPrice(double lowPrice, double highPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TouristSite> fetchNear(int adresseID, double km) {
		// TODO Auto-generated method stub
		return null;
	}



}
// test