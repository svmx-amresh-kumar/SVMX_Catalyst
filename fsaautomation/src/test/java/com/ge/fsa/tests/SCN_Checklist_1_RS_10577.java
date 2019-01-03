/*
 *  @author Vinod Tharavath
 *  SCN_Checklist_1_RS-10577 Verify Source Object Update in Checklists
 *  
 *  -Date validation in Appium is not done. Will revisit once TimeZone is sorted out.
 * 
 *  
 *  Validates number,text,date,datetime and picklist source object update
 *   with pre-filled questions in fsa and server through api
 */
package com.ge.fsa.tests;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.RestServices;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.ExploreSearchPO;

public class SCN_Checklist_1_RS_10577 extends BaseLib{
	String sTestCaseID= null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal =null;
	String sEditProcessName = null;
	String sSheetName =null;
	String sNoOfTimesAssigned = null;
	String time = null;
	String sWORecordID = null;
	String sProcessCheck = null;
	Boolean bProcessCheckResult;
	
	//checklist q's set--
			String sTextq = "AUTO_Which City you are from?";
			String sPicklistq = "AUTO_What is the Order Status?";
			String sNumberq = "AUTO_What is the IdleTime?";
			String sDateq = "AUTO_What is the Scheduled Date?";
			String sDatetimeq = "AUTO_What is the ScheduledDatetime?";
			String sTextAns = null;	String sPicklistAns = null;String sNumberAns; String sDateAns=null;String sDatetimeAns=null;				
			
	//Checklist PreFill Values
			String sCityPrefill = "Delhi";
			String sOrderStatusPrefill = "Open";
			String sIdleTimePrefill = "30";
			String sScheduleddatePrefill = "8/28/18";
			String sScheduledDateTimePrefill = "8/28/18 02:42";
	
	//Source object update values
			
			String sBillingType = null;String sBillingTypeSOU= "Loan";
			String sOrderStatus= null;String sOrderStatusSOU = "Open";
			String sIdleTime = null;String sIdleTimeSOU= "30";
			String sScheduledDate = null;
			String[] sScheduledDateSOU=null; 
			String sScheduledDateTime = null;String sScheduledDateTimeSou ="8/28/18 02:42";				
			String sNoofTimesAssigned = null; String sNooftimesAssignedSOU = "2";
			String sProformaInvoice = null; String sProformaInvoiceSOU="Source Object Updated";
			
	//For ServerSide Validations
			String sChecklistQuery = null;
			String sChecklistQueryval = null;
			String ChecklistAnsjson = null;
			String sSoqlqueryWO = null;
			String sSoqlProforma = null;
			String sSoqlNoOfTimes = null;
			String sBillTypeServer =null;
			String sProformaServer = null;
			String sNoOftimesServer = null;
			String sNoOftimesServer1 = null;
			String schecklistStatus = "Completed";
			String sProcessID = null;
			String sPrcessname1 = "vt";
			String sPrcname2 = "inactivetest";
			String sScriptName="Scenario_RS-10577_Checklist_SOU";
			
	public void prerequisites() throws Exception
	{
		sSheetName ="RS_10577";
		System.out.println("SCN_RS10577_Checklist_SOU");
		time = driver.getDeviceTime();
		System.out.println(time);
		sScheduledDateSOU=driver.getDeviceTime().split(" ");
		System.out.println(sScheduledDateSOU);
		sTestCaseID = "SCN_Checklist_1_RS-10577_SOU";
		sCaseWOID = "DATA_SCN_Checklist_1_RS-10577_SOU";
		

		//Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");
																 
	
		//Rest to Create Workorder
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\"}");
		System.out.println(sWORecordID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);		
		//sWOName = "WO-00005043";

		//incomplete case
		//ProcessCheck(sPrcname2, sScriptName, sTestCaseID);
		
		//Completed case
		//ProcessCheck(sChecklistName, sScriptName, sTestCaseID);

		//to create test
		//ProcessCheck(sPrcessname1, sScriptName, sTestCaseID);
		
		bProcessCheckResult =commonsPo.ProcessCheck(restServices, genericLib, sChecklistName, sScriptName, sTestCaseID);		
		/*genericLib.executeSahiScript("appium/Scenario_RS-10577_Checklist_SOU.sah",sTestCaseID);
		Assert.assertTrue(commonsPo.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID + "Sahi verification is successful");*/
	
	
	
} 
	
	 
@Test(retryAnalyzer=Retry.class)
	public void RS_10577() throws Exception {
		
		prerequisites();
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);	
	    toolsPo.OptionalConfigSync(toolsPo, commonsPo, bProcessCheckResult);
		
		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		//toolsPo.configSync(commonsPo);			
		
		//Navigation to WO
	    workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);							
		
		//Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);
		
		// Navigating to the checklist
		commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
			
		//System.out.println("validating pre filled text question is showing up");
		sTextAns = checklistPo.geteleChecklistAnswerTextArea(sTextq).getAttribute("value");
		Assert.assertTrue(sTextAns.equals(sCityPrefill), "Text question not Prefilled.");
		ExtentManager.logger.log(Status.PASS,"Text question prefilled sucessfully");
			
		sPicklistAns = checklistPo.geteleChecklistAnsPicklist(sPicklistq).getAttribute("value");
		Assert.assertTrue(sPicklistAns.equals(sOrderStatusPrefill), "Picklist question not Prefilled.");
		ExtentManager.logger.log(Status.PASS,"Picklist question prefilled sucessfully");
		
		sNumberAns = checklistPo.geteleChecklistAnsNumber(sNumberq).getAttribute("value");
		Assert.assertTrue(sNumberAns.equals(sIdleTimePrefill), "Number question not Prefilled.");
		ExtentManager.logger.log(Status.PASS,"Number question prefilled sucessfully");
		
		/*sDateAns = checklistPo.geteleChecklistAnsDate(sDateq).getAttribute("value");
		Assert.assertTrue(sDateAns.equals(sScheduleddatePrefill), "Date question not Prefilled.");
		ExtentManager.logger.log(Status.PASS,"Date question prefilled sucessfully");

		sDatetimeAns = checklistPo.geteleChecklistAnsDate(sDatetimeq).getAttribute("value");
		System.out.println(sDatetimeAns);
		Assert.assertTrue(sDatetimeAns.equals(sScheduledDateTimePrefill), "DateTime question not Prefilled.");
		ExtentManager.logger.log(Status.PASS,"DateTime question prefilled sucessfully");*/
		
		//tapping next button
		commonsPo.tap(checklistPo.geteleNext());
		Thread.sleep(GenericLib.iLowSleep);
						
		//submitting of checklist
		commonsPo.clickAllowPopUp();
		commonsPo.switchContext("WebView");
		commonsPo.tap(checklistPo.eleChecklistSubmit());
		commonsPo.tap(checklistPo.geteleChecklistPopupSubmit());
		
		//Navigating back to work Orders
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());
		
		//Navigation back to Work Order
		Assert.assertTrue(checklistPo.getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		ExtentManager.logger.log(Status.PASS,"Back to Work Order after submitting checklist passed");

		Thread.sleep(GenericLib.iLowSleep);		
		
		
		// Navigate to SFM processes
		 workOrderPo.selectAction(commonsPo, sEditProcessName);
		 	
		 //------------------Validating the Source Object Updates------------------	 	
		 	//1.Picklist
		 	sBillingType = workOrderPo.getEleBillingTypeLst().getAttribute("value");
		 	Assert.assertEquals(workOrderPo.getEleBillingTypeLst().getAttribute("value"), sBillingTypeSOU, "Picklist Source Object is not updated");
			ExtentManager.logger.log(Status.PASS,"Source Object Update for Picklist Sucessfull");
			
			//2.Number 
			
			sNoOfTimesAssigned = workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value");
			
			//sIdleTime = workOrderPo.geteleIdleTimetxt().getAttribute("value");
			System.out.println(sNoOfTimesAssigned);
		 	Assert.assertEquals(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value"), sNooftimesAssignedSOU, "Number Source Object is not updated");
			ExtentManager.logger.log(Status.PASS,"Source Object Update for Number with value  Sucessfull");

			/*//3.DateTime
			sScheduledDateTime = workOrderPo.getEleScheduledDateTimeTxt().getAttribute("value");
			System.out.println(sScheduledDateTime);
		 	Assert.assertEquals(workOrderPo.getEleScheduledDateTimeTxt().getAttribute("value"), sScheduledDateTimeSou, "DateTime Source Object is not updated");
			ExtentManager.logger.log(Status.PASS,"Source Object Update for DateTime Sucessfull");*/
		
			//4.Date
			/*sScheduledDate = workOrderPo.getEleScheduledDateLst().getAttribute("value");
			System.out.println(sScheduledDate);
			Assert.assertEquals(workOrderPo.getEleScheduledDateLst().getAttribute("value"), sScheduledDateSOU, "Date Source Object is not updated");
			ExtentManager.logger.log(Status.PASS,"Source Object Update for Date with function Today Sucessfull");*/
			
			//5. Text
			sProformaInvoice = workOrderPo.getEleProformaInvoiceTxt().getAttribute("value");
			Assert.assertEquals(workOrderPo.getEleProformaInvoiceTxt().getAttribute("value"), sProformaInvoiceSOU, "Text Source Object is not updated");
			ExtentManager.logger.log(Status.PASS,"Source Object Update for Text with Value Sucessfull");
			
			
			//6. Checkbox
			//Checkbox Button validation
		
			try {
				commonsPo.tap(workOrderPo.geteleIsEntitlementPerformed_Switch_On());
				ExtentManager.logger.log(Status.PASS, "Checkbox Source Object update with checkbox datatype Passed");
			} catch (Exception e) {
				ExtentManager.logger.log(Status.FAIL, "Checkbox Source Object update with checkbox datatype Failed");
			}
			
			 commonsPo.tap(workOrderPo.getEleCancelLink()); 
			 commonsPo.tap( workOrderPo.getEleDiscardChanges()); 

			 toolsPo.syncData(commonsPo);	
			 Thread.sleep(GenericLib.iMedSleep);
			 commonsPo.tap(exploreSearchPo.getEleExploreIcn());
				//Navigation to WO
			 workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);							

			 workOrderPo.selectAction(commonsPo, sEditProcessName);
			 
			//------------------Validating the Source Object Updates after data sync ------------------	 	
			 	//1.Picklist
			 	sBillingType = workOrderPo.getEleBillingTypeLst().getAttribute("value");
			 	Assert.assertEquals(workOrderPo.getEleBillingTypeLst().getAttribute("value"), sBillingTypeSOU, "Picklist Source Object is not updated");
				ExtentManager.logger.log(Status.PASS,"Source Object Update for Picklist Sucessfull after datasync");
				
				//2.Number 
				
				sNoOfTimesAssigned = workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value");
				
				//sIdleTime = workOrderPo.geteleIdleTimetxt().getAttribute("value");
				System.out.println(sNoOfTimesAssigned);
			 	Assert.assertEquals(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value"), sNooftimesAssignedSOU, "Number Source Object is not updated");
				ExtentManager.logger.log(Status.PASS,"Source Object Update for Number with value  Sucessfull after data sync");

				/*//3.DateTime
				sScheduledDateTime = workOrderPo.getEleScheduledDateTimeTxt().getAttribute("value");
				System.out.println(sScheduledDateTime);
			 	Assert.assertEquals(workOrderPo.getEleScheduledDateTimeTxt().getAttribute("value"), sScheduledDateTimeSou, "DateTime Source Object is not updated");
				ExtentManager.logger.log(Status.PASS,"Source Object Update for DateTime Sucessfull");*/
			
				//4.Date
				/*sScheduledDate = workOrderPo.getEleScheduledDateLst().getAttribute("value");
				System.out.println(sScheduledDate);
				Assert.assertEquals(workOrderPo.getEleScheduledDateLst().getAttribute("value"), sScheduledDateSOU, "Date Source Object is not updated");
				ExtentManager.logger.log(Status.PASS,"Source Object Update for Date with function Today Sucessfull");*/
				
				//5. Text
				sProformaInvoice = workOrderPo.getEleProformaInvoiceTxt().getAttribute("value");
				Assert.assertEquals(workOrderPo.getEleProformaInvoiceTxt().getAttribute("value"), sProformaInvoiceSOU, "Text Source Object is not updated");
				ExtentManager.logger.log(Status.PASS,"Source Object Update for Text with Value Sucessfull after datasync");
				
				
				//6. Checkbox
				//Checkbox Button validation
			
				try {
					commonsPo.tap(workOrderPo.geteleIsEntitlementPerformed_Switch_On());
					ExtentManager.logger.log(Status.PASS, "Checkbox Source Object update with checkbox datatype Passed after datasync");
				} catch (Exception e) {
					ExtentManager.logger.log(Status.FAIL, "Checkbox Source Object update with checkbox datatype Failed after datasync");
				}
			 
			 
		//	 commonsPo.tap(exploreSearchPo.getEleExploreIcn());
			
			//validating in server after source object update and if prefilled values are syned to server
			
			sChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
			sChecklistQueryval = restServices.restGetSoqlValue(sChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(sChecklistQueryval.contains(schecklistStatus),"checklist completed is not synced to server");
			ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");
			
			
			ChecklistAnsjson = restServices.restGetSoqlValue(sChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(ChecklistAnsjson.contains(sCityPrefill), "checklist text question answer is not synced to server");
			ExtentManager.logger.log(Status.PASS,"checklist text question answer with prefill synced to server");
			
			Assert.assertTrue(ChecklistAnsjson.contains(sIdleTimePrefill), "checklist number answer sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist number answer sycned to server in checklist answer");
			
			
			Assert.assertTrue(ChecklistAnsjson.contains(sPicklistAns), "checklist picklist answer was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist picklist question answer synced to server");
			
			/*SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy");
	        Date dTempDate1 = parser.parse(sScheduleddatePrefill);
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        String sformattedDate = formatter.format(dTempDate1);
			
			Assert.assertTrue(ChecklistAnsjson.contains(sformattedDate), "checklist date answer was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist date question answer synced to server");
			
			SimpleDateFormat parser1 = new SimpleDateFormat("MM/dd/yy hh:mm");
		    dTempDate1 = parser1.parse(sScheduledDateTime);
	        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String stempDate =  formatter1.format(dTempDate1);
	        System.out.println("formatter1.format value   "+stempDate);
	        dTempDate1 = formatter1.parse(stempDate);
	        //adding 7 hours to set to UTC/GMT time.. this is from PST timezone as 
        	Instant insDate =dTempDate1.toInstant().plus(7, ChronoUnit.HOURS);
	        System.out.println("7 aded to instant"+insDate); 
 
	        //formatter1.format(datetime1);
	        //System.out.println("after format datetime1"+datetime1);
	        String sformattedDatetime = formatter1.format(dTempDate1);
	        dTempDate1 = Date.from(insDate);
	         sformattedDatetime = formatter1.format((dTempDate1));  
        
	        Assert.assertTrue(ChecklistAnsjson.contains(sformattedDatetime), "checklist datetime answer was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist datetime question answer synced to server");
			*/
			sSoqlqueryWO = "Select+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
			sSoqlProforma = "Select+SVMXC__Proforma_Invoice__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
			sSoqlNoOfTimes = "Select+SVMXC__NoOfTimesAssigned__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
			//String sSoqlSchedulesDate = "Select+SVMXC__Scheduled_Date__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
			//String sSoqlScheduledDateTime = "Select+SVMXC__Scheduled_Date_Time__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 

			//String sSoqlqueryWO = "Select+Id+SVMXC__Billing_Type__c,+SVMXC__Proforma_Invoice__c,+SVMXC__NoOfTimesAssigned__c,+SVMXC__Scheduled_Date__c,+SVMXC__Scheduled_Date_Time__c+from+SVMXC__Service_Order__c+Where+Name+='\"+sWOName+\"'"; 
			restServices.getAccessToken();
			//String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlqueryWO, "Id");	
			sBillTypeServer = restServices.restGetSoqlValue(sSoqlqueryWO,"SVMXC__Billing_Type__c");
			sProformaServer = restServices.restGetSoqlValue(sSoqlProforma,"SVMXC__Proforma_Invoice__c");
			sNoOftimesServer = restServices.restGetSoqlValue(sSoqlNoOfTimes,"SVMXC__NoOfTimesAssigned__c");		
			sNoOftimesServer1= sNoOftimesServer.substring(0, sNoOftimesServer.length() - 2);
			
			//String sScheduledDateServer = restServices.restGetSoqlValue(sSoqlSchedulesDate,"SVMXC__Scheduled_Date__c");
			//String sScheduledDateTimeServer = restServices.restGetSoqlValue(sSoqlScheduledDateTime,"SVMXC__Scheduled_Date_Time__c");
			
			//System.out.println(sScheduledDateServer);
			//System.out.println(sScheduledDateTimeServer);
	        Assert.assertTrue(sBillTypeServer.equals(sBillingTypeSOU), "Picklist source object not syned to server");
			ExtentManager.logger.log(Status.PASS,"Picklist Source object update has synced to server");
			
			Assert.assertTrue(sProformaServer.equals(sProformaInvoiceSOU), "Text source object not syned to server");
			ExtentManager.logger.log(Status.PASS,"Text Source object update has synced to server");
			
			Assert.assertTrue(sNoOftimesServer1.equals(sNooftimesAssignedSOU), "Number source object not syned to server");
			ExtentManager.logger.log(Status.PASS,"Number Source object update has synced to server");
	        
	}
	
}
