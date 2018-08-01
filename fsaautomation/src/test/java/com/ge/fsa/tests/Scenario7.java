package com.ge.fsa.tests;

import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
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
		
		
		
		//Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID, "ChecklistName");
		sChecklistOpDocName =GenericLib.getExcelData(sTestCaseID, "ChecklistOpDocName");
		
		
		//Rest to Create Workorder
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		
		//checklist q's set--check if this needs to come from excel?
		String dynamicResponseTextQuestion = "What is the Work Order Number?";
		String dynamicResponseTextAnswer = null;	
		String checklistrequestionQ = "This is a required question";
		String checklistDefaultQuestion = "Which Division are you from ? default Answer:SVMX";
		String checklistDefaultAns = "SVMX";
		String checklistPickListdynamicQuestion = "What is the Order Status?";
		String checklistPickListdynamicQuestionAns;
		String TargetObjectUpdatevalue = "Target Object Update";
		String checklistStatus = "Completed";
		
		//sWOName = "WO-00000415";

			
		
		loginHomePo.login(commonsPo, exploreSearchPo);
		//toolsPo.configSync(commonsPo);
		toolsPo.syncData(commonsPo);	
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		// Explore and Navigate to the Search Process
		commonsPo.getSearch(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		// Select the Work Order
		exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
		
		//extract order status value
		
		String OrderStatusVal= workOrderPo.geteleOrderStatusvaluelbl().getText();
		System.out.println("Order Status of Work order is "+OrderStatusVal);
		
		
		 // Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);
		// Navigating to the checklist
		commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
			
		System.out.println("validating dynamic response,checking if work order no is populated inside answer ");
		dynamicResponseTextAnswer = checklistPo.geteleChecklistAnswerTextArea(dynamicResponseTextQuestion).getAttribute("value");
		Assert.assertTrue(dynamicResponseTextAnswer.equals(sWOName), "Textbox is not autopopulated with dynamic response, workorder no.");
		NXGReports.addStep("checklist dynamic response Workorderno-textbox populated into checklist", LogAs.PASSED, null);
		
		System.out.println("Validating Dynamic response, checking if orderstatus is populated based on workOrder status");
		checklistPickListdynamicQuestionAns = checklistPo.geteleChecklistAnsPicklist(checklistPickListdynamicQuestion).getAttribute("value");
		Assert.assertTrue(checklistPickListdynamicQuestionAns.equals(OrderStatusVal), "OrderStatus value is not populated correctly .");
		NXGReports.addStep("checklist dynamic response orderstatus-picklist populated into checklist", LogAs.PASSED, null);
		
		
		System.out.println("Validating default value entered to textbox");
		String ans =checklistPo.geteleChecklistAnswerTextArea(checklistDefaultQuestion).getAttribute("value");
		Assert.assertTrue(ans.equals(checklistDefaultAns), "Defualt value is not populated correctly");
	//	checklistDefaultQuestion 
		
		//tapping next button
		commonsPo.tap(checklistPo.eleNext());
		// submitting the checklist
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(checklistPo.eleChecklistSubmit());
		
		//Validation of required question lbl and issue found txt.
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(checklistPo.getelefillrequiredfieldlbl().isDisplayed(),"Failed to provide:Please fill this required field and submit again-checklist");
		NXGReports.addStep("checklist required question validation passed", LogAs.PASSED, null);
		commonsPo.waitforElement(checklistPo.geteleissuefoundlbl(),1000);
		Assert.assertTrue(checklistPo.geteleissuefoundlbl().isDisplayed(),"Failed to display issue found for required question-checklist");
		NXGReports.addStep("checklist required question validation issue display passed", LogAs.PASSED, null);
		checklistPo.geteleChecklistrequiredTxt(checklistrequestionQ).sendKeys("required answer");
		
		
		//submitting of checklist
		
		commonsPo.tap(checklistPo.eleChecklistSubmit());
		commonsPo.longPress(checklistPo.eleChecklistPopupSubmit());
		
		
		//Navigating back to work Orders
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());

		//Navigating to checklistOPDOC process
		checklistPo.validateChecklistServiceReport(commonsPo, workOrderPo, sChecklistOpDocName,sWOName );
	  	checklistPo.geteleChecklistOPDOCRow();
	
	  	//Validating is checklist status in opdoc is completed and also if checklist name is displayed.	  	  	
	  	 Assert.assertTrue(checklistPo.geteleChecklistOPDOCRow().getText().toString().contains(checklistStatus), "Checklist status is not displayed as completed.");		 		
		 Assert.assertTrue(checklistPo.geteleChecklistOPDOCRow().getText().toString().contains(sChecklistName), "Checklist Name is displayed");
				
		 		 
		//validting if it picks the checklist Question and answer.
		 checklistPo.geteleChecklistAnswerOPDOCtbl();
		 System.out.println( checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString());
		 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(dynamicResponseTextQuestion), "Couldnt find the checklist question in OPDOC");		 		
		 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sWOName), "Couldnt get the WorkOrder no populated through dynamic response");	 	
		
		checklistPo.getEleopDoneLnk().click();
		
		commonsPo.tap(checklistPo.getEleopDoneLnk());
		Thread.sleep(4000);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		Thread.sleep(4000);
	
		//Navigation back to Work Order after Service Report
		Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		NXGReports.addStep("Creation of Checklist OPDOC passed", LogAs.PASSED, null);		
		Thread.sleep(GenericLib.iLowSleep);
		// String ans= workOrderPo.geteleProblemDescriptionlbl().getText();
		// System.out.println(ans);
		 Assert.assertTrue(workOrderPo.geteleProblemDescriptionlbl().getText().toString().equals(TargetObjectUpdatevalue), "Target Object UPDATE did not happen");
		
		 toolsPo.syncData(commonsPo);
		 
		// Verifying if checklistopdoc is synced to server
		 System.out.println("Validating if attachment is syned to server.");
			String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
			restServices.getAccessToken();
			String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlqueryAttachment, "Id");	
			Thread.sleep(GenericLib.iMedSleep);
			assertNotNull(sAttachmentIDAfter);
			
			
		// 	Verifying Targetobject update in SF
			System.out.println("TArget OBJECT validation from SF");
			System.out.println(TargetObjectUpdatevalue);		
			String targetobjectupdateO = "Select+SVMXC__Problem_Description__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\'";
			//String targetobjectupdateO = "Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
			
			
			
			restServices.getAccessToken();
			//System.out.println(targetobjectupdateO);
		//	String targetobjectupdateOL = restServices.restGetSoqlValue(targetobjectupdateO, "SVMXC__Problem_Description__c");	
			String targetobjectupdateOL = restServices.restGetSoqlValue(targetobjectupdateO, "SVMXC__Problem_Description__c");	
			System.out.println(targetobjectupdateOL);
			Assert.assertTrue(TargetObjectUpdatevalue.equals(targetobjectupdateOL), "Target Object Value is not updated to server");
		
	}
}
