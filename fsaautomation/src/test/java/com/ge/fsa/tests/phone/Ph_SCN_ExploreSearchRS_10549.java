/*
 *  @author vinod for phoneApp ,lakshmibs for tablet.
 */
package com.ge.fsa.tests.phone;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;

public class Ph_SCN_ExploreSearchRS_10549 extends BaseLib {

	String sTestID = null;
	String sExploreSearch = null;
	//For SFM Process Sahi Script name
			String sScriptName="SCN_Explore_RS_10549_prerequisite";
	
	private void preRequiste() throws Exception  
	{

		commonUtility.executeSahiScript("appium/SCN_Explore_RS_10549_prerequisite.sah");
		
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
	}

	@Test(retryAnalyzer=Retry.class)
	public void RS_10549() throws Exception 
	{
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6486");
		}else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6781");

		}
			
		sTestID = "RS_10549";
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sTestID,"ExploreSearch");
		
	try {
		preRequiste();

		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		
		//Config sync for the process to come in FSA.
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		Thread.sleep(CommonUtility.iLowSleep);
		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);

		//Navigation to SFM
		ph_ExploreSearchPo.geteleExploreIcn().click();
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchNameTxt("SVMXSTD: Account Search").isDisplayed(), "SVMXSTD: Account Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"SVMXSTD: Account Search text is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchNameTxt("SVMXSTD: Timesheet Manager").isDisplayed(), "SVMXSTD: Timesheet Manager is not displayed");
		ExtentManager.logger.log(Status.PASS,"SVMXSTD: Timesheet Manager text is successfully displayed");
		
		
		commonUtility.swipeGeneric("up");
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchNameTxt("SVMXSTD:Search Trunk Stock").isDisplayed(), "SVMXSTD:Search Trunk Stock is not displayed");
		ExtentManager.logger.log(Status.PASS,"SVMXSTD:Search Trunk Stock text is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchNameTxt("RS_10549 Multi Field WO Search").isDisplayed(), "RS_10549 Multi Field WO Search  is not displayed");
		ExtentManager.logger.log(Status.PASS,"RS_10549 Multi Field WO Search text is successfully displayed");
		
		
		commonUtility.custScrollToElement(ph_ExploreSearchPo.getEleSearchNameTxt("Serial Number Search"));
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchNameTxt("Serial Number Search").isDisplayed(), "Serial Number Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Serial Number Search text is successfully displayed");
	
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();;
		commonUtility.waitforElement(ph_ExploreSearchPo.getEleExploreChildSearchTxt("Work Orders"), 4);
		//Validation of childSearch
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleExploreChildSearchTxt("Work Orders").isDisplayed(), "Work Orders for RS_10549 SFM Search  is not displayed");
		ExtentManager.logger.log(Status.PASS," Work Orders for RS_10549 Multi Field WO Search text is successfully displayed");
	
		Assert.assertTrue(ph_ExploreSearchPo.getEleExploreChildSearchTxt("Contacts").isDisplayed(), "Contacts for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Contacts for RS_10549 Multi Field WO Search text is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleExploreChildSearchTxt("Products").isDisplayed(), "Products for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Products for RS_10549 Multi Field WO Search text is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleExploreChildSearchTxt("Accounts").isDisplayed(), "Accounts for RS_10549 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Accounts for RS_10549 SFM Search is successfully displayed");
			
	}catch(Exception e)
	{
		ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + " failed.");
		throw e;
	}
	
	}

}

