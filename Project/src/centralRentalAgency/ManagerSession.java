package centralRentalAgency;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.InterfaceCarRentalCompany;

public class ManagerSession extends ReservationSession{

	private List<ReservationSession> clients =new ArrayList<ReservationSession>();
	
	public ManagerSession(String name,ArrayList<InterfaceCarRentalCompany> comp, List<ReservationSession> Clients) {
		super(name,comp);
		setClients(clients);
	}
	public void setClients(List<ReservationSession> clients) {
		this.clients = clients;
	}


	public int getNumberOfReservationsByRenter(String clientName) throws Exception{
		for (int i=0;i<clients.size();i++) {
			if(clients.get(i).name.equals(clientName)){
				return clients.get(i).getResList().size();
			}
		}
		throw new Exception("Did not find client: "+clientName);
	}

	public int getNumberOfReservationsForCarType(String carRentalName, String carType) throws Exception {
		for (int i=0;i<comp.size();i++) {
			if (comp.get(i).getName().equals(carRentalName)) {
				return comp.get(i).getNumberOfReservationsForCarType(carType);
			}
		}
		throw new Exception("Did not find carRentalName: "+carRentalName+" or CarType: "+carType);
	}

	public Set<String> getBestClients() {
		Set<String> bestC=new HashSet<String>();
		int best=0;
		for (int i=0;i<clients.size();i++) {
			if (best>clients.get(i).getResList().size()) {
				best=clients.get(i).getResList().size();
				bestC.clear();
				bestC.add(clients.get(i).name);
				
			}else if (best==clients.get(i).getResList().size()) {
				bestC.add(clients.get(i).name);
			}
		}		
		return bestC;
	}

	public CarType getMostPopularCarTypeInCRC(String carRentalCompanyName, int year) throws RemoteException {
		int mostPop=0;
		CarType best=null;
		for (int i=0;i<comp.size();i++) {
			if(comp.get(i).getName().equals(carRentalCompanyName)) {
				Collection<CarType> col= comp.get(i).getAllCarTypes();
				
				for (int j=0;j<col.size();j++) {
					CarType current = col.iterator().next();
					
					if(mostPop>comp.get(i).getNumberOfReservationsForCarType(current.getName())) {
						mostPop=comp.get(i).getNumberOfReservationsForCarType(current.getName());
						best=current;
					}
				}
			}
		}
		return best;
	}
	
	public void removeClient(String clientName) {
		
		for (int client=0;client<clients.size();client++) {
			if (clients.get(client).name.equals(clientName)) {
				clients.remove(client);
			}
		}
		
	}
	public void addClient(ReservationSession resSession) {
		clients.add(resSession);
		
	}
	
	
}
