/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_SelfDispatch_RS_10562 extends BaseLib {

	int iWhileCnt = 0;
	String sTestID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sSerialNumber = null;
	String sWorkOrderID = null;
	String sWOObejctApi = null;
	String sWOJsonData = null;
	String sWOName1 = null;
	String sWOSqlQuery = null;
	String sFieldServiceName = null;
	String sSubject =null;
	String sSqlQuery = null;
	List<WebElement> lsWoList = null;
	
	public void preRequiste() throws Exception { 
		
		restServices.getAccessToken();
		sWOObejctApi="SVMXC__Service_Order__c?";
		
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
		sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		sWOName1 =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 
		
		genericLib.executeSahiScript("appium/SCN_SelfDispatch_RS_10562_prerequisite.sah", sTestID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10562Test() throws Exception {
		
		sTestID = "RS_10562";
		sExploreSearch = GenericLib.readExcelData(GenericLib.sTestDataFile, sTestID,"ExploreSearch");
		sExploreChildSearchTxt = GenericLib.readExcelData(GenericLib.sTestDataFile, sTestID,"ExploreChildSearch");
		sFieldServiceName = GenericLib.readExcelData(GenericLib.sTestDataFile,sTestID, "ProcessName");
		preRequiste();
		sSubject = "Testing "+sWOName1+" "+sTestID;
		
		//Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		
		//Config Sync
		toolsPo.configSync(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		
		//Data Sync
		toolsPo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
	
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1, sFieldServiceName);
		
		//Set Start time for event
		commonUtility.setDateTime24hrs(workOrderPo.getEleStartDateAndTimeTxtFld(), 0, "0", "0");
		
		//Edit the subject
		commonUtility.switchContext("Webview");
		workOrderPo.getEleSubjectTxtFld().sendKeys(sSubject);
		
		//Set end time
		commonUtility.switchContext("Webview");
		commonUtility.setDateTime24hrs(workOrderPo.getEleEndDateAndTimeTxtFld(), 1, "0", "0");
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		Thread.sleep(GenericLib.iLowSleep);
		
		//Validation of auto update process
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Update process is not successful.");
		ExtentManager.logger.log(Status.PASS,"WorkOrder saved successfully.");
		
		//Navigation to Calendar
		commonUtility.tap(calendarPO.getEleCalendarIcn());
		Thread.sleep(GenericLib.iHighSleep);
		
		commonUtility.tap(calendarPO.getEleCalendarIcn());
		Thread.sleep(GenericLib.iMedSleep);
		//driver.activateApp(GenericLib.sAppBundleID);
		
		//Validation of event on the calender
		for(int i=0;i<calendarPO.getEleWOEventTitleTxt().size();i++){
		    if(calendarPO.getEleWOEventTitleTxt().get(i).getText().equals(sWOName1))
		    {
		    	Assert.assertTrue(true,"Work Order event is not displayed on calender");
		    	ExtentManager.logger.log(Status.PASS,"WorkOrder event is displayed successfully on calender.");
			}
		} 
		
		toolsPo.syncData(commonUtility);
		Thread.sleep(GenericLib.i30SecSleep);
		try {
			//Validation of event creation at server side after sync.
			sSqlQuery ="SELECT+Name+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sWorkOrderID+"\'";				
			Assert.assertTrue(restServices.restGetSoqlValue(sSqlQuery,"Name").equals(sSubject), "Event is not created");
			ExtentManager.logger.log(Status.PASS,"Event  is successfully created.");
		}catch(Exception e)
		{	try {	//Validation of event creation at server side after sync.
				sSqlQuery ="SELECT+Name+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sWorkOrderID+"\'";				
				Assert.assertTrue(restServices.restGetSoqlValue(sSqlQuery,"Name").contains(sWOName1), "Event is not created");
				ExtentManager.logger.log(Status.PASS,"Event  is successfully created.");
			}	catch(Exception ex){throw ex;}
		}
		
	}
}
