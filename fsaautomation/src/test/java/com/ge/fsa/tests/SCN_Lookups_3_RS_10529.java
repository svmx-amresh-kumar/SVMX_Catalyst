package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.WorkOrderPO;

public class SCN_Lookups_3_RS_10529 extends BaseLib {
	
	@Test
	public void RS_10529() throws InterruptedException, IOException{
		
	// Create Location
//	String sLocName = commonsPo.generaterandomnumber("Loc");
//	String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"SVMXC__Account__c\": \"Acc29082018095005\", \"Name\": \""+sLocName+"\", \"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}");
//	System.out.println("Loc Id IS "+sLocId);
				
	// Create Installed Product
//	String sIbName = commonsPo.generaterandomnumber("IB");
//	String sIbNum = commonsPo.generaterandomnumber("IBNum");
//	String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \""+sAccId+"\", \"Name\": \""+sIbName+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sIbNum+"\",\"SVMXC__Country__c\": \"India\", \"SVMXC__Site__c\": \""+sLocId+"\" }");
//    System.out.println("IB id is "+sIbId);
		
		String sProductName = "RSRegProduct03";
        restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
        String sProductName01 = "RSRegProduct04";
        restServices.restCreate("Product2?","{\"Name\":\""+sProductName01+"\",\"Description\":\"Hello World\" }");
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo); // To get the work Order and Products
//		toolsPo.configSync(commonsPo); // To get the SFM Wizard
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00002005", "Auto_Reg_10529");
		commonsPo.tap(workOrderPo.getElePartLnk());
		commonsPo.tap(commonsPo.getElesearchTap());
		commonsPo.getElesearchTap().clear();
		commonsPo.getElesearchTap().sendKeys(sProductName);
		commonsPo.tap(commonsPo.getElesearchButton());
		String sProductOptn = workOrderPo.getLblProductLookupOptns().getText();
		System.out.println(sProductOptn);
		assertEquals(sProductOptn, sProductName); // Covers Step 1 & 2
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
	}

}
