/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_2_RS-10578 Verify DataValidation on Checklists
 *  
 *  Date related stuff will be covered once timezone is sorted.
 */
package com.ge.fsa.tests.tablet;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.WorkOrderPO;

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
	String sWORecordID = null;
	String sScriptName="Scenario_RS10578_Checklist_DVR";


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
	boolean bProcessCheckResult = false;
	
	public void prerequisites() throws Exception
	
	{
		sSheetName ="RS_10578";
		System.out.println("SCN_RS10578_Checklist_DVR");
		String time = driver.getDeviceTime();
		System.out.println(time);

		sTestCaseID = "SCN_Checklist_2_RS-10578_DVR";
		sCaseWOID = "DATA_SCN_Checklist_2_RS-10578_DVR";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
		sChecklistName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ChecklistName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");

		// Rest to Create Workorder
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\"}");
		System.out.println(sWORecordID);
		sWOName = restServices
				.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
		
		bProcessCheckResult =commonUtility.ProcessCheck(restServices, sChecklistName, sScriptName, sTestCaseID);		


		// sWOName = "WO-00001266";
	}
	
	//@Test()
	@Test(retryAnalyzer=Retry.class)
	public void RS_10578() throws Exception {	
		//Prerequisite script
		prerequisites();
		// Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
	    toolsPo.OptionalConfigSync(toolsPo, commonUtility, bProcessCheckResult);
		// Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		// toolsPo.configSync(commonsUtility);
		// Navigation to WO
		workOrderPo.navigatetoWO(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		// Navigate to Field Service process
		workOrderPo.selectAction(commonUtility, sFieldServiceName);
		// Navigating to the checklist
		commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(CommonUtility.iLowSleep);
		checklistPo.geteleChecklistAnsNumber(sAdvancedDVRq).sendKeys("5");
		commonUtility.tap(checklistPo.geteleNext());
		Assert.assertTrue(checklistPo.geteleChecklistAdvanceDVR().isDisplayed(), "DataValidation rule failed for Advanced DVR ");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for Advanced DVR Passed");
		Assert.assertEquals(checklistPo.geteleChecklistErrorBadge().getText(),"1");
		ExtentManager.logger.log(Status.PASS,"ChecklistBadgeError Displays 1 - Passed");
//		checklistPo.geteleChecklistAnsDate(sDateq).click();
//	    commonsUtility.switchContext("Native");
//	    System.out.println("SEt to native view now will click done");
//	    Thread.sleep(GenericLib.iLowSleep);
//	    commonsUtility.getEleDonePickerWheelBtn().click();
//	   // commonsUtility.tap(commonsUtility.getEleDonePickerWheelBtn2());
//	    commonsUtility.switchContext("WebView");
//	    sDateAns = checklistPo.geteleChecklistAnsDate(sDateq).getAttribute("value");
		sDateAns=commonUtility.getDate(checklistPo.geteleChecklistAnsDate(sDateq),"date");
	    System.out.println("direct sdatetime"+sDateAns);	    
	    Assert.assertTrue(checklistPo.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for date ");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for date Field Passed");
		commonUtility.tap(checklistPo.geteleNext());
		
		
		checklistPo.geteleChecklistAnsNumber(sAdvancedDVRq).sendKeys("300");
		Assert.assertEquals(checklistPo.geteleChecklistErrorBadge().getText(),"2");
		ExtentManager.logger.log(Status.PASS,"ChecklistBadgeError Displays 2 - Passed");
		
//	    checklistPo.geteleChecklistAnsDate(sDateTimeq).click();
//	    commonsUtility.switchContext("Native");
//	   // commonsUtility.tap(commonsUtility.getEleDonePickerWheelBtn());
//	    commonsUtility.getEleDonePickerWheelBtn().click();
//	    commonsUtility.switchContext("WebView");
//	    sDateTimeAns = checklistPo.geteleChecklistAnsDate(sDateTimeq).getAttribute("value");
		sDateTimeAns=commonUtility.getDate(checklistPo.geteleChecklistAnsDate(sDateTimeq), "dateTime");
	    System.out.println("direct sdatetime"+sDateTimeAns);	
	    Assert.assertTrue(checklistPo.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for datetime");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for date Field Passed");
		
		
		checklistPo.geteleChecklistAnsNumber(sConfirmationDVRq).sendKeys(sConfirmationDVRAns);
		// tapping next button
		commonUtility.tap(checklistPo.geteleNext());
		
		Assert.assertTrue(checklistPo.geteleChecklistDVRConfirmationtxt().isDisplayed(), "DataValidation rule failed for number confirmation");	 	
		Assert.assertTrue(checklistPo.geteleDVRConfirmBtn().isDisplayed(),"Confirm button is not being displayed for confirmation dvr");
		ExtentManager.logger.log(Status.PASS,"Confirm button is displayed for confirmation DVR");
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for confirmation Passed");
		
		commonUtility.tap(checklistPo.geteleDVRConfirmBtn());
	    //checklistPo.geteleChecklistAnsDate(sDateTimeq).click();
		commonUtility.setDateTime24hrs(checklistPo.geteleChecklistAnsDate(sDateTimeq), -3, "05", "05");
		Thread.sleep(CommonUtility.iLowSleep);
	    //commonsUtility.tap(commonsUtility.getEleDonePickerWheelBtn());
	  
	    //checklistPo.geteleChecklistAnsDate(sDateq).click();
	    commonUtility.setSpecificDate(checklistPo.geteleChecklistAnsDate(sDateq),"June", "8", "2018");
		commonUtility.tap(checklistPo.geteleNext());

	    Assert.assertTrue(checklistPo.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for date");	 	
		ExtentManager.logger.log(Status.PASS,"8th June DVR passed date");
	    commonUtility.setSpecificDate(checklistPo.geteleChecklistAnsDate(sDateq),"March", "8", "2018");

		// checklistPo.geteleChecklistAnsDate(sDateq).click();
		// commonsUtility.setSpecificDateYear(checklistPo.geteleChecklistAnsDate(sDateq),"February", "1", "2018");
		// Assert.assertTrue(checklistPo.geteleChecklistDVRtxt().isDisplayed(), "DataValidation rule failed for datetime");	 	
		//ExtentManager.logger.log(Status.PASS,"DataValidation rule for date Field Passed :greater than today error thrown");
		
		Thread.sleep(CommonUtility.iLowSleep);
		
		// DVR BOUNDARY validations
		checklistPo.geteleChecklistAnsNumber(sAdvancedDVRq).clear();
		checklistPo.geteleChecklistAnsNumber(sAdvancedDVRq).sendKeys("9");
		commonUtility.tap(checklistPo.geteleNext());
		Assert.assertTrue(checklistPo.geteleChecklistAdvanceDVR().isDisplayed(), "DataValidation rule failed for Advanced DVR ");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for Advanced DVR Passed- Boundary test");
		
		checklistPo.geteleChecklistAnsNumber(sAdvancedDVRq).clear();
		checklistPo.geteleChecklistAnsNumber(sAdvancedDVRq).sendKeys("101");
		commonUtility.tap(checklistPo.geteleNext());
		Assert.assertTrue(checklistPo.geteleChecklistAdvanceDVR().isDisplayed(), "DataValidation rule failed for Advanced DVR ");	 	
		ExtentManager.logger.log(Status.PASS,"DataValidation rule for Advanced DVR Passed- Boundary test");
	  
		checklistPo.geteleChecklistAnsNumber(sAdvancedDVRq).clear();
		Thread.sleep(3000);
		checklistPo.geteleChecklistAnsNumber(sAdvancedDVRq).sendKeys("15");
		Thread.sleep(3000);
		commonUtility.tap(checklistPo.geteleNext());
	//	Assert.assertTrue(checklistPo.geteleChecklistAdvanceDVR().isDisplayed(), "DataValidation rule failed for Advanced DVR ");	 	
	//	ExtentManager.logger.log(Status.PASS,"DataValidation rule for Advanced DVR Passed- Boundary test");
		
		// tapping next button
	//	commonsUtility.tap(checklistPo.geteleNext());
		// submitting the checklist
		Thread.sleep(CommonUtility.iLowSleep);
		//commonsUtility.tap(checklistPo.eleChecklistSubmit());
		Thread.sleep(CommonUtility.iLowSleep);

		// submitting of checklist
		commonUtility.clickAllowPopUp();
		commonUtility.switchContext("WebView");
		commonUtility.tap(checklistPo.eleChecklistSubmit());
		commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());

		// Navigating back to work Orders
		commonUtility.tap(checklistPo.geteleBacktoWorkOrderlnk());

		// Navigation back to Work Order
		Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		ExtentManager.logger.log(Status.PASS, "Back to Work Order after submitting checklist passed");

		Thread.sleep(CommonUtility.iLowSleep);

		toolsPo.syncData(commonUtility);

		Thread.sleep(CommonUtility.iMedSleep);
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());

		// Navigate to Field Service process
		workOrderPo.selectAction(commonUtility, sEditProcessName);

	}

}
