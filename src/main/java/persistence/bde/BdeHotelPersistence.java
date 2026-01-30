package persistence.bde;

import java.util.ArrayList;
import java.util.List;

import api.engine.BDeConfig;
import api.engine.BDeConnection;
import api.engine.BDeResultSet;
import api.engine.BDeStatement;
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
			throw new RuntimeException("Impossible d'initialiser BDeConnection");
		}
	}
	 
	@Override
	public void dataInit() {
	}
	
	@Override
	public List<Hotel> fetchGrade(int range) {
		List<Hotel> hotels = new ArrayList<>();
		
		if (conn == null) {
			return hotels;
		}
		
		BDeStatement st = null;
		BDeResultSet result = null;
		
		try {
			String selectAddressQuery = "SELECT h.*, ad.* FROM Hotel h JOIN Adresse ad ON ad.adresseID = h.adresseID WHERE h.gamme >= " + range;
					
			st = conn.prepareStatement(selectAddressQuery);			
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
			se.printStackTrace();
		} finally {
			try {
				if (result != null) result.close();
			} catch (Exception e) {
				e.printStackTrace();
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
		}
		
		return 1;
	}
}