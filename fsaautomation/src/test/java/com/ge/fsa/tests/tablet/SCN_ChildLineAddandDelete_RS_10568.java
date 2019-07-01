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
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.tablet.WorkOrderPO;
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
		
		loginHomePo.login(commonUtility, exploreSearchPo);



		// To create a Work Order for Editing 
		String sRandomNumber = commonUtility.generateRandomNumber("");
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
		toolsPo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
		// Creating the Work Order - To create the Childlines
		createNewPO.createWorkOrder(commonUtility,sAccountName,sContactName, sProductName, "Medium", "Loan", sProformainVoice);
		toolsPo.syncData(commonUtility);
		Thread.sleep(2000);
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
		// Select the Work Order from the Recent items
		recenItemsPO.clickonWorkOrder(commonUtility, sworkOrderName);
		String sProcessname = "EditWoAutoTimesstamp";
		workOrderPo.selectAction(commonUtility,sProcessname);
		Thread.sleep(20000);
		// Single Adding the Labor by clicking on the +Add button
		workOrderPo.addLaborParts(commonUtility, workOrderPo, sProductName, "Calibration", sProcessname);
		commonUtility.tap(workOrderPo.getEleClickSave());
		workOrderPo.selectAction(commonUtility,sProcessname);
		Thread.sleep(2000);
		// Adding a new Line by clicking on +New button
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName));
		commonUtility.tap(workOrderPo.getEleclickNew());
		commonUtility.tap(workOrderPo.getEleclickOK());
		commonUtility.tap(workOrderPo.getElePartLaborLkUp());
		commonUtility.lookupSearch(sProductName2);
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		
		// Deleting the Line by clicking on Remove Button - Removing one Labor
		commonUtility.tap(workOrderPo.getEleChildLineTapName(sProductName));
		Thread.sleep(2000);
		commonUtility.tap(workOrderPo.getEleremoveitem());
		commonUtility.tap(workOrderPo.getEleclickyesitem());
		commonUtility.tap(workOrderPo.getEleclickOK());
		Thread.sleep(2000);
		//Multi-Add of the Labor by clicking the +Add Button - Adding the Parts
		String[] sProductNamesArray = {sProductName2,sProductName3};
		workOrderPo.addParts(commonUtility, workOrderPo,sProductNamesArray );
		Thread.sleep(1000);
		// Adding the Expenses to the Work Order
		workOrderPo.addExpense(commonUtility, workOrderPo, sExpenseType,sProcessname,sLineQty,slinepriceperunit);
		Thread.sleep(3000);
		// Adding the Another Expense by clicking on New Button
		commonUtility.tap(workOrderPo.getEleExpensestap(sExpenseType));
		commonUtility.tap(workOrderPo.getEleclickNew());
		commonUtility.tap(workOrderPo.getEleclickOK());
		//commonsUtility.tap(workOrderPo.getEleAddExpenseType());
		commonUtility.setPickerWheelValue(workOrderPo.getEleAddExpenseType(), sExpenseType);
		commonUtility.tap(workOrderPo.getEleDoneBtn());
		Thread.sleep(10000);
		// Saving all the Childlines of the Work Order
		commonUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(1000);
		// Syncing this Data into the Server for the Work Order
		toolsPo.syncData(commonUtility);
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
