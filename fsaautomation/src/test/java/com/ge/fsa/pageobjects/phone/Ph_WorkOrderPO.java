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
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Ph_WorkOrderPO {
	public Ph_WorkOrderPO(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator = null;

	@FindBy(xpath = "//*[@*='SFM.HEADER_RIGHT.ACTIONS']")
	private WebElement eleActionsLnk;

	public WebElement getEleActionsLnk() {
		return eleActionsLnk;
	}

	private WebElement eleActionsTxt;

	public WebElement getEleActionsTxt(String sActionsName) {

		switch (BaseLib.sOSName.toLowerCase()) {
		case "android":
			eleActionsTxt = driver.findElement(By.xpath("//*[@text='" + sActionsName + "']"));
			return eleActionsTxt;
		case "ios":
			eleActionsTxt = driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='" + sActionsName + "'])[3]"));
			return eleActionsTxt;

		}
		return eleActionsLnk;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='StartDateTime*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//*[@text='Start Date Time*']"),
			@FindBy(xpath = "//*[@*[contains(.,'Start Date and Time')]]//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			// @FindBy(xpath="//XCUIElementTypeOther[@name='SFM.LAYOUT.EDIT.DATEPICKER.2']"),
			// @FindBy(xpath="//XCUIElementTypeOther[@name='SFM.LAYOUT.EDIT.DATEPICKER.1']"),
			@FindBy(xpath = "//*[@*[contains(.,'StartDateTime')]]/following-sibling::*/XCUIElementTypeStaticText"),
			@FindBy(xpath = "//*[@*[contains(.,'Start Date Time')]]/following-sibling::*/XCUIElementTypeStaticText"),
			@FindBy(xpath = "//*[@*[contains(.,'Start Date and Time')]]/following-sibling::*/XCUIElementTypeStaticText") })
	private WebElement eleStartDateTimeTxtFld;

	public WebElement getEleStartDateTimeTxtFld() {
		return eleStartDateTimeTxtFld;
	}

	@FindAll({ @FindBy(xpath = "//*[@text='EndDateTime*']"), @FindBy(xpath = "//*[@text='End Date Time*']"),
			@FindBy(xpath = "//*[@*[contains(.,'End Date and Time')]]//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@name='SFM.LAYOUT.EDIT.DATEPICKER.3']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@name='SFM.LAYOUT.EDIT.DATEPICKER.2']"),
			@FindBy(xpath = "//*[@*='End Date and Time']/following-sibling::*/XCUIElementTypeStaticText"),
			@FindBy(xpath = "//*[@*[contains(.,'End Date and Time')]]/following-sibling::*/XCUIElementTypeStaticText"),
			@FindBy(xpath = "//*[@*[contains(.,'EndDateTime')]]/following-sibling::*/XCUIElementTypeStaticText"),
			@FindBy(xpath = "//*[@*[contains(.,'End Date Time')]]/following-sibling::*/XCUIElementTypeStaticText") })
	private WebElement eleEndDateTimeTxtFld;

	public WebElement getEleEndDateTimeTxtFld() {
		return eleEndDateTimeTxtFld;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Subject']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//*[@text='Subject*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@name='Subject*']") })
	private WebElement eleSubjectTxtFld;

	public WebElement getEleSubjectTxtFld() {
		return eleSubjectTxtFld;
	}

	@FindAll({ @FindBy(xpath = "//*[@text='Save']"), @FindBy(xpath = "(//XCUIElementTypeOther[@label=\"Save\"])") })
	private WebElement eleSaveLnk;

	public WebElement getEleSaveLnk() {
		return eleSaveLnk;
	}

	@FindAll({ @FindBy(xpath = "//*[@*='ADD SELECTED (1)']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@label=\"ADD SELECTED (1)\"]") })
	private WebElement eleAddSelected;

	public WebElement getEleAddSelected() {
		return eleAddSelected;
	}

	@FindAll({ @FindBy(xpath = "//*[@*='Finalize']"),
			@FindBy(xpath = "(//XCUIElementTypeOther[@label=\"Finalize\"])[3]") })
	private WebElement eleFinalize;

	public WebElement getEleFinalize() {
		return eleFinalize;
	}

	@FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Create New\"])[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[15]")
	private WebElement eleselectprocesscreateevent;

	public WebElement getEleselectprocesscreateevent() {
		return eleselectprocesscreateevent;
	}

	private WebElement eleselectprocess;

	public WebElement getEleselectprocess(String sProcessName) {
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""
							+ sProcessName + "\"))"));
		} else {
			return driver.findElement(By.xpath("//*[@label='" + sProcessName + "']"));
		}

	}

	public void selectAction(CommonUtility commonUtility, String sActionsName) throws InterruptedException {
		System.out.println("Selecting Action");
		commonUtility.waitforElement(getEleActionsLnk(), 3);
		getEleActionsLnk().click();
		Thread.sleep(3000);
		commonUtility.custScrollToElementAndClick(getEleselectprocess(sActionsName));
		// getEleActionsTxt(sActionsName).click();
	}

	public void createNewEvent(CommonUtility commonUtility, String sSubject, Ph_CalendarPO ip_CalendarPo)
			throws InterruptedException {
		System.out.println("Creating New Event");
		int hrs = 0;
		try {
			hrs = Integer.parseInt(commonUtility.gethrsfromdevicetime());
		} catch (Exception e) {

		}
		selectAction(commonUtility, "Create New Event From Work Order");
		commonUtility.setDateTime24hrs(getEleStartDateTimeTxtFld(), 0, Integer.toString(hrs), "00");
		commonUtility.setDateTime24hrs(getEleEndDateTimeTxtFld(), 0, Integer.toString(hrs + 2), "00");
		getEleSubjectTxtFld().sendKeys(sSubject);
		Thread.sleep(2000);
		getEleSaveLnk().click();
		Thread.sleep(5000);

	}

	@FindAll({ @FindBy(xpath = "//*[@text='Add Parts']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@label='Add Parts']") })
	private WebElement elePartLnk;

	public WebElement getElePartLnk() {
		return elePartLnk;
	}

	public String getStringParts() {
		return "PARTS";
	}

	@FindAll({ @FindBy(xpath = "//*[@text ='Add Labor']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@label='Add Labor']") })
	private WebElement eleLaborLnk;

	public WebElement getEleLaborLnk() {
		return eleLaborLnk;
	}

	public String getStringLabor() {
		return "LABOR";
	}

	@FindAll({ @FindBy(xpath = "//*[@text ='Add Travel']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@label='Add Travel']") })
	private WebElement eleTravelLnk;

	public WebElement getEleTravelLnk() {
		return eleTravelLnk;
	}

	public String getStringTravel() {
		return "TRAVEL";
	}

	@FindAll({ @FindBy(xpath = "//*[@text ='Add Expenses']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@label='Add Expenses']") })
	private WebElement eleExpensesLnk;

	public WebElement getEleExpensesLnk() {
		return eleExpensesLnk;
	}

	public String getStringExpenses() {
		return "EXPENSES";
	}

	@FindAll({ @FindBy(xpath = "//*[@text ='Add Image or Video']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@label='Add Image or Video']") })
	private WebElement eleAddImageLnk;

	public WebElement getEleAddImageLnk() {
		return eleAddImageLnk;
	}

	public String getStringAttachments() {
		return "ATTACHMENTS";
	}

	public String getStringDocuments() {
		return "DOCUMENTS";
	}

	public WebElement getEleSearchListItem(String sValue) {
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[contains(@text,'" + sValue
					+ "')]/../..//*[contains(@content-desc,'I')]/*[contains(@text,'" + sValue + "')]"));
		} else {
			// return
			// driver.findElement(By.xpath("//*[contains(@label,'"+sValue+"')]/*[contains(@name,'ITEM')]"));
			return driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + sValue + "']"));
		}

	}

	@FindBy(xpath = "(//*[@*='Part Lookup'])[last()]")
	private WebElement elepartlookup;

	public WebElement getElepartlookup() {
		return elepartlookup;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Activity Type']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@label=\"Activity Type\"]/../XCUIElementTypeOther") })
	private WebElement eleActivityType;

	public WebElement getEleActivityType() {
		return eleActivityType;
	}

	@FindAll({ @FindBy(xpath = "//*[@text='Calibration']"),
			@FindBy(xpath = "(//XCUIElementTypeOther[@label=\"Calibration\"])") })
	private WebElement eleCalibration;

	public WebElement getEleCalibration() {
		return eleCalibration;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Line Qty']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@label='Line Qty']/../XCUIElementTypeOther") })
	private WebElement eleLineQtyTxtFld;

	public WebElement getEleLineQtyTxtFld() {
		return eleLineQtyTxtFld;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Line Price Per Unit']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@label='Line Price Per Unit']/../XCUIElementTypeOther") })
	private WebElement eleLinePerUnitTxtFld;

	public WebElement getEleLinePerUnitTxtFld() {
		return eleLinePerUnitTxtFld;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Discount %']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@label=\"Discount %\"]/../XCUIElementTypeOther") })
	private WebElement eleDiscountPercentage;

	public WebElement getEleDiscountPercentage() {
		return eleDiscountPercentage;
	}

	
	@AndroidFindBy(xpath = "//*[@text='No Of Times Assigned']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='No Of Times Assigned']/..//XCUIElementTypeTextField")
	private WebElement NoOfTimesAssigned;

	public WebElement GetEleNoOfTimesAssigned_Edit_Input() {
		return NoOfTimesAssigned;
	}

	private WebElement eleDvrText;

	public WebElement getDvrText(String sIssueTxt) {
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleDvrText = driver.findElement(
					By.xpath("//*[@*[contains(.,'SFM.VALIDATION.LIST.ROW')]]//*[@*[contains(.,'" + sIssueTxt + "')]]"));
		} else {
			eleDvrText = driver.findElement(By.xpath("//*[@*[contains(.,'" + sIssueTxt + "')]]"));
		}

		return eleDvrText;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='This record does not meet the qualification criteria for this SFM Transaction']"),
			@FindBy(xpath = "//*[@label='This record does not meet the qualification criteria for this SFM Transaction']") })
	private WebElement eleThisRecordDoesNotPopup;

	public WebElement getEleThisRecordDoesNotPopup() {

		return eleThisRecordDoesNotPopup;
	}

	@FindAll({ @FindBy(xpath = "//*[@*[contains(.,'SFM.VALIDATION.LIST.TOGGLE_BUTTON')]]"),
			@FindBy(xpath = "//*[@*='SFM.VALIDATION.LIST.TOGGLE_BUTTON']") })

	private WebElement eleIssueFoundTxt;

	public WebElement getEleIssueFoundTxt() {
		return eleIssueFoundTxt;
	}

	private WebElement eleIssuePopupTxt;

	public WebElement getEleIssuePopupTxt(String sIssueTxt) {
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleIssuePopupTxt = driver.findElement(By.xpath(
					"//*[@*[contains(.,'SFM.VALIDATION.LIST.ANCHOR_BUTTON')]]/*[@*[contains(.,'" + sIssueTxt + "')]]"));
		} else {
			eleIssuePopupTxt = driver
					.findElement(By.xpath("//*[@*[contains(.,'//*[@*[contains(.,'" + sIssueTxt + "')]]"));
		}

		return eleIssuePopupTxt;
	}

	private WebElement elelaborpartresult;

	public WebElement getElelaborpartresult(String sProductName1) {
		switch (BaseLib.sOSName.toLowerCase()) {

		case "android":
			elelaborpartresult = driver
					.findElement(By.xpath("//*[@*='" + sProductName1 + "'][@class='android.widget.TextView']"));
			return elelaborpartresult;
		case "ios":
			return elelaborpartresult = driver.findElement(By.xpath(
					"(//XCUIElementTypeOther[@label=\" RESULTS\"])[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther"));
		}
		return elelaborpartresult;

	}

	@FindAll({ @FindBy(xpath = "//*[@*='Save']"), @FindBy(xpath = "(//XCUIElementTypeOther[@label=\"Save\"])[last()]") })
	private WebElement elesave;

	public WebElement getElesave() {
		return elesave;
	}

	// Added by Harish--------------
	@FindBy(xpath = "//*[@*='Contact']") //Works on both
	private WebElement lblContact;

	public WebElement getLblContact() {
		return lblContact;
	}

	@FindBy(xpath = "//*[@*='Account']")
	private WebElement lblAccount;

	public WebElement getLblAccount() {
		return lblAccount;
	}

	@FindBy(xpath = "//*[@*='Site']")
	private WebElement lblSite;

	public WebElement getLblSite() {
		return lblSite;
	}

	@FindBy(xpath = "//*[@*='To Location']")
	private WebElement lblToLocation;

	public WebElement getLblToLocation() {
		return lblToLocation;
	}

	@FindBy(xpath = "//*[@*='Component']")
	private WebElement lblComponent;

	public WebElement getLblComponent() {
		return lblComponent;
	}
	
	@FindBy(xpath = "//*[@*='Product']")
	private WebElement lblProduct;

	public WebElement getLblProduct() {
		return lblProduct;
	}

	@AndroidFindBy(xpath = "//*[@*='APP.BACK_BUTTON']")
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name='APP.BACK_BUTTON'])[last()]")
	private WebElement btnClose;

	public WebElement getBtnClose() {
		return btnClose;
	}
	
//	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeOther[@name='APP.BACK_BUTTON'])[last()]")
//	private WebElement btnContactClose;
//
//	public WebElement getIOSBtnContactClose() {
//		return btnContactClose;
//	}

	@FindBy(xpath = "//*[@*[contains(.,'Account ID')]]") //Works on both
	private List<WebElement> noAccContactsLst;

	public List<WebElement> getNoAccContactsLst() {
		return noAccContactsLst;
	}

	public List<WebElement> getContactLst(String sValue) {
			return driver.findElements(By.xpath("//*[@*='"+sValue+"']"));
	}	
	
	public WebElement getLkpEle(String sValue) {
		return driver.findElement(By.xpath("//*[@*='"+sValue+"']"));
}	
	

	public WebElement getEle(String sValue) {
		return driver.findElement(By.xpath("//*[@*='" + sValue + "']"));
	}

	@AndroidFindBy(xpath = "//*[@*='SFM.LAYOUT.LOOKUP.LIST']//android.widget.TextView[contains(@text,'RESULT')]")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@name,'RESULT')]")
	private WebElement lblResults;

	public WebElement getLblResults() {
		return lblResults;
	}

	@AndroidFindBy(xpath = "//*[@*[contains(.,'Limit search to Account')]]/following-sibling::android.view.ViewGroup")
	@iOSXCUITFindBy(xpath="//XCUIElementTypeOther[@name='CLEAR ALL']")
	private WebElement btnclrFilter;

	public WebElement getBtnclrFilter() {
		return btnclrFilter;
	}

//	@FindBy(xpath = "//*[@*='SFM.LAYOUT.EDIT.PICKLIST.3']")
	@AndroidFindBy(xpath = "(//*[@text='Country']/following::android.widget.TextView)[1]")
	@iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name='Country']/following::XCUIElementTypeOther)[1]")
	private WebElement countryPicklst;

	public WebElement getCountryPicklst() {
		return countryPicklst;
	}

//	@FindBy(xpath = "//*[@*='SFM.LAYOUT.EDIT.PICKLIST.11']")
	@FindBy(xpath = "(//*[@text='Requested Country']/following::android.widget.TextView)[1]")
	private WebElement requestedCountryPicklst;

	public WebElement getRequestedCountryPicklst() {
		return requestedCountryPicklst;
	}

	@AndroidFindBy(xpath = "//*[@text='PARTS']")
	@iOSXCUITFindBy(xpath = "//*[@*='PARTS']")
	private WebElement tabParts;

	public WebElement getTabParts() {
		return tabParts;
	}

	@AndroidFindBy(xpath = "//*[@*='Product']/following-sibling::android.view.ViewGroup/android.widget.TextView")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Product']/following-sibling::XCUIElementTypeOther")
	private WebElement txtProduct;

	public WebElement getTxtProduct() {
		return txtProduct;
	}

	@AndroidFindBy(xpath = "//*[@*='Contact']/following-sibling::android.view.ViewGroup/android.widget.TextView")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Contact']/following-sibling::XCUIElementTypeOther")
	private WebElement txtContact;

	public WebElement getTxtContact() {
		return txtContact;
	}

	@AndroidFindBy(xpath = "//*[@*='Country']/following-sibling::android.view.ViewGroup/android.widget.TextView")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Country']/following-sibling::XCUIElementTypeOther")
	private WebElement txtCountry;

	public WebElement getTxtCountry() {
		return txtCountry;
	}

	@AndroidFindBy(xpath = "//*[@*='Site']/following-sibling::android.view.ViewGroup/android.widget.TextView")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Site']/following-sibling::XCUIElementTypeOther")
	private WebElement txtSite;

	public WebElement getTxtSite() {
		return txtSite;
	}

	@AndroidFindBy(xpath = "//*[@*='Top-Level']/following-sibling::android.view.ViewGroup/android.widget.TextView")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Top-Level']/following-sibling::XCUIElementTypeOther")
	private WebElement txtTopLevel;

	public WebElement getTxtTopLevel() {
		return txtTopLevel;
	}

	@AndroidFindBy(xpath = "//*[@*='City']/following::android.widget.EditText")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='City']/following-sibling::XCUIElementTypeOther//XCUIElementTypeTextField")
	private WebElement txtCity;

	public WebElement getTxtCity() {
		return txtCity;
	}

	@AndroidFindBy(xpath = "//*[@*='Zip']/following::android.widget.EditText")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Zip']/following-sibling::XCUIElementTypeOther//XCUIElementTypeTextField")
	private WebElement txtZip;

	public WebElement getTxtZip() {
		return txtZip;
	}

	@FindBy(xpath = "(//*[@*='Save'])[last()]")
	private WebElement btnSave;

	public WebElement getBtnSave() {
		return btnSave;
	}
	
//	@iOSXCUITFindBy(xpath="(//XCUIElementTypeStaticText[@name='Description']/..//XCUIElementTypeStaticText)[last()]")
	@AndroidFindBy(xpath="//*[@*='Problem Description']/following::android.widget.EditText")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTextView")
	private WebElement txtProblemDescription;
	
	public WebElement getTxtProblemDescription() {
		return txtProblemDescription;
	}
	
	@AndroidFindBy(xpath="//android.widget.TextView[@text='Filter']")
	@iOSXCUITFindBy(xpath="	//XCUIElementTypeOther[@name='Filter']")
	private WebElement lnkFilter;
	
	public WebElement getLnkFilter() {
		return lnkFilter;
	}
	
	@FindBy(xpath="//android.widget.TextView[contains(@content-desc,'checkbox')]")
	private WebElement chkboxFilter;
	
	public WebElement getChkboxFilter() {
		return chkboxFilter;
	}
	

	@FindBy(xpath="//android.widget.TextView[@text='See results']")
	private WebElement btnSeeResults;
	
	public WebElement getBtnSeeResults() {
		return btnSeeResults;
	}
	//-----------------------------


	public void addParts(CommonUtility commonUtility, String sProductName1) {
		System.out.println("Adding Parts");
		commonUtility.gotToTabHorizontal(getStringParts());
		selectFromlookupSearchList(commonUtility, getElePartLnk(), sProductName1);
		getEleAddSelected().click();
	}

	public void addLabor(CommonUtility commonUtility, String sProductName1) {
		System.out.println("Adding Labor");
		commonUtility.custScrollToElementAndClick(getEleLaborLnk(), getStringLabor());
		selectFromlookupSearchList(commonUtility, getElepartlookup(), sProductName1);
		selectFromPickList(commonUtility, getEleActivityType(), "Calibration");

		try {
			commonUtility.setDateTime24hrs(getEleStartDateTimeTxtFld(), 0, "0", "0");
			commonUtility.setDateTime24hrs(getEleEndDateTimeTxtFld(), 1, "09", "00");

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

	@FindAll({
			@FindBy(xpath = "//*[@text='Account']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//*[@text='Account']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Account']/../XCUIElementTypeOther") })
	private WebElement eleAccount;

	public WebElement getEleAccount() {
		return eleAccount;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Scheduled Date Time']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Scheduled Date Time']/../XCUIElementTypeOther") })
	private WebElement eleScheduledDateTime;

	public WebElement getEleScheduledDateTime() {
		return eleScheduledDateTime;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Scheduled Date']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Scheduled Date']/../XCUIElementTypeOther") })
	private WebElement eleScheduledDate;

	public WebElement getEleScheduledDate() {
		return eleScheduledDate;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Auto_Date1']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Auto_Date1']/../XCUIElementTypeOther") })
	private WebElement eleAutoDate1_Edit_Input;

	public WebElement getEleAutoDate1_Edit_Input() {
		return eleAutoDate1_Edit_Input;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Auto_Date2']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Auto_Date2']/../XCUIElementTypeOther") })
	private WebElement eleAutoDate2_Edit_Input;

	public WebElement getEleAutoDate2_Edit_Input() {
		return eleAutoDate2_Edit_Input;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Scheduled Date Time']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Scheduled Date Time']/../XCUIElementTypeOther") })
	private WebElement eleScheduledDateTimeTxt;

	public WebElement getEleScheduledDateTimeTxt() {
		return eleScheduledDateTimeTxt;
	}

	/*
	 * @FindBy(
	 * xpath="//*[text()='Scheduled Date']/../..//div[@class='x-input-body-el']/input"
	 * ) private WebElement eleScheduledDate; public WebElement
	 * getEleScheduledDate() { return eleScheduledDate; }
	 */

	@FindAll({
			@FindBy(xpath = "//*[@text='Component']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Component']/../XCUIElementTypeOther") })

	private WebElement eleComponent;

	public WebElement getEleComponent() {
		return eleComponent;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Installed Product ID*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Installed Product ID*']/../XCUIElementTypeOther") })

	private WebElement eleInstalledProduct;

	public WebElement getEleInstalledProduct() {
		return eleInstalledProduct;
	}

	@FindAll({
			@FindBy(xpath = "(//*[@text='Parts (1)']//..//following-sibling::*[@class='android.view.ViewGroup'])[1]"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Parts (1)']/../XCUIElementTypeOther") })
	private WebElement eletabonpart;

	public WebElement getEletabonpart() {
		return eletabonpart;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Part']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),@FindBy(xpath = "//*[@text='Part*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Part']/../XCUIElementTypeOther"),@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Part*']/../XCUIElementTypeOther") })
	private WebElement elePart;

	public WebElement getPart() {
		return elePart;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Date Required']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Date Required']/../XCUIElementTypeOther") })
	private WebElement eleDateRequired;

	public WebElement getleDateRequired() {
		return eleDateRequired;
	}

	@FindBy(xpath = "//*[@*='APP.BACK_BUTTON']")
	private WebElement eleXsymbol;

	public WebElement geteleXsymbol() {
		return eleXsymbol;
	}

	@FindAll({ @FindBy(xpath = "//XCUIElementTypeStaticText[@name='Product*']/../XCUIElementTypeOther"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Product']/../XCUIElementTypeOther"),
			@FindBy(xpath = "//*[@text='Product*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//*[@text='Product']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']") })
	private WebElement eleProduct;

	public WebElement getEleProduct() {
		return eleProduct;
	}

	@FindAll({ @FindBy(xpath = "//XCUIElementTypeStaticText[@name='Installed Product ID*']/../XCUIElementTypeOther"),
			@FindBy(xpath = "//*[@text='Installed Product ID*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']") })
	private WebElement eleInstalledProductstar;

	public WebElement getEleInstalledProductstar() {
		return eleInstalledProductstar;
	}

	@FindAll({ @FindBy(xpath = "//XCUIElementTypeStaticText[@name='Account*']/../XCUIElementTypeOther"),
			@FindBy(xpath = "//*[@text='Account*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")})
	private WebElement eleAccountstar;

	public WebElement getAccountstar() {
		return eleAccountstar;
	}

	@FindAll({ @FindBy(xpath = "//XCUIElementTypeStaticText[@name='To Location']/../XCUIElementTypeOther"),
			@FindBy(xpath = "//*[@text='To Location']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']") })
	private WebElement eleToLocation;

	public WebElement getEleToLocation() {
		return eleToLocation;
	}

	public void createInstalledProduct(CommonUtility commonUtility, Ph_CalendarPO ph_CalendarPo, String accountName,
			String ProdutName, String InstalledProductID, Ph_ExploreSearchPO ph_ExploreSearchPO) throws Exception {
		ph_CalendarPo.getEleCalendarBtn().click();
		// click on new icon
		ph_CalendarPo.getEleCreateNewBtn().click();
		Thread.sleep(2000);
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			ph_CalendarPo.getEleSelectProcessNewProcess("Create New Installed Product Automation sahi").click();
		} else {
			ph_CalendarPo.getEleSelectProcessNewProcess("Create New Installed Product Automation sahi no description")
					.click();
		}
		Thread.sleep(2000);

		// Adding Value for Product
		ph_ExploreSearchPO.selectFromLookupSearchList(commonUtility, getEleProduct(), ProdutName);

		// Adding Value for InstalledproductID
		getEleInstalledProductstar().click();
		getEleInstalledProductstar().sendKeys(InstalledProductID);

		// Adding Value for Account
		commonUtility.tap(getAccountstar());
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			ph_ExploreSearchPO.getEleExploreSearchTxtFld().sendKeys(accountName + "\\n");
		} else {
			ph_ExploreSearchPO.getEleExploreSearchTxtFld().sendKeys(accountName + "\n");
		}

		Thread.sleep(1000);
		getEleSearchListItem(accountName).click();
		ph_ExploreSearchPO.selectFromLookupSearchList(commonUtility, getAccountstar(), accountName);

		Thread.sleep(1000);

		getEleAdd().click();
	}

	
	
	@FindAll({
		@FindBy(xpath = "//*[@text='Contact']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
		@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Contact']/../XCUIElementTypeOther") })

private WebElement eleContact;

public WebElement getEleContact() {
	return eleContact;
}

	
@FindAll({
	@FindBy(xpath = "//*[@text='Customer Down']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Customer Down']/../XCUIElementTypeOther") })

private WebElement eleCustomerDown;

public WebElement getEleCustomerDown() {
return eleCustomerDown;
}
	
	
	@FindAll({
			@FindBy(xpath = "//*[@text='Site']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Site Lookup']/../XCUIElementTypeOther") })

	private WebElement eleSite;

	public WebElement getEleSite() {
		return eleSite;
	}

	@FindAll({ @FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Product Lookup']"),
			@FindBy(xpath = "//*[@label='Product Product Lookup']") })
	public WebElement productLookup;

	public WebElement getProductLookup() {
		return productLookup;
	}

	public WebElement getChildLineAddItem(String value) {
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[./*[@class='android.widget.TextView'][@text='" + value
					+ "']][@class='android.view.ViewGroup']"));
		} else {
			return driver.findElement(By.xpath("//*[@*='" + value + "']"));
		}
	}

	public WebElement getChildLineAddedItem(String value) {
		// if(BaseLib.sOSName.equalsIgnoreCase("android")) {

		return driver.findElement(By.xpath("(//*[.//*[@*[contains(.,'" + value + "')]]])[last()]"));
		// }else {
		// return driver.findElement(By.xpath("//*[contains(@name,'"+value+"')]"));
		//
		// }
	}

	@FindAll({ @FindBy(xpath = "//*[@class='android.widget.TextView'][contains(@text,'ADD SELECTED')]"),
			@FindBy(xpath = "(//*[@*[contains(.,'ADD SELECTED')]])[last()]") })
	private WebElement eleAddSelectedButton;

	public WebElement getEleAddSelectedButton() {
		return eleAddSelectedButton;
	}

	@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='IB Serial Number Lookup']")
	private WebElement eleIBSerialNumber;

	public WebElement getEleIBSerialNumber() {
		return eleIBSerialNumber;
	}

	@FindAll({
			@FindBy(xpath = "//*[@class='android.widget.ScrollView']//*[@class='android.widget.TextView'][contains(@text,'IB')]") })
	public List<WebElement> IBLookup;

	public List<WebElement> getIBLookup() {
		return IBLookup;
	}

	@FindBy(xpath = "(//*[@*='Add'])[last()]")
	private WebElement eleAddButton;

	public WebElement getEleAddButton() {
		return eleAddButton;
	}

	@FindAll({ @FindBy(xpath = "//*[./*[@text='Remove']]"), @FindBy(xpath = "//*[@name='Remove']/*") })
	private WebElement eleRemoveButton;

	public WebElement getEleRemoveButton() {
		return eleRemoveButton;
	}

	@FindAll({ @FindBy(xpath = "//*[./*[@text='Discard Changes']]"), @FindBy(xpath = "//*[@*='Discard Changes']") })
	private WebElement eleDiscardChangesButton;

	public WebElement getEleDiscardChangesButton() {
		return eleDiscardChangesButton;
	}

	@FindBy(xpath = "(//*[@*='APP.BACK_BUTTON'])[last()]")
	private WebElement eleBackButton;

	public WebElement getEleBackButton() {
		return eleBackButton;
	}

	public WebElement getEleChildLineItem(String childLine) {
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath(
					"(//*[.//*[contains(@text,'" + childLine + "')]][@class='android.widget.ScrollView'])[last()]"));
		} else {
			return driver.findElement(By.xpath("//*[./*[@*='" + childLine + "']]"));
		}
	}

	@FindAll({ @FindBy(xpath = "(//*[@*[contains(.,'Expense Type')]])[last()]/following-sibling::*[1]"),
			@FindBy(xpath = "//*[@*='Expense Type']/following-sibling::*") })
	private WebElement eleExpenseTypeField;

	public WebElement getEleExpenseTypeField() {
		return eleExpenseTypeField;
	}

	public WebElement getEleDropDownValue(String value) {
		return driver.findElement(By.xpath("//*[@*='" + value + "']"));
	}

	@FindAll({
			@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Line Qty']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Line Qty*']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//*[@*='Line Qty']/following-sibling::*"),
			@FindBy(xpath = "//*[@*='Line Qty*']/following-sibling::*") })
	private WebElement eleLineQtyField;

	public WebElement getEleLineQtyField() {
		return eleLineQtyField;
	}

	@FindAll({
			@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Line Price Per Unit']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//*[@*='Line Price Per Unit']/following-sibling::*") })
	private WebElement eleLinePriceField;

	public WebElement getEleLinePriceField() {
		return eleLinePriceField;
	}

	@FindBy(xpath = "(//*[@*[contains(.,'SFM.LAYOUT.CHILDLINELIST.LISTITEM')]]//*)[last()]") // *[./*[@text='More']][@class='android.view.ViewGroup']
	private WebElement eleMore;

	public WebElement getEleMore() {
		return eleMore;
	}

	@FindAll({
			@FindBy(xpath = "//*[./*[@text='Manage Work Details for Products Serviced']][@class='android.view.ViewGroup']") })
	private WebElement eleManageWorkDetails;

	public WebElement getEleManageWorkDetails() {
		return eleManageWorkDetails;
	}

	public WebElement getEleeleIBId(String sProductName) {
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='" + sProductName + "']"));
		} else {
			return driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + sProductName + "']"));
		}
	}

	public void addParts(CommonUtility commonUtility, String[] productNames) throws InterruptedException {
		commonUtility.custScrollToElementAndClick(getElePartLnk(), getStringParts());
		for (String productName : productNames) {
			getElelookupsearch().click();
			if (BaseLib.sOSName.equalsIgnoreCase("android")) {
				getElelookupsearch().sendKeys(productName + "\\n");
			} else {
				getElelookupsearch().sendKeys(productName + "\n");
			}
			getEleSearchListItem(productName).click();
			getElelookupsearch().clear();
		}
		getEleAddSelectedButton().click();
	}

	public void addPartsManageWD(CommonUtility commonUtility, Ph_ExploreSearchPO ph_ExploreSearchPo, String sPartName1)
			throws InterruptedException {
		commonUtility.custScrollToElementAndClick(getElePartLnk(), getStringParts());
		selectFromlookupSearchList(commonUtility, getElepartlookup(), sPartName1);

		// commonUtility.tap(workOrderPo.getEleAddselectedbutton());
		// Thread.sleep(1000);
		// ph_ExploreSearchPo.getEleSearchListItem(sPartName1).click();

	}

	public void downloadCriteriaDOD(CommonUtility commonUtility, Ph_ExploreSearchPO exploreSearchPO,
			String sExploreSearch, String sExploreChildSearchTxt, String sWoName) throws InterruptedException {
		exploreSearchPO.geteleExploreIcn().click();
		;
		// exploreSearchPO.getEleSearchNameTxt(sExploreSearch).click();
		exploreSearchPO.getEleSearchListItem(sExploreSearch).click();
		Thread.sleep(3000);
		exploreSearchPO.getEleSearchChildListName(sExploreChildSearchTxt).click();
		;
		// exploreSearchPO.getEleExploreSearchTxtFld().click();
		exploreSearchPO.getEleExploreSearchTxtFld().clear();
		exploreSearchPO.getEleExploreSearchTxtFld().sendKeys(sWoName);

	}

	public void addExpense(CommonUtility commonUtility, String sExpenseType, String sLineQty, String slinepriceperunit)
			throws InterruptedException {
		// commonUtility.custScrollToElementAndClick("Add Expense").click();
		// commonUtility.custScrollToElementAndClick(getChildLineAddItem("Add
		// Expense"));
		commonUtility.custScrollToElementAndClick(getEleExpensesLnk(), getStringExpenses());
		selectFromPickList(commonUtility, getEleExpenseTypeField(), sExpenseType);
		// getEleExpenseTypeField().click();
		// getEleDropDownValue(sExpenseType).click();;
		getEleLineQtyField().sendKeys(sLineQty);
		getEleLinePriceField().sendKeys(slinepriceperunit);
		getEleAddButton().click();

	}

	public void navigatetoWO(CommonUtility commonUtility, Ph_ExploreSearchPO ph_ExploreSearchPo, String sExploreSearch,
			String sExploreChildSearchTxt, String sWOName) throws InterruptedException {
		ph_ExploreSearchPo.geteleExploreIcn().click();
		Thread.sleep(GenericLib.iMedSleep);
		// exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		commonUtility.custScrollToElementAndClick(ph_ExploreSearchPo.getEleSearchListItem(sExploreSearch));
		Thread.sleep(GenericLib.iMedSleep);
		// commonUtility.custScrollToElementAndClick(ph_ExploreSearchPo.getEleSearchListItem(sExploreChildSearchTxt));

		// Select the Work Order
		ph_ExploreSearchPo.selectFromLookupSearchList(commonUtility,
				ph_ExploreSearchPo.getEleSearchListItem(sExploreChildSearchTxt), sWOName);
	}

	public void addPSLines(CommonUtility commonUtility, String sSerialNumber) throws InterruptedException {
		commonUtility.gotToTabHorizontal("PRODUCTS SERVICED");
		selectFromlookupSearchList(commonUtility, getChildLineAddItem("Add Products Serviced"), sSerialNumber);
		getEleAddSelectedButton().click();

	}

	@FindBy(xpath = "//android.widget.ScrollView[@content-desc=\"SFM.LAYOUT.CHIILDLINE_LIST.ALLCHILDLINES\"]/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup")
	private WebElement eleOntoppart;

	public WebElement getEleOntoppart() {
		return eleOntoppart;
	}

	@FindBy(xpath = "(//android.widget.ScrollView[@content-desc=\"SFM.LAYOUT.CHIILDLINE_LIST.ALLCHILDLINES\"])[2]/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[1]/android.view.ViewGroup")
	private WebElement eleOntoplabor;

	public WebElement getEleOntoplabor() {
		return eleOntoplabor;
	}

	@FindBy(xpath = "//*[@text='DiscardChanges']")
	private WebElement eleDiscardChanges;

	public WebElement getEleDiscardChanges() {
		return eleDiscardChanges;
	}

	@FindBy(xpath = "//*[@*='Record Type ID']/following-sibling::*[1]")
	private WebElement eleRecordTypeID;

	public WebElement getEleRecordTypeID() {
		return eleRecordTypeID;
	}

	@FindBy(xpath = "//*[@*='controlling picklist']/following-sibling::*[1]")
	private WebElement eleControllingPicklist;

	public WebElement getEleControllingPicklist() {
		return eleControllingPicklist;
	}

	@FindBy(xpath = "//*[@*='dependent picklist']/following-sibling::*[1]")
	private WebElement eleDependentPicklist;

	public WebElement getEleDependentPicklist() {
		return eleDependentPicklist;
	}

	private WebElement elelookupsearhproduct;

	public WebElement getElelookupsearch() {
		return elelookupsearhproduct = driver.findElementByAccessibilityId("SFM_LAYOUT.LOOKUP.SEARCH_BAR");

	}

	@FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Contact Contact Lookup\"])[2]")
	private WebElement eleContactLookUp;

	public WebElement getEleContactLookUp() {
		return eleContactLookUp;
	}

	private WebElement eleContactlookupsearch;

	public WebElement geteleContactlookupsearch() {
		return eleContactlookupsearch = driver
				.findElementByAccessibilityId("Search Full Name, Business Phone, Mobile Phone, Email");
	}

	@FindBy(xpath = "(//XCUIElementTypeOther[@name=\"Product Product Lookup\"])[2]")
	private WebElement eleProductLookUp;

	public WebElement getEleProductLookUp() {
		return eleProductLookUp;
	}

	private WebElement eleProductlookupsearch;

	public WebElement geteleProductlookupsearch() {
		return eleProductlookupsearch = driver
				.findElementByAccessibilityId("Search Product Name, Product Code, Product Family, Product Line");
	}

	@FindAll({ @FindBy(xpath = "//*[@text='Low']"), @FindBy(xpath = "//*[@label='Low']") })
	private WebElement eleCreatenewpriorityLow;

	public WebElement getEleCreatenewpriorityLow() {
		return eleCreatenewpriorityLow;
	}

	@FindAll({ @FindBy(xpath = "//*[@*='SFM.HEADER_RIGHT']"), @FindBy(xpath = "//*[@content-desc='SFM.HEADER_RIGHT']"),
			@FindBy(xpath = "//*[@text='Add']") })
	private WebElement eleAdd;

	public WebElement getEleAdd() {
		return eleAdd;

		/*
		 * if(BaseLib.sOSName.equalsIgnoreCase("android")) { return eleAdd =
		 * driver.findElementByAccessibilityId("Add"); }else { return eleAdd
		 * =driver.findElement(By.xpath("//*[@text='Add']")); }
		 */
	}

	public void selectFromlookupSearchList(CommonUtility commonUtility, WebElement eleToSetValue, String sValue) {
		System.out.println("Select From Lookup List");
		commonUtility.custScrollToElementAndClick(eleToSetValue);
		getElelookupsearch().click();
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			getElelookupsearch().sendKeys(sValue + "\\n");
		} else {
			getElelookupsearch().sendKeys(sValue + "\n");
		}
		getEleSearchListItem(sValue).click();

	}

	public void selectFromPickList(CommonUtility commonUtility, WebElement eleToSetValue, String sValue) {
		System.out.println("Select From Picklist");
		commonUtility.custScrollToElementAndClick(eleToSetValue);
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			try {
				driver.findElement(By.xpath("//*[@text='" + sValue + "']")).click();
			} catch (Exception e) {
				driver.findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""
								+ sValue + "\"))"))
						.click();
			}
		} else {
			driver.findElement(By.xpath("//*[@label='" + sValue + "']")).click();
		}
	}

	@FindAll({ @FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"SFM.LAYOUT.ADD.0\"]/android.view.ViewGroup"),
			@FindBy(xpath = "//*[@name='SFM.LAYOUT.ADD']") })
	private WebElement eleclickonaddparts;

	public WebElement getEleclickonaddparts() {
		return eleclickonaddparts;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Order Status']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//*[@text='Order Status*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Order Status']/../XCUIElementTypeOther"),
			@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Order Status*']/../XCUIElementTypeOther") })
	private WebElement eleOrderStatus;

	public WebElement geteleOrderStatus() {
		return eleOrderStatus;
	}

	@AndroidFindBy(xpath = "//*[@text='[Part]'][@class='android.widget.TextView']")
	@iOSXCUITFindBy(xpath = "//*[@*='[Part]']")
	private WebElement eleRemoveablePart;

	public WebElement geteleRemoveablePart() {
		return eleRemoveablePart;
	}

	@FindBy(xpath = "//*[@*='Remove']")
	private WebElement eleRemovePopUp;

	public WebElement geteleRemovePopUp() {
		return eleRemovePopUp;
	}

	private WebElement eleRemove;

	public WebElement geteleRemove() {

		return eleRemove = driver.findElementByAccessibilityId("SFM.DELETE_CHILD_LINE.BUTTON");

	}

	public WebElement geteleAddedPart(String value) {

		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='" + value + "']"));
		} else {
			return eleRemove = driver.findElement(By.xpath("//*[@*='" + value + "']"));
		}

	}

	@AndroidFindBy(xpath = "//*[@text='Work Description Mapped'][@class='android.widget.EditText']")
	@iOSXCUITFindBy(xpath="//*[@*='Work Description Mapped']")
	private WebElement EleWODesMappedTxt;

	public WebElement getEleWODesMappedTxt() {
		return EleWODesMappedTxt;
	}

	@FindBy(xpath = "//*[@text='Save'][@class='android.widget.TextView']")
	private WebElement eleSaveButton;

	public WebElement geteleSaveButton() {
		return eleSaveButton;
	}

	@AndroidFindBy(xpath = "//*[@text='Billable Qty']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Billable Qty']/..//XCUIElementTypeTextField")
	private WebElement eleBillableQty;

	public WebElement geteleBillableQty() {
		return eleBillableQty;
	}

	@FindBy(xpath = "//*[@text='[Use Price From Pricebook/Contract]'][@class='android.widget.TextView']")
	private WebElement eleusePriceBookcontract;

	public WebElement geteleusePriceBookcontract() {
		return eleusePriceBookcontract;
	}


	@AndroidFindBy(xpath = "//*[@text='Description']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Description']/../XCUIElementTypeOther")
	private WebElement eleDescriptiontext;

	public WebElement geteleDescriptiontext() {
		return eleDescriptiontext;
	}

	@FindAll({
			@FindBy(xpath = "//*[@text='Problem Description']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//*[@text='Problem Description*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//*[@text='Problem Description']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@label='Problem Description*']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@name='Problem Description']//XCUIElementTypeTextView") })
	private WebElement eleProblemDescriptiontxt;

	public WebElement geteleProblemDescriptiontxt() {
		return eleProblemDescriptiontxt;
	}

	@FindAll({ @FindBy(xpath = "(//*[@*[contains(.,'Billing Type')]])[last()]/following-sibling::*[1]"),
			@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Billing Type']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[1]") })
	private WebElement eleBillingTypeField;

	public WebElement getEleBillingTypeField() {
		return eleBillingTypeField;
	}

	@FindAll({ @FindBy(xpath = "//*[@*[contains(.,'Auto_Date')]]/following-sibling::*/*[1]") })
	private WebElement autoDate;

	public WebElement getAutoDate() {
		return autoDate;
	}

	@FindAll({ @FindBy(xpath = "//*[@*[contains(.,'RS_10552_Automation_DateTime')]]/following-sibling::*/*[1]") })
	private WebElement automationDateTime;

	public WebElement getAutomationDateTime() {
		return automationDateTime;
	}

	@FindAll({ @FindBy(xpath = "//*[@*[contains(.,'RS_10552_Automation_Number')]]/following-sibling::*/*[1]") })
	private WebElement automationNumber;

	public WebElement getAutomationNumber() {
		return automationNumber;
	}

	@FindAll({ @FindBy(xpath = "//*[@text='Is Entitlement Performed']//following-sibling::*[@*='OFF']"),
			@FindBy(xpath = "//XCUIElementTypeOther[contains(@name,'Is Entitlement Performed')]//XCUIElementTypeSwitch[contains(@name,'SFM.LAYOUT.BOOLEAN_SWITCH')]") })
	private WebElement eleEntitlementPerformed;

	public WebElement geteleEntitlementPerformed() {
		return eleEntitlementPerformed;
	}

	private WebElement elePartName;

	public WebElement getelePartName(String sPartName) {

		switch (BaseLib.sOSName.toLowerCase()) {
		case "android":
			elePartName = driver.findElement(By.xpath("//*[@text='" + sPartName + "']"));
			return elePartName;
		case "ios":
			elePartName = driver
					.findElement(By.xpath("(//XCUIElementTypeStaticText[@name='" + sPartName + "'])[last()]"));
			return elePartName;

		}
		return elePartName;
	}

	@FindAll({ @FindBy(xpath = "//*[@text='Confirm']"), @FindBy(xpath = "//*[@*='Confirm']") })
	private WebElement eleConfirm;

	public WebElement geteleConfirm() {
		return eleConfirm;
	}

	@FindAll({
			@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Work Description']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//*[@*='Work Description']/following-sibling::*") })
	private WebElement eleWorkDescription;

	public WebElement geteleWorkDescription() {
		return eleWorkDescription;
	}

	@FindAll({ @FindBy(xpath = "//*[@text='Line Price is Less than 2000']"),
			@FindBy(xpath = "//*[@*='Line Price is Less than 2000']") })
	private WebElement eleLinePriceConfirmationtxt;

	public WebElement geteleLinePriceConfirmationtxt() {
		return eleLinePriceConfirmationtxt;
	}

	@FindAll({ @FindBy(xpath = "//*[@text='OVERVIEW']"), @FindBy(xpath = "//*[@*='OVERVIEW']") })
	private WebElement eleOverViewTab;

	public WebElement getEleOverViewTab() {
		return eleOverViewTab;
	}

	@FindAll({
			@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='URL']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@name='URL']//XCUIElementTypeTextField") })
	private WebElement eleURL;

	public WebElement getEleURL() {
		return eleURL;
	}
	
	
	@FindAll({
		@FindBy(xpath = "//*[@text='Record Type ID']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
		@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Record Type ID']/../XCUIElementTypeOther") })
private WebElement eleRecordtypeid;

public WebElement getRecordtypeid() {
	return eleRecordtypeid;
}
	

@FindAll({
	@FindBy(xpath = "//*[@text='Line Type']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Line Type']/../XCUIElementTypeOther") })
private WebElement eleLineType;

public WebElement geteleLineType() {
return eleLineType;
}
	
	@FindAll({
		@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Number']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']"),
		@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Number']/../XCUIElementTypeOther") })
private WebElement eleNumber;

public WebElement getEleNumber() {
	return eleNumber;
}


@FindAll({
	@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Closed By']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.TextView']"),
	@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Closed By']/../XCUIElementTypeOther") })
private WebElement eleclosedby;
public WebElement getEleclosedby() {
return eleclosedby;
}

@FindAll({
	@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Canceled By']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.TextView']"),
	@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Canceled By']/../XCUIElementTypeOther") })
private WebElement elecanceledby;
public WebElement getelecanceledby() {
return elecanceledby;
}


	@FindAll({
			@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Phone']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@name='Phone']//XCUIElementTypeTextField") })
	private WebElement elePhone;

	public WebElement getElePhone() {
		return elePhone;
	}

	@FindAll({
			@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Email']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//XCUIElementTypeOther[@name='Email']//XCUIElementTypeTextField") })
	private WebElement eleEmail;

	public WebElement getEleEmail() {
		return eleEmail;
	}

	@FindAll({ @FindBy(xpath = "//*[@text='Is Entitlement Performed']//following-sibling::*[@*='OFF']"),
			@FindBy(xpath = "//*") })
	private WebElement eleEntitlementPerformedOn;

	public WebElement getEleEntitlementPerformedOn() {
		return eleEntitlementPerformedOn;
	}
	
	
	@FindAll({
		@FindBy(xpath = "//*[@text='Email']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
		@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Email']/../XCUIElementTypeOther") })

	private WebElement eleEmailvalue;

	public WebElement getEmailvalue() {
	return eleEmailvalue;
	}
	
	
	@FindAll({ @FindBy(xpath = "//*[@text='Customer Down']//following-sibling::*[@*='ON']"),
		@FindBy(xpath = "//XCUIElementTypeOther[contains(@name,'Customer Down')]//XCUIElementTypeSwitch[contains(@name,'SFM.LAYOUT.BOOLEAN_SWITCH')]") })
private WebElement eleCustomerDowntoggle;

public WebElement geteleCustomerDowntoggle() {
	return eleCustomerDowntoggle;
}
	
	@FindAll({
		@FindBy(xpath = "//*[@text='Currency']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
		@FindBy(xpath = "//XCUIElementTypeStaticText[@name='Currency']/../XCUIElementTypeOther") })

	private WebElement elecurrency;

	public WebElement getelecurrency() {
	return elecurrency;
	}
	

	@FindAll({
			@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Billing Information']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']"),
			@FindBy(xpath = "//*[@*='Billing Information']/following-sibling::*") })
	private WebElement eleBillingInformation;

	public WebElement getEleBillingInformation() {
		return eleBillingInformation;
	}

	@FindAll({ @FindBy(xpath = "//android.widget.Switch[@text='OFF']") })
	private WebElement toggleCustomerDown;

	public WebElement getToggleCustomerDown() {
		return toggleCustomerDown;
	}

	@FindAll({ @FindBy(xpath = "//*[@text='Country should be Italy']"),
			@FindBy(xpath = "//*[@*='Country should be Italy']") })
	private WebElement eleCountryshouldbeItaly;

	public WebElement geteleCountryShouldbeItaly() {
		return eleCountryshouldbeItaly;
	}

	@FindAll({ @FindBy(xpath = "//*[@text[contains(.,'Account can')]]"), @FindBy(xpath = "//*[@*='NULL']") })
	private WebElement eleAccountnotNUll;

	public WebElement geteleAccountnotNUll() {
		return eleAccountnotNUll;
	}
	@FindAll({ @FindBy(xpath = "//*[@text='Is Billable']//following-sibling::*[@*='ON']"),
		@FindBy(xpath = "//XCUIElementTypeOther[contains(@name,'Is Billable')]//XCUIElementTypeSwitch[contains(@name,'SFM.LAYOUT.BOOLEAN_SWITCH')]") })
private WebElement eleIsbillable;

public WebElement geteleIsbillable() {
	return eleIsbillable;
}
	
	

	@FindAll({ @FindBy(xpath = "//*[@*[contains(.,'Auto_Text')]]/following-sibling::*/*[1]") })
	private WebElement autoTextBox;

	public WebElement getAutoTextBox() {
		return autoTextBox;
	}

	@FindBy(xpath = "//*[@*[contains(.,'View Event')]]")
	private WebElement eleViewEvent;

	public WebElement getEleViewEvent() {
		return eleViewEvent;
	}

	@FindBy(xpath = "//*[@*='multiLineHeaderTitle']")
	private WebElement eleHeaderTitle;

	public WebElement getEleHeaderTitle() {
		return eleHeaderTitle;
	}

	@FindBy(xpath = "//*[@*='multiLineHeaderSubTitle']")
	private WebElement eleHeaderSubTitle;

	public WebElement getEleHeaderSubTitle() {
		return eleHeaderSubTitle;
	}

	public String verifyWorkOrder() {
		String retText = getEleHeaderTitle().getText() + " ";
		retText += getEleHeaderSubTitle().getText();
		System.out.println("retText:" + retText);
		return retText;
	}

	@FindBy(xpath = "//*[@*[contains(.,'SFM.VALIDATION.LIST.ANCHOR_BUTTON')]]//*[@*='You must add at least one Labor to save.']")
	private WebElement eleNoLaborError;

	public WebElement getEleNoLaborError() {
		return eleNoLaborError;
	}

	@FindBy(xpath = "//*[@*[contains(.,'SFM.VALIDATION.LIST.TOGGLE_BUTTON')]]//*[@*='2 ERROR(S)']")
	private WebElement eleChildLine2IssuesFound;

	public WebElement getEleChildLine2IssuesFound() {
		return eleChildLine2IssuesFound;
	}

	public WebElement getEletapon(String value) {
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@text='" + value + "']"));
		} else {
			return driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + value + "']"));
		}
	}

	@FindBy(xpath = "//*[@*[contains(.,'SFM.VALIDATION.LIST.TOGGLE_BUTTON')]]//*[@*='1 ERROR(S)']")
	private WebElement eleChildLine1IssueFound;

	public WebElement getEleChildLine1IssueFound() {
		return eleChildLine1IssueFound;
	}

	@AndroidFindBy(xpath = "//*[@text='Delete']")
	@iOSXCUITFindBy(xpath = "(//*[@*='Delete'])[last()]")
	private WebElement eleDelete;

	public WebElement geteleDelete() {
		return eleDelete;
	}
	@FindAll({ @FindBy(xpath = "(//*[@*[contains(.,'Case Reason')]])[last()]/following-sibling::*[1]"),
		@FindBy(xpath = "//*[@class='android.widget.TextView'][@text='Billing Type']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[1]") })
	private WebElement eleCaseReasonField;

	public WebElement getEleCaseReasonField() {
		return eleCaseReasonField;
	}
	


	@AndroidFindBy(xpath ="//*[@class='android.widget.TextView'][@text='Line Qty*']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']")
	@iOSXCUITFindBy(xpath ="//*[@*='Line Qty*']/following-sibling::*")
	private WebElement eleLineQtymandatoryfldd;
	
	public WebElement geteleLineQtymandatoryfldd() {
		return eleLineQtymandatoryfldd;
	}
	

	public WebElement getEleWoPriorityTxt(String sWoNumTxt)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='" + sWoNumTxt + "']/following-sibling::*[2]"));
		} else {
			return driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + sWoNumTxt + "']/following-sibling::*[1]"));
		}
	}
	
	public WebElement getEleWoLocationTxt(String sWoNumTxt)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='" + sWoNumTxt + "']/following-sibling::*[1]"));
		} else {
			return driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='" + sWoNumTxt + "']/following-sibling::*[2]"));
		}
	}
	
	@FindBy(xpath="//*[@*[contains(.,'Item')]]//*[@class='android.widget.TextView'][1]")
	private WebElement eleWoOrderNumberTxt;
	public WebElement getEleWoOrderNumberTxt() {
		return eleWoOrderNumberTxt;
	}
	
	@FindBy(xpath="//*[@*[contains(.,'Item')]]//*[@class='android.widget.TextView'][2]")
	private WebElement eleBillingCityTxt;
	public WebElement getEleBillingCityTxt() {
		return eleBillingCityTxt;
	}
	
	@FindBy(xpath="//*[@*[contains(.,'Item')]]//*[@class='android.widget.TextView'][3]")
	private WebElement elePriorityTxt;
	public WebElement getElePriorityTxt() {
		return elePriorityTxt;
	}
	
	@FindBy(xpath="//*[@*='SFM.VALIDATION.LIST.TOGGLE_BUTTON']")
	private WebElement eleValidationToggle;
	public WebElement getEleValidationToggle() {
		return eleValidationToggle;
	}
}
