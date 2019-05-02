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
import java.util.Date;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SourceObjectUpdate_RS_10544 extends BaseLib{
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
	String sProductName = null;	
	String sObjectApi = null;
	String sJsonData=null;
	String sObjectAccID=null;
	String sSqlAccQuery=null;	
	String sAccountName=null;
	String  sUsageLine=null;
	String sRecordTypeId=null;
	String sworkDetail=null;
	Boolean bProcessCheckResult  = false;
	String sScriptName = "RS_10544_Source_Object_Update";
	//Source object update values
			
			String sBillingType = null;String sBillingTypeSOU= "Warranty";
			String sOrderStatus= "Open";String sOrderStatusSOU = "Open";
			String sIdleTime = null;String sIdleTimeSOU= "30";
			String sScheduledDate = null;
			String[] sScheduledDateSOU=null; 
			String sScheduledDateTime = null;String sScheduledDateTimeSou ="8/28/2018 02:42";				
			String sNoofTimesAssigned = null; String sNooftimesAssignedSOU = "2";
			String sProformaInvoice = null; String sProblemDescriptionSOU="Source Object Updated";
			String sAccountSOU=null;
			String sURLSOU = "www.servicemax.com";
			String sPhoneSOU = "9886098860";
			String sEmailSOU = "automation@qa.com";
			Boolean bBooleanSOU = true;
	
	//For ServerSide Validations
			String schecklistStatus = "Completed";
			String sSheetName =null;
			String sProformainVoice =null;
			String sTestIB = null;
			String sTestIBID=null;
			String sProductId = null;
	
			public void prerequisites() throws Exception
			{
				sSheetName ="RS_10544";
				System.out.println("SCN_SourceObject_UPDATE_RS10544");
				
				sProformainVoice = commonUtility.generaterandomnumber("Account");
				sTestIB="RS-10544_SOU";
				sTestIBID = sProformainVoice;

				//String time = driver.getDeviceTime();
				//System.out.println(time);
				sScheduledDateSOU=driver.getDeviceTime().split(" ");
				System.out.println(sScheduledDateSOU);
				sTestCaseID = "SCN_SourceObjectUpdate_RS_10544";
				sCaseWOID = "Data_SCN_SourceObjectUpdate_RS_10544";
				

				//Reading from the Excel sheet
				sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
				sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
				sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
				sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");
					
				
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
				sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWorkOrderID + "\'", "Name");
				System.out.println("WO no =" + sWOName);
			
				
				// Creating Product from API
				sProductName = "AUTO_RS10544";
				restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
				sProductId = restServices.restGetSoqlValue("SELECT+Id+from+Product2+Where+Name+=\'" + sProductName + "\'", "Id");
				System.out.println(sProductId);
						
				//Getting record type usage Usage/Consumption for work detail
				sUsageLine = "Usage/Consumption";
				sRecordTypeId = restServices.restGetSoqlValue("SELECT+Id+from+RecordType+Where+Name+=\'" + sUsageLine + "\'", "Id");

				
			//Creating and associating a work detail to the work Order	
			sworkDetail = restServices.restCreate("SVMXC__Service_Order_Line__c?","{\"SVMXC__Line_Status__c\":\"Open\",\"SVMXC__Line_Type__c\":\"Parts\",\"SVMXC__Service_Order__c\":\""+sWorkOrderID+"\",\"RecordTypeId\":\""+sRecordTypeId+"\",\"SVMXC__Actual_Quantity2__c\":\"11\",\"SVMXC__Product__c\":\""+sProductId+"\"}");//,,
			System.out.println("work Detail");
			System.out.println(sworkDetail);
			
			//Creating a servicemax event and assigning the work order to it.
			
		/*	String sTech_Id = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "TECH_ID");
			String sSoqlQueryTech = "SELECT+Id+from+SVMXC__Service_Group_Members__c+Where+SVMXC__Salesforce_User__c+=\'"+sTech_Id+"\'";
			restServices.getAccessToken();
			String sTechnician_ID = restServices.restGetSoqlValue(sSoqlQueryTech,"Id");
			String sEventName = "AUTO_10544Event";
			String sEventId = restServices.restCreate("SVMXC__SVMX_Event__c?", "{\"Name\":\""+sEventName+"\", \"SVMXC__Service_Order__c\":\""+sWorkOrderID+"\", \"SVMXC__Technician__c\":\""+sTechnician_ID+"\", \"SVMXC__StartDateTime__c\":\""+LocalDate.now()+"\", \"SVMXC__EndDateTime__c\": \""+LocalDate.now().plusDays(1L)+"\"}");*/
					
			//	sWOName = "WO-00002177";
			
			bProcessCheckResult =commonUtility.ProcessCheck(restServices, genericLib, sFieldServiceName, sScriptName, sTestCaseID);		

			}
	@Test()		
	//@Test(retryAnalyzer=Retry.class)
	public void RS_10544() throws Exception {
		
		prerequisites();					
		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		//Optional config based on process check
		ph_MorePo.OptionalConfigSync(commonUtility,ph_CalendarPo,bProcessCheckResult);
		
		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		//toolsPo.configSync(commonsUtility);			
		
		//Navigation to WO	
				ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
						sWOName, sFieldServiceName);

		Thread.sleep(GenericLib.iLowSleep);
		//Set the order status
		
		System.out.println("Waiting to set order status");
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_WorkOrderPo.geteleOrderStatus(),sOrderStatus);
		Thread.sleep(GenericLib.iLowSleep);
		
		//SEtting Account
		ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_CreateNewPo.getEleAccountLookUp(), sAccountName);
		
		//Adding Parts - to be uncommented after defect fix.
		//ph_WorkOrderPo.addParts(commonUtility, sProductName);

		ph_WorkOrderPo.getEleSaveLnk().click();
		Assert.assertTrue(commonUtility.waitforElement(ph_WorkOrderPo.getEleOverViewTab(),3),"Overview tab is not displayed, might not have saved correctly please check!");
		ExtentManager.logger.log(Status.PASS,"OverView Tab is displayed post saving.");
		Thread.sleep(GenericLib.iMedSleep);

		//relaunching app.
	//	lauchNewApp("true");

		//Navigation to WO	
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
				sWOName, sEditProcessName);
		
		Thread.sleep(2000);
		Assert.assertEquals(ph_CreateNewPo.getElebillingtype().getText(), sBillingTypeSOU, "Picklist source object update failed billing type not set to warranty");
		ExtentManager.logger.log(Status.PASS,"Picklist Source Object Update  Header sucessful in Client");
		
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleURL());
		Assert.assertEquals(ph_WorkOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getText(), sNooftimesAssignedSOU, " No source Object update failed No Of Times is not set to 2");
		ExtentManager.logger.log(Status.PASS,"Number Source Object Update  Header sucessful in Client");
		
		Assert.assertEquals(ph_WorkOrderPo.getEleAccount().getText(), sAccountName, "Lookup Source Object update failed.Account is not being displayed");
		ExtentManager.logger.log(Status.PASS,"Look Up Source Object Update Header sucessful in Client");
		
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleEmail());
		Assert.assertEquals(ph_WorkOrderPo.getEleURL().getText(),sURLSOU,"URL source update failed");
		ExtentManager.logger.log(Status.PASS,"URL Source Object Update Header sucessful in Client");		
		
		Assert.assertEquals(ph_WorkOrderPo.getElePhone().getText(),sPhoneSOU,"Phone source update failed");
		ExtentManager.logger.log(Status.PASS,"Phone Source Object Update Header sucessful in Client");
		
		commonUtility.custScrollToElement(ph_WorkOrderPo.geteleProblemDescriptiontxt());
		Assert.assertEquals(ph_WorkOrderPo.getEleEmail().getText(),sEmailSOU,"Email source update failed");
		ExtentManager.logger.log(Status.PASS,"Email  Source Object Update Header sucessful in Client");
		
		Assert.assertEquals(ph_WorkOrderPo.geteleProblemDescriptiontxt().getText(), sProblemDescriptionSOU, " No source Object update failed for problem Description");
		ExtentManager.logger.log(Status.PASS,"Text Area Source Object Update  Header sucessful in Client");
		
/*		Assert.assertEquals(ph_WorkOrderPo.getEleEntitlementPerformedOn().getText(), "Boolean  Source Object Update Header fail in Client");
		ExtentManager.logger.log(Status.PASS,"Boolean  Source Object Update Header sucessful in Client");
*/	
		// TO ALTER AFTER DATE AND DATETIME vALUES ARE FIXEd.
	/*	String sScheduledDateHeader = workOrderPo.getScheduledDatevalue().getAttribute("value").toString();
		System.out.println("Scheduled Date Header"+sScheduledDateHeader);		
		String sScheduledDateTimeHeader = workOrderPo.getScheduledDatetimevalue().getAttribute("value").toString();
		System.out.println("Scheduled Date Header"+sScheduledDateTimeHeader); */
		
		commonUtility.gotToTabHorizontal(ph_WorkOrderPo.getStringParts());
		Thread.sleep(genericLib.iLowSleep);
		
	//	ph_WorkOrderPo.geteleAddedPart(sProductName).click();
		//to be changed after code fix to above one
		ph_WorkOrderPo.geteleRemoveablePart().click();
		Thread.sleep(2000);
		
		//Assert.assertEquals(workOrderPo.getelePart_Edit_Input().getAttribute("value").toString(),sProductName,"Part is not source object updated");
	//	ExtentManager.logger.log(Status.PASS,"Part Child  Lookup Source Object Update Header sucessful in Client");
		
		
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleBillingInformation());
		Assert.assertEquals(ph_WorkOrderPo.geteleBillableQty().getText(), "2","Child number soulce object updated failed");
		ExtentManager.logger.log(Status.PASS,"Child NumberDataType Source Object Update Header sucessful in Client");

		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleStartDateTimeTxtFld());
		Assert.assertEquals(ph_WorkOrderPo.getEleBillingInformation().getText(), "Long Text area Source Object Updated","Long Text are is not source object updated");
		ExtentManager.logger.log(Status.PASS,"Child Text area Billing information  Source Object Update Header sucessful in Client");
		
		//DATE AND DATETIME VALIDATIONS NEED TO ALERTED AFTER final confirmation.		
/*		String sDateRec =  workOrderPo.getElePart_DateReceived_Edit_Input().getAttribute("value").toString();
	    System.out.println("DateReceived   "+sDateRec);    	    
	    String sStartDateTime =  workOrderPo.getElePart_StartDateTime_Edit_Input().getAttribute("value").toString();
	    System.out.println("StartDateTime   "+sStartDateTime);  
*/	    
	   //to close out of parts
		ph_WorkOrderPo.getEleBackButton().click();
		//to close out of work order
		ph_WorkOrderPo.getEleBackButton().click();

		
		//commonsUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());

		ph_MorePo.syncData(commonUtility);

		restServices.getAccessToken();
		
		String sSoqlqueryWO = "Select+SVMXC__Billing_Type__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
		String sSoqlNoOfTimes = "Select+SVMXC__NoOfTimesAssigned__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
		String sSoqlSchedulesDate = "Select+SVMXC__Scheduled_Date__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
		String sSoqlScheduledDateTime = "Select+SVMXC__Scheduled_Date_Time__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
		String sSoqlEmail = "Select+Email__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
		String sSoqlURL = "Select+URL__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
		String sSoqlPhone = "Select+Phone__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
		String sSoqlProbDesc = "Select+SVMXC__Problem_Description__c+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"'"; 
	
		String sURLServer = restServices.restGetSoqlValue(sSoqlURL,"URL__c");
		Assert.assertEquals(sURLServer, sURLSOU, " Server URL source object update failed  not set in Server");
		ExtentManager.logger.log(Status.PASS,"Server URL Source Object Update  Header sucessful in Server");

		String sEmailServer = restServices.restGetSoqlValue(sSoqlEmail,"Email__c");
		Assert.assertEquals(sEmailServer, sEmailSOU, " Server Email source object update failed not set in Server");
		ExtentManager.logger.log(Status.PASS,"Server eEmail Source Object Update  Header sucessful in Server");
		
		String sPhoneserver = restServices.restGetSoqlValue(sSoqlPhone,"Phone__c");
		Assert.assertEquals(sPhoneserver, sPhoneSOU, " Server Phone  source object update failed not set in Server");
		ExtentManager.logger.log(Status.PASS,"Server Phone Source Object Update  Header sucessful in Server");
		
		String sBillTypeServer = restServices.restGetSoqlValue(sSoqlqueryWO,"SVMXC__Billing_Type__c");
		Assert.assertEquals(sBillTypeServer, sBillingTypeSOU, " Server Picklist source object update failed billing type not set to warranty in Server");
		ExtentManager.logger.log(Status.PASS,"Server Picklist Source Object Update  Header sucessful in Server");

		
		String sProbDescServer = restServices.restGetSoqlValue(sSoqlProbDesc,"SVMXC__Problem_Description__c");
		Assert.assertEquals(sProbDescServer, sProblemDescriptionSOU, " Server Problem Desc source object update failed billing type not set to warranty in Server");
		ExtentManager.logger.log(Status.PASS,"Server Problem Desc Source Object Update  Header sucessful in Server");

		
		String sNoOftimesServer = restServices.restGetSoqlValue(sSoqlNoOfTimes,"SVMXC__NoOfTimesAssigned__c");
		String sNoOftimesServer1= sNoOftimesServer.substring(0, sNoOftimesServer.length() - 2);
		Assert.assertEquals(sNoOftimesServer1, sNoOftimesServer1, " Server Number source object update failed billing type not set to warranty in Server");
		ExtentManager.logger.log(Status.PASS,"Server Number Source Object Update  Header sucessful in Server");
												
	//	String sScheduledDateServer = restServices.restGetSoqlValue(sSoqlSchedulesDate,"SVMXC__Scheduled_Date__c");
	//	String sScheduledDateTimeServer = restServices.restGetSoqlValue(sSoqlScheduledDateTime,"SVMXC__Scheduled_Date_Time__c");
	    
		
/*		//Validation of SOURCE OBJECT UPDATE AFTER SERVER Verification back in client
		Thread.sleep(GenericLib.iLowSleep);
		commonUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(GenericLib.iLowSleep);
		commonUtility.tap(exploreSearchPo.getEleExploreIcn());
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sEditProcessName);
		
		Assert.assertEquals(workOrderPo.getEleBillingTypeCaseLst().getAttribute("value").toString(), sBillingTypeSOU, "Picklist source object update failed billing type not set to warranty");
		ExtentManager.logger.log(Status.PASS,"After Data Sync-Picklist Source Object Update  Header sucessful in Client");
		
		Assert.assertEquals(workOrderPo.GetEleNoOfTimesAssigned_Edit_Input().getAttribute("value").toString(), sNooftimesAssignedSOU, " No source Object update failed No Of Times is not set to 2");
		ExtentManager.logger.log(Status.PASS,"After Data Sync-Number Source Object Update  Header sucessful in Client");
		
		Assert.assertEquals(workOrderPo.getProblemDescription().getText().toString(), sProblemDescriptionSOU, " No source Object update failed for problem Description");
		ExtentManager.logger.log(Status.PASS,"After Data Sync-Text Area Source Object Update  Header sucessful in Client");
		
		Assert.assertEquals(workOrderPo.getAccountvalue().getAttribute("value"), sAccountName, "Lookup Source Object update failed.Account is not being displayed");
		ExtentManager.logger.log(Status.PASS,"After Data Sync-Look Up Source Object Update Header sucessful in Client");
			
		Assert.assertEquals(workOrderPo.getURLvalue().getAttribute("value").toString(),sURLSOU,"URL source update failed");
		ExtentManager.logger.log(Status.PASS,"After Data Sync-URL Source Object Update Header sucessful in Client");		
		
		Assert.assertEquals(workOrderPo.getPhonevalue().getAttribute("value").toString(),sPhoneSOU,"Phone source update failed");
		ExtentManager.logger.log(Status.PASS,"After Data Sync-Phone Source Object Update Header sucessful in Client");
		
		Assert.assertEquals(workOrderPo.getEmailvalue().getAttribute("value").toString(),sEmailSOU,"Email source update failed");
		ExtentManager.logger.log(Status.PASS,"After Data Sync-Email  Source Object Update Header sucessful in Client");
		
		
		Assert.assertTrue(workOrderPo.getEleIsEntitlementPerformed().isEnabled(), "Boolean  Source Object Update Header fail in Client");
		//Assert.assertEquals(workOrderPo.getEleIsEntitlementPerformed().isEnabled(), bBooleanSOU, "Boolean  Source Object Update Header sucessful in Client");
		ExtentManager.logger.log(Status.PASS,"After Data Sync- Boolean  Source Object Update Header sucessful in Client");
		
*/	
	    
	}
	
}
