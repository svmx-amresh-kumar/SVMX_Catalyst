package com.ge.fsa.pageobjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

import bsh.ParseException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class WorkOrderPO{

	public WorkOrderPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	AppiumDriver<WebElement> driver = null;
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
	
	@FindBy(xpath="//div[contains(text(),'Parts (')]/../../../../..//span[text()='Add']")
	private WebElement elePartLnk;
	public WebElement getElePartLnk()
	{
		return elePartLnk;
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
	
	
	@FindBy(xpath="//span[text()='Expense Type']/../..//input[@class='x-input-el']")
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
	
	private WebElement eleProcessName;
	public WebElement getEleProcessName(String sprocessname)
	{
		eleProcessName=driver.findElement(By.xpath("//span[text()='"+sprocessname+"']"));
		return eleProcessName;
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
	
	//@FindBy(xpath="//label[@class='opdoc-title'][text()='Work Order Service Report']")
	private WebElement eleWOServiceReportTxt;
	public WebElement getEleWOServiceReportTxt(String sReportTitle)
	{
		eleWOServiceReportTxt=driver.findElement(By.xpath("//label[@class='opdoc-title'][text()='"+sReportTitle+"']"));
		return eleWOServiceReportTxt;

	}
	
	
	/*@FindBy(xpath="//label[@class='opdoc-title'][text()='Work Order Service Report']")
	private WebElement eleWOServiceReportTxt;
	public WebElement getEleWOServiceReportTxt()
	{
		return eleWOServiceReportTxt;
	}*/
	
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
		eleProductNameTxt=driver.findElement(By.xpath("//div[@class='x-listitem x-gridrow x-component x-has-height x-heighted x-layout-auto-item x-first x-last']//div[@class='x-inner-el'][text()='"+sProductName+"']"));
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
	
	
	private WebElement eledeletepartChildline;
	public WebElement getEledeletepartchildline(String childlinevalue)
	{

		eledeletepartChildline = driver.findElement(By.xpath("(//div[@class='x-inner-el'][contains(text(),'"+childlinevalue+"')])[2]"));

		return eledeletepartChildline;
	}
	
	private WebElement eledeletelaborChildline;
	public WebElement getEledeletelaborChildline(String childlinevalue)
	{

		eledeletelaborChildline = driver.findElement(By.xpath("(//div[@class='x-inner-el'][contains(text(),'"+childlinevalue+"')])[23"));

		return eledeletelaborChildline;
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
	

	private WebElement eleclickparts;
	public WebElement getEleclickparts(String partsname)
	{

		eleclickparts = driver.findElement(By.xpath("(//div[@class='x-gridcell']//div[text()='"+partsname+"'])[1]"));

		return eleclickparts;
	}
	
	// Added by Meghana
	@FindBy(xpath="//span[@class='x-label-text-el'][text()='IB Serial Number']/../..//div[@class='x-mask-el']")
	private WebElement eleIbSerialnumTap;
	public WebElement getEleIbSerialnumTap()
	{
		return eleIbSerialnumTap;
	}

	

	public List<WebElement> getEleIBSerialNumber()
	{

		List<WebElement> eleIBSerialNumber = driver.findElements(By.xpath("//div[@class='x-inner-el'][contains(text(),'IB')]"));

		return eleIBSerialNumber;
	}
	
	
	private WebElement eleIBId;
	public WebElement getEleeleIBId(String sInstalledProductId)
	{

		eleIBId = driver.findElement(By.xpath("//div[@class='x-inner-el'][text()='"+sInstalledProductId+"']"));

		return eleIBId;
	}
	

	@FindBy(xpath="(//div[. = 'Product']//input[@class = 'x-input-el'])[2]")
	private WebElement eleProductLookup;
	public  WebElement getEleProductLookup()
		{
				
			return eleProductLookup;
		}
			

	
	// Saving the Child Line records
	@FindBy(xpath="//span[@class='x-button-label'][text()='Save']")
	private WebElement eleClickSave;
	public  WebElement getEleClickSave()
	{
		
		return eleClickSave;
	}
	
	// Add selected button

	@FindBy(xpath="//span[@class='x-button-label'][text()='Add Selected']")
	private WebElement eleAddselectedbutton;
	public  WebElement getEleAddselectedbutton()
	{
		
		return eleAddselectedbutton;
	}
	
	// Added by Meghana

	private WebElement eleTaponParts;
	public WebElement getEleTaponParts(String sProductName)
	{

		eleTaponParts = driver.findElement(By.xpath("//div[@class='x-inner-el'][text()='"+sProductName+"']"));

		return eleTaponParts;
	}
	
	
	//Verifying the Workorder name after saving the value
	private WebElement eleworkordernameonUI;
	public WebElement getEleworkordernameonUI(String workordername)
	{

		eleworkordernameonUI = driver.findElement(By.xpath("//div[@class='x-innerhtml'][text()='"+workordername+"']"));

		return eleworkordernameonUI;
	}
	
	
	//Verifying the OrderStatus of workorder
	@FindBy(xpath="//span[@class='x-label-text-el'][text()='Order Status']/../..//div[@class='x-innerhtml']")
	private WebElement eleOrderStatusvaluelbl;
	public WebElement geteleOrderStatusvaluelbl()
	{
		return eleOrderStatusvaluelbl;
	}
	
	
	//verifying ProblemDescription in WorkOrder
	@FindBy(xpath="//span[@class='x-label-text-el'][text()='Problem Description']/../..//div[@class='x-innerhtml']/span")
	private WebElement eleProblemDescriptionlbl;
	public WebElement geteleProblemDescriptionlbl()
	{
		return eleProblemDescriptionlbl;
	}

	@FindBy(xpath="//div[@class='x-innerhtml'][text()='This record does not meet the qualification criteria for this SFM Transaction']")
	private WebElement eleThisRecordDoesNotPopup;
	public  WebElement getEleThisRecordDoesNotPopup()
	{
		
		return eleThisRecordDoesNotPopup;
	}
	
	@FindBy(xpath="//span[text()='OK']")
	private WebElement eleOKBtn;
	public  WebElement getEleOKBtn()
	{
		
		return eleOKBtn;
	}
	
	@FindBy(xpath="(//*[text()='Billing Type']/../..//div[@class='x-input-body-el']/input)[2]")
	private WebElement eleBillingTypeLst;
	public WebElement getEleBillingTypeLst()
	{
		return eleBillingTypeLst;
	}
	
	
	private WebElement elePicklistValue;
	public WebElement  getelePicklistValue(String PicklistValue)
	{
		return elePicklistValue = driver.findElement(By.xpath("//*[text()='"+PicklistValue+"']/../..//div[@class='x-input-body-el']/input)"));
	}
	
	@FindBy(xpath="//div[contains(text(),'Issue found')]")
	private WebElement eleIssueFoundTxt;
	public WebElement getEleIssueFoundTxt()
	{
		return eleIssueFoundTxt;
	}
	
	private WebElement eleIssuePopupTxt;
	public WebElement getEleIssuePopupTxt(String sIssueTxt)
	{
		eleIssuePopupTxt = driver.findElement(By.xpath("//span[@class='x-button-label'][text()='"+sIssueTxt+"']"));
		return eleIssuePopupTxt;
	}
	
	@FindBy(xpath="//*[contains(text(),'Saved successfully')]")
	private WebElement eleSavedSuccessTxt;
	public WebElement getEleSavedSuccessTxt()
	{
		return eleSavedSuccessTxt;
	}
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='Discard Changes']")
	private WebElement eleDiscardBtn;
	public  WebElement getEleDiscardBtn()
	{
		return eleDiscardBtn;
	}
	@FindBy(xpath="//*[text() = 'Cancel']")
	private WebElement eleCancelLnk;
	public WebElement getEleCancelLnk()
	{
		return eleCancelLnk;
	}
	
	// Added by Harish.CS
	private WebElement eleOnTreeView;
	public WebElement getEleOnTreeView(String eleName) {
	eleOnTreeView = driver.findElement(By.xpath("//div[text()='"+eleName+"']"));
	return eleOnTreeView;
	
	}
	

	@FindBy(xpath="//*[text()='Scheduled Date']/../..//div[@class='x-input-body-el']/input")
	private WebElement eleScheduledDateLst;
	public WebElement getEleScheduledDateLst()
	{
		return eleScheduledDateLst;
	}
	
	@FindBy(xpath="//*[text()='Scheduled Date']/../..//div[@class='x-innerhtml']/../..//input")
	private WebElement eleScheduledDateTxt;
	public WebElement getEleScheduledDateTxt()
	{
		return eleScheduledDateTxt;
	}
	
	@FindBy(xpath="//*[text()='Order Status']/../..//div[@class='x-input-body-el']/input")
	private WebElement eleOrderStatusCaseLst;
	public WebElement getEleOrderStatusCaseLst()
	{
		return eleOrderStatusCaseLst;
	}
	
	@FindBy(xpath="//*[text()='Billing Type']/../..//div[@class='x-input-body-el']/input")
	private WebElement eleBillingTypeCaseLst;
	public WebElement getEleBillingTypeCaseLst()
	{
		return eleBillingTypeCaseLst;
	}
	
	private WebElement elePartsIcn;
	public WebElement getElePartsIcn(String sPart)
	{
		elePartsIcn=driver.findElement(By.xpath("//div[@class='x-cells-el']//div[text()='"+sPart+"']"));
		
		return elePartsIcn;
	}
	
	@FindBy(xpath="//span[text()='Description']/../..//div[@class='x-input-body-el']/input")
	private WebElement eleDescriptionTxt;
	public WebElement getEleDescriptionTxt()
	{
		return eleDescriptionTxt;
	}
	
	@FindBy(xpath="//span[text()='Work Description']/../..//div[@class='x-innerhtml']/span")
	private WebElement eleWODesMappedTxt;
	public WebElement getEleWODesMappedTxt()
	{
		return eleWODesMappedTxt;
	}
	@FindBy(xpath="(//div[@class='x-inner x-container-inner x-layout-auto x-component-inner x-widthed'])[1]")
	private WebElement eleCasePartIcn;
	public WebElement getEleCasePartIcn()
	{
		return eleCasePartIcn;
	}
	
	@FindBy(xpath="//span[text()='Part']/../..//input")
	private WebElement elePartLst;
	public WebElement getElePartLst()
	{
		return elePartLst;
	}
	
	@FindBy(xpath="//*[contains(text(), 'Idle Time')]/../..//div[@class='x-input-body-el']/input")
	
	private WebElement eleIdleTimetxt;
	public WebElement geteleIdleTimetxt()
	{
		return eleIdleTimetxt;
	}
	
	@FindBy(xpath="//*[text()='Scheduled Date Time']/../..//div[@class='x-innerhtml']/../..//input")
	private WebElement eleScheduledDateTimeTxt;
	public WebElement getEleScheduledDateTimeTxt()
	{
		return eleScheduledDateTimeTxt;
	}
	
	
	@FindBy(xpath="//*[text()='Proforma Invoice']/../..//div[@class='x-innerhtml']/../..//textarea")
	private WebElement EleProformaInvoiceTxt;
	public WebElement getEleProformaInvoiceTxt()
	{
		return EleProformaInvoiceTxt;
	}
	
	/*
	//NOTE: setTime should be a common function and added in coomPO object repo
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
	*/
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
		ExtentManager.logger.log(Status.PASS,"New Event screen is displayed successfully");		
		commonsPo.setTime24hrs(getEleStartDateTimeLst(), 0,"0", "0"); //set start time to Today
		commonsPo.setTime24hrs(getEleEndDateTimeLst(), 0,"0","0"); //set end time
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
		ExtentManager.logger.log(Status.PASS,"Creation of WO event is successfull and Work Order Screen is displayed successfully");
	}
	public void validateServiceReport(CommonsPO commonsPo, String sPrintReportSearch, String sWorkOrderID) throws InterruptedException
	{	
		selectAction(commonsPo, sPrintReportSearch);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(getEleWOServiceReportTxt(sPrintReportSearch).isDisplayed(), "Work Order Service Report is not displayed.");
		ExtentManager.logger.log(Status.PASS,"Work Order Service Report is displayed successfully");		
		Assert.assertTrue(getEleWONumberTxt(sWorkOrderID).isDisplayed(),"WO updated report details is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work order updated details for the work order "+sWorkOrderID);
		getEleDoneLnk().click();
		commonsPo.tap(getEleDoneLnk());
		Thread.sleep(GenericLib.iHighSleep);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		Thread.sleep(GenericLib.iMedSleep);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		Thread.sleep(GenericLib.iMedSleep);
	
		//Navigation back to Work Order after Service Report
		Assert.assertTrue(getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		ExtentManager.logger.log(Status.PASS,"Creation of WO event is successfull and Work Order Screen is displayed successfully");
	}
	
	// To add Parts
	
	public void addParts(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sProductName1) throws InterruptedException
	{
		commonsPo.tap(workOrderPo.getElePartLnk());
		commonsPo.lookupSearch(sProductName1);
		commonsPo.tap(workOrderPo.getEleAddselectedbutton());

	}
//To add product to parts
	public void addProductParts(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sProductName1) throws InterruptedException
	{
		commonsPo.longPress(workOrderPo.getEleCasePartIcn());
		commonsPo.singleTap(workOrderPo.getElePartLst().getLocation());
		commonsPo.lookupSearch(sProductName1);
		commonsPo.tap(workOrderPo.getEleAddselectedbutton());

	}
	//To add labor parts
	public void addLaborParts(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sProductName1, String sActivityType, String sprocessname) throws InterruptedException
	{	//Adding labor parts name
		commonsPo.tap(workOrderPo.getEleAddLaborLnk());
		commonsPo.tap(getElePartLaborLkUp());
		commonsPo.lookupSearch(sProductName1);
		//commonsPo.tap(getEleProductNameTxt(sProductName1));
		
		//Selecting Activity Type
		commonsPo.pickerWheel( getEleActivityTypeLst(), sActivityType);	
		
		Thread.sleep(2000);
		commonsPo.setTime24hrs(getEleStartDateTimeLst(), 0,"0", "0"); //set start time to Today
		commonsPo.setTime24hrs(getEleEndDateTimeLst(),  1,"9","00"); //set end time
		
//		workOrderPo.setTime(commonsPo, workOrderPo.getEleStartDateTimeLst(), 1, "6");  // Sets start date time
//		workOrderPo.setTime(commonsPo, workOrderPo.getEleEndDateTimeLst(), 1, "8");    // Sets end date time
	
		//Add the price and quantity
		commonsPo.tap(getEleUsePriceToggleBtn());
		getEleLineQtyTxtFld().sendKeys("10");
		getEleLinePerUnitTxtFld().sendKeys("1000");	
		commonsPo.tap(getEleDoneBtn());
		

		//Verify to Manage WO lines
		Assert.assertTrue(getEleProcessName(sprocessname).isDisplayed(),"Failed to add Labor parts");  
		ExtentManager.logger.log(Status.PASS,"Labor parts are added and saved successfully. ");		
	}
	
	//To add Travel
		public void addTravel(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sprocessname) throws InterruptedException
		{	//Adding labor parts name
			commonsPo.tap(workOrderPo.getEleAddTravelLnk());
		
			commonsPo.setTime24hrs(getEleStartDateTimeLst(), 0,"0", "0"); //set start time to Today
			commonsPo.setTime24hrs(getEleEndDateTimeLst(), 1,"9","00"); //set end time
//			workOrderPo.setTime(commonsPo, workOrderPo.getEleStartDateTimeLst(), 1, "5");  // Sets start date time
//			workOrderPo.setTime(commonsPo, workOrderPo.getEleEndDateTimeLst(), 1, "9");    // Sets end date time
			
			//Add the price and quantity
			commonsPo.tap(getEleUsePriceToggleBtn());
			
			getEleLineQtyTxtFld().sendKeys("10");
			getEleLinePerUnitTxtFld().sendKeys("1000");	
			commonsPo.tap(getEleDoneBtn());
			
			//Verify to Manage WO lines
			Assert.assertTrue(getEleProcessName(sprocessname).isDisplayed(), "Failed to add Labor parts");   
			ExtentManager.logger.log(Status.PASS,"Labor parts are added and saved successfully. ");	
	}
		
		
	// To add Expense
		
		public void addExpense(CommonsPO commonsPo, WorkOrderPO workOrderPo,String expenseType,String sprocessname, String sLineQty, String sLinepriceperUnit) throws InterruptedException
		{	//Adding Expense name
			commonsPo.tap(workOrderPo.getEleAddExpenseLnk());
			commonsPo.tap(workOrderPo.getEleAddExpenseType());
			commonsPo.pickerWheel(getEleAddExpenseType(), expenseType);

			//Add the price and quantity
			commonsPo.tap(getEleUsePriceToggleBtn());
			getEleLineQtyTxtFld().sendKeys(sLineQty);
			getEleLinePerUnitTxtFld().sendKeys(sLinepriceperUnit);	
			commonsPo.tap(getEleDoneBtn());
			
			//Verify to Manage WO lines
			Assert.assertTrue(getEleProcessName(sprocessname).isDisplayed(), "Failed to add Labor parts");   
			ExtentManager.logger.log(Status.PASS,"Labor parts are added and saved successfully. ");		
		}

		// Delete the Childlines
		public void deletechildlines(CommonsPO commonsPo, WorkOrderPO workOrderPo, String partname, String workordervalue, String schildtype) throws InterruptedException {
			commonsPo.tap(workOrderPo.getEledeletepartchildline(partname));
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
		
		//Navigation to WorkOrder SFM without child search
		public void navigateToWOSFM(CommonsPO commonsPo, ExploreSearchPO exploreSearchPo, String sExploreSearch, String sWOName, String sFieldServiceName ) throws InterruptedException
		{
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
			commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));

			// Select the Work Order
			exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
			selectAction(commonsPo, sFieldServiceName);		
		}
		
		
		//Navigation to WorkOrder SFM with child search	
		public void navigateToWOSFM(CommonsPO commonsPo, ExploreSearchPO exploreSearchPo, String sExploreSearch, String sExploreChildSearchTxt, String sWOName, String sFieldServiceName ) throws InterruptedException
		{
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
			commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
			commonsPo.longPress(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));

			// Select the Work Order
			exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
			selectAction(commonsPo, sFieldServiceName);		
			
		}
		
		
		
		//Navigate to WorkOrder Screen with a child search present
		public void navigatetoWO(CommonsPO commonsPo, ExploreSearchPO exploreSearchPo, String sExploreSearch, String sExploreChildSearchTxt, String sWOName) throws InterruptedException {
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
			commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
			commonsPo.longPress(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));

			// Select the Work Order
			exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
			
		}
		/**
		 * Author : Meghana Rao
		 * @param commonsPo - Passing the objects
		 * @param exploreSearchPo
		 * @param sExploreSearch - Search Name from Explore
		 * @param sExploreChildSearchTxt - WorkOrder object lookup
		 * @param sWoName - Work Order Name
		 * @throws InterruptedException
		 * this function will click on the Work Order button when the Work Order is there on DOD.
		 */
	public void downloadCriteriaDOD(CommonsPO commonsPo,ExploreSearchPO exploreSearchPO, String sExploreSearch, String sExploreChildSearchTxt, String sWoName) throws InterruptedException {
			
			commonsPo.tap(exploreSearchPO.getEleExploreIcn());
			exploreSearchPO.getEleSearchNameTxt(sExploreSearch).click();
			commonsPo.longPress(exploreSearchPO.getEleSearchNameTxt(sExploreSearch));
			commonsPo.longPress(exploreSearchPO.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
			exploreSearchPO.getEleExploreSearchTxtFld().click();
			try {exploreSearchPO.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
			exploreSearchPO.getEleExploreSearchTxtFld().clear();
			exploreSearchPO.getEleExploreSearchTxtFld().sendKeys(sWoName);
			commonsPo.tap(exploreSearchPO.getEleExploreSearchBtn());
			
		}
		
		
		//Navigate to WorkOrder Screen without child search.
		public void navigatetoWO(CommonsPO commonsPo, ExploreSearchPO exploreSearchPo, String sExploreSearch, String sWOName) throws InterruptedException {
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
			commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		
			// Select the Work Order
			exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
			
		}
		
		
		// get Account from header
		@FindBy(xpath="//*[text()='Account']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement Accountvalue;
		public WebElement getAccountvalue()
		{
			return Accountvalue;
		}
		
		
		@FindBy(xpath="//*[text()='Product']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement Productvalue;
		public WebElement getProductvalue()
		{
			return Productvalue;
		}
		
		@FindBy(xpath="//*[text()='Component']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement componentvalue;
		public WebElement getcomponentvalue()
		{
			return componentvalue;
		}

		@FindBy(xpath="//*[text()='Order Type']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement ordertypevalue;
		public WebElement getordertypevalue()
		{
			return ordertypevalue;
		}

		@FindBy(xpath="//*[text()='Scheduled Date']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement ScheduledDatevalue;
		public WebElement getScheduledDatevalue()
		{
			return ScheduledDatevalue;
		}

		@FindBy(xpath="//*[text()='Scheduled Date Time']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement ScheduledDatetimevalue;
		public WebElement getScheduledDatetimevalue()
		{
			return ScheduledDatetimevalue;
		}
		
		@FindBy(xpath="(//div[contains(text(), 'Parts')][@class='x-panel-title-text']/../../../..//div[@class='x-cells-el'])[1]")
		private WebElement partsontap;
		public WebElement openpartsontap()
		{
			return partsontap;
		}
		
		@FindBy(xpath="//*[text()='Date Required']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement DateRequired;
		public WebElement getDateRequired()
		{
			return DateRequired;
		}
		
		
		
		
		
		private static final String DATE_FORMAT = "M/dd/yy hh:mm:ss a";

	    public static String main(String dateStr ) throws java.text.ParseException {
	    	
	       LocalDateTime ldt = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));

	        ZoneId KolkataZoneId = ZoneId.of("Asia/Kolkata");
	        System.out.println("TimeZone : " + KolkataZoneId);

	        //LocalDateTime + ZoneId = ZonedDateTime
	        ZonedDateTime asiaZonedDateTime = ldt.atZone(KolkataZoneId);
	        System.out.println("Date (Singapore) : " + asiaZonedDateTime);
	        
	        
	        ZoneId losAngeles = ZoneId.of("America/Los_Angeles");
	        System.out.println("TimeZone : " + losAngeles);

	        //LocalDateTime + ZoneId = ZonedDateTime
	        ZonedDateTime losAngelesZonedDateTime = asiaZonedDateTime.withZoneSameInstant(losAngeles);
	        System.out.println("Date (losAngeles) : " + losAngelesZonedDateTime);

	       
	        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
	        System.out.println("\n---DateTimeFormatter---");
	        
	        String losAngelesformate=format.format(losAngelesZonedDateTime);
			return losAngelesformate;
	       
			
			
			
			

	    }

		
}











