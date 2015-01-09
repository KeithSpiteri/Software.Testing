package Cucumber.stepdefs;

import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import persistant.User;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import db.services.DbService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import Selenium.FillRegisterForm;
import Selenium.FillLoginForm;
import Selenium.FillBetForm;

public class StepDefinitions {
	
	WebDriver driver;
	
	FillRegisterForm fillRegister;
	FillLoginForm fillLogin;
	FillBetForm fillBet;
	static boolean createdBetters = false;
	static boolean finishedAll = false;
	
	@Before
	public void init()
	{
		if(!createdBetters)
		{
			createdBetters = true;
			createTestBetters();
		}
		driver = new FirefoxDriver();
	}
	
	@Given("^I am a user trying to register$")
	public void i_am_a_user_trying_to_register() throws Throwable {
	    fillRegister = new FillRegisterForm(driver);
	    fillRegister.visitRegister();
	}

	
	@When("^I register providing correct information$")
	public void i_register_providing_correct_information() throws Throwable {
	    fillRegister.fillForm();
	    fillRegister.submit();
	}

	@Then("^I should be told that the registration was successful$")
	public void i_should_be_told_that_the_registration_was_successful() throws Throwable {
		Boolean result = driver.getPageSource().contains("Invalid user details encountered");
	    assertFalse(result);
	}

	@When("^I fill in a form with correct data$")
	public void i_fill_in_a_form_with_correct_data() throws Throwable {
		fillRegister.fillForm();
	}

	@When("^I change the \"(.*?)\" field to have incorrect input$")
	public void i_change_the_name_field_to_have_incorrect_input(String fieldname) throws Throwable {		
		if(fieldname.equals("pass"))
			fillRegister.fillPassword("1234");
		else if(fieldname.equals("name"))
			fillRegister.fillName("123");
		else if(fieldname.equals("surname"))
			fillRegister.fillSurname("123");
		else if(fieldname.equals("date"))
			fillRegister.fillDate("2010-10-5");
		else if(fieldname.equals("CCN"))
			fillRegister.fillCCN("12345");
		else if(fieldname.equals("expiry"))
			fillRegister.fillExpiry("2010-10");
		else if(fieldname.equals("CVV"))
			fillRegister.fillCVV("1234");
	}

	@Then("^I should be told that the data in \"(.*?)\" is incorrect$")
	public void i_should_be_told_that_the_data_in_name_is_incorrect(String fieldname) throws Throwable {
		List<WebElement> paragraph = driver.findElements(By.className("invalid"));
		assertEquals(paragraph.size(), 1);
	}

	@Given("^I am a user with a free account$")
	public void i_am_a_user_with_a_free_account() throws Throwable {
		createTestBetters();
	    fillLogin = new FillLoginForm(driver);
	    fillLogin.visitLogin();
	    fillLogin.fillForm();
	    fillLogin.submitForm();
	}

	@When("^I try to place a bet of (\\d+) euros$")
	public void i_try_to_place_a_bet_of_euros(int arg1) throws Throwable {
		fillBet = new FillBetForm(driver);
	    fillBet.chooseRisk("Low");
	    fillBet.setAmount(arg1);
	    fillBet.submitBet();
	}

	@Then("^I should be told the bet was successfully placed$")
	public void i_should_be_told_the_bet_was_successfully_placed() throws Throwable {
		 assertEquals("http://localhost:8080/Software.Testing/bet.jsp", driver.getCurrentUrl());
	}

	@Then("^I should be told that I have reached the maximum number of bets$")
	public void i_should_be_told_that_I_have_reached_the_maximum_number_of_bets() throws Throwable {
		Boolean result = driver.getPageSource().contains("Unable to place bet");
	    assertTrue(result);
	}

	@Given("^I am a user with a premium account$")
	public void i_am_a_user_with_a_premium_account() throws Throwable {
		fillLogin = new FillLoginForm(driver);
	    fillLogin.visitLogin();
	    fillLogin.fillPremForm();
	    fillLogin.submitForm();
	}
	


	@Then("^I should be told that I have reached the maximum cumulative betting amount$")
	public void i_should_be_told_that_I_have_reached_the_maximum_cumulative_betting_amount() throws Throwable {
		Boolean result = driver.getPageSource().contains("Unable to place bet");
	    assertTrue(result);
	}

	@Given("^I am a user who has not yet logged on$")
	public void i_am_a_user_who_has_not_yet_logged_on() throws Throwable {
		fillLogin = new FillLoginForm(driver);
	    fillLogin.visitLogin();
	}

	@When("^I try to access the betting screen$")
	public void i_try_to_access_the_betting_screen() throws Throwable {
		driver.get("http://localhost:8080/Software.Testing/bet.jsp");
	}

	@Then("^I should be refused access$")
	public void i_should_be_refused_access() throws Throwable {
	    assertEquals("http://localhost:8080/Software.Testing/index.jsp", driver.getCurrentUrl());
	}

	@When("^I try to place a \"(.*?)\" bet of 5 euros$")
	public void i_try_to_place_a_Low_bet_of_euros(String arg1) throws Throwable {
		fillBet = new FillBetForm(driver);
	    fillBet.chooseRisk(arg1);
	    fillBet.setAmount(5);
	    fillBet.submitBet();
	}

	@Then("^I should be \"Allowed\" to bet$")
	public void i_should_be_Allowed_to_bet() throws Throwable {
	    assertEquals("http://localhost:8080/Software.Testing/bet.jsp", driver.getCurrentUrl());
	}

	@Then("^I should be \"Not Allowed\" to bet$")
	public void i_should_be_Not_Allowed_to_bet() throws Throwable {
		Select comboBox = new Select(driver.findElement(By.id("risk")));
		String selectedComboValue = comboBox.getFirstSelectedOption().getText();
		finishedAll = true;
	    assertEquals("Low", selectedComboValue);
	}


	
	public static void createTestBetters() {
		try { 		

			User user = new User();
			user.setUsername("LoginFreeDroid");
			user.setPassword("12345678");
			user.setName("Test");
			user.setSurname("Test");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			String dateInString = "1994-06-06";
			Date dob = sdf.parse(dateInString);
			user.setDob(dob);
			
			user.setCreditCardNumber("5555555555554444");
			
			dateInString = "2016-06-31";
			Date expiry = sdf.parse(dateInString);
			user.setExpiry(expiry);
			
			user.setCvv("123");
			user.setFree(true);
			
			user.setLockedTill(null);
			user.setFailedLogins(0);
			
			
			DbService dbService = DbService.getInstance();
			dbService.addUser(user);
			
			user.setUsername("LoginPremDroid");
			user.setPremium(true);
		
			dbService.addUser(user);
			
		} catch (Exception e) {
			e.printStackTrace(); 
		} 
	
	}
	
	public void clearTestUser() {

		try { 		
			User user = new User();
			user.setUsername("TestDroid");
			
			DbService dbService = DbService.getInstance();
			dbService.deleteUser(user);
			user.setUsername("LoginFreeDroid");
			dbService.deleteUser(user);
			user.setUsername("LoginPremDroid");
			dbService.deleteUser(user);
			
		} catch (Exception e) {
			e.printStackTrace(); } 
	
	}
	
	public void clearTestBets() {

		try { 		
	     

	      Connection connection = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost/sql457634",
					"root", "");
	      
	      Statement stmt = (Statement) connection.createStatement();
	      String sql = "DELETE FROM sql457634.bet " +
	                   "WHERE user_id = \"LoginFreeDroid\"";
	      stmt.executeUpdate(sql);
	      
	      sql = "DELETE FROM sql457634.bet " +
                  "WHERE user_id = \"LoginPremDroid\"";
	      stmt.executeUpdate(sql);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		
	
	}
	
	@After
	public void tearDown() {
		clearTestBets();

		if(finishedAll){
			clearTestUser();
		}
		driver.quit();
	}
	
	

}
