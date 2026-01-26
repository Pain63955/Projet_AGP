package dao;

import business.excursion.Hotel;

import java.math.BigDecimal;

import business.excursion.Adresse;

/**
 
General DAO interface for persistence APIs.*/
public interface HotelPersistence{
	
	void dataInit();

    Hotel fetchNear(Adresse adresse);

    Hotel fetchGamme(String range);

	Hotel fetchPrice(BigDecimal lowPrice, BigDecimal highPrice);

}