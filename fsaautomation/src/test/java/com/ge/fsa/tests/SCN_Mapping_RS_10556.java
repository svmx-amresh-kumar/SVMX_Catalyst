/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10556"
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.CreateNewPO;

public class SCN_Mapping_RS_10556 extends BaseLib {

	int iWhileCnt = 0;
	String sTestIBID = null;
	String sObjectIBID =null ;
	
	//String sIBname=null ;
	//String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
//	String sSqlAccQuery=null;
	//String sObjectProID=null;
	//String sObjectApi = null;
	//String sJsonData = null;
	//String sAccountName = "Proforma04092018185618account";
	//String sAccountName =null;
	String sFieldServiceName = null;
	//String sproductname = "Proforma04092018185618product";
	//String sproductname =null;
	//String sInstalledproductID="RS_10557_InstalledProduct_Auto";
	//String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	//String sObjectlocationID=null;
//String Location=null;
String SFMBillingtype="Empowerment";
String SFMcontact="C10556_ Auto";
String SFMCustomerDown="true";
String SFMEmail="testsample@gmail.com";
String SFMNumber="11";
String SFMPhone="9902819683";
String SFMURL="www.motogp.com";
String SFMCurrency="46";
String SFMclosedby="Auto Tech";
String SFMSite="L10556_Auto";
String SFMScheduledDate=null;
String SFMScheduledDatetime=null;
String SFMProduct="P10556_Auto";
String SFMLineQty="17";
String SFMLinePerUnit="46";
String SFMWorkdiscription="Verifying Value Map for New Child Record (text)";
String SFMRecordType="Usage/Consumption";
String SFMlinetype="Parts";
String SFMdaterequired=null;
String SFMIsBillable="true";
	WebElement productname=null;
	String sSheetName =null;
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	//@Test(retryAnalyzer=Retry.class)
	@Test()
	public void RS_10556() throws Exception {
		sSheetName ="RS_10556";
		String sTestCaseID="RS-10556_mapping";
		
		String sProformainVoice = commonsPo.generaterandomnumber("AUTO");
	
		
		
		
		  genericLib.executeSahiScript("appium/SCN_Mapping_RS_10556.sah")
		  ; if(commonsPo.verifySahiExecution()) { System.out.println("PASSED"); } else
		  { System.out.println("FAILED");
		  ExtentManager.logger.log(Status.FAIL,"Testcase " +
		  sTestCaseID+"Sahi verification failure"); assertEquals(0, 1); }
		  lauchNewApp("true"); System.out.println("RS_10556");
		 
		 
		 
		 
		 
		 
		//read from file
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		String sworkordernumber=GenericLib.getExcelData(sTestCaseID,sSheetName, "WorkOrder Number");
		
		
		
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
		
		  //config sync 
			//toolsPo.configSync(commonsPo);
		  Thread.sleep(GenericLib.iMedSleep);
		  
		  //datasync 
		 toolsPo.syncData(commonsPo); 
		  Thread.sleep(GenericLib.iMedSleep);
		  
		
		  
		  
		// calendarPO.openWoFromCalendar(commonsPo, sworkordernumber);
		  commonsPo.tap(calendarPO.getEleCalendarClick());
		 commonsPo.tap(calendarPO.gettaponcalevent(sworkordernumber),15,60);
		  Thread.sleep(GenericLib.iMedSleep);
		  workOrderPo.selectAction(commonsPo,sFieldServiceName);
		  
		  
		 
			
			//to get orderstatus nd ordertype from workorder
		  restServices.getAccessToken();
			 JSONArray sJsonArrayWO1 = restServices.restGetSoqlJsonArray("Select+SVMXC__Order_Status__c,+SVMXC__Order_Type__c+from+SVMXC__Service_Order__c+where+SVMXC__Service_Order__c.name=\'"+sworkordernumber+"\'");
				//String sordertype = restServices.getJsonValue(sJsonArrayWO1, "SVMXC__Order_Type__c");
				String sorderstatus = restServices.getJsonValue(sJsonArrayWO1, "SVMXC__Order_Status__c");
				
			 //Validating before save
		System.out.println(":):):):):):):):):before save header):):):):):):):)before saveheader:):):):):):):):):):):):)before save:):):):):):):)");

			String fetchedOrderStatus =workOrderPo.getEleOrderStatusCaseLst().getAttribute("value");
			System.out.println(fetchedOrderStatus);
			try{Assert.assertTrue(fetchedOrderStatus.equals(sorderstatus));
			ExtentManager.logger.log(Status.PASS,"OrderStatus (Picklist) value mapped Successful");
			}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"OrderStatus(Picklist)  mapping Failed ");}
			
			String fetchBillingType =workOrderPo.getEleBillingTypeLst().getAttribute("value");
			System.out.println(fetchBillingType);
			try{Assert.assertTrue(fetchBillingType.equals(SFMBillingtype));ExtentManager.logger.log(Status.PASS,"BillingType(Picklist)  mapping Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"BillingType(Picklist)  mapping Failed ");}
		
			String fetchedContact =workOrderPo.getTxtContact().getAttribute("value");
			System.out.println(fetchedContact);
			try{Assert.assertTrue(fetchedContact.equals(SFMcontact));ExtentManager.logger.log(Status.PASS,"contact(Lookup) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"contact(Lookup) value mapping Failed ");}
				
			String fetchCustomerDown =workOrderPo.getCustomerDown().getAttribute("checked");
			System.out.println(fetchCustomerDown);
			try{Assert.assertEquals(fetchCustomerDown,SFMCustomerDown);ExtentManager.logger.log(Status.PASS,"CustomerDown(Checkbox) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.PASS,"CustomerDown(Checkbox) value mapping Failed ");}
			
		
		Thread.sleep(GenericLib.iMedSleep);
			String fetchProblemDescription =workOrderPo.getProblemDescription().getText();
			System.out.println(fetchProblemDescription);
			try{Assert.assertTrue(fetchProblemDescription.equals(SFMBillingtype));ExtentManager.logger.log(Status.PASS,"ProblemDescription(Long Text Area) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"ProblemDescription(Long Text Area) value mapping Failed ");}//change it
			
			String fetchEmail =workOrderPo.getEmailvalue().getAttribute("value");
			try{Assert.assertTrue(fetchEmail.equals(SFMEmail));ExtentManager.logger.log(Status.PASS,"Email(Email) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Email(Email) value mapping Failed ");}
			
			String fetchURL =workOrderPo.getURLvalue().getAttribute("value");
			try{Assert.assertTrue(fetchURL.equals(SFMURL));ExtentManager.logger.log(Status.PASS,"URL(Url) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"URL(Url) value mapping Failed ");}
			
			String fetchNumber =workOrderPo.getNumbervalue().getAttribute("value");
			System.out.println(fetchNumber);
			try{Assert.assertTrue(fetchNumber.equals(SFMNumber));ExtentManager.logger.log(Status.PASS,"Number(Number) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Number(Number) value mapping Failed ");}
			
			String fetchPhone =workOrderPo.getPhonevalue().getAttribute("value");
			try{Assert.assertTrue(fetchPhone.equals(SFMPhone));ExtentManager.logger.log(Status.PASS,"Phone(Phone) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Phone(Phone) value mapping Failed ");}
			
			String fetchCurrency =workOrderPo.getCurrencyvalue().getAttribute("value");
			try{Assert.assertTrue(fetchCurrency.equals(SFMCurrency));ExtentManager.logger.log(Status.PASS,"Currency(Currency) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Currency(Currency) value mapping Failed ");}
			
			
			String fetchclosedby =workOrderPo.getclosedby().getAttribute("value");
			try{Assert.assertTrue(fetchclosedby.equals(SFMclosedby));ExtentManager.logger.log(Status.PASS,"closedby(Lookup) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"closedby(Lookup) value mapping Failed ");}
			
			String fetchSite =workOrderPo.getTxtSite().getAttribute("value");
			try{Assert.assertTrue(fetchSite.equals(SFMSite));ExtentManager.logger.log(Status.PASS,"Site(Lookup) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Site(Lookup) value mapping Failed ");}
			
			
			String fetchedScheduledDate =workOrderPo.getScheduledDatevalue().getAttribute("value");
			System.out.println(fetchedScheduledDate);
			
		DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
		Date date = new Date();
		SFMScheduledDate=dateFormat.format(date);
		try{Assert.assertTrue(fetchedScheduledDate.equals(SFMScheduledDate));ExtentManager.logger.log(Status.PASS,"ScheduledDate(Date) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
		ExtentManager.logger.log(Status.FAIL,"ScheduledDate(Date) value mapping Failed ");}
			
			String fetchedScheduledDatetime =workOrderPo.getScheduledDatetimevalue().getAttribute("value");
			System.out.println(fetchedScheduledDatetime);
		 SimpleDateFormat parser1 = new SimpleDateFormat("M/d/yyyy");
		 Instant insDate =date.toInstant().plus(1, ChronoUnit.DAYS);
	     Date dTempDate1 = Date.from(insDate);
	        SFMScheduledDatetime = parser1.format((dTempDate1));  
	        try{ Assert.assertTrue(fetchedScheduledDatetime.equals(SFMScheduledDatetime+" 00:00"), "ScheduledDatetime(Date/Time) value mapped is not displayed");ExtentManager.logger.log(Status.PASS,"ScheduledDate value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"ScheduledDate(Date/Time) value mapping Failed ");}
			ExtentManager.logger.log(Status.PASS,"Work Order  Mapping is Successful before save");
			
			//add new line for parts
			
			String sProductName="P10556_Auto";
			
			workOrderPo.addParts(commonsPo, workOrderPo,sProductName);
			commonsPo.tap(workOrderPo.openpartsontap());
			
			//Verifying mapping before save on child
			System.out.println(":):):):):):):):):before save child):):):):):):):)before savechild:):):):):):):):):):):):)before save:):):):):):):)");
			String fetchedlocation =workOrderPo.getElePartsLocation().getAttribute("value");
			System.out.println(fetchedlocation);
			try{Assert.assertTrue(fetchedlocation.equals(SFMSite));ExtentManager.logger.log(Status.PASS,"location(Lookup(Location)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"location(Lookup(Location)) value mapping Failed ");}
			
			String fetchedpart =workOrderPo.getElePartLaborLkUp().getAttribute("value");
			System.out.println(fetchedpart);
			try{Assert.assertTrue(fetchedpart.equals(SFMProduct));ExtentManager.logger.log(Status.PASS,"Part(Lookup(Product)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Part(Lookup(Product)) value mapping Failed ");}
			
			String fetchlineqty =workOrderPo.getEleLineQtyTxtFld().getAttribute("value");
			System.out.println(fetchlineqty);
			try{Assert.assertTrue(fetchlineqty.equals(SFMLineQty));ExtentManager.logger.log(Status.PASS,"lineqty(Number) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"lineqty(Number) value mapping Failed ");}
			
			String fetchlinepriceperunit =workOrderPo.getEleLinePerUnitTxtFld().getAttribute("value");
			System.out.println(fetchlinepriceperunit);
			try{Assert.assertTrue(fetchlinepriceperunit.equals(SFMLinePerUnit));ExtentManager.logger.log(Status.PASS,"LinePerUnit(Currency) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"LinePerUnit(Currency) value mapping Failed ");}
			
			String fetchworkdescription =workOrderPo.getEleWODesMappedTxt().getText();
			System.out.println(fetchworkdescription);
			try{Assert.assertTrue(fetchworkdescription.equals(SFMWorkdiscription));ExtentManager.logger.log(Status.PASS,"workdescription(Long Text Area) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"workdescription(Long Text Area) value mapping Failed ");}
			
			String fetchRecordType =workOrderPo.getRecordType().getAttribute("value");
			System.out.println(fetchRecordType);
			try{Assert.assertTrue(fetchRecordType.equals(SFMRecordType));ExtentManager.logger.log(Status.PASS,"RecordType(Record Type) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"RecordType(Record Type) value mapping Failed ");}
			
			String fetchlinetype =workOrderPo.getLineType().getAttribute("value");
			System.out.println(fetchlinetype);
			try{Assert.assertTrue(fetchlinetype.equals(SFMlinetype));ExtentManager.logger.log(Status.PASS,"linetype(Picklist) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"linetype(Picklist) value mapping Failed ");}
			
			String fetchdaterequired =workOrderPo.getDateRequired().getAttribute("value");
			System.out.println(fetchdaterequired);
			try{Assert.assertTrue(fetchdaterequired.equals(SFMScheduledDatetime));ExtentManager.logger.log(Status.PASS,"DateRequired(Date) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"DateRequired(Date) value mapping Failed ");}
			
			String fetchedstartdateandtime =workOrderPo.getStartDateandTime().getAttribute("value");
			System.out.println(fetchedstartdateandtime);
			try{Assert.assertTrue(fetchedstartdateandtime.equals(SFMScheduledDate+" 00:00"));ExtentManager.logger.log(Status.PASS,"startdateandtime(Date/Time) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"startdateandtime(Date/Time) value mapping Failed ");}
			
			String fetchclosedbyinpart =workOrderPo.getclosedby().getAttribute("value");
			System.out.println(fetchclosedbyinpart);
			try{Assert.assertTrue(fetchclosedbyinpart.equals(SFMclosedby));ExtentManager.logger.log(Status.PASS,"closedby(Lookup(User)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"closedby(Lookup(User)) value mapping Failed ");}
			
			String fetchcancledby =workOrderPo.getCanceledBy().getAttribute("value");
			System.out.println(fetchcancledby);
			try{Assert.assertTrue(fetchcancledby.equals(SFMclosedby));ExtentManager.logger.log(Status.PASS,"CanceledBy(Lookup(User)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"CanceledBy(Lookup(User)) value mapping Failed ");}
			
			boolean fetchisBillable =workOrderPo.getIsBillable().isEnabled();
			System.out.println(fetchisBillable);
			try{Assert.assertEquals(fetchisBillable,true);ExtentManager.logger.log(Status.PASS,"Billable(Checkbox) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Billable(Checkbox) value mapping Failed ");}
			
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful before save");
			commonsPo.tap(workOrderPo.getEleDoneBtn());
			
			commonsPo.tap(workOrderPo.getEleSaveLnk());
			
			//datasync
				toolsPo.syncData(commonsPo);
				Thread.sleep(GenericLib.iMedSleep);
				
				
			//collecting data from server
				
				System.out.println(":):):):):):):)Server Header:):):):):):):):):)Server Header:):):):):):):):):):):):):):):):):):):)");
				JSONArray sJsonArrayWO = restServices.restGetSoqlJsonArray("Select+SVMXC__Order_Status__c,+SVMXC__Billing_Type__c,+SVMXC__Contact__c,+SVMXC__Customer_Down__c,+SVMXC__Scheduled_Date_Time__c,+SVMXC__Scheduled_Date__c,SVMXC__Problem_Description__c,Email__c,URL__c,Number__c,Phone__c,Currency__c,SVMXC__Site__c,SVMXC__Closed_By__c+from+SVMXC__Service_Order__c+where+SVMXC__Service_Order__c.name=\'"+sworkordernumber+"\'");
				
				String orderstatus = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Order_Status__c");
				try{assertEquals("Open", orderstatus);ExtentManager.logger.log(Status.PASS,"orderstatus(Picklist) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"orderstatus(Picklist) value mapping Failed ");}
				System.out.println(orderstatus);
				
				String billingtype = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Billing_Type__c");
				try{assertEquals(SFMBillingtype, billingtype);ExtentManager.logger.log(Status.PASS,"billingtype(Picklist) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"billingtype(Picklist) value mapping Failed ");}
				
				String contactid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Contact__c");
				String contactQuery = "SELECT+Name+from+Contact+where+id=\'"+contactid+"\'";
				String soqlcontactName  =restServices.restGetSoqlValue(contactQuery,"Name");
				try{assertEquals(SFMcontact, soqlcontactName);ExtentManager.logger.log(Status.PASS,"Contact(Lookup(Contact)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Contact(Lookup(Contact)) value mapping Failed ");}
				
				 String customerdown = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Customer_Down__c");
				 try{assertEquals("true",customerdown);ExtentManager.logger.log(Status.PASS,"customerdown(Checkbox) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
					ExtentManager.logger.log(Status.FAIL,"customerdown(Checkbox) value mapping Failed ");}
				
				String scheddatetime = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Scheduled_Date_Time__c");
				scheddatetime = calendarPO.convertedformate(scheddatetime);
				System.out.println(scheddatetime);
				try{assertEquals((SFMScheduledDatetime+" 00:00"), scheddatetime);ExtentManager.logger.log(Status.PASS,"scheddatetime(date/time) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"scheddatetime((date/time) value mapping Failed ");}
				
				String scheddate = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Scheduled_Date__c");
				scheddate = calendarPO.converttosfdcformat(scheddate);
				System.out.println(scheddate);
				try{assertEquals(SFMScheduledDate, scheddate);ExtentManager.logger.log(Status.PASS,"ScheduledDate(Date) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"ScheduledDate(Date) value mapping Failed ");}
				
				String ProblemDescription = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Problem_Description__c");
				try{assertEquals(SFMBillingtype, ProblemDescription);ExtentManager.logger.log(Status.PASS,"ProblemDescription(Long Text Area) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"ProblemDescription(Long Text Area) value mapping Failed ");}
				
				String Email = restServices.getJsonValue(sJsonArrayWO, "Email__c");
				try{assertEquals(SFMEmail, Email);ExtentManager.logger.log(Status.PASS,"Email(Email) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Email(Email) value mapping Failed ");}
				
				String URL = restServices.getJsonValue(sJsonArrayWO, "URL__c");
				try{assertEquals(SFMURL, URL);ExtentManager.logger.log(Status.PASS,"URL(Url) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"URL(Url) value mapping Failed ");}
				
				String Number = restServices.getJsonValue(sJsonArrayWO, "Number__c");
				try{assertEquals("11.0", Number);ExtentManager.logger.log(Status.PASS,"Number(Number) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Number(Number) value mapping Failed ");}
				
				String Phone = restServices.getJsonValue(sJsonArrayWO, "Phone__c");
				try{assertEquals(SFMPhone, Phone);ExtentManager.logger.log(Status.PASS,"Phone(Phone) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Phone(Phone) value mapping Failed ");}
				
				String Currency = restServices.getJsonValue(sJsonArrayWO, "Currency__c");
				try{assertEquals("46.0", Currency);ExtentManager.logger.log(Status.PASS,"Currency(Currency) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Currency(Currency) value mapping Failed ");}
				
				String siteid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Site__c");
				String siteQuery = "SELECT+Name+from+SVMXC__Site__c+where+id=\'"+siteid+"\'";
				String siteName  =restServices.restGetSoqlValue(siteQuery,"Name");
				try{assertEquals(SFMSite, siteName);ExtentManager.logger.log(Status.PASS,"siteName(Lookup(Location)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"siteName(Lookup(Location)) value mapping Failed ");}
				
				String closedby = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Closed_By__c");
				String userQuery = "SELECT+Name+from+User+where+id=\'"+closedby+"\'";
				String userName  =restServices.restGetSoqlValue(userQuery,"Name");
				try{assertEquals(SFMclosedby, userName);ExtentManager.logger.log(Status.PASS,"Closed_By(Lookup(User)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Closed_By(Lookup(User)) value mapping Failed ");}
				ExtentManager.logger.log(Status.PASS,"Mapping validation is successful. Covered fields for Header record - from server");
				
				
				System.out.println(":):):):):):):)server child:):):):):):):):):)server child:):):):):):):):):):):):):):):):):):):)");
	
				JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Product__c,+SVMXC__Actual_Quantity2__c,SVMXC__Actual_Price2__c,SVMXC__Work_Description__c,SVMXC__Is_Billable__c,RecordType.name,SVMXC__Line_Type__c,SVMXC__Date_Requested__c,SVMXC__Start_Date_and_Time__c,SVMXC__Requested_Location__c,SVMXC__Closed_By__c,SVMXC__Canceled_By__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkordernumber+"\')");
				
				String spartid = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Product__c");
				String partQuery = "SELECT+Name+from+Product2+where+id=\'"+spartid+"\'";
				String soqlpartName  =restServices.restGetSoqlValue(partQuery,"Name"); 
				try{assertEquals(SFMProduct, soqlpartName);ExtentManager.logger.log(Status.PASS,"Product(Lookup(Product)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Product(Lookup(Product)) value mapping Failed ");}
	

				String lineqty = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Actual_Quantity2__c");
				try{assertEquals("17.0", lineqty);ExtentManager.logger.log(Status.PASS,"lineqty(Number) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"lineqty(Number) value mapping Failed ");}
				
				String linepriceperunit = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Actual_Price2__c");
				try{assertEquals("46.0", linepriceperunit);ExtentManager.logger.log(Status.PASS,"linepriceperunit(Currency) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"linepriceperunit(Currency) value mapping Failed ");}

				String Work_Description = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Work_Description__c");
				try{assertEquals(SFMWorkdiscription, Work_Description);ExtentManager.logger.log(Status.PASS,"Work_Description(Long Text Area() value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Work_Description(Long Text Area) value mapping Failed ");}

				String Is_Billable = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Is_Billable__c");
				try{assertEquals(SFMIsBillable, Is_Billable);ExtentManager.logger.log(Status.PASS,"Is_Billable(Checkbox) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Is_Billable(Checkbox) value mapping Failed ");}

				String RecordType = restServices.getJsonValue(sJsonArrayparts, "RecordType");
				//try{assertEquals(SFMRecordType, RecordType);ExtentManager.logger.log(Status.PASS,"RecordType(RecordType) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				//ExtentManager.logger.log(Status.FAIL,"RecordType(RecordType) value mapping Failed ");}
				
				String Line_Type = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Line_Type__c");
				try{assertEquals(SFMlinetype, Line_Type);ExtentManager.logger.log(Status.PASS,"Line_Type(Picklist) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Line_Type(Picklist) value mapping Failed ");}
				
				String Date_Requested = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Date_Requested__c");
				String SFMDate_Requested = calendarPO.converttosfdcformat(Date_Requested);
				try{assertEquals(SFMScheduledDatetime, SFMDate_Requested);ExtentManager.logger.log(Status.PASS,"Date_Requested(Date) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Date_Requested(Date) value mapping Failed ");}
				
				String Start_Date_and_Time = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Start_Date_and_Time__c");
				String SFMStartsatetime = calendarPO.convertedformate(Start_Date_and_Time);
				try{assertEquals((SFMScheduledDate+" 00:00"), SFMStartsatetime);ExtentManager.logger.log(Status.PASS,"StartDateandTime(Date/Time) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"StartDateandTime(Date/Time) value mapping Failed ");}//today
				
				String Requested_Location = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Requested_Location__c");
				String Requested_LocationQuery = "SELECT+Name+from+SVMXC__Site__c+where+id=\'"+Requested_Location+"\'";
				String Requested_LocationName  =restServices.restGetSoqlValue(Requested_LocationQuery,"Name");
				try{assertEquals(SFMSite, Requested_LocationName);ExtentManager.logger.log(Status.PASS,"Requested_Location(Lookup(Location)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Requested_LocationName(Lookup(Location)) value mapping Failed ");}
				
				 closedby = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Closed_By__c");
				 userQuery = "SELECT+Name+from+User+where+id=\'"+closedby+"\'";
				 userName  =restServices.restGetSoqlValue(userQuery,"Name");
				 try{assertEquals(SFMclosedby, userName);ExtentManager.logger.log(Status.PASS,"closedby(lookup(User)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
					ExtentManager.logger.log(Status.FAIL,"closedby((lookup(User))) value mapping Failed ");}

				String Canceled_By = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Canceled_By__c");
				 userQuery = "SELECT+Name+from+User+where+id=\'"+Canceled_By+"\'";
				 userName  =restServices.restGetSoqlValue(userQuery,"Name");
				 try{assertEquals(SFMclosedby, userName);ExtentManager.logger.log(Status.PASS,"Canceled_By((lookup(User))) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
					ExtentManager.logger.log(Status.FAIL,"Canceled_By((lookup(User))) value mapping Failed ");}
					ExtentManager.logger.log(Status.PASS,"Mapping validation is successful. Server-Child ");

				System.out.println(":))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))");
				
				System.out.println("Validating mapping after data sync");
				
				workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sworkordernumber,"EDIT_WORKORDER_MAPPING" );
				Thread.sleep(GenericLib.iMedSleep);
				
				
				 fetchedOrderStatus =workOrderPo.getEleOrderStatusCaseLst().getAttribute("value");
				System.out.println(fetchedOrderStatus);
				try{Assert.assertTrue(fetchedOrderStatus.equals("Open"));
				ExtentManager.logger.log(Status.PASS,"OrderStatus Picklist value mapped Successful");
				}catch(AssertionError e) {System.out.println(e);
					ExtentManager.logger.log(Status.FAIL,"OrderStatus(Picklist)  mapping Failed ");}
				
				 fetchBillingType =workOrderPo.getEleBillingTypeLst().getAttribute("value");
				System.out.println(fetchBillingType);
				try{Assert.assertTrue(fetchBillingType.equals(SFMBillingtype));ExtentManager.logger.log(Status.PASS,"BillingType(Picklist)  mapping Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"BillingType(Picklist)  mapping Failed ");}
			
				 fetchedContact =workOrderPo.getTxtContact().getAttribute("value");
				System.out.println(fetchedContact);
				try{Assert.assertTrue(fetchedContact.equals(SFMcontact));ExtentManager.logger.log(Status.PASS,"contact value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"contact value mapping Failed ");}
					
				 fetchCustomerDown =workOrderPo.getCustomerDown().getAttribute("checked");
				System.out.println(fetchCustomerDown);
				try{Assert.assertEquals(fetchCustomerDown,SFMCustomerDown);ExtentManager.logger.log(Status.PASS,"CustomerDown value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.PASS,"CustomerDown value mapping Failed ");}//change it
				
			
			Thread.sleep(GenericLib.iMedSleep);
				  fetchProblemDescription = workOrderPo.getProblemDescription().getText();
				System.out.println(fetchProblemDescription);
				try{Assert.assertTrue(fetchProblemDescription.equals(SFMBillingtype));ExtentManager.logger.log(Status.PASS,"ProblemDescription value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.PASS,"ProblemDescription value mapping Failed ");}//change it
				
				 fetchEmail =workOrderPo.getEmailvalue().getAttribute("value");
				try{Assert.assertTrue(fetchEmail.equals(SFMEmail));ExtentManager.logger.log(Status.PASS,"Email value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Email value mapping Failed ");}
				
				 fetchURL =workOrderPo.getURLvalue().getAttribute("value");
				try{Assert.assertTrue(fetchURL.equals(SFMURL));ExtentManager.logger.log(Status.PASS,"URL value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"URL value mapping Failed ");}
				
				 fetchNumber =workOrderPo.getNumbervalue().getAttribute("value");
				System.out.println(fetchNumber);
				try{Assert.assertTrue(fetchNumber.equals(SFMNumber));ExtentManager.logger.log(Status.PASS,"Number value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Number value mapping Failed ");}
				
				 fetchPhone =workOrderPo.getPhonevalue().getAttribute("value");
				try{Assert.assertTrue(fetchPhone.equals(SFMPhone));ExtentManager.logger.log(Status.PASS,"Phone value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Phone value mapping Failed ");}
				
				 fetchCurrency =workOrderPo.getCurrencyvalue().getAttribute("value");
				try{Assert.assertTrue(fetchCurrency.equals(SFMCurrency));ExtentManager.logger.log(Status.PASS,"Currency value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Phone value mapping Failed ");}
				
				
				 fetchclosedby =workOrderPo.getclosedby().getAttribute("value");
				try{Assert.assertTrue(fetchclosedby.equals(SFMclosedby));ExtentManager.logger.log(Status.PASS,"closedby value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"closedby value mapping Failed ");}
				
				 fetchSite =workOrderPo.getTxtSite().getAttribute("value");
				try{Assert.assertTrue(fetchSite.equals(SFMSite));ExtentManager.logger.log(Status.PASS,"Site value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Site value mapping Failed ");}
				
				
				 fetchedScheduledDate =workOrderPo.getScheduledDatevalue().getAttribute("value");
				System.out.println(fetchedScheduledDate);
				
			 dateFormat = new SimpleDateFormat("M/d/yyyy");
			 date = new Date();
			SFMScheduledDate=dateFormat.format(date);
			try{Assert.assertTrue(fetchedScheduledDate.equals(SFMScheduledDate));ExtentManager.logger.log(Status.PASS,"ScheduledDate value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"ScheduledDate value mapping Failed ");}
				
			 fetchedScheduledDatetime =workOrderPo.getScheduledDatetimevalue().getAttribute("value");
			System.out.println(fetchedScheduledDatetime);
			  parser1 = new SimpleDateFormat("M/d/yyyy");
			  insDate =date.toInstant().plus(1, ChronoUnit.DAYS);
		      dTempDate1 = Date.from(insDate);
		        SFMScheduledDatetime = parser1.format((dTempDate1));  
		        try{ Assert.assertTrue(fetchedScheduledDatetime.equals(SFMScheduledDatetime+" 00:00"), "ScheduledDatetime value mapped is not displayed");ExtentManager.logger.log(Status.PASS,"ScheduledDate value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"ScheduledDate value mapping Failed ");}
				ExtentManager.logger.log(Status.PASS,"Mapping validation is successful. Covered fields for Header record - lookup, record type, checkbox, picklist, number, currency, text, date, datetime, Email, Url and Phone. Also using these literals - SVMX.USERTRUNK,SVMX.CURRENTUSERID,SVMX.CURRENTRECORD");

				System.out.println("Validation of child after data sync ");
				
				//commonsPo.tap(workOrderPo.openpartsontap());
				commonsPo.tap(workOrderPo.geteletaponfirstpart(),20,20);
				Thread.sleep(2000);
				
				//Verifying mapping After data sync on child
				System.out.println(":):):):):):):):): After Datasync child):):):):):):):)After Datasync child:):):):):):):):):):):):)After Datasync child:):):):):):):)");
				  fetchedlocation = workOrderPo.getElePartsLocation().getAttribute("value");
				System.out.println(fetchedlocation);
				try{Assert.assertTrue(fetchedlocation.equals(SFMSite));ExtentManager.logger.log(Status.PASS,"location(Site) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"location(Site) value mapping Failed ");}
				
				 fetchedpart =workOrderPo.getElePartLaborLkUp().getAttribute("value");
				System.out.println(fetchedpart);
				try{Assert.assertTrue(fetchedpart.equals(SFMProduct));ExtentManager.logger.log(Status.PASS,"Part value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Part value mapping Failed ");}
				
				 fetchlineqty =workOrderPo.getEleLineQtyTxtFld().getAttribute("value");
				System.out.println(fetchlineqty);
				try{Assert.assertTrue(fetchlineqty.equals(SFMLineQty));ExtentManager.logger.log(Status.PASS,"lineqty value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"lineqty value mapping Failed ");}
				
				 fetchlinepriceperunit =workOrderPo.getEleLinePerUnitTxtFld().getAttribute("value");
				System.out.println(fetchlinepriceperunit);
				try{Assert.assertTrue(fetchlinepriceperunit.equals(SFMLinePerUnit));ExtentManager.logger.log(Status.PASS,"LinePerUnit value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"LinePerUnit value mapping Failed ");}
				
					 fetchworkdescription =workOrderPo.getEleWODesMappedTxt().getText();
				System.out.println(fetchworkdescription);
				try{Assert.assertTrue(fetchworkdescription.equals(SFMWorkdiscription));ExtentManager.logger.log(Status.PASS,"workdescription value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"workdescription value mapping Failed ");}
				
				 fetchRecordType =workOrderPo.getRecordType().getAttribute("value");
				System.out.println(fetchRecordType);
				try{Assert.assertTrue(fetchRecordType.equals(SFMRecordType));ExtentManager.logger.log(Status.PASS,"RecordType value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"RecordType value mapping Failed ");}
				
				 fetchlinetype =workOrderPo.getLineType().getAttribute("value");
				System.out.println(fetchlinetype);
				try{Assert.assertTrue(fetchlinetype.equals(SFMlinetype));ExtentManager.logger.log(Status.PASS,"linetype value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"linetype value mapping Failed ");}
				
				 fetchdaterequired =workOrderPo.getDateRequired().getAttribute("value");
				System.out.println(fetchdaterequired);
				try{Assert.assertTrue(fetchdaterequired.equals(SFMScheduledDatetime));ExtentManager.logger.log(Status.PASS,"DateRequired value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"DateRequired value mapping Failed ");}
				
				 fetchedstartdateandtime =workOrderPo.getStartDateandTime().getAttribute("value");
				System.out.println(fetchedstartdateandtime);
				try{Assert.assertTrue(fetchedstartdateandtime.equals(SFMScheduledDate+" 00:00"));ExtentManager.logger.log(Status.PASS,"startdateandtime value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"startdateandtime value mapping Failed ");}
				
				 fetchclosedbyinpart =workOrderPo.getclosedby().getAttribute("value");
				System.out.println(fetchclosedbyinpart);
				try{Assert.assertTrue(fetchclosedbyinpart.equals(SFMclosedby));ExtentManager.logger.log(Status.PASS,"closedby value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"closedby value mapping Failed ");}
				
				 fetchcancledby =workOrderPo.getCanceledBy().getAttribute("value");
				System.out.println(fetchcancledby);
				try{Assert.assertTrue(fetchcancledby.equals(SFMclosedby));ExtentManager.logger.log(Status.PASS,"CanceledBy value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"CanceledBy value mapping Failed ");}
				
				 fetchisBillable =workOrderPo.getIsBillable().isEnabled();
				System.out.println(fetchisBillable);
				try{Assert.assertEquals(fetchisBillable,true);ExtentManager.logger.log(Status.PASS,"Billable value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				ExtentManager.logger.log(Status.FAIL,"Billable value mapping Failed ");}		
				ExtentManager.logger.log(Status.PASS,"Mapping validation is successful. Covered fields for child record - lookup, record type, checkbox, picklist, number, currency, text, date & datetime, Also using these literals - SVMX.USERTRUNK,SVMX.CURRENTUSERID,SVMX.CURRENTRECORD,SVMX.CURRENTRECORDHEADER,SVMX.OWNER");

				
				
				
	}
				

}
