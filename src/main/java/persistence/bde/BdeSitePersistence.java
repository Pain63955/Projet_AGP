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
			throw new RuntimeException("Impossible d'initialiser BDeConnection");
		}
	}

	@Override
	public void dataInit() {
	}
	
	@Override
	public List<TouristSite> fetchKeywords(String keywords) {
		List<TouristSite> sites = new ArrayList<>();
		
		if (conn == null) {
			return sites;
		}
		
		BDeStatement st = null;
		BDeResultSet result = null;
		
		try {
			String selectAddressQuery;
			
			if (keywords == null || keywords.trim().isEmpty()) {
				selectAddressQuery = "SELECT st.*, ad.*, sa.duration AS info_specifique "
						+ "FROM SiteTouristique st "
						+ "JOIN SitesActiv sa ON sa.siteID = st.siteID "
						+ "JOIN Adresse ad ON st.adresseID = ad.adresseID "
						+ "UNION ALL "
						+ "SELECT st.*, ad.*, sh.guideName AS info_specifique "
						+ "FROM SiteTouristique st "
						+ "JOIN SitesHisto sh ON sh.siteID = st.siteID "
						+ "JOIN Adresse ad ON st.adresseID = ad.adresseID";
			} else {
				selectAddressQuery = /*"SELECT st.*, ad.*, sa.duration AS info_specifique "
						+ "FROM SiteTouristique st "
						+ "JOIN SitesActiv sa ON sa.siteID = st.siteID "
						+ "JOIN Adresse ad ON st.adresseID = ad.adresseID "
						+ "UNION ALL "
						+ "SELECT st.*, ad.*, sh.guideName AS info_specifique "
						+ "FROM SiteTouristique st "
						+ "JOIN SitesHisto sh ON sh.siteID = st.siteID "
						+ "JOIN Adresse ad ON st.adresseID = ad.adresseID "
						+ "WITH " + keywords;*/
						"SELECT siteID, site_type, nom FROM SiteTouristique WITH Ubud";
			}

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
			se.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sites;
	}

	@Override
	public List<TouristSite> fetchByInput() {
		return null;
	}
}