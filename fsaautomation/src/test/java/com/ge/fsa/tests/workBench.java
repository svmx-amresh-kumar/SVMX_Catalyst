/*
*@author MeghanaRao
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/AUT-62"
 */
package com.ge.fsa.tests;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.KeysRelatedAction;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;

import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.browser.Br_LoginHomePO;
import com.ge.fsa.pageobjects.phone.Ph_RecentsItemsPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;

public class workBench extends BaseLib
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

	//@Test(retryAnalyzer=Retry.class)
	@Test
public void workBenchAnd() throws Exception
{	
	
		//commonUtility.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah");
		
		ExtentManager.logger.log(Status.PASS,"Sahi verification is successful");
		
		loginHomePo.login(commonUtility, exploreSearchPo);		
		workOrderPo.getEleLinkedSFM().click();
//		}
		commonUtility.tap(workOrderPo.getEleSFMfromLinkedSFM2("Manage Work Details for Products Serviced"));
	
//		WebElement wElement =driver.findElement(By.xpath("//div[@class='x-component x-img x-sized x-widthed x-heighted x-floating ']"));
//		commonUtility.longPress(wElement,32,32);
//		System.out.println("Date = "+commonUtility.getDeviceDate()+"   device info= "+commonUtility.executeLibiMobileDeviceExecFile("ideviceinfo"));
//		//System.out.println(commonUtility.getDeviceDate());
	//System.out.println(commonUtility.getDeviceDate());

		/*WebElement wElement =driver.findElement(By.xpath("//div[@class='x-component x-img x-sized x-widthed x-heighted x-floating ']"));
		commonUtility.longPress(wElement,32,32);
*/
		
		//commonUtility.longPress(ph_WorkOrderPo.geteleRemoveablePart());

		
	//	commonUtility.setSpecificDate(ph_WorkOrderPo.getEleScheduledDate(), "June", "01", "2019"); 


		//commonUtility.setSpecificDate(ph_WorkOrderPo.getEleScheduledDate(), "0", "0", "0"); 
	//	commonUtility.setSpecificDate(ph_WorkOrderPo.getEleScheduledDate(), "June", "01", "2019"); 
		//commonUtility.setSpecificDate(ph_WorkOrderPo.getEleScheduledDate(), "0", "0", "0"); 

		//commonUtility.setDateTime24hrs(ph_CalendarPo.geteleStartDateTimecal(), 0, "11", "01");
		//commonUtility.setDateTime24hrs(ph_CalendarPo.geteleEndDateTimecal(), 0, "04" ,"59");
		
		
		/*
		
		System.out.println(commonUtility.getDeviceDate());
		String date=commonUtility.getDeviceDate();
		//String month = calendarPO.convertedformate(date,"E MMM d HH:mm:ss z yyyy","M/d/yyyy");
		//System.out.println(month);
		
		
		SimpleDateFormat parser1 = new SimpleDateFormat("E MMM d HH:mm:ss yyyy");
		 Date  dTempDate1 = parser1.parse("Wed Apr 24 09:12:57 2019");
		 SimpleDateFormat formatter1 = new SimpleDateFormat("M/d/yyyy");
	        String stempDate =  formatter1.format(dTempDate1);
	        System.out.println("Converted to date "+stempDate); 
		
	        Calendar cal=Calendar.getInstance();
	        String[] date1 = commonUtility.getDeviceDate().split(" ");
	        cal.set(Calendar.DATE,Integer.parseInt(date1[2].trim()));
			cal.set(Calendar.YEAR, Integer.parseInt(date1[5].trim()));
	         cal.add(Calendar.DATE, 7);
	         System.out.println(cal.get(Calendar.DATE));*/
////		date = date + " " + commonUtility.getYearPicker().getText();
////		Date currentDate = commonUtility.convertStringToDate("E, MMM dd yyyy", date);
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(currentDate);
//		cal.add(Calendar.DAY_OF_MONTH, 7);
//		Date newDate = cal.getTime();
//		System.out.println("Date = ");
	//	String selectDate = converDateToString("dd MMMM yyyy",newDate);
		
//	
}


//@Test
//public void porcessRestTest() throws IOException {
//	  String jsonPayload = "{\"entityType\":\"SFM/OPDOC\"}";
//	  String requestType = "POST";
//	  String restService = "listOfProcesses";
//	porcessRestApi(requestType, restService, jsonPayload);
//}
//
//
//public void porcessRestApi(String requestType, String restService,String jsonPayload) throws IOException {
//	restServices.getAccessToken();
//
//	String sURL =  "https://cs97.salesforce.com/services/apexrest/SVMXC/svmx/rest/SFMDesigner2ServiceIntf/"+restService+"/";
//	URL url = new URL(sURL);
//	System.out.println(sURL);
//	HttpsURLConnection httpsUrlCon = (HttpsURLConnection) url.openConnection();
//	httpsUrlCon.setDoOutput(true);
//	httpsUrlCon.setRequestMethod(requestType);
// 	httpsUrlCon.setRequestProperty("Content-Type", "application/json");
//	httpsUrlCon.setRequestProperty("Authorization", "OAuth "+restServices.sAccessToken);
//	httpsUrlCon.setRequestProperty("Username",commonUtility.readExcelData(commonUtility.sConfigPropertiesExcelFile,BaseLib.sSelectConfigPropFile, "ADMIN_USN") );
//	httpsUrlCon.setRequestProperty("Password", commonUtility.readExcelData(commonUtility.sConfigPropertiesExcelFile,BaseLib.sSelectConfigPropFile, "ADMIN_PWD"));
//
//	//Setting Json body
//  
//    System.out.println("httpsUrlCon = "+httpsUrlCon);
//	 	OutputStream os = httpsUrlCon.getOutputStream();
//	     os.write(jsonPayload.getBytes());
//	     os.flush();
//	
//	BufferedReader bufferedReader = null;
//	StringBuilder stringBuilder = new StringBuilder();
//	String line;
//	try {
//	   bufferedReader = new BufferedReader(new InputStreamReader(httpsUrlCon.getInputStream(),StandardCharsets.UTF_8));
//	   while ((line =bufferedReader.readLine())!=null){
//	         stringBuilder.append(line);
//	   }
//	} catch (IOException e) {
//	   e.printStackTrace();
//	} finally {
//	   if (bufferedReader != null) {
//	         try {
//	                bufferedReader.close();
//	         } catch (IOException e) {
//	                e.printStackTrace();
//	         }
//	   }
//	
//
//		}
//
//
//	JSONObject json = new JSONObject(stringBuilder.toString());
//	System.out.println("::::::: \n"+json);
//	
//	String sOutPutFile = "/auto/SVMX_Catalyst/Executable/jsonReturned.json";
//	
//	File file = new File(sOutPutFile);
//	file.createNewFile();
//	FileWriter writer = new FileWriter(file);
//	writer.write(""+json);
//	writer.flush();
//	writer.close();
//}

	
}