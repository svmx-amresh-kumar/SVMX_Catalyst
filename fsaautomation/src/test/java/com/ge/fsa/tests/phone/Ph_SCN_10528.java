package com.ge.fsa.tests.phone;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;

import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_SCN_10528 extends BaseLib {
	
	String sTestCaseID = "RS_10528";
	String sScriptName = "appium/Scenario_10528.sah";
	String sZipCode = "51203";
	String sCountry = "India";
	String sCity = "Bangalore";
	Boolean bProcessCheckResult;
	String sProcessName = "AutoReg10528";
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10528() throws Exception {
		//Jira Link
		if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6445");
		}else {
			commonUtility.addLinkInExtentReport("https://servicemax.atlassian.net/browse/GO-6799");

		}
		
	bProcessCheckResult = commonUtility.ProcessCheck(restServices, sProcessName, sScriptName, sTestCaseID);
	
// **********Create Account**********
    String sAccName = commonUtility.generateRandomNumber("Acc");
    String sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccName+"\" }");
    System.out.println("Account Id is "+sAccId);

// **********Create Contact**********
    String sRandomNumber = commonUtility.generateRandomNumber("");
    String sFirstName = "auto_contact";
    String sLastName = sRandomNumber;
    String sContactName = sFirstName+" "+sLastName;
    System.out.println(sContactName);
    String sConId = restServices.restCreate("Contact?","{\"FirstName\": \""+sFirstName+"\", \"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccId+"\"}");
    System.out.println("Contact Id is "+sConId);		
			
// **********Create IB**********
	String sIbName = commonUtility.generateRandomNumber("IB");
	String sIbNum = commonUtility.generateRandomNumber("IBNum");
	String sIbId = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \""+sAccId+"\", \"Name\": \""+sIbName+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sIbNum+"\",\"SVMXC__Country__c\": \"India\"}");
	System.out.println("IB Id is "+sIbId);
			
// **********Create Location**********
	String sLocName = commonUtility.generateRandomNumber("Loc");
	String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName+"\", \"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}");
	System.out.println("Loc Id is "+sLocId);
			
// **********Create Product**********
	String sProdName = commonUtility.generateRandomNumber("prod");
	String sProdId = restServices.restCreate("Product2?","{\"Name\": \""+sProdName+"\"}");
	System.out.println("Product Id is "+sProdId);
			
// **********Create IB with Top Level**********
	String sIbName1 = commonUtility.generateRandomNumber("IB");
	System.out.println("sIbName1 "+sIbName1);
	String sIbNum1 = commonUtility.generateRandomNumber("IBNum");
	String sIbId1 = restServices.restCreate("SVMXC__Installed_Product__c?","{\"SVMXC__Company__c\": \""+sAccId+"\", \"Name\": \""+sIbName1+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sIbNum1+"\",\"SVMXC__Country__c\": \""+sCountry+"\",\"SVMXC__City__c\": \""+sCity+"\",\"SVMXC__Zip__c\":\""+sZipCode+"\",\"SVMXC__Contact__c\":\""+sConId+"\",\"SVMXC__Product__c\":\""+sProdId+"\",\"SVMXC__Site__c\":\""+sLocId+"\",\"SVMXC__Top_Level__c\":\""+sIbId+"\"}");
	System.out.println("New IB Id is "+sIbId1);
			
// **********Create Work Order**********
	String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
//	System.out.println(sWORecordID);
	String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
//	System.out.println("WO no ="+sWOName);
			
	ph_LoginHomePo.login(commonUtility, ph_MorePo);
	
	ph_MorePo.syncData(commonUtility);
	ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
	
	ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  "AUTOMATION SEARCH", "Work Orders",sWOName,sProcessName);
	ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_WorkOrderPo.getLblComponent(), sIbName1);
	
	// **********Assert if Form Fill is happening correctly*****************
		Assert.assertEquals(ph_WorkOrderPo.getTxtProduct().getText(), sProdName);
		ExtentManager.logger.log(Status.PASS, "Actual Product: "+ph_WorkOrderPo.getTxtProduct().getText()+" Expected Product: "+sProdName);
		Assert.assertEquals(ph_WorkOrderPo.getTxtZip().getText(), sZipCode);
		ExtentManager.logger.log(Status.PASS, "Actual ZipCode: "+ph_WorkOrderPo.getTxtZip().getText()+" Expected ZipCode: "+sZipCode);
		Assert.assertEquals(ph_WorkOrderPo.getTxtTopLevel().getText(), sIbName);
		ExtentManager.logger.log(Status.PASS, "Actual Top Level: "+ph_WorkOrderPo.getTxtTopLevel().getText()+" Expected Top Level: "+sIbName);
		Assert.assertEquals(ph_WorkOrderPo.getTxtSite().getText(), sLocName);
		ExtentManager.logger.log(Status.PASS, "Actual Location: "+ph_WorkOrderPo.getTxtSite().getText()+" Expected Location: "+sLocName);
	Dimension dim = driver.manage().window().getSize();
	int height = dim.getHeight();
	int width = dim.getWidth();
	int x = width / 2;
	int y = height / 2;
	TouchAction ts = new TouchAction(driver);
	ts.press(new PointOption().withCoordinates(x, y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(new PointOption().withCoordinates(x, height-10)).release().perform();
	Thread.sleep(2000); 
		Assert.assertEquals(ph_WorkOrderPo.getTxtContact().getText(), sContactName);
		ExtentManager.logger.log(Status.PASS, "Actual Contact: "+ph_WorkOrderPo.getTxtContact().getText()+" Expected Contact: "+sContactName);
		Assert.assertEquals(ph_WorkOrderPo.getTxtCountry().getText(), sCountry);
		ExtentManager.logger.log(Status.PASS, "Actual Country: "+ph_WorkOrderPo.getTxtCountry().getText()+" Expected Country: "+sCountry);
		Assert.assertEquals(ph_WorkOrderPo.getTxtCity().getText(), sCity);
		ExtentManager.logger.log(Status.PASS, "Actual City: "+ph_WorkOrderPo.getTxtCity().getText()+" Expected City: "+sCity);
	// **********************************************************************
	Thread.sleep(10000);
	ph_WorkOrderPo.getBtnSave().click();
	ph_MorePo.getEleMoreBtn().click();
	ph_MorePo.syncData(commonUtility);
	// **********Assert if Database is updated correctly after Save**********
	String sDBCity = restServices.restGetSoqlValue("SELECT+SVMXC__City__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__City__c");
	Assert.assertEquals(sDBCity, sCity);
	ExtentManager.logger.log(Status.PASS, "Actual City from DB: "+sDBCity+" Expected City: "+sCity);
	String sDBZip = restServices.restGetSoqlValue("SELECT+SVMXC__Zip__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Zip__c");
	Assert.assertEquals(sDBZip, sZipCode);
	ExtentManager.logger.log(Status.PASS, "Actual ZipCode from DB: "+sDBZip+" Expected ZipCode: "+sZipCode);
	String sDBCountry = restServices.restGetSoqlValue("SELECT+SVMXC__Country__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Country__c");
	Assert.assertEquals(sDBCountry, sCountry);
	ExtentManager.logger.log(Status.PASS, "Actual Country from DB: "+sDBCountry+" Expected Country: "+sCountry);
	String sDBContactId = restServices.restGetSoqlValue("SELECT+SVMXC__Contact__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Contact__c");
	String sDBContact = restServices.restGetSoqlValue("SELECT+name+from+Contact+Where+id+=\'"+sDBContactId+"\'", "Name");
	Assert.assertEquals(sDBContact, sContactName);
	ExtentManager.logger.log(Status.PASS, "Actual Contact from DB: "+sDBContact+" Expected Contact: "+sContactName);
	String sDBProductId = restServices.restGetSoqlValue("SELECT+SVMXC__Product__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Product__c");
	String sDBProduct = restServices.restGetSoqlValue("SELECT+name+from+Product2+Where+id+=\'"+sDBProductId+"\'", "Name");
	Assert.assertEquals(sDBProduct, sProdName);
	ExtentManager.logger.log(Status.PASS, "Actual Product from DB: "+sDBProduct+" Expected Product: "+sProdName);
	// **********************************************************************
	
	
} 

}
