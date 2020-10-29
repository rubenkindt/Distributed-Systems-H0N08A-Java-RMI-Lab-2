// Implemented by Ruben Kindt
package centralRentalAgency;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.InterfaceCarRentalCompany;

public class ManagerSession extends ReservationSession implements InterfaceReservationSession, InterfaceManagerSession{

	private List<InterfaceReservationSession> clients =new ArrayList<InterfaceReservationSession>();
	
	public ManagerSession(String name,ArrayList<InterfaceCarRentalCompany> comp, List<InterfaceReservationSession> clients) throws RemoteException {
		super(name,comp);
		setClients(clients);
	}
	public void setClients(List<InterfaceReservationSession> clients) {
		this.clients = clients;
	}

	public int getNumberOfReservationsByRenter(String clientName) throws Exception{
		
		
		for (int i=0;i<clients.size();i++) {
			if(clients.get(i).getName().equals(clientName)){
				return clients.get(i).getResList().size();
			}
		}
		throw new Exception("Did not find client: "+clientName);
	}

	public int getNumberOfReservationsForCarType(String carRentalName, String carType) throws Exception {
		InterfaceCarRentalCompany compa=null;
		
		for (int i=0;i<ReservationSession.getComp().size();i++) {
			if (ReservationSession.getComp().get(i).getName().equals(carRentalName)) {
				compa=ReservationSession.getComp().get(i);
				break;
			}
		}
		if (compa==null) {
			throw new Exception("Can not find company with: "+carRentalName+" As name");
		}
		int nr= compa.getNumberOfReservationsForCarType(carType);
		return nr;
	}

	public Set<String> getBestClients() throws RemoteException{
		Set<String> bestC=new HashSet<String>();
		int best=0;
		bestC.clear();
		
		for (int i=0;i<clients.size();i++) {
			clients.get(i).getResList().size();

			if (best<clients.get(i).getResList().size()) {
				best=clients.get(i).getResList().size();
				bestC.clear();
				bestC.add(clients.get(i).getName());
				
			}
			if (best==clients.get(i).getResList().size()) {
				bestC.add(clients.get(i).getName());
			}
		}		
		
		return bestC;
	}

	public CarType getMostPopularCarTypeInCRC(String carRentalCompanyName, int year) throws Exception {
		
		int mostPop=0;
		CarType best=null;
		
		InterfaceCarRentalCompany compa=null;
		
		for (int i=0;i<ReservationSession.getComp().size();i++) {
			if(ReservationSession.getComp().get(i).getName().equals(carRentalCompanyName)) {
				compa=ReservationSession.getComp().get(i);
				break;
			}
		}
		
		if(compa==null) {
			throw new Exception("No company found with "+carRentalCompanyName+" as Name.");
		}
		
	
		Collection<CarType> col= compa.getAllCarTypes();
		
		for (Iterator<CarType> iterator = col.iterator(); iterator.hasNext();) {
			CarType current = (CarType) iterator.next();
			
			if(mostPop<compa.getNumberOfReservationsForCarType(current.getName())) {
				mostPop=compa.getNumberOfReservationsForCarType(current.getName());
				best=current;
			}
		}
		return best;
	
	}
	
	public void removeClient(String clientName) throws RemoteException{
		
		for (int client=0;client<clients.size();client++) {
			if (clients.get(client).getName().equals(clientName)) {
				clients.remove(client);
			}
		}
		
	}
	public void addClient(InterfaceReservationSession resSession) throws RemoteException{
		clients.add(resSession);
		
	}

	
	
}
