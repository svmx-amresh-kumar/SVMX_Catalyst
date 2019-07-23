package com.ge.fsa.tests.phone;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.json.JSONArray;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;

import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_WorkOrderPO;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_SCN_10530 extends BaseLib {
	
	String sTestCaseID = "RS_10530";
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
	String sProcessName = "Auto_10530_Regression";
	String sScriptName = "appium/Scenario_10530.sah";
	String sSearchTxt = "HarryProd";
	String sScriptName1 = "appium/Scenario_10530_edit.sah";
	Boolean bProcessCheckResult;
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10530() throws Exception {
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6442");
		}else {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6751");

		}
		
		//**********Create Processes on Sahi**********
//		commonUtility.executeSahiScript(sScriptName);
		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sProcessName, sScriptName,sTestCaseID);
			   
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
			String sprodStkName1 ="STK-00000009";
			String sprodStkCount1 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Product_Stock__c+Where+name+=\'"+sprodStkName1+"\'", "totalSize");
			if(Integer.parseInt(sprodStkCount1)==0) {
				String sprodStkId1 = restServices.restCreate("SVMXC__Product_Stock__c?","{\"SVMXC__Product__r\":{\"Name\": \""+sProdName1+"\"},\"SVMXC__Location__r\":{\"Name\":\""+sLocName1+"\"}}");
				System.out.println("Prod Stk Id is "+sprodStkId1);
			}
			
			//**********Product Stock2**********
			String sprodStkName2 ="STK-00000010";
			String sprodStkCount2 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Product_Stock__c+Where+name+=\'"+sprodStkName2+"\'", "totalSize");
			if(Integer.parseInt(sprodStkCount2)==0) {
			String sprodStkId2 = restServices.restCreate("SVMXC__Product_Stock__c?","{\"SVMXC__Product__r\":{\"Name\": \""+sProdName2+"\"},\"SVMXC__Location__r\":{\"Name\":\""+sLocName2+"\"}}");
			System.out.println("Prod Stk Id is "+sprodStkId2);
			}
			
			//**********Product Stock3**********
			String sprodStkName3 ="STK-00000011";
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
			
			ph_LoginHomePo.login(commonUtility, ph_MorePo);
			
			ph_MorePo.syncData(commonUtility);
//			ph_MorePo.configSync(commonUtility, ph_CalendarPo);
			ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
			ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearch, sWOName, sProcessName);
			// ************Start of Scenario 1****************
			commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getLblProduct());
			ph_WorkOrderPo.getElelookupsearch().sendKeys(sSearchTxt+ "\n");
			Assert.assertTrue(ph_WorkOrderPo.getLkpEle(sSearchTxt).isDisplayed());
			// ************End of Scenario 1******************
			// ************Start of Scenario 2****************
			Thread.sleep(3000);
			ph_WorkOrderPo.getElelookupsearch().clear();
			ph_WorkOrderPo.getLnkFilter().click();
			ph_WorkOrderPo.getChkboxFilter().click();
			ph_WorkOrderPo.getBtnSeeResults().click();
			String soqlquery="Select+Name+from+product2+where+id+in+(Select+SVMXC__Product__c+from+SVMXC__Product_Stock__c+where+SVMXC__Location__c=\'"+sLocId1+"\'+and+SVMXC__Product__c!=null)";
		    JSONArray jSonArr = restServices.restGetSoqlJsonArray(soqlquery);
		    ArrayList<String> sArrOfProd = restServices.getJsonArr(jSonArr, "Name");
		    List<WebElement> prodList = new ArrayList<WebElement>();
			prodList = ph_WorkOrderPo.getLkpLst();
		    ArrayList<String>sProdList = new ArrayList<String>();
		    for(WebElement we:prodList) {
		    	sProdList.add(we.getText());
		    }
		    Collections.sort(sArrOfProd);
		    Collections.sort(sProdList);
		    Assert.assertTrue(sArrOfProd.equals(sProdList));
			// ************End of Scenario 2******************
			// ************Start of Scenario 3****************
			ph_WorkOrderPo.getBtnClose().click();
			commonUtility.gotToTabHorizontal(ph_WorkOrderPo.getStringParts());
			ph_WorkOrderPo.getElePartLnk().click();
			soqlquery="select+SVMXC__Product_Name__c+from+SVMXC__Installed_Product__c+where+RecordType.name=\'IB002\'+and+SVMXC__Status__c=\'shipped\'";
			jSonArr = restServices.restGetSoqlJsonArray(soqlquery);
		    sArrOfProd = restServices.getJsonArr(jSonArr, "SVMXC__Product_Name__c");
		    prodList = ph_WorkOrderPo.getLkpLst();
		    sProdList = new ArrayList<String>();     	
		    for(WebElement we:prodList) {
		    	sProdList.add(we.getText());
		    }
		    Collections.sort(sArrOfProd);
		    Collections.sort(sProdList);
		    System.out.println(sArrOfProd);
		    System.out.println(sProdList);
		    Assert.assertTrue(sArrOfProd.equals(sProdList));
		    // ************End of Scenario 3******************
		    // ************Start of Scenario 4****************
			ph_WorkOrderPo.getLnkFilter().click();
			if(sOSName.equalsIgnoreCase("ios")) {
				Assert.assertTrue(ph_WorkOrderPo.getChkboxFilter().getAttribute("name").toLowerCase().contains("checked"));
			}
			else {
				Assert.assertTrue(ph_WorkOrderPo.getChkboxFilter().getAttribute("content-desc").toLowerCase().contains("checked"));	
			}
			// ************End of Scenario 4****************
			
			/*			
			// *************Edit Sahi Process***************
			commonUtility.executeSahiScript(sScriptName1);
			// ************Start of Scenario 5****************
			ph_WorkOrderPo.getBtnFltrClose().click();
			ph_WorkOrderPo.getBtnClose().click();
			Thread.sleep(3000); //Included to avoid Stale Element Exception
			ph_WorkOrderPo.getBtnClose().click();
			Thread.sleep(3000);
			ph_MorePo.configSync(commonUtility, ph_CalendarPo);
			ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearch, sWOName, sProcessName);
			commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getLblProduct());
			ph_WorkOrderPo.getLnkFilter().click();
			System.out.println(ph_WorkOrderPo.getChkboxFilter().getAttribute("clickable"));
			Assert.assertEquals(ph_WorkOrderPo.getChkboxFilter().getAttribute("clickable"), "false");
			// ************End of Scenario 5****************
			 
			 */
} 			

}


