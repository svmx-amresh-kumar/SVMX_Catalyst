package com.ge.fsa.tests;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.ge.fsa.lib.BaseLib;

public class Temp extends BaseLib{
	public static void main(String[] args) throws IOException, ParseException {
//		String hours="05";
//		int readBytes;
//		System.out.println(String.format("%02d",Integer.parseInt(hours)+1));
//		File file=new File("/usr/local/Cellar/libimobiledevice/");
//		String fileName=Arrays.toString(file.listFiles());
//		System.out.println(fileName);
//		System.out.println(fileName.substring(1,fileName.length()-1));
//		ProcessBuilder pb = new ProcessBuilder(fileName.substring(1,fileName.length()-1)+"/bin/ideviceinfo"); 
//		Process p = pb.start(); 
//		byte[] b = new byte[1024]; 
//		InputStream in = p.getInputStream(); 
//		try { 
//			while ((readBytes = in.read(b)) != -1) { 
//				System.out.println(new String(b, 0, readBytes));
//			} 
//
//		} catch (IOException e) { 
//			e.printStackTrace(); 
//		}
//		Calendar now1=Calendar.getInstance();
//		System.out.println(now1.getTime());
//		now1.set(Calendar.HOUR, 24);
//		now1.set(Calendar.MINUTE, 0);
//		now1.set(Calendar.SECOND, 0);
//		System.out.println(now1.getTime());
//		String sImageName="TestImage_1.jpg";
//		System.out.println(sImageName.substring(0, sImageName.indexOf(".")));
//		WebDriver driver=new ChromeDriver();
//
//		try {
//			System.setProperty("webdriver.chrome.driver","/Users/a212736896/Downloads/chromedriver/");
//			driver.get("https://jenkins.servicemax.com/view/sahi_execution/job/FSA_AUTOMATION/1735/Catalyst_HTML_Report/1735_Sanity_03-07-2019_14-13-36_os_ios_orgyType_Base_orgNAmeSapce_config_automation_build_sfv_%5B19.%2019113%5D_report.html#!");
//			List<WebElement> caseName=driver.findElements(By.xpath("//li[@status='fail']/div[1]/span[1]"));
//			for(WebElement ele : caseName) {
//				System.out.println("<class name=\"com.ge.fsa.tests.tablet."+ele.getText().substring(ele.getText().indexOf(": ")+2)+"\" />");
//			}
//		}
//		finally {
//			driver.quit();
//		}
//		LocalDateTime localDateTime = LocalDateTime.now();
//		ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
//		System.out.println(zonedDateTime.toString());
//		System.out.println(ZonedDateTime.of(localDateTime, ZoneId.of("Europe/London")));
//		Set<String> ids=ZoneId.getAvailableZoneIds();
//		for (String zoneid :  ids) {
//			
//			System.out.println(zoneid);
//		}
//		System.out.println(ids.contains("Europe/London"));
//		Date date=new Date();
//		Locale us=new Locale("en","US");
//		DateFormat usFormat=DateFormat.getDateInstance(DateFormat.SHORT, us);
//		System.out.println(usFormat.format(date));
//		Locale uk=new Locale("en","GB");
//		DateFormat ukFormat=DateFormat.getDateInstance(DateFormat.SHORT, Locale.UK);
//		System.out.println(ukFormat.format(date));
//		SimpleDateFormat dateFormatCN = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
//		System.out.println(dateFormatCN.format(date));
//	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
//	    System.out.println(dateFormat.format(date));
	    
	    //Jul 23 06:24:37 UTC 2019
		int iWhileCnt = 0;
		String sTestCaseID = null;
		String sWorkOrderID = null;
		String sCaseSahiFile = null;
		String sExploreSearch = null;
		String sExploreChildSearchTxt = null;
		String sObjectID = null;
		String sWOObejctApi = null;
		String sWOJsonData = null;
		String sFieldServiceName = null;
		String sProductName = null;
		String sWOName = null;
		String sWOSqlQuery = null;
		String sSheetName = null;
		String sTestID = null;
		String[] sDate = null;
		String sCompletedDateTxt = null;
		String sActualDateTxt = null;
		String sAutoDate = null;
		String sPreviousDate = null;
		String sOnsiteDate = null;
		String sSerialNumber = null;
		String sJsonData = null;
		String sObjectApi = null;
		String sSqlQuery = null;
		int iDay = 0;
		int iMonth = 0;
		int iYear = 0;
		sDate=new java.sql.Date(System.currentTimeMillis()).toString().split("-");
		iYear=Integer.parseInt(sDate[0])+1;
		sAutoDate=sDate[1]+"/"+1+"/"+iYear;
		iDay = Integer.parseInt(sDate[2])-1;
		sPreviousDate = Integer.parseInt(sDate[1])+"/"+iDay+"/"+sDate[0];
		sOnsiteDate=Integer.parseInt(sDate[1])+"/"+Integer.parseInt(sDate[2])+"/"+sDate[0];	
		
		if(Integer.parseInt(sDate[2])>28)
		{
			sDate[2]="1";
			iMonth=Integer.parseInt(sDate[1])+1;
			if(iMonth>12) {
				iMonth=01;
			}
			sDate[1]=""+iMonth;
		}
		sCompletedDateTxt=sDate[0]+"-"+sDate[1]+"-"+sDate[2];//+"T09:00:00.000+0000";
		iDay=Integer.parseInt(sDate[2])+2;
		sDate[2]=""+iDay;
		sActualDateTxt=sDate[0]+"-"+sDate[1]+"-"+sDate[2];//+"T09:00:00.000+0000";
		System.out.println("todays date:"+new java.sql.Date(System.currentTimeMillis()).toString());
		System.out.println("Completed *****"+sCompletedDateTxt);
		System.out.println("Actual *****"+sActualDateTxt);
		System.out.println("sAutoDate *****"+sAutoDate);
		System.out.println("sOnsiteDate *****"+sOnsiteDate);
		System.out.println("sPreviousDate *****"+sPreviousDate);
		SimpleDateFormat format=new SimpleDateFormat("M/d/yyyy");
		SimpleDateFormat restServiceFormat=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		sAutoDate=format.format(cal.getTime());
		cal.setTime(date);
		Date sampledate=cal.getTime();
		sCompletedDateTxt=restServiceFormat.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 2);
		sActualDateTxt=restServiceFormat.format(cal.getTime());
		cal.setTime(date);
		sOnsiteDate=format.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		sPreviousDate=format.format(cal.getTime());
		System.out.println("Completed *****" + sCompletedDateTxt);
		System.out.println("Actual *****" + sActualDateTxt);
		System.out.println("sAutoDate *****" + sAutoDate);
		System.out.println("sOnsiteDate *****" + sOnsiteDate);
		System.out.println("sPreviousDate *****" + sPreviousDate);

		
	}
}


