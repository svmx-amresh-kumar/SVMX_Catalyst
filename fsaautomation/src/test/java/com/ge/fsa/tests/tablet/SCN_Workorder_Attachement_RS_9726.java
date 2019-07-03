/*
 *  @author Abhijith Roy
 */
package com.ge.fsa.tests.tablet;


import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SCN_Workorder_Attachement_RS_9726 extends BaseLib {
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal = null;
	String sEditProcessName = null;
	String sSection1Name="Section One";
	String sSheetName =null;
	String sWORecordID = null;
	String sWORecordID1 = null;
	String sWORecordID2 = null;
	String sWOName2 = null;
	String sWOName1 = null;
	String sWOName = null;
	

	public void prereq() throws Exception
	{	
		sSheetName ="RS_9726";
		System.out.println("SCN_Workoreder_Attachment_RS-9726");
		sTestCaseID = "SCN_Workoreder_Attachment_RS-9726";
		sCaseWOID = "Data_SCN_Workoreder_Attachment_RS-9726";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
		
		
		  //WorkOrder for Choose from Library 
		
		  sWORecordID =restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID); sWOName= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'",
		  "Name"); System.out.println("WO no =" + sWOName); //sWOName = "WO-00004603";
		  
		  //Work Order for Record Video 
		  sWORecordID1 =restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID1); sWOName1= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID1 +
		  "\'", "Name"); System.out.println("WO no =" + sWOName1);
		  
		  //Work Order for Take Photo 
		  sWORecordID2 =restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID2); sWOName2= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID2 +
		  "\'", "Name"); System.out.println("WO no =" + sWOName2);
		 
		 	
	
	     }
	 @Test(retryAnalyzer=Retry.class)
	     public void RS_10584() throws Exception {

		// Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		prereq();
		// Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		
/*------------------------------------------option for choose from library -------------------------------------------------------*/
		//Set the setting SET007 to True
		
		  commonUtility.executeSahiScript(
		  "appium/SCN_option_to_remove_Choosefromlib_True.sah"); 
		/*
		 * commonUtility.executeSahiScript("appium/Checklist_preRequisite.sah",
		 * sTestCaseID); 

		 * ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID +
		 * "Sahi verification is successful");
		 * 
		 */
		  ExtentManager.logger.log(Status.PASS,"Setting SET007 has been set to True");
		  
		  //perform a config sync
  		  toolsPo.configSync(commonUtility);
  		
  		  //navigating to Workorder
  		
	      workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
	    
		  // Navigate to Field Service process
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		
		  // Navigating to the WO Attachment section
		  commonUtility.tap(workOrderPo.getEleAttacheddoc());
		  commonUtility.tap(workOrderPo.getEleAddAttachedImagesLnk());
		  commonUtility.switchContext("WebView");
		  Thread.sleep(CommonUtility.iLowSleep);
		  System.out.println(checklistPo.getElechoosefromlib().isDisplayed());
		  
		  //Checking for Option Choose from Library
		  
		  Assert.assertFalse(commonUtility.waitforElement(checklistPo.getElechoosefromlib(),3), "Choose from Library is not displayed");
		  ExtentManager.logger.log(Status.PASS, "Option Choose from Library is Not displayed as per setting True");
		  
		  lauchNewApp("true");
		
		//Set the setting SET007 to False
		  commonUtility.executeSahiScript(
		  "appium/SCN_option_to_remove_Choosefromlib_False.sah");
		 

				   
		  ExtentManager.logger.log(Status.PASS,"Setting SET007 has been set to Flase");
				  
		  //perform a config sync
		  toolsPo.configSync(commonUtility);
		  
		  //navigating to Workorder
	  		
	      workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
	    
		  // Navigate to Field Service process
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		
		  // Navigating to the WO Attachment section
		  commonUtility.tap(workOrderPo.getEleAttacheddoc());
		  commonUtility.tap(workOrderPo.getEleAddAttachedImagesLnk());
		  commonUtility.switchContext("WebView");
		  Thread.sleep(CommonUtility.iLowSleep);
		  System.out.println(checklistPo.getElechoosefromlib().isDisplayed());
		  
		  //Checking for Option Choose from Library
		  Assert.assertTrue(commonUtility.waitforElement(checklistPo.getElechoosefromlib(), 3), "Choose from Library is displayed");
		  ExtentManager.logger.log(Status.PASS, "Option Choose from Library is displayed as per setting False");
		  lauchNewApp("true");
		  
/*-----------------------------------Upload from Library------------------------------------*/
		  // Navigation to WO
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		
		  // Navigate to Field Service process
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		  
		 // Navigating to the WO Attachment section
		  workOrderPo.WorkorderAttach(commonUtility, "Choose from Library");
		  Thread.sleep(CommonUtility.iHighSleep);
		  commonUtility.tap(workOrderPo.getEleSaveLnk());
		  ExtentManager.logger.log(Status.PASS, "Work Order Attachment Choose from library added sucessfull");
		  Thread.sleep(CommonUtility.iHighSleep);
		
		

/*--------------------------------Take Video---------------------------------------*/
		  lauchNewApp("true");
		   workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1);
				
		  // Navigate to Field Service process
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
			
		 // Navigating to the WO Attachment section
		  workOrderPo.WorkorderAttach(commonUtility, "Take Video");
		  Thread.sleep(CommonUtility.iHighSleep);
		  commonUtility.tap(workOrderPo.getEleSaveLnk());
		  ExtentManager.logger.log(Status.PASS, "Work Order Attachment Take video added sucessfull");
		  Thread.sleep(CommonUtility.iHighSleep);	

/*------------------------------------------Take Photo ---------------------------------------------*/
		  lauchNewApp("true");

          workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName2);
				
		  // Navigate to Field Service process
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		
		 // Navigating to the WO Attachment section
		  workOrderPo.WorkorderAttach(commonUtility, "Take Photo");
		  Thread.sleep(CommonUtility.iHighSleep);
		  commonUtility.tap(workOrderPo.getEleSaveLnk());
		  ExtentManager.logger.log(Status.PASS, "Work Order Attachment Take Photo added sucessfull");
		  Thread.sleep(CommonUtility.iHighSleep);
				
				
				
				

//--------------------------------------SERVER SIDE VALIDATIONS=============--------------------------------------======//
		  //Navigating back to WorkOrder Screen as Tools button will not be visible from checklists
		  //checklistPo.navigateBacktoWorkOrder(commonUtility);	
		  toolsPo.syncData(commonUtility);
		  Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.i30SecSleep);
				
		  System.out.println("Validating if  attachment is syned to server.");
		  Thread.sleep(CommonUtility.iAttachmentSleep);
		  Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.iMedSleep);
		  String sSoqlAttachment = "SELECT Id FROM Attachment where ParentId in(select id from SVMXC__Service_Order__c where name =\'"+sWOName+"\')"; 
		  //String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
		  restServices.getAccessToken();
		  String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlAttachment, "Id");	
		  assertNotNull(sAttachmentIDAfter); 
		  ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
		  String sSoqlAttachmentName = "SELECT Name FROM Attachment where ParentId in(select id from SVMXC__Service_Order__c where name  =\'"+sWOName+"\')"; 
		  String sAttachmentNameAfter = restServices.restGetSoqlValue(sSoqlAttachmentName, "Name");
		  ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter);
				
				
		  System.out.println("Validating if  attachment is syned to server.");
		  Thread.sleep(60000);
		  Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.iMedSleep);
		  String sSoqlAttachment1 = "SELECT Id FROM Attachment where ParentId in(select id from SVMXC__Service_Order__c where name =\'"+sWOName1+"\')"; 
		  //String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
		  restServices.getAccessToken();
		  String sAttachmentIDAfter1 = restServices.restGetSoqlValue(sSoqlAttachment1, "Id");	
		  assertNotNull(sAttachmentIDAfter1); 
		  ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
		  String sSoqlAttachmentName1 = "SELECT Name FROM Attachment where ParentId in(select id from SVMXC__Service_Order__c where name  =\'"+sWOName1+"\')"; 
		  String sAttachmentNameAfter1 = restServices.restGetSoqlValue(sSoqlAttachmentName1, "Name");
		  ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter1);
		  
			
		  System.out.println("Validating if  attachment is syned to server.");
		  Thread.sleep(CommonUtility.iAttachmentSleep);
		  Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.iMedSleep);
		  String sSoqlAttachment2 = "SELECT Id FROM Attachment where ParentId in(select id from SVMXC__Service_Order__c where name =\'"+sWOName2+"\')"; 
		  //String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
		  restServices.getAccessToken();
		  String sAttachmentIDAfter2 = restServices.restGetSoqlValue(sSoqlAttachment2, "Id");	
		  assertNotNull(sAttachmentIDAfter2); 
		  ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
		  String sSoqlAttachmentName2 = "SELECT Name FROM Attachment where ParentId in(select id from SVMXC__Service_Order__c where name  =\'"+sWOName2+"\')"; 
		  String sAttachmentNameAfter2 = restServices.restGetSoqlValue(sSoqlAttachmentName2, "Name");
		  ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter2);
}

	
}
