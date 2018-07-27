package com.ge.fsa.tests;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

public class Scenario7 extends BaseLib{
	String sTestCaseID= null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sChecklistOpDocName = null;
	
	
	@Test(enabled = true)
	public void scenario7() throws Exception {
		sTestCaseID = "Scenario7_Checklist";
		sCaseWOID = "Data_Scenario7_Checklist";
		
		
		
		
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID, "ChecklistName");
		sChecklistOpDocName =GenericLib.getExcelData(sTestCaseID, "ChecklistOpDocName");
		
		
		//NEW REST 
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		
		
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");

		System.out.println("WO no ="+sWOName);

		String ChecklistTextQuestion = "What is the Work Order Number?";
		String ChecklistrequestionQ = "This is a required question";
		
		
	restServices.getAccessToken();
//		sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
//		sWorkOrderID = restServices.getWOORecordID(sWOJsonData);
//		sWOName = restServices.getWOName(sWorkOrderID);
		//sWOName= "WO-00000366";
		
	//vt to learn!---GenericLib.setCongigValue(GenericLib.sDataFile, sCaseWOID, sWOName);
		
		
		loginHomePo.login(commonsPo, exploreSearchPo);
		//toolsPo.configSync(commonsPo);
		toolsPo.syncData(commonsPo);	
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		// Explore and Navigate to the Search Process
		commonsPo.getSearch(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		// // Select the Work Order
		exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
		// // Navigate to Field Service
		workOrderPo.selectAction(commonsPo, sFieldServiceName);
		// Navigating to the checklist
		commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(2000);
		
		//TRIES FOR SHADOW
		
		
		System.out.println(checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).getText());
		checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).click();
		System.out.println(checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).getText());
		System.out.println(checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).getTagName());
		commonsPo.tap(checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion));
		System.out.println("tapped");
		commonsPo.longPress(checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion));
		System.out.println(checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).getText());
		System.out.println("longpressed");
		System.out.println(checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).getText());
		//checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).sendKeys("hello");
		System.out.println(checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).isEnabled());
		System.out.println(checklistPo.geteleChecklistAnswerTextArea(ChecklistTextQuestion).getCssValue("contenteditbale"));		
		
		
		
		//tapping next button
		commonsPo.tap(checklistPo.eleNext());
		// submitting the checklist
		Thread.sleep(2000);
		commonsPo.tap(checklistPo.eleChecklistSubmit());
		
		//Validation of required question lbl and issue found txt.
		Thread.sleep(2000);
		Assert.assertTrue(checklistPo.getelefillrequiredfieldlbl().isDisplayed(),"Failed to provide:Please fill this required field and submit again-checklist");
		NXGReports.addStep("checklistrequiredquestion passed", LogAs.PASSED, null);
		commonsPo.waitforElement(checklistPo.geteleissuefoundlbl(),1000);
		Assert.assertTrue(checklistPo.geteleissuefoundlbl().isDisplayed(),"Failed to display issue found for required question-checklist");
		NXGReports.addStep("checklistrequiredquestion passed", LogAs.PASSED, null);
		checklistPo.geteleChecklistrequiredTxt(ChecklistrequestionQ).sendKeys("required answer");
		
		
		//submitting of checklist
		
		commonsPo.tap(checklistPo.eleChecklistSubmit());
		commonsPo.longPress(checklistPo.eleChecklistPopupSubmit());
		
		
		//Navigating back to work Orders
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());		
			
		
		//Navigating to the checklist OP
		workOrderPo.selectAction(commonsPo, sChecklistOpDocName);
		checklistPo.validateChecklistServiceReport(commonsPo, workOrderPo, sChecklistOpDocName,sWOName );
		
		
		
	}
}
