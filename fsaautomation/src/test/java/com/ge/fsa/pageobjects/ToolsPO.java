/*
 *  @author lakshmibs
 */
package com.ge.fsa.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ge.fsa.lib.GenericLib;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class ToolsPO 
{
	public ToolsPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	
	@FindBy(xpath="//div[text()='Tools']")
	private WebElement eleToolsIcn;
	public WebElement getEleToolsIcn()
	{
		return eleToolsIcn;
	}
	@FindBy(xpath="//*[text() = 'Sync Data Now']")
	private WebElement eleSyncDataNowLnk;
	public WebElement getEleSyncDataNowLnk()
	{
		return eleSyncDataNowLnk;
	}
	@FindBy(xpath="//*[text()='Start Sync']")
	private WebElement eleStartSyncBtn;
	public WebElement getEleStartSyncBtn()
	{
		return eleStartSyncBtn;
	}
	@FindBy(xpath="//*[text()='Success']")
	private WebElement eleSuccessTxt;
	public WebElement getEleSuccessTxt()
	{
		return eleSuccessTxt;
	}
	
	@FindBy(xpath="//*[text()='Refreshing view']")
	private WebElement eleRefreshingViewTxt;
	public WebElement getEleRefreshingViewTxt()
	{
		return eleRefreshingViewTxt;
	}
	
	//To sync the data
	public void syncData(CommonsPO commonsPo) throws InterruptedException
	{
		GenericLib.lWaitTime=3*60*1000;
		
		//Navigation to Tools screen
		commonsPo.tap(getEleToolsIcn().getLocation());	
		Assert.assertTrue(getEleSyncDataNowLnk().isDisplayed(), "Tools screen is not displayed");
		NXGReports.addStep("Tools screen is displayed successfully", LogAs.PASSED, null);
		
		getEleSyncDataNowLnk().click();
		commonsPo.tap(getEleSyncDataNowLnk().getLocation());	
		getEleStartSyncBtn().click();
		commonsPo.longPress(getEleStartSyncBtn().getLocation());
		commonsPo.waitforElement(getEleRefreshingViewTxt(),  GenericLib.lWaitTime);
		
		//Verification of successful sync
		Assert.assertTrue(getEleSuccessTxt().isDisplayed(), "Data sync is not successfull");
		NXGReports.addStep("Data Sync is successfull", LogAs.PASSED, null);
	}
}
