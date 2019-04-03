// Author: Harish.CS
package com.ge.fsa.tests.tablet;

import java.io.IOException;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Sanity4_PIQ extends BaseLib{
	
	@Test(retryAnalyzer=Retry.class)
	public void scenario4Test() throws Exception {
		genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah");
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");

		// Create Account
		String sAccName = commonUtility.generaterandomnumber("Acc");
		String sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccName+"\" }");
		System.out.println("Account Id Is "+sAccId);
		//String accName1 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sAccId+"\'", "name");
		
		// Create Location
		String sLocName = commonUtility.generaterandomnumber("Loc");
		String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"SVMXC__Account__c\": \""+sAccId+"\", \"Name\": \""+sLocName+"\", \"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}");
		System.out.println("Loc Id IS "+sLocId);
		
		// Create Installed Product
		String sIbName = commonUtility.generaterandomnumber("IB");
		String sIbNum = commonUtility.generaterandomnumber("IBNum");
		String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \""+sAccId+"\", \"Name\": \""+sIbName+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sIbNum+"\",\"SVMXC__Country__c\": \"India\", \"SVMXC__Site__c\": \""+sLocId+"\" }");
		System.out.println("IB id is "+sIbId);
		
		// Create Work Order
		String sWoID  = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Company__c\": \""+sAccId+"\",\"SVMXC__Site__c\": \""+sLocId+"\",\"SVMXC__Component__c\":\""+sIbId+"\"}");
		System.out.println("Work Order Id Is "+ sWoID);
		String woName = restServices.restGetSoqlValue("SELECT+Name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWoID+"\'", "Name");
		System.out.println("Work Order Name Is "+ woName);
		
		loginHomePo.login(commonUtility, exploreSearchPo);
		toolsPo.configSync(commonUtility);
		toolsPo.syncData(commonUtility);
		Thread.sleep(GenericLib.iLowSleep);
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", woName, "Open tree view");
		
		Assert.assertTrue(workOrderPo.getEleOnTreeView(sAccName).isDisplayed(),"Account not displayed in Tree View");
		ExtentManager.logger.log(Status.PASS,"Account displayed on Tree View");
		Assert.assertTrue(workOrderPo.getEleOnTreeView(sLocName).isDisplayed(),"Location not displayed in Tree View");
		ExtentManager.logger.log(Status.PASS,"Location displayed on Tree View");
		Assert.assertTrue(workOrderPo.getEleOnTreeView(sIbName).isDisplayed(),"Installed Product not displayed in Tree View");
		ExtentManager.logger.log(Status.PASS,"Installed Product displayed on Tree View");
		Thread.sleep(GenericLib.iHighSleep);
	
		
	}

}
