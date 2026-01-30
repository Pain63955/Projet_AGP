package persistence.jdbc;

import business.excursion.ActivitySite;
import business.excursion.Address;
import business.excursion.HistoricSite;
import business.excursion.TouristSite;
import dao.SitePersistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.engine.BDeConfig;
import api.engine.BDeConnection;
import api.engine.BDeResultSet;
import api.engine.BDeStatement;

public class JdbcSitePersistence implements SitePersistence {
	
//	private Connection dbConnection;
	Connection dbConnection;
	//private BDeConfig cfg = new BDeConfig("SiteTouristique","siteID", "data/descriptions");
	//private BDeConnection conn = BDeConnection.open(cfg, dbConnection);
	
	public JdbcSitePersistence() {
		this.dbConnection = JdbcConnection.getConnection();
		if (this.dbConnection == null) {
			System.err.println("ERREUR CRITIQUE: La connexion à la base de données est null!");
		}
	}

	@Override
	public void dataInit() {
		System.err.println("Please don't forget to create tables manually by importing creation.sql in your database !");
	}
	
	@Override
	public Map<TouristSite, Double> fetchKeywords(String keywords) {

	    Map<TouristSite, Double> resultMap = new HashMap<>();

	    if (dbConnection == null) {
	        System.err.println("ERREUR: connexion BD null");
	        return resultMap; // map vide
	    }

	    PreparedStatement preparedStatement = null;
	    ResultSet result = null;

	    try {
	        String query =
	            "SELECT st.*, ad.*, sa.duration AS info_specifique " +
	            "FROM SiteTouristique st " +
	            "JOIN SitesActiv sa ON sa.siteID = st.siteID " +
	            "JOIN Adresse ad ON st.adresseID = ad.adresseID " +
	            "UNION ALL " +
	            "SELECT st.*, ad.*, sh.guideName AS info_specifique " +
	            "FROM SiteTouristique st " +
	            "JOIN SitesHisto sh ON sh.siteID = st.siteID " +
	            "JOIN Adresse ad ON st.adresseID = ad.adresseID";

	        preparedStatement = dbConnection.prepareStatement(query);
	        result = preparedStatement.executeQuery();

	        while (result.next()) {

	            TouristSite site = null;

	            if ("SiteActivite".equals(result.getString("site_type"))) {
	                ActivitySite s = new ActivitySite();
	                s.setName(result.getString("nom"));
	                s.setPrice(result.getDouble("prix"));
	                s.setTransport(result.getString("transport"));
	                s.setDurationRatio(result.getFloat("info_specifique"));
	                site = s;
	            }

	            if ("SiteHistorique".equals(result.getString("site_type"))) {
	                HistoricSite s = new HistoricSite();
	                s.setName(result.getString("nom"));
	                s.setPrice(result.getDouble("prix"));
	                s.setTransport(result.getString("transport"));
	                s.setGuideName(result.getString("info_specifique"));
	                site = s;
	            }

	            if (site != null) {
	                Address ad = new Address();
	                ad.setLatitude(result.getDouble("latitude"));
	                ad.setLongitude(result.getDouble("longitude"));
	                ad.setPostCode(result.getString("code_postal"));
	                ad.setStreet(result.getString("rue"));
	                ad.setTown(result.getString("ville"));
	                site.setAddress(ad);

	                // ⭐ même valeur Double pour tous les sites
	                resultMap.put(site, 2.37);
	            }
	        }

	    } catch (SQLException e) {
	        System.err.println("Erreur SQL fetchKeywords: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        try {
	            if (result != null) result.close();
	            if (preparedStatement != null) preparedStatement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return resultMap;
	}

	@Override
	public List<TouristSite> fetchByInput() {
		// TODO Auto-generated method stub
		return null;
	}

}