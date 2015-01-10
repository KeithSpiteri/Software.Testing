package model;

import java.util.Random;
import java.util.Vector;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.AllRoundTester;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;
import Selenium.FillRegisterForm;
import Selenium.FillLoginForm;
import Selenium.FillBetForm;
import static org.junit.Assert.assertTrue;



public class BettingModel implements FsmModel, Runnable{
	WebDriver driver;
	Vector<Long> timings;
	static int uuid;
	
	FillRegisterForm fillRegister;
	FillLoginForm fillLogin;
	FillBetForm fillBet;
	
	
	String username = "";
	boolean isFree = true;
	boolean toInvalid = false;
	boolean toLogout = false;
	
	Long start;
	
	
	int numBets = 0;
	
	public BettingModel(Vector<Long> timings){
		this.timings =  timings;
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
		else
		{
			driver.navigate().back();
			return getState();
		}
	}
	
	
	public void reset(boolean arg0) {
		driver.get("http://localhost:8080/Software.Testing/index.jsp");	
		
		toInvalid = false;
		toLogout = false;
	}
	
	
	public boolean toRegistrationGuard()
	{
		States st = this.getState();
		return (st.equals(States.Login) && username.equals(""));
	}
	
	@Action
	public void toRegistration()
	{
		start = System.currentTimeMillis();
		driver.findElement(By.xpath("/html/body/div/div/ul/li[2]/a")).click();
		assertEquals(driver.findElement(By.className("active")).getText(), "Register");
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean registerUserGuard()
	{
		States st = this.getState();
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
		
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
		timings.add(System.currentTimeMillis() - start);
	}
	
	public String generateUsername()
	{
		this.username = "user_"+uuid++;
		return this.username;
	}
	
	public boolean toLoginGuard()
	{
		States st = this.getState();
		return (st.equals(States.Register) && !username.equals(""));
	}
	
	@Action
	public void toLogin()
	{
		start = System.currentTimeMillis();
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean toValidLoginGuard()
	{
		double probability = Math.random();
		States st = this.getState();
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
	
	@Action
	public void toValidLogin()
	{
		start = System.currentTimeMillis();
		fillLogin = new FillLoginForm(driver);
		fillLogin.fillCustom(this.username, "password");
		fillLogin.submitForm();
		assertEquals("http://localhost:8080/Software.Testing/bet.jsp", driver.getCurrentUrl());
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean toInvalidLoginGuard()
	{
		double probability = Math.random();
		States st = this.getState();
		
		if((st.equals(States.Login) && this.toInvalid))
		{
			this.toInvalid = false;
			return true;
		}else
			return false;
	}
	
	@Action
	public void toInvalidLogin()
	{
		start = System.currentTimeMillis();
		fillLogin = new FillLoginForm(driver);
		fillLogin.fillCustom(this.username, "wrong_password");
		fillLogin.submitForm();	
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean placeBetGuard()
	{
		States st = this.getState();
		double probability = Math.random();
		
		if(st.equals(States.Bet) && probability < 0.5 )
			return true;
		else
		{
			if(st.equals(States.Bet))
				this.toLogout = true;
			return false;
		}
	}
	
	@Action
	public void placeBet()
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
				
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/bet.jsp") || driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/placeBet"));		
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean backInvalidBetGuard()
	{
		States st = this.getState();		
		if(st.equals(States.BetError))
			return true;
		else
			return false;
	}
	
	@Action
	public void backInvalidBet()
	{
		start = System.currentTimeMillis();
		driver.navigate().back();
		assertEquals("http://localhost:8080/Software.Testing/bet.jsp", driver.getCurrentUrl());
		timings.add(System.currentTimeMillis() - start);
	}
	
	public boolean doLogoutGuard()
	{
		States st = this.getState();
		double probability = Math.random();
		if((st.equals(States.Bet) && probability >= 0.5 )|| (st.equals(States.Bet) && this.toLogout == true))
		{
			this.toLogout = false;
			return true;
		}else
			return false;
	}

	@Action
	public void doLogout()
	{
		start = System.currentTimeMillis();
		fillBet = new FillBetForm(driver);
		fillBet.logout();
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
		timings.add(System.currentTimeMillis() - start);
	}
	
	public void run() {
		try{
			this.before();
			Tester t = new AllRoundTester(this);
			t.addListener(new VerboseListener());
			t.generate(15);
			t.buildGraph();
			this.after();
		}
		catch(Exception e)
		{
			this.after();
		}
	}
	


	private void after() {
		driver.quit();
		
	}

	private void before() {
		this.driver = new FirefoxDriver();
	}
	
}
