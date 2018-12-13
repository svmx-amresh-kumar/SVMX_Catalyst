package com.ge.fsa.tests;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.Retry;

public class SCN_CreateNewRecord_RS_10570 extends BaseLib {

	@Test//(retryAnalyzer=Retry.class)
	public void RS_10570Test() throws InterruptedException {
		loginHomePo.login(commonsPo, exploreSearchPo);
		toolsPo.syncData(commonsPo);
		commonsPo.tap(createNewPO.getEleCreateNew());
		Thread.sleep(10000);
//		commonsPo.tap(createNewPO.getEleCreateNew());
//		createNewPO.getEleItemNameTxt("place holder").click();
	}
}
