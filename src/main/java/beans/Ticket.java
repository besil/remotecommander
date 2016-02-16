package beans;

public class Ticket {
	String command;
	long ticketId;
	
	public Ticket withCommand(String cmd) {
		this.command = cmd;
		return this;
	}
	
	public Ticket withTicketId(long t) {
		this.ticketId = t;
		return this;
	}
	
	public String getCommand() {
		return command;
	}public long getTicketId() {
		return ticketId;
	}
}
