package com.ge.fsa.iphone.pageobjects;


import static org.testng.Assert.assertFalse;

import java.util.Iterator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.ExploreSearchPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ip_MorePO
{
	public Ip_MorePO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	

	@FindBy(id="More")
	private WebElement eleMoreBtn;
	public WebElement getEleMoreBtn()
	{
		return eleMoreBtn;
	}
	
	@FindBy(id="Sync ")
	private WebElement eleSyncBtn;
	public WebElement getEleSyncBtn()
	{
		return eleMoreBtn;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name='Run Config Sync Run Config Sync'])[3]")
	private WebElement eleRunConfigSync;
	public WebElement getEleRunConfigSync()
	{
		return eleRunConfigSync;
	}
	
	@FindBy(id="Perform Config Sync")
	private WebElement elePerformConfigSync;
	public WebElement getlePerformConfigSync()
	{
		return elePerformConfigSync;
	}
	
public void configSync(CommonsPO commonsPo) throws InterruptedException {
	getEleMoreBtn().click();
	getEleSyncBtn().click();
	getEleRunConfigSync().click();
	getlePerformConfigSync().click();
	commonsPo.waitforElement(getEleMoreBtn(), 200);
	assertFalse(getEleRunConfigSync().isDisplayed(), "Sync not done");
	ExtentManager.logger.log(Status.PASS,"Config Sync Completed sucessfully");
}

	
}
