package movierest.service;

import java.util.Comparator;

public class MovieTitleComparator implements Comparator<Movie>{
	 
	@Override
	public int compare(Movie a, Movie b) {
		return (a.getTitle().compareTo(b.getTitle()) >= 0) ? 1 : -1;
	}
	
}
