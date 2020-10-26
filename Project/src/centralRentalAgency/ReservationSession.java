package centralRentalAgency;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.InterfaceCarRentalCompany;
import rental.Reservation;

public class ReservationSession {
	
	public String name;

	private static List<InterfaceCarRentalCompany> comp;
	
	public ReservationSession(String Name,List<InterfaceCarRentalCompany> comp) {
		this.name=Name;
		this.comp=comp;
	}
	

	public void checkAvailableCarTYpes(Date start, Date end){
		//following javaDoc: return void
		Set<CarType> set=new HashSet<CarType>();
		
		for (int i=0;i<comp.size();i++) {
			try {
				set.addAll(comp.get(i).getAvailableCarTypes(start, end));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void addQuoteToSession(String name2, Date start, Date end, String carType, String region) {
		// TODO Auto-generated method stub
	}


	public List<Reservation> confirmQuotes(String name2) {
		// TODO Auto-generated method stub
		return null;
	}


	public String getCheapestCarType(Date start, Date end, String region) {
		// TODO Auto-generated method stub
		return null;
	}
}


