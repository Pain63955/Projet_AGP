package persistence.bde;

import business.excursion.ActivitySite;
import business.excursion.Address;
import business.excursion.HistoricSite;
import business.excursion.TouristSite;
import dao.SitePersistence;

import java.util.ArrayList;
import java.util.List;

import api.core.BDeConfig;
import api.core.BDeConnection;
import api.core.BDeResultSet;
import api.core.BDeStatement;

public class BdeSitePersistence implements SitePersistence {
	
	private BDeConfig cfg;
	private BDeConnection conn;
	
	public BdeSitePersistence() {
		
		this.cfg = new BDeConfig("SiteTouristique", "siteID", "data/descriptions");
		
		this.conn = BDeConnection.open(cfg);
		
		if (this.conn == null) {
			System.err.println("ERREUR CRITIQUE: La connexion BDE est null!");
			throw new RuntimeException("Impossible d'initialiser BDeConnection");
		}
	}

	@Override
	public void dataInit() {
		System.err.println("Please don't forget to create tables manually by importing creation.sql in your database !");
	}
	
	@Override
	public List<TouristSite> fetchKeywords(String keywords) {
		List<TouristSite> sites = new ArrayList<>();
		
		// Vérification de la connexion avant toute opération
		if (conn == null) {
			System.err.println("ERREUR: Impossible d'exécuter la requête - connexion BDE est null");
			return sites;
		}
		
		BDeStatement st = null;
		BDeResultSet result = null;
		
		try {
			String selectAddressQuery = "SELECT st.*, ad.*, sa.duration AS info_specifique\r\n"
					+ "FROM SiteTouristique st\r\n"
					+ "JOIN SitesActiv sa ON sa.siteID = st.siteID\r\n"
					+ "JOIN Adresse ad ON st.adresseID = ad.adresseID\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT st.*, ad.*, sh.guideName AS info_specifique\r\n"
					+ "FROM SiteTouristique st\r\n"
					+ "JOIN SitesHisto sh ON sh.siteID = st.siteID\r\n"
					+ "JOIN Adresse ad ON st.adresseID = ad.adresseID\r\n"
					+ "WITH "+ keywords ;

			st = conn.prepareStatement(selectAddressQuery);			
			result = st.executeQuery();
			
			while(result.next()) {
				
				if (result.getString("site_type").equals("SiteActivite")) {
					ActivitySite site = new ActivitySite();
					site.setName(result.getString("nom"));
					site.setPrice(result.getDouble("prix"));
					site.setTransport(result.getString("transport"));
					site.setDurationRatio(result.getDouble("info_specifique").floatValue());
					
					Address ad = new Address();
					ad.setLatitude(result.getDouble("latitude"));
					ad.setLongitude(result.getDouble("longitude"));
					ad.setPostCode(result.getString("code_postal"));
					ad.setStreet(result.getString("rue"));
					ad.setTown(result.getString("ville"));
					
					site.setAddress(ad);
					sites.add(site);
				}
				
				if (result.getString("site_type").equals("SiteHistorique")) {
					HistoricSite site = new HistoricSite();
					site.setName(result.getString("nom"));
					site.setPrice(result.getDouble("prix"));
					site.setTransport(result.getString("transport"));
					site.setGuideName(result.getString("info_specifique"));
					
					Address ad = new Address();
					ad.setLatitude(result.getDouble("latitude"));
					ad.setLongitude(result.getDouble("longitude"));
					ad.setPostCode(result.getString("code_postal"));
					ad.setStreet(result.getString("rue"));
					ad.setTown(result.getString("ville"));
					
					site.setAddress(ad);
					sites.add(site);
				}
			}
		
		} catch (Exception se) {
			System.err.println("Erreur SQL dans fetchKeywords: " + se.getMessage());
			se.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
//				if (st != null) st.close();
			} catch (Exception e) {
				System.err.println("Erreur lors de la fermeture des ressources: " + e.getMessage());
			}
		}
		return sites;
	}

	@Override
	public List<TouristSite> fetchByInput() {
		// TODO Auto-generated method stub
		return null;
	}

}