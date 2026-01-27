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
	public Hotel fetchNear(Adresse adresse) {
		Hotel hotel = Hotel.
		try {
			
			String selectAddressQuery = "SELECT * FROM Hotel hot WHERE hot.adresseID = ? ";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

			preparedStatement.setint(1, Adresse.getID());
			
			ResultSet result = preparedStatement.executeQuery();

			result.next();
			hotel = result.getInt("co");

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
