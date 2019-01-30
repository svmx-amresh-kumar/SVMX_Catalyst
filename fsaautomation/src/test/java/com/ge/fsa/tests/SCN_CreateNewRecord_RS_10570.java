//@Author: Harish.CS
package com.ge.fsa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.CommonsPO;

public class SCN_CreateNewRecord_RS_10570 extends BaseLib {

	@Test(retryAnalyzer=Retry.class)
	public void RS_10570Test() throws Exception {
		
		String sTestCaseID = "RS_10570";
		String sScriptName = "Scenario_10570";
		
		String sAccountName = "Acme";
		String sContactName = "stuart law";
		String sProductName = "SampleProd";
		
		String sCoName = commonsPo.generaterandomnumber("customAction");
		String sCaseSubject = commonsPo.generaterandomnumber("caseSub");
		String sLocName = commonsPo.generaterandomnumber("Loc");
		String sIBName = commonsPo.generaterandomnumber("IB");
		String sEmailDomain = commonsPo.generaterandomnumber("Auto");
		String sEmail = sEmailDomain+"@svmx.com";
		
		//**********Create Process on Sahi**********
		commonsPo.execSahi(genericLib, sScriptName, sTestCaseID);
		
		//********Login to FSA********
		loginHomePo.login(commonsPo, exploreSearchPo);
		
		//********Perform Data Sync********
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		//********Perform Config Sync********
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		//********Creating Work Order from FSA********
		commonsPo.tap(createNewPO.getEleCreateNew());
		Thread.sleep(3000);
		commonsPo.tap(createNewPO.getEleItemNameTxt("SFM_10570"),30,36);
		commonsPo.setPickerWheelValue(workOrderPo.getEleOrderStatusCaseLst(), "Open");
		workOrderPo.getEmailvalue().sendKeys(sEmail);
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		
		//********Creating Location from FSA********
		commonsPo.tap(createNewPO.getEleCreateNew());
		Thread.sleep(3000);
		commonsPo.tap(createNewPO.getEleItemNameTxt("SFM_Loc_10570"),30,36);
		createNewPO.getEleLocNameTxt().sendKeys(sLocName);
		commonsPo.tap(workOrderPo.getEleIBAccountIDTxt());
		commonsPo.lookupSearch(sAccountName);
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		
		//********Creating Case from FSA********
		commonsPo.tap(createNewPO.getEleCreateNew());
		commonsPo.tap(createNewPO.getEleItemNameTxt("SFM_Case_10570"),30,36);
		commonsPo.setPickerWheelValue(workOrderPo.getEleStatusCaseLst(), "New");
		commonsPo.tap(workOrderPo.getEleContactIDTxt());
		commonsPo.lookupSearch(sContactName);
		workOrderPo.getEleSubjectTxtFld().sendKeys(sCaseSubject);
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		
		//********Creating IB from FSA********
		commonsPo.tap(createNewPO.getEleCreateNew());
		commonsPo.tap(createNewPO.getEleItemNameTxt("SFM_IB_10570"),30,36);
		commonsPo.tap(workOrderPo.getProductvalue());
		commonsPo.lookupSearch(sProductName);
		commonsPo.tap(workOrderPo.getEleIBAccountIDTxt());
		commonsPo.lookupSearch(sAccountName);
		workOrderPo.getEleIBIDTxt().sendKeys(sIBName);
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		
		//********Creating Custom Object from FSA********
		commonsPo.tap(createNewPO.getEleCreateNew());
		commonsPo.tap(createNewPO.getEleItemNameTxt("SFM_CO_10570"),30,36);
		createNewPO.getEleCustomObjNameTxt().sendKeys(sCoName);
		commonsPo.tap(workOrderPo.getEleSaveLnk());
		Thread.sleep(10000);
		
		//********Check if Records are present in DB before Sync********
		String sLocCountBeforeSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName+"\'", "totalSize");
		Assert.assertEquals(0, Integer.parseInt(sLocCountBeforeSync));
		String sIBCountBeforeSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Installed_Product__c+Where+name+=\'"+sIBName+"\'", "totalSize");
		Assert.assertEquals(0, Integer.parseInt(sIBCountBeforeSync));
		String sCaseCountBeforeSync = restServices.restGetSoqlValue("SELECT+Count()+from+case+Where+Subject+=\'"+sCaseSubject+"\'", "totalSize");
		Assert.assertEquals(0, Integer.parseInt(sCaseCountBeforeSync));
		String sCoCountBeforeSync = restServices.restGetSoqlValue("SELECT+Count()+from+Auto_Custom_Object2__c+Where+name+=\'"+sCoName+"\'", "totalSize");
		Assert.assertEquals(0, Integer.parseInt(sCoCountBeforeSync));
		String sWoCountBeforeSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Service_Order__c+Where+Email__c+=\'"+sEmail+"\'", "totalSize");
		Assert.assertEquals(0, Integer.parseInt(sWoCountBeforeSync));
		
		//********Perform Data Sync********
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		//********Check if Records are present in DB after Sync********
		String sLocCountAfterSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName+"\'", "totalSize");
		Assert.assertEquals(Integer.parseInt(sLocCountBeforeSync)+1, Integer.parseInt(sLocCountAfterSync));
		String sIBCountAfterSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Installed_Product__c+Where+name+=\'"+sIBName+"\'", "totalSize");
		Assert.assertEquals(Integer.parseInt(sIBCountBeforeSync)+1, Integer.parseInt(sIBCountAfterSync));
		String sCaseCountAfterSync = restServices.restGetSoqlValue("SELECT+Count()+from+case+Where+Subject+=\'"+sCaseSubject+"\'", "totalSize");
		Assert.assertEquals(Integer.parseInt(sCaseCountBeforeSync)+1, Integer.parseInt(sCaseCountAfterSync));
		String sCoCountAfterSync = restServices.restGetSoqlValue("SELECT+Count()+from+Auto_Custom_Object2__c+Where+name+=\'"+sCoName+"\'", "totalSize");
		Assert.assertEquals(Integer.parseInt(sCoCountBeforeSync)+1, Integer.parseInt(sCoCountAfterSync));
		String sWoCountAfterSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Service_Order__c+Where+Email__c+=\'"+sEmail+"\'", "totalSize");
		Assert.assertEquals(Integer.parseInt(sCoCountBeforeSync)+1, Integer.parseInt(sWoCountAfterSync));
	}
}
