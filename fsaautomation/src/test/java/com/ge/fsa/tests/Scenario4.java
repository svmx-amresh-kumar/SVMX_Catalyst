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
		JSONArray jsonArrayAcc = restServices.restCreate("SVMXC__Company__c?","{\"Name\": \""+accName+"\" }");
		String accId = restServices.getJsonValue(jsonArrayAcc, "id");
	//	String accName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+accId+"\'", "name");
		
		// Create Location
		String locName = commonsPo.generaterandomnumber("Loc");
		JSONArray jsonArrayLoc = restServices.restCreate("SVMXC__Site__c?","{\"SVMXC__Account__c\": \""+accId+"\", \"Name\": \""+locName+"\", \"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}");
		String locId = restServices.getJsonValue(jsonArrayLoc, "id");
		
		// Create Installed Product
		String ibName = commonsPo.generaterandomnumber("IB");
		String ibNum = commonsPo.generaterandomnumber("IBNum");
		JSONArray jsonArrayIB = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \""+accId+"\", \"Name\": \""+ibName+"\", \"SVMXC__Serial_Lot_Number__c\": \""+ibNum+"\",\"SVMXC__Country__c\": \"India\", \"SVMXC__Site__c\": \""+locId+"\" }");
		String ibId = restServices.getJsonValue(jsonArrayIB, "id");
			
		// Create Work Order
		JSONArray jsonArrayWo = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Company__c\": \""+accId+"\",\"SVMXC__Site__c\": \""+locId+"\",\"SVMXC__Installed_Product__c\":\""+ibId+"\"}");
		String woID = restServices.getJsonValue(jsonArrayWo, "id");
		String woName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+woID+"\'", "name");
		
		loginHomePo.login(commonsPo, exploreSearchPo);
		toolsPo.syncData(commonsPo);
	}

}
