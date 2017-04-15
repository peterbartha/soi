package movies;

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

@Path("/")
public interface IMovieDatabase {

	@GET
	@Path("movies")
	@Produces({"application/x-protobuf", MediaType.APPLICATION_JSON })
	public Movies.MovieList getAllMovies();
	
	@GET
	@Path("movies/{id}")
	@Produces({"application/x-protobuf", MediaType.APPLICATION_JSON })
	public Response getMovieById(@PathParam("id") int id);
	
	@POST
	@Path("movies")
	@Consumes({"application/x-protobuf", MediaType.APPLICATION_JSON })
	@Produces({"application/x-protobuf", MediaType.APPLICATION_JSON })
	public Movies.MovieId insertMovie(Movies.Movie movie);
	
	@PUT
	@Path("movies/{id}")
	@Consumes({"application/x-protobuf", MediaType.APPLICATION_JSON })
	public void updateOrInsertMovie(@PathParam("id") int id, Movies.Movie movie);
	
	@DELETE
	@Consumes({"application/x-protobuf", MediaType.APPLICATION_JSON })
	@Path("movies/{id}")
	public void deleteMovie(@PathParam("id") int id);
	
	@GET
	@Path("movies/find")
	@Produces({"application/x-protobuf", MediaType.APPLICATION_JSON })
	public Response getMovieIds(@QueryParam("year") int year, @QueryParam("orderby") String field);
	
}
