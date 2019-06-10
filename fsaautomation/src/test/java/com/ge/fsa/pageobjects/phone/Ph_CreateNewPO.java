package com.ge.fsa.pageobjects.phone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Ph_CreateNewPO {
	
	public Ph_CreateNewPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	AppiumDriver driver = null;
	
	@FindBy(xpath="//*[@*='APP.TOOLBAR.CREATE_NEW.BUTTON']")
	private WebElement eleCreateNew;
	public WebElement getEleCreateNew() {
		return eleCreateNew;
	}
	
	@FindBy(xpath="//*[@*='Create New Work Order']")
	private WebElement eleCreateNewWorkOrder;
	public WebElement getEleCreateNewWorkOrder() {
		return eleCreateNewWorkOrder;
	}
	
	@FindBy(xpath="//*[@*='Create New Custom object']")
	private WebElement eleCreateNewCustomObject;
	public WebElement getEleCreateNewCustomObject() {
		return eleCreateNewCustomObject;
	}
	
	@FindBy(xpath="(//*[@*='Account Lookup'])[last()]")
	private WebElement eleAccountField;
	public WebElement getEleAccountFied() {
		return eleAccountField;
	}
	
	@FindBy(xpath="(//*[@*='Contact Lookup'])[last()]")
	private WebElement eleContactField;
	public WebElement getEleContactFied() {
		return eleContactField;
	}
	
	@FindBy(xpath="(//*[@*='Product Lookup'])[last()]")
	private WebElement eleProductField;
	public WebElement getEleProductFied() {
		return eleProductField;
	}
	
	@FindAll({@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Priority']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@text='--None--']"),
	@FindBy(xpath="//*[@*='Priority']/following-sibling::*")})
	private WebElement elePriorityField;
	public WebElement getElePriorityField() {
		return elePriorityField;
	}
	
	@FindAll({@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Billing Type']/following-sibling::*[@class='android.view.ViewGroup'][1]"),
	@FindBy(xpath="//*[@*='Billing Type']/following-sibling::*")})
	private WebElement eleBillingTypeField;
	public WebElement getEleBillingTypeField() {
		return eleBillingTypeField;
	}
	
	@FindAll({@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Proforma Invoice']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']"),
		@FindBy(xpath="//*[@*='Proforma Invoice']//XCUIElementTypeTextField")})
	private WebElement eleProformaInvoiceField;
	public WebElement getEleProformaInvoiceField() {
		return eleProformaInvoiceField;
	}
	
	@FindAll({@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Add']"),
		@FindBy(xpath="//*[@*='Add']")})
	private WebElement eleAdd;
	public WebElement getEleAdd() {
		return eleAdd;
	}
	
	public WebElement getEleDropDownValue(String value) {
		return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+value+"']"));
	}
	
	
	public void createWorkOrder(CommonUtility commonUtility,String accountName, String contactName, String ProdutName, String priority, String billingType, String proformaInvoice) throws Exception
	{
		
		getEleCreateNew().click();;
		commonUtility.custScrollToElementAndClick(getEleCreateNewWorkOrder());
		Thread.sleep(2000);
		// Adding Value for Account
		selectFromlookupSearchList(commonUtility, getEleAccountFied(), accountName);
		// Adding Value for Contact
		selectFromlookupSearchList(commonUtility, getEleContactFied(), contactName);
		// Adding Value for Product
		selectFromlookupSearchList(commonUtility, getEleProductFied(), ProdutName);
		// Selecting the Priority Low
		selectFromPickList(commonUtility, getElePriorityField(), priority);
		selectFromPickList(commonUtility, getEleBillingTypeField(), billingType);

		commonUtility.custScrollToElementAndClick(getEleProformaInvoiceField());
		getEleProformaInvoiceField().sendKeys(proformaInvoice);
		Thread.sleep(1000);
		getEleAdd().click();
		ExtentManager.logger.log(Status.INFO, "Created New Workorder with Account Name:"+accountName+", Contact Name:"+contactName+", Product Name:"+ProdutName
				+", Priority:"+priority+", Billing Type:"+billingType+" Proforma Invoice:"+proformaInvoice);
	}
	
	@FindBy(xpath="//*[@*[contains(.,'SEARCH_BAR')]]")
	private WebElement elelookupsearch;
	public WebElement getElelookupsearch()
	{
		return elelookupsearch;
	}
	
	private WebElement eleSearchListItem;
	public WebElement getEleSearchListItem(String sName)
	{
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			return eleSearchListItem = driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+sName+"']"));
		}else {
			return eleSearchListItem =  driver.findElement(By.xpath("//*[contains(@label,'"+sName+"')][contains(@name,'ITEM')]"));			}

	}

	public void selectFromlookupSearchList(CommonUtility commonUtility,WebElement eleToSetValue, String sValue) throws InterruptedException {
		System.out.println("Select From Lookup List");
		commonUtility.custScrollToElementAndClick(eleToSetValue);
		getElelookupsearch().click();
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			getElelookupsearch().sendKeys(sValue+"\\n");
		}else {
			getElelookupsearch().sendKeys(sValue+"\n");
		}
		Thread.sleep(2000);
		getEleSearchListItem(sValue).click();
	}

	public void selectFromPickList(CommonUtility commonUtility,WebElement eleToSetValue, String sValue){
		System.out.println("Select From Picklist");
		commonUtility.custScrollToElementAndClick(eleToSetValue);
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			driver.findElement(By.xpath("//*[@text='"+sValue+"']")).click();
		}else{
			driver.findElement(By.xpath("//*[@label='"+sValue+"']")).click();
		}
	}

	@FindAll({@FindBy(xpath="//*[@text='Account Lookup']"),
		@FindBy(id="Account Account Lookup")})
	private WebElement eleAccountLookUp;
	public WebElement getEleAccountLookUp()
	{
		return eleAccountLookUp;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Partner Account Lookup']"),
		@FindBy(id="Partner Account Lookup")})
	private WebElement elePartnerAccountLookUp;
	public WebElement getelePartnerAccountLookUp()
	{
		return elePartnerAccountLookUp;
	}

	private WebElement eleContactLookuptap;
	public WebElement getEleContactLookuptap()
	{
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleContactLookuptap = driver.findElement(By.xpath("//*[@text='Contact Lookup']"));
			return eleContactLookuptap;
		}else {
			return eleContactLookuptap = driver.findElementByAccessibilityId("Contact Contact Lookup");
		}
	}


	private WebElement eleproductLookuptap;
	public WebElement getEleProductLookuptap()
	{
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleproductLookuptap = driver.findElement(By.xpath("//*[@text='Product Lookup']"));
			return eleproductLookuptap;
		}else{
			return eleproductLookuptap = driver.findElementByAccessibilityId("Product Product Lookup");
		}
	}

	@FindAll({@FindBy(xpath="//*[@text='Billing Type']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
		@FindBy(xpath="//XCUIElementTypeStaticText[@name='Billing Type']/../XCUIElementTypeOther")})
	private WebElement elebillingtype;
	public WebElement getElebillingtype()
	{
		return elebillingtype;
	}



	@FindAll({@FindBy(xpath="//*[@text='Priority']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
		@FindBy(xpath="//XCUIElementTypeStaticText[@name='Priority']/../XCUIElementTypeOther")})
	private WebElement elePriority;
	public WebElement getElePriority()
	{
		return elePriority;
	}

	@FindAll({@FindBy(xpath="//*[@text='Proforma Invoice']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
		@FindBy(xpath="//XCUIElementTypeStaticText[@name='Proforma Invoice']/../XCUIElementTypeOther")})	
	private WebElement eleProformaInvoice;
	public WebElement getEleProformaInvoice()
	{
		
		return eleProformaInvoice;

	}
	
	@FindAll({@FindBy(xpath="//*[@*[contains(.,'Email')]]/following-sibling::*/*"),
		@FindBy(xpath="//*[@*='Email']//XCUIElementTypeTextField")})
	private WebElement eleEmail;
	public WebElement getEleEmail() {
		return eleEmail;
	}
	
	@FindAll({@FindBy(xpath="//*[@*[contains(.,'Location Name')]]/following-sibling::*/*"),
		@FindBy(xpath="//*[@*[contains(.,'Location Name')]]//XCUIElementTypeTextField")})
	private WebElement eleLocationName;
	public WebElement getEleLocationName() {
		return eleLocationName;
	}
	
	@FindAll({@FindBy(xpath="//*[@*[contains(.,'Status')]]//*[contains(@name,'SFM.LAYOUT.EDIT.PICKLIST')]"),
		@FindBy(xpath="//*[@*[contains(.,'Status')]]/following-sibling::*/*")
		})
	private WebElement eleStatus;
	public WebElement getEleStatus() {
		return eleStatus;
	}
	
	@FindBy(xpath="(//*[@*='Contact ID Lookup'])[last()]")
	private WebElement eleContactIDLookup;
	public WebElement getEleContactIDLookup() {
		return eleContactIDLookup;
	}
	
	@FindAll({@FindBy(xpath="//*[@*[contains(.,'Subject')]]/following-sibling::*/*"),
		@FindBy(xpath="//*[@*[contains(.,'Subject')]]//XCUIElementTypeTextField")})
	private WebElement eleSubject;
	public WebElement getEleSubject() {
		return eleSubject;
	}
	
	@FindAll({@FindBy(xpath="//*[@*[contains(.,'Installed Product ID')]]/following-sibling::*/*"),
		@FindBy(xpath="//*[@*='Installed Product ID*']//XCUIElementTypeTextField")})
	private WebElement eleInstalledProductID;
	public WebElement getEleInstalledProductID() {
		return eleInstalledProductID;
	}
	
	@FindAll({@FindBy(xpath="//*[@*[contains(.,'Auto_Custom_Object2 Name')]]/following-sibling::*/*"),
		@FindBy(xpath="//*[@*='Auto_Custom_Object2 Name']//XCUIElementTypeTextField")})
	private WebElement eleAutoCustomObject;
	public WebElement getEleAutoCustomObject() {
		return eleAutoCustomObject;
	}
}