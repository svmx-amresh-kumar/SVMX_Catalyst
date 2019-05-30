/*
 @author Vinod Tharavath
 SCN_DVR_RS_10550
 
 
 SCRIPT PRE REQUISITES TASKS ---
 === MAKE SURE custom fields Auto_Date1 and 2 are created in workOrder the ORG before running.
 === 

 */

package com.ge.fsa.tests.phone;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.pagefactory.iOSFindBy;

public class Ph_SCN_DVR_RS_10550 extends BaseLib {

	String sTestCaseID = null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal = null;
	String sEditProcessName = null;
	String sProductName = "v1";
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectAccID = null;
	String sSqlAccQuery = null;
	String sAccountName = null;
	String sWORecordID = null;
	String sSqlAccQuery1 = null;
	String sAccountName1 = null;
	// DVR STRINGS
	String sProbDescRequired = "The field Problem Description is required";
	String sAccountDVR = "Account cannot be same as Partner Account";
	String sBooleanDVR = "Is Entitlement Performed has to be true..";
	String sNoDVR = "Number1 cannot be anything between 100 and 200. Also number 302 is not allowed";
	String sDate1DVR = "Auto_Date1 cannot be greater than Auto_Date2";
	String sScheduledDateTimeDVR = "Scheduled_DateTime cannot be Today";
	String sScheduledDateDVR = "Scheduled Date cannot be Today or Yesterday or Tommorow";

	String sPartsLineQtyDVR = "Line Qty has to be more than 2 and WD cannot be Null";
	String sPartsLinePriceDVR = "Line Price is Less than 2000";
	String sBillingDVR = "Billing Type cannot be Loan";
	String sBillingTypeDVR = "Loan";
	String SBillingType = "Warranty";
	String sSheetName = null;
	String sObjectApi1 = null;
	String sJsonData1 = null;

	// For SFM Process Sahi Script name
	String sScriptName = "SCN_DVR_RS_10550";
	Boolean bProcessCheckResult = false;

	public void prerequisites() throws Exception {
		sSheetName = "RS_10550";
		sTestCaseID = "SCN_DVR_RS_10550";
		sCaseWOID = "Data_SCN_DVR_RS_10550";
		String sAccountNameToCreate = "Auto_10550_Account";
		String sAccountNameToCreate1 = "Auto_10550_Account1";

		// Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, sSheetName, "ProcessName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID, sSheetName, "EditProcessName");

		// Creation of dynamic Work Order
		restServices.getAccessToken();
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		sWOName = restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
		// sWOName="WO-00002400";

		// Account Creation

		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \"" + sAccountNameToCreate + "\"}";
		sObjectAccID = restServices.restCreate(sObjectApi, sJsonData);
		sSqlAccQuery = "SELECT+name+from+Account+Where+id+=\'" + sObjectAccID + "\'";
		sAccountName = restServices.restGetSoqlValue(sSqlAccQuery, "Name");
		System.out.println(sAccountName);

		// Creating Product from API
		sProductName = "RS10550Product";
		restServices.restCreate("Product2?", "{\"Name\":\"" + sProductName + "\" }");

		bProcessCheckResult = commonUtility.ProcessCheck(restServices, genericLib, sFieldServiceName, sScriptName,
				sTestCaseID);

		sObjectApi1 = "Account?";
		sJsonData1 = "{\"Name\": \"" + sAccountNameToCreate1 + "\"}";
		String sObjectAccID1 = restServices.restCreate(sObjectApi1, sJsonData1);
		sSqlAccQuery1 = "SELECT+name+from+Account+Where+id+=\'" + sObjectAccID1 + "\'";
		sAccountName1 = restServices.restGetSoqlValue(sSqlAccQuery1, "Name");
		System.out.println(sAccountName1);

	}

	@Test(retryAnalyzer=Retry.class)
	public void RS_10550() throws Exception {

		prerequisites();
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		// Optional configy sync
		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
		// Config sync for the process to come in FSA.
		// toolsPo.configSync(commonsUtility);
		Thread.sleep(GenericLib.iLowSleep);
		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);

		// Navigation to WO
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sFieldServiceName);

		// commonUtility.gotToTabHorizontal(ph_WorkOrderPo.getStringParts());

		// Setting up Data for DVR billing type picklist
		Thread.sleep(GenericLib.iLowSleep);
		commonUtility.custScrollToElement(ph_CreateNewPo.getElebillingtype());
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getElebillingtype(), sBillingTypeDVR);

		// Validation of picklist DVR billing type cannot be loan
		Assert.assertTrue(ph_WorkOrderPo.getDvrText(sBillingDVR).isDisplayed(),
				"pickList DVR failed:Billing type cannot be Loan is not displayed");
		ExtentManager.logger.log(Status.PASS, "picklist DVR validation PASS: " + sBillingDVR + " is displayed");

		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getElebillingtype(), SBillingType);
		try {
			Assert.assertFalse(ph_WorkOrderPo.getDvrText(sBillingDVR).isDisplayed(),
					"pickList DVR failed:Billing type cannot be Loan is not displayed");

		} catch (Exception e) {
			ExtentManager.logger.log(Status.PASS,
					"picklist DVR validation message is no longer displayed after switching billing type to :"
							+ SBillingType + "");
		}

		// Validation of TextArea
		ph_WorkOrderPo.getEleSaveLnk().click();
		Assert.assertTrue(ph_WorkOrderPo.getDvrText("Problem Description cannot be Null..").isDisplayed(),
				"Required Field Validation failed");
		commonUtility.custScrollToElement(ph_WorkOrderPo.geteleProblemDescriptiontxt());
		ph_WorkOrderPo.geteleProblemDescriptiontxt().sendKeys("Ok");
		ph_WorkOrderPo.getEleSaveLnk().click();

		try {
			Assert.assertFalse(ph_WorkOrderPo.getDvrText("Problem Description cannot be Null..").isDisplayed(),
					"Required Field Validation failed");

		} catch (Exception e) {
			ExtentManager.logger.log(Status.PASS,
					"PRoblem Description cannot be null is no longer displayed after entering text");
		}

		// Validation of
		ph_WorkOrderPo.getEleSaveLnk().click();
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleAutoDate1_Edit_Input());
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(sBooleanDVR), 3),
				"Boolean Field confirmation failed.");
		ph_WorkOrderPo.geteleConfirm().click();
		ExtentManager.logger.log(Status.PASS, "Boolean field confirmationvalidation passed");

		// ph_WorkOrderPo.geteleEntitlementPerformed().click();
		
		
		//Setting up Auto_date1 greater than Auto_date2
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleAutoDate1_Edit_Input()
		); commonUtility.setSpecificDate(ph_WorkOrderPo.getEleAutoDate1_Edit_Input(),
		"June", "3", "2019");
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleAutoDate2_Edit_Input()
		); commonUtility.setSpecificDate(ph_WorkOrderPo.getEleAutoDate2_Edit_Input(),
		"May", "3", "2019");
		
		Assert.assertTrue(ph_WorkOrderPo.getDvrText(sDate1DVR).isDisplayed(),
		"Date1 DVR did not display,Auto_Date1 cant be greater than Auto_date2");
		ExtentManager.logger.log(Status.
		PASS,"Auto_date1 DVR passed - Auto_Date1 can't be greater than Auto_Date2 displayed"
		);
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleAutoDate2_Edit_Input()
		); commonUtility.setSpecificDate(ph_WorkOrderPo.getEleAutoDate2_Edit_Input(),
		"June", "3", "2019");
		 

		/*
		//number DVR no of times assigned
		commonUtility.custScrollToElement(ph_WorkOrderPo.
		GetEleNoOfTimesAssigned_Edit_Input());
		ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("302");
		ph_WorkOrderPo.getElesave().click(); Thread.sleep(3000);
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(
		sNoDVR), 3), "Number DVR did not display,Number cannot be 302");
		ExtentManager.logger.log(Status.PASS,"Number 302 displayed DVR Message :"
		+sNoDVR+""); //Trying with no150 - should throw DVR again.
		ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().clear();
		ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("150");
		ph_WorkOrderPo.getElesave().click();
		Assert.assertTrue(ph_WorkOrderPo.getDvrText(sNoDVR).isDisplayed(),
		"Number DVR did not display,Number cannot be 302");
		ExtentManager.logger.log(Status.
		PASS,"Validated if 150 displayed DVR Message :"+sNoDVR+"");
		ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().clear();
		ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("20");*/
		 

		// Setting up scheduled DAtetime to today.
		System.out.println("Validation of Datetime starts now");
		commonUtility.custScrollToElement(ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleScheduledDateTimeTxt(), 0, "0", "0");
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(sScheduledDateTimeDVR), 3),
				"DateTime Literal Today DVR did not trigger");
		ExtentManager.logger.log(Status.PASS, "DateTime Literal Today validation passed");
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleScheduledDateTimeTxt(), 3, "0", "0");

		// Setting up Scheduled Date to today
		commonUtility.custScrollToElement(ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleScheduledDate());
		commonUtility.setSpecificDate(ph_WorkOrderPo.getEleScheduledDate(), "0", "0", "0");
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(sScheduledDateDVR), 3),
				"DateTime Literal Today DVR did not trigger");
		ExtentManager.logger.log(Status.PASS, "DATE Literal Today validation passed");
		Thread.sleep(GenericLib.iLowSleep);
		commonUtility.setSpecificDate(ph_WorkOrderPo.getEleScheduledDate(), "June", "1", "2019");
		// ph_WorkOrderPo.getElesave().click();
		// Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sAccountDVR).isDisplayed(),
		// "Look up DVR did not display, account cannot be same as Parnter Account ");

		// eleAccountLookUp DVR
		Thread.sleep(2000);
		System.out.println("Begining account look upf");
		commonUtility.custScrollToElement(ph_CreateNewPo.getelePartnerAccountLookUp());
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_CreateNewPo.getEleAccountLookUp(), sAccountName);
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_CreateNewPo.getelePartnerAccountLookUp(),
				sAccountName);
		ph_WorkOrderPo.getElesave().click();
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(sAccountDVR), 3),
				"Look up DVR did not display, account cannot be same as Parnter Account ");
		ExtentManager.logger.log(Status.PASS,
				"Lookup DVR passed Account cannot be same as Partner Account is displayed");
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_WorkOrderPo.getEleAccount(), sAccountName1);
		ph_WorkOrderPo.getEleSaveLnk().click();
		// Assert.assertTrue(commonUtility.waitForElementNotVisible(ph_WorkOrderPo.getDvrText(sAccountDVR),
		// 3), "Look up DVR did not display, account cannot be same as Parnter Account
		// ");
		// ExtentManager.logger.log(Status.PASS,"Lookup DVR passed Account cannot be
		// same as Partner Account not displayed when accounts are different.");

		/*
		//Trying with no150 - sohuld throw DVR again.
		commonUtility.tap(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().clear();
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("150");
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys(Keys.ENTER);
		
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		commonUtility.tap(workOrderPo.getEleIssueFoundTxt());
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sNoDVR).isDisplayed(),
		"Number DVR did not display,Number cannot be 302");
		ExtentManager.logger.log(Status.PASS,"Number 150 displayed DVR Message ");
		
		try {
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sAccountDVR).isDisplayed(),
		"Look up DVR did not display, account cannot be same as Parnter Account ");
		ExtentManager.logger.log(Status.FAIL,"Account DVR should not be displayed");
		
		} catch (Exception e) {
		ExtentManager.logger.log(Status.PASS,"Account DVR is no longer displayed");
		
		  }*/
		 
		Thread.sleep(GenericLib.iLowSleep);
		ph_CalendarPo.getEleCalendarBtn().click();
		Thread.sleep(GenericLib.iLowSleep);
		ph_ExploreSearchPo.geteleExploreIcn().click();

		// navigating to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sFieldServiceName);

		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleAutoDate1_Edit_Input());
		ph_WorkOrderPo.geteleProblemDescriptiontxt().sendKeys("Ok");
		ph_WorkOrderPo.getEleSaveLnk().click();
		commonUtility.custScrollToElement(ph_WorkOrderPo.geteleConfirm());
		ph_WorkOrderPo.geteleConfirm().click();

		// Adding Partts
		ph_WorkOrderPo.addParts(commonUtility, sProductName);
		ph_WorkOrderPo.getEleSaveLnk().click();
		ph_WorkOrderPo.getelePartName(sProductName).click();

		// validation of Parts DVR
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(sPartsLineQtyDVR), 3),
				"PARts Line qty cannot be less than 2 and work description cannot be null");
		ExtentManager.logger.log(Status.PASS, "Parts lineqty dvr displayed");
		ph_WorkOrderPo.getEleValidationToggle().click();

		// commonUtility.custScrollToElement(ph_WorkOrderPo.geteleLinePriceConfirmationtxt());
		// ph_WorkOrderPo.getEleLineQtyField().clear();
		String svalue = "3";
		String svalue2 = "100";
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			ph_WorkOrderPo.geteleLineQtymandatoryfldd().sendKeys(svalue + "\\n");
			ph_WorkOrderPo.getEleLinePriceField().sendKeys(svalue + "\\n");
		} else {
			ph_WorkOrderPo.geteleLineQtymandatoryfldd().sendKeys(svalue + "\n");
			driver.hideKeyboard();
			ph_WorkOrderPo.getEleLinePriceField().sendKeys(svalue + "\n");
			driver.hideKeyboard();

		}

		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.geteleLinePriceConfirmationtxt(), 3),
				sPartsLinePriceDVR);
		ExtentManager.logger.log(Status.PASS,
				"Parts DVR PASS :Line Quantity confirmation message displayed" + sPartsLinePriceDVR + "");
		ph_WorkOrderPo.geteleConfirm().click();
		ExtentManager.logger.log(Status.PASS, "Clicked on Confirm button");
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			commonUtility.custScrollToElement(ph_WorkOrderPo.geteleBillableQty());

		} else {
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleDiscountPercentage());

		}
		ph_WorkOrderPo.geteleWorkDescription().click();
		ph_WorkOrderPo.geteleWorkDescription().sendKeys("Testeer");
		ph_WorkOrderPo.getBtnSave().click();
		ph_WorkOrderPo.getBtnSave().click();

		//ph_WorkOrderPo.getEleBackButton().click();
		/*if (BaseLib.sOSName.equalsIgnoreCase("android")) {
		}*/
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getEleOverViewTab(), 3),
				"Overview tab is not displayed, might not have saved correctly please check!");
		ExtentManager.logger.log(Status.PASS, "OverView Tab is displayed post saving.");

	}

}
