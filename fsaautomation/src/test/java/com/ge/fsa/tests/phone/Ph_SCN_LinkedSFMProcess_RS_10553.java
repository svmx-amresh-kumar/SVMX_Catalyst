package com.ge.fsa.tests.phone;

import java.time.Duration;
import java.time.LocalDate;

import org.json.JSONArray;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_SCN_LinkedSFMProcess_RS_10553 extends BaseLib{

	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sIBName = null;
	String sProcessname = "SFM Process for RS-10553";

	@Test(retryAnalyzer = Retry.class)
	public void RS_10553() throws Exception {
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6500");
		}else {
			commonUtility.addJiraLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6794");

		}
			
		System.out.println("SCN_LinkedSFMProcess_RS_10553");
		boolean configSync=commonUtility.ProcessCheck(restServices, sProcessname, "appium/scenario_10553.sah", "LinkedSFMProcess_10553");


		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		// Have a config Sync
		if(configSync) {
			ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		}
		ph_MorePo.syncData(commonUtility);

		String sRandomNumber = commonUtility.generateRandomNumber("");
		// Creating Account from API
		sAccountName = "auto_account" + sRandomNumber;
		String sAccountId = restServices.restCreate("Account?", "{\"Name\":\"" + sAccountName + "\"}");
		ExtentManager.logger.log(Status.INFO, "Account has been created through rest web service with Name : "+sAccountName+" and returned AccountId : "+sAccountId);


		// Creating Product from API
		sProductName = "auto_product1" + sRandomNumber;
		String sProductId = restServices.restCreate("Product2?", "{\"Name\":\"" + sProductName + "\" }");
		ExtentManager.logger.log(Status.INFO, "Product has been created through rest web service with Name : "+sProductName+" and returned AccountId : "+sProductId);

		// Creating Installed Product from API

		sIBName = "auto_IB" + sRandomNumber;
		String sIBId=restServices.restCreate("SVMXC__Installed_Product__c?",
				"{\"Name\":\"" + sIBName + "\", \"SVMXC__Product__c\":\"" + sProductId
						+ "\",\"SVMXC__Serial_Lot_Number__c\":\"" + sIBName + "\" }");
		ExtentManager.logger.log(Status.INFO, "Installed Product has been created through rest web service with ProductId : "+sProductId+" and returned InstalledProductId : "+sIBId);


		// Creating Contact from API
		sFirstName = "auto_contact";
		sLastName = sRandomNumber;
		sContactName = sFirstName + " " + sLastName;
		System.out.println(sContactName);
		String sContactId = restServices.restCreate("Contact?", "{\"FirstName\": \"" + sFirstName
				+ "\", \"LastName\": \"" + sLastName + "\", \"AccountId\": \"" + sAccountId + "\"}");
		ExtentManager.logger.log(Status.INFO, "Contact created through webservices with FirstName : "+sFirstName+" , LastName : "+sLastName+" , AccountId : "+sAccountId+
				" and returned ContactId : "+sContactId);
		// toolsPo.syncData(commonUtility);
		// Creating the Work Order using API

		String sWorkorderID = restServices.restCreate("SVMXC__Service_Order__c?", "{\"SVMXC__Company__c\": \""
				+ sAccountId + "\", \"SVMXC__Contact__c\": \"" + sContactId + "\", \"SVMXC__Priority__c\": \"High\"}");
		ExtentManager.logger.log(Status.INFO, "WorkOrder is created through webservice with Contact : "+sContactId+" and returned WorkOder : "+sWorkorderID);
		System.out.println("Work OrderId" + sWorkorderID);
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+Id+=\'" + sWorkorderID + "\'";
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery, "Name");

		// Syncing the Data of the Work Order
		//ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		ph_MorePo.syncData(commonUtility);
		// Click on the Work Order
		ph_WorkOrderPo.navigatetoWO(commonUtility, ph_ExploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);
		// Need to pass this from the Excel sheet
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		// To Add a PS Line to the Work Order and Parts to the Work ORder
		ph_WorkOrderPo.addPSLines(commonUtility, sIBName);
//		Point coordinates=ph_WorkOrderPo.getChildLineAddedItem(sIBName).getLocation();
//		System.out.println("x:"+coordinates.getX()+"y:"+coordinates.getY());
//		Dimension dim=driver.manage().window().getSize();
//		System.out.println("x:"+dim.getWidth()+"y:"+dim.getHeight());
//		new TouchAction(driver).press(new PointOption().withCoordinates(dim.getWidth()-25,coordinates.getY())).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
//					.moveTo(new PointOption().withCoordinates(coordinates.getX(), coordinates.getY())).release().perform();
		commonUtility.clickPopup(ph_WorkOrderPo.getEleMore(), ph_WorkOrderPo.getEleManageWorkDetails());
//		ph_WorkOrderPo.getEleMore().click();
//		ph_WorkOrderPo.getEleManageWorkDetails().click();
		//Thread.sleep(3000);
		ph_WorkOrderPo.addPartsManageWD(commonUtility,ph_ExploreSearchPo, sProductName);
		ph_WorkOrderPo.getEleAddButton().click();
//		ph_WorkOrderPo.getEleBackButton().click();
//		// Discard the Changes by clicking on it
//		ph_WorkOrderPo.getEleDiscardChangesButton().click();
		commonUtility.clickPopup(ph_WorkOrderPo.getEleBackButton(), ph_WorkOrderPo.getEleDiscardChangesButton());
		// Click on Cancel Button and verify the Changes of the ChildLines
		ph_WorkOrderPo.getEleBackButton().click();

		// Verifying if PS Lines are Visible and Part Lines are not Visible
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		commonUtility.gotToTabHorizontal("PRODUCTS SERVICED");
		if (ph_WorkOrderPo.getChildLineAddedItem(sIBName).isDisplayed() == true) {
			ExtentManager.logger.log(Status.PASS, "The PS Lines are Added ");
//			new TouchAction(driver).press(new PointOption().withCoordinates(dim.getWidth()-25,coordinates.getY())).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
//			.moveTo(new PointOption().withCoordinates(coordinates.getX(), coordinates.getY())).release().perform();
//			ph_WorkOrderPo.getEleMore().click();
//			ph_WorkOrderPo.getEleManageWorkDetails().click();
			commonUtility.clickPopup(ph_WorkOrderPo.getEleMore(), ph_WorkOrderPo.getEleManageWorkDetails());
			try {
				ph_WorkOrderPo.getEleeleIBId(sProductName).isDisplayed();
				ExtentManager.logger.log(Status.FAIL, "The Product from Parts is Saved - Not Expected Scenario");
			} catch (Exception e) {
				ExtentManager.logger.log(Status.PASS, "The Product from Parts is not Saved");
			}

		} else {
			ExtentManager.logger.log(Status.FAIL, "The PS Lines are Not Added ");

		}

		// To Add PS Lines and Parts to the Work Order and save ans Sync the Data
		ph_WorkOrderPo.addPartsManageWD(commonUtility,ph_ExploreSearchPo, sProductName);
		ph_WorkOrderPo.getEleAddButton().click();
		ph_WorkOrderPo.getEleSaveLnk().click();
		Thread.sleep(3000);
		System.out.println(ph_WorkOrderPo.getEleBackButton());
		ph_WorkOrderPo.getEleBackButton().click();
		
		// Sync the Data and verify in the Server end if both the data are present
		ph_MorePo.syncData(commonUtility);
		String sSoqlquerychildlines = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"
				+ sworkOrderName + "\')";
		String sChildlines = restServices.restGetSoqlValue(sSoqlquerychildlines, "totalSize");
		if (sChildlines.equals("2")) {
			ExtentManager.logger.log(Status.PASS, "The Childlines Number is " + sChildlines);

			String sSoqlquerychildlineps = "Select+RecordType.Name+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"
					+ sworkOrderName + "\')";
			JSONArray sChildlinesarray = restServices.restGetSoqlJsonArray(sSoqlquerychildlineps);
			try {
				for (int i = 0; i < sChildlinesarray.length(); i++) {
					String value = sChildlinesarray.getJSONObject(i).getJSONObject("RecordType").getString("Name");

					if (value.equals("Products Serviced")) {
						ExtentManager.logger.log(Status.PASS, "Products Serviced is added to WO ");
					}
					else if (value.equals("Usage/Consumption")) {
						ExtentManager.logger.log(Status.PASS, "Work Detail is added to WO");
					}
					else {
						ExtentManager.logger.log(Status.FAIL, value+" is wrongly added to WO");
					}
				}

			} catch (Exception e) {
				ExtentManager.logger.log(Status.FAIL, "Work Detail is Not added to WO and not Synced");
			}

		} else {
			ExtentManager.logger.log(Status.FAIL, "The Childlines Number  is " + sChildlines);
		}
//
	}

}