package com.ge.fsa.iphone.pageobjects;


import static org.testng.Assert.assertFalse;

import java.util.Iterator;

import org.openqa.selenium.By;
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

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ip_CalendarPO
{
	public Ip_CalendarPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	

	@FindBy(id="Calendar, tab, 1 of 4")
	private WebElement eleCalendarBtn;
	public WebElement getEleCalendarBtn()
	{
		return eleCalendarBtn;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"March 2019 \"])[4]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther[1]")
	private WebElement eleCalendarViewMenu;
	public WebElement getEleCalendarViewMenu()
	{
		return eleCalendarViewMenu;
	}

	//to revisit
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"S M T W T F S\"])[2]/XCUIElementTypeOther[3]")
	private WebElement eleCalendarplus;
	public WebElement getEleCalendarplus()
	{
		return eleCalendarplus;
	}

	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Subject*\"])")
	private WebElement eleCalendarEventSubject;
	public WebElement getEleCalendarEventSubject()
	{
		return eleCalendarEventSubject;
	}

	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"All-Day Event No\"])/XCUIElementTypeSwitch")
	private WebElement eleCalendarEventAllDayEvent;
	public WebElement getEleCalendarEventAllDayEvent()
	{
		return eleCalendarEventAllDayEvent;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Description\"])/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextView")
	private WebElement eleCalendarEventDescription;
	public WebElement getEleCalendarEventDescription()
	{
		return eleCalendarEventDescription;
	}
	
	
///////////////////////////////////////////////////////////////////////////////	create new PO
	
	//revisit
	@FindAll({@FindBy(xpath="hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"March 2019 \"])[4]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]")})
	private WebElement eleCreateNew;
	public WebElement getEleCreateNew()
	{
		return eleCreateNew;
	}

	//revisit
	
	@FindAll({@FindBy(xpath="//*[@text='Create New Work Order']"),
		@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Create New\"])[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[15]")})
		private WebElement eleselectprocess;
		public WebElement getEleselectprocess()
		{
			return eleselectprocess;
		}
		
		@FindAll({@FindBy(xpath="//*[@text='Account Lookup']"),
		@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Account Account Lookup\"])[2]")})
		private WebElement eleAccountLookUp;
		public WebElement getEleAccountLookUp()
		{
			return eleAccountLookUp;
		}
		
		
		@FindBy(xpath="//*[@text='Search Keyword...']")
		private WebElement elelookupsearch;
		public WebElement getElelookupsearch()
		{
			try {
				return elelookupsearch = driver.findElementByAccessibilityId("Search Account Name, Account Phone");

			} catch (Exception e) {
				return elelookupsearch = driver.findElement(By.xpath("//*[@text='Search Keyword...']"));
			}
		}
		
		@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Contact Contact Lookup\"])[2]")
		private WebElement eleContactLookUp;
		public WebElement getEleContactLookUp()
		{
			return eleContactLookUp;
		}
		
		private WebElement eleContactlookupsearch;
		public WebElement geteleContactlookupsearch()
		{
			return eleContactlookupsearch = driver.findElementByAccessibilityId("Search Full Name, Business Phone, Mobile Phone, Email");
		}
		
		@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Product Product Lookup\"])[2]")
		private WebElement eleProductLookUp;
		public WebElement getEleProductLookUp()
		{
			return eleProductLookUp;
		}
		
		private WebElement eleProductlookupsearch;
		public WebElement geteleProductlookupsearch()
		{
			return eleProductlookupsearch = driver.findElementByAccessibilityId("Search Product Name, Product Code, Product Family, Product Line");
		}
		
		@FindAll({@FindBy(xpath="//*[@text='--None--'][1]"),
		@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Priority --None--\"])[1]")})
		private WebElement elePriority;
		public WebElement getElePriority()
		{
			return elePriority;
		}
		
	/*//	@FindAll({@FindBy(xpath="//*[@text='--None--'][1]"),
		private WebElement eleMedium;
		public WebElement geteleMedium()
		{
			return eleMedium = driver.findElementByAccessibilityId("Medium");
		}*/
		
		@FindBy(xpath="//*[@text='Medium']")
		private WebElement eleMedium;
		public WebElement geteleMedium()
		{
			return eleMedium;
		}
		
	/*
	 * @FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Work Order\"]") private
	 * WebElement elelookupsearch; public WebElement getElelookupsearch() { return
	 * elelookupsearch; }
	 */
		
		@FindAll({@FindBy(xpath="//*[@class='android.widget.TextView'][@text='05032019204015AccA']"),
		@FindBy(xpath="(//XCUIElementTypeOther[@name=\" Clear text RESULTS\"])[3]/XCUIElementTypeOther[2]")})
		private WebElement eleSearchListItem;
		public WebElement getEleSearchListItem()
		{
			return eleSearchListItem;
		}
		
		
}
