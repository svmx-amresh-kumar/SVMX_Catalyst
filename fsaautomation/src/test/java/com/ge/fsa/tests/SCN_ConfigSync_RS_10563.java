package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_ConfigSync_RS_10563 extends BaseLib {
	
	@Test//(retryAnalyzer=Retry.class)
	public void RS_10563() throws Exception {
		
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		String sProcessName = "Auto_Reg_10563";
		String sOpDocProcessName = "Auto_OPDOC_10563";
		String sTestCaseID = "RS_10563_configSync_edit";
		String sScriptName = "Scenario_RS_10563_configSync_edit";
		String sScriptName1 = "Scenario_RS_10563_configSync";
		String sExploreSearch = "WO SEARCH";
		
		// Add Processes
		commonsPo.execSahi(genericLib, sScriptName1, sTestCaseID);
		
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt(sExploreSearch).isDisplayed());
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sWOName, sProcessName);
		Assert.assertTrue(workOrderPo.getEleProcessName(sProcessName).isDisplayed());
		commonsPo.tap(workOrderPo.getEleCancelLink());
		commonsPo.tap(workOrderPo.geteleDiscardChangesbutton());
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sWOName, sOpDocProcessName);
		Assert.assertTrue(workOrderPo.getEleProcessNameLsMode(sOpDocProcessName).isDisplayed());
		workOrderPo.getEleDoneBtnLsMode().click();
//		commonsPo.tap(workOrderPo.getEleDoneBtnLsMode());
		Thread.sleep(GenericLib.iMedSleep);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		Thread.sleep(GenericLib.iMedSleep);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		
		//Edit Processes
		commonsPo.execSahi(genericLib, sScriptName, sTestCaseID);
	
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		Thread.sleep(GenericLib.iMedSleep);
		exploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		Assert.assertTrue(exploreSearchPo.getEleExploreChildSearchTxt("Cases").isDisplayed());
		commonsPo.tap(exploreSearchPo.getEleExploreChildSearchTxt("Work Orders"),20,20);
		exploreSearchPo.selectWorkOrder(commonsPo, sWOName);
		workOrderPo.selectAction(commonsPo, sProcessName);	
		Assert.assertTrue(workOrderPo.getTxtCity().isDisplayed());
		Assert.assertTrue(workOrderPo.getTxtCountry().isDisplayed());
			
	}

}
