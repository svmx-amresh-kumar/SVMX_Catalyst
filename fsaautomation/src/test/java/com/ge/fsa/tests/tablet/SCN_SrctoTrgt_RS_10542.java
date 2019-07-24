/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class SCN_SrctoTrgt_RS_10542 extends BaseLib {
	
	int iWhileCnt = 0;
	String sTestID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sFieldServiceName = null;
	String sIBName1 = null;
	String sIBName2 = null;
	String sIBRecord = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectAccID = null;
	String sSerialNumber = null;
	String sSqlQuery = null;
	String sAccountName = null;
	String sObjectProID = null;
	String sProductName = null;

	
	private void preRequiste() throws Exception { 
		
		restServices.getAccessToken();
		sSerialNumber = commonUtility.generateRandomNumber("IB_10542_");
		
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
		sJsonData = "{\"Name\": \""+sSerialNumber+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sSerialNumber+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Country__c\": \"United States\", \"SVMXC__City__c\": \"Liver Pool\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sIBRecord=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBRecord+"\'";				
		sIBName1 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sIBName1);

		//Creation of dynamic IB2
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\", \"Name\": \""+commonUtility.generateRandomNumber("IB_10542_")+"\", \"SVMXC__Serial_Lot_Number__c\": \""+commonUtility.generateRandomNumber("IB_10542_")+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Country__c\": \"Italy\", \"SVMXC__City__c\": \"Vatican\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sIBRecord=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBRecord+"\'";				
		sIBName2 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sIBName2);
		
		
		commonUtility.executeSahiScript("appium/SCN_SrctoTrgt_RS_10542_prerequisite.sah");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10542Test() throws Exception {
		// JiraLink
		if (BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10542");
		} else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12098");

		}
		
		sTestID = "RS_10542";	
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sTestID, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID,"ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sTestID, "ProcessName");
		preRequiste();
			
		//Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		
		toolsPo.configSync(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sIBName1, sFieldServiceName);

		//Validation of not qualifying Work Order
		Assert.assertTrue(workOrderPo.getElePopupTxt().getText().equals("Account can't be NULL"), "Error popup is not displayed");
		ExtentManager.logger.log(Status.PASS,"Error popup Country should be Italy is displayed successfully");
		
		commonUtility.tap(workOrderPo.getEleOKBtn());
		Thread.sleep(CommonUtility.iLowSleep);
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sIBName2, sFieldServiceName);
		Thread.sleep(CommonUtility.iLowSleep);
		
		//Validation of auto update process
		Assert.assertTrue(workOrderPo.getEleIBAccountIDTxt().getAttribute("value").equals(sAccountName), "Account is not displayed.");
		ExtentManager.logger.log(Status.PASS,"IB Account is displayed successfully");
		
		//Validation of auto update process
		Assert.assertTrue(workOrderPo.getEleIBComponentTxt().getAttribute("value").equals(sIBName2), "Component is not displayed");
		ExtentManager.logger.log(Status.PASS,"Component is  displayed");
		
		//Set the schedule Date for future date by 2 days
		if(BaseLib.sOSName.equals("ios") ){
			workOrderPo.getEleIBScheduledTxtFld().click();
			Thread.sleep(CommonUtility.iMedSleep);
			commonUtility.setDatePicker(1, 2);
			commonUtility.getEleDonePickerWheelBtn().click();
			commonUtility.switchContext("Webview");}
		else{
			commonUtility.tap(workOrderPo.getEleIBScheduledTxtFld());
			Thread.sleep(CommonUtility.iMedSleep);
			commonUtility.setDatePicker(1, 2);
			commonUtility.switchContext("native");
			commonUtility.getCalendarDone().click();
			commonUtility.switchContext("Webview");
			}
		
		Thread.sleep(CommonUtility.iMedSleep);
		//Save the case created by IB
		commonUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(CommonUtility.iLowSleep);
		//driver.activateApp(GenericLib.sAppBundleID);
		
		//Config Sync
		commonUtility.tap(toolsPo.getEleToolsIcn());
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		
		//Validation of WorkOrder from IB
		sSqlQuery="SELECT+Name+FROM+SVMXC__Service_Order__c+WHERE+SVMXC__Company__r.Name+=\'"+sSerialNumber+"account"+"\'";
		Assert.assertTrue(restServices.restGetSoqlValue(sSqlQuery,"Name")!=null, "IB to WorkOrder is not created");
		ExtentManager.logger.log(Status.PASS,"IB to WorkOrder is successfully created.");
	}
}
