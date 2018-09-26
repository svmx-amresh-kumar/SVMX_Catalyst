package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.WorkOrderPO;

/**
 * 
 * @author meghanarao
 *
 */
public class SCN_GetPriceNotCovered_RS_10532 extends BaseLib {
	String sTestCaseID= "Scenario_10532";
	String sAccountName = null;
	String sProductName = null;
	String sworkOrderName = null;
	String sIBName = null;
	String sContactName = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;


	@Test(enabled = true)
	public void RS_10532() throws Exception {
	
		System.out.println("SCN_GetPriceNotCovered_RS_10532");
		loginHomePo.login(commonsPo, exploreSearchPo);
		// Have a config Sync
//		toolsPo.configSync(commonsPo);
//		// Do a Data sync
		toolsPo.syncData(commonsPo);
		// Get the Work Order from the sheet
		String sTestDataValue = "SCN_GetPrice_RS_10538";
		sAccountName = GenericLib.getExcelData(sTestDataValue,"Account Name");
		sProductName = GenericLib.getExcelData(sTestDataValue,"Product Name");
		System.out.println(sProductName);
		sIBName = GenericLib.getExcelData(sTestDataValue,"Installed Product Name");
		System.out.println(sIBName);	
		
		// To fetch the Account ID from the Name
		String sSoqlAccount = "SELECT+Id+from+Account+Where+Name+=\'"+sAccountName+"\'";
		restServices.getAccessToken();
		String sAccountID = restServices.restGetSoqlValue(sSoqlAccount,"Id");	
		
		// To fetch the Product ID from the Name
		String sSoqlProduct = "SELECT+Id+from+Product2+Where+Name+=\'"+sProductName+"\'";
		restServices.getAccessToken();
		String sProductID = restServices.restGetSoqlValue(sSoqlProduct,"Id");
		
		// To fetch the IB ID from the Name
		
		String sSoqlIB = "SELECT+Id+from+SVMXC__Installed_Product__c+Where+Name+=\'"+sIBName+"\'";
		restServices.getAccessToken();
		String sIBID = restServices.restGetSoqlValue(sSoqlIB,"Id");
				
		// To create a Work Order from the API
		String sWorkorderID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Company__c\": \""+sAccountID+"\", \"SVMXC__Component__c\": \""+sIBID+"\", \"SVMXC__Priority__c\": \"High\"}");
		System.out.println("Work Order Id"+sWorkorderID);
		String sSoqlWO = "SELECT+Name+from+SVMXC__Service_Order__c+Where+Id+=\'"+sWorkorderID+"\'";
		restServices.getAccessToken();
		String sWorkOrderName = restServices.restGetSoqlValue(sSoqlWO,"Name");
	}
}