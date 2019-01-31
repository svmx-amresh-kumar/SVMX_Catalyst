/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

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
	int iDay=0;
	int iMonth=0;
	
	public void preRequiste() throws Exception { 
		sDate=new java.sql.Date(System.currentTimeMillis()).toString().split("-");
		driver.getDeviceTime();
		
		System.out.println(driver.getDeviceTime());
		/*
		if(Integer.parseInt(sDate[2])>28)
		{
			sDate[2]="1";
			iMonth=Integer.parseInt(sDate[1])+1;
			if(iMonth>12) {
				iMonth=01;
			}
			sDate[1]=""+iMonth;
		}*/
		sCompletedDateTxt=sDate[0]+"-"+sDate[1]+"-"+sDate[2]+"T09:00:00.000+0000";
		iDay=Integer.parseInt(sDate[2])+2;
		sDate[2]=""+iDay;
		sActualDateTxt=sDate[0]+"-"+sDate[1]+"-"+sDate[2]+"T09:00:00.000+0000";
		
		System.out.println("*****"+sCompletedDateTxt);
		System.out.println("*****"+sActualDateTxt);
		
		restServices = new RestServices();
		genericLib = new GenericLib();
		restServices.getAccessToken();
		sWOObejctApi="SVMXC__Service_Order__c?";
		
		//Creation of dynamic Work Order2
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__Actual_Initial_Response__c\":\""+sActualDateTxt+"\",\"SVMXC__Completed_Date_Time__c\":\""+sCompletedDateTxt+"\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
		sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		sWOName =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 
		
		//Creation of dynamic Work Order2
				sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__Actual_Initial_Response__c\":\""+sActualDateTxt+"\",\"SVMXC__Completed_Date_Time__c\":\""+sCompletedDateTxt+"\",\"SVMXC__State__c\":\"Haryana\"}";
				sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
				sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		String		sWOName1 =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 
				
				//Creation of dynamic Work Order2
				sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__Actual_Initial_Response__c\":\""+sActualDateTxt+"\",\"SVMXC__Completed_Date_Time__c\":\""+sCompletedDateTxt+"\",\"SVMXC__State__c\":\"Haryana\"}";
				sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
				sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		String		sWOName2 =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 
				
		/*
		genericLib.executeSahiScript("appium/RS_10552_prerequisite.sah", sTestCaseID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
		*/
	}

	@Test(retryAnalyzer=Retry.class)
	public void SCN_RS_10552() throws Exception {
		sSheetName ="RS_10552";
		sTestCaseID = "RS_10552";
		
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
			
		System.out.println("0000000    "+new java.sql.Date(System.currentTimeMillis()));
		preRequiste();
		sWOName="WO-00007127";
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		/*
		//Config Sync for process
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
			
		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);*/
		Thread.sleep(GenericLib.iMedSleep); 
		sFieldServiceName="RS_10552Process";
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		
		Assert.assertTrue(workOrderPo.getEleCustomerDownOffRdBtn().isDisplayed(), " Customer Down is not OFF for contract");
		ExtentManager.logger.log(Status.PASS,"Order status is open and Customer down is OFF");
		
		Assert.assertTrue(workOrderPo.getEleAutoChkBxOnRdBtn().isDisplayed(), "Auto Check Box is not ON for Billing Type Contract.");
		ExtentManager.logger.log(Status.PASS,"Auto Check Box is ON for Billing Type Contract.");
		
		//Validate for status is open and customer down is unchecked
		workOrderPo.getEleOrderStatusCase2Lst().click();;
		//commonsPo.tap(workOrderPo.getEleOrderStatusCaseLst());
		commonsPo.switchContext("Native");
		Assert.assertTrue(commonsPo.getElePickerWheelPopUp().getText().equals("Open"), " Order status is not open.");
		ExtentManager.logger.log(Status.PASS,"Order status is open.");
		commonsPo.getElePickerWheelPopUp().sendKeys("Closed");
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.getEleDonePickerWheelBtn().click();
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Webview");
		System.out.println("-------      VVVVVVVV    --------");
		/*
		try {workOrderPo.getEleAutoOnSwitchBtn().click();}
		catch(Exception e) {
			commonsPo.tap(workOrderPo.getEleAutoOnSwitchBtn());
		}
		
		System.out.println("-------      VVVVVVVV    --------");
		
		Thread.sleep(GenericLib.iMedSleep);
		
		
		Assert.assertTrue(workOrderPo.getEleAutoChkBxOFFRdBtn().isDisplayed(), "Auto Check Box is not OFF for Billing Type Contract.");
		ExtentManager.logger.log(Status.PASS,"Auto Check Box is OFFfor Billing Type Contract.");
		
		//Validate for Billing type is contract and Auto check box is unchecked
		workOrderPo.getEleBillingTypeCaseLst().click();;
		commonsPo.switchContext("Native");
		Assert.assertTrue(commonsPo.getElePickerWheelPopUp().getText().equals("Contract"), " Billing type is not contract.");
		ExtentManager.logger.log(Status.PASS,"Billing type is Contract.");
		commonsPo.getElePickerWheelPopUp().sendKeys("Courtesy");
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.getEleDonePickerWheelBtn().click();
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Webview");*/
		commonsPo.tap(workOrderPo.getEleQuickSaveIcn());
		Thread.sleep(GenericLib.iMedSleep);
		
		workOrderPo.getEleOrderStatusCase2Lst().click();
		commonsPo.switchContext("Native");
		Assert.assertTrue(commonsPo.getElePickerWheelPopUp().getText().equals("Open"), " Order status is not open.");
		ExtentManager.logger.log(Status.PASS,"Order status is open.");
		commonsPo.getEleDonePickerWheelBtn().click();
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Webview");
		
		Assert.assertTrue(workOrderPo.getEleAutoChkBxOnRdBtn().isDisplayed(), "Auto Check Box is not ON for Courtesy.");
		ExtentManager.logger.log(Status.PASS,"Auto Check Box is ON for Courtesy.");
		System.out.println("***************      ---------     ****************");
		System.out.println(workOrderPo.getEleAutoDate1Txt().getAttribute("value"));
		
		
//		ExtentManager.logger.log(Status.PASS,"Auto Check Box is ON for Courtesy.");
		
		//Validation of event on the calender
				
	    if(workOrderPo.getEleDateTimeLst().get(1).getText().equals("1/1/2020"))
	    {
	    	Assert.assertTrue(true,"Work Order event is not displayed on calender");
	    	ExtentManager.logger.log(Status.PASS,"WorkOrder event is displayed successfully on calender.");
		}
	    
	    if(workOrderPo.getEleDateTimeLst().get(2).getText().equals("1/1/2020"))
	    {
	    	Assert.assertTrue(true,"Work Order event is not displayed on calender");
	    	ExtentManager.logger.log(Status.PASS,"WorkOrder event is displayed successfully on calender.");
		}
	
		
	}

}
