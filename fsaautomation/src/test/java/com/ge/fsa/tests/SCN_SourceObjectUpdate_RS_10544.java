/*
 *  @author Vinod Tharavath
 *  SCN_SourceObjectUpdate_RS-10544 Verify Source Object Update
 *  
 *  PENDING TASKS ---
 *  ====Sahi Script for Process Creations SourcetoTarget and Edit process.
 *  === MAKE SURE URL,Phone,Email - custom fields are created in the ORG before running.
 *  === DateTime and date field literals need to be completed.
 *  ===Child level SOurce Object updates
 */

package com.ge.fsa.tests;
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
import com.ge.fsa.pageobjects.WorkOrderPO;


public class SCN_SourceObjectUpdate_RS_10544 extends BaseLib{
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
	
	
	String sObjectApi = null;
	String sJsonData=null;
	String sObjectAccID=null;
	String sSqlAccQuery=null;	
	String sAccountName=null;
	
	
	//Source object update values
			
			String sBillingType = null;String sBillingTypeSOU= "Warranty";
			String sOrderStatus= "Open";String sOrderStatusSOU = "Open";
			String sIdleTime = null;String sIdleTimeSOU= "30";
			String sScheduledDate = null;
			String[] sScheduledDateSOU=null; 
			String sScheduledDateTime = null;String sScheduledDateTimeSou ="8/28/18 02:42";				
			String sNoofTimesAssigned = null; String sNooftimesAssignedSOU = "2";
			String sProformaInvoice = null; String sProblemDescriptionSOU="Source Object Updated";
			String sAccountSOU=null;
			String sURLSOU = "www.servicemax.com";
			String sPhoneSOU = "9886098860";
			String sEmailSOU = "automation@qa.com";
			
	//For ServerSide Validations
			String schecklistStatus = "Completed";
			
			
	@Test(enabled = true)
	public void RS_10544() throws Exception {

		System.out.println("SCN_SourceObject_UPDATE_RS10544");
		
		String sProformainVoice = commonsPo.generaterandomnumber("Account");
		String sTestIB="RS-10544_SOU";
		String sTestIBID = sProformainVoice;

		//String time = driver.getDeviceTime();
		//System.out.println(time);
		sScheduledDateSOU=driver.getDeviceTime().split(" ");
		System.out.println(sScheduledDateSOU);
		sTestCaseID = "SCN_SourceObjectUpdate_RS_10544";
		sCaseWOID = "Data_SCN_SourceObjectUpdate_RS_10544";
		

		//Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID, "EditProcessName");
			
		
		//Account Creation
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sTestIBID+"\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlAccQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		sAccountName =restServices.restGetSoqlValue(sSqlAccQuery,"Name"); 
		System.out.println(sAccountName);	
		
		//Work Order Creation
		sWorkOrderID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"Low\"}");
		System.out.println(sWorkOrderID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWorkOrderID + "\'", "Name");
		System.out.println("WO no =" + sWOName);
	
	//	sWOName = "WO-00002177";

							
		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);		
		
		//Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		//toolsPo.configSync(commonsPo);			
		
		//Navigation to WO	
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);

		Thread.sleep(GenericLib.iLowSleep);
		//Set the order status
		
		System.out.println("Waiting to set order status");
		commonsPo.pickerWheel(workOrderPo.geteleOrderStatusEditMandatoryValue(), sOrderStatus);
		Thread.sleep(GenericLib.iLowSleep);
		
		//SEtting Account
		commonsPo.tap(workOrderPo.getEleAccount_Edit_Input());
		Thread.sleep(GenericLib.iLowSleep);
		commonsPo.lookupSearch(sAccountName);
		commonsPo.tap(workOrderPo.getEleSaveLnk());	
		//commonsPo.singleTap(workOrderPo.getEleSaveLnk().getLocation());
		Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Failed to save the work orer update");
		//NXGReports.addStep("Work Order Saved successfully", LogAs.PASSED, null);
		ExtentManager.logger.log(Status.PASS,"Work Order Saved successfully");
		Thread.sleep(GenericLib.iMedSleep);
		
		commonsPo.tap(workOrderPo.geteleBacktoWorkOrderlnk());
	 
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sEditProcessName);
		
		Assert.assertEquals(workOrderPo.getEleBillingTypeCaseLst().getAttribute("value").toString(), sBillingTypeSOU, "Picklist source object update failed billing type not set to warranty");
		ExtentManager.logger.log(Status.PASS,"Picklist Source Object Update  Header sucessful in Client");
		
		Assert.assertEquals(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value").toString(), sNooftimesAssignedSOU, " No source Object update failed No Of Times is not set to 2");
		ExtentManager.logger.log(Status.PASS,"Number Source Object Update  Header sucessful in Client");
		
		Assert.assertEquals(workOrderPo.getProblemDescription().getText().toString(), sProblemDescriptionSOU, " No source Object update failed for problem Description");
		ExtentManager.logger.log(Status.PASS,"Text Area Source Object Update  Header sucessful in Client");
		
		Assert.assertEquals(workOrderPo.getAccountvalue().getAttribute("value"), sAccountName, "Lookup Source Object update failed.Account is not being displayed");
		ExtentManager.logger.log(Status.PASS,"Look Up Source Object Update Header sucessful in Client");
		
		Assert.assertEquals(workOrderPo.getURLvalue().getAttribute("value").toString(),sURLSOU,"URL source update failed");
		ExtentManager.logger.log(Status.PASS,"URL Source Object Update Header sucessful in Client");		
		
		Assert.assertEquals(workOrderPo.getPhonevalue().getAttribute("value").toString(),sPhoneSOU,"Phone source update failed");
		ExtentManager.logger.log(Status.PASS,"Phone Source Object Update Header sucessful in Client");
		
		Assert.assertEquals(workOrderPo.getEmailvalue().getAttribute("value").toString(),sEmailSOU,"Email source update failed");
		ExtentManager.logger.log(Status.PASS,"Email  Source Object Update Header sucessful in Client");


		

			
	        
	}
	
}
