package business.excursion;

import java.util.ArrayList;
import java.util.List;

import business.trajet.Trajet;
import business.trajet.TransportFactory;

public class Excursion implements ElementTarifiable {
    
    private List<SiteTouristique> sites = new ArrayList<>();
    private List<Trajet> trajets = new ArrayList<>();
    private int nbSite;
    private TransportFactory factory;

    public Excursion() {}

    public void ajouterSite(SiteTouristique site) {
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
    public void genererCircuit(Hotel hotel, String mode) {
        if (sites.isEmpty()) return;
        this.trajets.clear();

        // 1. Départ : Hôtel -> Premier Site
        creerEtAjouterTrajet(hotel.getAdresse(), sites.get(0).getAdresse(), mode);

        // 2. Entre les sites : Site n -> Site n+1
        for (int i = 0; i < sites.size() - 1; i++) {
            creerEtAjouterTrajet(sites.get(i).getAdresse(), sites.get(i+1).getAdresse(), mode);
        }

        // 3. Retour : Dernier Site -> Hôtel
        creerEtAjouterTrajet(sites.get(sites.size() - 1).getAdresse(), hotel.getAdresse(), mode);
    }

    private void creerEtAjouterTrajet(Adresse depart, Adresse arrivee, String mode) {
        if (depart == null || arrivee == null) {
            throw new IllegalArgumentException("Les adresses de départ et d'arrivée ne peuvent pas être nulles.");
        }
        double distance = calculerDistance(depart.getLat(), depart.getLon(), arrivee.getLat(), arrivee.getLon());
        Trajet t = factory.creerTrajet(mode, distance);
        this.trajets.add(t);
    }

    private double calculerDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) 
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        return dist * 60 * 1.1515 * 1.609344; // Distance en km
    }

    @Override
    public double getPrix() {
        double totalTrajets = trajets.stream().mapToDouble(Trajet::getPrix).sum();
        double totalEntreesSites = sites.stream().mapToDouble(SiteTouristique::getPrix).sum();
        return totalTrajets + totalEntreesSites;
    }

    // Getters et Setters
    public List<SiteTouristique> getSites() { return sites; }
    public List<Trajet> getTrajets() { return trajets; }
    public void setFactory(TransportFactory factory) { this.factory = factory; }
    
    public void setNbSite(int nbSite) {
        this.nbSite = nbSite;
    }

    public int getNbSite() {
        return nbSite;
    }
}