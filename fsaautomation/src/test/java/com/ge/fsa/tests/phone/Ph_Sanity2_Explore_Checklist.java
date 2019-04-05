/*
 *  @author Vinod Tharavath
 *  Date/dateTime validaitons are commented/removed to revisit later.
 */
package com.ge.fsa.tests.phone;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.apache.http.client.utils.DateUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_ChecklistPO;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_Sanity2_Explore_Checklist extends BaseLib {

	int iWhileCnt = 0;
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sWOName = null;
	String sFieldServiceName = null;
	String sProductName1 = null;
	String sProductName2 = null;
	String sActivityType = null;
	String sPrintReportSearch = null;
	String sChecklistName = null;
	String sExploreChildSearchTxt = null;
	String formattedDate  = null;
	String sformattedDatetime = null;
	Date dtempDate2;
	Date dTempDate1;
	String sSheetName =null;
	String sScriptName = null;
	String sEditProcessName = null;
	String sWORecordID = null;
	boolean bProcessCheckResult = false;

	public void prereq() throws Exception
	{

		sSheetName = "RS_2389";
		sTestCaseID = "RS_2389_checklist";
		sCaseWOID = "RS_2389_checklistID";
		
		//Extracting Excel Data
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		//sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?",
					"{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
			System.out.println(sWORecordID);
			sWOName= restServices
					.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
			System.out.println("WO no =" + sWOName);
			//boolean bProcessCheckResult =commonUtility.ProcessCheck(restServices, genericLib, sChecklistName, sScriptName, sTestCaseID);		
			//sWOName1 = "WO-00001615";
		}
	
	@Test()
	public void scenario2_checklist() throws Exception {
		//commonsUtility.preReqSetup(genericLib);
		// Resinstall the app
		//lauchNewApp("false");
		
		sSheetName = "RS_2389";
		sTestCaseID = "RS_2389_checklist";
		sCaseWOID = "RS_2389_checklistID";
		
		String sradioQuestion ="RadioButton Question";
		String sradioAns = null;		
		String stextQuestion = "Test Question";
		String stextAns = "Text Question Answered";
		String snumberQuestion = "Number Question";
		String snumberAns ="200";		
		String spicklistQuestion = "Picklist Question";
		String spicklistAns = "PicklOne";
		String sdateQuestion = "Date Question";
		String sdateAns = null;
		String sdateTimeQuestion = "DateTime Question";
		String sdateTimeAns = null;
		String schecklistStatus = "Completed";	
		String sPicklistval="PicklOne";
		String sPicklistAns = null;
		String sTextQAns = null;
		String sNumberQAns = null;
		
		
		String sValue ="MultiOn, MultiTwo";
		String sProcessname = "Default title for Checklist";

			// Pre Login to app
			Thread.sleep(10000);
			prereq();
			
		 	ph_LoginHomePo.login(commonUtility, ph_MorePo);
		 
			//toolsPo.configSync(commonsUtility);
		 	ph_MorePo.syncData(commonUtility);
			Thread.sleep(GenericLib.iMedSleep);
			System.out.println(sWOName);
			System.out.println("Going to click explo9re");

			//Navigating to Checklist 
			ph_WorkOrderPo.navigateToWOSFM(ph_ExploreSearchPO, "AUTOMATION SEARCH", "Work Orders", sWOName, sProcessname, commonUtility);
			
			//Click on ChecklistName
			ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
			System.out.println("clicked checklistname");
			Thread.sleep(5000);			
			
			//Starting new Checklist
			ph_ChecklistPO.getelecheckliststartnew(sChecklistName).click();
			Thread.sleep(2000);			
			ph_ChecklistPO.geteleInProgress().click();
			
			ph_ChecklistPO.getelechecklistPickListQAns("1. Picklist Question", "PicklOne").click();
			//passing "pickone" to be clicked basically a picklist
			Thread.sleep(2000);
			ph_ChecklistPO.geteleChecklistGenericText("PicklOne").click();
			ph_ChecklistPO.elechecklistRadioButtonQAns("2. RadioButton Question", "RadioTwo").click();
			Thread.sleep(5000);
				
			commonUtility.custScrollToElementAndClick("4. MultiPicklist Question");
			ph_ChecklistPO.getelechecklistcheckboxQAns("3. Checkbox Question", "CheckBoxTwo").click();;
			commonUtility.custScrollToElementAndClick("MultiOn, MultiTwo");
			System.out.println("Scrolled");
		//	ph_ChecklistPO.getelechecklistMultiPicklistQAns("4. MultiPicklist Question", "MultiOn, MultiTwo").click();
			Thread.sleep(2000);
			ph_ChecklistPO.geteleBackbutton().click();
			Thread.sleep(1000);

			commonUtility.custScrollToElementAndClick("5. Test Question");
			ph_ChecklistPO.getelechecklistTextQAns("5. Test Question").sendKeys("Text Question Answered");
			
			commonUtility.custScrollToElementAndClick("8. Number Question");
			commonUtility.custScrollToElementAndClick("8. Number Question");
			
			ph_ChecklistPO.getelechecklistNumberQAns("8. Number Question").sendKeys(snumberAns);
			
			Thread.sleep(2000);	
			ph_ChecklistPO.geteleSubmitbtn().click();
			ph_ChecklistPO.geteleCompleted().click();
			Thread.sleep(1000);	
			ph_ChecklistPO.geteleName().click();
			Thread.sleep(1000);	
			ph_ChecklistPO.geteleInProgress().click();
			sPicklistAns = ph_ChecklistPO.getelechecklistPickListQAns("1. Picklist Question", "PicklOne").getText();
			Assert.assertTrue(sPicklistAns.equals(sPicklistval),
					"Picklist answer --expected: " + sPicklistval + " actual: " + sPicklistAns + "");
			ExtentManager.logger.log(Status.PASS,
					"picklist answer sucessfull expected: " + sPicklistval + " actual: " + sPicklistAns + "");
			
			commonUtility.custScrollToElementAndClick("6. DateTime Question");
			sTextQAns = ph_ChecklistPO.getelechecklistTextQAnsValue("5. Test Question",stextAns).getText();
			Assert.assertTrue(sTextQAns.equals(stextAns),
					"Checklist Text answer --expected: " + stextAns + " actual: " + sTextQAns + "");
			ExtentManager.logger.log(Status.PASS,
					"Checklist Text answer sucessfull expected: " + stextAns + " actual: " + sTextQAns + "");

			// Sync the Data
			 ph_MorePo.syncData(commonUtility);
			 Thread.sleep(GenericLib.iVHighSleep);
			
			 									//SERVER SIDE API VALIDATIONS
			 
			 
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
			String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus),"checklist completed is not synced to server");
			ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");

			
			
			String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(ChecklistAnsjson.contains(stextAns), "checklist text question answer is not synced to server");
			ExtentManager.logger.log(Status.PASS,"checklist text question answer is synced to server");

/*			Assert.assertTrue(ChecklistAnsjson.contains(snumberAns), "checklist number answer sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist number answer sycned to server in checklist answer");
*/
			//	Assert.assertTrue(ChecklistAnsjson.contains(formattedDate), "checklist date answer was not sycned to server in checklist answer");
			//	ExtentManager.logger.log(Status.PASS,"checklist date question answer synced to server");

			//	Assert.assertTrue(ChecklistAnsjson.contains(sformattedDatetime), "checklist datetime answer was not sycned to server in checklist answer");
			//	ExtentManager.logger.log(Status.PASS,"checklist datetime question answer synced to server");

			Assert.assertTrue(ChecklistAnsjson.contains(spicklistAns), "checklist picklist answer was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist picklist question answer synced to server");
			
/*			Assert.assertTrue(ChecklistAnsjson.contains(sradioAns), "radio picklist answer was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist checkbox question answer synced to server");
*/			
			
			//Navigating to Checklist 
			ph_WorkOrderPo.navigateToWOSFM(ph_ExploreSearchPO, "AUTOMATION SEARCH", "Work Orders", sWOName, sProcessname, commonUtility);
			//Click on ChecklistName
			ph_ChecklistPO.getEleChecklistName(sChecklistName).click();
			System.out.println("clicked checklistname");
			Thread.sleep(5000);			
			ph_ChecklistPO.geteleCompleted().click();
			ph_ChecklistPO.getelechecklistinstance().click();
			
			//Validation after sync
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
			ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus),"checklist completed is not synced to server");
			ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");

			
			
			ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(ChecklistAnsjson.contains(stextAns), "checklist text question answer is not synced to server");
			ExtentManager.logger.log(Status.PASS,"checklist text question answer is synced to server");

/*			Assert.assertTrue(ChecklistAnsjson.contains(snumberAns), "checklist number answer sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist number answer sycned to server in checklist answer");
*/
			//	Assert.assertTrue(ChecklistAnsjson.contains(formattedDate), "checklist date answer was not sycned to server in checklist answer");
			//	ExtentManager.logger.log(Status.PASS,"checklist date question answer synced to server");

			//	Assert.assertTrue(ChecklistAnsjson.contains(sformattedDatetime), "checklist datetime answer was not sycned to server in checklist answer");
			//	ExtentManager.logger.log(Status.PASS,"checklist datetime question answer synced to server");

			Assert.assertTrue(ChecklistAnsjson.contains(spicklistAns), "checklist picklist answer was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist picklist question answer synced to server");
			
			
	}

	@AfterMethod
	public void tearDownDriver() {
	//	driver.quit();
	}

}

