package persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	public List<Hotel> fetchNear(Adresse adresse) {
		
		List<Hotel> hotels = new ArrayList<>();
		
		try {
			
			String selectAddressQuery = "SELECT * FROM Hotel hot WHERE hot.adresseID = ? ";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(selectAddressQuery);

			preparedStatement.setInt(1, adresse.getId());
			
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
				hotel.setNom(result.getString("nom_hotel"));
				hotel.setPrixNuit(result.getDouble("prix_hotel"));
				hotel.setGamme(result.getString("gamme"));
				hotel.setPlage(result.getString("plage"));
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
