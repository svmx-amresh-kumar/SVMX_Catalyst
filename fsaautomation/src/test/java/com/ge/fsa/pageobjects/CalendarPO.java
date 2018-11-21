
package com.ge.fsa.pageobjects;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class CalendarPO
{
	public CalendarPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	
	@FindBy(xpath="//div[@class='svmx-menu-icon-label'][text()='Calendar']")
	private WebElement elecalendarClick;
	public WebElement getEleCalendarClick()
	{
		return elecalendarClick;
	}
	
	private WebElement eleProductNameTxt;
	public WebElement getEleProductNameTxt(String sProductName)
	{
		eleProductNameTxt=driver.findElement(By.xpath("//div[@class='x-inner-el'][text()='"+sProductName+"']"));
		return eleProductNameTxt;
	}
	
	private WebElement eleworkordernumonCalendar;
	public WebElement getEleworkordernumonCalendar(String sWorkOrdernumber)
	{
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		eleworkordernumonCalendar=driver.findElement(By.xpath("//div[contains(.,'\" "+ sWorkOrdernumber +" \"')]/div[@class='sfmevent-location-container']"));
		
		return eleworkordernumonCalendar;
	}
	
	private WebElement eleworkordernumonCalendarWeek;
	public WebElement getEleworkordernumonCalendarWeek(String sWorkOrdernumber)
	{
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		eleworkordernumonCalendarWeek=driver.findElement(By.xpath("(//div[@class='sfmevent-day']//div[@class='sfmevent-location-container']//div[contains(text(),'"+sWorkOrdernumber+"')]/../div[contains(@class,'sfmevent-account')])"));
		return eleworkordernumonCalendarWeek;
	}

	@FindBy(xpath="//span[@class='x-button-label'][text()='Week']")
	private WebElement elecalendarWeektap;
	public WebElement getElecalendarWeektap()
	{
		return elecalendarWeektap;
	}
	
	
	private WebElement elegetsubject;
	public WebElement getelegetsubject(String WOname)
	{
		elegetsubject = driver.findElement(By.xpath("//div[contains(text(),'"+WOname+"')]/..//div[@class='sfmevent-account sfmevent-day-subtitle sfmevent-subject-top-border']"));

		return elegetsubject;
	}

	
	@FindBy(xpath="//*[text()='+New']")
	private WebElement eleNewClick;
	public WebElement geteleNewClick()
	{
		return eleNewClick;
	}
	
	@FindBy(xpath="//span[text()='+New']//..//..//div[@class='x-component x-button x-button-no-icon x-button-svmx-default x-component-svmx-default x-haslabel x-layout-box-item x-layout-hbox-item x-stretched']")
	private WebElement eleNewClickdisabled;
	public WebElement geteleNewdisabled()
	{
		return eleNewClickdisabled;
	}
	
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='Day']")
	private WebElement elecalendarDaytap;
	public WebElement getElecalendarDaytap()
	{
		return elecalendarDaytap;
	}
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='Month']")
	private WebElement elecalendarmonthtap;
	public WebElement getElecalendarmonthtap()
	{
		return elecalendarmonthtap;
	}
	
	//String sWorkOrdernumber/////////////////////////////////
	private WebElement elepenciliconcal;
	public WebElement getelepenciliconcal(String WOname)
	{
		elepenciliconcal = driver.findElement(By.xpath("//div[contains(text(),'"+WOname+"')]/..//..//div[@class='sfmevent-icon-edit sfmevent-icon-edit-hidden']"));

		return elepenciliconcal;
	}
	
	
	
	@FindBy(xpath="(//div[@class='sfmevent-day']//div[@class='sfmevent-location-container']//div[contains(text(),'WO-00002486')]/../div[contains(@class,'sfmevent-account')])[1]/../../../../../div[contains(@class,'event-bar')]")
	private WebElement eleWOlabelincalender;
	public WebElement geteleWOlabelincalender()
	{
		return eleWOlabelincalender;
	}
	
	private WebElement eleWOendpoint;
	public WebElement geteleWOendpoint(String hour)
	{
		eleWOendpoint = driver.findElement(By.xpath("(//span[@class ='hour'][contains(text(), '"+hour+"')])[2]"));
		return eleWOendpoint;
	}
	@FindBy(xpath="//div[@class='svmx-menu-icon-label'][text()='Calendar']")
	private WebElement eleCalendarIcn;
	public WebElement getEleCalendarIcn()
	{
		return eleCalendarIcn;
	}
	
	
	private WebElement eleWOEventTitleTxt;
	public WebElement getEleWOEventTitleTxt(String sWOName)
	{
		eleWOEventTitleTxt = driver.findElement(By.xpath("//div[@class='sfmevent-title'][text()='"+sWOName+"']"));
		return eleWOEventTitleTxt;
	}
	
	private WebElement eleWOEventSubjectTxt;
	public WebElement getEleWOEventSubjectTxt(String sWOSubject)
	{
		eleWOEventSubjectTxt = driver.findElement(By.xpath("//div[@class='sfmevent-subject-container sfmevent-day-subject sfmevent-short-duration'][text()='"+sWOSubject+"']"));
		return eleWOEventSubjectTxt;
	}
	
	public void validateeventlocation(String workordername,String startcalDate,String endcalDate,int hrs) throws Exception 
	{
		Thread.sleep(3000);
		startcalDate=startcalDate+":00";
		endcalDate=endcalDate+":00"; 
		
		geteleWOendpoint(startcalDate).getLocation();//To navigate to particular location
		getEleworkordernumonCalendarWeek(workordername).getLocation();//get the WOname location
		Point WorkOrderLocation=getEleworkordernumonCalendarWeek(workordername).getLocation();
		System.out.println("WorkOrderLocation"+WorkOrderLocation);
		
		
		Thread.sleep(2000);
		geteleWOendpoint(startcalDate).getLocation();
		Point caldatenearWO = geteleWOendpoint(startcalDate).getLocation();//get the cal time near to WO
		System.out.println("caldatenearWO"+ " "+caldatenearWO);
		
		int Diffstartcoodinates = WorkOrderLocation.getY() - caldatenearWO.getY();
		System.out.println("Diff of start coodinates"+Diffstartcoodinates);
	
		
		//end location
		Thread.sleep(2000);
		getEleworkordernumonCalendarWeek(workordername);
		Point WorkOrderendLocation=getEleworkordernumonCalendarWeek(workordername).getLocation();
		Thread.sleep(2000);
		geteleWOendpoint(endcalDate).getLocation();
		Point WorkOrderendcalLocation=geteleWOendpoint(endcalDate).getLocation();
		System.out.println("WorkOrderendcalLocation"+WorkOrderendcalLocation);
		Thread.sleep(5000);
		int Diffendcoodinates = WorkOrderendcalLocation.getY() - WorkOrderendLocation.getY();
		System.out.println("Diffofendcoodinates"+Diffendcoodinates);
	
		
	if(Diffstartcoodinates==21 && Diffendcoodinates==(79+(hrs*100)))
		{
			System.out.println("Event  is displayed at right position");
		}
		
	else if(Diffstartcoodinates==21 && Diffendcoodinates==(31+(hrs*100)))
	{
		System.out.println("Event  is displayed at right position");
	}
	else if (Diffstartcoodinates==21 && Diffendcoodinates==(-21))
	{
		System.out.println("Event  is displayed at right position");
	}
		else {
			System.out.println("Event is not displayed in the right position");
			throw new Exception("Event is not displayed in the right position");
		}
	
	}
	//tap on particular day in rhe month view
	private WebElement eletaponmonthday;
	public WebElement geteletaponmonthday(String datetotap)
	{
		eletaponmonthday = driver.findElement(By.xpath("//span[contains(@datetime, '"+datetotap+"')]"));
		return eletaponmonthday;
	}
	
	
	private WebElement eletaponmonthdayindex;
	public WebElement geteletaponmonthdayindex(String datetotap)
	{
		eletaponmonthdayindex = driver.findElement(By.xpath("(//span[contains(@datetime, '"+datetotap+"')])"));
		return eletaponmonthdayindex;
	}
	
	
	
	@FindBy(xpath="(//div[@class ='x-component x-button x-button-svmx-default x-component-svmx-default x-button-no-icon sfmevent-nav-next x-haslabel x-layout-box-item x-layout-hbox-item x-stretched'])[3]")
	private WebElement elenavigatetonextmonthcalender;
	public WebElement getelenavigatetonextmonthcalender()
	{
		return elenavigatetonextmonthcalender;
	}
	
	
	
	/**
	 * Author - Meghana Rao
	 * @param commonsPo - Passing CommonsPO Function
	 * @param workordername - Passing the Work Order number
	 * @throws Exception - Throwing Required Exception
	 */
	public void openWofromCalendar(CommonsPO commonsPo, String workordername) throws Exception 
	{
		commonsPo.tap(getEleCalendarClick());
		Thread.sleep(3000);

		commonsPo.tap(getEleCalendarClick());
		Thread.sleep(3000);
		commonsPo.waitforElement(getEleworkordernumonCalendarWeek(workordername), 300);
		
	
		if(getEleworkordernumonCalendarWeek(workordername) != null){
			System.out.println("Found WO " + workordername);
			commonsPo.tap(getEleworkordernumonCalendarWeek(workordername));
			
			}
				
		else
		{
			System.out.println("Did not Find WO " + workordername);
			throw new Exception("WorkOrder not found on the Calendar");	
		
	}

	}
	
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='Month']")
	private WebElement elecalendarMonthtap;
	public WebElement getElecalendarMonthtap()
	{
		return elecalendarMonthtap;
	}
	
	
	public String convertdatetimetohr( String Datetime) throws Exception 
	{
		SimpleDateFormat parser1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 Date  dTempDate1 = parser1.parse(Datetime);
		 SimpleDateFormat formatter1 = new SimpleDateFormat("HH");
	        String stempDate =  formatter1.format(dTempDate1);
	        System.out.println("Converted to hr "+stempDate); 
		return stempDate;
	
	}
	
	
	public String convertdatetimetoday( String Datetime) throws Exception 
	{
		SimpleDateFormat parser1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 Date  dTempDate1 = parser1.parse(Datetime);
		 SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
	        String stempDate =  formatter1.format(dTempDate1);
	        System.out.println("Converted to date "+stempDate); 
		return stempDate;
	}
	
	
	public void VerifyWOInCalender(CommonsPO commonsPo, String workordername) throws Exception 
	{
	
		Thread.sleep(3000);
		try {
		commonsPo.waitforElement(getEleworkordernumonCalendarWeek(workordername), 300);
		
	
		if(getEleworkordernumonCalendarWeek(workordername) != null){
			System.out.println("Found WO " + workordername);
			
			}
				
		else
		{
			System.out.println("Did not Find WO " + workordername);
			throw new Exception("WorkOrder not found on the Calendar");	
		
	}
	
	}
	
	catch(Exception e){
		System.out.println(e);
		System.out.println("Did not Find WO " + workordername);
		
		
	}
		
		
	}
	
	//create event from calender
	@FindBy(xpath="//*[text()='Subject']/../..//span[@class='x-label-text-el']/../..//input")
	private WebElement elesubjectcal;
	public WebElement getelesubjectcal()
	{
		return elesubjectcal;
	}
	
	@FindBy(xpath="//*[text()='Start Date Time']/../..//span[@class='x-label-text-el']/../..//input")
	private WebElement eleStartDateTimecal;
	public WebElement geteleStartDateTimecal()
	{
		return eleStartDateTimecal;
	}
	@FindBy(xpath="//*[text()='End Date Time']/../..//span[@class='x-label-text-el']/../..//input")
	private WebElement eleEndDateTimecal;
	public WebElement geteleEndDateTimecal()
	{
		return eleEndDateTimecal;
	}
	
	@FindBy(xpath="//*[text()='End Date and Time']/../..//span[@class='x-label-text-el']/../..//input")
	private WebElement eleEndDateTime;
	public WebElement geteleEndDateTime()
	{
		return eleEndDateTime;
	}
	
	
	@FindBy(xpath="//*[text()='Start Date and Time']/../..//span[@class='x-label-text-el']/../..//input")
	private WebElement eleStartDateTime;
	public WebElement geteleStartDateTime()
	{
		return eleStartDateTime;
	}
	
	
	public String convertedformate( String Datetime) throws Exception 
	{
		SimpleDateFormat parser1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 Date  dTempDate1 = parser1.parse(Datetime);
		 SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	        String stempDate =  formatter1.format(dTempDate1);
	        System.out.println("Converted to date "+stempDate); 
		return stempDate;
	}
	
	@FindBy(xpath="//span[@class='x-label-text-el'][contains(text(),'Subject')]/../../div[@class='x-body-el x-widthed']")
	private WebElement elesubjectSFDCtap;
	public WebElement getelesubjectSFDCtap()
	{
		return elesubjectSFDCtap;
	}
///////////////////////////////changes
	@FindBy(xpath="//div[@class='x-body-el x-widthed x-heighted']//div[@class='x-input-body-el']//input")
	private WebElement elesubjectSFDCtextarea;
	public WebElement getelesubjectSFDCtextarea()
	{
		return elesubjectSFDCtextarea;
	}

	@FindBy(xpath="//*[text()='Update']")
	private WebElement eleclickupdate;
	public WebElement geteleclickupdate()
	{
		return eleclickupdate;
	}

	
	@FindBy(xpath="//*[text()='StartDateTime']/../..//span[@class='x-label-text-el']/../..//input")
	private WebElement eleStartDateTimesvmx;
	public WebElement geteleStartDateTimesvmx()
	{
		return eleStartDateTimesvmx;
	}
	
	@FindBy(xpath="//*[text()='EndDateTime']/../..//span[@class='x-label-text-el']/../..//input")
	private WebElement eleEndDateTimesvmx;
	public WebElement geteleEndDateTimesvmx()
	{
		return eleEndDateTimesvmx;
	}
	
	@FindBy(xpath="//span[text()='Create New Event']")
	private WebElement validatecreatenew;
	public WebElement getvalidatecreatenew()
	{
		return validatecreatenew;
	}
	
	@FindBy(xpath="//div[text()='Unable to create event since there is no technician associated.']")
	private WebElement validationmsgonsave;
	public WebElement getvalidationmsgonsave()
	{
		return validationmsgonsave;
	}
	

	private WebElement elegetday;
	public WebElement getelegetday(String WOname)
	{
		//elegetday = driver.findElement(By.xpath("//div[contains(text(),'"+WOname+"')]/..//div[@class='sfmevent-account sfmevent-day-subtitle sfmevent-subject-top-border']"));
		elegetday = driver.findElement(By.xpath("(//div[contains(text(),'Day 1')]/..//div[contains(text(),'"+WOname+"')])"));

		return elegetday;
	}



}



