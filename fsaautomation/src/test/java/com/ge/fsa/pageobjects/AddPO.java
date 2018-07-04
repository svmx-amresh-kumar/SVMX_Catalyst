/*
 *  @author lakshmibs
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

public class AddPO 
{
	public AddPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	
	@FindBy(xpath="//span[text()='Part']/../../div[2]//input[@class='x-input-el']")
	private WebElement elePartLaborLkUp;
	public WebElement getElePartLaborLkUp()
	{
		return elePartLaborLkUp;
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
	
	@FindBy(xpath="//*[. = 'Line Qty']//*[@class = 'x-input-el']")
	private WebElement eleLineQtyTxtFld;
	public WebElement getEleLineQtyTxtFld()
	{
		return eleLineQtyTxtFld;
	}
	
	@FindBy(xpath="//*[. = 'Line Price Per Unit']//*[@class = 'x-input-el']")
	private WebElement eleLinePerUnitTxtFld;
	public WebElement getEleLinePerUnitTxtFld()
	{
		return eleLinePerUnitTxtFld;
	}
	
	@FindBy(xpath="//*[text() = 'Done']")
	private WebElement eleDoneBtn;
	public WebElement getEleDoneBtn()
	{
		return eleDoneBtn;
	}
	
	@FindBy(xpath="//div[@class='x-size-monitors scroll']")
	private WebElement eleUsePriceToggleBtn;
	public WebElement getEleUsePriceToggleBtn()
	{
		return eleUsePriceToggleBtn;
	}
	
	//To add labor parts
	public void addLaborParts(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sProductName1, String sActivityType) throws InterruptedException
	{	//Adding labor parts name
		commonsPo.tap(workOrderPo.getEleAddLaborLnk().getLocation());
		commonsPo.tap(getElePartLaborLkUp().getLocation());
		commonsPo.longPress(getEleProductNameTxt(sProductName1).getLocation());
		
		//Selecting Activity Type
		commonsPo.pickerWheel(getEleActivityTypeLst(), sActivityType);	
		
		workOrderPo.setTime(commonsPo, workOrderPo.getEleStartDateTimeLst(), 1, "6");  // Sets start date time
		workOrderPo.setTime(commonsPo, workOrderPo.getEleEndDateTimeLst(), 1, "8");    // Sets end date time
		
		//Add the price and quantity
		commonsPo.tap(getEleUsePriceToggleBtn().getLocation());
		getEleLineQtyTxtFld().sendKeys("10");
		getEleLinePerUnitTxtFld().sendKeys("1000");	
		commonsPo.tap(getEleDoneBtn().getLocation());
		
		//Verify to Manage WO lines
		Assert.assertTrue(workOrderPo.getEleManageWOLinesTxt().isDisplayed(), "Failed to add Labor parts");   
		NXGReports.addStep("Labor parts are added and saved successfully. ", LogAs.PASSED, null);		
	}
	
	//To add Travel
		public void addTravel(CommonsPO commonsPo, WorkOrderPO workOrderPo) throws InterruptedException
		{	//Adding labor parts name
			commonsPo.tap(workOrderPo.getEleAddTravelLnk().getLocation());
		
			workOrderPo.setTime(commonsPo, workOrderPo.getEleStartDateTimeLst(), 1, "5");  // Sets start date time
			workOrderPo.setTime(commonsPo, workOrderPo.getEleEndDateTimeLst(), 1, "9");    // Sets end date time
			
			//Add the price and quantity
			commonsPo.tap(getEleUsePriceToggleBtn().getLocation());
			getEleLineQtyTxtFld().sendKeys("10");
			getEleLinePerUnitTxtFld().sendKeys("1000");	
			commonsPo.tap(getEleDoneBtn().getLocation());
			
			//Verify to Manage WO lines
			Assert.assertTrue(workOrderPo.getEleManageWOLinesTxt().isDisplayed(), "Failed to add Labor parts");   
			NXGReports.addStep("Labor parts are added and saved successfully. ", LogAs.PASSED, null);		
		}
}
