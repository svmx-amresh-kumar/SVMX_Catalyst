/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.Retry;

public class SCN_RS_10552 extends BaseLib {
	
	int iWhileCnt = 0;
	String sTestCaseID = null;
	String sWorkOrderID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectID = null;
	String sWOObejctApi = null;
	String sWOJsonData = null;
	String sFieldServiceName = null;
	String sWOName = null;
	String sWOSqlQuery = null;
	String sSheetName =null;
	String sTestID = null;
	String[] sDate=null;
	String sCompletedDateTxt=null;
	String sActualDateTxt=null;
	String sAutoDate=null;
	String sPreviousDate=null;
	String sOnsiteDate=null;
	int iDay=0;
	int iMonth=0;
	int iYear=0;
	
	public void preRequiste() throws Exception { 
		sDate=new java.sql.Date(System.currentTimeMillis()).toString().split("-");
		iYear=Integer.parseInt(sDate[0])+1;
		sAutoDate=sDate[1]+"/"+1+"/"+iYear;
		iDay = Integer.parseInt(sDate[2])-1;
		sPreviousDate = Integer.parseInt(sDate[1])+"/"+iDay+"/"+sDate[0];
		sOnsiteDate=Integer.parseInt(sDate[1])+"/"+Integer.parseInt(sDate[2])+"/"+sDate[0];	
		
		if(Integer.parseInt(sDate[2])>28)
		{
			sDate[2]="1";
			iMonth=Integer.parseInt(sDate[1])+1;
			if(iMonth>12) {
				iMonth=01;
			}
			sDate[1]=""+iMonth;
		}
		sCompletedDateTxt=sDate[0]+"-"+sDate[1]+"-"+sDate[2];//+"T09:00:00.000+0000";
		iDay=Integer.parseInt(sDate[2])+2;
		sDate[2]=""+iDay;
		sActualDateTxt=sDate[0]+"-"+sDate[1]+"-"+sDate[2];//+"T09:00:00.000+0000";
		
		System.out.println("Completed *****"+sCompletedDateTxt);
		System.out.println("Actual *****"+sActualDateTxt);
		System.out.println("sAutoDate *****"+sAutoDate);
		System.out.println("sOnsiteDate *****"+sOnsiteDate);
		System.out.println("sPreviousDate *****"+sPreviousDate);
		
		restServices = new RestServices();
		genericLib = new GenericLib();
		restServices.getAccessToken();
		sWOObejctApi="SVMXC__Service_Order__c?";
		
		//Creation of dynamic Work Order2
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__Actual_Initial_Response__c\":\""+sActualDateTxt+"\",\"SVMXC__Completed_Date_Time__c\":\""+sCompletedDateTxt+"\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
		sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		sWOName =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 
		
		genericLib.executeSahiScript("appium/RS_10552_prerequisite.sah", sTestCaseID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
		
	}

	@Test(retryAnalyzer=Retry.class)
	public void SCN_RS_10552() throws Exception {
		sSheetName ="RS_10552";
		sTestCaseID = "RS_10552";
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
			
		preRequiste();
		
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		
		//Config Sync for process
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
			
		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep); 
		sFieldServiceName="RS_10552Process";
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(sAutoDate.contains(workOrderPo.getEleDateTimeLst().get(2).getAttribute("value")),"Next Scheduled Date is not set to 1st day of the created month in next year.");
		ExtentManager.logger.log(Status.PASS,"Next Scheduled Date is set to 1st day of the created month in next year.");
		
		Assert.assertTrue(( workOrderPo.getEleDateTimeLst().get(3).getAttribute("value")).contains(sOnsiteDate) || (workOrderPo.getEleDateTimeLst().get(3).getAttribute("value")).contains(sPreviousDate),"Actual Onsite Response is not set to current date.");
		ExtentManager.logger.log(Status.PASS,"Actual Onsite Response is set to current date.");
		
		Assert.assertTrue(workOrderPo.getEleDateTimeLst().get(4).getAttribute("value").equals("2"),"Difference in days from Actual Initial Response to Completed Date Time, should be updated for Initial To Completion Days field.");
		ExtentManager.logger.log(Status.PASS,"Difference in days from Actual Initial Response to Completed Date Time, should be updated for Initial To Completion Days field.");
		
		Assert.assertTrue(workOrderPo.getEleCustomerDownOffRdBtn().isDisplayed(), " Customer Down is not OFF for contract");
		ExtentManager.logger.log(Status.PASS,"Order status is open and Customer down is OFF");
		
		//Validate for status is open and customer down is unchecked
		workOrderPo.getEleOrderStatusCase2Lst().click();;
		commonsPo.switchContext("Native");
		Assert.assertTrue(commonsPo.getElePickerWheelPopUp().getText().equals("Open"), " Order status is not open.");
		ExtentManager.logger.log(Status.PASS,"Order status is open.");
		commonsPo.getElePickerWheelPopUp().sendKeys("Closed");
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.getEleDonePickerWheelBtn().click();
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Webview");
		
		Assert.assertTrue(workOrderPo.getEleAutoChkBxOnRdBtn().isDisplayed(), "Auto Check Box is not ON for Billing Type Contract.");
		ExtentManager.logger.log(Status.PASS,"Auto Check Box is ON for Billing Type Contract.");
		Thread.sleep(GenericLib.iMedSleep);
		
		//Validate for Billing type is contract and Auto check box is checked
		workOrderPo.getEleWOBillingTypeCaseLst().click();;
		commonsPo.switchContext("Native");
		Assert.assertTrue(commonsPo.getElePickerWheelPopUp().getText().equals("Contract"), " Billing type is not contract.");
		ExtentManager.logger.log(Status.PASS,"Billing type is Contract.");
		commonsPo.getElePickerWheelPopUp().sendKeys("Courtesy");
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.getEleDonePickerWheelBtn().click();
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Webview");
		commonsPo.tap(workOrderPo.getEleQuickSaveIcn());
		Thread.sleep(GenericLib.iMedSleep);
		
		
		workOrderPo.getEleOrderStatusCase2Lst().click();
		commonsPo.switchContext("Native");
		Assert.assertTrue(commonsPo.getElePickerWheelPopUp().getText().equals("Open"), " Order status is not open.");
		ExtentManager.logger.log(Status.PASS,"Order status is still open as customer down is OFF");
		commonsPo.getEleDonePickerWheelBtn().click();
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Webview");
		
		//Validating for check box is OFF when Billing type is Coutesy
		Assert.assertTrue(workOrderPo.getEleAutoChkBxOFFRdBtn().isDisplayed(), "Auto Check Box is not OFF for Billing Type Courtesy.");
		ExtentManager.logger.log(Status.PASS,"Auto Check Box is OFF for Billing Type Courtesy.");
		
		
		
				
	   
	
		
	}

}
