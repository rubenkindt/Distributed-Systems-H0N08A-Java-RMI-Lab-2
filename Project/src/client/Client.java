package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;

import rental.CarType;
import rental.Reservation;
import centralRentalAgency.InterfaceCentralRentalAgency;
import centralRentalAgency.InterfaceManagerSession;
import centralRentalAgency.ManagerSession;
import centralRentalAgency.ReservationSession;


public class Client extends AbstractTestManagement<ReservationSession,ManagerSession> {

	/********
	 * MAIN * 
	 ********/

	private final static int LOCAL = 0;
	private final static int REMOTE = 1;
	
	private InterfaceCentralRentalAgency cra;

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
		Client client = new Client("Trips", centeralRentalAgency, localOrRemote);
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public Client(String scriptFile, String centeralRentalAgency, int localOrRemote) {
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
	/*********************
	 * manager functions *
	 ********************/
	
	@Override
	protected Set<String> getBestClients(ManagerSession ms) throws Exception {
		return  ms.getBestClients();
		
	}

	@Override
	protected String getCheapestCarType(ReservationSession session, Date start, Date end, String region) throws Exception {
		return session.getCheapestCarType( start, end, region);
	}

	@Override
	protected CarType getMostPopularCarTypeInCRC(ManagerSession ms, String carRentalCompanyName, int year) throws Exception {
		return ms.getMostPopularCarTypeInCRC(carRentalCompanyName,year);
	}

	
	/*******************
	 * client bookings *
	 ******************/
	
	
	@Override
	protected ReservationSession getNewReservationSession(String name) throws Exception {
		return (ReservationSession)cra.getNewReservationSession(name);
	}

	@Override
	protected ManagerSession getNewManagerSession(String name) throws Exception {
		return (ManagerSession)cra.getNewManagerSession(name);
	}

	@Override
	protected void checkForAvailableCarTypes(ReservationSession session, Date start, Date end) throws Exception {
		//System.out.println("00000000000000000000000");
		//System.out.println(Integer.toString(session.comp.size()));
		
		session.checkAvailableCarTYpes(start, end);
	}

	@Override
	protected void addQuoteToSession(ReservationSession session, String name, Date start, Date end, String carType, String region) throws Exception {
		session.addQuoteToSession(name, start, end, carType, region);
		
	}

	@Override
	protected List<Reservation> confirmQuotes(ReservationSession session, String name) throws Exception {
		List<Reservation> list = session.confirmQuotes(name);
		return list;
	}

	@Override
	protected int getNumberOfReservationsByRenter(ManagerSession ms, String clientName) throws Exception {
		return ms.getNumberOfReservationsByRenter(clientName);
	}

	@Override
	protected int getNumberOfReservationsForCarType(ManagerSession ms, String carRentalName, String carType) throws Exception {
		return ms.getNumberOfReservationsForCarType(carRentalName,carType);
	}

}