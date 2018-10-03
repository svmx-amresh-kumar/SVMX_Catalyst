/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

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


	private void preRequiste() throws IOException { 

		restServices.getAccessToken();
		sSerialNumber = commonsPo.generaterandomnumber("SAN6_");
		
		//	sDeviceDate = driver.getDeviceTime().split(" ");
		/*sObjectApi = "Product2?";
		sJsonData = "{\"Name\": \""+sTestCaseID+"\", \"IsActive\": \"true\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectID+"\'";				
		sProductName1 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		sProductName1="v1";*/

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


	}

	@Test(enabled = true)
	public void scenario6Test() throws Exception {
		sDeviceDate = driver.getDeviceTime().split(" ");
		sTestCaseID = "SANITY6";

		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sIssueTxt = GenericLib.getExcelData(sTestCaseID, "IssueText");
		sOrderStatus = GenericLib.getExcelData(sTestCaseID, "OrderStatus");
		sBillingType = GenericLib.getExcelData(sTestCaseID, "BillingType");
		preRequiste();
		//sCaseID = "00001161";
		//sProductName="SANITY6";
		
		genericLib.executeSahiScript("appium/scenario6_prerequisite.sah", sTestCaseID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID +  "Sahi verification is successful");
		
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		
		//Config Sync for process
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);

		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep); 
		sFieldServiceName="lscenario6";
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sCaseID, sFieldServiceName);
		sAppDate = workOrderPo.getEleScheduledDateTxt().getAttribute("value").split("/");
		System.out.println(Arrays.toString(sAppDate));
		System.out.println(Arrays.toString(sDeviceDate));
		//Assert.assertEquals(sAppDate[1], sDeviceDate[3], "Date is current device date");
		Thread.sleep(GenericLib.iLowSleep);

		//Set the order status
		commonsPo.pickerWheel(workOrderPo.getEleOrderStatusCaseLst(), sOrderStatus);
		Thread.sleep(GenericLib.iLowSleep);

		//Set the billing type
		commonsPo.pickerWheel(workOrderPo.getEleBillingTypeCaseLst(), sBillingType);
		Thread.sleep(GenericLib.iLowSleep);
	
		try {
			System.out.println("Need to be Handled successfully");
			workOrderPo.getElePartsToggleBtn().click();
			commonsPo.tap(workOrderPo.getElePartsToggleBtn());
			
			commonsPo.tap(workOrderPo.getEleRemoveItemLnk());
			commonsPo.tap(workOrderPo.getEleYesBtn());
			
			commonsPo.tap(workOrderPo.getEleOKBtn());
			System.out.println("Handled successfully");
			Thread.sleep(GenericLib.iMedSleep);
		}catch(Exception e){
			
		}
		
		
		//Add the workorder parts
		workOrderPo.addParts(commonsPo, workOrderPo, sProductName);
		commonsPo.tap(workOrderPo.getElePartsIcn(sProductName));
		Assert.assertTrue(workOrderPo.getEleWODesMappedTxt().isDisplayed(), "Work Description is not mapped");
		ExtentManager.logger.log(Status.PASS,"Work Order Description Mapped is dispalyed successfully");

		//Save the workorder updates and validate
		commonsPo.singleTap(workOrderPo.getEleDoneBtn().getLocation());
		commonsPo.singleTap(workOrderPo.getEleSaveLnk().getLocation());
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Failed to save the work orer update");
		ExtentManager.logger.log(Status.PASS,"Work Order Saved successfully");
	
	
	}

}
