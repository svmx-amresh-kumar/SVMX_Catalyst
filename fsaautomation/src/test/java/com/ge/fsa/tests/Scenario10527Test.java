package com.ge.fsa.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;

public class Scenario10527Test extends BaseLib {
	
	@Test
	public void Scenario10527Test() throws IOException, InterruptedException {
		
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
		
		//Create Contact
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
//		String sWoID  = restServices.restCreate("SVMXC__Service_Order__c?","{}");
//		System.out.println("Wo ID "+sWoID);
		String sProdName = "a1";
		loginHomePo.login(commonsPo, exploreSearchPo);	
//		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00002068", "AutoReg10529");
		//******Validate 1st Case******
		commonsPo.tap(workOrderPo.getLblContact());
		List<WebElement> contactList = new ArrayList<WebElement>();
		contactList = workOrderPo.getcontactListInLkp();
		System.out.println(contactList.size());
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		//******Validate 2nd Case******
		commonsPo.tap(workOrderPo.getLblAccount());
		commonsPo.lookupSearch("Acme");
		commonsPo.tap(workOrderPo.getLblContact());
		contactList = workOrderPo.getcontactListInLkp();
		System.out.println(contactList.size());
		//******Validate 3rd Case******
		commonsPo.tap(workOrderPo.getLnkFilters());
		Thread.sleep(GenericLib.iLowSleep);
//		System.out.println(workOrderPo.getCheckBoxAccount().isSelected());
		if(workOrderPo.getCheckBoxAccount().isSelected()) {
			commonsPo.tap(workOrderPo.getcheckBoxAccount01(),20,20);
		}
		commonsPo.tap(workOrderPo.getBtnApply());
		contactList = workOrderPo.getcontactListInLkp();
		System.out.println(contactList.size());
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		//******Validate 4th Case******
		workOrderPo.addParts(commonsPo, workOrderPo, sProdName);
		workOrderPo.getLblChildPart(sProdName).click();
		commonsPo.tap(workOrderPo.getLblChildPart(sProdName));
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(workOrderPo.getLblPartContact());
		contactList = workOrderPo.getcontactListInLkp();
		System.out.println(contactList.size());
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		//******Validate 5th Case******
		commonsPo.tap(workOrderPo.getLblChildPart(sProdName));
		commonsPo.tap(workOrderPo.getLblPartAccount());
		commonsPo.lookupSearch("Acme");
		commonsPo.tap(workOrderPo.getLblPartContact());
		contactList = workOrderPo.getcontactListInLkp();
		System.out.println(contactList.size());
		//******Validate 6th Case******
		commonsPo.tap(workOrderPo.getLnkFilters());
		System.out.println("Waiting");
		Thread.sleep(GenericLib.iMedSleep);
//		System.out.println(workOrderPo.getCheckBoxAccount().isSelected());
		if(workOrderPo.getCheckBoxAccount().isSelected()) {
			commonsPo.tap(workOrderPo.getcheckBoxAccount01(),20,20);
		}
		commonsPo.tap(workOrderPo.getBtnApply());
		contactList = workOrderPo.getcontactListInLkp();
		System.out.println(contactList.size());
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		
	}
}
