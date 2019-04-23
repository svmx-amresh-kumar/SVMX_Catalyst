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

	@Test(retryAnalyzer = Retry.class)
	public void RS_10553() throws Exception {

		System.out.println("SCN_LinkedSFMProcess_RS_10553");

		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		System.out.println(LocalDate.now().plusDays(1L));
		// Have a config Sync
		//ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		ph_MorePo.syncData(commonUtility);

		String sRandomNumber = commonUtility.generaterandomnumber("");
		// Creating Account from API
		sAccountName = "auto_account" + sRandomNumber;
		String sAccountId = restServices.restCreate("Account?", "{\"Name\":\"" + sAccountName + "\"}");

		// Creating Product from API
		sProductName = "auto_product1" + sRandomNumber;
		String sProductId = restServices.restCreate("Product2?", "{\"Name\":\"" + sProductName + "\" }");

		// Creating Installed Product from API

		sIBName = "auto_IB" + sRandomNumber;
		restServices.restCreate("SVMXC__Installed_Product__c?",
				"{\"Name\":\"" + sIBName + "\", \"SVMXC__Product__c\":\"" + sProductId
						+ "\",\"SVMXC__Serial_Lot_Number__c\":\"" + sIBName + "\" }");

		// Creating Contact from API
		sFirstName = "auto_contact";
		sLastName = sRandomNumber;
		sContactName = sFirstName + " " + sLastName;
		System.out.println(sContactName);
		String sContactId = restServices.restCreate("Contact?", "{\"FirstName\": \"" + sFirstName
				+ "\", \"LastName\": \"" + sLastName + "\", \"AccountId\": \"" + sAccountId + "\"}");
		// toolsPo.syncData(commonUtility);
		// Creating the Work Order using API

		String sWorkorderID = restServices.restCreate("SVMXC__Service_Order__c?", "{\"SVMXC__Company__c\": \""
				+ sAccountId + "\", \"SVMXC__Contact__c\": \"" + sContactId + "\", \"SVMXC__Priority__c\": \"High\"}");
		System.out.println("Work Order Id" + sWorkorderID);
		String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+Id+=\'" + sWorkorderID + "\'";
		restServices.getAccessToken();
		String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery, "Name");

		// Syncing the Data of the Work Order
		//ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		ph_MorePo.syncData(commonUtility);
		// Click on the Work Order
		Thread.sleep(2000);
		ph_WorkOrderPo.navigatetoWO(commonUtility, ph_ExploreSearchPo, "AUTOMATION SEARCH", "Work Orders", sworkOrderName);
		String sProcessname = "SFM Process for RS-10553";// Need to pass this from the Excel sheet
		Thread.sleep(2000);
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		Thread.sleep(3000);
		// To Add a PS Line to the Work Order and Parts to the Work ORder
		ph_WorkOrderPo.addPSLines(commonUtility, sIBName);
		Thread.sleep(3000);
		Point coordinates=ph_WorkOrderPo.getChildLineAddedItem(sIBName).getLocation();
		System.out.println("x:"+coordinates.getX()+"y:"+coordinates.getY());
		Dimension dim=driver.manage().window().getSize();
		System.out.println("x:"+dim.getWidth()+"y:"+dim.getHeight());
		new TouchAction(driver).press(new PointOption().withCoordinates(dim.getWidth()-25,coordinates.getY())).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
					.moveTo(new PointOption().withCoordinates(coordinates.getX(), coordinates.getY())).release().perform();
		ph_WorkOrderPo.getEleMore().click();
		ph_WorkOrderPo.getEleManageWorkDetails().click();
		Thread.sleep(3000);
		ph_WorkOrderPo.addPartsManageWD(commonUtility,ph_ExploreSearchPo, sProductName);
		ph_WorkOrderPo.getEleAddButton().click();
		ph_WorkOrderPo.getEleBackButton().click();
		// Discard the Changes by clicking on it
		ph_WorkOrderPo.getEleDiscardChangesButton().click();
		// Click on Cancel Button and verify the Changes of the ChildLines
		ph_WorkOrderPo.getEleBackButton().click();

		// Verifying if PS Lines are Visible and Part Lines are not Visible
		ph_WorkOrderPo.selectAction(commonUtility, sProcessname);
		Thread.sleep(3000);
		commonUtility.gotToTabHorizontal("PRODUCTS SERVICED");
		if (ph_WorkOrderPo.getChildLineAddedItem(sIBName).isDisplayed() == true) {
			ExtentManager.logger.log(Status.PASS, "The PS Lines are Added ");
			new TouchAction(driver).press(new PointOption().withCoordinates(dim.getWidth()-25,coordinates.getY())).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
			.moveTo(new PointOption().withCoordinates(coordinates.getX(), coordinates.getY())).release().perform();
			ph_WorkOrderPo.getEleMore().click();
			ph_WorkOrderPo.getEleManageWorkDetails().click();Thread.sleep(3000);
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
		Thread.sleep(1000);
		ph_WorkOrderPo.getEleSaveLnk().click();
		ph_WorkOrderPo.getEleBackButton().click();
		// Sync the Data and verify in the Server end if both the data are present
		Thread.sleep(2000);
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(2000);
		// Verify the Queries
		restServices.getAccessToken();
		String sSoqlquerychildlines = "Select+Count()+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"
				+ sworkOrderName + "\')";
		String sChildlines = restServices.restGetSoqlValue(sSoqlquerychildlines, "totalSize");
		if (sChildlines.equals("2")) {
			ExtentManager.logger.log(Status.PASS, "The Childlines Number is " + sChildlines);

			String sSoqlquerychildlineps = "Select+RecordType.Name+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"
					+ sworkOrderName + "\')";
			JSONArray sChildlinesarray = restServices.restGetSoqlJsonArray(sSoqlquerychildlineps);
			System.out.println(sChildlinesarray.length());
			try {
				for (int i = 0; i < sChildlinesarray.length(); i++) {
					String value = sChildlinesarray.getJSONObject(i).getJSONObject("RecordType").getString("Name");

					if (value.equals("Products Serviced")) {
						ExtentManager.logger.log(Status.PASS, "Products Serviced is added to WO ");
						System.out.println(value);
					}
					else if (value.equals("Usage/Consumption")) {
						ExtentManager.logger.log(Status.PASS, "Work Detail is added to WO");
						System.out.println(value);
					}
					else {
						ExtentManager.logger.log(Status.FAIL, value+" is wrongly added to WO");
						System.out.println(value);
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