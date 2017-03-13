package movierest.service;

import java.util.Comparator;

public class MovieDirectorComparator implements Comparator<Movie>{
	 
	@Override
	public int compare(Movie a, Movie b) {
		return (a.getTitle().compareTo(b.getDirector()) >= 0) ? 1 : -1;
	}
	
}
