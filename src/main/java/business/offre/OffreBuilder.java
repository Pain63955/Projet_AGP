package business.offre;

import org.springframework.context.ApplicationContext;

import business.excursion.Excursion;
import business.excursion.SiteTouristique;

import java.util.*;
import java.util.stream.Collectors;

public class OffreBuilder {
    private ApplicationContext context;
    private CritereRecherche criteres;
    private OffreSejour offre;
    private static final double TOLERANCE_BUDGET = 200.0;

    public OffreBuilder(ApplicationContext context, CritereRecherche criteres) {
        this.context = context;
        this.criteres = criteres;
        // On récupère le bean offreBali configuré dans le XML (contient déjà l'hôtel)
        this.offre = (OffreSejour) context.getBean("offreBali");
    }

    public OffreBuilder genererIA() {
        // 1. Récupération et filtrage sémantique des sites
        Map<String, SiteTouristique> catalogue = context.getBeansOfType(SiteTouristique.class);
        String[] motsCles = criteres.getMotsCles().toLowerCase().split(" ");

        List<SiteTouristique> sitesMatches = catalogue.values().stream()
            .filter(s -> {
                String desc = (s.getDescription() != null) ? s.getDescription().toLowerCase() : "";
                return Arrays.stream(motsCles).anyMatch(m -> m.length() > 2 && desc.contains(m));
            })
            .collect(Collectors.toList());

        // 2. Calcul de la répartition (pour éviter les jours vides)
        int totalSites = sitesMatches.size();
        int nbJours = criteres.getNbJours();
        
        // On calcule combien de sites mettre par jour (max 3)
        int sitesParJour = (int) Math.ceil((double) totalSites / nbJours);
        sitesParJour = Math.min(Math.max(sitesParJour, 1), 3);

        int currentSiteIdx = 0;

        // 3. Boucle de génération des journées
        for (int i = 0; i < nbJours; i++) {
            Excursion ex = (Excursion) context.getBean("excursion");
            
            // Ajout des sites selon la répartition calculée
            for (int slot = 0; slot < sitesParJour; slot++) {
                if (currentSiteIdx < totalSites) {
                    ex.ajouterSite(sitesMatches.get(currentSiteIdx));
                    currentSiteIdx++;
                }
            }

            // 4. Gestion du circuit et du Budget
            if (!ex.getSites().isEmpty()) {
                // Choix du mode de transport avec tolérance de 200€
                // Si (Prix Actuel + Prix Excursion estimée) > (Budget Max + 200) -> Passage en BUS
                String modeChoisi = criteres.getTransportSouhaite();
                
                // Simulation rapide : si on est déjà proche du plafond, on sécurise en AUTOBUS
                if (offre.getPrix() > (criteres.getBudgetMax() + TOLERANCE_BUDGET - 100)) {
                    modeChoisi = "AUTOBUS";
                }

                // Génération du circuit complet (Aller -> Sites -> Retour)
                ex.genererCircuit(offre.getHotel(), modeChoisi);
                
                // 5. Validation finale du budget avant d'ajouter l'excursion
                // On accepte de dépasser un peu si c'est dans la variance des 200€
                if (offre.getPrix() + ex.getPrix() <= (criteres.getBudgetMax() + TOLERANCE_BUDGET)) {
                    this.offre.ajouterExcursion(ex);
                } else {
                    // Si même en bus ça dépasse trop, on tente avec moins de sites ou on arrête
                    System.out.println("Jour " + (i+1) + " ignoré : dépassement du budget (variance incluse)");
                }
            }
        }
        return this;
    }

    public OffreSejour build() {
        return this.offre;
    }
}