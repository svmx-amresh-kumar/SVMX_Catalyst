/*@Author Abhijith Roy
 * Please do this first
 * For test case image compression as None , getting size of image before upload is not possible, so added testimage in resource, need to perform a air drop as Pre requisites
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

public class SCN_ImageCompression_RS_12341 extends BaseLib {
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
	String sWORecordID3 = null;
	String sWOName3 = null;
	String sWOName2 = null;
	String sWOName1 = null;
	String sWOName = null;
	String sLow="Low";
	String sNone="None";
	String sHigh="High";
	String sMedium="Medium";
	int iSizeBeforeCompression = 738780;
	Boolean bProcessCheckResult  = false;
	String sScriptName="Scenario_RS10584_Checklist_Attachments";
	//Attachment questions
	
	String sAttachmentQuestion1 = null;
	String sAttachText ="AttachmentChecklistupload";
	String sAttachmentQ = "AttachmentQuestion1";


	public void prereq() throws Exception
	{	
		
		sSheetName ="RS_12341";
		System.out.println("SCN_RS_12341_Image_Compression");
		sTestCaseID = "SCN_ChecklistAttachment_RS-12341";
		sCaseWOID = "Data_SCN_ChecklistAttachment_RS-12341";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
		
		
		//Work Order for Value None 
		sWORecordID =restServices.restCreate("SVMXC__Service_Order__c?",
		"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"); 
		System.out.println(sWORecordID); sWOName= restServices.restGetSoqlValue(
		"SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'",
		"Name"); System.out.println("WO no =" + sWOName); //sWOName = "WO-00004603";
		 
		
		  //Work Order for Value Low 
		  sWORecordID1=restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID1); sWOName1= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID1 +
		  "\'", "Name"); System.out.println("WO no =" + sWOName1);
		  
		  
		  //Work Order for Value High
		  sWORecordID2=restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID2); sWOName2= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID2 +
		  "\'", "Name"); System.out.println("WO no =" + sWOName2);
		  
		  
		  //Work Order for Value Medium
		  sWORecordID3=restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID3); sWOName3= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID3 +
		  "\'", "Name"); System.out.println("WO no =" + sWOName3);
		 
		  bProcessCheckResult =commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);
		 
		 	}
	
	     @Test(retryAnalyzer=Retry.class)

	     public void RS_12341() throws Exception {
	    	// JiraLink
	 		if (BaseLib.sOSName.equalsIgnoreCase("ios")) {
	 			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12341");
	 		} else {
	 			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12566");

	 		}

		 // Pre Login to app
		 loginHomePo.login(commonUtility, exploreSearchPo);
		 prereq();
		 toolsPo.OptionalConfigSync(toolsPo, commonUtility, bProcessCheckResult);
		 // Data Sync for WO's created
		 toolsPo.syncData(commonUtility);
		 Thread.sleep(CommonUtility.iMedSleep);
		
/*------------------------------------------------ Image Compression Set to None-----------------------------------*/
		
	    
		  
		  commonUtility.tap(toolsPo.getEleToolsIcn());
		  commonUtility.setPickerWheelValue(toolsPo.getimagecompression(), "None");
		  ExtentManager.logger.log(Status.INFO,"The image compression is set to None");
		  
		  // Navigation to WO 
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,sExploreSearch, sExploreChildSearchTxt, sWOName);
		  
		  // Navigate to Field Service process 
		  workOrderPo.selectAction(commonUtility,sFieldServiceName);
		  
		  // Navigating to the checklist
		  commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility,sAttachmentQ);
		  ExtentManager.logger.log(Status.INFO,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput(
		  "AttachmentQuestion1"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys(
		  "AttachmentChecklistupload"); commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  ExtentManager.logger.log(Status.INFO,
		  "Checklist is submitted sucessfully for Work Order" + sWOName);
		  Thread.sleep(CommonUtility.iHighSleep);
		  
		  //server side validation //Navigating back to Work order
		  commonUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());
		  commonUtility.tap(toolsPo.getEleToolsIcn());
		  toolsPo.syncData(commonUtility); Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.i30SecSleep);
		  System.out.println("Validating if  attachment is syned to server.");
		  Thread.sleep(CommonUtility.iAttachmentSleep);
		  Thread.sleep(CommonUtility.i30SecSleep); Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.iMedSleep);
		  
		  String sSoqlchecklistid3="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name = \'"+sWOName+"\')"; 
		  String schecklistid3 =restServices.restGetSoqlValue(sSoqlchecklistid3, "Id"); 
		  String sSoqlAttachment3 ="SELECT Id FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid3+"\')"; 
		  //String sSoqlqueryAttachment ="Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')"; 
		  restServices.getAccessToken(); 
		  String sAttachmentIDAfter3 =restServices.restGetSoqlValue(sSoqlAttachment3, "Id");
		  assertNotNull(sAttachmentIDAfter3);
		  ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
		  String sSoqlAttachmentName3 ="SELECT Name, BodyLength FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid3+"\')"; 
		  String sAttachmentNameAfter3 =restServices.restGetSoqlValue(sSoqlAttachmentName3, "Name");
		  ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter3);
		  
		  //Getting the size after compression 
		  String sSizeAftercompression3 =restServices.restGetSoqlValue(sSoqlAttachmentName3, "BodyLength"); 
		  int iSizeaftercompression3 = Integer.parseInt(sSizeAftercompression3);
		  System.out.println(iSizeaftercompression3);
		  ExtentManager.logger.log(Status.INFO,"The image size before compression is "+iSizeBeforeCompression);
		  ExtentManager.logger.log(Status.INFO,"The image size after compression is "+sSizeAftercompression3);
		  
		  //Comparing the input and output size
		  
		  if(iSizeaftercompression3==iSizeBeforeCompression) {
		  
		  ExtentManager.logger.log(Status.PASS,"Image compression is successful and size of the image is as per setting ---None ");
		  
		  
		  }else {
		  
		  ExtentManager.logger.log(Status.FAIL,"Image compression is Not as per setting --None"); }
		 
		  
	
		/*-------------------------------- Image Compression Set to Low-----------------------------------*/
		
	    
		 lauchNewApp("true");
		 commonUtility.tap(toolsPo.getEleToolsIcn());
		 commonUtility.setPickerWheelValue(toolsPo.getimagecompression(), "Low");
		 ExtentManager.logger.log(Status.INFO,"The image compression is set to Low");
		
		 // Navigation to WO
		 workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1);
				
		 // Navigate to Field Service process
		 workOrderPo.selectAction(commonUtility, sFieldServiceName);
				
		 // Navigating to the checklist
		 commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
		 Thread.sleep(CommonUtility.iLowSleep);
		 checklistPo.checklistAttach(commonUtility,"Choose from Library",sAttachmentQ);
		 ExtentManager.logger.log(Status.INFO, "Checklist Attachment Choose from library added sucessfull");
		 commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
		 checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
		 commonUtility.tap(checklistPo.geteleNext());
		 commonUtility.tap(checklistPo.eleChecklistSubmit());
		 commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		 ExtentManager.logger.log(Status.INFO, "Checklist is submitted sucessfully for Work Order" + sWOName1);
		 Thread.sleep(CommonUtility.iHighSleep);
		
		 //server side validation
		 //Navigating back to Work order
		 commonUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());
		 commonUtility.tap(toolsPo.getEleToolsIcn());
	    
		 toolsPo.syncData(commonUtility);
		
		 Thread.sleep(CommonUtility.i30SecSleep); Thread.sleep(CommonUtility.i30SecSleep);
		 System.out.println("Validating if  attachment is syned to server.");
		 Thread.sleep(CommonUtility.iAttachmentSleep);
		 Thread.sleep(CommonUtility.i30SecSleep); 
		 Thread.sleep(CommonUtility.i30SecSleep);
		 Thread.sleep(CommonUtility.iMedSleep);
		  
		 String sSoqlchecklistid ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name = \'"+sWOName1+"\')";
		 String schecklistid = restServices.restGetSoqlValue(sSoqlchecklistid, "Id");	
		 String sSoqlAttachment = "SELECT Id FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid+"\')"; 
		 //String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
		 restServices.getAccessToken();
		 String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlAttachment, "Id");	
		 assertNotNull(sAttachmentIDAfter); 
		 ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
		 String sSoqlAttachmentName = "SELECT Name, BodyLength FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid+"\')"; 
		 String sAttachmentNameAfter = restServices.restGetSoqlValue(sSoqlAttachmentName, "Name");
		 ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter);
		  
		 //Getting the size after compression
		 String sSizeAftercompression = restServices.restGetSoqlValue(sSoqlAttachmentName, "BodyLength");
		 int iSizeaftercompression = Integer.parseInt(sSizeAftercompression);
		 System.out.println(iSizeaftercompression);
		 ExtentManager.logger.log(Status.INFO,"The image size before compression is " + iSizeBeforeCompression);
		 ExtentManager.logger.log(Status.INFO,"The image size after compression is "+ sSizeAftercompression);
		  
		 //Comparing the input and output size
		  
		 if(iSizeaftercompression<500000) {
			
		 ExtentManager.logger.log(Status.PASS,"Image compression is successful and size of the image is as per setting ---Low ");
		 
			  
		 }else {
			  
			  ExtentManager.logger.log(Status.FAIL,"Image compression is Not as per setting --Low");
		 }
		  
		  /*-------------------------------- Image Compression Set to High-----------------------------------*/
			
		  lauchNewApp("true");
		  commonUtility.tap(toolsPo.getEleToolsIcn());
		  commonUtility.setPickerWheelValue(toolsPo.getimagecompression(), "High");
		  ExtentManager.logger.log(Status.INFO,"The image compression is set to High");
			
		  // Navigation to WO
		  commonUtility.tap(driver.findElement(By.xpath("//div[text()='Explore']")));
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName2);
					
		  // Navigate to Field Service process
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
					
		  // Navigating to the checklist
		  commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility,"Choose from Library",sAttachmentQ);
		  ExtentManager.logger.log(Status.INFO, "Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  ExtentManager.logger.log(Status.INFO, "Checklist is submitted sucessfully for Work Order" + sWOName2);
		  Thread.sleep(CommonUtility.iHighSleep);
			
		  //server side validation
		  //Navigating back to Work order
		  commonUtility.switchContext("WebView");
		  commonUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());
		  commonUtility.tap(toolsPo.getEleToolsIcn());
		    
		  toolsPo.syncData(commonUtility);
			
		  Thread.sleep(CommonUtility.i30SecSleep); Thread.sleep(CommonUtility.i30SecSleep);
		  System.out.println("Validating if  attachment is syned to server.");
		  Thread.sleep(CommonUtility.iAttachmentSleep);
		  Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.i30SecSleep);
		  Thread.sleep(CommonUtility.iMedSleep);
			  
		  String sSoqlchecklistid1 ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name = \'"+sWOName2+"\')";
		  String schecklistid1 = restServices.restGetSoqlValue(sSoqlchecklistid1, "Id");	
		  String sSoqlAttachment1 = "SELECT Id FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid1+"\')"; 
		  //String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
		  restServices.getAccessToken();
		  String sAttachmentIDAfter1 = restServices.restGetSoqlValue(sSoqlAttachment1, "Id");	
		  assertNotNull(sAttachmentIDAfter1); 
		  ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
		  String sSoqlAttachmentName1 = "SELECT Name, BodyLength FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid1+"\')"; 
		  String sAttachmentNameAfter1 = restServices.restGetSoqlValue(sSoqlAttachmentName1, "Name");
		  ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter1);
			  
		  //Getting the size after compression
		  String sSizeAftercompression1 = restServices.restGetSoqlValue(sSoqlAttachmentName1, "BodyLength");
		  int iSizeaftercompression1 = Integer.parseInt(sSizeAftercompression1);
		  System.out.println(iSizeaftercompression1);
		  ExtentManager.logger.log(Status.INFO,"The image size before compression is "+iSizeBeforeCompression);
		  ExtentManager.logger.log(Status.INFO,"The image size after compression is "+sSizeAftercompression1);
			  
		  //Comparing the input and output size
			  
		  if(iSizeaftercompression1<250000) {
				
		  ExtentManager.logger.log(Status.PASS,"Image compression is successful and size of the image is as per setting ---High ");
			 
				  
		  }else {
				  
		  ExtentManager.logger.log(Status.FAIL,"Image compression is Not as per setting --High");
		}
			  
/*-------------------------------- Image Compression Set to Medium-----------------------------------*/
				
		 lauchNewApp("true");
		 commonUtility.tap(toolsPo.getEleToolsIcn());
		 commonUtility.setPickerWheelValue(toolsPo.getimagecompression(), "Medium");
		 ExtentManager.logger.log(Status.INFO,"The image compression is set to Medium");
				
		 // Navigation to WO
		 commonUtility.tap(driver.findElement(By.xpath("//div[text()='Explore']")));
		 workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName3);
						
		 // Navigate to Field Service process
		 workOrderPo.selectAction(commonUtility, sFieldServiceName);
						
		 // Navigating to the checklist
		 commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
		 Thread.sleep(CommonUtility.iLowSleep);
		 checklistPo.checklistAttach(commonUtility,"Choose from Library",sAttachmentQ);
		 ExtentManager.logger.log(Status.INFO, "Checklist Attachment Choose from library added sucessfull");
		 commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
		 checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
		 commonUtility.tap(checklistPo.geteleNext());
		 commonUtility.tap(checklistPo.eleChecklistSubmit());
		 commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		 ExtentManager.logger.log(Status.INFO, "Checklist is submitted sucessfully for Work Order" + sWOName3);
		 Thread.sleep(CommonUtility.iHighSleep);
				
		 //server side validation
		 //Navigating back to Work order
		 commonUtility.switchContext("WebView");
		 commonUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());
		 commonUtility.tap(toolsPo.getEleToolsIcn());
		 toolsPo.syncData(commonUtility);
		 Thread.sleep(CommonUtility.i30SecSleep); Thread.sleep(CommonUtility.i30SecSleep);
		 System.out.println("Validating if  attachment is syned to server.");
		 Thread.sleep(CommonUtility.iAttachmentSleep);
		 Thread.sleep(CommonUtility.i30SecSleep); 
		 Thread.sleep(CommonUtility.i30SecSleep);
		 Thread.sleep(CommonUtility.iMedSleep);
				  
         String sSoqlchecklistid2 ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name = \'"+sWOName3+"\')";
		 String schecklistid2 = restServices.restGetSoqlValue(sSoqlchecklistid2, "Id");	
		 String sSoqlAttachment2 = "SELECT Id FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid2+"\')"; 
		 //String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
		 restServices.getAccessToken();
		 String sAttachmentIDAfter2 = restServices.restGetSoqlValue(sSoqlAttachment2, "Id");	
		 assertNotNull(sAttachmentIDAfter2); 
		 ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
		 String sSoqlAttachmentName2 = "SELECT Name, BodyLength FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid1+"\')"; 
		 String sAttachmentNameAfter2 = restServices.restGetSoqlValue(sSoqlAttachmentName2, "Name");
		 ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter2);
				  
		 //Getting the size after compression
		 String sSizeAftercompression2 = restServices.restGetSoqlValue(sSoqlAttachmentName2, "BodyLength");
		 int iSizeaftercompression2 = Integer.parseInt(sSizeAftercompression2);
		 System.out.println(iSizeaftercompression2);
		 ExtentManager.logger.log(Status.INFO,"The image size before compression is "+iSizeBeforeCompression);
		 ExtentManager.logger.log(Status.INFO,"The image size after compression is "+sSizeAftercompression2);
				  
		//Comparing the input and output size
				  
		 if(iSizeaftercompression1<150000) {
					
		 ExtentManager.logger.log(Status.PASS,"Image compression is successful and size of the image is as per setting ---Medium ");
				 
					  
		 }else {
					  
					  ExtentManager.logger.log(Status.FAIL,"Image compression is Not as per setting --Medium");
				  }
		
		
	    }}
		
		