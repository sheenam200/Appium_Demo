package com.walmart.qa.testcases;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.walmart.qa.base.TestBase;
import com.walmart.qa.pages.BaseClass;

public class BaseClassTest extends TestBase {
	
	BaseClass base;
	public BaseClassTest()
	{
		super();
		
	}
	
	@BeforeSuite
	public void chromestartup() throws Exception {
		// initialization();
		startUpAppium();
		base=new BaseClass();

	}
	
	@Test(priority=1)
	public void URLTest() throws Exception, Exception
	{
		base.URL();
	}
	
	
	@Test(priority=2)
	public void PriceTest() throws Exception, Exception
	{
		base.price();
	}
	
	@Test(priority=3)
	public void BrandTest() throws Exception, Exception
	{
		base.verifyBrands();
	}


}
