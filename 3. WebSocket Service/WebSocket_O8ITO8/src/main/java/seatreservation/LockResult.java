package seatreservation;

public class LockResult {
	
	private int row;
	private int column;
	private String id;
	private static int counter = 0;
	
	
	public LockResult(int row, int column) {
		this.row = row;
		this.column = column;
		this.id = "lock" + counter;
		counter++;
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
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean equals(LockResult lock) {
		return this.row == lock.getRow() && this.column == lock.getColumn();
	}
	
}
