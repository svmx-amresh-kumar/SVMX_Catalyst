/*
 *  @author Vinod Tharavath
 *  
 *  
 */
package com.ge.fsa.tests.phone;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.ChecklistPO;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ph_SCN_Checklist_Attachment_RS_10581 extends BaseLib {
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
	String sSheetName =null;
	String sWORecordID = null;
	String sWOName = null;
	Boolean bProcessCheckResult  = false;
	String sProcessname = "Default title for Checklist";

	//For SFM Process Sahi Script name
	String sScriptName="Scenario_RS10584_Checklist_Attachments";
	//Attachment questions
	String sAttachText ="AttachmentChecklistupload";
	String sAttachmentQuestion1 = null;
	String sAttachmentQ = "AttachmentQuestion1";
	
	public void prereq() throws Exception
	{	
		sSheetName ="RS_10584";
		System.out.println("SCN_RS10581_Checklist_Attachment");
		sTestCaseID = "SCN_ChecklistAttachment_RS-10584";
		sCaseWOID = "Data_SCN_ChecklistAttachment_RS_10584";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
		
		// Rest to Create Workorder -Standard Work Order - Satisfies Qualification Criteria and Checklist Entry Criteria
		
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);			
		//sWOName = "WO-00004603";
		bProcessCheckResult =commonUtility.ProcessCheck(restServices, genericLib, sChecklistName, sScriptName, sTestCaseID);		
	}
	
	//@Test()
	@Test(retryAnalyzer=Retry.class)
	public void RS_10581() throws Exception {
			
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		//Running prerequisites
		prereq();
		
		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);		

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sProcessname);
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName + "");
		
		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();		
		ExtentManager.logger.log(Status.INFO, "Clicked ChecklistProcess" + sChecklistName + "");

		// Starting new Checklist
		ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
		Thread.sleep(2000);
		ph_ChecklistPO.geteleInProgress().click();
		ph_ChecklistPO.checklistAttach(commonUtility, "Choose from Camera Roll","");
		
		//Submitting the checklist
		ph_ChecklistPO.geteleSubmitbtn().click();
		
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		ph_ChecklistPO.geteleCompleted().click();
		
		//retreiving completed checklist
		ph_ChecklistPO.getelechecklistinstance().click();
		// to be reverted once number is fixed for android!
				try {
					ph_ChecklistPO.geteleChecklistCompleted().click();

				} catch (Exception e) {
					ph_ChecklistPO.geteleInProgress().click();
				}		
		ph_MorePo.syncData(commonUtility);

		// ------------------SERVER SIDE VALIDATIONS
		System.out.println("Validating if  attachment is syned to server.");
		//The below 60seconds wait is must as attachment takes time to sync to server and there is no ways we can predict how long it takes.
		Thread.sleep(60000);
		String sSoqlchecklistid = "SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"
				+ sWOName + "\')";
		String schecklistid = restServices.restGetSoqlValue(sSoqlchecklistid, "Id");
		String sSoqlAttachment = "SELECT Id FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"
				+ schecklistid + "\')";

		String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlAttachment, "Id");
		assertNotNull(sAttachmentIDAfter);
		ExtentManager.logger.log(Status.PASS, "attachment is synced to Server");
		String sSoqlAttachmentName = "SELECT Name FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"
				+ schecklistid + "\')";
		String sAttachmentNameAfter = restServices.restGetSoqlValue(sSoqlAttachmentName, "Name");
		ExtentManager.logger.log(Status.INFO, "Attachment uplaoded is" + sAttachmentNameAfter);
		
}
	
	
	
}