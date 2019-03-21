package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.Retry;

public class SCN_Lookups_4_10530 extends BaseLib {
	
	String sTestCaseID = "RS_10530";
//	String[] sProdArr = {"P1", "P2", "P3", "P4"};
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
	String sProcessName = "Auto_10530_Regression";
//	String sProcessName = "Auto_Regression_10530";
	String sScriptName = "Scenario_10530";
	String sSearchTxt = "HarryProd";
	
	
	@Test//(retryAnalyzer=Retry.class)
	public void RS_10530() throws Exception {
		
		//**********Create Processes on Sahi**********
	//	commonsPo.execSahi(genericLib, sScriptName, sTestCaseID);
		   
		//**********Create Product1**********
		String sProdName1 = "P1_10530";
		String sProdCount1 = restServices.restGetSoqlValue("SELECT+Count()+from+product2+Where+name+=\'"+sProdName1+"\'", "totalSize");
		if(Integer.parseInt(sProdCount1)==0) {
			String sProdId = restServices.restCreate("Product2?","{\"Name\": \""+sProdName1+"\"}");
			System.out.println("Product Id is "+sProdId);
		}
		
		//**********Create Product2**********
		String sProdName2 = "P2_10530";
		String sProdCount2 = restServices.restGetSoqlValue("SELECT+Count()+from+product2+Where+name+=\'"+sProdName2+"\'", "totalSize");
		if(Integer.parseInt(sProdCount2)==0) {
			String sProdId = restServices.restCreate("Product2?","{\"Name\": \""+sProdName2+"\"}");
			System.out.println("Product Id is "+sProdId);
		}
		//**********Create Product3**********
		String sProdName3 = "P3_10530";
		String sProdCount3 = restServices.restGetSoqlValue("SELECT+Count()+from+product2+Where+name+=\'"+sProdName3+"\'", "totalSize");
		if(Integer.parseInt(sProdCount3)==0) {
			String sProdId = restServices.restCreate("Product2?","{\"Name\": \""+sProdName3+"\"}");
			System.out.println("Product Id is "+sProdId);
		}
		
		//**********Create Product4**********
		String sProdName4 = "P4_10530";
		String sProdCount4 = restServices.restGetSoqlValue("SELECT+Count()+from+product2+Where+name+=\'"+sProdName4+"\'", "totalSize");
		if(Integer.parseInt(sProdCount4)==0) {
			String sProdId = restServices.restCreate("Product2?","{\"Name\": \""+sProdName4+"\"}");
			System.out.println("Product Id is "+sProdId);
		}
		
		//**********Create Location1**********
		String sLocName1 = "Loc1_10530";
		String sLocCount1 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName1+"\'", "totalSize");
		if(Integer.parseInt(sLocCount1)==0) {
		String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName1+"\", \"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}");
        System.out.println("Loc Id is "+sLocId);
		}
		
		//**********Create Location2**********
		String sLocName2 = "InventoryLoc2_10530";
		String sLocCount2 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName2+"\'", "totalSize");
		String sLocId1 = "";
		if(Integer.parseInt(sLocCount2)==0) {
		sLocId1 = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName2+"\", \"SVMXC__Street__c\": \"Paris\",\"SVMXC__Country__c\": \"France\"}");
        System.out.println("Loc Id is "+sLocId1);
		}
		else {
			sLocId1=restServices.restGetSoqlValue("SELECT+Id+from+SVMXC__Site__c+Where+name+=\'"+sLocName2+"\'", "Id");
			System.out.println("Loc id is "+sLocId1);
		}
		
		
		//**********Fetch Technician ID**********
		String sTechId = restServices.restGetSoqlValue("SELECT+Id+from+SVMXC__Service_Group_Members__c+Where+name+=\'Auto_Tech\'", "Id");
		System.out.println("Tech id is "+sTechId);
		
		//**********Update Inventory Location of Technician**********
		String sObjName= "SVMXC__Service_Group_Members__c";
		String sObJson = "{\"SVMXC__Inventory_Location__c\":\""+sLocId1+"\"}";
		restServices.restUpdaterecord(sObjName, sObJson, sTechId);
		
		//**********Create IB1**********
		String sIBName1 = "IB1_10530";
		String sIBCount1 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Installed_Product__c+Where+name+=\'"+sIBName1+"\'", "totalSize");
		if(Integer.parseInt(sIBCount1)==0) {
			String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Product__r\":{\"Name\": \""+sProdName1+"\"}, \"Name\": \""+sIBName1+"\",\"SVMXC__Status__c\":\"installed\",\"RecordType\":{\"Name\":\"IB001\"}}");
			System.out.println("IB Id is "+sIbId);
		}
		
		//**********Create IB2**********
		String sIBName2 = "IB2_10530";
		String sIBCount2 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Installed_Product__c+Where+name+=\'"+sIBName2+"\'", "totalSize");
		if(Integer.parseInt(sIBCount2)==0) {
			String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Product__r\":{\"Name\": \""+sProdName2+"\"}, \"Name\": \""+sIBName2+"\",\"SVMXC__Status__c\":\"installed\",\"RecordType\":{\"Name\":\"IB002\"}}");
			System.out.println("IB Id is "+sIbId);
		}
		
		//**********Create IB3**********
		String sIBName3 = "IB3_10530";
		String sIBCount3 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Installed_Product__c+Where+name+=\'"+sIBName3+"\'", "totalSize");
		if(Integer.parseInt(sIBCount3)==0) {
			String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Product__r\":{\"Name\": \""+sProdName3+"\"}, \"Name\": \""+sIBName3+"\",\"SVMXC__Status__c\":\"shipped\",\"RecordType\":{\"Name\":\"IB003\"}}");
			System.out.println("IB Id is "+sIbId);
		}
		
		//**********Create IB4**********
		String sIBName4 = "IB4_10530";
		String sIBCount4 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Installed_Product__c+Where+name+=\'"+sIBName4+"\'", "totalSize");
		if(Integer.parseInt(sIBCount4)==0) {
			String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Product__r\":{\"Name\": \""+sProdName4+"\"}, \"Name\": \""+sIBName4+"\",\"SVMXC__Status__c\":\"shipped\",\"RecordType\":{\"Name\":\"IB002\"}}");
			System.out.println("IB Id is "+sIbId);
		}
		
		//**********Product Stock1**********
		String sprodStkName1 ="STK-00000006";
		String sprodStkCount1 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Product_Stock__c+Where+name+=\'"+sprodStkName1+"\'", "totalSize");
		if(Integer.parseInt(sprodStkCount1)==0) {
			String sprodStkId1 = restServices.restCreate("SVMXC__Product_Stock__c?","{\"SVMXC__Product__r\":{\"Name\": \""+sProdName1+"\"},\"SVMXC__Location__r\":{\"Name\":\""+sLocName1+"\"}}");
			System.out.println("Prod Stk Id is "+sprodStkId1);
		}
		
		//**********Product Stock2**********
		String sprodStkName2 ="STK-00000007";
		String sprodStkCount2 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Product_Stock__c+Where+name+=\'"+sprodStkName2+"\'", "totalSize");
		if(Integer.parseInt(sprodStkCount2)==0) {
		String sprodStkId2 = restServices.restCreate("SVMXC__Product_Stock__c?","{\"SVMXC__Product__r\":{\"Name\": \""+sProdName2+"\"},\"SVMXC__Location__r\":{\"Name\":\""+sLocName2+"\"}}");
		System.out.println("Prod Stk Id is "+sprodStkId2);
		}
		
		//**********Product Stock3**********
		String sprodStkName3 ="STK-00000008";
		String sprodStkCount3 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Product_Stock__c+Where+name+=\'"+sprodStkName3+"\'", "totalSize");
		if(Integer.parseInt(sprodStkCount3)==0) {
		String sprodStkId3 = restServices.restCreate("SVMXC__Product_Stock__c?","{\"SVMXC__Product__r\":{\"Name\": \""+sProdName3+"\"},\"SVMXC__Location__r\":{\"Name\":\""+sLocName2+"\"}}");
		System.out.println("Prod Stk Id is "+sprodStkId3);
		}
		
		// {"SVMXC__Product__r":{"Name": "HarryProd"},"SVMXC__Location__r":{"Name":"Loc16012019111519"}}
		
		//**********Create Work Order**********
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		
		//**********Login to FSA**********
		loginHomePo.login(commonsPo, exploreSearchPo);	
		
		//**********Perform a Data Sync**********
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		//**********Perform a Config Sync**********
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		//**********Launch Process**********
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearch, sWOName, sProcessName);
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(workOrderPo.getLblProduct());
		System.out.println("I am Here--------");
		Thread.sleep(60000);
		commonsPo.lookupSearchOnly(sSearchTxt); //As validating all products fails when the count exceeds 250 due to FSA limitation trimming the search result by searching for the product
		Thread.sleep(GenericLib.iMedSleep);
		List<WebElement> prodList = new ArrayList<WebElement>();
		prodList = workOrderPo.getProductListInLkp();
//		System.out.println(prodList.size());
//		String sProdCount = restServices.restGetSoqlValue("SELECT+Count()+from+product2+Where+Name+LIKE+'%"+sSearchTxt+"%'", "totalSize");
		String sProdCount = restServices.restGetSoqlValue("SELECT+Count()+from+product2+Where+Name+=\'"+sSearchTxt+"\'", "totalSize");
//		System.out.println(sProdCount);
		Assert.assertEquals(prodList.size(),Integer.parseInt(sProdCount)); //Scenario 1
		commonsPo.tap(workOrderPo.getBtnReset());
		Thread.sleep(GenericLib.iHighSleep);
		commonsPo.tap(workOrderPo.getLnkFilters());
		Thread.sleep(GenericLib.iLowSleep);
			commonsPo.tap(workOrderPo.getCheckBoxUserTrunk(),20,20);
			commonsPo.tap(workOrderPo.getBtnApply());
		    String soqlquery="Select+Name+from+product2+where+id+in+(Select+SVMXC__Product__c+from+SVMXC__Product_Stock__c+where+SVMXC__Location__c=\'a2O3D000000KGuyUAG\'+and+SVMXC__Product__c!=null)";
		    JSONArray jSonArr = restServices.restGetSoqlJsonArray(soqlquery);
		    ArrayList<String> sArrOfProd = restServices.getJsonArr(jSonArr, "Name");
		    prodList = workOrderPo.getProductListInLkp();
//		    System.out.println(prodList);
//		    System.out.println(prodList.size());
//		    System.out.println(sArrOfProd);
		    ArrayList<String>sProdList = new ArrayList<String>();
		    for(WebElement we:prodList) {
		    	sProdList.add(we.getText());
		    }
//		    System.out.println(sProdList);
		    Collections.sort(sArrOfProd);
		    Collections.sort(sProdList);
		    Assert.assertTrue(sArrOfProd.equals(sProdList)); //Scenario 2
			commonsPo.tap(workOrderPo.getLnkLookupCancel());
			commonsPo.tap(workOrderPo.getElePartLnk());
			soqlquery="select+SVMXC__Product_Name__c+from+SVMXC__Installed_Product__c+where+RecordType.name=\'IB002\'+and+SVMXC__Status__c=\'shipped\'";
			jSonArr = restServices.restGetSoqlJsonArray(soqlquery);
		    sArrOfProd = restServices.getJsonArr(jSonArr, "SVMXC__Product_Name__c");
		    prodList = workOrderPo.getProductListInLkp();
//		    System.out.println(prodList);
//		    System.out.println(prodList.size());
//		    System.out.println(sArrOfProd);
		    sProdList = new ArrayList<String>();     	
		    for(WebElement we:prodList) {
		    	sProdList.add(we.getText());
		    }
//		    System.out.println(sProdList);
		    Collections.sort(sArrOfProd);
		    Collections.sort(sProdList);
		    Assert.assertTrue(sArrOfProd.equals(sProdList)); //Scenario 4
		    Thread.sleep(GenericLib.iHighSleep);
			commonsPo.tap(workOrderPo.getLnkFilters());
			Assert.assertTrue(workOrderPo.getChkBoxComplexFilter().isSelected()); //Scenario 5
	}
	


}
