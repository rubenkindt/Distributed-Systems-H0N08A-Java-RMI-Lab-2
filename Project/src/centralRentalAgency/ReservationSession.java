package centralRentalAgency;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.InterfaceCarRentalCompany;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public class ReservationSession {
	
	public String name;

	private static List<InterfaceCarRentalCompany> comp;
	private List<Quote> quoteList=new ArrayList<Quote>();
	
	public ReservationSession(String Name,List<InterfaceCarRentalCompany> comp) {
		this.name=Name;
		ReservationSession.setComp(comp);
	}
	
	public static List<InterfaceCarRentalCompany> getComp() {
		return comp;
	}


	public static void setComp(List<InterfaceCarRentalCompany> comp) {
		ReservationSession.comp = comp;
	}

	public void checkAvailableCarTYpes(Date start, Date end) throws RemoteException{
		//following javaDoc: return void
		Set<CarType> set=new HashSet<CarType>();
		
		for (int i=0;i<getComp().size();i++) {
			set.addAll(getComp().get(i).getAvailableCarTypes(start, end));
			
		}
	}

	public void addQuoteToSession(String name, Date start, Date end, String carType, String region) throws Exception {

		ReservationConstraints constraint = new ReservationConstraints(end, end, region, region);
		List<Quote> q=new ArrayList<Quote>();
		for (int i=0;i<getComp().size();i++) {
			try {
				q.add(getComp().get(i).createQuote(constraint, name));
			} catch (ReservationException e) {
				
			}
			if (q.get(i)!=null){
				quoteList.add((Quote) q);
				return;
			}
		}
		
		throw new Exception("Not able to create valid Quotes" );
	}


	public List<Reservation> confirmQuotes(String name2) throws RemoteException, ReservationException {
		
		List<Reservation> resList=new ArrayList<Reservation>();
		
		for (int quote=0;quote<quoteList.size();quote++) {
			String compName=quoteList.get(quote).getRentalCompany();
			for (int company=0;company<getComp().size();company++) {
				if (compName==getComp().get(company).getName()) {
					resList.add(getComp().get(company).confirmQuote(quoteList.get(quote)));
					break;
				}
			}
		}
		
		return resList;
	}


	public String getCheapestCarType(Date start, Date end, String region) throws Exception {
		
		ReservationConstraints constraint = new ReservationConstraints(end, end, region, region);
		List<Quote> q=new ArrayList<Quote>();
		for (int company=0;company<getComp().size();company++) {
			q.add(getComp().get(company).createQuote(constraint, name));
		
		}
		Quote cheap=q.get(0);
		for (int quo=1;quo<q.size();quo++) {
			if (q.get(quo)!=null){
				
				if (cheap.getRentalPrice()>q.get(quo).getRentalPrice()) {
					cheap=q.get(quo);
				}
			}
		}
		
		return cheap.getCarType();
	}


	
}


