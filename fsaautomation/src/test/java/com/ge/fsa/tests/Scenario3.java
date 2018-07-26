package com.ge.fsa.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

public class Scenario3 extends BaseLib {
	
	@Test
	public void scenario3_initialSync() throws InterruptedException, IOException {
		String taskName = "";
		lauchNewApp("false");
		loginHomePo.login(commonsPo, exploreSearchPo);
		taskName = tasksPo.addTask(commonsPo);
		toolsPo.syncData(commonsPo);
		String soqlquery = "Select+Count()+from+Task+where+Subject+=\'"+taskName+"\'";
		restServices.getAccessToken();
		String taskcount = restServices.restSoql(soqlquery, "totalSize");
		Assert.assertTrue(taskcount.equals("1"));
		NXGReports.addStep("Tasks updated successfully", LogAs.PASSED, null);
	}


}
