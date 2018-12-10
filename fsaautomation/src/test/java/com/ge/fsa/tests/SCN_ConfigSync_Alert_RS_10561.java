/*
 *  @author Vinod Tharavath
 *  To Validate Config Sync Due. Validation include - 
 *  1. Validation of 1 config due badge showing up in tools icon
 *  2. Validation of 1 besides config sync in tools page when config sync is due.
 *  3. Rechecking when config sync due setting is turned back to 600 minutes that tools icon is not displaying 1 sync due.
 *   */
package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_ConfigSync_Alert_RS_10561 extends BaseLib {
	String sTestCaseID;	
	String stoolspageval = "";
	String stoolsexpectedval = "1";
	String sConfigSyncDueBadge = "";
	String sConfigSyncDueBadgevalidationvalue = "1";

	// For ServerSide Validations
	String sSheetName =null;
	
	public void prerequisites() throws Exception
	{
		sSheetName ="RS_10561";
		System.out.println("RS 10561 Config sync alert");
		sTestCaseID = "Config_sync_alert_10561";
		//sWOName = "WO-00002005";
		// running the Sahi Script Pre-requisites - To make All Records to My Records in Mobile Configuration
				
				genericLib.executeSahiScript("appium/Scenario_RS_10561_ConfigSync_Alert_Pre.sah",sTestCaseID);
				Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
				ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
	}
	
	public void postscript() throws Exception
	{
		genericLib.executeSahiScript("appium/Scenario_RS_10561_ConfigSync_Alert_Post.sah",sTestCaseID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");
	}
		
	@Test(retryAnalyzer=Retry.class)
	public void RS_10561() throws Exception {
		prerequisites();
	// Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.configSync(commonsPo);
		ExtentManager.logger.log(Status.PASS,"First Config sync is sucessfull after setting value");
		System.out.println("First Config Sync sucessfull after setting value");
		System.out.println("Begining wait to check for config sync due is displayed after 5 minutes");
		Thread.sleep(GenericLib.iMedSleep);
		//need to wait for a minimum of 5 minutes for the pop up to show up
		Thread.sleep(300000);
		Thread.sleep(GenericLib.iVHighSleep);
		System.out.println("wait ended to check for config sync due is displayed after 5 minutes");
		String sConfigsyncduelbl = toolsPo.geteleConfigSyncDue().getText();
		Assert.assertTrue(sConfigsyncduelbl.equals("Configuration sync is due"), "Config sync Due alert popup is not displayed");
		ExtentManager.logger.log(Status.PASS,"Config Sync due pop alert pop up is displayed");

		//Relauching the app in order to view the conflict.
		lauchNewApp("true");
		System.out.println("AppLaunchsettotrue");
		/*//the config sync due pop up may thrown if 5 minutes has passed hence clicking on the popup in try block
		try {
			commonsPo.tap(toolsPo.getEleOkBtn());	
		} catch (Exception e) {
			// TODO: handle exception 
			System.out.println("Could not find the popup config sync pop up button this time");
		}	*/
		
		
		//Getting the value of 1 in Tools icon as there is a config sync due
		sConfigSyncDueBadge =  toolsPo.geeteleConflictBadge().getText();
		Assert.assertTrue(sConfigSyncDueBadge.equals(sConfigSyncDueBadgevalidationvalue), "Config sync Due alert is not turned to 1");
		ExtentManager.logger.log(Status.PASS,"Config Sync Alert 1 shown in Tools");
		commonsPo.tap(toolsPo.getEleToolsIcn());
		
		//Getting the value of 1 in Tools page besides Config Sync
		stoolspageval=	toolsPo.geteleConfigSyncAlertBadge().getText();		
		Assert.assertTrue(stoolspageval.equals(stoolsexpectedval), "Config sync Due alert is not turned to 1");
		ExtentManager.logger.log(Status.PASS,"Config Sync Alert 1 shown besides ConfigSync");
		toolsPo.configSync(commonsPo);
		
		
		// running the Sahi Script to set back value to 600
		postscript();
		
		
		loginHomePo.login(commonsPo, exploreSearchPo);
		
		
		//Performing config after resetting config sync setting back to 600 and validate if badge is still visible.
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.configSync(commonsPo);
		System.out.println("Second Config Sync sucessfull after resetting value");
		
		try {
			toolsPo.geeteleConflictBadge().getText();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Element not found as there is no config sync due setting scheduled.");
			ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Config sync due badge is not shown as settings are reverted");

		}				

	}
		
	@AfterMethod
	public void tearDown() throws Exception {
	postscript();
	System.out.println("Running the post script");
	ExtentManager.logger.log(Status.INFO,"Post script run sucessfully at aftermethod");

	}

}
