package com.ge.fsa.tests.phone;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_ExploreSearchRS_10546 extends BaseLib{


	String sTestID = null;
	String sExploreSearch = null;
	String sSerialNumber = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectID = null;
	String sSqlQuery = null;
	String sWOName = null;
	String sAccountNameA = null;
	String sObjectProID = null;
	String sProductName = null;
	String sContactName = null;
	String sWorkOrderCnt=null;
	String sProductCnt=null;
	String sAccountCnt=null;
	String sContactCnt=null;
	
	
	private void preRequiste() throws Exception  
	{
		restServices.getAccessToken();
		sSerialNumber = commonUtility.generaterandomnumber("");
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName =restServices.restGetSoqlValue(sSqlQuery,"Name"); //"WO-00000455"; 
		
		// Create product
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"product\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectProID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectProID+"\'";				
		sProductName  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//create Account
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccA\"}";
		sAccountNameA=restServices.restCreate(sObjectApi,sJsonData);
		System.out.println(sAccountNameA);
		
		//create Contact
		restServices.restCreate("Contact?","{\"FirstName\": \""+sSerialNumber+"\", \"LastName\": \"RS_10546\", \"AccountId\": \""+sAccountNameA+"\"}");
		sContactName = sSerialNumber+" "+"RS_10546";
		
		genericLib.executeSahiScript("appium/SCN_Explore_RS_10549_prerequisite.sah", sTestID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10546Test() throws Exception 
	{
		sTestID = "RS_10549";
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		preRequiste();

		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		//Config Sync for process
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		
		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		
		//Navigation to SFM search
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		
		//Validation of WorkOrder object search and count
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Work Orders").isDisplayed(), "Work Orders for RS_10549 SFM Search  is not displayed");
		ExtentManager.logger.log(Status.PASS," Work Orders for RS_10549 Multi Field WO Search text is successfully displayed");
		sSqlQuery ="SELECT count() FROM SVMXC__Service_Order__c";				
		sWorkOrderCnt  =restServices.restGetSoqlValue(sSqlQuery,"totalSize"); 
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItemCount("Work Orders").getText().contains(sWorkOrderCnt), "Invalid WorkOrder count is displayed.");
		ExtentManager.logger.log(Status.PASS,"Valid WorkOrder count is displayed.");
		
		//Validation of Contacts object search and count
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Contacts").isDisplayed(), "Contacts for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Contacts for RS_10549 Multi Field WO Search text is successfully displayed");
		sSqlQuery ="SELECT count() FROM Contact";				
		sContactCnt  =restServices.restGetSoqlValue(sSqlQuery,"totalSize"); 
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItemCount("Contacts").getText().contains(sContactCnt), "Invalid Contact count is displayed.");
		ExtentManager.logger.log(Status.PASS,"Valid Contact count is displayed.");
		
		//Validation of Products object search and count
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Products").isDisplayed(), "Products for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Products for RS_10549 Multi Field WO Search text is successfully displayed");
		sSqlQuery ="SELECT count() FROM Product2";				
		sProductCnt  =restServices.restGetSoqlValue(sSqlQuery,"totalSize"); 
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItemCount("Products").getText().contains(sProductCnt), "Invalid Product count is displayed.");
		ExtentManager.logger.log(Status.PASS,"Valid Product count is displayed.");

		//Validation of Accounts object search and count
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Accounts").isDisplayed(), "Accounts for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Accounts for RS_10549 SFM Search is successfully displayed");
		sSqlQuery ="SELECT count() FROM Account";				
		sAccountCnt  =restServices.restGetSoqlValue(sSqlQuery,"totalSize"); 
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItemCount("Accounts").getText().contains(sAccountCnt), "Invalid Account count is displayed.");
		ExtentManager.logger.log(Status.PASS,"Valid Accounts count is displayed.");

		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Work Orders", sWOName, null);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(ph_WorkOrderPo.getEleHeaderSubTitle().getText().equals(sWOName), sWOName +" is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName+" is successfully displayed");
		
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Contacts", sWOName, null);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(ph_WorkOrderPo.getEleHeaderSubTitle().getText().equals(sContactName), sContactName +" is not displayed");
		ExtentManager.logger.log(Status.PASS,sContactName+" is successfully displayed");
		
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Products", sWOName, null);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(ph_WorkOrderPo.getEleHeaderSubTitle().getText().equals(sProductName), sProductName +" is not displayed");
		ExtentManager.logger.log(Status.PASS,sProductName+" is successfully displayed");
		
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Accounts", sWOName, null);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(ph_WorkOrderPo.getEleHeaderSubTitle().getText().equals(sSerialNumber+"AccA"), sSerialNumber+"AccA is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber+"AccA is successfully displayed");
		
	}


}
