/*
 *  @author Vinod Tharavath
 */
package com.ge.fsa.tests.tablet;

import java.util.Date;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Sanity2_Explore_Checklist_Config_Sync_DataSync_RS11180 extends BaseLib {

	int iWhileCnt = 0;
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sFieldServiceName = null;
	String sProductName1 = null;
	String sProductName2 = null;
	String sActivityType = null;
	String sPrintReportSearch = null;
	String sChecklistName = null;
	String sExploreChildSearchTxt = null;
	String formattedDate = null;
	String sformattedDatetime = null;
	Date dtempDate2;
	Date dTempDate1;
	String sSheetName = null;
	Boolean bProcessCheckResult = false;
	// For SFM Process Sahi Script name
	String sScriptName = "/appium/scenario2_preRequisite.sah";

	//@Test()
	@Test(retryAnalyzer = Retry.class)
	public void scenario2_checklist() throws Exception {
		// JiraLink
		if (BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-11180");
		} else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12050");
		}
		commonUtility.preReqSetup();

		// Resinstall the app
		lauchNewApp("false");
		sSheetName = "RS_2389";
		sTestCaseID = "RS_2389_checklist";
		sCaseWOID = "RS_2389_checklistID";

		// Extracting Excel Data
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ProcessName");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreChildSearch");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ChecklistName");
		System.out.println("---------- " + sChecklistName + "        &&&&&  ");

		// Creation of dynamic Work Order
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?", "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no = " + sWOName);
		// sWOName="WO-00000695";
		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);

		String sradioQuestion = "RadioButton Question";
		String sradioAns = null;
		String stextQuestion = "Test Question";
		String stextAns = "Text Question Answered";
		String snumberQuestion = "Number Question";
		String snumberAns = "200";
		String spicklistQuestion = "Picklist Question";
		String spicklistAns = "PicklOne";
		String sdateQuestion = "Date Question";
		String sdateAns = null;
		String sdateTimeQuestion = "DateTime Question";
		String sdateTimeAns = null;
		String schecklistStatus = "Completed";

		// Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		
		//Optional Config Sync
		toolsPo.OptionalConfigSync(toolsPo, commonUtility, bProcessCheckResult);
		
		// Sync
		toolsPo.syncData(commonUtility);
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName + "");
		// sWOName="WO-00004920";
		
		//Navigating to SFM
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));

		// System.out.println("Entering Text Question Answer");
		commonUtility.tap(checklistPo.geteleChecklistAnswerTextArea(stextQuestion));

		System.out.println("-------------" + stextAns);
		checklistPo.geteleChecklistAnswerTextArea(stextQuestion).sendKeys(stextAns);

		// System.out.println("Entering Number Question Answer");
		commonUtility.tap(checklistPo.geteleChecklistAnsNumber(snumberQuestion));
		checklistPo.geteleChecklistAnsNumber(snumberQuestion).sendKeys(snumberAns);

		// System.out.println("Selecting Picklist Question Answer");
		commonUtility.setPickerWheelValue(checklistPo.geteleChecklistAnsPicklist(spicklistQuestion), spicklistAns);
		commonUtility.switchContext("WebView");

		// Date Question
		commonUtility.setSpecificDate(checklistPo.geteleChecklistAnsDate(sdateQuestion), "0", "0", "0");
		sdateAns = driver.findElement(By.xpath("//div[contains(@class,'checklisteditview')][not(contains(@class,'hidden'))]//div[text()='Date Question'][@class='x-innerhtml']/../following-sibling::div//div[@class='x-innerhtml']")).getAttribute("innerHTML");

		// Set datetime Answer
		commonUtility.setDateTime24hrs(checklistPo.geteleChecklistAnsDate(sdateTimeQuestion), 0, "0", "0");
		commonUtility.switchContext("WebView");
		// sdateTimeAns =
		// checklistPo.geteleChecklistAnsDate(sdateTimeQuestion).getAttribute("innerHTML");

		commonUtility.clickAllowPopUp();
		
		// System.out.println("Setting Radio button Question Answer");
		commonUtility.tap(checklistPo.geteleChecklistAnsradio(sradioQuestion));
		sradioAns = checklistPo.geteleChecklistAnsradio(sradioQuestion).getText();

		commonUtility.clickAllowPopUp();

		// tapping the next button in checklist
		commonUtility.tap(checklistPo.geteleNext());

		// submitting the checklist
		Thread.sleep(CommonUtility.iHighSleep);
			commonUtility.clickAllowPopUp();
		Thread.sleep(CommonUtility.iLowSleep);
		System.out.println(driver.getContext());
		commonUtility.tap(checklistPo.eleChecklistSubmit());
		Thread.sleep(CommonUtility.iHighSleep);
		
		commonUtility.clickAllowPopUp();
		// tapping on the validation sucessfull checklist popup
		commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
		System.out.println("finished clicking on submit popup.");
		Thread.sleep(CommonUtility.iLowSleep);

		// Tapping on Show Completed Checklists
		// System.out.println("going to tap on show completedchecklists");
		commonUtility.tap(checklistPo.geteleShowCompletedChecklist(), 15, 18);
		
		// System.out.println("tapped on completed checklist");
		// System.out.println("going to tap on the completedchecklist");
		commonUtility.tap(checklistPo.geteleCompletedChecklistName(sChecklistName));
		// System.out.println("tapped on completed checklist");
		Thread.sleep(CommonUtility.iMedSleep);

		// System.out.println("====================Checklist AnswersValidation=========================");
		Assert.assertEquals(checklistPo.geteleChecklistAnswerTextArea(stextQuestion).getAttribute("value"), stextAns, "textquestion answered is not displayed");
		ExtentManager.logger.log(Status.PASS, "picklist answer sucessfull expected: " + stextAns + " actual: " + checklistPo.geteleChecklistAnswerTextArea(stextQuestion).getAttribute("value") + "");

		String sToday = checklistPo.get_device_date(commonUtility);
		System.out.println(sToday);
		Assert.assertTrue(sToday.contains(sdateAns), "date checklist question answered is not displayed expected  " + sdateAns + " Actual : " + sToday + "");
		ExtentManager.logger.log(Status.PASS, "Checklist Date Quesiton Answer validation sucessfull expected : " + sdateAns + " Actual : " + sToday + "");

		Assert.assertTrue(checklistPo.geteleChecklistAnsDate(sdateTimeQuestion).getAttribute("value").contains(sToday), "datetime checklist question answered is not displayed");
		ExtentManager.logger.log(Status.PASS, "Checklist Datetime Quesiton Answer validation sucessfull");

		Assert.assertEquals(checklistPo.geteleChecklistAnsNumber(snumberQuestion).getAttribute("value"), snumberAns, "number checklist question answered is not displayed");
		ExtentManager.logger.log(Status.PASS, "Checklist Number Quesiton Answer validation sucessfull expected : " + snumberAns + "Actual : " + checklistPo.geteleChecklistAnsNumber(snumberQuestion).getAttribute("value") + "");

		Assert.assertEquals(checklistPo.geteleChecklistAnsPicklist(spicklistQuestion).getAttribute("value"), spicklistAns, "number checklist question answered is not displayed");
		ExtentManager.logger.log(Status.PASS, "Checklist Number Quesiton Answer validation sucessfull expected :" + checklistPo.geteleChecklistAnsPicklist(spicklistQuestion).getAttribute("value") + "Actual :" + spicklistAns + "");

		// Navigating back to Work Orders
		System.out.println("Going to navigate back to work Order");
		checklistPo.navigateBacktoWorkOrder(commonUtility);

		// Sync the Data
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iVHighSleep);

		// SERVER SIDE API VALIDATIONS

		System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
		String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'" + sWOName + "')";
		String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");
		Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus), "checklist completed is not synced to server");
		ExtentManager.logger.log(Status.PASS, "Checklist Completed status is displayed in Salesforce after sync");

		String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
		Assert.assertTrue(ChecklistAnsjson.contains(stextAns), "checklist text question answer is not synced to server");
		ExtentManager.logger.log(Status.PASS, "checklist text question answer is synced to server");

		Assert.assertTrue(ChecklistAnsjson.contains(snumberAns), "checklist number answer sycned to server in checklist answer");
		ExtentManager.logger.log(Status.PASS, "checklist number answer sycned to server in checklist answer");

		System.out.println("Json"+ChecklistAnsjson);
		String serverDate =	checklistPo.server_date(commonUtility, sToday);
		System.out.println("converted date"+serverDate);
		Assert.assertTrue(ChecklistAnsjson.contains(serverDate), "checklist date answer was not sycned to server in checklist answer");
		ExtentManager.logger.log(Status.PASS, "checklist date question answer synced to server");

		Assert.assertTrue(ChecklistAnsjson.contains(spicklistAns), "checklist picklist answer was not sycned to server in checklist answer");
		ExtentManager.logger.log(Status.PASS, "checklist picklist question answer synced to server");

		Assert.assertTrue(ChecklistAnsjson.contains(sradioAns), "radio picklist answer was not sycned to server in checklist answer");
		ExtentManager.logger.log(Status.PASS, "checklist checkbox question answer synced to server");

	}

}
