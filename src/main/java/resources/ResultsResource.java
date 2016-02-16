package resources;

import beans.Ticket;
import services.TicketManagerService;
import spark.Spark;
import utils.JsonTransformer;

public class ResultsResource {
	private static final String API_CONTEXT = "/result";
	private TicketManagerService ticketManager;

	public ResultsResource(TicketManagerService ticketManager) {
		this.ticketManager = ticketManager;
		setUpEndpoints();
	}

	private void setUpEndpoints() {
		Spark.get(API_CONTEXT+"/latest", (req, res) -> {
			return ticketManager.latest();
		}, new JsonTransformer());
		
		Spark.get(API_CONTEXT+"/:ticket", (req, res) -> {
			long ticketId = Long.parseLong(req.params(":ticket"));
			Ticket ticket = new Ticket().withTicketId(ticketId);
			return ticketManager.get(ticket);
		}, new JsonTransformer());
	}
	
}
