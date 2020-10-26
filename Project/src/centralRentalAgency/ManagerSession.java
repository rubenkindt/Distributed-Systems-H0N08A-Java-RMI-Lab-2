package centralRentalAgency;

import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.InterfaceCarRentalCompany;

public class ManagerSession extends ReservationSession{

	public String name;

	private static List<InterfaceCarRentalCompany> comp;
	
	public ManagerSession(String name,List<InterfaceCarRentalCompany> comp) {
		super(name,comp);
	}

	public int getNumberOfReservationsByRenter(String clientName) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getNumberOfReservationsForCarType(String carRentalName, String carType) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Set<String> getBestClients() {
		// TODO Auto-generated method stub
		return null;
	}

	public CarType getMostPopularCarTypeInCRC(String carRentalCompanyName, int year) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
