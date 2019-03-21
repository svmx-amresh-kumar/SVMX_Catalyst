package com.ge.fsa.tests.tablet;

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
	
	@Test(retryAnalyzer=Retry.class)
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
		commonsUtility.execSahi(genericLib, sScriptName1, sTestCaseID);
		
		genericLib.executeSahiScript("appium/Scenario_RS_10561_ConfigSync_Alert_Post.sah");
		Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		lauchNewApp("false");
		loginHomePo.login(commonsUtility, exploreSearchPo);	
		toolsPo.configSync(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.syncData(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);
		commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt(sExploreSearch).isDisplayed());
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsUtility, exploreSearchPo, sExploreSearch, sWOName, sProcessName);
		Assert.assertTrue(workOrderPo.getEleProcessName(sProcessName).isDisplayed());
		commonsUtility.tap(workOrderPo.getEleCancelLink());
		commonsUtility.tap(workOrderPo.geteleDiscardChangesbutton());
		workOrderPo.navigateToWOSFM(commonsUtility, exploreSearchPo, sExploreSearch, sWOName, sOpDocProcessName);
		Assert.assertTrue(workOrderPo.getEleProcessNameLsMode(sOpDocProcessName).isDisplayed());
		workOrderPo.getEleDoneBtnLsMode().click();
		Thread.sleep(GenericLib.iMedSleep);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		Thread.sleep(GenericLib.iMedSleep);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		
		//Edit Processes
		commonsUtility.execSahi(genericLib, sScriptName, sTestCaseID);
	
		toolsPo.configSync(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);
		commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
		commonsUtility.tap(exploreSearchPo.getEleSearchItem("WO SEARCH"));
		Assert.assertTrue(exploreSearchPo.getEleSearchItem("Cases").isDisplayed());
		commonsUtility.tap(exploreSearchPo.getEleSearchItem("Work Orders"));
		commonsUtility.tap(exploreSearchPo.getEleWOSearch(sWOName));
		workOrderPo.selectAction(commonsUtility, sProcessName);
		Assert.assertTrue(workOrderPo.getTxtCity().isDisplayed());
		Assert.assertTrue(workOrderPo.getTxtCountry().isDisplayed());
			
	}

}