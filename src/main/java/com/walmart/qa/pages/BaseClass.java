package com.walmart.qa.pages;

import static org.testng.Assert.fail;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.walmart.qa.base.TestBase;

public class BaseClass extends TestBase {

	@FindBy(xpath = "//input[@name='query']")
	WebElement SearchHeaderBox;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement SubmitButton;

	@FindBy(xpath = "//h1[@class='breadcrumb-leaf']")
	WebElement TV;

	@FindBy(xpath = "//button[contains(text(),'Price')]")
	WebElement Price;

	@FindBy(xpath = "(//input[@name='minPrice'])[1]")
	WebElement MinPrice;

	@FindBy(xpath = "(//input[@name='maxPrice'])[1]")
	WebElement MaxPrice;

	@FindBy(xpath = "(//span[(text()='Go')])[1]")
	WebElement GoButton;

	@FindBy(xpath = "//span[@class='price-characteristic']")
	List<WebElement> PriceCheck;

	@FindBy(xpath = "//button[text()='Top Brands']")
	WebElement Brand;

	@FindBy(xpath = "//input[@name='TCL'])[1]")
	WebElement BrandTCL;

	@FindBy(xpath = "//div[@class='desktop-bar-generic-dropdown']//label//span//span[2]//div//div")
	List<WebElement> TopBrands;

	@FindBy(xpath = "//a[@class='product-title-link line-clamp line-clamp-2']/span")
	List<WebElement> SearchResultsItems;
	
	
	//Initialize the Page Objects
	public BaseClass(){	
		super();
		PageFactory.initElements(driver, this);		
		
	}

	public void URL() throws InterruptedException, Exception {
		System.out.println("inside url method");

		Thread.sleep(5000);
		SearchHeaderBox.click();
		System.out.println("clicking search button");
		SearchHeaderBox.sendKeys(prop.getProperty("searchQuery"));
		Thread.sleep(3000);
		SubmitButton.click();
		Thread.sleep(3000);
		String TV1 = TV.getText();
		String Expected = "All TVs";
		Assert.assertEquals(TV1, Expected, "TV search result is incorrect");

	}

	public void price() throws Exception {
		Thread.sleep(3000);
		Price.click();
		Thread.sleep(3000);
		MinPrice.sendKeys("100");
		Thread.sleep(3000);
		MaxPrice.click();
		Thread.sleep(3000);
		MaxPrice.sendKeys("500");
		Thread.sleep(3000);
		GoButton.click();
		Thread.sleep(3000);

		// Validation
		int PriceCheckValue = PriceCheck.size();
		Thread.sleep(3000);
		Assert.assertTrue(PriceCheckValue > 0);
		Thread.sleep(3000);
		System.out.println("Number of total items : " + PriceCheckValue);
		Thread.sleep(3000);
		for (WebElement P : PriceCheck) {
			System.out.println("Price is :" + P.getText());
			Thread.sleep(3000);
			int PriceValue = Integer.parseInt(P.getText());
			Thread.sleep(10000);
			if (PriceValue < 100 || PriceValue > 500) {
				//takeScreenshot("\\Walmart_appiumDemo\\Screenshot\\PriceFail.png");  
				System.out.println("$" + P.getText() + "  : Not in the Selected Price limit");
				Assert.assertTrue(false);
			}
			
		}
	}

	
	public void verifyBrands() throws Exception {
		Thread.sleep(3000);
		Brand.click();
		Thread.sleep(3000);
		WebElement Brand = Matching(TopBrands, prop.getProperty("Brand"));
		Brand.click();
		Thread.sleep(8000);
		for (WebElement T : SearchResultsItems) {
			System.out.println("Title :" + T.getText());
			// String TitleOfProduct=T.getText();
			String actualString = T.getText();
			Assert.assertTrue(actualString.contains(prop.getProperty("Brand")));

		}

	}

	@Test(priority = 4)
	public void verifyStoreAvailability() throws InterruptedException {
		Thread.sleep(3000);
		// Actions act = new Actions(driver);
		// act.moveToElement(driver.findElement(By.xpath("//button[@aria-label='Refine
		// by Store Availability']"))).doubleClick().build().perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@aria-label='Refine by Store Availability']")).click();
		Thread.sleep(2000);
		// driver.findElement(By.xpath("//button[@aria-label='Refine by Store
		// Availability']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@aria-label='San Leandro, 15555 Hesperian Blvd']")).click();
		Thread.sleep(2000);

	}

	@Test(priority = 5)
	public void verifySortBy() throws InterruptedException {
		Thread.sleep(3000);

		WebElement element = driver.findElement(By.xpath("//select[@class='field-input field-input--primary']"));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		// Actions act1 = new Actions(driver);
		// act1.moveToElement(driver.findElement(By.xpath("//select[@data-automation-id='field']"))).click().build().perform();
		Thread.sleep(5000);
		driver.findElement(By.xpath(
				"//span[@class='elc-icon field-select-arrow field-select-arrow--primary elc-icon-caret-down elc-icon-24']"))
				.click();
		// driver.findElement(By.xpath("//select[@data-automation-id='field']")).click();
		Select drpSort = new Select(driver.findElement(By.xpath("//select[@data-automation-id='field']")));
		drpSort.selectByVisibleText("Price: low to high");
		Thread.sleep(8000);
		Select drpSort1 = new Select(driver.findElement(By.xpath("//select[@data-automation-id='field']")));
		drpSort1.selectByVisibleText("Best Sellers");

		Thread.sleep(5000);

	}

	@Test(priority = 5)
	public void verifyListGrid() throws Exception {
		Thread.sleep(3000);

		driver.findElement(By.xpath("//span[@class='elc-icon active elc-icon-list']")).click();

		Thread.sleep(8000);

		driver.findElement(By.xpath("//span[@class='switcher grid-view-switcher']")).click();

		takeScreenshot("\\AppiumDemo\\Screenshot\\test1.png");

	}

	public static void takeScreenshot(String fileWithPath) throws Exception {
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);

	}

}
