package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_10527 extends BaseLib {
	
@Test//(retryAnalyzer=Retry.class)
	
	public void RS_10527() throws Exception {
	
	ph_LoginHomePo.login(commonsUtility, ph_MorePo);
	

	Thread.sleep(GenericLib.iMedSleep);
	

	Thread.sleep(GenericLib.iMedSleep);
	
}

}
