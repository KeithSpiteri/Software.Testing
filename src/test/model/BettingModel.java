package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.DriverManager;
import java.util.Random;
import java.util.Vector;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.AllRoundTester;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.LookaheadTester;
import nz.ac.waikato.modeljunit.RandomTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;
import nz.ac.waikato.modeljunit.coverage.CoverageMetric;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import Selenium.FillBetForm;
import Selenium.FillLoginForm;
import Selenium.FillRegisterForm;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;



public class BettingModel implements FsmModel, Runnable{
	FirefoxDriver driver;
	Vector<Long> timings;
	
	FillRegisterForm fillRegister;
	FillLoginForm fillLogin;
	FillBetForm fillBet;
	
	
	String username = "";
	private int uid = 0;
	
	boolean isFree = true;
	boolean toInvalid = false;
	boolean toLogout = false;
	boolean afterLogin = true;
	boolean toBet = false;
	
	static boolean isLastThread = false;
	
	boolean toValid = false;
	
	
	Long start;
	
	
	int numBets = 0;
	
	public BettingModel(Vector<Long> timings, int uid){
		this.timings = timings;
		this.uid = uid;
	}
	
	@Override
	public States getState()
	{
		if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/index.jsp") 
				&& driver.findElement(By.className("active")).getText().equals("Login") )
		{
			return States.Login;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/index.jsp") 
				&& driver.findElement(By.className("active")).getText().equals("Register") )
		{
			return States.Register;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/bet.jsp"))
		{
			return States.Bet;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/placeBet"))
		{
			return States.BetError;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/register"))
		{
			return States.RegisterError;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/login"))
		{
			return States.LoginError;
		}
		else
		{
			driver.navigate().back();
			return getState();
		}
	}
	@Override
	public void reset(boolean testing) {
		toInvalid = false;
		toValid = false;
		toLogout = false;
		afterLogin = true;
		driver.get("http://localhost:8080/Software.Testing/index.jsp");	

	}
	
	public boolean toValidLoginGuard()
	{
		double probability = Math.random();
		States st = getState();
		if((st.equals(States.Login) && probability >= 0.25 && !username.equals("")) || toValid){
			toValid = false;
			return true;
		}else
		{
			if(st.equals(States.Login) && !username.equals(""))
				toInvalid = true;
			return false;
		}
	}
	
	@Action
	public void toValidLogin()
	{
		start = System.currentTimeMillis();
		fillLogin = new FillLoginForm(driver);
		fillLogin.fillCustom(this.username, "password");
		this.afterLogin = true;
		fillLogin.submitForm();

		timings.add(System.currentTimeMillis() - start);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/bet.jsp") || driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/login"));
	}
	
	public boolean toInvalidLoginGuard()
	{
		double probability = Math.random();
		States st = getState();
		
		if(((st.equals(States.Login) && !username.equals("") && probability < 0.25)) || toInvalid)
		{
			this.toInvalid = false;
			return true;
		}else{
			if(st.equals(States.Login) && !username.equals(""))
					toValid = true;
			return false;
		}
	}
	
	@Action
	public void toInvalidLogin()
	{
		start = System.currentTimeMillis();
		fillLogin = new FillLoginForm(driver);
		fillLogin.fillCustom(this.username, "wrong_password");
		fillLogin.submitForm();	
		timings.add(System.currentTimeMillis() - start);
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
	}
	
	public boolean registerErrorGuard()
	{
		States st = getState();
		return (st.equals(States.RegisterError));
	}
	
	@Action
	public void registerError()
	{
		start = System.currentTimeMillis();
		this.username = "";
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
		timings.add(System.currentTimeMillis() - start);
		driver.findElement(By.xpath("/html/body/div/div/ul/li[2]/a")).click();
	}
	
	public boolean loginErrorGuard()
	{
		States st = getState();
		return (st.equals(States.LoginError));
	}
	
	@Action
	public void loginError()
	{
		this.username = "";
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
	}
	
	public boolean toRegistrationGuard()
	{
		States st = getState();
		return (st.equals(States.Login) && username.equals(""));
	}
	
	@Action
	public void toRegistration()
	{
		start = System.currentTimeMillis();
		driver.findElement(By.xpath("/html/body/div/div/ul/li[2]/a")).click();

		timings.add(System.currentTimeMillis() - start);
		assertEquals(driver.findElement(By.className("active")).getText(), "Register");
		
	}
	
	public boolean registerUserGuard()
	{
		States st = getState();
		return st.equals(States.Register) && username.equals("");
	}
	
	@Action
	public void registerUser()
	{
		start = System.currentTimeMillis();
		String userName = generateUsername();
		fillRegister = new FillRegisterForm(driver);
		double probability = Math.random();
		
		if(probability < 0.75)
		{
			fillRegister.fillModelForm(userName, 0);
			this.isFree = true;
		}
		else
		{
			fillRegister.fillModelForm(userName, 1);
			this.isFree = false;
		}
		if(!driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/index.jsp"))
			this.username = "";

		timings.add(System.currentTimeMillis() - start);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/index.jsp") || driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/register"));
		
	}
	
	public String generateUsername()
	{
		this.username = "user_"+uid;
		return this.username;
	}
	
	
	
	
	
	
	
	public boolean placeBetGuard()
	{
		States st = getState();
		double probability = Math.random();
		
		if((st.equals(States.Bet) && probability <= 0.5) || (st.equals(States.Bet) && this.afterLogin) || toBet ){
			this.toBet = false;
			this.afterLogin = false;
			return true;
		}
		else
		{
			if(st.equals(States.Bet))
				toLogout = true;
			return false;
		}
	}
	
	@Action
	public void placeBet()
	{
		start = System.currentTimeMillis();
		int min = 1;
		int max = 6;
		
		if(!this.isFree)
		{
			min = 100;
			max = 2000;
		}
		
		Random r = new Random();
		int bet_amount = r.nextInt(max - min + 1) + min;
		
		
		this.numBets++;
		
		fillBet = new FillBetForm(driver);
		fillBet.setAmount(bet_amount);
		fillBet.submitBet();

		timings.add(System.currentTimeMillis() - start);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/bet.jsp") || driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/placeBet"));		
	}
	
	public boolean backInvalidBetGuard()
	{
		States st = getState();		
		if(st.equals(States.BetError))
			return true;
		else
			return false;
	}
	
	@Action
	public void backInvalidBet()
	{
		driver.get("http://localhost:8080/Software.Testing/bet.jsp");
		assertEquals("http://localhost:8080/Software.Testing/bet.jsp", driver.getCurrentUrl());
	}
	
	public boolean doLogoutGuard()
	{
		States st = getState();
		double probability = Math.random();
		if((st.equals(States.Bet) && probability >= 0.5  && !this.afterLogin) || toLogout)
		{
			this.afterLogin = true;
			this.toBet = false;
			this.toLogout = false;
			return true;
		}else{
			if(st.equals(States.Bet))
				toBet = true;
			
			return false;
		}
	}

	@Action
	public void doLogout()
	{
		start = System.currentTimeMillis();
		fillBet = new FillBetForm(driver);
		fillBet.logout();
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
		timings.add(System.currentTimeMillis() - start);
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
	}
	
	@Test
	public void run() {
//		this.before();
//		Tester tester = new GreedyTester(this);
//		tester.setRandom(new Random());
//		tester.generate(20);
//		
//		tester.buildGraph();
//		
//		tester.addListener(new StopOnFailureListener());
//		
//		CoverageMetric trCoverage = new TransitionCoverage();
//		tester.addCoverageMetric(trCoverage);
//		clearUser();
//		username = "";
//		
//		VerboseListener verboseListener = new VerboseListener();
//		verboseListener.setModel(tester.getModel());
//		tester.addListener(verboseListener);
//		
//		tester.generate(20);
//		
//		tester.getModel().printMessage(trCoverage.getName() + " was " + trCoverage.toString());
//		this.after();
		
		this.before();
		
	
		
		Tester t = new GreedyTester(this);
		t.addListener(new VerboseListener());
		t.generate(20);
		t.buildGraph();
		this.after();
		
	}
	


	private void after() {
		driver.quit();
	}

	private void before() {
		this.driver = new FirefoxDriver();
		
		
		
		while(!isLastThread)
		{
			try {
				Thread.sleep(500);
				if(uid == ModelRunner.instances-1)
					isLastThread = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void clearUser()
	{
		try { 		
		      Connection connection = (Connection) DriverManager.getConnection(
						"jdbc:mysql://localhost/sql457634",
						"root", "");
		      
		      Statement stmt = (Statement) connection.createStatement();
		      
		      String sql = "DELETE FROM sql457634.bet " +
	                  "WHERE user_id LIKE \"user_"+uid+"\"";
		      stmt.executeUpdate(sql);
		      
		      sql = "DELETE FROM sql457634.user " +
		                   "WHERE username LIKE \"user_"+uid+"\"";
		      stmt.executeUpdate(sql);
		      
		     
		      connection.close();
			} catch (Exception e) {
				e.printStackTrace(); 
			}
	}
	
}
