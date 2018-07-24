package com.ge.fsa.tests;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;

public class Scenario3 extends BaseLib {
	
	@Test
	public void scenario3_initialSync() throws InterruptedException {
		
		lauchNewApp("false");
		loginHomePo.login(commonsPo, exploreSearchPo);
		tasksPo.addTask(commonsPo);
		
	}

}
