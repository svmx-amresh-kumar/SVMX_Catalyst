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
	
	
	@FindBy(xpath="//*[@*='TAB_BAR.RECENTS_TAB']")
	private WebElement eleClickRecentItems;
	public WebElement getEleClickRecentItems()
	{
		return eleClickRecentItems;
	}
	
	public WebElement getEleWorkorder(String sValue)
	{
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
		
			return driver.findElement(By.xpath("//*[@text='"+sValue+"']"));
		}else {
			//return  driver.findElement(By.xpath("//*[contains(@label,'"+sValue+"')]//*[contains(@name,'I')]"));
			return  driver.findElement(By.xpath("//*[contains(@label,'"+sValue+"')][contains(@name,'I')]/*[contains(@name,'I')]//*[contains(@label,'"+sValue+"')][contains(@name,'I')]"));
		}
		
	}
	
	


	public void selectRecentsItem(CommonUtility commonUtility, String sValue) throws InterruptedException 
	{
		getEleClickRecentItems().click();
		Thread.sleep(1000);
		commonUtility.custScrollToElementAndClick(getEleWorkorder(sValue));

	}


}	

