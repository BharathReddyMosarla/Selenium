package Orangehrm;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class login 
{
	WebDriver driver;
	Properties config;

	
	@BeforeTest
	public void SetUp() throws Exception
	{
		config = new Properties();
		File file = new File("/Users/bharathreddymosarla/eclipse-workspace/TestNG/src/logindata.properties");
		FileInputStream fis= new FileInputStream(file);
		config.load(fis);
		System.setProperty(config.getProperty("DriverName"), config.getProperty("DriverPath"));
        driver = new ChromeDriver();
		driver.get(config.getProperty("URL"));
		driver.manage().window().maximize();
	}
	
	@Test
	public void loginHrm() throws Exception
	{
		WebElement username = driver.findElement(By.id("txtUsername"));
		WebElement password = driver.findElement(By.id("txtPassword"));
		WebElement loginbtn = driver.findElement(By.id("btnLogin"));
		username.sendKeys(config.getProperty("UserName"));
		password.sendKeys(config.getProperty("Password"));
		loginbtn.click();
		
		Assert.assertEquals(true,driver.findElement(By.id("welcome")).isDisplayed());		
	}
	

	@AfterTest
	public void terminate()
	{
		driver.close();
	}
   
}
