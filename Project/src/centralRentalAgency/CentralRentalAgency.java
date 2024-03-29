// Implemented by Ruben Kindt
package centralRentalAgency;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


import rental.InterfaceCarRentalCompany;

import javax.swing.Spring;


public class CentralRentalAgency implements InterfaceCentralRentalAgency{

	private static Logger logger = Logger.getLogger(CentralRentalAgency.class.getName());
	
	private String name;
	private Map<String, InterfaceReservationSession> resSessions = new HashMap<String, InterfaceReservationSession>();
	//private Map<String, ManagerSession> mSessions = new HashMap<String, ManagerSession>();
	// only one Manager
	private InterfaceManagerSession mSession= null;

	public static ArrayList<InterfaceCarRentalCompany> comp;
	
	/***************
	 * CONSTRUCTOR 
	 ***************/

	public CentralRentalAgency(String name,ArrayList<InterfaceCarRentalCompany> comp) {
		logger.log(Level.INFO, "<{0}> Car Rental Company {0} starting up...", name);
		setName(name);
		logger.log(Level.INFO, this.toString());
		CentralRentalAgency.comp=comp;
	}

	/********
	 * NAME *
	 ********/

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

    @Override
	public InterfaceReservationSession getNewReservationSession(String name) throws RemoteException {
		InterfaceReservationSession resSession = new ReservationSession(name, comp);
		
		InterfaceReservationSession stub = (InterfaceReservationSession) UnicastRemoteObject.exportObject(resSession,0);
		
		resSessions.put(name, resSession);
		if (mSession!=null) {
			mSession.addClient(resSession);
		}
		return stub;
	}

	@Override
	public void removeReservationSession(String name) throws RemoteException {
		resSessions.remove(name);
		if (mSession!=null) {
			mSession.removeClient(name);
		}
	}

	
	@Override
	public InterfaceManagerSession getNewManagerSession(String name) throws RemoteException {
		
		List<InterfaceReservationSession> clients = new ArrayList<InterfaceReservationSession>(resSessions.values());

		mSession = new ManagerSession(name,comp,clients);
		
		InterfaceManagerSession stub = (InterfaceManagerSession) UnicastRemoteObject.exportObject(mSession,0);
		
		return stub ;
	}

	@Override
	public void removeManagerSession() {
		this.mSession=null;
	}
	
	
	
}