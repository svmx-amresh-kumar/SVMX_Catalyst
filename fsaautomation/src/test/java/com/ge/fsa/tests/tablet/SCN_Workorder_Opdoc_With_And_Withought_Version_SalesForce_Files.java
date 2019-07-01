package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;

public class SCN_Workorder_Opdoc_With_And_Withought_Version_SalesForce_Files extends BaseLib {
	
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
	String sScriptName="SCN_Work_Order_OPDoc_when_Files_Enabled";
	Boolean bProcessCheckResult  = false; 	
	String sWOName2 = null;
	String sWOName1 = null;
	String sChecklistNameAllVersions = null;
	String sChecklistNameFirstVersion = null;
	String sChecklistNameLastVersion =null;
	String sChecklistOpDocNameForversion=null;
	String sChecklistNameFirstVersions =null;
	String sChecklistNameLastVersions =null;
	String sWorkorderOpDocName = null;
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
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
		//sWorkorderOpDocName = GenericLib.readExcelData(GenericLib.sTestDataFile,sSheetName, "WorkorderOpDocName");
		sWorkorderOpDocName= "OPDOC_WorkOrderOPDOC_Files_Enables";
		
		//Work Order for Files --upload from library
		
	
	  sWORecordID=restServices.restCreate("SVMXC__Service_Order__c?",
	  "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
	  ); System.out.println(sWORecordID); sWOName= restServices.restGetSoqlValue(
	  "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID +
	  "\'","Name"); System.out.println("WO no =" + sWOName); 
	  //sWOName ="WO-00004603";
	  
		/*
		 * sWORecordID1 =restServices.restCreate("SVMXC__Service_Order__c?",
		 * "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}"
		 * ); System.out.println(sWORecordID1); sWOName1= restServices.restGetSoqlValue(
		 * "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID1 +"\'",
		 * "Name"); System.out.println("WO no =" + sWOName1);
		 */
	 
	 			  
	bProcessCheckResult =commonUtility.ProcessCheck(restServices, sWorkorderOpDocName, sScriptName, sTestCaseID);  
		
}
	
	@Test()
	public void RS_9726() throws Exception {

		// Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		prereq();
		
		// Data Sync for WO's created
		toolsPo.syncData(commonUtility);
	
	   Thread.sleep(CommonUtility.iMedSleep);
	   
	   commonUtility.executeSahiScript("appium/SCN_Work_Order_OPDoc_when_Files_Enabled.sah", "sTestCaseID");
		if(commonUtility.verifySahiExecution()) 
		{
			System.out.println("PASSED"); 
			} else
	   { 
		System.out.println("FAILED");
		ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID+"Sahi verification failure");
		assertEquals(0, 1);
		}
		
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		workOrderPo.selectAction(commonUtility, sFieldServiceName);
		workOrderPo.WorkorderAttach(commonUtility, "Choose from Library");
		Thread.sleep(CommonUtility.iHighSleep);
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		ExtentManager.logger.log(Status.PASS, "Work Order Attachment Choose from library added sucessfull");
		Thread.sleep(CommonUtility.iHighSleep);
		Thread.sleep(CommonUtility.i30SecSleep);
		 
		 //Server validation
		 toolsPo.syncData(commonUtility);
		 Thread.sleep(CommonUtility.i30SecSleep); 
		 Thread.sleep(CommonUtility.i30SecSleep);
		 Thread.sleep(CommonUtility.iAttachmentSleep);
		 System.out.println("Validating if  File is syned to server.");
		 Thread.sleep(CommonUtility.i30SecSleep); 
		 
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
		 
		 //Submitting and Verifying opdoc
		  
		 //workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		   lauchNewApp("true"); 
		   workOrderPo.navigatetoWO(commonUtility, exploreSearchPo,sExploreSearch, sExploreChildSearchTxt, sWOName);	
		   workOrderPo.selectAction(commonUtility, sWorkorderOpDocName);
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
			 "select id from SVMXC__Service_Order__c where name =\'"+sWOName+"\'"; 
			 String sSoqlWorkorderidafter3 = restServices.restGetSoqlValue(sSoqlWorkorderid3,"Id"); 
			 String sSoqlFile3 ="select Id,LinkedEntityID,ContentDocumentID,ContentDocument.Title from ContentDocumentLink where (LinkedEntityID = \'"+sSoqlWorkorderidafter3+"\')";
			 String sSoqlFileafter3 = restServices.restGetSoqlValue(sSoqlFile3, "Id");
			 assertNotNull(sSoqlFileafter3);
			 System.out.println(sSoqlFile3);
			 ExtentManager.logger.log(Status.PASS,"File is synced to Server successfully when Take video is selected");
			 
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
}
