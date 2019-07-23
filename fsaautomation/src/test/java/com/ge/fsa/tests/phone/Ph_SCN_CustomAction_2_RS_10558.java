package com.ge.fsa.tests.phone;

import java.util.Set;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_CustomAction_2_RS_10558 extends BaseLib {
	
	String sProcessName = "10558_Action";
	String sScriptName = "appium/SCN_CustomAction_RS_10558.sah";
	String sTestCaseID = "RS_10558";
	Boolean bProcessCheckResult;
	String sUrl = "";
	@Test//(retryAnalyzer=Retry.class)
	public void RS_10558() throws Exception {
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6514");
		}else {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6787");

		}
			
		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sProcessName, sScriptName, sTestCaseID);
		
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
//		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
//		System.out.println("WO no ="+sWOName);
		
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		ph_MorePo.syncData(commonUtility);
		
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  "AUTOMATION SEARCH", "Work Orders",sWOName,sProcessName);
		Thread.sleep(CommonUtility.i30SecSleep);
		System.out.println("Context count " + driver.getContextHandles().size());
		Set<String> contextNames = driver.getContextHandles();
//		System.out.println(contextNames.size());
//		System.out.println(contextNames);
		if(sOSName.contains("android")) {
			driver.context("NATIVE_APP");
			Thread.sleep(5000);
			sUrl =  driver.findElement(By.xpath("//android.widget.TextView[@content-desc='multiLineHeaderTitle']")).getText();
			Assert.assertTrue(sUrl.contains("MotoGP"));
			ExtentManager.logger.log(Status.PASS, "Newly added Fields to Process: City and Country is displayed after config sync");
		}
		else {
			driver.context(contextNames.toArray()[contextNames.size()-1].toString());
			Thread.sleep(CommonUtility.i30SecSleep);
			sUrl = driver.getCurrentUrl();
			 Assert.assertTrue(sUrl.contains("motogp")&&sUrl.contains(sWOName));
		}
		ExtentManager.logger.log(Status.PASS, "The Url is launched succesfully");


	}
}
