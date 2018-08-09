package com.ge.fsa.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ge.fsa.lib.BaseLib;

import io.appium.java_client.AppiumDriver;


public class InventoryPO{
	
	public InventoryPO(AppiumDriver driver) 
	{
	this.driver = driver;
	PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	
	@FindBy(xpath="//div[@class='svmx-menu-icon-label'][text()='Parts']")
	private WebElement elePartsIcn;
	public WebElement getelePartsIcn()
	{
		return elePartsIcn;
	}
	
	@FindBy(xpath="//*[text() = 'My Stock']")
	private WebElement eleMyStockTab;
	public WebElement geteleMyStockTab()
	{
		return eleMyStockTab;
	}
	
	@FindBy(xpath="//*[text() = 'Catalog']")
	private WebElement eleCatalogTab;
	public WebElement geteleCatalogTab()
	{
		return eleCatalogTab;
	}
	

}
