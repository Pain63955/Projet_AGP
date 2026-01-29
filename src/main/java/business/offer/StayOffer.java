package business.offer;

import java.util.ArrayList;
import java.util.List;
import business.excursion.PriceableElement;
import business.excursion.Excursion;
import business.excursion.Hotel;

public class StayOffer implements PriceableElement {
    private String idOffer;
    private List<Hotel> hotels = new ArrayList<>(); 
    private List<Excursion> excursions = new ArrayList<>();
    private int nbNights;
    private double scoreComfort;

    public StayOffer() {}

    @Override
    public double getPrice() {
        // Somme du prix de toutes les excursions
        double totalExcursions = excursions.stream().mapToDouble(Excursion::getPrice).sum();
        
        // Somme du prix de chaque hôtel stocké dans la liste (1 hôtel = 1 nuit)
        double totalHotels = hotels.stream().mapToDouble(Hotel::getPrice).sum();
        
        return totalExcursions + totalHotels;
    }

    public void addExcursion(Excursion excursion) {
        if (excursion != null) this.excursions.add(excursion);
    }

    public void addHotel(Hotel hotel) {
        if (hotel != null) this.hotels.add(hotel);
    }

    public List<Hotel> getHotels() { return hotels; }
    public List<Excursion> getExcursions() { return excursions; }
    public double getScoreComfort() { return scoreComfort; }
    public void setScoreComfort(double scoreComfort) { this.scoreComfort = scoreComfort; }
    public int getNbNights() { return nbNights; }
    public void setNbNights(int nbNights) { this.nbNights = nbNights; }

    // Pour compatibilité avec les anciens tests
    public Hotel getHotel() {
        return hotels.isEmpty() ? null : hotels.get(hotels.size() - 1);
    }

    public void setHotel(Hotel hotel) {
        this.hotels.clear();
        this.hotels.add(hotel);
    }
}