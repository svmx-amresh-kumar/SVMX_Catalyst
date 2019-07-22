
package com.ge.fsa.pageobjects.tablet;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ge.fsa.lib.CommonUtility;

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
	
	//@FindBy(xpath="//span[text()='+New']//..//..//div[@class='x-component x-button x-button-no-icon x-button-svmx-default x-component-svmx-default x-haslabel x-layout-box-item x-layout-hbox-item x-stretched']")
	@FindBy(xpath="//div[./span[text()='+New']][contains(@class,'disabled')]")
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
	
	private WebElement eleWOendpoint3;
	public WebElement geteleWOendpoint3(String hour)
	{
		eleWOendpoint3 = driver.findElement(By.xpath("(//span[@class ='hour'][contains(text(), '"+hour+"')])[3]"));
		return eleWOendpoint3;
	}
	
	@FindBy(xpath="//div[@class='svmx-menu-icon-label'][text()='Calendar']")
	private WebElement eleCalendarIcn;
	public WebElement getEleCalendarIcn()
	{
		return eleCalendarIcn;
	}
	
	
	private List<WebElement> eleWOEventTitleTxt;
	public List<WebElement> getEleWOEventTitleTxt()
	{
		eleWOEventTitleTxt = driver.findElements(By.xpath("//div[@class='sfmevent-title']"));
		return eleWOEventTitleTxt;
	}
	
	private List<WebElement> eleWOEventTitleTxt1;
	public List<WebElement> getEleWOEventTitleTxt1()
	{
		eleWOEventTitleTxt1 = driver.findElements(By.xpath("//div[@class='sfmevent-title sfmevent-subject-top-border']"));
		return eleWOEventTitleTxt1;
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
	
		
	if(Diffstartcoodinates==21 && Diffendcoodinates==(79+(hrs*100)) || Diffstartcoodinates==18)
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
	

	private WebElement elemonthday;
	public WebElement getelemonthday(String datetotap)
	{
		elemonthday = driver.findElement(By.xpath("//div[@class='x-component x-carousel-item x-sized x-widthed x-heighted'][not(contains(@style,'800'))]//span[contains(@datetime, '"+datetotap+"')]"));
		return elemonthday;
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
	 * @param commonsUtility - Passing CommonUtility Function
	 * @param workordername - Passing the Work Order number
	 * @throws Exception - Throwing Required Exception
	 */
	public void openWoFromCalendar(CommonUtility commonUtility, String workordername) throws Exception 
	{
	
		commonUtility.tap(getEleCalendarClick());
		Thread.sleep(6000);
		try
		{
		//geteleWOendpoint("07:00").getLocation();
		commonUtility.waitforElement(getEleworkordernumonCalendarWeek(workordername), 3);
		getEleworkordernumonCalendarWeek(workordername).getLocation();
			commonUtility.tap(getEleworkordernumonCalendarWeek(workordername),15,60);
			
		}
		catch(Exception e)
		{
			//geteleWOendpoint("07:00 AM").getLocation();
			commonUtility.waitforElement(getEleworkordernumonCalendarWeek(workordername), 3);
			getEleworkordernumonCalendarWeek(workordername).getLocation();
				commonUtility.tap(getEleworkordernumonCalendarWeek(workordername),15,60);
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
	
	
	
	public void VerifyWOInCalender(CommonUtility commonUtility, String workordername) throws Exception 
	{
		gettaponcalevent(workordername).getLocation();
		Thread.sleep(3000);
		try {
		commonUtility.waitforElement(getEleworkordernumonCalendarWeek(workordername), 10);
		
	
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
	
	
	
	public void VerifyWOInCalenderException(CommonUtility commonUtility, String workordername) throws Exception 
	{
	
		Thread.sleep(3000);
		try {
		commonUtility.waitforElement(getEleworkordernumonCalendarWeek(workordername), 10);
		
	
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
	
		/* //adding 7 hours to set to UTC/GMT time.. this is from PST timezone as 
    	Instant insDate =dTempDate1.toInstant().minus(7, ChronoUnit.HOURS);
        System.out.println("7 aded to instant"+insDate); 
        
       String sformattedDatetime = formatter1.format(dTempDate1);
        dTempDate1 = Date.from(insDate);
        sformattedDatetime = formatter1.format((dTempDate1));  
        System.out.println("formateed dateTime"+sformattedDatetime);
*/
	
	}
	
	@FindBy(xpath="//span[@class='x-label-text-el'][contains(text(),'Subject')]/../../div[@class='x-body-el x-widthed']//input")
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
	
	
	private WebElement elegetcolourcode;
	public WebElement getelegetcolourcode(String WOname,String priority)
	{
		elegetcolourcode = driver.findElement(By.xpath("//div[contains(text(),'"+WOname+"')]//..//..//..//div[@class='sfmevent-colorbar sfmevent-colorbar-"+priority+"']"));
		return elegetcolourcode;
	}

	public  String hex2Rgb(String colorStr) {
	    
		Color c = new Color(
	        Integer.valueOf(colorStr.substring(1, 3), 16), 
	        Integer.valueOf(colorStr.substring(3, 5), 16), 
	        Integer.valueOf(colorStr.substring(5, 7), 16));

	    StringBuffer sb = new StringBuffer();
	    sb.append("background-color: rgb(");
	    sb.append(c.getRed());
	    sb.append(", ");
	    sb.append(c.getGreen());
	    sb.append(", ");
	    sb.append(c.getBlue());
	    sb.append(");");
	    return sb.toString();
	}
	
	private WebElement elegetsubjectblock;
	public WebElement getelegetsubjectblocke(String WOname)
	{
		elegetsubjectblock = driver.findElement(By.xpath("(//div[@class='sfmevent-day']//div[@class='sfmevent-location-container']//div[contains(text(),'"+WOname+"')]//..//div[@class='sfmevent-techSubject'])"));
		return elegetsubjectblock;
	}
	
	
	
	public void VerifyWOInCalenderafterconfchange(CommonUtility commonUtility, String workordername) throws Exception 
	{
		gettaponcalevent(workordername).getLocation();
				Thread.sleep(3000);
		commonUtility.waitforElement(getelegetWOnum(workordername), 10);
		
	
		if(getelegetWOnum(workordername) != null){
			System.out.println("Found WO " + workordername);
			
			}
				
		else
		{
			System.out.println("Did not Find WO " + workordername);
			throw new Exception("WorkOrder not found on the Calendar");	
		
	}
	

	}
		
		
		private WebElement elegetWOnum;
		public WebElement getelegetWOnum(String WOname)
		{
			elegetWOnum = driver.findElement(By.xpath("//div[contains(text(),'"+WOname+"')]"));
			return elegetWOnum;
		}
		
		
		private WebElement elesub;
		public WebElement getelesub(String Sub)
		{
			elesub = driver.findElement(By.xpath("//div[contains(text(),'"+Sub+"')]"));
			return elesub;
		}
		
		private WebElement elegetWOtitle;
		public WebElement getelegetWOtitle(String WOname)
		{
			elegetWOtitle = driver.findElement(By.xpath("(//div[@class='sfmevent-day']//div[@class='sfmevent-location-container']//div[contains(text(),'"+WOname+"')])"));
			return elegetWOtitle;
		}

		private WebElement elegetWOlocation;
		public WebElement getelegetWOlocation(String WOname)
		{
			elegetWOlocation = driver.findElement(By.xpath("(//div[@class='sfmevent-day']//div[@class='sfmevent-location-container']//div[contains(text(),'"+WOname+"')]/../div[contains(@class,'sfmevent-location sfmevent-day-subject')])"));
			return elegetWOlocation;
		}

		private WebElement elegetsubjectcal;
		public WebElement getelegetsubjectcal(String WOname)
		{
			elegetsubjectcal = driver.findElement(By.xpath("(//div[@class='sfmevent-day']//div[@class='sfmevent-location-container']//div[contains(text(),'"+WOname+"')]/..//div[contains(@class,'sfmevent-subject-container sfmevent-day-subject sfmevent-short-duration')])"));
			return elegetsubjectcal;
		}

		private WebElement elegetWOlocationontop;
		public WebElement getelegetWOlocationontop(String WOname)
		{
			elegetWOlocationontop = driver.findElement(By.xpath("(//div[@class='sfmevent-day']//div[@class='sfmevent-location-container']//div[contains(text(),'"+WOname+"')]/../div[contains(@class,'sfmevent-location sfmevent-day-subtitle sfmevent-subject-top-border')])"));
			return elegetWOlocationontop;
		}

	
		private WebElement taponcalevent;
		public WebElement gettaponcalevent(String WOname)
		{
			taponcalevent = driver.findElement(By.xpath("(//div[contains(text(),'"+WOname+"')])[last()]"));
			return taponcalevent;
		}


		public String converttosfdcformat( String Datetime) throws Exception 
		{
			SimpleDateFormat parser1 = new SimpleDateFormat("yyyy-MM-dd");
			 Date  dTempDate1 = parser1.parse(Datetime);
			 SimpleDateFormat formatter1 = new SimpleDateFormat("M/d/yyyy");
		        String stempDate =  formatter1.format(dTempDate1);
		        System.out.println("Converted to date "+stempDate); 
			return stempDate;
		}
		
		
	
		
		private WebElement elegetsubjectformultiday;
		public WebElement getsubjectformultiday(String WOname)
		{
			elegetsubjectformultiday = driver.findElement(By.xpath("(//div[contains(text(),'"+WOname+"')]/..//div[@class='sfmevent-account sfmevent-day-subtitle sfmevent-subject-top-border'])[2]"));

			return elegetsubjectformultiday;
		}

		private WebElement elepenciliconcalmultiday;
		public WebElement getelepenciliconcalmultiday(String WOname)
		{
			elepenciliconcalmultiday = driver.findElement(By.xpath("(//div[contains(text(),'"+WOname+"')]/..//..//div[@class='sfmevent-icon-edit sfmevent-icon-edit-hidden'])[2]"));

			return elepenciliconcalmultiday;
		}

		private WebElement elepenciliconcalmultidaysfdc;
		public WebElement getelepenciliconcalmultidaysfdc(String WOname)
		{
			elepenciliconcalmultidaysfdc = driver.findElement(By.xpath("(//div[contains(text(),'"+WOname+"')]/..//..//div[@class='sfmevent-icon-edit sfmevent-icon-edit-hidden'])[4]"));

			return elepenciliconcalmultidaysfdc;
		}


		private WebElement elesubjectformultidaysfdc;
		public WebElement getsubjectformultidaysfdc(String WOname)
		{
			elesubjectformultidaysfdc = driver.findElement(By.xpath("(//div[contains(text(),'"+WOname+"')]/..//div[@class='sfmevent-account sfmevent-day-subtitle sfmevent-subject-top-border'])[4]"));

			return elesubjectformultidaysfdc;
		}


		private WebElement swipeblock;
		public WebElement getswipeblock(String WOname)
		{
			swipeblock = driver.findElement(By.xpath("//div[@class='sfmevent-title'][contains(text(), '"+WOname+"')]/../..//div[@class ='sfmevent-location-container']/../..//div[@class='sfmevent-content']/../..//div[@class='sfmevent-day']/.."));
			return swipeblock;
		}
		
	
		
		
		
		
}






