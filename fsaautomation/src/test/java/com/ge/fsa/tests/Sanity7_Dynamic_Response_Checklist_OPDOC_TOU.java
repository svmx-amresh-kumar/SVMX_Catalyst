/*
 *  @author Vinod Tharavath
 */
package com.ge.fsa.tests;

import static org.testng.Assert.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.CalendarPO;

public class Sanity7_Dynamic_Response_Checklist_OPDOC_TOU extends BaseLib{
	String sTestCaseID= null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sChecklistOpDocName = null;
	String sExploreChildSearchTxt = null;
	String OrderStatusVal =null;
	String sSheetName =null;

	
	@Test(retryAnalyzer=Retry.class)
	public void scenario7() throws Exception {
		sSheetName ="Scenario7_Checklist";
		sTestCaseID = "Scenario7_Checklist";
		sCaseWOID = "Data_Scenario7_Checklist";
		

		//Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");
		sChecklistOpDocName =GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistOpDocName");
	
		
		
		//Rest to Create Workorder
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		
		//checklist q's set--
		String sDynamicResponseTextQuestion = "What is the Work Order Number?";
		String sDynamicResponseTextAnswer = null;	
		String sChecklistReQuestion = "This is a required question";
		String sChecklistDefaultQuestion = "Which Division are you from ? default Answer:SVMX";
		String sChecklistDefaultAns = "SVMX";
		String sChecklistPickListDynamicQuestion = "What is the Order Status?";
		String sChecklistPickListdynamicQuestionAns;
		String sTargetObjectUpdateValue = "Target Object Update";
		String sChecklistStatus = "Completed";
		
		//sWOName = "WO-00000415";
							
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		
		
		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		//toolsPo.configSync(commonsPo);			
		
		//Navigation to WO
	    workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);				
		
		//extract order status value		
		OrderStatusVal= workOrderPo.geteleOrderStatusvaluelbl().getText();
		System.out.println("Order Status of Work order is "+OrderStatusVal);		
		
		 // Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);
		
		// Navigating to the checklist
		commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
		//commonsPo.longPress();
		Thread.sleep(GenericLib.iLowSleep);
			
		System.out.println("validating dynamic response,checking if work order no is populated inside answer ");
		sDynamicResponseTextAnswer = checklistPo.geteleChecklistAnswerTextArea(sDynamicResponseTextQuestion).getAttribute("value");
		Assert.assertTrue(sDynamicResponseTextAnswer.equals(sWOName), "Textbox is not autopopulated with dynamic response, workorder no.");
		ExtentManager.logger.log(Status.PASS,"checklist dynamic response Workorderno-textbox populated into checklist");
		
		
		System.out.println("Validating Dynamic response, checking if orderstatus is populated based on workOrder status");
		sChecklistPickListdynamicQuestionAns = checklistPo.geteleChecklistAnsPicklist(sChecklistPickListDynamicQuestion).getAttribute("value");
		Assert.assertTrue(sChecklistPickListdynamicQuestionAns.equals(OrderStatusVal), "OrderStatus value is not populated correctly .");
		ExtentManager.logger.log(Status.PASS,"checklist dynamic response orderstatus-picklist populated into checklist");

		
		System.out.println("Validating default value entered to textbox");
		String ans =checklistPo.geteleChecklistAnswerTextArea(sChecklistDefaultQuestion).getAttribute("value");
		Assert.assertTrue(ans.equals(sChecklistDefaultAns), "Defualt value is not populated correctly");
		ExtentManager.logger.log(Status.PASS,"Default Value in checklist Answer validated");

	//	checklistDefaultQuestion 
		
		//tapping next button
		commonsPo.tap(checklistPo.geteleNext());
		// submitting the checklist
		Thread.sleep(GenericLib.iLowSleep);
	//	try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}

		commonsPo.tap(checklistPo.eleChecklistSubmit());
	//	try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}

		//Validation of required question lbl and issue found txt.
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(checklistPo.getelefillrequiredfieldlbl().isDisplayed(),"Failed to provide:Please fill this required field and submit again-checklist");
		ExtentManager.logger.log(Status.PASS,"checklist required question validation passed");

		commonsPo.waitforElement(checklistPo.geteleissuefoundlbl(),1000);
		Assert.assertTrue(checklistPo.geteleissuefoundlbl().isDisplayed(),"Failed to display issue found for required question-checklist");
		ExtentManager.logger.log(Status.PASS,"checklist required question validation issue display passed");

		checklistPo.geteleChecklistrequiredTxt(sChecklistReQuestion).sendKeys("required answer");
		
		
		//submitting of checklist
	//	try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}

		commonsPo.tap(checklistPo.eleChecklistSubmit());
	//	try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}

		commonsPo.longPress(checklistPo.geteleChecklistPopupSubmit());
		
		
		//Navigating back to work Orders
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());

		//Navigating to checklistOPDOC process
		checklistPo.validateChecklistServiceReport(commonsPo, workOrderPo, sChecklistOpDocName,sWOName );
	  	checklistPo.geteleChecklistOPDOCRow();
	
	  	//Validating is checklist status in opdoc is completed and also if checklist name is displayed.	  	  	
	  	 Assert.assertTrue(checklistPo.geteleChecklistOPDOCRow().getText().toString().contains(sChecklistStatus), "Checklist status is not displayed as completed.");		 		
		 Assert.assertTrue(checklistPo.geteleChecklistOPDOCRow().getText().toString().contains(sChecklistName), "Checklist Name is displayed");
				
		 		 
		//validating if it picks the checklist Question and answer.
		 checklistPo.geteleChecklistAnswerOPDOCtbl();
		 System.out.println( checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString());
		 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sDynamicResponseTextQuestion), "Couldnt find the checklist question in OPDOC");	
		ExtentManager.logger.log(Status.PASS,"Found Dynamic REsponse Text question in OPDOC");

		 Assert.assertTrue(checklistPo.geteleChecklistAnswerOPDOCtbl().getText().toString().contains(sWOName), "Couldnt get the WorkOrder no populated through dynamic response");	 	
		ExtentManager.logger.log(Status.PASS,"WorkORder No populated through dynamic response displayed in OPDOC");

	
		commonsPo.tap(workOrderPo.getEleDoneLnk());
		Thread.sleep(GenericLib.iHighSleep);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		Thread.sleep(GenericLib.iHighSleep);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		Thread.sleep(GenericLib.iHighSleep);
		
		//Navigation back to Work Order after Service Report
		Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		ExtentManager.logger.log(Status.PASS,"Creation of Checklist OPDOC passed");

		Thread.sleep(GenericLib.iHighSleep);
		// String ans= workOrderPo.geteleProblemDescriptionlbl().getText();
		// System.out.println(ans);
		 Assert.assertTrue(workOrderPo.geteleProblemDescriptionlbl().getText().equals(sTargetObjectUpdateValue), "Target Object UPDATE did not happen");
		commonsPo.tap(calendarPO.getEleCalendarIcn());	
		 toolsPo.syncData(commonsPo);
		 Thread.sleep(GenericLib.i30SecSleep);
		 
		// Verifying if checklistopdoc is synced to server
		  	System.out.println("Validating if OPDOC attachment is syned to server.");
		  	Thread.sleep(GenericLib.iMedSleep);
		  	Thread.sleep(GenericLib.iMedSleep);
			String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
			restServices.getAccessToken();
			String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlqueryAttachment, "Id");	
			
			assertNotNull(sAttachmentIDAfter); 
			
			
		// 	Verifying Targetobject update in SF
			System.out.println("Validating Target OBJECT update from SF");
			System.out.println(sTargetObjectUpdateValue);		
			String targetobjectupdateO = "Select+SVMXC__Problem_Description__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\'";
			//String targetobjectupdateO = "Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";						
			
			restServices.getAccessToken();
			//System.out.println(targetobjectupdateO);
			String targetobjectupdateOL = restServices.restGetSoqlValue(targetobjectupdateO, "SVMXC__Problem_Description__c");	
			System.out.println(targetobjectupdateOL);
			Assert.assertTrue(sTargetObjectUpdateValue.equals(targetobjectupdateOL), "Target Object Value is not updated to server");
			ExtentManager.logger.log(Status.PASS,"Target Object Update Value is updated to server");
			//Validate if checklist is updated to server
			Thread.sleep(GenericLib.iHighSleep);
			Thread.sleep(GenericLib.iHighSleep);		
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
			String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(ChecklistQueryval.contains(sChecklistStatus),"checklist being updated is not synced to server");
			String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
			ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");
			Assert.assertTrue(ChecklistAnsjson.contains(sDynamicResponseTextAnswer), "dynamicrepsonse workorder no was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"dynamicrepsonse workorder no was sycned to server in checklist answer");
			Assert.assertTrue(sChecklistDefaultAns.contains(sChecklistDefaultAns), "default answer was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"default answer was sycned to server in checklist answer");

		
			
	}
}
