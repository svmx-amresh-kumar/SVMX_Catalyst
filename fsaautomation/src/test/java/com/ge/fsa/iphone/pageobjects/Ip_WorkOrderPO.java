package com.ge.fsa.iphone.pageobjects;


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
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.WorkOrderPO;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ip_WorkOrderPO extends BaseLib
{
	public Ip_WorkOrderPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	

	
	@FindBy(xpath="((//XCUIElementTypeOther[contains(@name,\"Work Order\")])/../../../../../XCUIElementTypeOther[2])[4]")
	private WebElement eleActionsLnk;
	public WebElement getEleActionsLnk()
	{
		return eleActionsLnk;
	}
	
	private WebElement eleActionsTxt;
	public WebElement getEleActionsTxt(String sActionsName)
	{
		eleActionsTxt=driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='"+sActionsName+"'])[3]"));
		return eleActionsTxt;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[contains(@name,\"Information\")])/XCUIElementTypeOther[3]/XCUIElementTypeOther")
	private WebElement eleStartDateTimeTxtFld;
	public WebElement getEleStartDateTimeTxtFld()
	{
		return eleStartDateTimeTxtFld;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[contains(@name,\"Information\")])/XCUIElementTypeOther[5]/XCUIElementTypeOther")
	private WebElement eleEndDateTimeTxtFld;
	public WebElement getEleEndDateTimeTxtFld()
	{
		return eleEndDateTimeTxtFld;
	}
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Subject*\"])[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField")
	private WebElement eleSubjectTxtFld;
	public WebElement getEleSubjectTxtFld()
	{
		return eleSubjectTxtFld;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Save\"])[3]")
	private WebElement eleSaveLnk;
	public WebElement getEleSaveLnk()
	{
		return eleSaveLnk;
	}
	@FindBy(xpath="//XCUIElementTypeOther[@name=\"ADD SELECTED\"]")
	private WebElement eleAddSelected;
	public WebElement getEleAddSelected()
	{
		return eleAddSelected;
	}
	
	//XCUIElementTypeOther[@name="ADD SELECTED"]
	public void selectAction( String sActionsName) throws InterruptedException
	{
		Thread.sleep(5000);
		getEleActionsLnk().click();	
		Thread.sleep(5000);
		getEleActionsTxt(sActionsName).click();		
		Thread.sleep(5000);
	}

	public void createNewEvent(CommonsPO commonsPo,String sSubject) throws InterruptedException
	{
		selectAction("Create New Event From Work Order");
		Thread.sleep(3000);
		commonsPo.setDateTime24hrs(getEleStartDateTimeTxtFld(), 0,"05", "00"); //set start time to Today
		commonsPo.setDateTime24hrs(getEleEndDateTimeTxtFld(), 0,"06","00"); //set end time
		getEleSubjectTxtFld().click();
		getEleSubjectTxtFld().sendKeys(sSubject);
		Thread.sleep(2000);
		getEleSaveLnk().click();
		Thread.sleep(5000);
	
	}
	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Add Parts\"]")
	private WebElement elePartLnk;
	public WebElement getElePartLnk()
	{
		return elePartLnk;
	}
	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Add Labor\"]")
	private WebElement eleLaborLnk;
	public WebElement getEleLaborLnk()
	{
		return eleLaborLnk;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"\"])[1]")
	private WebElement elePartcheckbox;
	public WebElement getElePartcheckbox()
	{
		return elePartcheckbox;}
	

	public void addParts(Ip_CalendarPO ip_CalendarPo,String sProductName1) throws InterruptedException 
	{
		getElePartLnk().click();
		Thread.sleep(3000);
		ip_CalendarPo.getElelookupsearhproduct().click();
		Thread.sleep(3000);
		ip_CalendarPo.getElelookupsearhproduct().sendKeys(sProductName1);
		getElePartcheckbox().click();
		Thread.sleep(3000);
		getEleAddSelected().click();
	}

	public void addLaborParts(Ip_CalendarPO ip_CalendarPo,String sProductName1) throws InterruptedException 
	{
		getEleLaborLnk().click();
		Thread.sleep(3000);
		ip_CalendarPo.getElelookupsearhproduct().click();
		Thread.sleep(3000);
		ip_CalendarPo.getElelookupsearhproduct().sendKeys(sProductName1);
		getElePartcheckbox().click();
		Thread.sleep(3000);
		getEleAddSelected().click();
	}
	
	}	

