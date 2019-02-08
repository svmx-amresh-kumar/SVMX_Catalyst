package com.ge.fsa.tests;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_CustomAction_1_RS_10559 extends BaseLib {
	
	String sScriptName = "SCN_CustomAction_RS_10559";
	String sTestCaseID = "RS_10559";
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10559() throws Exception {
		
		//Running Pre-Req
		 commonsPo.preReqSetup(genericLib);
		// Resinstall the app
		lauchNewApp("false");
		//Execute Sahi
		commonsPo.execSahi(genericLib, sScriptName, sTestCaseID);
		
		//**********Create Work Orderfrom API**********
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		//	System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		//	System.out.println("WO no ="+sWOName);
		//**********Create Product from API**********
		String sProductName = commonsPo.generaterandomnumber("Prod");
		restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		String sProductId = restServices.restGetSoqlValue("SELECT+Id+from+Product2+Where+Name+=\'" + sProductName + "\'", "Id");
		//	System.out.println(sProductId);
		//**********Get record type Usage/Consumption for work detail**********
		String sUsageLine = "Usage/Consumption";
		String sRecordTypeId = restServices.restGetSoqlValue("SELECT+Id+from+RecordType+Where+Name+=\'" + sUsageLine + "\'", "Id");
		//**********Create Work Detail and associate with Work Order
		String sworkDetail = restServices.restCreate("SVMXC__Service_Order_Line__c?","{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Parts\",\"SVMXC__Service_Order__c\":\""+sWORecordID+"\",\"RecordTypeId\":\""+sRecordTypeId+"\",\"SVMXC__Actual_Quantity2__c\":\"11\",\"SVMXC__Product__c\":\""+sProductId+"\"}");
		//	System.out.println(sworkDetail);
	
		String sWebServiceState = "Nottingham Shire";
		String sWebServiceCountry = "United Kingdom";
		String sRequestedCity = "Adams Town";
		String sRequestedCountry = "French Polynesia";
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
		TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		dateFormat.setTimeZone(gmtTime);
		String sCurrentDate = dateFormat.format(date);
		System.out.println(sCurrentDate);
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.configSync(commonsPo); 
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFMWithIcon(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWOName, "10559_Action");
		Thread.sleep(GenericLib.i30SecSleep);
		workOrderPo.getEleActionsLnk().click();
		commonsPo.tap(workOrderPo.getEleActionsLnk());
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(workOrderPo.getEleActionsTxt("EDIT_WORKORDER_MAPPING"),20,20);
		Thread.sleep(GenericLib.iMedSleep);
//		System.out.println("State is "+workOrderPo.getEleLblStateName().getAttribute("innerText"));
//		Thread.sleep(GenericLib.iHighSleep);
//		System.out.println("Country is "+workOrderPo.getEleLblCountryName().getText());
//		System.out.println("Country is 1111 "+workOrderPo.getEleLblCountryName().getAttribute("textContent"));
		Assert.assertTrue(workOrderPo.getEleLblStateName().getAttribute("textContent").equals(sWebServiceState));
//		Assert.assertTrue(workOrderPo.getEleLblCountryName().getText().equals(sWebServiceCountry));
//		System.out.println("Time is "+workOrderPo.getEleLblCompletedDateTime().getAttribute("innerText"));
//		System.out.println("Build Up Time "+sCurrentDate);
//		System.out.println(workOrderPo.getEleLblCompletedDateTime().getAttribute("textContent"));
//		Assert.assertTrue(workOrderPo.getEleLblCompletedDateTime().getAttribute("textContent").contains(sCurrentDate));
		workOrderPo.getelecancelbutton().click();
		commonsPo.tap(workOrderPo.getelecancelbutton());
		workOrderPo.geteleDiscardChangesbutton().click();
		commonsPo.tap(workOrderPo.geteleDiscardChangesbutton());
		workOrderPo.navigateToWOSFMWithIcon(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWOName, "10559_Action_Child");
		Thread.sleep(GenericLib.i30SecSleep);
		workOrderPo.getEleActionsLnk().click();
		commonsPo.tap(workOrderPo.getEleActionsLnk());
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(workOrderPo.getEleActionsTxt("EDIT_WORKORDER_MAPPING"),20,20);
		workOrderPo.getEleclickparts(sProductName).click();
		commonsPo.tap(workOrderPo.getEleclickparts(sProductName),20,20);
		Thread.sleep(5000);
//		System.out.println("R city "+workOrderPo.getEleLblRequestedCity().getAttribute("innerText"));
//		System.out.println(sRequestedCity);
//		System.out.println("R county "+workOrderPo.getEleLblRequestedCountry().getText());
		Assert.assertTrue(workOrderPo.getEleLblRequestedCity().getAttribute("innerText").equals(sRequestedCity));
//		Assert.assertTrue(workOrderPo.getEleLblRequestedCountry().getText().equals(sRequestedCountry));
	}

}
