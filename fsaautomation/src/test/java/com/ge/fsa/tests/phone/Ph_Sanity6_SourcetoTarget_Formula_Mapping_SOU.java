/*
 *  @author 
 */
package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import java.awt.KeyEventPostProcessor;
import java.io.IOException;
import java.util.Arrays;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.phone.Ph_ExploreSearchPO;
import com.ge.fsa.pageobjects.phone.Ph_WorkOrderPO;

public class Ph_Sanity6_SourcetoTarget_Formula_Mapping_SOU extends BaseLib {

	int iWhileCnt = 0;
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectID = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sProductName = null;
	String sFieldServiceName = null;
	String sActivityType = null;
	String sCase = null;
	String sIssueTxt = null;
	String sOrderStatus = null;
	String sBillingType = null;
	String sSqlQuery = null;
	String sCaseID = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	String sSerialNumber = null;
	String sSheetName =null;
	boolean bProcessCheckResult = false;

	
	private void preRequiste() throws Exception { 

		restServices.getAccessToken();
		sSerialNumber = commonUtility.generateRandomNumber("SAN6_");
		
		//Creating Product
		sJsonData = "{\"Name\": \""+sSerialNumber+"\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectID+"\'";		
		
		//Creating Case
		sProductName  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		sJsonData = "{\"Origin\": \"phone\", \"Subject\": \"Sanity6 is validated\", \"Priority\": \"High\", \"Description\": \"Description of Sanity6 \",\"Status\": \"Escalated\"}";
		sObjectApi = "Case?";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+CaseNumber+from+Case+Where+id+=\'"+sObjectID+"\'";				
		sCaseID  =restServices.restGetSoqlValue(sSqlQuery,"CaseNumber"); 
		//sCaseID="00001550";
		commonUtility.executeSahiScript("appium/scenario6_prerequisite.sah", sTestCaseID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID +  "Sahi verification is successful");	
	}

	//@Test()
	@Test(retryAnalyzer=Retry.class)
	public void scenario6Test() throws Exception {
		sSheetName ="SANITY6";
		sDeviceDate = driver.getDeviceTime().split(" ");
		sTestCaseID = "SANITY6";

		sExploreSearch = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName , "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName , "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName , "ProcessName");
		sIssueTxt = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName , "IssueText");
		sOrderStatus = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName , "OrderStatus");
		sBillingType = CommonUtility.readExcelData(GenericLib.sTestDataFile,sSheetName , "BillingType");
		preRequiste();
		//sCaseID = "00001161";
		//sProductName="SANITY6";
		//lauchNewApp("false");

		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		//Config Sync for process
		ph_MorePo.OptionalConfigSync(commonUtility,ph_CalendarPo,bProcessCheckResult);
		Thread.sleep(GenericLib.iMedSleep);

		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep); 
		
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,sExploreSearch,sExploreChildSearchTxt,sCaseID,sFieldServiceName);
		commonUtility.gotToTabHorizontal(ph_WorkOrderPo.getStringParts());
		//need this wait as waitforelement did not work
		Thread.sleep(3000);
		commonUtility.longPress(ph_WorkOrderPo.geteleRemoveablePart());
		ph_WorkOrderPo.geteleRemoveablePart().click();
		
		if(BaseLib.sOSName.equalsIgnoreCase("android"))
		{
			commonUtility.waitforElement(ph_WorkOrderPo.geteleDelete(), 3);
			ph_WorkOrderPo.geteleDelete().click();
			commonUtility.waitforElement(ph_WorkOrderPo.geteleDelete(), 3);
			ph_WorkOrderPo.geteleDelete().click();;
		}
		else
		{
			commonUtility.waitforElement(ph_WorkOrderPo.geteleDelete(), 3);
			ph_WorkOrderPo.geteleDelete().click();
			Thread.sleep(3000);
			ph_WorkOrderPo.geteleDelete().click();

		}
		ph_WorkOrderPo.getEleBackButton().click();
		ph_WorkOrderPo.getEleOverViewTab().click();
		
		//Set the order status
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_WorkOrderPo.geteleOrderStatus(), "Open");
		Thread.sleep(GenericLib.iLowSleep);

		//billing type
		ph_CreateNewPo.selectFromPickList(commonUtility, ph_CreateNewPo.getElebillingtype(), "Loan");
		Thread.sleep(GenericLib.iLowSleep);
		
		//Add the workorder parts
		ph_WorkOrderPo.addParts(commonUtility, sProductName);
		commonUtility.isDisplayedCust(ph_WorkOrderPo.geteleAddedPart(sProductName));
		ph_WorkOrderPo.geteleAddedPart(sProductName).click();
		
		//Validating Mapping for text. 
		commonUtility.custScrollToElement(ph_WorkOrderPo.getEleWODesMappedTxt());
		Assert.assertTrue(ph_WorkOrderPo.getEleWODesMappedTxt().isDisplayed(),"Work description is not Mapped");
		if(BaseLib.sOSName.equalsIgnoreCase("android"))
		{
			ph_WorkOrderPo.getEleWODesMappedTxt().click();

		}
		ExtentManager.logger.log(Status.PASS,"Work Order Description Mapped is displayed successfully");
		
		//Validating Mapping for Number. 
		commonUtility.custScrollToElement(ph_WorkOrderPo.geteleBillableQtyLbl());
		String billableQfeed = "2";
		String billableQtyapp=ph_WorkOrderPo.geteleBillableQty().getText();
		Assert.assertTrue(billableQtyapp.equals(billableQfeed), "Billable Quantity mapped right!");
		ExtentManager.logger.log(Status.PASS,"Billing Quantity Mapped successfully");
		ph_WorkOrderPo.geteleXsymbol().click();
		commonUtility.isDisplayedCust(ph_WorkOrderPo.getElesave());
		ph_WorkOrderPo.getElesave().click();
		
		//Validating Source  object update.
		
		ph_WorkOrderPo.navigatetoWO(commonUtility,ph_ExploreSearchPo, sExploreSearch,sExploreChildSearchTxt,sCaseID);
		commonUtility.custScrollToElement(ph_WorkOrderPo.geteleDescriptiontext());
		String ssouClientValue =ph_WorkOrderPo.geteleDescriptiontext().getText();
		String ssouExpectedValue = "Source Object Updated";
		Assert.assertTrue(ssouClientValue.equals(ssouExpectedValue), "Source Object Not updated");
		ExtentManager.logger.log(Status.PASS,"Source Object Sucessful Expected :"+ssouExpectedValue+" Actual : "+ssouClientValue+"");

		// Validation on Server.
		
		// Collecting the Work Order number from the Server.
		ph_MorePo.syncData(commonUtility);

		String sQosqlquery = "SELECT+id,SVMXC__Case__c,Name+FROM+SVMXC__Service_Order__c+where+SVMXC__Case__c+in+(select+id+from+Case+where+CaseNumber+=\'"+sCaseID+"\')";
		//String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
		//restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sQosqlquery,"Name");
		ExtentManager.logger.log(Status.PASS,"Work Order Created sucessfully though source target process linked to case :"+sCaseID+"  and wo :"+sworkOrderName+" ");

		ph_RecentsItemsPo.selectRecentsItem(commonUtility, sworkOrderName);
		commonUtility.custScrollToElement(ph_WorkOrderPo.geteleProblemDescriptiontxt());
		String sProbdescWOClient = ph_WorkOrderPo.geteleProblemDescriptiontxt().getText();
		String sExpectedProbeDesc = "Description of Sanity6";
		Assert.assertTrue(sProbdescWOClient.equals(sExpectedProbeDesc), "Source to Target Failed!");
		ExtentManager.logger.log(Status.PASS,"Source to Target Process Sucessfull, WO Desc Expected :"+sExpectedProbeDesc+" Actual : "+sProbdescWOClient+"");

	}

}
