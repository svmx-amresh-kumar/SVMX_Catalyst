package com.ge.fsa.tests.phone;

import java.time.Duration;

import javax.swing.event.SwingPropertyChangeSupport;

import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_SCN_ConfigSync_RS_10563 extends BaseLib {
	
	@Test(retryAnalyzer=Retry.class)
	public void Ph_RS_10563() throws Exception {
		
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		String sProcessName = "Auto_Reg_10563";
		String sOpDocProcessName = "Auto_OPDOC_10563";
		String sTestCaseID = "RS_10563_configSync_edit";
		String sScriptName = "appium/Scenario_RS_10563_configSync_edit.sah";
		String sScriptName1 = "appium/Scenario_RS_10563_configSync.sah";
		String sExploreSearch = "WO SEARCH";
		
		//*************** Execute Sahi Script ***************
		commonUtility.executeSahiScript(sScriptName1);	
//		commonUtility.executeSahiScript("appium/Scenario_RS_10561_ConfigSync_Alert_Post.sah");
//		
		lauchNewApp("false");
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		ph_MorePo.syncData(commonUtility);
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		//*************** Start of Scenario 1 ***************
		ph_ExploreSearchPo.geteleExploreIcn().click();
		Thread.sleep(3000);
		commonUtility.swipeGeneric("up");
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sExploreSearch).isDisplayed());
		ExtentManager.logger.log(Status.PASS, "Explore Search: "+sExploreSearch+" is displayed");
		//**************** End of Scenario 1 ****************
		//*************** Start of Scenario 2 ***************
		ph_ExploreSearchPo.getEleSearchListItem(sExploreSearch).click();
		try {
			ph_ExploreSearchPo.getEleSearchListItem("Work Orders").click();
		}
		catch (Exception e) {}
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName + "\\n");
		} else {
			ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName + "\n");
		}
		Thread.sleep(1000);
		ph_ExploreSearchPo.getEleSearchListItem(sWOName).click();
		ph_WorkOrderPo.selectAction(commonUtility, sProcessName); 
		Assert.assertTrue(ph_WorkOrderPo.getLblHeader().getText().equals(sProcessName));
		ExtentManager.logger.log(Status.PASS, "Process: "+sProcessName+" is displayed");
		//**************** End of Scenario 2 ****************
		//*************** Start of Scenario 3 ***************
		ph_WorkOrderPo.getBtnClose().click();
		ph_WorkOrderPo.selectAction(commonUtility, sOpDocProcessName);
		Assert.assertTrue(ph_WorkOrderPo.getLblHeader().getText().equals(sOpDocProcessName));
		ExtentManager.logger.log(Status.PASS, "Process: "+sProcessName+" is displayed");
		//**************** End of Scenario 3 ****************
		//*************** Start of Scenario 4 ***************
		ph_WorkOrderPo.getBtnFinalize().click();
		//********** Sahi script to Edit Processes **********
		commonUtility.executeSahiScript(sScriptName);
		Thread.sleep(10000);
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		ph_ExploreSearchPo.geteleExploreIcn().click();
		Thread.sleep(3000);
		commonUtility.swipeGeneric("up");
		ph_ExploreSearchPo.getEleSearchListItem(sExploreSearch).click();
		Thread.sleep(3000);
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Cases").isDisplayed());
		ExtentManager.logger.log(Status.PASS, "Newly added Parameter to WO Search : Cases is displayed after config sync");
		//**************** End of Scenario 4 ****************
		//*************** Start of Scenario 5 ***************
		ph_ExploreSearchPo.getEleSearchListItem("Work Orders").click();
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName + "\\n");
		} else {
			ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName + "\n");
		}
		Thread.sleep(3000);
		ph_ExploreSearchPo.getEleSearchListItem(sWOName).click();
		ph_WorkOrderPo.selectAction(commonUtility, sProcessName);
		commonUtility.custScrollToElement(ph_WorkOrderPo.getLblCity());
		Assert.assertTrue(ph_WorkOrderPo.getLblCity().isDisplayed());
		Assert.assertTrue(ph_WorkOrderPo.getLblCountry().isDisplayed());
		ExtentManager.logger.log(Status.PASS, "Newly added Fields to Process: City and Country is displayed after config sync");
		//**************** End of Scenario 5 ****************
	} 

}

