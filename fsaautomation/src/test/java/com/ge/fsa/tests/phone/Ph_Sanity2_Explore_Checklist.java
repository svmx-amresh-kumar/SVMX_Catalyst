/*
 *  @author Vinod Tharavath
 */
package com.ge.fsa.tests.phone;

import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_Sanity2_Explore_Checklist extends BaseLib {

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
	String sEditProcessName = null;
	String sWORecordID = null;
	boolean bProcessCheckResult = false;
	//For SFM Process Sahi Script name
	String sScriptName="scenario2_preRequisite";
	
	public void prereq() throws Exception {

		sSheetName = "RS_2389";
		sTestCaseID = "RS_2389_checklist";
		sCaseWOID = "RS_2389_checklistID";

		// Extracting Excel Data
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, sSheetName, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, sSheetName, "ProcessName");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, sSheetName, "ExploreChildSearch");
		// sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName,
		// "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID, sSheetName, "ChecklistName");
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName = restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
		// boolean bProcessCheckResult =commonUtility.ProcessCheck(restServices,
		// genericLib, sChecklistName, sScriptName, sTestCaseID);
		// sWOName1 = "WO-00001615";
		bProcessCheckResult = commonUtility.ProcessCheck(restServices, genericLib, sChecklistName, sScriptName,
				sTestCaseID);

	}

	@Test()
	public void scenario2_checklist() throws Exception {
		// commonsUtility.preReqSetup(genericLib);
		// Resinstall the app
		// lauchNewApp("false");

		sSheetName = "RS_2389";
		sTestCaseID = "RS_2389_checklist";
		sCaseWOID = "RS_2389_checklistID";

		String spicklistQuestion = "1. Picklist Question";
		String sradioQuestion = "2. RadioButton Question";
		String sCheckboxQuestion = "3. Checkbox Question";
		String sMultiPicklistQuestion = "4. MultiPicklist Question";
		String stextQuestion = "5. Test Question";
		String snumberQuestion = "8. Number Question";
		String sdateQuestion = "7. Date Question";
		String sdateTimeQuestion = "6. DateTime Question";
		String stextAns = "Text Question Answered";
		String snumberAns = "200";
		String sradioAns = null;
		String spicklistAns = "PicklOne";
		String sdateAns = null;
		String sdateTimeAns = null;
		String schecklistStatus = "Completed";
		String sPicklistval = "PicklOne";
		String sPicklistAns = null;
		String sTextQAns = null;
		String sNumberQAns = null;
		String sValue = "MultiOn, MultiTwo";
		String sProcessname = "Default title for Checklist";
		String sDateExpected = "1/1/2019";

		prereq();
/*		genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah");
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Sahi verification is successful");
*/
		
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		ph_MorePo.OptionalConfigSync(commonUtility,ph_CalendarPo,bProcessCheckResult);
		// toolsPo.configSync(commonsUtility);

		
		
		// Data Sync
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		System.out.println(sWOName);

		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
				sWOName, sProcessname);

		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		System.out.println("clicked checklistname");
		Thread.sleep(5000);

		// Starting new Checklist
		ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
		Thread.sleep(2000);
		ph_ChecklistPO.geteleInProgress().click();

		// Entering Picklist Question
		ph_ChecklistPO.getelechecklistPickListQAns(spicklistQuestion, "PicklOne").click();
		Thread.sleep(2000);
		ph_ChecklistPO.geteleChecklistGenericText("PicklOne").click();
		ph_ChecklistPO.elechecklistRadioButtonQAns(sradioQuestion, "RadioTwo").click();

		// Entering Checkbox Question
		commonUtility.custScrollToElementAndClick(sMultiPicklistQuestion);
		ph_ChecklistPO.getelechecklistcheckboxQAns(sCheckboxQuestion, "CheckBoxTwo").click();
		;

		// Selecting MultiPicklist Question
		commonUtility.custScrollToElementAndClick("MultiOn, MultiTwo");
		// ph_ChecklistPO.getelechecklistMultiPicklistQAns("4. MultiPicklist Question",
		// "MultiOn, MultiTwo").click();
		Thread.sleep(2000);
		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(1000);

		// Entering Text question
		commonUtility.custScrollToElementAndClick(stextQuestion);
		ph_ChecklistPO.getelechecklistTextQAns(stextQuestion).sendKeys(stextAns);

		// Entering DateTime question
		commonUtility.custScrollToElement("6. DateTime Question");
		//commonUtility.setDateTime12Hrs(ph_ChecklistPO.getelechecklistdate(sdateTimeQuestion), 0, "5", "30", "AM");
		commonUtility.setDateTime24hrs(ph_ChecklistPO.getelechecklistdate(sdateTimeQuestion), 0, "0", "0");

		// Entering Date question
		commonUtility.custScrollToElementAndClick("7. Date Question");
		commonUtility.setSpecificDate(ph_ChecklistPO.getelechecklistdate(sdateQuestion), "JAN", "01", "2019");

		// Entering Number Question
		commonUtility.custScrollToElementAndClick(snumberQuestion);
		ph_ChecklistPO.getelechecklistNumberQAns(snumberQuestion).sendKeys(snumberAns);

		// Submitting Checklist
		ph_ChecklistPO.geteleSubmitbtn().click();

		// Opening Completed Checklist
		ph_ChecklistPO.geteleCompleted().click();
		Thread.sleep(1000);
		ph_ChecklistPO.geteleName().click();
		Thread.sleep(1000);
		ph_ChecklistPO.geteleInProgress().click();

		// ------------------------validating values after submitting---------

		// picklist validation
		sPicklistAns = ph_ChecklistPO.getelechecklistPickListQAns(spicklistQuestion, "PicklOne").getText();
		Assert.assertTrue(sPicklistAns.equals(sPicklistval),
				"Picklist answer --expected: " + sPicklistval + " actual: " + sPicklistAns + "");
		ExtentManager.logger.log(Status.PASS,
				"picklist answer sucessfull expected: " + sPicklistval + " actual: " + sPicklistAns + "");

		// Text question
		commonUtility.custScrollToElementAndClick(sdateTimeQuestion);
		sTextQAns = ph_ChecklistPO.getelechecklistTextQAnsValue(stextQuestion, stextAns).getText();
		Assert.assertTrue(sTextQAns.equals(stextAns),
				"Checklist Text answer --expected: " + stextAns + " actual: " + sTextQAns + "");
		ExtentManager.logger.log(Status.PASS,
				"Checklist Text answer sucessfull expected: " + stextAns + " actual: " + sTextQAns + "");

		// Date question
		String sDateQAns = ph_ChecklistPO.getelechecklistDateQAnsValue(sdateQuestion, sDateExpected).getText();
		Assert.assertTrue(sDateQAns.equals(sDateExpected),
				"Checklist Text answer --expected: " + sDateExpected + " actual: " + sDateQAns + "");
		ExtentManager.logger.log(Status.PASS,
				"Checklist Text answer sucessfull expected: " + sDateQAns + " actual: " + sDateQAns + "");

		/*
		 * commonUtility.custScrollToElementAndClick(snumberQuestion);
		 * Thread.sleep(3000); sNumberQAns =
		 * ph_ChecklistPO.getelechecklistTextQAnsValue(snumberQuestion,snumberAns).
		 * getText(); Assert.assertTrue(sNumberQAns.equals(snumberAns),
		 * "Checklist Text answer --expected: " + snumberAns + " actual: " + sNumberQAns
		 * + ""); ExtentManager.logger.log(Status.PASS,
		 * "Checklist Text answer sucessfull expected: " + snumberAns + " actual: " +
		 * sNumberQAns + "");
		 * 
		 */
		// Sync the Data
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iVHighSleep);

		// SERVER SIDE API VALIDATIONS

		System.out.println(
				"validating if checklist is synced to server.validate the checklist status and answers through API.");
		String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"
				+ sWOName + "')";
		String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");
		Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus), "checklist completed is not synced to server");
		ExtentManager.logger.log(Status.PASS, "Checklist Completed status is displayed in Salesforce after sync");

		String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
		Assert.assertTrue(ChecklistAnsjson.contains(stextAns),
				"checklist text question answer is not synced to server");
		ExtentManager.logger.log(Status.PASS, "checklist text question answer is synced to server");

		/*
		 * Assert.assertTrue(ChecklistAnsjson.contains(snumberAns),
		 * "checklist number answer sycned to server in checklist answer");
		 * ExtentManager.logger.log(Status.
		 * PASS,"checklist number answer sycned to server in checklist answer");
		 */
		// Assert.assertTrue(ChecklistAnsjson.contains(formattedDate), "checklist date
		// answer was not sycned to server in checklist answer");
		// ExtentManager.logger.log(Status.PASS,"checklist date question answer synced
		// to server");

		// Assert.assertTrue(ChecklistAnsjson.contains(sformattedDatetime), "checklist
		// datetime answer was not sycned to server in checklist answer");
		// ExtentManager.logger.log(Status.PASS,"checklist datetime question answer
		// synced to server");

		Assert.assertTrue(ChecklistAnsjson.contains(spicklistAns),
				"checklist picklist answer was not sycned to server in checklist answer");
		ExtentManager.logger.log(Status.PASS, "checklist picklist question answer synced to server");

		/*
		 * Assert.assertTrue(ChecklistAnsjson.contains(sradioAns),
		 * "radio picklist answer was not sycned to server in checklist answer");
		 * ExtentManager.logger.log(Status.
		 * PASS,"checklist checkbox question answer synced to server");
		 */

		// Navigating to Checklist
		
		ph_CalendarPo.getEleCalendarBtn().click();
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
				sWOName, sProcessname);
		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		System.out.println("clicked checklistname");
		Thread.sleep(5000);
		ph_ChecklistPO.geteleCompleted().click();
		ph_ChecklistPO.getelechecklistinstance().click();

		// Validation after sync
		System.out.println(
				"validating if checklist is synced to server.validate the checklist status and answers through API.");
		ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"
				+ sWOName + "')";
		ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");
		Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus), "checklist completed is not synced to server");
		ExtentManager.logger.log(Status.PASS, "Checklist Completed status is displayed in Salesforce after sync");

		ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
		Assert.assertTrue(ChecklistAnsjson.contains(stextAns),
				"checklist text question answer is not synced to server");
		ExtentManager.logger.log(Status.PASS, "checklist text question answer is synced to server");

		/*
		 * Assert.assertTrue(ChecklistAnsjson.contains(snumberAns),
		 * "checklist number answer sycned to server in checklist answer");
		 * ExtentManager.logger.log(Status.
		 * PASS,"checklist number answer sycned to server in checklist answer");
		 */
		// Assert.assertTrue(ChecklistAnsjson.contains(formattedDate), "checklist date
		// answer was not sycned to server in checklist answer");
		// ExtentManager.logger.log(Status.PASS,"checklist date question answer synced
		// to server");

		// Assert.assertTrue(ChecklistAnsjson.contains(sformattedDatetime), "checklist
		// datetime answer was not sycned to server in checklist answer");
		// ExtentManager.logger.log(Status.PASS,"checklist datetime question answer
		// synced to server");

		Assert.assertTrue(ChecklistAnsjson.contains(spicklistAns),
				"checklist picklist answer was not sycned to server in checklist answer");
		ExtentManager.logger.log(Status.PASS, "checklist picklist question answer synced to server");

	}

	@AfterMethod
	public void tearDownDriver() {
		// driver.quit();
	}

}
