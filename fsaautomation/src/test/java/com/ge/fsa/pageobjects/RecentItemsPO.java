/*
 *  @author MeghanaRao
 */
package com.ge.fsa.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;


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
	
	
	
	/**
	 * Owner: Meghana Rao
	 * @param commonsPo = This will pass the CommonsPO functions
	 * @throws InterruptedException 
	 */
	public void clickonWorkOrder(CommonsPO commonsPo, String workordername) throws InterruptedException 
	{
		commonsPo.tap(getEleClickRecentItems());
		Thread.sleep(1000);
		commonsPo.tap(getEleSearchtext());
		getEleSearchtext().sendKeys(workordername);
		commonsPo.tap(getEleworkordertabtap());
		commonsPo.tap(getEleWorkordernumberclick(workordername));
	
		
	
		
	}
	
}

