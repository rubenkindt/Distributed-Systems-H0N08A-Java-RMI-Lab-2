package rental;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
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

public class RentalServer {
	
	private final static int LOCAL = 0;
	private final static int REMOTE = 1;
	private static Logger logger = Logger.getLogger(RentalServer.class.getName());
	//                     ^ looks like a CAPITAL 'L' but isn't
	

	public static List<InterfaceCarRentalCompany> main(int localOrRemote) throws ReservationException, NumberFormatException, IOException {
				
		if (localOrRemote==REMOTE) {
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
		
		List<InterfaceCarRentalCompany> comp= new ArrayList<InterfaceCarRentalCompany>();
		
		CrcData data  = loadData("hertz.csv");
		InterfaceCarRentalCompany hertzLocal = link(reg,data);		
		comp.add(hertzLocal);
		
		CrcData dataDockx  = loadData("dockx.csv");
		InterfaceCarRentalCompany dockx = link(reg,dataDockx);	
		comp.add(dockx);
		
		return comp;
		
	}
	
	private static InterfaceCarRentalCompany link(Registry reg,CrcData data) throws RemoteException {
		InterfaceCarRentalCompany company= new CarRentalCompany(data.name, data.regions, data.cars);

		InterfaceCarRentalCompany stub;
		try { 
			stub = (InterfaceCarRentalCompany) UnicastRemoteObject.exportObject(company, 0);
			reg.rebind(company.getName(), stub);
			logger.log(Level.INFO, "<{0}> Car Rental Company fel is registered.", company.getName());
			
		} catch(RemoteException e) { 
			logger.log(Level.SEVERE, "<{0}> Could not get stub bound of Car Rental Company {0}.", company.getName());
			e.printStackTrace();
			System.exit(-1); 
			
		}
		return company;
	}

	public static CrcData loadData(String datafile)
			throws ReservationException, NumberFormatException, IOException {

		CrcData out = new CrcData();
		int nextuid = 0;

		// open file
		InputStream stream = MethodHandles.lookup().lookupClass().getClassLoader().getResourceAsStream(datafile);
		if (stream == null) {
			System.err.println("Could not find data file " + datafile);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		StringTokenizer csvReader;
		
		try {
			// while next line exists
			while (in.ready()) {
				String line = in.readLine();
				
				if (line.startsWith("#")) {
					// comment -> skip					
				} else if (line.startsWith("-")) {
					csvReader = new StringTokenizer(line.substring(1), ",");
					out.name = csvReader.nextToken();
					out.regions = Arrays.asList(csvReader.nextToken().split(":"));
				} else {
					// tokenize on ,
					csvReader = new StringTokenizer(line, ",");
					// create new car type from first 5 fields
					CarType type = new CarType(csvReader.nextToken(),
							Integer.parseInt(csvReader.nextToken()),
							Float.parseFloat(csvReader.nextToken()),
							Double.parseDouble(csvReader.nextToken()),
							Boolean.parseBoolean(csvReader.nextToken()));
					System.out.println(type);
					// create N new cars with given type, where N is the 5th field
					for (int i = Integer.parseInt(csvReader.nextToken()); i > 0; i--) {
						out.cars.add(new Car(nextuid++, type));
					}
				}
			}
		} finally {
			in.close();
		}

		return out;
	}
	
	public static class CrcData {//
		public List<Car> cars = new LinkedList<Car>();
		public String name;
		public List<String> regions =  new LinkedList<String>();
	}

}
