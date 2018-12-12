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
import com.ge.fsa.lib.Retry;

public class SCN_ExploreSearchRS_10546 extends BaseLib {

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
	
	private void preRequiste() throws Exception  
	{
		restServices.getAccessToken();
		sSerialNumber = commonsPo.generaterandomnumber("");
		
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
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10546Test() throws Exception 
	{
		sTestID = "RS_10549";
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		
	
		preRequiste();

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
		
		commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		Assert.assertTrue(exploreSearchPo.getEleExploreChildSearchTxt("Work Orders").isDisplayed(), "Work Orders for RS_10549 SFM Search  is not displayed");
		ExtentManager.logger.log(Status.PASS," Work Orders for RS_10549 Multi Field WO Search text is successfully displayed");
	
		Assert.assertTrue(exploreSearchPo.getEleExploreChildSearchTxt("Contacts").isDisplayed(), "Contacts for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Contacts for RS_10549 Multi Field WO Search text is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleExploreChildSearchTxt("Products").isDisplayed(), "Products for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Products for RS_10549 Multi Field WO Search text is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleExploreChildSearchTxt("Accounts").isDisplayed(), "Accounts for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Accounts for RS_10549 SFM Search is successfully displayed");
			
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, "Work Orders", sWOName, null);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(workOrderPo.getEleObjectTxt(sWOName).isDisplayed(), sWOName +" is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName+" is successfully displayed");
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, "Contacts", sContactName, null);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(workOrderPo.getEleObjectTxt(sContactName).isDisplayed(), sContactName +" is not displayed");
		ExtentManager.logger.log(Status.PASS,sContactName+" is successfully displayed");
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, "Products", sProductName, null);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(workOrderPo.getEleObjectTxt(sProductName).isDisplayed(), sProductName +" is not displayed");
		ExtentManager.logger.log(Status.PASS,sProductName+" is successfully displayed");
		
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, "Accounts", sSerialNumber+"AccA", null);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(workOrderPo.getEleObjectTxt(sSerialNumber+"AccA").isDisplayed(), sSerialNumber+"AccA is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber+"AccA is successfully displayed");
		
	}

}

