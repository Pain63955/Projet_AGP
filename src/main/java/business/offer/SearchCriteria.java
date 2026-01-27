package business.offer;

public class SearchCriteria {
    private String destination;     // Pour filtrer la zone géographique
    private int nbDays;           // Utilise la boucle i < nbJours dans le Builder
    private double budgetMax;      // La limite pour la méthode estDansLeBudget() de OffreSejour
    private String keywords;       // La chaîne de texte analysée par l'IA (ex: "zen temple surf")
    private String askedTransport; // Le mode envoyé à la factory (BATEAU, BUS, etc.)

    public SearchCriteria() {}

    public SearchCriteria(String destination, int nbJours, double budgetMax, String motsCles, String transportSouhaite) {
        this.destination = destination;
        this.nbDays = nbJours;
        this.budgetMax = budgetMax;
        this.keywords = motsCles;
        this.askedTransport = transportSouhaite;
    }

    // --- GETTERS ET SETTERS ---
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getNbDays() { return nbDays; }
    public void setNbDays(int nbJours) { this.nbDays = nbJours; }

    public double getBudgetMax() { return budgetMax; }
    public void setBudgetMax(double budgetMax) { this.budgetMax = budgetMax; }

    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }

    public String getAskedTransport() { return askedTransport; }
    public void setAskedTransport(String askedTransport) { this.askedTransport = askedTransport; }
}