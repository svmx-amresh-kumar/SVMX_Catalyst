/*
 *  @author MeghanaRao
 */
package com.ge.fsa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class CreateNewPO 
{
	public CreateNewPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	
	@FindBy(xpath="//div[@class='svmx-menu-icon-label'][text()='Create New']")
	private WebElement eleCreateNew;
	public WebElement getEleCreateNew()
	{
		return eleCreateNew;
	}
	
	private WebElement eleProductNameTxt;
	public WebElement getEleProductNameTxt(String sProductName)
	{
		eleProductNameTxt=driver.findElement(By.xpath("//div[@class='x-inner-el'][text()='"+sProductName+"']"));
		return eleProductNameTxt;
	}
	
	@FindBy(xpath="//*[. = 'Activity Type']//input")
	private WebElement eleActivityTypeLst;
	public WebElement getEleActivityTypeLst()
	{
		return eleActivityTypeLst;
	}
	
	@FindBy(xpath="//div[text()='Create Work Order']")
	private WebElement eleCreateNewWorkOrder;
	public WebElement getEleCreateNewWorkOrder()
	{
		return eleCreateNewWorkOrder;
	}
	
	@FindBy(xpath="//div[. = 'Account']//input[@class = 'x-input-el']")
	private WebElement eleClickAccountfield;
	public WebElement getEleClickAccountfield()
	{
		return eleClickAccountfield;
	}
	
	@FindBy(xpath="//div[. = 'Contact']//input[@class = 'x-input-el']")
	private WebElement eleClickContactfield;
	public WebElement getEleClickContactfield()
	{
		return eleClickContactfield;
	}
	
	@FindBy(xpath="//div[. = 'Product']//input[@class = 'x-input-el']")
	private WebElement eleClickProductfield;
	public WebElement getEleClickProductfield()
	{
		return eleClickProductfield;
	}
	
	
	
	
	
	
	
	//To add Travel
		public void addTravel(CommonsPO commonsPo, WorkOrderPO workOrderPo) throws InterruptedException
		{	//Adding labor parts name
			commonsPo.tap(workOrderPo.getEleAddTravelLnk());
		
			workOrderPo.setTime(commonsPo, workOrderPo.getEleStartDateTimeLst(), 1, "5");  // Sets start date time
			workOrderPo.setTime(commonsPo, workOrderPo.getEleEndDateTimeLst(), 1, "9");    // Sets end date time
			

			//Verify to Manage WO lines
			Assert.assertTrue(workOrderPo.getEleManageWOLinesTxt().isDisplayed(), "Failed to add Labor parts");   
			NXGReports.addStep("Labor parts are added and saved successfully. ", LogAs.PASSED, null);		
		}
}
