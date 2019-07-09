package com.walmart.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

public class TestBase {

	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebDriver driver;
	

	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("/Users/sheenam_bajaj/Documents/WalmartTesting/src/main/java/com/walmart/qa/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	 public void startUpAppium() throws Exception {
		  
		  DesiredCapabilities capabilities = new DesiredCapabilities();
		 System.out.println("in startUpAppium");
		  
		  capabilities.setCapability("platformName", "iOS");
		  capabilities.setCapability("platformVersion", "11.2.6");
		  capabilities.setCapability("deviceName", "Mayank's iPad");
		  capabilities.setCapability("fullReset", false);
		  capabilities.setCapability("noReset", true);
		  capabilities.setCapability("automationName", "XCUITest");
		  capabilities.setCapability("browserName", "Safari");
		  capabilities.setCapability("udid","3b3eda6476c8a68ce7ba7f60bd9415e06a3c3359");
		  capabilities.setCapability("usePrebuiltWDA", false);
		  capabilities.setCapability("useNewWDA", false);
		  capabilities.setCapability("newCommandTimeout", 300);
		  capabilities.setCapability("safariInitialUrl", "https://www.walmart.com/");
		  System.out.println("Capability done Starting the server");
		  try {
		  
		  driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		 System.out.println("server running");
		  } catch (MalformedURLException e) { // TODO Auto-generated catch block
		  e.printStackTrace(); System.out.println("in Error"); }
		  System.out.println("Connected"); 
		  driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//driver.get("https://www.walmart.com/");
			System.out.println("going child class");
		  }
		
	 
	 public WebElement Matching(List <WebElement> list,String text) throws Exception
	 {
		WebElement SelectedElement=null;
		 for (WebElement P : list) {
				System.out.println("Brand name is :" + P.getText());
				Thread.sleep(3000);
				String BrandName=P.getText();
				if(BrandName.equalsIgnoreCase(text))
				{
					SelectedElement=P;
					break;
				}
				
			}
		 return SelectedElement;
		 
		 
	 }
	}


