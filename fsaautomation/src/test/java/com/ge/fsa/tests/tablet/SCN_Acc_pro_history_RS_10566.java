/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10566"
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class SCN_Acc_pro_history_RS_10566 extends BaseLib {

	int iWhileCnt = 0;
	String sTestCaseIDID = null;
	String sObjectIBID =null ;
	
	
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sObjectProID=null;
	String sObjectWOID1=null;
	String sObjectWOID2=null;
	String sObjectWOID3=null;
	String sObjectApi = null;
	String sJsonData = null;
	String WOname1 = null;
	String WOname2 = null;
	String WOname3 = null;
	String sFieldServiceName = null;
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	WebElement productname=null;
	String sSheetName =null;
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test(retryAnalyzer=Retry.class)
	public void Acc_pro_history_RS_10566() throws Exception {
		sSheetName ="RS_10566";
		sDeviceDate = driver.getDeviceTime().split(" ");
		
		String sTestCaseID="RS_10566_Acc_Pro_History";
		
		
		//sahi
		
		  commonUtility.executeSahiScript("appium/SCN_Acc_Pro_His_RS_10566.sah"); 
		  if(commonUtility.verifySahiExecution()) {
		  
		  System.out.println("PASSED"); } else { System.out.println("FAILED");
		  
		  
		  ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID +
		  "Sahi verification failure"); assertEquals(0, 1); } lauchNewApp("true");
		  System.out.println("RS_10566");
		 
		
		//create Account
		sJsonData = "{\"Name\": \""+sTestCaseIDID+""+"Account\"}";
		sObjectApi = "Account?";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		
		
		// Create product
		sJsonData = "{\"Name\": \""+sTestCaseIDID+""+"product\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectProID=restServices.restCreate(sObjectApi,sJsonData);
         //create IB
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\", \"Name\": \""+sTestCaseIDID+""+"IB\", \"SVMXC__Serial_Lot_Number__c\": \""+sTestCaseIDID+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sObjectIBID=restServices.restCreate(sObjectApi,sJsonData);
		
		// create Work Order
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Component__c\": \""+sObjectIBID+"\",\"SVMXC__Order_Status__c\":\"Open\"}";
		sObjectApi = "SVMXC__Service_Order__c?";
		
		sObjectWOID1=restServices.restCreate(sObjectApi,sJsonData);
		String sSqlWOQuery1 = "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectWOID1+"\'";				
		WOname1 =restServices.restGetSoqlValue(sSqlWOQuery1,"Name"); 
		
		sObjectWOID2=restServices.restCreate(sObjectApi,sJsonData);
		String sSqlWOQuery2 = "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectWOID2+"\'";				
		WOname2 =restServices.restGetSoqlValue(sSqlWOQuery2,"Name"); 
		
		sObjectWOID3=restServices.restCreate(sObjectApi,sJsonData);
		String sSqlWOQuery3 = "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectWOID3+"\'";				
		WOname3 =restServices.restGetSoqlValue(sSqlWOQuery3,"Name"); 
		
		
		//read from file
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ViewProcessNameAccPro");
		String sFieldServiceName2 = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
		
		
			//Pre Login to app
			loginHomePo.login(commonUtility, exploreSearchPo);
			//config sync
			toolsPo.configSync(commonUtility);
			Thread.sleep(CommonUtility.iMedSleep);
			
			//datasync
			toolsPo.syncData(commonUtility);
			Thread.sleep(CommonUtility.iMedSleep);
			
			workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, WOname1, null);
			Thread.sleep(CommonUtility.iMedSleep);
			
			String fetchedProducthistory =workOrderPo.getProductHistory().getText();
			System.out.println(fetchedProducthistory);
			assertTrue(fetchedProducthistory.contains("( 0 )"));
			
			String fetchedAccounthistory =workOrderPo.getAccountHistory().getText();
			System.out.println(fetchedAccounthistory);
			assertTrue(fetchedAccounthistory.contains("( 0 )"));
			
			workOrderPo.selectAction(commonUtility, sFieldServiceName2);
			Thread.sleep(CommonUtility.iLowSleep);
			commonUtility.tap(workOrderPo.getEleSaveLnk());
			Thread.sleep(CommonUtility.iMedSleep);
			
			workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, WOname2, null);

			String fetchedProducthistory1 =workOrderPo.getProductHistory().getText();
			System.out.println(fetchedProducthistory1);
			assertTrue(fetchedProducthistory1.contains("( 1 )"));
		
			String fetchedAccounthistory1 =workOrderPo.getAccountHistory().getText();
			System.out.println(fetchedAccounthistory1);
			assertTrue(fetchedAccounthistory1.contains("( 1 )"));	
			
			String ProHisWO1 =workOrderPo.getProHisWO().getText();
			System.out.println(ProHisWO1);
			assertEquals(ProHisWO1,WOname1 );
			
			
			String AccHisWO1 =workOrderPo.getAccHisWO().getText();
			System.out.println(AccHisWO1);
			assertEquals(AccHisWO1,WOname1 );
			
			workOrderPo.selectAction(commonUtility, sFieldServiceName2);
			Thread.sleep(CommonUtility.iLowSleep);
			commonUtility.tap(workOrderPo.getEleSaveLnk());
			Thread.sleep(CommonUtility.iMedSleep);
			
			workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, WOname3, null);

			String fetchedProducthistory2 =workOrderPo.getProductHistory().getText();
			System.out.println(fetchedProducthistory2);
			assertTrue(fetchedProducthistory2.contains("( 2 )"));
		
			String fetchedAccounthistory2 =workOrderPo.getAccountHistory().getText();
			System.out.println(fetchedAccounthistory2);
			assertTrue(fetchedAccounthistory2.contains("( 2 )"));	
			
			 ProHisWO1 =workOrderPo.getProHisWO1().getText();
			System.out.println(ProHisWO1);
			assertEquals(ProHisWO1,WOname1 );
			
			
			AccHisWO1 =workOrderPo.getAccHisWO1().getText();
			System.out.println(AccHisWO1);
			assertEquals(AccHisWO1,WOname1 );
			
			
			String ProHisWO2 =workOrderPo.getProHisWO().getText();
			System.out.println(ProHisWO2);
			assertEquals(ProHisWO2,WOname2);
			
			
			String AccHisWO2 =workOrderPo.getAccHisWO().getText();
			System.out.println(AccHisWO2);
			assertEquals(AccHisWO2,WOname2);
			
	}

}
