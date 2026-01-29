package dao;

import business.excursion.Hotel;

import java.util.List;

public interface HotelPersistence{ 
	
	void dataInit();

    List<Hotel> fetchGrade(int range);

}