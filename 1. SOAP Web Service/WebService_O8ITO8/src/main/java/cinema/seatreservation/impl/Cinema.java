package cinema.seatreservation.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

import cinema.seatreservation.ArrayOfSeat;
import cinema.seatreservation.CinemaException;
import cinema.seatreservation.ICinema;
import cinema.seatreservation.ICinemaBuyCinemaException;
import cinema.seatreservation.ICinemaGetAllSeatsCinemaException;
import cinema.seatreservation.ICinemaGetSeatStatusCinemaException;
import cinema.seatreservation.ICinemaInitCinemaException;
import cinema.seatreservation.ICinemaLockCinemaException;
import cinema.seatreservation.ICinemaReserveCinemaException;
import cinema.seatreservation.ICinemaUnlockCinemaException;
import cinema.seatreservation.Seat;
import cinema.seatreservation.SeatStatus;


@WebService(
		name = "Cinema",
		portName = "ICinema_HttpSoap11_Port",
		targetNamespace = "http://www.iit.bme.hu/soi/hw/SeatReservation",
		endpointInterface = "cinema.seatreservation.ICinema",
		wsdlLocation = "WEB-INF/wsdl/SeatReservation.wsdl")
public class Cinema implements ICinema {

	private static List<ExtendedSeat> seats;
	private static List<ExtendedLock> locks;
	
	private boolean initialized = false;
	private int rowCount;
	private int columnCount;
	
	@Override
	public void init(int rows, int columns) throws ICinemaInitCinemaException {
		System.out.println("Initializing cinema with " + rows + " rows and " + columns + " columns of seats.");
		locks = new ArrayList<ExtendedLock>();
		buildSeats(rows, columns);
		initialized = true;
		printSeats();
	}

	@Override
	public ArrayOfSeat getAllSeats() throws ICinemaGetAllSeatsCinemaException {
		if (!initialized) {
			return new ArrayOfSeat();
		}
		return getSeatsAsArrayOfSeat();
	}

	@Override
	public SeatStatus getSeatStatus(Seat seat) throws ICinemaGetSeatStatusCinemaException {
		if (!initialized) {
			throw new ICinemaGetSeatStatusCinemaException("Cinema is not initialized yet.", null);
		}
		
		System.out.println("Retrieving status of seat [" + seat.getRow() + "," + seat.getColumn() + "].");
		ExtendedSeat extSeat = getExtendedSeat(seat);
		
		if (extSeat == null) {
			String message = "Seat not found.";
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaGetSeatStatusCinemaException(message, exception);
		}
		return extSeat.getStatus();
	}

	@Override
	public String lock(Seat seat, int count) throws ICinemaLockCinemaException {
		if (!initialized) {
			throw new ICinemaLockCinemaException("Cinema is not initialized yet.", null);
		}
		
		if (count < 1) {
			throw new ICinemaLockCinemaException("Number of seats to be locked has to be greater than 0.", null);
		}
		
		ExtendedSeat extSeat = getExtendedSeat(seat);
		if (extSeat == null) {
			String message = "Seat not found.";
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaLockCinemaException(message, exception);
		}
		
		if (Integer.parseInt(extSeat.getColumn()) + count - 1 > columnCount) {
			String message = "Not enough seats in the row.";
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaLockCinemaException(message, exception);
		}
		
		if (!checkSeatsStatus(seat, count, SeatStatus.FREE)) {
			String message = "There is at least one seat of the lock request that is not free.";
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaLockCinemaException(message, exception);
		}
		
		System.out.println("Locking " + count + " seat(s) starting from seat " + seat.getRow() + ":" + seat.getColumn());
		setSeatsStatuses(seat, count, SeatStatus.LOCKED);
		
		ExtendedLock lock = new ExtendedLock(seat, count); 
		locks.add(lock);
		String lockId = lock.getId();
		System.out.println("Seats locked, ID: " + lockId);
		
		printSeats();
		
		return lockId;
	}

	@Override
	public void unlock(String lockId) throws ICinemaUnlockCinemaException {
		if (!initialized) {
			throw new ICinemaUnlockCinemaException("Cinema is not initialized yet.", null);
		}
		
		ExtendedLock lock = findLock(lockId);
		if (lock == null) {
			String message = "No lock found with the following ID: " + lockId;
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaUnlockCinemaException(message, exception);
		}
		
		if (!checkSeatsStatus(lock.getSeat(), lock.getCount(), SeatStatus.LOCKED)) {
			String message = "There is at least one seat of the request that is not locked.";
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaUnlockCinemaException(message, exception);
		};
		
		System.out.println("Removing " + lockId + " lock...");
		setSeatsStatuses(lock.getSeat(), lock.getCount(), SeatStatus.FREE);
		System.out.println("Lock removed.");
		
		printSeats();
	}

	@Override
	public void reserve(String lockId) throws ICinemaReserveCinemaException {
		if (!initialized) {
			throw new ICinemaReserveCinemaException("Cinema is not initialized yet.", null);
		}
		
		ExtendedLock lock = findLock(lockId);
		if (lock == null) {
			String message = "No lock found with the following ID: " + lockId;
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaReserveCinemaException(message, exception);
		}
		
		if (!checkSeatsStatus(lock.getSeat(), lock.getCount(), SeatStatus.LOCKED)) {
			String message = "There is at least one seat of the reserve request that is not locked.";
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaReserveCinemaException(message, exception);
		};
		
		System.out.println("Reserving seats of lock with the following ID: " + lockId);
		setSeatsStatuses(lock.getSeat(), lock.getCount(), SeatStatus.RESERVED);
		System.out.println("Seats reserved.");
		
		printSeats();
	}

	@Override
	public void buy(String lockId) throws ICinemaBuyCinemaException {
		if (!initialized) {
			throw new ICinemaBuyCinemaException("Cinema is not initialized yet.", null);
		}
		
		ExtendedLock lock = findLock(lockId);
		if (lock == null) {
			String message = "No lock found with the following ID: " + lockId;
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaBuyCinemaException(message, exception);
		}
		
		System.out.println("Selling seats of " + lockId + " lock...");
		setSeatsStatuses(lock.getSeat(), lock.getCount(), SeatStatus.SOLD);
		System.out.println("Seats sold.");
		
		printSeats();
	}

	private void buildSeats(int rows, int columns) throws ICinemaInitCinemaException {
		seats = new ArrayList<ExtendedSeat>();
		if (rows < 1 || rows > 26) {
			String message = "Number of rows out of bound.";
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaInitCinemaException(message, exception);
		}
		
		if (columns < 1 || columns > 100) {
			String message = "Number of columns out of bound.";
			CinemaException exception = new CinemaException();
			exception.setErrorMessage(message);
			throw new ICinemaInitCinemaException(message, exception);
		}
		
		char rowLetter = 'A';
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				ExtendedSeat seat = new ExtendedSeat(rowLetter, j + 1);
				seats.add(seat);
			}
			rowLetter++;
		}
		
		rowCount = rows;
		columnCount = columns;
	}

	
	private void printSeats() {
		char rowChar = 'A';
		for (int i = 0; i < rowCount; i++) {
			String rowText = "" + rowChar + ":";
			for (int j = 0; j < columnCount; j++) {
				ExtendedSeat temp = new ExtendedSeat(rowChar, j + 1);
				rowText += " " + getExtendedSeat(temp).getStatus().value().charAt(0);
			}
			System.out.println(rowText);
			rowChar++;
		}
		
	}

	private ArrayOfSeat getSeatsAsArrayOfSeat() {
		ArrayOfSeat arrayOfSeat = new ArrayOfSeat();
		for (ExtendedSeat seat: seats) {
			arrayOfSeat.getSeat().add(seat);
		}
		return arrayOfSeat;
	}
	
	private ExtendedSeat getExtendedSeat(Seat seat) {
		for (ExtendedSeat extSeat: seats) {
			if (extSeat.equals(seat)) {
				return extSeat;
			}
		}
		return null;
	}

	private ExtendedLock findLock(String lockId) {
		for (ExtendedLock lock: locks) {
			if (lock.getId().equals(lockId)) {
				return lock;
			}
		}
		return null;
	}
	
	private boolean checkSeatsStatus(Seat seat, int count, SeatStatus statusToCheck) {
		Iterator<ExtendedSeat> seatIterator = seats.iterator();
		ExtendedSeat currentSeat = seatIterator.next();
		
		while (seatIterator.hasNext() && !currentSeat.equals(seat)) {
			currentSeat = seatIterator.next();
		}
		
		for (int i = 0; i < count; i++) {
			if (currentSeat.getStatus() != statusToCheck) {
				return false;
			}
			if (seatIterator.hasNext()) {
				currentSeat = seatIterator.next();
			}
		}
		
		return true;
	}
	
	private void setSeatsStatuses(Seat seat, int count, SeatStatus newStatus) {
		Iterator<ExtendedSeat> seatIterator = seats.iterator();
		ExtendedSeat currentSeat = seatIterator.next();
		
		while (seatIterator.hasNext() && !currentSeat.equals(seat)) {
			currentSeat = seatIterator.next();
		}
		
		for (int i = 0; i < count; i++) {
			System.out.println("Setting status of seat "+ currentSeat.getRow() + ":" + currentSeat.getColumn() + " to " + newStatus.value() + "...");
			currentSeat.setStatus(newStatus);
			if (seatIterator.hasNext()) {
				currentSeat = seatIterator.next();
			}
		}
	}
}
