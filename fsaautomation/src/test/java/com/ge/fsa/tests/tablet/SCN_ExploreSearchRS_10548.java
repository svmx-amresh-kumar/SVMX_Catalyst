/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.tablet;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class SCN_ExploreSearchRS_10548 extends BaseLib {
	
	int iWhileCnt = 0;
	String sTestID = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectID = null;	
	String sSerialNumber = null;
	String sSqlQuery = null;
	String sWOName1 = null;
	String sWOName2 = null;
	String sWOName3 = null;
	String sWOName4 = null;
	String sWOName5 = null;
	String sAccountNameA=null;
	String sAccountNameB=null;
	String sAccountNameC=null;
	String sLocationA = null;
	String sLocationB = null;
	String sLocationC = null;
	String sLocationD = null;
	String sLocationE = null;
	String sLocationId = null;
	
	
	private void preRequiste() throws Exception { 

		restServices.getAccessToken();
		sSerialNumber = commonUtility.generateRandomNumber("RS_10548_");
		
		//create Account
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccA\"}";
		sAccountNameA=restServices.restCreate(sObjectApi,sJsonData);
		System.out.println(sAccountNameA);
		sObjectApi = "Account";
		sJsonData="{\"BillingCity\":\""+"Hyderabad"+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sAccountNameA );
	
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccB\"}";
		sAccountNameB=restServices.restCreate(sObjectApi,sJsonData);
		sObjectApi = "Account";
		sJsonData="{\"BillingCity\":\""+"Bangalore"+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sAccountNameB );

		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccC\"}";
		sAccountNameC=restServices.restCreate(sObjectApi,sJsonData);
		sObjectApi = "Account";
		sJsonData="{\"BillingCity\":\""+"Bangalore"+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sAccountNameC );

		//Create Location
		sLocationA = sSerialNumber+"LocA";
		sObjectApi = "SVMXC__Site__c?";
		sJsonData = "{\"Name\": \""+sLocationA+"\", \"SVMXC__Stocking_Location__c\": false,\"SVMXC__Street__c\": \"Lewes\",\"SVMXC__Country__c\": \"United Kingdom\"}";
		sLocationA = restServices.restCreate(sObjectApi,sJsonData);
		
		//Create Location
		sLocationB = sSerialNumber+"LocB";
		sJsonData = "{\"Name\": \""+sLocationB+"\", \"SVMXC__Stocking_Location__c\": true,\"SVMXC__Street__c\": \"Colombo\",\"SVMXC__Country__c\": \"SriLanka\"}";
		sLocationB = restServices.restCreate(sObjectApi,sJsonData);
		
		//Create Location
		sLocationC = sSerialNumber+"LocC";
		sJsonData = "{\"Name\": \""+sLocationC+"\", \"SVMXC__Stocking_Location__c\": true,\"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}";
		sLocationC = restServices.restCreate(sObjectApi,sJsonData);
		
		//Create Location
		sLocationD = sSerialNumber+"LocD";
		sJsonData ="{\"Name\": \""+sLocationD+"\", \"SVMXC__Stocking_Location__c\": false,\"SVMXC__Street__c\": \"Berlin\",\"SVMXC__Country__c\": \"Germany\"}" ;
		sLocationD = restServices.restCreate(sObjectApi,sJsonData);
		
		//Create Location
		sLocationE = sSerialNumber+"LocE";
		sJsonData ="{\"Name\": \""+sLocationE+"\", \"SVMXC__Stocking_Location__c\": false}" ;
		sLocationE = restServices.restCreate(sObjectApi,sJsonData);
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"High\",\"SVMXC__Site__c\":\""+sLocationA+"\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName1 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Company__c\":\""+sAccountNameB+"\",\"SVMXC__Priority__c\":\"High\",\"SVMXC__Site__c\":\""+sLocationE+"\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName2 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"Medium\",\"SVMXC__Site__c\":\""+sLocationC+"\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName3 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Company__c\":\""+sAccountNameA+"\",\"SVMXC__Priority__c\":\"High\",\"SVMXC__Site__c\":\""+sLocationD+"\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName4 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"High\",\"SVMXC__Site__c\":\""+sLocationE+"\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName5 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		commonUtility.executeSahiScript("appium/SCN_Explore_RS_10548_prerequisite.sah", sTestID);
		
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10548Test() throws Exception {
		sTestID = "RS_10548";
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID,"ExploreSearch");
		
		try {
		preRequiste();
		
		//Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		
		//Config Sync for process
		toolsPo.configSync(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
			
		//Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep); 
		
		//Navigation to SFM
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		commonUtility.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		
		//Validation of WO with invalid country
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys("Srilanka");
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		//Validation of WO with invalid account
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sSerialNumber+"AccC");
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		//Validation of WO with invalid wo number
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys("WO-354300000");
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		//Validation of WO in search
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys("WO");
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleWoOrderNumberTxt().isDisplayed(), "Display field WO number is not displayed.");
		ExtentManager.logger.log(Status.PASS,"Display field WO number text is successfully displayed");
		
		Assert.assertTrue(workOrderPo.getEleBillingCityTxt().isDisplayed(), "Display field Billing City Text is not displayed.");
		ExtentManager.logger.log(Status.PASS,"Display field Billing City Text is successfully displayed");
		
		Assert.assertTrue(workOrderPo.getElePriorityTxt().isDisplayed(), "Display field Priority text is not displayed.");
		ExtentManager.logger.log(Status.PASS,"Display field Priority text is successfully displayed");
		
		//Validation of WO1
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName1).isDisplayed(), "Work Order1 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 Record is successfully displayed");
		
		Assert.assertTrue(workOrderPo.getEleWoPriorityTxt(sWOName1,"High").isDisplayed(), "Work Order1 priority is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 priority is successfully displayed");
		
		//Validation of WO2
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName2).isDisplayed(), "Work Order2 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order2 Record is successfully displayed");
		
		Assert.assertTrue(workOrderPo.getEleWoBillingCityTxt(sWOName2,"Bangalore").isDisplayed(), "Work Order2 Billing city is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order2 Billing city is successfully displayed");
		
		Assert.assertTrue(workOrderPo.getEleWoPriorityTxt(sWOName2, "High").isDisplayed(), "Work Order2 priority is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order2 priority is successfully displayed");
		
		//Validation of WO3
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName3).isDisplayed(), "Work Order3 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order3 Record is successfully displayed");
			
		Assert.assertTrue(workOrderPo.getEleWoPriorityTxt(sWOName3, "Medium").isDisplayed(), "Work Order3 priority is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order3 priority is successfully displayed");
		
		//Validation of WO4
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName4).isDisplayed(), "Work Order4 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order4 Record is successfully displayed");
		
		Assert.assertTrue(workOrderPo.getEleWoBillingCityTxt(sWOName4, "Hyderabad").isDisplayed(), "Work Order4 Billing city is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order4 Billing city is successfully displayed");
		
		Assert.assertTrue(workOrderPo.getEleWoPriorityTxt(sWOName4,"High").isDisplayed(), "Work Order4 priority is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order4 priority is successfully displayed");
		
		//Validation of WO5
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName5).isDisplayed(), "Work Order5 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order5 Record is successfully displayed");
		
		Assert.assertTrue(workOrderPo.getEleWoPriorityTxt(sWOName5,"High").isDisplayed(), "Work Order5 priority is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order5 priority is successfully displayed");
		
		//Validation of WO with WO-number
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName5);
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName5).isDisplayed(), "Work Order5 with WO-number search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order5 Record with WO-number search is successfully displayed");
		
		//Validation of WO4 with valid country
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys("Germany");
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName4).isDisplayed(), "Work Order4 with country-site search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order4 Record with country-site search is successfully displayed");
		
		//Validation of WO1 & WO3 with valid country
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys("in");
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
		
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName1).isDisplayed(), "Work Order1 with country-site is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 Record with country-site is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName3).isDisplayed(), "Work Order3 with country-site is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order3 Record with country-site is successfully displayed");
		
		//Validation of WO4 with valid account number
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sSerialNumber+"AccA");
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
				
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName4).isDisplayed(), "Work Order4 with account search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order4 Record with account search is successfully displayed");
	
		//Validation of WO2 with valid account number
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(CommonUtility.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sSerialNumber+"AccB");
		commonUtility.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(CommonUtility.iMedSleep);
				
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName2).isDisplayed(), "Work Order2 with account search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order2 Record with account search is successfully displayed");
	
		
		}
		catch(Exception e) {
			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + " Testcase failed");

			throw e;
			
		}
		
	}

	
	
	
	
}
