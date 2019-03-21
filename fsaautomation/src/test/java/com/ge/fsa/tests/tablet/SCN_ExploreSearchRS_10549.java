/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.tablet;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_ExploreSearchRS_10549 extends BaseLib {

	String sTestID = null;
	String sExploreSearch = null;
	
	private void preRequiste() throws Exception  
	{
		genericLib.executeSahiScript("appium/SCN_Explore_RS_10549_prerequisite.sah", sTestID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10549Test() throws Exception 
	{
		sTestID = "RS_10549";
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		
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
		
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt("SVMXSTD: Timesheet Manager").isDisplayed(), "SVMXSTD: Timesheet Manager is not displayed");
		ExtentManager.logger.log(Status.PASS,"SVMXSTD: Timesheet Manager text is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt("SVMXSTD:Search Trunk Stock").isDisplayed(), "SVMXSTD:Search Trunk Stock is not displayed");
		ExtentManager.logger.log(Status.PASS,"SVMXSTD:Search Trunk Stock text is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleSearchNameTxt("RS_10549 Multi Field WO Search").isDisplayed(), "RS_10549 Multi Field WO Search  is not displayed");
		ExtentManager.logger.log(Status.PASS,"RS_10549 Multi Field WO Search text is successfully displayed");
		
	
		commonsPo.longPress(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		Assert.assertTrue(exploreSearchPo.getEleExploreChildSearchTxt("Work Orders").isDisplayed(), "Work Orders for RS_10549 SFM Search  is not displayed");
		ExtentManager.logger.log(Status.PASS," Work Orders for RS_10549 Multi Field WO Search text is successfully displayed");
	
		Assert.assertTrue(exploreSearchPo.getEleExploreChildSearchTxt("Contacts").isDisplayed(), "Contacts for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Contacts for RS_10549 Multi Field WO Search text is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleExploreChildSearchTxt("Products").isDisplayed(), "Products for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Products for RS_10549 Multi Field WO Search text is successfully displayed");
		
		Assert.assertTrue(exploreSearchPo.getEleExploreChildSearchTxt("Accounts").isDisplayed(), "Accounts for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Accounts for RS_10549 SFM Search is successfully displayed");
			
	}catch(Exception e)
	{
		ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + " failed.");
		throw e;
	}
	
	}

}

