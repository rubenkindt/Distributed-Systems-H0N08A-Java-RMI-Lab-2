package centralRentalAgency;

import java.util.List;

import rental.InterfaceCarRentalCompany;

public class ManagerSession extends Session{

	public String name;

	private static List<InterfaceCarRentalCompany> comp;
	
	public ManagerSession(String name,List<InterfaceCarRentalCompany> comp) {
		super(name,comp);
	}
	
	
}
