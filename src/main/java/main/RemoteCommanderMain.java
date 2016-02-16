package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import resources.CommandResource;
import resources.ResultsResource;
import services.TicketManagerService;
import spark.Spark;

public class RemoteCommanderMain {
	@Parameter(names="-ttl")
	int ttl = 60;
	@Parameter(names="-port")
	int port = 2220;
	@Parameter(names={"-threads", "-t"})
	int threads = Runtime.getRuntime().availableProcessors();
	
	public static void main(String[] args) {
		RemoteCommanderMain main = new RemoteCommanderMain();
		new JCommander(main, args);
		main.run();
	}
	
	public void run() {
		Spark.port(2220);
		ExecutorService executorPool = Executors.newFixedThreadPool(threads);
		TicketManagerService resultManager = new TicketManagerService(ttl, TimeUnit.MINUTES);
		
		new CommandResource(executorPool, resultManager);
		new ResultsResource(resultManager);
	}
}
