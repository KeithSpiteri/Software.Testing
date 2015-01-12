package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.Vector;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.AllRoundTester;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Selenium.FillBetForm;
import Selenium.FillLoginForm;
import Selenium.FillRegisterForm;

public class Model implements FsmModel, Runnable {
	
	WebDriver driver;
	String username = "";
	Vector<Long> timings;
	boolean isFree = true;
	boolean doInvalid = false;
	boolean logout = false;
	
	public int uid = 0;
	
	Long start;
	

	@Override
	public States getState() {
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
		doInvalid = false;
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
	}
	
	
	public boolean toLoginGuard()
	{
		if (getState().equals(States.Login) && !username.equals("")){
			return true;
		}else{
			return false;
			
		}
			
	}
	
	@Action
	public void toLogin()
	{
		start = System.currentTimeMillis();
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
		timings.add(System.currentTimeMillis() - start);

		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
	}
	
	public boolean toRegisterGuard()
	{
		if (getState().equals(States.Login) && username.equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	@Action
	public void toRegister()
	{
		start = System.currentTimeMillis();
		driver.findElement(By.xpath("/html/body/div/div/ul/li[2]/a")).click();
		timings.add(System.currentTimeMillis() - start);

		assertEquals(driver.findElement(By.className("active")).getText(), "Register");
	}
	
	public boolean registerGuard()
	{
		if (getState().equals(States.Register)){
			return true;
		}else{
			return false;
		}
	}
	
	@Action
	public void register()
	{
		start = System.currentTimeMillis();
		String userName = generateUsername();
		FillRegisterForm fillRegister = new FillRegisterForm(driver);
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
		timings.add(System.currentTimeMillis() - start);

		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/index.jsp"));
	}
	
	public String generateUsername()
	{
		this.username = "user_"+uid;
		return this.username;
	}
	
	public boolean validLoginGuard()
	{
		double probability = Math.random();
		
		if(getState().equals(States.Login) && probability >= 0.25 && !username.equals(""))
		{
			return true;
		}else
		{
			if(getState().equals(States.Login) && !username.equals("")){
				this.doInvalid = true;
			}
			return false;
		}
	}
	
	@Action
	public void validLogin()
	{
		start = System.currentTimeMillis();
		FillLoginForm fillLogin = new FillLoginForm(driver);
		fillLogin.fillCustom(this.username, "password");
		fillLogin.submitForm();		
		timings.add(System.currentTimeMillis() - start);

		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/bet.jsp") || driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/login"));
	}
	
	public boolean invalidLoginGuard()
	{
		if(getState().equals(States.Login) && this.doInvalid)
		{
			this.doInvalid = false;
			return true;
		}else
			return false;
	}

	@Action
	public void invalidLogin()
	{
		start = System.currentTimeMillis();
		FillLoginForm fillLogin = new FillLoginForm(driver);
		fillLogin.fillCustom(this.username, "wrong");
		fillLogin.submitForm();		
		
		timings.add(System.currentTimeMillis() - start);

		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/login.jsp") || driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/login"));
	}
	
	public boolean betGuard()
	{
		double probability = Math.random();
		if(getState().equals(States.Bet) && probability <= 0.5)
			return true;
		else
		{
			if(getState().equals(States.Bet))
				this.logout = true;
			return false;
			
		}
	}
	
	@Action
	public void bet()
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
				
		FillBetForm fillBet = new FillBetForm(driver);
		fillBet.setAmount(bet_amount);
		fillBet.submitBet();
		
		timings.add(System.currentTimeMillis() - start);

		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/bet.jsp") || driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/placeBet"));		
	}
	
	public boolean logoutGuard()
	{
		if(getState().equals(States.Bet) && this.logout)
		{
			this.logout = false;
			return true;
		}else
		{
			return false;
		}
	}
	
	@Action
	public void logout()
	{
		start = System.currentTimeMillis();
		FillBetForm fillBet = new FillBetForm(driver);
		fillBet.logout();
		driver.get("http://localhost:8080/Software.Testing/index.jsp");

		timings.add(System.currentTimeMillis() - start);
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
	}
	
	@Override
	public void run() {
		this.before();
		Tester t = new AllRoundTester(this);
		t.addListener(new VerboseListener());
		t.generate(10);
		t.buildGraph();
		this.after();
		
	}
	
	private void before() {
		this.driver = new FirefoxDriver();
	}
	
	private void after() {
		driver.quit();
	}

	

}
