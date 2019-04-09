package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_ChildLineAddandDelete_RS_10568 extends BaseLib{

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
		
		ph_LoginHomePo.login(commonUtility, ph_MorePo);



		// To create a Work Order for Editing 
		String sRandomNumber = commonUtility.generaterandomnumber("");
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
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(genericLib.iMedSleep);
		// Creating the Work Order - To create the Childlines
		ph_CreateNewPo.createWorkOrder(commonUtility, sAccountName, sContactName, sProductName, "Medium", "Loan", sProformainVoice);
		Thread.sleep(2000);
		ph_MorePo.syncData(commonUtility);
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		//restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");
		// Select the Work Order from the Recent items
		ph_RecentsPo.selectRecentsItem(sworkOrderName);
		Thread.sleep(2000);
		String sProcessname = "EditWoAutoTimesstamp";
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		Thread.sleep(4000);
		// Single Adding the Labor by clicking on the +Add button
		//workOrderPo.addLaborParts(commonUtility, workOrderPo, sProductName, "Calibration", sProcessname);
		ph_WorkOrderPo.addLabor(sProductName);
		ph_WorkOrderPo.getElesave().click();
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		Thread.sleep(4000);
		ph_WorkOrderPo.addLabor(sProductName2);
		// Deleting the Line by clicking on Remove Button - Removing one Labor
		ph_WorkOrderPo.getEleChildLineItem(sProductName).click();
		Thread.sleep(2000);
		commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getEleRemoveButton());
		ph_WorkOrderPo.getEleRemoveButton().click();
		Thread.sleep(2000);
		//Multi-Add of the Labor by clicking the +Add Button - Adding the Parts
		String[] sProductNamesArray = {sProductName2,sProductName3};
		ph_WorkOrderPo.addParts(commonUtility, sProductNamesArray);
		Thread.sleep(2000);
		// Adding the Expenses to the Work Order
		//workOrderPo.addExpense(commonUtility, workOrderPo, sExpenseType,sProcessname,sLineQty,slinepriceperunit);
		ph_WorkOrderPo.addExpense(commonUtility, sExpenseType, sLineQty, slinepriceperunit);
		Thread.sleep(3000);
		// Adding the Another Expense by clicking on New Button
		ph_WorkOrderPo.addExpense(commonUtility, sExpenseType, sLineQty, slinepriceperunit);
		Thread.sleep(2000);
		// Saving all the Childlines of the Work Order
		ph_WorkOrderPo.getElesave().click();
		Thread.sleep(2000);
		// Syncing this Data into the Server for the Work Order
		ph_MorePo.syncData(commonUtility);
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