/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;
import java.io.IOException;
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

public class Scenario6Test extends BaseLib {
//	GenericLib genericLib = null;
//	RestServices restServices = null;
//	LoginHomePO loginHomePo = null;
//	ExploreSearchPO exploreSearchPo = null;
//	WorkOrderPO workOrderPo = null;
//	CommonsPO commonsPo = null;
//	ToolsPO toolsPo = null;
	
	int iWhileCnt = 0;
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectID = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sProductName = null;
	String sFieldServiceName = null;
	String sProductName1 = null;
	String sActivityType = null;
	String sCase = null;
	String sIssueTxt = null;
	String sOrderStatus = null;
	String sBillingType = null;
	String sSqlQuery = null;
	String sCaseID = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
//		genericLib = new GenericLib();
//		restServices = new RestServices();
//		loginHomePo = new LoginHomePO(driver);
//		exploreSearchPo = new ExploreSearchPO(driver);
//		workOrderPo = new WorkOrderPO(driver);	
//		toolsPo = new ToolsPO(driver);
//		commonsPo = new CommonsPO(driver);
//		restServices.getAccessToken();
//		sDeviceDate = driver.getDeviceTime().split(" ");
		
	}

	@Test(enabled = true)
	public void scenario6Test() throws Exception {
		sTestCaseID = "SANITY6";
		sObjectApi = "Product2?";
		sJsonData = "{\"Name\": \""+sTestCaseID+"\", \"IsActive\": \"true\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectID+"\'";				
		sProductName1 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		sProductName1="v1";
		
		sJsonData = "{\"Origin\": \"phone\", \"Subject\": \"Sanity6 is validated\", \"Priority\": \"High\", \"Description\": \"Description of Sanity6 \",\"Status\": \"Escalated\"}";
		sObjectApi = "Case?";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+CaseNumber+from+Case+Where+id+=\'"+sObjectID+"\'";				
		sCaseID  =restServices.restGetSoqlValue(sSqlQuery,"CaseNumber"); 
		//sCaseID="00001001";
		
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sIssueTxt = GenericLib.getExcelData(sTestCaseID, "IssueText");
		sOrderStatus = GenericLib.getExcelData(sTestCaseID, "OrderStatus");
		sBillingType = GenericLib.getExcelData(sTestCaseID, "BillingType");
		
	//	try {
		
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			
			//Data Sync for WO's created
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			//Navigation to SFM
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sCaseID, sFieldServiceName);
			
			sAppDate = workOrderPo.getEleScheduledDateTxt().getAttribute("value").split("/");
			Assert.assertEquals(sAppDate[1], sDeviceDate[3], "Date is current device date");
			Thread.sleep(GenericLib.iLowSleep);
			
			//Set the order status
			commonsPo.pickerWheel(workOrderPo.getEleOrderStatusCaseLst(), sOrderStatus);
			Thread.sleep(GenericLib.iLowSleep);
			
			//Set the billing type
			commonsPo.pickerWheel(workOrderPo.getEleBillingTypeCaseLst(), sBillingType);
			Thread.sleep(GenericLib.iLowSleep);
			
			//Add the workorder parts
			workOrderPo.addProductParts(commonsPo, workOrderPo, sProductName1);
			commonsPo.tap(workOrderPo.getElePartsIcn(sProductName1));
			Assert.assertTrue(workOrderPo.getEleWODesMappedTxt().isDisplayed(), "Work Description is not mapped");
			NXGReports.addStep("Work Order Description Mapped is dispalyed successfully", LogAs.PASSED, null);		
			
			//Save the workorder updates and validate
			commonsPo.singleTap(workOrderPo.getEleDoneBtn().getLocation());
			commonsPo.singleTap(workOrderPo.getEleSaveLnk().getLocation());
			Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Failed to save the work orer update");
			NXGReports.addStep("Work Order Saved successfully", LogAs.PASSED, null);
	
			NXGReports.addStep("Testcase " + sTestCaseID + " PASSED", LogAs.PASSED, null);
//		} catch (Exception e) {
//			NXGReports.addStep("Testcase " + sTestCaseID + " FAILED", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
//			throw e;
//		}

	}
	
	
	
	

}
