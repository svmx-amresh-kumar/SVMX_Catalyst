/*
 *  @author Vinod Tharavath
 *  SCN_DVR_RS_10550
 *  
 *  
 *  SCRIPT PRE REQUISITES TASKS ---
 *  === MAKE SURE custom fields Auto_Date1 and 2 are created in workOrder the ORG before running.
 *  === 
 * 
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

public class Ph_SCN_DVR_RS_10550 extends BaseLib{	
	
	String sTestCaseID= null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal =null;
	String sEditProcessName = null;
	String sProductName = "v1";	
	String sObjectApi = null;
	String sJsonData=null;
	String sObjectAccID=null;
	String sSqlAccQuery=null;	
	String sAccountName=null;
	String sWORecordID = null;
	String sSqlAccQuery1 = null;
	String sAccountName1 = null;
	//DVR STRINGS
	String sProbDescRequired = "The field Problem Description is required";
	String sAccountDVR = "Account cannot be same as Partner Account";
	String sBooleanDVR= "Is Entitlement Performed has to be true..";
	String sNoDVR = "Number1 cannot be anything between 100 and 200. Also number 302 is not allowed";
	String sDate1DVR = "Auto_Date1 cannot be greater than Auto_Date2";
	String sScheduledDateTimeDVR="Scheduled_DateTime cannot be Today";
	String sScheduledDateDVR = "Scheduled Date cannot be Today or Yesterday or Tommorow";

	String sPartsLineQtyDVR = "Parts: Line Qty has to be more than 2 and WD cannot be Null";
	String sPartsLinePriceDVR="Parts: Line Price is Less than 2000";
	String sBillingDVR="Billing Type cannot be Loan";
	String sBillingTypeDVR = "Loan";
	String SBillingType = "Warranty";
	String sSheetName =null;
	
	//For SFM Process Sahi Script name
		String sScriptName="SCN_DVR_RS_10550";
		Boolean bProcessCheckResult  = false;
	
	public void prerequisites() throws Exception
	{
		sSheetName ="RS_10550";
		sTestCaseID = "SCN_DVR_RS_10550";
		sCaseWOID = "Data_SCN_DVR_RS_10550";
		String sAccountNameToCreate = "Auto_10550_Account";
		
		//Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");
			
		// Creation of dynamic Work Order
		restServices.getAccessToken();
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		//sWOName="WO-00002400";
		
		//Account Creation

		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sAccountNameToCreate+"\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
		System.out.println(sAccountName);		
		
		// Creating Product from API
				sProductName = "RS10550Product";
				restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");	
				
				bProcessCheckResult =commonUtility.ProcessCheck(restServices, genericLib, sFieldServiceName, sScriptName, sTestCaseID);		
		
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sAccountNameToCreate+"\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlAccQuery1 ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		sAccountName1 =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
		System.out.println(sAccountName1);		
		
				
				
				
	}
	@Test//(retryAnalyzer=Retry.class)
	public void RS_10550() throws Exception {
		
		prerequisites();
		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		//Optional configy sync
		ph_MorePo.OptionalConfigSync(commonUtility,ph_CalendarPo,bProcessCheckResult);
		//Config sync for the process to come in FSA.
		//toolsPo.configSync(commonsUtility);
		Thread.sleep(GenericLib.iLowSleep);
		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
					
		//Navigation to WO	
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
				sWOName, sFieldServiceName);
		
		//commonUtility.gotToTabHorizontal(ph_WorkOrderPo.getStringParts());

		//Setting up Data for DVR billing type picklist
		Thread.sleep(GenericLib.iLowSleep);
		commonUtility.custScrollToElement(ph_CreateNewPo.getElebillingtype());
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getElebillingtype(),sBillingTypeDVR);
		
		//Validation of picklist DVR billing type cannot be loan
		Assert.assertTrue(ph_WorkOrderPo.getDvrText(sBillingDVR).isDisplayed(), "pickList DVR failed:Billing type cannot be Loan is not displayed");
		ExtentManager.logger.log(Status.PASS,"picklist DVR validation PASS: "+sBillingDVR+" is displayed");

		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getElebillingtype(),SBillingType);
		try {
			Assert.assertFalse(ph_WorkOrderPo.getDvrText(sBillingDVR).isDisplayed(), "pickList DVR failed:Billing type cannot be Loan is not displayed");

		} catch (Exception e) {
			ExtentManager.logger.log(Status.PASS,"picklist DVR validation message is no longer displayed after switching billing type to :"+SBillingType+"");
		}
		
		//Validation of TextArea
		ph_WorkOrderPo.getEleSaveLnk().click();
		Assert.assertTrue(ph_WorkOrderPo.getDvrText("Problem Description cannot be Null..").isDisplayed(), "Required Field Validation failed");
		commonUtility.custScrollToElement(ph_WorkOrderPo.geteleProblemDescriptiontxt());
		ph_WorkOrderPo.geteleProblemDescriptiontxt().sendKeys("Ok");
		ph_WorkOrderPo.getEleSaveLnk().click();

		try {
			Assert.assertFalse(ph_WorkOrderPo.getDvrText("Problem Description cannot be Null..").isDisplayed(), "Required Field Validation failed");

		} catch (Exception e) {
			ExtentManager.logger.log(Status.PASS,"PRoblem Description cannot be null is no longer displayed after entering text");
		}

		
		//Validation of 
		ph_WorkOrderPo.getEleSaveLnk().click();
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(sBooleanDVR),3), "Required Field Validation failed");
		ph_WorkOrderPo.geteleEntitlementPerformed().click();

		
		//Setting up Auto_date1 greater than Auto_date2	
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleAutoDate1_Edit_Input());
		commonUtility.setSpecificDate(ph_WorkOrderPo.getEleAutoDate1_Edit_Input(),"MAY", "3", "2019");
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleAutoDate2_Edit_Input());
	    commonUtility.setSpecificDate(ph_WorkOrderPo.getEleAutoDate2_Edit_Input(),"FEB", "3", "2019");		
		Assert.assertTrue(ph_WorkOrderPo.getDvrText(sDate1DVR).isDisplayed(), "Date1 DVR did not display,Auto_Date1 cant be greater than Auto_date2");
		ExtentManager.logger.log(Status.PASS,"Auto_date1 DVR passed - Auto_Date1 can't be greater than Auto_Date2 displayed");
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleAutoDate2_Edit_Input());
	    commonUtility.setSpecificDate(ph_WorkOrderPo.getEleAutoDate2_Edit_Input(),"JUN", "3", "2019");		

		
	/*	//number DVR no of times assigned
		commonUtility.custScrollToElement(ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("302");
		ph_WorkOrderPo.getElesave().click();
		Thread.sleep(3000);
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(sNoDVR), 3), "Number DVR did not display,Number cannot be 302");
		ExtentManager.logger.log(Status.PASS,"Number 302 displayed DVR Message :"+sNoDVR+"");	
		//Trying with no150 - should throw DVR again.
		ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().clear();
		ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("150");
		ph_WorkOrderPo.getElesave().click();
		Assert.assertTrue(ph_WorkOrderPo.getDvrText(sNoDVR).isDisplayed(), "Number DVR did not display,Number cannot be 302");
		ExtentManager.logger.log(Status.PASS,"Validated if 150 displayed DVR Message :"+sNoDVR+"");	
		ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().clear();
		ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("20");*/

	    //Setting up scheduled DAtetime to today.
		System.out.println("Validation of Datetime starts now");
		commonUtility.custScrollToElement(ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleScheduledDateTimeTxt(),0, "0", "0");
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(sScheduledDateTimeDVR), 3), "DateTime Literal Today DVR did not trigger");
		ExtentManager.logger.log(Status.PASS,"DateTime Literal Today validation passed");	
	//    commonUtility.setSpecificDate(ph_WorkOrderPo.getEleScheduledDateTimeTxt(),"JUN", "3", "2019");	
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleScheduledDateTimeTxt(),3, "0", "0");


	   //Setting up Scheduled Date to today
	   /* workOrderPo.getEleScheduledDateLst().click();
	    commonsUtility.switchContext("Native");
	   commonsUtility.tap(commonsUtility.getEleDonePickerWheelBtn());
	   commonsUtility.switchContext("WebView");   */
		commonUtility.custScrollToElement(ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleScheduledDate());
		commonUtility.setSpecificDate(ph_WorkOrderPo.getEleScheduledDate(), "0", "0", "0"); 
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(sScheduledDateDVR), 3), "DateTime Literal Today DVR did not trigger");
		ExtentManager.logger.log(Status.PASS,"DATE Literal Today validation passed");	
		Thread.sleep(GenericLib.iLowSleep);
		commonUtility.setSpecificDate(ph_WorkOrderPo.getEleScheduledDate(), "JUN", "01", "2019"); 
	//	ph_WorkOrderPo.getElesave().click();
		//Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sAccountDVR).isDisplayed(), "Look up DVR did not display, account cannot be same as Parnter Account ");


		//eleAccountLookUp DVR
/*		Thread.sleep(2000);
		System.out.println("Begining account look upf");
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_CreateNewPo.getEleAccountLookUp(), sAccountName);
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_CreateNewPo.getelePartnerAccountLookUp(), sAccountName);
		ph_WorkOrderPo.getElesave().click();				
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getDvrText(sAccountDVR),3), "Look up DVR did not display, account cannot be same as Parnter Account ");
		ExtentManager.logger.log(Status.PASS,"Lookup DVR passed Account cannot be same as Partner Account is displayed");	
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_CreateNewPo.getEleAccountLookUp(), sAccountName1);*/
		
	/*	//Trying with no150 - sohuld throw DVR again.
		commonUtility.tap(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().clear();
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("150");
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys(Keys.ENTER);
		
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		commonUtility.tap(workOrderPo.getEleIssueFoundTxt());
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sNoDVR).isDisplayed(), "Number DVR did not display,Number cannot be 302");
		ExtentManager.logger.log(Status.PASS,"Number 150 displayed DVR Message ");
				
		try {
			Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sAccountDVR).isDisplayed(), "Look up DVR did not display, account cannot be same as Parnter Account ");
			ExtentManager.logger.log(Status.FAIL,"Account DVR should not be displayed");

		} catch (Exception e) {
			ExtentManager.logger.log(Status.PASS,"Account DVR is no longer displayed");

		}*/
		
		//Confirming IsEntitmenet
		
		//android.widget.Switch
		
		commonUtility.tap(workOrderPo.getEleIsEntitlementPerformedConfirmation(),20,20);
		System.out.println("tapped 20");
		//Thread.sleep(10000);
		commonUtility.tap(workOrderPo.getEleIssueFoundTxt());		
		
		commonUtility.tap(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().clear();
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("20");
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys(Keys.ENTER);
		
		commonUtility.tap(workOrderPo.getEleSaveLnk());
	try {
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Work Order Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
	} catch (AssertionError e) {
		ExtentManager.logger.log(Status.INFO,"Did not get the verbiage work order saved sucessfully will try for work order element");						
	}	
		Thread.sleep(GenericLib.iLowSleep);
		commonUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(GenericLib.iLowSleep);
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		workOrderPo.addParts(commonUtility, workOrderPo,sProductName);
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		commonUtility.tap(workOrderPo.getEleIssueFoundTxt());
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sPartsLineQtyDVR).isDisplayed(), "PARts Line qty cannot be less than 2 and work description cannot be null");
		ExtentManager.logger.log(Status.PASS,"Parts lineqty dvr displayed");
		commonUtility.tap(workOrderPo.getEleIssueFoundTxt());
		commonUtility.tap(workOrderPo.openpartsontap());
		workOrderPo.getEleLineQtyTxtFld().clear();
		workOrderPo.getEleLineQtyTxtFld().sendKeys("3");
		workOrderPo.getEleLinePerUnitTxtFld().sendKeys("100");
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		commonUtility.tap(workOrderPo.getEleIssueFoundTxt());
		commonUtility.tap(workOrderPo.getEleIsEntitlementPerformedConfirmation(),20,20);
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sPartsLinePriceDVR).isDisplayed(), "Line Price Confirmation displayed");
		ExtentManager.logger.log(Status.PASS,"Line Price Confirmation displayed");
		
		commonUtility.tap(workOrderPo.getEleLinePriceLessthanConfirmation(), 20,20);
		System.out.println("tapped 20");
		commonUtility.tap(workOrderPo.getEleIssueFoundTxt());
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		//commonsUtility.waitforElement(workOrderPo.getEleSavedSuccessTxt(), 3);
		//
		try {
			Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), " Work Order Saved successfully is not displayed");
			ExtentManager.logger.log(Status.PASS,"Work Order Saved successfully text is displayed successfully");
		} catch (AssertionError e) {
			ExtentManager.logger.log(Status.INFO,"Did not get the verbiage work order saved sucessfully will try for work order element");						
		}
			
		try {
			Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sPartsLineQtyDVR).isDisplayed(), "PARts Line qty cannot be less than 2 and work description cannot be null");
			ExtentManager.logger.log(Status.FAIL,"Parts DVR messge is still being displayed");

		} catch (Exception e) {
			ExtentManager.logger.log(Status.PASS,"Parts DVR message is not longer displayed after adding more than 2 line qty");
		}

		//Validation of qualifying workorder with Issue found text error.
		//Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), " Work Order Saved successfully is not displayed");
		//ExtentManager.logger.log(Status.PASS,"Work Order Saved successfully text is displayed successfully");

	}
	
	

}
