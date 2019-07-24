package com.ge.fsa.tests.phone;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;

import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_Formula_RS_10552 extends BaseLib {

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
	String sSheetName = null;
	String sTestID = null;
//	String sDate = null;
	String sCompletedDateTxt = null;
	String sActualDateTxt = null;
	String sAutoDate = null;
	String sPreviousDate = null;
	String sOnsiteDate = null;
	String sSerialNumber = null;
	String sJsonData = null;
	String sObjectApi = null;
	String sSqlQuery = null;
	int iDay = 0;
	int iMonth = 0;
	int iYear = 0;
	boolean presence = false;
	boolean bProcessCheckResult = false;
	Calendar cal = null;

	public void preRequiste() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");
		Date date = format.parse(ph_ChecklistPO.get_device_date(commonUtility));// E MMM d HH:mm:ss z yyyy
		SimpleDateFormat restServiceFormat = new SimpleDateFormat("yyyy-MM-dd");
		cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		sAutoDate = format.format(cal.getTime());
		cal.setTime(date);
		sCompletedDateTxt = restServiceFormat.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, 2);
		sActualDateTxt = restServiceFormat.format(cal.getTime());
		cal.setTime(date);
		sOnsiteDate = format.format(cal.getTime());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		sPreviousDate = format.format(cal.getTime());
		cal.setTime(date);
		System.out.println("Completed *****" + sCompletedDateTxt);
		System.out.println("Actual *****" + sActualDateTxt);
		System.out.println("sAutoDate *****" + sAutoDate);
		System.out.println("sOnsiteDate *****" + sOnsiteDate);
		System.out.println("sPreviousDate *****" + sPreviousDate);
//		
//		restServices = new RestServices();
//		restServices.getAccessToken();
		sSerialNumber = commonUtility.generateRandomNumber("RS_10552_");
//		
//		//Creation of dynamic Work Order
		sWOObejctApi = "SVMXC__Service_Order__c?";
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__Actual_Initial_Response__c\":\"" + sActualDateTxt + "\",\"SVMXC__Completed_Date_Time__c\":\""
				+ sCompletedDateTxt + "\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID = restServices.restCreate(sWOObejctApi, sWOJsonData);
		sWOSqlQuery = "SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWorkOrderID + "\'";
		sWOName = restServices.restGetSoqlValue(sWOSqlQuery, "Name"); // "WO-00000455";

//		
//		//Creation of product
		sJsonData = "{\"Name\": \"" + sSerialNumber + "\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectID = restServices.restCreate(sObjectApi, sJsonData);
		sSqlQuery = "SELECT+name+from+Product2+Where+id+=\'" + sObjectID + "\'";
		sProductName = restServices.restGetSoqlValue(sSqlQuery, "Name");
		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sFieldServiceName, "/appium/RS_10552_prerequisite.sah", sTestCaseID);

		/*
		 * commonUtility.executeSahiScript("appium/RS_10552_prerequisite.sah",
		 * sTestCaseID);
		 * 
		 * ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID +
		 * "Sahi verification failure");
		 */
	}

	@Test()
	// @Test(retryAnalyzer=Retry.class)
	public void SCN_RS_10552() throws Exception {
		sSheetName = "RS_10552";
		sTestCaseID = "RS_10552";
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ProcessName");
		preRequiste();

		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		// Data Sync for WO's created
		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
		ph_MorePo.syncData(commonUtility);

		// Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		Thread.sleep(CommonUtility.iMedSleep);

		// Validation of Next Scheduled date, Actual Onsite Response, Customer OFF
		// button

		Assert.assertEquals(ph_WorkOrderPo.getAutoDate().getText().trim(),sAutoDate, "Next Scheduled Date is not set to 1st day of the created month in next year.");
		ExtentManager.logger.log(Status.PASS, "Next Scheduled Date is set to 1st day of the created month in next year.");

		Assert.assertEquals(ph_WorkOrderPo.getAutomationNumber().getText().trim(),"2", "Difference in days from Actual Initial Response to Completed Date Time, should be updated for Initial To Completion Days field.");
		ExtentManager.logger.log(Status.PASS, "Difference in days from Actual Initial Response to Completed Date Time, should be updated for Initial To Completion Days field.");
		
		// Validation of Order status and change the status
		Assert.assertTrue(verifyListValue(ph_WorkOrderPo.geteleOrderStatus(), "Open", "Closed"), " Order status is not open.");
		ExtentManager.logger.log(Status.PASS, "Order status is open.");

		// Validation of Autocheck box off for Billing type contract
		Assert.assertTrue(ph_WorkOrderPo.getEleAutoChkBx("OFF").isDisplayed(), "Auto Check Box is not OFF for Billing Type Contract.");
		ExtentManager.logger.log(Status.PASS, "Auto Check Box is OFF for Billing Type Contract.");

		// Validation of Billing type not changed and changing the billing type
		Assert.assertTrue(verifyListValue(ph_WorkOrderPo.getEleBillingTypeField(), "Contract", "Courtesy"), " Billing type is not contract.");
		ExtentManager.logger.log(Status.PASS, "Billing type is Contract.");
		ph_WorkOrderPo.getElesave().click();
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);

		Assert.assertTrue(verifyListValue(ph_WorkOrderPo.geteleOrderStatus(), "Open", "Open"), " Order status is not open.");
		ExtentManager.logger.log(Status.PASS, "Order status is still open as customer down is OFF");

		// Validating for check box is OFF when Billing type is Coutesy
		Assert.assertTrue(ph_WorkOrderPo.getEleAutoChkBx("OFF").isDisplayed(), "Auto Check Box is not OFF for Billing Type Courtesy.");
		ExtentManager.logger.log(Status.PASS, "Auto Check Box is OFF for Billing Type Courtesy.");
		ph_WorkOrderPo.getEleAutoChkBx().click();
		
		// Validating for check box is OFF when Billing type is Coutesy
		Assert.assertTrue(ph_WorkOrderPo.getEleAutoChkBx("OFF").isDisplayed(), "Auto Check Box is not OFF for Billing Type Courtesy.");
		ExtentManager.logger.log(Status.PASS, "Auto Check Box is OFF for Billing Type Courtesy.");

		// Addition of Parts
		ph_WorkOrderPo.addParts(commonUtility, sProductName);
		ph_WorkOrderPo.getElesave().click();
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);

		// Enter line price in parts
		ph_WorkOrderPo.getChildLineAddedItem(sProductName).click();
		ph_WorkOrderPo.getEleUsePriceFromPricebook().click();
		ph_WorkOrderPo.getEleLineQtyField().click();
		ph_WorkOrderPo.getEleLineQtyField().clear();
		ph_WorkOrderPo.getEleLineQtyField().sendKeys("10");
		ph_WorkOrderPo.getEleLinePriceField().click();
		ph_WorkOrderPo.getEleLinePriceField().clear();
		ph_WorkOrderPo.getEleLinePriceField().sendKeys("10.5");

		ph_WorkOrderPo.getEleDiscountPercentage().click();
		ph_WorkOrderPo.getEleDiscountPercentage().sendKeys("3");
		// Set Start time for event
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleStartDateTimeTxtFld(), 0, "0", "0");
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleEndDateTimeTxtFld(), 1, "0", "0");
		ph_WorkOrderPo.getElesave().click();

		// Save the parts details
		ph_WorkOrderPo.getElesave().click();
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);

		ph_WorkOrderPo.getChildLineAddedItem(sProductName).click();

		// Validation of formula after adding parts
		Assert.assertEquals(ph_WorkOrderPo.getEleAutoActivityMonth().getText().trim(),String.valueOf(cal.get(Calendar.MONTH)), "Activity month is displayed ");
		ExtentManager.logger.log(Status.PASS, "Activity month value is displayed as expected.");

		Assert.assertEquals(ph_WorkOrderPo.getEleAutoActivityYear().getText().trim(),String.valueOf(cal.get(Calendar.MONTH)), "Activity year is displayed");
		ExtentManager.logger.log(Status.PASS, "Activity year value is displayed as expected.");

		Assert.assertEquals(ph_WorkOrderPo.getEleAutoDiscountLinePrice().getText().trim(),"10.5", "Auto Discount line price is not displayed.");
		ExtentManager.logger.log(Status.PASS, "Auto Discount Line price is displayed as expected.");

		Assert.assertEquals(ph_WorkOrderPo.getEleAutoCalLinePrice().getText().trim(),"0", "Auto call line price is not displayed.");
		ExtentManager.logger.log(Status.PASS, "Auto Call Line price is displayed as expected.");

		ph_WorkOrderPo.getElesave().click();


		// Validating for change of order status when customer down is on.
		ph_WorkOrderPo.getEleCustomerDown().click();
		Assert.assertTrue(ph_WorkOrderPo.getEleCustomerDown().getText().equals("ON"), " Customer Down is not ON");
		ExtentManager.logger.log(Status.PASS, "Customer down is set to ON");

		// Update the WorkOrder and validate if the Order status is now changed
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_WorkOrderPo.geteleOrderStatus(), "Completed");
		ph_WorkOrderPo.getElesave().click();


		// Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		Assert.assertTrue(ph_WorkOrderPo.getEleThisRecordDoesNotPopup().isDisplayed(), "WorkOrder status is changed after customer down is checked");
		ExtentManager.logger.log(Status.PASS, "Order status is successfully changed once the customer down is checked");

		ph_WorkOrderPo.getEleBackButton().click();

	}

	public boolean verifyListValue(WebElement elePickerLst, String sStatus, String sSetlistTxt) throws InterruptedException {

		presence = elePickerLst.getText().trim().equals(sStatus);
		elePickerLst.click();
		driver.findElement(By.xpath("//*[@*='" + sSetlistTxt + "']")).click();
		return presence;
	}

}
