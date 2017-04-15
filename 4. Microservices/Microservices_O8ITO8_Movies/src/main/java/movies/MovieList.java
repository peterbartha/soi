package movies;

import java.util.ArrayList;
import java.util.List;

import movies.Movies.MovieList.Builder;


public class MovieList {

	private List<Movie> movies;
	
	public MovieList() {
		this.movies = new ArrayList<Movie>();
	}
	
	public MovieIdResult addMovie(Movie movie) {
		if (!movie.hasId()) {
			movie.setId(this.generateMovieId());
		}
		this.movies.add(movie);
		
		MovieIdResult result = new MovieIdResult();
		result.setId(movie.getId());
		return result;
	}
	
	public Movie getMovieById(int id) {
		for (Movie movie: this.movies) {
			if (movie.getId() == id) {
				return movie;
			}
		}
		return null;
	}
	
	public MovieIdList getMoviesByYear(int year, String field) {
		MovieIdList idList = new MovieIdList();
		List<Movie> moviesOfYear = new ArrayList<Movie>();
		
		for (Movie movie: this.movies) {
			if (movie.getYear() == year) {
				moviesOfYear.add(movie);
			}
		}
		
		switch (field) {
			case "Title":
				MovieTitleComparator titleComp = new MovieTitleComparator();
				moviesOfYear.sort(titleComp);
				break;
				
			case "Director":
				MovieDirectorComparator directorComp = new MovieDirectorComparator();
				moviesOfYear.sort(directorComp);
				break;
		}
		
		for (Movie movie: moviesOfYear) {
			idList.add(movie.getId());
		}
		
		return idList;
	}
	
	public void modifyMovie(Movie newMovie) {
		int i = 0;
		for (Movie movie: this.movies) {
			if (movie.getId() == newMovie.getId()) {
				this.movies.set(i, newMovie);
				break;
			}
			i++;
		}
	}
	
	public void removeMovieById(int id) {
		this.movies.remove(getMovieById(id));
	}

	private int generateMovieId() {
		if (this.movies.size() == 0) {
			return 0;
		}
		
		int id = 0;
		for (Movie movie: this.movies) {
			int currentId = movie.getId();
			if (currentId >= id) {
				id = currentId + 1;
			}
		}
		return id;
	}
	
	public Movies.MovieList getProto() {
		Builder movieListBuilder = Movies.MovieList.newBuilder();
		
		for (Movie movie: this.movies) {
			Movies.Movie m = movie.getProto();
			movieListBuilder.addMovie(m);
		}
		
		return movieListBuilder.build();
	}
}
