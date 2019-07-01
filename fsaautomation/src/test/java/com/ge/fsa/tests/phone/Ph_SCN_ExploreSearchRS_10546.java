package com.ge.fsa.tests.phone;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
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
		sSerialNumber = commonUtility.generateRandomNumber("");
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName =restServices.restGetSoqlValue(sSqlQuery,"Name"); //"WO-00000455";
		ExtentManager.logger.log(Status.INFO, "Work Order has been created through rest web service. Work Order Id:"+sObjectID);

		
		// Create product
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"product\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectProID=restServices.restCreate(sObjectApi,sJsonData);
		ExtentManager.logger.log(Status.INFO, "Product has been created through rest web service with Name:"+sSerialNumber+" and returned Product Id:"+sObjectProID);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectProID+"\'";				
		sProductName  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//create Account
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccA\"}";
		sAccountNameA=restServices.restCreate(sObjectApi,sJsonData);
		ExtentManager.logger.log(Status.INFO, "Account has been created through rest web service with Name:"+sSerialNumber+" and returned Account Id:"+sAccountNameA);
		
		//create Contact
		restServices.restCreate("Contact?","{\"FirstName\": \""+sSerialNumber+"\", \"LastName\": \"RS_10546\", \"AccountId\": \""+sAccountNameA+"\"}");
		sContactName = sSerialNumber+" "+"RS_10546";
		ExtentManager.logger.log(Status.INFO, "Contact has been created through rest web service with Name:"+sSerialNumber+" and returned Account Id:"+sContactName);

		
		commonUtility.executeSahiScript("appium/SCN_Explore_RS_10549_prerequisite.sah", sTestID);
		
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(retryAnalyzer=Retry.class)
	public void RS_10546Test() throws Exception 
	{
		sTestID = "RS_10549";
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID,"ExploreSearch");
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
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Work Orders").isDisplayed(), "Work Orders for RS_10549 SFM Search  is not displayed ");
		ExtentManager.logger.log(Status.PASS," Work Orders for RS_10549 Multi Field WO Search text is successfully displayed");
		sSqlQuery ="SELECT count() FROM SVMXC__Service_Order__c";				
		sWorkOrderCnt  =restServices.restGetSoqlValue(sSqlQuery,"totalSize");
		String appWorkOrdercnt=ph_ExploreSearchPo.getEleSearchListItemCount("Work Orders").getText();
		Assert.assertTrue(appWorkOrdercnt.equals(sWorkOrderCnt), "Invalid WorkOrder count is displayed. Expected:"+sWorkOrderCnt
				+" Actual:"+appWorkOrdercnt);
		ExtentManager.logger.log(Status.PASS,"Valid WorkOrder count is displayed. Expected:"+sWorkOrderCnt+" Actual:"+appWorkOrdercnt);
		
		//Validation of Contacts object search and count
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Contacts").isDisplayed(), "Contacts for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Contacts for RS_10549 Multi Field WO Search text is successfully displayed");
		sSqlQuery ="SELECT count() FROM Contact";				
		sContactCnt  =restServices.restGetSoqlValue(sSqlQuery,"totalSize");
		String appContactCnt=ph_ExploreSearchPo.getEleSearchListItemCount("Contacts").getText();
		Assert.assertTrue(appContactCnt.equals(sContactCnt), "Invalid Contact count is displayed. Expected:"+sContactCnt
				+" Actual:"+appContactCnt);
		ExtentManager.logger.log(Status.PASS,"Valid Contact count is displayed.Expected:"+sContactCnt+" Actual:"+appContactCnt);
		
		//Validation of Products object search and count
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Products").isDisplayed(), "Products for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Products for RS_10549 Multi Field WO Search text is successfully displayed");
		sSqlQuery ="SELECT count() FROM Product2";				
		sProductCnt  =restServices.restGetSoqlValue(sSqlQuery,"totalSize");
		String appProductCnt=ph_ExploreSearchPo.getEleSearchListItemCount("Products").getText();
		Assert.assertTrue(appProductCnt.equals(sProductCnt), "Invalid Product count is displayed. Expected:"+sProductCnt
				+" Actual:"+appProductCnt);
		ExtentManager.logger.log(Status.PASS,"Valid Product count is displayed.Expected:"+sProductCnt+" Actual:"+appProductCnt);

		//Validation of Accounts object search and count
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Accounts").isDisplayed(), "Accounts for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Accounts for RS_10549 SFM Search is successfully displayed");
		sSqlQuery ="SELECT count() FROM Account";				
		sAccountCnt  =restServices.restGetSoqlValue(sSqlQuery,"totalSize"); 
		String appAccountCnt=ph_ExploreSearchPo.getEleSearchListItemCount("Accounts").getText();
		Assert.assertTrue(appAccountCnt.equals(sAccountCnt), "Invalid Account count is displayed. Expected:"+sAccountCnt
				+" Actual:"+appAccountCnt);
		ExtentManager.logger.log(Status.PASS,"Valid Accounts count is displayed.Expected: "+sAccountCnt+" Actual:"+appAccountCnt);

		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Work Orders", sWOName, null);
		Assert.assertTrue(ph_WorkOrderPo.getEleHeaderSubTitle().getText().equals(sWOName), sWOName +" is not displayed for Work Orders, actual displayed is"+ph_WorkOrderPo.getEleHeaderSubTitle().getText());
		ExtentManager.logger.log(Status.PASS,sWOName+" is successfully displayed");
		
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Contacts", sContactName, null);
		Assert.assertTrue(ph_WorkOrderPo.getEleHeaderSubTitle().getText().equals(sContactName), sContactName +" is not displayed for contacts, actual displayed is"+ph_WorkOrderPo.getEleHeaderSubTitle().getText());
		ExtentManager.logger.log(Status.PASS,sContactName+" is successfully displayed");
		
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Products", sProductName, null);
		Assert.assertTrue(ph_WorkOrderPo.getEleHeaderSubTitle().getText().equals(sProductName), sProductName +" is not displayed for products, actual displayed is"+ph_WorkOrderPo.getEleHeaderSubTitle().getText());
		ExtentManager.logger.log(Status.PASS,sProductName+" is successfully displayed");
		
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Accounts", sSerialNumber+"AccA", null);
		Assert.assertTrue(ph_WorkOrderPo.getEleHeaderSubTitle().getText().equals(sSerialNumber+"AccA"), sSerialNumber+"AccA is not displayed for Accoutns, actual displayed is"+ph_WorkOrderPo.getEleHeaderSubTitle().getText());
		ExtentManager.logger.log(Status.PASS,sSerialNumber+"AccA is successfully displayed");
		
	}


}
