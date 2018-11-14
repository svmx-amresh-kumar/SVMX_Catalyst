/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_2_RS-10578 Verify DataValidation on Checklists
 *  
 *  
 *  Date related stuff will be covered once timezone is sorted.
 */
package com.ge.fsa.tests;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.WorkOrderPO;

public class SCN_Checklist_2_RS_10578 extends BaseLib {
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

	// checklist q's set--
	
	String sDateq = "Date DVRVS";
	String sDateTimeq = "DateTime DVR";
	String sConfirmationDVRq = "Confirmation DVR number cannot be 10";	
	
	String sDateAns = null;
	String sDateTimeAns = null;
	String sConfirmationDVRAns = "10";

	
	String sAdvancedDVRq="Advanced Expression DVR";
	String sAdvanceDVRAns = "20";
	String sAdvancedDVRValidAns = "300";
			

	// For ServerSide Validations
	String schecklistStatus = "Completed";
	String sSheetName =null;
	
	@Test(enabled = true)
	public void RS_10578() throws Exception {
		sSheetName ="RS_10578";
		System.out.println("SCN_RS10578_Checklist_DVR");

		String time = driver.getDeviceTime();
		System.out.println(time);

		sTestCaseID = "SCN_Checklist_2_RS-10578_DVR";
		sCaseWOID = "DATA_SCN_Checklist_2_RS-10578_DVR";

		// Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");

		// Rest to Create Workorder
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);

		// sWOName = "WO-00001266";

		// Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);

		// Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		// toolsPo.configSync(commonsPo);

		// Navigation to WO
		workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);

		// Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);

		// Navigating to the checklist
		commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
		
		checklistPo.geteleChecklistAnsNumber(sAdvancedDVRq).sendKeys("10");
		commonsPo.tap(checklistPo.geteleNext());
		Assert.assertTrue(checklistPo.geteleChecklistAdvanceDVR().isDisplayed(), "DataValidation rule failed for Advanced DVR ");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for Advanced DVR Passed");
		checklistPo.geteleChecklistAnsNumber(sAdvancedDVRq).sendKeys("300");
		
		checklistPo.geteleChecklistAnsDate(sDateq).click();
	    commonsPo.switchContext("Native");
	    System.out.println("SEt to native view now will click done");
	    Thread.sleep(GenericLib.iLowSleep);
	    commonsPo.getEleDonePickerWheelBtn().click();
	   // commonsPo.tap(commonsPo.getEleDonePickerWheelBtn2());
	    commonsPo.switchContext("WebView");
	    sDateAns = checklistPo.geteleChecklistAnsDate(sDateq).getAttribute("value");	    
	    System.out.println("direct sdatetime"+sDateAns);	    
	    Assert.assertTrue(checklistPo.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for date ");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for date Field Passed");
	    
	    checklistPo.geteleChecklistAnsDate(sDateTimeq).click();
	    commonsPo.switchContext("Native");
	   // commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
	    commonsPo.getEleDonePickerWheelBtn().click();
	    commonsPo.switchContext("WebView");
	    sDateTimeAns = checklistPo.geteleChecklistAnsDate(sDateTimeq).getAttribute("value");	    
	    System.out.println("direct sdatetime"+sDateTimeAns);	
	    Assert.assertTrue(checklistPo.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for datetime");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for date Field Passed");
		
		
		checklistPo.geteleChecklistAnsNumber(sConfirmationDVRq).sendKeys(sConfirmationDVRAns);
		// tapping next button
		commonsPo.tap(checklistPo.geteleNext());
		
		Assert.assertTrue(checklistPo.geteleChecklistDVRConfirmationtxt().isDisplayed(), "DataValidation rule failed for number confirmation");	 	
		Assert.assertTrue(checklistPo.geteleDVRConfirmBtn().isDisplayed(),"Confirm button is not being displayed for confirmation dvr");
		ExtentManager.logger.log(Status.PASS,"Confirm button is displayed for confirmation DVR");
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for confirmation Passed");
		
		commonsPo.tap(checklistPo.geteleDVRConfirmBtn());
	    //checklistPo.geteleChecklistAnsDate(sDateTimeq).click();
		commonsPo.setDateTime24hrs(checklistPo.geteleChecklistAnsDate(sDateTimeq), -3, "05", "05");
		Thread.sleep(GenericLib.iLowSleep);
	    //commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
	  
	    //checklistPo.geteleChecklistAnsDate(sDateq).click();
	    commonsPo.setSpecificDateYear(checklistPo.geteleChecklistAnsDate(sDateq),"June", "8", "2018");
		commonsPo.tap(checklistPo.geteleNext());

	    Assert.assertTrue(checklistPo.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for date");	 	
		ExtentManager.logger.log(Status.PASS,"8th June DVR passed date");
	    commonsPo.setSpecificDateYear(checklistPo.geteleChecklistAnsDate(sDateq),"March", "8", "2018");

		// checklistPo.geteleChecklistAnsDate(sDateq).click();
		// commonsPo.setSpecificDateYear(checklistPo.geteleChecklistAnsDate(sDateq),"February", "1", "2018");
		// Assert.assertTrue(checklistPo.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for datetime");	 	
		//ExtentManager.logger.log(Status.PASS,"DataValidation rule for date Field Passed :greater than today error thrown");
		
		Thread.sleep(GenericLib.iLowSleep);
	  
		// tapping next button
		commonsPo.tap(checklistPo.geteleNext());
		// submitting the checklist
		Thread.sleep(GenericLib.iLowSleep);
		//commonsPo.tap(checklistPo.eleChecklistSubmit());

		// Validation of required question lbl and issue found txt.
		Thread.sleep(GenericLib.iLowSleep);

		// submitting of checklist
		commonsPo.clickAllowPopUp();
		commonsPo.switchContext("WebView");
		commonsPo.tap(checklistPo.eleChecklistSubmit());
		commonsPo.tap(checklistPo.geteleChecklistPopupSubmit());

		// Navigating back to work Orders
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());

		// Navigation back to Work Order
		Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		// NXGReports.addStep("Creation of Checklist OPDOC passed", LogAs.PASSED, null);
		ExtentManager.logger.log(Status.PASS, "Back to Work Order after submitting checklist passed");

		Thread.sleep(GenericLib.iLowSleep);

		toolsPo.syncData(commonsPo);

		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());

		// Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sEditProcessName);

	}

}
