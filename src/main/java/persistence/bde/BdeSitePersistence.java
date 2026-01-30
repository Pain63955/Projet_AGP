package persistence.bde;

import business.excursion.ActivitySite;
import business.excursion.Address;
import business.excursion.HistoricSite;
import business.excursion.TouristSite;
import dao.SitePersistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	public Map<TouristSite,Double> fetchKeywords(String keywords) {
		Map<TouristSite, Double> sitesWithScores = new HashMap<>();
		
		if (conn == null) {
			return sitesWithScores;
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
				selectAddressQuery = "SELECT st.*, ad.*, sa.duration AS info_specifique "
						+ "FROM SiteTouristique st "
						+ "JOIN SitesActiv sa ON sa.siteID = st.siteID "
						+ "JOIN Adresse ad ON st.adresseID = ad.adresseID "
						+ "UNION ALL "
						+ "SELECT st.*, ad.*, sh.guideName AS info_specifique "
						+ "FROM SiteTouristique st "
						+ "JOIN SitesHisto sh ON sh.siteID = st.siteID "
						+ "JOIN Adresse ad ON st.adresseID = ad.adresseID "
						+ "WITH " + keywords;
			}

			st = conn.prepareStatement(selectAddressQuery);			
			result = st.executeQuery();

			while(result.next()) {
				TouristSite site = null;
				
				if (result.getString("site_type").equals("SiteActivite")) {
					ActivitySite activitySite = new ActivitySite();
					activitySite.setName(result.getString("nom"));
					activitySite.setPrice(result.getDouble("prix"));
					activitySite.setTransport(result.getString("transport"));
					activitySite.setDurationRatio(result.getDouble("info_specifique").floatValue());
					
					Address ad = new Address();
					ad.setLatitude(result.getDouble("latitude"));
					ad.setLongitude(result.getDouble("longitude"));
					ad.setPostCode(result.getString("code_postal"));
					ad.setStreet(result.getString("rue"));
					ad.setTown(result.getString("ville"));
					
					activitySite.setAddress(ad);
					site = activitySite;
				}
				
				if (result.getString("site_type").equals("SiteHistorique")) {
					HistoricSite historicSite = new HistoricSite();
					historicSite.setName(result.getString("nom"));
					historicSite.setPrice(result.getDouble("prix"));
					historicSite.setTransport(result.getString("transport"));
					historicSite.setGuideName(result.getString("info_specifique"));
					
					Address ad = new Address();
					ad.setLatitude(result.getDouble("latitude"));
					ad.setLongitude(result.getDouble("longitude"));
					ad.setPostCode(result.getString("code_postal"));
					ad.setStreet(result.getString("rue"));
					ad.setTown(result.getString("ville"));
					
					historicSite.setAddress(ad);
					site = historicSite;
				}
				
				if (site != null) {
					double score = result.getScore();
					sitesWithScores.put(site, score);
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
		
		return sitesWithScores.entrySet().stream()
				.sorted(Map.Entry.<TouristSite, Double>comparingByValue().reversed())
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(e1, e2) -> e1,
						LinkedHashMap::new
				));
	}

	@Override
	public List<TouristSite> fetchByInput() {
		return null;
	}
}