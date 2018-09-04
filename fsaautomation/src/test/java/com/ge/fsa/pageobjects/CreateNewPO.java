package com.ge.fsa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class CreateNewPO
{
	public CreateNewPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	
	@FindBy(xpath="//div[@class='svmx-menu-icon-label'][text()='Create New']")
	private WebElement eleCreateNew;
	public WebElement getEleCreateNew()
	{
		return eleCreateNew;
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
	
	@FindBy(xpath="//div[text()='Create New Work Order']")
	private WebElement eleCreateNewWorkOrder;
	public WebElement getEleCreateNewWorkOrder()
	{
		return eleCreateNewWorkOrder;
	}
	
	@FindBy(xpath="//div[. = 'Account']//input[@class = 'x-input-el']")
	private WebElement eleClickAccountfield;
	public WebElement getEleClickAccountfield()
	{
		return eleClickAccountfield;
	}
	
	@FindBy(xpath="//div[. = 'Contact']//input[@class = 'x-input-el']")
	private WebElement eleClickContactfield;
	public WebElement getEleClickContactfield()
	{
		return eleClickContactfield;
	}
	
	@FindBy(xpath="//div[. = 'Product']//input[@class = 'x-input-el']")
	private WebElement eleClickProductfield;
	public WebElement getEleClickProductfield()
	{
		return eleClickProductfield;
	}
	
	// Xpath for Priority Value
	@FindBy(xpath="//span[text()='Priority']/../..//div[@class='x-body-el x-widthed']//input[@class='x-input-el']")
	private WebElement eleClickPriorityPicklist;
	public WebElement getEleClickPriorityPicklist()
	{
		return eleClickPriorityPicklist;
	}
	
	// Xpath for Billing Type
	
	@FindBy(xpath="//span[text()='Billing Type']/../..//div[@class='x-body-el x-widthed']//input[@class='x-input-el']")
	private WebElement eleClickBillingTypePicklist;
	public WebElement getEleClickBillingTypePicklist()
	{
		return eleClickBillingTypePicklist;
	}
	
	
	@FindBy(xpath="//span[. = 'Proforma Invoice']/../..//div[@class = 'x-innerhtml']")
	private WebElement eleproformainvoicevalue;
	public WebElement getEleproformainvoicevalue()
	{
		return eleproformainvoicevalue;
	}
	
	@FindBy(xpath="//div[@class='x-dock-body']//div[@class='x-body x-widthed x-heighted']//div[@class='x-textareainput x-textinput x-input x-component x-sized x-widthed x-heighted']//textarea[@class='x-input-el']")
	private WebElement eleproformainvoicetextarea;
	public WebElement getEleproformainvoicetextarea()
	{
		return eleproformainvoicetextarea;
	}
	
	// Save the New Work Order
	@FindBy(xpath="//span[text()='Save']")
	private WebElement eleSaveWorkOrder;
	public WebElement getEleSaveWorkOrdert()
	{
		return eleSaveWorkOrder;
	}
	
	@FindBy(xpath="//span[text()='Update']")
	private WebElement eleupdatethetextfield;
	public WebElement getEleupdatethetextfield()
	{
		return eleupdatethetextfield;
	}
	
	//Added by Harish.CS
	private WebElement eleItemNameTxt;
	public WebElement getEleItemNameTxt(String sItemName)
	{
		eleItemNameTxt=driver.findElement(By.xpath("//div[@class='listitem-sfm-search-result-name'][text()='"+sItemName+"']"));
		return eleItemNameTxt;
	}
	
	
	/**
	 * Owner : Meghana Rao P
	 * @param commonsPo = Passing the function CommonsPO
	 * @param accountName = Passing the Account Field
	 * @param contactName = Passing the Contact
	 * @param ProdutName = Passing the Product
	 * @param priority = Passing the Priority
	 * @param billingType = Passing the billingType
	 */
	public void createWorkOrder(CommonsPO commonsPo,String accountName, String contactName, String ProdutName, String priority, String billingType, String proformaInvoice) throws Exception
	{
		
		commonsPo.tap(getEleCreateNew());
		commonsPo.tap(getEleCreateNewWorkOrder());
		Thread.sleep(2000);
		// Adding Value for Account
		commonsPo.tap(getEleClickAccountfield());
		commonsPo.lookupSearch(accountName);
	
		// Adding Value for Contact
		commonsPo.tap(getEleClickContactfield());
		commonsPo.lookupSearch(contactName);
		
		// Adding Value for Product
		commonsPo.tap(getEleClickProductfield());
		commonsPo.lookupSearch(ProdutName);
		
		// Selecting the Priority Low
		commonsPo.pickerWheel(getEleClickPriorityPicklist(), priority);
		commonsPo.pickerWheel(getEleClickBillingTypePicklist(), billingType);
		getEleproformainvoicevalue().click();
		commonsPo.tap(getEleproformainvoicevalue());
		getEleproformainvoicetextarea().sendKeys(proformaInvoice);
		commonsPo.tap(getEleupdatethetextfield());
		Thread.sleep(1000);
		commonsPo.tap(getEleSaveWorkOrdert());
		
	}


}