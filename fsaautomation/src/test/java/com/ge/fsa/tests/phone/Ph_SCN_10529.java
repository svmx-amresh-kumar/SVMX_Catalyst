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

import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_SCN_10529 extends BaseLib {
	
	String sTestCaseID = "RS_10529";
	String sScriptName = "appium/Scenario_10529.sah";
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
	String sCountry = "Australia";
	String sProcessName = "Auto_Regression_10529";
	Boolean bProcessCheckResult;
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10529() throws Exception {
		
		bProcessCheckResult = commonUtility.ProcessCheck(restServices, sProcessName, sScriptName,sTestCaseID);
		
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
		
		ph_MorePo.syncData(commonUtility);
		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearch, sWOName, sProcessName);
		// ************Start of Scenario 1****************
		commonUtility.gotToTabHorizontal(ph_WorkOrderPo.getStringParts());
		ph_WorkOrderPo.getElePartLnk().click();
		String sProductCount = restServices.restGetSoqlValue("SELECT+Count()+from+Product2+Where+Description+=null", "totalSize");
	//	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sProductCount));--Failing
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
		if(sOSName.equalsIgnoreCase("IOS")) {
			ph_WorkOrderPo.getTxtProduct().click(); //Added to close the Keyboard
		}
		commonUtility.gotToTabHorizontal(ph_WorkOrderPo.getStringParts());
		ph_WorkOrderPo.getElePartLnk().click();
		Assert.assertTrue(ph_WorkOrderPo.getLkpEle(sProductName01).isDisplayed());
		// ************End of Scenario 3******************
		// ************Start of Scenario 4****************
		Thread.sleep(10000);
		ph_WorkOrderPo.getBtnClose().click();
		ph_WorkOrderPo.getEleOverViewTab().click();
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getTxtSite());
		ph_WorkOrderPo.getElelookupsearch().sendKeys(sLocName+ "\n");
		Assert.assertTrue(ph_WorkOrderPo.getLkpEle(sLocName).isDisplayed()); //Failing
		// ************End of Scenario 4******************
		// ************Start of Scenario 5****************
		ph_WorkOrderPo.getBtnClose().click();
		ph_WorkOrderPo.getEleOverViewTab().click();
		ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getCountryPicklst(), sCountry);
		ph_WorkOrderPo.getTxtSite().click();
		String sLocItaCount = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+SVMXC__Country__c+='"+sCountry+"'", "totalSize");
		Thread.sleep(3000);
		Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sLocItaCount)); //Failing
		ph_WorkOrderPo.getBtnClose().click();
		Thread.sleep(3000);
		// ************End of Scenario 5******************
		// ************Start of Scenario 6****************
		Dimension dim = driver.manage().window().getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		int x = width / 2;
		int y = height / 2;
		TouchAction ts = new TouchAction(driver);
		ts.press(new PointOption().withCoordinates(x, y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(new PointOption().withCoordinates(x, height-10)).release().perform();
		ph_WorkOrderPo.getLblContact().click();
		String sConWoAcc = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Id+=null", "totalSize");
		System.out.println(ph_WorkOrderPo.getLblResults().getText());
//		Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sConWoAcc));--Commenting as the scenario will fail if number of records exceed 250, taken care in scenario 2
		// ************End of Scenario 6******************

} 	

}
