/*
*@author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import org.json.JSONArray;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;



public class workBench extends BaseLib
{
	
	
	int iWhileCnt =0;
	String sTestCaseID="Scenario-1"; String sCaseWOID=null; String sCaseSahiFile=null;
	String sExploreSearch=null;String sWorkOrderID=null; String sWOJsonData=null;String sWOName=null; String sFieldServiceName=null; String sProductName1=null;String sProductName2=null; 
	String sActivityType=null;String sPrintReportSearch="Auto_PrintServiceReport";
	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sExpenseType = "Airfare";
	String sLineQty = "10.0";
	String slinepriceperunit = "1000";

	
@Test
public void Scenario1Test() throws Exception
{		


	loginHomePo.login(commonsPo, exploreSearchPo);String sworkOrderName = "WO-00001744";
	calendarPO.openWofromCalendar(commonsPo, sworkOrderName);
		workOrderPo.validateServiceReport(commonsPo, sPrintReportSearch, sworkOrderName);
		calendarPO.openWofromCalendar(commonsPo, sworkOrderName);
		workOrderPo.validateServiceReport(commonsPo, sPrintReportSearch, sworkOrderName);
		calendarPO.openWofromCalendar(commonsPo, sworkOrderName);
		workOrderPo.validateServiceReport(commonsPo, sPrintReportSearch, sworkOrderName);
		calendarPO.openWofromCalendar(commonsPo, sworkOrderName);
		workOrderPo.validateServiceReport(commonsPo, sPrintReportSearch, sworkOrderName);

	}
	

	
}