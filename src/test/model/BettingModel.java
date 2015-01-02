package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import nz.ac.waikato.modeljunit.FsmModel;



public class BettingModel implements FsmModel{
	WebDriver driver;
	
	public BettingModel(){
		this.driver = new FirefoxDriver();
	}
	
	public States getState()
	{
		if(driver.getCurrentUrl().equals("http://localhost:8080/Software.Testing/index.jsp"))
		{
			
		}
		
		return null;
	}
	
}
