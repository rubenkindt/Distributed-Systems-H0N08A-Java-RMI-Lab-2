package centralRentalAgency;

import java.io.Serializable;
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

public class ReservationSession implements Serializable{

	
	private static final long serialVersionUID = 1L;

	public String name;

	public static ArrayList<InterfaceCarRentalCompany> comp=null;
	protected List<Quote> quoteList=new ArrayList<Quote>();
	protected List<Reservation> resList=null;//new ArrayList<Reservation>();
	
	public ReservationSession(String Name,ArrayList<InterfaceCarRentalCompany> comp) {
		this.name=Name;
		ReservationSession.setComp(comp);
		
		System.out.println("AAAAAAAAAAAAAA");
		System.out.println(Integer.toString(ReservationSession.getComp().size()));
		System.out.println("BBBBBBBBBBBBBBBBBBBB");
		
	}
	
	public static ArrayList<InterfaceCarRentalCompany> getComp() {
		return ReservationSession.comp;
	}

	public static void setComp(ArrayList<InterfaceCarRentalCompany> comp) {
		ReservationSession.comp = comp;
	}

	public void checkAvailableCarTYpes(Date start, Date end) throws RemoteException{
		//following javaDoc: return void
		//List<InterfaceCarRentalCompany> companies=ReservationSession.getComp();
		
		System.out.println("name: "+this.name);
		System.out.println("CCCCCCCC");
		
		System.out.println(Integer.toString(ReservationSession.getComp().size()));
		System.out.println("DDDDDDDD");
		
		Set<CarType> s = new HashSet<CarType>();

		System.out.println("EEEEEEEE");
		
		for (int i=0;i<ReservationSession.getComp().size();i++) {
			Set<CarType> out=ReservationSession.getComp().get(i).getAvailableCarTypes(start, end);
			
			for (int j=0;j<out.size();j++) {
				s.add(out.iterator().next());
			}

		}
	}

	public void addQuoteToSession(String name, Date start, Date end, String carType, String region) throws Exception {

		ReservationConstraints constraint = new ReservationConstraints(end, end, region, region);
		List<Quote> q=new ArrayList<Quote>();
		for (int i=0;i<comp.size();i++) {
			try {
				q.add(comp.get(i).createQuote(constraint, name));
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
		List<Reservation> reservationList=new ArrayList<Reservation>();
		
		for (int quote=0;quote<quoteList.size();quote++) {
			String compName=quoteList.get(quote).getRentalCompany();
			for (int company=0;company<comp.size();company++) {
				if (compName==comp.get(company).getName()) {
					reservationList.add(comp.get(company).confirmQuote(quoteList.get(quote)));
					
				}
			}
		}
		this.resList=reservationList;
		return this.resList;
	}


	public List<Reservation> getResList() {
		return resList;
	}

	public String getCheapestCarType(Date start, Date end, String region) throws Exception {
		
		ReservationConstraints constraint = new ReservationConstraints(end, end, region, region);
		List<Quote> q=new ArrayList<Quote>();
		for (int company=0;company<comp.size();company++) {
			q.add(comp.get(company).createQuote(constraint, name));
		
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


