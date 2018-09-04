package com.ge.fsa.tests;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;

public class Scenario10570Test extends BaseLib {

	@Test
	public void scenario10570Test() throws InterruptedException {
		loginHomePo.login(commonsPo, exploreSearchPo);
		toolsPo.syncData(commonsPo);
		createNewPO.getEleCreateNew().click();
//		commonsPo.tap(createNewPO.getEleCreateNew());
		createNewPO.getEleItemNameTxt("place holder").click();
	}
}
