/*
 *  @author lakshmibs
 */
package com.ge.fsa.pageobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
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

public class WorkOrderPO {

	public WorkOrderPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	int iWhileCnt =0;
	int i=0;
	
	@FindBy(xpath="//span[text() = 'Actions']")
	private WebElement eleActionsLnk;
	public WebElement getEleActionsLnk()
	{
		return eleActionsLnk;
	}
	
	private WebElement eleActionsTxt;
	public WebElement getEleActionsTxt(String sActionsName)
	{
		eleActionsTxt=driver.findElement(By.xpath("//div[@class='x-component x-button x-button-svmx-menu-button x-component-svmx-menu-button x-button-no-icon x-layout-box-item x-layout-vbox-item x-stretched x-widthed']//span[text()='"+sActionsName+"']"));
		return eleActionsTxt;
	}
	
	@FindBy(xpath="//div[contains(text(),'Labor (')]/../../../../..//span[text()='Add']")
	private WebElement eleAddLaborLnk;
	public WebElement getEleAddLaborLnk()
	{
		return eleAddLaborLnk;
	}
	
	@FindBy(xpath="//*[contains(text(),'Travel (')]/../../../../..//*[contains(text(),'Add')]")
	private WebElement eleAddTravelLnk;
	public WebElement getEleAddTravelLnk()
	{
		return eleAddTravelLnk;
	}
		
	@FindBy(xpath="//span[text()='Manage Work Order Lines - Usage']")
	private WebElement eleManageWOLinesTxt;
	public WebElement getEleManageWOLinesTxt()
	{
		return eleManageWOLinesTxt;
	}
	
	@FindBy(xpath="//*[text() = 'Save']")
	private WebElement eleSaveLnk;
	public WebElement getEleSaveLnk()
	{
		return eleSaveLnk;
	}
	
	@FindBy(xpath="//input[@value='Done']")
	private WebElement eleDoneLnk;
	public WebElement getEleDoneLnk()
	{
		return eleDoneLnk;
	}
	
	private WebElement eleWONumberTxt;
	public WebElement getEleWONumberTxt(String sWorkOrder)
	{
		eleWONumberTxt=driver.findElement(By.xpath("//div[@class='content']/p[text()=': "+sWorkOrder+"']"));
		return eleWONumberTxt;
	}
	
	@FindBy(xpath="//label[@class='opdoc-title'][text()='Work Order Service Report']")
	private WebElement eleWOServiceReportTxt;
	public WebElement getEleWOServiceReportTxt()
	{
		return eleWOServiceReportTxt;
	}
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='New Event']")
	private WebElement eleNewEventTxt;
	public WebElement getEleNewEventTxt()
	{
		return eleNewEventTxt;
	}
	@FindBy(xpath="//*[contains(text(),'Subject')][@class = 'x-label-text-el']/../..//input")
	private WebElement eleSubjectTxtFld;
	public WebElement getEleSubjectTxtFld()
	{
		return eleSubjectTxtFld;
	}
	
	@FindBy(xpath="//*[contains(text(),'Description')][@class = 'x-label-text-el']/../..//textarea")
	private WebElement eleDescriptionTxtFld;
	public WebElement getEleDescriptionTxtFld()
	{
		return eleDescriptionTxtFld;
	}
	@FindBy(xpath="//*[contains(text(),'Start Date and Time')][@class = 'x-label-text-el']/../..//input")
	private WebElement eleStartDateTimeLst;
	public WebElement getEleStartDateTimeLst()
	{
		return eleStartDateTimeLst;
	}
	
	@FindBy(xpath="//*[contains(text(),'End Date and Time')][@class = 'x-label-text-el']/../..//input")
	private WebElement eleEndDateTimeLst;
	public WebElement getEleEndDateTimeLst()
	{
		return eleEndDateTimeLst;
	}
	@FindBy(xpath="//XCUIElementTypePickerWheel[@type='XCUIElementTypePickerWheel']")	
	private List<WebElement> eleDatePickerPopup;
	public  List<WebElement> getEleDatePickerPopUp()
	{
		return eleDatePickerPopup;
	}
	public void setTime(CommonsPO commonsPo, WebElement element, int iDay, String sTime) throws InterruptedException
	{
		element.click();
		commonsPo.switchContext("Native");
		datePicker( 0,iDay);
		timeSetter(1, sTime);
		commonsPo.getEleDonePickerWheelBtn().click();
		commonsPo.switchContext("Webview");
		Thread.sleep(GenericLib.iLowSleep);
	}
	public void datePicker(int iIndex, int scrollNum)
	{ 	i=0;
		for(i=0;i<scrollNum;i++)
		{JavascriptExecutor js = (JavascriptExecutor) driver;
	    Map<String, Object> params = new HashMap<>();
	    params.put("order", "next");
	    params.put("offset", 0.15);
	    params.put("element", (getEleDatePickerPopUp().get(iIndex)));
	    js.executeScript("mobile: selectPickerWheelValue", params);	
		}
	}
	
	public void timeSetter(int iIndex, String sTime )
	{
		getEleDatePickerPopUp().get(1).sendKeys(sTime);
		getEleDatePickerPopUp().get(2).sendKeys("00");
		getEleDatePickerPopUp().get(3).sendKeys("PM");
	}
	
	public void navigateToActions(CommonsPO commonsPo, String sActionsName) throws InterruptedException
	{
		getEleActionsLnk().click();
		commonsPo.tap(getEleActionsLnk().getLocation());	
		commonsPo.getSearch(getEleActionsTxt(sActionsName));
		commonsPo.tap(getEleActionsTxt(sActionsName).getLocation());
		
	}
	public void createNewEvent(CommonsPO commonsPo, String sSubject, String sDescription) throws InterruptedException
	{
		navigateToActions(commonsPo, "New Event");
		Assert.assertTrue(getEleNewEventTxt().isDisplayed(), "New Event screen is not displayed");
		NXGReports.addStep("New Event screen is displayed successfully", LogAs.PASSED, null);		
		
		setTime(commonsPo, getEleStartDateTimeLst(), 1,"6"); //set start time
		setTime(commonsPo, getEleEndDateTimeLst(), 1,"8"); //set end time
		getEleSubjectTxtFld().sendKeys(sSubject);
		//getEleDescriptionTxtFld().click();
		//getEleDescriptionTxtFld().sendKeys(sDescription);
		commonsPo.tap(getEleSaveLnk().getLocation());
		Assert.assertTrue(getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		NXGReports.addStep("Creation of WO event is successfull and Work Order Screen is displayed successfully", LogAs.PASSED, null);		
	}
	public void validateServiceReport(CommonsPO commonsPo, String sPrintReportSearch, String sWorkOrderID ) throws InterruptedException
	{	
		navigateToActions(commonsPo, sPrintReportSearch);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(getEleWOServiceReportTxt().isDisplayed(), "Work Order Service Report is not displayed.");
		NXGReports.addStep("Work Order Service Report is displayed successfully", LogAs.PASSED, null);		
		Assert.assertTrue(getEleWONumberTxt(sWorkOrderID).isDisplayed(),"WO updated report details is not displayed");
		NXGReports.addStep("Work order updated details for the work order "+sWorkOrderID, LogAs.PASSED, null);	
		getEleDoneLnk().click();
		commonsPo.tap(getEleDoneLnk().getLocation());
		Thread.sleep(GenericLib.iLowSleep);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		Thread.sleep(GenericLib.iLowSleep);
	
		//Navigation back to Work Order after Service Report
		Assert.assertTrue(getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		NXGReports.addStep("Creation of WO event is successfull and Work Order Screen is displayed successfully", LogAs.PASSED, null);		
	}
}
