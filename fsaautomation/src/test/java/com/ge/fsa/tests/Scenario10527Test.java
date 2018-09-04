package com.ge.fsa.tests;

import java.io.IOException;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;

public class Scenario10527Test extends BaseLib {
	
	@Test
	public void Scenario10527Test() throws IOException {
		
		// Create Account
		String sAccId = restServices.restCreate("Account?","{\"Name\": \"Ferrari\" }");
		System.out.println(sAccId);
		String sAccId1 = restServices.restCreate("Account?","{\"Name\": \"McLaren\" }");
		System.out.println(sAccId1);
		String sAccId2 = restServices.restCreate("Account?","{\"Name\": \"Williams\" }");
		System.out.println(sAccId2);
		String sAccId3 = restServices.restCreate("Account?","{\"Name\": \"Renault\" }");
		System.out.println(sAccId3);
		String sAccId4 = restServices.restCreate("Account?","{\"Name\": \"Red Bull\" }");
		System.out.println(sAccId4);
		
		//Create Contact
		String sConId = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId+"\",\"Name\": \"Jody Scheckter\"}");
		System.out.println(sConId);
		String sConId1 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId+"\",\"Name\": \"John Surtees\"}");
		System.out.println(sConId1);
		String sConId2 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId+"\",\"Name\": \"Michael Schumacher\"}");
		System.out.println(sConId2);
		String sConId3 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId+"\",\"Name\": \"Kimi Raikkonen\"}");
		System.out.println(sConId3);
		String sConId4 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId4+"\",\"Name\": \"Sebastian Vettel\"}");
		System.out.println(sConId4);
		String sConId5 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId3+"\",\"Name\": \"Fernando Alonso\"}");
		System.out.println(sConId5);
		String sConId6 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId1+"\",\"Name\": \"Mika Hakkinen\"}");
		System.out.println(sConId6);
		String sConId7 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId1+"\",\"Name\": \"Ayrton Senna\"}");
		System.out.println(sConId7);
		String sConId8 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId1+"\",\"Name\": \"James Hunt\"}");
		System.out.println(sConId8);
		String sConId9 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId2+"\",\"Name\": \"Damon Hill\"}");
		System.out.println(sConId9);
		String sConId10 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId2+"\",\"Name\": \"Nigel Mansell\"}");
		System.out.println(sConId10);
		String sConId11 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId2+"\",\"Name\": \"Jacques Villeneuve\"}");
		System.out.println(sConId11);
		String sConId12 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId2+"\",\"Name\": \"Alan Jones\"}");
		System.out.println(sConId12);
		String sConId13 = restServices.restCreate("Contact?","{\"SVMXC__Account__c\":\""+sAccId2+"\",\"Name\": \"Keke Rosberg\"}");
		System.out.println(sConId13);
		String sConId14 = restServices.restCreate("Contact?","{\"Name\": \"Stirling Moss\"}");
		System.out.println(sConId14);
		String sConId15 = restServices.restCreate("Contact?","{\"Name\": \"David Coulthard\"}");
		System.out.println(sConId15);
		//Create Work Order
		String sWoID  = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		System.out.println(sWoID);
	}
	
	
	

}
