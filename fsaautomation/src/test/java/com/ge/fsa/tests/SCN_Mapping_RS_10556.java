/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10556"
 */
package com.ge.fsa.tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.CreateNewPO;

public class SCN_Mapping_RS_10556 extends BaseLib {

	int iWhileCnt = 0;
	String sTestIBID = null;
	String sObjectIBID =null ;
	
	//String sIBname=null ;
	//String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
//	String sSqlAccQuery=null;
	//String sObjectProID=null;
	//String sObjectApi = null;
	//String sJsonData = null;
	//String sAccountName = "Proforma04092018185618account";
	//String sAccountName =null;
	String sFieldServiceName = null;
	//String sproductname = "Proforma04092018185618product";
	//String sproductname =null;
	//String sInstalledproductID="RS_10557_InstalledProduct_Auto";
	//String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	//String sObjectlocationID=null;
//String Location=null;
String SFMBillingtype="Empowerment";
String SFMcontact="C10556_ Auto";
boolean SFMCustomerDown=true;
String SFMEmail="testsample@gmail.com";
String SFMNumber="11";
String SFMPhone="9902819683";
String SFMURL="www.motogp.com";
String SFMCurrency="46";
String SFMclosedby="Auto Tech";
String SFMSite="L10556_Auto";
String SFMScheduledDate="";
String SFMScheduledDatetime="";
String SFMProduct="P10556_Auto";
String SFMLineQty="17";
String SFMLinePerUnit="46";
String SFMWorkdiscription="Verifying Value Map for New Child Record (text)";
String SFMRecordType="Usage/Consumption";
String SFMlinetype="Parts";
String SFMdaterequired="";
boolean SFMIsBillable=true;
	WebElement productname=null;
	
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test(enabled = true)
	public void RS_10556() throws Exception {
		
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
			
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sworkordernumber, sFieldServiceName);

			
			/*calendarPO.openWofromCalendar(commonsPo, sworkordernumber);
			
			workOrderPo.selectAction(commonsPo,sFieldServiceName);
			*/
			Thread.sleep(GenericLib.iMedSleep);
			
			//to get orderstatus nd ordertype from workorder
			/* JSONArray sJsonArrayWO1 = restServices.restGetSoqlJsonArray("Select+SVMXC__Order_Status__c,+SVMXC__Order_Type__c+from+SVMXC__Service_Order__c+where+SVMXC__Service_Order__c.name=\'"+sworkordernumber+"\'");
				//String sordertype = restServices.getJsonValue(sJsonArrayWO1, "SVMXC__Order_Type__c");
				String sorderstatus = restServices.getJsonValue(sJsonArrayWO1, "SVMXC__Order_Status__c");
				
				System.out.println(":):):):):):):)");
			 //Validating before save
			String fetchedOrderStatus =workOrderPo.getEleOrderStatusCaseLst().getAttribute("value");
			System.out.println(fetchedOrderStatus);
			Assert.assertTrue(fetchedOrderStatus.equals(sorderstatus), "OrderStatus value mapped is not displayed");
			
			String fetchBillingType =workOrderPo.getEleBillingTypeLst().getAttribute("value");
			System.out.println(fetchBillingType);
			Assert.assertTrue(fetchBillingType.equals(SFMBillingtype), "BillingType value mapped is not displayed");
			
			String fetchedContact =workOrderPo.getTxtContact().getAttribute("value");
			System.out.println(fetchedContact);
			Assert.assertTrue(fetchedContact.equals(SFMcontact), "contact value mapped is not displayed");
			*/
			String fetchCustomerDown =workOrderPo.getCustomerDown().getText().toString();
			System.out.println(fetchCustomerDown);
			//Assert.assertEquals(fetchCustomerDown,SFMCustomerDown, "CustomerDown value mapped is not displayed");
			
		Thread.sleep(GenericLib.iVHighSleep);
			/*String fetchProblemDescription =workOrderPo.getProblemDescription().getText();
			System.out.println(fetchProblemDescription);
			Assert.assertTrue(fetchProblemDescription.equals(SFMBillingtype), "ProblemDescription value mapped is not displayed");
			
			String fetchEmail =workOrderPo.getEmailvalue().getAttribute("value");
			Assert.assertTrue(fetchEmail.equals(SFMEmail), "Email value mapped is not displayed");
			
			String fetchURL =workOrderPo.getURLvalue().getAttribute("value");
			Assert.assertTrue(fetchURL.equals(SFMURL), "URL value mapped is not displayed");
			
			String fetchNumber =workOrderPo.getNumbervalue().getAttribute("value");
			System.out.println(fetchNumber);
			Assert.assertTrue(fetchNumber.equals(SFMNumber), "number value mapped is not displayed");
			
			String fetchPhone =workOrderPo.getPhonevalue().getAttribute("value");
			Assert.assertTrue(fetchPhone.equals(SFMPhone), "Phone value mapped is not displayed");
			
			String fetchCurrency =workOrderPo.getCurrencyvalue().getAttribute("value");
			Assert.assertTrue(fetchCurrency.equals(SFMCurrency), "Currency value mapped is not displayed");
			
			
			String fetchclosedby =workOrderPo.getclosedby().getAttribute("value");
			Assert.assertTrue(fetchclosedby.equals(SFMclosedby), "closedby value mapped is not displayed");
			
			String fetchSite =workOrderPo.getTxtSite().getAttribute("value");
			Assert.assertTrue(fetchSite.equals(SFMSite), "fetchSite value mapped is not displayed");
			
			
			String fetchedScheduledDate =workOrderPo.getScheduledDatevalue().getAttribute("value");
			System.out.println(fetchedScheduledDate);
			//Assert.assertTrue(fetchedScheduledDate.equals("09.09.18"), "ScheduledDate value mapped is not displayed");
			
			String fetchedScheduledDatetime =workOrderPo.getScheduledDatetimevalue().getAttribute("value");
			System.out.println(fetchedScheduledDatetime);
		//	Assert.assertTrue(fetchedScheduledDatetime.equals(sformattedDatetime), "ScheduledDatetime value mapped is not displayed");
			ExtentManager.logger.log(Status.PASS,"Work Order  Mapping is Successful before save");
			
			//add new line for parts
			
			String sProductName="P10556_Auto";
			
			workOrderPo.addParts(commonsPo, workOrderPo,sProductName);
			commonsPo.tap(workOrderPo.openpartsontap());
			
			//Verifying mapping before save
			String fetchedlocation =workOrderPo.getElePartsLocation().getAttribute("value");
			System.out.println(fetchedlocation);
			Assert.assertTrue(fetchedlocation.equals(SFMSite), "location value mapped is not displayed");
			
			String fetchedpart =workOrderPo.getElePartLaborLkUp().getAttribute("value");
			System.out.println(fetchedpart);
			Assert.assertTrue(fetchedpart.equals(SFMProduct), "part value mapped is not displayed");
			
			String fetchlineqty =workOrderPo.getEleLineQtyTxtFld().getAttribute("value");
			System.out.println(fetchlineqty);
			Assert.assertTrue(fetchlineqty.equals(SFMLineQty), "lineqty value mapped is not displayed");
			
			String fetchlinepriceperunit =workOrderPo.getEleLinePerUnitTxtFld().getAttribute("value");
			System.out.println(fetchlinepriceperunit);
			Assert.assertTrue(fetchlinepriceperunit.equals(SFMLinePerUnit), "LinePerUnit value mapped is not displayed");
			
			String fetchworkdescription =workOrderPo.getEleWODesMappedTxt().getText();
			System.out.println(fetchworkdescription);
			Assert.assertTrue(fetchworkdescription.equals(SFMWorkdiscription), "workdescription value mapped is not displayed");
			
			String fetchRecordType =workOrderPo.getRecordType().getAttribute("value");
			System.out.println(fetchRecordType);
			Assert.assertTrue(fetchRecordType.equals(SFMRecordType), "RecordType value mapped is not displayed");
			
			String fetchlinetype =workOrderPo.getLineType().getAttribute("value");
			System.out.println(fetchlinetype);
			Assert.assertTrue(fetchlinetype.equals(SFMlinetype), "linetype value mapped is not displayed");
			
			String fetchdaterequired =workOrderPo.getDateRequired().getAttribute("value");
			System.out.println(fetchdaterequired);
		//	Assert.assertTrue(fetchdaterequired.equals("09.09.18"), "DateRequired value mapped is not displayed");
			
			String fetchedstartdateandtime =workOrderPo.getStartDateandTime().getAttribute("value");
			System.out.println(fetchedstartdateandtime);
		//	Assert.assertTrue(fetchedstartdateandtime.equals("08.09.18 00:00"), "startdateandtime required value mapped is not displayed");
			
			String fetchclosedbyinpart =workOrderPo.getclosedby().getAttribute("value");
			System.out.println(fetchclosedbyinpart);
			Assert.assertTrue(fetchclosedbyinpart.equals(SFMclosedby), "closedby required value mapped is not displayed");
			
			String fetchcancledby =workOrderPo.getCanceledBy().getAttribute("value");
			System.out.println(fetchcancledby);
			Assert.assertTrue(fetchcancledby.equals(SFMclosedby), "CanceledBy required value mapped is not displayed");
			
			boolean fetchisBillable =workOrderPo.getIsBillable().isEnabled();
			System.out.println(fetchisBillable);
			Assert.assertEquals(fetchisBillable,true, "Billable value mapped is not displayed");
			
			ExtentManager.logger.log(Status.PASS,"Work details  Mapping is Successful before save");
			commonsPo.tap(workOrderPo.getEleDoneBtn());*/
			
		

	
	}

}
