package movies;

import java.util.Comparator;

public class MovieDirectorComparator implements Comparator<Movie> {
	 
	@Override
	public int compare(Movie a, Movie b) {
		return (a.getDirector().compareTo(b.getDirector()) >= 0) ? 1 : -1;
	}
	
}
