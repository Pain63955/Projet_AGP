package business.excursion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import business.path.*;

public class Excursion implements PriceableElement {
    
    private List<TouristSite> sites = new ArrayList<>();
    private List<Path> paths = new ArrayList<>();
    private int nbSite;
    private TransportFactory factory;

    public Excursion() {}

    public void addSite(TouristSite site) {
        if (sites.size() < this.nbSite) {
            sites.add(site);
        } else {
            throw new RuntimeException("Maximum " + this.nbSite + " sites par jour autorisé.");
        }
    }

    /**
     * Génère le circuit logique de la journée :
     * Hôtel -> Site 1 -> Site 2 -> Site 3 -> Hôtel
     */
    public void generateTour(Hotel hotel, String mode) {
        if (sites.isEmpty()) return;
        this.paths.clear();

        // 1. Départ : Hôtel -> Premier Site
        createAndAddPath(hotel.getAddress(), sites.get(0).getAddress(), mode);

        // 2. Entre les sites : Site n -> Site n+1
        for (int i = 0; i < sites.size() - 1; i++) {
            createAndAddPath(sites.get(i).getAddress(), sites.get(i+1).getAddress(), mode);
        }

        // 3. Retour : Dernier Site -> Hôtel
        createAndAddPath(sites.get(sites.size() - 1).getAddress(), hotel.getAddress(), mode);
    }

    private void createAndAddPath(Address departure, Address arrival, String mode) {
        if (departure == null || arrival == null) {
            throw new IllegalArgumentException("Adresses nulles.");
        }
        double distance = calculeDistance(departure.getLatitude(), departure.getLongitude(), arrival.getLatitude(), arrival.getLongitude());
        Path t = factory.createPath(mode, distance);
        
        if (t == null) {
            throw new RuntimeException("La factory n'a pas pu créer de trajet pour le mode : " + mode);
        }
        this.paths.add(t);
    }

    private double calculeDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) 
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        return dist * 60 * 1.1515 * 1.609344; // Distance en km
    }

    @Override
    public double getPrice() {
        double totalPricePath = 0.0;
        double totalPriceSite = 0.0;
        
        // Calcul sécurisé des trajets
        if (paths != null) {
            for (Path path : paths) {
                if (path != null) {
                    totalPricePath += path.getPrice();
                }
            }
        }
        
        // Calcul sécurisé des sites (Correction de l'accumulation)
        if (sites != null) {
            for (TouristSite site : sites) {
                if (site != null) {
                    totalPriceSite += site.getPrice(); // On additionne au total des sites
                }
            }
        }
        
        return totalPricePath + totalPriceSite;
    }

    // Getters et Setters
    public List<TouristSite> getSites() { 
    	return sites; 
    }
    
    public List<Path> getTrajets() { 
    	return paths;
    
    }
    public void setFactory(TransportFactory factory) { 
    	this.factory = factory;
    }
    
    public void setNbSite(int nbSite) {
        this.nbSite = nbSite;
    }

    public int getNbSite() {
        return nbSite;
    }
    
    public double getTotalDistance() {
        double total = 0.0;
        for (Path p : paths) {
            if (p != null) {
                total += p.getDistance(); // Assurez-vous que la classe Path a getDistance()
            }
        }
        return total;
    }
}