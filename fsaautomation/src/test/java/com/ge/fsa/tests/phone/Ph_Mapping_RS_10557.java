package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import org.json.JSONArray;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_Mapping_RS_10557 extends BaseLib {

	int iWhileCnt = 0;

	String sObjectIBID =null ;
	
	String sIBname=null ;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sSqlAccQuery=null;
	String sObjectProID=null;
	String sObjectApi = null;
	String sJsonData = null;
	//String sAccountName = "Proforma04092018185618account";
	String sAccountName =null;
	String sFieldServiceName = null;
	//String sproductname = "Proforma04092018185618product";
	String sproductname =null;
	//String sInstalledproductID="RS_10557_InstalledProduct_Auto";
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	String sIBLastModifiedBy=null;
	String sObjectlocationID=null;
String Location=null;
	
	WebElement productname=null;
	String sSheetName =null;
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test()
	
	public void RS_10557() throws Exception {
		sSheetName ="RS_10557";
		sDeviceDate = driver.getDeviceTime().split(" ");
		
		String sProformainVoice = commonUtility.generaterandomnumber("AUTO");
		String sTestCaseID="RS-10557_mapping";
		String sInstalledproductID=sProformainVoice+"RS_10557_IB";
		
		//sahi
				/*genericLib.executeSahiScript("appium/SCN_Mapping_RS_10557.sah", "sTestCaseID");
				if(commonsUtility.verifySahiExecution()) {
					
					System.out.println("PASSED");
				}
				else 
				{
					System.out.println("FAILED");
					

					ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
					assertEquals(0, 1);
				}
				lauchNewApp("true");
				System.out.println("RS_10557");
		*/
		
		
		//read from file
				sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
				sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
				sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
	/*	
		
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sProformainVoice+""+"account\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
		//sProductName1="v1";
		System.out.println(sAccountName);
		// Create product
		sJsonData = "{\"Name\": \""+sProformainVoice+""+"product\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectProID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectProID+"\'";				
		sproductname  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sproductname);
		//create location
		sObjectApi = "SVMXC__Site__c?";
		sJsonData = "{\"Name\": \""+sProformainVoice+""+"Location\", \"SVMXC__Street__c\": \"#4566\", \"SVMXC__Country__c\": \"India\", \"SVMXC__Zip__c\": \"560008\"}";
		sObjectlocationID=restServices.restCreate(sObjectApi,sJsonData);
		String sSqllocQuery = "SELECT+name+from+SVMXC__Site__c+Where+id+=\'"+sObjectlocationID+"\'";				
		Location =restServices.restGetSoqlValue(sSqllocQuery,"Name"); 
		//sProductName1="v1";
		System.out.println(Location);
		*/
		
			//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
	
			//datasync
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		//ph_WorkOrderPo.createInstalledProduct(commonUtility,ph_CalendarPo,sAccountName, sproductname, sInstalledproductID,ph_ExploreSearchPO);
		ph_WorkOrderPo.createInstalledProduct(commonUtility,ph_CalendarPo,"AUTO11042019131130account", "AUTO11042019131130product",sInstalledproductID,ph_ExploreSearchPo);

			
			Thread.sleep(5000);
			//navigate to sfm
			ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  sExploreSearch, sExploreChildSearchTxt, sInstalledproductID,sFieldServiceName );	
			
			
			
			//fill the values to to fields
			//commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleScheduledDateTime(), 0,"0", "0");
			commonUtility.setDateTime12Hrs(ph_WorkOrderPo.getEleScheduledDateTime(), 0,"0", "0","0"); //set start time to Today

			String ScheduledDateTimeWO = ph_WorkOrderPo.getEleScheduledDateTime().getAttribute("text");
		
		  //Thread.sleep(2000); ph_WorkOrderPo.getEleSite().click();
		  ph_ExploreSearchPo.selectFromLookupSearchList(commonUtility,ph_WorkOrderPo.getEleSite(), Location);
		  
		  //ph_WorkOrderPo.getEleComponent().click();
		  ph_ExploreSearchPo.selectFromLookupSearchList(commonUtility,ph_WorkOrderPo.getEleComponent(), sproductname); Thread.sleep(2000);
		 
			
			//add new line for parts
		  commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getElePartLnk(), ph_WorkOrderPo.getStringParts());
			Thread.sleep(4000);
			String LocationWO = ph_WorkOrderPo.getEleToLocation().getAttribute("text");
			System.out.println(LocationWO);
			Assert.assertNotNull(LocationWO);
			try{Assert.assertTrue(Location.equals(LocationWO));ExtentManager.logger.log(Status.PASS,"(Lookup(Location)) value mapped Successful  ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"(Lookup(Location)) value mapping Failed ");}
			ph_WorkOrderPo.getEleAdd().click();
			
			//Add new line for labor
			commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleLaborLnk(),ph_WorkOrderPo.getStringLabor());
			String startdatetimeWO = ph_WorkOrderPo.getEleStartDateTimeTxtFld().getAttribute("text");
			System.out.println(startdatetimeWO);
			Assert.assertNotNull(startdatetimeWO);
			
			
			try{Assert.assertTrue(ScheduledDateTimeWO.equals(startdatetimeWO));ExtentManager.logger.log(Status.PASS,"startdatetime value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"startdatetime value mapping Failed ");}
			ph_WorkOrderPo.getEleAdd().click();
			
			//validating mapped values before save
			//Part
			ph_WorkOrderPo.getEleOntoppart().click();
			Thread.sleep(2000);
			  LocationWO = ph_WorkOrderPo.getEleToLocation().getAttribute("text");
			 System.out.println(LocationWO);
			try{Assert.assertTrue(Location.equals(LocationWO));ExtentManager.logger.log(Status.PASS,"Location value mapped Successful before save ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Location value mapping Failed before save ");}
			ph_WorkOrderPo.geteleXsymbol().click();
			 
			 //Labor
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleOntoplabor());
			ph_WorkOrderPo.getEleOntoplabor().click();
			Thread.sleep(2000);
			 startdatetimeWO = ph_WorkOrderPo.getEleStartDateTimeTxtFld().getAttribute("text");
			 System.out.println(startdatetimeWO);
			try{Assert.assertTrue(ScheduledDateTimeWO.equals(startdatetimeWO));ExtentManager.logger.log(Status.PASS,"startdatetime value mapped Successful before save ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"startdatetime value mapping Failed before save ");}
			ph_WorkOrderPo.geteleXsymbol().click();
			Thread.sleep(2000);
			
			ph_WorkOrderPo.getElesave().click();
	
			
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful before save");
			Thread.sleep(4000);
			ph_MorePo.syncData(commonUtility);
			Thread.sleep(GenericLib.iMedSleep);
			Thread.sleep(15000);
///////////////////////validation from server//////////////////////////////////////////////////	
		//	String sSoqlQuery = "SELECT+Id+from+SVMXC__Installed_Product__c+Where+SVMXC__Company__c+=\'"+sObjectAccID+"\'+AND+SVMXC__Product__c+=\'"+sObjectProID+"\'";
			String sSoqlQuery = "SELECT+Id+from+SVMXC__Installed_Product__c+Where+Name+=\'"+sInstalledproductID+"\'";
			restServices.getAccessToken();
			String sIBID = restServices.restGetSoqlValue(sSoqlQuery,"Id");
			System.out.println(sIBID);
				
			
			
			// Collecting the Work Order number from the Server.
			String sSoqlQuerywo = "SELECT+name+from+SVMXC__Service_Order__c+Where+SVMXC__Component__c+=\'"+sIBID+"\'";
			restServices.getAccessToken();
			String sworkOrdername = restServices.restGetSoqlValue(sSoqlQuerywo,"Name");
			System.out.println(sworkOrdername);
		
		String sSoqlscheduleddatewo= "SELECT+SVMXC__Scheduled_Date_Time__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sworkOrdername+"\'";
		String sSoqlQueryscheduleddatewo = restServices.restGetSoqlValue(sSoqlscheduleddatewo, "SVMXC__Scheduled_Date_Time__c");
		System.out.println(sSoqlQueryscheduleddatewo);
	
		
			//Collecting the parts from the Server.
			JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Requested_Location__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Start_Date_and_Time__c+ = null+and+SVMXC__Requested_Location__c!=null+and+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkOrdername+"\')");
			String sLocationid = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Requested_Location__c");
			System.out.println("****************"+sLocationid);
			String LocationQuery = "SELECT+Name+from+SVMXC__Site__c+where+id=\'"+sLocationid+"\'";
			String soqllocationName  =restServices.restGetSoqlValue(LocationQuery,"Name"); 
			try{assertEquals(Location, soqllocationName);ExtentManager.logger.log(Status.PASS,"(Lookup(Location)) value mapped Successful from server ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Location value mapping Failed from server ");}
	
			
			//Collecting the labor from the Server.
			JSONArray sJsonArraylabor = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Start_Date_and_Time__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Requested_Location__c+ = null+and+SVMXC__Start_Date_and_Time__c!=null+and+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkOrdername+"\')");
			String sstartdatetime = restServices.getJsonValue(sJsonArraylabor, "SVMXC__Start_Date_and_Time__c");
			System.out.println("****************"+sstartdatetime);
			try{assertEquals(sSoqlQueryscheduleddatewo, sstartdatetime);ExtentManager.logger.log(Status.PASS,"startdatetime value mapped Successful from server ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"startdatetime value mapping Failed from server ");}
			ExtentManager.logger.log(Status.PASS,"Mapping is Successful from Server");

		
			
			System.out.println("Validating mapping after data sync");
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  sExploreSearch, "Work Orders", sworkOrdername,"EDIT_WORKORDER_MAPPING" );	
			Thread.sleep(GenericLib.iMedSleep);
		
			commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEletabonpart());
			Thread.sleep(GenericLib.iLowSleep);
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleToLocation());
			String fetchedlocation = ph_WorkOrderPo.getEleToLocation().getAttribute("text");
		System.out.println(fetchedlocation);
			try{Assert.assertTrue(Location.equals(fetchedlocation));ExtentManager.logger.log(Status.PASS,"Location value mapped Successful after data sync ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Location value mapping Failed after data sync ");}
			ph_WorkOrderPo.geteleXsymbol().click();
			 
			 //Labor
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleOntoplabor());
			ph_WorkOrderPo.getEleOntoplabor().click();
			Thread.sleep(2000);
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleStartDateTimeTxtFld());
			 startdatetimeWO = ph_WorkOrderPo.getEleStartDateTimeTxtFld().getAttribute("text");
			 System.out.println(startdatetimeWO);
			try{Assert.assertTrue(ScheduledDateTimeWO.equals(startdatetimeWO));ExtentManager.logger.log(Status.PASS,"startdatetime mapped Successful after data sync");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"startdatetime value mapping Failed after data sync ");}
			Thread.sleep(2000);
			ph_WorkOrderPo.geteleXsymbol().click();
			Thread.sleep(2000);
			
	
	ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful After save(Lookup and datetime fields covered using SVMX.CURRENTRECORDHEADER)");

			
			
		
	
	}

//	
//	  @AfterClass(enabled = true) public void deletedata() throws Exception {
//	  //Deleting data created
//	  restServices.restDeleterecord("Account",sObjectAccID);
//	  restServices.restDeleterecord("Product2",sObjectProID);
//	  restServices.restDeleterecord("SVMXC__Site__c",sObjectlocationID); }
//	 
}
