package com.ge.fsa.tests.tablet;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.Retry;

public class SCN_CustomAction_1_RS_10559 extends BaseLib {
	
	String sScriptName = "SCN_CustomAction_RS_10559";
	String sTestCaseID = "RS_10559";
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10559() throws Exception {
		
//		//Running Pre-Req
//		 commonsUtility.preReqSetup(genericLib);
//		// Resinstall the app
//		lauchNewApp("false");
//		//Execute Sahi
		commonUtility.executeSahiScript(sScriptName, sTestCaseID);
		
		//**********Create Work Orderfrom API**********
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		//	System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		//	System.out.println("WO no ="+sWOName);
		//**********Create Product from API**********
		String sProductName = commonUtility.generateRandomNumber("Prod");
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
		loginHomePo.login(commonUtility, exploreSearchPo);	
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		toolsPo.configSync(commonUtility); 
		Thread.sleep(CommonUtility.iMedSleep);
		workOrderPo.navigateToWOSFMWithIcon(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWOName, "10559_Action");
		Thread.sleep(CommonUtility.i30SecSleep);
		commonUtility.tap(workOrderPo.getEleActionsLnk());
		Thread.sleep(CommonUtility.iLowSleep);
		commonUtility.tap(workOrderPo.getEleActionsTxt("EDIT_WORKORDER_MAPPING"),20,20);
	//	commonsUtility.tap(workOrderPo.getEleLblStateName());
		Thread.sleep(5000);
//		System.out.println("State is "+workOrderPo.getEleLblStateName().getAttribute("innerText"));
//		Thread.sleep(GenericLib.iHighSleep);
//		System.out.println("Country is "+workOrderPo.getEleLblCountryName().getText());
//		System.out.println("Country is 1111 "+workOrderPo.getEleLblCountryName().getAttribute("textContent"));
		
		Assert.assertTrue(workOrderPo.getEleLblStateName().getAttribute("innerText").equals(sWebServiceState));
//		Assert.assertTrue(workOrderPo.getEleLblCountryName().getText().equals(sWebServiceCountry));
//		System.out.println("Time is "+workOrderPo.getEleLblCompletedDateTime().getAttribute("innerText"));
//		System.out.println("Build Up Time "+sCurrentDate);
//		System.out.println(workOrderPo.getEleLblCompletedDateTime().getAttribute("textContent"));
//		Assert.assertTrue(workOrderPo.getEleLblCompletedDateTime().getAttribute("textContent").contains(sCurrentDate));
		try{
			workOrderPo.getelecancelbutton().click();
			workOrderPo.geteleDiscardChangesbutton().click();
		}
		catch(Exception e){
			commonUtility.tap(workOrderPo.getelecancelbutton());
			commonUtility.tap(workOrderPo.geteleDiscardChangesbutton());
		}
		workOrderPo.navigateToWOSFMWithIcon(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWOName, "10559_Action_Child");
		Thread.sleep(CommonUtility.i30SecSleep);
//		try{
//			workOrderPo.getEleActionsLnk().click();
//		}
//		catch (Exception e) {
//			commonUtility.tap(workOrderPo.getEleActionsLnk());
//		}
//		
//		Thread.sleep(GenericLib.iLowSleep);
//		commonUtility.tap(workOrderPo.getEleActionsTxt("EDIT_WORKORDER_MAPPING"),20,20);
		workOrderPo.selectAction(commonUtility, "EDIT_WORKORDER_MAPPING");
		workOrderPo.getEleclickparts(sProductName).click();
		commonUtility.tap(workOrderPo.getEleclickparts(sProductName),20,20);
		Thread.sleep(5000);
//		System.out.println("R city "+workOrderPo.getEleLblRequestedCity().getAttribute("innerText"));
//		System.out.println(sRequestedCity);
//		System.out.println("R county "+workOrderPo.getEleLblRequestedCountry().getText());
		Assert.assertTrue(workOrderPo.getEleLblRequestedCity().getAttribute("innerText").equals(sRequestedCity));
//		Assert.assertTrue(workOrderPo.getEleLblRequestedCountry().getText().equals(sRequestedCountry));
	}

}
