package centralRentalAgency;

import java.util.List;

import rental.InterfaceCarRentalCompany;

public class ManagerSession {

	public String name;

	private static List<InterfaceCarRentalCompany> comp;
	
	public ManagerSession(String managerName,List<InterfaceCarRentalCompany> comp) {
		this.name=managerName;
		this.comp=comp;
	}
	
	
}
