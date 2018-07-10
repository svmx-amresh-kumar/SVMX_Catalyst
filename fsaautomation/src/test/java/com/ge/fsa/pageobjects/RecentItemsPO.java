/*
 *  @author MeghanaRao
 */
package com.ge.fsa.pageobjects;

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
	@FindBy(xpath="//div[text()='Recent Items']']")
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
	

	
	/**
	 * Owner: Meghana Rao
	 * @param commonsPo = This will pass the CommonsPO functions
	 * @throws InterruptedException 
	 */
	public void createEvent(CommonsPO commonsPo ) throws InterruptedException 
	{
		commonsPo.tap(getEleClickRecentItems());
	
		
		
		
	}
	
}

