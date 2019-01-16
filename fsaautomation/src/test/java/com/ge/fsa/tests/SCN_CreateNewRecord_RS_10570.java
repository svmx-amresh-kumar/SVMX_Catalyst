package com.ge.fsa.tests;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.CommonsPO;

public class SCN_CreateNewRecord_RS_10570 extends BaseLib {

	@Test(retryAnalyzer=Retry.class)
	public void RS_10570Test() throws Exception {
		
		commonsPo.preReqSetup(genericLib);
		// Resinstall the app
		lauchNewApp("false");
		
		String sAccountName = "Acme";
		String sContactName = "Jon Amos";
		String sProductName = "testerProd";
		loginHomePo.login(commonsPo, exploreSearchPo);
//		toolsPo.syncData(commonsPo);
		//********Creating Work Order********
		commonsPo.tap(createNewPO.getEleCreateNew());
		Thread.sleep(3000);
		commonsPo.tap(createNewPO.getEleItemNameTxt("SFM_10570"),30,36);
		commonsPo.setPickerWheelValue(workOrderPo.getEleOrderStatusCaseLst(), "Open");
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		//********Creating Loc********
//		commonsPo.tap(createNewPO.getEleCreateNew());
//		Thread.sleep(3000);
//		commonsPo.tap(createNewPO.getEleItemNameTxt("SFM_Loc_10570"),30,36);
//		createNewPO.getEleLocNameTxt().sendKeys("LName");
//		commonsPo.tap(workOrderPo.getEleIBAccountIDTxt());
//		commonsPo.lookupSearch(sAccountName);
//		commonsPo.tap(workOrderPo.getEleSaveLnk());
//		//********Creating Contact********
//		commonsPo.tap(createNewPO.getEleCreateNew());
//		commonsPo.tap(createNewPO.getEleItemNameTxt("SFM_Case_10570"),30,36);
//		commonsPo.setPickerWheelValue(workOrderPo.getEleStatusCaseLst(), "New");
//		commonsPo.tap(workOrderPo.getEleContactIDTxt());
//		commonsPo.lookupSearch(sContactName);
//		workOrderPo.getEleSubjectTxtFld().sendKeys("Tester");
//		commonsPo.tap(workOrderPo.getEleSaveLnk());
//		//********Creating IB********
//		commonsPo.tap(createNewPO.getEleCreateNew());
//		commonsPo.tap(createNewPO.getEleItemNameTxt("SFM_IB_10570"),30,36);
//		commonsPo.tap(workOrderPo.getProductvalue());
//		commonsPo.lookupSearch(sProductName);
//		commonsPo.tap(workOrderPo.getEleIBAccountIDTxt());
//		commonsPo.lookupSearch(sAccountName);
//		workOrderPo.getEleIBIDTxt().sendKeys("Hello");
//		commonsPo.tap(workOrderPo.getEleSaveLnk());
//		//********Creating CO********
//		commonsPo.tap(createNewPO.getEleCreateNew());
//		commonsPo.tap(createNewPO.getEleItemNameTxt("SFM_CO_10570"),30,36);
//		commonsPo.tap(workOrderPo.getEleSaveLnk());
		Thread.sleep(10000);

	}
}
