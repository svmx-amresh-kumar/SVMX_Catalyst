/*
 *  @author Vinod Tharavath
 */
package com.ge.fsa.tests.phone;

import static org.testng.Assert.assertNotNull;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_Sanity7_Dynamic_Response_Checklist_OPDOC_TOU extends BaseLib{
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
	String OrderStatusVal ="Open";
	String sSheetName =null;
	Boolean bProcessCheckResult  = false;
	String sProcessname = "Default title for Checklist";

	//For SFM Process Sahi Script name
		String sScriptName="/appium/scenario7_preRequisite.sah";
	//@Test()
	@Test(retryAnalyzer=Retry.class)
	public void scenario7() throws Exception {
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6501");
		}else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6793");

		}
		
		sSheetName ="Scenario7_Checklist";
		sTestCaseID = "Scenario7_Checklist";
		sCaseWOID = "Data_Scenario7_Checklist";
		

		//Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName");
		sChecklistOpDocName =CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistOpDocName");
	
		
		
		//Rest to Create Workorder
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		
		//checklist q's set--
		String sDynamicResponseTextQuestion = "1. What is the Work Order Number?";
		String sDynamicResponseTextQuestionOPDOC = "What is the Work Order Number?";

		String sDynamicResponseTextAnswer = null;
		String sChecklistRequiredQuestion = "* 3. This is a required question, Please Answer.";

		String sChecklistReQuestion = "3. This is a required question, Please Answer.";
		String sChecklistReQuestionOPDOC = "This is a required question, Please Answer.";

		String sChecklistDefaultQuestion = "4. Which Division are you from ? default Answer:SVMX";
		String sChecklistDefaultQuestionOPDC = "Which Division are you from ? default Answer:SVMX";

		String sChecklistDefaultAns = "SVMX";
		String sChecklistPickListDynamicQuestion = "2. What is the Order Status?";
		String sPickListAnswer = "Open";
		String sChecklistPickListdynamicQuestionAns;
		String sTargetObjectUpdateValue = "Target Object Update";
		String sChecklistStatus = "Completed";
		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);

		//sWOName = "WO-00003414";
							
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		//ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);

		// Data Sync
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(2000);

		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sProcessname);
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName + "");

		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		System.out.println("clicked checklistname");
		Thread.sleep(1000);

		// Starting new Checklist
		ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
		Thread.sleep(2000);

		ph_ChecklistPO.geteleInProgress().click();
		System.out.println("Clicked in Progress");
	
		// Text
		commonUtility.custScrollToElement(sDynamicResponseTextQuestion, false);
		String sTextDynamicAnsApp = ph_ChecklistPO.getelechecklistTextQAns(sDynamicResponseTextQuestion).getText();
		Assert.assertTrue(sTextDynamicAnsApp.equals(sWOName), "Static response with picklist failed");
		ExtentManager.logger.log(Status.PASS, "Dynamic response with text datatype Passed expected :" + sWOName + "actual:" + sTextDynamicAnsApp + "");

		//Picklist
		sChecklistPickListdynamicQuestionAns = ph_ChecklistPO.getelechecklistPickListQAns(sChecklistPickListDynamicQuestion, sPickListAnswer).getText().trim();
		Assert.assertTrue(sChecklistPickListdynamicQuestionAns.equals(OrderStatusVal), "OrderStatus value is not populated correctly .");
		ExtentManager.logger.log(Status.PASS,"checklist dynamic response orderstatus-picklist populated into checklist");

		//SVMX value
		commonUtility.custScrollToElement("SVMX", false);
		String ans =ph_ChecklistPO.getelechecklistTextQAns(sChecklistDefaultQuestion).getText();
		Assert.assertTrue(ans.equals(sChecklistDefaultAns), "Defualt value is not populated correctly");
		ExtentManager.logger.log(Status.PASS,"Default Value in checklist Answer validated");

		
		ph_ChecklistPO.geteleSubmitbtn().click();
		
		
		commonUtility.waitforElement(ph_ChecklistPO.geteleQuestion3Required(),10);
		Assert.assertTrue(ph_ChecklistPO.geteleQuestion3Required().isDisplayed(),"Failed to display issue found for required question-checklist");
		ExtentManager.logger.log(Status.PASS,"checklist required question validation issue display passed");
		ph_ChecklistPO.getelechecklistNumberQAns(sChecklistRequiredQuestion).click();
		ph_ChecklistPO.getelechecklistNumberQAns(sChecklistRequiredQuestion).sendKeys("required answer");

		ph_ChecklistPO.geteleChecklistGenericContainsTxt(sChecklistDefaultQuestion).click();
		
		ph_ChecklistPO.geteleSubmitbtn().click();
		
		//Navigating back to work Orders
		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(2000);
		ph_ChecklistPO.geteleBackbutton().click();

		ph_WorkOrderPo.selectAction(commonUtility, sChecklistOpDocName);
		
	
		commonUtility.waitforElement(ph_ChecklistPO.geteleFinalize(), 15);
		// Do not remove. waitforElement did not work thats why the hard sleep.
		Thread.sleep(8000);
		ph_ChecklistPO.geteleFinalize().click();
		// cust displayed did not work
		Thread.sleep(8000);
		// Navigation back to Work Order after Service Report
		Assert.assertTrue(ph_ExploreSearchPo.geteleExploreIcn().isDisplayed(), "Work Order screen is displayed");
		ExtentManager.logger.log(Status.PASS, "Creation of Checklist OPDOC passed");

		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName, "AUTO_EDIT_WORKORDER");
		commonUtility.gotToTabHorizontal(ph_WorkOrderPo.getStringDocuments());
		ph_ChecklistPO.geteleDocumentInstance().click();

		// rework and improve performance by adding fluent wait
		// cust displayed did not work
		Thread.sleep(18000);

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sChecklistStatus).toString().contains(sChecklistStatus), "Checklist status is not displayed as completed.");
		ExtentManager.logger.log(Status.PASS, "checklist status completed is displayed in OPDOC");

		// defect has been raised checklisttitle is not displayed
		// Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sChecklistName).toString().contains(sChecklistName),
		// "Checklist Name is not displayed");
		// ExtentManager.logger.log(Status.PASS,"checklist Name is displayed in OPDOC");

		// validating if it picks the checklist Question and answer.
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sDynamicResponseTextQuestionOPDOC).toString().contains(sDynamicResponseTextQuestionOPDOC), "Couldnt get the WorkOrder no populated through dynamic response");
		ExtentManager.logger.log(Status.PASS, "WorkORder No populated through dynamic response displayed in OPDOC");

		// validating if it picks the checklist Question and answer.
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sChecklistDefaultAns).toString().contains(sChecklistDefaultAns), "Couldnt get default answer");
		ExtentManager.logger.log(Status.PASS, "OPDOC displays ChecklistDefualtAns:SVMX");

		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			driver.navigate().back();
		} else
			commonUtility.getEleDonePickerWheelBtn().click();

		ph_WorkOrderPo.getEleBackButton().click();

		Thread.sleep(2000);
		commonUtility.custScrollToElement("Description", true);
		// System.out.println(ans);
		 Assert.assertTrue(ph_WorkOrderPo.geteleProblemDescriptiontxt().getText().equals(sTargetObjectUpdateValue), "Target Object UPDATE did not happen");

		 
		// Data Sync
			ph_MorePo.syncData(commonUtility);
		 Thread.sleep(CommonUtility.iAttachmentSleep);
		 
		// Verifying if checklistopdoc is synced to server
		  	System.out.println("Validating if OPDOC attachment is syned to server.");
		  	Thread.sleep(CommonUtility.iMedSleep);
		  	Thread.sleep(CommonUtility.iMedSleep);
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
			Thread.sleep(CommonUtility.iHighSleep);
			Thread.sleep(CommonUtility.iHighSleep);		
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
			String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(ChecklistQueryval.contains(sChecklistStatus),"checklist being updated is not synced to server");
			String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
			ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");
			Assert.assertTrue(sChecklistDefaultAns.contains(sChecklistDefaultAns), "default answer was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"default answer was sycned to server in checklist answer");

		
			
	}
}
