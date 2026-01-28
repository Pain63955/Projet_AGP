package persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import business.excursion.Address;
import business.excursion.Hotel;
import dao.HotelPersistence;

public class JdbcHotelPersistence implements HotelPersistence { 
	 
	@Override
	public void dataInit() {
		System.err.println("Please don't forget to create tables manually by importing creation.sql in your database !");
	}
	
	Connection dbConnection = JdbcConnection.getConnection();

	@Override
	public Hotel fetchName(String name) {
		try {
			
			String selectAddressQuery = "SELECT hotelID, nom_hotel, prix_hotel, gamme, plage, h.adresseID, latitude, longitude, rue, ville, code_postal "+
										"FROM Hotel h "+
										"JOIN Adresse a ON a.adresseID = h.adresseID "+
										"WHERE h.nom_hotel = ? ";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

			preparedStatement.setString(1, name);
			
			try (ResultSet result = preparedStatement.executeQuery()) {
                if (!result.next()) {
                    return null;
                }
				
                Hotel hotel = new Hotel();
				hotel.setId(result.getInt("hotelID"));
				hotel.setName(result.getString("nom_hotel"));
				hotel.setNightRate(result.getDouble("prix_hotel"));
				hotel.setGrade(result.getString("gamme"));
				hotel.setBeach(result.getString("plage"));
				
				Address address = new Address();
				address.setAddressId(result.getInt("adresseID"));
				address.setLatitude(result.getDouble("latitude"));
				address.setLongitude(result.getDouble("longitude"));
				address.setStreet(result.getString("rue"));
				address.setTown(result.getString("ville"));
				address.setPostCode(result.getString("code_postal"));
				
				hotel.setAddress(address);
				
				preparedStatement.close();
				return hotel;
			}
		}catch (SQLException se) {
			System.err.println(se.getMessage());
		}	
		return null;
	}
	
	@Override
	public List<Hotel> fetchNear(Address adresse) {
		
		List<Hotel> hotels = new ArrayList<>();
		
		try {
			
			String selectAddressQuery = "SELECT * FROM Hotel hot WHERE hot.adresseID = ? ";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

			preparedStatement.setInt(1, adresse.getAddressId());
			preparedStatement.setInt(1, adresse.getAddressId());
			
			ResultSet result = preparedStatement.executeQuery();
			try (ResultSet resultTry = preparedStatement.executeQuery()) {
                if (!resultTry.next()) {
                    return null;
                }
			} 
			
			while(result.next()) {
				result.next();
				
				Hotel hotel = new Hotel();
				
				hotel.setId(result.getInt("hotelID"));
				hotel.setName(result.getString("nom_hotel"));
				hotel.setNightRate(result.getDouble("prix_hotel"));
				hotel.setGrade(result.getString("gamme"));
				hotel.setBeach(result.getString("plage"));
				hotel.setDescription(result.getString("description"));	
				
				hotels.add(hotel);
			}

			preparedStatement.close();

			preparedStatement.executeUpdate();

			preparedStatement.close();
		
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}	
		return (hotels);
	}

	@Override
	public List<Hotel> fetchGrade(String range) {
		List<Hotel> hotels = new ArrayList<>();
		
		try {
			
			String selectAddressQuery = "SELECT * FROM Hotel hot WHERE hot.gamme = ? ";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

			preparedStatement.setString(1, range);
			
			ResultSet result = preparedStatement.executeQuery();
			try (ResultSet resultTry = preparedStatement.executeQuery()) {
                if (!resultTry.next()) {
                    return null;
                }
			} 
			result.next();
			
			while(result.next()) {
				result.next();
				
				Hotel hotel = new Hotel();
				
				hotel.setId(result.getInt("hotelID"));
				hotel.setName(result.getString("nom_hotel"));
				hotel.setNightRate(result.getDouble("prix_hotel"));
				hotel.setGrade(result.getString("gamme"));
				hotel.setBeach(result.getString("plage"));
				hotel.setDescription(result.getString("description"));	
				
				hotels.add(hotel);
			}

			preparedStatement.close();

			preparedStatement.executeUpdate();

			preparedStatement.close();
		
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}	
		return (hotels);
	}

	@Override
	public List<Hotel> fetchPrice(double lowPrice, double highPrice) {
		List<Hotel> hotels = new ArrayList<>();
		
		try {
			
			String selectAddressQuery = "SELECT * FROM Hotel hot WHERE hot.prix_hotel >= ? AND hot.prix_hotel <=  ? ";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

			preparedStatement.setDouble(1, lowPrice);
			preparedStatement.setDouble(1, highPrice);
			
			ResultSet result = preparedStatement.executeQuery();
			try (ResultSet resultTry = preparedStatement.executeQuery()) {
                if (!resultTry.next()) {
                    return null;
                }
			} 
			
			while(result.next()) {
				result.next();
				
				Hotel hotel = new Hotel();
				
				hotel.setId(result.getInt("hotelID"));
				hotel.setName(result.getString("nom_hotel"));
				hotel.setNightRate(result.getDouble("prix_hotel"));
				hotel.setGrade(result.getString("gamme"));
				hotel.setBeach(result.getString("plage"));
				hotel.setDescription(result.getString("description"));	
				
				hotels.add(hotel);
			}

			preparedStatement.close();

			preparedStatement.executeUpdate();

			preparedStatement.close();
		
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}	
		return (hotels);
	}
}
