package cinema.seatreservation.impl;

import cinema.seatreservation.Lock;
import cinema.seatreservation.Seat;

public class ExtendedLock extends Lock {
	
	private String id;
	
	public ExtendedLock(Seat seat, int count) {
		super();
		setSeat(seat);
		setCount(count);
		this.id = seat.getRow() + ':' + seat.getColumn() + ':' + getCount();
	}
	
	public String getId() {
		return id;
	}

}
