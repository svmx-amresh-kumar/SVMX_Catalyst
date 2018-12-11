/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;
import org.json.JSONArray;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

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
	
	
	public void preRequiste() throws Exception { 
		
		restServices.getAccessToken();
		sWOObejctApi="SVMXC__Service_Order__c?";
		
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
		sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		sWOName1 =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 
		
		
		
		genericLib.executeSahiScript("appium/SCN_SelfDispatch_RS_10562_prerequisite.sah", sTestID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
		//sWOName1="WO-00003607";
		
	}

	@Test(enabled = true)
	public void SCN_SrctoTrgt_RS_10562Test() throws Exception {
		
		sTestID = "RS_10562";
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestID, sTestID,"ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestID,sTestID, "ProcessName");
		sSubject = "Testing "+sTestID;
		preRequiste();
		
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
	
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1, sFieldServiceName);
		
		//Set Start time for event
		workOrderPo.getEleStartDateTimeTxtFld().click();
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Native");
		commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
		
		
		//Edit the subject
		commonsPo.switchContext("Webview");
		workOrderPo.getEleSubjectTxtFld().sendKeys(sSubject);
		
		workOrderPo.getEleEndDateTimeTxtFld().click();
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Native");
		
		commonsPo.setDatePicker(1, 1);
		commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
		
		commonsPo.switchContext("Webview");
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		Thread.sleep(GenericLib.iLowSleep);

		//Validation of auto update process
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Update process is not successful.");
		ExtentManager.logger.log(Status.PASS,"WorkOrder saved successfully.");
		
		commonsPo.tap(calendarPO.getEleCalendarIcn());
		Thread.sleep(GenericLib.iLowSleep);
		
		//Validation of WorkOrder event
		Assert.assertTrue(calendarPO.getEleWOEventSubjectTxt(sSubject).isDisplayed(), "WorkOrder Subject is not displayed on the calender");
		ExtentManager.logger.log(Status.PASS,"WorkOrder Subject is displayed successfully on calender.");
		Assert.assertTrue(calendarPO.getEleWOEventTitleTxt(sWOName1).isDisplayed() , "WorkOrder Event is not displayed on the calender");
		ExtentManager.logger.log(Status.PASS,"WorkOrder event is displayed successfully on calender.");
		
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		//JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("Select+Name+from+Auto_Custom_Object10540__c+where+Number_10541__c+= \'"+sIBName2+"\')");
		//System.out.println(restServices.getJsonValue(sJsonArrayparts, "Name"));

		
	}
}
