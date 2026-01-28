package business.offer;

import org.springframework.context.ApplicationContext;

import business.excursion.Excursion;
import business.excursion.TouristSite;

import java.util.*;
import java.util.stream.Collectors;

public class OfferBuilder {
	
    private ApplicationContext context;
    private SearchCriteria criteria;
    private StayOffer offer;
    private static final double TOLERANCE_BUDGET = 200.0;

    public OfferBuilder(ApplicationContext context, SearchCriteria criteres) {
        this.context = context;
        this.criteria = criteres;
        // On récupère le bean offerBali configuré dans le XML (contient déjà l'hôtel)
        this.offer = (StayOffer) context.getBean("offerBali");
    }
    
    
    
    public StayOffer build() {
        return this.offer;
    }
}