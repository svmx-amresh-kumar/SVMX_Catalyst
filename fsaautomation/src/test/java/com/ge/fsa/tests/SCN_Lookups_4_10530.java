package com.ge.fsa.tests;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;

public class SCN_Lookups_4_10530 extends BaseLib {
	
	@Test
	public void RS_10530() throws InterruptedException {
		loginHomePo.login(commonsPo, exploreSearchPo);	
//		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00002005", "10530_lkp_proc1");
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(workOrderPo.getLblProduct());
	}

}
