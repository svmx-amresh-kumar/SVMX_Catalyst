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
	boolean configSync;
	public void preRequiste() throws Exception { 
		
		restServices.getAccessToken();
		sWOObejctApi="SVMXC__Service_Order__c?";
		
		sWOJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sWorkOrderID=restServices.restCreate(sWOObejctApi,sWOJsonData);
		sWOSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWorkOrderID+"\'";				
		sWOName1 =restServices.restGetSoqlValue(sWOSqlQuery,"Name"); //"WO-00000455";
		ExtentManager.logger.log(Status.INFO, "Work Order has been created through rest web service. CaseId : "+sWorkOrderID);
		sTestID = "RS_10562";
		sExploreSearch = GenericLib.readExcelData(GenericLib.sTestDataFile, sTestID,"ExploreSearch");
		sExploreChildSearchTxt = GenericLib.readExcelData(GenericLib.sTestDataFile, sTestID,"ExploreChildSearch");
		sFieldServiceName = GenericLib.readExcelData(GenericLib.sTestDataFile,sTestID, "ProcessName");
		
		
		configSync=commonUtility.ProcessCheck(restServices, genericLib, sFieldServiceName, "SCN_SelfDispatch_RS_10562_prerequisite", sTestID);
		
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10562Test() throws Exception {
		
		
		preRequiste();
		sSubject = "Testing "+sWOName1+" "+sTestID;
		
		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		//Config Sync
		if(configSync) {
			ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		}
		
		//Data Sync
		ph_MorePo.syncData(commonUtility);
	
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt, sWOName1, sFieldServiceName);
		ExtentManager.logger.log(Status.INFO, "Navigated to actions Create New Event of owkr Order from Work Orders of "+sExploreSearch);
		
		//Set Start time for event
		String[] deviceDate=commonUtility.getDeviceDate().split(" ");
		String hours=deviceDate[3].substring(0, 2);
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleStartDateTimeTxtFld(), 0, hours, "00");
		
		//Edit the subject
		commonUtility.switchContext("native");
		ph_WorkOrderPo.getEleSubjectTxtFld().sendKeys(sSubject);
		
		//Set end time
		commonUtility.setDateTime24hrs(ph_WorkOrderPo.getEleEndDateTimeTxtFld(), 0, String.format("%02d", Integer.parseInt(hours)+1), "00");
		commonUtility.switchContext("native");
		ph_WorkOrderPo.getElesave().click();
		ExtentManager.logger.log(Status.INFO, "Creation of new event from work order is successful");
		
		//Navigation to Calendar
		ph_CalendarPo.getEleCalendarBtn().click();
		
		//Validation of event on the calendar
		for(int i=0;i<ph_CalendarPo.getEleWOEventTitleTxt().size();i++){
		    if(ph_CalendarPo.getEleWOEventTitleTxt().get(i).getText().equals(sSubject))
		    {
		    	Assert.assertTrue(true,"Work Order event is not displayed on calendar");
		    	ExtentManager.logger.log(Status.PASS,"WorkOrder event is displayed successfully in calendar. Expected : "+sSubject+", Actual : "+ph_CalendarPo.getEleWOEventTitleTxt().get(i).getText());
		    	break;
			}
		    else if(i==ph_CalendarPo.getEleWOEventTitleTxt().size()-1) {
		    	ExtentManager.logger.log(Status.FAIL,"WorkOrder event is not displayed in calendar.");
		    	Assert.assertTrue(false,"Work Order event is not displayed on calendar");
		    }
		} 
		
		ph_MorePo.syncData(commonUtility);
		sSqlQuery ="SELECT+Name+from+SVMXC__SVMX_Event__c+Where+SVMXC__Service_Order__c=\'"+sWorkOrderID+"\'";
		String dbSubject=restServices.restGetSoqlValue(sSqlQuery,"Name");
		Assert.assertTrue(dbSubject.contains(sSubject), "Event is not created in DB.");
		ExtentManager.logger.log(Status.PASS,"Work Order is validated in DB. Expected : "+sSubject+", actual : "+dbSubject);
		
	}
}
