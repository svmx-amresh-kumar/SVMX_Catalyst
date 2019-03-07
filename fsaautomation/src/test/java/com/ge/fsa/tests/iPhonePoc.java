/*
*@author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.KeysRelatedAction;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.ge.fsa.iphone.pageobjects.Ip_LoginHomePO;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.Point;

public class iPhonePoc extends BaseLib
{
	
	
	int iWhileCnt =0;
	String sTestCaseID="Scenario-1"; String sCaseWOID=null; String sCaseSahiFile=null;
	String sExploreSearch=null;String sWorkOrderID=null; String sWOJsonData=null;String sWOName=null; String sFieldServiceName=null; String sProductName1=null;String sProductName2=null; 
	String sActivityType=null;String sPrintReportSearch="Auto_PrintServiceReport";
	String sAccountName = null;
	String sProductName = null;
	String sFirstName = null;
	String sLastName = null;
	String sContactName = null;
	String sExpenseType = "Airfare";
	String sLineQty = "10.0";
	String slinepriceperunit = "1000";

//	@FindBy(xpath="//XCUIElementTypeOther[@type='XCUIElementTypeOther']")	
//	private List<WebElement> picPic;
//	public  List<WebElement> getPicPic()
//	{
//		return picPic;
//	}
	
//	@FindBy(id="com.servicemaxinc.svmxfieldserviceapp:id/menu_host")
//	private WebElement eleMenuIcn;
//	public WebElement getEleMenuIcn() 
//	{
//		return eleMenuIcn;
//	}

	@Test()

public void iphone() throws Exception
{	
		
		ip_LoginHomePo.login(commonsPo, ip_MorePo);
		ip_MorePo.configSync(commonsPo);
//		commonsPo.switchContext("Native");
//		TouchAction touchAction2 = new TouchAction(driver);
//		//Locating & clicking on the element
//		Point point = driver.findElementByAccessibilityId("PhotosGridView").findElement(By.xpath("//*[contains(@label,'Photo')]")).getLocation();
//		int x = point.getX() + 2;
//		int y = point.getY() + 5;
//		commonsPo.switchContext("Webview");
//		touchAction2.tap(new PointOption().withCoordinates(x, y)).perform().release();

//		
//		//Need to remove the autowebview capability, set it to false
//		Thread.sleep(4000);
//		//MobileElement el1 = (MobileElement) driver.findElementByXPath("(//XCUIElementTypeOther[@name=\"SIGN IN WITH SALESFORCE\"])[2]");
//		ip_LoginHomePo.getEleSignInBtn().click();
//		
//		//MobileElement el2 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Login | Salesforce\"]/XCUIElementTypeTextField");
//		ip_LoginHomePo.getEleUserNameTxtFld().click();
//		ip_LoginHomePo.getEleUserNameTxtFld().sendKeys("rkong@t.com");
//		Thread.sleep(4000);
//
//		//MobileElement el4 = (MobileElement) driver.findElementByXPath("//XCUIElementTypeOther[@name=\"Login | Salesforce\"]/XCUIElementTypeSecureTextField");
//		ip_LoginHomePo.getElePasswordTxtFld().click();
//		ip_LoginHomePo.getElePasswordTxtFld().sendKeys("servicemax1");
//		MobileElement el5 = (MobileElement) driver.findElementByAccessibilityId("Log In");
//		el5.click();
//		commonsPo.waitforElement((MobileElement) driver.findElementByAccessibilityId("Allow"), 20);
//
//		MobileElement el61 = (MobileElement) driver.findElementByAccessibilityId(" Allow ");
//		el61.click();
//		Thread.sleep(2000);
//
//	
//		commonsPo.waitforElement((MobileElement) driver.findElementByAccessibilityId("More"), 360);
//		MobileElement el14 = (MobileElement) driver.findElementByAccessibilityId("More");
//		el14.click();
//		MobileElement el15 = (MobileElement) driver.findElementByAccessibilityId("Sync ");
//		el15.click();
//		MobileElement el16 = (MobileElement) driver.findElementByXPath("(//XCUIElementTypeOther[@name='Run Config Sync Run Config Sync'])[3]");
//		el16.click();
//		MobileElement el17 = (MobileElement) driver.findElementByAccessibilityId("Perform Config Sync");
//		el17.click();
//		commonsPo.waitforElement(el14, 60);
//		//Check if the test "Run Config Sync Run Config Sync" is not visible
//		assertFalse(el16.isDisplayed(), "Sync not done");
//		ExtentManager.logger.log(Status.PASS,"Sync Completed sucessfully");

}

	
public void DateFormatTests() throws Exception {
	String sSheetName ="RS_11859";
	String[] sDeviceDate = driver.getDeviceTime().split(" ");

	String sTestCaseID="RS_11859_Calender_3";

		//Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);

		//config sync
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		System.out.println("First time");
		commonsPo.tap(calendarPO.getEleCalendarClick());
		
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00004603", "");
		
		
		 toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		System.out.println("second time");
		commonsPo.tap(calendarPO.getEleCalendarClick());
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00004603", "");
		
		
		 toolsPo.configSync(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			System.out.println("third time");
			commonsPo.tap(calendarPO.getEleCalendarClick());
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00004603", "");
			
			

			 toolsPo.configSync(commonsPo);
				Thread.sleep(GenericLib.iMedSleep);
				System.out.println("forth time");
				commonsPo.tap(calendarPO.getEleCalendarClick());
				workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00004603", "");
				

    System.out.println("//////////////////////////////////////////////////////////////////////////////////////////////");
}


public void AttachmentTests() throws Exception
{		


	loginHomePo.login(commonsPo, exploreSearchPo);
	//String sworkOrderName = "WO-00001744";
	Thread.sleep(30000);
	//commonsPo.switchContext("Webview");
	System.out.println("ererererererererer");
//driver.findElement(By.xpath("(//span[contains(text(),'Choose from Library')])[3]")).click();;
Thread.sleep(16000);
//try {
//commonsPo.tap(driver.findElement(By.xpath("//*[contains(@label,'Photo, Portrait, September 05, 23:05')]")));
//}
//catch(Exception e) {
//	commonsPo.tap(driver.findElement(By.xpath("//*[contains(@label,'Photo')]")));
//
//}
////XCUIElementTypePickerWheel[@type='XCUIElementTypePickerWheel']
//List<IOSElement> picPic = (List<IOSElement>) driver.findElements(By.xpath("//XCUIElementTypeOther[@type='XCUIElementTypeOther']"));

	//commonsPo.switchContext("Webview");

	//List<IOSElement> picPic1 = (List<IOSElement>) driver.findElements(By.xpath("//XCUIElementTypeApplication[@name=\"ServiceMax\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[2]"));
	//List<IOSElement> picPic1 = (List<IOSElement>) driver.findElements(By.xpath("//*[@type='XCUIElementTypeOther'][contains(@label,'Photo')]"));

	
	
//	
//	Point point =  picPic1.get(1).getLocation();
//	System.out.println("Points are as = "+point.getX()+":"+point.getY());
//	TouchAction touchAction2 = new TouchAction(driver);;	
//	//commonsPo.switchContext("Native");
//	int x = point.getX() + 44;
//	int y = point.getY() + 191;
//	touchAction2.tap(new PointOption().withCoordinates(x, y)).perform().release();
//	Thread.sleep(6000);
//
//	//commonsPo.switchContext("Webview");
//	touchAction2.press(new PointOption().withCoordinates(x,y)).perform().release();
//
//	commonsPo.tap(picPic1.get(1), 53,180);
//	picPic1.get(1).sendKeys("0");
//	
//	
////List<IOSElement> picPic = (List<IOSElement>) driver.findElements(By.xpath("//XCUIElementTypeApplication[@name=\"ServiceMax\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[2]"));
////System.out.println("picpic = "+picPic.size()+"----"+picPic);
////
////commonsPo.tap(picPic.get(0), 53,180);
////picPic.get(0).sendKeys("Photo, Portrait, September 05, 23:05");
////picPic.get(0).sendKeys("22");
////
////picPic.get(1).click();
////
////picPic.get(2).click();
////
////picPic.get(3).click();
////
////
////picPic.get(1).sendKeys("22");
////picPic.get(2).sendKeys("22");
////picPic.get(3).sendKeys("22");


//    Thread.sleep(GenericLib.iMedSleep);
//    loginHomePo.geteleMotoGpLogin().click();
//    Thread.sleep(GenericLib.iMedSleep);



try {
	Thread.sleep(6000);
	System.out.println("Context count " + driver.getContextHandles().size());
//
//	Set contextNames = driver.getContextHandles();
//	driver.context(contextNames.toArray()[1].toString());
//	commonsPo.switchContext("Native");
//	//List<IOSElement> picPic1 = (List<IOSElement>)driver.findElements(By.xpath("//*[contains(.,'Photo')]"));
//	List<IOSElement> picPic1 = (List<IOSElement>) driver.findElements(By.xpath("//XCUIElementTypeApplication[@name=\"ServiceMax\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[2]"));
//	System.out.println("picpic4 = "+picPic1.size()+"----"+picPic1);
//	System.out.println("catch4 00000000000000000000000");
//	
	
//	Set<String> contextNames = driver.getContextHandles();
//	for (String contextName : contextNames) {
//	 if (contextName.contains("NATIVE"))
//	 driver.context(contextName);
//	}
//	commonsPo.switchContext("Native");
//	TouchAction touchAction2 = new TouchAction(driver);
//	//Locating & clicking on the element
//	Point point = driver.findElementByAccessibilityId("PhotosGridView").findElement(By.xpath("//*[contains(@label,'Photo')]")).getLocation();
//	int x = point.getX() + 2;
//	int y = point.getY() + 5;
//	commonsPo.switchContext("Webview");
//	touchAction2.tap(new PointOption().withCoordinates(x, y)).perform().release();
	
	
	commonsPo.switchContext("Native");
	List<WebElement> mel = (List<WebElement>) driver.findElementByAccessibilityId("PhotosGridView").findElements(By.xpath("//*[contains(@label,'Photo')]"));
for(int i =0;i<mel.size();i++) {
	try {
		WebElement elem =  mel.get(i);
		System.out.println("  picpic = "+i+"----"+elem.getText()+" remotewebele = "+((RemoteWebElement) elem).getId());
	//	elem.getLocation();
		//System.out.println("Click  picpic = "+i+"----"+elem.getLocation());
		//elem.click();
		
		//((RemoteWebElement) elem).click();
		
		
		//WebElement element = driver.findElement('...');
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		HashMap<String, Object> params = new HashMap<>();
//		params.put("x", elem.getLocation().getX()+10);
//		params.put("y", elem.getLocation().getY()+10);
//		params.put("element", elem);
//		js.executeScript("mobile: tap", params);
		
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		HashMap<String, String> scrollObject = new HashMap<String, String>();
//		scrollObject.put("direction", "down");
//		scrollObject.put("element", ((RemoteWebElement) elem).getId());
//		js.executeScript("mobile: scroll", scrollObject);
//		
		WebElement pgv = driver.findElement(By.xpath("//*[contains(@label,'Moments')]"));
		Point pointPhotoGrid = pgv.getLocation();
		int pgx = pointPhotoGrid.getX() + 2;
		int pgy = pointPhotoGrid.getY() + 5;
		System.out.println("Moments  pgv = "+i+"----"+pointPhotoGrid);
		//System.out.println("Tapping  pgv crazy move= "+i+"----"+(pgx)+","+pgy);

		

		//WebElement picElem = driver.findElement(By.xpath("//*[contains(@label,'"+elem.getText()+"')]"));
				//WebElement picElem = driver.findElementByName(elem.getText());
		IOSElement picElem = (IOSElement) driver.findElementByName(elem.getText());

				picElem.getSize();
				System.out.println("Getting size picElem ori = "+picElem.getSize());
				System.out.println("Getting size picElem ori Location = "+picElem.getLocation()+"picElem.getClass() = "+picElem.getClass()+"  tag = "+picElem.getTagName()+" rect ="+picElem.getRect());
				try {

				picElem.click();
				System.out.println("Tried Click success");

				}catch(Exception e) {
					System.out.println("In Click Catch ##### "+e);

				}
				//commonsPo.switchContext("Webview");

				
		TouchAction touchAction2 = new TouchAction(driver);
		
		//touchAction2.tap(new PointOption().withCoordinates(pgx, pgy)).perform();
		//touchAction2.tap(new TapOptions().withElement(new ElementOption().element(elem).withCoordinates(pgx, pgy))).perform().release();
		
		Point point = picElem.getLocation();
		int x = point.getX();
		int y = point.getY();	
		System.out.println("Tapping  picElem ori = "+i+"----"+ x +","+ (y));
		
		//touchAction2.tap(new PointOption().withCoordinates(388, y+300)).perform();
		touchAction2.longPress(new PointOption().withCoordinates(x, y)).perform();

		
	
	}catch(Exception e) {
		System.out.println("In catch ##### "+e);

	}


}

//	List<IOSElement> picPic1 = (List<IOSElement>)driver.findElements(By.xpath("//*[contains(.,'Photo')]"));
//	System.out.println("picPic1 = "+picPic1.size()+"----"+picPic1);
//	System.out.println("picPic1 00000000000000000000000");
	
	//picPic1.get(0).sendKeys("Photo, Landscape, 17. August, 06:23");

	
//	for(int i =0;i<picPic1.size();i++) {
//		try {
//			
//		System.out.println("Click  picpic = "+i+"----"+picPic1.get(i));
//		picPic1.get(i).click();
//		}catch(Exception e) {
//			System.out.println("In catch ##### "+e);
//
//		}
//
//
//	}
//	
//	
//
//	
//	for(int i =0;i<picPic1.size();i++) {
//		try {
//		System.out.println("TAP  picpic = "+i+"----"+picPic1.get(i));
//		//commonsPo.tap(picPic1.get(i),53,180);	
//		
//		Point point =  picPic1.get(i).getLocation();
//		System.out.println("Points are as = "+point.getX()+":"+point.getY());
//		System.out.println("remote address after native "+driver.getRemoteAddress());
//
//		commonsPo.switchContext("Webview");
//		System.out.println("remote address after webview "+driver.getRemoteAddress());
//		TouchAction touchAction2 = new TouchAction(driver);;	
//		int x = point.getX() + 44;
//		int y = point.getY() + 191;
//		touchAction2.tap(new PointOption().withCoordinates(x, y)).perform().release();
//		Thread.sleep(6000);
//		
//		}catch(Exception e) {
//			System.out.println("In catch ##### "+e);
//
//		}
//
//
//	}

}catch(Exception e){
	System.out.println("In catch ouuter "+e );

}

commonsPo.switchContext("Native");


//List<IOSElement> picPic = (List<IOSElement>) driver.findElements(By.xpath("//XCUIElementTypeOther[@name='RemoteViewBridge']"));
//List<IOSElement> picPic = (List<IOSElement>) driver.findElements(By.xpath("//XCUIElementTypeApplication[@name='ServiceMax']/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther"));

//List<WebElement> pp = commonsPo.getPicPic();
//System.out.println("picpic = "+picPic.size()+"----"+picPic);
//	System.out.println("00000000000000000000000");
//	Thread.sleep(30000);
//
//
}


	
}