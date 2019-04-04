package com.ge.fsa.pageobjects.phone;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
		getEleAccountFied().click();;
		commonUtility.ph_lookupSearch(accountName);
	
		// Adding Value for Contact
		getEleContactFied().click();
		commonUtility.ph_lookupSearch(contactName);
		
		// Adding Value for Product
		getEleProductFied().click();
		commonUtility.ph_lookupSearch(ProdutName);
		
		// Selecting the Priority Low
		getElePriorityField().click();
		getEleDropDownValue(priority).click();
		getEleBillingTypeField().click();
		getEleDropDownValue(billingType).click();
		//getEleproformainvoicevalue().click();
		//commonsUtility.tap(getEleproformainvoicevalue());
		//commonsUtility.tap(getEleproformainvoicevalue());
		commonUtility.custScrollToElementAndClick(getEleProformaInvoiceField());
		getEleProformaInvoiceField().sendKeys(proformaInvoice);
		//commonsUtility.tap(getEleupdatethetextfield());
		Thread.sleep(1000);
		getEleAddWorkOrder().click();
		
	}
	
}