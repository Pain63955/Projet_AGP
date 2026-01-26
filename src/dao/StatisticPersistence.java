package dao;

/**
 * General DAO interface for persistence APIs.
 */
public interface StatisticPersistence {
	
	void dataInit();

	int persist(SimulationEntry simulationEntry, StatisticManager statisticManager);

	int servedClientCount(int simulationEntryId);
	
	int nonServedClientCount(int simulationEntryId);

}
