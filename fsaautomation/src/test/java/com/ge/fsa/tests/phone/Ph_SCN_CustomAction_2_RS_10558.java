package com.ge.fsa.tests.phone;

import java.util.Set;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_CustomAction_2_RS_10558 extends BaseLib {
	
	String sProcessName = "10558_Action";
	String sScriptName = "appium/SCN_CustomAction_RS_10558.sah";
	String sTestCaseID = "RS_10558";
	Boolean bProcessCheckResult;
	@Test//(retryAnalyzer=Retry.class)
	public void Ph_RS_10558() throws Exception {
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
		System.out.println(contextNames.size());
		Thread.sleep(15000000);

		

	}

}
