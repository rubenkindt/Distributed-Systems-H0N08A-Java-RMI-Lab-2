package centralRentalAgency;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

import rental.Car;
import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;


public interface InterfaceCentralRentalAgency extends Remote{
	
 	public String getName() throws RemoteException;


    // TODO remove comment 25/10
	

	public ReservationSession getNewReservationSession(String name);
	public void removeReservationSession(String name);


	public ManagerSession getNewManagerSession(String name);
	public void removeManagerSession(String name);
	 	
	 
}
