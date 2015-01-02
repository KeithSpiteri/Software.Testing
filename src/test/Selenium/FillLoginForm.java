package Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FillLoginForm {
	
	WebDriver driver;
	
	public FillLoginForm(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void visitLogin()
	{
		driver.get("http://localhost:8080/Software.Testing/");
	}
	
	public void fillForm()
	{
		driver.findElement(By.id("login_user")).sendKeys("LoginFreeDroid");
		driver.findElement(By.id("login_pass")).sendKeys("12345678");
	}
	
	public void fillPremForm()
	{
		driver.findElement(By.id("login_user")).sendKeys("LoginPremDroid");
		driver.findElement(By.id("login_pass")).sendKeys("12345678");
	}
	
	public void submitForm()
	{
		driver.findElement(By.id("login_submit")).click();
	}

}
