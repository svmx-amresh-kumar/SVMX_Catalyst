package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.json.JSONArray;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

public class Ph_Mapping_RS_10556 extends BaseLib {

	int iWhileCnt = 0;

	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sFieldServiceName = null;
	String SFMBillingtype = "Empowerment";
	String SFMcontact = "C10556_ Auto";
	//String SFMCustomerDown = "1";
	String SFMEmail = "testsample@gmail.com";
	String SFMNumber = "11.11";
	String SFMPhone = "9902819683";
	String SFMURL = "www.motogp.com";
	String SFMCurrency = "46.91";
	String SFMclosedby = "Tech";
	String SFMSite = "L10556_Auto";
	String SFMScheduledDate = null;
	String SFMScheduledDatetime = null;
	String SFMProduct = "P10556_Auto";
	String SFMLineQty = "17";
	String SFMLinePerUnit = "46";
	String SFMWorkdiscription = "Verifying Value Map for New Child Record (text)";
	String SFMRecordType = "Usage/Consumption";
	String SFMlinetype = "Parts";
	String SFMdaterequired = null;
	String SFMIsBillable = "true";
	WebElement productname = null;
	String sSheetName = null;
	Boolean bProcessCheckResult = false;

	
	
	//@Test(retryAnalyzer=Retry.class)
	@Test()
	public void RS_10556() throws Exception {
		sSheetName = "RS_10556";
		String sTestCaseID = "RS-10556_mapping";

		
		String SFMCustomerDown=BaseLib.sOSName.equalsIgnoreCase("android")?"ON":"1";
		
		
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"SVMXC__SVMX_Event__c");
		commonUtility.deleteCalendarEvents(restServices,calendarPO,"Event");
		String sProformainVoice = commonUtility.generateRandomNumber("AUTO");
		
		 commonUtility.executeSahiScript("appium/SCN_Mapping_RS_10556.sah");
			
			ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseID +  "Sahi verification is successful");

		// read from file
		sExploreSearch = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ExploreChildSearch");
		sFieldServiceName = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "ProcessName");
		String sworkordernumber = CommonUtility.readExcelData(CommonUtility.sTestDataFile, sSheetName, "WorkOrder Number");

		// Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);

		//config sync
		//ph_MorePo.OptionalConfigSync(commonUtility, ph_CalendarPo, bProcessCheckResult);
	Thread.sleep(CommonUtility.iMedSleep);

		// datasync
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(CommonUtility.iMedSleep);
			//open WO from calendar

		ph_CalendarPo.getEleCalendarBtn().click();
		ph_CalendarPo.custScroll(commonUtility,"Event_10556");
		ph_CalendarPo.getEleworkordernumonCalendar("Event_10556").click();
		ph_CalendarPo.getEleworkordernumon(sworkordernumber).click();
		
		ph_WorkOrderPo.selectAction(commonUtility, sFieldServiceName);
	
		// to get orderstatus nd ordertype from workorder

				JSONArray sJsonArrayWO1 = restServices.restGetSoqlJsonArray("Select+SVMXC__Order_Status__c,+SVMXC__Order_Type__c+from+SVMXC__Service_Order__c+where+SVMXC__Service_Order__c.name=\'"+ sworkordernumber + "\'");
				// String sordertype = restServices.getJsonValue(sJsonArrayWO1,"SVMXC__Order_Type__c");
				String sorderstatus = restServices.getJsonValue(sJsonArrayWO1, "SVMXC__Order_Status__c");
				System.out.println(sorderstatus);
				// Validating before save
				System.out.println(":):):):):):):):):before save header):):):):):):):)before saveheader:):):):):):):):):):):):)before save:):):):):):):)");
			
				String fetchedOrderStatus = ph_WorkOrderPo.geteleOrderStatus().getText();
				System.out.println(fetchedOrderStatus);
				try {
					Assert.assertTrue(fetchedOrderStatus.equals(sorderstatus));
					ExtentManager.logger.log(Status.PASS, "OrderStatus (Picklist) value mapped Successful");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "OrderStatus(Picklist)  mapping Failed ");
				}

				String fetchBillingType = ph_WorkOrderPo.getEleBillingTypeField().getText();
				System.out.println(fetchBillingType);
				try {
					Assert.assertTrue(fetchBillingType.equals(SFMBillingtype));
					ExtentManager.logger.log(Status.PASS, "BillingType(Picklist)  mapping Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "BillingType(Picklist)  mapping Failed ");
				}

				String fetchedContact = ph_WorkOrderPo.getEleContact().getText();
				System.out.println(fetchedContact);
				try {
					Assert.assertTrue(fetchedContact.equals(SFMcontact));
					ExtentManager.logger.log(Status.PASS, "contact(Lookup) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "contact(Lookup) value mapping Failed ");
				}

				
				commonUtility.custScrollToElement(ph_WorkOrderPo.geteleCustomerDowntoggle());
				String fetchCustomerDown = ph_WorkOrderPo.geteleCustomerDowntoggle().getText();
				System.out.println(fetchCustomerDown);//Androin ON
				try {
					Assert.assertEquals(fetchCustomerDown, SFMCustomerDown);
					ExtentManager.logger.log(Status.PASS, "CustomerDown(Checkbox) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.PASS, "CustomerDown(Checkbox) value mapping Failed ");
				}

				
				commonUtility.custScrollToElement(ph_WorkOrderPo.getEleScheduledDate());
				String fetchedScheduledDate = ph_WorkOrderPo.getEleScheduledDate().getText();
				System.out.println(fetchedScheduledDate);

				DateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
				Date date = new Date();
				SFMScheduledDate = dateFormat.format(date);
				try {
					Assert.assertTrue(fetchedScheduledDate.equals(SFMScheduledDate+" "));
					ExtentManager.logger.log(Status.PASS, "ScheduledDate(Date) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "ScheduledDate(Date) value mapping Failed ");
				}

				commonUtility.custScrollToElement(ph_WorkOrderPo.getEleScheduledDateTime());
				String fetchedScheduledDatetime = ph_WorkOrderPo.getEleScheduledDateTime().getText();
				System.out.println(fetchedScheduledDatetime);
				SimpleDateFormat parser1 = new SimpleDateFormat("d/M/yyyy");
				Instant insDate = date.toInstant().plus(1, ChronoUnit.DAYS);
				Date dTempDate1 = Date.from(insDate);
				SFMScheduledDatetime = parser1.format((dTempDate1));
				try {
					Assert.assertTrue(fetchedScheduledDatetime.equals(SFMScheduledDatetime + " 00:00"),
							"ScheduledDatetime(Date/Time) value mapped is not displayed");
					ExtentManager.logger.log(Status.PASS, "ScheduledDate value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "ScheduledDate(Date/Time) value mapping Failed ");
				}
				ExtentManager.logger.log(Status.PASS, "Work Order  Mapping is Successful before save");

				
				
				commonUtility.custScrollToElement(ph_WorkOrderPo.geteleProblemDescriptiontxt());
				String fetchProblemDescription = ph_WorkOrderPo.geteleProblemDescriptiontxt().getText();
				System.out.println(fetchProblemDescription);
				try {
					Assert.assertTrue(fetchProblemDescription.equals(SFMBillingtype));
					ExtentManager.logger.log(Status.PASS, "ProblemDescription(Long Text Area) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "ProblemDescription(Long Text Area) value mapping Failed ");
				} // change it

				
				commonUtility.custScrollToElement(ph_WorkOrderPo.getEmailvalue());
				String fetchEmail = ph_WorkOrderPo.getEmailvalue().getText();
				try {
					Assert.assertTrue(fetchEmail.equals(SFMEmail));
					ExtentManager.logger.log(Status.PASS, "Email(Email) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Email(Email) value mapping Failed ");
				}

				commonUtility.custScrollToElement(ph_WorkOrderPo.getEleURL());
				String fetchURL = ph_WorkOrderPo.getEleURL().getText();
				try {
					Assert.assertTrue(fetchURL.equals(SFMURL));
					ExtentManager.logger.log(Status.PASS, "URL(Url) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "URL(Url) value mapping Failed ");
				}

				commonUtility.custScrollToElement(ph_WorkOrderPo.getEleNumber());
 				String fetchNumber = ph_WorkOrderPo.getEleNumber().getText();
				System.out.println(fetchNumber);
				try {
					Assert.assertTrue(fetchNumber.equals(SFMNumber));
					ExtentManager.logger.log(Status.PASS, "Number(Number) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Number(Number) value mapping Failed ");
				}
				
				commonUtility.custScrollToElement(ph_WorkOrderPo.getElePhone());
				String fetchPhone = ph_WorkOrderPo.getElePhone().getText();
				try {
					Assert.assertTrue(fetchPhone.equals(SFMPhone));
					ExtentManager.logger.log(Status.PASS, "Phone(Phone) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Phone(Phone) value mapping Failed ");
				}

				commonUtility.custScrollToElement(ph_WorkOrderPo.getelecurrency());
				String fetchCurrency = ph_WorkOrderPo.getelecurrency().getText();
				try {
					Assert.assertTrue(fetchCurrency.equals(SFMCurrency));
					ExtentManager.logger.log(Status.PASS, "Currency(Currency) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Currency(Currency) value mapping Failed ");
				}

				commonUtility.custScrollToElement(ph_WorkOrderPo.getEleclosedby());
				String fetchclosedby = ph_WorkOrderPo.getEleclosedby().getText();
				try {
					Assert.assertTrue(fetchclosedby.equals(SFMclosedby));
					ExtentManager.logger.log(Status.PASS, "closedby(Lookup) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "closedby(Lookup) value mapping Failed ");
				}

				commonUtility.custScrollToElement(ph_WorkOrderPo.getEleSite());
				String fetchSite = ph_WorkOrderPo.getEleSite().getText();
				try {
					Assert.assertTrue(fetchSite.equals(SFMSite));
					ExtentManager.logger.log(Status.PASS, "Site(Lookup) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Site(Lookup) value mapping Failed ");
				}

				ph_WorkOrderPo.addParts(commonUtility,  "P10556_Auto");
				Thread.sleep(3000);
				ph_WorkOrderPo.getEletapon(SFMProduct).click();
				Thread.sleep(3000);

				// Verifying mapping before save on child
				System.out.println(	":):):):):):):):):before save child):):):):):):):)before savechild:):):):):):):):):):):):)before save:):):):):):):)");
				
				commonUtility.custScrollToElement(ph_WorkOrderPo.getPart());
				String fetchedpart = ph_WorkOrderPo.getPart().getText();
				System.out.println(fetchedpart);
				try {
					Assert.assertTrue(fetchedpart.equals(SFMProduct));
					ExtentManager.logger.log(Status.PASS, "Part(Lookup(Product)) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Part(Lookup(Product)) value mapping Failed ");
				}
				
				
				String fetchlineqty = ph_WorkOrderPo.getEleLineQtyField().getText();
				System.out.println(fetchlineqty);
				try {
					Assert.assertTrue(fetchlineqty.equals(SFMLineQty));
					ExtentManager.logger.log(Status.PASS, "lineqty(Number) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "lineqty(Number) value mapping Failed ");
				}

				String fetchlinepriceperunit = ph_WorkOrderPo.getEleLinePriceField().getText();
				System.out.println(fetchlinepriceperunit);
				try {
					Assert.assertTrue(fetchlinepriceperunit.equals(SFMCurrency));
					ExtentManager.logger.log(Status.PASS, "LinePerUnit(Currency) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "LinePerUnit(Currency) value mapping Failed ");
				}
		
				commonUtility.custScrollToElement(ph_WorkOrderPo.geteleWorkDescription());
				String fetchworkdescription = ph_WorkOrderPo.geteleWorkDescription().getText();
				System.out.println(fetchworkdescription);
				try {
					Assert.assertTrue(fetchworkdescription.equals(SFMWorkdiscription));
					ExtentManager.logger.log(Status.PASS, "workdescription(Long Text Area) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "workdescription(Long Text Area) value mapping Failed ");
				}
				
				commonUtility.custScrollToElement(ph_WorkOrderPo.geteleIsbillable());
				String fetchisBillable = ph_WorkOrderPo.geteleIsbillable().getText();
				System.out.println(fetchisBillable);
				try {
					Assert.assertEquals(fetchisBillable, SFMCustomerDown);
					ExtentManager.logger.log(Status.PASS, "Billable(Checkbox) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Billable(Checkbox) value mapping Failed ");
				}
			
				commonUtility.custScrollToElement(ph_WorkOrderPo.getRecordtypeid());
				String fetchRecordType = ph_WorkOrderPo.getRecordtypeid().getText();
				System.out.println(fetchRecordType);
				try {
					Assert.assertTrue(fetchRecordType.equals(SFMRecordType));
					ExtentManager.logger.log(Status.PASS, "RecordType(Record Type) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "RecordType(Record Type) value mapping Failed ");
				}
				
				commonUtility.custScrollToElement(ph_WorkOrderPo.geteleLineType());
				String fetchlinetype = ph_WorkOrderPo.geteleLineType().getText();
				System.out.println(fetchlinetype);
				try {
					Assert.assertTrue(fetchlinetype.equals(SFMlinetype));
					ExtentManager.logger.log(Status.PASS, "linetype(Picklist) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "linetype(Picklist) value mapping Failed ");
				}

				commonUtility.custScrollToElement(ph_WorkOrderPo.getleDateRequired());
				String fetchdaterequired = ph_WorkOrderPo.getleDateRequired().getText();
				System.out.println(fetchdaterequired);
				try {
					Assert.assertTrue(fetchdaterequired.equals(SFMScheduledDatetime+" "));
					ExtentManager.logger.log(Status.PASS, "DateRequired(Date) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "DateRequired(Date) value mapping Failed ");
				}

				commonUtility.custScrollToElement(ph_WorkOrderPo.getEleStartDateTimeTxtFld());
				String fetchedstartdateandtime = ph_WorkOrderPo.getEleStartDateTimeTxtFld().getText();
				System.out.println(fetchedstartdateandtime);
				try {
					Assert.assertTrue(fetchedstartdateandtime.equals(SFMScheduledDate + " 00:00"));
					ExtentManager.logger.log(Status.PASS, "startdateandtime(Date/Time) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "startdateandtime(Date/Time) value mapping Failed ");
				}
				
				commonUtility.custScrollToElement(ph_WorkOrderPo.getEleToLocation());
				String fetchedlocation = ph_WorkOrderPo.getEleToLocation().getText();
				System.out.println(fetchedlocation);
				try {
					Assert.assertTrue(fetchedlocation.equals(SFMSite));
					ExtentManager.logger.log(Status.PASS, "location(Lookup(Location)) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "location(Lookup(Location)) value mapping Failed ");
				}

				commonUtility.custScrollToElement(ph_WorkOrderPo.getEleclosedby());
				String fetchclosedbyinpart = ph_WorkOrderPo.getEleclosedby().getText();
				System.out.println(fetchclosedbyinpart);
				try {
					Assert.assertTrue(fetchclosedbyinpart.equals(SFMclosedby));
					ExtentManager.logger.log(Status.PASS, "closedby(Lookup(User)) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "closedby(Lookup(User)) value mapping Failed ");
				}

				commonUtility.custScrollToElement(ph_WorkOrderPo.getelecanceledby());
				String fetchcancledby = ph_WorkOrderPo.getelecanceledby().getText();
				System.out.println(fetchcancledby);
				try {
					Assert.assertTrue(fetchcancledby.equals(SFMclosedby));
					ExtentManager.logger.log(Status.PASS, "CanceledBy(Lookup(User)) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "CanceledBy(Lookup(User)) value mapping Failed ");
				}


				ExtentManager.logger.log(Status.PASS, "Work details  Mapping is Successful before save");
				Thread.sleep(15000);
				ph_WorkOrderPo.getEleBackButton().click();
				ph_WorkOrderPo.getElesave().click();

				
				ph_MorePo.syncData(commonUtility);
				Thread.sleep(5000);
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
				
		System.out.println(":):):):):):):)Server Header:):):):):):):):):)Server Header:):):):):):):):):):):):):):):):):):):)");
				JSONArray sJsonArrayWO = restServices.restGetSoqlJsonArray("Select+SVMXC__Order_Status__c,+SVMXC__Billing_Type__c,+SVMXC__Contact__c,+SVMXC__Customer_Down__c,+SVMXC__Scheduled_Date_Time__c,+SVMXC__Scheduled_Date__c,SVMXC__Problem_Description__c,Email__c,URL__c,Number__c,Phone__c,Currency__c,SVMXC__Site__c,SVMXC__Closed_By__c+from+SVMXC__Service_Order__c+where+SVMXC__Service_Order__c.name=\'"
								+ sworkordernumber + "\'");
		
				String orderstatus = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Order_Status__c");
				try {
					assertEquals("Open", orderstatus);
					ExtentManager.logger.log(Status.PASS, "orderstatus(Picklist) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "orderstatus(Picklist) value mapping Failed ");
				}
				System.out.println(orderstatus);
		
				String billingtype = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Billing_Type__c");
				try {
					assertEquals(SFMBillingtype, billingtype);
					ExtentManager.logger.log(Status.PASS, "billingtype(Picklist) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "billingtype(Picklist) value mapping Failed ");
				}
		
				String contactid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Contact__c");
				String contactQuery = "SELECT+Name+from+Contact+where+id=\'" + contactid + "\'";
				String soqlcontactName = restServices.restGetSoqlValue(contactQuery, "Name");
				try {
					assertEquals(SFMcontact, soqlcontactName);
					ExtentManager.logger.log(Status.PASS, "Contact(Lookup(Contact)) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Contact(Lookup(Contact)) value mapping Failed ");
				}
		
				String customerdown = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Customer_Down__c");
				try {
					assertEquals("true", customerdown);
					ExtentManager.logger.log(Status.PASS, "customerdown(Checkbox) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "customerdown(Checkbox) value mapping Failed ");
				}
		
				String scheddatetime = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Scheduled_Date_Time__c");
				 scheddatetime = ph_CalendarPo.convertedformate(scheddatetime,"yyyy-MM-dd'T'HH:mm:ss","d/M/yyyy HH:mm");

				System.out.println(scheddatetime);
				try {
					assertEquals((SFMScheduledDatetime + " 00:00"), scheddatetime);
					ExtentManager.logger.log(Status.PASS, "scheddatetime(date/time) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "scheddatetime((date/time) value mapping Failed ");
				}
		
				String scheddate = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Scheduled_Date__c");
				scheddate = ph_CalendarPo.convertedformate(scheddate,"yyyy-MM-dd","d/M/yyyy");

				System.out.println(scheddate);
				try {
					assertEquals(SFMScheduledDate, scheddate);
					ExtentManager.logger.log(Status.PASS, "ScheduledDate(Date) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "ScheduledDate(Date) value mapping Failed ");
				}
		
				String ProblemDescription = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Problem_Description__c");
				try {
					assertEquals(SFMBillingtype, ProblemDescription);
					ExtentManager.logger.log(Status.PASS, "ProblemDescription(Long Text Area) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "ProblemDescription(Long Text Area) value mapping Failed ");
				}
		
				String Email = restServices.getJsonValue(sJsonArrayWO, "Email__c");
				try {
					assertEquals(SFMEmail, Email);
					ExtentManager.logger.log(Status.PASS, "Email(Email) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Email(Email) value mapping Failed ");
				}
		
				String URL = restServices.getJsonValue(sJsonArrayWO, "URL__c");
				try {
					assertEquals(SFMURL, URL);
					ExtentManager.logger.log(Status.PASS, "URL(Url) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "URL(Url) value mapping Failed ");
				}
		
				String Number = restServices.getJsonValue(sJsonArrayWO, "Number__c");
				try {
					assertEquals(SFMNumber, Number);
					ExtentManager.logger.log(Status.PASS, "Number(Number) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Number(Number) value mapping Failed ");
				}
		
				String Phone = restServices.getJsonValue(sJsonArrayWO, "Phone__c");
				try {
					assertEquals(SFMPhone, Phone);
					ExtentManager.logger.log(Status.PASS, "Phone(Phone) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Phone(Phone) value mapping Failed ");
				}
		
				String Currency = restServices.getJsonValue(sJsonArrayWO, "Currency__c");
				try {
					assertEquals(SFMCurrency, Currency);
					ExtentManager.logger.log(Status.PASS, "Currency(Currency) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Currency(Currency) value mapping Failed ");
				}
		
				String siteid = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Site__c");
				String siteQuery = "SELECT+Name+from+SVMXC__Site__c+where+id=\'" + siteid + "\'";
				String siteName = restServices.restGetSoqlValue(siteQuery, "Name");
				try {
					assertEquals(SFMSite, siteName);
					ExtentManager.logger.log(Status.PASS, "siteName(Lookup(Location)) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "siteName(Lookup(Location)) value mapping Failed ");
				}
		
				String closedby = restServices.getJsonValue(sJsonArrayWO, "SVMXC__Closed_By__c");
				String userQuery = "SELECT+Name+from+User+where+id=\'" + closedby + "\'";
				String userName = restServices.restGetSoqlValue(userQuery, "Name");
				try {
					assertEquals(SFMclosedby, userName);
					ExtentManager.logger.log(Status.PASS, "Closed_By(Lookup(User)) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Closed_By(Lookup(User)) value mapping Failed ");
				}
				ExtentManager.logger.log(Status.PASS,
						"Mapping validation is successful. Covered fields for Header record - from server");
		
				System.out.println(":):):):):):):)server child:):):):):):):):):)server child:):):):):):):):):):):):):):):):):):):)");
		
				JSONArray sJsonArrayparts = restServices.restGetSoqlJsonArray(
						"SELECT+SVMXC__Product__c,+SVMXC__Actual_Quantity2__c,SVMXC__Actual_Price2__c,SVMXC__Work_Description__c,SVMXC__Is_Billable__c,RecordType.name,SVMXC__Line_Type__c,SVMXC__Date_Requested__c,SVMXC__Start_Date_and_Time__c,SVMXC__Requested_Location__c,SVMXC__Closed_By__c,SVMXC__Canceled_By__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+= \'"
								+ sworkordernumber + "\')");
		
				String spartid = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Product__c");
				String partQuery = "SELECT+Name+from+Product2+where+id=\'" + spartid + "\'";
				String soqlpartName = restServices.restGetSoqlValue(partQuery, "Name");
				try {
					assertEquals(SFMProduct, soqlpartName);
					ExtentManager.logger.log(Status.PASS, "Product(Lookup(Product)) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Product(Lookup(Product)) value mapping Failed ");
				}
		
				String lineqty = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Actual_Quantity2__c");
				try {
					assertEquals("17.0", lineqty);
					ExtentManager.logger.log(Status.PASS, "lineqty(Number) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "lineqty(Number) value mapping Failed ");
				}
		
				String linepriceperunit = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Actual_Price2__c");
				try {
					assertEquals("46.0", linepriceperunit);
					ExtentManager.logger.log(Status.PASS, "linepriceperunit(Currency) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "linepriceperunit(Currency) value mapping Failed ");
				}
		
				String Work_Description = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Work_Description__c");
				try {
					assertEquals(SFMWorkdiscription, Work_Description);
					ExtentManager.logger.log(Status.PASS, "Work_Description(Long Text Area() value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Work_Description(Long Text Area) value mapping Failed ");
				}
		
				String Is_Billable = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Is_Billable__c");
				try {
					assertEquals(SFMIsBillable, Is_Billable);
					ExtentManager.logger.log(Status.PASS, "Is_Billable(Checkbox) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Is_Billable(Checkbox) value mapping Failed ");
				}
		
				String RecordType = restServices.getJsonValue(sJsonArrayparts, "RecordType");
				// try{assertEquals(SFMRecordType,
				// RecordType);ExtentManager.logger.log(Status.PASS,"RecordType(RecordType)
				// value mapped Successful ");}catch(AssertionError e) {System.out.println(e);
				// ExtentManager.logger.log(Status.FAIL,"RecordType(RecordType) value mapping
				// Failed ");}
		
				String Line_Type = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Line_Type__c");
				try {
					assertEquals(SFMlinetype, Line_Type);
					ExtentManager.logger.log(Status.PASS, "Line_Type(Picklist) value mapped Successful ");
				} catch (AssertionError e) {
					System.out.println(e);
					ExtentManager.logger.log(Status.FAIL, "Line_Type(Picklist) value mapping Failed ");
			}

			String Date_Requested = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Date_Requested__c");
			String SFMDate_Requested = ph_CalendarPo.convertedformate(Date_Requested,"yyyy-MM-dd","d/M/yyyy");
			 
			try {
				assertEquals(SFMScheduledDatetime, SFMDate_Requested);
				ExtentManager.logger.log(Status.PASS, "Date_Requested(Date) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "Date_Requested(Date) value mapping Failed ");
			}

			String Start_Date_and_Time = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Start_Date_and_Time__c");
			String SFMStartsatetime  = ph_CalendarPo.convertedformate(Start_Date_and_Time,"yyyy-MM-dd'T'HH:mm:ss","d/M/yyyy HH:mm");
			try {
				assertEquals((SFMScheduledDate + " 00:00"), SFMStartsatetime);
				ExtentManager.logger.log(Status.PASS, "StartDateandTime(Date/Time) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "StartDateandTime(Date/Time) value mapping Failed ");
			} // today

			String Requested_Location = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Requested_Location__c");
			String Requested_LocationQuery = "SELECT+Name+from+SVMXC__Site__c+where+id=\'" + Requested_Location + "\'";
			String Requested_LocationName = restServices.restGetSoqlValue(Requested_LocationQuery, "Name");
			try {
				assertEquals(SFMSite, Requested_LocationName);
				ExtentManager.logger.log(Status.PASS, "Requested_Location(Lookup(Location)) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "Requested_LocationName(Lookup(Location)) value mapping Failed ");
			}

			closedby = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Closed_By__c");
			userQuery = "SELECT+Name+from+User+where+id=\'" + closedby + "\'";
			userName = restServices.restGetSoqlValue(userQuery, "Name");
			try {
				assertEquals(SFMclosedby, userName);
				ExtentManager.logger.log(Status.PASS, "closedby(lookup(User)) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "closedby((lookup(User))) value mapping Failed ");
			}

			String Canceled_By = restServices.getJsonValue(sJsonArrayparts, "SVMXC__Canceled_By__c");
			userQuery = "SELECT+Name+from+User+where+id=\'" + Canceled_By + "\'";
			userName = restServices.restGetSoqlValue(userQuery, "Name");
			try {
				assertEquals(SFMclosedby, userName);
				ExtentManager.logger.log(Status.PASS, "Canceled_By((lookup(User))) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "Canceled_By((lookup(User))) value mapping Failed ");
			}
			ExtentManager.logger.log(Status.PASS, "Mapping validation is successful. Server-Child ");

			System.out.println(":))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))");

			//validaing after data sync
			
			
			ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  sExploreSearch, "Work Orders", sworkordernumber,"EDIT_WORKORDER_MAPPING" );	
			Thread.sleep(CommonUtility.iMedSleep);
			
			 fetchedOrderStatus = ph_WorkOrderPo.geteleOrderStatus().getText();
			System.out.println(fetchedOrderStatus);
			try {
				Assert.assertTrue(fetchedOrderStatus.equals("open"));//change it
				ExtentManager.logger.log(Status.PASS, "OrderStatus (Picklist) value mapped Successful");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "OrderStatus(Picklist)  mapping Failed ");
			}

			 fetchBillingType = ph_WorkOrderPo.getEleBillingTypeField().getText();
			System.out.println(fetchBillingType);
			try {
				Assert.assertTrue(fetchBillingType.equals(SFMBillingtype));
				ExtentManager.logger.log(Status.PASS, "BillingType(Picklist)  mapping Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "BillingType(Picklist)  mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleContact());
			 fetchedContact = ph_WorkOrderPo.getEleContact().getText();
			System.out.println(fetchedContact);
			try {
				Assert.assertTrue(fetchedContact.equals(SFMcontact));
				ExtentManager.logger.log(Status.PASS, "contact(Lookup) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "contact(Lookup) value mapping Failed ");
			}

			
			commonUtility.custScrollToElement(ph_WorkOrderPo.geteleCustomerDowntoggle());
			 fetchCustomerDown = ph_WorkOrderPo.geteleCustomerDowntoggle().getText();
			System.out.println(fetchCustomerDown);
			try {
				Assert.assertEquals(fetchCustomerDown, SFMCustomerDown);
				ExtentManager.logger.log(Status.PASS, "CustomerDown(Checkbox) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.PASS, "CustomerDown(Checkbox) value mapping Failed ");
			}

			
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleScheduledDate());
			 fetchedScheduledDate = ph_WorkOrderPo.getEleScheduledDate().getText();
			System.out.println(fetchedScheduledDate);

			 dateFormat = new SimpleDateFormat("d/M/yyyy");
			 date = new Date();
			SFMScheduledDate = dateFormat.format(date);
			try {
				Assert.assertTrue(fetchedScheduledDate.equals(SFMScheduledDate+" "));
				ExtentManager.logger.log(Status.PASS, "ScheduledDate(Date) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "ScheduledDate(Date) value mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleScheduledDateTime());
			 fetchedScheduledDatetime = ph_WorkOrderPo.getEleScheduledDateTime().getText();
			System.out.println(fetchedScheduledDatetime);
			 parser1 = new SimpleDateFormat("d/M/yyyy");
			 insDate = date.toInstant().plus(1, ChronoUnit.DAYS);
			 dTempDate1 = Date.from(insDate);
			SFMScheduledDatetime = parser1.format((dTempDate1));
			try {
				Assert.assertTrue(fetchedScheduledDatetime.equals(SFMScheduledDatetime + " 00:00"),
						"ScheduledDatetime(Date/Time) value mapped is not displayed");
				ExtentManager.logger.log(Status.PASS, "ScheduledDate value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "ScheduledDate(Date/Time) value mapping Failed ");
			}
			ExtentManager.logger.log(Status.PASS, "Work Order  Mapping is Successful before save");

			
			
			commonUtility.custScrollToElement(ph_WorkOrderPo.geteleProblemDescriptiontxt());
			 fetchProblemDescription = ph_WorkOrderPo.geteleProblemDescriptiontxt().getText();
			System.out.println(fetchProblemDescription);
			try {
				Assert.assertTrue(fetchProblemDescription.equals(SFMBillingtype));
				ExtentManager.logger.log(Status.PASS, "ProblemDescription(Long Text Area) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "ProblemDescription(Long Text Area) value mapping Failed ");
			} // change it

			
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEmailvalue());
			 fetchEmail = ph_WorkOrderPo.getEmailvalue().getText();
			try {
				Assert.assertTrue(fetchEmail.equals(SFMEmail));
				ExtentManager.logger.log(Status.PASS, "Email(Email) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "Email(Email) value mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleURL());
			 fetchURL = ph_WorkOrderPo.getEleURL().getText();
			try {
				Assert.assertTrue(fetchURL.equals(SFMURL));
				ExtentManager.logger.log(Status.PASS, "URL(Url) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "URL(Url) value mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleNumber());
				 fetchNumber = ph_WorkOrderPo.getEleNumber().getText();
			System.out.println(fetchNumber);
			try {
				Assert.assertTrue(fetchNumber.equals(SFMNumber));
				ExtentManager.logger.log(Status.PASS, "Number(Number) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "Number(Number) value mapping Failed ");
			}
			
			commonUtility.custScrollToElement(ph_WorkOrderPo.getElePhone());
			 fetchPhone = ph_WorkOrderPo.getElePhone().getText();
			try {
				Assert.assertTrue(fetchPhone.equals(SFMPhone));
				ExtentManager.logger.log(Status.PASS, "Phone(Phone) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "Phone(Phone) value mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getelecurrency());
			 fetchCurrency = ph_WorkOrderPo.getelecurrency().getText();
			try {
				Assert.assertTrue(fetchCurrency.equals(SFMCurrency));
				ExtentManager.logger.log(Status.PASS, "Currency(Currency) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "Currency(Currency) value mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleclosedby());
			 fetchclosedby = ph_WorkOrderPo.getEleclosedby().getText();
			try {
				Assert.assertTrue(fetchclosedby.equals(SFMclosedby));
				ExtentManager.logger.log(Status.PASS, "closedby(Lookup) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "closedby(Lookup) value mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleSite());
			 fetchSite = ph_WorkOrderPo.getEleSite().getText();
			try {
				Assert.assertTrue(fetchSite.equals(SFMSite));
				ExtentManager.logger.log(Status.PASS, "Site(Lookup) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "Site(Lookup) value mapping Failed ");
			}

			commonUtility.gotToTabHorizontal("PARTS");
			Thread.sleep(3000);
			ph_WorkOrderPo.getEletapon(SFMProduct).click();
			Thread.sleep(3000);

			
			commonUtility.custScrollToElement(ph_WorkOrderPo.getPart());
			 fetchedpart = ph_WorkOrderPo.getPart().getText();
			System.out.println(fetchedpart);
			try {
				Assert.assertTrue(fetchedpart.equals(SFMProduct));
				ExtentManager.logger.log(Status.PASS, "Part(Lookup(Product)) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "Part(Lookup(Product)) value mapping Failed ");
			}
			
			
			 fetchlineqty = ph_WorkOrderPo.getEleLineQtyField().getText();
			System.out.println(fetchlineqty);
			try {
				Assert.assertTrue(fetchlineqty.equals(SFMLineQty));
				ExtentManager.logger.log(Status.PASS, "lineqty(Number) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "lineqty(Number) value mapping Failed ");
			}

			 fetchlinepriceperunit = ph_WorkOrderPo.getEleLinePriceField().getText();
			System.out.println(fetchlinepriceperunit);
			try {
				Assert.assertTrue(fetchlinepriceperunit.equals(SFMCurrency));
				ExtentManager.logger.log(Status.PASS, "LinePerUnit(Currency) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "LinePerUnit(Currency) value mapping Failed ");
			}
	
			commonUtility.custScrollToElement(ph_WorkOrderPo.geteleWorkDescription());
			 fetchworkdescription = ph_WorkOrderPo.geteleWorkDescription().getText();
			System.out.println(fetchworkdescription);
			try {
				Assert.assertTrue(fetchworkdescription.equals(SFMWorkdiscription));
				ExtentManager.logger.log(Status.PASS, "workdescription(Long Text Area) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "workdescription(Long Text Area) value mapping Failed ");
			}
			
			commonUtility.custScrollToElement(ph_WorkOrderPo.geteleIsbillable());
			 fetchisBillable = ph_WorkOrderPo.geteleIsbillable().getText();
			System.out.println(fetchisBillable);
			try {
				Assert.assertEquals(fetchisBillable, SFMCustomerDown);
				ExtentManager.logger.log(Status.PASS, "Billable(Checkbox) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "Billable(Checkbox) value mapping Failed ");
			}
		
			commonUtility.custScrollToElement(ph_WorkOrderPo.getRecordtypeid());
			 fetchRecordType = ph_WorkOrderPo.getRecordtypeid().getText();
			System.out.println(fetchRecordType);
			try {
				Assert.assertTrue(fetchRecordType.equals(SFMRecordType));
				ExtentManager.logger.log(Status.PASS, "RecordType(Record Type) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "RecordType(Record Type) value mapping Failed ");
			}
			
			commonUtility.custScrollToElement(ph_WorkOrderPo.geteleLineType());
			 fetchlinetype = ph_WorkOrderPo.geteleLineType().getText();
			System.out.println(fetchlinetype);
			try {
				Assert.assertTrue(fetchlinetype.equals(SFMlinetype));
				ExtentManager.logger.log(Status.PASS, "linetype(Picklist) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "linetype(Picklist) value mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getleDateRequired());
			 fetchdaterequired = ph_WorkOrderPo.getleDateRequired().getText();
			System.out.println(fetchdaterequired);
			try {
				Assert.assertTrue(fetchdaterequired.equals(SFMScheduledDatetime+" "));
				ExtentManager.logger.log(Status.PASS, "DateRequired(Date) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "DateRequired(Date) value mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleStartDateTimeTxtFld());
			 fetchedstartdateandtime = ph_WorkOrderPo.getEleStartDateTimeTxtFld().getText();
			System.out.println(fetchedstartdateandtime);
			try {
				Assert.assertTrue(fetchedstartdateandtime.equals(SFMScheduledDate + " 00:00"));
				ExtentManager.logger.log(Status.PASS, "startdateandtime(Date/Time) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "startdateandtime(Date/Time) value mapping Failed ");
			}
			
			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleToLocation());
			 fetchedlocation = ph_WorkOrderPo.getEleToLocation().getText();
			System.out.println(fetchedlocation);
			try {
				Assert.assertTrue(fetchedlocation.equals(SFMSite));
				ExtentManager.logger.log(Status.PASS, "location(Lookup(Location)) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "location(Lookup(Location)) value mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getEleclosedby());
			 fetchclosedbyinpart = ph_WorkOrderPo.getEleclosedby().getText();
			System.out.println(fetchclosedbyinpart);
			try {
				Assert.assertTrue(fetchclosedbyinpart.equals(SFMclosedby));
				ExtentManager.logger.log(Status.PASS, "closedby(Lookup(User)) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "closedby(Lookup(User)) value mapping Failed ");
			}

			commonUtility.custScrollToElement(ph_WorkOrderPo.getelecanceledby());
			 fetchcancledby = ph_WorkOrderPo.getelecanceledby().getText();
			System.out.println(fetchcancledby);
			try {
				Assert.assertTrue(fetchcancledby.equals(SFMclosedby));
				ExtentManager.logger.log(Status.PASS, "CanceledBy(Lookup(User)) value mapped Successful ");
			} catch (AssertionError e) {
				System.out.println(e);
				ExtentManager.logger.log(Status.FAIL, "CanceledBy(Lookup(User)) value mapping Failed ");
			}


			
			
	
	}
/*
	
	  @AfterClass(enabled = true) public void deletedata() throws Exception {
	  //Deleting data created
	  restServices.restDeleterecord("Account",sObjectAccID);
	  restServices.restDeleterecord("Product2",sObjectProID);
	  restServices.restDeleterecord("SVMXC__Site__c",sObjectlocationID); }
	*/ 
}
