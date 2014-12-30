package Selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



public class FillRegisterForm {
	
	WebDriver driver;
	
	public FillRegisterForm(WebDriver driver)
	{
		this.driver = driver;
	}
	
	public void visitRegister()
	{
		driver.get("http://localhost:8080/Software.Testing/");
		driver.findElement(By.xpath("/html/body/div/div/ul/li[2]/a")).click();
		//driver.findElement(By.cssSelector("a[href='register']")).click();
	}
	
	public void fillForm()
	{
		driver.findElement(By.id("user")).sendKeys("TestDroid");
		driver.findElement(By.id("pass")).sendKeys("password");
		driver.findElement(By.id("name")).sendKeys("Brian");
		driver.findElement(By.id("surname")).sendKeys("Spiteri");
		driver.findElement(By.id("date")).sendKeys("1994-06-06");
		driver.findElement(By.id("CCN")).sendKeys("5500005555555559");
		driver.findElement(By.id("expiry")).sendKeys("2017-12");
		driver.findElement(By.id("CVV")).sendKeys("123");
	}
	
	public void fillUser(String user)
	{
		driver.findElement(By.id("user")).sendKeys(user);
	}
	
	public void fillPassword(String pass)
	{
		driver.findElement(By.id("pass")).sendKeys(pass);
	}
	
	public void fillName(String name)
	{
		driver.findElement(By.id("name")).sendKeys(name);
	}
	
	public void fillSurname(String surname)
	{
		driver.findElement(By.id("surname")).sendKeys(surname);
	}
	
	public void fillDate(String date)
	{
		driver.findElement(By.id("date")).sendKeys(date);
	}
	
	public void fillCCN(String ccn)
	{
		driver.findElement(By.id("CCN")).sendKeys(ccn);
	}
	
	public void fillExpiry(String expiry)
	{
		driver.findElement(By.id("expiry")).sendKeys(expiry);
	}
	
	public void fillCVV(String cvv)
	{
		driver.findElement(By.id("CVV")).sendKeys(cvv);
	}
	
	public void submit()
	{
		driver.findElement(By.id("register_submit")).click();
	}
	
	
	public void exit()
	{
		driver.quit();
	}
	

}
