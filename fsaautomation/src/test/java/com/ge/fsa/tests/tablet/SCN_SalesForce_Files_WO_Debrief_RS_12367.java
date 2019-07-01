/*
 *  @author Abhijith Roy
 */


package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_SalesForce_Files_WO_Debrief_RS_12367 extends BaseLib {
	
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
	String sWORecordIDTP = null;
	String sWORecordIDTV = null;
	String sWOName2 = null;
	String sWOName1 = null;
	String sWOName = null;
	String sWONameTP = null;
	String sWONameTV = null;
	String sEventSubject = "Create Event from WO in Client";
	

	public void prereq() throws Exception
	{	
		sSheetName ="RS_12367";
		System.out.println("SCN_SalesForce_Files_WO_Debrief_RS_12367");
		sTestCaseID = "SCN_Workoreder_Attachment_RS-12367";
		sCaseWOID = "Data_SCN_Workoreder_Attachment_RS-12367";
		
		sExploreSearch = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName, "EditProcessName");
		sEditProcessName = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName, "EditProcessName");
		
		
		  //Work Order for Files --upload from library
		
		  sWORecordID=restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID); 
		  sWOName= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'","Name"); 
		  System.out.println("WO no =" + sWOName); //sWOName = "WO-00004603";
		  
		  //Work Order for Files --Take Photo
			
		  sWORecordIDTP=restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID); 
		  sWONameTP= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordIDTP + "\'","Name"); 
		  System.out.println("WO no =" + sWONameTP); //sWOName = "WO-00004603";
		  
		  //Work Order for Files --Take Video
			
		  sWORecordIDTV=restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID); 
		  sWONameTV= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordIDTV + "\'","Name"); 
		  System.out.println("WO no =" + sWONameTV); //sWOName = "WO-00004603";
		  
		  
		  //Work Order for Attachement
		  
		  sWORecordID1 =restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		   ); System.out.println(sWORecordID1); 
		   sWOName1= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID1 +"\'", "Name"); 
		   System.out.println("WO no =" + sWOName1);
		   
		 		 
}
	
	public void postscript() throws Exception
	{
		commonUtility.executeSahiScript("appium/SCN_Disabling_Salesforce_Files.sah",sTestCaseID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
	}
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_12367() throws Exception {

		// Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		prereq();
		// Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		
	/*------------------------Validating--Files are sending when Salesforce files are enabled-----------*/
		
		//Enabling salesforce files
		
		
		  System.out.println("Setting the GBL037 to true");
		  
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
		 
		 
		 /*------------Upload from library- sales force files--------------*/ 
		
		 workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		 workOrderPo.createNewEvent(commonUtility,sEventSubject, "Test Description");
		 commonUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());
		 Thread.sleep(GenericLib.i30SecSleep); 
		 workOrderPo.selectAction(commonUtility, sFieldServiceName);
		 workOrderPo.WorkorderAttach(commonUtility, "Choose from Library");
		 Thread.sleep(GenericLib.iHighSleep);
		 commonUtility.tap(workOrderPo.getEleSaveLnk());
		 ExtentManager.logger.log(Status.PASS, "Work Order Attachment Choose from library added sucessfull");
		 Thread.sleep(GenericLib.iHighSleep);
		 Thread.sleep(GenericLib.i30SecSleep);
		 
		 //Server validation
		 toolsPo.syncData(commonUtility);
		 Thread.sleep(GenericLib.i30SecSleep); 
		 Thread.sleep(GenericLib.i30SecSleep);
		 Thread.sleep(GenericLib.iAttachmentSleep);
		 System.out.println("Validating if  File is syned to server.");
		 Thread.sleep(GenericLib.i30SecSleep); 
		 
		 String sSoqlWorkorderid = "select id from SVMXC__Service_Order__c where name =\'"+sWOName+"\'";
		 String sSoqlWorkorderidafter = restServices.restGetSoqlValue(sSoqlWorkorderid, "Id");
		 String sSoqlFile = "select Id,LinkedEntityID,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter+"\')";
		 String sSoqlFileafter = restServices.restGetSoqlValue(sSoqlFile, "Id");
		 assertNotNull(sSoqlFileafter); 
		 System.out.println(sSoqlFile);
		 ExtentManager.logger.log(Status.PASS,"File is synced to Server successfully when choose from library is selected");
		 
		 String sContentdocid = "select ContentDocumentId from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter+"\')";
		 //System.out.println(sContentdocid);
		 String sContentdocidafter = restServices.restGetSoqlValue(sContentdocid, "ContentDocumentId");
		 String sFileTitle = "select Title from ContentDocument where Id=\'"+sContentdocidafter+"\'";
		 String sFileTitleafter = restServices.restGetSoqlValue(sFileTitle, "Title");
		 System.out.println(sFileTitleafter);
		 ExtentManager.logger.log(Status.INFO,"File uplaoded is"+sFileTitleafter);
		 
		 /*------------Take Photo- sales force files--------------*/
		 lauchNewApp("true");	
		 workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWONameTP);
		 workOrderPo.createNewEvent(commonUtility,sEventSubject, "Test Description");
		 commonUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());
		 Thread.sleep(GenericLib.i30SecSleep); 
		 workOrderPo.selectAction(commonUtility, sFieldServiceName);
		 workOrderPo.WorkorderAttach(commonUtility, "Take Photo");
		 Thread.sleep(GenericLib.iHighSleep);
		 commonUtility.tap(workOrderPo.getEleSaveLnk());
		 ExtentManager.logger.log(Status.PASS, "Work Order Attachment Take Photo added sucessfull");
		 Thread.sleep(GenericLib.iHighSleep);
		 Thread.sleep(GenericLib.i30SecSleep);
		 
		 //Server validation
		 toolsPo.syncData(commonUtility);
		 Thread.sleep(GenericLib.i30SecSleep); 
		 Thread.sleep(GenericLib.i30SecSleep);
		 System.out.println("Validating if  File is syned to server.");
		 Thread.sleep(GenericLib.iAttachmentSleep);
		 Thread.sleep(GenericLib.i30SecSleep); 
		 
		 String sSoqlWorkorderid1 = "select id from SVMXC__Service_Order__c where name =\'"+sWONameTP+"\'";
		 String sSoqlWorkorderidafter1 = restServices.restGetSoqlValue(sSoqlWorkorderid1, "Id");
		 String sSoqlFile1 = "select Id,LinkedEntityID,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter1+"\')";
		 String sSoqlFileafter1 = restServices.restGetSoqlValue(sSoqlFile1, "Id");
		 assertNotNull(sSoqlFileafter1); 
		 System.out.println(sSoqlFile1);
		 ExtentManager.logger.log(Status.PASS,"File is synced to Server successfully when Take photo is selected");
		 
		 String sContentdocid1 = "select ContentDocumentId from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter1+"\')";
		 //System.out.println(sContentdocid);
		 String sContentdocidafter1 = restServices.restGetSoqlValue(sContentdocid1, "ContentDocumentId");
		 String sFileTitle1 = "select Title from ContentDocument where Id=\'"+sContentdocidafter1+"\'";
		 String sFileTitleafter1 = restServices.restGetSoqlValue(sFileTitle1, "Title");
		 System.out.println(sFileTitleafter1);
		 ExtentManager.logger.log(Status.INFO,"File uplaoded is"+sFileTitleafter1);
		 
		 /*------------Take video- sales force files--------------*/ 
		 lauchNewApp("true");	
		 workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWONameTV);
		 workOrderPo.createNewEvent(commonUtility,sEventSubject, "Test Description");
		 commonUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());
		 Thread.sleep(GenericLib.i30SecSleep); 
		 workOrderPo.selectAction(commonUtility, sFieldServiceName);
		 workOrderPo.WorkorderAttach(commonUtility, "Take Video");
		 Thread.sleep(GenericLib.iHighSleep);
		 commonUtility.tap(workOrderPo.getEleSaveLnk());
		 ExtentManager.logger.log(Status.PASS, "Work Order Attachment Take Video added sucessfull");
		 Thread.sleep(GenericLib.iHighSleep);
		 Thread.sleep(GenericLib.i30SecSleep);
		 
		 //Server validation
		 toolsPo.syncData(commonUtility);
		 Thread.sleep(GenericLib.i30SecSleep); 
		 Thread.sleep(GenericLib.i30SecSleep);
		 System.out.println("Validating if  File is syned to server.");
		 Thread.sleep(GenericLib.i30SecSleep); 
		 Thread.sleep(GenericLib.iAttachmentSleep);
		 
		 String sSoqlWorkorderid2 = "select id from SVMXC__Service_Order__c where name =\'"+sWONameTV+"\'";
		 String sSoqlWorkorderidafter2 = restServices.restGetSoqlValue(sSoqlWorkorderid2, "Id");
		 String sSoqlFile2 = "select Id,LinkedEntityID,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter2+"\')";
		 String sSoqlFileafter2 = restServices.restGetSoqlValue(sSoqlFile2, "Id");
		 assertNotNull(sSoqlFileafter2); 
		 System.out.println(sSoqlFile2);
		 ExtentManager.logger.log(Status.PASS,"File is synced to Server successfully when Take video is selected");
		 
		 String sContentdocid2 = "select ContentDocumentId from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter2+"\')";
		 //System.out.println(sContentdocid);
		 String sContentdocidafter2 = restServices.restGetSoqlValue(sContentdocid2, "ContentDocumentId");
		 String sFileTitle2 = "select Title from ContentDocument where Id=\'"+sContentdocidafter2+"\'";
		 String sFileTitleafter2 = restServices.restGetSoqlValue(sFileTitle2, "Title");
		 System.out.println(sFileTitleafter2);
		 ExtentManager.logger.log(Status.INFO,"File uplaoded is"+sFileTitleafter2);
		 
		 
		//Checking whether Files are downloading
		  toolsPo.Resetapp(commonUtility, exploreSearchPo);
		  lauchNewApp("true");
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		  commonUtility.tap(workOrderPo.getEleAttacheddoc());
		  Thread.sleep(GenericLib.iHighSleep);
		  WebElement Filedownload = driver.findElement(By.xpath("//div[@class='sfm-delivery-detail-name sfm-list-header sfm-attachment-header-name '][text()='" + sFileTitleafter + "']"));
		  Assert.assertTrue(commonUtility.waitforElement(Filedownload,10), "Files are downloading as expected");
		  ExtentManager.logger.log(Status.PASS,"Files are downloading as expected");
		  
		//Checking whether files are removed from server when deleted from client
		  
		  workOrderPo.DeletingAttachement(commonUtility, sFileTitleafter);
		  toolsPo.syncData(commonUtility);
		  System.out.println("Deleting File from client");
		  ExtentManager.logger.log(Status.INFO,"File is Deleted from client");
		  assertNotNull(sSoqlFileafter);
		  ExtentManager.logger.log(Status.PASS,"Files are Deleted from server");
		 
		 
		 
/*------------------------Validating--Attachments are sending when Salesforce files are Disabled-----------*/
		  //Disabling salesforce files
		  lauchNewApp("true");
		  
		  commonUtility.executeSahiScript("appium/SCN_Disabling_Salesforce_Files.sah",
		  "sTestCaseID"); if(commonUtility.verifySahiExecution())
		  {System.out.println("PASSED"); } else { System.out.println("FAILED");
		  ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID +
		  "Sahi verification failure"); assertEquals(0, 1); }
		  
		  System.out.println("Setting the GBL037 is set to False");
		  ExtentManager.logger.log(Status.PASS,"Setting GBL037 has been set to False");
		  
		  toolsPo.Resetapp(commonUtility, exploreSearchPo);
		  
		  
		  //Checking whether files are downloading when Files are disabled
		  //lauchNewApp("true");
		  //workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		  //workOrderPo.selectAction(commonUtility, sFieldServiceName);
		  //commonUtility.tap(workOrderPo.getEleAttacheddoc());
		  //Thread.sleep(GenericLib.iHighSleep);
		  //WebElement FiledownloadWhenSettingisOff = driver.findElement(By.xpath("//div[@class='sfm-delivery-detail-name sfm-list-header sfm-attachment-header-name '][text()='" + sFileTitleafter + "']"));
		  //Assert.assertFalse(commonUtility.waitforElement(FiledownloadWhenSettingisOff,3), "Files are not downloaded");
		  //ExtentManager.logger.log(Status.PASS,"File is not able to download when Setting is disabled");
		  
		  //Uploading attachment
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1);
		  workOrderPo.createNewEvent(commonUtility,sEventSubject, "Test Description");
		  commonUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());
		  Thread.sleep(GenericLib.iHighSleep);
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		  workOrderPo.WorkorderAttach(commonUtility, "Choose from Library");
		  Thread.sleep(GenericLib.iHighSleep);
		  commonUtility.tap(workOrderPo.getEleSaveLnk());
		  ExtentManager.logger.log(Status.PASS, "Work Order Attachment Choose from library added sucessfull");
		  
		  
		  //Server validation
		  toolsPo.syncData(commonUtility);
		  Thread.sleep(GenericLib.i30SecSleep); 
		  Thread.sleep(GenericLib.i30SecSleep);
		  System.out.println("Validating if  File is syned to server.");
		  Thread.sleep(GenericLib.iAttachmentSleep);
		  Thread.sleep(GenericLib.i30SecSleep); 
		  String sSoqlWorkorderidAtach = "select id from SVMXC__Service_Order__c where name =\'"+sWOName1+"\'";
		  String sSoqlWorkorderidafterAtach = restServices.restGetSoqlValue(sSoqlWorkorderidAtach, "Id");
		  String sSoqlFileAtach = "select Id,LinkedEntityID,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafterAtach+"\')";
		  String sSoqlFileafterAtach = restServices.restGetSoqlValue(sSoqlFileAtach, "Id");
		  assertNull(sSoqlFileafterAtach);
		  ExtentManager.logger.log(Status.PASS,"File is not synced to Server as setting is attachemnt ");
		  
		  //Checking attachement is synced to server
		  String sSoqlAttachment = "SELECT Id FROM Attachment where ParentId in(select id from SVMXC__Service_Order__c where name =\'"+sWOName1+"\')"; 
		  //String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
		  restServices.getAccessToken();
		  String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlAttachment, "Id");	
		  assertNotNull(sAttachmentIDAfter);
		  ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
		  String sSoqlAttachmentName = "SELECT Name FROM Attachment where ParentId in(select id from SVMXC__Service_Order__c where name  =\'"+sWOName1+"\')"; 
		  String sAttachmentNameAfter = restServices.restGetSoqlValue(sSoqlAttachmentName, "Name");
		  ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter);
		  
		  
		//Checking whether Attachment are downloading
		  toolsPo.Resetapp(commonUtility, exploreSearchPo);
		  lauchNewApp("true");
		  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1);
		  workOrderPo.selectAction(commonUtility, sFieldServiceName);
		  commonUtility.tap(workOrderPo.getEleAttacheddoc());
		  Thread.sleep(GenericLib.iHighSleep);
		  WebElement AttachDownload = driver.findElement(By.xpath("//div[@class='sfm-delivery-detail-name sfm-list-header sfm-attachment-header-name '][text()='" + sAttachmentNameAfter + "']"));
		  Assert.assertTrue(commonUtility.waitforElement(AttachDownload,3), "Attachments are downloading as expected");
		  ExtentManager.logger.log(Status.PASS,"Attachments are downloading as expected");
	}
	
	
	@AfterMethod
	public void tearDown() throws Exception {
	postscript();
	
	
	
	}
	
	
	
	
}
