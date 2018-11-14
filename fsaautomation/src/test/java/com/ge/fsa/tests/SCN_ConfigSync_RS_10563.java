package com.ge.fsa.tests;

import java.io.IOException;

import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;

public class SCN_ConfigSync_RS_10563 extends BaseLib {
	
	@Test
	public void RS_10563() throws InterruptedException, IOException {
		
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFMWithIcon(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sWOName, "Auto_Reg_10563");
		Thread.sleep(GenericLib.iHighSleep);
		
	}

}
