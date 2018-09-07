
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.json.JSONArray;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.CreateNewPO;

public class RS_10556_mapping_svmx extends BaseLib {

	int iWhileCnt = 0;
	String sTestIBID = null;
	String sObjectIBID =null ;
	
	String sIBname=null ;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sSqlAccQuery=null;
	String sObjectProID=null;
	String sObjectApi = null;
	String sJsonData = null;
	//String sAccountName = "Proforma04092018185618account";
	String sAccountName =null;
	String sFieldServiceName = null;
	//String sproductname = "Proforma04092018185618product";
	String sproductname =null;
	//String sInstalledproductID="RS_10557_InstalledProduct_Auto";
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	String sIBLastModifiedBy=null;
	String sObjectlocationID=null;
String Location=null;
	
	WebElement productname=null;
	
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test(enabled = true)
	public void RS_10556_mapping_svmx_literals() throws Exception {
		
		sDeviceDate = driver.getDeviceTime().split(" ");
		
		String sProformainVoice = commonsPo.generaterandomnumber("AUTO");
		String sTestIB="RS-10556_mapping";
		sTestIBID = sProformainVoice;
		
	/*	
		
		// Create product
		sJsonData = "{\"Name\": \""+sTestIBID+""+"product\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectProID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectProID+"\'";				
		sproductname  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sproductname);
		//create location
		sObjectApi = "SVMXC__Site__c?";
		sJsonData = "{\"Name\": \""+sTestIBID+""+"Location\", \"SVMXC__Street__c\": \"#4566\", \"SVMXC__Country__c\": \"India\", \"SVMXC__Zip__c\": \"560008\"}";
		sObjectlocationID=restServices.restCreate(sObjectApi,sJsonData);
		String sSqllocQuery = "SELECT+name+from+SVMXC__Site__c+Where+id+=\'"+sObjectlocationID+"\'";				
		Location =restServices.restGetSoqlValue(sSqllocQuery,"Name"); 
		//sProductName1="v1";
		System.out.println(Location);
		*/
		//read from file
		sExploreSearch = GenericLib.getExcelData(sTestIB, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestIB, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestIB, "ProcessName");
		String sworkordernumber=GenericLib.getExcelData(sTestIB, "WorkOrder Number");
	
		
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			//config sync
			//toolsPo.configSync(commonsPo);
			//Thread.sleep(GenericLib.iMedSleep);
			
			//datasync
			//toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			calendarPO.openWofromCalendar(commonsPo, sworkordernumber);
			
			workOrderPo.selectAction(commonsPo,sFieldServiceName);
			
			Thread.sleep(GenericLib.iMedSleep);
			
			
			
	
		
	
			/*//add new line for parts
			commonsPo.tap(workOrderPo.getElePartLnk());
			commonsPo.tap(workOrderPo.getEleDoneBtn());
			
			//Add new line for labor
			commonsPo.tap(workOrderPo.getEleAddLaborLnk());
			commonsPo.tap(workOrderPo.getEleDoneBtn());
			*/
			
			//validating mapped values before save on workorder
			
			String fetchedOrderStatus =workOrderPo.geteleOrderStatusvaluelbl().getAttribute("value");
			System.out.println(fetchedOrderStatus);
			Assert.assertTrue(fetchedOrderStatus.equals(sAccountName), "Account value mapped is not displayed");
			
			String fetchedaccount =workOrderPo.getAccountvalue().getAttribute("value");
			System.out.println(fetchedaccount);
			Assert.assertTrue(fetchedaccount.equals(sAccountName), "Account value mapped is not displayed");
			
			
			String fetchedproduct =workOrderPo.getProductvalue().getAttribute("value");
			System.out.println(fetchedproduct);
			Assert.assertTrue(fetchedproduct.equals(sproductname), "product value mapped is not displayed");
			
			String fetchedcomponent =workOrderPo.getcomponentvalue().getAttribute("value");
			System.out.println(fetchedcomponent);
			Assert.assertTrue(fetchedcomponent.equals(sIBname), "component value mapped is not displayed");
			
			
			
			String fetchedScheduledDate =workOrderPo.getScheduledDatevalue().getAttribute("value");
			System.out.println(fetchedScheduledDate);
			Assert.assertTrue(fetchedScheduledDate.equals("29.08.18"), "ScheduledDate value mapped is not displayed");
			
			String fetchedScheduledDatetime =workOrderPo.getScheduledDatetimevalue().getAttribute("value");
			System.out.println(fetchedScheduledDatetime);
		//	Assert.assertTrue(fetchedScheduledDatetime.equals(sformattedDatetime), "ScheduledDatetime value mapped is not displayed");
			ExtentManager.logger.log(Status.PASS,"Work Order  Mapping is Successful before save");
			
			
			
			
			
			
			
			
			
			commonsPo.tap(workOrderPo.openpartsontap());
			//Thread.sleep(GenericLib.iHighSleep);
			String fetchedlocation =workOrderPo.getElePartsLocation().getAttribute("value");
			System.out.println(fetchedlocation);
			commonsPo.tap(workOrderPo.getEleDoneBtn());
			Assert.assertTrue(fetchedlocation.equals(Location), "location value mapped is not displayed");
			
			commonsPo.tap(workOrderPo.openLaborontap());
			String fetchedstartdateandtime =workOrderPo.getStartDateandTime().getAttribute("value");
			System.out.println(fetchedstartdateandtime);
			commonsPo.tap(workOrderPo.getEleDoneBtn());
			String getscheduleddatetime= workOrderPo.getScheduledDatetimevalue().getAttribute("value");
			Assert.assertTrue(fetchedstartdateandtime.equals(getscheduleddatetime), "date required value mapped is not displayed");
			
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful before save");
			commonsPo.tap(workOrderPo.getEleSaveLnk());
			
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			
			String sSoqlQuery = "SELECT+Id+from+SVMXC__Installed_Product__c+Where+SVMXC__Company__c+=\'"+sObjectAccID+"\'+AND+SVMXC__Product__c+=\'"+sObjectProID+"\'";
			restServices.getAccessToken();
			String sIBID = restServices.restGetSoqlValue(sSoqlQuery,"Id");
			System.out.println(sIBID);
				
			
			
			// Collecting the Work Order number from the Server.
			String sSoqlQuerywo = "SELECT+name+from+SVMXC__Service_Order__c+Where+SVMXC__Site__c+=\'"+sObjectlocationID+"\'";
			restServices.getAccessToken();
			String sworkOrdername = restServices.restGetSoqlValue(sSoqlQuerywo,"Name");
			System.out.println(sworkOrdername);
		
		String sSoqlscheduleddatewo= "SELECT+SVMXC__Scheduled_Date_Time__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sworkOrdername+"\'";
		String sSoqlQueryscheduleddatewo = restServices.restGetSoqlValue(sSoqlscheduleddatewo, "SVMXC__Scheduled_Date_Time__c");
		
	
		
			//Collecting the parts from the Server.
			JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Requested_Location__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Start_Date_and_Time__c+ = null+and+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkOrdername+"\')");
			String sLocationid = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Requested_Location__c");
			System.out.println("****************"+sLocationid);
			String LocationQuery = "SELECT+Name+from+SVMXC__Site__c+where+id=\'"+sLocationid+"\'";
			String soqlpartName  =restServices.restGetSoqlValue(LocationQuery,"Name"); 
			assertEquals(Location, soqlpartName);
			//Collecting the labor from the Server.
			JSONArray sJsonArraylabor = restServices.restGetSoqlJsonArray("SELECT+SVMXC__Start_Date_and_Time__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Requested_Location__c+ = null+and+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"+sworkOrdername+"\')");
			String sstartdatetime = restServices.getJsonValue(sJsonArraylabor, "SVMXC__Start_Date_and_Time__c");
			System.out.println("****************"+sstartdatetime);
			assertEquals(sSoqlQueryscheduleddatewo, sstartdatetime);
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful After save");
		
	
	
	}

}
