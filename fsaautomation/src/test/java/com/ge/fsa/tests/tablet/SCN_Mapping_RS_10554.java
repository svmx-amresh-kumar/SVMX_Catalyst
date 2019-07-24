/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.json.JSONArray;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class SCN_Mapping_RS_10554 extends BaseLib {

	int iWhileCnt = 0;
	String sTestCaseID = null;
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
	String sSheetName =null;
	
	WebElement productname=null;
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	//@Test(retryAnalyzer=Retry.class)
	@Test()
	public void RS_10554() throws Exception {
		// JiraLink
		if (BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10554");
		} else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12086");

		}
		
		sSheetName ="RS_10554";
		String sProformainVoice = commonUtility.generateRandomNumber("AUTO");
		String sTestCaseID="RS-10554_mapping";
		String sID=sProformainVoice+"RS_10554_IB";
	//sahi
		
		/*
		 * commonUtility.executeSahiScript("appium/SCN_Mapping_RS_10554.sah",
		 * "sTestCaseID"); if(commonsUtility.verifySahiExecution()) {
		 * 
		 * System.out.println("PASSED"); } else { System.out.println("FAILED");
		 * 
		 * 
		 * ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID +
		 * "Sahi verification failure"); assertEquals(0, 1); } lauchNewApp("true");
		 * System.out.println("RS_10554");
		 */
		 
		
		//create Account
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sID+""+"account\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
		//sProductName1="v1";
		System.out.println(sAccountName);
		// Create product
		sJsonData = "{\"Name\": \""+sID+""+"product\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectProID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectProID+"\'";				
		sproductname  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sproductname);
		//create IB
		
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\", \"Name\": \""+sID+""+"IB\", \"SVMXC__Serial_Lot_Number__c\": \""+sID+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Date_Installed__c\": \"2018-08-29\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sObjectIBID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sObjectIBID+"\'";				
		sIBname  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sIBname);
		//get last modified date
		sSqlQuery ="SELECT+LastModifiedDate+from+SVMXC__Installed_Product__c+Where+id+=\'"+sObjectIBID+"\'";				
		 sIBLastModifiedBy  =restServices.restGetSoqlValue(sSqlQuery,"LastModifiedDate"); 
		 System.out.println(sIBLastModifiedBy);
		 
		//converting to GMT to PST
			    SimpleDateFormat parser1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			  Date  dTempDate1 = parser1.parse(sIBLastModifiedBy);
		        SimpleDateFormat formatter1 = new SimpleDateFormat("M/d/yyyy HH:mm");
		        String stempDate =  formatter1.format(dTempDate1);
		        System.out.println("formatter1.format value   "+stempDate);
		        dTempDate1 = formatter1.parse(stempDate);
		        //adding 7 hours to set to UTC/GMT time.. this is from PST timezone as 
	        	Instant insDate =dTempDate1.toInstant().minus(7, ChronoUnit.HOURS);
		        System.out.println("7 aded to instant"+insDate); 
		        
		       String sformattedDatetime = formatter1.format(dTempDate1);
		        dTempDate1 = Date.from(insDate);
		        sformattedDatetime = formatter1.format((dTempDate1));  
		        System.out.println("formateed dateTime"+sformattedDatetime);

		
		
		
		
		//read from file
				sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
				sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
				sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
		
		
			//Pre Login to app
			loginHomePo.login(commonUtility, exploreSearchPo);
			//config sync
			//toolsPo.configSync(commonsUtility);
			Thread.sleep(CommonUtility.iMedSleep);
			
			//Data Sync for WO's created
			toolsPo.syncData(commonUtility);
			Thread.sleep(CommonUtility.iMedSleep);
			
			//Navigation to SFM
			workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sIBname, sFieldServiceName);
			
			Thread.sleep(CommonUtility.iHighSleep);
			
			
			//validating mapped values before save
			String fetchedaccount =workOrderPo.getAccountvalue().getAttribute("value");
			System.out.println(fetchedaccount);
			try{Assert.assertTrue(fetchedaccount.equals(sAccountName));ExtentManager.logger.log(Status.PASS,"Account value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Account value mapping Failed ");}
			
			
			String fetchedproduct =workOrderPo.getProductvalue().getAttribute("value");
			System.out.println(fetchedproduct);
			try{Assert.assertTrue(fetchedproduct.equals(sproductname));ExtentManager.logger.log(Status.PASS,"product value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"product value mapping Failed ");}
			
			String fetchedcomponent =workOrderPo.getcomponentvalue().getAttribute("value");
			System.out.println(fetchedcomponent);
			try{Assert.assertTrue(fetchedcomponent.equals(sIBname));ExtentManager.logger.log(Status.PASS,"component value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"component value mapping Failed ");}
			
			
			
			String fetchedScheduledDate =workOrderPo.getScheduledDatevalue().getAttribute("value");
			System.out.println(fetchedScheduledDate);
			try{assertEquals(fetchedScheduledDate,"8/29/2018");ExtentManager.logger.log(Status.PASS,"ScheduledDate value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"ScheduledDate value mapping Failed ");}
			
			String fetchedScheduledDatetime =workOrderPo.getScheduledDatetimevalue().getAttribute("value");
			System.out.println(fetchedScheduledDatetime);
			try{Assert.assertEquals(stempDate,fetchedScheduledDatetime);ExtentManager.logger.log(Status.PASS,"ScheduledDatetime value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"ScheduledDatetime value mapping Failed ");}
			ExtentManager.logger.log(Status.PASS,"Work Order  Mapping is Successful before save");
			
			commonUtility.tap(workOrderPo.openpartsontap());
			//Thread.sleep(GenericLib.iHighSleep);
			
			String fetchedpart =workOrderPo.getElePartLaborLkUp().getAttribute("value");
			System.out.println(fetchedpart);
			try{Assert.assertTrue(fetchedpart.equals(sproductname));ExtentManager.logger.log(Status.PASS,"part value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"part value mapping Failed ");}
			
			String fetcheddaterequired =workOrderPo.getDateRequired().getAttribute("value");
			System.out.println(fetcheddaterequired);
			try{assertEquals(fetcheddaterequired,"8/29/2018");ExtentManager.logger.log(Status.PASS,"date required value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"date required value mapping Failed ");}
			
			commonUtility.tap(workOrderPo.getEleDoneBtn());
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful before save");
			
			commonUtility.tap(workOrderPo.getEleSaveLnk());
			
			toolsPo.syncData(commonUtility);
			Thread.sleep(CommonUtility.i30SecSleep);
			
			// Collecting the Work Order number from the Server.
			String sSoqlQuery = "SELECT+name+from+SVMXC__Service_Order__c+Where+SVMXC__Component__c+=\'"+sObjectIBID+"\'";
			restServices.getAccessToken();
			String sworkOrdername = restServices.restGetSoqlValue(sSoqlQuery,"Name");
			System.out.println(sworkOrdername);
			
			JSONArray sJsonArrayWO = restServices.restGetSoqlJsonArray("Select+SVMXC__Component__c,+SVMXC__Product__c,+SVMXC__Company__c,+SVMXC__Scheduled_Date__c,+SVMXC__Scheduled_Date_Time__c,+SVMXC__Order_Type__c+from+SVMXC__Service_Order__c+where+SVMXC__Service_Order__c.name=\'"+sworkOrdername+"\'");
			String sProductid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Product__c");
			String ProductQuery = "SELECT+Name+from+Product2+where+id=\'"+sProductid+"\'";
			String sSoqlProductName  =restServices.restGetSoqlValue(ProductQuery,"Name"); 
			try{assertEquals(sproductname, sSoqlProductName);ExtentManager.logger.log(Status.PASS,"Product value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Product value mapping Failed ");}
			System.out.println(sSoqlProductName);
			
			String sAccountid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Company__c");
			String AccountQuery = "SELECT+Name+from+Account+where+id=\'"+sAccountid+"\'";
			String soqlAccounyName  =restServices.restGetSoqlValue(AccountQuery,"Name"); 
			try{assertEquals(sAccountName, soqlAccounyName);ExtentManager.logger.log(Status.PASS,"Account value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Account value mapping Failed ");}
			
			
			String scomponentid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Component__c");
			String componentQuery = "SELECT+Name+from+SVMXC__Installed_Product__c+where+id=\'"+scomponentid+"\'";
			String soqlcomponentName  =restServices.restGetSoqlValue(componentQuery,"Name"); 
			try{assertEquals(sIBname, soqlcomponentName);ExtentManager.logger.log(Status.PASS,"Component value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Component value mapping Failed ");}
			
			String sScheduledDate = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Scheduled_Date__c");
			try{assertEquals("2018-08-29", sScheduledDate);ExtentManager.logger.log(Status.PASS,"ScheduledDate value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"ScheduledDate value mapping Failed ");}
			
			String sScheduledDatetime = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Scheduled_Date_Time__c");
			 SimpleDateFormat parser2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			  Date  dTempDate2 = parser2.parse(sScheduledDatetime);
		        SimpleDateFormat formatter2 = new SimpleDateFormat("M/d/yyyy HH:mm");
		        String stempDate1 =  formatter2.format(dTempDate2);
		        System.out.println("formatter1.format value   "+stempDate1);
		        dTempDate1 = formatter2.parse(stempDate1);
		        //adding 7 hours to set to UTC/GMT time.. this is from PST timezone as 
	        	Instant insDate1 =dTempDate1.toInstant().minus(7, ChronoUnit.HOURS);
		        System.out.println("7 aded to instant"+insDate1); 
		        
		       String sformattedDatetime1 = formatter2.format(dTempDate1);
		        dTempDate1 = Date.from(insDate1);
		        sformattedDatetime1 = formatter2.format((dTempDate1));  
		        System.out.println("formateed dateTime"+sformattedDatetime1);
		       
	////////////////////		
		
			try{assertEquals(sformattedDatetime, sformattedDatetime1);ExtentManager.logger.log(Status.PASS,"ScheduledDatetime value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
		ExtentManager.logger.log(Status.FAIL,"ScheduledDatetime value mapping Failed ");}//change it later
				
	
				
			//Collecting the parts from the Server.
			JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Product__c,+SVMXC__Date_Requested__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkOrdername+"\')");
			String spartid = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Product__c");
			String partQuery = "SELECT+Name+from+Product2+where+id=\'"+spartid+"\'";
			String soqlpartName  =restServices.restGetSoqlValue(partQuery,"Name"); 
			try{assertEquals(sproductname, soqlpartName);ExtentManager.logger.log(Status.PASS,"Part value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Part value mapping Failed ");}
				
			String srequesteddateDate = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Date_Requested__c");
			try{assertEquals("2018-08-29", srequesteddateDate);ExtentManager.logger.log(Status.PASS,"Date_Requested value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Date_Requested value mapping Failed ");}
			
			
			//Validating after datasync
			commonUtility.tap(exploreSearchPo.getEleExploreIcn());//sworkOrdername
			workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, "Work Orders",sworkOrdername,"EDIT_WORKORDER_MAPPING");

			 fetchedaccount =workOrderPo.getAccountvalue().getAttribute("value");
			System.out.println(fetchedaccount);
			try{Assert.assertTrue(fetchedaccount.equals(sAccountName));ExtentManager.logger.log(Status.PASS,"Account value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Account value mapping Failed ");}
			
			
			 fetchedproduct =workOrderPo.getProductvalue().getAttribute("value");
			System.out.println(fetchedproduct);
			try{Assert.assertTrue(fetchedproduct.equals(sproductname));ExtentManager.logger.log(Status.PASS,"product value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"product value mapping Failed ");}
			
			 fetchedcomponent =workOrderPo.getcomponentvalue().getAttribute("value");
			System.out.println(fetchedcomponent);
			try{Assert.assertTrue(fetchedcomponent.equals(sIBname));ExtentManager.logger.log(Status.PASS,"component value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"component value mapping Failed ");}
			
			
			 fetchedScheduledDate =workOrderPo.getScheduledDatevalue().getAttribute("value");
			System.out.println(fetchedScheduledDate);
			try{assertEquals(fetchedScheduledDate,"8/29/2018");ExtentManager.logger.log(Status.PASS,"ScheduledDate value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"ScheduledDate value mapping Failed ");}
			
			 fetchedScheduledDatetime =workOrderPo.getScheduledDatetimevalue().getAttribute("value");
			System.out.println(fetchedScheduledDatetime);
			try{Assert.assertEquals(stempDate,fetchedScheduledDatetime);ExtentManager.logger.log(Status.PASS,"ScheduledDatetime value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"ScheduledDatetime value mapping Failed ");}
			ExtentManager.logger.log(Status.PASS,"Work Order  Mapping is Successful After data sync");
			
			
			
			commonUtility.tap(workOrderPo.getpartsontapedit(sproductname));
			Thread.sleep(CommonUtility.iLowSleep);
		
			 fetchedpart =workOrderPo.getElePartLaborLkUp().getAttribute("value");
			System.out.println(fetchedpart);
			try{Assert.assertTrue(fetchedpart.equals(sproductname));ExtentManager.logger.log(Status.PASS,"part value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"part value mapping Failed ");}
			
			 fetcheddaterequired =workOrderPo.getDateRequired().getAttribute("value");
			System.out.println(fetcheddaterequired);
			try{assertEquals(fetcheddaterequired,"8/29/2018");ExtentManager.logger.log(Status.PASS,"date required value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"date required value mapping Failed ");}
			
			commonUtility.tap(workOrderPo.getEleDoneBtn());
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful After Data Sync");
			
			
			ExtentManager.logger.log(Status.PASS,"Installed product to workorder field mapping is successfull(Lookup,date,datetime fields are covered for both header and child)");
			
	
	}

	
	
	@AfterClass(enabled = true)
	public void deletedata() throws Exception {
		//Deleting data created
		
		  restServices.restDeleterecord("Account",sObjectAccID);
		  restServices.restDeleterecord("Product2",sObjectProID);
		  restServices.restDeleterecord("SVMXC__Installed_Product__c",sObjectIBID);
		 
	}
	
}
