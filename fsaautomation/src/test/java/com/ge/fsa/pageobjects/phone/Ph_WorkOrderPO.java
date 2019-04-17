package com.ge.fsa.pageobjects.phone;


import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.GenericLib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;



public class Ph_WorkOrderPO
{
	public Ph_WorkOrderPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	

	@FindBy(xpath="//*[@*='SFM.HEADER_RIGHT.ACTIONS']")
	private WebElement eleActionsLnk;
	public WebElement getEleActionsLnk()
	{
		return eleActionsLnk;
	}
	
	private WebElement eleActionsTxt;
	public WebElement getEleActionsTxt(String sActionsName)
	{
		
		switch(BaseLib.sOSName.toLowerCase()) {
		case "android":
			eleActionsTxt = driver.findElement(By.xpath("//*[@text='"+sActionsName+"']"));
			return eleActionsTxt;
		case "ios":
			eleActionsTxt=driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='"+sActionsName+"'])[3]"));
			return eleActionsTxt;

		}
		return eleActionsLnk;
		}
	@FindAll({@FindBy(xpath="//*[@text='StartDateTime*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	//@FindAll({@FindBy(xpath="//*[@text='StartDateTime*']"),
	@FindBy(xpath="//*[@text='Start Date and Time']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath="//XCUIElementTypeOther[@name='SFM.LAYOUT.EDIT.DATEPICKER.2']"),
	@FindBy(xpath="//XCUIElementTypeOther[@name='SFM.LAYOUT.EDIT.DATEPICKER.1']")})
	private WebElement eleStartDateTimeTxtFld;
	public WebElement getEleStartDateTimeTxtFld()
	{
		return eleStartDateTimeTxtFld;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='EndDateTime*']"),
	@FindBy(xpath="//*[@text='End Date and Time']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath="//XCUIElementTypeOther[@name='SFM.LAYOUT.EDIT.DATEPICKER.3']"),
	@FindBy(xpath="//XCUIElementTypeOther[@name='SFM.LAYOUT.EDIT.DATEPICKER.2']")})
	private WebElement eleEndDateTimeTxtFld;
	public WebElement getEleEndDateTimeTxtFld()
	{
		return eleEndDateTimeTxtFld;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Subject*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
	@FindBy(xpath="//XCUIElementTypeOther[@name='Subject*']")})
	private WebElement eleSubjectTxtFld;
	public WebElement getEleSubjectTxtFld()
	{
		return eleSubjectTxtFld;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Save']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@label=\"Save\"])")})
	private WebElement eleSaveLnk;
	public WebElement getEleSaveLnk()
	{
		return eleSaveLnk;
	}
	
	@FindAll({@FindBy(xpath="//*[@*='ADD SELECTED (1)']"),
	@FindBy(xpath="//XCUIElementTypeOther[@label=\"ADD SELECTED (1)\"]")})
	private WebElement eleAddSelected;
	public WebElement getEleAddSelected()
	{
		return eleAddSelected;
	}
	
	@FindAll({@FindBy(xpath="//*[@*='Finalize']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@label=\"Finalize\"])[3]")})
	private WebElement eleFinalize;
	public WebElement getEleFinalize()
	{
		return eleFinalize;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Create New\"])[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[15]")
	private WebElement eleselectprocesscreateevent;
	public WebElement getEleselectprocesscreateevent()
	{
		return eleselectprocesscreateevent;
	}

	private WebElement eleselectprocess;
	public WebElement getEleselectprocess(String sProcessName)
	{
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""+sProcessName+"\"))"));
		}else {
			return driver.findElement(By.xpath("//*[@label='"+sProcessName+"']"));
		}
		
	}
	
	
	public void selectAction(CommonUtility commonUtility,String sActionsName) throws InterruptedException
	{
		System.out.println("Selecting Action");
		getEleActionsLnk().click();	
		Thread.sleep(3000);
		commonUtility.custScrollToElementAndClick(getEleselectprocess(sActionsName));
		//getEleActionsTxt(sActionsName).click();		
	}

	public void createNewEvent(CommonUtility commonUtility,String sSubject,Ph_CalendarPO ip_CalendarPo) throws InterruptedException
	{
		System.out.println("Creating New Event");

		selectAction(commonUtility,"Create New Event From Work Order");
		
		commonUtility.setDateTime24hrs(getEleStartDateTimeTxtFld(), 0, "16", "0");
		commonUtility.setDateTime24hrs(getEleEndDateTimeTxtFld(), 0, "18", "0");
		getEleSubjectTxtFld().sendKeys(sSubject);
		Thread.sleep(2000);
		getEleSaveLnk().click();
		Thread.sleep(5000);
	
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Add Parts']"),
	@FindBy(xpath="//XCUIElementTypeOther[@label=\"Add Parts\"]")})
	private WebElement elePartLnk;
	public WebElement getElePartLnk()
	{
		return elePartLnk;
	}
	
	
	public String getStringParts()
	{
		return "PARTS";
	}
	
	@FindAll({@FindBy(xpath="//*[@text ='Add Labor']"),
	@FindBy(xpath="//XCUIElementTypeOther[@label='Add Labor']")})
	private WebElement eleLaborLnk;
	public WebElement getEleLaborLnk()
	{
		return eleLaborLnk;
	}
	
	public String getStringLabor()
	{
		return "LABOR";
	}
	
	@FindAll({@FindBy(xpath="//*[@text ='Add Travel']"),
		@FindBy(xpath="//XCUIElementTypeOther[@label='Add Travel']")})
	private WebElement eleTravelLnk;
	public WebElement getEleTravelLnk()
	{
		return eleTravelLnk;
	}

	public String getStringTravel()
	{
		return "TRAVEL";
	}
	
	@FindAll({@FindBy(xpath="//*[@text ='Add Expense']"),
		@FindBy(xpath="//XCUIElementTypeOther[@label='Add Expense']")})
	private WebElement eleExpensesLnk;
	public WebElement getEleExpensesLnk()
	{
		return eleExpensesLnk;
	}
	
	public String getStringExpenses()
	{
		return "EXPENSES";
	}
	

	@FindAll({@FindBy(xpath="//*[@text ='Add Image or Video']"),
		@FindBy(xpath="//XCUIElementTypeOther[@label='Add Image or Video']")})
	private WebElement eleAddImageLnk;
	public WebElement getEleAddImageLnk()
	{
		return eleAddImageLnk;
	}
	
	public String getStringAttachments()
	{
		return "ATTACHMENTS";
	}
	
	public String getStringDocuments()
	{
		return "DOCUMENTS";
	}
		
	public WebElement getEleSearchListItem(String sValue)
	{
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
		return driver.findElement(By.xpath("//*[contains(@text,'"+sValue+"')]/../..//*[contains(@content-desc,'I')]/*[contains(@text,'"+sValue+"')]"));
		}else {
		//return driver.findElement(By.xpath("//*[contains(@label,'"+sValue+"')]/*[contains(@name,'ITEM')]"));
			return driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='"+sValue+"']"));
		} 

	}
	
	@FindAll({@FindBy(xpath="//*[contains(@text,'Part')]//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath="//*[@label='Part Part Lookup']")})
	private WebElement elepartlookup;
	public WebElement getElepartlookup()
	{
		return elepartlookup;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Activity Type']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath="//XCUIElementTypeStaticText[@label=\"Activity Type\"]/../XCUIElementTypeOther")})
	private WebElement eleActivityType;
	public WebElement getEleActivityType()
	{
		return eleActivityType;
		}
	
	@FindAll({@FindBy(xpath="//*[@text='Calibration']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@label=\"Calibration\"])")})
	private WebElement eleCalibration;
	public WebElement getEleCalibration()
	{
		return eleCalibration;
	}
	
	
	@FindAll({@FindBy(xpath="//*[@text='Line Qty']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
	@FindBy(xpath="//XCUIElementTypeStaticText[@label=\"Line Qty\"]/../XCUIElementTypeOther")})
	private WebElement eleLineQtyTxtFld;
	public WebElement getEleLineQtyTxtFld()
	{
		return eleLineQtyTxtFld;
	}
	@FindAll({@FindBy(xpath="//*[@text='Line Price Per Unit']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
	@FindBy(xpath="//XCUIElementTypeStaticText[@label=\"Line Price Per Unit\"]/../XCUIElementTypeOther")})
	private WebElement eleLinePerUnitTxtFld;
	public WebElement getEleLinePerUnitTxtFld()
	{
		return eleLinePerUnitTxtFld;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Discount %']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
		@FindBy(xpath="//XCUIElementTypeStaticText[@label=\"Discount %\"]/../XCUIElementTypeOther")})
		private WebElement eleDiscountPercentage;
		public WebElement getEleDiscountPercentage()
		{
			return eleDiscountPercentage;
		}
	
	
	@FindBy(xpath="//*[@text='This record does not meet the qualification criteria for this SFM Transaction']")
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

	@FindBy(xpath="//span[@class='x-button-label'][text()='Save']")
	private WebElement eleClickSave;
	public  WebElement getEleClickSave()
	{

		return eleClickSave;
	}
	@FindBy(xpath="//*[contains(text(),'Saved successfully')]")
	private WebElement eleSavedSuccessTxt;
	public WebElement getEleSavedSuccessTxt()
	{
		return eleSavedSuccessTxt;
	}
	@FindBy(xpath="//*[@*='SFM.VALIDATION.LIST.TOGGLE_BUTTON']")
	private WebElement eleIssueFoundTxt;
	public WebElement getEleIssueFoundTxt()
	{
		return eleIssueFoundTxt;
	}
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='Discard Changes']")
	private WebElement eleDiscardBtn;
	public  WebElement getEleDiscardBtn()
	{
		return eleDiscardBtn;
	}
	
	private WebElement eleIssuePopupTxt;
	public WebElement getEleIssuePopupTxt(String sIssueTxt)
	{
		eleIssuePopupTxt = driver.findElement(By.xpath("//*[@*[contains(.,'SFM.VALIDATION.LIST.ANCHOR_BUTTON')]]/*[@*[contains(.,'"+sIssueTxt+"')]]"));
		return eleIssuePopupTxt;
	}
	@FindBy(xpath="//*[text() = 'Cancel']")
	private WebElement eleCancelLnk;
	public WebElement getEleCancelLink()
	{
		return eleCancelLnk;
	}
	

	private WebElement elelaborpartresult;
	public WebElement getElelaborpartresult(String sProductName1)
	{
		switch (BaseLib.sOSName.toLowerCase()) {

		case "android":
			elelaborpartresult = driver.findElement(By.xpath("//*[@*='"+sProductName1+"'][@class='android.widget.TextView']"));
			return elelaborpartresult;
		case "ios":
			return elelaborpartresult = driver.findElement(By.xpath("(//XCUIElementTypeOther[@label=\" RESULTS\"])[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther"));
		}
			return elelaborpartresult;
		
	}
	@FindAll({@FindBy(xpath="//*[@*='Save']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@label=\"Save\"])[3]")})
	private WebElement elesave;
	public WebElement getElesave()
	{
		return elesave;
	}
	
	public void addParts(CommonUtility commonUtility,String sProductName1) 
	{
	System.out.println("Adding Parts");
	commonUtility.gotToTabHorizontal(getStringParts());
	selectFromlookupSearchList(commonUtility, getElePartLnk(), sProductName1);
	getEleAddSelected().click();
	}

	public void addLabor(CommonUtility commonUtility,String sProductName1)
	{
		System.out.println("Adding Labor");
		commonUtility.custScrollToElementAndClick(getEleLaborLnk(),getStringLabor());
		selectFromlookupSearchList(commonUtility, getElepartlookup(), sProductName1);
		selectFromPickList(commonUtility, getEleActivityType(), "Calibration");
		
		
			try {
				commonUtility.setDateTime24hrs(getEleStartDateTimeTxtFld(), 0,"0", "0");
				commonUtility.setDateTime24hrs(getEleEndDateTimeTxtFld(),1,"09","00"); 

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		
		getEleLineQtyTxtFld().click();
		getEleLineQtyTxtFld().sendKeys("10");
		
		getEleLinePerUnitTxtFld().click();
		getEleLinePerUnitTxtFld().sendKeys("1000");
		getEleAdd().click();
		
		
	}

	@FindAll({@FindBy(xpath="//*[@text='Account']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
@FindBy(xpath="//XCUIElementTypeStaticText[@name='Account']/../XCUIElementTypeOther")	})
private WebElement eleAccount;
	public WebElement getEleAccount()
	{
		return eleAccount;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Scheduled Date Time']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")
	,@FindBy(xpath="//XCUIElementTypeStaticText[@name='Scheduled Date Time']/../XCUIElementTypeOther")	})
	private WebElement eleScheduledDateTime;
	public WebElement getEleScheduledDateTime()
	{
		return eleScheduledDateTime;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Scheduled Date']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")
	,@FindBy(xpath="//XCUIElementTypeStaticText[@name='Scheduled Date']/../XCUIElementTypeOther")	})

	private WebElement eleScheduledDate;
	public WebElement getEleScheduledDate()
	{
		return eleScheduledDate;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Component']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")
	,@FindBy(xpath="//XCUIElementTypeStaticText[@name='Component']/../XCUIElementTypeOther")	})

	private WebElement eleComponent;
	public WebElement getEleComponent()
	{
		return eleComponent;
	}
	
	
	@FindAll({@FindBy(xpath="//*[@text='Installed Product ID*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")
	,@FindBy(xpath="//XCUIElementTypeStaticText[@name='Installed Product ID*']/../XCUIElementTypeOther")	})

	private WebElement eleInstalledProduct ;
	public WebElement getEleInstalledProduct()
	{
		return eleInstalledProduct;
	}
	
	
	@FindAll({@FindBy(xpath="(//*[@text='Parts (1)']//..//following-sibling::*[@class='android.view.ViewGroup'])[1]")
	,@FindBy(xpath="//XCUIElementTypeStaticText[@name='Parts (1)']/../XCUIElementTypeOther")	})
	private WebElement eletabonpart;
	public WebElement getEletabonpart()
	{
		return eletabonpart;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Part']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")
	,@FindBy(xpath="//XCUIElementTypeStaticText[@name='Part']/../XCUIElementTypeOther")	})
	private WebElement elePart;
	public WebElement getPart()
	{
		return elePart;
	}
	

	@FindAll({@FindBy(xpath="//*[@text='Date Required']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")
	,@FindBy(xpath="//XCUIElementTypeStaticText[@name='Date Required']/../XCUIElementTypeOther")	})
	private WebElement eleDateRequired;
	public WebElement getleDateRequired()
	{
		return eleDateRequired;
	}
	
	
	@FindBy(xpath="//*[@*='APP.BACK_BUTTON']")
	private WebElement eleXsymbol;
	public WebElement geteleXsymbol()
	{
		return eleXsymbol;
	}
	
	
	@FindAll({@FindBy(xpath="//XCUIElementTypeStaticText[@name='Product*']/../XCUIElementTypeOther"),
		@FindBy(xpath="//XCUIElementTypeStaticText[@name='Product']/../XCUIElementTypeOther"),
	@FindBy(xpath="//*[@text='Product*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath="//*[@text='Product']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")})
	private WebElement eleProduct;
	public WebElement getEleProduct()
	{
		return eleProduct;
	}
	
	@FindAll({@FindBy(xpath="//XCUIElementTypeStaticText[@name='Installed Product ID*']/../XCUIElementTypeOther"),
	@FindBy(xpath="//*[@text='Installed Product ID*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']")})
	private WebElement eleInstalledProductstar;
	public WebElement getEleInstalledProductstar()
	{
		return eleInstalledProductstar;
	}
	
	@FindAll({@FindBy(xpath="//XCUIElementTypeStaticText[@name='Account*']/../XCUIElementTypeOther"),
	@FindBy(xpath="//*[@text='Account*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")})
	private WebElement eleAccountstar;
	public WebElement getAccountstar()
	{
		return eleAccountstar;
	}
	
	@FindAll({@FindBy(xpath="//XCUIElementTypeStaticText[@name='To Location']/../XCUIElementTypeOther"),
	@FindBy(xpath="//*[@text='To Location']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")})
	private WebElement eleToLocation;
	public WebElement getEleToLocation()
	{
		return eleToLocation;
	}
	
	public void createInstalledProduct(CommonUtility commonUtility,Ph_CalendarPO ph_CalendarPo,String accountName, String ProdutName,String InstalledProductID,Ph_ExploreSearchPO ph_ExploreSearchPO ) throws Exception
	{
		ph_CalendarPo.getEleCalendarBtn().click();
		//click on new icon
		ph_CalendarPo.getEleCreateNewBtn().click();
		Thread.sleep(2000);
		if(BaseLib.sOSName.equalsIgnoreCase("android")) 
		{
	ph_CalendarPo.getEleSelectProcessNewProcess("Create New Installed Product Automation sahi").click();
		}
		else
		{
			ph_CalendarPo.getEleSelectProcessNewProcess("Create New Installed Product Automation sahi no description").click();
		}
		Thread.sleep(2000);
		
		// Adding Value for Product
		//commonUtility.custScrollToElement(getEleProduct());
		//getEleProductstar().click();
		ph_ExploreSearchPO.selectFromLookupSearchList(commonUtility,getEleProduct(), ProdutName);
		
		// Adding Value for InstalledproductID
		//commonUtility.custScrollToElement(getEleInstalledProduct());
		getEleInstalledProductstar().click();
		getEleInstalledProductstar().sendKeys(InstalledProductID);
		
		// Adding Value for Account
		commonUtility.custScrollToElement(getAccountstar());
		//getAccountstar().click();
		ph_ExploreSearchPO.selectFromLookupSearchList(commonUtility,getAccountstar(), accountName);
	
		
		Thread.sleep(1000);
		
		getEleAdd().click();
	}
	
	
	@FindBy(xpath="//*[@text='Site']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")
	private WebElement eleSite;
	public WebElement getEleSite()
	{
		return eleSite;
	}
	@FindAll({@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Product Lookup']"),
		@FindBy(xpath="//*[@label='Product Product Lookup']")})
	public WebElement productLookup;
	public WebElement getProductLookup() {
		return productLookup;
	}
	public WebElement getChildLineAddItem(String value) {
		return driver.findElement(By.xpath("//*[./*[@class='android.widget.TextView'][@text='"+value+"']][@class='android.view.ViewGroup']"));
	}
	
	public WebElement getChildLineAddedItem(String value) {
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {

		return driver.findElement(By.xpath("(//*[.//*[contains(@text,'"+value+"')]][@class='android.widget.ScrollView'])[last()]"));
	}else {
		return driver.findElement(By.xpath("//*[contains(@name,'"+value+"')]"));

	}
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][contains(@text,'ADD SELECTED')]")
	private WebElement eleAddSelectedButton;
	public WebElement getEleAddSelectedButton()
	{
		return eleAddSelectedButton;
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='IB Serial Number Lookup']")
	private WebElement eleIBSerialNumber;
	public WebElement getEleIBSerialNumber() {
		return eleIBSerialNumber;
	}
	
	@FindAll({@FindBy(xpath="//*[@class='android.widget.ScrollView']//*[@class='android.widget.TextView'][contains(@text,'IB')]")})
	public List<WebElement> IBLookup;
	public List<WebElement> getIBLookup(){
		return IBLookup;
	}

	@FindBy(xpath="//*[@text='Add']")
	private WebElement eleAddButton;
	public WebElement getEleAddButton() {
		return eleAddButton;
	}
	
	@FindBy(xpath="//*[./*[@text='Remove']]")
	private WebElement eleRemoveButton;
	public WebElement getEleRemoveButton() {
		return eleRemoveButton;
	}
	
	@FindBy(xpath="//*[./*[@text='Discard Changes']]")
	private WebElement eleDiscardChangesButton;
	public WebElement getEleDiscardChangesButton() {
		return eleDiscardChangesButton;
	}
	
	@FindBy(xpath="//*[@content-desc='APP.BACK_BUTTON']")
	private WebElement eleBackButton;
	public WebElement getEleBackButton() {
		return eleBackButton;
	}
	
	public WebElement getEleChildLineItem(String childLine) {
		return driver.findElement(By.xpath("(//*[.//*[contains(@text,'"+childLine+"')]][@class='android.widget.ScrollView'])[last()]"));
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Expense Type']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@text='--None--']")
	private WebElement eleExpenseTypeField;
	public WebElement getEleExpenseTypeField() {
		return eleExpenseTypeField;
	}
	
	public WebElement getEleDropDownValue(String value) {
		return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+value+"']"));
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Line Qty']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']")
	private WebElement eleLineQtyField;
	public WebElement getEleLineQtyField() {
		return eleLineQtyField;
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Line Price Per Unit']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']")
	private WebElement eleLinePriceField;
	public WebElement getEleLinePriceField() {
		return eleLinePriceField;
	}
	
	@FindBy(xpath="//*[./*[@text='More']][@class='android.view.ViewGroup']")
	private WebElement eleMore;
	public WebElement getEleMore() {
		return eleMore;
	}
	
	@FindBy(xpath="//*[./*[@text='Manage Work Details for Products Serviced']][@class='android.view.ViewGroup']")
	private WebElement eleManageWorkDetails;
	public WebElement getEleManageWorkDetails() {
		return eleManageWorkDetails;
	}
	
	public WebElement getEleeleIBId(String sProductName) {
		return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+sProductName+"']"));
	}
	public void addParts(CommonUtility commonUtility,String[] productNames) throws InterruptedException {
		commonUtility.custScrollToElementAndClick(getElePartLnk(), getStringParts());
		for(String productName : productNames) {
			selectFromlookupSearchList(commonUtility, getElePartLnk(), productName);
			commonUtility.getSearchLookupWithText(productName).clear();
		}
		getEleAddSelectedButton().click();
	}
	
	public void addPartsManageWD(CommonUtility commonUtility,Ph_ExploreSearchPO ph_ExploreSearchPo, String sPartName1) throws InterruptedException
	{
		commonUtility.custScrollToElementAndClick(getElePartLnk(),getStringParts());
		selectFromlookupSearchList(commonUtility, getElepartlookup(), sPartName1);

		//commonUtility.tap(workOrderPo.getEleAddselectedbutton());
		//Thread.sleep(1000);
		ph_ExploreSearchPo.getEleSearchListItem(sPartName1).click();

	}
public void downloadCriteriaDOD(CommonUtility commonUtility,Ph_ExploreSearchPO exploreSearchPO, String sExploreSearch, String sExploreChildSearchTxt, String sWoName) throws InterruptedException {
		exploreSearchPO.geteleExploreIcn().click();;
		//exploreSearchPO.getEleSearchNameTxt(sExploreSearch).click();
		exploreSearchPO.getEleSearchListItem(sExploreSearch).click();;
		exploreSearchPO.getEleSearchChildListName(sExploreChildSearchTxt).click();;
//		exploreSearchPO.getEleExploreSearchTxtFld().click();
		exploreSearchPO.getEleExploreSearchTxtFld().clear();
		exploreSearchPO.getEleExploreSearchTxtFld().sendKeys(sWoName);

	}
	
	public void addExpense(CommonUtility commonUtility, String sExpenseType,String sLineQty,String slinepriceperunit) throws InterruptedException {
		//commonUtility.custScrollToElementAndClick("Add Expense").click();
		//commonUtility.custScrollToElementAndClick(getChildLineAddItem("Add Expense"));
		commonUtility.custScrollToElementAndClick(getEleExpensesLnk(),"EXPENSES");
		getEleExpenseTypeField().click();
		getEleDropDownValue(sExpenseType).click();;
		getEleLineQtyField().sendKeys(sLineQty);
		getEleLinePriceField().sendKeys(slinepriceperunit);
		getEleAddButton().click();
		
		
	}

	public void navigatetoWO(CommonUtility commonUtility, Ph_ExploreSearchPO ph_ExploreSearchPo, String sExploreSearch, String sExploreChildSearchTxt, String sWOName) throws InterruptedException {
		ph_ExploreSearchPo.geteleExploreIcn().click();
		Thread.sleep(GenericLib.iMedSleep);
		//exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		commonUtility.custScrollToElementAndClick(ph_ExploreSearchPo.getEleSearchListItem(sExploreSearch));
		Thread.sleep(GenericLib.iMedSleep);
		commonUtility.custScrollToElementAndClick(ph_ExploreSearchPo.getEleSearchListItem(sExploreChildSearchTxt));

		// Select the Work Order
		ph_ExploreSearchPo.selectFromLookupSearchList(commonUtility,ph_ExploreSearchPo.getEleSearchListItem(sExploreChildSearchTxt), sWOName);
	}
	
	public void addPSLines(CommonUtility commonUtility,String sSerialNumber)throws InterruptedException
	{
		
		selectFromlookupSearchList(commonUtility, getChildLineAddItem("Add Products Serviced"), sSerialNumber);
		getEleAddSelectedButton().click();

	}
	
	
	@FindBy(xpath="//android.widget.ScrollView[@content-desc=\"SFM.LAYOUT.CHIILDLINE_LIST.ALLCHILDLINES\"]/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup")
	private WebElement eleOntoppart;
	public WebElement getEleOntoppart()
	{
		return eleOntoppart;
	}
	
	
	@FindBy(xpath="(//android.widget.ScrollView[@content-desc=\"SFM.LAYOUT.CHIILDLINE_LIST.ALLCHILDLINES\"])[2]/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup")
	private WebElement eleOntoplabor;
	public WebElement getEleOntoplabor()
	{
		return eleOntoplabor;
	}
	
	@FindBy(xpath="//*[@text='DiscardChanges']")
		private WebElement eleDiscardChanges;
		public WebElement getEleDiscardChanges()
		{
			return eleDiscardChanges;
		}
//		//android.view.ViewGroup[@content-desc="SFM.LAYOUT.ADD.0"]/android.view.ViewGroup
//	@FindBy(xpath = "//*[@*='StartDateTime*']//following::*[@class='android.widget.TextView']")
//	private WebElement eleStartDateTimeField;
//
//	public WebElement getEleStartDateTimeField() {
//		return eleStartDateTimeField;
//	}

//	@FindBy(xpath = "//*[@*='EndDateTime*']//following::*[@class='android.widget.TextView']")
//	private WebElement eleEndDateTimeField;
//
//	public WebElement getEleEndDateTimeField() {
//		return eleEndDateTimeField;
//	}
	
	@FindBy(xpath="//*[@*='Record Type ID']/following-sibling::*[1]")
	private WebElement eleRecordTypeID;
	public WebElement getEleRecordTypeID() {
		return eleRecordTypeID;
	}
	@FindBy(xpath="//*[@*='controlling picklist']/following-sibling::*[1]")
	private WebElement eleControllingPicklist;
	public WebElement getEleControllingPicklist() {
		return eleControllingPicklist;
	}
	@FindBy(xpath="//*[@*='dependent picklist']/following-sibling::*[1]")
	private WebElement eleDependentPicklist;
	public WebElement getEleDependentPicklist() {
		return eleDependentPicklist;
	}
	

	private WebElement elelookupsearhproduct;
	public WebElement getElelookupsearch()
	{
			return elelookupsearhproduct = driver.findElementByAccessibilityId("SFM_LAYOUT.LOOKUP.SEARCH_BAR");
		
	}	




	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Contact Contact Lookup\"])[2]")
	private WebElement eleContactLookUp;
	public WebElement getEleContactLookUp()
	{
		return eleContactLookUp;
	}

	private WebElement eleContactlookupsearch;
	public WebElement geteleContactlookupsearch()
	{
		return eleContactlookupsearch = driver.findElementByAccessibilityId("Search Full Name, Business Phone, Mobile Phone, Email");
	}

	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Product Product Lookup\"])[2]")
	private WebElement eleProductLookUp;
	public WebElement getEleProductLookUp()
	{
		return eleProductLookUp;
	}

	private WebElement eleProductlookupsearch;
	public WebElement geteleProductlookupsearch()
	{
		return eleProductlookupsearch = driver.findElementByAccessibilityId("Search Product Name, Product Code, Product Family, Product Line");
	}



	@FindAll({@FindBy(xpath="//*[@text='Low']"),
		@FindBy(xpath="//*[@label='Low']")})
	private WebElement eleCreatenewpriorityLow;
	public WebElement getEleCreatenewpriorityLow()
	{
		return eleCreatenewpriorityLow;
	}


	
	@FindAll({@FindBy(xpath="//*[@*='SFM.HEADER_RIGHT']"),
	@FindBy(xpath="//*[@content-desc='SFM.HEADER_RIGHT']"),
	@FindBy(xpath="//*[@text='Add']")})
	private WebElement eleAdd;
	public WebElement getEleAdd()
	{
		return eleAdd;

		/*if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			return eleAdd = driver.findElementByAccessibilityId("Add");
		}else {
			return eleAdd =driver.findElement(By.xpath("//*[@text='Add']"));
		}*/
	}	

	public void selectFromlookupSearchList(CommonUtility commonUtility,WebElement eleToSetValue, String sValue){
		System.out.println("Select From Lookup List");
		commonUtility.custScrollToElementAndClick(eleToSetValue);
		getElelookupsearch().click();
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			getElelookupsearch().sendKeys(sValue+"\\n");
		}else {
			getElelookupsearch().sendKeys(sValue+"\n");
		}
		getEleSearchListItem(sValue).click();

	}
	
	public void selectFromPickList(CommonUtility commonUtility,WebElement eleToSetValue, String sValue) {
		System.out.println("Select From Picklist");
		commonUtility.custScrollToElementAndClick(eleToSetValue);
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			driver.findElement(By.xpath("//*[@text='"+sValue+"']")).click();
		}else{
			driver.findElement(By.xpath("//*[@label='"+sValue+"']")).click();
		}
	}
		
		
	@FindAll({@FindBy(xpath="//android.view.ViewGroup[@content-desc=\"SFM.LAYOUT.ADD.0\"]/android.view.ViewGroup"),
		@FindBy(xpath="//*[@name='SFM.LAYOUT.ADD']")})
		private WebElement eleclickonaddparts;
		public WebElement getEleclickonaddparts()
		{
			return eleclickonaddparts;
		}
		
		@FindAll({@FindBy(xpath="//*[@text='Order Status']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath="//*[@text='Order Status*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath="//XCUIElementTypeStaticText[@name='Order Status']/../XCUIElementTypeOther"),
			@FindBy(xpath="//XCUIElementTypeStaticText[@name='Order Status*']/../XCUIElementTypeOther")})
		private WebElement eleOrderStatus;
		public WebElement geteleOrderStatus()
		{
			return eleOrderStatus;
		}
		
		
		@FindAll({@FindBy(xpath="//*[@text='[Part]'][@class='android.widget.TextView']"),
			@FindBy(xpath="//*[@*='[Part]']")})
		private WebElement eleRemoveablePart;
		public WebElement geteleRemoveablePart()
		{
			return eleRemoveablePart;
		}
		
		@FindBy(xpath="//*[@*='Remove']")
		private WebElement eleRemovePopUp;
		public WebElement geteleRemovePopUp()
		{
			return eleRemovePopUp;
		}
		
		private WebElement eleRemove;
		public WebElement geteleRemove()
		{

			return eleRemove = driver.findElementByAccessibilityId("SFM.DELETE_CHILD_LINE.BUTTON");
		
		}
		
		public WebElement geteleAddedPart(String value) {

			if(BaseLib.sOSName.equalsIgnoreCase("android")) {
				return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+value+"']"));
			}else {
				return eleRemove =driver.findElement(By.xpath("//*[@*='"+value+"']"));
			}


		}
			
			@FindBy(xpath="//*[@text='Work Description Mapped'][@class='android.widget.EditText']")
			private WebElement EleWODesMappedTxt;
			public WebElement getEleWODesMappedTxt()
			{
				return EleWODesMappedTxt;
			}
			
			@FindBy(xpath="//*[@text='Save'][@class='android.widget.TextView']")
			private WebElement eleSaveButton;
			public WebElement geteleSaveButton()
			{
				return eleSaveButton;
			}
			
			@FindAll({@FindBy(xpath="//*[@text='Billable Qty']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
				@FindBy(xpath="//XCUIElementTypeOther[@name='Billable Qty']")})
				private WebElement eleBillableQty;
				public WebElement geteleBillableQty()
				{
					return eleBillableQty;
				}
				
	@FindBy(xpath="//*[@text='[Use Price From Pricebook/Contract]'][@class='android.widget.TextView']")
	private WebElement eleusePriceBookcontract;
	public WebElement geteleusePriceBookcontract()
		{
					return eleusePriceBookcontract;
		}
				
		@FindAll({@FindBy(xpath="//*[@text='Description']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
		@FindBy(xpath="//XCUIElementTypeOther[@label='Description']")})
		private WebElement eleDescriptiontext;
		public WebElement geteleDescriptiontext()
		{
		return eleDescriptiontext;
		}
		
		@FindAll({@FindBy(xpath="//*[@text='Problem Description']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
		@FindBy(xpath="//XCUIElementTypeOther[@label='Problem Description']")})
		private WebElement eleProblemDescriptiontxt;
		public WebElement geteleProblemDescriptiontxt()
		{
		return eleProblemDescriptiontxt;
		}
		
		@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Billing Type']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[1]")
		private WebElement eleBillingTypeField;
		public WebElement getEleBillingTypeField() {
			return eleBillingTypeField;
		}
		
	}	 

