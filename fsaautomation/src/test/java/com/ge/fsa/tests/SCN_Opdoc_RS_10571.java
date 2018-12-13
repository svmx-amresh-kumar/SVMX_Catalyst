//@Author: Harish.CS
package com.ge.fsa.tests;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_Opdoc_RS_10571 extends BaseLib{
	
	String sTestID = null;
	String sWORecordID = null;
	String sWOName = null;
	String  sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sFieldServiceName = null;
	String sProbDesc = null;
//	int iValToIncrease = 0;
	int iValNoOfTimesAssigned = 0;
	
	
	private void preRequisite() throws Exception {
		
		// Create Work Order with No of Times Assigned
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__NoOfTimesAssigned__c\":\"10\"}");
		System.out.println(sWORecordID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
		
		// Invoking Sahi Script.
		genericLib.executeSahiScript("appium/RS_10571_prerequisite.sah", sTestID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}
	
	@Test(retryAnalyzer=Retry.class)
	public void SCN_Opdoc_RS_10571() throws Exception {
		sTestID = "RS_10571"; 
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestID, sTestID,"ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestID,sTestID, "ProcessName");
		sProbDesc = GenericLib.getExcelData(sTestID,sTestID, "ProbDesc");
		System.out.println("The Value is "+sProbDesc);
//		iValToIncrease = Integer.parseInt(GenericLib.getExcelData(sTestID,sTestID, "Increased"));
		preRequisite();
		
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
		
		Assert.assertEquals(workOrderPo.getScheduledDatevalue().getAttribute("value"),"");
		Assert.assertEquals(workOrderPo.getScheduledDatetimevalue().getAttribute("value"),"");
		Assert.assertEquals(workOrderPo.getProblemDescription().getAttribute("value"),null);
		iValNoOfTimesAssigned = Integer.parseInt(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value"));
		Assert.assertEquals(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value"),"10");
		commonsPo.tap(workOrderPo.getEleClickSave());
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.selectAction(commonsPo, "10571_sample_test");
		workOrderPo.getEleDoneLnk().click();
//		commonsPo.tap(workOrderPo.getEleDoneLnk());
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
//		Thread.sleep(GenericLib.iHighSleep);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		workOrderPo.selectAction(commonsPo, sFieldServiceName);
		DateFormat dateFormat = new SimpleDateFormat("M/d/yy");
		Date date = new Date();
		String sCurrentDate = dateFormat.format(date);
//		System.out.println("Todays Date "+sCurrentDate);
		Assert.assertEquals(workOrderPo.getScheduledDatevalue().getAttribute("value"),"1/1/19");
		Assert.assertTrue(workOrderPo.getScheduledDatetimevalue().getAttribute("value").contains(sCurrentDate));
		System.out.println(workOrderPo.getProblemDescription().getAttribute("value"));
		Assert.assertEquals(workOrderPo.getProblemDescription().getText(),sProbDesc);
		iValNoOfTimesAssigned = iValNoOfTimesAssigned+10;
		System.out.println("Value to increase "+iValNoOfTimesAssigned);
		Assert.assertEquals(Integer.parseInt(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value")),iValNoOfTimesAssigned);
		
		
				
	}
}