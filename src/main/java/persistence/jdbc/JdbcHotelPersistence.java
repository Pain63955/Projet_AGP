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
		Hotel hotel = new Hotel();
		
		try {
			
			String selectAddressQuery = "SELECT * FROM Hotel hot WHERE hot.name = ? ";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

			preparedStatement.setString(1, name);
			
			ResultSet result = preparedStatement.executeQuery();
			try (ResultSet resultTry = preparedStatement.executeQuery()) {
                if (!resultTry.next()) {
                    return null;
                }
			} 
			result.next();
			
			hotel.setId(result.getInt("hotelID"));
			hotel.setName(result.getString("nom_hotel"));
			hotel.setNightRate(result.getDouble("prix_hotel"));
			hotel.setGrade(result.getString("gamme"));
			hotel.setBeach(result.getString("plage"));
			hotel.setDescription(result.getString("description"));	

			preparedStatement.close();

			preparedStatement.executeUpdate();

			preparedStatement.close();
		
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}	
		return hotel;
	}
	
	@Override
	public List<Hotel> fetchNear(Address adresse) {
		
		List<Hotel> hotels = new ArrayList<>();
		
		try {
			
			String selectAddressQuery = "SELECT * FROM Hotel hot WHERE hot.adresseID = ? ";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

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
