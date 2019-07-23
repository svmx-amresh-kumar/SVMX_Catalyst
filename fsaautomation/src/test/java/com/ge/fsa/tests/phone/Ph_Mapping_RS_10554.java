/*
x*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10554"
 */
package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
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
import com.ge.fsa.pageobjects.phone.Ph_ExploreSearchPO;
import com.ge.fsa.pageobjects.phone.Ph_WorkOrderPO;

public class Ph_Mapping_RS_10554 extends BaseLib {

	
	String sObjectIBID =null ;
	
	String sIBname=null ;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sSqlAccQuery=null;
	String sObjectProID=null;
	String sObjectApi = null;
	String sJsonData = null;
	String sAccountName =null;
	String sFieldServiceName = null;
	String sproductname =null;
	String sSqlQuery = null;
	String sIBLastModifiedBy=null;
	String sSheetName =null;
	String assertionMessage="";
	WebElement productname=null;
	Boolean bProcessCheckResult = false;
	String ScheduledDate="29/8/2018";

	//@Test(retryAnalyzer=Retry.class)
	@Test()
	public void RS_10554() throws Exception {
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10554");
		}else {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12086");

		}
		
		sSheetName ="RS_10554";
		String sProformainVoice = commonUtility.generateRandomNumber("AUTO");
		String sTestCaseID="RS-10554_mapping";
		String sID=sProformainVoice+"RS_10554_IB";
	
		
		//read from file
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");

		
		
		//sahi
		 bProcessCheckResult = commonUtility.ProcessCheck(restServices, sFieldServiceName, "SCN_Mapping_RS_10554", sTestCaseID);
		
		
		
		//create Account
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sID+""+"account\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
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
		
		
		 String stempDate = ph_CalendarPo.convertedformate(sIBLastModifiedBy,"yyyy-MM-dd'T'HH:mm:ss","d/M/y HH:mm");
		
			//Pre Login to app
				ph_LoginHomePo.login(commonUtility, ph_MorePo);
			
			//config sync
				ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
			Thread.sleep(CommonUtility.iMedSleep);
			
			//Data Sync for WO's created
			ph_MorePo.syncData(commonUtility);
			Thread.sleep(CommonUtility.iMedSleep);
			
			//Navigation to SFM

			ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  sExploreSearch, sExploreChildSearchTxt, sIBname,sFieldServiceName );	
			Thread.sleep(3000);
		
			
			//validating mapped values before save
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleAccount());
			
			
			String fetchedaccount =ph_WorkOrderPo.getEleAccount().getText();
			System.out.println(fetchedaccount);
			assertionMessage= "Account value mapping before save  Expected = "+sAccountName +" Actual = "+fetchedaccount;
			try{Assert.assertEquals(fetchedaccount,sAccountName);ExtentManager.logger.log(Status.PASS, assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			
			String fetchedproduct =ph_WorkOrderPo.getEleProduct().getText();
			System.out.println(fetchedproduct);
			assertionMessage= "Product value mapping before save  Expected = "+sproductname +" Actual = "+fetchedproduct;
			try{Assert.assertEquals(fetchedproduct,sproductname);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			
			String fetchedcomponent =ph_WorkOrderPo.getEleComponent().getText();
			System.out.println(fetchedcomponent);
			assertionMessage= "Component value mapping before save  Expected = "+sIBname +" Actual = "+fetchedcomponent;
			try{Assert.assertEquals(fetchedcomponent,sIBname);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			
			String fetchedScheduledDate =ph_WorkOrderPo.getEleScheduledDate().getText().trim();
			System.out.println(fetchedScheduledDate);
			assertionMessage= "Scheduled Date value mapping before save  Expected = "+fetchedScheduledDate +" Actual = "+ScheduledDate;// Hard coded as expected in the test case
			try{assertEquals(fetchedScheduledDate,ScheduledDate);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			String fetchedScheduledDatetime =ph_WorkOrderPo.getEleScheduledDateTime().getText();
			System.out.println(fetchedScheduledDatetime);
			assertionMessage= "Scheduled Datetime value mapping before save  Expected = "+stempDate +" Actual = "+fetchedScheduledDatetime;
			try{Assert.assertEquals(stempDate,fetchedScheduledDatetime);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			ExtentManager.logger.log(Status.PASS,"Work Order  Mapping is Successful before save");
			
				
			commonUtility.gotToTabHorizontal("PARTS");
			Thread.sleep(3000);
			ph_WorkOrderPo.getEletapon(sproductname).click();
			Thread.sleep(3000);
			
			String fetchedpart =ph_WorkOrderPo.getPart().getText();
			System.out.println(fetchedpart);
			assertionMessage= "Part value mapping before save  Expected = "+fetchedpart +" Actual = "+sproductname;
			try{Assert.assertTrue(fetchedpart.equals(sproductname));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			String fetcheddaterequired =ph_WorkOrderPo.getleDateRequired().getText().trim();
			System.out.println(fetcheddaterequired);
			assertionMessage= "date required value mapping before save  Expected = "+fetcheddaterequired +"And Actual = "+ScheduledDate;
			try{assertEquals(fetcheddaterequired,ScheduledDate);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			
			ph_WorkOrderPo.geteleXsymbol().click();
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful before save");
			
			ph_WorkOrderPo.getElesave().click();
			Thread.sleep(3000);
			ph_MorePo.syncData(commonUtility);
			Thread.sleep(10000);
			
			// Collecting the Work Order number from the Server.
			String sSoqlQuery = "SELECT+name+from+SVMXC__Service_Order__c+Where+SVMXC__Component__c+=\'"+sObjectIBID+"\'";
			restServices.getAccessToken();
			String sworkOrdername = restServices.restGetSoqlValue(sSoqlQuery,"Name");
			System.out.println(sworkOrdername);
			
			JSONArray sJsonArrayWO = restServices.restGetSoqlJsonArray("Select+SVMXC__Component__c,+SVMXC__Product__c,+SVMXC__Company__c,+SVMXC__Scheduled_Date__c,+SVMXC__Scheduled_Date_Time__c,+SVMXC__Order_Type__c+from+SVMXC__Service_Order__c+where+SVMXC__Service_Order__c.name=\'"+sworkOrdername+"\'");
			String sProductid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Product__c");
			String ProductQuery = "SELECT+Name+from+Product2+where+id=\'"+sProductid+"\'";
			String sSoqlProductName  =restServices.restGetSoqlValue(ProductQuery,"Name"); 
			assertionMessage= "Product value mapping from the Server  Expected = "+sproductname +" Actual = "+sSoqlProductName;
			try{assertEquals(sproductname, sSoqlProductName);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			System.out.println(sSoqlProductName);
			
			String sAccountid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Company__c");
			String AccountQuery = "SELECT+Name+from+Account+where+id=\'"+sAccountid+"\'";
			String soqlAccounyName  =restServices.restGetSoqlValue(AccountQuery,"Name"); 
			assertionMessage= "Account value mapping from the Server  Expected = "+sAccountName +" Actual = "+soqlAccounyName;
			try{assertEquals(sAccountName, soqlAccounyName);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			
			String scomponentid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Component__c");
			String componentQuery = "SELECT+Name+from+SVMXC__Installed_Product__c+where+id=\'"+scomponentid+"\'";
			String soqlcomponentName  =restServices.restGetSoqlValue(componentQuery,"Name"); 
			assertionMessage= "Component value mapping from the Server  Expected = "+sIBname +" Actual = "+soqlcomponentName;
			try{assertEquals(sIBname, soqlcomponentName);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
		
			
			String sScheduledDate = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Scheduled_Date__c");
			String convertedScheduledDate = ph_CalendarPo.convertedformate(sScheduledDate,"yyyy-MM-dd","d/M/yyyy");
			assertionMessage= "Scheduled Date value mapping from the Server   Expected =" +convertedScheduledDate+ "Actual = "+ScheduledDate;
			try{assertEquals(convertedScheduledDate, ScheduledDate);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			String sScheduledDatetime = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Scheduled_Date_Time__c");
		
			 String stempDate1 = ph_CalendarPo.convertedformate(sScheduledDatetime,"yyyy-MM-dd'T'HH:mm:ss","d/M/y HH:mm");
			SimpleDateFormat formatter2 = new SimpleDateFormat("d/M/y HH:mm");
			Date dTempDate = formatter2.parse(stempDate1);
	        //adding 7 hours to set to UTC/GMT time.. this is from PST timezone as 
        	Instant insDate1 =dTempDate.toInstant().minus(7, ChronoUnit.HOURS);
	        System.out.println("7 aded to instant"+insDate1); 
	        
	       String sformattedDatetime = formatter2.format(dTempDate);
	        dTempDate = Date.from(insDate1);
	        sformattedDatetime = formatter2.format((dTempDate));  
	        System.out.println("formateed dateTime"+sformattedDatetime);
			
		       assertionMessage= "Scheduled Datetime value mapping from the Server  Expected = "+sformattedDatetime +" Actual = "+sformattedDatetime;
			try{assertEquals(sformattedDatetime, sformattedDatetime);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
				
	
				
			//Collecting the parts from the Server.
			JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Product__c,+SVMXC__Date_Requested__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkOrdername+"\')");
			String spartid = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Product__c");
			String partQuery = "SELECT+Name+from+Product2+where+id=\'"+spartid+"\'";
			String soqlpartName  =restServices.restGetSoqlValue(partQuery,"Name"); 
			assertionMessage= "part value mapping from the Server  Expected = "+sproductname +" Actual = "+soqlpartName;
			try{assertEquals(sproductname, soqlpartName);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
				
			String srequesteddateDate = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Date_Requested__c");
			 convertedScheduledDate = ph_CalendarPo.convertedformate(srequesteddateDate,"yyyy-MM-dd","d/M/yyyy");
			assertionMessage= "Date Requested value mapping from the Server  Expected = "+convertedScheduledDate+" Actual = "+ScheduledDate;
			try{assertEquals(ScheduledDate, convertedScheduledDate);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			
			//Validating after datasync
			ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  sExploreSearch, "Work Orders", sworkOrdername,"EDIT_WORKORDER_MAPPING" );	
////////////////////////////////////////////////////////////////////////////////////////////////
			//ph_WorkOrderPo.getEleselectprocess("EDIT_WORKORDER_MAPPING").click();
		
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleAccount());
			 fetchedaccount = ph_WorkOrderPo.getEleAccount().getText();
			System.out.println(fetchedaccount);
			assertionMessage= "Account value mapping after datasync Expected = "+fetchedaccount +" Actual = "+sAccountName;
			try{Assert.assertTrue(fetchedaccount.equals(sAccountName));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleProduct());
			  fetchedproduct = ph_WorkOrderPo.getEleProduct().getText();
			System.out.println(fetchedproduct);
			assertionMessage= "Product value mapping after datasync Expected = "+fetchedproduct +" Actual = "+sproductname;
			try{Assert.assertTrue(fetchedproduct.equals(sproductname));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleComponent());
			  fetchedcomponent = ph_WorkOrderPo.getEleComponent().getText();
			System.out.println(fetchedcomponent);
			assertionMessage= "Component value mapping after datasync Expected = "+fetchedcomponent +" Actual = "+sIBname;
			try{Assert.assertTrue(fetchedcomponent.equals(sIBname));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			

			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleScheduledDate());
			  fetchedScheduledDate = ph_WorkOrderPo.getEleScheduledDate().getText().trim();
			System.out.println(fetchedScheduledDate);
			assertionMessage= "Scheduled Date value mapping after datasync Expected = "+fetchedScheduledDate +" Actual = "+ScheduledDate;
			try{assertEquals(fetchedScheduledDate,ScheduledDate);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleScheduledDateTime());
			  fetchedScheduledDatetime = ph_WorkOrderPo.getEleScheduledDateTime().getText();
			System.out.println(fetchedScheduledDatetime);
			assertionMessage= "Scheduled Datetime value mapping after datasync Expected = "+stempDate +" Actual = "+fetchedScheduledDatetime;
			try{Assert.assertEquals(stempDate,fetchedScheduledDatetime);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			ExtentManager.logger.log(Status.PASS,"Work Order  Mapping is Successful After data sync");
			
			
			commonUtility.gotToTabHorizontal("PARTS");
			
			ph_WorkOrderPo.getEletapon(sproductname).click();
			//Thread.sleep(GenericLib.iHighSleep);
			
			commonUtility.custScrollToElement(ph_WorkOrderPo.getPart());
			  fetchedpart = ph_WorkOrderPo.getPart().getText();
			System.out.println(fetchedpart);
			assertionMessage= "part value mapping after datasync Expected = "+fetchedpart +" Actual = "+sproductname;
			try{Assert.assertTrue(fetchedpart.equals(sproductname));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
		
		
	
			commonUtility.custScrollToElement(ph_WorkOrderPo.getleDateRequired());
			   fetcheddaterequired = ph_WorkOrderPo.getleDateRequired().getText().trim();
			System.out.println(fetcheddaterequired);
			assertionMessage= "date required value mapping after datasync Expected = "+fetcheddaterequired +" Actual = "+ScheduledDate;
			try{assertEquals(fetcheddaterequired,ScheduledDate);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			
			ph_WorkOrderPo.geteleXsymbol().click();
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful After Data Sync");
			
			ExtentManager.logger.log(Status.PASS,"Installed product to workorder field mapping is successfull(Lookup,date,datetime fields are covered for both header and child)");
		
	
	}

	

	 	
}
