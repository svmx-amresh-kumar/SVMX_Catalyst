// Author: Harish.CS
package com.ge.fsa.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

public class Scenario3Test extends BaseLib {
	
	@Test
	public void scenario3Test() throws InterruptedException, IOException {
		String sTaskName = "";
		lauchNewApp("false");
		loginHomePo.login(commonsPo, exploreSearchPo);
		sTaskName = tasksPo.addTask(commonsPo);
		toolsPo.syncData(commonsPo);
		//Fetching the task name and checking if the task is present on the server.
		String soqlquery = "Select+Count()+from+Task+where+Subject+=\'"+sTaskName+"\'";
		
		String sTaskcount = restServices.restGetSoqlValue(soqlquery, "totalSize");
		Assert.assertTrue(sTaskcount.equals("1"));
		ExtentManager.logger.log(Status.PASS,"Tasks updated successfully");
		//NXGReports.addStep("Tasks updated successfully", LogAs.PASSED, null);
	}


}
