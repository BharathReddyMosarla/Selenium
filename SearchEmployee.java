package Orangehrm;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SearchEmployee 
{
	WebDriver driver;
	Properties config;

	
	@BeforeClass
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
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
	@Test(dependsOnMethods="loginHrm",dataProvider="data")
	public void search(String fname, String mname, String lname) throws Exception
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement PIM = driver.findElement(By.id("menu_pim_viewPimModule"));
		PIM.click();
		WebElement empname = driver.findElement(By.id("empsearch_employee_name_empName"));
		//WebElement empid = driver.findElement(By.id("empsearch_id"));
		WebElement search = driver.findElement(By.id("searchBtn"));
		//WebElement reset = driver.findElement(By.id("resetBtn"));
		empname.sendKeys(fname+" "+mname+" "+lname);
		//empid.sendKeys("");
		//reset.click();
		search.click();
		
		
		WebElement table = driver.findElement(By.xpath("//*[@id='resultTable']/tbody"));
		List<WebElement> tablerows = table.findElements(By.tagName("tr"));
		int noofrows = tablerows.size();
		if(noofrows==1)
		{
			System.out.println(driver.findElement(By.xpath("//*[@id='resultTable']/tbody/tr/td")).getText());
		}
		else
		{
			for(int i=0;i<noofrows;i++)
			{
				List<WebElement> cells = tablerows.get(i).findElements(By.tagName("td"));
				for(int j=1;j<cells.size();j++)
				{
					System.out.print(cells.get(j).getText() +" ");
				}
				System.out.println();
			}	
		}
		System.out.println("--------");
		
	}
	@DataProvider
	public  Object[][] data() throws Exception
	{
		String FileName=config.getProperty("ExcelFile");
		String SheetName=config.getProperty("SheetName");
		return ExcelData.getData(FileName, SheetName);	  // classname.method name	
	}
	
	@AfterClass
	public void terminate()
	{
		driver.close();
	}
}