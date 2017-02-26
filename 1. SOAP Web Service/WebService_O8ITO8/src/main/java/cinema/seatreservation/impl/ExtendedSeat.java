package cinema.seatreservation.impl;

import cinema.seatreservation.Seat;
import cinema.seatreservation.SeatStatus;

public class ExtendedSeat extends Seat {

	private SeatStatus status;
	
	public ExtendedSeat(char row, int column) {
		super();
		setRow("" + row);
		setColumn("" + column);
		this.status = SeatStatus.FREE;
	}
	
	public SeatStatus getStatus() {
		return status;
	}
	
	public void setStatus(SeatStatus status) {
		this.status = status;
	}
	
	public boolean equals(Seat seat) {
		return getRow().equals(seat.getRow()) && getColumn().equals(seat.getColumn());
	}
}
