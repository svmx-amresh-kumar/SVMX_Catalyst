/*
 *  @author MeghanaRao
 */
package com.ge.fsa.tests;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

import org.testng.annotations.BeforeMethod;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.LoginHomePO;
import com.ge.fsa.pageobjects.RecentItemsPO;
import com.ge.fsa.pageobjects.CalendarPO;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.CreateNewPO;
import com.ge.fsa.pageobjects.ToolsPO;
import com.ge.fsa.pageobjects.WorkOrderPO;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;


public class Scenario1 extends BaseLib
{
	GenericLib genericLib = null;
	RestServices restServices = null;
	LoginHomePO loginHomePo = null;
	ExploreSearchPO exploreSearchPo = null;	
	WorkOrderPO workOrderPo = null;
	CommonsPO commonsPo = null;
	CreateNewPO createNewPO = null;
	ToolsPO toolsPo=null;
	RecentItemsPO recenItemsPO = null;
	CalendarPO calendarPO = null;

	
	int iWhileCnt =0;
	String sTestCaseID=null; String sCaseWOID=null; String sCaseSahiFile=null;
	String sExploreSearch=null;String sWorkOrderID=null; String sWOJsonData=null;String sWOName=null; String sFieldServiceName=null; String sProductName1=null;String sProductName2=null; 
	String sActivityType=null;String sPrintReportSearch=null;
	@BeforeMethod
	public void initializeObject() throws IOException
	{	// Initialization of objects
		genericLib=new GenericLib();
		restServices = new RestServices();
		loginHomePo = new LoginHomePO(driver);
		exploreSearchPo = new ExploreSearchPO(driver);	
		workOrderPo = new WorkOrderPO(driver);
		toolsPo = new ToolsPO(driver);	
		commonsPo = new CommonsPO(driver);
		restServices = new RestServices();
		createNewPO = new CreateNewPO(driver);
		recenItemsPO = new RecentItemsPO(driver);
		calendarPO = new CalendarPO(driver);
		sPrintReportSearch = "Print Service Report";

	}
	
	@Test
	public void Scenario1Functions() throws Exception
	{

		String sproformainvoice = commonsPo.generaterandomnumber("Proforma");
		String seventSubject = commonsPo.generaterandomnumber("EventName");
		loginHomePo.login(commonsPo, exploreSearchPo);
		createNewPO.createWorkOrder(commonsPo,"Account47201811263","ContactAutomation 234567", "Product9876789", "Medium", "Loan", sproformainvoice);
		toolsPo.syncData(commonsPo);
		Thread.sleep(2000);
		String soqlquery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sproformainvoice+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restapisoql(soqlquery);	
		recenItemsPO.clickonWorkOrder(commonsPo, sworkOrderName);
		// To create a new Event for the given Work Order
		workOrderPo.createNewEvent(commonsPo,seventSubject, "Test Desscription");
		calendarPO.verifyworkorderCalendar(commonsPo, sworkOrderName);
		// To add Labor, Parts , Travel , Expense
		String sProcessname = "EditWoAutoTimesstamp";
		workOrderPo.selectAction(commonsPo,sProcessname);
		workOrderPo.addParts(commonsPo, workOrderPo,"Product9876789");
		workOrderPo.addLaborParts(commonsPo, workOrderPo, "Product9876789", "Calibration", sProcessname);
		workOrderPo.addTravel(commonsPo, workOrderPo, sProcessname);
		commonsPo.tap(workOrderPo.getEleClickSave());
		Thread.sleep(10000);
		workOrderPo.validateServiceReport(commonsPo, sPrintReportSearch, sworkOrderName);

	}
	

	
}