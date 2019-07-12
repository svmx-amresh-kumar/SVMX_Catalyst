/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Sanity6_SourcetoTarget_Formula_Mapping_SOU extends BaseLib {

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
	String sActivityType = null;
	String sCase = null;
	String sIssueTxt = null;
	String sOrderStatus = null;
	String sBillingType = null;
	String sSqlQuery = null;
	String sCaseID = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	String sSerialNumber = null;
	String sSheetName =null;

	private void preRequiste() throws Exception { 

		restServices.getAccessToken();
		sSerialNumber = commonUtility.generateRandomNumber("SAN6_");
		
		//	sDeviceDate = driver.getDeviceTime().split(" ");

		sJsonData = "{\"Name\": \""+sSerialNumber+"\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectID+"\'";				
		sProductName  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		//sProductName = "";
		
		sJsonData = "{\"Origin\": \"phone\", \"Subject\": \"Sanity6 is validated\", \"Priority\": \"High\", \"Description\": \"Description of Sanity6 \",\"Status\": \"Escalated\"}";
		sObjectApi = "Case?";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+CaseNumber+from+Case+Where+id+=\'"+sObjectID+"\'";				
		sCaseID  =restServices.restGetSoqlValue(sSqlQuery,"CaseNumber"); 
		//sCaseID="00001147";
	/*	commonUtility.executeSahiScript("appium/scenario6_prerequisite.sah", sTestCaseID);
		Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		
	*/	
	}

	@Test(retryAnalyzer=Retry.class)
	public void scenario6Test() throws Exception {
		 sSheetName ="SANITY6";
		sDeviceDate = driver.getDeviceTime().split(" ");
		sTestCaseID = "SANITY6";

		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName , "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName , "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName , "ProcessName");
		sIssueTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName , "IssueText");
		sOrderStatus = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName , "OrderStatus");
		sBillingType = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName , "BillingType");
		preRequiste();
		//sCaseID = "00001161";
		//sProductName="SANITY6";
		
		
		//Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		
		//Config Sync for process
		toolsPo.configSync(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);

		//Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep); 
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sCaseID, sFieldServiceName);
		sAppDate = workOrderPo.getEleScheduledDateTxt().getAttribute("value").split("/");
		System.out.println(Arrays.toString(sAppDate));
		System.out.println(Arrays.toString(sDeviceDate));
		//Assert.assertEquals(sAppDate[1], sDeviceDate[3], "Date is current device date");
		Thread.sleep(CommonUtility.iLowSleep);

		//Set the order status
		commonUtility.setPickerWheelValue(workOrderPo.getEleOrderStatusCaseLst(), sOrderStatus);
		Thread.sleep(CommonUtility.iLowSleep);

		//Set the billing type
		commonUtility.setPickerWheelValue(workOrderPo.getEleBillingTypeCaseLst(), sBillingType);
		Thread.sleep(CommonUtility.iLowSleep);
	
		try {
			System.out.println("Need to be Handled successfully");
			workOrderPo.getElePartsToggleBtn().click();
			commonUtility.tap(workOrderPo.getElePartsToggleBtn());
			
			commonUtility.tap(workOrderPo.getEleRemoveItemLnk());
			commonUtility.tap(workOrderPo.getEleYesBtn());
			
			commonUtility.tap(workOrderPo.getEleOKBtn());
			System.out.println("Handled successfully");
			Thread.sleep(CommonUtility.iMedSleep);
		}catch(Exception e){
			
		}
		
		
		//Add the workorder parts
		workOrderPo.addParts(commonUtility, workOrderPo, sProductName);
		commonUtility.tap(workOrderPo.getElePartsIcn(sProductName));
		Assert.assertTrue(workOrderPo.getEleWODesMappedTxt().isDisplayed(), "Work Description is not mapped");
		ExtentManager.logger.log(Status.PASS,"Work Order Description Mapped is dispalyed successfully");

		//Save the workorder updates and validate
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Failed to save the work orer update");
		ExtentManager.logger.log(Status.PASS,"Work Order Saved successfully");
	
	
	}

}
