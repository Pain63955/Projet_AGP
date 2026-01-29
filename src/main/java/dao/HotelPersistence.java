package dao;

import business.excursion.Hotel;

import java.util.List;

public interface HotelPersistence{ 
	
	void dataInit();
	
	Hotel fetchName(String nom);

    List<Hotel> fetchGrade(String range);

}