package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.DriverManager;
import java.util.Random;
import java.util.Vector;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.AllRoundTester;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

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
	
	static boolean isLastThread = false;
	
	
	Long start;
	
	
	int numBets = 0;
	
	public BettingModel(Vector<Long> timings, int uid){
		this.timings =  timings;
		this.uid = uid;
	}
	
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
	
	public void reset(boolean arg0) {
		driver.get("http://localhost:8080/Software.Testing/index.jsp");	
		this.afterLogin = true;
	}
	
	public boolean registerErrorGuard()
	{
		States st = getState();
		return (st.equals(States.RegisterError));
	}
	
	
	public @Action void registerError()
	{
		start = System.currentTimeMillis();
		this.username = "";
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
		driver.findElement(By.xpath("/html/body/div/div/ul/li[2]/a")).click();
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean loginErrorGuard()
	{
		States st = getState();
		return (st.equals(States.LoginError));
	}
	
	
	public @Action void loginError()
	{
		start = System.currentTimeMillis();
		this.username = "";
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean toRegistrationGuard()
	{
		States st = getState();
		return (st.equals(States.Login) && username.equals(""));
	}
	
	
	public @Action void toRegistration()
	{
		start = System.currentTimeMillis();
		driver.findElement(By.xpath("/html/body/div/div/ul/li[2]/a")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals(driver.findElement(By.className("active")).getText(), "Register");
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean registerUserGuard()
	{
		States st = getState();
		return st.equals(States.Register) && username.equals("");
	}
	
	
	public @Action void registerUser()
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
		//assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(driver.getCurrentUrl());
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/index.jsp") || driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/register"));
		timings.add(System.currentTimeMillis() - start);
	}
	
	public String generateUsername()
	{
		this.username = "user_"+uid;
		return this.username;
	}
	
	public boolean toLoginGuard()
	{
		States st = getState();
		return (st.equals(States.Register) && !username.equals(""));
	}
	
	
	public @Action void toLogin()
	{
		start = System.currentTimeMillis();
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean toValidLoginGuard()
	{
		double probability = Math.random();
		States st = getState();
		probability = 0.5;
		if(st.equals(States.Login) && probability >= 0.25 && !username.equals("")  || (st.equals(States.Login) && !this.toInvalid))
			return true;
		else
		{
			if(!username.equals("") && st.equals(States.Login))
				this.toInvalid = true;
			return false;
		}
	}
	
	
	public @Action void toValidLogin()
	{
		start = System.currentTimeMillis();
		fillLogin = new FillLoginForm(driver);
		fillLogin.fillCustom(this.username, "password");
		this.afterLogin = true;
		fillLogin.submitForm();
		//assertEquals("http://localhost:8080/Software.Testing/bet.jsp", driver.getCurrentUrl());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/bet.jsp") || driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/login"));
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean toInvalidLoginGuard()
	{
		States st = getState();
		
		if((st.equals(States.Login) && !username.equals("") && this.toInvalid))
		{
			this.toInvalid = false;
			return true;
		}else
			return false;
	}
	
	
	public @Action void toInvalidLogin()
	{
		start = System.currentTimeMillis();
		fillLogin = new FillLoginForm(driver);
		fillLogin.fillCustom(this.username, "wrong_password");
		fillLogin.submitForm();	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean placeBetGuard()
	{
		States st = getState();
		double probability = Math.random();
		
		if(st.equals(States.Bet) && (probability <= 0.5 || this.afterLogin == true) ){
			this.afterLogin = false;
			return true;
		}
		else
		{
			if(st.equals(States.Bet))
				this.toLogout = true;
			return false;
		}
	}
	
	
	public @Action void placeBet()
	{
		start = System.currentTimeMillis();
		int min = 1;
		int max = 6;
		Random r = new Random();
		int bet_amount = r.nextInt(max - min + 1) + min;
		
		
		
		if(!this.isFree)
		{
			if(this.numBets > 3)
				bet_amount = 5000;
			
		}
		this.numBets++;
		
		fillBet = new FillBetForm(driver);
		fillBet.setAmount(bet_amount);
		fillBet.submitBet();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/bet.jsp") || driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/placeBet"));		
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean backInvalidBetGuard()
	{
		States st = getState();		
		if(st.equals(States.BetError))
			return true;
		else
			return false;
	}
	
	
	public @Action void backInvalidBet()
	{
		start = System.currentTimeMillis();
		driver.get("http://localhost:8080/Software.Testing/bet.jsp");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("http://localhost:8080/Software.Testing/bet.jsp", driver.getCurrentUrl());
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean doLogoutGuard()
	{
		States st = getState();
		double probability = Math.random();
		if(((st.equals(States.Bet) && probability >= 0.5 )|| (st.equals(States.Bet) && this.toLogout == true)) && !this.afterLogin)
		{
			this.toLogout = false;
			return true;
		}else
			return false;
	}

	
	public @Action void doLogout()
	{
		start = System.currentTimeMillis();
		fillBet = new FillBetForm(driver);
		fillBet.logout();
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
		timings.add(System.currentTimeMillis() - start);
	}
	
	@Test
	public void run() {
		
		this.before();
		

		
		Tester t = new AllRoundTester(this);
		t.addListener(new VerboseListener());
		t.generate(10);
		t.buildGraph();
		this.after();
		
//		this.before();
//		Tester tester = new GreedyTester(this);
//		tester.setRandom(new Random());
//		tester.generate(100);
//		
//		tester.buildGraph();
//		clearUser();
//		
//		tester.addListener(new StopOnFailureListener());
//		
//		CoverageMetric trCoverage = new TransitionCoverage();
//		tester.addCoverageMetric(trCoverage);
//		
//		VerboseListener verboseListener = new VerboseListener();
//		verboseListener.setModel(tester.getModel());
//		tester.addListener(verboseListener);
//		
//		tester.generate(10);
//		
//		tester.getModel().printMessage(trCoverage.getName() + " was " + trCoverage.toString());
//		this.after();
	}
	


	private void after() {
		driver.quit();
	}

	private void before() {
//		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//	     
//		driver = new HtmlUnitDriver(capabilities); 
//		((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		this.driver = new FirefoxDriver();
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
