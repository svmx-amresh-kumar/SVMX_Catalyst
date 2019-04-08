package com.ge.fsa.pageobjects.phone;


import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;



public class Ph_CalendarPO
{
	public Ph_CalendarPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;

	@FindBy(xpath="//*[@*='TAB_BAR.CALENDAR_TAB']")
	private WebElement eleCalendarBtn;
	public WebElement getEleCalendarBtn()
	{
		return eleCalendarBtn;
	}

	@FindBy(xpath="//*[@*='CALENDAR.SELECT_VIEW']")
	private WebElement eleCalendarViewMenu;
	public WebElement getEleCalendarViewMenu()
	{
		return eleCalendarViewMenu;
	}

	@FindBy(xpath="//*[@*='APP.TOOLBAR.CREATE_NEW.BUTTON']")
	private WebElement eleCreateNewBtn;
	public WebElement getEleCreateNewBtn()
	{
		return eleCreateNewBtn;
	}

	@FindBy(xpath="//*[@*='CALENDAR.ADD.FAB_BUTTON']")
	private WebElement eleCreateNewEvent;
	public WebElement getEleCreateNewEvent()
	{
		return eleCreateNewEvent;
	}


	@FindAll({@FindBy(xpath="//*[@text='Subject*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
		@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Subject*\"])")})
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



	private WebElement eleSelectProcessNewProcess;
	public WebElement getEleSelectProcessNewProcess(String sProcessName) throws InterruptedException
	{				Thread.sleep(1000);

	if(BaseLib.sOSName.equalsIgnoreCase("android")) {
		return eleSelectProcessNewProcess = driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""+sProcessName+"\"))"));

	}else {
		return eleSelectProcessNewProcess =  driver.findElement(By.xpath("//*[contains(@label,'"+sProcessName+"')]/../*[contains(@name,'Item')]"));
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


	private WebElement elelookupsearhproduct;
	public WebElement getElelookupsearhproduct()
	{
		switch (BaseLib.sOSName.toLowerCase()) {

		case "android":
			elelookupsearhproduct = driver.findElement(By.xpath("//*[@text='Search Keyword...']"));
			return elelookupsearhproduct;
		case "ios":
			return elelookupsearhproduct = driver.findElementByAccessibilityId("Search Product Name, Product Code, Product Family, Product Line");
		}
		return elelookupsearhproduct;



	}	


	@FindAll({@FindBy(xpath="//*[@text='Low']"),
		@FindBy(xpath="//*[@label='Low']")})
	private WebElement eleCreatenewpriorityLow;
	public WebElement getEleCreatenewpriorityLow()
	{
		return eleCreatenewpriorityLow;
	}



	private WebElement eleAdd;
	public WebElement getEleAdd()
	{
		try {
			return eleAdd = driver.findElementByAccessibilityId("Add");

		} catch (Exception e) {
			return eleAdd =driver.findElement(By.xpath("//*[@text='Add']"));
		}		}	

	private WebElement eleworkordernumonCalendar;
	public WebElement getEleworkordernumonCalendar(String Subject)
	{

		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleworkordernumonCalendar=driver.findElement(By.xpath("//*[@text='"+Subject+"']"));
			return eleworkordernumonCalendar;
		}else {
			eleworkordernumonCalendar=driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='"+Subject+"'])[6]"));
			return eleworkordernumonCalendar;
		}



	}

	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Work Order\"]")
	private WebElement eleworkordernumontap;
	public WebElement getEleworkordernumontap()
	{
		return eleworkordernumontap;
	}



	public void openWoFromCalendar(String Subject) throws Exception 
	{

		getEleCalendarBtn().click();
		//getEleCalendarBtn().click();
		Thread.sleep(2000);
		try
		{
			getEleworkordernumonCalendar(Subject).click();
			getEleworkordernumontap().click();

		}
		catch(Exception e)
		{
			System.out.println("unable to find the event");
		}

	}


	public void VerifyWOInCalender(CommonUtility commonUtility, String Subject) throws Exception 
	{

		Thread.sleep(3000);
		try {
			commonUtility.waitforElement(getEleworkordernumonCalendar(Subject), 10);
			if(getEleworkordernumonCalendar(Subject) != null){
				System.out.println("Found WO " + Subject);
			}

			else
			{
				System.out.println("Did not Find WO " + Subject);
				throw new Exception("WorkOrder not found on the Calendar");	

			}

		}

		catch(Exception e){
			System.out.println(e);
			System.out.println("Did not Find WO " + Subject);
		}
	}


	@FindBy(xpath="//*[@text='StartDateTime*']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.TextView']")
	private WebElement eleStartDateTimecal;
	public WebElement geteleStartDateTimecal()
	{
		return eleStartDateTimecal;
	}	

	@FindBy(xpath="//*[@text='EndDateTime*']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.TextView']")
	private WebElement eleEndDateTimecal;
	public WebElement geteleEndDateTimecal()
	{
		return eleEndDateTimecal;
	}	


	private WebElement eleWOendpoint;
	public WebElement getEleWOendpoint(String hour)
	{
		eleWOendpoint = driver.findElement(By.xpath("//*[@text='"+hour+"']"));
		return eleWOendpoint;
	}

	@FindBy(xpath="//*[@*='APP.TOOLBAR.SYNC_STATUS.BUTTON']")
	private WebElement eleDataSync;
	public WebElement getEleDataSync()
	{
		return eleDataSync;
	}

	
	
	
	private WebElement elenewprocess;
	public WebElement getElenewprocess(String Process)
	{
		elenewprocess = driver.findElement(By.xpath("//*[@text='"+Process+"']"));
		return elenewprocess;
	}
	
	private List<WebElement> eleWOEventTitleTxt;
	public List<WebElement> getEleWOEventTitleTxt(){
		return driver.findElements(By.xpath("//*[@*[contains(., 'CALENDAR.APPOINTMENT')]]//*[@*='android.widget.TextView'][1]"));
	}
}



