package resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;

import beans.CommandResult;
import beans.Ticket;
import services.TicketManagerService;
import spark.Spark;
import utils.JsonTransformer;

public class CommandResource {
	private static final String API_CONTEXT = "/command";
	private static final String CMD_PARAM = "command";
	private final ExecutorService executor;
	private TicketManagerService ticketManager;
	
	public CommandResource(ExecutorService executorService, TicketManagerService resultManager) {
		this.executor = executorService;
		this.ticketManager = resultManager;
		setupEndpoints();
	}

	private void setupEndpoints() {
		Spark.post(API_CONTEXT+"/submit", (req, res) -> {
			String cmd = req.queryParams(CMD_PARAM);			
			Ticket ticket = ticketManager.newTicket(cmd);
			
			System.out.println("New Ticket: "+ticket.getTicketId()+". Cmd: "+ticket.getCommand());
			
			executor.submit(new Worker(ticket));
			return ticket;

		} , new JsonTransformer());
	}
	
	class Worker implements Runnable {
		private final Ticket ticket;
		
		public Worker( Ticket ticket ) {
			this.ticket = ticket;
		}
		
		@Override
		public void run() {
			CommandResult res = executeCommand(ticket.getCommand());
			ticketManager.close(ticket, res);
		}
		
		private String consumeStream(InputStream in) {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return sb.toString();
		}
		
		private CommandResult executeCommand(String command) {
			CommandResult cmdResult = new CommandResult();
			Process p;
			
			try {
				p = Runtime.getRuntime().exec(command);
				int exitVal = p.waitFor();
				String stdout = consumeStream(p.getInputStream());
				String stderr = consumeStream(p.getErrorStream());
				
				cmdResult
					.withStdout(stdout)
					.withStderr(stderr)
					.withExitStatus(exitVal)
					.withCommand(command)
				;
			} catch (Exception e) {
				cmdResult
					.withStackTrace( e.toString() )
				;
				e.printStackTrace();
			}
			return cmdResult;
		}
	}
}
