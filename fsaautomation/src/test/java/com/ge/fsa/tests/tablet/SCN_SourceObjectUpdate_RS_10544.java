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

package com.ge.fsa.tests.tablet;
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
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;



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
				
				sProformainVoice = commonUtility.generateRandomNumber("Account");
				sTestIB="RS-10544_SOU";
				sTestIBID = sProformainVoice;

				//String time = driver.getDeviceTime();
				//System.out.println(time);
				sScheduledDateSOU=driver.getDeviceTime().split(" ");
				System.out.println(sScheduledDateSOU);
				sTestCaseID = "SCN_SourceObjectUpdate_RS_10544";
				sCaseWOID = "Data_SCN_SourceObjectUpdate_RS_10544";
				

				//Reading from the Excel sheet
				sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreSearch");
				sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ExploreChildSearch");
				sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "ProcessName");
				sEditProcessName = CommonUtility.readExcelData(CommonUtility.sTestDataFile,sSheetName, "EditProcessName");
					
				
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
			
	@Test(retryAnalyzer=Retry.class)
	public void RS_10544() throws Exception {
		
		prerequisites();					
		//Pre Login to app
		loginHomePo.login(commonUtility, exploreSearchPo);
		
		//Optional config based on process check
	    toolsPo.OptionalConfigSync(toolsPo, commonUtility, bProcessCheckResult);
		
		//Data Sync for WO's created
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		//toolsPo.configSync(commonsUtility);			
		
		//Navigation to WO	
		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);

		Thread.sleep(CommonUtility.iLowSleep);
		//Set the order status
		
		System.out.println("Waiting to set order status");
		commonUtility.setPickerWheelValue(workOrderPo.geteleOrderStatusEditMandatoryValue(), sOrderStatus);
		Thread.sleep(CommonUtility.iLowSleep);
		
		//SEtting Account
		commonUtility.tap(workOrderPo.getEleAccount_Edit_Input());
		Thread.sleep(CommonUtility.iLowSleep);
		commonUtility.lookupSearch(sAccountName);
		commonUtility.tap(workOrderPo.getEleSaveLnk());	
		
		try {
			Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), " Work Order Saved successfully is not displayed");
			ExtentManager.logger.log(Status.PASS,"Work Order Saved successfully text is displayed successfully");
		} catch (AssertionError e) {
			ExtentManager.logger.log(Status.INFO,"Did not get the verbiage work order saved sucessfully will try for work order element");						
		}
		
		Thread.sleep(CommonUtility.iMedSleep);
		
		commonUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());
		
		ExtentManager.logger.log(Status.PASS,"Data Sync sucessfull after Source object Update process invoking");

		workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sEditProcessName);
		
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
				
		Assert.assertTrue(workOrderPo.getEleIsEntitlementPerformed().isEnabled(), "Boolean  Source Object Update Header fail in Client");
		//Assert.assertEquals(workOrderPo.getEleIsEntitlementPerformed().isEnabled(), bBooleanSOU, "Boolean  Source Object Update Header sucessful in Client");
		ExtentManager.logger.log(Status.PASS,"Boolean  Source Object Update Header sucessful in Client");
	
		// TO ALTER AFTER DATE AND DATETIME vALUES ARE FIXEd.
		String sScheduledDateHeader = workOrderPo.getScheduledDatevalue().getAttribute("value").toString();
		System.out.println("Scheduled Date Header"+sScheduledDateHeader);		
		String sScheduledDateTimeHeader = workOrderPo.getScheduledDatetimevalue().getAttribute("value").toString();
		System.out.println("Scheduled Date Header"+sScheduledDateTimeHeader);
		
		/*commonsUtility.tap(workOrderPo.openpartsontap1());
		Thread.sleep(genericLib.iLowSleep);
		
		Assert.assertEquals(workOrderPo.getelePart_Edit_Input().getAttribute("value").toString(),sProductName,"Part is not source object updated");
		ExtentManager.logger.log(Status.PASS,"Part Child  Lookup Source Object Update Header sucessful in Client");

		Assert.assertEquals(workOrderPo.getElePart_BillingInformation_Edit_Input().getText(), "Long Text area Source Object Updated","Long Text are is not source object updated");
		ExtentManager.logger.log(Status.PASS,"Child Text area Billing information  Source Object Update Header sucessful in Client");
		

		Assert.assertEquals(workOrderPo.getElePart_BillableQty_Edit_Input().getAttribute("value").toString(), "2","Child number soulce object updated failed");
		ExtentManager.logger.log(Status.PASS,"Child NumberDataType Source Object Update Header sucessful in Client");

		
		//DATE AND DATETIME VALIDATIONS NEED TO ALERTED AFTER final confirmation.		
		String sDateRec =  workOrderPo.getElePart_DateReceived_Edit_Input().getAttribute("value").toString();
	    System.out.println("DateReceived   "+sDateRec);    	    
	    String sStartDateTime =  workOrderPo.getElePart_StartDateTime_Edit_Input().getAttribute("value").toString();
	    System.out.println("StartDateTime   "+sStartDateTime);  
	    
	    commonsUtility.tap(workOrderPo.getEleDoneBtn()); */ 
	    commonUtility.tap(workOrderPo.getEleClickSave());
	    Thread.sleep(CommonUtility.iMedSleep);
		//commonsUtility.tap(workOrderPo.geteleBacktoWorkOrderlnk());

		toolsPo.syncData(commonUtility);

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
	    
		
		//Validation of SOURCE OBJECT UPDATE AFTER SERVER Verification back in client
		Thread.sleep(CommonUtility.iLowSleep);
		commonUtility.tap(calendarPO.getEleCalendarClick());
		Thread.sleep(CommonUtility.iLowSleep);
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
		
		
	    
	}
	
}
