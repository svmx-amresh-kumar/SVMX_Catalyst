/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_RS_10546 extends BaseLib {
	
	int iWhileCnt = 0;
	String sTestCaseID = null;
	String sWorkOrderID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectID = null;
	String sWOObejctApi = null;
	String sWOJsonData = null;
	String sFieldServiceName = null;
	String sWOName = null;
	String sActivityType = null;
	String sCase = null;
	String sIssueTxt = null;
	String sOrderStatus = null;
	String sBillingType = null;
	String sWOSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	String sSheetName =null;
	
	@BeforeMethod
	public void initializeObject() throws IOException { 

		restServices.getAccessToken();
		
	}

	@Test(enabled = true)
	public void scenario6Test() throws Exception {
		sSheetName ="RS_10545";
		sDeviceDate = driver.getDeviceTime().split(" ");
		sTestCaseID = "SCN_RS_10552";
		sWOObejctApi="SVMXC__Service_Order__c?";
		
		//Creation of dynamic Work Order2
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
		sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		sWOName =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 
						
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sIssueTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "IssueText");
		sOrderStatus = GenericLib.getExcelData(sTestCaseID,sSheetName, "OrderStatus");
		sBillingType = GenericLib.getExcelData(sTestCaseID,sSheetName, "BillingType");
		
		genericLib.executeSahiScript("appium/RS_6967_prerequisite.sah", sTestCaseID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
		
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
			
		//Config Sync for process
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
			
		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep); 
		sFieldServiceName="lscenario6";
			
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
			
			}

}
