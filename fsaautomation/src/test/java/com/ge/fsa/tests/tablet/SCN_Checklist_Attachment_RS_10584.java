/*
 *  @author Vinod Tharavath
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
import com.ge.fsa.lib.GenericLib;
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

public class SCN_Checklist_Attachment_RS_10584 extends BaseLib {
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
	Boolean bProcessCheckResult  = false;

	//Attachment questions
	
	String sAttachmentQuestion1 = null;
	String sAttachText ="AttachmentChecklistupload";
	String sAttachmentQ = "AttachmentQuestion1";


	public void prereq() throws Exception
	{	
		sSheetName ="RS_10584";
		System.out.println("SCN_RS10584_Checklist_Attachment");
		sTestCaseID = "SCN_ChecklistAttachment_RS-10584";
		sCaseWOID = "Data_SCN_ChecklistAttachment_RS_10584";

		// Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");
		
		//WorkOrder for Choose from Library
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);			
		//sWOName = "WO-00004603";
		
		//Work Order for Record Video
		sWORecordID1 = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID1);
		sWOName1= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID1 + "\'", "Name");
		System.out.println("WO no =" + sWOName1);	
		
		//Work Order for Take Photo
		sWORecordID2 = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID2);
		sWOName2= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID2 + "\'", "Name");
		System.out.println("WO no =" + sWOName2);
		bProcessCheckResult =commonsUtility.ProcessCheck(restServices, genericLib, sFieldServiceName, sFieldServiceName, sTestCaseID);		

	
	}
	@Test(retryAnalyzer=Retry.class)
	public void RS_10584() throws Exception {

		// Pre Login to app
		loginHomePo.login(commonsUtility, exploreSearchPo);
		prereq();
	    toolsPo.OptionalConfigSync(toolsPo, commonsUtility, bProcessCheckResult);

		// Data Sync for WO's created
		toolsPo.syncData(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);

/*-----------------------------------Upload from Library------------------------------------*/
		// Navigation to WO
		workOrderPo.navigatetoWO(commonsUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		
		// Navigate to Field Service process
		workOrderPo.selectAction(commonsUtility, sFieldServiceName);
		
		// Navigating to the checklist
		commonsUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
		checklistPo.checklistAttach(commonsUtility, "Choose from Library", sAttachmentQ);
		ExtentManager.logger.log(Status.PASS, "Checklist Attachment Choose from library added sucessfull");
		commonsUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
		checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
		commonsUtility.tap(checklistPo.geteleNext());
		commonsUtility.tap(checklistPo.eleChecklistSubmit());
		commonsUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		ExtentManager.logger.log(Status.PASS, "Checklist is submitted sucessfully for Work Order" + sWOName);
		Thread.sleep(GenericLib.iHighSleep);
		
		// Navigating back to work Orders
		commonsUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());
		Thread.sleep(GenericLib.iHighSleep);
		workOrderPo.selectAction(commonsUtility, sFieldServiceName);
		System.out.println("Tapped on default title for checklist");
		Thread.sleep(GenericLib.iLowSleep);
		commonsUtility.tap(checklistPo.geteleShowCompletedChecklist(), 15, 18);
		commonsUtility.tap(checklistPo.geteleCompletedChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iMedSleep);
		Assert.assertEquals(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").getAttribute("value"),
				sAttachText, "Attachment Text is not "+sAttachText+"displayed");
		ExtentManager.logger.log(Status.PASS, "Attachment text"+sAttachText +"is visible in completed Checklist" + sChecklistName);
		Thread.sleep(GenericLib.iHighSleep);
		commonsUtility.tap(checklistPo.eleChecklistImage(), 20, 20);
		commonsUtility.switchContext("Native");
		
		if (com.ge.fsa.lib.BaseLib.sOSName.contains("android"))
		{
		     ((AndroidDriver<MobileElement>) driver).pressKey(new KeyEvent(AndroidKey.BACK));
		}
		else {
			checklistPo.geteleDoneAttachment().click();
		} 
		
		ExtentManager.logger.log(Status.PASS, "done button was clicked below the image/video");
		commonsUtility.switchContext("WebView");
		checklistPo.navigateBacktoWorkOrder(commonsUtility);
		System.out.println("Upload from Library completed");

/*--------------------------------Take Video---------------------------------------*/
				lauchNewApp("true");
				//driver.activateApp(GenericLib.sAppBundleID);
				commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
				commonsUtility.tap(exploreSearchPo.getEleExploreIcn());

			    workOrderPo.navigatetoWO(commonsUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt,sWOName1);	
			    Thread.sleep(GenericLib.iMedSleep);
				workOrderPo.selectAction(commonsUtility, sFieldServiceName);
				// Navigating to the checklist
				commonsUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
				Thread.sleep(GenericLib.iLowSleep);
				//assertFalse(commonsUtility.isDisplayedCust(checklistPo.eleAttachNew()));
				//ExtentManager.logger.log(Status.PASS,"Attach New link is not visible");
				checklistPo.checklistAttach(commonsUtility, "Take Video",sAttachmentQ);
				System.out.println("Attaching video finished");
				ExtentManager.logger.log(Status.PASS,"Checklist Attachment Take video added sucessfull");

				//assertEquals(checklistPo.eleAttachNew().isDisplayed(),true);
				//ExtentManager.logger.log(Status.PASS,"Attach New link visible after attaching video");
				commonsUtility.switchContext("WebView");
				Thread.sleep(GenericLib.iHighSleep);
				commonsUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
				checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
				commonsUtility.tap(checklistPo.geteleNext());
				commonsUtility.tap(checklistPo.eleChecklistSubmit());
				commonsUtility.tap(checklistPo.geteleChecklistPopupSubmit());
				ExtentManager.logger.log(Status.PASS,"Checklist is submitted sucessfully for Work Order"+sWOName1);

				//Navigating back to work Orders
				commonsUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());
				workOrderPo.selectAction(commonsUtility, sFieldServiceName);
				System.out.println("Tapped on default title for checklist");
				Thread.sleep(GenericLib.iLowSleep);
				commonsUtility.tap(checklistPo.geteleShowCompletedChecklist(),15,18);
				commonsUtility.tap(checklistPo.geteleCompletedChecklistName(sChecklistName));
				
				Assert.assertEquals(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").getAttribute("value"), sAttachText, "Attachment Text is displayed");
				ExtentManager.logger.log(Status.PASS,"Attachment text is visible for video attachment in completed Checklist:"+sChecklistName);
				Thread.sleep(GenericLib.iHighSleep);
				
				commonsUtility.tap(checklistPo.eleChecklistVideo(),20,20);
				commonsUtility.switchContext("Native");
				Thread.sleep(GenericLib.iHighSleep);
				
		if (com.ge.fsa.lib.BaseLib.sOSName.contains("android")) {
			((AndroidDriver<MobileElement>) driver).pressKey(new KeyEvent(AndroidKey.BACK));
		} else {
			try {
				// WebElement eleDoneButton1 =driver.findElementByAccessibilityId("Done");
				// eleDoneButton1.click();
				checklistPo.geteleDoneAccessibilityid().click();

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Did not get the fullscreen video mode.. proceeding with exection");
			}
			try {
				checklistPo.geteleDoneAccessibilityid().click();
				System.out.println("Done button clicked below");
				// WebElement eleDoneButton2 =driver.findElementByAccessibilityId("Done");
				// eleDoneButton2.click();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Did not click done button");
			}
		}
				ExtentManager.logger.log(Status.INFO,"done button was clicked below the video");
				commonsUtility.switchContext("WebView");
				checklistPo.navigateBacktoWorkOrder(commonsUtility);	

				/*--------------------------Take Photo -----------------------*/
				lauchNewApp("true");

				commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
				commonsUtility.tap(exploreSearchPo.getEleExploreIcn());

			    workOrderPo.navigatetoWO(commonsUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt,sWOName2);							
				workOrderPo.selectAction(commonsUtility, sFieldServiceName);
				// Navigating to the checklist
				commonsUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
				Thread.sleep(GenericLib.iLowSleep);
				checklistPo.checklistAttach(commonsUtility, "Take Photo",sAttachmentQ);
				ExtentManager.logger.log(Status.PASS,"Checklist Attachment Take Photo added sucessfull");
				commonsUtility.switchContext("WebView");
				commonsUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
				checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
				commonsUtility.tap(checklistPo.geteleNext());
				commonsUtility.tap(checklistPo.eleChecklistSubmit());
				commonsUtility.tap(checklistPo.geteleChecklistPopupSubmit());
				ExtentManager.logger.log(Status.PASS,"Checklist is submitted sucessfully for Work Order"+sWOName2);

				//Navigating back to work Orders
				commonsUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());
				workOrderPo.selectAction(commonsUtility, sFieldServiceName);
				System.out.println("Tapped on default title for checklist");
				Thread.sleep(GenericLib.iLowSleep);
				commonsUtility.tap(checklistPo.geteleShowCompletedChecklist(),15,18);
				commonsUtility.tap(checklistPo.geteleCompletedChecklistName(sChecklistName));
				
				Assert.assertEquals(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").getAttribute("value"), sAttachText, "Attachment Text is displayed");
				ExtentManager.logger.log(Status.PASS,"Attachment text is visible for take photo in completed Checklist:"+sChecklistName);
				Thread.sleep(GenericLib.iHighSleep);
				commonsUtility.tap(checklistPo.eleChecklistImage(),20,20);
				commonsUtility.switchContext("Native");
				
				if (com.ge.fsa.lib.BaseLib.sOSName.contains("android"))
				{
				     ((AndroidDriver<MobileElement>) driver).pressKey(new KeyEvent(AndroidKey.BACK));
				}
				else {
					checklistPo.geteleDoneAccessibilityid().click();
				} 
				//WebElement eleDoneButton3 =driver.findElementByAccessibilityId("Done");
				//eleDoneButton3.click();
				ExtentManager.logger.log(Status.PASS,"done button was clicked below the photo");
				commonsUtility.switchContext("WebView");
				checklistPo.navigateBacktoWorkOrder(commonsUtility);	
				

				//------------------SERVER SIDE VALIDATIONS===================//
				//Navigating back to WorkOrder Screen as Tools button will not be visible from checklists
				//checklistPo.navigateBacktoWorkOrder(commonsUtility);	
				toolsPo.syncData(commonsUtility);
				Thread.sleep(GenericLib.i30SecSleep);
			  	Thread.sleep(GenericLib.i30SecSleep);
				
				System.out.println("Validating if  attachment is syned to server.");
			  	Thread.sleep(GenericLib.i30SecSleep);
			  	Thread.sleep(GenericLib.i30SecSleep);
			  	Thread.sleep(GenericLib.iMedSleep);
			  	String sSoqlchecklistid ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"+sWOName+"\')";
			  	String schecklistid = restServices.restGetSoqlValue(sSoqlchecklistid, "Id");	
			  	String sSoqlAttachment = "SELECT Id FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid+"\')"; 
			  	//String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
				restServices.getAccessToken();
				String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlAttachment, "Id");	
				assertNotNull(sAttachmentIDAfter); 
				ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
			  	String sSoqlAttachmentName = "SELECT Name FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid+"\')"; 
			  	String sAttachmentNameAfter = restServices.restGetSoqlValue(sSoqlAttachmentName, "Name");
				ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter);
				
				
				String sSoqlchecklistid1 ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"+sWOName1+"\')";
			  	String schecklistid1 = restServices.restGetSoqlValue(sSoqlchecklistid1, "Id");	
			  	String sSoqlAttachment1 = "SELECT Id FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid1+"\')"; 
			  	//String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
				restServices.getAccessToken();
				String sAttachmentIDAfter1 = restServices.restGetSoqlValue(sSoqlAttachment1, "Id");	
				assertNotNull(sAttachmentIDAfter1); 
				ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
			  	String sSoqlAttachmentName1 = "SELECT Name FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid1+"\')"; 
			  	String sAttachmentNameAfter1 = restServices.restGetSoqlValue(sSoqlAttachmentName1, "Name");
				ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter1);
			
				String sSoqlchecklistid2 ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"+sWOName2+"\')";
			  	String schecklistid2 = restServices.restGetSoqlValue(sSoqlchecklistid2, "Id");	
			  	String sSoqlAttachment2 = "SELECT Id FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid2+"\')"; 
			  	//String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
				restServices.getAccessToken();
				String sAttachmentIDAfter2 = restServices.restGetSoqlValue(sSoqlAttachment2, "Id");	
				assertNotNull(sAttachmentIDAfter2); 
				ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
			  	String sSoqlAttachmentName2 = "SELECT Name FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid2+"\')"; 
			  	String sAttachmentNameAfter2 = restServices.restGetSoqlValue(sSoqlAttachmentName2, "Name");
				ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter2);
}

	
}
