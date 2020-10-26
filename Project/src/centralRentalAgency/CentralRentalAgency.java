package centralRentalAgency;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

import rental.Car;
import rental.CarType;
import rental.InterfaceCarRentalCompany;
import rental.Quote;
import rental.RentalServer;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

import javax.swing.Spring;

import client.Client;

public class CentralRentalAgency implements InterfaceCentralRentalAgency{

	private static Logger logger = Logger.getLogger(CentralRentalAgency.class.getName());
	
	private List<InterfaceCarRentalCompany> crc; //crc=car rental company
	private String name;
	private Map<String, ReservationSession> resSessions = new HashMap<String, ReservationSession>();
	//private Map<String, ManagerSession> mSessions = new HashMap<String, ManagerSession>();
	// only one Manager
	private ManagerSession mSession= null;

	private static List<InterfaceCarRentalCompany> comp;
	
	/***************
	 * CONSTRUCTOR 
	 ***************/

	public CentralRentalAgency(String name,List<InterfaceCarRentalCompany> comp) {
		logger.log(Level.INFO, "<{0}> Car Rental Company {0} starting up...", name);
		setName(name);
		logger.log(Level.INFO, this.toString());
		this.comp=comp;
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

    /***********
     * Regions *
     **********/
    private List<InterfaceCarRentalCompany> getCrc() {
        return this.crc;
    }

	
	@Override
	public ReservationSession getNewReservationSession(String name) {
		ReservationSession resSession = new ReservationSession(name, comp);
		resSessions.put(name, resSession);
		if (mSession!=null) {
			mSession.addClient(resSession);
		}
		return resSession;
	}

	@Override
	public void removeReservationSession(String name) {
		resSessions.remove(name);
		if (mSession!=null) {
			mSession.removeClient(name);
		}
	}

	
	@Override
	public ManagerSession getNewManagerSession(String name) {
		List<ReservationSession> clients = new ArrayList<ReservationSession>(resSessions.values());
		this.mSession = new ManagerSession(name,comp,clients);
		return this.mSession ;
	}

	@Override
	public void removeManagerSession(String name) {
		this.mSession=null;
	}
	
	
	
}