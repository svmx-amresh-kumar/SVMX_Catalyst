
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
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

	@Test(enabled = true)
	public void Trial() throws Exception {
		
	String	 date="2018-09-01T16:37:04.000+0000";
		
			  SimpleDateFormat parser1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			  Date  dTempDate1 = parser1.parse(date);
			  System.out.println("dTempDate1========="+dTempDate1);
			  SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yy hh:mm:ss a");
		       System.out.println("formatter1========="+formatter1);
		     String stempDate =  formatter1.format(dTempDate1);
		        System.out.println("stempDate============   "+stempDate);
		//WorkOrderPO.main(stempDate);
		        
		/*SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.ENGLISH);
		System.out.println("formatter============   "+formatter);
		String stempDate =  formatter.format(dTempDate1);  
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
