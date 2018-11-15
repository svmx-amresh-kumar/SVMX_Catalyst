package com.ge.fsa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.ge.fsa.lib.GenericLib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class ExploreSearchPO
{
	public ExploreSearchPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	
	private WebElement eleSearchNameTxt;
	public WebElement getEleSearchNameTxt(String sSearchTxt)
	{
		 eleSearchNameTxt=driver.findElement(By.xpath("//div[@class='listitem-sfmsearch-lineup-list-item-name'][text()='"+sSearchTxt+"']"));
		 return eleSearchNameTxt;
	}
	
	private WebElement eleWorkOrderIDTxt;
	public WebElement getEleWorkOrderIDTxt(String sWorkOrderIDTxt)
	{
		eleWorkOrderIDTxt=driver.findElement(By.xpath("//div[@class='x-inner-el sfmsearch-grid-cell-inner'][text()='"+sWorkOrderIDTxt+"']"));
		 return eleWorkOrderIDTxt;
	}
	
	
	@FindBy(xpath="//div[@class='x-innerhtml'][text()='No records to display.']")
	private WebElement eleNorecordsToDisplay;
	public WebElement getEleNorecordsToDisplay()
	{
		return eleNorecordsToDisplay;
	}
	
	@FindBy(xpath="//div[text()='Explore']")
	private WebElement eleExploreIcn;
	public WebElement getEleExploreIcn()
	{
		return eleExploreIcn;
	}
	@FindBy(xpath="//input[@name='keyWord']")
	private WebElement eleExploreSearchTxtFld;
	public WebElement getEleExploreSearchTxtFld()
	{
		return eleExploreSearchTxtFld;
	}
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='Search']")
	private WebElement eleExploreSearchBtn;
	public WebElement getEleExploreSearchBtn()
	{
		return eleExploreSearchBtn;
	}
	
	@FindBy(xpath="//span[text()='Reset filter']")
	private WebElement eleResetFilerBtn;
	public WebElement getEleResetFilerBtn()
	{
		return eleResetFilerBtn;
	}
	
	@FindBy(xpath="//div[@class='x-label-el sfmsearch-include-online-label']/../div[@class = 'x-body-el']/div/div[5]")
	private WebElement eleOnlineBttn;
	public WebElement getEleOnlineBttn()
	{
		return eleOnlineBttn;
	}
	private WebElement eleExploreChildSearchTxt;
	public WebElement getEleExploreChildSearchTxt(String sExploreChildSearchTxt)
	{
		eleExploreChildSearchTxt=driver.findElement(By.xpath("//div[@class='listitem-sfmsearch-lineup-list-item-name'][contains(text(),'"+sExploreChildSearchTxt+"')]"));
		return eleExploreChildSearchTxt;
	}
	

	@FindBy(xpath="//div[@class='icon-cloud-download sfmsearch-download-icon']")
	private WebElement eleCloudSymbol;
	public WebElement getEleCloudSymbol()
	{
		return eleCloudSymbol;
	}
	
	
	public void selectWorkOrder(CommonsPO commonsPo, String sWOName) throws InterruptedException
	{
		
		commonsPo.tap(getEleExploreSearchTxtFld());
		//getEleExploreSearchTxtFld().click();
		try {commonsPo.tap(getEleResetFilerBtn());Thread.sleep(GenericLib.iLowSleep);}catch(Exception e) {}
		getEleExploreSearchTxtFld().clear();
		getEleExploreSearchTxtFld().sendKeys(sWOName);
		commonsPo.tap(getEleExploreSearchBtn());
		commonsPo.tap(getEleWorkOrderIDTxt(sWOName));
		Thread.sleep(GenericLib.iLowSleep);
	}
	
	
	
	
}




