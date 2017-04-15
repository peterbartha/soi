package movies;

import java.util.ArrayList;
import java.util.List;

public class MovieIdList {
	
	private List<Integer> ids;
	
	public MovieIdList() {
		ids = new ArrayList<Integer>();
	}
	
	public void add(int id) {
		ids.add(new Integer(id));
	}
	
	public Movies.MovieIdList getProto() {
		return Movies.MovieIdList.newBuilder().addAllId(this.ids).build();
	}
}
