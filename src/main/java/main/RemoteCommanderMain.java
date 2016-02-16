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
	@Parameter(names = "-ttl", description = "Number of minutes before results expire")
	int ttl = 60;
	@Parameter(names = "-port", description = "Port where to bind the server")
	int port = 2220;
	@Parameter(names = { "-threads", "-t" }, description = "Number of threads to uses")
	int threads = Runtime.getRuntime().availableProcessors();
	@Parameter(names = "--help", help = true)
	private boolean help = false;

	public static void main(String[] args) {
		RemoteCommanderMain main = new RemoteCommanderMain();
		JCommander jcm = new JCommander(main, args);

		if( main.help ) {
			jcm.usage();
			return;
		}
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
