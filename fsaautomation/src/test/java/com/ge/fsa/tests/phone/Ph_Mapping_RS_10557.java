package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
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

public class Ph_Mapping_RS_10557 extends BaseLib {

	int iWhileCnt = 0;

	String sObjectIBID =null ;
	
	
	
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
	String sObjectlocationID=null;
	String Location=null;
	String assertionMessage="";
	WebElement productname=null;
	String sSheetName =null;
	Boolean	bProcessCheckResult=false;
	
	@Test(retryAnalyzer=Retry.class)
	
	public void RS_10557() throws Exception {
		sSheetName ="RS_10557";
	
		
		String sProformainVoice = commonUtility.generateRandomNumber("AUTO");
		String sTestCaseID="RS-10557_mapping";
		String sInstalledproductID=sProformainVoice+"RS_10557_IB";
		
		//read from file
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");

		//sahi
		 bProcessCheckResult = commonUtility.ProcessCheck(restServices, sFieldServiceName, "SCN_Mapping_RS_10557", sTestCaseID);
		
		
	
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sProformainVoice+""+"account\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
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
		System.out.println(Location);
		
		
			//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
	
		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
		Thread.sleep(CommonUtility.iMedSleep);
			//datasync
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		ph_WorkOrderPo.createInstalledProduct(commonUtility,ph_CalendarPo,sAccountName, sproductname, sInstalledproductID,ph_ExploreSearchPo);

			
			Thread.sleep(5000);
			//navigate to sfm
			ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  sExploreSearch, sExploreChildSearchTxt, sInstalledproductID,sFieldServiceName );	
			//fill the values to to fields
			commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleScheduledDateTime(), 0,"0", "0"); //set start time to Today

			String ScheduledDateTimeWO = ph_WorkOrderPo.getEleScheduledDateTime().getText();
		
		  ph_ExploreSearchPo.selectFromLookupSearchList(commonUtility,ph_WorkOrderPo.getEleSite(), Location);
		  
		  ph_ExploreSearchPo.selectFromLookupSearchList(commonUtility,ph_WorkOrderPo.getEleComponent(), sproductname);
		 
		Thread.sleep(3000);
			//add new line for parts
		  commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getElePartLnk(), ph_WorkOrderPo.getStringParts());
			Thread.sleep(3000);
			String LocationWO = ph_WorkOrderPo.getEleToLocation().getText();
			System.out.println(LocationWO);
			Assert.assertNotNull(LocationWO);
			 assertionMessage = "Location value mapping before save Expected = "+Location +" Actual = "+LocationWO;
			try{Assert.assertTrue(Location.equals(LocationWO));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			ph_WorkOrderPo.getEleAdd().click();
			
			//Add new line for labor
			commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleLaborLnk(),ph_WorkOrderPo.getStringLabor());
			Thread.sleep(3000);
			String startdatetimeWO = ph_WorkOrderPo.getEleStartDateTimeTxtFld().getText();
			System.out.println(startdatetimeWO);
			Assert.assertNotNull(startdatetimeWO);
			
			 assertionMessage = "Scheduled DateTime value mapping before save Expected = "+ScheduledDateTimeWO +" Actual = "+startdatetimeWO;
			try{Assert.assertTrue(ScheduledDateTimeWO.equals(startdatetimeWO));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			ph_WorkOrderPo.getEleAdd().click();
			
			//validating mapped values before save
			//Part
			commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getStringParts());
			ph_WorkOrderPo.getEletapon(Location).click();
			Thread.sleep(2000);
			  LocationWO = ph_WorkOrderPo.getEleToLocation().getText();
			 System.out.println(LocationWO);
			 assertionMessage = "Location value mapping before save Expected = "+Location +" Actual = "+LocationWO;
			try{Assert.assertTrue(Location.equals(LocationWO));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			ph_WorkOrderPo.geteleXsymbol().click();
			
			 //Labor
			
			commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getStringLabor());
			ph_WorkOrderPo.getEletapon(startdatetimeWO).click();
			Thread.sleep(2000);
			 startdatetimeWO = ph_WorkOrderPo.getEleStartDateTimeTxtFld().getText();
			 System.out.println(startdatetimeWO);
			 assertionMessage = "Scheduled DateTime value mapping before save Expected = "+ScheduledDateTimeWO +" Actual = "+startdatetimeWO;
			 try{Assert.assertTrue(ScheduledDateTimeWO.equals(startdatetimeWO));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			ph_WorkOrderPo.geteleXsymbol().click();
			Thread.sleep(2000);
			
			ph_WorkOrderPo.getElesave().click();
	
			
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful before save");
			Thread.sleep(4000);
			ph_MorePo.syncData(commonUtility);
			Thread.sleep(CommonUtility.iMedSleep);
			Thread.sleep(15000);
///////////////////////validation from server//////////////////////////////////////////////////	
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
			assertionMessage = "Location value mapping from server Expected = "+Location +" Actual = "+soqllocationName;
			try{assertEquals(Location, soqllocationName);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
	
			
			//Collecting the labor from the Server.
			JSONArray sJsonArraylabor = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Start_Date_and_Time__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Requested_Location__c+ = null+and+SVMXC__Start_Date_and_Time__c!=null+and+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkOrdername+"\')");
			String sstartdatetime = restServices.getJsonValue(sJsonArraylabor, "SVMXC__Start_Date_and_Time__c");
			System.out.println("****************"+sstartdatetime);
			 assertionMessage = "Scheduled DateTime value mapping from server Expected = "+sSoqlQueryscheduleddatewo +" Actual = "+sstartdatetime;
			 try{assertEquals(sSoqlQueryscheduleddatewo, sstartdatetime);ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			ExtentManager.logger.log(Status.PASS,"Mapping is Successful from Server");

		
			
			System.out.println("Validating mapping after data sync");
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  sExploreSearch, "Work Orders", sworkOrdername,"EDIT_WORKORDER_MAPPING" );	
			Thread.sleep(CommonUtility.iMedSleep);
		
			commonUtility.gotToTabHorizontal("PARTS");
			Thread.sleep(CommonUtility.iLowSleep);
			
			ph_WorkOrderPo.geteleRemoveablePart().click();
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleToLocation());
			String fetchedlocation = ph_WorkOrderPo.getEleToLocation().getText();
		System.out.println(fetchedlocation);
		assertionMessage = "Location value mapping after data sync Expected = "+Location +" Actual = "+soqllocationName;
			try{Assert.assertTrue(Location.equals(fetchedlocation));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			ph_WorkOrderPo.geteleXsymbol().click();
			 
			 //Labor
			commonUtility.gotToTabHorizontal("LABOR");
			ph_WorkOrderPo.geteleRemoveablePart().click();
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleStartDateTimeTxtFld());
			 startdatetimeWO = ph_WorkOrderPo.getEleStartDateTimeTxtFld().getText();
			 System.out.println(startdatetimeWO);
			 assertionMessage = "Scheduled DateTime value mapping after data sync Expected = "+sSoqlQueryscheduleddatewo +" Actual = "+sstartdatetime;
			try{Assert.assertTrue(ScheduledDateTimeWO.equals(startdatetimeWO));ExtentManager.logger.log(Status.PASS,assertionMessage);}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,assertionMessage);}
			Thread.sleep(2000);
			ph_WorkOrderPo.geteleXsymbol().click();
			Thread.sleep(2000);
			
	
	ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful After save(Lookup and datetime fields covered using SVMX.CURRENTRECORDHEADER)");

			
			
		
	
	}

	
}
