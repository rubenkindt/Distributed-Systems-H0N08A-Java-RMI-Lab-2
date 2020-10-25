package centralRentalAgency;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.InterfaceCarRentalCompany;

public class ReservationSession extends Session{
	
	public String name;
	private static List<InterfaceCarRentalCompany> comp;
	
	public ReservationSession(String Name, List<InterfaceCarRentalCompany> comp) {
		super(Name, comp);
	}
	
	
	
	
	
	
	
}
