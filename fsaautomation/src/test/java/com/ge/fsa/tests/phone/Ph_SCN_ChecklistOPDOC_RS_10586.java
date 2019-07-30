/*
 *  @author Vinod Tharavath
 *  
 *
 *  
 */
package com.ge.fsa.tests.phone;

import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.apache.http.client.utils.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_ChecklistOPDOC_RS_10586 extends BaseLib {

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
	String sChecklistOpDocName = null;
	Date dtempDate2;
	Date dTempDate1;
	String sSheetName = null;
	String sWORecordID = null;
	String sTech_Id = null;
	String sSoqlQueryTech = null;
	String sTechnician_ID = null;
	String sProcessname = "Default title for Checklist";

	// For SFM Process Sahi Script name
	String sScriptName = "/appium/Scenario_RS10586_ChecklistOPDOC_DynamicnStaticRes.sah";
	Boolean bProcessCheckResult = false;

	public void prerequisites() throws Exception {
		sSheetName = "RS_10586";
		sTestCaseID = "SCN_ChecklistOPDOC_2_RS-10586";
		sCaseWOID = "Data_SCN_ChecklistOPDOC_2_RS-10586";
		sCaseSahiFile = "backOffice/appium_verifyWorkDetails.sah";
		// sWOJsonData =
		// "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Bangalore\"}";

		// Extracting Excel Data
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ProcessName");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ChecklistName");
		sChecklistOpDocName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ChecklistOpDocName");

		// Creation of Work Order
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-01-01\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-01-01T00:00:00.000+0000\",\"SVMXC__NoOfTimesAssigned__c\":\"10\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__Proforma_Invoice__c\":\"Proforma Dynamic Response\"}");
		System.out.println(sWORecordID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);

		// Creating a servicemax event and assigning the work order to it.

		sTech_Id = CommonUtility.readExcelData(CommonUtility.sConfigPropertiesExcelFile, sSelectConfigPropFile, "TECH_ID");
		sSoqlQueryTech = "SELECT+Id+from+SVMXC__Service_Group_Members__c+Where+SVMXC__Salesforce_User__c+=\'" + sTech_Id + "\'";
		restServices.getAccessToken();
		sTechnician_ID = restServices.restGetSoqlValue(sSoqlQueryTech, "Id");
		// String sEventName = "AUTO_10586Event";
		// String sEventId = restServices.restCreate("SVMXC__SVMX_Event__c?",
		// "{\"Name\":\""+sEventName+"\",
		// \"SVMXC__Service_Order__c\":\""+sWORecordID+"\",
		// \"SVMXC__Technician__c\":\""+sTechnician_ID+"\",
		// \"SVMXC__StartDateTime__c\":\""+LocalDate.now()+"\",
		// \"SVMXC__EndDateTime__c\":
		// \""+LocalDate.now().plusDays(1L)+"\",\"SVMXC__WhatId__c\":\""+sWORecordID+"\"}");
		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);

		// sWOName="WO-00002849";
	}

	@Test()
	//@Test(retryAnalyzer = Retry.class)
	public void RS_10586() throws Exception {
		// Jira Link
		if (BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6482");
		} else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6770");

		}

		// Static Questions and Answers
		String sCheckboxStaticQ = "Checkbox Static Question";
		String sCheckboxStaticAns = "CheckBoxOne";
		String sDateStaticQ = "7. Date Static Question";
		String sDateStaticQv = "Date Static Question";

		String sDateStaticAns = "01/01/2018";
		String sDateTimeStaticQ = "8. Datetime Static question";
		String sDateTimeStaticQv = "Datetime Static question";

		String sDateTimeStaticAns = "01/01/2018 00:00";
		String sMultiPicklistStaticQ = "MultiPicklist Static Question";
		String sMultiPicklistStaticAns = "MultiOne;MultiTwo";
		String sNumberStaticQ = "1. Number Static Question";
		String sNumberStaticQv = "Number Static Question";

		String sNumberStaticAns = "10.00";
		String sNumbeStaticAnsOP = "10";
		String sPicklistStaticQ = "3. Picklist Static Question";
		String sPicklistStaticQv = "Picklist Static Question";

		String sPicklistStaticAns = "PicklOne";
		String sRadioButtonStaticQ = "4. RadioButton Static Question";
		String sRadioButtonStaticQv = "RadioButton Static Question";

		String sRadioButtonStaticAns = "RadioOne";
		String sTextStaticQ = "2. Text Static Question";
		String sTextStaticQv = "Text Static Question";

		String sTextStaticAns = "Static Answer";

		String sDateDynamicQ = "9. Dynamic Date From Scheduled Date";
		String sDateDynamicQv = "Dynamic Date From Scheduled Date";

		String sDateDynamicAns = "01/01/2018";
		String sDateTimeDynamicQ = "Dynamic DateTime from ScheduledDateTime";
		String sDateTimeDynamicAns = "01/01/2018 00:00";
		String sNumberDynamicQ = "10. Dynamic Number from No Of Times Assigned";
		String sNumberDynamicQv = "Dynamic Number from No Of Times";

		String sNumberDynamicAns = "10";

		String sPicklistDynamicQ = "11. Dynamic Picklist From Billing Type";
		String sPicklistDynamicQv = "Dynamic Picklist From Billing Type";

		String sPicklistDynamicAns = "Contract";
		String sTextDynamicq = "12. Dynamic Text From Proforma Invoice";
		String sTextDynamicqv = "Dynamic Text From Proforma Invoice";

		String sTextDynamicAns = "Proforma Dynamic Response";

		String sChecklistStatus = "Completed";

		prerequisites();
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		// ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo,
		// bProcessCheckResult);
		// Pre Login to app

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		// Navigating to Checklist
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sProcessname);
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName + "");

		// Scrolling
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			commonUtility.custScrollToElement(sChecklistName, false);
		} else {
			commonUtility.custScrollToElement(sChecklistName);
		}

		// Click on ChecklistName
		ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
		System.out.println("clicked checklistname");
		Thread.sleep(3000);

		// Starting new Checklist
		ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
		Thread.sleep(2000);

		// Hitting on Completed button as this is a dynamic response all answers are
		// filled hence complete button
		ph_ChecklistPO.geteleChecklistCompleted().click();

		// Number Static Reponse Question
		String sNumberStaticAnsApp = ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberStaticQ).getText();
		Assert.assertTrue(sNumberStaticAnsApp.equals(sNumberStaticAns), "Static response with Number datatype failed");
		ExtentManager.logger.log(Status.PASS, "Static response with Number datatype Passed expected :" + sNumberStaticAns + "actual:" + sNumberStaticAnsApp + "");

		// Text Static Response

		String sTextStaticAnsApp = ph_ChecklistPO.geteleTextQAnswithMoreInfo(sTextStaticQ).getText();
		System.out.println("sTextStaticAnsApp" + sTextStaticAnsApp);
		Assert.assertTrue(sTextStaticAnsApp.equals(sTextStaticAns), "Static response with picklist failed");
		ExtentManager.logger.log(Status.PASS, "Static response with text datatype Passed expected :" + sTextStaticAns + "actual:" + sTextStaticAnsApp + "");

		// Picklist Static Reponse Question

		commonUtility.custScrollToElement(sPicklistStaticAns, false);
		// commonUtility.custScrollToElement(ph_ChecklistPO.getelechecklistPickListQAns(sPicklistStaticQ,
		// sPicklistStaticAns));
		String sPicklistStaticAnsApp = ph_ChecklistPO.getelechecklistPickListQAns(sPicklistStaticQ, sPicklistStaticAns).getText();
		Assert.assertTrue(sPicklistStaticAnsApp.equals(sPicklistStaticAns), "Static response with picklist failed");
		ExtentManager.logger.log(Status.PASS, "Static response with picklist datatype Passed expected :" + sPicklistStaticAns + "actual:" + sPicklistStaticAnsApp + "");

		// Date and Datetime validations
		commonUtility.custScrollToElement(sDateTimeStaticQ, false);
		String sDateStaticAnsApp = ph_ChecklistPO.getelechecklistdatewithMoreinfo(sDateStaticQ).getText().trim();
		System.out.println(sDateStaticAnsApp);
		Assert.assertTrue(sDateStaticAns.equals(sDateStaticAnsApp), "Static response date failed");
		ExtentManager.logger.log(Status.PASS, "Static response with Date datatype Passed expected :" + sDateStaticAns + "actual:" + sDateStaticAnsApp + "");

		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			commonUtility.custScrollToElement(sDateStaticQ, false);
		} else {
			commonUtility.custScrollToElement(sDateDynamicQ, false);
		}
		String sDateTimeStaticAnsApp = ph_ChecklistPO.getelechecklistdatewithMoreinfo(sDateTimeStaticQ).getText().trim();
		System.out.println(sDateTimeStaticQ);
		Assert.assertTrue(sDateTimeStaticAnsApp.equals(sDateTimeStaticAns), "Static response with DateTime datatype failed");
		ExtentManager.logger.log(Status.PASS, "Static response with DateTime datatype Passed");

		/*
		 * //Checkbox datatype validation String val =
		 * checklistPo.geteleChecklistCheckboxValue("CheckBoxOne").getAttribute(
		 * "checked");
		 * Assert.assertTrue(val.equals("true"),"Static response with Checkbox failed");
		 * ExtentManager.logger.log(Status.PASS,
		 * "checkbox Static response with checkbox datatype Passed");
		 * 
		 * //Radio Button validation String valv =
		 * checklistPo.geteleChecklistRadioButtonValue("RadioOne").getAttribute(
		 * "checked"); System.out.println("radioOne"+valv);
		 * Assert.assertTrue(valv.equals("true"),"Static response with Radio failed");
		 * ExtentManager.logger.log(Status.PASS,
		 * "RadioButton Static response with checkbox datatype Passed");
		 */
		// String sRadioButtonStaticAnsApp =
		// checklistPo.geteleChecklistAnsradio(sRadioButtonStaticQ).getAttribute("type");
		// Assert.assertTrue(sRadioButtonStaticAnsApp.equals(sRadioButtonStaticAns),"Static
		// response with picklist failed");
		// System.out.println("sRadioButtonStaticAnsApp type"+sRadioButtonStaticAnsApp);
		// ExtentManager.logger.log(Status.PASS, "Static response with radio datatype
		// Passed");

		// -----------------DYNAMIC RESPONSE VALIDATIONS in Checklist--------------

		// Date and Datetime validations
		commonUtility.custScrollToElement(sPicklistDynamicQ, false);
		String sDateDynamicAnsApp = ph_ChecklistPO.getelechecklistdatewithMoreinfo(sDateDynamicQ).getText().trim();
		System.out.println(sDateDynamicAnsApp);
		Assert.assertTrue(sDateDynamicAns.equals(sDateDynamicAnsApp), "Dynamic response date failed");
		ExtentManager.logger.log(Status.PASS, "Dynamic response with Date datatype Passed expected :" + sDateDynamicAns + "actual:" + sDateDynamicAnsApp + "");

		// Number question
		commonUtility.custScrollToElement(sPicklistDynamicQ, false);
		String sNumberDynamicAnsApp = ph_ChecklistPO.geteleNumberQAnswithMoreInfo(sNumberDynamicQ).getText();
		;
		Assert.assertTrue(sNumberDynamicAnsApp.equals(sNumberDynamicAns), "Dynamic response with Number datatype failed expected : " + sNumberDynamicAns + "actual :" + sNumberDynamicAnsApp + "");
		ExtentManager.logger.log(Status.PASS, "dynamic response with Number datatype Passed expected : " + sNumberDynamicAns + "actual :" + sNumberDynamicAnsApp + "");

		// Picklist
		commonUtility.custScrollToElement(sTextDynamicq, false);
		System.out.println("SCROLLED");
		String sPicklistDynamicAnsApp = ph_ChecklistPO.getelechecklistPickListQAns(sPicklistDynamicQ, sPicklistDynamicAns).getText();
		Assert.assertTrue(sPicklistStaticAnsApp.equals(sPicklistStaticAns), "Static response with picklist failed expected :" + sPicklistStaticAns + "actual:" + sPicklistDynamicAnsApp + "");
		ExtentManager.logger.log(Status.PASS, "Dynamic response with picklist datatype Passed expected :" + sPicklistStaticAns + "actual:" + sPicklistDynamicAnsApp + "");

		// Text
		commonUtility.custScrollToElement(sTextDynamicAns, false);
		String sTextDynamicAnsApp = ph_ChecklistPO.geteleTextQAnswithMoreInfo(sTextDynamicq).getText();
		Assert.assertTrue(sTextDynamicAnsApp.equals(sTextDynamicAns), "Static response with picklist failed");
		ExtentManager.logger.log(Status.PASS, "Dynamic response with text datatype Passed expected :" + sTextDynamicAns + "actual:" + sTextDynamicAnsApp + "");

		// Submit the checklist
		ph_ChecklistPO.geteleSubmitbtn().click();

		ph_ChecklistPO.geteleCompleted().click();
		Thread.sleep(3000);

		ph_ChecklistPO.getelechecklistinstance().click();
		// VT can add checklist completed validation in form and summary at later point.
		// create new story

		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(3000);
		ph_ChecklistPO.geteleBackbutton().click();
		Thread.sleep(3000);
		ph_ChecklistPO.geteleBackbutton().click();

		// -------------------OPDOC------------------------------
		Thread.sleep(3000);
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

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sTextDynamicAns).toString().contains(sTextDynamicAns), "Couldnt get the WorkOrder no populated through dynamic response");
		ExtentManager.logger.log(Status.PASS, "WorkORder No populated through dynamic response displayed in OPDOC");

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sCheckboxStaticQ).toString().contains(sCheckboxStaticQ), "Couldnt find the checklist question in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Checkbox StaticQuestion Validation passed");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sCheckboxStaticAns).toString().contains(sCheckboxStaticAns), "Couldnt find the checklist Answer in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Checkbox StaticAnswer Validation passed");

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sDateStaticQv).toString().contains(sDateStaticQv), "Couldnt find the date checklist question in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Date StaticQuestion Validation passed");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sDateStaticAns).toString().contains(sDateStaticAns), "Couldnt find the date checklist Answer in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Date Static Answer Validation passed");

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sNumberStaticQv).toString().contains(sNumberStaticQv), "Couldnt find the number checklist question in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Number StaticQuestion Validation passed");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sNumberStaticAns).toString().contains(sNumberStaticAns), "Couldnt find the number checklist Answer in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Number Static Answer Validation passed");

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sPicklistStaticQv).toString().contains(sPicklistStaticQv), "Couldnt find the picklist checklist question in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Picklist StaticQuestion Validation passed");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sPicklistStaticAns).toString().contains(sPicklistStaticAns), "Couldnt find the picklist checklist Answer in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Picklist Static Answer Validation passed");

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sTextStaticQv).toString().contains(sTextStaticQv), "Couldnt find the text checklist question in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Text StaticQuestion Validation passed");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sTextStaticAns).toString().contains(sTextStaticAns), "Couldnt find the text checklist Answer in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Text Static Answer Validation passed");

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sDateDynamicQv).toString().contains(sDateDynamicQv), "Couldnt find the Dynamic checklist date question in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Date Dynamic Question Validation passed");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sDateDynamicAns).toString().contains(sDateDynamicAns), "Couldnt find the Dynamic checklist dateAnswer in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Date Dynamic Response Answer Validation passed");

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sNumberDynamicQv).toString().contains(sNumberDynamicQv), "Couldnt find the Dynamic checklist number question in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Number Dynamic Question Validation passed");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sNumberDynamicAns).toString().contains(sNumberDynamicAns), "Couldnt find the Dynamic checklist number Answer in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Number Dynamic Response Answer Validation passed");

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sPicklistDynamicQv).toString().contains(sPicklistDynamicQv), "Couldnt find the Dynamic checklist picklist question in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Picklist Dynamic Question Validation passed");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sPicklistDynamicAns).toString().contains(sPicklistDynamicAns), "Couldnt find the Dynamic checklist picklist Answer in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Picklist Dynamic Response Answer Validation passed");

		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sTextDynamicqv).toString().contains(sTextDynamicqv), "Couldnt find the Dynamic checklist text uestion in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Text Dynamic Question Validation passed");
		Assert.assertTrue(ph_ChecklistPO.geteleChecklistGenericContainsTxt(sTextDynamicAns).toString().contains(sTextDynamicAns), "Couldnt find the Dynamic checklist text Answer in OPDOC");
		ExtentManager.logger.log(Status.PASS, "Text Dynamic Response Answer Validation passed");

		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			driver.navigate().back();
		} else
			commonUtility.getEleDonePickerWheelBtn().click();

		ph_WorkOrderPo.getEleBackButton().click();

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);

		Thread.sleep(CommonUtility.iAttachmentSleep);

		// String ans= workOrderPo.geteleProblemDescriptionlbl().getText();
		// System.out.println(ans);
		Thread.sleep(CommonUtility.iLowSleep);

		System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
		String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'" + sWOName + "')";
		String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");
		Assert.assertTrue(ChecklistQueryval.contains(sChecklistStatus), "checklist being updated is not synced to server");
		String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
		ExtentManager.logger.log(Status.PASS, "Checklist Completed status is displayed in Salesforce after sync");

		Assert.assertTrue(ChecklistAnsjson.contains(sTextDynamicAns), "dynamicrepsonse text ans was not sycned to server in checklist answer");
		Assert.assertTrue(ChecklistAnsjson.contains(sNumberDynamicAns), "dynamicrepsonse number ans was not sycned to server in checklist answer");
		Assert.assertTrue(ChecklistAnsjson.contains(sPicklistDynamicAns), "dynamicrepsonse Picklist ans was not sycned to server in checklist answer");
		Assert.assertTrue(ChecklistAnsjson.contains(sTextStaticAns), "static response text ans was not sycned to server in checklist answer");
		Assert.assertTrue(ChecklistAnsjson.contains(sNumbeStaticAnsOP), "static number ans was not sycned to server in checklist answer");
		Assert.assertTrue(ChecklistAnsjson.contains(sPicklistStaticAns), "static picklist ans was not sycned to server in checklist answer");

	}
}
