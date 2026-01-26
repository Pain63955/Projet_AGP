package persistence.hibernate;

import java.util.ArrayList;
import java.util.List;

import businessTests.client.AbstractClient;
import businessTests.client.AbstractOperation;
import businessTests.client.SimulationEntry;
import businessTests.simulation.StatisticManager;

/**
 * This class constructs persistence objects from business objects.
 * 
 */
class ORMBuilder {
	public static SimulationData buildData(SimulationEntry entry, StatisticManager statisticManager) {
		List<ClientData> allClients = new ArrayList<ClientData>();
		for (AbstractClient client : statisticManager.getServedClients()) {
			AbstractOperation operation = client.getOperation();
			OperationData operationData = new OperationData(operation.getClass().getName(), operation.getServiceTime(), operation.isUrgent());
			ClientData clientData = new ClientData(client.getArrivalTime(), client.getServiceStartTime(), client.getDepartureTime(), true,
					client.isPriority(), operationData);
			allClients.add(clientData);
		}
		for (AbstractClient client : statisticManager.getNonServedClients()) {
			AbstractOperation operation = client.getOperation();
			OperationData operationData = new OperationData(operation.getClass().getName(), operation.getServiceTime(), operation.isUrgent());
			ClientData clientData = new ClientData(client.getArrivalTime(), client.getServiceStartTime(), client.getDepartureTime(), false,
					client.isPriority(), operationData);
			allClients.add(clientData);
		}

		return new SimulationData(entry.getSimulationDuration(), entry.getCashierCount(), entry.getMinServiceTime(), entry.getMaxServiceTime(),
				entry.getClientArrivalInterval(), entry.getPriorityClientRate(), entry.getClientPatienceTime(), allClients);
	}

	public static SimulationEntry buildEntry(SimulationData data) {
		return new SimulationEntry(data.getSimulationDuration(), data.getCashierCount(), data.getMinServiceTime(), data.getMaxServiceTime(),
				data.getClientArrivalInterval(), data.getPriorityClientRate(), data.getClientPatienceTime());
	}
}
