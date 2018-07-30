package com.ge.fsa.tests;

import java.io.IOException;

import org.json.JSONArray;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;

public class Scenario4 extends BaseLib{
	
	@Test
	public void scenario4_piq() throws IOException, InterruptedException {
		
		// Create Account
		String accName = commonsPo.generaterandomnumber("Acc");
		String sAccId = restServices.restCreate("Account?","{\"Name\": \""+accName+"\" }");
		System.out.println("Account Id Is "+sAccId);
		//String accName1 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sAccId+"\'", "name");
		
		// Create Location
		String locName = commonsPo.generaterandomnumber("Loc");
		String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"SVMXC__Account__c\": \""+sAccId+"\", \"Name\": \""+locName+"\", \"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}");
		System.out.println("Loc Id IS "+sLocId);
		
		// Create Installed Product
		String ibName = commonsPo.generaterandomnumber("IB");
		String ibNum = commonsPo.generaterandomnumber("IBNum");
		String ibId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \""+sAccId+"\", \"Name\": \""+ibName+"\", \"SVMXC__Serial_Lot_Number__c\": \""+ibNum+"\",\"SVMXC__Country__c\": \"India\", \"SVMXC__Site__c\": \""+sLocId+"\" }");
		System.out.println("IB id is "+ibId);
		
		// Create Work Order
		String woID  = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Company__c\": \""+sAccId+"\",\"SVMXC__Site__c\": \""+sLocId+"\",\"SVMXC__Component__c\":\""+ibId+"\"}");
		System.out.println("Work Order Id Is "+ woID);
		String woName = restServices.restGetSoqlValue("SELECT+Name+from+SVMXC__Service_Order__c+Where+id+=\'"+woID+"\'", "Name");
		System.out.println("Work Order Name Is "+ woName);
		
//		loginHomePo.login(commonsPo, exploreSearchPo);
//		toolsPo.syncData(commonsPo);
	}

}
