/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.Retry;
import com.sun.glass.events.KeyEvent;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidKeyCode;

public class SCN_Formula_RS_10552 extends BaseLib {
	
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
	String sProductName = null;
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
	String sSerialNumber=null;
	String sJsonData=null;
	String sObjectApi=null;
	String sSqlQuery=null;
	int iDay=0;
	int iMonth=0;
	int iYear=0;
	boolean presence=false;
	
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
		sSerialNumber = commonsPo.generaterandomnumber("RS_10552_");
		
		//Creation of dynamic Work Order
		sWOObejctApi="SVMXC__Service_Order__c?";
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__Actual_Initial_Response__c\":\""+sActualDateTxt+"\",\"SVMXC__Completed_Date_Time__c\":\""+sCompletedDateTxt+"\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
		sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		sWOName =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 
		
		//Creation of product
		sJsonData = "{\"Name\": \""+sSerialNumber+"\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectID+"\'";				
		sProductName  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
	
		genericLib.executeSahiScript("appium/RS_10552_prerequisite.sah", sTestCaseID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification failure");
		
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
		
		commonsPo.longPress(workOrderPo.getEleAutoChkBxRdBtn());
		//Validation of Next Scheduled date, Actual Onsite Response, Customer OFF button
		Assert.assertTrue(sAutoDate.contains(workOrderPo.getEleDateTimeLst().get(2).getAttribute("value")),"Next Scheduled Date is not set to 1st day of the created month in next year.");
		ExtentManager.logger.log(Status.PASS,"Next Scheduled Date is set to 1st day of the created month in next year.");
		System.out.println(workOrderPo.getEleDateTimeLst().get(3).getAttribute("value"));
		Assert.assertTrue(( workOrderPo.getEleDateTimeLst().get(3).getAttribute("value")).contains(sOnsiteDate) || (workOrderPo.getEleDateTimeLst().get(3).getAttribute("value")).contains(sPreviousDate),"Actual Onsite Response is not set to current date.");
		ExtentManager.logger.log(Status.PASS,"Actual Onsite Response is set to current date.");
		Assert.assertTrue(workOrderPo.getEleDateTimeLst().get(4).getAttribute("value").equals("2"),"Difference in days from Actual Initial Response to Completed Date Time, should be updated for Initial To Completion Days field.");
		ExtentManager.logger.log(Status.PASS,"Difference in days from Actual Initial Response to Completed Date Time, should be updated for Initial To Completion Days field.");
		Assert.assertTrue(workOrderPo.getEleCustomerDownOffRdBtn().isDisplayed(), " Customer Down is not OFF for contract");
		ExtentManager.logger.log(Status.PASS,"Order status is open and Customer down is OFF");
		
		//Validation of Order status and change the status
		Assert.assertTrue(verifyListValue(workOrderPo.getEleOrderStatusCase2Lst(),"Open","Closed"), " Order status is not open.");
		ExtentManager.logger.log(Status.PASS,"Order status is open.");
		commonsPo.setPickerWheelValue(workOrderPo.getEleOrderStatusCase2Lst(), "Closed");
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Webview");
		
		//Validation of Autocheck box off for Billing type contract
		Assert.assertTrue(workOrderPo.getEleAutoChkBxOFFRdBtn().isDisplayed(), "Auto Check Box is not OFF for Billing Type Contract.");
		ExtentManager.logger.log(Status.PASS,"Auto Check Box is OFF for Billing Type Contract.");
		Thread.sleep(GenericLib.iMedSleep);
		
		//Validation of Billing type not changed and changing the billing type
		Assert.assertTrue(verifyListValue(workOrderPo.getEleWOBillingTypeCaseLst(),"Contract","Courtesy"), " Billing type is not contract.");
		ExtentManager.logger.log(Status.PASS,"Billing type is Contract.");
		commonsPo.setPickerWheelValue(workOrderPo.getEleWOBillingTypeCaseLst(), "Courtesy");
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Webview");
		commonsPo.tap(workOrderPo.getEleQuickSaveIcn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(verifyListValue(workOrderPo.getEleOrderStatusCase2Lst(),"Open","Open"), " Order status is not open.");
		ExtentManager.logger.log(Status.PASS,"Order status is still open as customer down is OFF");
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Webview");
		
		//Validating for check box is OFF when Billing type is Coutesy
		Assert.assertTrue(workOrderPo.getEleAutoChkBxOFFRdBtn().isDisplayed(), "Auto Check Box is not OFF for Billing Type Courtesy.");
		ExtentManager.logger.log(Status.PASS,"Auto Check Box is OFF for Billing Type Courtesy.");
		commonsPo.longPress(workOrderPo.getEleAutoChkBxRdBtn());
		Assert.assertTrue(workOrderPo.getEleAutoChkBxOnRdBtn().isDisplayed(), "Auto Check Box is not ON for Billing Type Contract.");
		ExtentManager.logger.log(Status.PASS,"Auto Check Box is ON for Billing Type Contract.");
		commonsPo.tap(workOrderPo.getEleQuickSaveIcn());
		Thread.sleep(GenericLib.iMedSleep);
		//Validating for check box is OFF when Billing type is Coutesy
		Assert.assertTrue(workOrderPo.getEleAutoChkBxOFFRdBtn().isDisplayed(), "Auto Check Box is not OFF for Billing Type Courtesy.");
		ExtentManager.logger.log(Status.PASS,"Auto Check Box is OFF for Billing Type Courtesy.");
		
		//Addition of Parts
		workOrderPo.addParts(commonsPo, workOrderPo, sProductName);
		commonsPo.tap(workOrderPo.getEleQuickSaveIcn());
		Thread.sleep(GenericLib.iMedSleep);
		
		//Enter line price in parts
		commonsPo.tap(workOrderPo.getElePartsIcn(sProductName));
		commonsPo.tap(workOrderPo.getEleUsePriceToggleBtn());
		commonsPo.tap(workOrderPo.getEleLineQtyTxtFld());
		workOrderPo.getEleLineQtyTxtFld().clear();
		workOrderPo.getEleLineQtyTxtFld().sendKeys("10");
		commonsPo.tap(workOrderPo.getEleLinePerUnitTxtFld());
		workOrderPo.getEleLinePerUnitTxtFld().clear();
		workOrderPo.getEleLinePerUnitTxtFld().sendKeys("10.5");
		Thread.sleep(1000);
		
		commonsPo.tap(workOrderPo.getEleDiscountTxtFld());
		workOrderPo.getEleDiscountTxtFld().sendKeys("3");
		//Set Start time for event
		commonsPo.setDateTime24hrs(workOrderPo.getEleStartDateTxtFld(), 0, "0", "0");
		commonsPo.setDateTime24hrs(workOrderPo.getEleEndDateTxtFld(), 1, "0", "0");
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		
		//Save the parts details
		commonsPo.tap(workOrderPo.getEleQuickSaveIcn());
		Thread.sleep(GenericLib.iMedSleep);
		
		commonsPo.tap(workOrderPo.getElePartsIcn(sProductName));
		Thread.sleep(GenericLib.iMedSleep);
		
		//Validation of formula after adding parts
		workOrderPo.getEleAutoActivityMonthTxtFld().click();
		Assert.assertTrue(workOrderPo.getEleAutoActivityMonthTxtFld().getAttribute("value").equals("2"), "Activity month is displayed ");
		ExtentManager.logger.log(Status.PASS,"Activity month value is displayed as expected.");
		
		workOrderPo.getEleAutoActivityYearTxtFld().click();
		Assert.assertTrue(workOrderPo.getEleAutoActivityYearTxtFld().getAttribute("value").equals(sDate[0]), "Activity year is displayed");
		ExtentManager.logger.log(Status.PASS,"Activity year value is displayed as expected.");
		
		workOrderPo.getEleAutoDiscountLinePriceTxtFld().click();
		Assert.assertTrue(workOrderPo.getEleAutoDiscountLinePriceTxtFld().getAttribute("value").equals("10.5"), "Auto Discount line price is not displayed.");
		ExtentManager.logger.log(Status.PASS,"Auto Discount Line price is displayed as expected.");
		
		System.out.println(workOrderPo.getEleAutoCalLinePriceTxtFld().getAttribute("value"));
		workOrderPo.getEleAutoCalLinePriceTxtFld().click();
		Assert.assertTrue(workOrderPo.getEleAutoCalLinePriceTxtFld().getAttribute("value").equals("0"), "Auto call line price is not displayed.");
		ExtentManager.logger.log(Status.PASS,"Auto Call Line price is displayed as expected.");
		
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		Thread.sleep(GenericLib.iMedSleep);
			
		//Validating for change of order status when customer down is on.
		commonsPo.longPress(workOrderPo.getEleCustomerDownRdBtn());
		Assert.assertTrue(workOrderPo.getEleCustomerDownOnRdBtn().isDisplayed(), " Customer Down is not ON");
		ExtentManager.logger.log(Status.PASS,"Customer down is set to ON");
		Thread.sleep(GenericLib.iMedSleep);
		
		//Update the WorkOrder and validate if the Order status is now changed
		commonsPo.setPickerWheelValue(workOrderPo.getEleOrderStatusCase2Lst(), "Completed");
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.switchContext("Webview");
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		Thread.sleep(GenericLib.iLowSleep);
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		Thread.sleep(GenericLib.iMedSleep);
		Assert.assertTrue(workOrderPo.getEleThisRecorddoesnotMeetTxt().isDisplayed(), "WorkOrder status is changed after customer down is checked");
		ExtentManager.logger.log(Status.PASS,"Order status is successfully changed once the customer down is checked");
		
		
		try {workOrderPo.getEleOKBtn().click();}catch(Exception e) {commonsPo.tap(workOrderPo.getEleOKBtn());}
		
	}
	
	public boolean verifyListValue(WebElement elePickerLst,String sStatus, String sSetlistTxt) throws InterruptedException {
		
		if(BaseLib.sOSName.equals("android")){
			commonsPo.tap(elePickerLst);
			commonsPo.switchContext("Native");
			presence=commonsPo.getEleAndroidWheelPopUp().getText().equals(sStatus);
			commonsPo.getElePicklistValue(sSetlistTxt).click();
		}
		else {
			elePickerLst.click();
			commonsPo.switchContext("Native");
			presence=commonsPo.getElePickerWheelPopUp().getText().equals(sStatus);
			commonsPo.getElePickerWheelPopUp().sendKeys(sSetlistTxt);
			commonsPo.getEleDonePickerWheelBtn().click();
		}
		commonsPo.switchContext("webview");
		return presence;
	}

}
