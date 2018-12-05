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

	@Test(enabled = true)
	public void RS_10556() throws Exception {
		sSheetName ="RS_10556";
		String sTestCaseID="RS-10556_mapping";
		
		String sProformainVoice = commonsPo.generaterandomnumber("AUTO");
	
		
		genericLib.executeSahiScript("appium/SCN_Mapping_RS_10556.sah", "sTestCaseID");
		if(commonsPo.verifySahiExecution()) {
			
			System.out.println("PASSED");
		}
		else 
		{
			System.out.println("FAILED");
			

			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
			assertEquals(0, 1);
		}
		lauchNewApp("true");
		System.out.println("RS_10556");
		
		
		//read from file
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		String sworkordernumber=GenericLib.getExcelData(sTestCaseID,sSheetName, "WorkOrder Number");
		
		
		
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			//config sync
			toolsPo.configSync(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			//datasync
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			//workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sworkordernumber, sFieldServiceName);

			
			//calendarPO.openWoFromCalendar(commonsPo, sworkordernumber);
			commonsPo.tap(calendarPO.getEleCalendarClick());
			commonsPo.tap(calendarPO.gettaponcalevent(sworkordernumber),15,60);
			Thread.sleep(GenericLib.iMedSleep);
			workOrderPo.selectAction(commonsPo,sFieldServiceName);
			
			
			
			//to get orderstatus nd ordertype from workorder
			 JSONArray sJsonArrayWO1 = restServices.restGetSoqlJsonArray("Select+SVMXC__Order_Status__c,+SVMXC__Order_Type__c+from+SVMXC__Service_Order__c+where+SVMXC__Service_Order__c.name=\'"+sworkordernumber+"\'");
				//String sordertype = restServices.getJsonValue(sJsonArrayWO1, "SVMXC__Order_Type__c");
				String sorderstatus = restServices.getJsonValue(sJsonArrayWO1, "SVMXC__Order_Status__c");
				
			 //Validating before save
		System.out.println(":):):):):):):):):before save header):):):):):):):)before saveheader:):):):):):):):):):):):)before save:):):):):):):)");

			String fetchedOrderStatus =workOrderPo.getEleOrderStatusCaseLst().getAttribute("value");
			System.out.println(fetchedOrderStatus);
			Assert.assertTrue(fetchedOrderStatus.equals(sorderstatus), "OrderStatus value mapped is not displayed");
			
			String fetchBillingType =workOrderPo.getEleBillingTypeLst().getAttribute("value");
			System.out.println(fetchBillingType);
			Assert.assertTrue(fetchBillingType.equals(SFMBillingtype), "BillingType value mapped is not displayed");
			
			String fetchedContact =workOrderPo.getTxtContact().getAttribute("value");
			System.out.println(fetchedContact);
			Assert.assertTrue(fetchedContact.equals(SFMcontact), "contact value mapped is not displayed");
				
			String fetchCustomerDown =workOrderPo.getCustomerDown().getAttribute("checked");
			System.out.println(fetchCustomerDown);
			//Assert.assertEquals(fetchCustomerDown,SFMCustomerDown, "CustomerDown value mapped is not displayed");
		
		Thread.sleep(GenericLib.iMedSleep);
			String fetchProblemDescription =workOrderPo.getProblemDescription().getText();
			System.out.println(fetchProblemDescription);
			Assert.assertTrue(fetchProblemDescription.equals(SFMBillingtype), "ProblemDescription value mapped is not displayed");
			
			String fetchEmail =workOrderPo.getEmailvalue().getAttribute("value");
			Assert.assertTrue(fetchEmail.equals(SFMEmail), "Email value mapped is not displayed");
			
			String fetchURL =workOrderPo.getURLvalue().getAttribute("value");
			Assert.assertTrue(fetchURL.equals(SFMURL), "URL value mapped is not displayed");
			
			String fetchNumber =workOrderPo.getNumbervalue().getAttribute("value");
			System.out.println(fetchNumber);
			Assert.assertTrue(fetchNumber.equals(SFMNumber), "number value mapped is not displayed");
			
			String fetchPhone =workOrderPo.getPhonevalue().getAttribute("value");
			Assert.assertTrue(fetchPhone.equals(SFMPhone), "Phone value mapped is not displayed");
			
			String fetchCurrency =workOrderPo.getCurrencyvalue().getAttribute("value");
			Assert.assertTrue(fetchCurrency.equals(SFMCurrency), "Currency value mapped is not displayed");
			
			
			String fetchclosedby =workOrderPo.getclosedby().getAttribute("value");
			Assert.assertTrue(fetchclosedby.equals(SFMclosedby), "closedby value mapped is not displayed");
			
			String fetchSite =workOrderPo.getTxtSite().getAttribute("value");
			Assert.assertTrue(fetchSite.equals(SFMSite), "fetchSite value mapped is not displayed");
			
			
			String fetchedScheduledDate =workOrderPo.getScheduledDatevalue().getAttribute("value");
			System.out.println(fetchedScheduledDate);
			
		DateFormat dateFormat = new SimpleDateFormat("MM/d/yyyy");
		Date date = new Date();
		SFMScheduledDate=dateFormat.format(date);
		Assert.assertTrue(fetchedScheduledDate.equals(SFMScheduledDate), "ScheduledDate value mapped is not displayed");
			
			String fetchedScheduledDatetime =workOrderPo.getScheduledDatetimevalue().getAttribute("value");
			System.out.println(fetchedScheduledDatetime);
		 SimpleDateFormat parser1 = new SimpleDateFormat("MM/d/yyyy");
		 Instant insDate =date.toInstant().plus(1, ChronoUnit.DAYS);
	     Date dTempDate1 = Date.from(insDate);
	        SFMScheduledDatetime = parser1.format((dTempDate1));  
	        Assert.assertTrue(fetchedScheduledDatetime.equals(SFMScheduledDatetime+" 00:00"), "ScheduledDatetime value mapped is not displayed");
			ExtentManager.logger.log(Status.PASS,"Work Order  Mapping is Successful before save");
			
			//add new line for parts
			
			String sProductName="P10556_Auto";
			
			workOrderPo.addParts(commonsPo, workOrderPo,sProductName);
			commonsPo.tap(workOrderPo.openpartsontap());
			
			//Verifying mapping before save on child
			System.out.println(":):):):):):):):):before save child):):):):):):):)before savechild:):):):):):):):):):):):)before save:):):):):):):)");
			String fetchedlocation =workOrderPo.getElePartsLocation().getAttribute("value");
			System.out.println(fetchedlocation);
			Assert.assertTrue(fetchedlocation.equals(SFMSite), "location value mapped is not displayed");
			
			String fetchedpart =workOrderPo.getElePartLaborLkUp().getAttribute("value");
			System.out.println(fetchedpart);
			Assert.assertTrue(fetchedpart.equals(SFMProduct), "part value mapped is not displayed");
			
			String fetchlineqty =workOrderPo.getEleLineQtyTxtFld().getAttribute("value");
			System.out.println(fetchlineqty);
			Assert.assertTrue(fetchlineqty.equals(SFMLineQty), "lineqty value mapped is not displayed");
			
			String fetchlinepriceperunit =workOrderPo.getEleLinePerUnitTxtFld().getAttribute("value");
			System.out.println(fetchlinepriceperunit);
			Assert.assertTrue(fetchlinepriceperunit.equals(SFMLinePerUnit), "LinePerUnit value mapped is not displayed");
			
			String fetchworkdescription =workOrderPo.getEleWODesMappedTxt().getText();
			System.out.println(fetchworkdescription);
			Assert.assertTrue(fetchworkdescription.equals(SFMWorkdiscription), "workdescription value mapped is not displayed");
			
			String fetchRecordType =workOrderPo.getRecordType().getAttribute("value");
			System.out.println(fetchRecordType);
			Assert.assertTrue(fetchRecordType.equals(SFMRecordType), "RecordType value mapped is not displayed");
			
			String fetchlinetype =workOrderPo.getLineType().getAttribute("value");
			System.out.println(fetchlinetype);
			Assert.assertTrue(fetchlinetype.equals(SFMlinetype), "linetype value mapped is not displayed");
			
			String fetchdaterequired =workOrderPo.getDateRequired().getAttribute("value");
			System.out.println(fetchdaterequired);
			Assert.assertTrue(fetchdaterequired.equals(SFMScheduledDatetime), "DateRequired value mapped is not displayed");
			
			String fetchedstartdateandtime =workOrderPo.getStartDateandTime().getAttribute("value");
			System.out.println(fetchedstartdateandtime);
			Assert.assertTrue(fetchedstartdateandtime.equals(SFMScheduledDate+" 00:00"), "startdateandtime required value mapped is not displayed");
			
			String fetchclosedbyinpart =workOrderPo.getclosedby().getAttribute("value");
			System.out.println(fetchclosedbyinpart);
			Assert.assertTrue(fetchclosedbyinpart.equals(SFMclosedby), "closedby required value mapped is not displayed");
			
			String fetchcancledby =workOrderPo.getCanceledBy().getAttribute("value");
			System.out.println(fetchcancledby);
			Assert.assertTrue(fetchcancledby.equals(SFMclosedby), "CanceledBy required value mapped is not displayed");
			
			boolean fetchisBillable =workOrderPo.getIsBillable().isEnabled();
			System.out.println(fetchisBillable);
			Assert.assertEquals(fetchisBillable,true, "Billable value mapped is not displayed");
			
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful before save");
			commonsPo.tap(workOrderPo.getEleDoneBtn());
			
			commonsPo.tap(workOrderPo.getEleSaveLnk());
			
			//datasync
				toolsPo.syncData(commonsPo);
				Thread.sleep(GenericLib.iMedSleep);
				
				
			//collecting data from server
				
				System.out.println(":):):):):):):)Server:):):):):):):):):)Server:):):):):):):):):):):):):):):):):):):)");
				JSONArray sJsonArrayWO = restServices.restGetSoqlJsonArray("Select+SVMXC__Order_Status__c,+SVMXC__Billing_Type__c,+SVMXC__Contact__c,+SVMXC__Customer_Down__c,+SVMXC__Scheduled_Date_Time__c,+SVMXC__Scheduled_Date__c,SVMXC__Problem_Description__c,Email__c,URL__c,Number__c,Phone__c,Currency__c,SVMXC__Site__c,SVMXC__Closed_By__c+from+SVMXC__Service_Order__c+where+SVMXC__Service_Order__c.name=\'"+sworkordernumber+"\'");
				
				String orderstatus = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Order_Status__c");
				assertEquals("Open", orderstatus);
				System.out.println(orderstatus);
				
				String billingtype = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Billing_Type__c");
				assertEquals(SFMBillingtype, billingtype);
				
				String contactid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Contact__c");
				String contactQuery = "SELECT+Name+from+Contact+where+id=\'"+contactid+"\'";
				String soqlcontactName  =restServices.restGetSoqlValue(contactQuery,"Name");
				assertEquals(SFMcontact, soqlcontactName);
				
				 String customerdown = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Customer_Down__c");
				assertEquals("true",customerdown);
				
				String scheddatetime = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Scheduled_Date_Time__c");
				scheddatetime = calendarPO.convertedformate(scheddatetime);
				System.out.println(scheddatetime);
				//assertEquals((SFMScheduledDatetime+" 00:00"), scheddatetime);
				
				String scheddate = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Scheduled_Date__c");
				//scheddate = calendarPO.converttosfdcformat(scheddate);
				System.out.println(scheddate);
				//assertEquals(SFMScheduledDate, scheddate);
				
				String ProblemDescription = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Problem_Description__c");
				assertEquals(SFMBillingtype, ProblemDescription);
				
				String Email = restServices.getJsonValue(sJsonArrayWO, "Email__c");
				assertEquals(SFMEmail, Email);
				
				String URL = restServices.getJsonValue(sJsonArrayWO, "URL__c");
				assertEquals(SFMURL, URL);
				
				String Number = restServices.getJsonValue(sJsonArrayWO, "Number__c");
				assertEquals("11.0", Number);
				
				String Phone = restServices.getJsonValue(sJsonArrayWO, "Phone__c");
				assertEquals(SFMPhone, Phone);
				
				String Currency = restServices.getJsonValue(sJsonArrayWO, "Currency__c");
				assertEquals("46.0", Currency);
				
				String siteid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Site__c");
				String siteQuery = "SELECT+Name+from+SVMXC__Site__c+where+id=\'"+siteid+"\'";
				String siteName  =restServices.restGetSoqlValue(siteQuery,"Name");
				assertEquals(SFMSite, siteName);
				
				String closedby = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Closed_By__c");
				String userQuery = "SELECT+Name+from+User+where+id=\'"+closedby+"\'";
				String userName  =restServices.restGetSoqlValue(userQuery,"Name");
				assertEquals(SFMclosedby, userName);
				System.out.println(":):):):):):):)Client:):):):):):):):):)Client:):):):):):):):):):):):):):):):):):):)");
	
				//////////////////////////
			
				
				JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Product__c,+SVMXC__Actual_Quantity2__c,SVMXC__Actual_Price2__c,SVMXC__Work_Description__c,SVMXC__Is_Billable__c,RecordType.name,SVMXC__Line_Type__c,SVMXC__Date_Requested__c,SVMXC__Start_Date_and_Time__c,SVMXC__Requested_Location__c,SVMXC__Closed_By__c,SVMXC__Canceled_By__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkordernumber+"\')");
				
				String spartid = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Product__c");
				String partQuery = "SELECT+Name+from+Product2+where+id=\'"+spartid+"\'";
				String soqlpartName  =restServices.restGetSoqlValue(partQuery,"Name"); 
				assertEquals(SFMProduct, soqlpartName);
	

				String lineqty = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Actual_Quantity2__c");
				assertEquals("17.0", lineqty);
				
				String linepriceperunit = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Actual_Price2__c");
				assertEquals("46.0", linepriceperunit);

				String Work_Description = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Work_Description__c");
				assertEquals(SFMWorkdiscription, Work_Description);

				String Is_Billable = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Is_Billable__c");
				assertEquals(SFMIsBillable, Is_Billable);

				String RecordType = restServices.getJsonValue(sJsonArrayparts, "RecordType");
				//assertEquals(SFMRecordType, RecordType);
				
				String Line_Type = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Line_Type__c");
				assertEquals(SFMlinetype, Line_Type);
				
				String Date_Requested = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Date_Requested__c");
				//String SFMDate_Requested = calendarPO.converttosfdcformat(Date_Requested);
			//	assertEquals(SFMScheduledDatetime, SFMDate_Requested);
				
				String Start_Date_and_Time = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Start_Date_and_Time__c");
				String SFMStartsatetime = calendarPO.convertedformate(Start_Date_and_Time);
			//	assertEquals((SFMScheduledDate+" 00:00"), SFMStartsatetime);//today
				
				String Requested_Location = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Requested_Location__c");
				String Requested_LocationQuery = "SELECT+Name+from+SVMXC__Site__c+where+id=\'"+Requested_Location+"\'";
				String Requested_LocationName  =restServices.restGetSoqlValue(Requested_LocationQuery,"Name");
				assertEquals(SFMSite, Requested_LocationName);
				
				 closedby = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Closed_By__c");
				 userQuery = "SELECT+Name+from+User+where+id=\'"+closedby+"\'";
				 userName  =restServices.restGetSoqlValue(userQuery,"Name");
				assertEquals(SFMclosedby, userName);

				String Canceled_By = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Canceled_By__c");
				 userQuery = "SELECT+Name+from+User+where+id=\'"+Canceled_By+"\'";
				 userName  =restServices.restGetSoqlValue(userQuery,"Name");
				assertEquals(SFMclosedby, userName);
				
	}
				

}
