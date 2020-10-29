// Implemented by Ruben Kindt
package centralRentalAgency;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import rental.Car;
import rental.CarType;
import rental.InterfaceCarRentalCompany;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public class ReservationSession implements InterfaceReservationSession{

	public String name;

	public static ArrayList<InterfaceCarRentalCompany> comp=null;
	protected List<Quote> quoteList=new ArrayList<Quote>();
	protected List<Reservation> resList=new ArrayList<Reservation>();//new ArrayList<Reservation>();
	
	
	public ReservationSession(String Name,ArrayList<InterfaceCarRentalCompany> comp) throws RemoteException{
		this.name=Name;
		ReservationSession.setComp(comp);
	
	}
	
	@Override
	public String getName() throws RemoteException {
		return name;
	}

	
	public static ArrayList<InterfaceCarRentalCompany> getComp() {
		return ReservationSession.comp;
	}

	public static void setComp(ArrayList<InterfaceCarRentalCompany> comp) {
		ReservationSession.comp = comp;
	}

	public void checkAvailableCarTypes(Date start, Date end) throws RemoteException{

		Set<CarType> s = new HashSet<CarType>();
		
		for (int i=0;i<ReservationSession.getComp().size();i++) {
			Set<CarType> out=ReservationSession.getComp().get(i).getAvailableCarTypes(start, end);


			for (Iterator<CarType> iterator = out.iterator(); iterator.hasNext();) {
				System.out.println((String) iterator.next().toString());
			}

		}
	}

	public void addQuoteToSession(String name, Date start, Date end, String carType, String region) throws Exception{

		ReservationConstraints constraint = new ReservationConstraints(start, end, carType, region);
		List<Quote> q=new ArrayList<Quote>();
		for (int i=0;i<ReservationSession.getComp().size();i++) {
			try {
				q.add(ReservationSession.getComp().get(i).createQuote(constraint, name));
			} catch (ReservationException e) {
				
			}catch (IllegalArgumentException e) {
				
			}
		}
		for (int i=0;i<q.size();i++){
			if (q.get(i)!=null) {
				quoteList.add((Quote) q.get(i));
				return;	
			}
		}
		
		throw new Exception("Not able to create valid Quotes" );
	}

	public List<Reservation> confirmQuotes(String clientName) throws Exception {
		if (!this.getName().equals(clientName)) {
			throw new Exception("wrong client Name");
		}
		List<Reservation> reservationList=new ArrayList<Reservation>();
		
		boolean succes=true;
		for (int quote=0;quote<quoteList.size();quote++) {
			
			String compName=quoteList.get(quote).getRentalCompany();
			for (int company=0;company<comp.size();company++) {
				if (compName.equals(comp.get(company).getName())) {
					try {
						Reservation res=comp.get(company).confirmQuote(quoteList.get(quote));
						reservationList.add(res);	
					}
					catch (ReservationException e){
						succes=false;
					}
					
				}
			}
		}
		if (!succes) {
			InterfaceCarRentalCompany compa=null;
			for (int i=0;i<reservationList.size();i++) {
				String compaName=reservationList.get(i).getRentalCompany();
				for (int company=0;company<comp.size();company++) {
					if (comp.get(company).getName().equals(compaName)) {
						compa=comp.get(company);
						break;
					}
				}
				if (compa!=null) {
					compa.cancelReservation(reservationList.get(i));
				}
			}
			reservationList.clear();
		}
		
		this.resList.addAll(reservationList);
		return this.resList;
	}


	public List<Reservation> getResList() {
		return resList;
	}

	public String getCheapestCarType(Date start, Date end, String region) throws Exception {
		
		List<Quote> q=new ArrayList<Quote>();
		for (int j=0;j<ReservationSession.getComp().size();j++) {
			InterfaceCarRentalCompany compan=ReservationSession.getComp().get(j);
			Collection<CarType> possibleCarTypes= compan.getAllCarTypes();
			
			for (Iterator<CarType> iterator = possibleCarTypes.iterator(); iterator.hasNext();) {
				CarType carType = (CarType) iterator.next(); 
				
				try {
					ReservationConstraints constraint = new ReservationConstraints(start, end, carType.getName(), region);
					q.add(compan.createQuote(constraint, name));
				}
				catch(ReservationException e) {
					
				}
				
			}
		}	
		if (q.size()==0) {
			throw new Exception("No cars found, therefor not able to any Quotes" );
		}
		
		Quote cheap=q.get(0);
		for (int quo=0;quo<q.size();quo++) {
			if (q.get(quo)!=null){
				
				if (cheap.getRentalPrice()>q.get(quo).getRentalPrice()) {
					cheap=q.get(quo);
				}
			}
		}
		
		return cheap.getCarType();
	}


	

	




	
}


