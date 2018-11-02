// Author: Harish.CS
package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;

public class SanityPreReq extends BaseLib {
	
	String sTestCaseID = "SanityPreReq";
	
	@Test
	public void SanityPreReq() throws Exception {

		
		// running the Sahi Script Pre-requisites - To make My Records to All Records in Mobile Configuration
		genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah", "sTestCaseID");
//		if(commonsPo.verifySahiExecution()) {
//			
//			System.out.println("Sahi verification success");
//		}
//		else 
//		{
//			System.out.println("Sahi verification failure");
//			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
//			assertEquals(0, 1);
//		}
		
		//Resinstall the app
		lauchNewApp("false");
		// Syncing after the Pre-Requisite is done
		//toolsPo.configSync(commonsPo);
	}


}
