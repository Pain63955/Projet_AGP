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
<<<<<<< HEAD
				
                Hotel hotel = new Hotel();
				hotel.setId(result.getInt("hotelID"));
				hotel.setNom(result.getString("nom_hotel"));
				hotel.setPrixNuit(result.getDouble("prix_hotel"));
				hotel.setGamme(result.getString("gamme"));
				hotel.setPlage(result.getString("plage"));
				
				Adresse adresse = new Adresse();
				adresse.setAdresseId(result.getInt("adresseID"));
				adresse.setLatitude(result.getDouble("latitude"));
				adresse.setLongitude(result.getDouble("longitude"));
				adresse.setRue(result.getString("rue"));
				adresse.setVille(result.getString("ville"));
				adresse.setCodePostal(result.getString("code_postal"));
				
				hotel.setAdresse(adresse);
				
				preparedStatement.close();
				return hotel;
			}
=======
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
		
>>>>>>> branch 'master' of https://github.com/Pain63955/Projet_AGP.git
		} catch (SQLException se) {
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

<<<<<<< HEAD
			preparedStatement.setInt(1, adresse.getAdresseId());
=======
			preparedStatement.setInt(1, adresse.getAddressId());
>>>>>>> branch 'master' of https://github.com/Pain63955/Projet_AGP.git
			
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
