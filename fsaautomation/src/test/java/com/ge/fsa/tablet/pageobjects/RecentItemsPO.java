package com.ge.fsa.tablet.pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ge.fsa.lib.CommonUtility;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class RecentItemsPO
{
	public RecentItemsPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	TouchAction touchAction = null;

	
	
	// Click on Recent Items
	@FindBy(xpath="//div[text()='Recent Items']")
	private WebElement eleClickRecentItems;
	public WebElement getEleClickRecentItems()
	{
		return eleClickRecentItems;
	}

	private WebElement eleworkOrderNumberClick;
	public WebElement getEleworkOrderNumberClick(String sworkordernumber)
	{
		eleworkOrderNumberClick=driver.findElement(By.xpath("//div[text()='"+sworkordernumber+"']"));
		return eleworkOrderNumberClick;
	}
	
	
	private WebElement eleSearchtext;
	public WebElement getEleSearchtext()
	{
		
		eleSearchtext = driver.findElement(By.xpath("(//input[@class='x-input-el'][@placeholder='Search'])[1]"));
		return eleSearchtext;
	}
	
	private WebElement eleworkordertabtap;
	public WebElement getEleworkordertabtap()
	{
		
		eleworkordertabtap = driver.findElement(By.xpath("(//div[@class='x-innerhtml'][contains(text(),'Work Order ')])[1]"));
		return eleworkordertabtap;
	}
	
	
	private WebElement eleWorkorderNumberClick;
	public WebElement getEleWorkordernumberclick(String workordername)
	{
		
		eleWorkorderNumberClick = driver.findElement(By.xpath("(//div[@class='x-inner-el'][text()='"+workordername+"'])[1]"));
		return eleWorkorderNumberClick;
	}
	
	
	@FindBy(xpath="(//*[contains(text(), 'Work Order (')]//..//..//..//..//..//..//div[text()='Record name']//..//..//..//..//..//..//div[@class='x-inner-el'])[1]")
	private WebElement eleworkorderrecentused;
	public WebElement getEleworkorderrecentused()
	{
		return eleworkorderrecentused;
	}
	
	private WebElement eleRecentitemsName;
	public WebElement geteleChecklistName(String RecentitemsName)
	{
		eleRecentitemsName = driver.findElement(By.xpath("(//*[contains(text(), '"+RecentitemsName+"')]//..//..//..//..//..//..//div[text()='Record name']//..//..//..//..//..//..//div[@class='x-inner-el'])[1]"));
		return eleRecentitemsName;
	}
	
	
	private WebElement eletaponobject;
	public WebElement gettaponobject(String objectRecentitemsName)
	{
		eletaponobject = driver.findElement(By.xpath("//*[contains(text(), '"+objectRecentitemsName+"')]"));
		return eletaponobject;
	}
	
	
	
	@FindBy(xpath="//div[text() ='There are no items created, viewed, edited recently.']")
	private WebElement elecheckrecentitemisempty;
	public WebElement getelecheckrecentitemisempty()
	{
		return elecheckrecentitemisempty;
	}
	
	
	/**
	 * Owner: Meghana Rao
	 * @param commonsUtility = This will pass the CommonUtility functions
	 * @throws InterruptedException 
	 */
	public void clickonWorkOrder(CommonUtility commonUtility, String workordername) throws InterruptedException 
	{
		commonUtility.tap(getEleClickRecentItems());
		Thread.sleep(1000);
		commonUtility.tap(getEleSearchtext());
		getEleSearchtext().sendKeys(workordername);
		try
		{
			commonUtility.tap(getEleWorkordernumberclick(workordername));
	
		}
		catch(Exception e)
		{
			commonUtility.tap(getEleworkordertabtap());
			commonUtility.tap(getEleWorkordernumberclick(workordername));
			
		}
		Thread.sleep(1000);
	
		
	}
	
}

