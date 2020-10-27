package centralRentalAgency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


import rental.Car;
import rental.CarRentalCompany;
import rental.CarType;
import rental.InterfaceCarRentalCompany;
import rental.Quote;
import rental.RentalServer;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;
import rental.RentalServer.CrcData;


public class CentralRentalAgencyServer {
	
	private final static int LOCAL = 0;
	private final static int REMOTE = 1;
	private static Logger logger = Logger.getLogger(CentralRentalAgencyServer.class.getName());
	//                     ^ looks like a CAPITAL 'L' but isn't

	
	public static void main(String[] args) throws ReservationException, NumberFormatException, IOException {
		// The first argument passed to the `main` method (if present)
		// indicates whether the application is run on the remote setup or not.
		int localOrRemote = (args.length == 1 && args[0].equals("REMOTE")) ? REMOTE : LOCAL;
		
		if (localOrRemote==LOCAL) {
			if (System.getSecurityManager() != null) {
				System.setSecurityManager(null);
			}
		}
		
		Registry reg=null;
		try {
			
			if (localOrRemote ==LOCAL){

				reg=LocateRegistry.getRegistry();
				System.out.println("LOCAL reg");
		
			}else {//REMOTE
				reg=LocateRegistry.getRegistry("10.10.10.17");
			}
			
		}
		catch(RemoteException e) {
			logger.log(Level.SEVERE, "Failed to locate RMI Reg");
			System.exit(-1);
			
		}
		
		//sets stubs of companies (herz and dockx)
		ArrayList<InterfaceCarRentalCompany> comp;
		//RentalServer companies =new RentalServer();
		comp=RentalServer.main(localOrRemote);
		
		InterfaceCentralRentalAgency carRentalAgency = new CentralRentalAgency("CRA",comp);
		
		//Car Rental Agency
		InterfaceCentralRentalAgency stub;
		try { 
			stub = (InterfaceCentralRentalAgency) UnicastRemoteObject.exportObject(carRentalAgency, 0);
			reg.rebind(carRentalAgency.getName(), stub);
			logger.log(Level.INFO, "<{0}> Car Rental Company fel is registered.", carRentalAgency.getName());
			
		} catch(RemoteException e) { 
			logger.log(Level.SEVERE, "<{0}> Could not get stub bound of Car Rental Company {0}.", carRentalAgency.getName());
			e.printStackTrace();
			System.exit(-1); 
			
		}
		
	}

		
}
