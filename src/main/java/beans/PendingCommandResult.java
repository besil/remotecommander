package beans;

public class PendingCommandResult extends MessageResult {

	public PendingCommandResult(long ticket) {
		super("Job pending. Ticket: "+ticket);
	}
	
}
