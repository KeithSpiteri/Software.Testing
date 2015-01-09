package model;

import java.sql.DriverManager;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import static org.junit.Assert.assertEquals;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.AllRoundTester;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;
import Selenium.FillRegisterForm;
import Selenium.FillLoginForm;
import Selenium.FillBetForm;



public class BettingModel implements FsmModel, Runnable{
	WebDriver driver;
	static int uuid;
	
	FillRegisterForm fillRegister;
	FillLoginForm fillLogin;
	FillBetForm fillBet;
	
	
	String username = "";
	boolean isFree = true;
	
	public BettingModel(){
		
	}
	
	public States getState()
	{
		if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/index.jsp") 
				&& driver.findElement(By.className("active")).getText().equals("Login") )
		{
			System.out.println("Login state");

			return States.Login;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/index.jsp") 
				&& driver.findElement(By.className("active")).getText().equals("Register") )
		{
			System.out.println("Register state");
			return States.Register;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/bet.jsp"))
		{
			System.out.println("Bet state");

			return States.Bet;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/register"))
		{
			System.out.println("Register Error");

			return States.RegistrationError;
		}
		else if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/placeBet"))
		{
			System.out.println("Bet Error");

			return States.BetError;
		}
		else
			return null;
	}
	

	public void reset(boolean arg0) {
		driver.get("http://localhost:8080/Software.Testing/index.jsp");		
	}
	
	
	public boolean toRegistrationGuard()
	{
		States st = this.getState();
		return (st == States.Login && username.equals(""));
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
		return st == States.Register && username.equals("");
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
		return (st == States.Register && !username.equals(""));
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
		
		if(st.equals(States.Login) && probability >= 0.25 && !username.equals("") )
			return true;
		else
			return false;
	}
	
	@Action
	public void toValidLogin()
	{

		fillLogin = new FillLoginForm(driver);
		fillLogin.fillCustom(this.username, "password");
		fillLogin.submitForm();
	}
	
	public boolean toInvalidLoginGuard()
	{
		double probability = Math.random();
		States st = this.getState();
		
		if(st.equals(States.Login) && probability < 0.25 && !username.equals(""))
			return true;
		else
			return false;
	}
	
	@Action
	public void toInvalidLogin()
	{
		fillLogin = new FillLoginForm(driver);
		fillLogin.fillCustom(this.username, "wrong_password");
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
		fillBet.submitBet();
		
		String expected = "";
		
		if(bet_amount>5)
		
			expected = "http://localhost:8080/Software.Testing/placeBet";
		else
			expected = "http://localhost:8080/Software.Testing/bet.jsp";
		
		assertEquals(expected, driver.getCurrentUrl());		
	}
	
	public boolean logoutGaurd()
	{
		States st = this.getState();
		double probability = Math.random();
		if(st.equals(States.Bet) && probability >= 0.5 )
			return true;
		else
			return false;
	}

	public void logout()
	{
		fillBet = new FillBetForm(driver);
		fillBet.logout();
		assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
	}
	
	public void run() {
		this.before();
		Tester t = new AllRoundTester(this);
		t.addListener(new VerboseListener());
		t.generate(1);
		t.buildGraph();
		this.after();
		
	}
	


	private void after() {
		driver.quit();
		try { 		
	      Connection connection = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost/sql457634",
					"root", "");
	      
	      Statement stmt = (Statement) connection.createStatement();
	      
	      String sql = "DELETE FROM sql457634.bet " +
                  "WHERE user_id = \"user_%\"";
	      stmt.executeUpdate(sql);
	      
	      sql = "DELETE FROM sql457634.user " +
	                   "WHERE username = \"user_%\"";
	      stmt.executeUpdate(sql);
	      
	     
	      connection.close();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	private void before() {
		this.driver = new FirefoxDriver();
	}
	
}
