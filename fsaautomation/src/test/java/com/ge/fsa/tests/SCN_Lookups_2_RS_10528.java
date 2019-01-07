package com.ge.fsa.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.WorkOrderPO;

public class SCN_Lookups_2_RS_10528 extends BaseLib {
	
	
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10528() throws Exception {
		
		String sTestID = "RS_10528"; 
		String sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		String sExploreChildSearchTxt = GenericLib.getExcelData(sTestID, sTestID,"ExploreChildSearch");
		String sFieldServiceName = GenericLib.getExcelData(sTestID,sTestID, "ProcessName");
		String sZipCode = "51203";
		String sCountry = GenericLib.getExcelData(sTestID,sTestID, "Country");
		String sCity = GenericLib.getExcelData(sTestID,sTestID, "City");
		String sScriptName = "Scenario_10528";
		
		
//		commonsPo.preReq(genericLib, sScriptName, sTestID);
		

		
		
		
		
		// Create Account
		String sAccName = commonsPo.generaterandomnumber("Acc");
		String sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccName+"\" }");
		System.out.println("Account Id is "+sAccId);
		
		// Creating Contact
		String sRandomNumber = commonsPo.generaterandomnumber("");
		String sFirstName = "auto_contact";
		String sLastName = sRandomNumber;
		String sContactName = sFirstName+" "+sLastName;
		System.out.println(sContactName);
		String sConId = restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccId+"\"}");
		System.out.println("Contact Id is "+sConId);		
		
		//Create IB
		String sIbName = commonsPo.generaterandomnumber("IB");
		String sIbNum = commonsPo.generaterandomnumber("IBNum");
		String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \""+sAccId+"\", \"Name\": \""+sIbName+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sIbNum+"\",\"SVMXC__Country__c\": \"India\"}");
		System.out.println("IB Id is "+sIbId);
		
		//Create Location
		String sLocName = commonsPo.generaterandomnumber("Loc");
		String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName+"\", \"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}");
        System.out.println("Loc Id is "+sLocId);
		
		//Create Product
		String sProdName = commonsPo.generaterandomnumber("prod");
		String sProdId = restServices.restCreate("Product2?","{\"Name\": \""+sProdName+"\"}");
		System.out.println("Product Id is "+sProdId);
		
		//Create IB with Top Level
		String sIbName1 = commonsPo.generaterandomnumber("IB");
		String sIbNum1 = commonsPo.generaterandomnumber("IBNum");
		String sIbId1 = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \""+sAccId+"\", \"Name\": \""+sIbName1+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sIbNum1+"\",\"SVMXC__Country__c\": \""+sCountry+"\",\"SVMXC__City__c\": \""+sCity+"\",\"SVMXC__Zip__c\":\""+sZipCode+"\",\"SVMXC__Contact__c\":\""+sConId+"\",\"SVMXC__Product__c\":\""+sProdId+"\",\"SVMXC__Site__c\":\""+sLocId+"\",\"SVMXC__Top_Level__c\":\""+sIbId+"\"}");
		System.out.println("New IB Id is "+sIbId1);
		
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
//		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
//		System.out.println("WO no ="+sWOName);
		
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		commonsPo.tap(workOrderPo.getLblComponent());
		commonsPo.lookupSearch(sIbName1);
		
		String city = workOrderPo.getTxtCity().getAttribute("value");
		System.out.println("pre-filled city name: "+city);
		Assert.assertEquals(city, sCity);
		
		String contact = workOrderPo.getTxtContact().getAttribute("value");
		System.out.println("pre-filled contact name: "+contact);
		Assert.assertEquals(contact, sContactName);
		
		String product = workOrderPo.getTxtProduct().getAttribute("value");
		System.out.println("pre-filled product name: "+product);
		Assert.assertEquals(product, sProdName);
		
		String country = workOrderPo.getTxtCountry().getAttribute("value");
		System.out.println("pre-filled country name: "+country);
		Assert.assertEquals(country, sCountry);
		
		String topLevel = workOrderPo.getTxtTopLevel().getAttribute("value");
		System.out.println("pre-filled Toplevel name: "+topLevel);
		Assert.assertEquals(topLevel, sIbName);
		
		String location = workOrderPo.getTxtSite().getAttribute("value");
		System.out.println("pre-filled Location name: "+location);
		Assert.assertEquals(location, sLocName);
		
		String zip = workOrderPo.getTxtZip().getAttribute("value");
		System.out.println("pre-filled zip: "+zip);
		Assert.assertEquals(zip, sZipCode);
		
		
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.syncData(commonsPo);
		
		String sDBCity = restServices.restGetSoqlValue("SELECT+SVMXC__City__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__City__c");
		System.out.println(sDBCity);
		Assert.assertEquals(sDBCity, sCity);
		String sDBZip = restServices.restGetSoqlValue("SELECT+SVMXC__Zip__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Zip__c");
		System.out.println(sDBZip);
		Assert.assertEquals(sDBZip, sZipCode);
		String sDBCountry = restServices.restGetSoqlValue("SELECT+SVMXC__Country__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Country__c");
		System.out.println(sDBCountry);
		Assert.assertEquals(sDBCountry, sCountry);
		String sDBContactId = restServices.restGetSoqlValue("SELECT+SVMXC__Contact__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Contact__c");
		System.out.println(sDBContactId);
		String sDBContact = restServices.restGetSoqlValue("SELECT+name+from+Contact+Where+id+=\'"+sDBContactId+"\'", "Name");
		Assert.assertEquals(sDBContact, sContactName);
//		String sDBSite = restServices.restGetSoqlValue("SELECT+SVMXC__Site__r.Name+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "Name");
//		System.out.println(sDBSite);
//		Assert.assertEquals(sDBSite, sLocName);
		String sDBProductId = restServices.restGetSoqlValue("SELECT+SVMXC__Product__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Product__c");
		System.out.println(sDBProductId);
		String sDBProduct = restServices.restGetSoqlValue("SELECT+name+from+Product2+Where+id+=\'"+sDBProductId+"\'", "Name");
		Assert.assertEquals(sDBProduct, sProdName);
//		String sDBTopLevel = restServices.restGetSoqlValue("SELECT+SVMXC__Top_Level__r.Name+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Top_Level__r.Name");
//		System.out.println(sDBTopLevel);
//		Assert.assertEquals(sDBTopLevel, sIbName);
//		JSONArray jSon = restServices.restGetSoqlJsonArray("SELECT+SVMXC__City__c,SVMXC__Country__c,SVMXC__Zip__c,SVMXC__Top_Level__r.Name,SVMXC__Contact__r.Name,SVMXC__Product__r.Name,SVMXC__Site__r.Name+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'");
//		System.out.println("Here it is------::::::: "+jSon.toString()+" :::::::::");

		
		

	}
}
