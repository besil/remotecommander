package services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import beans.CommandResult;
import beans.MessageResult;
import beans.PendingCommandResult;
import beans.Ticket;

public class TicketManagerService {
	private Map<Long, Ticket> pending;
	private Cache<Long, CommandResult> completed;
	private long ticketCount = 0;
	
	public TicketManagerService(int cacheTTL, TimeUnit timeUnit) {
		this.pending = new HashMap<>();
		
		this.completed = CacheBuilder.newBuilder()
			    .concurrencyLevel(Runtime.getRuntime().availableProcessors())
			    .weakKeys()
			    .maximumSize(10000)
			    .expireAfterWrite(cacheTTL, timeUnit)
			    .build();
	}
	
	public synchronized Ticket newTicket(String cmd) {
		Ticket t = new Ticket()
				.withCommand(cmd)
				.withTicketId(ticketCount++);
		pending.put(t.getTicketId(), t);
		return t;
	}

	public synchronized CommandResult get(Ticket ticket) {
		if( pending.containsKey(ticket.getTicketId()) )
			return new PendingCommandResult(ticket.getTicketId());
		
		CommandResult result = completed.getIfPresent(ticket.getTicketId());
		if( result != null )
			return result;
		
		return new MessageResult("Ticket "+ticket.getTicketId()+" not found");
		
	}

	public synchronized void close(Ticket ticket, CommandResult result) {
		pending.remove(ticket.getTicketId());
		completed.put(ticket.getTicketId(), result);
	}
	
	public synchronized CommandResult latest() {
		if( ticketCount == 0 )
			return new MessageResult("No ticket found");
		return this.get(new Ticket().withTicketId(ticketCount-1));
	}

}
