package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_Lookups_4_10530 extends BaseLib {
	
	String sTestCaseID = "RS_10530";
//	String[] sProdArr = {"P1", "P2", "P3", "P4"};
	String sProdName = null;
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
	String sProcessName = "Auto_10530_Regression";
//	String sProcessName = "Auto_Regression_10530";
	String sScriptName = "Scenario_10530";
	
	
	@Test//(retryAnalyzer=Retry.class)
	public void RS_10530() throws Exception {
		commonsPo.execSahi(genericLib, sScriptName, sTestCaseID);
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		sProdName = "P1_HCS";
	//	preRequisite();
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearch, sWOName, sProcessName);
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(workOrderPo.getLblProduct());
		List<WebElement> prodList = new ArrayList<WebElement>();
		prodList = workOrderPo.getProductListInLkp();
		System.out.println(prodList.size()); //Scenario 1
		// Add Query
//		for(int i=0;i<sProdArr.length;i++) {
//			commonsPo.lookupSearchOnly(sProdArr[i].toString());
//			Assert.assertTrue(workOrderPo.getEleProds(sProdArr[i].toString()).isDisplayed());
//		}
		commonsPo.tap(workOrderPo.getLnkFilters());
		Thread.sleep(GenericLib.iLowSleep);
//		System.out.println(workOrderPo.getCheckBoxAccount().isSelected());
			commonsPo.tap(workOrderPo.getCheckBoxUserTrunk(),20,20);
			commonsPo.tap(workOrderPo.getBtnApply());
			Assert.assertTrue(workOrderPo.getEleProds(sProdName).isDisplayed()); //Scenario 2
			// Call edit Sahi_Process
			commonsPo.tap(workOrderPo.getLnkLookupCancel());
			workOrderPo.addParts(commonsPo, workOrderPo, sProdName);
			workOrderPo.getLblChildPart(sProdName).click();
			commonsPo.tap(workOrderPo.getLblChildPart(sProdName));
			commonsPo.tap(workOrderPo.getLblPart());
			commonsPo.lookupSearchOnly("P4");
//			select SVMXC__Product_Name__c from SVMXC__Installed_Product__c where RecordType.name='IB002' and SVMXC__Status__c='shipped'
		
	}

}
