/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_RS_10543 extends BaseLib {
	
	int iWhileCnt = 0;
	String sTestID = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectID = null;	
	String sFieldServiceName = null;
	String sSerialNumber = null;
	String sSqlQuery = null;
	String sWOName = null;
	String sCaseID = null;
	String sProductName = null;
	String sBillingType = null;
	
	
	private void preRequiste() throws Exception { 

		restServices.getAccessToken();
		sSerialNumber = commonsPo.generaterandomnumber("RS_10543_");
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName =restServices.restGetSoqlValue(sSqlQuery,"Name"); //"WO-00000455"; 
		
		sJsonData = "{\"Origin\": \"phone\", \"Subject\": \"Test RS_10543 Validation\", \"Priority\": \"High\", \"Description\": \"Description of RS_10543 \",\"Status\": \"Escalated\"}";
		sObjectApi = "Case?";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+CaseNumber+from+Case+Where+id+=\'"+sObjectID+"\'";				
		sCaseID  =restServices.restGetSoqlValue(sSqlQuery,"CaseNumber"); 
						
		genericLib.executeSahiScript("appium/SCN_Explore_RS_10543_prerequisite.sah", sTestID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
	}

	@Test(enabled = true)
	public void RS_10543Test() throws Exception {
		sTestID = "RS_10543";
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestID, sTestID,"ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestID,sTestID, "ProcessName");
		sBillingType = GenericLib.getExcelData(sTestID,sTestID, "BillingType");
		
		preRequiste();
		//sWOName="WO-00004442";
		//sCaseID="00001211";
		
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		
		//Config Sync for process
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
			
		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep); 
		
		//Navigation to SFM
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		commonsPo.longPress(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName);
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		commonsPo.longPress(workOrderPo.getEleIncludeOnlineRdBtn());
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iHighSleep);
		
		Assert.assertTrue(workOrderPo.getEleCloudIcn().isDisplayed(), "Cloud Icon is not displayed");
		ExtentManager.logger.log(Status.PASS,"Cloud Icon  is successfully displayed");
		
		commonsPo.tap(workOrderPo.getEleCloudIcn(),20,20);
		Thread.sleep(GenericLib.iHighSleep);
		commonsPo.tap(exploreSearchPo.getEleWorkOrderIDTxt(sWOName),10,10);
		
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.getEleActionsLnk().click();
		commonsPo.tap(workOrderPo.getEleActionsLnk());	
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.getSearch(workOrderPo.getEleActionsTxt(sFieldServiceName));
		commonsPo.tap(workOrderPo.getEleActionsTxt(sFieldServiceName),20,20);

		//Selecting Billing Type to contract to make sure sfm is working fine.
		commonsPo.setPickerWheelValue(workOrderPo.getEleBillingTypeLst(), "Contract");
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		Thread.sleep(GenericLib.iMedSleep);
		
		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
		
		//Steps for Case search
		sExploreChildSearchTxt="Cases";
		sFieldServiceName="Add/Edit Case Lines";
		
		//Navigation to SFM
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		Thread.sleep(GenericLib.iMedSleep);
		//commonsPo.tap(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		//exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		commonsPo.longPress(exploreSearchPo.getEleExploreChildSearchTxt(sExploreChildSearchTxt));
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sCaseID);
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		commonsPo.longPress(workOrderPo.getEleIncludeOnlineRdBtn());
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleCloudIcn().isDisplayed(), "Cloud Icon is not displayed");
		ExtentManager.logger.log(Status.PASS,"Cloud Icon  is successfully displayed");
		
		commonsPo.tap(workOrderPo.getEleCloudIcn(),20,20);
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(exploreSearchPo.getEleWorkOrderIDTxt(sCaseID),10,10);
		
		Thread.sleep(1000);
		workOrderPo.getEleActionsLnk().click();
		commonsPo.tap(workOrderPo.getEleActionsLnk());	
		commonsPo.getSearch(workOrderPo.getEleActionsTxt(sFieldServiceName));
		commonsPo.tap(workOrderPo.getEleActionsTxt(sFieldServiceName),20,20);

		//Selecting Billing Type to contract to make sure sfm is working fine.
		commonsPo.setPickerWheelValue(workOrderPo.getEleCaseReasonLst(), "Existing problem");
		commonsPo.tap(workOrderPo.getEleSaveLnk());

		//Validation of qualifying workorder with Issue found text error.
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Saved successfully is not displayed");
		ExtentManager.logger.log(Status.PASS,"Saved successfully text is displayed successfully");
		
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		/*
		JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("Select+Name+from+Auto_Custom_Object10540__c+where+Number_10540__c+= \'"+sIBName2+"\')");
		System.out.println(restServices.getJsonValue(sJsonArrayparts, "Name"));
		
		JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("Select+Name+from+Auto_Custom_Object10540__c+where+Number_10540__c+= \'"+sIBName2+"\')");
		System.out.println(restServices.getJsonValue(sJsonArrayparts, "Name"));
		*/
	
	}

}
