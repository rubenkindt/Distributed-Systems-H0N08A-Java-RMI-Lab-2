package centralRentalAgency;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.InterfaceCarRentalCompany;

public class ManagerSession extends ReservationSession{

	public String name;
	private List<ReservationSession> clients =new ArrayList<ReservationSession>();
	
	public ManagerSession(String name,List<InterfaceCarRentalCompany> comp, List<ReservationSession> Clients) {
		super(name,comp);
		
	}
	public void setClients(List<ReservationSession> clients) {
		this.clients = clients;
	}


	public int getNumberOfReservationsByRenter(String clientName) {
		
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberOfReservationsForCarType(String carRentalName, String carType) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Set<String> getBestClients() {
		// TODO Auto-generated method stub
		return null;
	}

	public CarType getMostPopularCarTypeInCRC(String carRentalCompanyName, int year) {
		// TODO Auto-generated method stub
		return null;
	}
	public void removeClient(String name2) {
		
		for (int client=0;client<clients.size();client++) {
			if (clients.get(client).name.equals(name2)) {
				clients.remove(client);
			}
		}
		
	}
	public void addClient(ReservationSession resSession) {
		clients.add(resSession);
		
	}
	
	
}
