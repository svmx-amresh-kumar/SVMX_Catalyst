  package com.ge.fsa.tests.tablet;

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
import com.ge.fsa.lib.Retry;
import com.ge.fsa.tablet.pageobjects.WorkOrderPO;
/**
 * 
 * @author meghanarao
 *
 */


public class SCN_ChildLineAddandDelete_RS_10568 extends BaseLib {
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

	@Test(retryAnalyzer=Retry.class)
	public void RS_10568() throws Exception {
		
		System.out.println("SCN_RS10568_ChildLineAddDelete");
		
		loginHomePo.login(commonsUtility, exploreSearchPo);



		// To create a Work Order for Editing 
		String sRandomNumber = commonsUtility.generaterandomnumber("");
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
		sContactName = sFirstName+" "+sLastName;
		System.out.println(sContactName);
		restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccountId+"\"}");
		toolsPo.syncData(commonsUtility);
		Thread.sleep(genericLib.iMedSleep);
		// Creating the Work Order - To create the Childlines
		createNewPO.createWorkOrder(commonsUtility,sAccountName,sContactName, sProductName, "Medium", "Loan", sProformainVoice);
		toolsPo.syncData(commonsUtility);
		Thread.sleep(2000);
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
		// Select the Work Order from the Recent items
		recenItemsPO.clickonWorkOrder(commonsUtility, sworkOrderName);
		String sProcessname = "EditWoAutoTimesstamp";
		workOrderPo.selectAction(commonsUtility,sProcessname);
		Thread.sleep(20000);
		// Single Adding the Labor by clicking on the +Add button
		workOrderPo.addLaborParts(commonsUtility, workOrderPo, sProductName, "Calibration", sProcessname);
		commonsUtility.tap(workOrderPo.getEleClickSave());
		workOrderPo.selectAction(commonsUtility,sProcessname);
		Thread.sleep(2000);
		// Adding a new Line by clicking on +New button
		commonsUtility.tap(workOrderPo.getEleChildLineTapName(sProductName));
		commonsUtility.tap(workOrderPo.getEleclickNew());
		commonsUtility.tap(workOrderPo.getEleclickOK());
		commonsUtility.tap(workOrderPo.getElePartLaborLkUp());
		commonsUtility.lookupSearch(sProductName2);
		commonsUtility.tap(workOrderPo.getEleDoneBtn());
		
		// Deleting the Line by clicking on Remove Button - Removing one Labor
		commonsUtility.tap(workOrderPo.getEleChildLineTapName(sProductName));
		Thread.sleep(2000);
		commonsUtility.tap(workOrderPo.getEleremoveitem());
		commonsUtility.tap(workOrderPo.getEleclickyesitem());
		commonsUtility.tap(workOrderPo.getEleclickOK());
		Thread.sleep(2000);
		//Multi-Add of the Labor by clicking the +Add Button - Adding the Parts
		String[] sProductNamesArray = {sProductName2,sProductName3};
		workOrderPo.addParts(commonsUtility, workOrderPo,sProductNamesArray );
		Thread.sleep(1000);
		// Adding the Expenses to the Work Order
		workOrderPo.addExpense(commonsUtility, workOrderPo, sExpenseType,sProcessname,sLineQty,slinepriceperunit);
		Thread.sleep(3000);
		// Adding the Another Expense by clicking on New Button
		commonsUtility.tap(workOrderPo.getEleExpensestap(sExpenseType));
		commonsUtility.tap(workOrderPo.getEleclickNew());
		commonsUtility.tap(workOrderPo.getEleclickOK());
		//commonsUtility.tap(workOrderPo.getEleAddExpenseType());
		commonsUtility.setPickerWheelValue(workOrderPo.getEleAddExpenseType(), sExpenseType);
		commonsUtility.tap(workOrderPo.getEleDoneBtn());
		Thread.sleep(10000);
		// Saving all the Childlines of the Work Order
		commonsUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(1000);
		// Syncing this Data into the Server for the Work Order
		toolsPo.syncData(commonsUtility);
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