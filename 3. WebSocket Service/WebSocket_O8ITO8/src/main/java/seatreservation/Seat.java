package seatreservation;

public class Seat {

	private int row;
	private int column;
	private SeatStatus status;
	
	
	public Seat() {
		this.status = SeatStatus.FREE;
	}
	
	public Seat(int row, int column) {
		this.row = row;
		this.column = column;
		this.status = SeatStatus.FREE;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public SeatStatus getStatus() {
		return this.status;
	}
	
	public void setStatus(SeatStatus status) {
		this.status = status;
	}
	
	public boolean equals(Seat seat) {
		return this.row == seat.getRow() && this.column == seat.getColumn();
	}
	
}
