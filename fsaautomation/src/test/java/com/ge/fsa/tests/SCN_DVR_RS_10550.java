/*
 *  @author Vinod Tharavath
 *  SCN_DVR_RS_10550
 *  
 *  
 *  PENDING TASKS ---
 *  ====Sahi Script for Process DVR process.
 *  === MAKE SURE custom fields Auto_Date1 and 2 are created in workOrder the ORG before running.
 *  === 
 * 
 */

package com.ge.fsa.tests;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_DVR_RS_10550 extends BaseLib{	
	
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
	String sAccountName="SANITY10";
	
	
	//DVR STRINGS
	String sProbDescRequired = "The field Problem Description is required";
	String sAccountDVR = "Account cannot be same as Partner Account";
	String sBooleanDVR= "Is Entitlement Performed has to be true.";
	String sNoDVR = "Number1 cannot be anything between 100 and 200. Also number 302 is not allowed";
	String sDate1DVR = "Auto_Date1 cannot be greater than Auto_Date2";
	String sScheduledDateTimeDVR="Scheduled_DateTime cannot be Today";
	String sScheduledDateDVR = "Scheduled Date cannot be Today or Yesterday or Tommorow";

	String sPartsLineQtyDVR = "Parts: Line Qty has to be more than 2 and WD cannot be Null";
	String sPartsLinePriceDVR="Parts: Line Price is Less than 2000";
	String sBillingDVR="Billing Type cannot be Loan";
	String sBillingTypeDVR = "Loan";
	String SBillingType = "Warranty";
	
	@Test(enabled = true)
	public void RS_10550() throws Exception {
		
		sTestCaseID = "SCN_DVR_RS_10550";
		sCaseWOID = "Data_SCN_DVR_RS_10550";
		String sAccountNameToCreate = "Auto_10550_Account";
		

		//Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID, "EditProcessName");
			
		// Creation of dynamic Work Order
		restServices.getAccessToken();
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
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
				
		
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);		

		//Config sync for the process to come in FSA.
		//toolsPo.configSync(commonsPo);
		Thread.sleep(genericLib.iLowSleep);
		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
					

		//Navigation to WO	
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);				
		
		//number no of times assigned
		commonsPo.tap(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("302");
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys(Keys.ENTER);
		workOrderPo.geteleWorkOrderLeftPane_View();
		
		//Setting up Data for DVR billing type picklist
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.pickerWheel(workOrderPo.geteleBillingType_Edit_Lst(), sBillingTypeDVR);
		
		//Setting up Auto_date1 greater than Auto_date2		
	    commonsPo.setDateYear(workOrderPo.getEleAutoDate1_Edit_Input(),"February", "3", "2019");
	    commonsPo.setDateYear(workOrderPo.getEleAutoDate2_Edit_Input(),"February", "3", "2018");		
	    
	    //Setting up scheduled DAtetime to today.
	   workOrderPo.getEleScheduledDateTimeTxt().click();	   
	   commonsPo.switchContext("Native");
	   commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
	   commonsPo.switchContext("WebView");	   
	   
	   //Setting up Scheduled Date to today
	   workOrderPo.getEleScheduledDateLst().click();
	   commonsPo.switchContext("Native");
	   commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
	   commonsPo.switchContext("WebView");   
	    	    		
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		
		Thread.sleep(GenericLib.iLowSleep);
		
		
		//Required Field Validation
		Assert.assertTrue(workOrderPo.getEleIssueFoundTxt().isDisplayed(), "Issue found error is not displayed");
		ExtentManager.logger.log(Status.PASS,"Issue found is displayed successfully");
		commonsPo.tap(workOrderPo.getEleIssueFoundTxt());	
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sProbDescRequired).isDisplayed(), "Required field message did not show up. Problem Description is empty");
		ExtentManager.logger.log(Status.PASS,"Required Field Problem Description message displayed- the field problem description is required");	
		commonsPo.tap(workOrderPo.getEleIssueFoundTxt());
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.longPress(workOrderPo.getProblemDescription());
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.tap(workOrderPo.geteleProblemDesc_Edit_WorkOrder());
		workOrderPo.geteleProblemDesc_Edit_WorkOrderPopup().sendKeys("Hello Work");
		workOrderPo.geteleProblemDesc_Edit_WorkOrderPopup().sendKeys(Keys.ENTER);
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.tap(workOrderPo.getEleUpdateLnk());
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(workOrderPo.getEleSaveLnk());
				
		//Validation of the DVR's
		Assert.assertTrue(workOrderPo.getEleIssueFoundTxt().isDisplayed(), "Issue found error is not displayed");
		ExtentManager.logger.log(Status.PASS,"Issue found is displayed successfully");
		commonsPo.tap(workOrderPo.getEleIssueFoundTxt());	
		
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sBillingDVR).isDisplayed(), "picklist billing type did not display DVR");
		ExtentManager.logger.log(Status.PASS,"Picklist DVR passed Billing Type cannot be loan");	
	
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sAccountDVR).isDisplayed(), "Look up DVR did not display, account cannot be same as Parnter Account ");
		ExtentManager.logger.log(Status.PASS,"Lookup DVR passed Account cannot be same as Partner Account is displayed");	
		
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sBooleanDVR).isDisplayed(), "Boolean DVR did not display,is entitlement performed has to be true");
		ExtentManager.logger.log(Status.PASS,"Boolean DVR passed ");
		
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sScheduledDateTimeDVR).isDisplayed(), "DateTime Literal Today DVR did not trigger");
		ExtentManager.logger.log(Status.PASS,"DateTime Literal Today validation passed");	
		
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sScheduledDateDVR).isDisplayed(), "Date Literal Today DVR did not trigger");
		ExtentManager.logger.log(Status.PASS,"Date Literal Today validation passed");
				
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sDate1DVR).isDisplayed(), "Date1 DVR did not display,Auto_Date1 cant be greater than Auto_date2");
		ExtentManager.logger.log(Status.PASS,"Auto_date1 DVR passed - Auto_Date1 can't be greater than Auto_Date2 displayed");
								
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sNoDVR).isDisplayed(), "Number DVR did not display,Number cannot be 302");
		ExtentManager.logger.log(Status.PASS,"Number 302 displayed DVR Message ");	
	
		//Removing off the DVRS
		
		//Account Lookup
		commonsPo.tap(workOrderPo.getEleAccount_Edit_Input());
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(workOrderPo.getEleAccount_Edit_Input());
		commonsPo.lookupSearch(sAccountName);
		

		//Setting boolean
		
		commonsPo.tap(workOrderPo.geteleIsEntitlementPerformed_Edit_Switch());
		workOrderPo.getEleIsEntitlementPerformed().click();
		System.out.println("tried click");
		
		commonsPo.tap(workOrderPo.geteleIsEntitlementPerformed_Edit_Switch());
		System.out.println("tried commons.tap");
		
		commonsPo.longPress(workOrderPo.geteleIsEntitlementPerformed_Edit_Switch());
		System.out.println("tried long press");
		

		//setting ScehduledDatetime to other than today		
		commonsPo.setDateTime24hrs(workOrderPo.getEleScheduledDateTimeTxt(), 2, "5", "5");
		//commonsPo.setDateYear(workOrderPo.getEleScheduledDateTimeTxt(),"February", "3", "2019");
	
		//setting scheduled date other than today
		commonsPo.setDateYear(workOrderPo.getEleScheduledDateLst(),"February", "3", "2019");
	
		//Setting up Auto_date2 greater than Auto_date1		
	    commonsPo.setDateYear(workOrderPo.getEleAutoDate1_Edit_Input(),"February", "3", "2017");
	    commonsPo.setDateYear(workOrderPo.getEleAutoDate2_Edit_Input(),"February", "3", "2018");
		
		//Setting up billing Type
		commonsPo.pickerWheel(workOrderPo.geteleBillingType_Edit_Lst(), "Contract");		
		
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		commonsPo.tap(workOrderPo.getEleIssueFoundTxt());
		
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sNoDVR).isDisplayed(), "Number DVR did not display,Number cannot be 302");
		ExtentManager.logger.log(Status.PASS,"Number 302 displayed DVR Message ");
		
		//Trying with no150 - sohuld throw DVR again.
		commonsPo.tap(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().clear();
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("150");
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys(Keys.ENTER);
		
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		commonsPo.tap(workOrderPo.getEleIssueFoundTxt());
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sNoDVR).isDisplayed(), "Number DVR did not display,Number cannot be 302");
		ExtentManager.logger.log(Status.PASS,"Number 150 displayed DVR Message ");
				
		try {
			Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sAccountDVR).isDisplayed(), "Look up DVR did not display, account cannot be same as Parnter Account ");
			ExtentManager.logger.log(Status.FAIL,"Lookup DVR should not be displayed");

		} catch (Exception e) {
			ExtentManager.logger.log(Status.PASS,"Account DVR is not longer displayed");

		}
		
		//Confirming IsEntitmenet
		commonsPo.tap(workOrderPo.getEleIsEntitlementPerformedConfirmation(),20,20);
		System.out.println("tapped 20");
		//Thread.sleep(10000);
		commonsPo.tap(workOrderPo.getEleIssueFoundTxt());		
		
		commonsPo.tap(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input());
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().clear();
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys("20");
		workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().sendKeys(Keys.ENTER);
		
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		
		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Work Order Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
		
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		workOrderPo.addParts(commonsPo, workOrderPo,sProductName);
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		commonsPo.tap(workOrderPo.getEleIssueFoundTxt());
		Thread.sleep(genericLib.iLowSleep);
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sPartsLineQtyDVR).isDisplayed(), "PARts Line qty cannot be less than 2 and work description cannot be null");
		ExtentManager.logger.log(Status.PASS,"Parts lineqty dvr displayed");
		commonsPo.tap(workOrderPo.getEleIssueFoundTxt());
		commonsPo.tap(workOrderPo.openpartsontap());
		workOrderPo.getEleLineQtyTxtFld().clear();
		workOrderPo.getEleLineQtyTxtFld().sendKeys("3");
		workOrderPo.getEleLinePerUnitTxtFld().sendKeys("100");
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		commonsPo.tap(workOrderPo.getEleIssueFoundTxt());

		commonsPo.tap(workOrderPo.getEleIsEntitlementPerformedConfirmation(),20,20);
		
		Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sPartsLinePriceDVR).isDisplayed(), "Line Price Confirmation displayed");
		ExtentManager.logger.log(Status.PASS,"Line Price Confirmation displayed");
		
		commonsPo.tap(workOrderPo.getEleLinePriceLessthanConfirmation(), 20,20);
		System.out.println("tapped 20");
		commonsPo.tap(workOrderPo.getEleIssueFoundTxt());
		commonsPo.tap(workOrderPo.getEleSaveLnk());

		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), " Work Order Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order Saved successfully text is displayed successfully");
		try {
			Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sPartsLineQtyDVR).isDisplayed(), "PARts Line qty cannot be less than 2 and work description cannot be null");
			ExtentManager.logger.log(Status.FAIL,"Parts DVR messge is still being displayed");


		} catch (Exception e) {
			ExtentManager.logger.log(Status.PASS,"Parts DVR message is not longer displayed after adding more than 2 line qty");
		}

		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), " Work Order Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order Saved successfully text is displayed successfully");

	}
	
	

}
