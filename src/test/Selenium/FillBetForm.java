package Selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FillBetForm {
	WebDriver driver;

	public FillBetForm(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void chooseRisk(String risk)
	{
		WebElement select = driver.findElement(By.id("risk"));
		List<WebElement> options = select.findElements(By.tagName("option"));

		for (WebElement option : options) {

		if(risk.equals(option.getText().trim()))
			option.click();   
		}
	}
	
	public void setAmount(int amount)
	{
		WebElement am = driver.findElement(By.id("amount"));
		am.clear();
		am.sendKeys(Integer.toString(amount));
	}
	
	public void submitBet()
	{
		driver.findElement(By.id("place_bet_submit")).click();
	}
	
	public void logout()
	{
		driver.findElement(By.id("logout")).click();
	}
}
