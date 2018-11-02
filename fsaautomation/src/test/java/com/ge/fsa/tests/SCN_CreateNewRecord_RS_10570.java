package com.ge.fsa.tests;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;

public class SCN_CreateNewRecord_RS_10570 extends BaseLib {

	@Test
	public void RS_10570Test() throws InterruptedException {
		loginHomePo.login(commonsPo, exploreSearchPo);
		toolsPo.syncData(commonsPo);
		createNewPO.getEleCreateNew().click();
//		commonsPo.tap(createNewPO.getEleCreateNew());
		createNewPO.getEleItemNameTxt("place holder").click();
	}
}
