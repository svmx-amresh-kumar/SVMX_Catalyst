	package com.ge.fsa.pageobjects;
	
	import java.util.List;
	import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Rotatable;
	import org.openqa.selenium.ScreenOrientation;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.support.FindBy;
	import org.openqa.selenium.support.PageFactory;
	import org.testng.Assert;
	
	import com.aventstack.extentreports.Status;
	import com.ge.fsa.lib.ExtentManager;
	import com.ge.fsa.lib.GenericLib;
	
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
	
	
	
		
		
		@FindBy(xpath="	//span[text()='Estimated Qty']/..//..//input[@class='x-input-el']")
		private WebElement eleEstimatedQty;
		public WebElement getEleEstimatedQty()
		{
			return eleEstimatedQty;
		}
		private WebElement eleChildLineTapName;
		public WebElement getEleChildLineTapName(String sProductName)
		{
			eleChildLineTapName=driver.findElement(By.xpath("//div[text()='"+sProductName+"']"));
			return eleChildLineTapName;
		}
		@FindBy(xpath="//span[text() = 'Actions']")
		private WebElement eleActionsLnk;
		public WebElement getEleActionsLnk()
		{
			return eleActionsLnk;
		}
	
		
	
		// Added by Harish.CS
		private WebElement eleActionsTxtWithIcon;
		public WebElement getEleActionsTxtWithIcon(String sActionsName)
		{
			eleActionsTxtWithIcon=driver.findElement(By.xpath("//div[@class='x-component x-button x-button-svmx-menu-button x-component-svmx-menu-button x-iconalign-center x-iconalign-right x-layout-box-item x-layout-vbox-item x-stretched x-widthed']//span[@class='x-button-label'][text()='"+sActionsName+"']"));
//			System.out.println(eleActionsTxt);
			return eleActionsTxtWithIcon;
		}
		
		@FindBy(xpath="//div[@class='x-component x-button x-button-svmx-menu-button x-component-svmx-menu-button x-iconalign-center x-iconalign-right x-layout-box-item x-layout-vbox-item x-stretched x-widthed']//span[@class='x-button-label'][text()='10558_Action']")
		private WebElement test;
		public WebElement getTest() {
			return test;
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
	
	
		@FindBy(xpath="(//span[@class='x-label-text-el'][text()='Billing Type']//..//..//input[@class='x-input-el'])")
		private WebElement eleBillingTypeValue;
		public WebElement getEleBillingTypeValue()
		{
			return eleBillingTypeValue;
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
	
	
		private WebElement eleTraveltap;
		public WebElement geteleTraveltap(String sEndTime)
		{
			eleTraveltap=driver.findElement(By.xpath("//div[@class='x-panel-title-text'][text()='Travel (1)']/..//..//..//..//div[@class='x-inner-el'][contains(text(),'"+sEndTime+"')]"));
			return eleTraveltap;
		}
	
		// Added by Harish.CS
		@FindBy(xpath="//div[text()='Attached Images/Videos']/following::span[text()='Add']")
		private WebElement eleAddAttachedImagesLnk;
		public WebElement getEleAddAttachedImagesLnk()
		{
			return eleAddAttachedImagesLnk;
		}
	
		@FindBy(xpath="//span[text()='Expense Type']/../..//input[@class='x-input-el']")
		private WebElement eleAddExpenseType;
		public WebElement getEleAddExpenseType()
		{
			return eleAddExpenseType;
		}
	
		@FindBy(xpath="//div[contains(text(),'Full Name')]/following::div[contains(@id,'ext-gridrow-')]")
		private List<WebElement> contactListInLkp;
		public List<WebElement> getcontactListInLkp() {
			return contactListInLkp;
		}
		
		@FindBy(xpath="//div[contains(text(),'Product Name')]/following::div[contains(@id,'ext-gridrow-')]")
		private List<WebElement> productListInLkp;
		public List<WebElement> getProductListInLkp() {
			return productListInLkp;
		}
		
		@FindBy(xpath="//div[contains(text(),'Location Name')]/following::div[contains(@id,'ext-gridrow-')]")
		private List<WebElement> LocListInLkp;
		public List<WebElement> getLocListInLkp() {
			return LocListInLkp;
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
		//Added by Harish.CS
		private WebElement eleProcessNameLsMode;
		public WebElement getEleProcessNameLsMode(String sprocessname)
		{
			eleProcessNameLsMode=driver.findElement(By.xpath("//label[@class='opdoc-title'][text()='"+sprocessname+"']"));
			return eleProcessNameLsMode;
		}
	
		@FindBy(xpath="//input[@value='Done']")
		private WebElement eleDoneBtnLsMode;
		public WebElement getEleDoneBtnLsMode(){
			return eleDoneBtnLsMode;
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
	
		@FindBy(xpath="//span[@class='x-button-label'][text()='Create New Event From Work Order']")
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
		@FindBy(xpath="//*[contains(text(),'StartDateTime')][@class = 'x-label-text-el']/../..//input")
		private WebElement eleStartDateTimeLst1;
		public WebElement getEleStartDateTimeLst1()
		{
			return eleStartDateTimeLst1;
		}
		@FindBy(xpath="//*[contains(text(),'Start Date and Time')][@class = 'x-label-text-el']/../..//input")
		private WebElement eleStartDateTimeLst;
		public WebElement getEleStartDateTimeLst()
		{
			return eleStartDateTimeLst;
		}
		
	
	
		@FindBy(xpath="	//span[text()='Dead Time (In Minutes)']")
		private WebElement eleDeadTimeLst;
		public WebElement getEleDeadTimeLst()
		{
			return eleDeadTimeLst;
		}
		@FindBy(xpath="//*[contains(text(),'EndDateTime')][@class = 'x-label-text-el']/../..//input")
		private WebElement eleEndDateTimeLst1;
		public WebElement getEleEndDateTimeLst1()
		{
			return eleEndDateTimeLst1;
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
	
	
		@FindBy(xpath="(//span[text()='Part']/../../div[2]//input[@class='x-input-el'])[2]")
		private WebElement elePartLaborLkUp2;
		public WebElement getElePartLaborLkUp2()
		{
			return elePartLaborLkUp2;
		}
	
		@FindBy(xpath="//span[text()='To Location']/../../div[2]//input[@class='x-input-el']")
		private WebElement elePartsLocation;
		public WebElement getElePartsLocation()
		{
			return elePartsLocation;
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
		@FindBy(xpath="//*[. = 'Estimated Qty']//*[@class = 'x-input-el']")
		private WebElement eleEstimatedQtyTxtFld;
		public WebElement getEstimatedQtyTxtFld()
		{
			return eleEstimatedQtyTxtFld;
		}
	
		@FindBy(xpath="//*[. = 'Line Price Per Unit']//*[@class = 'x-input-el']")
		private WebElement eleLinePerUnitTxtFld;
		public WebElement getEleLinePerUnitTxtFld()
		{
			return eleLinePerUnitTxtFld;
		}
		
		@FindBy(xpath="//*[. = 'Estimated Price Per Unit']//*[@class = 'x-input-el']")
		private WebElement eleEstimatedPerUnitTxtFld;
		public WebElement getEstimatedPerUnitTxtFld()
		{
			return eleEstimatedPerUnitTxtFld;
		}
	
		@FindBy(xpath="//*[text() = 'Done']")
		private WebElement eleDoneBtn;
		public WebElement getEleDoneBtn()
		{
			return eleDoneBtn;
		}
	
		@FindBy(xpath="//span[text()='Use Price From Pricebook/Contract']//..//..//div[@class= 'x-icon-el x-font-icon']//..//div[@class='x-size-monitors scroll']")
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
	
	
		@FindBy(xpath="//span[@class='x-button-label'][text()='Get Price']")
		private WebElement eleGetPrice;
		public WebElement geteleGetPrice()
		{
			return eleGetPrice;
		}
	
	
		private WebElement eledeletepartChildline;
		public WebElement getEledeletepartchildline(String childlinevalue)
		{
	
			eledeletepartChildline = driver.findElement(By.xpath("(//div[@class='x-inner-el'][contains(text(),'"+childlinevalue+"')])[2]"));
	
			return eledeletepartChildline;
		}
	
		private WebElement eleExpensestap;
		public WebElement getEleExpensestap(String sExpensetype)
		{
	
			eleExpensestap = driver.findElement(By.xpath("//div[@class='x-inner-el'][text()='"+sExpensetype+"']"));
	
			return eleExpensestap;
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
		@FindBy(xpath="//span[@class='x-button-icon x-font-icon x-hidden']//..//span[@class='x-button-label'][text()='Cancel']")
		private WebElement eleclickCancel;
		public  WebElement getEleclickCancel()
		{
	
			return eleclickCancel;
		}
	
		@FindBy(xpath="	//span[@class='x-button-label'][text()='+New']")
		private WebElement eleclickNew;
		public  WebElement getEleclickNew()
		{
	
			return eleclickNew;
		}
		//x-gridcell x-gridcell-gridcell-sfmdelivery-details
		private WebElement eleclickparts;
		public WebElement getEleclickparts(String partsname)
		{
	
			//eleclickparts = driver.findElement(By.xpath("(//div[@class='x-gridcell']//div[text()='"+partsname+"'])[1]"));
			eleclickparts = driver.findElement(By.xpath("(//div[@class='x-inner-el'][text()='"+partsname+"'])[2]"));
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
	
	
	
		@FindBy(xpath="//span[@class='x-button-icon x-shown x-fa fa-list']")
		private WebElement eleLinkedSFM;
		public  WebElement getEleLinkedSFM()
		{
	
			return eleLinkedSFM;
		}
	
	
		private WebElement eleSFMfromLinkedSFM;
		public WebElement getEleSFMfromLinkedSFM(String sSFMName)
		{
	
			eleSFMfromLinkedSFM = driver.findElement(By.xpath("//span[@class='x-button-label'][text()='"+sSFMName+"']"));
	
			return eleSFMfromLinkedSFM;
		}
	
	
	
	
		private WebElement eleSFMfromLinkedSFM2;
		public WebElement getEleSFMfromLinkedSFM2(String sSFMName)
		{
	
			eleSFMfromLinkedSFM2 = driver.findElement(By.xpath("(//span[@class='x-button-label'][text()='Manage Work Details for Products Serviced'])[2]"));
			return eleSFMfromLinkedSFM2;
		}
		@FindBy(xpath="//span[@class='x-button-label'][text()='Discard Changes']")
		private WebElement eleDiscardChanges;
		public  WebElement getEleDiscardChanges()
		{
	
			return eleDiscardChanges;
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
		
		// Tap on the UI
		
		
		
		@FindBy(xpath="//div[@class='x-mask']")
		private WebElement eleTapUI;
		public  WebElement getEleTapUI()
		{
	
			return eleTapUI;
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
	
	
		//verifying Billing Type in WorkOrder View
		@FindBy(xpath="//span[@class='x-label-text-el'][text()='Billing Type']/../..//div[@class='x-innerhtml']")
		private WebElement eleBillingTypelbl;
		public WebElement geteleBillingTypelbl()
		{
			return eleBillingTypelbl;
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
	
		@FindBy(xpath="//div[contains(text(),'Issue')]")
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
		public WebElement getEleCancelLink()
		{
			return eleCancelLnk;
		}
		
		@FindBy(xpath="//input[@class='opdoc-cancel-button']")
		private WebElement eleOPDOCCancelLnk;
		public WebElement geteleOPDOCCancelLnk()
		{
			return eleOPDOCCancelLnk;
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
	
	
	
		//Changed to contains text in order to accomodate mandatory order status* values 	
		@FindBy(xpath="(//*[contains(text(), 'Order Status')]/../..//div[@class='x-input-body-el']/input)[2]")
		private WebElement eleOrderStatusEditMandatoryValue;
		public WebElement geteleOrderStatusEditMandatoryValue()
		{
			return eleOrderStatusEditMandatoryValue;
		}
	
		@FindBy(xpath="//*[text()='Order Status']/../..//div[@class='x-input-body-el']/input")
		private WebElement eleOrderStatusCaseLst;
		public WebElement getEleOrderStatusCaseLst()
		{
			return eleOrderStatusCaseLst;
		}
		
		@FindBy(xpath="//*[text()='Status']/../..//div[@class='x-input-body-el']/input")
		private WebElement eleStatusCaseLst;
		public WebElement getEleStatusCaseLst()
		{
			return eleStatusCaseLst;
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
		
		private WebElement eleProds;
		public WebElement getEleProds(String sProd)
		{
			eleProds = driver.findElement(By.xpath("//div[text()='"+sProd+"']"));
			return eleProds;
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
	
	
		@FindBy(xpath="//*[text()='Proforma Invoice']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement EleProformaInvoiceTxt;
		public WebElement getEleProformaInvoiceTxt()
		{
			return EleProformaInvoiceTxt;
		}
	
		//Added By Harish.CS----
		@FindBy(xpath="(//span[text()='Component'])[2]")
		private WebElement lblComponent;
		public WebElement getLblComponent()
		{
			return lblComponent;
		}
	
		@FindBy(xpath="(//span[text()='Contact'])[2]")
		private WebElement lblContact;
		public WebElement getLblContact()
		{
			return lblContact;
		}
	
		@FindBy(xpath="(//span[text()='Account'])[2]")
		private WebElement lblAccount;
		public WebElement getLblAccount()
		{
			return lblAccount;
		}
	
		@FindBy(xpath="(//span[text()='Product'])[2]")
		private WebElement lblProduct;
		public WebElement getLblProduct()
		{
			return lblProduct;
		}
		
		@FindBy(xpath="//span[text()='Part']")
		private WebElement lblPart;
		public WebElement getLblPart()
		{
			return lblPart;
		}
	
		@FindBy(xpath="(//span[text()='Cancel'])[2]")
		private WebElement lnkLookupCancel;
		public WebElement getLnkLookupCancel()
		{
			return lnkLookupCancel;
		}
	
		@FindBy(xpath="//span[text()='Filters']")
		private WebElement lnkFilters;
		public WebElement getLnkFilters()
		{
			return lnkFilters;
		}
	
	
		@FindBy(xpath="//span[@class='x-button-label'][text()='Add']")
		private WebElement eleAddPSLines;
		public WebElement getEleAddPSLines()
		{
			return eleAddPSLines;
		}
	
		@FindBy(xpath="//span[contains(text(),'Account:')]/following::input[@type='checkbox']")
		private WebElement checkBoxAccount;
		public WebElement getCheckBoxAccount()
		{
			return checkBoxAccount;
		}
		
		@FindBy(xpath="//span[text()='USERTRUNK']/following::input[@type='checkbox']/following::div[@class='x-mask-el']")
		private WebElement checkBoxUserTrunk;
		public WebElement getCheckBoxUserTrunk()
		{
			return checkBoxUserTrunk;
		}
	
		@FindBy(xpath="//span[contains(text(),'Account:')]/following::input[@type='checkbox']/following::div[@class='x-mask-el']")
		private WebElement checkBoxAccount01;
		public WebElement getcheckBoxAccount01()
		{
			return checkBoxAccount01;
		}
	
		@FindBy(xpath="//span[text()='Apply']")
		private WebElement btnApply;
		public WebElement getBtnApply()
		{
			return btnApply;
		}
	
	
		@FindBy(xpath="(//*[text()='Component']/../..//div[@class='x-innerhtml']/../..//input)[2]")
		private WebElement txtComponent;
		public WebElement getTxtComponent()
		{
			return txtComponent;
		}
	
		@FindBy(xpath="(//span[text()='City']/following::input)[1]")
		private WebElement txtCity;
		public WebElement getTxtCity()
		{
			return txtCity;
		}
	
		@FindBy(xpath="(//*[text()='Contact']/../..//div[@class='x-innerhtml']/../..//input)[2]")
		private WebElement txtContact;
		public WebElement getTxtContact()
		{
			return txtContact;
		}
	
		@FindBy(xpath="(//span[text()='Country']/following::input)[1]")
		private WebElement txtCountry;
		public WebElement getTxtCountry()
		{
			return txtCountry;
		}
	
		@FindBy(xpath="(//*[text()='Product']/../..//div[@class='x-innerhtml']/../..//input)[2]")
		private WebElement txtProduct;
		public WebElement getTxtProduct()
		{
			return txtProduct;
		}
	
		@FindBy(xpath="(//span[text()='Top-Level']/following::input)[1]")
		private WebElement txtTopLevel;
		public WebElement getTxtTopLevel()
		{
			return txtTopLevel;
		}
	
		@FindBy(xpath="(//span[text()='Site']/following::input)[1]")
		private WebElement txtSite;
		public WebElement getTxtSite()
		{
			return txtSite;
		}
	
		@FindBy(xpath="//span[text()='Site']")
		private WebElement lblSite;
		public WebElement getlblSite()
		{
			return lblSite;
		}
		
		@FindBy(xpath="//span[text()='To Location']")
		private WebElement lblToLocation;
		public WebElement getlblToLocation()
		{
			return lblToLocation;
		}
	
		@FindBy(xpath="(//span[text()='Zip']/following::input)[1]")
		private WebElement txtZip;
		public WebElement getTxtZip()
		{
			return txtZip;
		}
	
		private WebElement lblChildPart;
		public WebElement  getLblChildPart(String prodName)
		{
			return lblChildPart = driver.findElement(By.xpath("//div[text()='"+prodName+"']"));
		}
	
		@FindBy(xpath="(//span[text()='Contact'])[3]")
		private WebElement lblPartContact;
		public WebElement getLblPartContact()
		{
			return lblPartContact;
		}
	
		@FindBy(xpath="(//span[text()='Account'])[3]")
		private WebElement lblPartAccount;
		public WebElement getLblPartAccount()
		{
			return lblPartAccount;
		}
	
		@FindBy(xpath="(//div[@class='x-inner-el'])[1]")
		private WebElement lblLookupOptns;
		public WebElement getLblLookupOptns()
		{
			return lblLookupOptns;
		}
	
		@FindBy(xpath="//*[text()='Country']/../..//div[@class='x-input-body-el']/input")
		private WebElement eleCountry_Edit_Lst;
		public WebElement geteleCountry_Edit_Lst()
		{
			return eleCountry_Edit_Lst;
		}
	
		@FindBy(xpath="//span[@class='x-label-text-el'][text()='State']/following::div[@class='sfm-delivery-textField-value']")
//		@FindBy(xpath="(//span[@class='x-label-text-el'][text()='State']/following::input)[1]")
		private WebElement eleLblStateName;
		public WebElement getEleLblStateName()
		{
			return eleLblStateName;
		}
	
//		@FindBy(xpath="//span[@class='x-label-text-el'][text()='Country']/../..//div[@class='x-innerhtml']")
//		@FindBy(xpath="//span[@class='x-label-text-el'][text()='Country']/../..//div[@class='x-mask-el']")
		@FindBy(xpath="(//span[text()='Country']/following::input[@name='picker'][@type='text'][@class='x-input-el'])[1]")
		private WebElement eleLblCountryName;
		public WebElement getEleLblCountryName()
		{
			return eleLblCountryName;
		}
	
		@FindBy(xpath="//span[@class='x-label-text-el'][text()='Completed Date Time']/../..//div[@class='x-innerhtml']")
		private WebElement eleLblCompletedDateTime;
		public WebElement getEleLblCompletedDateTime()
		{
			return eleLblCompletedDateTime;
		}
	
		@FindBy(xpath="//span[@class='x-label-text-el'][text()='Requested City']/following::div[@class='sfm-delivery-textField-value']")
		private WebElement eleLblRequestedCity;
		public WebElement getEleLblRequestedCity()
		{
			return eleLblRequestedCity;
		}
	
//		@FindBy(xpath="//span[@class='x-label-text-el'][text()='Requested Country']/../..//div[@class='x-innerhtml']")
		@FindBy(xpath="(//span[text()='Requested Country']/following::input[@name='picker'][@type='text'][@class='x-input-el'])[1]")
		private WebElement eleLblRequestedCountry;
		public WebElement getEleLblRequestedCountry()
		{
			return eleLblRequestedCountry;
		}
		
		@FindBy(xpath="//span[text()='Reset']")
		private WebElement btnReset;
		public WebElement getBtnReset()
		{
			return btnReset;
		}
		
		@FindBy(xpath="//span[text()='Complex Filter']/../..//input[@type='checkbox']")
		private WebElement chkBoxComplexFilter;
		public WebElement getChkBoxComplexFilter()
		{
			return chkBoxComplexFilter;
		}
		
		//close---------
	
	
		@FindBy(xpath="//div[@class='x-innerhtml'][text()='2 Issues Found']")
		private WebElement eleChildLine2IssuesFound;
		public WebElement getEleChildLine2IssuesFound()
		{
			return eleChildLine2IssuesFound;
		}
	
		@FindBy(xpath="//div[@class='x-innerhtml'][text()='1 Issue Found']")
		private WebElement eleChildLine1IssueFound;
		public WebElement getEleChildLine1IssueFound()
		{
			return eleChildLine1IssueFound;
		}
	
	
	
	
		@FindBy(xpath="//span[@class='x-button-label'][text()='Labor (0): You must add at least one line to save this record.']")
		private WebElement eleNoLaborEntry;
		public WebElement getEleNoLaborEntry()
		{
			return eleNoLaborEntry;
		}
	
	
		@FindBy(xpath="//span[@class='x-button-label'][text()='Parts (0): You have not created any lines. Do you still want to save?']")
		private WebElement eleNoPartsEntry;
		public WebElement getEleNoPartsEntry()
		{
			return eleNoPartsEntry;
		}
	
	
		@FindBy(xpath="//input[@class='x-input-el'][@type='checkbox']/../../div[@class='x-input-body-el']")
		private WebElement elePartsIssueCheckbox;
		public WebElement getElePartsIssueCheckbox()
		{
			return elePartsIssueCheckbox;
		}
	
		// Fields on the ChildLines and to get there values
	
		private WebElement elechildlinefields;
		public WebElement  getelechildlinefields(String sfieldName)
		{
			elechildlinefields = driver.findElement(By.xpath("//span[@class='x-label-text-el'][text()='"+sfieldName+"']//..//..//input[@class='x-input-el']"));
			return elechildlinefields;
		}
	
		@FindBy(xpath="//div[@class='x-unsized x-toggleslider x-slider x-component x-size-monitored x-paint-monitored x-has-width x-widthed x-off']")
		private WebElement eleAutoOffSwitchBtn;
		public WebElement getEleAutoOffSwitchBtn()
		{
			return eleAutoOffSwitchBtn;
		}
	
		@FindBy(xpath="//div[@class='x-unsized x-toggleslider x-slider x-component x-size-monitored x-paint-monitored x-has-width x-widthed x-on']")
		private WebElement eleAutoOnSwitchBtn;
		public WebElement getEleAutoOnSwitchBtn()
		{
			return eleAutoOnSwitchBtn;
		}
	
	
		@FindBy(xpath="//div[@class='x-component x-msgbox-text x-layout-box-item x-layout-vbox-item x-stretched']/div")
		private WebElement elePopupTxt;
		public WebElement getElePopupTxt()
		{
			return elePopupTxt;
		}
	
	
		@FindBy(xpath="//span[@class='x-button-icon x-shown icon-save']")
		private WebElement eleQuickSaveIcn;
		public WebElement getEleQuickSaveIcn()
		{
			return eleQuickSaveIcn;
		}
	
		@FindBy(xpath="//div[@class='svmx-editor-link'][text()='Edit']")
		private WebElement eleAutoText_10540TxtFld;
		public WebElement getEleAutoText_10540TxtFld()
		{
			return eleAutoText_10540TxtFld;
		}
	
		@FindBy(xpath="//input[@type='number']")
		private WebElement eleNumber_10540TxtFld;
		public WebElement getEleNumber_10540TxtFld()
		{
			return eleNumber_10540TxtFld;
		}
	
	
	
		@FindBy(xpath="//span[@class='x-button-label'][text()='Update']")
		private WebElement eleUpdateBtn;
		public WebElement getEleUpdateBtn()
		{
			return eleUpdateBtn;
		}
	
		@FindBy(xpath="//i[@class='fa fa-toggle-off fa-2x']")
		private WebElement elePartsToggleBtn;
		public WebElement getElePartsToggleBtn()
		{
			return elePartsToggleBtn;
		}
	
	
		@FindBy(xpath="//span[@class='x-button-label'][text()='Remove item']")
		private WebElement eleRemoveItemLnk;
		public  WebElement getEleRemoveItemLnk()
		{
	
			return eleRemoveItemLnk;
		}
	
	
		@FindBy(xpath="//span[text()='Search']")
		private WebElement eleSearchBtn;
		public WebElement getEleSearchBtn()
		{
			return eleSearchBtn;
		}
	
		@FindBy(xpath="//span[contains(text(),'Account')]/../..//input")
		private WebElement eleIBAccountIDTxt;
		public WebElement getEleIBAccountIDTxt()
		{
			return eleIBAccountIDTxt;
		}
		
		@FindBy(xpath="//span[contains(text(),'Installed Product ID')]/../..//input")
		private WebElement eleIBIDTxt;
		public WebElement getEleIBIDTxt()
		{
			return eleIBIDTxt;
		}
		
		@FindBy(xpath="//span[contains(text(),'Contact ID')]/../..//input")
		private WebElement eleContactIDTxt;
		public WebElement getEleContactIDTxt()
		{
			return eleContactIDTxt;
		}
	
		//@FindBy(xpath="//div[@class='sfm-delivery-textField-value'][text()='"++"']")
		
		private WebElement eleIBSubjectTxt;
		public WebElement getEleIBSubjectTxt(String sSubject)
		{
			driver.findElement(By.xpath("//div[text()='"+sSubject+"']"));
			return eleIBSubjectTxt;
		}
		
		@FindBy(xpath="//span[text()='Component']/../..//input")
		private WebElement eleIBComponentTxt;
		public WebElement getEleIBComponentTxt()
		{
			return eleIBComponentTxt;
		}
		
		@FindBy(xpath="//span[text()='Scheduled Date']/../..//input")
		private WebElement eleIBScheduledTxtFld;
		public WebElement getEleIBScheduledTxtFld()
		{
			return eleIBScheduledTxtFld;
		}


		@FindBy(xpath="//*[contains(text(),'StartDateTime')][@class = 'x-label-text-el']/../..//input")
		private WebElement eleStartDateTimeTxtFld;
		public WebElement getEleStartDateTimeTxtFld()
		{
			return eleStartDateTimeTxtFld;
		}
		
		@FindBy(xpath="//*[contains(text(),'EndDateTime')][@class = 'x-label-text-el']/../..//input")
		private WebElement eleEndDateTimeTxtFld;
		public WebElement getEleEndDateTimeTxtFld()
		{
			return eleEndDateTimeTxtFld;
		}
		

		@FindBy(xpath="//div[@class='x-label-el sfmsearch-include-online-label']/../div[@class = 'x-body-el']/div/div[5]")
		private WebElement eleIncludeOnlineRdBtn;
		public WebElement getEleIncludeOnlineRdBtn()
		{
			return eleIncludeOnlineRdBtn;
		}
		
		@FindBy(xpath="//div[@class='icon-cloud-download sfmsearch-download-icon']")
		private WebElement eleCloudIcn;
		public WebElement getEleCloudIcn()
		{
			return eleCloudIcn;
		}
		
		@FindBy(xpath="//div[text()='No records to display.']")
		private WebElement eleNoRecordsTxt;
		public WebElement getEleNoRecordsTxt()
		{
			return eleNoRecordsTxt;
		}
				
		@FindBy(xpath="//*[text()='Case Reason']/../..//div[@class='x-input-body-el']/input")
		private WebElement eleCaseReasonLst;
		public WebElement getEleCaseReasonLst()
		{
			return eleCaseReasonLst;
		}
		private WebElement eleObjectTxt;
		public WebElement getEleObjectTxt(String sValue)
		{
			eleObjectTxt = driver.findElement(By.xpath("//div[@class='x-innerhtml'][text()='"+sValue+"']"));
			return eleObjectTxt;
		}
		
		private WebElement eleActionsTxt;
		public WebElement getEleActionsTxt(String sActionsName)
		{
			eleActionsTxt=driver.findElement(By.xpath("//span[@class='x-button-label'][text()='"+sActionsName+"']"));
			//eleActionsTxt=driver.findElement(By.xpath("(//span[@class='x-button-label'][text()='"+sActionsName+"']/../span)[4]"));
			return eleActionsTxt;
		}
		
		@FindBy(xpath="(//span[text()='Priority']/../..//input[@class='x-input-el'])[2]")
		private WebElement elePriorityLst;
		public WebElement getElePriorityLst()
		{
			return elePriorityLst;
		}
		@FindBy(xpath="(//span[text()='Account']/../..//input[@class='x-input-el'])[2]")
		private WebElement eleAccountTxtFld;
		public WebElement getEleAccountTxtFld()
		{
			return eleAccountTxtFld;
		}
		//lks
		@FindBy(xpath="//div[@class='x-inner x-headercontainer-inner x-align-stretch x-layout-hbox x-horizontal x-pack-start x-layout-box x-container-inner x-component-inner']//div[text()='Work Order Number']")
		private WebElement eleWoOrderNumberTxt;
		public WebElement getEleWoOrderNumberTxt()
		{
			return eleWoOrderNumberTxt;
		}
		@FindBy(xpath="//div[@class='x-inner x-headercontainer-inner x-align-stretch x-layout-hbox x-horizontal x-pack-start x-layout-box x-container-inner x-component-inner']//div[text()='Billing City']")
		private WebElement eleBillingCityTxt;
		public WebElement getEleBillingCityTxt()
		{
			return eleBillingCityTxt;
		}
		@FindBy(xpath="//div[@class='x-inner x-headercontainer-inner x-align-stretch x-layout-hbox x-horizontal x-pack-start x-layout-box x-container-inner x-component-inner']//div[text()='Priority']")
		private WebElement elePriorityTxt;
		public WebElement getElePriorityTxt()
		{
			return elePriorityTxt;
		}

		private WebElement eleWoNumTxt;
		public WebElement getEleWoNumTxt(String sWoNumTxt)
		{
			eleWoNumTxt=driver.findElement(By.xpath("//div[text()="+sWoNumTxt+"]/../../..//div[@class='x-inner-el sfmsearch-grid-cell-inner']"));
			return eleWoNumTxt;
		}
		private WebElement eleWoBillingCityTxt;
		public WebElement getEleWoBillingCityTxt(String sWoNumTxt, String sBillingCity)
		{
			eleWoBillingCityTxt=driver.findElement(By.xpath("//div[text()='"+sWoNumTxt+"']/../../..//div[@class='x-inner-el sfmsearch-grid-cell-inner'][text()='"+sBillingCity +"']"));
			return eleWoBillingCityTxt;
		}
		private WebElement eleWoPriorityTxt;
		public WebElement getEleWoPriorityTxt(String sWoNumTxt, String sPriority)
		{
			eleWoPriorityTxt=driver.findElement(By.xpath("//div[text()='"+sWoNumTxt+"']/../..//div[@class='x-inner-el sfmsearch-grid-cell-inner'][text()='"+sPriority +"']"));
			return eleWoPriorityTxt;
		}
		
	/**
	 * Select the process name from the action menu in workorder page
	 * @param commonsPo
	 * @param sActionsName
	 * @throws InterruptedException
	 */
		public void selectAction(CommonsPO commonsPo, String sActionsName) throws InterruptedException
		{
			Thread.sleep(5000);
			commonsPo.tap(getEleActionsLnk());	
			commonsPo.getSearch(getEleActionsTxt(sActionsName));		
			Thread.sleep(5000);
			driver.findElement(By.xpath("(//span[@class='x-button-label'][text()='"+sActionsName+"']/../span)[4]")).getLocation();
			Thread.sleep(5000);
			commonsPo.tap(getEleActionsTxt(sActionsName));

		}
	
		public void selectActionWithIcon(CommonsPO commonsPo, String sActionsName) throws InterruptedException
		{
			Thread.sleep(1000);
			//getEleActionsLnk().click();
			commonsPo.tap(getEleActionsLnk());	
			commonsPo.getSearch(getEleActionsTxtWithIcon(sActionsName));
			try {
			commonsPo.tap(getEleActionsTxtWithIcon(sActionsName));
			}catch(Exception e)
			{commonsPo.tap(getEleActionsTxtWithIcon(sActionsName),20,20);
			}
		}
	
		public void createNewEvent(CommonsPO commonsPo, String sSubject, String sDescription) throws InterruptedException
		{
			selectAction(commonsPo, "Create New Event From Work Order");
			Assert.assertTrue(getEleNewEventTxt().isDisplayed(), "New Event screen is not displayed");
			ExtentManager.logger.log(Status.PASS,"New Event screen is displayed successfully");		
			commonsPo.setDateTime24hrs(getEleStartDateTimeLst1(), 0,"05", "00"); //set start time to Today
			commonsPo.setDateTime24hrs(getEleEndDateTimeLst1(), 0,"06","00"); //set end time
			getEleSubjectTxtFld().sendKeys(sSubject);
			//getEleDescriptionTxtFld().click();
			//getEleDescriptionTxtFld().sendKeys(sDescription);
			commonsPo.tap(getEleSaveLnk());
			/*try {
				if(getEleYesBtn() != null){
					commonsPo.tap(getEleYesBtn());	
				}
			}
			catch(Exception e){
	
			}*/
		//	Assert.assertTrue(getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		//	ExtentManager.logger.log(Status.PASS,"Creation of WO event is successfull and Work Order Screen is displayed successfully");
		}
		public void validateServiceReport(CommonsPO commonsPo, String sPrintReportSearch, String sWorkOrderID) throws InterruptedException
		{	
			selectAction(commonsPo, sPrintReportSearch);
			Thread.sleep(GenericLib.iLowSleep);
			Assert.assertTrue(getEleWOServiceReportTxt(sPrintReportSearch).isDisplayed(), "Work Order Service Report is not displayed.");
			ExtentManager.logger.log(Status.PASS,"Work Order Service Report is displayed successfully");		
			Assert.assertTrue(getEleWONumberTxt(sWorkOrderID).isDisplayed(),"WO updated report details is not displayed");
			ExtentManager.logger.log(Status.PASS,"Work order updated details for the work order "+sWorkOrderID);
			
			Thread.sleep(GenericLib.iHighSleep);
			//commonsPo.tap(getEleDoneLnk());
			getEleDoneLnk().click();
	
			((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
			Thread.sleep(GenericLib.iHighSleep);
			((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
			Thread.sleep(GenericLib.iHighSleep);
			//Navigation back to Work Order after Service Report
			Assert.assertTrue(getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
//			ExtentManager.logger.log(Status.PASS,"Creation of WO event is successfull and Work Order Screen is displayed successfully");
		}
	
		// To add Parts
	
		public void addParts(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sProductName1) throws InterruptedException
		{
			commonsPo.tap(workOrderPo.getElePartLnk());
			commonsPo.lookupSearch(sProductName1);
			commonsPo.tap(workOrderPo.getEleAddselectedbutton());
	
		}
	
		// Add parts for Manage Work Details
	
		public void addPartsManageWD(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sProductName1) throws InterruptedException
		{
			commonsPo.tap(workOrderPo.getElePartLnk());
			commonsPo.tap(getElePartLaborLkUp2());
			commonsPo.lookupSearch(sProductName1);
			//commonsPo.tap(workOrderPo.getEleAddselectedbutton());
			//Thread.sleep(1000);
			commonsPo.tap(getEleDoneBtn());
	
		}
	
	
	
		// To add PS Lines to the Work Order
		public void addPSLines(CommonsPO commonsPo, WorkOrderPO workOrderPo,String sSerialNumber)throws InterruptedException
		{
			commonsPo.tap(workOrderPo.getEleAddPSLines());
			commonsPo.lookupSearch(sSerialNumber);
			commonsPo.tap(workOrderPo.getEleAddselectedbutton());
	
		}
	
		// To multi Select the Parts for the Work Order ChildLines
	
		public void addParts(CommonsPO commonsPo, WorkOrderPO workOrderPo, String[] sProductName1) throws InterruptedException
		{
			commonsPo.tap(workOrderPo.getElePartLnk());
			for(int i=0;i<sProductName1.length ;i++) {
				commonsPo.lookupSearch(sProductName1[i]);
			} 
			commonsPo.tap(workOrderPo.getEleAddselectedbutton());
	
		}
		//To add product to parts
		public void addProductParts(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sProductName1) throws InterruptedException
		{
			commonsPo.tap(workOrderPo.getEleCasePartIcn());
			commonsPo.tap(workOrderPo.getElePartLst());
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
			commonsPo.setPickerWheelValue( getEleActivityTypeLst(), sActivityType);	
	
			Thread.sleep(2000);
			commonsPo.setDateTime24hrs(getEleStartDateTimeLst(), 0,"0", "0"); //set start time to Today
			commonsPo.setDateTime24hrs(getEleEndDateTimeLst(),  1,"9","00"); //set end time
	
			//		workOrderPo.setTime(commonsPo, workOrderPo.getEleStartDateTimeLst(), 1, "6");  // Sets start date time
			//		workOrderPo.setTime(commonsPo, workOrderPo.getEleEndDateTimeLst(), 1, "8");    // Sets end date time
	
			//Add the price and quantity
			commonsPo.tap(getEleUsePriceToggleBtn());
			commonsPo.tap(getEleLineQtyTxtFld());
			getEleLineQtyTxtFld().sendKeys("10");
			commonsPo.tap(getEleLinePerUnitTxtFld());
			getEleLinePerUnitTxtFld().sendKeys("1000");
			Thread.sleep(1000);
			commonsPo.tap(getEleDeadTimeLst());
		
				if(commonsPo.isDisplayedCust(getEleclickCancel()))
				{
					commonsPo.tap(getEleclickCancel());
					Thread.sleep(2000);
				}
				
			commonsPo.tap(getEleDoneBtn());
			//Verify to Manage WO lines
			Assert.assertTrue(getEleProcessName(sprocessname).isDisplayed(),"Failed to add Labor parts");  
			ExtentManager.logger.log(Status.PASS,"Labor parts are added and saved successfully. ");		
		}
		// Adding Labor for Customized Date entries into Labor
		public void addLaborCustomizedDate(CommonsPO commonsPo, WorkOrderPO workOrderPo,String sActivityType, String sStartDate, String sEndDate, String sprocessname) throws InterruptedException
		{	//Adding labor parts name
			commonsPo.tap(workOrderPo.getEleAddLaborLnk());
			//Selecting Activity Type
			commonsPo.setPickerWheelValue(getEleActivityTypeLst(), sActivityType);	
			Thread.sleep(2000);
			if(Integer.parseInt(sEndDate)< 10)
			{
				sEndDate = "0"+sEndDate;
			}	
			commonsPo.setDateTime24hrs(getEleStartDateTimeLst(), 0, sStartDate, "00"); //set start time to Today
			commonsPo.setDateTime24hrs(getEleEndDateTimeLst(),  0,sEndDate,"00"); //set end time
			commonsPo.tap(getEleDoneBtn());
	
		}
	
		//To add Travel
		public void addTravel(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sprocessname) throws InterruptedException
		{	//Adding labor parts name
			commonsPo.tap(workOrderPo.getEleAddTravelLnk());
	
			commonsPo.setDateTime24hrs(getEleStartDateTimeLst(), 0,"0", "0"); //set start time to Today
			commonsPo.setDateTime24hrs(getEleEndDateTimeLst(), 1,"9","00"); //set end time
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
	
		public void addTravelwithTime(CommonsPO commonsPo, WorkOrderPO workOrderPo, String sprocessname, String sStartTime , String sEndTime) throws InterruptedException
		{	//Adding labor parts name
			commonsPo.tap(workOrderPo.getEleAddTravelLnk());
			if(Integer.parseInt(sEndTime)< 10)
			{
				sEndTime = "0"+sEndTime;
			}
			commonsPo.setDateTime24hrs(getEleStartDateTimeLst(), 0,sStartTime, "00"); //set start time to Today
			commonsPo.setDateTime24hrs(getEleEndDateTimeLst(), 0,sEndTime,"00"); //set end time			
			commonsPo.tap(getEleDoneBtn());
		}
	
	
		// To add Expense
	
		public void addExpense(CommonsPO commonsPo, WorkOrderPO workOrderPo,String expenseType,String sprocessname, String sLineQty, String sLinepriceperUnit) throws InterruptedException
		{	//Adding Expense name
			commonsPo.tap(workOrderPo.getEleAddExpenseLnk());
			//commonsPo.tap(workOrderPo.getEleAddExpenseType());
			commonsPo.setPickerWheelValue(getEleAddExpenseType(), expenseType);
	
			//Add the price and quantity
			//commonsPo.tap(getEleUsePriceToggleBtn(),20,20);
			getEleLineQtyTxtFld().sendKeys(sLineQty);
			getEleLinePerUnitTxtFld().sendKeys(sLinepriceperUnit);	
			commonsPo.tap(getEleDoneBtn());
	
			//Verify to Manage WO lines
			//Assert.assertTrue(getEleProcessName(sprocessname).isDisplayed(), "Failed to add Labor parts");   
			//ExtentManager.logger.log(Status.PASS,"Labor parts are added and saved successfully. ");		
		}
		
		// To add Expense Values to the Estimates
		public void addExpenseEstimates(CommonsPO commonsPo, WorkOrderPO workOrderPo,String expenseType,String sprocessname, String sEstimatedQty, String sEstimatedpriceperUnit) throws InterruptedException
		{	//Adding Expense name
			commonsPo.tap(workOrderPo.getEleAddExpenseLnk());
			commonsPo.tap(workOrderPo.getEleAddExpenseType());
			commonsPo.setPickerWheelValue(getEleAddExpenseType(), expenseType);
	
			//Add the price and quantity
			commonsPo.tap(getEleUsePriceToggleBtn());
			getEstimatedQtyTxtFld().sendKeys(sEstimatedQty);
			getEstimatedPerUnitTxtFld().sendKeys(sEstimatedpriceperUnit);	
			commonsPo.tap(getEleDoneBtn());	
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
			Thread.sleep(GenericLib.iMedSleep);
			commonsPo.tap(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
	
			// Select the Work Order
			exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
			selectAction(commonsPo, sFieldServiceName);		
		}
	
	
	
		//Navigation to WorkOrder SFM with child search	
		public void navigateToWOSFM(CommonsPO commonsPo, ExploreSearchPO exploreSearchPo, String sExploreSearch, String sExploreChildSearchTxt, String sWOName, String sFieldServiceName ) throws InterruptedException
		{
			try {
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			//exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
			Thread.sleep(GenericLib.iLowSleep);
			commonsPo.tap(exploreSearchPo.getEleSearchNameTxt(sExploreSearch),20,20);
			Thread.sleep(3000);
			commonsPo.waitforElement(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt), 3);			
			commonsPo.tap(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt),20,20);
	
			// Select the Work Order
			exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
			if(sFieldServiceName!=null)
			{
				selectAction(commonsPo, sFieldServiceName);	
			}
			}catch(Exception e)
			{
				throw e;
			}
	
		}
	
		public void navigateToWOSFMWithIcon(CommonsPO commonsPo, ExploreSearchPO exploreSearchPo, String sExploreSearch, String sExploreChildSearchTxt, String sWOName, String sFieldServiceName ) throws InterruptedException
		{
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			//exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
			commonsPo.tap(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
			Thread.sleep(GenericLib.iMedSleep);
			commonsPo.tap(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
	
			// Select the Work Order
			exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
			if(sFieldServiceName!=null)
			{
				commonsPo.tap(getEleActionsLnk());
				Thread.sleep(10000);
//				commonsPo.swipeUp();
//				getTest().click();
//				commonsPo.tap(getTest());
//				System.out.println(sFieldServiceName);
//				getEleActionsTxtWithIcon(sFieldServiceName).click();
				commonsPo.tap(getEleActionsTxtWithIcon(sFieldServiceName));
//				selectActionWithIcon(commonsPo, sFieldServiceName);	
			}
	
		}
	
	
	
		//Navigate to WorkOrder Screen with a child search present
		public void navigatetoWO(CommonsPO commonsPo, ExploreSearchPO exploreSearchPo, String sExploreSearch, String sExploreChildSearchTxt, String sWOName) throws InterruptedException {
			commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			Thread.sleep(GenericLib.iMedSleep);
			//exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
			commonsPo.tap(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
			Thread.sleep(GenericLib.iMedSleep);
			commonsPo.tap(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
	
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
			//exploreSearchPO.getEleSearchNameTxt(sExploreSearch).click();
			commonsPo.tap(exploreSearchPO.getEleSearchNameTxt(sExploreSearch));
			commonsPo.tap(exploreSearchPO.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
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
			commonsPo.tap(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
	
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
	
		@FindBy(xpath="(//*[text()='Work Order Number'])[3]")
		private WebElement WorkOrderNumber ;
		public WebElement getWorkOrderNumber()
		{
			return WorkOrderNumber;
		}
	
		@FindBy(xpath="(//span[text()='Customer Down']//..//..//div[@class='x-size-monitors scroll'])[3]")
		private WebElement CustomerDown ;
		public WebElement getCustomerDown()
		{
			return CustomerDown;
		}
	
	
		@FindBy(xpath="//*[text()='Is Billable']")
		private WebElement IsBillable ;
		public WebElement getIsBillable()
		{
			return IsBillable;
		}
	
		@FindBy(xpath="//*[text()='Closed By']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement closedby ;
		public WebElement getclosedby()
		{
			return closedby;
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
	
		@FindBy(xpath="//*[text()='Email']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement Email;
		public WebElement getEmailvalue()
		{
			return Email;
		}
	
		@FindBy(xpath="//*[text()='URL']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement URL;
		public WebElement getURLvalue()
		{
			return URL;
		}
	
		@FindBy(xpath="//*[text()='Number']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement Number;
		public WebElement getNumbervalue()
		{
			return Number;
		}
	
		@FindBy(xpath="//*[text()='Phone']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement Phone;
		public WebElement getPhonevalue()
		{
			return Phone;
		}
	
		@FindBy(xpath="//*[text()='Currency']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement Currency;
		public WebElement getCurrencyvalue()
		{
			return Currency;
		}
	
	
		@FindBy(xpath="(//span[@class='x-label-text-el'][text()='Problem Description']/../..//div[@class='x-innerhtml']/span)[2]")
		private WebElement ProblemDescription;
		public WebElement getProblemDescription()
		{
			return ProblemDescription;
		}
	
	
		//span[@class='x-label-text-el'][text()='Problem Description']/../..//div[@class='x-innerhtml']/span)[2]
	
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
	
	
		@FindBy(xpath="(//div[contains(text(), 'Labor')][@class='x-panel-title-text']/../../../..//div[@class='x-cells-el'])[1]")
		private WebElement Laborontap;
		public WebElement openLaborontap()
		{
			return Laborontap;
		}
	
		@FindBy(xpath="(//div[contains(text(), 'Labor')][@class='x-panel-title-text']/../../../..//div[@class='x-cells-el'])[2]")
		private WebElement Laboronsecondprt;
		public WebElement getLaboronsecondprt()
		{
			return Laboronsecondprt;
		}
	
		
		
	
		@FindBy(xpath="//*[text()='Date Required']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement DateRequired;
		public WebElement getDateRequired()
		{
			return DateRequired;
		}
	
	
		@FindBy(xpath="//*[text()='Start Date and Time']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement StartDateandTime;
		public WebElement getStartDateandTime()
		{
			return StartDateandTime;
		}
	
	
		@FindBy(xpath="//*[text()='Record Type']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement RecordType;
		public WebElement getRecordType()
		{
			return RecordType;
		}
	
		@FindBy(xpath="//*[text()='Canceled By']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement CanceledBy;
		public WebElement getCanceledBy()
		{
			return CanceledBy;
		}
	
		@FindBy(xpath="//*[text()='Line Type']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement LineType;
		public WebElement getLineType()
		{
			return LineType;
		}
	
	
		@FindBy(xpath="//*[contains(text(), 'Product History (')]")
		private WebElement ProductHistory ;
		public WebElement getProductHistory()
		{
			return ProductHistory;
		}
	
		@FindBy(xpath="//*[contains(text(), 'Account History (')]")
		private WebElement AccountHistory ;
		public WebElement getAccountHistory()
		{
			return AccountHistory;
		}
	
	
		@FindBy(xpath="(//*[contains(text(), 'Product History (')]//..//..//..//..//..//div[text()='Work Order Number']//..//..//..//..//..//..//div[@class='x-inner-el sfmdelivery-history-grid-cell-inner'])[2]")
		private WebElement EleProHisWO ;
		public WebElement getProHisWO()
		{
			return EleProHisWO;
		}
	
		@FindBy(xpath="(//*[contains(text(), 'Account History (')]//..//..//..//..//..//div[text()='Work Order Number']//..//..//..//..//..//..//div[@class='x-inner-el sfmdelivery-history-grid-cell-inner'])[2]")
		private WebElement EleAccHisWO ;
		public WebElement getAccHisWO()
		{
			return EleAccHisWO;
		}
	
		@FindBy(xpath="(//*[contains(text(), 'Product History (')]//..//..//..//..//..//div[text()='Work Order Number']//..//..//..//..//..//..//div[@class='x-inner-el sfmdelivery-history-grid-cell-inner'])[5]")
		private WebElement EleProHisWO1 ;
		public WebElement getProHisWO1()
		{
			return EleProHisWO1;
		}
	
		@FindBy(xpath="(//*[contains(text(), 'Account History (')]//..//..//..//..//..//div[text()='Work Order Number']//..//..//..//..//..//..//div[@class='x-inner-el sfmdelivery-history-grid-cell-inner'])[5]")
		private WebElement EleAccHisWO1 ;
		public WebElement getAccHisWO1()
		{
			return EleAccHisWO1;
		}
	
	
	
		@FindBy(xpath="//*[text()='Number']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement txtNumber;
		public WebElement getTxtNumber()
		{
			return txtNumber;
		}
		
		@FindBy(xpath="//*[text()='No Of Times Assigned']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement NoOfTimesAssigned;
		public WebElement GetEleNoOfTimesAssigned_Edit_Input()
		{
			return NoOfTimesAssigned;
		}
	
		@FindBy(xpath="//span[@class='x-button-icon x-shown icon-chevron-left svmx-back-button']")
		private WebElement eleBacktoWorkOrderlnk;
		public WebElement geteleBacktoWorkOrderlnk()
		{
			return eleBacktoWorkOrderlnk;
		}
	
		//index is provided as we are editing a double layer page which contains two accounts.
		@FindBy(xpath="(//div[. = 'Account']//input[@class = 'x-input-el'])[2]")
		private WebElement eleAccount_Edit_Input;
		public WebElement getEleAccount_Edit_Input()
		{
			return eleAccount_Edit_Input;
		}
	
		//Retreiving Part value in Edit of part
		@FindBy(xpath="//*[text()='Part']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement elePart_Edit_Input;
		public WebElement getelePart_Edit_Input()
		{
			return elePart_Edit_Input;
		}
	
		@FindBy(xpath="//*[text()='Billing Information']/../..//div[@class='x-innerhtml']/../..//span")
		private WebElement elePart_BillingInformationEdit_Input;
		public WebElement getElePart_BillingInformation_Edit_Input()
		{
			return elePart_BillingInformationEdit_Input;
		}
	
		@FindBy(xpath="//*[text()='Received City']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement elePart_Received_City_Edit_Input;
		public WebElement getelePart_Received_City_Edit_Input()
		{
			return elePart_Received_City_Edit_Input;
		}
	
		@FindBy(xpath="//*[text()='Billable Qty']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement elePart_BillableQty_Edit_Input;
		public WebElement getElePart_BillableQty_Edit_Input()
		{
			return elePart_BillableQty_Edit_Input;
		}
	
		@FindBy(xpath="//*[text()='Date Received']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement elePart_DateReceived_Edit_Input;
		public WebElement getElePart_DateReceived_Edit_Input()
		{
			return elePart_DateReceived_Edit_Input;
		}
	
	
		@FindBy(xpath="//*[text()='Start Date and Time']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement elePart_StartDateTime_Edit_Input;
		public WebElement getElePart_StartDateTime_Edit_Input()
		{
			return elePart_StartDateTime_Edit_Input;
		}
	
	
		//VT: Need to update the xpath as index is not a viable solution-- for now proceeding
		@FindBy(xpath="(//div[contains(text(), 'Parts')][@class='x-panel-title-text']/../../../..//div[@class='x-cells-el'])[3]")
		private WebElement partsontap1;
		public WebElement openpartsontap1()
		{
			return partsontap1;
		}
	
		@FindBy(xpath="//*[text()='Is Entitlement Performed']")
		private WebElement EleIsEntitlementPerformed ;
		public WebElement getEleIsEntitlementPerformed()
		{
			return EleIsEntitlementPerformed;
		}
	
		@FindBy(xpath="//*[text()='Update']")
		private WebElement EleUpdateLnk ;
		public WebElement getEleUpdateLnk()
		{
			return EleUpdateLnk;
		}
	
		@FindBy(xpath="(//*[contains(text(),'Description')][@class = 'x-label-text-el']/../..//textarea)[2]")
		private WebElement eleProblemDesc_Edit_WorkOrder;
		public WebElement geteleProblemDesc_Edit_WorkOrder()
		{
			return eleProblemDesc_Edit_WorkOrder;
		}
	
		@FindBy(xpath="//*[contains(text(),'Description')][@class = 'x-innerhtml']/../../../../../../..//textarea")
		private WebElement eleProblemDesc_Edit_WorkOrderPopup;
		public WebElement geteleProblemDesc_Edit_WorkOrderPopup()
		{
			return eleProblemDesc_Edit_WorkOrderPopup;
		}	
	
		@FindBy(xpath="//*[text()='Billing Type']/../..//div[@class='x-unsized x-textinput x-input x-component x-has-width x-widthed']/div[@class='x-input-body-el']/input")
		private WebElement eleBillingType_Edit_Lst;
		public WebElement geteleBillingType_Edit_Lst()
		{
			return eleBillingType_Edit_Lst;
		}
	
	
		@FindBy(xpath="(//span[@class='tools-sync-button-label-largemodern'][text()='Work Order'])[1]")
		private WebElement eleWorkOrderLeftPane_View;
		public WebElement geteleWorkOrderLeftPane_View()
		{
			return eleWorkOrderLeftPane_View;
		}
	
		@FindBy(xpath="//*[contains(text(),'Auto_Date1')][@class = 'x-label-text-el']/../..//input")
		private WebElement eleAutoDate1_Edit_Input;
		public WebElement getEleAutoDate1_Edit_Input()
		{
			return eleAutoDate1_Edit_Input;
		}
	
	
		@FindBy(xpath="//*[contains(text(),'Auto_Date2')][@class = 'x-label-text-el']/../..//input")
		private WebElement eleAutoDate2_Edit_Input;
		public WebElement getEleAutoDate2_Edit_Input()
		{
			return eleAutoDate2_Edit_Input;
		}
	
	
	
		@FindBy(xpath="//span[text()='Attached Documents']")
		private WebElement eleAttachedDocumentLeftPane;
		public WebElement geteleAttachedDocumentLeftPane()
		{
			return eleAttachedDocumentLeftPane;
		}
	
	
	
		private WebElement eleAttachedDocument;
		public WebElement  getEleAttachedDocument(String sAttachedDocumentName)
		{
			return eleAttachedDocument = driver.findElement(By.xpath("//div[text()='"+sAttachedDocumentName+"']/../.."));
		}
	
	
	
	
		@FindBy(xpath="//*[contains(text(),'Is Entitlement Performed')]/../..//div[@class='x-unsized x-toggleslider x-slider x-component x-size-monitored x-paint-monitored x-has-width x-widthed x-on']")
		private WebElement eleIsEntitlementPerformed_Switch_On;
		public WebElement geteleIsEntitlementPerformed_Switch_On()
		{
			return eleIsEntitlementPerformed_Switch_On;
		}
	
	
		@FindBy(xpath="//span[text()='Is Entitlement Performed has to be true.']/../..//div[@class='x-body-el']")
		private WebElement eleIsEntitlementPerformedConfirmation;
		public WebElement getEleIsEntitlementPerformedConfirmation()
		{
			return eleIsEntitlementPerformedConfirmation;
		}
	
		@FindBy(xpath="//*[contains(text(),'Line Price Per Unit')][@class = 'x-label-text-el']/../..//input")
		private WebElement eleLinePricePerUnit_Parts_Edit_Input;
		public WebElement getEleLinePricePerUnit_Parts_Edit_Input()
		{
			return eleLinePricePerUnit_Parts_Edit_Input;
		}
	
		@FindBy(xpath="//span[contains(text(),'Line Price is Less than 2000')]/../..//div[@class='x-body-el']")
		private WebElement eleLinePriceLessthanConfirmation;
		public WebElement getEleLinePriceLessthanConfirmation()
		{
			return eleLinePriceLessthanConfirmation;
		}
	
	
		@FindBy(xpath="//*[text()='Auto_TextBox_c']/../..//div[@class='x-innerhtml']/../..//input")
		private WebElement eleAuto_TextBox_c;
		public WebElement geteleAuto_TextBox_c()
		{
			return eleAuto_TextBox_c;
		}
		
		@FindBy(xpath="//*[. = 'Record Type']//input")
		private WebElement eleRecordTypeLst;
		public WebElement getEleeleRecordTypeLst()
		{
			return eleRecordTypeLst;
		}
		
		@FindBy(xpath="//*[. = 'controlling picklist']//input")
		private WebElement eleControllingPicklist;
		public WebElement getEleeleControllingPicklist()
		{
			return eleControllingPicklist;
		}
		
		
		@FindBy(xpath="//*[. = 'dependent picklist']//input")
		private WebElement eleDependentPicklist;
		public WebElement getEleeleDependentPicklist()
		{
			return eleDependentPicklist;
		}
		
		
		@FindBy(xpath="//span[text()='Cancel']")
		private WebElement elecancelbutton;
		public WebElement getelecancelbutton()
		{
			return elecancelbutton;
		}
	
		@FindBy(xpath="//span[text()='Discard Changes']")
		private WebElement eleDiscardChangesbutton;
		public WebElement geteleDiscardChangesbutton()
		{
			return eleDiscardChangesbutton;
		}


		@FindBy(xpath="(//div[contains(text(), 'Parts')][@class='x-panel-title-text']/../../../..//div[@class='x-inner x-container-inner x-layout-auto x-component-inner x-widthed'])[3]")
		private WebElement eletaponfirstpart;
		public WebElement geteletaponfirstpart()
		{
			return eletaponfirstpart;
		}
		
		@FindBy(xpath="//span[text()='Customer Down']/../..//div[@class='x-unsized x-component x-thumb x-size-monitored x-paint-monitored x-draggable x-thumb-toggle-off x-component-toggle-off']")
		private WebElement eleCustomerDownOffRdBtn;
		public WebElement getEleCustomerDownOffRdBtn()
		{
			return eleCustomerDownOffRdBtn;
		}
		
		
		@FindBy(xpath="//div[@class='x-unsized x-toggleslider x-slider x-component x-size-monitored x-paint-monitored x-has-width x-widthed x-on']")
		private WebElement eleAutoChkBxRdBtn;
		public WebElement getEleAutoChkBxRdBtn()
		{
			return eleAutoChkBxRdBtn;
		}
		
		@FindBy(xpath="//span[text()='RS_10552_AutoChkBx']/../..//div[@class='x-unsized x-component x-thumb x-size-monitored x-paint-monitored x-draggable x-thumb-toggle-on x-component-toggle-on']")
		private WebElement eleAutoChkBxOnRdBtn;
		public WebElement getEleAutoChkBxOnRdBtn()
		{
			return eleAutoChkBxOnRdBtn;
		}
		
		@FindBy(xpath="//span[text()='RS_10552_AutoChkBx']/../..//div[@class='x-unsized x-component x-thumb x-size-monitored x-paint-monitored x-draggable x-thumb-toggle-off x-component-toggle-off']")
		private WebElement eleAutoChkBxOFFRdBtn;
		public WebElement getEleAutoChkBxOFFRdBtn()
		{
			return eleAutoChkBxOFFRdBtn;
		}
		@FindBy(xpath="(//*[text()='Order Status']/../..//div[@class='x-input-body-el']/input)[2]")
		private WebElement eleOrderStatusCase2Lst;
		public WebElement getEleOrderStatusCase2Lst()
		{
			return eleOrderStatusCase2Lst;
		}
		
		@FindBy(xpath="//span[text()='Auto_Date1']/../..//div[@class='x-innerhtml']")
		private WebElement eleAutoDate1Txt;
		public WebElement getEleAutoDate1Txt()
		{
			return eleAutoDate1Txt;
		}
		
		
		@FindBy(xpath="//div[@class='x-unsized x-component x-label x-label-svmx-default x-component-svmx-default x-has-width x-widthed x-hidden-display x-hidden sfm-readonly-label']/../..//div[@class='x-innerhtml')")
		private List<WebElement> eleDateTimeLst;
		public List<WebElement> getEleDateTimeLst() {
			return eleDateTimeLst;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
