package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import centralRentalAgency.InterfaceCentralRentalAgency;
import centralRentalAgency.ManagerSession;
import centralRentalAgency.ReservationSession;
import rental.CarType;

public class Manager extends AbstractTestManagement{

	/********
	 * MAIN *
	 ********/

	private final static int LOCAL = 0;
	private final static int REMOTE = 1;
	
	private InterfaceCentralRentalAgency cra;
	private ManagerSession mSession;
	
	/**
	 * The `main` method is used to launch the client application and run the test
	 * script.
	 */
	public static void main(String[] args) throws Exception {
		// The first argument passed to the `main` method (if present)
		// indicates whether the application is run on the remote setup or not.
		int localOrRemote = (args.length == 1 && args[0].equals("REMOTE")) ? REMOTE : LOCAL;

		String centeralRentalAgency = "CRA";

		if (localOrRemote==LOCAL){
			//disable security
			if (System.getSecurityManager() != null) {
				System.setSecurityManager(null);
			}
		}
		
		
		// An example reservation scenario on car rental company 'Hertz' would be...
		Manager manager= new Manager("Trips", centeralRentalAgency, localOrRemote);
		manager.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public Manager(String scriptFile, String centeralRentalAgency, int localOrRemote) {
		super(scriptFile);
		
		try {
			if (localOrRemote ==LOCAL){

				Registry registry = LocateRegistry.getRegistry();
				cra = (InterfaceCentralRentalAgency) registry. lookup(centeralRentalAgency) ;
				System.out.println("LOCAL centeralRentalAgency " + cra.getName() + " found.");
				
			}else {//REMOTE

				Registry registry = LocateRegistry.getRegistry("10.10.10.17");
				cra = (InterfaceCentralRentalAgency) registry. lookup(centeralRentalAgency) ;
				System.out.println("REMOTE centeralRentalAgency " + cra.getName() + " found.");
			}
			
		} catch (NotBoundException ex) {
			System.err.println("Could not find centeralRentalAgency with given name.");
		} catch (RemoteException ex) {
			System.err.println(ex.getMessage());
		}
		
	
	}
	
	
	@Override
	protected Set getBestClients(Object ms) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getCheapestCarType(Object session, Date start, Date end, String region) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CarType getMostPopularCarTypeInCRC(Object ms, String carRentalCompanyName, int year) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object getNewReservationSession(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object getNewManagerSession(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void checkForAvailableCarTypes(Object session, Date start, Date end) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addQuoteToSession(Object session, String name, Date start, Date end, String carType, String region)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List confirmQuotes(Object session, String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getNumberOfReservationsByRenter(Object ms, String clientName) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getNumberOfReservationsForCarType(Object ms, String carRentalName, String carType) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}



}
