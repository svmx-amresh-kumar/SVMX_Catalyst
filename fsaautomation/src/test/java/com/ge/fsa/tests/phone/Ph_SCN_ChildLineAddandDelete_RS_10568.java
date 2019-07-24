package com.ge.fsa.tests.phone;

import java.util.Arrays;

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
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6454");
		}else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6809");

		}
			
		System.out.println("SCN_RS10568_ChildLineAddDelete");
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		// To create a Work Order for Editing 
		String sRandomNumber = commonUtility.generateRandomNumber("");
		
		// Creating Account from API
		sAccountName = "auto_account"+sRandomNumber;
		String sAccountId = restServices.restCreate("Account?","{\"Name\":\""+sAccountName+"\"}");
		ExtentManager.logger.log(Status.INFO, "Account has been created through rest web service with Name : "+sAccountName+" AccountId : "+sAccountId);
		
		// Creating Product from API
		sProductName = "auto_product1"+sRandomNumber;
		String sProductId1=restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		ExtentManager.logger.log(Status.INFO, "Product has been created through rest web service with Name : "+sProductName+" ProductId : "+sProductId1);

		// Creating Product2 from API
		sProductName2 = "auto_product2"+sRandomNumber;
		String sProductId2=restServices.restCreate("Product2?","{\"Name\":\""+sProductName2+"\" }");
		ExtentManager.logger.log(Status.INFO, "Product has been create through rest web service with Name : "+sProductName2+" ProductId : "+sProductId2);

		// Creating Product3 from API
		sProductName3 = "auto_product3"+sRandomNumber;
		String sProductId3=restServices.restCreate("Product2?","{\"Name\":\""+sProductName3+"\" }");
		ExtentManager.logger.log(Status.INFO, "Product has been create through rest web service with Name : "+sProductName3+" ProductId : "+sProductId3);

		
		// Creating Contact from API
		sFirstName = "auto_contact";
		sLastName = sRandomNumber;
		String sProformainVoice = "Proforma"+sRandomNumber;
		sContactName = sFirstName+" "+sLastName;
		System.out.println(sContactName);
		String sContactId=restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccountId+"\"}");
		ExtentManager.logger.log(Status.INFO, "Contact has been create through rest web service. With AccountId : "+sAccountId+" and ContactId : "+sContactId);
		ph_MorePo.syncData(commonUtility);

		// Creating the Work Order - To create the Childlines
		ph_CreateNewPo.createWorkOrder(commonUtility, sAccountName, sContactName, sProductName, "Medium", "Loan", sProformainVoice);
		ExtentManager.logger.log(Status.INFO, "Work order created successfully with details as Account Name : "+sAccountName+", Contact Name : "+
				sContactName+", Product Name : "+sProductName+" Priority Type : Medium, Billing Type : Loan, Proforma Invoice : "+sProformainVoice);
			
		ph_MorePo.syncData(commonUtility);
		
		// Collecting the Work Order number from the Server.
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");
		
		// Select the Work Order from the Recent items
		ph_RecentsItemsPo.selectRecentsItem(commonUtility, sworkOrderName);
		String sProcessname = "EditWoAutoTimesstamp";//It's created as part of prerequisite setup scripts.
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		ExtentManager.logger.log(Status.INFO, "selected action "+sProcessname);
		
		// Single Adding the Labor by clicking on the +Add button
		//workOrderPo.addLaborParts(commonUtility, workOrderPo, sProductName, "Calibration", sProcessname);
		ph_WorkOrderPo.addLabor(commonUtility, sProductName);
		ph_WorkOrderPo.getElesave().click();
		ExtentManager.logger.log(Status.INFO, "Added labor with Product Name : "+sProductName);
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		ExtentManager.logger.log(Status.INFO, "selected action "+sProcessname);
		ph_WorkOrderPo.addLabor(commonUtility, sProductName2);
		ExtentManager.logger.log(Status.INFO, "Added labor with Product Name : "+sProductName2);
		
		// Deleting the Line by clicking on Remove Button - Removing one Labor
		ph_WorkOrderPo.getEleChildLineItem(sProductName).click();
		commonUtility.clickPopup(ph_WorkOrderPo.getEleRemoveButton(), ph_WorkOrderPo.getEleRemoveButton());
		ExtentManager.logger.log(Status.INFO, "Removed labor with Product Name : "+sProductName);
		
		//Multi-Add of the Labor by clicking the +Add Button - Adding the Parts
		String[] sProductNamesArray = {sProductName2,sProductName3};
		ph_WorkOrderPo.addParts(commonUtility, sProductNamesArray);
		ExtentManager.logger.log(Status.INFO, "Added parts with Product Name : "+Arrays.toString(sProductNamesArray));
		
		// Adding the Expenses to the Work Order
		//workOrderPo.addExpense(commonUtility, workOrderPo, sExpenseType,sProcessname,sLineQty,slinepriceperunit);
		ph_WorkOrderPo.addExpense(commonUtility, sExpenseType, sLineQty, slinepriceperunit);
		ExtentManager.logger.log(Status.INFO, "Added Expenses with Expense Type : "+sExpenseType+", Line Quantity : "+sLineQty+", Line Proce Per Unit : "+slinepriceperunit);
		
		// Adding the Another Expense by clicking on New Button
		ph_WorkOrderPo.addExpense(commonUtility, sExpenseType, sLineQty, slinepriceperunit);
		ExtentManager.logger.log(Status.INFO, "Added Expenses with Expense Type : "+sExpenseType+", Line Quantity : "+sLineQty+", Line Proce Per Unit : "+slinepriceperunit);
		
		// Saving all the Childlines of the Work Order
		ph_WorkOrderPo.getElesave().click();
		ExtentManager.logger.log(Status.INFO, "Saved Work Order after adding child Lines");
		
		// Syncing this Data into the Server for the Work Order
		ph_MorePo.syncData(commonUtility);
		
		// Verifying the number of Child lines on the Server Side from API after the Sync
		String sSoqlQueryChildline = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		String sChildlines = restServices.restGetSoqlValue(sSoqlQueryChildline, "totalSize");	
		if(sChildlines.equals("5"))
		{
			ExtentManager.logger.log(Status.PASS,"The Childlines on the Work Order is Expected : 5, Actual : "+sChildlines);
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"The Childlines on the Work Order is Expected : 5, Actual : "+sChildlines);
		}

	}


}