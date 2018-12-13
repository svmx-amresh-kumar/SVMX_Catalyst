package com.ge.fsa.tests;

import java.io.IOException;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_ConfigSync_RS_10563 extends BaseLib {
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10563() throws InterruptedException, IOException {
		
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		String sProcessName = "Auto_Reg_10563";
		String sOpDocProcessName = "Auto_OPDOC_10563";
		
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt("WO SEARCH").isDisplayed());
//		Thread.sleep(GenericLib.iHighSleep);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWOName, sProcessName);
		Assert.assertTrue(workOrderPo.getEleProcessName(sProcessName).isDisplayed());
		commonsPo.tap(workOrderPo.getEleCancelLnk());
		commonsPo.tap(workOrderPo.geteleDiscardChangesbutton());
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWOName, sOpDocProcessName);
		Assert.assertTrue(workOrderPo.getEleProcessNameLsMode(sOpDocProcessName).isDisplayed());
		workOrderPo.getEleDoneBtnLsMode().click();
//		commonsPo.tap(workOrderPo.getEleDoneBtnLsMode());
		Thread.sleep(GenericLib.iMedSleep);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		Thread.sleep(GenericLib.iMedSleep);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);

		
		
	}

}
