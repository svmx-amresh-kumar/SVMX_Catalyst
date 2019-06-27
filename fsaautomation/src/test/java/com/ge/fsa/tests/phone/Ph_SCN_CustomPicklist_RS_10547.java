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
import com.ge.fsa.pageobjects.phone.Ph_WorkOrderPO;
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
		String sProcessname = "RS_10547CreateWOfromIB";
		
		boolean configSync=commonUtility.ProcessCheck(restServices, genericLib, sProcessname, "SCN_Explore_RS_10548_prerequisite", "Custom Picklist");

		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		if(configSync) {
			ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		}

		// Create Installed Product
		sIBName = commonUtility.generateRandomNumber("IB");
		sIbSerialNum = commonUtility.generateRandomNumber("IBNum");
		String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?", "{\"Name\": \"" + sIBName
				+ "\", \"SVMXC__Serial_Lot_Number__c\": \"" + sIbSerialNum + "\",\"SVMXC__Country__c\": \"India\"}");
		ExtentManager.logger.log(Status.INFO, "Installed Product has been created through rest web service with Name : "+sIBName+" SerialNumber : "+sIbSerialNum+
				" and returned InstalledProductId : "+sIbId);
		
		// To sync the Data
		ph_MorePo.syncData(commonUtility);
		ph_WorkOrderPo.navigatetoWO(commonUtility, ph_ExploreSearchPo, "AUTOMATION SEARCH", "Installed Products",sIBName);
		ExtentManager.logger.log(Status.INFO,"Navigated to Installed Products of number "+sIBName);
		// Standard SFM Process
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		ExtentManager.logger.log(Status.INFO, "selected action "+sProcessname+" in Installed Products.");
		String[] sContollingPicklist_WO_001 = { "--None--", "CP-011", "CP-012" };
		System.out.println(sContollingPicklist_WO_001.length);
		String[] sDependentPicklist_CP_001 = { "--None--", "DP-0111" };
		String[] sControllingPicklist2 = { "--None--", "CP-011" };

		// To click the Record Type button and choosing the values
		ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getEleRecordTypeID(), "WO_001");
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleControllingPicklist());

		// ==============================================================================================

		String[] sControllingActualValues = commonUtility.getAllPicklistValues(commonUtility,
				sContollingPicklist_WO_001);
		if (Arrays.equals(sContollingPicklist_WO_001, sControllingActualValues)) {
			ExtentManager.logger.log(Status.PASS, "Controlling picklist values are matching. Expected : "+Arrays.toString(sControllingPicklist2)+", Actual : "+
										Arrays.toString(sControllingActualValues));
		} else {
			ExtentManager.logger.log(Status.FAIL, "Controlling picklist values are not matching. Expected : "+Arrays.toString(sControllingPicklist2)+", Actual : "+
					Arrays.toString(sControllingActualValues));
		}

		// To click on the Controlling picklist value to the Dependent Picklist value
		ph_WorkOrderPo.getEleDropDownValue("CP-011").click();

		// For Dependent Picklist clicking and verifying the values

		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleDependentPicklist());

		String[] sDependentActualValues = commonUtility.getAllPicklistValues(commonUtility,
				sDependentPicklist_CP_001);
		if (Arrays.equals(sDependentPicklist_CP_001, sDependentActualValues)) {
			ExtentManager.logger.log(Status.PASS, "Dependent picklist values are matching for CP-011. Expected : "+Arrays.toString(sDependentPicklist_CP_001)+", Actual : "+
					Arrays.toString(sDependentActualValues));
		} else {
			ExtentManager.logger.log(Status.FAIL, "Dependent picklist values are not matching for CP-011. Expected : "+Arrays.toString(sDependentPicklist_CP_001)+", Actual : "+
					Arrays.toString(sDependentActualValues));
		}
		
		// To select the Dependent Picklist value
		ph_WorkOrderPo.getEleDropDownValue("DP-0111").click();
		ph_WorkOrderPo.getElesave().click();
		ExtentManager.logger.log(Status.INFO, "Installed Product saved successfully.");

		ph_MorePo.syncData(commonUtility);

		// To update the Work Order Order status

		String sObjectApi = "SVMXC__Service_Order__c";
		String sSqlWOID = "SELECT+id+from+SVMXC__Service_Order__c+Where+SVMXC__Component__c+=\'" + sIbId + "\'";
		String sWOId = restServices.restGetSoqlValue(sSqlWOID, "Id");
		System.out.println(sWOId);
		String sWOJson = "{\"SVMXC__Order_Status__c\":\"Open\"}";
		restServices.restUpdaterecord(sObjectApi, sWOJson, sWOId);
		ExtentManager.logger.log(Status.INFO, "Updating the status to Open though webservice for InstalledProduct : "+sIbId);
		ph_MorePo.syncData(commonUtility);
		
		// To Edit the Work Order value and to verify in the Data Sync
		// To save the Work Order and verify the Values after the Edit Work Order is
		// Selected
		ph_ExploreSearchPo.geteleExploreIcn().click();
		String sProcessname2 = "RS_10547CustomPicklistUI";// Standard SFM Process
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname2);
		ExtentManager.logger.log(Status.INFO, "selected action "+sProcessname2+" in Installed Products.");
		ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getEleControllingPicklist(), "CP-012");

		// To verify the values at the Dependent Picklist
		String[] sDependentPicklist_CP_012 = { "--None--", "DP-0112" };
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleDependentPicklist());

		String[] sDependentActualValues1 = commonUtility.getAllPicklistValues(commonUtility,
				sDependentPicklist_CP_012);
		if (Arrays.equals(sDependentPicklist_CP_012, sDependentActualValues1)) {
			ExtentManager.logger.log(Status.PASS, "Dependent picklist values are matching for CP-012. Expected : "+Arrays.deepToString(sDependentPicklist_CP_012)+", Actual : "+
					Arrays.toString(sDependentActualValues1));
		} else {
			ExtentManager.logger.log(Status.PASS, "Dependent picklist values are not matching for CP-012. Expected : "+Arrays.deepToString(sDependentPicklist_CP_012)+", Actual : "+
					Arrays.toString(sDependentActualValues1));
		}
		ph_WorkOrderPo.getEleDropDownValue("DP-0112").click();

		ph_WorkOrderPo.getElesave().click();
		ExtentManager.logger.log(Status.INFO, "Installed Product saved successfully.");
	}

}
