// Author: Harish.CS
package com.ge.fsa.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

public class Scenario4Test extends BaseLib{
	
	@Test
	public void scenario4_test() throws IOException, InterruptedException {
		
		// Create Account
		String accName = commonsPo.generaterandomnumber("Acc");
		String sAccId = restServices.restCreate("Account?","{\"Name\": \""+accName+"\" }");
//		System.out.println("Account Id Is "+sAccId);
		//String accName1 = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sAccId+"\'", "name");
		
		// Create Location
		String locName = commonsPo.generaterandomnumber("Loc");
		String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"SVMXC__Account__c\": \""+sAccId+"\", \"Name\": \""+locName+"\", \"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}");
//		System.out.println("Loc Id IS "+sLocId);
		
		// Create Installed Product
		String ibName = commonsPo.generaterandomnumber("IB");
		String ibNum = commonsPo.generaterandomnumber("IBNum");
		String ibId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \""+sAccId+"\", \"Name\": \""+ibName+"\", \"SVMXC__Serial_Lot_Number__c\": \""+ibNum+"\",\"SVMXC__Country__c\": \"India\", \"SVMXC__Site__c\": \""+sLocId+"\" }");
//		System.out.println("IB id is "+ibId);
		
		// Create Work Order
		String woID  = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Company__c\": \""+sAccId+"\",\"SVMXC__Site__c\": \""+sLocId+"\",\"SVMXC__Component__c\":\""+ibId+"\"}");
		System.out.println("Work Order Id Is "+ woID);
		String woName = restServices.restGetSoqlValue("SELECT+Name+from+SVMXC__Service_Order__c+Where+id+=\'"+woID+"\'", "Name");
//		System.out.println("Work Order Name Is "+ woName);
		
		loginHomePo.login(commonsPo, exploreSearchPo);
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", woName, "Open tree view");
		
		Assert.assertTrue(workOrderPo.getEleOnTreeView(accName).isDisplayed(),"Account not displayed in Tree View");
		NXGReports.addStep("Account displayed on Tree View", LogAs.PASSED, null);
		Assert.assertTrue(workOrderPo.getEleOnTreeView(locName).isDisplayed(),"Location not displayed in Tree View");
		NXGReports.addStep("Location displayed on Tree View", LogAs.PASSED, null);
		Assert.assertTrue(workOrderPo.getEleOnTreeView(ibName).isDisplayed(),"Installed Product not displayed in Tree View");
		NXGReports.addStep("Installed Product displayed on Tree View", LogAs.PASSED, null);
		
		
	}

}
