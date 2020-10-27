package centralRentalAgency;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rental.InterfaceCarRentalCompany;
import rental.Reservation;
import rental.ReservationException;

public interface InterfaceReservationSession extends Remote{
	
	public void checkAvailableCarTYpes(Date start, Date end) throws RemoteException, RemoteException;
	public void addQuoteToSession(String name, Date start, Date end, String carType, String region) throws Exception, RemoteException; ;

	public List<Reservation> confirmQuotes(String name2) throws RemoteException, ReservationException;


	public List<Reservation> getResList() throws RemoteException;

	public String getCheapestCarType(Date start, Date end, String region) throws Exception,RemoteException;

		
}
