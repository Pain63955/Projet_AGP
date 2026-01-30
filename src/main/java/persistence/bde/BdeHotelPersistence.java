package persistence.bde;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import api.core.BDeConfig;
import api.core.BDeConnection;
import api.core.BDeResultSet;
import api.core.BDeStatement;
import business.excursion.Address;
import business.excursion.Hotel;
import dao.HotelPersistence;

public class BdeHotelPersistence implements HotelPersistence { 
	
	private BDeConfig cfg;
	private BDeConnection conn;
	
	public BdeHotelPersistence() {

		this.cfg = new BDeConfig("Hotel", "hotelID", "data/descriptions");
		
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
	public List<Hotel> fetchGrade(int range) {
		List<Hotel> hotels = new ArrayList<>();
		
		// Vérification de la connexion avant toute opération
		if (conn == null) {
			System.err.println("ERREUR: Impossible d'exécuter la requête - connexion BDE est null");
			return hotels;
		}
		
		BDeStatement st = null;
		BDeResultSet result = null;
		
		try {
			String selectAddressQuery = "SELECT h.*, ad.* \r\n"
					+ "FROM `Hotel` h\r\n"
					+ "JOIN Adresse ad ON ad.adresseID = h.adresseID\r\n"
					+ "WHERE h.gamme >= ?";
					
			st = conn.prepareStatement(selectAddressQuery);
			//st.setInt(1, range); 
			
			result = st.executeQuery();

			while(result.next()) {

				Hotel hotel = new Hotel();
				
				hotel.setName(result.getString("nom_hotel"));
				hotel.setNightRate(result.getDouble("prix_hotel"));

				String gammeStr = result.getString("gamme");
				hotel.setGrade(extractGradeFromString(gammeStr));
				
				hotel.setBeach(result.getString("plage"));
				
				Address ad = new Address();
				ad.setLatitude(result.getDouble("latitude"));
				ad.setLongitude(result.getDouble("longitude"));
				ad.setPostCode(result.getString("code_postal"));
				ad.setStreet(result.getString("rue"));
				ad.setTown(result.getString("ville"));
				
				hotel.setAddress(ad);
				
				hotels.add(hotel);
			}
			
		} catch (Exception se) {
			System.err.println("Erreur SQL dans fetchGrade: " + se.getMessage());
			se.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
//				if (st != null) st.close();
			} catch (Exception e) {
				System.err.println("Erreur lors de la fermeture des ressources: " + e.getMessage());
			}
		}
		return hotels;
	}
	
	private int extractGradeFromString(String gammeStr) {
		if (gammeStr == null || gammeStr.isEmpty()) {
			return 0;
		}
		
		try {
			if (gammeStr.matches("\\d+")) {
				return Integer.parseInt(gammeStr);
			}
			
			String[] parts = gammeStr.split("\\s+");
			for (String part : parts) {
				if (part.matches("\\d+")) {
					return Integer.parseInt(part);
				}
			}
			
			java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+)");
			java.util.regex.Matcher matcher = pattern.matcher(gammeStr);
			if (matcher.find()) {
				return Integer.parseInt(matcher.group(1));
			}
			
		} catch (NumberFormatException e) {
			System.err.println("Impossible de convertir la gamme: " + gammeStr);
		}
		
		return 1;
	}

}