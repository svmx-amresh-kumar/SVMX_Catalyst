/*
 *  @author Abhijith Roy
 */

package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class SCN_SalesForce_Files_Checklist_Attachment_RS_12378 extends BaseLib {
	
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
	String sChecklistName=null;
	String sFileTitleafter1=null;
	
	//Attachment questions
	
	String sAttachmentQuestion1 = null;
	String sAttachText ="AttachmentChecklistupload";
	String sAttachmentQ = "AttachmentQuestion1";
	
	
	public void prereq() throws Exception
	{	
		sSheetName ="RS_12367";
		System.out.println("SCN_RS10584_Checklist_Attachment");
		sTestCaseID = "SCN_Workoreder_Attachment_RS-12367";
		sCaseWOID = "Data_SCN_Workoreder_Attachment_RS-12367";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
		
		//Work Order for Files --upload from library
		
		  sWORecordID=restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID); sWOName= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID +
		  "\'","Name"); System.out.println("WO no =" + sWOName); 
		  //sWOName ="WO-00004603";
		  
		  //Work Order for Files --Take Photo
		  
		  sWORecordIDTP=restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID); sWONameTP= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordIDTP +
		  "\'","Name"); System.out.println("WO no =" + sWONameTP); 
		  //sWOName = "WO-00004603";
		  
		  //Work Order for Files --Take Video
		  
		  sWORecordIDTV=restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID); sWONameTV= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordIDTV +
		  "\'","Name"); System.out.println("WO no =" + sWONameTV); 
		  //sWOName ="WO-00004603";
		  
		  
		  //Work Order for Attachement
		  
		  sWORecordID1 =restServices.restCreate("SVMXC__Service_Order__c?",
		  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		  ); System.out.println(sWORecordID1); sWOName1= restServices.restGetSoqlValue(
		  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID1 +"\'",
		  "Name"); System.out.println("WO no =" + sWOName1);
		  
		  sWORecordID2=restServices.restCreate("SVMXC__Service_Order__c?",
		 "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"); System.out.println(sWORecordID2); 
		 sWOName2= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID2 +"\'","Name"); 
		  System.out.println("WO no =" + sWOName2); 
		//sWOName ="WO-00004603";
		 
		

}
	
	public void postscript() throws Exception
	{
		commonUtility.executeSahiScript("appium/SCN_Disabling_Salesforce_Files.sah");
		
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
	}
	
	//@Test(retryAnalyzer=Retry.class)
	@Test()
	public void RS_12378() throws Exception {
		// JiraLink
		if (BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12378");
		} else {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12568");

		}

		// Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		prereq();
		// Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		
	/*------------------------Validating--Files are sending when Salesforce files are enabled-----------*/
		
		//Enabling salesforce files
		
		
		  System.out.println("Setting the GBL037 to true");
		  
		  commonUtility.executeSahiScript("appium/SCN_Enabling_Salesforce_Files.sah"); 
		  
		  ExtentManager.logger.log(Status.PASS,"Setting GBL037 has been set to True");
		  
		  System.out.println("Setting the SET007 to Flase");
		  commonUtility.executeSahiScript(
		  "appium/SCN_option_to_remove_Choosefromlib_False.sah");
		 
		  
		  toolsPo.Resetapp(commonUtility, exploreSearchPo);
		 
		 
		  
		  /*------------Upload from library- sales force files--------------*/ 
			
			 workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
			 workOrderPo.createNewEvent(commonUtility,sEventSubject, "Test Description");
			 commonUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());
			 Thread.sleep(CommonUtility.i30SecSleep); 
			 workOrderPo.selectAction(commonUtility, sFieldServiceName);
			 commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
			 Thread.sleep(CommonUtility.iLowSleep);
			 checklistPo.checklistAttach(commonUtility, "Choose from Library", sAttachmentQ);
			 ExtentManager.logger.log(Status.PASS, "Checklist Attachment Choose from library added sucessfull");
			 commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
			 checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
			 commonUtility.tap(checklistPo.geteleNext());
			 commonUtility.tap(checklistPo.eleChecklistSubmit());
			 commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
			 ExtentManager.logger.log(Status.PASS, "Checklist is submitted sucessfully for Work Order" + sWOName);
			 Thread.sleep(CommonUtility.iHighSleep);
			 checklistPo.navigateBacktoWorkOrder(commonUtility);
			 
			 
			//Server validation
			 toolsPo.syncData(commonUtility);
			 Thread.sleep(CommonUtility.i30SecSleep); 
			 Thread.sleep(CommonUtility.i30SecSleep);
			 System.out.println("Validating if  File is syned to server.");
			 Thread.sleep(CommonUtility.iAttachmentSleep);
			 Thread.sleep(CommonUtility.i30SecSleep);
			 
			 String sSoqlchecklistid ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"+sWOName+"\')";
			 String schecklistid = restServices.restGetSoqlValue(sSoqlchecklistid, "Id");
			 String sContentDocLinkId = "SELECT SVMXC__SM_ContentDocumentLink_ID__c FROM SVMXC__SM_Checklist_Attachment__c  where (SVMXC__SM_Checklist__c= \'"+schecklistid+"\')";
			 String sContentDocLinkIdafter = restServices.restGetSoqlValue(sContentDocLinkId,"SVMXC__SM_ContentDocumentLink_ID__c");
			 String sSoqlFile = "select Id,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (id = \'"+sContentDocLinkIdafter+"\')";
			 String sSoqlFileafter = restServices.restGetSoqlValue(sSoqlFile, "Id");
			 assertNotNull(sSoqlFileafter); 
			 System.out.println(sSoqlFile);
			 ExtentManager.logger.log(Status.PASS,"File is synced to Server successfully when choose from library is selected");
			 
			 String sContentdocid = "select ContentDocumentId from ContentDocumentLink where (id = \'"+sContentDocLinkIdafter+"\')";
			 String sContentdocidafter = restServices.restGetSoqlValue(sContentdocid, "ContentDocumentId");
			 String sFileTitle = "select Title from ContentDocument where Id=\'"+sContentdocidafter+"\'";
			 String sFileTitleafter = restServices.restGetSoqlValue(sFileTitle, "Title");
			 System.out.println(sFileTitleafter);
			 ExtentManager.logger.log(Status.INFO,"File uplaoded is"+sFileTitleafter);
			 
			//Checking whether Files are downloading
			  toolsPo.Resetapp(commonUtility, exploreSearchPo);
			  lauchNewApp("true");
			  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt,sWOName);	
			  Thread.sleep(CommonUtility.iMedSleep);
			  workOrderPo.selectAction(commonUtility, sFieldServiceName);
			  
			  commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
			  Thread.sleep(30000);
			  commonUtility.tap(checklistPo.geteleChecklistBackButton());
			  commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
			  Thread.sleep(30000);
			  
			  try {
				  WebElement Filedownload = driver.findElement(By.xpath("//div[@class='x-component x-img x-sized x-widthed x-heighted x-floating ']"));
				  Assert.assertTrue(commonUtility.waitforElement(Filedownload,10), "Files are downloading as expected");
				  ExtentManager.logger.log(Status.PASS,"Files are downloading as expected");
				  }
			  catch (Exception e) {
					  WebElement FiledownloadC = driver.findElement(By.xpath("//div[@class='x-component x-img x-sized x-widthed x-heighted x-floating sfm-attachment-cloud-icon']"));
					  Assert.assertTrue(commonUtility.waitforElement(FiledownloadC,10), "Files are downloading as expected");
					  ExtentManager.logger.log(Status.PASS,"Files are downloading as expected");
					  
				  }
			  
			//Checking whether files are removed from server when deleted from client
			  lauchNewApp("true");
			  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch,sExploreChildSearchTxt, sWOName2);
					  
			// Navigate to Field Service process 
			  workOrderPo.selectAction(commonUtility,sFieldServiceName);
					  
			// Navigating to the checklist
			  commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
			  Thread.sleep(CommonUtility.iLowSleep);
			  checklistPo.checklistAttach(commonUtility, "Choose from Library",sAttachmentQ); 
			  ExtentManager.logger.log(Status.PASS,"Checklist Attachment Choose from library added sucessfull");
			  commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
			  checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload"); 
			  commonUtility.tap(checklistPo.geteleNext());
			  commonUtility.tap(checklistPo.geteleChecklistBackButton());
			  commonUtility.tap(checklistPo.geteleeleSaveButton());
			  checklistPo.navigateBacktoWorkOrder(commonUtility);	  
					  
			  toolsPo.syncData(commonUtility); 
			  Thread.sleep(CommonUtility.i30SecSleep);
			  Thread.sleep(CommonUtility.i30SecSleep);
			  System.out.println("Validating if  File is syned to server.");
			  Thread.sleep(CommonUtility.iAttachmentSleep);
			  Thread.sleep(CommonUtility.i30SecSleep);
			  
			  String sSoqlchecklistid4="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"+sWOName2+"\')"; 
			  String schecklistid4 =restServices.restGetSoqlValue(sSoqlchecklistid4, "Id"); 
			  String sContentDocLinkId4 ="SELECT SVMXC__SM_ContentDocumentLink_ID__c FROM SVMXC__SM_Checklist_Attachment__c  where (SVMXC__SM_Checklist__c= \'"+schecklistid4+"\')"; 
			  String sContentDocLinkIdafter4 =restServices.restGetSoqlValue(sContentDocLinkId4,"SVMXC__SM_ContentDocumentLink_ID__c"); 
			  String sSoqlFile4 ="select Id,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (id = \'" +sContentDocLinkIdafter4+"\')"; 
			  String sSoqlFileafter4 =restServices.restGetSoqlValue(sSoqlFile4, "Id");
			  assertNotNull(sSoqlFileafter4); 
			  System.out.println(sSoqlFile4);
			  ExtentManager.logger.log(Status.PASS,"File is synced to Server successfully when choose from library is selected" );	 
					 
			  workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName2);
				
			// Navigate to Field Service process
			  workOrderPo.selectAction(commonUtility, sFieldServiceName);
			  checklistPo.DeleteAttachement(commonUtility, sChecklistName);
			  checklistPo.navigateBacktoWorkOrder(commonUtility);		
			  toolsPo.syncData(commonUtility);
			  Thread.sleep(CommonUtility.i30SecSleep);
			  Thread.sleep(CommonUtility.i30SecSleep);
			  System.out.println("Validating if  File is Deleted from server.");
			  Thread.sleep(CommonUtility.iAttachmentSleep);
			  Thread.sleep(CommonUtility.i30SecSleep);
			  
			//Checking if file is deleted from server	
			  assertNotNull(sSoqlFileafter4);
			  ExtentManager.logger.log(Status.PASS,"File is Deleted successfully from server");	
					
					 
					
					 
					
					
			  
			 
			 
			 /*------------Take Photo- sales force files--------------*/
			 lauchNewApp("true");
			 workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWONameTP);
			 workOrderPo.createNewEvent(commonUtility,sEventSubject, "Test Description");
			 commonUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());
			 Thread.sleep(CommonUtility.i30SecSleep); 
			 workOrderPo.selectAction(commonUtility, sFieldServiceName);
			 commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
			 Thread.sleep(CommonUtility.iLowSleep);
			 checklistPo.checklistAttach(commonUtility, "Take Photo", sAttachmentQ);
			 ExtentManager.logger.log(Status.PASS, "Checklist Attachment Take Photo added sucessfull");
			 commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
			 checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
			 commonUtility.tap(checklistPo.geteleNext());
			 commonUtility.tap(checklistPo.eleChecklistSubmit());
			 commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
			 ExtentManager.logger.log(Status.PASS, "Checklist is submitted sucessfully for Work Order" + sWONameTP);
			 Thread.sleep(CommonUtility.iHighSleep);
			 checklistPo.navigateBacktoWorkOrder(commonUtility);
			 
			//Server validation
			 toolsPo.syncData(commonUtility);
			 Thread.sleep(CommonUtility.i30SecSleep); 
			 Thread.sleep(CommonUtility.i30SecSleep);
			 System.out.println("Validating if  File is syned to server.");
			 Thread.sleep(CommonUtility.iAttachmentSleep);
			 Thread.sleep(CommonUtility.i30SecSleep);
			 
			 String sSoqlchecklistid1 ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"+sWONameTP+"\')";
			 String schecklistid1 = restServices.restGetSoqlValue(sSoqlchecklistid1, "Id");
			 String sContentDocLinkId1 = "SELECT SVMXC__SM_ContentDocumentLink_ID__c FROM SVMXC__SM_Checklist_Attachment__c  where (SVMXC__SM_Checklist__c= \'"+schecklistid1+"\')";
			 String sContentDocLinkIdafter1 = restServices.restGetSoqlValue(sContentDocLinkId1,"SVMXC__SM_ContentDocumentLink_ID__c");
			 String sSoqlFile1 = "select Id,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (id = \'"+sContentDocLinkIdafter1+"\')";
			 String sSoqlFileafter1 = restServices.restGetSoqlValue(sSoqlFile1, "Id");
			 assertNotNull(sSoqlFileafter1); 
			 System.out.println(sSoqlFile1);
			 ExtentManager.logger.log(Status.PASS,"File is synced to Server successfully when Take Photo is selected");
			 
			 String sContentdocid1 = "select ContentDocumentId from ContentDocumentLink where (id = \'"+sContentDocLinkIdafter1+"\')";
			 String sContentdocidafter1 = restServices.restGetSoqlValue(sContentdocid1, "ContentDocumentId");
			 String sFileTitle1 = "select Title from ContentDocument where Id=\'"+sContentdocidafter1+"\'";
			 sFileTitleafter1 = restServices.restGetSoqlValue(sFileTitle1, "Title");
			 System.out.println(sFileTitleafter1);
			 ExtentManager.logger.log(Status.INFO,"File uplaoded is"+sFileTitleafter1);
			 
			 /*------------Take video- sales force files--------------*/ 
			 lauchNewApp("true");
			 workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWONameTV);
			 workOrderPo.createNewEvent(commonUtility,sEventSubject, "Test Description");
			 commonUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());
			 Thread.sleep(CommonUtility.i30SecSleep); 
			 workOrderPo.selectAction(commonUtility, sFieldServiceName);
			 commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
			 Thread.sleep(CommonUtility.iLowSleep);
			 checklistPo.checklistAttach(commonUtility, "Take Video", sAttachmentQ);
			 ExtentManager.logger.log(Status.PASS, "Checklist Attachment Take Video added sucessfull");
			 commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
			 checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
			 commonUtility.tap(checklistPo.geteleNext());
			 commonUtility.tap(checklistPo.eleChecklistSubmit());
			 commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
			 ExtentManager.logger.log(Status.PASS, "Checklist is submitted sucessfully for Work Order" + sWONameTV);
			 Thread.sleep(CommonUtility.iHighSleep);
			 checklistPo.navigateBacktoWorkOrder(commonUtility);
			 
			//Server validation
			 toolsPo.syncData(commonUtility);
			 Thread.sleep(CommonUtility.i30SecSleep); 
			 Thread.sleep(CommonUtility.i30SecSleep);
			 System.out.println("Validating if  File is syned to server.");
			 Thread.sleep(CommonUtility.iAttachmentSleep);
			 Thread.sleep(CommonUtility.i30SecSleep);
			 
			 String sSoqlchecklistid2 ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"+sWONameTV+"\')";
			 String schecklistid2 = restServices.restGetSoqlValue(sSoqlchecklistid2, "Id");
			 String sContentDocLinkId2 = "SELECT SVMXC__SM_ContentDocumentLink_ID__c FROM SVMXC__SM_Checklist_Attachment__c  where (SVMXC__SM_Checklist__c= \'"+schecklistid2+"\')";
			 String sContentDocLinkIdafter2 = restServices.restGetSoqlValue(sContentDocLinkId2,"SVMXC__SM_ContentDocumentLink_ID__c");
			 String sSoqlFile2 = "select Id,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (id = \'"+sContentDocLinkIdafter2+"\')";
			 String sSoqlFileafter2 = restServices.restGetSoqlValue(sSoqlFile2, "Id");
			 assertNotNull(sSoqlFileafter2); 
			 System.out.println(sSoqlFile2);
			 ExtentManager.logger.log(Status.PASS,"File is synced to Server successfully when Take Video is selected");
			 
			 String sContentdocid2 = "select ContentDocumentId from ContentDocumentLink where (id = \'"+sContentDocLinkIdafter2+"\')";
			 String sContentdocidafter2 = restServices.restGetSoqlValue(sContentdocid2, "ContentDocumentId");
			 String sFileTitle2 = "select Title from ContentDocument where Id=\'"+sContentdocidafter2+"\'";
			 String sFileTitleafter2 = restServices.restGetSoqlValue(sFileTitle2, "Title");
			 System.out.println(sFileTitleafter2);
			 ExtentManager.logger.log(Status.INFO,"File uplaoded is"+sFileTitleafter2);
			 
			 
			 /*------------------------Validating--Attachments are sending when Salesforce files are Disabled-----------*/
			  //Disabling salesforce files
			  lauchNewApp("true");
			  
			  commonUtility.executeSahiScript("appium/SCN_Disabling_Salesforce_Files.sah");

			  
			  System.out.println("Setting the GBL037 is set to False");
			  ExtentManager.logger.log(Status.PASS,"Setting GBL037 has been set to False");
			  
			  toolsPo.Resetapp(commonUtility, exploreSearchPo);
			  
			  /*------------Upload from library- sales force files--------------*/ 
				
				 workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName1);
				 workOrderPo.createNewEvent(commonUtility,sEventSubject, "Test Description");
				 commonUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());
				 Thread.sleep(CommonUtility.i30SecSleep); 
				 workOrderPo.selectAction(commonUtility, sFieldServiceName);
				 commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
				 Thread.sleep(CommonUtility.iLowSleep);
				 checklistPo.checklistAttach(commonUtility, "Choose from Library", sAttachmentQ);
				 ExtentManager.logger.log(Status.PASS, "Checklist Attachment Choose from library added sucessfull");
				 commonUtility.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
				 checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
				 commonUtility.tap(checklistPo.geteleNext());
				 commonUtility.tap(checklistPo.eleChecklistSubmit());
				 commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
				 ExtentManager.logger.log(Status.PASS, "Checklist is submitted sucessfully for Work Order" + sWOName1);
				 Thread.sleep(CommonUtility.iHighSleep);
				 checklistPo.navigateBacktoWorkOrder(commonUtility);
				 
				//Server validation
				 toolsPo.syncData(commonUtility);
				 Thread.sleep(CommonUtility.i30SecSleep); 
				 Thread.sleep(CommonUtility.i30SecSleep);
				 System.out.println("Validating if  File is syned to server.");
				 Thread.sleep(CommonUtility.iAttachmentSleep);
				 Thread.sleep(CommonUtility.i30SecSleep);
				 Thread.sleep(CommonUtility.iMedSleep);
				 String sSoqlchecklistid3 ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"+sWOName1+"\')";
				 String schecklistid3 = restServices.restGetSoqlValue(sSoqlchecklistid3, "Id");	
				 String sSoqlAttachment = "SELECT Id FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid3+"\')"; 
				 //String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
				 restServices.getAccessToken();
				 String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlAttachment, "Id");	
				 assertNotNull(sAttachmentIDAfter); 
				 ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
				 String sSoqlAttachmentName = "SELECT Name FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid3+"\')"; 
				 String sAttachmentNameAfter = restServices.restGetSoqlValue(sSoqlAttachmentName, "Name");
				 ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter);
			  
	
	
	
	
	}
	
	@AfterMethod
	public void tearDown() throws Exception {
	postscript();
	}
	
}
