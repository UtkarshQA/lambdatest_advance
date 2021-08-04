package com.lambdatest.TestCases;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.Meinbeck.Utilities.TestBase;

public class lambdatest_advance extends TestBase{

	String username = System.getenv("LT_USERNAME") == null ? "info.sqa.consultant" : System.getenv("LT_USERNAME"); 
	String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "aQXYjgQyUqEBI0iWA4sbu9NLBT1phYcXB75CZn1o2yQ2WiEhc4" : System.getenv("LT_ACCESS_KEY"); 
	
	
	public static WebDriver driver;
	public static String status = "failed";

	@BeforeTest(alwaysRun = true)
	@Parameters(value = { "browser", "version", "platform" })
	public void setUp(String browser,String version,String platform) throws Exception {

	
		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setCapability("browserName", browser);
		capability.setCapability("version", version);
		capability.setCapability("platform", platform);
		capability.setCapability("build", "Selenium Advanced Certification");
		capability.setCapability("name", "Selenium Advanced Certification");
		capability.setCapability("network", true);
		capability.setCapability("video", true);
		capability.setCapability("console", true);
		capability.setCapability("visual", true);

		String gridURL = "http://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";
		System.out.println(gridURL);
		try {
			driver = new RemoteWebDriver(new URL(gridURL), capability);
		} catch (Exception e) {
			System.out.println("driver error");
			System.out.println(e.getMessage());
		}

	}

	@Test
	public static void test() throws Exception {
	
		JavascriptExecutor js = (JavascriptExecutor) driver;

		driver.get("https://www.lambdatest.com/");

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement ele_integrations = driver
				.findElement(By.xpath("(//a[contains(@href,'https://www.lambdatest.com/integrations')])[1]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele_integrations);
		Thread.sleep(500);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", ele_integrations);

		Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), "LambdaTest Integrations");

		WebElement ele_codelessAutomation = driver.findElement(By.xpath("//h2[text()='Codeless Automation']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele_codelessAutomation);
		Thread.sleep(500);

		Assert.assertTrue(ele_codelessAutomation.isDisplayed());

		driver.findElement(By.xpath("//h3[text()='Testing Whiz']//following-sibling::a[text()='Learn More']")).click();

		Assert.assertEquals(driver.getTitle(), "TestingWhiz Integration | LambdaTest");

		driver.get("https://www.lambdatest.com/blog");

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.linkText("Community")).click();

		Assert.assertEquals(driver.getCurrentUrl(), "https://community.lambdatest.com/");
		status = "passed";
		((JavascriptExecutor) driver).executeScript("lambda-status=" + status + "");
	
	}

	@AfterTest
	public static void afterTest() {
		((JavascriptExecutor) driver).executeScript("lambda-status=" + status + "");
		driver.close();
	}

}