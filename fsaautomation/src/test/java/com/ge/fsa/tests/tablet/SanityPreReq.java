// Author: Harish.CS
package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class SanityPreReq extends BaseLib {
	
	String sTestCaseID = "SanityPreReq";
	
	@Test(retryAnalyzer=Retry.class)
	public void SanityPreReq() throws Exception {

		
		// running the Sahi Script Pre-requisites - To make My Records to All Records in Mobile Configuration
		genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah");
		Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");

		genericLib.executeSahiScript("appium/Scenario_RS_10561_ConfigSync_Alert_Post.sah");
		Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		
		genericLib.executeSahiScript("appium/Scenario_RS_10569_ScheduledDataSync_Post.sah");
		Assert.assertTrue(commonsUtility.verifySahiExecution(), "Execution of Sahi script is failed");

		//Resinstall the app
		lauchNewApp("false");

		// Syncing after the Pre-Requisite is done
		//toolsPo.configSync(commonsUtility);
	}


}
