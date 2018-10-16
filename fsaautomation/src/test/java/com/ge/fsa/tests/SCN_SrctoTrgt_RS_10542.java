/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_SrctoTrgt_RS_10542 extends BaseLib {
	
	int iWhileCnt = 0;
	String sTestID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sFieldServiceName = null;
	String sProductName1 = null;
	String sProductName2 = null;
	String sActivityType = null;
	String sPrintReportSearch = null;
	String sIssueTxt = null;
	String sBillingType = null;
	
	String sIBName1 = null;
	String sIBName2 = null;
	String sIBRecord = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectAccID = null;
	String sSerialNumber = null;
	String sSqlQuery = null;
	String sAccountName = null;
	//String sTestIBID = null;
	String sObjectProID = null;
	String sProductName = null;

	@BeforeMethod
	public void initializeObject() throws Exception { 

		restServices.getAccessToken();/*
		sSerialNumber = commonsPo.generaterandomnumber("IB_10541_");
		
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"account\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		sAccountName =restServices.restGetSoqlValue(sSqlQuery,"Name"); 


		// Create product
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"product\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectProID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectProID+"\'";				
		sProductName  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic IB1
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\", \"Name\": \""+sSerialNumber+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sSerialNumber+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Country__c\": \"United States\", \"SVMXC__City__c\": \"Liver Pool\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sIBRecord=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBRecord+"\'";				
		sIBName1 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sIBName1);

		//Creation of dynamic IB2
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\", \"Name\": \""+commonsPo.generaterandomnumber("IB_10541_")+"\", \"SVMXC__Serial_Lot_Number__c\": \""+commonsPo.generaterandomnumber("IB_10541_")+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Country__c\": \"Italy\", \"SVMXC__City__c\": \"Vatican\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sIBRecord=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBRecord+"\'";				
		sIBName2 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sIBName2);*/
		
		sIBName1 ="IB_10540_16102018145445";
		sIBName2 = "IB_10540_16102018145450";
				
		
		
		/*
		genericLib.executeSahiScript("appium/SCN_SrctoTrgt_RS_10542_prerequisite.sah", sTestID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + "Sahi verification failure");
		*/
	}

	@Test(enabled = true)
	public void toTest() throws Exception {
		sTestID = "RS_10542";
		
		sExploreSearch = GenericLib.getExcelData(sTestID,sTestID, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestID,sTestID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestID, sTestID,"ProcessName");
		sIssueTxt = GenericLib.getExcelData(sTestID,sTestID, "IssueText");
		sBillingType = GenericLib.getExcelData(sTestID,sTestID, "BillingType");
		
		
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			/*
			toolsPo.configSync(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);*/
			
			//Navigation to SFM
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sIBName1, sFieldServiceName);
			
			//Validation of not qualifying Work Order
			Assert.assertTrue(workOrderPo.getEleThisRecordDoesNotPopup().isDisplayed(), "Error popup is not displayed");
			ExtentManager.logger.log(Status.PASS,"Error popup This is record does not meet is displayed successfully");
			commonsPo.tap(workOrderPo.getEleOKBtn());
			Thread.sleep(GenericLib.iLowSleep);
			
			
			//Navigation to SFM
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sIBName2, sFieldServiceName);
			Thread.sleep(GenericLib.iLowSleep);
			
			commonsPo.pickerWheel(workOrderPo.getEleBillingTypeLst(), sBillingType);
			Thread.sleep(GenericLib.iLowSleep);
			
			
			commonsPo.tap(workOrderPo.getEleClickSave());
			Thread.sleep(GenericLib.iLowSleep);
			
			//Validation of qualifying workorder with Issue found text error.
			Assert.assertTrue(workOrderPo.getEleIssueFoundTxt().isDisplayed(), "Issue found error is not displayed");
			ExtentManager.logger.log(Status.PASS,"Issue found is displayed successfully");
			
			//Validation of qualifying workorder with Issue found text popup.
			commonsPo.tap(workOrderPo.getEleIssueFoundTxt());	
			Assert.assertTrue(workOrderPo.getEleIssuePopupTxt(sIssueTxt).isDisplayed(), "Error popup is not displayed");
			ExtentManager.logger.log(Status.PASS,"Error popup Issue found is displayed successfully");
			
			commonsPo.tap(workOrderPo.getEleIssueFoundTxt());
			Thread.sleep(GenericLib.iMedSleep);
			commonsPo.tap(workOrderPo.getEleCancelLnk());
			commonsPo.tap(workOrderPo.getEleDiscardBtn());
			
			//Navigation to WO
			workOrderPo.selectAction(commonsPo, sFieldServiceName);
			Thread.sleep(GenericLib.iMedSleep);
			
			//Selecting Billing Type to contract to make sure sfm is working fine.
			commonsPo.pickerWheel(workOrderPo.getEleBillingTypeLst(), "Contract");
			commonsPo.tap(workOrderPo.getEleSaveLnk());
			
			//Validation of qualifying workorder with Issue found text error.
			Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Saved successfully is not displayed");
			ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
	}
}
