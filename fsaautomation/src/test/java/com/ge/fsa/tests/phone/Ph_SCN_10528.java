package com.ge.fsa.tests.phone;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_SCN_10528 extends BaseLib {
	
	String sTestCaseID = "RS_10528";
	String sScriptName = "Scenario_10528";
	String sZipCode = "51203";
	String sCountry = "India";
	String sCity = "Bangalore";
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10528() throws Exception {
	
//	commonUtility.execSahi(genericLib, sScriptName, sTestCaseID);
	
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
//	ph_MorePo.configSync(commonUtility, ph_CalendarPo);
	
	ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  "AUTOMATION SEARCH", "Work Orders",sWOName,"AutoReg10528");
	ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_WorkOrderPo.getLblComponent(), sIbName1);
	
	// **********Assert if Form Fill is happening correctly*****************
	if(sOSName.equalsIgnoreCase("android")) {
		Assert.assertEquals(ph_WorkOrderPo.getTxtProduct().getText(), sProdName);
		Assert.assertEquals(ph_WorkOrderPo.getTxtZip().getText(), sZipCode);
		Assert.assertEquals(ph_WorkOrderPo.getTxtTopLevel().getText(), sIbName);
		Assert.assertEquals(ph_WorkOrderPo.getTxtSite().getText(), sLocName);
	}
	else {
//		System.out.println(ph_WorkOrderPo.getTxtProduct().getAttribute("name"));
//		System.out.println(ph_WorkOrderPo.getTxtZip().getAttribute("value"));
//		System.out.println(ph_WorkOrderPo.getTxtTopLevel().getAttribute("name"));
//		System.out.println(ph_WorkOrderPo.getTxtSite().getAttribute("name"));
//		System.out.println(ph_WorkOrderPo.getTxtContact().getAttribute("name"));
//		System.out.println(ph_WorkOrderPo.getTxtCountry().getAttribute("label"));
//		System.out.println(ph_WorkOrderPo.getTxtCity().getAttribute("value"));
		Assert.assertEquals(ph_WorkOrderPo.getTxtProduct().getAttribute("name"), sProdName);
		Assert.assertEquals(ph_WorkOrderPo.getTxtZip().getAttribute("name"), sZipCode);
		Assert.assertEquals(ph_WorkOrderPo.getTxtTopLevel().getAttribute("name"), sIbName);
		Assert.assertEquals(ph_WorkOrderPo.getTxtSite().getAttribute("name"), sLocName);
	}
	Dimension dim = driver.manage().window().getSize();
	int height = dim.getHeight();
	int width = dim.getWidth();
	int x = width / 2;
	int y = height / 2;
	TouchAction ts = new TouchAction(driver);
	ts.press(new PointOption().withCoordinates(x, y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(new PointOption().withCoordinates(x, height-10)).release().perform();
	Thread.sleep(2000);
	if(sOSName.equalsIgnoreCase("android")) {
		Assert.assertEquals(ph_WorkOrderPo.getTxtContact().getText(), sContactName);
		Assert.assertEquals(ph_WorkOrderPo.getTxtCountry().getText(), sCountry);
		Assert.assertEquals(ph_WorkOrderPo.getTxtCity().getText(), sCity);
	}
	else {
		Assert.assertEquals(ph_WorkOrderPo.getTxtContact().getAttribute("value"), sContactName);
		Assert.assertEquals(ph_WorkOrderPo.getTxtCountry().getAttribute("value"), sCountry);
		Assert.assertEquals(ph_WorkOrderPo.getTxtCity().getAttribute("value"), sCity);
	}
	// **********************************************************************
	Thread.sleep(10000);
	ph_WorkOrderPo.getBtnSave().click();
	ph_MorePo.getEleMoreBtn().click();
	ph_MorePo.configSync(commonUtility, ph_CalendarPo);
	ph_MorePo.syncData(commonUtility);
	// **********Assert if Database is updated correctly after Save**********
	String sDBCity = restServices.restGetSoqlValue("SELECT+SVMXC__City__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__City__c");
	System.out.println(sDBCity);
	Assert.assertEquals(sDBCity, sCity);
	String sDBZip = restServices.restGetSoqlValue("SELECT+SVMXC__Zip__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Zip__c");
	System.out.println(sDBZip);
	Assert.assertEquals(sDBZip, sZipCode);
	String sDBCountry = restServices.restGetSoqlValue("SELECT+SVMXC__Country__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Country__c");
	System.out.println(sDBCountry);
	Assert.assertEquals(sDBCountry, sCountry);
	String sDBContactId = restServices.restGetSoqlValue("SELECT+SVMXC__Contact__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Contact__c");
	System.out.println(sDBContactId);
	String sDBContact = restServices.restGetSoqlValue("SELECT+name+from+Contact+Where+id+=\'"+sDBContactId+"\'", "Name");
	Assert.assertEquals(sDBContact, sContactName);
	String sDBProductId = restServices.restGetSoqlValue("SELECT+SVMXC__Product__c+from+SVMXC__Service_Order__c+Where+name+=\'"+sWOName+"\'", "SVMXC__Product__c");
	System.out.println(sDBProductId);
	String sDBProduct = restServices.restGetSoqlValue("SELECT+name+from+Product2+Where+id+=\'"+sDBProductId+"\'", "Name");
	Assert.assertEquals(sDBProduct, sProdName);
	// **********************************************************************
	
	
} 

}
