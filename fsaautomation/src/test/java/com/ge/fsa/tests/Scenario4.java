package com.ge.fsa.tests;

import java.io.IOException;

import org.json.JSONArray;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;

public class Scenario4 extends BaseLib{
	
	@Test
	public void test1() throws IOException {
		JSONArray sJsonArrayWo = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");

			String sWORecordID = restServices.getJsonValue(sJsonArrayWo, "id");
			
			String sJArr = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "name");
	
	System.out.println("WO no ="+sJArr);
	}

}
