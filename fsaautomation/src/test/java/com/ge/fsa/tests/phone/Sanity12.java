package com.ge.fsa.tests.phone;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Sanity12 extends BaseLib {
	String sWorkOrderID;
	String sTestID = "Sanity12";
	String sProductId1;
	String sProductId2;
	String sProductId3;
	String sProductId4;
	String sWOName;
	String sProductName;
	String sUsageLine;
	String sRecordTypeId;
	String sworkDetail;
	String sPartId1;
	String sPartId2;
	String sPartId3;
	String sLaborId1;
	String sLaborId2;
	String sExpenseId1;
	String sExpenseId2;
	String sExpenseId3;
	String sExpenseId4;
	String sObjectApi;
	String sJsonData;
	String sTechName;
	String sObjectAccID;
	String sproductname;
	String sSqlAccQuery;
	String[] sDeviceDate;
	String sAttachmentID;
	String sImageName="TestImage";
	String sActionsName = "EditWoAutoTimesstamp";

	public void prerequisite() throws Exception {

		// Work Order Creation
		sTechName=GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "TECH_ID");
		sWorkOrderID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"Low\"}");
		System.out.println(sWorkOrderID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWorkOrderID + "\'", "Name");
		System.out.println("WO no =" + sWOName);

		// Getting record type usage Usage/Consumption for work detail
		sUsageLine = "Usage/Consumption";
		sRecordTypeId = restServices.restGetSoqlValue("SELECT+Id+from+RecordType+Where+Name+=\'" + sUsageLine + "\'", "Id");

		// Creating Product and Creating and associating a work detail to the work Order
		// (Parts)
		sProductName = "Sanity12" + commonUtility.generateRandomNumber("");
		restServices.restCreate("Product2?", "{\"Name\":\"" + sProductName + "\" }");
		sProductId1 = restServices.restGetSoqlValue("SELECT+Id+from+Product2+Where+Name+=\'" + sProductName + "\'", "Id");
		System.out.println(sProductId1);

		sPartId1 = restServices.restCreate("SVMXC__Service_Order_Line__c?",
				"{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Parts\",\"SVMXC__Service_Order__c\":\"" + sWorkOrderID + "\",\"RecordTypeId\":\"" + sRecordTypeId + "\",\"SVMXC__Actual_Quantity2__c\":\"11\",\"SVMXC__Product__c\":\"" + sProductId1 + "\"}");

		sProductName = "Sanity12" + commonUtility.generateRandomNumber("");
		restServices.restCreate("Product2?", "{\"Name\":\"" + sProductName + "\" }");
		sProductId2 = restServices.restGetSoqlValue("SELECT+Id+from+Product2+Where+Name+=\'" + sProductName + "\'", "Id");
		System.out.println(sProductId2);

		sPartId2 = restServices.restCreate("SVMXC__Service_Order_Line__c?",
				"{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Parts\",\"SVMXC__Service_Order__c\":\"" + sWorkOrderID + "\",\"RecordTypeId\":\"" + sRecordTypeId + "\",\"SVMXC__Actual_Quantity2__c\":\"11\",\"SVMXC__Product__c\":\"" + sProductId2 + "\"}");

		sProductName = "Sanity12" + commonUtility.generateRandomNumber("");
		restServices.restCreate("Product2?", "{\"Name\":\"" + sProductName + "\" }");
		sProductId3 = restServices.restGetSoqlValue("SELECT+Id+from+Product2+Where+Name+=\'" + sProductName + "\'", "Id");
		System.out.println(sProductId3);

		sPartId3 = restServices.restCreate("SVMXC__Service_Order_Line__c?",
				"{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Parts\",\"SVMXC__Service_Order__c\":\"" + sWorkOrderID + "\",\"RecordTypeId\":\"" + sRecordTypeId + "\",\"SVMXC__Actual_Quantity2__c\":\"11\",\"SVMXC__Product__c\":\"" + sProductId3 + "\"}");

		// Creating and associating a work detail to the work Order (Labor)
		sLaborId1 = restServices.restCreate("SVMXC__Service_Order_Line__c?",
				"{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Labor\",\"SVMXC__Service_Order__c\":\"" + sWorkOrderID + "\",\"RecordTypeId\":\"" + sRecordTypeId + "\",\"SVMXC__Actual_Quantity2__c\":\"11\",\"SVMXC__Product__c\":\"" + sProductId3 + "\"}");

		sLaborId2 = restServices.restCreate("SVMXC__Service_Order_Line__c?",
				"{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Labor\",\"SVMXC__Service_Order__c\":\"" + sWorkOrderID + "\",\"RecordTypeId\":\"" + sRecordTypeId + "\",\"SVMXC__Actual_Quantity2__c\":\"11\",\"SVMXC__Product__c\":\"" + sProductId3 + "\"}");

		// Creating and associating a work detail to the work Order (Expense)
		sExpenseId1 = restServices.restCreate("SVMXC__Service_Order_Line__c?", "{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Expenses\",\"SVMXC__Service_Order__c\":\"" + sWorkOrderID + "\",\"RecordTypeId\":\"" + sRecordTypeId + "\",\"SVMXC__Product__c\":\"" + sProductId1 + "\"}");

		sExpenseId2 = restServices.restCreate("SVMXC__Service_Order_Line__c?", "{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Expenses\",\"SVMXC__Service_Order__c\":\"" + sWorkOrderID + "\",\"RecordTypeId\":\"" + sRecordTypeId + "\",\"SVMXC__Product__c\":\"" + sProductId2 + "\"}");

		sExpenseId3 = restServices.restCreate("SVMXC__Service_Order_Line__c?", "{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Expenses\",\"SVMXC__Service_Order__c\":\"" + sWorkOrderID + "\",\"RecordTypeId\":\"" + sRecordTypeId + "\",\"SVMXC__Product__c\":\"" + sProductId3 + "\"}");

		sExpenseId4 = restServices.restCreate("SVMXC__Service_Order_Line__c?", "{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Expenses\",\"SVMXC__Service_Order__c\":\"" + sWorkOrderID + "\",\"RecordTypeId\":\"" + sRecordTypeId + "\",\"SVMXC__Product__c\":\"" + sProductId1 + "\"}");

		// Creating and associating a work detail to the work Order (Attchements)
		String file = restServices.encodeFileToBase64Binary(new File("/auto/SVMX_Catalyst/fsaautomation/resources/imageData/TestImage_1.jpg"));
		sAttachmentID = restServices.restCreate("Attachment?", "{\"ContentType\":\"image/png\",\"Name\":\""+sImageName+"\",\"ParentId\":\""+sWorkOrderID+"\",\"Body\":\"" + file + "\"}");

		// creating an event for workorder with 1 hour
		sDeviceDate = commonUtility.getDeviceDate().split(" ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Calendar now = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, Integer.parseInt(sDeviceDate[2].trim()));
		cal.set(Calendar.YEAR, Integer.parseInt(sDeviceDate[5].trim()));
		cal.set(Calendar.MONTH, new SimpleDateFormat("MMM").parse(sDeviceDate[1].trim()).getMonth());
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sDeviceDate[3].trim().substring(0, 2)));
		cal.set(Calendar.MINUTE,0);
		String sStartDateTime = sdf.format(cal.getTime());
		cal.add(Calendar.HOUR, 1);
		String sEndDateTime = sdf.format(cal.getTime());

		sObjectApi = "SVMXC__SVMX_Event__c?";
		sJsonData = "{\"Name\": \"Sanity12\",\"SVMXC__Service_Order__c\": \"" + sWorkOrderID + "\",\"SVMXC__Technician__c\": \"" + sTechName + "\",\"SVMXC__StartDateTime__c\": \"" + sStartDateTime + "\", \"SVMXC__EndDateTime__c\":\"" + sEndDateTime + "\",\"SVMXC__WhatId__c\": \"" + sWorkOrderID
				+ "\"}";

		String sObjecteventID = restServices.restCreate(sObjectApi, sJsonData);
		sSqlAccQuery = "SELECT+Name+from+SVMXC__SVMX_Event__c+Where+id+=\'" + sObjecteventID + "\'";
		String sEventName = restServices.restGetSoqlValue(sSqlAccQuery, "Name");
		System.out.println(sEventName);

		genericLib.executeSahiScript("Sanity12_prerequisite.sah", sTestID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
	}

	@Test(retryAnalyzer = Retry.class)
	public void RS_10556() throws Exception {
		prerequisite();
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		ph_MorePo.syncData(commonUtility);
		ph_WorkOrderPo.navigatetoWO(commonUtility, ph_ExploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWOName);
		ph_WorkOrderPo.selectAction(commonUtility, sActionsName);
		commonUtility.gotToTabHorizontal("PARTS");
		int partsCount=Integer.parseInt(ph_WorkOrderPo.getEleWorkDetailCount().getText().replaceAll("[^0-9]", ""));
		Assert.assertTrue(partsCount==3, "Parts count is not matching after creation from server.");
		ExtentManager.logger.log(Status.PASS, "Parts added from server and displayed count is matching. Expected : 3, Actual : "+partsCount);
		commonUtility.gotToTabHorizontal("LABOR");
		int laborCount=Integer.parseInt(ph_WorkOrderPo.getEleWorkDetailCount().getText().replaceAll("[^0-9]", ""));
		Assert.assertTrue(laborCount==2, "Labor count is not matching after creation from server.");
		ExtentManager.logger.log(Status.PASS, "Labor added from server and displayed count is matching. Expected : 2, Actual : "+laborCount);
		commonUtility.gotToTabHorizontal("EXPENSES");
		int expensesCount=Integer.parseInt(ph_WorkOrderPo.getEleWorkDetailCount().getText().replaceAll("[^0-9]", ""));
		Assert.assertTrue(expensesCount==4, "Expenses count is not matching after creation from server.");
		ExtentManager.logger.log(Status.PASS, "Expenses added from server and displayed count is matching. Expected : 4, Actual : "+expensesCount);
		commonUtility.gotToTabHorizontal("IMAGES & VIDEOS");
		int attachmentCount=Integer.parseInt(ph_WorkOrderPo.getEleWorkDetailCount().getText().replaceAll("[^0-9]", ""));
		Assert.assertTrue(attachmentCount==1, "Attachment count is not matching after creation from server.");
		ExtentManager.logger.log(Status.PASS, "Attachment added from server and displayed count is matching. Expected : 1, Actual : "+attachmentCount);
		ph_WorkOrderPo.getEleAttachedImage().click();
		String dbImageName=ph_WorkOrderPo.getEleImageTitle().getText();
		Assert.assertTrue(sImageName.equals(dbImageName)," Image names are not matching");
		ExtentManager.logger.log(Status.PASS, "Image names are matching. Expected : "+sImageName+" , Actual : "+dbImageName);
		ph_WorkOrderPo.getEleBackButton().click();
		ph_WorkOrderPo.addParts(commonUtility, sProductName);
		int newWorkDetailCount=Integer.parseInt(ph_WorkOrderPo.getEleWorkDetailCount().getText().replaceAll("[^0-9]", ""));
		Assert.assertTrue(newWorkDetailCount==partsCount+1," Parts count is not matching after adding");
		ExtentManager.logger.log(Status.PASS, "Parts count is matching after adding from app. Expected : "+(partsCount+1)+" , Actual : "+newWorkDetailCount);
		commonUtility.gotToTabHorizontal("EXPENSES");
		commonUtility.longPress(ph_WorkOrderPo.getEleWorkDetailsItem());
		ph_WorkOrderPo.getEleWorkDetailsItemCheckbox().click();
		ph_WorkOrderPo.geteleDelete().click();
		Thread.sleep(2000);
		ph_WorkOrderPo.geteleDelete().click();
		ph_WorkOrderPo.getEleBackButton().click();
		newWorkDetailCount=Integer.parseInt(ph_WorkOrderPo.getEleWorkDetailCount().getText().replaceAll("[^0-9]", ""));
		Assert.assertTrue(newWorkDetailCount==expensesCount-1," Expenses count is not matching after deleting");
		ExtentManager.logger.log(Status.PASS, "Expenses count is matching after deleting from app. Expected : "+(expensesCount-1)+" , Actual : "+newWorkDetailCount);
		ph_WorkOrderPo.getElesave().click();
		ph_MorePo.getEleDataSync().click();
		commonUtility.waitForElementNotVisible(ph_MorePo.getEleSmartSync(), 15);
		if(commonUtility.isDisplayedCust(ph_MorePo.getEleDataSynccompleted())) {
			System.out.println("Data Sync Completed Sucessfully");
			ExtentManager.logger.log(Status.INFO,"Data Sync is successfull");
		}else {
			System.out.println("Data Sync Failed");
			//Verification of successful sync
			ExtentManager.logger.log(Status.FAIL,"Data Sync Failed");
			Assert.assertTrue(2<1, "Data Sync Failed");
		}
		
		commonUtility.press(ph_MorePo.getEleMoreBtn().getLocation());
		expensesCount= expensesCount - 1;
		partsCount=partsCount + 1;
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.VerifyEventInCalender(commonUtility, "Sanity12");
		

		
		//query for extracting work detail by line type wise and verifying the count with app
		String temp=restServices.restGetSoqlValue("SELECT count(Id),SVMXC__Line_Type__c FROM SVMXC__Service_Order_Line__c WHERE SVMXC__Service_Order__c = '"+sWorkOrderID+"' Group by SVMXC__Line_Type__c", "allvalues");
		JSONArray arr=new JSONArray(temp);
		Iterator ite=arr.iterator();
		Map<String,Integer> workDetails= new HashMap<>();
		while(ite.hasNext()) {
			JSONObject obj=(JSONObject) ite.next();
			workDetails.put(obj.getString("SVMXC__Line_Type__c"), obj.getInt("expr0"));
		}
		Assert.assertTrue(partsCount==workDetails.get("Parts"), "Parts count is not matching after deleting from app.");
		ExtentManager.logger.log(Status.PASS, "Parts are correctly synced to server after deleting from app. Expected : "+partsCount+", Actual : "+workDetails.get("Parts"));
		Assert.assertTrue(laborCount==workDetails.get("Labor"), "Labor count is not matching after deleting from app.");
		ExtentManager.logger.log(Status.PASS, "Labor are correctly synced to server after deleting from app. Expected : "+laborCount+", Actual : "+workDetails.get("Labor"));
		Assert.assertTrue(partsCount==workDetails.get("Expenses"), "Expenses count is not matching after deleting from app.");
		ExtentManager.logger.log(Status.PASS, "Expenses are correctly synced to server after deleting from app. Expected : "+expensesCount+", Actual : "+workDetails.get("Expenses"));
		int dbAttachmentCount=Integer.parseInt(restServices.restGetSoqlValue("SELECT count(Id) FROM Attachment WHERE ParentId ='"+sWorkOrderID+"'", "expr0"));
		Assert.assertTrue(dbAttachmentCount==attachmentCount,"Attachment count is not matching after deleting from app.");
		ExtentManager.logger.log(Status.PASS, "Attachments are correctly synced to server after deleting from app. Expected : "+attachmentCount+", Actual : "+dbAttachmentCount);
		
		//deleting some work details from server
		restServices.restDeleterecord("SVMXC__Service_Order_Line__c", sPartId1);
		restServices.restDeleterecord("SVMXC__Service_Order_Line__c", sLaborId1);
		
		//verification of deleted workdetails in app after data sync
		ph_MorePo.syncData(commonUtility);
		ph_RecentsItemsPo.selectRecentsItem(commonUtility, sWOName);
		commonUtility.gotToTabHorizontal("PARTS");
		partsCount=Integer.parseInt(ph_WorkOrderPo.getEleWorkDetailCount().getText().replaceAll("[^0-9]", ""));
		Assert.assertTrue(partsCount==workDetails.get("Parts") - 1, "Parts count is not matching after deletind from server");
		ExtentManager.logger.log(Status.PASS, "Parts are correctly synced after deleting from server. Expected : 3, Actual : "+partsCount);
		commonUtility.gotToTabHorizontal("LABOR");
		laborCount=Integer.parseInt(ph_WorkOrderPo.getEleWorkDetailCount().getText().replaceAll("[^0-9]", ""));
		Assert.assertTrue(laborCount==workDetails.get("labor") - 1, "Labor count is not matching after deleting from server");
		ExtentManager.logger.log(Status.PASS, "Labor are correctly synced after deleting from server. Expected : 2, Actual : "+laborCount);
		
		genericLib.executeSahiScript("Sanity12_postcleanup.sah", sTestID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
	}
}
