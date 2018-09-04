package com.ge.fsa.tests;

import java.io.IOException;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;

public class Scenario10528Test extends BaseLib {
	
	@Test
	public void Scenario10528Test() throws IOException {
		
		//Create IB
		String sIbName = commonsPo.generaterandomnumber("IB");
		String sIbNum = commonsPo.generaterandomnumber("IBNum");
		String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \"Acc04092018143244\", \"Name\": \""+sIbName+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sIbNum+"\",\"SVMXC__Country__c\": \"India\"}");
		System.out.println("IB Id is "+sIbId);
		
		//Create Location
		String sLocName = commonsPo.generaterandomnumber("Loc");
		String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName+"\", \"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}");
        System.out.println("Loc Id IS "+sLocId);
		
		//Create Product
		String sProdName = commonsPo.generaterandomnumber("prod");
		String sProdId = restServices.restCreate("Product2?","{\"Name\": \""+sProdName+"\"}");
		System.out.println("Product Id IS "+sProdId);
		
		//Create IB
		String sIbName1 = commonsPo.generaterandomnumber("IB");
		String sIbNum1 = commonsPo.generaterandomnumber("IBNum");
		String sIbId1 = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \"Acc04092018143244\", \"Name\": \""+sIbName1+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sIbNum1+"\",\"SVMXC__Country__c\": \"Italy\",\"SVMXC__City__c\": \"Maranello\",\"SVMXC__Zip__c\":\"51203\",\"SVMXC__Contact__c\":\"Jody33Scheckter\",\"SVMXC__Product__c\":\""+sProdId+"\",\"SVMXC__Site__c\":\""+sLocId+"\",\"SVMXC__Top_Level__c\":\""+sIbId+"\"}");
		System.out.println("New IB Id is "+sIbId1);
		
	}
}
