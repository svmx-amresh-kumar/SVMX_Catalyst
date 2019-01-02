package com.ge.fsa.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_Lookups_1_RS_10527 extends BaseLib {
	
	String sTestCaseID = "RS_10527";
	String sScriptName = "Scenario_10527";
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
	String sProcessName = "AutoReg10529";
	String sAccountName = "AshwiniAutoAcc";
	String sAccId = "";
	
	@Test//(retryAnalyzer=Retry.class)
	public void RS_10527() throws Exception {
		
//		commonsPo.preReq(genericLib, sScriptName, sTestCaseID);
		// Create Account
		String sAccCount = restServices.restGetSoqlValue("SELECT+Count()+from+Account+Where+name+=\'"+sAccountName+"\'", "totalSize");
		System.out.println(sAccCount);
		if(Integer.parseInt(sAccCount)==0) {
			sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccountName+"\"}");
			System.out.println("The Acc Id of "+sAccountName+" is "+sAccId);
			// Create Contact
			String sConId = restServices.restCreate("Contact?","{\"FirstName\": \"Shane\", \"LastName\": \"Shalken\", \"AccountId\": \""+sAccId+"\"}");
			System.out.println("SS "+sConId);
			String sConId1 = restServices.restCreate("Contact?","{\"FirstName\": \"Tim\", \"LastName\": \"Shalken\", \"AccountId\": \""+sAccId+"\"}");
			System.out.println("TS "+sConId1);
			String sConId2 = restServices.restCreate("Contact?","{\"FirstName\": \"Ronald\", \"LastName\": \"Ross\", \"AccountId\": \""+sAccId+"\"}");
			System.out.println("RR "+sConId2);
		}
		
		//******Creating Work Order******
		String sWoID  = restServices.restCreate("SVMXC__Service_Order__c?","{}");
//		System.out.println("Wo ID "+sWoID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWoID+"\'", "Name");
//		System.out.println("WO no ="+sWOName);
//		String sProdName = "a1";
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(10000);
	//	toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearch, sWOName, sProcessName);
		//******Validate 1st Case******
		commonsPo.tap(workOrderPo.getLblContact());
		List<WebElement> contactList = new ArrayList<WebElement>();
		contactList = workOrderPo.getcontactListInLkp();
//		System.out.println("Contacts without Account "+contactList.size());
		String sConWoAcc = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Id+=null", "totalSize");
//		System.out.println("Contacts Without Accounts fetched from Database ="+sConWoAcc);
		Assert.assertEquals(contactList.size(), Integer.parseInt(sConWoAcc));
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		//******Validate 2nd Case******
		commonsPo.tap(workOrderPo.getLblAccount());
		commonsPo.lookupSearch(sAccountName);
		commonsPo.tap(workOrderPo.getLblContact());
		contactList = workOrderPo.getcontactListInLkp();
//		System.out.println("Contacts with Account Acme "+contactList.size());
		String sConWithAcme = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Name+=\'"+sAccountName+"\'", "totalSize");
//		System.out.println("Contacts with Account Acme fetched from Database ="+sConWithAcme);
		Assert.assertEquals(contactList.size(), Integer.parseInt(sConWithAcme));
		//******Validate 3rd Case******
		commonsPo.tap(workOrderPo.getLnkFilters());
		Thread.sleep(GenericLib.iLowSleep);
		if(workOrderPo.getCheckBoxAccount().isSelected()) {
			commonsPo.tap(workOrderPo.getcheckBoxAccount01(),20,20);
		}
		commonsPo.tap(workOrderPo.getBtnApply());
		contactList = workOrderPo.getcontactListInLkp();
//		System.out.println("All Accounts "+contactList.size());
		String sAllCon = restServices.restGetSoqlValue("SELECT+Count()+from+Contact", "totalSize");
//		System.out.println("All Contacts fetched from Database ="+sAllCon);
//		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		Assert.assertEquals(contactList.size(), Integer.parseInt(sAllCon));
	
	}
}
