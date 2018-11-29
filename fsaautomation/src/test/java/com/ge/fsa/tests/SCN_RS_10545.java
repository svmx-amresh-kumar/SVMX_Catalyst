/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_RS_10545 extends BaseLib {

	int iWhileCnt = 0;
	String sTestID = null;
	
	private void preRequiste()  
	{
		

	}

	@Test(enabled = true)
	public void SCN_RS_10545Test() throws Exception 
	{
		sTestID = "RS_10545";
		
	try {
		preRequiste();

		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);

		//Config Sync for process
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);

		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep); 

		//Navigation to SFM
		commonsPo.tap(exploreSearchPo.getEleExploreIcn());
		
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt("Serial Number Search").isDisplayed(), "Serial Number Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Serial Number Search text is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt("SVMXSTD: Account Search").isDisplayed(), "SVMXSTD: Account Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"SVMXSTD: Account Search text is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt("SVMXSTD: Timesheet Manager").isDisplayed(), "Serial Number Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"SVMXSTD: Timesheet Manager text is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt("SVMXSTD:Search Trunk Stock").isDisplayed(), "Serial Number Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"SVMXSTD:Search Trunk Stock text is successfully displayed");
		

	}catch(Exception e)
	{
		
	}

}
}
