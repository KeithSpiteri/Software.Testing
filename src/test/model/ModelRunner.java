package model;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class ModelRunner {
	private final int INSTANCES = 10;

	@Test
	public void runModel()
	{
		long start_time=System.currentTimeMillis();
		Vector<Long> loadTimes = new Vector<Long>();
		ExecutorService executor = Executors.newFixedThreadPool(INSTANCES);
		for (int i = 0; i < INSTANCES; i++) {
			Runnable instance = new BettingModel();
			executor.execute(instance);
		}
		executor.shutdown();
		while(!executor.isTerminated()){}
		//long end_time=System.currentTimeMillis();
		//long total = 0;
		/*for(long i : loadTimes){
			total += i;
		}*/
		
		System.out.println("Finished Running the model");
		
		//System.out.println("Total execution time for the test: "+(start_time-end_time));
		
		//System.out.println("Average response time per page: " + total/loadTimes.size());
	}
}
