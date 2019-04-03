/*
 *  @author Vinod Tharavath
 *  Date/dateTime validaitons are commented/removed to revisit later.
 */
package com.ge.fsa.tests.tablet;

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
import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

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
	Boolean bProcessCheckResult  = false;

	@BeforeMethod
	public void initializeObject() throws Exception { // Initialization of objects

	}

	
	@Test(retryAnalyzer=Retry.class)
	public void scenario2_checklist() throws Exception {
		
		commonUtility.preReqSetup(genericLib);
		// Resinstall the app
		lauchNewApp("false");
		
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
		System.out.println("---------- "+ sChecklistName+"        &&&&&  ");
		// Creation of dynamic Work Order
		restServices.getAccessToken();
		String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no = "+sWOName);
		//sWOName="WO-00000695";
		bProcessCheckResult =commonUtility.ProcessCheck(restServices, genericLib, sChecklistName, sChecklistName, sTestCaseID);		


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

		// Pre Login to app
			loginHomePo.login(commonUtility, exploreSearchPo);	
		    toolsPo.OptionalConfigSync(toolsPo, commonUtility, bProcessCheckResult);
			//toolsPo.configSync(commonsUtility);
			toolsPo.syncData(commonUtility);
			Thread.sleep(GenericLib.iMedSleep);
			System.out.println(sWOName);
			//sWOName="WO-00004920";
			workOrderPo.navigateToWOSFM(commonUtility, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName, sFieldServiceName);					
			Thread.sleep(GenericLib.iMedSleep);
			commonUtility.tap(checklistPo.geteleChecklistName(sChecklistName));
			//commonsUtility.longPress(checklistPo.geteleChecklistName(sChecklistName));
			Thread.sleep(GenericLib.iLowSleep);
			
			//System.out.println("Entering Text Question Answer");
			commonUtility.tap(checklistPo.geteleChecklistAnswerTextArea(stextQuestion));
		
			System.out.println("-------------"+stextAns);
			checklistPo.geteleChecklistAnswerTextArea(stextQuestion).sendKeys(stextAns);
			
			//System.out.println("Entering Number Question Answer");
			commonUtility.tap(checklistPo.geteleChecklistAnsNumber(snumberQuestion));
			checklistPo.geteleChecklistAnsNumber(snumberQuestion).sendKeys(snumberAns);;
			
			//System.out.println("Selecting Picklist Question Answer");
			commonUtility.setPickerWheelValue(checklistPo.geteleChecklistAnsPicklist(spicklistQuestion), spicklistAns);
		    commonUtility.switchContext("WebView");
//			try {
//			checklistPo.geteleChecklistAnsDate(sdateQuestion).click();
//			System.out.println("*** USed normal click ******");
//			
//			}
//			catch(Exception er) {
//				commonsUtility.tap(checklistPo.geteleChecklistAnsDate(sdateQuestion));
//				System.out.println("*** USed normal SINGLE TAP ******");
//				
//			}
//			commonsUtility.switchContext("Native");
//			commonsUtility.getEleDonePickerWheelBtn().click();
//		    commonsUtility.switchContext("WebView");
		    commonUtility.setSpecificDate(checklistPo.geteleChecklistAnsDate(sdateQuestion),"0","0","0");
			//sdateAns = checklistPo.geteleChecklistAnsDate(sdateQuestion).getAttribute("value");
		    commonUtility.switchContext("Webview");
		    sdateAns = driver.findElement(By.xpath("//div[contains(@class,'checklisteditview')][not(contains(@class,'hidden'))]//div[text()='Date Question'][@class='x-innerhtml']/../following-sibling::div//div[@class='x-innerhtml']")).getAttribute("innerHTML");
		    System.out.println("dateANS is "+sdateAns);
		    SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yy");
	        dTempDate1 = parser.parse(sdateAns);
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        formattedDate = formatter.format(dTempDate1);
		    System.out.println("formateed date"+formattedDate);    	    	    	    
			try{commonUtility.clickAllowPopUp();}catch(Exception e) {}
			commonUtility.switchContext("Webview");
			
			
		    //System.out.println("Setting dateTime Question Answer");
//			checklistPo.geteleChecklistAnsDate(sdateTimeQuestion).click();
//		   // commonsUtility.tap((checklistPo.geteleChecklistAnsDate(sdateTimeQuestion)));
//		    commonsUtility.switchContext("Native");
//		   // commonsUtility.tap(commonsUtility.getEleDonePickerWheelBtn());
//			commonsUtility.getEleDonePickerWheelBtn().click();
			commonUtility.setDateTime24hrs(checklistPo.geteleChecklistAnsDate(sdateTimeQuestion), 0, "0", "0");
		    commonUtility.switchContext("WebView");
		    //sdateTimeAns = checklistPo.geteleChecklistAnsDate(sdateTimeQuestion).getAttribute("innerHTML");	    
		    sdateTimeAns=driver.findElement(By.xpath("//div[contains(@class,'checklisteditview')][not(contains(@class,'hidden'))]//div[text()='DateTime Question'][@class='x-innerhtml']/../following-sibling::div//div[@class='x-innerhtml']")).getAttribute("innerHTML");
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
	    
			try{commonUtility.clickAllowPopUp();}catch(Exception e) {}
		   commonUtility.switchContext("WebView");
		    //System.out.println("Setting Radio button  Question Answer");
	        
	        
		    commonUtility.tap(checklistPo.geteleChecklistAnsradio(sradioQuestion));
		  //  commonsUtility.tap(checklistPo.geteleChecklistAnsradio(sradioQuestion));
		    sradioAns = checklistPo.geteleChecklistAnsradio(sradioQuestion).getText();
			try{commonUtility.clickAllowPopUp();}catch(Exception e) {}
		   commonUtility.switchContext("WebView");
	    // tapping the next button in checklist
		 	commonUtility.tap(checklistPo.geteleNext());

		 	
			// submitting the checklist
			Thread.sleep(GenericLib.iHighSleep);
			try{commonUtility.clickAllowPopUp();}catch(Exception e) {}
			Thread.sleep(GenericLib.iLowSleep);
			System.out.println(driver.getContext());
			commonUtility.tap(checklistPo.eleChecklistSubmit());	
			Thread.sleep(GenericLib.iHighSleep);
			
			try{commonUtility.clickAllowPopUp();}catch(Exception e) {}
			commonUtility.switchContext("WebView");
			// tapping on the validation sucessfull checklist popup
			commonUtility.tap(checklistPo.geteleChecklistPopupSubmit());
			System.out.println("finished clicking on submit popup.");
			Thread.sleep(GenericLib.iLowSleep);

			
			// Tapping on Show Completed Checklists
			//System.out.println("going to tap on show completedchecklists");
			commonUtility.tap(checklistPo.geteleShowCompletedChecklist(),15,18);
			//System.out.println("tapped on completed checklist");
			//System.out.println("going to tap on the completedchecklist");
			commonUtility.tap(checklistPo.geteleCompletedChecklistName(sChecklistName));
			//System.out.println("tapped on completed checklist");
			Thread.sleep(GenericLib.iMedSleep);
			
			
			
			//System.out.println("====================Checklist Answers Valdation=========================");
			System.out.println("_______________________"+checklistPo.geteleChecklistAnswerTextArea(stextQuestion).getAttribute("value"));
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
			System.out.println("Going to navigate back to work Order");
			checklistPo.navigateBacktoWorkOrder(commonUtility);
			
			
			
			// Sync the Data
			 toolsPo.syncData(commonUtility);		
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
					
	}

	@AfterMethod
	public void tearDownDriver() {
	//	driver.quit();
	}

}

