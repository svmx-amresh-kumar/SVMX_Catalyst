/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;
import org.json.JSONArray;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_SrctoTrgt_RS_10540 extends BaseLib {

	int iWhileCnt = 0;
	String sTestID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sIBRecord = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sIBName1 = null;
	String sIBName2 = null;
	String sFieldServiceName = null;
	String sObjectAccID = null;
	String sSerialNumber = null;
	String sSqlQuery = null;
	String sAccountName = null;
	String sObjectProID = null;
	String sProductName = null;
	
	private void preRequiste() throws Exception { 
		
		restServices.getAccessToken();
		sSerialNumber = commonsPo.generaterandomnumber("IB_10540_");
		
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
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\", \"Name\": \""+commonsPo.generaterandomnumber("IB_10540_")+"\", \"SVMXC__Serial_Lot_Number__c\": \""+commonsPo.generaterandomnumber("IB_10540_")+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Country__c\": \"Italy\", \"SVMXC__City__c\": \"Vatican\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sIBRecord=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBRecord+"\'";				
		sIBName2 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sIBName2);

		//sIBName1 ="IB_10540_27092018155351";
		//sIBName2 = "IB_10540_27092018155358";
		

		
		genericLib.executeSahiScript("appium/SCN_SrctoTrgt_RS_10540_prerequisite.sah", sTestID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + "Sahi verification failure");
		
		
	}

	@Test(enabled = true)
	public void SCN_SrctoTrgt_RS_10540() throws Exception {
		
		sTestID = "RS_10540";
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestID, sTestID,"ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestID,sTestID, "ProcessName");
		preRequiste();
		
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);

		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
	
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sIBName1, sFieldServiceName);

		//Validation of not qualifying Work Order
		Assert.assertTrue(workOrderPo.getElePopupTxt().getText().equals("Country should be Italy"), "Error popup is not displayed");
		ExtentManager.logger.log(Status.PASS,"Error popup Country should be Italy is displayed successfully");
		
		commonsPo.tap(workOrderPo.getEleOKBtn());
		Thread.sleep(GenericLib.iLowSleep);

		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sIBName2, sFieldServiceName);
		Thread.sleep(GenericLib.iLowSleep);

		
		commonsPo.tap(workOrderPo.getEleClickSave());
		Thread.sleep(GenericLib.iLowSleep);

		//Validation of auto update process
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Update process is not successful.");
		ExtentManager.logger.log(Status.PASS,"Update process is successful");
		
		
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		//JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("Select+Name+from+Auto_Custom_Object10540__c+where+Number_10540__c+= \'"+sIBName2+"\')");
		//System.out.println(restServices.getJsonValue(sJsonArrayparts, "Name"));

	}
}
