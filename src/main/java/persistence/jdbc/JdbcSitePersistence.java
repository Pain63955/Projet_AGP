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
import java.util.List;

public class JdbcSitePersistence implements SitePersistence {

	private Connection dbConnection;
	
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
	public List<TouristSite> fetchKeywords(String keywords) {
		List<TouristSite> sites = new ArrayList<>();
		
		// Vérification de la connexion avant toute opération
		if (dbConnection == null) {
			System.err.println("ERREUR: Impossible d'exécuter la requête - connexion à la BD est null");
			return sites; // Retourne une liste vide plutôt que null
		}
		
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		
		try {
			String selectAddressQuery = "SELECT st.*, ad.*, sa.duration AS info_specifique\r\n"
					+ "FROM SiteTouristique st\r\n"
					+ "JOIN SitesActiv sa ON sa.siteID = st.siteID\r\n"
					+ "JOIN Adresse ad ON st.adresseID = ad.adresseID\r\n"
					+ "UNION ALL\r\n"
					+ "SELECT st.*, ad.*, sh.guideName AS info_specifique\r\n"
					+ "FROM SiteTouristique st\r\n"
					+ "JOIN SitesHisto sh ON sh.siteID = st.siteID\r\n"
					+ "JOIN Adresse ad ON st.adresseID = ad.adresseID\r\n";
					//+ "WITH ? ";

			preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

			//preparedStatement.setString(1, keywords);
			
			result = preparedStatement.executeQuery();
			
			while(result.next()) {
				
				if (result.getString("site_type").equals("SiteActivite")) {
					ActivitySite site = new ActivitySite();
					site.setName(result.getString("nom"));
					site.setPrice(result.getDouble("prix"));
					site.setTransport(result.getString("transport"));
					site.setDurationRatio(result.getFloat("info_specifique"));
					
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
		
		} catch (SQLException se) {
			System.err.println("Erreur SQL dans fetchKeywords: " + se.getMessage());
			se.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
				if (preparedStatement != null) preparedStatement.close();
			} catch (SQLException e) {
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