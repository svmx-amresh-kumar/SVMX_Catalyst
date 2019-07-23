package com.ge.fsa.tests.tablet;

import java.io.IOException;
import java.util.Set;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.Retry;

public class SCN_CustomAction_2_RS_10558 extends BaseLib {
	
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
	String sProcessName = "10558_Action";
	String sScriptName = "appium/SCN_CustomAction_RS_10558.sah";
	String sTestCaseID = "RS_10558";
	String url = "";

	@Test(retryAnalyzer=Retry.class)
	public void RS_10558() throws Exception {
		
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-10558");
		}else {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/RS-12082");

		}
		
		commonUtility.executeSahiScript(sScriptName);
		
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
//		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
//		System.out.println("WO no ="+sWOName);
		
		loginHomePo.login(commonUtility, exploreSearchPo);	
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		workOrderPo.navigateToWOSFMWithIcon(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearch, sWOName, sProcessName);
//		workOrderPo.navigateToWOSFM(commonsUtility, exploreSearchPo, sExploreSearch, sExploreChildSearch, sWOName, "10558_Action");
		Thread.sleep(CommonUtility.i30SecSleep);
		System.out.println("Context count " + driver.getContextHandles().size());
		Set<String> contextNames = driver.getContextHandles();
//		System.out.println(contextNames.size());
//		for(String s:contextNames) {
////			if(s.contains("WEBVIEW")) {
////				driver.context(s);
////				String url = driver.getCurrentUrl();
////		        System.out.println(url);
////			}
//			System.out.println(s);
//		}
//		for(int i=0;i<contextNames.size();i++) {
//			if(contextNames.toArray()[i].toString().contains("WEBVIEW")) {
//				System.out.println(i+": "+contextNames.toArray()[i].toString());
//				driver.context(contextNames.toArray()[i].toString());
//				String url = driver.getCurrentUrl();
//				System.out.println(url);
//			}
//			
//		}
		if(sOSName.contains("android")) {
			driver.context("NATIVE_APP");
			Thread.sleep(5000);
			url = driver.findElement(By.id("com.android.chrome:id/url_bar")).getText();
			driver.context("WEBVIEW_com.servicemaxinc.svmxfieldserviceapp");
		}
		else {
			driver.context(contextNames.toArray()[contextNames.size()-1].toString());
			Thread.sleep(CommonUtility.i30SecSleep);
			url = driver.getCurrentUrl();
		}
		
        System.out.println("The Url is "+url);
        Assert.assertTrue(url.contains("motogp")&&url.contains(sWOName));
        driver.quit();
//        Thread.sleep(GenericLib.iMedSleep);
//        loginHomePo.geteleMotoGpLogin().click();
//        Thread.sleep(GenericLib.iMedSleep);
        
		
	}
	

}
