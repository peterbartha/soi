package movies;

import javax.ws.rs.core.Response;

public class MovieDatabase implements IMovieDatabase {

	private static MovieList movieList;

	public MovieDatabase() {
		if (movieList == null) {
			movieList = new MovieList();
		}
	}
	
	@Override
	public Movies.MovieList getAllMovies() {
		return movieList.getProto();
	}

	@Override
	public Response getMovieById(int id) {
		Movie movie = movieList.getMovieById(id);
		if (movie == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(movie.getProto()).build();
	}

	@Override
	public Movies.MovieId insertMovie(Movies.Movie movie) {
		Movie m = new Movie(movie);
		MovieIdResult result = movieList.addMovie(m);
		return result.getProto();
	}

	@Override
	public void updateOrInsertMovie(int id, Movies.Movie movie) {
		Movie m = new Movie(movie);
		m.setId(id);
		if (movieList.getMovieById(id) == null) {
			movieList.addMovie(m);
		} else {
			movieList.modifyMovie(m);
		}
	}

	@Override
	public void deleteMovie(int id) {
		if (movieList.getMovieById(id) != null) {
			movieList.removeMovieById(id);
		}
	}

	@Override
	public Response getMovieIds(int year, String field) {
		if (field.equals("Title") || field.equals("Director")) {
			MovieIdList idList = movieList.getMoviesByYear(year, field);
			return Response.ok(idList.getProto()).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

}
