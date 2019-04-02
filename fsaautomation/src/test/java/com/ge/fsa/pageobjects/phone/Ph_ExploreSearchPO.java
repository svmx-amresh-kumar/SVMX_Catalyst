package com.ge.fsa.pageobjects.phone;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ph_ExploreSearchPO
{
	public Ph_ExploreSearchPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	

	@FindAll({@FindBy(xpath="//*[@text='More']"),
	@FindBy(id="More, tab, 4 of 4")})
	private WebElement eleMoreBtn;
	public WebElement getEleMoreBtn()
	{
		return eleMoreBtn;
	}
	
	@FindBy(xpath="//*[@text='Explore']")
	private WebElement eleExploreIcn;
	public WebElement geteleExploreIcn()
	{
		return eleExploreIcn;
	}
	
	@FindBy(xpath="//*[@text='AUTOMATION SEARCH']")
	private WebElement eleAutomationSearch;
	public WebElement geteleAutomationSearch()
	{
		return eleAutomationSearch;
	}
	
	@FindBy(xpath="//*[@text='Work Orders']")
	private WebElement eleWorkOrdersChildSearch;
	public WebElement geteleWorkOrdersChildSearch()
	{
		return eleWorkOrdersChildSearch;
	}

	@FindBy(xpath ="//*[@text='Search Keyword...']")
	private WebElement eleSearchKeyword;
	public WebElement geteleSearchKeyword()
	{
		return eleSearchKeyword;
	}
	
	private WebElement eleSearchListItem;
	public WebElement getEleSearchListItem(String sName)
	{
			return eleSearchListItem = driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+sName+"']"));
	
	}
	
		
	private WebElement eleSearchNameTxt;
	public WebElement getEleSearchNameTxt(String sSearchTxt)
	{
		 eleSearchNameTxt=driver.findElement(By.xpath("//*[@text='"+sSearchTxt+"']"));
		 return eleSearchNameTxt;
	}
	private WebElement eleExploreChildSearchTxt;
	public WebElement getEleExploreChildSearchTxt(String sExploreChildSearchTxt)
	{
		eleExploreChildSearchTxt=driver.findElement(By.xpath("//*[contains(@text,'"+sExploreChildSearchTxt+"')]"));
		return eleExploreChildSearchTxt;
	}
	@FindBy(xpath="//input[@name='keyWord']")
	private WebElement eleExploreSearchTxtFld;
	public WebElement getEleExploreSearchTxtFld()
	{
		return eleExploreSearchTxtFld;
	}
	@FindBy(xpath="//div[@class='x-component x-button x-button-button-sfmsearch-search x-component-button-sfmsearch-search x-button-no-icon x-stretched sfmsearch-search-button x-haslabel x-layout-box-item x-layout-hbox-item']//span[@class='x-button-label'][text()='Reset filter']")
	//@FindBy(xpath="//span[text()='Reset filter']")
	private WebElement eleResetFilerBtn;
	public WebElement getEleResetFilerBtn()
	{
		return eleResetFilerBtn;
	}
	
	
	@FindBy(xpath="//*[text()='Search']")
	private WebElement eleExploreSearchBtn;
	public WebElement getEleExploreSearchBtn()
	{
		return eleExploreSearchBtn;
	}
	private WebElement eleWorkOrderIDTxt;
	public WebElement getEleWorkOrderIDTxt(String sWorkOrderIDTxt)
	{
		eleWorkOrderIDTxt=driver.findElement(By.xpath("//*[text()='"+sWorkOrderIDTxt+"']"));
		 return eleWorkOrderIDTxt;
	}
	public void selectWorkOrder(String sWOName) throws InterruptedException
	{		
		getEleExploreSearchTxt().click();
		Thread.sleep(3000);
	try {getEleResetFilerBtn().click();Thread.sleep(GenericLib.iLowSleep);}catch(Exception e) {}
		getEleExploreSearchTxt().click();
		getEleExploreSearchTxt().clear();
		
		getEleExploreSearchTxt().sendKeys(sWOName);
		Thread.sleep(3000);
		getEleSearchListItem(sWOName).click();
		Thread.sleep(GenericLib.iLowSleep);
	}
	
	
	@FindBy(xpath="//*[@text='Search Keyword...']")
	private WebElement eleExploreSearchTxt;
	public WebElement getEleExploreSearchTxt()
	{
		return eleExploreSearchTxt;
	}
	
	
	public void commonlookupsearch(String Record) throws InterruptedException
	{
		getEleExploreSearchTxt().click();
		Thread.sleep(3000);			
		getEleExploreSearchTxt().sendKeys(Record);
		getEleSearchListItem(Record).click();

		
	}
	
}
