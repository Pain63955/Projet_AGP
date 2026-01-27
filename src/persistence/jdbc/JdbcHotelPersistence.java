package persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import business.excursion.Adresse;
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Hotel fetchNear(Adresse adresse) {
		Hotel hotel = new Hotel();
		try {
			
			String selectAddressQuery = "SELECT * FROM Hotel hot WHERE hot.adresseID = ? ";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

			preparedStatement.setint(1, adresse.getID());
			
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()) {
				
			}

			result.next();
			hotel.setId(result.getInt("hotelID"));
			hotel.setNom(result.getString("nom_hotel"));
			hotel.setPrixNuit(result.getDouble("prix_hotel"));
			hotel.setGamme(result.getString("gamme"));
			hotel.setPlage(result.getString("plage"));
			hotel.setDescription(((Hotel) result).getDescription());				

			preparedStatement.close();

			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
	}

	@Override
	public Hotel fetchGamme(String range) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hotel fetchPrice(double lowPrice, double highPrice) {
		// TODO Auto-generated method stub
		return null;
	}
}
