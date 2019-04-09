package com.ge.fsa.pageobjects.phone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;

import io.appium.java_client.AppiumDriver;

public class Ph_CreateNewPO {
	
	public Ph_CreateNewPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	
	@FindBy(xpath="//*[@content-desc='APP.TOOLBAR.CREATE_NEW.BUTTON']")
	private WebElement eleCreateNew;
	public WebElement getEleCreateNew() {
		return eleCreateNew;
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Create New Work Order']")
	private WebElement eleCreateNewWorkOrder;
	public WebElement getEleCreateNewWorkOrder() {
		return eleCreateNewWorkOrder;
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Account Lookup']")
	private WebElement eleAccountField;
	public WebElement getEleAccountFied() {
		return eleAccountField;
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Contact Lookup']")
	private WebElement eleContactField;
	public WebElement getEleContactFied() {
		return eleContactField;
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Product Lookup']")
	private WebElement eleProductField;
	public WebElement getEleProductFied() {
		return eleProductField;
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Priority']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@text='--None--']")
	private WebElement elePriorityField;
	public WebElement getElePriorityField() {
		return elePriorityField;
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Billing Type']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@text='--None--']")
	private WebElement eleBillingTypeField;
	public WebElement getEleBillingTypeField() {
		return eleBillingTypeField;
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Proforma Invoice']/following-sibling::*[@class='android.view.ViewGroup'][1]/*[@class='android.widget.EditText']")
	private WebElement eleProformaInvoiceField;
	public WebElement getEleProformaInvoiceField() {
		return eleProformaInvoiceField;
	}
	
	@FindBy(xpath="//*[@class='android.widget.TextView'][@text='Add']")
	private WebElement eleAddWorkOrder;
	public WebElement getEleAddWorkOrder() {
		return eleAddWorkOrder;
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
		getEleAddWorkOrder().click();
		
	}
	
	@FindBy(xpath="//*[@*='SFM_LAYOUT.LOOKUP.SEARCH_BAR']")
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
			return eleSearchListItem =  driver.findElement(By.xpath("//*[contains(@label,'"+sName+"')][contains(@name,'SFM.LAYOUT.LOOKUP')]"));			}

	}

	public void selectFromlookupSearchList(CommonUtility commonUtility,WebElement eleToSetValue, String sValue) {
		commonUtility.custScrollToElementAndClick(eleToSetValue);
		getElelookupsearch().click();
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			getElelookupsearch().sendKeys(sValue);
		}else {
			getElelookupsearch().sendKeys(sValue+"\n");
		}
		getEleSearchListItem(sValue).click();
	}

	public void selectFromPickList(CommonUtility commonUtility,WebElement eleToSetValue, String sValue){
		
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
				
}