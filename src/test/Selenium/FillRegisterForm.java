package Selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
		driver.findElement(By.id("register")).click();
	}
	
	public void fillUser(String user)
	{
		driver.findElement(By.id("user")).clear();
		driver.findElement(By.id("user")).sendKeys(user);
		driver.findElement(By.id("register")).click();
	}
	
	public void fillPassword(String pass)
	{
		driver.findElement(By.id("pass")).clear();
		driver.findElement(By.id("pass")).sendKeys(pass);
		driver.findElement(By.id("register")).click();
	}
	
	public void fillName(String name)
	{
		driver.findElement(By.id("name")).clear();
		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("register")).click();
	}
	
	public void fillSurname(String surname)
	{
		driver.findElement(By.id("surname")).clear();
		driver.findElement(By.id("surname")).sendKeys(surname);
		driver.findElement(By.id("register")).click();
	}
	
	public void fillDate(String date)
	{
		driver.findElement(By.id("date")).clear();
		driver.findElement(By.id("date")).sendKeys(date);
		driver.findElement(By.id("register")).click();
	}
	
	public void fillCCN(String ccn)
	{
		driver.findElement(By.id("CCN")).clear();
		driver.findElement(By.id("CCN")).sendKeys(ccn);
		driver.findElement(By.id("register")).click();
	}
	
	public void fillExpiry(String expiry)
	{
		driver.findElement(By.id("expiry")).clear();
		driver.findElement(By.id("expiry")).sendKeys(expiry);
		driver.findElement(By.id("register")).click();
	}
	
	public void fillCVV(String cvv)
	{
		driver.findElement(By.id("CVV")).clear();
		driver.findElement(By.id("CVV")).sendKeys(cvv);
		driver.findElement(By.id("register")).click();
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
