package business.offre;

public class CritereRecherche {
    private String destination;     // Pour filtrer la zone géographique
    private int nbJours;           // Utilise la boucle i < nbJours dans le Builder
    private double budgetMax;      // La limite pour la méthode estDansLeBudget() de OffreSejour
    private String motsCles;       // La chaîne de texte analysée par l'IA (ex: "zen temple surf")
    private String transportSouhaite; // Le mode envoyé à la factory (BATEAU, BUS, etc.)

    // Constructeur vide (nécessaire si tu veux l'utiliser en tant que Bean Spring)
    public CritereRecherche() {}

    // Constructeur pratique pour ton Main
    public CritereRecherche(String destination, int nbJours, double budgetMax, String motsCles, String transportSouhaite) {
        this.destination = destination;
        this.nbJours = nbJours;
        this.budgetMax = budgetMax;
        this.motsCles = motsCles;
        this.transportSouhaite = transportSouhaite;
    }

    // --- GETTERS ET SETTERS ---
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getNbJours() { return nbJours; }
    public void setNbJours(int nbJours) { this.nbJours = nbJours; }

    public double getBudgetMax() { return budgetMax; }
    public void setBudgetMax(double budgetMax) { this.budgetMax = budgetMax; }

    public String getMotsCles() { return motsCles; }
    public void setMotsCles(String motsCles) { this.motsCles = motsCles; }

    public String getTransportSouhaite() { return transportSouhaite; }
    public void setTransportSouhaite(String transportSouhaite) { this.transportSouhaite = transportSouhaite; }
}