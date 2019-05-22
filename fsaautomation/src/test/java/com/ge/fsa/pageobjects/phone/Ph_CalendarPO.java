package com.ge.fsa.pageobjects.phone;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;



public class Ph_CalendarPO
{
	public Ph_CalendarPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
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
	
	
	private WebElement eleAppointmenttime;
	public WebElement getEleAppointmenttime(String Subject)
	{

		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleAppointmenttime=driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"APPOINTMENT_DETAIL.VIEW\"]/android.widget.ScrollView/android.view.ViewGroup/android.widget.TextView[3]"));
			return eleAppointmenttime;
		}else {
			eleAppointmenttime=driver.findElement(By.xpath("(//XCUIElementTypeStaticText[@name=\""+Subject+"\"]/..//..//XCUIElementTypeStaticText)[4]"));
			return eleAppointmenttime;
		}
	
	}
	
	
	
	
	private WebElement eleAppointmentdate;
	public WebElement getEleAppointmentdate(String Subject)
	{

		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleAppointmentdate=driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"APPOINTMENT_DETAIL.VIEW\"]/android.widget.ScrollView/android.view.ViewGroup/android.widget.TextView[2]"));
			return eleAppointmentdate;
		}else {
			eleAppointmentdate=driver.findElement(By.xpath("(//XCUIElementTypeStaticText[@name=\""+Subject+"\"]/..//..//XCUIElementTypeStaticText)[3]"));
			return eleAppointmentdate;
		}
	
	}
	
	


	@FindAll({@FindBy(xpath="//*[@text='Subject*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
		@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Subject*\"])"),
		@FindBy(xpath="//*[@text='Subject']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']")})
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


	private WebElement eleworkordernumonCalendar;
	public WebElement getEleworkordernumonCalendar(String Subject)
	{

		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleworkordernumonCalendar=driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"CALENDAR.APPOINTMENT."+Subject+"\"]/android.view.ViewGroup[2]/android.widget.TextView[1]"));
			//android.view.ViewGroup[@content-desc="CALENDAR.APPOINTMENT.A11859_SFDC_Event1"]/android.view.ViewGroup[2]/android.widget.TextView[1]
			
			return eleworkordernumonCalendar;
		}else {
			eleworkordernumonCalendar=driver.findElement(By.xpath("(//XCUIElementTypeOther[@name=\"CALENDAR.APPOINTMENT."+Subject+"\"])[1]"));
			return eleworkordernumonCalendar;
		}
	}	
		
		
		private WebElement eleworkordernumon;
		public WebElement getEleworkordernumon(String WOname)
		{

			if(BaseLib.sOSName.equalsIgnoreCase("android")) {
				eleworkordernumon=driver.findElement(By.xpath("//*[@text='"+WOname+"']"));
				return eleworkordernumon;
			}else {
				eleworkordernumon=driver.findElement(By.xpath("//XCUIElementTypeOther[@name=\"CALENDAR.APPOINTMENT.VIEW_WORKORDER\"]"));
				return eleworkordernumon;
			}
	}

	@FindBy(xpath="//*[@*='CALENDAR.APPOINTMENT.VIEW_WORKORDER']")
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
		
		
		System.out.println("scroll");
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
			ExtentManager.logger.log(Status.FAIL," Events are not displayed in calendar");
		}
	}


	@FindAll({@FindBy(xpath="//*[@text='StartDateTime*']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.TextView']"),
		@FindBy(xpath="//*[@text='Start Date Time*']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.TextView']"),
	@FindBy(xpath="//XCUIElementTypeStaticText[@name='Start Date Time*']/../XCUIElementTypeOther"),@FindBy(xpath="//XCUIElementTypeStaticText[@name='StartDateTime*']/../XCUIElementTypeOther")})
	private WebElement eleStartDateTimecal;
	public WebElement geteleStartDateTimecal()
	{
		return eleStartDateTimecal;
	}	

	@FindAll({@FindBy(xpath="//*[@text='EndDateTime*']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.TextView']"),
		@FindBy(xpath="//*[@text='End Date Time*']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.TextView']"),
	@FindBy(xpath="//XCUIElementTypeStaticText[@name='End Date Time*']/../XCUIElementTypeOther"),@FindBy(xpath="//XCUIElementTypeStaticText[@name='EndDateTime*']/../XCUIElementTypeOther")})
	private WebElement eleEndDateTimecal;
	public WebElement geteleEndDateTimecal()
	{
		return eleEndDateTimecal;
	}	


	private WebElement eleWOendpoint;
	public WebElement getEleWOendpoint(String hour)
	{
		eleWOendpoint = driver.findElement(By.xpath("//*[@text='"+hour+"']"));
		
		//eleWOendpoint = driver.findElement(By.xpath("//android.support.v4.view.ViewPager[@content-desc=\"CALENDAR.DAY_SCROLLER\"]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]"));
		//android.support.v4.view.ViewPager[@content-desc="CALENDAR.DAY_SCROLLER"]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]
		return eleWOendpoint;
	}

	@FindBy(xpath="//*[@*='APP.TOOLBAR.SYNC_STATUS.BUTTON']")
	private WebElement eleDataSync;
	public WebElement getEleDataSync()
	{
		return eleDataSync;
	}

	
	
	private WebElement eleeventtime;
	public WebElement getEleeventtime(String Subject)
	{

		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleeventtime=driver.findElement(By.xpath("//android.widget.ScrollView[@content-desc=\"EVENT_DETAIL.VIEW\"]/android.view.ViewGroup/android.widget.TextView[3]"));
			return eleeventtime;
		}else {
			eleeventtime=driver.findElement(By.xpath("(//XCUIElementTypeStaticText[@name=\""+Subject+"\"]/..//..//XCUIElementTypeStaticText)[3]"));
			return eleeventtime;
		}
	
	}
	
	
	
	private WebElement eleeventdate;
	public WebElement getEleeventdate(String Subject)
	{

		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleeventdate=driver.findElement(By.xpath("//android.widget.ScrollView[@content-desc=\"EVENT_DETAIL.VIEW\"]/android.view.ViewGroup/android.widget.TextView[2]"));
			return eleeventdate;
		}else {
			eleeventdate=driver.findElement(By.xpath("(//XCUIElementTypeStaticText[@name=\""+Subject+"\"]/..//..//XCUIElementTypeStaticText)[2]"));
			return eleeventdate;
		}
	
	}
	
	
	
	
	private List<WebElement> eleWOEventTitleTxt;
	public List<WebElement> getEleWOEventTitleTxt(){
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElements(By.xpath("//*[@*[contains(., 'CALENDAR.APPOINTMENT')]]//*[@*='android.widget.TextView'][1]"));
		}
		else {
			return driver.findElements(By.xpath("//*[@*[contains(., 'CALENDAR.APPOINTMENT')]]"));
		}
	}
	
	@FindAll({@FindBy(xpath="//*[@*='CALENDAR.DATE_SELECTED']"),
	@FindBy(xpath="//android.view.ViewGroup[@content-desc=\"CALENDAR.DATE_SELECTED\"]/android.view.ViewGroup[2]")})
	private WebElement elecurrentdatedot;
	public WebElement getlecurrentdatedot()
	{
		return elecurrentdatedot;
	}	
	
	private WebElement elegetdot;
	public WebElement getElegetdot(String hour)
	{
		
		elegetdot = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"CALENDAR.DATE_NUMBER"+hour+"\"]/android.view.ViewGroup"));
		
			return elegetdot;
		}
		

	private WebElement eleGetDate;
	public WebElement getGetDate(String sDate)
	{
		
		elegetdot = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"CALENDAR.DATE_NUMBER"+sDate+"\"]"));
		//android.view.ViewGroup[@content-desc="CALENDAR.DATE_NUMBER20"]

			return eleGetDate;
		}
	
	
	private WebElement eleGetDates;
	public WebElement getGetDates()
	{//eleGetDates = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=CALENDAR.DATE_NUMBER"+sDate+"]/android.widget.TextView"));
		eleGetDates = driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"CALENDAR.DATE_NUMBER17\"]/android.widget.TextView"));

	return eleGetDates;
		//android.view.ViewGroup[@content-desc="CALENDAR.DATE_NUMBER13"]/android.view.ViewGroup/android.widget.TextView
		//android.view.ViewGroup[@content-desc="CALENDAR.DATE_NUMBER13"]/android.widget.TextView

	
		}
	
	private WebElement elegetday;
	public WebElement getelegetday(String hour)
	{//android.view.ViewGroup[@content-desc="CALENDAR.DATE_NUMBER26"]/android.widget.TextView

		elegetday = driver.findElement(By.xpath("//android.widget.ScrollView[@content-desc=\"CALENDAR.MONTH_SCROLLER\"]"));
		return elegetday;
	}

	public String convertedformate( String Datetime) throws Exception 
	{
		SimpleDateFormat parser1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 Date  dTempDate1 = parser1.parse(Datetime);
		 SimpleDateFormat formatter1 = new SimpleDateFormat("M/d/yyyy HH:mm");
	        String stempDate =  formatter1.format(dTempDate1);
	        System.out.println("Converted to date "+stempDate); 
		return stempDate;
	}
	
	public String convertedformate( String Datetime,String format1,String format2) throws Exception 
	{
		SimpleDateFormat parser1 = new SimpleDateFormat(format1);
		 Date  dTempDate1 = parser1.parse(Datetime);
		 SimpleDateFormat formatter1 = new SimpleDateFormat(format2);
	        String stempDate =  formatter1.format(dTempDate1);
	        System.out.println("Converted to date "+stempDate); 
		return stempDate;
	}

	//XCUIElementTypeOther[@name="CALENDAR.CARET_DOWN"]
	@FindBy(xpath="//*[@*='CALENDAR.CARET_DOWN']")
	private WebElement eleyearandmonth;
	public WebElement geteleyearandmonth()
	{
		return eleyearandmonth;
	}	
	//*[@*='TAB_BAR.CALENDAR_TAB']
	
	
	@FindBy(xpath="//*[@*='CALENDAR.SELECT_VIEW']")
	private WebElement eleselectview;
	public WebElement getEleselectview()
	{
		return eleselectview;
	}
	
	
	@FindBy(xpath="//*[@text='Agenda View']")
	private WebElement eleAgendaView;
	public WebElement getEleAgendaView()
	{
		return eleAgendaView;
	}
	

	
	
	
	private List<WebElement> eleAgendaViewcomponent;
	public List<WebElement> getEleAgendaViewcomponent(){
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElements(By.xpath("//*[@*[contains(., 'CALENDAR.COMPONENT.AGENDA_VIEW')]]//*[@*='android.view.ViewGroup']//*[@*='android.view.ViewGroup'][2]//*[@*='android.widget.TextView'][1]"));
			//*[@*='android.view.ViewGroup']//*[@*='android.view.ViewGroup'][2]//*[@*='android.widget.TextView'][1]
		}
		else {
			return driver.findElements(By.xpath("//*[@*[contains(., 'CALENDAR.APPOINTMENT')]]"));
		}
	}
	

	@FindBy(xpath="//android.widget.ScrollView[@content-desc=\"CALENDAR.COMPONENT.AGENDA_VIEW\"]/android.view.ViewGroup/android.widget.TextView[1]")
	private WebElement eletopelement;
	public WebElement getEletopelement()
	{
		return eletopelement;
	}


	@FindBy(xpath="//android.widget.TextView[@content-desc=\"multiLineHeaderTitle\"]")
	private WebElement elevalidatecreatenew;
	public WebElement getvalidatecreatenew()
	{
		return elevalidatecreatenew;
	}

	
	
	public void validateeventlocation(String workordername,String startcalDate,String endcalDate,int hrs,CommonUtility commonUtility) throws Exception 
	{
		Thread.sleep(3000);
		
		int minus1 = hrs-1;
		  startcalDate = startcalDate+":00";
		 endcalDate=endcalDate+":00"; 
		 
		
		 
		 custScroll(startcalDate,"down",commonUtility);
		 Thread.sleep(3000);
		 Point startDate=getEleWOendpoint(startcalDate).getLocation();//To navigate to particular location
		 System.out.println("startcalDate"+startcalDate);
		 Point WorkOrderLocation=getEleworkordernumonCalendar(workordername).getLocation();
		System.out.println("WorkOrderLocation"+WorkOrderLocation);
		
		int Diffstartcoodinates = WorkOrderLocation.getY() - startDate.getY();
		System.out.println("Diff of start coodinates"+Diffstartcoodinates);
		
		 custScroll(endcalDate,"up",commonUtility);
		 Thread.sleep(3000);
		Point endDate=getEleWOendpoint(endcalDate).getLocation();
		 System.out.println("endcalDate"+endcalDate);
		 int Diffendcoodinates = endDate.getY() - WorkOrderLocation.getY();
			System.out.println("Diff of end coodinates"+Diffendcoodinates);
	
if(Diffstartcoodinates==26 || Diffendcoodinates==((121*hrs)+(26*minus1)))
{
System.out.println("Event  is displayed at right position");
}
else {
	System.out.println("Event is not displayed in the right position");
	throw new Exception("Event is not displayed in the right position");
}
	
	}

	
	
	
	public void custScroll(String androidTextInElementOrXpath, String direction,CommonUtility commonUtility)
	{
		boolean reTry=false;
		while(!reTry) {
			try {	
				if(getEleWOendpoint(androidTextInElementOrXpath).isDisplayed()) {
					reTry=true;
				}
			}
			catch(Exception e) {
				commonUtility.swipeGeneric(direction);
			}
		}	
		}
	
	
	
		
	
	
	
	
}
