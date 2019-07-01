
package com.ge.fsa.tests.phone;

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
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_Opdoc_RS_10571 extends BaseLib{
	
	String sTestCaseID = "RS_10571";
	String sScriptName = "RS_10571_prerequisite";
	String sWORecordID = null;
	String sWOName = null;
	String  sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sFieldServiceName = null;
	String sProbDesc = null;
	int iValNoOfTimesAssigned = 0;
	
	@Test(retryAnalyzer=Retry.class)
	public void Ph_SCN_Opdoc_RS_10571() throws Exception {
		sExploreSearch = CommonUtility.readExcelData(GenericLib.sTestDataFile, sTestCaseID,"ExploreSearch");
		System.out.println(sExploreSearch);
		sExploreChildSearchTxt = CommonUtility.readExcelData(GenericLib.sTestDataFile, sTestCaseID,"ExploreChildSearch");
		System.out.println(sExploreChildSearchTxt);
		sFieldServiceName = CommonUtility.readExcelData(GenericLib.sTestDataFile,sTestCaseID, "ProcessName");
		sProbDesc = CommonUtility.readExcelData(GenericLib.sTestDataFile,sTestCaseID, "ProbDesc");
		System.out.println("The Value is "+sProbDesc);
		
		//**********Create Processes on Sahi**********
//		commonUtility.execSahi(genericLib, sScriptName, sTestCaseID);
		
		//**********Create Work Order with No of Times Assigned**********
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"Number__c\":\"10\"}");
		System.out.println(sWORecordID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);

		//************Login to Go************
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		//************Perform Data Sync************
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		
		//************Perform Config Sync************
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		//************Navigate to SFM************
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);
		commonUtility.custScrollToElement(ph_WorkOrderPo.getTxtProblemDescription());
		if(sOSName.equalsIgnoreCase("ios")) {
			Assert.assertEquals(ph_WorkOrderPo.getTxtScheduledDate().getAttribute("value").trim(),"");
			Assert.assertEquals(ph_WorkOrderPo.getTxtScheduledDateTime().getAttribute("value").trim(),"");
			Assert.assertEquals(ph_WorkOrderPo.getTxtProblemDescription().getAttribute("value"),null);
		}
		else {
		Assert.assertEquals(ph_WorkOrderPo.getTxtScheduledDate().getAttribute("text").trim(),"");
		Assert.assertEquals(ph_WorkOrderPo.getTxtScheduledDateTime().getAttribute("text").trim(),"");
		Assert.assertEquals(ph_WorkOrderPo.getTxtProblemDescription().getAttribute("text"),"");
		}
		commonUtility.custScrollToElement(ph_WorkOrderPo.getTxtNumber());
		if(sOSName.equalsIgnoreCase("ios")) {
			iValNoOfTimesAssigned = Integer.parseInt(ph_WorkOrderPo.getTxtNumber().getAttribute("value"));
			Assert.assertEquals(ph_WorkOrderPo.getTxtNumber().getAttribute("value"),"10");
		}
		else {
			iValNoOfTimesAssigned = Integer.parseInt(ph_WorkOrderPo.getTxtNumber().getAttribute("text"));
			Assert.assertEquals(ph_WorkOrderPo.getTxtNumber().getAttribute("text"),"10");	
		}
		
		ph_WorkOrderPo.getBtnClose().click();
		ph_WorkOrderPo.selectAction(commonUtility, "Auto_Regression_10571");
		Thread.sleep(3000);
		ph_WorkOrderPo.getBtnFinalize().click();
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);
		DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
		Date date = new Date();
		String sCurrentDate = dateFormat.format(date);
//		System.out.println("Todays Date "+sCurrentDate);
		iValNoOfTimesAssigned = iValNoOfTimesAssigned+10;
		commonUtility.custScrollToElement(ph_WorkOrderPo.getTxtProblemDescription());
		if(sOSName.equalsIgnoreCase("ios")) {
			Assert.assertEquals(ph_WorkOrderPo.getTxtScheduledDate().getAttribute("value").trim(),"1/1/2019");
			Assert.assertTrue(ph_WorkOrderPo.getTxtScheduledDateTime().getAttribute("value").trim().contains(sCurrentDate));
			Assert.assertEquals(ph_WorkOrderPo.getTxtProblemDescription().getAttribute("value"),sProbDesc);
		}
		else {
			Assert.assertEquals(ph_WorkOrderPo.getTxtScheduledDate().getAttribute("text").trim(),"1/1/2019");
			Assert.assertTrue(ph_WorkOrderPo.getTxtScheduledDateTime().getAttribute("text").trim().contains(sCurrentDate));
			Assert.assertEquals(ph_WorkOrderPo.getTxtProblemDescription().getAttribute("text"),sProbDesc);
		}
		commonUtility.custScrollToElement(ph_WorkOrderPo.getTxtNumber());
		if(sOSName.equalsIgnoreCase("ios")) {
			Assert.assertEquals(Integer.parseInt(ph_WorkOrderPo.getTxtNumber().getAttribute("value")),iValNoOfTimesAssigned);
		}
		else {
			Assert.assertEquals(Integer.parseInt(ph_WorkOrderPo.getTxtNumber().getAttribute("text")),iValNoOfTimesAssigned);
		}		
	}
}
