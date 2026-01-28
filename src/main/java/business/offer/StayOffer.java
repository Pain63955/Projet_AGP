package business.offer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import business.excursion.PriceableElement;
import business.excursion.Excursion;
import business.excursion.Hotel;

public class StayOffer implements PriceableElement{
	
	//
	private String idOffer;
    private Hotel hotel; 
    private List<Excursion> excursions = new ArrayList<>();
    private int nbNights;
    

	private double scoreComfort;

    public StayOffer() {
    }

    @Override
    public double getPrice() {
    	double totalPriceExcursions = 0.0;
    	
    	Iterator<Excursion> ite = excursions.iterator();
    	while(ite.hasNext()) {
    		Excursion excursion = ite.next();
    		double priceExcursion = excursion.getPrice();
    		totalPriceExcursions = totalPriceExcursions + priceExcursion;
    	}
    	
        double totalPriceHotel = 0.0;
        if (hotel != null) {
            totalPriceHotel = hotel.getPrice() * nbNights;
        }
        
        return totalPriceHotel + totalPriceExcursions;
    }


    public void addExcursion(Excursion excursion) {
        if (excursion != null) {
            this.excursions.add(excursion);
        }
    }


    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Excursion> getExcursions() {
        return excursions;
    }

    public double getScoreComfort() {
        return scoreComfort;
    }

    public void setScoreComfort(double scoreComfort) {
        this.scoreComfort = scoreComfort;
    }

    public String getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(String idOffer) {
        this.idOffer = idOffer;
    }

    public boolean fitsBudget(double budgetMax) {
        return getPrice() <= budgetMax;
    }
    
    public int getNbNights() {
    	return nbNights;
    }

	public void setNbNights(int nbNights) {
		this.nbNights = nbNights;
	}
    
}

