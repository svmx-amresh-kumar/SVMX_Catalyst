//@Author: Harish.CS
package com.ge.fsa.tests.tablet;

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
	
	String sTestCaseID = "RS_10571";
	String sScriptName = "RS_10571_prerequisite";
	String sWORecordID = null;
	String sWOName = null;
	String  sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sFieldServiceName = null;
	String sProbDesc = null;
//	int iValToIncrease = 0;
	int iValNoOfTimesAssigned = 0;
	
	@Test(retryAnalyzer=Retry.class)
	public void SCN_Opdoc_RS_10571() throws Exception {
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, sTestCaseID,"ExploreSearch");
		System.out.println(sExploreSearch);
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, sTestCaseID,"ExploreChildSearch");
		System.out.println(sExploreChildSearchTxt);
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sTestCaseID, "ProcessName");
		System.out.println(sFieldServiceName);
		sProbDesc = GenericLib.getExcelData(sTestCaseID,sTestCaseID, "ProbDesc");
		System.out.println("The Value is "+sProbDesc);
//		iValToIncrease = Integer.parseInt(GenericLib.getExcelData(sTestID,sTestID, "Increased"));
		
		//**********Create Processes on Sahi**********
		commonUtility.execSahi(genericLib, sScriptName, sTestCaseID);
		
		//**********Create Work Order with No of Times Assigned**********
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"Number__c\":\"10\"}");
		System.out.println(sWORecordID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);

		//************Login to FSA************
		loginHomePo.login(commonUtility, exploreSearchPo);	
		
		//************Perform Data Sync************
		toolsPo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		
		//************Perform Config Sync************
		toolsPo.configSync(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		
		//************Navigate to SFM************
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		
		Assert.assertEquals(workOrderPo.getScheduledDatevalue().getAttribute("value"),"");
		Assert.assertEquals(workOrderPo.getScheduledDatetimevalue().getAttribute("value"),"");
		Assert.assertEquals(workOrderPo.getProblemDescription().getAttribute("value"),null);
		iValNoOfTimesAssigned = Integer.parseInt(workOrderPo.getTxtNumber().getAttribute("value"));
		Assert.assertEquals(workOrderPo.getTxtNumber().getAttribute("value"),"10");
		commonUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.selectAction(commonUtility, "Auto_Regression_10571");
		workOrderPo.getEleDoneLnk().click();
//		commonsUtility.tap(workOrderPo.getEleDoneLnk());
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
//		Thread.sleep(GenericLib.iHighSleep);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		workOrderPo.selectAction(commonUtility, sFieldServiceName);
		DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
		Date date = new Date();
		String sCurrentDate = dateFormat.format(date);
//		System.out.println("Todays Date "+sCurrentDate);
		iValNoOfTimesAssigned = iValNoOfTimesAssigned+10;
		System.out.println("Value to increase "+iValNoOfTimesAssigned);
		Assert.assertEquals(Integer.parseInt(workOrderPo.getTxtNumber().getAttribute("value")),iValNoOfTimesAssigned);
		Assert.assertEquals(workOrderPo.getScheduledDatevalue().getAttribute("value"),"1/1/2019");
		Assert.assertTrue(workOrderPo.getScheduledDatetimevalue().getAttribute("value").contains(sCurrentDate));
//		System.out.println(workOrderPo.getProblemDescription().getAttribute("value"));
		Assert.assertEquals(workOrderPo.getProblemDescription().getText(),sProbDesc);
				
	}
}