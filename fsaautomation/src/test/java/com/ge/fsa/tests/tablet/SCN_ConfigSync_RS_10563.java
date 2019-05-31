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
		String sScriptName = "Scenario_RS_10563_configSync_edit ";      
		String sScriptName1 = "Scenario_RS_10563_configSync";
		String sExploreSearch = "WO SEARCH";
		
		// Add Processes
		commonUtility.execSahi(genericLib, sScriptName1, sTestCaseID);
		
		genericLib.executeSahiScript("appium/Scenario_RS_10561_ConfigSync_Alert_Post.sah");
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		lauchNewApp("false");
		loginHomePo.login(commonUtility, exploreSearchPo);	
		toolsPo.configSync(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt(sExploreSearch).isDisplayed());
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sWOName, sProcessName);
		Assert.assertTrue(workOrderPo.getEleProcessName(sProcessName).isDisplayed());
		commonUtility.tap(workOrderPo.getEleCancelLink());
		commonUtility.tap(workOrderPo.geteleDiscardChangesbutton());
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sWOName, sOpDocProcessName);
		Assert.assertTrue(workOrderPo.getEleProcessNameLsMode(sOpDocProcessName).isDisplayed());
		workOrderPo.getEleDoneBtnLsMode().click();
		Thread.sleep(GenericLib.iMedSleep);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		Thread.sleep(GenericLib.iMedSleep);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		
		//Edit Processes
		commonUtility.execSahi(genericLib, sScriptName, sTestCaseID);
	
		toolsPo.configSync(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		commonUtility.tap(exploreSearchPo.getEleSearchItem("WO SEARCH"));
		Assert.assertTrue(exploreSearchPo.getEleSearchItem("Cases").isDisplayed());
		commonUtility.tap(exploreSearchPo.getEleSearchItem("Work Orders"));
		commonUtility.tap(exploreSearchPo.getEleWOSearch(sWOName));
		workOrderPo.selectAction(commonUtility, sProcessName);
		Assert.assertTrue(workOrderPo.getTxtCity().isDisplayed());
		Assert.assertTrue(workOrderPo.getTxtCountry().isDisplayed());
			
	}

}
