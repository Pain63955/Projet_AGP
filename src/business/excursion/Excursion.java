package business.excursion;

import java.util.ArrayList;
import java.util.List;

import business.trajet.Trajet;
import business.trajet.TransportFactory;

public class Excursion implements ElementTarifiable{
	
	private List<SiteTouristique> sites = new ArrayList<>();
    private List<Trajet> trajets = new ArrayList<>();
    private int nbSite = 3;

    public void ajouterSite(SiteTouristique site) {
        if (sites.size() < this.nbSite) {
            sites.add(site);
        } else {
            throw new RuntimeException("Maximum"+ this.nbSite +" sites par jour autorisé.");
        }
    }

    public void ajouterTrajet(Hotel hotel, SiteTouristique site, String mode) {
        if (hotel == null || site == null) {
            throw new IllegalArgumentException("Hôtel et Site ne peuvent pas être nuls.");
        }

        // 1. Récupération des coordonnées via la classe Adresse
        double lat1 = hotel.getAdresse().getLat();
        double lon1 = hotel.getAdresse().getLon();
        double lat2 = site.getAdresse().getLat();
        double lon2 = site.getAdresse().getLon();

        // 2. Calcul de la distance (Formule simplifiée ou Haversine)
        double distance = calculerDistance(lat1, lon1, lat2, lon2);

        // 3. Création du trajet via la logique tarifaire du CDC
        // Pattern Factory : On délègue la création pour respecter les prix fixes
        Trajet nouveauTrajet = TransportFactory.creerTrajet(mode, distance);

        this.trajets.add(nouveauTrajet);
    }
    
    /**
     * Calcule la distance en km entre deux points GPS.
     */
    private double calculerDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) 
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        return dist * 60 * 1.1515 * 1.609344; // Conversion en Kilomètres
    }
    
    @Override
    public double getPrix() {
        double totalTrajets = trajets.stream().mapToDouble(Trajet::getPrix).sum();
        double totalEntreesSites = sites.stream().mapToDouble(SiteTouristique::getPrix).sum();
        
        return totalTrajets + totalEntreesSites;
    }
}

