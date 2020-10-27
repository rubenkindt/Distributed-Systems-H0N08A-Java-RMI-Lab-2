package centralRentalAgency;

import java.rmi.Remote;

import java.rmi.RemoteException;
import java.util.Set;

import rental.CarType;

public interface InterfaceManagerSession extends Remote {

	public int getNumberOfReservationsByRenter(String clientName) throws RemoteException, Exception;
	
	public int getNumberOfReservationsForCarType(String carRentalName, String carType) throws RemoteException, Exception;
	
	public Set<String> getBestClients() throws RemoteException;
	
	public CarType getMostPopularCarTypeInCRC(String carRentalCompanyName, int year) throws RemoteException;
	
	public void removeClient(String clientName) throws RemoteException;
	public void addClient(ReservationSession resSession) throws RemoteException;


	
	
}
