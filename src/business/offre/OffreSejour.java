package business.offre;

import java.util.ArrayList;
import java.util.List;

import business.excursion.ElementTarifiable;
import business.excursion.Excursion;
import business.excursion.Hotel;

public class OffreSejour implements ElementTarifiable{
	
	private String idOffre;
    private Hotel hotel; 
    private List<Excursion> excursions = new ArrayList<>();
    private int nbNuits;
    
    private double scoreConfort;

    public OffreSejour() {
    }

    @Override
    public double getPrix() {
        double prixTotalExcursions = excursions.stream().mapToDouble(Excursion::getPrix).sum();
        double prixTotalHotel = 0;
        if (hotel != null) {
            prixTotalHotel = hotel.getPrix() * nbNuits;
        }
        
        return prixTotalHotel + prixTotalExcursions;
    }


    public void ajouterExcursion(Excursion excursion) {
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

    public double getScoreConfort() {
        return scoreConfort;
    }

    public void setScoreConfort(double scoreConfort) {
        this.scoreConfort = scoreConfort;
    }

    public String getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(String idOffre) {
        this.idOffre = idOffre;
    }

    public boolean estDansLeBudget(double budgetMax) {
        return getPrix() <= budgetMax;
    }

	public void setNbNuits(int nbNuits) {
		this.nbNuits = nbNuits;
	}
    
}

