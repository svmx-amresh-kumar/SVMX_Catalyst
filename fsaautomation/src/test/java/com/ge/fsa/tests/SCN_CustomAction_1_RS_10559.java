package com.ge.fsa.tests;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;

public class SCN_CustomAction_1_RS_10559 extends BaseLib {
	
	@Test
	public void RS_10559() throws InterruptedException {
		String sWebServiceState = "Nottingham Shire";
		String sWebServiceCountry = "United Kingdom";
		DateFormat dateFormat = new SimpleDateFormat("M/dd/yy");
		Date date = new Date();
		String sCurrentDate = dateFormat.format(date);
		loginHomePo.login(commonsPo, exploreSearchPo);	
//		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFMWithIcon(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00002005", "10559_Action");
		Thread.sleep(GenericLib.iLowSleep);
		// Validating Step 1
		Assert.assertTrue(workOrderPo.getEleLblStateName().getText().equals(sWebServiceState));
		Assert.assertTrue(workOrderPo.getEleLblCountryName().getText().equals(sWebServiceCountry));
		Assert.assertTrue(workOrderPo.getEleLblCompletedDateTime().getText().contains(sCurrentDate));
		
	}

}
