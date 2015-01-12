package model;

import java.sql.DriverManager;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class ModelRunner {

	static final int instances = 1;

	@Test
	public void runModel()
	{
		Long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(instances);
		//ExecutorService executor = Executors.newCachedThreadPool();
		Vector<Long> timings = new Vector<Long>();
		for (int i = 0; i < instances; i++) {
			//Runnable instance = new BettingModel(timings, i);
			Runnable thread = new Model();
			executor.execute(thread);
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
	                  "WHERE user_id LIKE \"user_%\"";
		      stmt.executeUpdate(sql);
		      
		      sql = "DELETE FROM sql457634.user " +
		                   "WHERE username LIKE \"user_%\"";
		      stmt.executeUpdate(sql);
		      
		     
		      connection.close();
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		
		
		System.out.println("Finished Running the model");
		
	
	}
}
