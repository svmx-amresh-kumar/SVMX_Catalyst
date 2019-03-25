package com.ge.fsa.pageobjects.phone;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ph_RecentsItemsPO
{
	public Ph_RecentsItemsPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	
	
	@FindAll({@FindBy(xpath="//*[@text='Recents']"),
	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Recents, tab, 3 of 4\"]")})
	private WebElement eleClickRecentItems;
	public WebElement getEleClickRecentItems()
	{
		return eleClickRecentItems;
	}
	
	private WebElement eleWorkorderNumberClick;
	public WebElement getEleWorkordernumberclick(String workordername)
	{
		switch(BaseLib.sOSName.toLowerCase()) {
		case "android":
		eleWorkorderNumberClick = driver.findElement(By.xpath("//*[@text='"+workordername+"']"));
		return eleWorkorderNumberClick;
		case "ios":
			eleWorkorderNumberClick = driver.findElement(By.xpath("(//XCUIElementTypeOther[@name=\"Recents\"])[12]/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]"));
			return eleWorkorderNumberClick;
		}
		return eleWorkorderNumberClick;
		
	}
	
	


	public void clickonWorkOrderfromrecents(String workordername) throws InterruptedException 
	{
		getEleClickRecentItems().click();
		Thread.sleep(1000);
		try
		{
			getEleWorkordernumberclick(workordername).click();
	
		}
		catch(Exception e)
		{
			System.out.println("unable to find the workorder");
		}
		

	}


}	

