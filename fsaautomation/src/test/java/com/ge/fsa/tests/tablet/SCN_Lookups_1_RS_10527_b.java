package com.ge.fsa.tests.tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.Retry;

public class SCN_Lookups_1_RS_10527_b extends BaseLib {
	
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
//	String sFieldProcessName = "AutoReg10529";
	String sFieldProcessName = "Auto_Reg_10527";
	String sAccountName = "AshwiniAutoAcc";
	String sProdName = "SampleProd";
	String sSearchTxt = "Ronald Ross";
	int getContactCount = 0;
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10527_b() throws IOException, InterruptedException {
		//Test has been split
		// JiraLink
		if (BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10527");
		} else {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12113");

		}
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
		loginHomePo.login(commonUtility, exploreSearchPo);	
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		toolsPo.configSync(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearch, sWOName, sFieldProcessName);
		//******Validate 4th Case******
		workOrderPo.addParts(commonUtility, workOrderPo, sProdName);
		workOrderPo.getLblChildPart(sProdName).click();
		commonUtility.tap(workOrderPo.getLblChildPart(sProdName));
		Thread.sleep(CommonUtility.iMedSleep);
		commonUtility.tap(workOrderPo.getLblPartContact());
		contactList = workOrderPo.getcontactListInLkp();
		System.out.println("Contacts without Account "+contactList.size());
		String sConWoAcc = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Id+=null", "totalSize");
		System.out.println("Contacts Without Accounts fetched from Database ="+sConWoAcc);
		if(sOSName.equals("android")) {
			System.out.println(commonUtility.getHeaderCount(workOrderPo));
			Assert.assertEquals(commonUtility.getHeaderCount(workOrderPo), Integer.parseInt(sConWoAcc));
		}
		else {
			Assert.assertEquals(contactList.size(), Integer.parseInt(sConWoAcc));
		}
		commonUtility.tap(workOrderPo.getLnkLookupCancel());
		//******Validate 5th Case******
		commonUtility.tap(workOrderPo.getLblChildPart(sProdName));
		commonUtility.tap(workOrderPo.getLblPartAccount());
		commonUtility.lookupSearch(sAccountName);
		commonUtility.tap(workOrderPo.getLblPartContact());
		contactList = workOrderPo.getcontactListInLkp();
//		System.out.println("Contacts with Account "+contactList.size());
//		for(WebElement we:contactList) {
//			System.out.println(we.getText());
//		}
		String sContactWithAcc = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Name+=\'"+sAccountName+"\'", "totalSize");
//		System.out.println("Contacts with Account fetched from Database ="+sContactWithAcc);
		if(sOSName.equals("android")) {
//			getContactCount = Integer.parseInt(workOrderPo.getTxtFullNameHeader().getText().substring(workOrderPo.getTxtFullNameHeader().getText().indexOf('(')+1,workOrderPo.getTxtFullNameHeader().getText().indexOf(')')));
			Assert.assertEquals(commonUtility.getHeaderCount(workOrderPo), Integer.parseInt(sContactWithAcc));
		}
		else {
			Assert.assertEquals(contactList.size(), Integer.parseInt(sContactWithAcc));
		}
		
		//******Validate 6th Case******
		commonUtility.tap(workOrderPo.getLnkFilters());
		Thread.sleep(CommonUtility.iMedSleep);
//		System.out.println(workOrderPo.getCheckBoxAccount().isSelected());
		if(workOrderPo.getCheckBoxAccount().isSelected()&&sOSName.equals("ios")) {
			commonUtility.tap(workOrderPo.getcheckBoxAccount01(),20,20);
		}       
		else {
			commonUtility.tap(workOrderPo.getcheckBoxAccount02());
		}
		commonUtility.tap(workOrderPo.getBtnApply());
		commonUtility.lookupSearchOnly(sSearchTxt);
		Thread.sleep(CommonUtility.iHighSleep);
		contactList = workOrderPo.getcontactListInLkp();
//		System.out.println("All Accounts "+contactList.size());
//		commonsUtility.tap(workOrderPo.getLnkLookupCancel());
		String sAllCon = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Name+=\'"+sSearchTxt+"\'", "totalSize");
//		System.out.println("All Accounts fetched from DB "+sAllCon);
		if(sOSName.equals("android")) {
			Assert.assertEquals(commonUtility.getHeaderCount(workOrderPo), Integer.parseInt(sAllCon));
		}
		else {
			Assert.assertEquals(contactList.size(), Integer.parseInt(sAllCon));
		}
		
		
	}
}
