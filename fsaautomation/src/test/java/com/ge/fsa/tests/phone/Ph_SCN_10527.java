package com.ge.fsa.tests.phone;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;

import com.ge.fsa.lib.Retry;

public class Ph_SCN_10527 extends BaseLib {
	
	String sAccountName = "AshAutoAcc";
	String sAccId = "";
	String sCountry = "Australia";
	String sCountry1 = "Afghanistan";
	String sProdName = "SampleProd";
	String sLocName = "Ricardo";
	String sLocName1 = "Nashville";
	String sTestCaseID = "RS_10527";
	String sScriptName = "appium/Scenario_10527.sah";
	Boolean bProcessCheckResult;
	String sProcessName = "Auto_Reg_10527";
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10527() throws Exception {
	
	// ******Creating Account******x
	String sAccCount = restServices.restGetSoqlValue("SELECT+Count()+from+Account+Where+name+=\'"+sAccountName+"\'", "totalSize");
//	System.out.println(sAccCount);
	if(Integer.parseInt(sAccCount)==0) {
	sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccountName+"\"}");
	System.out.println("The Acc Id of "+sAccountName+" is "+sAccId);
	// ******Create Contact******
	String sConId = restServices.restCreate("Contact?","{\"FirstName\": \"Shane\", \"LastName\": \"Shalken\", \"AccountId\": \""+sAccId+"\"}");
	System.out.println("SS "+sConId);
	String sConId1 = restServices.restCreate("Contact?","{\"FirstName\": \"Tim\", \"LastName\": \"Shalken\", \"AccountId\": \""+sAccId+"\"}");
	System.out.println("TS "+sConId1);
	String sConId2 = restServices.restCreate("Contact?","{\"FirstName\": \"Ronald\", \"LastName\": \"Ross\", \"AccountId\": \""+sAccId+"\"}");
	System.out.println("RR "+sConId2);
	}
	// ******Create Product******
	String sProd = restServices.restGetSoqlValue("SELECT+Count()+from+Product2+Where+name+=\'"+sProdName+"\'", "totalSize");
	if(Integer.parseInt(sProd)==0) {
	String sProdId = restServices.restCreate("Product2?","{\"Name\": \""+sProdName+"\"}");
	System.out.println("Product Id is "+sProdId);
	}
	// ******Create Location******
	String sLoc = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName+"\'", "totalSize");
	if(Integer.parseInt(sLoc)==0) {
	String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName+"\",\"SVMXC__Country__c\": \"Australia\"}");
	System.out.println("Loc Id is "+sLocId);
	}		
	// ******Create Location 1******
	String sLoc1 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName1+"\'", "totalSize");
	if(Integer.parseInt(sLoc1)==0) {
	String sLocId1 = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName1+"\",\"SVMXC__Country__c\": \"Afghanistan\"}");
	System.out.println("Loc Id is "+sLocId1);
	}
	
	// ******Creating Work Order******
	String sWoID  = restServices.restCreate("SVMXC__Service_Order__c?","{}");
//	System.out.println("Wo ID "+sWoID);
	String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWoID+"\'", "Name");
//	System.out.println("Wo ID "+sWOName);
	
	bProcessCheckResult = commonUtility.ProcessCheck(restServices, sProcessName, sScriptName, sTestCaseID);
	
	ph_LoginHomePo.login(commonUtility, ph_MorePo);
	
	ph_MorePo.syncData(commonUtility);
	
	String sAllCon = restServices.restGetSoqlValue("SELECT+Count()+from+Contact", "totalSize");
//	System.out.println(sAllCon);

	ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
	
	ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  "AUTOMATION SEARCH", "Work Orders",sWOName,sProcessName);
	
	// ***********Start of Scenario 1*******************
	ph_WorkOrderPo.getLblContact().click();
	String sConWoAcc = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Id+=null", "totalSize");
	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sConWoAcc));
	ExtentManager.logger.log(Status.PASS, "Contacts without Accounts matches with Database Count "+sConWoAcc);
	ph_WorkOrderPo.getBtnClose().click();
	// ************End of Scenario 1********************
	// ***********Start of Scenario 2*******************
	ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_WorkOrderPo.getLblAccount(), sAccountName);
	ph_WorkOrderPo.getLblContact().click();
	Thread.sleep(5000);
	String sConWithAcme = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Name+=\'"+sAccountName+"\'", "totalSize");
	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sConWithAcme));
	ExtentManager.logger.log(Status.PASS, "Contacts with Account "+sAccountName+"matches with Database Count "+sConWithAcme);
	// ************End of Scenario 2********************
	// ***********Start of Scenario 3*******************
	ph_WorkOrderPo.getBtnclrFilter().click();
//	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sAllCon));-Commenting as the scenario will fail if number of records exceed 250, taken care in scenario 2
//	ExtentManager.logger.log(Status.PASS, "All contacts are displayed after clearing the filter and the count matches with Database "+sAllCon);
	ph_WorkOrderPo.getBtnClose().click();
	// ************End of Scenario 3********************
	// ************Start of Scenario 7******************
	ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getCountryPicklst(), sCountry);
	ph_WorkOrderPo.getLblSite().click();
	String sLocCnt = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+SVMXC__Country__c+=\'Australia\'", "totalSize");
	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sLocCnt));
	ExtentManager.logger.log(Status.PASS, "All contacts with "+sCountry+" are displayed "+sLocCnt);
	ph_WorkOrderPo.getBtnClose().click();
	// ************End of Scenario 7********************
	// ************Start of Scenario 8******************
	ph_WorkOrderPo.addParts(commonUtility, sProdName);
	Thread.sleep(3000);
	ph_WorkOrderPo.getEle(sProdName).click();
	commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getLblToLocation());
	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sLocCnt));
	ExtentManager.logger.log(Status.PASS, "All contacts with "+sCountry+" are displayed in childlines "+sLocCnt);
	ph_WorkOrderPo.getBtnClose().click();
	Thread.sleep(5000);
	ph_WorkOrderPo.getBtnClose().click();
	if(sOSName.equalsIgnoreCase("android")) {
		ph_WorkOrderPo.getEleDiscardChangesButton().click();
	}
	// ************End of Scenario 8******************
	// ************Start of Scenario 9****************
	ph_WorkOrderPo.getEleOverViewTab().click();
	ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getCountryPicklst(), sCountry1);
	ph_WorkOrderPo.getTabParts().click();
	Thread.sleep(3000);
	ph_WorkOrderPo.getEle(sProdName).click();
	commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getLblToLocation());
	String sLocCnt1 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+SVMXC__Country__c+=\'Afghanistan\'", "totalSize");
	Thread.sleep(3000);
	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sLocCnt1));
	ExtentManager.logger.log(Status.PASS, "All contacts with "+sCountry1+" are displayed in childlines "+sLocCnt);
	ph_WorkOrderPo.getBtnClose().click();
	// ************End of Scenario 9******************
	// ************Start of Scenario 4****************
	commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getLblContact());
	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sConWoAcc));
	ExtentManager.logger.log(Status.PASS, "Contacts without Accounts matches with Database Count "+sConWoAcc);
	ph_WorkOrderPo.getBtnClose().click();
	Thread.sleep(5000);
	// ************End of Scenario 4******************
	// ************Start of Scenario 5****************
	ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_WorkOrderPo.getLblAccount(), sAccountName);
	commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getLblContact());
	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sConWithAcme));
	ExtentManager.logger.log(Status.PASS, "Contacts with Account "+sAccountName+"matches with Database Count "+sConWithAcme);
	// ************End of Scenario 5******************
	// ***********Start of Scenario 6*****************
	ph_WorkOrderPo.getBtnclrFilter().click();
//	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sAllCon));-Commenting as the scenario will fail if number of records exceed 250, taken care in scenario 2
//	ExtentManager.logger.log(Status.PASS, "All contacts are displayed after clearing the filter and the count matches with Database "+sAllCon);
	// ************End of Scenario 6******************
} 

}
