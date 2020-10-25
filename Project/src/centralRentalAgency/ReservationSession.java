package centralRentalAgency;

import java.util.List;

import rental.InterfaceCarRentalCompany;

public class ReservationSession {
	public String name;
	private static List<InterfaceCarRentalCompany> comp;
	
	public ReservationSession(String renterName,List<InterfaceCarRentalCompany> comp) {
		this.name=renterName;
		this.comp=comp;
	}
	
	
	
}
