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

public class SCN_RS10568_ChildLineAddDelete extends BaseLib {
	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sProductName2 = null;
	String sProductName3 = null;
	String sExpenseType = "Airfare";
	String sLineQty = "10.0";
	String slinepriceperunit = "1000";

	@Test(enabled = true)
	public void SCN_RS10568() throws Exception {
		
		System.out.println("SCN_RS710568_ChildLineAddDelete");
		
		loginHomePo.login(commonsPo, exploreSearchPo);
		// To create a Work Order for Editing 
		String sRandomNumber = commonsPo.generaterandomnumber("");
		// Creating Account from API
		sAccountName = "auto_account"+sRandomNumber;
		String sAccountId = restServices.restCreate("Account?","{\"Name\":\""+sAccountName+"\"}");
		
		// Creating Product from API
		sProductName = "auto_product1"+sRandomNumber;
		restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		
		// Creating Product2 from API
		sProductName2 = "auto_product2"+sRandomNumber;
		restServices.restCreate("Product2?","{\"Name\":\""+sProductName2+"\" }");
		
		// Creating Product3 from API
		sProductName3 = "auto_product3"+sRandomNumber;
		restServices.restCreate("Product2?","{\"Name\":\""+sProductName3+"\" }");
		
		
		// Creating Contact from API
		sFirstName = "auto_contact";
		sLastName = sRandomNumber;
		String sProformainVoice = "Proforma"+sRandomNumber;
		String sEventSubject = "EventName"+sRandomNumber;
		sContactName = sFirstName+" "+sLastName;
		System.out.println(sContactName);
		restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccountId+"\"}");
		toolsPo.syncData(commonsPo);
		// Creating the Work Order - To create the Childlines
		createNewPO.createWorkOrder(commonsPo,sAccountName,sContactName, sProductName, "Medium", "Loan", sProformainVoice);
		toolsPo.syncData(commonsPo);
		Thread.sleep(2000);
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
		// Select the Work Order from the Recent items
		recenItemsPO.clickonWorkOrder(commonsPo, sworkOrderName);
		String sProcessname = "EditWoAutoTimesstamp";
		workOrderPo.selectAction(commonsPo,sProcessname);
		Thread.sleep(2000);
		// Single Adding the Labor by clicking on the +Add button
		workOrderPo.addLaborParts(commonsPo, workOrderPo, sProductName, "Calibration", sProcessname);
		commonsPo.tap(workOrderPo.getEleClickSave());
		workOrderPo.selectAction(commonsPo,sProcessname);
		Thread.sleep(2000);
		// Adding a new Line by clicking on +New button
		commonsPo.tap(workOrderPo.getEleProductTapName(sProductName));
		commonsPo.tap(workOrderPo.getEleclickNew());
		commonsPo.tap(workOrderPo.getEleclickOK());
		commonsPo.tap(workOrderPo.getElePartLaborLkUp());
		commonsPo.lookupSearch(sProductName2);
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		
		// Deleting the Line by clicking on Remove Button - Removing one Labor
		commonsPo.tap(workOrderPo.getEleProductTapName(sProductName));
		Thread.sleep(2000);
		commonsPo.tap(workOrderPo.getEleremoveitem());
		commonsPo.tap(workOrderPo.getEleclickyesitem());
		commonsPo.tap(workOrderPo.getEleclickOK());
		Thread.sleep(2000);
		//Multi-Add of the Labor by clicking the +Add Button - Adding the Parts
		String[] sProductNamesArray = {sProductName2,sProductName3};
		workOrderPo.addParts(commonsPo, workOrderPo,sProductNamesArray );
		Thread.sleep(1000);
		// Adding the Expenses to the Work Order
		workOrderPo.addExpense(commonsPo, workOrderPo, sExpenseType,sProcessname,sLineQty,slinepriceperunit);
		Thread.sleep(3000);
		// Adding the Another Expense by clicking on New Button
		commonsPo.tap(workOrderPo.getEleExpensestap(sExpenseType));
		commonsPo.tap(workOrderPo.getEleclickNew());
		commonsPo.tap(workOrderPo.getEleclickOK());
		commonsPo.tap(workOrderPo.getEleAddExpenseType());
		commonsPo.pickerWheel(workOrderPo.getEleAddExpenseType(), sExpenseType);
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		Thread.sleep(10000);
		// Saving all the Childlines of the Work Order
		commonsPo.tap(workOrderPo.getEleClickSave());
		Thread.sleep(1000);
		// Syncing this Data into the Server for the Work Order
		toolsPo.syncData(commonsPo);
		// Verifying the number of Child lines on the Server Side from API after the Sync
	
		String sSoqlQueryChildline = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		restServices.getAccessToken();
		String sChildlines = restServices.restGetSoqlValue(sSoqlQueryChildline, "totalSize");	
		if(sChildlines.equals("5"))
		{
			ExtentManager.logger.log(Status.PASS,"The Childlines on the Work Order is "+sChildlines);


		System.out.println("The Childlines on the Work Order "+sChildlines);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"The Childlines on the Work Order isnt equal to 3");
			System.out.println("The Childlines on the Work Order "+sChildlines);
		}

	}

}
