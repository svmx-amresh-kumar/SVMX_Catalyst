/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.ExploreSearchPO;

import com.ge.fsa.pageobjects.LoginHomePO;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.ToolsPO;
import com.ge.fsa.pageobjects.WorkOrderPO;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;

public class Scenario6 extends BaseLib {
	GenericLib genericLib = null;
	RestServices restServices = null;
	LoginHomePO loginHomePo = null;
	ExploreSearchPO exploreSearchPo = null;
	WorkOrderPO workOrderPo = null;
	CommonsPO commonsPo = null;
	ToolsPO toolsPo = null;
	
	int iWhileCnt = 0;
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sWorkOrderID = null;
	String sWOObejctApi = null;
	String sWOJsonData = null;
	String sWOName1 = null;
	String sWOName2 = null;
	String sFieldServiceName = null;
	String sProductName1 = null;
	String sProductName2 = null;
	String sActivityType = null;
	String sPrintReportSearch = null;
	String sIssueTxt = null;
	String sOrderStatus = null;
	String sBillingType = null;
	String sWOSqlQuery = null;
	String sCaseID = null;
	String[] sDate = null;
	@BeforeMethod
	public void initializeObject() throws IOException { 
		genericLib = new GenericLib();
		restServices = new RestServices();
		loginHomePo = new LoginHomePO(driver);
		exploreSearchPo = new ExploreSearchPO(driver);
		workOrderPo = new WorkOrderPO(driver);	
		toolsPo = new ToolsPO(driver);
		commonsPo = new CommonsPO(driver);
		restServices.getAccessToken();
		sWOObejctApi="SVMXC__Service_Order__c?";
			
		sCaseID = "00001000";
		sDate = driver.getDeviceTime().split(" ");
		/*
		DateTimeFormatter f = new DateTimeFormatterBuilder().appendPattern("yyyy MMMM dd").toFormatter();
		LocalDate parsedDate = LocalDate.parse(driver.getDeviceTime(), f);
		DateTimeFormatter f2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		System.out.println("****"+parsedDate.format(f2));
			*/	
	}

	@Test(enabled = true)
	public void toTest() throws Exception {
		sTestCaseID = "SANITY6";
		
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sIssueTxt = GenericLib.getExcelData(sTestCaseID, "IssueText");
		sOrderStatus = GenericLib.getExcelData(sTestCaseID, "OrderStatus");
		sBillingType = GenericLib.getExcelData(sTestCaseID, "BillingType");
		sProductName1= GenericLib.getExcelData(sTestCaseID, "ProductName1");
		try {
		
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			
			//Navigation to SFM
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sCaseID, sFieldServiceName);
			
			//workOrderPo.getEleScheduledDateTxt().click();
			Thread.sleep(GenericLib.iLowSleep);
			
			//Set the orer status
			commonsPo.pickerWheel(workOrderPo.getEleOrderStatusCaseLst(), sOrderStatus);
			Thread.sleep(GenericLib.iLowSleep);
			
			//Set the billing type
			commonsPo.pickerWheel(workOrderPo.getEleBillingTypeCaseLst(), sBillingType);
			Thread.sleep(GenericLib.iLowSleep);
			
			//Add the workorder parts
			workOrderPo.addParts(commonsPo, workOrderPo, sProductName1);
			commonsPo.singleTap(workOrderPo.getElePartsIcn(sProductName1).getLocation());
			Assert.assertTrue(workOrderPo.getEleWODesMappedTxt().isDisplayed(), "Work Description is not mapped");
			NXGReports.addStep("Work Order Description Mapped is dispalyed successfully", LogAs.PASSED, null);		
			
			//Save the workorder updates and validate
			commonsPo.singleTap(workOrderPo.getEleDoneBtn().getLocation());
			commonsPo.singleTap(workOrderPo.getEleSaveLnk().getLocation());
			Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Failed to save the work orer update");
			NXGReports.addStep("Work Order Saved successfully", LogAs.PASSED, null);
	
			NXGReports.addStep("Testcase " + sTestCaseID + " PASSED", LogAs.PASSED, null);
		} catch (Exception e) {
		//	NXGReports.addStep("Testcase " + sTestCaseID + " FAILED", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			throw e;
		}

	}
	
	
	
	

}
