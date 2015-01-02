package model;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import Selenium.FillRegisterForm;
import Selenium.FillLoginForm;
import Selenium.FillBetForm;



public class BettingModel implements FsmModel{
	WebDriver driver;
	static int uuid;
	
	FillRegisterForm fillRegister;
	FillLoginForm fillLogin;
	FillBetForm fillBet;
	
	
	String username = "";
	boolean isFree = true;
	
	public BettingModel(){
		this.driver = new FirefoxDriver();
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
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/register"))
		{
			return States.RegistrationError;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/login"))
		{
			return States.LockedAccount;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/placeBet"))
		{
			return States.BetError;
		}
		else
			return null;
	}
	

	public void reset(boolean arg0) {
		driver.get("http://localhost:8080/Software.Testing/index");		
	}
	
	
	public boolean toRegistrationGuard()
	{
		States st = this.getState();
		return st == States.Login;
	}
	
	@Action
	public void toRegistration()
	{
		driver.findElement(By.xpath("/html/body/div/div/ul/li[2]/a")).click();
		assertEquals(driver.findElement(By.className("active")).getText(), "Register");
	}
	
	public boolean registerUserGuard()
	{
		States st = this.getState();
		return st == States.Register;
	}
	
	@Action
	public void registerUser()
	{
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
	}
	
	public String generateUsername()
	{
		this.username = "user_"+uuid++;
		return this.username;
	}
	
	public boolean toLoginGuard()
	{
		States st = this.getState();
		return st == States.Register;
	}
	
	@Action
	public void toLogin()
	{
		driver.get("http://localhost:8080/Software.Testing/index.jsp");
	}
	
	public boolean toValidLoginGuard()
	{
		double probability = Math.random();
		States st = this.getState();
		
		if(st.equals(States.Login) && probability >= 0.25 )
			return true;
		else
			return false;
	}
	
	@Action
	public void toValidLogin()
	{
		driver.findElement(By.id("login_user")).sendKeys(this.username);
		driver.findElement(By.id("login_pass")).sendKeys("password");
		fillLogin = new FillLoginForm(driver);
		fillLogin.submitForm();
	}
	
	public boolean toInvalidLoginGuard()
	{
		double probability = Math.random();
		States st = this.getState();
		
		if(st.equals(States.Login) && probability < 0.25 )
			return true;
		else
			return false;
	}
	
	@Action
	public void toInvalidLogin()
	{
		driver.findElement(By.id("login_user")).sendKeys(this.username);
		driver.findElement(By.id("login_pass")).sendKeys("wrong_password");
		fillLogin = new FillLoginForm(driver);
		fillLogin.submitForm();
	}
	
	public boolean placeBetGuards()
	{
		States st = this.getState();
		double probability = Math.random();
		
		if(st.equals(States.Bet) && probability < 0.5 )
			return true;
		else
			return false;
	}
	
	@Action
	public void placeBet()
	{
		int min = 1;
		int max = 6;
		Random r = new Random();
		int bet_amount = r.nextInt(max - min + 1) + min;
		
		fillBet = new FillBetForm(driver);
		fillBet.setAmount(bet_amount);
		
		boolean bet_placed = 
		
		
		
	}
	
}
