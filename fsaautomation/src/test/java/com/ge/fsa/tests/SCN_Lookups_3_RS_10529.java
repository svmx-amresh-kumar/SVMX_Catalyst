package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.WorkOrderPO;

public class SCN_Lookups_3_RS_10529 extends BaseLib {
	
	String sTestCaseID = "RS_10529";
	String sScriptName = "Scenario_10529";
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
//	String sProcessName = "Auto_Reg_10529";
	String sCountry = "Italy";
	String sProcessName = "Auto_Regression_10529";
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10529() throws Exception{
		
		commonsPo.execSahi(genericLib, sScriptName, sTestCaseID);
		
		// Create Product without Description
		String sProductName = "AshProd";
		String sProdCount = restServices.restGetSoqlValue("SELECT+Count()+from+Product2+Where+name+=\'"+sProductName+"\'", "totalSize");
		if(Integer.parseInt(sProdCount)==0) {
			restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		}
		
		// Create Product with Description
		String sProductName01 = "HarryProd";
		String sProdCount01 = restServices.restGetSoqlValue("SELECT+Count()+from+Product2+Where+name+=\'"+sProductName01+"\'", "totalSize");
		if(Integer.parseInt(sProdCount01)==0) {
			restServices.restCreate("Product2?","{\"Name\":\""+sProductName01+"\",\"Description\":\"HarryProd Desc\" }");
		}
		
		// Create Location without Country
		String sLocName = "HarryLoc";
		String sLocCount = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName+"\'", "totalSize");
		if(Integer.parseInt(sLocCount)==0) {
		restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName+"\"}");
		}
		
		// Create Location with Country
		String sLocName01 = "AshLoc";
		String sLocCount01 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName01+"\'", "totalSize");
		if(Integer.parseInt(sLocCount01)==0) {
			restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName01+"\",\"SVMXC__Country__c\": \""+sCountry+"\"}");
		}
		

		
		
		
		
		
		//************************************************************************************
		

//        // Create Contact with Account and Last Name
//        String sAccName = commonsPo.generaterandomnumber("AutoAcc");
//		String sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccName+"\" }");
//     	String sLastName = "HCSContact";
//     	restServices.restCreate("Contact?","{\"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccId+"\"}");
////     	// Create Contact without Account and with Last Name
//     	String sLastName01 = "HarryContact";
//     	restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLastName01+"\"}");
     	
     	String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
     	
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo); // To get the work Order and Products
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.configSync(commonsPo); // To get the SFM Wizard
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearch, sWOName, sProcessName);
		commonsPo.tap(workOrderPo.getElePartLnk());
		List<WebElement> productList = new ArrayList<WebElement>();
		productList = workOrderPo.getProductListInLkp();
		String sProductCount = restServices.restGetSoqlValue("SELECT+Count()+from+Product2+Where+Description+=null", "totalSize");
		System.out.println(productList.size());
		System.out.println(sProductCount);
		//		Assert.assertEquals(productList.size(), Integer.parseInt(sProductCount)); //Step 1, Have commented out step 1 validation since the number of records products exceeds 250 and on fsa maximum 250 items are displayed
		commonsPo.tap(commonsPo.getElesearchTap());
		commonsPo.getElesearchTap().clear();
		commonsPo.getElesearchTap().sendKeys(sProductName);
		commonsPo.tap(commonsPo.getElesearchButton());
		String sProductOptn = workOrderPo.getLblLookupOptns().getText();
		System.out.println(sProductOptn);
		assertEquals(sProductOptn, sProductName); // Step 2
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		commonsPo.longPress(workOrderPo.getProblemDescription());
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.tap(workOrderPo.geteleProblemDesc_Edit_WorkOrder());
		workOrderPo.geteleProblemDesc_Edit_WorkOrderPopup().sendKeys("HarryProd Desc");
	//	workOrderPo.geteleProblemDesc_Edit_WorkOrderPopup().sendKeys(Keys.ENTER);
		commonsPo.tap(workOrderPo.getEleUpdateLnk()); 
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.tap(workOrderPo.getElePartLnk());
		commonsPo.tap(commonsPo.getElesearchTap());
		commonsPo.getElesearchTap().clear();
		commonsPo.getElesearchTap().sendKeys(sProductName01);
		commonsPo.tap(commonsPo.getElesearchButton());
		String sProductOptn01 = workOrderPo.getLblLookupOptns().getText();
		System.out.println(sProductOptn01);
		assertEquals(sProductOptn01, sProductName01); //Covers Step 3
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		commonsPo.tap(workOrderPo.getlblSite()); 
		commonsPo.tap(commonsPo.getElesearchTap()); 
		commonsPo.getElesearchTap().clear(); 
		commonsPo.getElesearchTap().sendKeys(sLocName); 
		commonsPo.tap(commonsPo.getElesearchButton()); 
		String sLocationOptn01 = workOrderPo.getLblLookupOptns().getText(); 
		System.out.println(sLocationOptn01); 
		assertEquals(sLocationOptn01, sLocName); //Covers Step 4
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.setPickerWheelValue(workOrderPo.geteleCountry_Edit_Lst(), sCountry);	
		commonsPo.tap(workOrderPo.getlblSite()); 
		List<WebElement> locList = new ArrayList<WebElement>();
		locList = workOrderPo.getLocListInLkp();
		String sLocItaCount = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+SVMXC__Country__c+='"+sCountry+"'", "totalSize");
		Assert.assertEquals(locList.size(), Integer.parseInt(sLocItaCount)); //Step 5
//		commonsPo.tap(commonsPo.getElesearchTap()); 
//		commonsPo.getElesearchTap().clear(); 
//		commonsPo.getElesearchTap().sendKeys(sLocName01); 
//		commonsPo.tap(commonsPo.getElesearchButton()); 
//		String sLocationOptn02 = workOrderPo.getLblLookupOptns().getText(); 
//		System.out.println(sLocationOptn02); 
//		assertEquals(sLocationOptn02, sLocName01); //Covers Step 5
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		Thread.sleep(genericLib.iMedSleep);
//		workOrderPo.getLblContact().click();
		commonsPo.tap(workOrderPo.getTxtContact()); 
		List<WebElement> contactList = new ArrayList<WebElement>();
		contactList = workOrderPo.getcontactListInLkp();
		System.out.println("contactList "+contactList.size());
		String sConWoAcc = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Id+=null", "totalSize");
		Assert.assertEquals(contactList.size(), Integer.parseInt(sConWoAcc),"Valid Failure: FSA-2528"); //Covers Step 7-Valid Failure: FSA-2528

	}

}
