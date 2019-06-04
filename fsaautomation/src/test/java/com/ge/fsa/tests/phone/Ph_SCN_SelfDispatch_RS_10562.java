/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_SelfDispatch_RS_10562 extends BaseLib {

	int iWhileCnt = 0;
	String sTestID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sSerialNumber = null;
	String sWorkOrderID = null;
	String sWOObejctApi = null;
	String sWOJsonData = null;
	String sWOName1 = null;
	String sWOSqlQuery = null;
	String sFieldServiceName = null;
	String sSubject =null;
	String sSqlQuery = null;
	List<WebElement> lsWoList = null;
	
	public void preRequiste() throws Exception { 
		
		restServices.getAccessToken();
		sWOObejctApi="SVMXC__Service_Order__c?";
		
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
		sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		sWOName1 =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455"; 
		
		genericLib.executeSahiScript("appium/SCN_SelfDispatch_RS_10562_prerequisite.sah", sTestID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10562Test() throws Exception {
		
		sTestID = "RS_10562";
		sExploreSearch = GenericLib.readExcelData(GenericLib.sTestDataFile, sTestID,"ExploreSearch");
		sExploreChildSearchTxt = GenericLib.readExcelData(GenericLib.sTestDataFile, sTestID,"ExploreChildSearch");
		sFieldServiceName = GenericLib.readExcelData(GenericLib.sTestDataFile,sTestID, "ProcessName");
		preRequiste();
		sSubject = "Testing "+sWOName1+" "+sTestID;
		
		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		//Config Sync
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		Thread.sleep(GenericLib.iMedSleep);
		
		//Data Sync
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
	
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName1, sFieldServiceName);
		//Set Start time for event
		String[] deviceDate=commonUtility.getDeviceDate().split(" ");
		String hours=deviceDate[3].substring(0, 2);
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleStartDateTimeTxtFld(), 0, hours, "00");
		//Edit the subject
		commonUtility.switchContext("native");
		ph_WorkOrderPo.getEleSubjectTxtFld().sendKeys(sSubject);
		
		//Set end time
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleEndDateTimeTxtFld(), 0, Integer.toString(Integer.parseInt(hours)+1), "00");
		commonUtility.switchContext("native");
		ph_WorkOrderPo.getElesave().click();
		
		//Validation of auto update process
		//Assert.assertTrue(workOrderPo.getEleSavedSuccessTxt().isDisplayed(), "Update process is not successful.");
		ExtentManager.logger.log(Status.PASS,"WorkOrder saved successfully.");
		
		//Navigation to Calendar
		Thread.sleep(GenericLib.iHighSleep);
		ph_CalendarPo.getEleCalendarBtn().click();
		Thread.sleep(GenericLib.iMedSleep);
		
		//Validation of event on the calender
		for(int i=0;i<ph_CalendarPo.getEleWOEventTitleTxt().size();i++){
		    if(ph_CalendarPo.getEleWOEventTitleTxt().get(i).getText().equals(sSubject))
		    {
		    	Assert.assertTrue(true,"Work Order event is not displayed on calender");
		    	ExtentManager.logger.log(Status.PASS,"WorkOrder event is displayed successfully in calender.");
		    	break;
			}
		    else if(i==ph_CalendarPo.getEleWOEventTitleTxt().size()-1) {
		    	Assert.assertTrue(false,"Work Order event is not displayed on calender");
		    	ExtentManager.logger.log(Status.FAIL,"WorkOrder event is not displayed in calender.");
		    }
		} 
		
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.i30SecSleep);
		try {
			//Validation of event creation at server side after sync.
			sSqlQuery ="SELECT+Name+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sWorkOrderID+"\'";				
			Assert.assertTrue(restServices.restGetSoqlValue(sSqlQuery,"Name").equals(sSubject), "Event is not created");
			ExtentManager.logger.log(Status.PASS,"Event  is successfully created.");
		}catch(Exception e)
		{	try {	//Validation of event creation at server side after sync.
				sSqlQuery ="SELECT+Name+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sWorkOrderID+"\'";				
				Assert.assertTrue(restServices.restGetSoqlValue(sSqlQuery,"Name").contains(sWOName1), "Event is not created");
				ExtentManager.logger.log(Status.PASS,"Event  is successfully created.");
			}	catch(Exception ex){throw ex;}
		}
		
	}
}
