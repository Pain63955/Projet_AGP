package business.excursion;

import java.math.BigDecimal;

public class Hotel implements ElementTarifiable{

    private String nom;
    private BigDecimal prixNuit;
    private Plage plage;
    private Adresse adresse;
    private String description;
    private int id;

    @Override
    public BigDecimal getPrix() { 
        return this.prixNuit; 
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public BigDecimal getPrixNuit() {
        return prixNuit;
    }

    public void setPrixNuit(BigDecimal prixNuit) {
        this.prixNuit = prixNuit;
    }

    public Plage getPlage() {
        return plage;
    }

    public void setPlage(Plage plage) {
        this.plage = plage;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}