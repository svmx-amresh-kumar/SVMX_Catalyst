
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONArray;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.WorkOrderPO;

public class trial extends BaseLib {

	private static final boolean True = false;
	int iWhileCnt = 0;
	String sTestIBID = null;
	String sObjectIBID =null ;
	//String sObjectIBID = "a0N0t000001BA45EAG";
   // String sIBname="Proforma30082018102823IB" ;
	String sIBname=null ;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sSqlAccQuery=null;
	String sObjectProID=null;
	String sObjectApi = null;
	String sJsonData = null;
	//String sAccountName = "Proforma30082018102823account";
	String sAccountName =null;
	String sFieldServiceName = null;
//String sproductname = "Proforma30082018102823product";
	String sproductname =null;
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	String sIBLastModifiedBy=null;
	
	WebElement productname=null;
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	
	 private static final String DATE_FORMAT = "dd-M-yyyy'T'hh:mm:ss a";
	  @Test
	  public void datetest() throws ParseException {

			
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					int offset = TimeZone.getTimeZone("GMT").getRawOffset();
					System.out.println("OFFSET IS = "+offset);

					sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
					Date Datetest = sdf.parse("2018-09-01T4:48:04.000+4560");
					System.out.println("DATE IS = "+Datetest);
					
					
					
					
					SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
					dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

					//Local time zone   
					SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
					System.out.println("**********"+dateFormatLocal.toLocalizedPattern());

					//Time in GMT
					System.out.println(dateFormatLocal.parse( dateFormatGmt.format(new Date())) );
	  }
		 
	
	@Test(enabled = false)
	 void Trial() throws Exception {
		
		final String DATE_FORMAT = "dd-M-yyyy hh:mm:ss a";


		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        String dateInString = "22-01-2015 10:15:55 AM";
        Date date = formatter.parse(dateInString);
        TimeZone tz = TimeZone.getDefault();

        // From TimeZone Asia/Singapore
        System.out.println("TimeZone : " + tz.getID() + " - " + tz.getDisplayName());
        System.out.println("TimeZone : " + tz);
        System.out.println("Date (Singapore) : " + formatter.format(date));

        // To TimeZone America/New_York
        SimpleDateFormat sdfAmerica = new SimpleDateFormat(DATE_FORMAT);
        TimeZone tzInAmerica = TimeZone.getTimeZone("America/New_York");
        sdfAmerica.setTimeZone(tzInAmerica);

        String sDateInAmerica = sdfAmerica.format(date); // Convert to String first
        Date dateInAmerica = formatter.parse(sDateInAmerica); // Create a new Date object

        System.out.println("\nTimeZone : " + tzInAmerica.getID() + " - " + tzInAmerica.getDisplayName());
        System.out.println("TimeZone : " + tzInAmerica);
        System.out.println("Date (New York) (String) : " + sDateInAmerica);
        System.out.println("Date (New York) (Object) : " + formatter.format(dateInAmerica));

		
		
		
	/*String	 date="2018-09-01T16:37:04.000+4560";
		
			  SimpleDateFormat parser1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			  Date  dTempDate1 = parser1.parse(date);
			  System.out.println("dTempDate1========="+dTempDate1);
			  SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yy hh:mm:ss a");
		       System.out.println("formatter1========="+formatter1);
		     String stempDate =  formatter1.format(dTempDate1);
		        System.out.println("stempDate============   "+stempDate);
		//WorkOrderPO.main(stempDate);
		        
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.ENGLISH);
		System.out.println("formatter============   "+formatter);
		 stempDate =  formatter.format(dTempDate1);  
		System.out.println("stempDate============   "+stempDate);
		String dateInString = stempDate;
		Date date2 = formatter.parse(dateInString);
		System.out.println("date2============   "+date2);*/
			
		       /* DateFormat istFormat = new SimpleDateFormat();
		        DateFormat gmtFormat = new SimpleDateFormat();
		        DateFormat pstFormat = new SimpleDateFormat();
		        
		        TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		        TimeZone istTime = TimeZone.getTimeZone("IST");
		        TimeZone pstTime = TimeZone.getTimeZone("PST");
		        
		        istFormat.setTimeZone(gmtTime);
		        gmtFormat.setTimeZone(istTime);
		        gmtFormat.setTimeZone(pstTime);
		       
		        System.out.println("GMT Time: " + istFormat.format(dTempDate1));
		        System.out.println("IST Time: " + gmtFormat.format(dTempDate1));
		        System.out.println("PST Time: " + pstFormat.format(dTempDate1));*/
		
		       
	}

}
