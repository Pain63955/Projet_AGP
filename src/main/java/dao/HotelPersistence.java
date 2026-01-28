package dao;

import business.excursion.Hotel;

import java.util.List;

import business.excursion.Address;

public interface HotelPersistence{ 
	
	void dataInit();
	
	Hotel fetchName(String nom);

    List<Hotel> fetchNear(Address adresse);

    List<Hotel> fetchGrade(String range);

	List<Hotel> fetchPrice(double lowPrice, double highPrice);

}