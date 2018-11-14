/*
 *  @author Vinod Tharavath
 */
package com.ge.fsa.tests;


import org.testng.annotations.Test;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;

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
	String sWOName = null;
	
	//Attachment questions
	
	String sAttachmentQuestion1 = null;
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
		
		// Rest to Create Workorder -Standard Work Order - Satisfies Qualification Criteria and Checklist Entry Criteria
		
	/*	sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);*/				
		sWOName = "WO-00004603";
	
	}
	
	@Test(enabled = true)
	public void RS_10584() throws Exception {
	
		
		// Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		prereq();
		// Data Sync for WO's created
		//toolsPo.syncData(commonsPo);
		// Thread.sleep(GenericLib.iMedSleep);
		// toolsPo.configSync(commonsPo);

		// Navigation to WO
		workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);

		// Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);

		// Navigating to the checklist
		commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
		
		try {
			//commonsPo.tap(checklistPo.geteleChecklistAttach(sAttachmentQuestion1));
			checklistPo.geteleChecklistAttach(sAttachmentQuestion1).click();
			//commonsPo.switchContext("Native");
			driver.findElementByAccessibilityId("Attach").click();
			Thread.sleep(10000);
		} catch (Exception e) {
			checklistPo.geteleChecklistAttach(sAttachmentQuestion1).click();
			// TODO: handle exception
		}
			//------------------SERVER SIDE VALIDATIONS

}
}