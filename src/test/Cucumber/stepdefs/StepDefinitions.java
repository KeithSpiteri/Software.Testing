package Cucumber.stepdefs;

import java.sql.DriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import Selenium.FillRegisterForm;

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
	    // Write code here that turns the phrase above into concrete actions
	    fill = new FillRegisterForm(driver);
	    fill.visitRegister();
	}

	
	@When("^I register providing correct information$")
	public void i_register_providing_correct_information() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    fill.fillForm();
	    fill.submit();
	}

	@Then("^I should be told that the registration was successful$")
	public void i_should_be_told_that_the_registration_was_successful() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		Boolean result = driver.getPageSource().contains("Invalid user details encountered");
	    assertFalse(result);
	}

	@When("^I fill in a form with correct data$")
	public void i_fill_in_a_form_with_correct_data() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I change the name field to have incorrect input$")
	public void i_change_the_name_field_to_have_incorrect_input() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told that the data in name is incorrect$")
	public void i_should_be_told_that_the_data_in_name_is_incorrect() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I change the surname field to have incorrect input$")
	public void i_change_the_surname_field_to_have_incorrect_input() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told that the data in surname is incorrect$")
	public void i_should_be_told_that_the_data_in_surname_is_incorrect() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I change the dob field to have incorrect input$")
	public void i_change_the_dob_field_to_have_incorrect_input() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told that the data in dob is incorrect$")
	public void i_should_be_told_that_the_data_in_dob_is_incorrect() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I change the ccn field to have incorrect input$")
	public void i_change_the_ccn_field_to_have_incorrect_input() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told that the data in ccn is incorrect$")
	public void i_should_be_told_that_the_data_in_ccn_is_incorrect() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I change the expiry field to have incorrect input$")
	public void i_change_the_expiry_field_to_have_incorrect_input() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^I should be told that the data in expiry is incorrect$")
	public void i_should_be_told_that_the_data_in_expiry_is_incorrect() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^I am a user with a free account$")
	public void i_am_a_user_with_a_free_account() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^I try to place a bet of (\\d+) euros$")
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
		String url = "jdbc:mysql://sql4.freesqldatabase.com/"; 
		String dbName = "sql457634"; 
		String driver = "com.mysql.jdbc.Driver"; 
		String userName = "sql457634"; 
		String password = "qJ4*nP7*"; 
		try { 
			Class.forName(driver).newInstance(); 
			Connection conn = (Connection) DriverManager.getConnection(url+dbName,userName,password); 
			Statement stmt = (Statement) conn.createStatement();

			String rem = "DELETE FROM sql457634.user WHERE username = \"TestDroid\";" ;
			stmt.executeUpdate(rem);
			
			conn.close(); 
			
		} catch (Exception e) {
			e.printStackTrace(); } 
	
	}

	
	
	
	@After
	public void tearDown() {
		clearTestUser();
		driver.quit();
	}
	

}
