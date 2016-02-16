package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import resources.CommandResource;
import resources.ResultsResource;
import services.TicketManagerService;
import spark.Spark;

public class RemoteCommanderMain {
	public static void main(String[] args) {
		Spark.port(2220);
		ExecutorService executorPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		TicketManagerService resultManager = new TicketManagerService(10, TimeUnit.SECONDS);
		
		new CommandResource(executorPool, resultManager);
		new ResultsResource(resultManager);
	}
}
