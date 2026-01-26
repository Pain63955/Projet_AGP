package business.trajet;

public class TransportFactory {
    public static Trajet creerTrajet(String mode, double distance) {
        double prixFixe;
        switch (mode.toUpperCase()) {
            case "AUTOBUS":
                prixFixe = 5.0;
                break;
            case "BATEAU":
                prixFixe = 15.0;
                break;
            case "PIED":
            default:
                prixFixe = 0.0;
                break;
        }
        return new Trajet(mode, prixFixe, distance);
    }
}
