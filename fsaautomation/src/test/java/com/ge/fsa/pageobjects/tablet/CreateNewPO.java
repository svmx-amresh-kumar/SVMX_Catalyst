package com.ge.fsa.pageobjects.tablet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ge.fsa.lib.CommonUtility;

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
	
//	@FindBy(xpath="//div[@class='svmx-menu-icon-label'][text()='Create New']")
	@FindBy(xpath="//*[text()='Create New']")
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
	@FindBy(xpath="//div[text()='Create New Custom object']")
	private WebElement eleCreateNewcustomrecord;
	public WebElement getEleCreateNewcustomrecord()
	{
		return eleCreateNewcustomrecord;
	}
	 
	
	@FindBy(xpath="//div[text()='Create New Installed Product Automation sahi']")
	private WebElement eleCreateIB;
	public WebElement getEleCreateNewIB()
	{
		return eleCreateIB;
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
	
	@FindBy(xpath="//span[@class='x-label-text-el'][contains(text(), 'Installed Product ID')]/../../div[@class='x-body-el x-widthed']//input")
	private WebElement eleClickInstalledProductID;
	public WebElement getClickInstalledProductID()
	{
		return eleClickInstalledProductID;
	}
	
	@FindBy(xpath="//div[. = 'Site']//input[@class = 'x-input-el']")
	private WebElement eleClicksite;
	public WebElement getEleClicksite()
	{
		return eleClicksite;
	}
	
	@FindBy(xpath="//div[. = 'Component']//input[@class = 'x-input-el']")
	private WebElement eleClickComponent;
	public WebElement getEleClickComponent()
	{
		return eleClickComponent;
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
	
//	@FindBy(xpath="//div[@class='x-dock-body']//div[@class='x-body x-widthed x-heighted']//div[@class='x-textareainput x-textinput x-input x-component x-sized x-widthed x-heighted']//textarea[@class='x-input-el']")
//	private WebElement eleproformainvoicetextarea;
//	public WebElement getEleproformainvoicetextarea()
//	{
//		return eleproformainvoicetextarea;
//	}
	
	@FindBy(xpath="//span[@class='x-label-text-el'][text()='Proforma Invoice']/../..//input[@class='x-input-el']")
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
	
	@FindBy(xpath="//div[text()='Create New Work Order']")
	private WebElement eleCreateNewInstalledProduct;
	public WebElement getCreateNewInstalledProduct()
	{
		return eleCreateNewInstalledProduct;
	}
	
	//Added by Harish.CS--------------
	private WebElement eleItemNameTxt;
	public WebElement getEleItemNameTxt(String sItemName)
	{
		eleItemNameTxt=driver.findElement(By.xpath("//div[@class='listitem-sfm-search-result-name'][text()='"+sItemName+"']"));
		return eleItemNameTxt;
	}
	@FindBy(xpath="//*[contains(text(),'Location Name')][@class = 'x-label-text-el']/../..//input")
	private WebElement eleLocNameTxt;
	public WebElement getEleLocNameTxt() {
		return eleLocNameTxt;
	}
	@FindBy(xpath="//*[contains(text(),'Auto_Custom_Object2 Name')][@class = 'x-label-text-el']/../..//input")
	private WebElement eleCustomObjNameTxt;
	public WebElement getEleCustomObjNameTxt() {
		return eleCustomObjNameTxt;
	}
	//----------------------
	
	/**
	 * Owner : Meghana Rao P
	 * @param commonsUtility = Passing the function CommonUtility
	 * @param accountName = Passing the Account Field
	 * @param contactName = Passing the Contact
	 * @param ProdutName = Passing the Product
	 * @param priority = Passing the Priority
	 * @param billingType = Passing the billingType
	 */
	public void createWorkOrder(CommonUtility commonUtility,String accountName, String contactName, String ProdutName, String priority, String billingType, String proformaInvoice) throws Exception
	{
		
		commonUtility.tap(getEleCreateNew());
		commonUtility.tap(getEleCreateNewWorkOrder());
		Thread.sleep(2000);
		// Adding Value for Account
		commonUtility.tap(getEleClickAccountfield());
		commonUtility.lookupSearch(accountName);
	
		// Adding Value for Contact
		commonUtility.tap(getEleClickContactfield());
		commonUtility.lookupSearch(contactName);
		
		// Adding Value for Product
		commonUtility.tap(getEleClickProductfield());
		commonUtility.lookupSearch(ProdutName);
		
		// Selecting the Priority Low
		commonUtility.setPickerWheelValue(getEleClickPriorityPicklist(), priority);
		commonUtility.setPickerWheelValue(getEleClickBillingTypePicklist(), billingType);
		//getEleproformainvoicevalue().click();
		//commonsUtility.tap(getEleproformainvoicevalue());
		//commonsUtility.tap(getEleproformainvoicevalue());
		getEleproformainvoicetextarea().sendKeys(proformaInvoice);
		//commonsUtility.tap(getEleupdatethetextfield());
		Thread.sleep(1000);
		commonUtility.tap(getEleSaveWorkOrdert());
		
	}
	
	
	
	public void createInstalledProduct(CommonUtility commonUtility,String accountName, String ProdutName,String InstalledProductID) throws Exception
	{
		
		commonUtility.tap(getEleCreateNew());
		commonUtility.tap(getEleCreateNewIB());
		Thread.sleep(2000);
		// Adding Value for Account
		commonUtility.tap(getEleClickAccountfield());
		commonUtility.lookupSearch(accountName);
	
		
		// Adding Value for Product
		commonUtility.tap(getEleClickProductfield());
		commonUtility.lookupSearch(ProdutName);
		// Adding Value for InstalledproductID
				commonUtility.tap(getClickInstalledProductID());
			getClickInstalledProductID().sendKeys(InstalledProductID);
		
		Thread.sleep(1000);
		commonUtility.tap(getEleSaveWorkOrdert());
		
	}
	
	


}