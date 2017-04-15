package movies;

public class MovieIdResult {

	private int id;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Movies.MovieId getProto() {
		return Movies.MovieId.newBuilder().setId(this.getId()).build();
	}
}
