package dao;

import business.excursion.Hotel;

import java.util.List;

import business.excursion.Adresse;

public interface HotelPersistence{ 
	
	void dataInit();
	
	Hotel fetchName(String nom);

    List<Hotel> fetchNear(Adresse adresse);

    List<Hotel> fetchGamme(String range);

	List<Hotel> fetchPrice(double lowPrice, double highPrice);

}