package com.ge.fsa.tests.tablet;

	
	/*
	 *  @author Abhijith Roy
	 */


	

	import static org.testng.Assert.assertEquals;
	import static org.testng.Assert.assertNotNull;
	import static org.testng.Assert.assertNull;

import java.util.List;

import org.json.JSONArray;
	import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
	import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

	import com.aventstack.extentreports.Status;
	import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

	public class SCN_Checklist_Opdoc_With_And_Withought_Version_SalesForce_Files extends BaseLib {
		
		String sTestCaseID = null;
		String sCaseWOID = null;
		String sExploreSearch = null;
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
		String sWOName = null;
		String sChecklistName=null;
		String sChecklistOpDocName=null;
		String sScriptName="SCN_Checklist_OPDoc_when_Files_Enabled";
		Boolean bProcessCheckResult  = false; 	
		String sWOName2 = null;
		String sWOName1 = null;
		String sChecklistNameAllVersions = null;
		String sChecklistNameFirstVersion = null;
		String sChecklistNameLastVersion =null;
		String sChecklistOpDocNameForversion=null;
		String sChecklistNameFirstVersions =null;
		String sChecklistNameLastVersions =null;

		String sAttachmentQuestion1 = null;
		String sAttachText ="AttachmentChecklistupload";
		String sAttachmentQ = "AttachmentQuestion1";
		
		
		
		public void prereq() throws Exception
		{	
			sSheetName ="RS_12367";
			System.out.println("SCN_SalesForce_Files_WO_Debrief_RS_12367");
			sTestCaseID = "SCN_Workoreder_Attachment_RS-12367";
			sCaseWOID = "Data_SCN_Workoreder_Attachment_RS-12367";
			
			sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
			sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
			sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
			sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName");
			sChecklistOpDocName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "sChecklistOpDocName");
			sChecklistNameAllVersions = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistAllVersion");
			sChecklistNameFirstVersions = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistFirstVersion");
			sChecklistNameLastVersions = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistLastVersion");
			sChecklistOpDocNameForversion = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "sChecklistOpDocNameVersions");
			
			
			
			//Work Order for Files --upload from library
			
		
		  sWORecordID=restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID); sWOName= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID +
		  "\'","Name"); System.out.println("WO no =" + sWOName); 
		  //sWOName ="WO-00004603";
		  
		  sWORecordID1 =restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID1); sWOName1= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID1 +"\'",
		  "Name"); System.out.println("WO no =" + sWOName1);
		 
		 			  
			//bProcessCheckResult =commonUtility.ProcessCheck(restServices, genericLib, sChecklistOpDocName, sScriptName, sTestCaseID);  
			
	}
		
		public void postscript() throws Exception
		{
			commonUtility.executeSahiScript("appium/SCN_Disabling_Salesforce_Files.sah",sTestCaseID);
			Assert.assertTrue(commonUtility.verifySahiExecution(), "Failed to execute Sahi script");
			ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
		}
		
		@Test(retryAnalyzer=Retry.class)
		public void RS_9726() throws Exception {

			// Pre Login to app
			loginHomePo.login(commonUtility, exploreSearchPo);
			prereq();
			
			// Data Sync for WO's created
			toolsPo.syncData(commonUtility);
		
		   Thread.sleep(CommonUtility.iMedSleep);
		  
		
		
		  commonUtility.executeSahiScript(
		  "appium/SCN_Checklist_OPDoc_when_Files_Enabled.sah", "sTestCaseID");
		  if(commonUtility.verifySahiExecution()) {System.out.println("PASSED"); } else
		  { System.out.println("FAILED");
		  ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID
		  +"Sahi verification failure"); assertEquals(0, 1); }
		  
		  commonUtility.executeSahiScript(
		  "appium/SCN_Checklist_OPDoc_when_Files_Enabled_Versions.sah", "sTestCaseID");
		  if(commonUtility.verifySahiExecution()) {System.out.println("PASSED"); } else
		  { System.out.println("FAILED");
		  ExtentManager.logger.log(Status.FAIL,"Testcase " +
		  sTestCaseID+"Sahi verification failure"); assertEquals(0, 1); }
		  
		  commonUtility.executeSahiScript("appium/SCN_Enabling_Salesforce_Files.sah",
		  "sTestCaseID"); if(commonUtility.verifySahiExecution())
		  {System.out.println("PASSED"); } else { System.out.println("FAILED");
		  ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID +
		  "Sahi verification failure"); assertEquals(0, 1); }
		  
		  ExtentManager.logger.log(Status.PASS,"Setting GBL037 has been set to True");
		  
		  System.out.println("Setting the SET007 to Flase");
		  commonUtility.executeSahiScript(
		  "appium/SCN_option_to_remove_Choosefromlib_False.sah", "sTestCaseID");
		  if(commonUtility.verifySahiExecution()) {System.out.println("PASSED"); } else
		  { System.out.println("FAILED");
		  ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID +
		  "Sahi verification failure"); assertEquals(0, 1); }
		  
		  toolsPo.Resetapp(commonUtility, exploreSearchPo);
		 
		 
		 
		 
		  /*------------------------Validating--opdoc without versioning when Salesforce files are enabled-----------*/		 
		
		
		  //toolsPo.OptionalConfigSync(toolsPo, commonUtility, bProcessCheckResult);
		  
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch,sExploreChildSearchTxt, sWOName); 
		  workOrderPo.selectAction(commonUtility,sFieldServiceName);
		  commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility, "Choose from Library",sAttachmentQ); 
		  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  ExtentManager.logger.log(Status.PASS,"Checklist is submitted sucessfully for Work Order" + sWOName);
		  System.out.println("Checklist is submitted sucessfully");
		  Thread.sleep(CommonUtility.iHighSleep);
		  checklistPo.navigateBacktoWorkOrder(commonUtility);
		  
		  
		  //Server validation 
		  toolsPo.syncData(commonUtility);
		  Thread.sleep(CommonUtility.i30SecSleep); 
		  Thread.sleep(CommonUtility.i30SecSleep);
		  System.out.println("Validating if  File is syned to server.");
		  Thread.sleep(60000); 
		  Thread.sleep(CommonUtility.i30SecSleep);
		  
		  String sSoqlchecklistid ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"+sWOName+"\')"; 
		  String schecklistid = restServices.restGetSoqlValue(sSoqlchecklistid, "Id"); 
		  String sContentDocLinkId = "SELECT SVMXC__SM_ContentDocumentLink_ID__c FROM SVMXC__SM_Checklist_Attachment__c  where (SVMXC__SM_Checklist__c= \'" +schecklistid+"\')"; 
		  String sContentDocLinkIdafter = restServices.restGetSoqlValue(sContentDocLinkId, "SVMXC__SM_ContentDocumentLink_ID__c"); 
		  String sSoqlFile ="select Id,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (id = \'" +sContentDocLinkIdafter+"\')"; 
		  String sSoqlFileafter =restServices.restGetSoqlValue(sSoqlFile, "Id");
		  assertNotNull(sSoqlFileafter);
		  System.out.println(sSoqlFile);
		  ExtentManager.logger.log(Status.PASS,"File is synced to Server successfully when choose from library is selected");  
		  String sContentdocid = "select ContentDocumentId from ContentDocumentLink where (id = \'" +sContentDocLinkIdafter+"\')";
		  String sContentdocidafter =restServices.restGetSoqlValue(sContentdocid, "ContentDocumentId"); 
		  String sFileTitle = "select Title from ContentDocument where Id=\'"+sContentdocidafter+"\'";
		  String sFileTitleafter = restServices.restGetSoqlValue(sFileTitle, "Title");
		  System.out.println(sFileTitleafter);
		  ExtentManager.logger.log(Status.INFO,"File uplaoded is"+sFileTitleafter);
		  
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,
		  sExploreSearch,sExploreChildSearchTxt, sWOName);
		  checklistPo.validateChecklistServiceReport(commonUtility, workOrderPo,sChecklistOpDocName,sWOName,sFileTitleafter);
		  System.out.println("OPDoc validation submitted successfully");
		  commonUtility.tap(workOrderPo.getEleDoneLnk());
		  Thread.sleep(CommonUtility.i30SecSleep);
		  ((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		  Thread.sleep(CommonUtility.iHighSleep);
		  ((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		  Thread.sleep(CommonUtility.i30SecSleep); 
		  toolsPo.syncData(commonUtility);
		  Thread.sleep(CommonUtility.iMedSleep);
		  Thread.sleep(CommonUtility.iMedSleep);
		  Thread.sleep(20000); 
		  System.out.println("Opdoc is synced to server");
		  String sSoqlWorkorderid2 = "select id from SVMXC__Service_Order__c where name =\'"+sWOName+"\'"; 
		  String sSoqlWorkorderidafter2 = restServices.restGetSoqlValue(sSoqlWorkorderid2, "Id"); 
		  String sSoqlFile2 = "select Id,LinkedEntityID,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter2+"\')"; 
		  String sSoqlFileafter2 =restServices.restGetSoqlValue(sSoqlFile2, "Id");
		  assertNotNull(sSoqlFileafter2); System.out.println(sSoqlFile2);
		  ExtentManager.logger.log(Status.PASS,"Opdoc is synced to server sucessfully when Files are enabled");
		  String sContentdocid2 ="select ContentDocumentId from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter2+"\')"; 
		  //System.out.println(sContentdocid); 
		  String sContentdocidafter2 = restServices.restGetSoqlValue(sContentdocid2,"ContentDocumentId");
		  String sFileTitle2 = "select Title from ContentDocument where Id=\'"+sContentdocidafter2+"\'";
		  String sFileTitleafter2 = restServices.restGetSoqlValue(sFileTitle2, "Title");
		  System.out.println(sFileTitleafter2);
		  ExtentManager.logger.log(Status.INFO,"File uplaoded is"+sFileTitleafter2);
		  Assert.assertTrue(sFileTitleafter2.contains("pdf") ,"fileformat stored is not pdf");
		  ExtentManager.logger.log(Status.PASS,"file is in pdf format in server");
		 
		 			
		 
			
			/*------------------------Validating--opdoc with versioning when Salesforce files are enabled-----------*/
		
		//First Checklist sumitting For All versions
		  
		  lauchNewApp("true");
		  
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,
		  sExploreSearch,sExploreChildSearchTxt, sWOName1);
		  workOrderPo.selectAction(commonUtility,sFieldServiceName);
		  commonUtility.tap(checklistPo.geteleChecklistName(sChecklistNameAllVersions));
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility, "Choose from Library","AttachmentQuestionAllVersion");
		  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestionAllVersion"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestionAllVersion").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  System.out.println("First Checklist sumitted successfully For All versions");
		  ExtentManager.logger.log(Status.INFO,"First Checklist sumitted successfully For All versions");
		  
		//Second Checklist sumitting For All versions
		  lauchNewApp("true");
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,
		  sExploreSearch,sExploreChildSearchTxt, sWOName1);
		  workOrderPo.selectAction(commonUtility,sFieldServiceName);
		  Thread.sleep(CommonUtility.iLowSleep);
		  commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameAllVersions),20,20); 
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility, "Choose from Library","AttachmentQuestionAllVersion"); 
		  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestionAllVersion"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestionAllVersion").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  System.out.println("Second Checklist sumitted successfully For All versions");
		  ExtentManager.logger.log(Status.INFO,"Second Checklist sumitted successfully For All versions");
		  
		//Third Checklist sumitting For All versions
		  lauchNewApp("true"); 
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,sExploreSearch, sExploreChildSearchTxt, sWOName1);
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		  Thread.sleep(CommonUtility.iLowSleep);
		  commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameAllVersions),20,20); 
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility, "Choose from Library", "AttachmentQuestionAllVersion"); 
		  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestionAllVersion"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestionAllVersion").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  Thread.sleep(CommonUtility.iHighSleep);
		  System.out.println("Third Checklist sumitted successfully For All versions");
		  ExtentManager.logger.log(Status.INFO,"Third Checklist sumitted successfully For All versions");
		 
		//First Checklist sumitting For First versions
		  lauchNewApp("true");
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch,
		  sExploreChildSearchTxt, sWOName1); 
		  workOrderPo.selectAction(commonUtility,sFieldServiceName); 
		  Thread.sleep(CommonUtility.iLowSleep);
		  commonUtility.tap(checklistPo.geteleChecklistName(sChecklistNameFirstVersions)); 
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility, "Choose from Library","AttachmentQuestionFirstVersion"); 
		  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestionFirstVersion"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestionFirstVersion").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  System.out.println("First Checklist sumitted successfully For First versions");
		  ExtentManager.logger.log(Status.INFO,"First Checklist sumitted successfully For First versions");
		  
		//Second Checklist sumitting For First versions
		  lauchNewApp("true"); 
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,sExploreSearch, sExploreChildSearchTxt, sWOName1);
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		  Thread.sleep(CommonUtility.iLowSleep);
		  commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameFirstVersions),20,20); 
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility, "Choose from Library","AttachmentQuestionFirstVersion"); 
		  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestionFirstVersion"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestionFirstVersion").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  Thread.sleep(CommonUtility.iHighSleep);
		  System.out.println("Second Checklist sumitted successfully For First versions");
		  ExtentManager.logger.log(Status.INFO,"Second Checklist sumitted successfully For First versions");
		  
		//Third Checklist sumitting For First versions
		  lauchNewApp("true");
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,sExploreSearch, sExploreChildSearchTxt, sWOName1);
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		  Thread.sleep(CommonUtility.iLowSleep);
		  commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameFirstVersions),20,20); 
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility, "Choose from Library","AttachmentQuestionFirstVersion"); 
		  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestionFirstVersion"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestionFirstVersion").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  Thread.sleep(CommonUtility.iHighSleep);
		  System.out.println("Third Checklist sumitted successfully For First versions");
		  ExtentManager.logger.log(Status.INFO,"Third Checklist sumitted successfully For First versions");
		  
		//First Checklist sumitting For Last versions
		  lauchNewApp("true");
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch,sExploreChildSearchTxt, sWOName1); 
		  workOrderPo.selectAction(commonUtility,sFieldServiceName); 
		  Thread.sleep(CommonUtility.iLowSleep);
		  commonUtility.tap(checklistPo.geteleChecklistName(sChecklistNameLastVersions));
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility, "Choose from Library","AttachmentQuestionLastVersion"); 
		  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestionLastVersion"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestionLastVersion").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  System.out.println("First Checklist sumitted successfully For Last versions");
		  ExtentManager.logger.log(Status.INFO,"First Checklist sumitted successfully For Last versions");
		  
		//Second Checklist sumitting For Last versions
		  lauchNewApp("true"); 
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,sExploreSearch, sExploreChildSearchTxt, sWOName1);
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		  Thread.sleep(CommonUtility.iLowSleep);
		  commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameLastVersions),20,20); 
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility, "Choose from Library","AttachmentQuestionLastVersion"); 
		  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestionLastVersion"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestionLastVersion").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  Thread.sleep(CommonUtility.iHighSleep);
		  System.out.println("Second Checklist sumitted successfully For Last versions");
		  ExtentManager.logger.log(Status.INFO,"Second Checklist sumitted successfully For Last versions");
		  
		//Third Checklist sumitting For Last versions
		  lauchNewApp("true"); 
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,sExploreSearch, sExploreChildSearchTxt, sWOName1);
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		  Thread.sleep(CommonUtility.iLowSleep);
		  commonUtility.tap(checklistPo.getEleStartNewLnk(sChecklistNameLastVersions),20,20); 
		  Thread.sleep(CommonUtility.iLowSleep);
		  checklistPo.checklistAttach(commonUtility, "Choose from Library","AttachmentQuestionLastVersion"); 
		  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
		  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestionLastVersion"));
		  checklistPo.geteleChecklistAnswerInput("AttachmentQuestionLastVersion").sendKeys("AttachmentChecklistupload");
		  commonUtility.tap(checklistPo.geteleNext());
		  commonUtility.tap(checklistPo.eleChecklistSubmit());
		  commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		  Thread.sleep(CommonUtility.iHighSleep);
		  System.out.println("Third Checklist sumitted successfully For Last versions");
		  ExtentManager.logger.log(Status.INFO,"Third Checklist sumitted successfully For Last versions");
		  
		  
		  //Submitting and Verifying opdoc
		  
		 //workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		   lauchNewApp("true"); 
		   workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,sExploreSearch, sExploreChildSearchTxt, sWOName1);	
		   workOrderPo.selectAction(commonUtility, sChecklistOpDocNameForversion);
		 //List<WebElement> e =driver.findElements(By.xpath("//*[contains(@label,'SCN_ChecklistOPDOC_Files_allversions')]"));
		   commonUtility.tap(workOrderPo.getEleDoneLnk());
			  Thread.sleep(CommonUtility.i30SecSleep);
			  ((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
			  Thread.sleep(CommonUtility.iHighSleep);
			  ((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
			  Thread.sleep(CommonUtility.i30SecSleep);
			  toolsPo.syncData(commonUtility);
			  Thread.sleep(CommonUtility.iMedSleep); 
			  Thread.sleep(CommonUtility.iMedSleep);
			  Thread.sleep(20000); 
			  String sSoqlWorkorderid3 =
			  "select id from SVMXC__Service_Order__c where name =\'"+sWOName1+"\'"; 
			  String sSoqlWorkorderidafter3 = restServices.restGetSoqlValue(sSoqlWorkorderid3,"Id"); 
			  String sSoqlFile3 ="select Id,LinkedEntityID,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter3+"\')";
			  String sSoqlFileafter3 = restServices.restGetSoqlValue(sSoqlFile3, "Id");
			  assertNotNull(sSoqlFileafter3); System.out.println(sSoqlFile3);
			  ExtentManager.logger.log(Status.PASS,"Opdoc with versioning is synced to server sucessfully when Files are enabled");
			  
			  String sContentdocid3 ="select ContentDocumentId from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter3+"\')"; 
			  //System.out.println(sContentdocid); 
			  String sContentdocidafter3 = restServices.restGetSoqlValue(sContentdocid3,"ContentDocumentId"); 
			  String sFileTitle3 ="select Title from ContentDocument where Id=\'"+sContentdocidafter3+"\'";
			  String sFileTitleafter3 = restServices.restGetSoqlValue(sFileTitle3,"Title"); 
			  System.out.println(sFileTitleafter3);
			  ExtentManager.logger.log(Status.INFO,"File uplaoded is"+sFileTitleafter3);
			  Assert.assertTrue(sFileTitleafter3.contains("pdf"),"fileformat stored is not pdf");
			  ExtentManager.logger.log(Status.PASS,"file is in pdf format in server"); 		      
	         
		

		
	
		
		}     
		
		@AfterMethod
		public void tearDown() throws Exception {
		postscript();
		
		
		
		}

}
