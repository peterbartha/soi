package ticketing;

public class Ticket {
	
	private int movieId;
	private int count;
	
	public Ticket() { }
	
	public int getMovieId() {
		return this.movieId;
	}
	
	public void setMovieId(int id) {
		this.movieId = id;
	}

	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void addCount(int plus) {
		this.count += plus;
	}
	
	public Ticketing.Ticket getProto() {
		return Ticketing.Ticket.newBuilder().setMovieId(this.getMovieId()).setCount(this.getCount()).build();
	}
}
