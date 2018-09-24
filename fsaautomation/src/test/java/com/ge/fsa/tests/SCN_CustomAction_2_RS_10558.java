package com.ge.fsa.tests;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;

public class SCN_CustomAction_2_RS_10558 extends BaseLib {
	
	@Test
	public void RS_10558() throws InterruptedException {
		loginHomePo.login(commonsPo, exploreSearchPo);	
//		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFMWithIcon(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00002005", "10558_Action");
		Thread.sleep(GenericLib.iHighSleep);
		System.out.println("Context count " + driver.getContextHandles().size());
		for (Object contextName : driver.getContextHandles()) {
	        System.out.println("Context Name -> " + contextName);
//	        if (contextName.toString().toUpperCase().contains("WEBVIEW")){
//	            driver.context(contextName.toString());
//	            System.out.println("Switched to WebView Context");
//	        }
	    }
	}
	

}
