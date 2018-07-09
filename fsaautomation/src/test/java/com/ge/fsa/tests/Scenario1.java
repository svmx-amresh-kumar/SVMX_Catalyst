/*
 *  @author MeghanaRao
 */
package com.ge.fsa.tests;
import org.testng.annotations.Test;
import java.io.IOException;
import org.testng.annotations.BeforeMethod;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.LoginHomePO;
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
		createNewPO = new CreateNewPO(driver);
//		restServices.getAccessToken();
//		//Creation of dynamic Work Order
//		sWOJsonData="{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";	
//		
//		sWorkOrderID=restServices.getWOORecordID(sWOJsonData);
//		sWOName =restServices.getWOName(sWorkOrderID);
//				
//		//Pre Login to app
//	    loginHomePo.login(commonsPo,exploreSearchPo);
	}
	
	@Test
	public void scenario1() throws Exception
	{
		
		System.out.println("Automation : "+createNewPO.getEleCreateNew().getLocation());
		commonsPo.tap(createNewPO.getEleCreateNew());
		commonsPo.tap(createNewPO.getEleCreateNewWorkOrder());
		Thread.sleep(2000);
		// Adding Value for Account
		commonsPo.tap(createNewPO.getEleClickAccountfield());
		commonsPo.lookupSearch("Acc2952018141658");
	
		// Adding Value for Contact
		commonsPo.tap(createNewPO.getEleClickContactfield());
		commonsPo.lookupSearch("X C");
		
		// Adding Value for Product
		commonsPo.tap(createNewPO.getEleClickProductfield());
		commonsPo.lookupSearch("BMW 1");
		
	}
	
}