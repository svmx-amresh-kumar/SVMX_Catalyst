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

public class SCN_RS_10548 extends BaseLib {
	
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
	
	
	private void preRequiste() throws Exception { 

		restServices.getAccessToken();
		sSerialNumber = commonsPo.generaterandomnumber("RS_10548_");
		
		//create Account
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccA\"}";
		sAccountNameA=restServices.restCreate(sObjectApi,sJsonData);
		System.out.println(sAccountNameA);
		
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccB\"}";
		sAccountNameB=restServices.restCreate(sObjectApi,sJsonData);
		
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccC\"}";
		sAccountNameC=restServices.restCreate(sObjectApi,sJsonData);
			
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Company__c\":\""+sAccountNameA+"\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName1 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Company__c\":\""+sAccountNameB+"\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110008\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName2 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Company__c\":\""+sAccountNameB+"\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110015\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName3 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Hove\",\"SVMXC__Zip__c\":\"BN52\",\"SVMXC__Country__c\":\"United Kingdom\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName4 =restServices.restGetSoqlValue(sSqlQuery,"Name"); //"WO-00000455"; 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Mysore\",\"SVMXC__Zip__c\":\"570010\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Karnataka\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName5 =restServices.restGetSoqlValue(sSqlQuery,"Name"); //"WO-00000455"; 
		
		genericLib.executeSahiScript("appium/SCN_Explore_RS_10548_prerequisite.sah", sTestID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true)
	public void RS_10548Test() throws Exception {
		sTestID = "RS_10548";
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		
		try {
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
		exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		
		//Validation of WO with invalid country
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys("USA");
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		//Validation of WO with invalid account
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sSerialNumber+"AccC");
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		//Validation of WO with invalid wo number
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys("WO-354300000");
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(workOrderPo.getEleNoRecordsTxt().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		//Validation of WO in search
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName5);
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName5).isDisplayed(), "Work Order1 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 Record is successfully displayed");
		
		//Validation of WO with valid country
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys("India");
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName1).isDisplayed(), "Work Order1 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 Record is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName2).isDisplayed(), "Work Order2 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 Record is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName3).isDisplayed(), "Work Order3 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 Record is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName5).isDisplayed(), "Work Order5 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 Record is successfully displayed");
		
		//Validation of WO with valid country
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys("United Kingdom");
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
		
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName4).isDisplayed(), "Work Order4 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order4 Record is successfully displayed");
		
		//Validation of WO with valid account number
		exploreSearchPo.getEleExploreSearchTxtFld().click();
		
		try {exploreSearchPo.getEleResetFilerBtn().click();Thread.sleep(GenericLib.iMedSleep);}catch(Exception e) {}
		exploreSearchPo.getEleExploreSearchTxtFld().clear();
		exploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sSerialNumber+"AccB");
		commonsPo.tap(exploreSearchPo.getEleExploreSearchBtn());
		Thread.sleep(GenericLib.iMedSleep);
				
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName2).isDisplayed(), "Work Order2 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order2 Record is successfully displayed");
	
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName3).isDisplayed(), "Work Order3 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order3 Record is successfully displayed");
	
		/*
		JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("Select+Name+from+Auto_Custom_Object10540__c+where+Number_10540__c+= \'"+sIBName2+"\')");
		System.out.println(restServices.getJsonValue(sJsonArrayparts, "Name"));
		
		JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("Select+Name+from+Auto_Custom_Object10540__c+where+Number_10540__c+= \'"+sIBName2+"\')");
		System.out.println(restServices.getJsonValue(sJsonArrayparts, "Name"));
		*/
		}
		catch(Exception e) {
			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + " Testcase failed");

			throw e;
			
		}
		
	}

	
	
	
	
}
