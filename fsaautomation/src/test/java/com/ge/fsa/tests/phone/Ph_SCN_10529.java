package com.ge.fsa.tests.phone;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_SCN_10529 extends BaseLib {
	
	String sTestCaseID = "RS_10529";
	String sScriptName = "Scenario_10529";
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
	String sCountry = "Italy";
	String sProcessName = "Auto_Regression_10529";
	
	@Test//(retryAnalyzer=Retry.class)
	public void RS_10529() throws Exception {
		
//		commonUtility.execSahi(genericLib, sScriptName, sTestCaseID);
		
		// Create Product without Description
		String sProductName = "AshProd";
		String sProdCount = restServices.restGetSoqlValue("SELECT+Count()+from+Product2+Where+name+=\'"+sProductName+"\'", "totalSize");
		if(Integer.parseInt(sProdCount)==0) {
			restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		}
		
		// Create Product with Description
		String sProductName01 = "HarryProd";
		String sProdCount01 = restServices.restGetSoqlValue("SELECT+Count()+from+Product2+Where+name+=\'"+sProductName01+"\'", "totalSize");
		if(Integer.parseInt(sProdCount01)==0) {
			restServices.restCreate("Product2?","{\"Name\":\""+sProductName01+"\",\"Description\":\"HarryProd Desc\" }");
		}
		
		// Create Location without Country
		String sLocName = "HarryLoc";
		String sLocCount = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName+"\'", "totalSize");
		if(Integer.parseInt(sLocCount)==0) {
		restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName+"\"}");
		}
		
		// Create Location with Country
		String sLocName01 = "AshLoc";
		String sLocCount01 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName01+"\'", "totalSize");
		if(Integer.parseInt(sLocCount01)==0) {
			restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName01+"\",\"SVMXC__Country__c\": \""+sCountry+"\"}");
		}
		
		// Create Work Order
     	String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
//		ph_MorePo.syncData(commonUtility);
//		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearch, "WO-00013467", sProcessName);
		// ************Start of Scenario 1****************
		commonUtility.gotToTabHorizontal(ph_WorkOrderPo.getStringParts());
		ph_WorkOrderPo.getElePartLnk().click();
		String sProductCount = restServices.restGetSoqlValue("SELECT+Count()+from+Product2+Where+Description+=null", "totalSize");
//		Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sProductCount));
		// ************End of Scenario 1******************
		// ************Start of Scenario 2****************
		ph_WorkOrderPo.getElelookupsearch().sendKeys(sProductName);
		Assert.assertTrue(ph_WorkOrderPo.getLkpEle(sProductName).isDisplayed());
		// ************End of Scenario 2******************
		// ************Start of Scenario 3****************
		ph_WorkOrderPo.getBtnClose().click();
		ph_WorkOrderPo.getEleOverViewTab().click();
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getTxtProblemDescription());
		ph_WorkOrderPo.getTxtProblemDescription().sendKeys("HarryProd Desc");
		commonUtility.gotToTabHorizontal(ph_WorkOrderPo.getStringParts());
		ph_WorkOrderPo.getElePartLnk().click();
		Assert.assertTrue(ph_WorkOrderPo.getLkpEle(sProductName01).isDisplayed());
		// ************End of Scenario 3******************
		// ************Start of Scenario 4****************
		ph_WorkOrderPo.getBtnClose().click();
		ph_WorkOrderPo.getEleOverViewTab().click();
		ph_WorkOrderPo.getTxtSite().click();
		ph_WorkOrderPo.getElelookupsearch().sendKeys(sLocName);
		Assert.assertTrue(ph_WorkOrderPo.getLkpEle(sLocName).isDisplayed());
		// ************End of Scenario 4******************
		// ************Start of Scenario 5****************
		ph_WorkOrderPo.getBtnClose().click();
		ph_WorkOrderPo.getEleOverViewTab().click();
		ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getCountryPicklst(), sCountry);
		ph_WorkOrderPo.getTxtSite().click();
		String sLocItaCount = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+SVMXC__Country__c+='"+sCountry+"'", "totalSize");
		Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sLocItaCount));
		Thread.sleep(3000000);
		// ************End of Scenario 5******************
		// ************Start of Scenario 6****************
} 	

}
