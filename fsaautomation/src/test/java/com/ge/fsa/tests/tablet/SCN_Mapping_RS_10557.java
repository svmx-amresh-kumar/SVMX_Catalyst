/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10557"
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
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.CreateNewPO;

public class SCN_Mapping_RS_10557 extends BaseLib {

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

	@Test(retryAnalyzer=Retry.class)
	
	public void RS_10557() throws Exception {
		sSheetName ="RS_10557";
		sDeviceDate = driver.getDeviceTime().split(" ");
		
		String sProformainVoice = commonsUtility.generaterandomnumber("AUTO");
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
				System.out.println("RS_10557");*/
		
		
		
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sTestCaseID+""+"account\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
		//sProductName1="v1";
		System.out.println(sAccountName);
		// Create product
		sJsonData = "{\"Name\": \""+sTestCaseID+""+"product\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectProID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectProID+"\'";				
		sproductname  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sproductname);
		//create location
		sObjectApi = "SVMXC__Site__c?";
		sJsonData = "{\"Name\": \""+sTestCaseID+""+"Location\", \"SVMXC__Street__c\": \"#4566\", \"SVMXC__Country__c\": \"India\", \"SVMXC__Zip__c\": \"560008\"}";
		sObjectlocationID=restServices.restCreate(sObjectApi,sJsonData);
		String sSqllocQuery = "SELECT+name+from+SVMXC__Site__c+Where+id+=\'"+sObjectlocationID+"\'";				
		Location =restServices.restGetSoqlValue(sSqllocQuery,"Name"); 
		//sProductName1="v1";
		System.out.println(Location);
		
		//read from file
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		
		
			//Pre Login to app
			loginHomePo.login(commonsUtility, exploreSearchPo);
			//config sync
			//toolsPo.configSync(commonsUtility);
			Thread.sleep(GenericLib.iMedSleep);
		
			//datasync
			toolsPo.syncData(commonsUtility);
			Thread.sleep(GenericLib.iMedSleep);
			
			createNewPO.createInstalledProduct(commonsUtility,sAccountName, sproductname, sInstalledproductID);
			
			Thread.sleep(5000);
			//navigate to sfm
			workOrderPo.navigateToWOSFM(commonsUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sInstalledproductID, sFieldServiceName);
			//fill the values to to fields
			commonsUtility.setDateTime24hrs((workOrderPo.getEleScheduledDateTimeTxt()), 0,"0", "0");
			Thread.sleep(2000);
			commonsUtility.tap(createNewPO.getEleClicksite());
			commonsUtility.lookupSearch(Location);
			Thread.sleep(2000);
			
			//commonsUtility.tap(createNewPO.getEleClickComponent());
			commonsUtility.tap(createNewPO.getEleClickComponent());
			commonsUtility.lookupSearch(sInstalledproductID);
			Thread.sleep(2000);
			//add new line for parts
			commonsUtility.tap(workOrderPo.getElePartLnk());
			commonsUtility.tap(workOrderPo.getEleDoneBtn());
			
			//Add new line for labor
			commonsUtility.tap(workOrderPo.getEleAddLaborLnk());
			commonsUtility.tap(workOrderPo.getEleDoneBtn());
			
			//validating mapped values before save
			commonsUtility.tap(workOrderPo.openpartsontap());
			//Thread.sleep(GenericLib.iHighSleep);
			String fetchedlocation =workOrderPo.getElePartsLocation().getAttribute("value");
			System.out.println(fetchedlocation);
			commonsUtility.tap(workOrderPo.getEleDoneBtn());
			try{Assert.assertTrue(fetchedlocation.equals(Location));ExtentManager.logger.log(Status.PASS,"(Lookup(Location)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"(Lookup(Location)) value mapping Failed ");}
			
			commonsUtility.tap(workOrderPo.openLaborontap());
			String fetchedstartdateandtime =workOrderPo.getEleStartDateAndTimeTxtFld().getAttribute("value");
			System.out.println(fetchedstartdateandtime);
			commonsUtility.tap(workOrderPo.getEleDoneBtn());
			String getscheduleddatetime= workOrderPo.getScheduledDatetimevalue().getAttribute("value");
			try{Assert.assertTrue(fetchedstartdateandtime.equals(getscheduleddatetime));ExtentManager.logger.log(Status.PASS,"date required(Date/time) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"date required(Date/time) value mapping Failed ");}
			
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful before save");
			commonsUtility.tap(workOrderPo.getEleSaveLnk());
			
			toolsPo.syncData(commonsUtility);
			Thread.sleep(GenericLib.iMedSleep);
			
			
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
		
	
		
			//Collecting the parts from the Server.
			JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Requested_Location__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Start_Date_and_Time__c+ = null+and+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkOrdername+"\')");
			String sLocationid = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Requested_Location__c");
			System.out.println("****************"+sLocationid);
			String LocationQuery = "SELECT+Name+from+SVMXC__Site__c+where+id=\'"+sLocationid+"\'";
			String soqlpartName  =restServices.restGetSoqlValue(LocationQuery,"Name"); 
			try{assertEquals(Location, soqlpartName);ExtentManager.logger.log(Status.PASS,"(Lookup(Location)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"(Lookup(Location)) value mapping Failed ");}
			//Collecting the labor from the Server.
			JSONArray sJsonArraylabor = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Start_Date_and_Time__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Requested_Location__c+ = null+and+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkOrdername+"\')");
			String sstartdatetime = restServices.getJsonValue(sJsonArraylabor, "SVMXC__Start_Date_and_Time__c");
			System.out.println("****************"+sstartdatetime);
			try{assertEquals(sSoqlQueryscheduleddatewo, sstartdatetime);ExtentManager.logger.log(Status.PASS,"Date required(Date/time) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"Date required(Date/time) value mapping Failed ");}
			ExtentManager.logger.log(Status.PASS,"Mapping is Successful from Server");

			System.out.println("Validating mapping after data sync");
			
			commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
			workOrderPo.navigateToWOSFM(commonsUtility, exploreSearchPo, sExploreSearch, "Work Orders", sworkOrdername,"EDIT_WORKORDER_MAPPING" );
			Thread.sleep(GenericLib.iMedSleep);
			
			commonsUtility.tap(workOrderPo.openpartsontap());
			//Thread.sleep(GenericLib.iHighSleep);
			  fetchedlocation = workOrderPo.getElePartsLocation().getAttribute("value");
			System.out.println(fetchedlocation);
			commonsUtility.tap(workOrderPo.getEleDoneBtn());
			try{Assert.assertTrue(fetchedlocation.equals(Location));ExtentManager.logger.log(Status.PASS,"(Lookup(Location)) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"(Lookup(Location)) value mapping Failed ");}
			
			commonsUtility.tap(workOrderPo.getLaboronsecondprt());
			  fetchedstartdateandtime = workOrderPo.getEleStartDateAndTimeTxtFld().getAttribute("value");
			System.out.println(fetchedstartdateandtime);
			commonsUtility.tap(workOrderPo.getEleDoneBtn());
			  getscheduleddatetime = workOrderPo.getScheduledDatetimevalue().getAttribute("value");
			try{Assert.assertTrue(fetchedstartdateandtime.equals(getscheduleddatetime));ExtentManager.logger.log(Status.PASS,"date required(Date/time) value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
			ExtentManager.logger.log(Status.FAIL,"date required(Date/time) value mapping Failed ");}
			
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful After save(Lookup and datetime fields covered using SVMX.CURRENTRECORDHEADER)");
		
	
	
	}

	@AfterClass(enabled = true)
	public void deletedata() throws Exception {
		//Deleting data created
					restServices.restDeleterecord("Account",sObjectAccID); 
					restServices.restDeleterecord("Product2",sObjectProID); 
					restServices.restDeleterecord("SVMXC__Site__c",sObjectlocationID); 
	}
	
}
