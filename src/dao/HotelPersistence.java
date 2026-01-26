package dao;

import business.excursion.Hotel;

import business.excursion.Adresse;
import business.simulation.StatisticManager;

/**
 
General DAO interface for persistence APIs.*/
public interface HotelPersistence{

    Hotel fetchNear(Adresse adresse);

    Hotel fetchGamme(String gamme);

    Hotel fetchPrice();


    /*
    void dataInit();

    int persist(SimulationEntry simulationEntry, StatisticManager statisticManager);

    int servedClientCount(int simulationEntryId);

    int nonServedClientCount(int simulationEntryId);*/

}