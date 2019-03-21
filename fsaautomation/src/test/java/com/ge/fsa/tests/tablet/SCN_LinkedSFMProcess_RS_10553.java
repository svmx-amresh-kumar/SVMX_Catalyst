package com.ge.fsa.tests.tablet;

import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.tablet.pageobjects.ExploreSearchPO;
import com.ge.fsa.tablet.pageobjects.WorkOrderPO;

/**
 * 
 * @author meghanarao
 *
 */
public class SCN_LinkedSFMProcess_RS_10553 extends BaseLib {
	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sIBName = null;
	@Test(retryAnalyzer=Retry.class)
	public void RS_10553() throws Exception {
		
		System.out.println("SCN_LinkedSFMProcess_RS_10553");
		
		
		loginHomePo.login(commonsUtility, exploreSearchPo);
		System.out.println(LocalDate.now().plusDays(1L));
		// Have a config Sync
		toolsPo.configSync(commonsUtility);
		
		String sRandomNumber = commonsUtility.generaterandomnumber("");
		// Creating Account from API
		sAccountName = "auto_account"+sRandomNumber;
		String sAccountId = restServices.restCreate("Account?","{\"Name\":\""+sAccountName+"\"}");
		
		// Creating Product from API
		sProductName = "auto_product1"+sRandomNumber;
		String sProductId = restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
		
		// Creating Installed Product from API
		
		sIBName = "auto_IB"+sRandomNumber;
		restServices.restCreate("SVMXC__Installed_Product__c?", "{\"Name\":\""+sIBName+"\", \"SVMXC__Product__c\":\""+sProductId+"\",\"SVMXC__Serial_Lot_Number__c\":\""+sIBName+"\" }");
		
		
		// Creating Contact from API
		sFirstName = "auto_contact";
		sLastName = sRandomNumber;
		sContactName = sFirstName+" "+sLastName;
		System.out.println(sContactName);
		String sContactId = restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccountId+"\"}");
		//toolsPo.syncData(commonsUtility);
		// Creating the Work Order using API
		
		String sWorkorderID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__Company__c\": \""+sAccountId+"\", \"SVMXC__Contact__c\": \""+sContactId+"\", \"SVMXC__Priority__c\": \"High\"}");
		System.out.println("Work Order Id"+sWorkorderID);
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+Id+=\'"+sWorkorderID+"\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
		
		// Syncing the Data of the Work Order
		toolsPo.syncData(commonsUtility);
		// Click on the Work Order
		Thread.sleep(10000);
		workOrderPo.navigatetoWO(commonsUtility, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);	
		String sProcessname = "SFM Process for RS-10553";// Need to pass this from the Excel sheet
		Thread.sleep(2000);
		workOrderPo.selectAction(commonsUtility,sProcessname);
		
		// To Add a PS Line to the Work Order and Parts to the Work ORder
		workOrderPo.addPSLines(commonsUtility, workOrderPo,sIBName);
		commonsUtility.tap(workOrderPo.getEleLinkedSFM());
		commonsUtility.tap(workOrderPo.getEleSFMfromLinkedSFM("Manage Work Details for Products Serviced"));
		commonsUtility.tap(workOrderPo.getEleOKBtn());
		workOrderPo.addPartsManageWD(commonsUtility, workOrderPo,sProductName);
		commonsUtility.tap(workOrderPo.getEleSFMfromLinkedSFM(sProcessname));
		//Discard the Changes by clicking on it
		commonsUtility.tap(workOrderPo.getEleDiscardChanges());
		//Click on Cancel Button and verify the Changes of the ChildLines
		commonsUtility.tap(workOrderPo.getEleCancelLink());
		commonsUtility.tap(workOrderPo.getEleDiscardChanges());
		
		// Verifying if PS Lines are Visible and Part Lines are not Visible
		workOrderPo.selectAction(commonsUtility,sProcessname);
		if(workOrderPo.getEleeleIBId(sIBName).isDisplayed()== true)
		{
			ExtentManager.logger.log(Status.PASS,"The PS Lines are Added ");
			commonsUtility.tap(workOrderPo.getEleLinkedSFM());
			commonsUtility.tap(workOrderPo.getEleSFMfromLinkedSFM2("Manage Work Details for Products Serviced"));
			Thread.sleep(1000);
			commonsUtility.tap(workOrderPo.getEleOKBtn());
			try
			{
			workOrderPo.getEleeleIBId(sProductName).isDisplayed();
			ExtentManager.logger.log(Status.FAIL,"The Product from Parts is Saved - Not Expected Scenario");
			}
			catch (Exception e) {
				ExtentManager.logger.log(Status.PASS,"The Product from Parts is not Saved");
			}
			
		}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"The PS Lines are Not Added ");
			
		}
		
		// To Add PS Lines and Parts to the Work Order and save ans Sync the Data
		workOrderPo.addPartsManageWD(commonsUtility, workOrderPo,sProductName);
		commonsUtility.tap(workOrderPo.getEleClickSave());
		Thread.sleep(1000);
		commonsUtility.tap(workOrderPo.getEleClickSave());
		// Sync the Data and verify in the Server end if both the data are present
		Thread.sleep(20000);
		toolsPo.syncData(commonsUtility);
		Thread.sleep(20000);
		// Verify the Queries

		restServices.getAccessToken();
		String sSoqlquerychildlines = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
		String sChildlines = restServices.restGetSoqlValue(sSoqlquerychildlines, "totalSize");	
		if(sChildlines.equals("2"))
			{
			ExtentManager.logger.log(Status.PASS,"The Childlines Number is "+sChildlines);
			
			String sSoqlquerychildlineps = "Select+RecordType.Name+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkOrderName+"\')";
			JSONArray sChildlinesarray = restServices.restGetSoqlJsonArray(sSoqlquerychildlineps);
			System.out.println(sChildlinesarray.length());
			try
	        {
			for(int i=0;i<sChildlinesarray.length();i++)
			{
	            String value = sChildlinesarray.getJSONObject(i).getJSONObject("RecordType").getString("Name");
	           
				if(value.equals("Products Serviced"))
				{
					ExtentManager.logger.log(Status.PASS,"Products Serviced is added to WO ");
					System.out.println(value);
				}
				if(value.equals("Usage/Consumption"))
				{
					ExtentManager.logger.log(Status.PASS,"Work Detail is added to WO");
					System.out.println(value);
				}
			}
	            
			}
			catch(Exception e)
            {
            	ExtentManager.logger.log(Status.FAIL,"Work Detail is Not added to WO and not Synced");
            }
			
			}
		else
		{
			ExtentManager.logger.log(Status.FAIL,"The Childlines Number  is "+sChildlines);
		}
		
		
	
		
	}

}