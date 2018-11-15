/*
 *  @author Vinod Tharavath
 *  Date/dateTime validaitons are commented/removed to revisit later.
 */
package com.ge.fsa.tests;

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
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class Sanity2_Explore_Checklist_Config_Sync_DataSync_RS11180 extends BaseLib {

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

	@BeforeMethod
	public void initializeObject() throws Exception { // Initialization of objects

	}

	
	@Test(enabled = true)
	public void scenario2_checklist() throws Exception {
		sSheetName = "RS_2389";
		sTestCaseID = "RS_2389_checklist";
		sCaseWOID = "RS_2389_checklistID";
		//sCaseSahiFile = "backOffice/appium_verifyWorkDetails.sah";
		//sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Bangalore\"}";
		
		//Extracting Excel Data
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		//sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");

		// Creation of dynamic Work Order
		restServices.getAccessToken();
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no = "+sWOName);
		//sWOName="WO-00000695";
		
		
		String sradioQuestion ="checkbox2372018162542";
		String sradioAns = null;		
		String stextQuestion = "text2372018162553";
		String stextAns = "Text Question Answered";
		String snumberQuestion = "number2372018162548";
		String snumberAns ="200";		
		String spicklistQuestion = "picklist2372018162550";
		String spicklistAns = "Answer2372018163046";
		String sdateQuestion = "date2372018162544";
		String sdateAns = null;
		String sdateTimeQuestion = "dateTime2372018162545";
		String sdateTimeAns = null;
		String schecklistStatus = "Completed";
		
	//	GenericLib.setCongigValue(GenericLib.sDataFile, sCaseWOID, sWOName);


			// Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);			
			
			
			
			//toolsPo.configSync(commonsPo);
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);					
			Thread.sleep(GenericLib.iMedSleep);
			
			
			Thread.sleep(2000);
			

			//System.out.println("Going to Enter checklist");
			
			commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
			
			//commonsPo.longPress(checklistPo.geteleChecklistName(sChecklistName));
			Thread.sleep(GenericLib.iLowSleep);
			
			//System.out.println("Entering Text Question Answer");
			checklistPo.geteleChecklistAnswerTextArea(stextQuestion).sendKeys(stextAns);
			
			//System.out.println("Entering Number Question Answer");
			checklistPo.geteleChecklistAnsNumber(snumberQuestion).sendKeys(snumberAns);;
			
			//System.out.println("Selecting Picklist Question Answer");
			commonsPo.setPickerWheelValue(checklistPo.geteleChecklistAnsPicklist(spicklistQuestion), spicklistAns);
			
			//System.out.println("Setting  Date Question Answer");
			checklistPo.geteleChecklistAnsDate(sdateQuestion).click();
			commonsPo.switchContext("Native");
		   // commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
			commonsPo.getEleDonePickerWheelBtn().click();
		    commonsPo.switchContext("WebView");
		    sdateAns = checklistPo.geteleChecklistAnsDate(sdateQuestion).getAttribute("value");
		    System.out.println("dateANS is "+sdateAns);
		    SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy");
	        dTempDate1 = parser.parse(sdateAns);
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        formattedDate = formatter.format(dTempDate1);
		    System.out.println("formateed date"+formattedDate);    	    	    	    
			try{commonsPo.clickAllowPopUp();}catch(Exception e) {}

		    
		    //System.out.println("Setting dateTime Question Answer");
		    checklistPo.geteleChecklistAnsDate(sdateTimeQuestion).click();
		    commonsPo.switchContext("Native");
		   // commonsPo.tap(commonsPo.getEleDonePickerWheelBtn());
			commonsPo.getEleDonePickerWheelBtn().click();
		    commonsPo.switchContext("WebView");
		    sdateTimeAns = checklistPo.geteleChecklistAnsDate(sdateTimeQuestion).getAttribute("value");	    
		    System.out.println("direct sdatetime"+sdateTimeAns);	    
		    SimpleDateFormat parser1 = new SimpleDateFormat("MM/dd/yy hh:mm");
		    dTempDate1 = parser1.parse(sdateTimeAns);
	        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String stempDate =  formatter1.format(dTempDate1);
	        System.out.println("formatter1.format value   "+stempDate);
	        dTempDate1 = formatter1.parse(stempDate);
	        //adding 7 hours to set to UTC/GMT time.. this is from PST timezone as 
        	Instant insDate =dTempDate1.toInstant().plus(7, ChronoUnit.HOURS);
	        System.out.println("7 aded to instant"+insDate); 
 
	        //formatter1.format(datetime1);
	        //System.out.println("after format datetime1"+datetime1);
	        sformattedDatetime = formatter1.format(dTempDate1);
	        dTempDate1 = Date.from(insDate);
	        sformattedDatetime = formatter1.format((dTempDate1));  
	        System.out.println("formateed dateTime"+sformattedDatetime);
	    
			try{commonsPo.clickAllowPopUp();}catch(Exception e) {}

		    //System.out.println("Setting Radio button  Question Answer");
		    checklistPo.geteleChecklistAnsradio(sradioQuestion).click();
		    commonsPo.tap(checklistPo.geteleChecklistAnsradio(sradioQuestion));
		    sradioAns = checklistPo.geteleChecklistAnsradio(sradioQuestion).getText();
			try{commonsPo.clickAllowPopUp();}catch(Exception e) {}

		    
		    // tapping the next button in checklist
		 	commonsPo.tap(checklistPo.geteleNext());

		 	
			// submitting the checklist
			Thread.sleep(GenericLib.iHighSleep);
			try{commonsPo.clickAllowPopUp();}catch(Exception e) {}
			Thread.sleep(genericLib.iLowSleep);
			System.out.println(driver.getContext());
			commonsPo.tap(checklistPo.eleChecklistSubmit());	
			Thread.sleep(GenericLib.iHighSleep);
			
			try{commonsPo.clickAllowPopUp();}catch(Exception e) {}

			// tapping on the validation sucessfull checklist popup
			commonsPo.tap(checklistPo.geteleChecklistPopupSubmit());
			System.out.println("finished clicking on submit popup.");

			
			// Tapping on Show Completed Checklists
			//System.out.println("going to tap on show completedchecklists");
			commonsPo.tap(checklistPo.geteleShowCompletedChecklist());
			//System.out.println("tapped on completed checklist");
			//System.out.println("going to tap on the completedchecklist");
			commonsPo.tap(checklistPo.geteleCompletedChecklistName(sChecklistName));
			//System.out.println("tapped on completed checklist");
			
			
			
			
			//System.out.println("====================Checklist Answers Valdation=========================");
			
			Assert.assertEquals(checklistPo.geteleChecklistAnswerTextArea(stextQuestion).getAttribute("value"), stextAns, "textquestion answered is not displayed");
			ExtentManager.logger.log(Status.PASS,"ChecklistText Quesiton Answer validation sucessfull");

			
		//	Assert.assertEquals(checklistPo.geteleChecklistAnsDate(sdateQuestion).getAttribute("value"), sdateAns, "date checklist question answered is not displayed");
		//ExtentManager.logger.log(Status.PASS,"Checklist Date Quesiton Answer validation sucessfull");

					
			//Assert.assertEquals(checklistPo.geteleChecklistAnsDate(sdateTimeQuestion).getAttribute("value"), sdateTimeAns, "datetime checklist question answered is not displayed");
			//ExtentManager.logger.log(Status.PASS,"Checklist Datetime Quesiton Answer validation sucessfull");

		
			Assert.assertEquals(checklistPo.geteleChecklistAnsNumber(snumberQuestion).getAttribute("value"), snumberAns, "number checklist question answered is not displayed");
			ExtentManager.logger.log(Status.PASS,"Checklist Number Quesiton Answer validation sucessfull");

			
			Assert.assertEquals(checklistPo.geteleChecklistAnsPicklist(spicklistQuestion).getAttribute("value"), spicklistAns, "number checklist question answered is not displayed");
			ExtentManager.logger.log(Status.PASS,"Checklist Number Quesiton Answer validation sucessfull");

			
			//Navigating back to Work Orders
			checklistPo.navigateBacktoWorkOrder(commonsPo);
			
			
			
			// Sync the Data
			 toolsPo.syncData(commonsPo);		
			 
			
			 									//SERVER SIDE API VALIDATIONS
			 
			 
			System.out.println("validating if checklist is synced to server.validate the checklist status and answers through API.");
			String ChecklistQuery = "select+SVMXC__Status__c,SVMXC__ChecklistJSON__c+from+SVMXC__Checklist__c+where+SVMXC__Work_Order__c+in+(SELECT+id+from+SVMXC__Service_Order__c+where+name+=\'"+sWOName+"')";
			String ChecklistQueryval = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__Status__c");	
			Assert.assertTrue(ChecklistQueryval.contains(schecklistStatus),"checklist completed is not synced to server");
			ExtentManager.logger.log(Status.PASS,"Checklist Completed status is displayed in Salesforce after sync");

			
			
			String ChecklistAnsjson = restServices.restGetSoqlValue(ChecklistQuery, "SVMXC__ChecklistJSON__c");
			Assert.assertTrue(ChecklistAnsjson.contains(stextAns), "checklist text question answer is not synced to server");
			ExtentManager.logger.log(Status.PASS,"checklist text question answer is not synced to server");

			Assert.assertTrue(ChecklistAnsjson.contains(snumberAns), "checklist number answer sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist number answer sycned to server in checklist answer");

		//	Assert.assertTrue(ChecklistAnsjson.contains(formattedDate), "checklist date answer was not sycned to server in checklist answer");
		//	ExtentManager.logger.log(Status.PASS,"checklist date question answer synced to server");

		//	Assert.assertTrue(ChecklistAnsjson.contains(sformattedDatetime), "checklist datetime answer was not sycned to server in checklist answer");
		//	ExtentManager.logger.log(Status.PASS,"checklist datetime question answer synced to server");

			Assert.assertTrue(ChecklistAnsjson.contains(spicklistAns), "checklist picklist answer was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist picklist question answer synced to server");
			
			Assert.assertTrue(ChecklistAnsjson.contains(sradioAns), "radio picklist answer was not sycned to server in checklist answer");
			ExtentManager.logger.log(Status.PASS,"checklist checkbox question answer synced to server");

			
																	
		//	genericLib.executeSahiScript(GenericLib.getCongigValue(GenericLib.sDataFile, "RS_2389_SAHISCRIPT"),
		//			sTestCaseID);
		
	}

	@AfterMethod
	public void tearDownDriver() {
	//	driver.quit();
	}

}

