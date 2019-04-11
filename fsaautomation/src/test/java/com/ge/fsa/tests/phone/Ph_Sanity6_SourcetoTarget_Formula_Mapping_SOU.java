/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.phone;

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
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_ExploreSearchPO;

public class Ph_Sanity6_SourcetoTarget_Formula_Mapping_SOU extends BaseLib {

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
	boolean bProcessCheckResult = false;

	
	private void preRequiste() throws Exception { 

		restServices.getAccessToken();
		sSerialNumber = commonUtility.generaterandomnumber("SAN6_");
		
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
	/*	genericLib.executeSahiScript("appium/scenario6_prerequisite.sah", sTestCaseID);
		Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID +  "Sahi verification is successful");
	*/	
	}

	//@Test(retryAnalyzer=Retry.class)
	@Test()
	public void scenario6Test() throws Exception {
		 sSheetName ="SANITY6";
		sDeviceDate = driver.getDeviceTime().split(" ");
		sTestCaseID = "SANITY6";

		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName , "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName , "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName , "ProcessName");
		sIssueTxt = GenericLib.getExcelData(sTestCaseID,sSheetName , "IssueText");
		sOrderStatus = GenericLib.getExcelData(sTestCaseID,sSheetName , "OrderStatus");
		sBillingType = GenericLib.getExcelData(sTestCaseID,sSheetName , "BillingType");
		preRequiste();
		//sCaseID = "00001161";
		//sProductName="SANITY6";
		
		
		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		//Config Sync for process
		ph_MorePo.OptionalConfigSync(commonUtility,ph_CalendarPo,bProcessCheckResult);
		Thread.sleep(GenericLib.iMedSleep);

		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep); 
		
		//Navigation to SFM
		
		ph_WorkOrderPo.navigateToWOSFM(ph_ExploreSearchPO, sExploreSearch,sExploreChildSearchTxt,sCaseID,sFieldServiceName,commonUtility);
		//sAppDate = workOrderPo.getEleScheduledDateTxt().getAttribute("value").split("/");
		//System.out.println(Arrays.toString(sAppDate));
		//System.out.println(Arrays.toString(sDeviceDate));
		//Assert.assertEquals(sAppDate[1], sDeviceDate[3], "Date is current device date");
		Thread.sleep(GenericLib.iLowSleep);

		//Set the order status
		
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_WorkOrderPo.geteleOrderStatus(), "Open");
		Thread.sleep(GenericLib.iLowSleep);

		//billing type
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getElebillingtype(), "Loan");
		Thread.sleep(GenericLib.iLowSleep);
	
		try {
			System.out.println("Removing part as default part is displayed which will help us validate mapped child line");
			
		//	commonUtility.s
			commonUtility.swipeLeft(ph_WorkOrderPo.geteleRemoveablePart());
			ph_WorkOrderPo.geteleRemove().click();
			ph_WorkOrderPo.geteleRemovePopUp().click();
			System.out.println("Handled successfully");
			Thread.sleep(GenericLib.iMedSleep);
		}catch(Exception e){
			
		}
		
		
		//Add the workorder parts
		ph_WorkOrderPo.addParts(commonUtility, sProductName);
		
		ph_WorkOrderPo.geteleAddedPart(sProductName).click();
		
/*
		commonUtility.tap(workOrderPo.getElePartsIcn(sProductName));
		Assert.assertTrue(workOrderPo.getEleWODesMappedTxt().isDisplayed(), "Work Description is not mapped");
		ExtentManager.logger.log(Status.PASS,"Work Order Description Mapped is dispalyed successfully");

		//Save the workorder updates and validate
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		commonUtility.tap(workOrderPo.getEleSaveLnk());
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Failed to save the work orer update");
		ExtentManager.logger.log(Status.PASS,"Work Order Saved successfully");
	
	*/
	}

}
