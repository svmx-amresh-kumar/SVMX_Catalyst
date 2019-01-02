package com.ge.fsa.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_Lookups_1_RS_10527_b extends BaseLib {
	
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
	String sFieldProcessName = "AutoReg10529";
	String sAccountName = "McLaren3";
	String sProdName = "a1";
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10527_b() throws IOException, InterruptedException {
		
		// Create Account
//		String sAccId = restServices.restCreate("Account?","{\"Name\": \"Ferrari3\" }");
//		System.out.println("Fer "+sAccId);
//		String sAccId1 = restServices.restCreate("Account?","{\"Name\": \"McLaren3\" }");
//		System.out.println("McLaren "+sAccId1);
//		String sAccId2 = restServices.restCreate("Account?","{\"Name\": \"Williams3\" }");
//		System.out.println("Williams "+sAccId2);
//		String sAccId3 = restServices.restCreate("Account?","{\"Name\": \"Renault3\" }");
//		System.out.println("Renault "+sAccId3);
//		String sAccId4 = restServices.restCreate("Account?","{\"Name\": \"Red Bull3\" }");
//		System.out.println("Red Bull "+sAccId4);
		
		// Create Contact
//		String sConId = restServices.restCreate("Contact?","{\"FirstName\": \"Jody33\", \"LastName\": \"Scheckter22\", \"AccountId\": \""+sAccId+"\"}");
//		System.out.println("Jacke "+sConId);
//		String sConId1 = restServices.restCreate("Contact?","{\"FirstName\": \"John\", \"LastName\": \"Surtees\", \"AccountId\": \""+sAccId+"\"}");
//		System.out.println(sConId1);
//		String sConId2 = restServices.restCreate("Contact?","{\"FirstName\": \"Michael\", \"LastName\": \"Schumacher\", \"AccountId\": \""+sAccId+"\"}");
//		System.out.println(sConId2);
//		String sConId3 = restServices.restCreate("Contact?","{\"FirstName\": \"Kimi\", \"LastName\": \"Roikkonen\", \"AccountId\": \""+sAccId+"\"}");
//		System.out.println(sConId3);
//		String sConId4 = restServices.restCreate("Contact?","{\"FirstName\": \"Sebastian\", \"LastName\": \"Vettel\", \"AccountId\": \""+sAccId4+"\"}");
//		System.out.println(sConId4);
//		String sConId5 = restServices.restCreate("Contact?","{\"FirstName\": \"Fernando\", \"LastName\": \"Alonso\", \"AccountId\": \""+sAccId3+"\"}");
//		System.out.println(sConId5);
//		String sConId6 = restServices.restCreate("Contact?","{\"FirstName\": \"Mika\", \"LastName\": \"Hakkinen\", \"AccountId\": \""+sAccId1+"\"}");
//		System.out.println(sConId6);
//		String sConId7 = restServices.restCreate("Contact?","{\"FirstName\": \"Ayrton\", \"LastName\": \"Senna\", \"AccountId\": \""+sAccId1+"\"}");
//		System.out.println(sConId7);
//		String sConId8 = restServices.restCreate("Contact?","{\"FirstName\": \"James\", \"LastName\": \"Hunt\", \"AccountId\": \""+sAccId1+"\"}");
//		System.out.println(sConId8);
//		String sConId9 = restServices.restCreate("Contact?","{\"FirstName\": \"Damon\", \"LastName\": \"Hill\", \"AccountId\": \""+sAccId2+"\"}");
//		System.out.println(sConId9);
//		String sConId10 = restServices.restCreate("Contact?","{\"FirstName\": \"Nigel\", \"LastName\": \"Mansel\", \"AccountId\": \""+sAccId2+"\"}");
//		System.out.println(sConId10);
//		String sConId11 = restServices.restCreate("Contact?","{\"FirstName\": \"Jacques\", \"LastName\": \"Villeneuve\", \"AccountId\": \""+sAccId2+"\"}");
//		System.out.println(sConId11);
//		String sConId12 = restServices.restCreate("Contact?","{\"FirstName\": \"Alan\", \"LastName\": \"Jones\", \"AccountId\": \""+sAccId2+"\"}");
//		System.out.println(sConId12);
//		String sConId13 = restServices.restCreate("Contact?","{\"FirstName\": \"Keke\", \"LastName\": \"Roseberg\", \"AccountId\": \""+sAccId2+"\"}");
//		System.out.println(sConId13);
//		String sConId14 = restServices.restCreate("Contact?","{\"FirstName\": \"Stirling\",\"LastName\": \"Moss\"}");
//		System.out.println(sConId14);
//		String sConId15 = restServices.restCreate("Contact?","{\"FirstName\": \"David\",\"LastName\": \"Coulthard\"}");
//		System.out.println(sConId15);
		
		//Create Work Order
		String sWoID  = restServices.restCreate("SVMXC__Service_Order__c?","{}");
//		System.out.println("Wo ID "+sWoID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWoID+"\'", "Name");
//		System.out.println("WO no ="+sWOName);
		List<WebElement> contactList = new ArrayList<WebElement>();
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearch, sWOName, sFieldProcessName);
		//******Validate 4th Case******
		workOrderPo.addParts(commonsPo, workOrderPo, sProdName);
		workOrderPo.getLblChildPart(sProdName).click();
		commonsPo.tap(workOrderPo.getLblChildPart(sProdName));
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(workOrderPo.getLblPartContact());
		contactList = workOrderPo.getcontactListInLkp();
//		System.out.println("Contacts without Account "+contactList.size());
		String sConWoAcc = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Id+=null", "totalSize");
		System.out.println("Contacts Without Accounts fetched from Database ="+sConWoAcc);
//		Assert.assertEquals(contactList.size(), Integer.parseInt(sConWoAcc));
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		//******Validate 5th Case******
		commonsPo.tap(workOrderPo.getLblChildPart(sProdName));
		commonsPo.tap(workOrderPo.getLblPartAccount());
		commonsPo.lookupSearch(sAccountName);
		commonsPo.tap(workOrderPo.getLblPartContact());
		contactList = workOrderPo.getcontactListInLkp();
//		System.out.println("Contacts with Account Acme "+contactList.size());
		String sConWithAcme = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Name+=\'"+sAccountName+"\'", "totalSize");
//		System.out.println("Contacts with Account Acme fetched from Database ="+sConWithAcme);
		Assert.assertEquals(contactList.size(), Integer.parseInt(sConWithAcme));
		//******Validate 6th Case******
		commonsPo.tap(workOrderPo.getLnkFilters());
		Thread.sleep(GenericLib.iMedSleep);
//		System.out.println(workOrderPo.getCheckBoxAccount().isSelected());
		if(workOrderPo.getCheckBoxAccount().isSelected()) {
			workOrderPo.getcheckBoxAccount01().click();
			commonsPo.tap(workOrderPo.getcheckBoxAccount01(),20,20);
//			commonsPo.tap(workOrderPo.getcheckBoxAccount01());
		}
		commonsPo.tap(workOrderPo.getBtnApply());
		contactList = workOrderPo.getcontactListInLkp();
//		System.out.println("All Accounts "+contactList.size());
//		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		String sAllCon = restServices.restGetSoqlValue("SELECT+Count()+from+Contact", "totalSize");
		Assert.assertEquals(contactList.size(), Integer.parseInt(sAllCon));
		
	}
}
