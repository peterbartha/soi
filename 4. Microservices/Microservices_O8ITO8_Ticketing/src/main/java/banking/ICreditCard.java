package banking;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/banking")
public interface ICreditCard {

	@POST
	@Path("ChargeCard")
	@Consumes({"application/x-protobuf", MediaType.APPLICATION_JSON })
	@Produces({"application/x-protobuf", MediaType.APPLICATION_JSON })
	public Banking.ChargeCardResponse charge(Banking.ChargeCardRequest creditCard);
	
}
