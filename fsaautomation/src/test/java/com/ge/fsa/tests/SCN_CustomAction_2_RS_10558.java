package com.ge.fsa.tests;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;

public class SCN_CustomAction_2_RS_10558 extends BaseLib {
	
	@Test
	public void RS_10558() throws InterruptedException {
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFMWithIcon(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00002005", "10558_Action");
		Thread.sleep(GenericLib.iHighSleep);
		System.out.println("Context count " + driver.getContextHandles().size());
		Set<String> contextNames = driver.getContextHandles();
		for(String s:contextNames) {
			System.out.println(s);
		}
		driver.context(contextNames.toArray()[2].toString());
		String url = driver.getCurrentUrl();
        System.out.println(url);
        Assert.assertTrue(url.contains("motogp")&&url.contains("WO-00002005"));
//        Thread.sleep(GenericLib.iMedSleep);
//        loginHomePo.geteleMotoGpLogin().click();
//        Thread.sleep(GenericLib.iMedSleep);
		
	}
	

}
