/*
 *  @author Vinod Tharavath
 *  SCN_SourceObjectUpdate_RS-10544 Verify Source Object Update
 *  
 *  PENDING TASKS ---
 *  
 *  === MAKE SURE URL,Phone,Email - custom fields are created in the ORG before running.
 *  === DateTime and date field literals after timezone is fixed.
 * 
 */

package com.ge.fsa.tests.phone;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_SourceObjectUpdate_RS_10544 extends BaseLib {
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal = null;
	String sEditProcessName = null;
	String sProductName = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectAccID = null;
	String sSqlAccQuery = null;
	String sAccountName = null;
	String sUsageLine = null;
	String sRecordTypeId = null;
	String sworkDetail = null;
	Boolean bProcessCheckResult = false;
	String sScriptName = "RS_10544_Source_Object_Update";
	// Source object update values

	String sBillingType = null;
	String sBillingTypeSOU = "Warranty";
	String sOrderStatus = "Open";
	String sOrderStatusSOU = "Open";
	String sIdleTime = null;
	String sIdleTimeSOU = "30";
	String sScheduledDate = null;
	String[] sScheduledDateSOU = null;
	String sScheduledDateTime = null;
	String sScheduledDateTimeSou = "8/28/2018 02:42";
	String sNoofTimesAssigned = null;
	String sNooftimesAssignedSOU = "2";
	String sProformaInvoice = null;
	String sProblemDescriptionSOU = "Source Object Updated";
	String sAccountSOU = null;
	String sURLSOU = "www.servicemax.com";
	String sPhoneSOU = "9886098860";
	String sEmailSOU = "automation@qa.com";
	Boolean bBooleanSOU = true;

	// For ServerSide Validations
	String schecklistStatus = "Completed";
	String sSheetName = null;
	String sProformainVoice = null;
	String sTestIB = null;
	String sTestIBID = null;
	String sProductId = null;

	// Fetching Client SOU to variables
	String sClientBillingTypeSOU = null;
	String sClientEleNoOfTimesAssignedSOU = null;
	String sClientsAccountName = null;
	String sClientURLSOU = null;
	String sClientPhone = null;
	String sClientEmail = null;
	String sClientProblemDesc = null;

	public void prerequisites() throws Exception {
		sSheetName = "RS_10544";
		System.out.println("SCN_SourceObject_UPDATE_RS10544");

		sProformainVoice = commonUtility.generateRandomNumber("Account");
		sTestIB = "RS-10544_SOU";
		sTestIBID = sProformainVoice;
		
		sScheduledDateSOU = driver.getDeviceTime().split(" ");
		System.out.println(sScheduledDateSOU);
		sTestCaseID = "SCN_SourceObjectUpdate_RS_10544";
		sCaseWOID = "Data_SCN_SourceObjectUpdate_RS_10544";

		// Reading from the Excel sheet
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ProcessName");
		sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "EditProcessName");

		// Account Creation
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \"" + sTestIBID + "\"}";
		sObjectAccID = restServices.restCreate(sObjectApi, sJsonData);
		sSqlAccQuery = "SELECT+name+from+Account+Where+id+=\'" + sObjectAccID + "\'";
		sAccountName = restServices.restGetSoqlValue(sSqlAccQuery, "Name");
		System.out.println(sAccountName);

		// Work Order Creation
		sWorkOrderID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"Low\"}");
		System.out.println(sWorkOrderID);
		sWOName = restServices.restGetSoqlValue(
				"SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWorkOrderID + "\'", "Name");
		System.out.println("WO no =" + sWOName);

		// Creating Product from API
		sProductName = "AUTO_RS10544";
		restServices.restCreate("Product2?", "{\"Name\":\"" + sProductName + "\" }");
		sProductId = restServices.restGetSoqlValue("SELECT+Id+from+Product2+Where+Name+=\'" + sProductName + "\'",
				"Id");
		System.out.println(sProductId);

		// Getting record type usage Usage/Consumption for work detail
		sUsageLine = "Usage/Consumption";
		sRecordTypeId = restServices.restGetSoqlValue("SELECT+Id+from+RecordType+Where+Name+=\'" + sUsageLine + "\'",
				"Id");

		// Creating and associating a work detail to the work Order
		sworkDetail = restServices.restCreate("SVMXC__Service_Order_Line__c?",
				"{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Parts\",\"SVMXC__Service_Order__c\":\""
						+ sWorkOrderID + "\",\"RecordTypeId\":\"" + sRecordTypeId
						+ "\",\"SVMXC__Actual_Quantity2__c\":\"11\",\"SVMXC__Product__c\":\"" + sProductId + "\"}");// ,,
		System.out.println("work Detail");
		System.out.println(sworkDetail);

		bProcessCheckResult = commonUtility.ProcessCheck(restServices, genericLib, sFieldServiceName, sScriptName,
				sTestCaseID);

	}

	@Test()
	//@Test(retryAnalyzer=Retry.class)
	public void RS_10544() throws Exception {

		prerequisites();
		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		// Optional config based on process check
		ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
		ExtentManager.logger.log(Status.INFO, "WorkOrder dynamically created and used is :" + sWOName + "");

		// Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		// toolsPo.configSync(commonsUtility);

		// Navigation to WO
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sFieldServiceName);

		Thread.sleep(CommonUtility.iLowSleep);
		// Set the order status

		System.out.println("Waiting to set order status");
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_WorkOrderPo.geteleOrderStatus(), sOrderStatus);
		Thread.sleep(CommonUtility.iLowSleep);

		// SEtting Account
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_CreateNewPo.getEleAccountLookUp(), sAccountName);

		// Adding Parts - to be uncommented after defect fix.
		ph_WorkOrderPo.addParts(commonUtility, sProductName);

		ph_WorkOrderPo.getEleSaveLnk().click();
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getEleOverViewTab(), 3),
				"Overview tab is not displayed, might not have saved correctly please check!");
		ExtentManager.logger.log(Status.PASS, "OverView Tab is displayed post saving.");
		Thread.sleep(CommonUtility.iMedSleep);

		// Navigation to WO
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sEditProcessName);

		Thread.sleep(2000);
		sClientBillingTypeSOU = ph_CreateNewPo.getElebillingtype().getText();
		Assert.assertEquals(sClientBillingTypeSOU, sBillingTypeSOU,
				"Picklist source object update failed billing type not set to warranty");
		ExtentManager.logger.log(Status.PASS, "Picklist SOU Header sucessful in Client Expected: " + sBillingTypeSOU
				+ "  Actual : " + sClientBillingTypeSOU + "");
		
		String sScheduledDateSOU  = ph_WorkOrderPo.getEleScheduledDate().getText().trim();
		String sTodaysDate = ph_ChecklistPO.get_device_date(commonUtility).trim();
		Assert.assertEquals(sScheduledDateSOU, sTodaysDate,
				"Today source object update failed on Schedule Date");
		ExtentManager.logger.log(Status.PASS, "Today Date Expected: " + sTodaysDate
				+ "  Actual : " + sScheduledDateSOU + "");
		
		String sScheduledDateTimeSOU  = ph_WorkOrderPo.getEleScheduledDateTime().getText().trim();
		String dt = sTodaysDate;  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		Date datev = (Date) sdf.parse(dt);
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(dt));
		c.add(Calendar.DATE, 1);  // number of days to add
		String sTomsDate = sdf.format(c.getTime());  // dt is now the new date
		
		Assert.assertTrue(sScheduledDateTimeSOU.contains(sTomsDate), 
				"Today source object update failed on Schedule Date");
		ExtentManager.logger.log(Status.PASS, "Tommorrow Date Expected: " + sTomsDate
				+ "  Actual : " + sScheduledDateTimeSOU + "");
	
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleURL());
		sClientEleNoOfTimesAssignedSOU = ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getText();
		Assert.assertEquals(sClientEleNoOfTimesAssignedSOU, sNooftimesAssignedSOU,
				" No source Object update failed No Of Times is not set to 2");
		ExtentManager.logger.log(Status.PASS, "Number SOU Header sucessful in Client Expected : "
				+ sNooftimesAssignedSOU + " Actual: " + sClientEleNoOfTimesAssignedSOU + "");

		sClientsAccountName = ph_WorkOrderPo.getEleAccount().getText();
		Assert.assertEquals(sClientsAccountName, sAccountName,
				"Lookup Source Object update failed.Account is not being displayed");
		ExtentManager.logger.log(Status.PASS, "Look Up SOU Header sucessful in Client Expected :" + sAccountName
				+ " Actual : " + sClientsAccountName + "");

		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleEmail());
		sClientURLSOU = ph_WorkOrderPo.getEleURL().getText();
		Assert.assertEquals(sClientURLSOU, sURLSOU, "URL source update failed");
		ExtentManager.logger.log(Status.PASS,
				"URL SOU Header sucessful in Client Expected : " + sURLSOU + " Actual : " + sClientURLSOU + " ");

		sClientPhone = ph_WorkOrderPo.getElePhone().getText();
		Assert.assertEquals(sClientPhone, sPhoneSOU, "Phone source update failed");
		ExtentManager.logger.log(Status.PASS,
				"Phone SOU Header sucessful in Client Expected :" + sPhoneSOU + " Acutal:" + sClientPhone + "");

		sClientEmail = ph_WorkOrderPo.getEleEmail().getText();
		commonUtility.custScrollToElement(ph_WorkOrderPo.geteleProblemDescriptiontxt());
		Assert.assertEquals(sClientEmail, sEmailSOU, "Email source update failed");
		ExtentManager.logger.log(Status.PASS,
				"Email SOU Header sucessful in Client Expected : " + sEmailSOU + " Actual: " + sClientEmail + "");

		sClientProblemDesc = ph_WorkOrderPo.geteleProblemDescriptiontxt().getText();
		Assert.assertEquals(sClientProblemDesc, sProblemDescriptionSOU,
				" No source Object update failed for problem Description");
		ExtentManager.logger.log(Status.PASS, "Text Area SOU Header sucessful in Client Expected : "
				+ sProblemDescriptionSOU + " Actual: " + sClientProblemDesc + "");

		
		// to close out of work order
		ph_WorkOrderPo.getEleBackButton().click();

		ph_MorePo.syncData(commonUtility);

		restServices.getAccessToken();

		String sSoqlqueryWO = "Select+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName
				+ "'";
		String sSoqlNoOfTimes = "Select+SVMXC__NoOfTimesAssigned__c+from+SVMXC__Service_Order__c+Where+Name+=\'"
				+ sWOName + "'";
		String sSoqlSchedulesDate = "Select+SVMXC__Scheduled_Date__c+from+SVMXC__Service_Order__c+Where+Name+=\'"
				+ sWOName + "'";
		String sSoqlScheduledDateTime = "Select+SVMXC__Scheduled_Date_Time__c+from+SVMXC__Service_Order__c+Where+Name+=\'"
				+ sWOName + "'";
		String sSoqlEmail = "Select+Email__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		String sSoqlURL = "Select+URL__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		String sSoqlPhone = "Select+Phone__c+from+SVMXC__Service_Order__c+Where+Name+=\'" + sWOName + "'";
		String sSoqlProbDesc = "Select+SVMXC__Problem_Description__c+from+SVMXC__Service_Order__c+Where+Name+=\'"
				+ sWOName + "'";

		String sURLServer = restServices.restGetSoqlValue(sSoqlURL, "URL__c");
		Assert.assertEquals(sURLServer, sURLSOU, " Server URL source object update failed  not set in Server");
		ExtentManager.logger.log(Status.PASS, "Server URL Source Object Update  Header sucessful in Server");

		String sEmailServer = restServices.restGetSoqlValue(sSoqlEmail, "Email__c");
		Assert.assertEquals(sEmailServer, sEmailSOU, " Server Email source object update failed not set in Server");
		ExtentManager.logger.log(Status.PASS, "Server eEmail Source Object Update  Header sucessful in Server");

		String sPhoneserver = restServices.restGetSoqlValue(sSoqlPhone, "Phone__c");
		Assert.assertEquals(sPhoneserver, sPhoneSOU, " Server Phone  source object update failed not set in Server");
		ExtentManager.logger.log(Status.PASS, "Server Phone Source Object Update  Header sucessful in Server");

		String sBillTypeServer = restServices.restGetSoqlValue(sSoqlqueryWO, "SVMXC__Billing_Type__c");
		Assert.assertEquals(sBillTypeServer, sBillingTypeSOU,
				" Server Picklist source object update failed billing type not set to warranty in Server");
		ExtentManager.logger.log(Status.PASS, "Server Picklist Source Object Update  Header sucessful in Server");

		String sProbDescServer = restServices.restGetSoqlValue(sSoqlProbDesc, "SVMXC__Problem_Description__c");
		Assert.assertEquals(sProbDescServer, sProblemDescriptionSOU,
				" Server Problem Desc source object update failed billing type not set to warranty in Server");
		ExtentManager.logger.log(Status.PASS, "Server Problem Desc Source Object Update  Header sucessful in Server");

		String sNoOftimesServer = restServices.restGetSoqlValue(sSoqlNoOfTimes, "SVMXC__NoOfTimesAssigned__c");
		String sNoOftimesServer1 = sNoOftimesServer.substring(0, sNoOftimesServer.length() - 2);
		Assert.assertEquals(sNoOftimesServer1, sNoOftimesServer1,
				" Server Number source object update failed billing type not set to warranty in Server");
		ExtentManager.logger.log(Status.PASS, "Server Number Source Object Update  Header sucessful in Server");

		// String sScheduledDateServer =
		// restServices.restGetSoqlValue(sSoqlSchedulesDate,"SVMXC__Scheduled_Date__c");
		// String sScheduledDateTimeServer =
		// restServices.restGetSoqlValue(sSoqlScheduledDateTime,"SVMXC__Scheduled_Date_Time__c");

		// Validation of SOURCE OBJECT UPDATE AFTER SERVER Verification back in client

		ph_CalendarPo.getEleCalendarBtn().click();
		Thread.sleep(CommonUtility.iLowSleep);
		ph_ExploreSearchPo.geteleExploreIcn().click();
		// Navigation to WO
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName,
				sEditProcessName);

		Thread.sleep(2000);
		sClientBillingTypeSOU = ph_CreateNewPo.getElebillingtype().getText();
		Assert.assertEquals(sClientBillingTypeSOU, sBillingTypeSOU,
				"Picklist source object update failed billing type not set to warranty");
		ExtentManager.logger.log(Status.PASS, "Picklist SOU Header sucessful in Client Expected: " + sBillingTypeSOU
				+ "  Actual : " + sClientBillingTypeSOU + "");

		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleURL());
		sClientEleNoOfTimesAssignedSOU = ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getText();
		Assert.assertEquals(sClientEleNoOfTimesAssignedSOU, sNooftimesAssignedSOU,
				" No source Object update failed No Of Times is not set to 2");
		ExtentManager.logger.log(Status.PASS, "Number SOU Header sucessful in Client Expected : "
				+ sNooftimesAssignedSOU + " Actual: " + sClientEleNoOfTimesAssignedSOU + "");

		sClientsAccountName = ph_WorkOrderPo.getEleAccount().getText();
		Assert.assertEquals(sClientsAccountName, sAccountName,
				"Lookup Source Object update failed.Account is not being displayed");
		ExtentManager.logger.log(Status.PASS, "Look Up SOU Header sucessful in Client Expected :" + sAccountName
				+ " Actual : " + sClientsAccountName + "");

		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleEmail());
		sClientURLSOU = ph_WorkOrderPo.getEleURL().getText();
		Assert.assertEquals(sClientURLSOU, sURLSOU, "URL source update failed");
		ExtentManager.logger.log(Status.PASS,
				"URL SOU Header sucessful in Client Expected : " + sURLSOU + " Actual : " + sClientURLSOU + " ");

		sClientPhone = ph_WorkOrderPo.getElePhone().getText();
		Assert.assertEquals(sClientPhone, sPhoneSOU, "Phone source update failed");
		ExtentManager.logger.log(Status.PASS,
				"Phone SOU Header sucessful in Client Expected :" + sPhoneSOU + " Acutal:" + sClientPhone + "");

		sClientEmail = ph_WorkOrderPo.getEleEmail().getText();
		commonUtility.custScrollToElement(ph_WorkOrderPo.geteleProblemDescriptiontxt());
		Assert.assertEquals(sClientEmail, sEmailSOU, "Email source update failed");
		ExtentManager.logger.log(Status.PASS,
				"Email SOU Header sucessful in Client Expected : " + sEmailSOU + " Actual: " + sClientEmail + "");

		sClientProblemDesc = ph_WorkOrderPo.geteleProblemDescriptiontxt().getText();
		Assert.assertEquals(sClientProblemDesc, sProblemDescriptionSOU,
				" No source Object update failed for problem Description");
		ExtentManager.logger.log(Status.PASS, "Text Area SOU Header sucessful in Client Expected : "
				+ sProblemDescriptionSOU + " Actual: " + sClientProblemDesc + "");

	}

}
