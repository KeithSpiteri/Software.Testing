package Cucumber.stepdefs;

import java.sql.DriverManager;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import persistant.User;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import cucumber.api.PendingException;
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

public class StepDefinitions {
	
	WebDriver driver;
	
	FillRegisterForm fill;
	
	@Before
	public void init()
	{
		driver = new FirefoxDriver();
	}
	
	@Given("^I am a user tyring to register$")
	public void i_am_a_user_tyring_to_register() throws Throwable {
	    fill = new FillRegisterForm(driver);
	    fill.visitRegister();
	}

	
	@When("^I register providing correct information$")
	public void i_register_providing_correct_information() throws Throwable {
	    fill.fillForm();
	    fill.submit();
	}

	@Then("^I should be told that the registration was successful$")
	public void i_should_be_told_that_the_registration_was_successful() throws Throwable {
		Boolean result = driver.getPageSource().contains("Invalid user details encountered");
	    assertFalse(result);
	}

	@When("^I fill in a form with correct data$")
	public void i_fill_in_a_form_with_correct_data() throws Throwable {
		fill.fillForm();
	}

	@When("^I change the \"(.*?)\" field to have incorrect input$")
	public void i_change_the_name_field_to_have_incorrect_input(String fieldname) throws Throwable {		
		if(fieldname.equals("pass"))
			fill.fillPassword("1234");
		else if(fieldname.equals("name"))
			fill.fillName("123");
		else if(fieldname.equals("surname"))
			fill.fillSurname("123");
		else if(fieldname.equals("date"))
			fill.fillDate("2010-10-5");
		else if(fieldname.equals("CCN"))
			fill.fillCCN("12345");
		else if(fieldname.equals("expiry"))
			fill.fillExpiry("2010-10");
		else if(fieldname.equals("CVV"))
			fill.fillCVV("1234");
	}

	@Then("^I should be told that the data in \"(.*?)\" is incorrect$")
	public void i_should_be_told_that_the_data_in_name_is_incorrect(String fieldname) throws Throwable {
		List<WebElement> paragraph = driver.findElements(By.className("invalid"));
		assertEquals(paragraph.size(), 1);
	}

	@Given("^I am a user with a free account$")
	public void i_am_a_user_with_a_free_account() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I try to place a bet of 5 euros$")
	public void i_try_to_place_a_bet_of_euros(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told the bet was successfully placed$")
	public void i_should_be_told_the_bet_was_successfully_placed() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told that I have reached the maximum number of bets$")
	public void i_should_be_told_that_I_have_reached_the_maximum_number_of_bets() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I am a suer with a premium account$")
	public void i_am_a_suer_with_a_premium_account() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told that I have reached the maximum cumulative betting amount$")
	public void i_should_be_told_that_I_have_reached_the_maximum_cumulative_betting_amount() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I am a user who has not yet logged on$")
	public void i_am_a_user_who_has_not_yet_logged_on() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I try to access the betting screen$")
	public void i_try_to_access_the_betting_screen() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be refused access$")
	public void i_should_be_refused_access() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I try to place a Low bet of (\\d+) euros$")
	public void i_try_to_place_a_Low_bet_of_euros(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be Allowed to bet$")
	public void i_should_be_Allowed_to_bet() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I try to place a Medium bet of (\\d+) euros$")
	public void i_try_to_place_a_Medium_bet_of_euros(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be Not Allowed to bet$")
	public void i_should_be_Not_Allowed_to_bet() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I try to place a High bet of (\\d+) euros$")
	public void i_try_to_place_a_High_bet_of_euros(int arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
	public void clearTestUser() {
		/*String url = "jdbc:mysql://sql4.freesqldatabase.com/"; 
		String dbName = "sql457634"; 
		String driver = "com.mysql.jdbc.Driver"; 
		String userName = "sql457634"; 
		String password = "qJ4*nP7*"; */
		try { 
			Class.forName(driver).newInstance(); 
			//Connection conn = (Connection) DriverManager.getConnection(url+dbName,userName,password); 
			//Statement stmt = (Statement) conn.createStatement();

			//String rem = "DELETE FROM sql457634.user WHERE username = \"TestDroid\";" ;
			//stmt.executeUpdate(rem);
			
			//conn.close(); 
			
			User user = new User();
			user.setUsername("TestDroid");
			
			DbService dbService = DbService.getInstance();
			dbService.deleteUser(user);
			
		} catch (Exception e) {
			e.printStackTrace(); } 
	
	}

	
	
	
	@After
	public void tearDown() {
		clearTestUser();
		driver.quit();
	}
	

}
