package com.ge.fsa.tests;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_CustomAction_1_RS_10559 extends BaseLib {
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10559() throws InterruptedException {
		String sWebServiceState = "Nottingham Shire";
		String sWebServiceCountry = "United Kingdom";
		DateFormat dateFormat = new SimpleDateFormat("M/d/yy");
		Date date = new Date();
		String sCurrentDate = dateFormat.format(date);
//		System.out.println(sCurrentDate);
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFMWithIcon(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00003685", "10559_Action");
//		Thread.sleep(GenericLib.iLowSleep);
		// Validating Step 1
		Assert.assertTrue(workOrderPo.getEleLblStateName().getText().equals(sWebServiceState));
		Assert.assertTrue(workOrderPo.getEleLblCountryName().getText().equals(sWebServiceCountry));
//		System.out.println(workOrderPo.getEleLblCompletedDateTime().getText());
		Assert.assertTrue(workOrderPo.getEleLblCompletedDateTime().getText().contains(sCurrentDate));
			workOrderPo.getEleActionsLnk().click();
			commonsPo.tap(workOrderPo.getEleActionsLnk());	
			commonsPo.getSearch(workOrderPo.getEleActionsTxt("Record T&M"));
			Thread.sleep(GenericLib.iLowSleep);
			commonsPo.tap(workOrderPo.getEleActionsTxt("Record T&M"),20,20);
			commonsPo.tap(workOrderPo.getEleSaveLnk());
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			workOrderPo.navigateToWOSFMWithIcon(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00003685", "10559_Action_Child");
			Thread.sleep(GenericLib.iHighSleep);
//			System.out.println("Wait Completed");
//			workOrderPo.getEleclickparts("Prod03082018171757").click();
			commonsPo.tap(workOrderPo.getEleclickparts("Prod03082018171757"));
			// Validating Step 2
			Assert.assertTrue(workOrderPo.getEleLblRequestedCity().getText().equals("Adams Town"));
			Assert.assertTrue(workOrderPo.getEleLblRequestedCountry().getText().equals("Adams Town"));
	}

}
