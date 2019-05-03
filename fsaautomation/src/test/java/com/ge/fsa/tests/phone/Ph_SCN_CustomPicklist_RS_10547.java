package com.ge.fsa.tests.phone;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;
import com.ge.fsa.pageobjects.tablet.WorkOrderPO;

import io.appium.java_client.ios.IOSElement;


public class Ph_SCN_CustomPicklist_RS_10547 extends BaseLib {
	String sTestCaseID = "Scenario_10547";
	String sAccountName = null;
	String sProductName10538 = null;
	String sProductName10533 = null;
	String sInstalledProduct10538 = null;
	String sworkOrderName = null;
	String sSCONName = null;
	String sContactName = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sIBName = null;
	String sIbSerialNum = null;
	String sSheetName2 = null;
	String sSheetName3 = null;

	@Test(retryAnalyzer = Retry.class)
	public void RS_10547() throws Exception {
		System.out.println("SCN_CustomPicklist_RS_10547");
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		// Create Installed Product
		sIBName = commonUtility.generaterandomnumber("IB");
		sIbSerialNum = commonUtility.generaterandomnumber("IBNum");
		String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?", "{\"Name\": \"" + sIBName
				+ "\", \"SVMXC__Serial_Lot_Number__c\": \"" + sIbSerialNum + "\",\"SVMXC__Country__c\": \"India\"}");
		System.out.println("IB id is " + sIbId);
		Thread.sleep(1000);
		// To sync the Data
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(genericLib.iMedSleep);
		ph_WorkOrderPo.navigatetoWO(commonUtility, ph_ExploreSearchPo, "AUTOMATION SEARCH", "Installed Products",
				sIBName);
		String sProcessname = "RS_10547CreateWOfromIB";// Standard SFM Process
		Thread.sleep(2000);
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		String[] sContollingPicklist_WO_001 = { "--None--", "CP-011", "CP-012" };
		System.out.println(sContollingPicklist_WO_001.length);
		String[] sDependentPicklist_CP_001 = { "--None--", "DP-0111" };
		String[] sControllingPicklist2 = { "--None--", "CP-011" };

		// ==========================================================================================================================================
		// To click the Record Type button and choosing the values
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleRecordTypeID());
		ph_WorkOrderPo.getEleDropDownValue("WO_001").click();
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleControllingPicklist());

		// ==============================================================================================

		String[] sExpectedValues = commonUtility.getAllPicklistValues(commonUtility,
				sContollingPicklist_WO_001);
		// for(int i=0;i<sContollingPicklist_WO_001.length;i++) {
		// if(sExpectedValues[i].equals(sContollingPicklist_WO_001[i]))
		// {
		// ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "The
		// Controlling Picklist Values match");
		// }
		// else
		// {
		// ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "The
		// Controlling Picklist Values don't match");
		// }
		//
		// }
		if (Arrays.equals(sContollingPicklist_WO_001, sExpectedValues)) {
			ExtentManager.logger.log(Status.PASS, "Testcase " + sTestCaseID + "The Controlling Picklist Values match");
		} else {
			ExtentManager.logger.log(Status.FAIL,
					"Testcase " + sTestCaseID + "The Controlling Picklist Values don't match");
		}

		// ==============================================================================================
		// To click on the Controlling picklist value to the Dependent Picklist value
		ph_WorkOrderPo.getEleDropDownValue("CP-011").click();

		// For Dependent Picklist clicking and verifying the values

		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleDependentPicklist());
		// ==============================================================================================

		String[] sExpectedValues2 = commonUtility.getAllPicklistValues(commonUtility,
				sDependentPicklist_CP_001);
//		for (int i = 0; i < sDependentPicklist_CP_001.length; i++) {
//			if (sExpectedValues2[i].equals(sDependentPicklist_CP_001[i])) {
//				ExtentManager.logger.log(Status.PASS,
//						"Testcase " + sTestCaseID + "The Controlling Picklist Values match");
//			} else {
//				ExtentManager.logger.log(Status.FAIL,
//						"Testcase " + sTestCaseID + "The Controlling Picklist Values don't match");
//			}
//		}
		if (Arrays.equals(sDependentPicklist_CP_001, sExpectedValues2)) {
			ExtentManager.logger.log(Status.PASS, "Testcase " + sTestCaseID + "The depending Picklist Values match");
		} else {
			ExtentManager.logger.log(Status.FAIL,
					"Testcase " + sTestCaseID + "The depending Picklist Values don't match");
		}
		// =======================================================================================================
		// To select the Dependent Picklist value
		ph_WorkOrderPo.getEleDropDownValue("DP-0111").click();
		ph_WorkOrderPo.getElesave().click();
		Thread.sleep(4000);
		ph_MorePo.syncData(commonUtility);
		// ==============================================================================================================

		// To update the Work Order Order status

		String sObjectApi = "SVMXC__Service_Order__c";
		String sSqlWOID = "SELECT+id+from+SVMXC__Service_Order__c+Where+SVMXC__Component__c+=\'" + sIbId + "\'";
		String sWOId = restServices.restGetSoqlValue(sSqlWOID, "Id");
		System.out.println(sWOId);
		String sWOJson = "{\"SVMXC__Order_Status__c\":\"Open\"}";
		restServices.restUpdaterecord(sObjectApi, sWOJson, sWOId);
		ph_MorePo.syncData(commonUtility);
		// ===============================================================================================================
		// To Edit the Work Order value and to verify in the Data Sync
		// To save the Work Order and verify the Values after the Edit Work Order is
		// Selected
		ph_ExploreSearchPo.geteleExploreIcn().click();
		String sProcessname2 = "RS_10547CustomPicklistUI";// Standard SFM Process
		Thread.sleep(2000);
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname2);
		Thread.sleep(2000);
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleControllingPicklist());
		Thread.sleep(2000);
		ph_WorkOrderPo.getEleDropDownValue("CP-012").click();
		// ======================================================================================================
		// To verify the values at the Dependent Picklist
		String[] sDependentPicklist_CP_012 = { "--None--", "DP-0112" };
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleDependentPicklist());

		String[] sExpectedValues3 = commonUtility.getAllPicklistValues(commonUtility,
				sDependentPicklist_CP_012);
//		for (int i1 = 0; i1 < sDependentPicklist_CP_012.length; i1++) {
//			if (sExpectedValues3[i1].equals(sDependentPicklist_CP_012[i1])) {
//				ExtentManager.logger.log(Status.PASS,
//						"Testcase " + sTestCaseID + "The Dependent Picklist Values match");
//			} else {
//				ExtentManager.logger.log(Status.FAIL,
//						"Testcase " + sTestCaseID + "The Dependent Picklist Values don't match");
//			}
//		}
		if (Arrays.equals(sDependentPicklist_CP_012, sExpectedValues3)) {
			ExtentManager.logger.log(Status.PASS, "Testcase " + sTestCaseID + "The depending Picklist Values match");
		} else {
			ExtentManager.logger.log(Status.FAIL,
					"Testcase " + sTestCaseID + "The depending Picklist Values don't match");
		}
		ph_WorkOrderPo.getEleDropDownValue("DP-0112").click();

		ph_WorkOrderPo.getElesave().click();
	}

}
