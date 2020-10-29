// Implemented by Ruben Kindt
package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;


public interface InterfaceCarRentalCompany extends Remote{
	
 	public String getName() throws RemoteException;


     /***********
	  * Regions *
	  **********/
	 
	 public List<String> getRegions() throws RemoteException;
	 
	 public boolean operatesInRegion(String region) throws RemoteException;
	
	/*************
	 * CAR TYPES *
	 *************/
	
	public Collection<CarType> getAllCarTypes() throws RemoteException;
	
	public CarType getCarType(String carTypeName) throws RemoteException;
	
	// mark
	public boolean isAvailable(String carTypeName, Date start, Date end) throws RemoteException;
	
	public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException;
	
	
	/*********
	 * CARS *
	 *********/
	public int getNumberOfReservationsForCarType(String carType) throws RemoteException;
	
	
	/****************
	 * RESERVATIONS *
	 ****************/
	
	public List<Reservation> getReservationsByRenter(String clientName) throws RemoteException;
	
	public Quote createQuote(ReservationConstraints constraints, String client) throws ReservationException, RemoteException;
				
	
	public Reservation confirmQuote(Quote quote) throws ReservationException, RemoteException;
	
	public void cancelReservation(Reservation res) throws RemoteException;
	 	
	 
}
