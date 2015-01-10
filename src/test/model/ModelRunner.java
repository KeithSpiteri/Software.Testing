package model;

import java.sql.DriverManager;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ModelRunner {
	private final int INSTANCES = 1;

	@Test
	public void runModel()
	{
		Long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(INSTANCES);
		Vector<Long> timings = new Vector<Long>();
		for (int i = 0; i < INSTANCES; i++) {
			Runnable instance = new BettingModel(timings);
			executor.execute(instance);
		}
		executor.shutdown();
		while(!executor.isTerminated()){}
	
		Long end = System.currentTimeMillis();
		
		Long execution_time = 0l;
		
		for(long time : timings){
			execution_time += time;
		}
		
		System.out.println("Model finished execution in " + (end-start) +"ms");
		System.out.println("Average transition time: " + (execution_time/timings.size()) + "ms");
		
		try { 		
		      Connection connection = (Connection) DriverManager.getConnection(
						"jdbc:mysql://localhost/sql457634",
						"root", "");
		      
		      Statement stmt = (Statement) connection.createStatement();
		      
		      String sql = "DELETE FROM sql457634.bet " +
	                  "WHERE user_id LIKE \"user__\"";
		      stmt.executeUpdate(sql);
		      
		      sql = "DELETE FROM sql457634.user " +
		                   "WHERE username LIKE \"user__\"";
		      stmt.executeUpdate(sql);
		      
		     
		      connection.close();
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		
		
		System.out.println("Finished Running the model");
		
	
	}
}
