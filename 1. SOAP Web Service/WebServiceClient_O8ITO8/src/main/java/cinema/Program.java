package cinema;

import javax.xml.ws.BindingProvider;

import cinema.seatreservation.CinemaService;
import cinema.seatreservation.ICinema;
import cinema.seatreservation.Seat;
import cinema.seatreservation.ICinemaBuyCinemaException;
import cinema.seatreservation.ICinemaLockCinemaException;
import cinema.seatreservation.ICinemaReserveCinemaException;


public class Program {
	
	public static void main(String[] args) {
		String url = args[0];
		String row = args[1];
		String column = args[2];
		String task = args[3];
		
		CinemaService factory = new CinemaService();
		ICinema client = factory.getICinemaHttpSoap11Port();
		BindingProvider binding = (BindingProvider) client;
		binding.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
		
		try {
			String lockId;
			Seat seat = new Seat();
			seat.setRow(row);
			seat.setColumn(column);
			
			switch(task) {
				case "Lock": 
					lockId = client.lock(seat, 1);
					System.out.println("Seat locked.");
					break;
					
				case "Reserve": 
					lockId = client.lock(seat, 1);
					client.reserve(lockId);
					System.out.println("Seat reserved.");
					break;
					
				case "Buy": 
					lockId = client.lock(seat, 1);
					client.buy(lockId);
					System.out.println("Seat bought.");
					break;
			}
		} catch (ICinemaLockCinemaException e) {
			e.printStackTrace();
		} catch (ICinemaReserveCinemaException e) {
			e.printStackTrace();
		} catch (ICinemaBuyCinemaException e) {
			e.printStackTrace();
		}
		
	}
	
}