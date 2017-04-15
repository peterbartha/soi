package movies;

import java.util.ArrayList;
import java.util.List;

import movies.Movies.Movie.Builder;

public class Movie {

	private int id;
	private boolean hasId;
	private String title;
	private int year;
	private String director;
	private String[] actor;

	public Movie() {
		this.hasId = false;
		this.actor = new String[] {};
	}
	
	public Movie(Movies.Movie movie) {
		this.hasId = false;
		this.setTitle(movie.getTitle());
		this.setYear(movie.getYear());
		this.setDirector(movie.getDirector());
		List<String> actors = new ArrayList<String>();
		for (int i = 0; i < movie.getActorCount(); i++) {
			String actor = movie.getActor(i);
			if (actor != "") {
				actors.add(actor);
			}
		}
		this.setActors(actors.toArray(new String[0]));
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
		this.hasId = true;
	}

	public boolean hasId() {
		return this.hasId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return this.director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String[] getActors() {
		return this.actor;
	}

	public void setActors(String[] actor) {
		this.actor = actor;
	}

	public Movies.Movie getProto() {
		Builder movieBuilder = Movies.Movie.newBuilder();
		movieBuilder.setTitle(this.getTitle()).setYear(this.getYear()).setDirector(this.getDirector());
		for (String actor: this.actor) {
			movieBuilder.addActor(actor);
		}
		return movieBuilder.build();
	}

}
