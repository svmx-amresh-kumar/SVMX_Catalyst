package com.ge.fsa.tests.phone;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_CalendarPO;
import com.ge.fsa.pageobjects.phone.Ph_WorkOrderPO;

public class Ph_SCN_CreateNewRecord_RS_10570 extends BaseLib{


	@Test(retryAnalyzer=Retry.class)
	public void RS_10570Test() throws Exception {
		
		String sTestCaseID = "RS_10570";
		String sScriptName = "Scenario_10570";
		String sProcessName = "SFM_10570";
		String sAccountName = "Acme";
		String sContactName = "Stuart Law";
		String sProductName = "SampleProd";
		
		String sCoName = commonUtility.generateRandomNumber("customAction");
		String sCaseSubject = commonUtility.generateRandomNumber("caseSub");
		String sLocName = commonUtility.generateRandomNumber("Loc");
		String sIBName = commonUtility.generateRandomNumber("IB");
		String sEmailDomain = commonUtility.generateRandomNumber("Auto");
		String sEmail = sEmailDomain+"@svmx.com";
		
		// Create Account
		String sAccCount = restServices.restGetSoqlValue("SELECT+Count()+from+Account+Where+name+=\'"+sAccountName+"\'", "totalSize");
		if(Integer.parseInt(sAccCount)==0) {
			String sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccountName+"\"}");
			ExtentManager.logger.log(Status.INFO, "Account has been created through rest web service with Name: "+sAccountName+" and returned AccountId : "+sAccId);
			// Create Contact
			String sConId = restServices.restCreate("Contact?","{\"FirstName\": \"Stuart\", \"LastName\": \"Law\", \"AccountId\": \""+sAccId+"\"}");
			ExtentManager.logger.log(Status.INFO, "Contact has been created through rest web service with AccountId : "+sAccId+" ContactId : "+sConId);
		}
		
		//Create Process on Sahi
		boolean configSync=commonUtility.ProcessCheck(restServices, genericLib, sProcessName, sScriptName, sTestCaseID);
		
		//Login to GoApp
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		if(configSync) {
			ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		}
		
		//Perform Data Sync
		ph_MorePo.syncData(commonUtility);
		ph_CalendarPo.getEleCalendarBtn().click();
		
		//Creating Work Order from FSA
		ph_CreateNewPo.getEleCreateNew().click();
		ph_CalendarPo.getEleSelectProcessNewProcess("SFM_10570").click();
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_WorkOrderPo.geteleOrderStatus(), "Open");
		ph_CreateNewPo.getEleEmail().sendKeys(sEmail);
		ph_CreateNewPo.getEleAdd().click();
		ExtentManager.logger.log(Status.INFO, "New Process has been added for SFM_10570 process");
		
		//Creating Location from GoApp
		ph_CreateNewPo.getEleCreateNew().click();
		ph_CalendarPo.getEleSelectProcessNewProcess("SFM_Loc_10570").click();
		ph_CreateNewPo.getEleLocationName().sendKeys(sLocName+"\n");
		ph_WorkOrderPo.selectFromlookupSearchList(commonUtility,ph_CreateNewPo.getEleAccountFied(), sAccountName);
		ph_CreateNewPo.getEleAdd().click();
		ExtentManager.logger.log(Status.INFO, "New Location has been added for SFM_Loc_10570 process");
		
		//Creating Case from GoApp
		ph_CreateNewPo.getEleCreateNew().click();
		ph_CalendarPo.getEleSelectProcessNewProcess("SFM_Case_10570").click();
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getEleStatus(), "New");
		ph_WorkOrderPo.selectFromlookupSearchList(commonUtility,ph_CreateNewPo.getEleContactIDLookup(), sContactName);
		commonUtility.custScrollToElementAndClick(ph_CreateNewPo.getEleSubject());
		ph_CreateNewPo.getEleSubject().sendKeys(sCaseSubject);
		ph_CreateNewPo.getEleAdd().click();
		ExtentManager.logger.log(Status.INFO, "New Case has been added for SFM_Case_10570 process");
		
		//Creating IB from GoApp
		ph_CreateNewPo.getEleCreateNew().click();
		commonUtility.custScrollToElementAndClick(ph_CalendarPo.getEleSelectProcessNewProcess("SFM_IB_10570"));
		ph_WorkOrderPo.selectFromlookupSearchList(commonUtility,ph_CreateNewPo.getEleProductFied(), sProductName);
		ph_WorkOrderPo.selectFromlookupSearchList(commonUtility,ph_CreateNewPo.getEleAccountFied(), sAccountName);
		ph_CreateNewPo.getEleInstalledProductID().sendKeys(sIBName);
		ph_CreateNewPo.getEleAdd().click();
		ExtentManager.logger.log(Status.INFO, "New Installed Product has been added for SFM_IB_10570 process");
		
		//Creating Custom Object from GoApp
		ph_CreateNewPo.getEleCreateNew().click();
		commonUtility.custScrollToElementAndClick(ph_CalendarPo.getEleSelectProcessNewProcess("SFM_CO_10570"));
		ph_CreateNewPo.getEleAutoCustomObject().sendKeys(sCoName);
		ph_CreateNewPo.getEleAdd().click();
		ExtentManager.logger.log(Status.INFO, "New Custom Object has been added for SFM_CO_10570 process");
		
		//Check if Records are present in DB before Sync
		String sLocCountBeforeSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName+"\'", "totalSize");
		Assert.assertEquals(0, Integer.parseInt(sLocCountBeforeSync),"Before sync location count Expected : 0 , Actual : "+sLocCountBeforeSync);
		ExtentManager.logger.log(Status.PASS, "Before sync location count Expected : 0 , Actual : "+sLocCountBeforeSync);
		String sIBCountBeforeSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Installed_Product__c+Where+name+=\'"+sIBName+"\'", "totalSize");
		Assert.assertEquals(0, Integer.parseInt(sIBCountBeforeSync),"Before sync IB count Expected : 0 , Actual : "+sIBCountBeforeSync);
		ExtentManager.logger.log(Status.PASS, "Before sync Installed Product count Expected : 0 , Actual : "+sLocCountBeforeSync);
		String sCaseCountBeforeSync = restServices.restGetSoqlValue("SELECT+Count()+from+case+Where+Subject+=\'"+sCaseSubject+"\'", "totalSize");
		Assert.assertEquals(0, Integer.parseInt(sCaseCountBeforeSync),"Before sync case count Expected : 0 , Actual : "+sCaseCountBeforeSync);
		ExtentManager.logger.log(Status.PASS, "Before sync Case count Expected:0 , Actual:"+sCaseCountBeforeSync);
		String sCoCountBeforeSync = restServices.restGetSoqlValue("SELECT+Count()+from+Auto_Custom_Object2__c+Where+name+=\'"+sCoName+"\'", "totalSize");
		Assert.assertEquals(0, Integer.parseInt(sCoCountBeforeSync),"Before sync custom object count Expected : 0 , Actual : "+sCoCountBeforeSync);
		ExtentManager.logger.log(Status.PASS, "Before sync Custom Object count Expected:0 , Actual:"+sCoCountBeforeSync);
		String sWoCountBeforeSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Service_Order__c+Where+Email__c+=\'"+sEmail+"\'", "totalSize");
		Assert.assertEquals(0, Integer.parseInt(sWoCountBeforeSync),"Before sync service order count Expected : 0 , Actual : "+sWoCountBeforeSync);
		ExtentManager.logger.log(Status.PASS, "Before sync service order count Expected : 0 , Actual : "+sWoCountBeforeSync);
		
		//Perform Data Sync
		ph_WorkOrderPo.getBtnClose().click();
		ph_MorePo.getEleMoreBtn().click();
		ph_MorePo.syncData(commonUtility);
		
		//Check if Records are present in DB after Sync
		String sLocCountAfterSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName+"\'", "totalSize");
		Assert.assertEquals(Integer.parseInt(sLocCountBeforeSync)+1, Integer.parseInt(sLocCountAfterSync),"After sync location count Expected : "+Integer.parseInt(sLocCountBeforeSync)+1
				+", Actual : "+sLocCountAfterSync);
		ExtentManager.logger.log(Status.PASS, "After sync location count Expected:"+Integer.parseInt(sLocCountBeforeSync)+1+", Actual:"+sLocCountAfterSync);
		String sIBCountAfterSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Installed_Product__c+Where+name+=\'"+sIBName+"\'", "totalSize");
		Assert.assertEquals(Integer.parseInt(sIBCountBeforeSync)+1, Integer.parseInt(sIBCountAfterSync),"After sync IB count Expected : "+Integer.parseInt(sIBCountBeforeSync)+1
				+", Actual : "+sIBCountAfterSync);
		ExtentManager.logger.log(Status.PASS, "After sync Installed Product count Expected : "+Integer.parseInt(sIBCountBeforeSync)+1+", Actual : "+sIBCountAfterSync);
		String sCaseCountAfterSync = restServices.restGetSoqlValue("SELECT+Count()+from+case+Where+Subject+=\'"+sCaseSubject+"\'", "totalSize");
		Assert.assertEquals(Integer.parseInt(sCaseCountBeforeSync)+1, Integer.parseInt(sCaseCountAfterSync),"After sync case count Expected : "+Integer.parseInt(sCaseCountBeforeSync)+1
				+", Actual : "+sCaseCountAfterSync);
		ExtentManager.logger.log(Status.PASS, "After sync Case count Expected : "+Integer.parseInt(sCaseCountBeforeSync)+1+", Actual : "+sCaseCountAfterSync);
		String sCoCountAfterSync = restServices.restGetSoqlValue("SELECT+Count()+from+Auto_Custom_Object2__c+Where+name+=\'"+sCoName+"\'", "totalSize");
		Assert.assertEquals(Integer.parseInt(sCoCountBeforeSync)+1, Integer.parseInt(sCoCountAfterSync),"After sync custom object count Expected : "+Integer.parseInt(sCoCountBeforeSync)+1
				+", Actual : "+sCoCountAfterSync);
		ExtentManager.logger.log(Status.PASS, "After sync Custom Object count Expected : "+Integer.parseInt(sCoCountBeforeSync)+1+", Actual : "+sCoCountAfterSync);
		String sWoCountAfterSync = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Service_Order__c+Where+Email__c+=\'"+sEmail+"\'", "totalSize");
		Assert.assertEquals(Integer.parseInt(sWoCountBeforeSync)+1, Integer.parseInt(sWoCountAfterSync),"After sync service order count Expected : "+Integer.parseInt(sWoCountBeforeSync)+1
				+", Actual : "+sWoCountAfterSync);
		ExtentManager.logger.log(Status.PASS, "After sync service order count Expected : "+Integer.parseInt(sWoCountBeforeSync)+1+", Actual : "+sWoCountAfterSync);
	}

}
