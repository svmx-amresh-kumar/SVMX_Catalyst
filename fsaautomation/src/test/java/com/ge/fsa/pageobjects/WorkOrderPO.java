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
	
	@FindBy(xpath="//*[contains(text(),'Expenses (')]/../../../../..//*[contains(text(),'Add')]")
	private WebElement eleAddExpenseLnk;
	public WebElement getEleAddExpenseLnk()
	{
		return eleAddExpenseLnk;
	}
	
	
	@FindBy(xpath="//span[text()='Expense Type']/../..//div[@class='x-mask-el']")
	private WebElement eleAddExpenseType;
	public WebElement getEleAddExpenseType()
	{
		return eleAddExpenseType;
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
	
	@FindBy(xpath="(//div[contains(text(), 'Parts')][@class='x-panel-title-text']/../../../..//div[@class='x-cells-el'])[1]")

	
	private WebElement eleChildLinesadded;
	public WebElement getEleChildLinesadded(String childLineName)
	{
		
		//eleChildLinesadded = driver.findElement(By.xpath("(//div[contains(text(), 'Labor')][@class='x-panel-title-text']/../../../..//div[contains(text(),'"+childLineName+"')])[2]/../.."));
		eleChildLinesadded = driver.findElement(By.xpath("//div[contains(text(), 'rrrr')]/../div[2]"));

		return eleChildLinesadded;
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
	

	@FindBy(xpath="//*[text() = 'Yes']")
	private WebElement eleYesBtn;
	public WebElement getEleYesBtn()
	{
		return eleYesBtn;
	}
	
	
	private WebElement eledeleteChildline;
	public WebElement getEledeletechildline(String childlinevalue)
	{

		eledeleteChildline = driver.findElement(By.xpath("(//div[@class='x-inner-el'][contains(text(),'"+childlinevalue+"')])[2]"));

		return eledeleteChildline;
	}
	
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='Remove item']")
	private WebElement eleremoveitem;
	public  WebElement getEleremoveitem()
	{
		
		return eleremoveitem;
	}

	
	@FindBy(xpath="//span[@class='x-button-label'][text()='Yes']")
	private WebElement eleclickyes;
	public  WebElement getEleclickyesitem()
	{
		
		return eleclickyes;
	}

	@FindBy(xpath="//span[@class='x-button-label'][text()='OK']")
	private WebElement eleclickOK;
	public  WebElement getEleclickOK()
	{
		
		return eleclickOK;
	}
	
	// Saving the Child Line records
	@FindBy(xpath="//span[@class='x-button-label'][text()='Save']")
	private WebElement eleClickSave;
	public  WebElement getEleClickSave()
	{
		
		return eleClickSave;
	}
	//Verifying the Workorder name after saving the value
	private WebElement eleworkordernameonUI;
	public WebElement getEleworkordernameonUI(String workordername)
	{

		eleworkordernameonUI = driver.findElement(By.xpath("//div[@class='x-innerhtml'][text()='"+workordername+"']"));

		return eleworkordernameonUI;
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
	
	public void selectAction(CommonsPO commonsPo, String sActionsName) throws InterruptedException
	{
		Thread.sleep(1000);
		getEleActionsLnk().click();
		commonsPo.tap(getEleActionsLnk());	
		commonsPo.getSearch(getEleActionsTxt(sActionsName));
		commonsPo.tap(getEleActionsTxt(sActionsName));
		
	}
	public void createNewEvent(CommonsPO commonsPo, String sSubject, String sDescription) throws InterruptedException
	{
		selectAction(commonsPo, "New Event");
		Assert.assertTrue(getEleNewEventTxt().isDisplayed(), "New Event screen is not displayed");
		NXGReports.addStep("New Event screen is displayed successfully", LogAs.PASSED, null);		
		
		setTime(commonsPo, getEleStartDateTimeLst(), 0,"6"); //set start time
		setTime(commonsPo, getEleEndDateTimeLst(), 0,"8"); //set end time
		getEleSubjectTxtFld().sendKeys(sSubject);
		//getEleDescriptionTxtFld().click();
		//getEleDescriptionTxtFld().sendKeys(sDescription);
		commonsPo.tap(getEleSaveLnk());
		try {
		if(getEleYesBtn() != null){
			commonsPo.tap(getEleYesBtn());	
		}
		}
		catch(Exception e){
			
		}
		Assert.assertTrue(getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		NXGReports.addStep("Creation of WO event is successfull and Work Order Screen is displayed successfully", LogAs.PASSED, null);		
	}
	public void validateServiceReport(CommonsPO commonsPo, String sPrintReportSearch, String sWorkOrderID ) throws InterruptedException
	{	
		selectAction(commonsPo, sPrintReportSearch);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(getEleWOServiceReportTxt().isDisplayed(), "Work Order Service Report is not displayed.");
		NXGReports.addStep("Work Order Service Report is displayed successfully", LogAs.PASSED, null);		
		Assert.assertTrue(getEleWONumberTxt(sWorkOrderID).isDisplayed(),"WO updated report details is not displayed");
		NXGReports.addStep("Work order updated details for the work order "+sWorkOrderID, LogAs.PASSED, null);	
		getEleDoneLnk().click();
		commonsPo.tap(getEleDoneLnk());
		Thread.sleep(GenericLib.iLowSleep);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		Thread.sleep(GenericLib.iLowSleep);
	
		//Navigation back to Work Order after Service Report
		Assert.assertTrue(getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		NXGReports.addStep("Creation of WO event is successfull and Work Order Screen is displayed successfully", LogAs.PASSED, null);		
	}
	
	
	
	//To add labor parts
	public void addLaborParts(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sProductName1, String sActivityType) throws InterruptedException
	{	//Adding labor parts name
		commonsPo.tap(workOrderPo.getEleAddLaborLnk());
		commonsPo.tap(getElePartLaborLkUp());
		commonsPo.longPress(getEleProductNameTxt(sProductName1));
		
		//Selecting Activity Type
		commonsPo.pickerWheel( getEleActivityTypeLst(), sActivityType);	
		
		workOrderPo.setTime(commonsPo, workOrderPo.getEleStartDateTimeLst(), 1, "6");  // Sets start date time
		workOrderPo.setTime(commonsPo, workOrderPo.getEleEndDateTimeLst(), 1, "8");    // Sets end date time
		
		//Add the price and quantity
		commonsPo.tap(getEleUsePriceToggleBtn());
		getEleLineQtyTxtFld().sendKeys("10");
		getEleLinePerUnitTxtFld().sendKeys("1000");	
		commonsPo.tap(getEleDoneBtn());
		
		//Verify to Manage WO lines
		Assert.assertTrue(workOrderPo.getEleManageWOLinesTxt().isDisplayed(), "Failed to add Labor parts");   
		NXGReports.addStep("Labor parts are added and saved successfully. ", LogAs.PASSED, null);		
	}
	
	//To add Travel
		public void addTravel(CommonsPO commonsPo, WorkOrderPO workOrderPo) throws InterruptedException
		{	//Adding labor parts name
			commonsPo.tap(workOrderPo.getEleAddTravelLnk());
		
			workOrderPo.setTime(commonsPo, workOrderPo.getEleStartDateTimeLst(), 1, "5");  // Sets start date time
			workOrderPo.setTime(commonsPo, workOrderPo.getEleEndDateTimeLst(), 1, "9");    // Sets end date time
			
			//Add the price and quantity
			commonsPo.tap(getEleUsePriceToggleBtn());
			getEleLineQtyTxtFld().sendKeys("10");
			getEleLinePerUnitTxtFld().sendKeys("1000");	
			commonsPo.tap(getEleDoneBtn());
			
			//Verify to Manage WO lines
			Assert.assertTrue(workOrderPo.getEleManageWOLinesTxt().isDisplayed(), "Failed to add Labor parts");   
			NXGReports.addStep("Labor parts are added and saved successfully. ", LogAs.PASSED, null);		
		}
		
		
	// To add Expense
		
		public void addExpense(CommonsPO commonsPo, WorkOrderPO workOrderPo,String expenseType, String expensetype) throws InterruptedException
		{	//Adding Expense name
			commonsPo.tap(workOrderPo.getEleAddExpenseLnk());
			commonsPo.tap(workOrderPo.getEleAddExpenseType());
			commonsPo.pickerWheel(getEleAddExpenseType(), expensetype);
			commonsPo.tap(getEleDoneBtn());

			//Add the price and quantity
			commonsPo.tap(getEleUsePriceToggleBtn());
			getEleLineQtyTxtFld().sendKeys("10");
			getEleLinePerUnitTxtFld().sendKeys("1000");	
			commonsPo.tap(getEleDoneBtn());
			
			//Verify to Manage WO lines
			Assert.assertTrue(workOrderPo.getEleManageWOLinesTxt().isDisplayed(), "Failed to add Labor parts");   
			NXGReports.addStep("Labor parts are added and saved successfully. ", LogAs.PASSED, null);		
		}
		
		
		// Delete the Childlines
		public void deletechildlines(CommonsPO commonsPo, WorkOrderPO workOrderPo, String childlinesvalue, String workordervalue) throws InterruptedException {
			commonsPo.tap(workOrderPo.getEledeletechildline(childlinesvalue));
			commonsPo.tap(workOrderPo.getEleremoveitem());
			commonsPo.tap(workOrderPo.getEleclickyesitem());
			commonsPo.tap(workOrderPo.getEleclickOK());
			Thread.sleep(10000);
			commonsPo.tap(workOrderPo.getEleClickSave());
			Thread.sleep(10000);
			if(workOrderPo.getEleworkordernameonUI(workordervalue) != null)
			{
				System.out.println("Chidlines are saved");
			
			}
			else 
			{
				System.err.println("Chidlines are not saved");
				
			}
			
		}
		
		// Edit the ChildLines and save them
		
		
}


