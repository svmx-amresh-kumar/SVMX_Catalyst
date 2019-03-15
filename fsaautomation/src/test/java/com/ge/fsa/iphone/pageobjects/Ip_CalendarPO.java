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
import io.appium.java_client.MobileBy;
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
	
	@FindAll({@FindBy(xpath="//*[@text='Calendar']"),
	@FindBy(id="Calendar, tab, 1 of 4")})
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
	@FindAll({@FindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[3]/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"March 2019 \"])[4]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther[2]/XCUIElementTypeOther[1]")})
	private WebElement eleCreateNew;
	public WebElement getEleCreateNew()
	{
		return eleCreateNew;
	}

	//revisit
	
//	@FindAll({@FindBy(xpath="//*[@text='Create New Work Order']"),
//		@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Create New\"])[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[15]")})
		private WebElement eleselectprocess;
		public WebElement getEleselectprocess(String sProcessName)
		{
			
			return eleselectprocess = BaseLib.sOSName.equalsIgnoreCase("android")?driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""+sProcessName+"\"))")):driver.findElement(By.xpath("(//XCUIElementTypeOther[@name=\"Create New\"])[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[15]"));
			
		}
		
		@FindAll({@FindBy(xpath="//*[@text='Account Lookup']"),
		@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Account Account Lookup\"])[2]")})
		private WebElement eleAccountLookUp;
		public WebElement getEleAccountLookUp()
		{
			return eleAccountLookUp;
		}
		
		@FindAll({@FindBy(id="Search Account Name, Account Phone"),
		@FindBy(xpath="//*[@text='Search Keyword...']")})
		private WebElement elelookupsearch;
		public WebElement getElelookupsearch()
		{
			return elelookupsearch;
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
		
		@FindAll({@FindBy(xpath="//*[@text='Priority']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
		@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Priority\"]/../XCUIElementTypeOther")})
		private WebElement elePriority;
		public WebElement getElePriority()
		{
			return elePriority;
		}
		

		@FindBy(xpath="//*[@text='Medium']")
		private WebElement eleMedium;
		public WebElement geteleMedium()
		{
			return eleMedium;
		}

		@FindAll({@FindBy(xpath="//*[@class='android.widget.TextView'][@text='auto_account12032019181633']"),
		@FindBy(xpath="//*[@class='android.widget.TextView'][@text='auto_contact 12032019181633']"),
		@FindBy(xpath="//*[@class='android.widget.TextView'][@text='auto_product12032019181633']"),
		@FindBy(xpath="(//XCUIElementTypeOther[@name=\" Clear text RESULTS\"])[3]/XCUIElementTypeOther[2]")})
		private WebElement eleSearchListItem;
		public WebElement getEleSearchListItem()
		{
			return eleSearchListItem;
		}
		
		private WebElement eleContactLookuptap;
		public WebElement getEleContactLookuptap()
		{
			return eleContactLookuptap = driver.findElementByAccessibilityId("Contact Contact Lookup");
		}
		
		private WebElement elelookupsearchcontact;
		public WebElement getElelookupsearchcontact()
		{
			return elelookupsearchcontact = driver.findElementByAccessibilityId("Search Full Name, Business Phone, Mobile Phone, Email");
		}
		
		
		private WebElement eleproductLookuptap;
		public WebElement getEleproductLookuptap()
		{
			return eleproductLookuptap = driver.findElementByAccessibilityId("Product Product Lookup");
		}

		private WebElement elelookupsearhproduct;
		public WebElement getElelookupsearhproduct()
		{
			return elelookupsearhproduct = driver.findElementByAccessibilityId("Search Product Name, Product Code, Product Family, Product Line");
		}	

		
		@FindAll({@FindBy(xpath="//*[@text='Low']"),
		@FindBy(xpath="//XCUIElementTypeOther[@name=\"Low\"]")})
		private WebElement eleCreatenewpriorityLow;
		public WebElement getEleCreatenewpriorityLow()
		{
			return eleCreatenewpriorityLow;
		}
		
		
		@FindAll({@FindBy(xpath="//*[@text='Billing Type']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
		@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Billing Type\"]/../XCUIElementTypeOther")})
		private WebElement elebillingtype;
		public WebElement getElebillingtype()
		{
			return elebillingtype;
		}
		
		@FindAll({@FindBy(xpath="//*[@text='Loan']"),
		@FindBy(xpath="//XCUIElementTypeOther[@name=\"Loan\"]")})
		private WebElement elebillingtypeloan;
		public WebElement getElebillingtypeloan()
		{
			return elebillingtypeloan;
		}
		
		@FindAll({@FindBy(xpath="//*[@text='Proforma Invoice']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
	          @FindBy(xpath="(//XCUIElementTypeOther[@name=\"Proforma Invoice\"])[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField")})		private WebElement eleProformaInvoice;
		public WebElement getEleProformaInvoice()
		{
			return eleProformaInvoice;
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
			
			switch(BaseLib.sOSName.toLowerCase()) {
			case "android":
				eleworkordernumonCalendar=driver.findElement(By.xpath("//*[@text='"+Subject+"']"));
				return eleworkordernumonCalendar;
			case "ios":
				eleworkordernumonCalendar=driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='"+Subject+"'])[6]"));
				return eleworkordernumonCalendar;
			}
			return eleAccountLookUp;
			
			
			
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

}



