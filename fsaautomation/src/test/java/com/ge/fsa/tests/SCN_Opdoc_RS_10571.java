//@Author: Harish.CS
package com.ge.fsa.tests;

import java.io.IOException;

import javax.swing.SwingWorker;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class SCN_Opdoc_RS_10571 extends BaseLib{
	
	String sTestID = null;
	String sWORecordID = null;
	String sWOName = null;
	String  sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sFieldServiceName = null;
	
	
	private void preRequisite() throws Exception {
		
		// Create Work Order with No of Times Assigned
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__NoOfTimesAssigned__c\":\"10\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		
		// Invoking Sahi Script.
		genericLib.executeSahiScript("appium/SCN_SrctoTrgt_RS_10571_prerequisite.sah", sTestID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}
	
	@Test
	public void SCN_Opdoc_RS_10571() throws Exception {
		sTestID = "RS_10571"; 
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestID, sTestID,"ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestID,sTestID, "ProcessName");
	
		
		//Login to FSA
		loginHomePo.login(commonsPo, exploreSearchPo);
				
		//Perform Config Sync
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);

		//Perform Data Sync
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		//Navigate to SFM
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);		
				
	}
}