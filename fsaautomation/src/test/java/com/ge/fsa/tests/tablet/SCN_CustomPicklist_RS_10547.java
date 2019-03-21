package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
import com.ge.fsa.tablet.pageobjects.ExploreSearchPO;
import com.ge.fsa.tablet.pageobjects.WorkOrderPO;

import io.appium.java_client.ios.IOSElement;

/**
 * 
 * @author meghanarao
 *
 */
public class SCN_CustomPicklist_RS_10547 extends BaseLib {
	String sTestCaseID= "Scenario_10547";
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
	String sIbSerialNum =null;
	String sSheetName2 =null;
	String sSheetName3 =null;
	
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10547() throws Exception {
		System.out.println("SCN_CustomPicklist_RS_10547");
		loginHomePo.login(commonsUtility, exploreSearchPo);
		Thread.sleep(10000);
		
		// Create Installed Product
		sIBName = commonsUtility.generaterandomnumber("IB");
		sIbSerialNum = commonsUtility.generaterandomnumber("IBNum");
		String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"Name\": \""+sIBName+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sIbSerialNum+"\",\"SVMXC__Country__c\": \"India\"}");
		System.out.println("IB id is "+sIbId);
		Thread.sleep(1000);
		// To sync the Data
		toolsPo.syncData(commonsUtility);
		Thread.sleep(genericLib.iMedSleep);
		workOrderPo.navigatetoWO(commonsUtility, exploreSearchPo, "AUTOMATION SEARCH", "Installed Products", sIBName);	
		String sProcessname = "RS_10547CreateWOfromIB";// Standard SFM Process
		Thread.sleep(2000);
		workOrderPo.selectAction(commonsUtility,sProcessname);
		String[] sContollingPicklist_WO_001 = {"--None--","CP-011", "CP-012"};
		System.out.println(sContollingPicklist_WO_001.length);
		String[] sDependentPicklist_CP_001 = {"--None--","DP-0111"};
		String[] sControllingPicklist2 = {"--None--","CP-011"};
	
// ==========================================================================================================================================
		// To click the Record Type button and choosing the values
		commonsUtility.setPickerWheelValue(workOrderPo.getEleeleRecordTypeLst(), "WO_001");
		if(BaseLib.sOSName.toLowerCase().equals("android")) {
			commonsUtility.tap(workOrderPo.getEleeleControllingPicklist());
		}else {
			workOrderPo.getEleeleControllingPicklist().click();
		}	
		
//==============================================================================================
	
		String[] sExpectedValues = commonsUtility.getAllPicklistValues(commonsUtility, workOrderPo, sContollingPicklist_WO_001);
		for(int i=0;i<sContollingPicklist_WO_001.length;i++) {
			if(sExpectedValues[i].equals(sContollingPicklist_WO_001[i]))
					{
				ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "The Controlling Picklist Values match");
					}
			else
			{
				ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "The Controlling Picklist Values don't match");
			}
		
			}
	
//==============================================================================================
		// To click on the Controlling picklist value to the Dependent Picklist value
				Thread.sleep(10000);
				commonsUtility.tap(createNewPO.getEleSaveWorkOrdert());
				commonsUtility.setPickerWheelValue(workOrderPo.getEleeleControllingPicklist(), "CP-011");

				
	// For Dependent Picklist clicking and verifying the values
				
				if(BaseLib.sOSName.toLowerCase().equals("android")) 
				{
					commonsUtility.tap(workOrderPo.getEleeleDependentPicklist());
				}
				else {
					workOrderPo.getEleeleDependentPicklist().click();
				}		
//==============================================================================================
			
				String[] sExpectedValues2 = commonsUtility.getAllPicklistValues(commonsUtility, workOrderPo, sDependentPicklist_CP_001);
				for(int i=0;i<sDependentPicklist_CP_001.length;i++) {
					if(sExpectedValues2[i].equals(sDependentPicklist_CP_001[i]))
							{
						ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "The Controlling Picklist Values match");
							}
					else
					{
						ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "The Controlling Picklist Values don't match");
					}
				}
//=======================================================================================================
			// To select the Dependent Picklist value
					Thread.sleep(10000);
					commonsUtility.tap(createNewPO.getEleSaveWorkOrdert());
					commonsUtility.setPickerWheelValue(workOrderPo.getEleeleDependentPicklist(), "DP-0111");	
					
					Thread.sleep(20000);
					commonsUtility.tap(workOrderPo.getEleClickSave());
					toolsPo.syncData(commonsUtility);
//==============================================================================================================					
					
// To update the Work Order Order status
				
					String sObjectApi = "SVMXC__Service_Order__c";
				String sSqlWOID ="SELECT+id+from+SVMXC__Service_Order__c+Where+SVMXC__Component__c+=\'"+sIbId+"\'";					
					String sWOId =restServices.restGetSoqlValue(sSqlWOID,"Id"); 
					System.out.println(sWOId);
					String sWOJson="{\"SVMXC__Order_Status__c\":\"Open\"}";
					restServices.restUpdaterecord(sObjectApi,sWOJson,sWOId);
					toolsPo.syncData(commonsUtility);
//===============================================================================================================
		// To Edit the Work Order value and to verify in the Data Sync
		// To save the Work Order and verify the Values after the Edit Work Order is Selected
			commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
			String sProcessname2 = "RS_10547CustomPicklistUI";// Standard SFM Process
			Thread.sleep(2000);
			workOrderPo.selectAction(commonsUtility,sProcessname2);	
			if(BaseLib.sOSName.toLowerCase().equals("android")) {
				commonsUtility.tap(workOrderPo.getEleeleControllingPicklist());
			}else {
				workOrderPo.getEleeleControllingPicklist().click();
			}	
			Thread.sleep(10000);
			commonsUtility.tap(createNewPO.getEleSaveWorkOrdert());
			commonsUtility.setPickerWheelValue(workOrderPo.getEleeleControllingPicklist(), "CP-012");


//======================================================================================================
		// To verify the values at the Dependent Picklist
			String[] sDependentPicklist_CP_012 = {"--None--","DP-0112"};
			if(BaseLib.sOSName.toLowerCase().equals("android")) {
				commonsUtility.tap(workOrderPo.getEleeleDependentPicklist());
			}else {
				workOrderPo.getEleeleDependentPicklist().click();
			}	
			
			
			String[] sExpectedValues3 = commonsUtility.getAllPicklistValues(commonsUtility, workOrderPo, sDependentPicklist_CP_012);
			for(int i1=0;i1<sDependentPicklist_CP_012.length;i1++) {
				if(sExpectedValues3[i1].equals(sDependentPicklist_CP_012[i1]))
						{
					ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "The Dependent Picklist Values match");
						}
				else
				{
					ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "The Dependent Picklist Values don't match");
				}
			}
			
			Thread.sleep(10000);
			commonsUtility.tap(createNewPO.getEleSaveWorkOrdert());
			commonsUtility.setPickerWheelValue(workOrderPo.getEleeleDependentPicklist(), "DP-0112");	

			commonsUtility.tap(workOrderPo.getEleClickSave());

		
		}



}
