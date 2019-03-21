//@Author: Harish.CS
package com.ge.fsa.tests.tablet;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_CreateNewRecord_RS_10570 extends BaseLib {

	@Test//(retryAnalyzer=Retry.class)
	public void RS_10570Test() throws Exception {
		
		String sTestCaseID = "RS_10570";
		String sScriptName = "Scenario_10570";
		
		String sAccountName = "Acme";
		String sContactName = "Stuart Law";
		String sProductName = "SampleProd";
		
		String sCoName = commonsUtility.generaterandomnumber("customAction");
		String sCaseSubject = commonsUtility.generaterandomnumber("caseSub");
		String sLocName = commonsUtility.generaterandomnumber("Loc");
		String sIBName = commonsUtility.generaterandomnumber("IB");
		String sEmailDomain = commonsUtility.generaterandomnumber("Auto");
		String sEmail = sEmailDomain+"@svmx.com";
		
		// Create Account
		String sAccCount = restServices.restGetSoqlValue("SELECT+Count()+from+Account+Where+name+=\'"+sAccountName+"\'", "totalSize");
//		System.out.println(sAccCount);
		if(Integer.parseInt(sAccCount)==0) {
			String sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccountName+"\"}");
			System.out.println("The Acc Id of "+sAccountName+" is "+sAccId);
			// Create Contact
			String sConId = restServices.restCreate("Contact?","{\"FirstName\": \"Stuart\", \"LastName\": \"Law\", \"AccountId\": \""+sAccId+"\"}");
			System.out.println("SS "+sConId);
		}
		
		//**********Create Process on Sahi**********
//		commonsUtility.execSahi(genericLib, sScriptName, sTestCaseID);
		
		//********Login to FSA********
		loginHomePo.login(commonsUtility, exploreSearchPo);
		
		//********Perform Data Sync********
		toolsPo.syncData(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);
		
		//********Perform Config Sync********
		toolsPo.configSync(commonsUtility);
		Thread.sleep(GenericLib.iMedSleep);
		
		//********Creating Work Order from FSA********
		commonsUtility.tap(createNewPO.getEleCreateNew());
		Thread.sleep(3000);
		commonsUtility.tap(createNewPO.getEleItemNameTxt("SFM_10570"),30,36);
		commonsUtility.setPickerWheelValue(workOrderPo.getEleOrderStatusCaseLst(), "Open");
		workOrderPo.getEmailvalue().sendKeys(sEmail);
		commonsUtility.tap(workOrderPo.getEleSaveLnk());
		
		//********Creating Location from FSA********
		commonsUtility.tap(createNewPO.getEleCreateNew());
		Thread.sleep(3000);
		commonsUtility.tap(createNewPO.getEleItemNameTxt("SFM_Loc_10570"),30,36);
		createNewPO.getEleLocNameTxt().sendKeys(sLocName);
		commonsUtility.tap(workOrderPo.getEleIBAccountIDTxt());
		commonsUtility.lookupSearch(sAccountName);
		commonsUtility.tap(workOrderPo.getEleSaveLnk());
		
		//********Creating Case from FSA********
		commonsUtility.tap(createNewPO.getEleCreateNew());
		commonsUtility.tap(createNewPO.getEleItemNameTxt("SFM_Case_10570"),30,36);
		commonsUtility.setPickerWheelValue(workOrderPo.getEleStatusCaseLst(), "New");
		commonsUtility.tap(workOrderPo.getEleContactIDTxt());
		commonsUtility.lookupSearch(sContactName);
		workOrderPo.getEleSubjectTxtFld().sendKeys(sCaseSubject);
		commonsUtility.tap(workOrderPo.getEleSaveLnk());
		
		//********Creating IB from FSA********
		commonsUtility.tap(createNewPO.getEleCreateNew());
		commonsUtility.tap(createNewPO.getEleItemNameTxt("SFM_IB_10570"),30,36);
		commonsUtility.tap(workOrderPo.getProductvalue());
		commonsUtility.lookupSearch(sProductName);
		commonsUtility.tap(workOrderPo.getEleIBAccountIDTxt());
		commonsUtility.lookupSearch(sAccountName);
		workOrderPo.getEleIBIDTxt().sendKeys(sIBName);
		commonsUtility.tap(workOrderPo.getEleSaveLnk());
		
		//********Creating Custom Object from FSA********
		commonsUtility.tap(createNewPO.getEleCreateNew());
		commonsUtility.tap(createNewPO.getEleItemNameTxt("SFM_CO_10570"),30,36);
		createNewPO.getEleCustomObjNameTxt().sendKeys(sCoName);
		commonsUtility.tap(workOrderPo.getEleSaveLnk());
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
		toolsPo.syncData(commonsUtility);
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