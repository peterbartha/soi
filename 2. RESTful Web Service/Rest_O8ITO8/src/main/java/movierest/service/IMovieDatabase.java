package movierest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("MovieDatabase")
public interface IMovieDatabase {

	@GET
	@Path("movies")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public MovieList getAllMovies();
	
	@GET
	@Path("movies/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getMovieById(@PathParam("id") int id);
	
	@POST
	@Path("movies")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public MovieIdResult insertMovie(Movie movie);
	
	@PUT
	@Path("movies/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void updateOrInsertMovie(@PathParam("id") int id, Movie movie);
	
	@DELETE
	@Path("movies/{id}")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public void deleteMovie(@PathParam("id") int id);
	
	@GET
	@Path("movies/find")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getMovieIds(@QueryParam("year") int year, @QueryParam("orderby") String field);
	
}
