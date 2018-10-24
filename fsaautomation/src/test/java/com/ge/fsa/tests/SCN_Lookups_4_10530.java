package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_Lookups_4_10530 extends BaseLib {
	
	String sTestID = null;
	String[] sProdArr = {"P1", "P2", "P3", "P4"};
	String sProdName = null;
	
private void preRequisite() throws Exception {
		
		// Invoking Sahi Script.
		genericLib.executeSahiScript("appium/Scenario_10530.sah", sTestID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}
	
	@Test
	public void RS_10530() throws Exception {
		sProdName = "P2";
//		preRequisite();
		loginHomePo.login(commonsPo, exploreSearchPo);	
//		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00002005", "10530_lkp_proc1");
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(workOrderPo.getLblProduct());
		for(int i=0;i<sProdArr.length;i++) {
			commonsPo.lookupSearchOnly(sProdArr[i].toString());
			Assert.assertTrue(workOrderPo.getEleProds(sProdArr[i].toString()).isDisplayed());
		}
		commonsPo.tap(workOrderPo.getLnkFilters());
		Thread.sleep(GenericLib.iLowSleep);
//		System.out.println(workOrderPo.getCheckBoxAccount().isSelected());
			commonsPo.tap(workOrderPo.getCheckBoxUserTrunk(),20,20);
			commonsPo.tap(workOrderPo.getBtnApply());
//			Assert.assertTrue(workOrderPo.getEleProds("P1").isDisplayed());
			commonsPo.tap(workOrderPo.getLnkLookupCancel());
			workOrderPo.addParts(commonsPo, workOrderPo, sProdName);
			workOrderPo.getLblChildPart(sProdName).click();
			commonsPo.tap(workOrderPo.getLblChildPart(sProdName));
			commonsPo.tap(workOrderPo.getLblPart());
			commonsPo.lookupSearchOnly("P4");
//			Assert.assertTrue(workOrderPo.getEleProds("P4").isDisplayed());
		
	}

}
