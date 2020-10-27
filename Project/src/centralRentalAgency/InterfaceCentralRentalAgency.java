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

	public InterfaceReservationSession getNewReservationSession(String name) throws RemoteException;
	public void removeReservationSession(String name) throws RemoteException;

	public InterfaceManagerSession getNewManagerSession(String name) throws RemoteException;
	public void removeManagerSession(String name) throws RemoteException;
	 	
	 
}
