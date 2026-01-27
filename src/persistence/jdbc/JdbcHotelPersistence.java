package persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import business.excursion.Adresse;
import business.excursion.Hotel;
import dao.HotelPersistence;

public class JdbcHotelPersistence implements HotelPersistence {
	
	@Override
	public void dataInit() {
		System.err.println("Please don't forget to create tables manually by importing creation.sql in your database !");
	}
	
	@Override
	public Hotel fetchNear(Adresse adresse) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public Hotel fetchPrice(double lowPrice, double highPrice) {
		// TODO Auto-generated method stub
		return null;
	}
}
