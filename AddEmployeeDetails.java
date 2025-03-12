package Orangehrm;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddEmployeeDetails
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
	public void loginHrm()
	{
		WebElement username = driver.findElement(By.id("txtUsername"));
		WebElement password = driver.findElement(By.id("txtPassword"));
		WebElement loginbtn = driver.findElement(By.id("btnLogin"));
		username.sendKeys(config.getProperty("UserName"));
		password.sendKeys(config.getProperty("Password"));
		loginbtn.click();
		
		Assert.assertEquals(true,driver.findElement(By.id("welcome")).isDisplayed());		
	}
	
	@Test(dependsOnMethods="loginHrm", dataProvider="data")
	public void addEmployee(String fname, String mname, String lname) throws Exception 
	{
		WebElement PIM = driver.findElement(By.id("menu_pim_viewPimModule"));
		PIM.click();
		
		WebElement addEmployee = driver.findElement(By.id("menu_pim_addEmployee"));
		addEmployee.click();
		
		
		WebElement firstname 	= driver.findElement(By.id("firstName"));
		WebElement lastname 	= driver.findElement(By.id("lastName"));
		WebElement middlename 	= driver.findElement(By.id("middleName"));
	//	WebElement empId 		= driver.findElement(By.id("employeeId"));
	//	WebElement profileimage = driver.findElement(By.id("photofile"));
		WebElement chcekboxCLD 	= driver.findElement(By.id("chkLogin"));
		WebElement save 		= driver.findElement(By.id("btnSave"));

		firstname.sendKeys(fname);
		middlename.sendKeys(mname);
		lastname.sendKeys(lname);
		
		if (chcekboxCLD.isSelected()) 
		{
			WebElement username 		= driver.findElement(By.id("btnSave"));
			WebElement password 		= driver.findElement(By.id("btnSave"));
			WebElement confirmpassword 	= driver.findElement(By.id("btnSave"));
			WebElement status 			= driver.findElement(By.id("btnSave"));

            username.sendKeys(fname.toLowerCase() + "hrm");
            password.sendKeys(fname+lname+"@123");
            confirmpassword.sendKeys(fname+lname+"@123");
            new Select(status).selectByVisibleText("Enabled");
        }
		save.click();
	}
	
	@DataProvider
	public  Object[][] data() throws Exception
	{
		String FileName=config.getProperty("ExcelFile");
		String SheetName=config.getProperty("SheetName");
		return ExcelData.getData(FileName, SheetName);	  // classname.method name
		
	}
	

	@AfterTest
	public void terminate()
	{
		driver.close();
	}

}
