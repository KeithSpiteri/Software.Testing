package model;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class ModelRunner {
	private final int INSTANCES =20;

	@Test
	public void runModel()
	{
		ExecutorService executor = Executors.newFixedThreadPool(INSTANCES);
		for (int i = 0; i < INSTANCES; i++) {
			Runnable instance = new BettingModel();
			executor.execute(instance);
		}
		executor.shutdown();
		while(!executor.isTerminated()){}
			
		System.out.println("Finished Running the model");
		
	
	}
}
