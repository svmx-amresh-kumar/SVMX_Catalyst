package com.ge.fsa.pageobjects.phone;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;



public class Ph_ChecklistPO
{
	public Ph_ChecklistPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	
	
	//*[@text='Sanity2_Checklist']
	private WebElement eleChecklistName;
	public WebElement getEleChecklistName(String sChecklistName)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return eleChecklistName = driver.findElement(By.xpath("//*[@text='"+sChecklistName+"']"));
		}
		else
		{
			return eleChecklistName = driver.findElement(By.xpath("//*[@name='"+sChecklistName+"']"));

		}
		
		
	}
	
	private WebElement elecheckliststartnew;
	public WebElement getelecheckliststartnew(String sChecklistName)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return elecheckliststartnew = driver.findElement(By.xpath("(//*[@text='"+sChecklistName+"'][@class='android.widget.TextView']/..//*[@text='Start New'])"));

		}
		else
		{
			return eleChecklistName = driver.findElement(By.xpath("//*[@name='"+sChecklistName+"']/../..//*[@name='CHECKLIST.START.NEW']"));

		}
		
		
	}
	
	/*@FindBy(xpath="//*[@text='Start New']")
	private WebElement eleStartNew;
	public WebElement geteeleStartNew()
	{
		return eleStartNew;
	}*/
	
	
	@FindBy(xpath="(//*[@*='APP.BACK_BUTTON'])[last()]")
	private WebElement eleBackbutton;
	public WebElement geteleBackbutton()
	{
		return eleBackbutton;
	}
	
	@AndroidFindBy(xpath="(//*[@text='IN PROGRESS'])[last()]")
	@iOSXCUITFindBy(xpath="(//*[@*[contains(.,'IN PROGRESS')]])[last()]")
	private WebElement eleInProgress;
	public WebElement geteleInProgress()
	{
		return eleInProgress;
	}
	
	@FindBy(xpath="//*[@*='Submit']")
	private WebElement eleSubmitbtn;
	public WebElement geteleSubmitbtn()
	{
		return eleSubmitbtn;
	}
	
	@FindBy(xpath="//*[@*='Completed']")
	private WebElement eleCompleted;
	public WebElement geteleCompleted()
	{
		return eleCompleted;
	}
	
	
	
	@iOSXCUITFindBy(xpath="//*[@*='[Name]'])[last()]")
	@AndroidFindBy(xpath="//*[@text='[Name]']")
	private WebElement eleName;
	public WebElement geteleName()
	{
		return eleName;
	}
	
	
	
	@AndroidFindAll({
	@AndroidBy(xpath="//*[@*='Completed'])"),
	@AndroidBy(xpath="//*[@*='COMPLETED']")})	
	@iOSXCUITFindBy(xpath="(//*[@*[contains(.,'Checklist Section Title COMPLETED')]])[last()]")
	private WebElement eleChecklistCompleted;
	public WebElement geteleChecklistCompleted()
	{
		return eleChecklistCompleted;
	}
	
	@FindBy(xpath="//*[@*[contains(.,'RadioTwo_selected=true')]]")
	private WebElement eleRadioTwoSelected;
	public WebElement geteleRadioTwoSelected()
	{
		return eleRadioTwoSelected;
	}
	
	@FindBy(xpath="//*[@*[contains(.,'RadioOne_selected=true')]]")
	private WebElement eleRadionOneSelected;
	public WebElement geteleRadionOneSelected()
	{
		return eleRadionOneSelected;
	}

	
	
	private WebElement elechecklistinprogress;
	public WebElement getelechecklistinprogress(String sChecklistName)
	{
		return elechecklistinprogress = driver.findElement(By.xpath("//*[@text='"+sChecklistName+"'][@class='android.widget.TextView']/..//*[@text='In Progress']"));
	}
	
	private WebElement elechecklistPickListQAns;
	public WebElement getelechecklistPickListQAns(String sPicklistQuestion, String sPicklistValue)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return elechecklistPickListQAns = driver.findElement(By.xpath("//*[@text='"+sPicklistQuestion+"'][@class='android.widget.TextView']/..//*[@text='"+sPicklistValue+"']"));
		}
		else
		{
			return elechecklistPickListQAns = driver.findElement(By.xpath("//*[@name='"+sPicklistQuestion+"']/../..//*[@name='"+sPicklistValue+"']"));
		}		
	}
	
	private WebElement eleChecklistGenericText;
	public WebElement geteleChecklistGenericText(String sGenericText)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return eleChecklistGenericText = driver.findElement(By.xpath("//*[@text='"+sGenericText+"']"));
		}
		else
		{
			return eleChecklistGenericText = driver.findElement(By.xpath("(//*[@label='"+sGenericText+"'])[last()]"));
		}
		
	}
	
	

	
	private WebElement eleChecklistGenericContainsTxt;
	public WebElement geteleChecklistGenericContainsTxt(String sGenericText)
	{
		return eleChecklistGenericContainsTxt = driver.findElement(By.xpath("//*[@*[contains(.,'"+sGenericText+"')]]"));
	}
	
	

	private WebElement elechecklistRadioButtonQAns;
	public WebElement elechecklistRadioButtonQAns(String sRadioQuestion, String sRadiovalue)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return elechecklistRadioButtonQAns = driver.findElement(By.xpath("//*[@text='"+sRadioQuestion+"'][@class='android.widget.TextView']/..//*[@text='"+sRadiovalue+"']"));
		}
		else
		{																	
			return elechecklistRadioButtonQAns = driver.findElement(By.xpath("//*[@name='"+sRadioQuestion+"']/..//*[@*[contains(.,'"+sRadiovalue+"')]]"));
		}		
	}

	private WebElement elechecklistcheckboxQAns;
	public WebElement getelechecklistcheckboxQAns(String sCheckboxQuestion, String sRadiovalue)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return elechecklistRadioButtonQAns = driver.findElement(By.xpath("//*[@text='"+sCheckboxQuestion+"'][@class='android.widget.TextView']/..//*[@text='"+sRadiovalue+"']"));
		}
		else
		{
			return elechecklistRadioButtonQAns = driver.findElement(By.xpath("//*[@name='"+sCheckboxQuestion+"']/..//*[@*[contains(.,'"+sRadiovalue+"')]]"));
		}		

	}
	
	private WebElement elechecklistMultiPicklistQAns;
	public WebElement getelechecklistMultiPicklistQAns(String sMultiPickQuestion, String sMultivalue)
	{
		return elechecklistMultiPicklistQAns = driver.findElement(By.xpath("//*[@text='"+sMultiPickQuestion+"'][@class='android.widget.TextView']/..//*[@text='"+sMultivalue+"']"));

	}
	
	private WebElement elechecklistTextQAns;
	public WebElement getelechecklistTextQAns(String sTextQuestion)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return elechecklistTextQAns = driver.findElement(By.xpath("//*[@text='"+sTextQuestion+"']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"));
		}
		else
		{																											
			return elechecklistTextQAns = driver.findElement(By.xpath("//*[@name='"+sTextQuestion+"']//XCUIElementTypeTextView"));
		}	
		

	}
	
	private WebElement elechecklistTextQAnsValue;
	public WebElement getelechecklistTextQAnsValue(String sTextQuestion,String sTextAnswer)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return elechecklistTextQAns = driver.findElement(By.xpath("//*[@text='"+sTextQuestion+"']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.EditText'][@text='"+sTextAnswer+"']"));
		}
		else
		{																											
			return elechecklistTextQAns = driver.findElement(By.xpath("//*[@name='"+sTextQuestion+"']//XCUIElementTypeTextView"));
		}	
	}
	
	private WebElement elechecklistDateQAnsValue;
	public WebElement getelechecklistDateQAnsValue(String sTextQuestion)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return elechecklistDateQAnsValue = driver.findElement(By.xpath("//*[@text='"+sTextQuestion+"']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.EditText']"));
		}
		else
		{																											
			return elechecklistDateQAnsValue = driver.findElement(By.xpath("//*[@name='"+sTextQuestion+"']//XCUIElementTypeTextField"));
		}	
	}
	
	
	private WebElement elechecklistNumberQAns;
	public WebElement getelechecklistNumberQAns(String sNumberQuestion)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return elechecklistNumberQAns = driver.findElement(By.xpath("//*[@text='"+sNumberQuestion+"']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"));
		}
		else
		{																											
			return elechecklistNumberQAns = driver.findElement(By.xpath("//*[@name='"+sNumberQuestion+"']/following-sibling::*//*[contains(@type,'XCUIElementTypeText')]"));
		}	
	}
	
	
	private WebElement eleNumberQAnswithMoreInfo;
	public WebElement geteleNumberQAnswithMoreInfo(String sNumberQuestion)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return eleNumberQAnswithMoreInfo = driver.findElement(By.xpath("//*[@text='"+sNumberQuestion+"']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.EditText']"));
		}
		else
		{																											
			return eleNumberQAnswithMoreInfo = driver.findElement(By.xpath("//*[@name='"+sNumberQuestion+"']/following-sibling::*//*[contains(@type,'XCUIElementTypeText')]"));
		}	
	}
	
	private WebElement eleTextQAnswithMoreInfo;
	public WebElement geteleTextQAnswithMoreInfo(String sTextQuestion)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return eleTextQAnswithMoreInfo = driver.findElement(By.xpath("//*[@text='"+sTextQuestion+"']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.EditText']"));
		}
		else
		{																											
			return eleTextQAnswithMoreInfo = driver.findElement(By.xpath("//*[@name='"+sTextQuestion+"']/following-sibling::*//*[contains(@type,'XCUIElementTypeText')]"));
		}	
		

	}
	
	private WebElement elechecklistdatewithMoreinfo;
	public WebElement getelechecklistdatewithMoreinfo(String sdates)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("(//*[@text='"+sdates+"']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.TextView'])[last()]"));
		}
		else
		{																											
			return driver.findElement(By.xpath("(//*[@name='"+sdates+"']/..//XCUIElementTypeStaticText)[last()]"));
		}	
		
		
	}
	
	private WebElement elechecklistinstance;
	public WebElement getelechecklistinstance() 
	{
		return elechecklistinstance = driver.findElementByAccessibilityId("CHECKLIST.INSTANCE.ITEM.0");
	}
	
	
	private WebElement eleDocumentInstance;
	public WebElement geteleDocumentInstance() 
	
	{
		return eleDocumentInstance = driver.findElementByAccessibilityId("Item0");
	}
	
	private WebElement elechecklistdate;
	public WebElement getelechecklistdate(String sdates)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@text='"+sdates+"']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"));
		}
		else
		{																											
			return driver.findElement(By.xpath("(//*[@name='"+sdates+"']/..//XCUIElementTypeStaticText)[last()]"));
		}	
		
		
	}
	
	
	private WebElement eleChecklistAddImage;
	public WebElement geteleChecklistAddImage(String sChecklistQ)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@text='Add Image or Video']"));
		}
		else
		{																											
			return driver.findElement(By.xpath("//*[contains(@label,'"+sChecklistQ+"')]//XCUIElementTypeOther[@name='Add Image or Video']"));
		}	
		
		
	}
	
	/*//*[contains(@label,'Photo')]
	@iOSXCUITFindBy(xpath="//*[contains(@label,'"+sdates+"')]//*[@label='Add Image or Video']")

	//@iOSXCUITFindBy(xpath="//*[@label='Add Image or Video'])")
	@AndroidFindBy(xpath="//*[@text='Add Image or Video']")
	private WebElement eleAddImageorVideo;
	public WebElement geteleAddImageorVideo()
	{
		return eleAddImageorVideo;
	}*/
	
	@iOSXCUITFindBy(xpath="//*[@*='Take Photo'])[last()]")
	@AndroidFindBy(xpath="//*[@text='Take Photo']")
	private WebElement eleTakePhoto;
	public WebElement geteleTakePhoto()
	{
		return eleTakePhoto;
	}
	
	@iOSXCUITFindBy(xpath="//*[@*='Take Video'])[last()]")
	@AndroidFindBy(xpath="//*[@text='Take Video']")
	private WebElement eleTakeVideo;
	public WebElement geteleTakeVideo()
	{
		return eleTakeVideo;
	}
	
	
	@iOSXCUITFindBy(xpath="(//*[@label='Choose from Camera Roll'])[last()]")
	@AndroidFindBy(xpath="//*[@text='Choose from Camera Roll']")
	private WebElement eleChoosefromCameraRoll;
	public WebElement geteleChoosefromCameraRoll()
	{
		return eleChoosefromCameraRoll;
	}
	
	@iOSXCUITFindBy(xpath="(//*[@*='Add'])[last()]")
	@AndroidFindBy(xpath="//*[@text='Add']")
	private WebElement eleAdd;
	public WebElement geteleAdd()
	{
		return eleAdd;
	}
	
	public void AllowCamerabutton(CommonUtility commonUtility) throws Exception {
		commonUtility.switchContext("Native");
		Thread.sleep(CommonUtility.iHighSleep);
		try {
			driver.findElementByAccessibilityId("OK").click();

		} catch (Exception e) {
			System.out.println("Could not Find OK Popup for camera");
		}
	}
	
	public void checklistAttach(CommonUtility commonUtility, String AttachmentAction, String checklistq) throws Exception
	{
		geteleChecklistAddImage(checklistq).click();
		
		
		if (com.ge.fsa.lib.BaseLib.sOSName.contains("android")) {

			if (AttachmentAction == "Take Photo" || AttachmentAction == "take photo") {
				geteleTakePhoto().click();
				AllowCamerabutton(commonUtility);
				try {
					Thread.sleep(3000);
					List<WebElement> e = driver.findElementsByAccessibilityId("Shutter");
					e.get(0).click();
				} catch (Exception e) {
					List<MobileElement> els1 = (List<MobileElement>) driver.findElementsByAccessibilityId("Shutter");
					els1.get(0).click();
				}
				Thread.sleep(2000);
				driver.findElementByAccessibilityId("Done").click();
				geteleAdd().click();
			}

			if (AttachmentAction == "Take Video" || AttachmentAction == "take video") {
				geteleTakeVideo().click();
				AllowCamerabutton(commonUtility);

			}

			if (AttachmentAction == "Choose from Camera Roll" || AttachmentAction == "choose from camera roll") {
				geteleChoosefromCameraRoll().click();
				// AllowCamerabutton(commonUtility);
				Thread.sleep(5000);
				WebElement sd = driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc='MEDIA_LIBRARY.IMAGE_CLICK'])[last()]"));
				sd.click();
				geteleAdd().click();
				Thread.sleep(3000);
			}

		}

		else
		// for IOS
		{
			if (AttachmentAction == "Choose from Camera Roll" || AttachmentAction == "Choose from Camera Roll") {
				geteleChoosefromCameraRoll().click();
				Thread.sleep(3000);
				WebDriverWait wait = new WebDriverWait(driver, 10);
				AllowCamerabutton(commonUtility);
				commonUtility.switchContext("Native");
				WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("All Photos")));
				el.click();
				List<WebElement> photos = driver.findElements(MobileBy.className("XCUIElementTypeCell"));
				int numPhotos = photos.size();
				// commonsUtility.switchContext("Native");
				WebElement elem = null;
				Thread.sleep(5000);
				List<WebElement> lsPhotoGrid = (List<WebElement>) driver.findElementByAccessibilityId("PhotosGridView").findElements(By.xpath("//*[contains(@label,'Photo')]"));
				int count = lsPhotoGrid.size();
				if (count == 0 || count == 1) {
					ExtentManager.logger.log(Status.FAIL, "PLease add photos to Device and reexecute tests");
				} else {
					//-2 because last is giving the count element which is not clickable
					count = lsPhotoGrid.size() - 2;
					elem = lsPhotoGrid.get(count);
					System.out.println(elem.getText());
					driver.findElement(By.xpath("//*[contains(@label,'" + elem.getText() + "')]")).click();
					System.out.println("finished Clicking");
					geteleAdd().click();
					Thread.sleep(10000);
				}
			}
			if (AttachmentAction == "Take Photo" || AttachmentAction == "take photo") {
				Thread.sleep(CommonUtility.iMedSleep);
				AllowCamerabutton(commonUtility);
				// commonUtility.switchContext("Native");
				Thread.sleep(5000);
				driver.findElementByAccessibilityId("Take Picture").click();
				Thread.sleep(3000);
				driver.findElementByAccessibilityId("Use Photo").click();
			}

			if (AttachmentAction == "Take Video" || AttachmentAction == "take video") {
				Thread.sleep(CommonUtility.iMedSleep);
				AllowCamerabutton(commonUtility);
				// com.apple.camera
				commonUtility.switchContext("Native");
				driver.findElementByAccessibilityId("Record Video").click();
				Thread.sleep(5000);
				System.out.println("Recording 5 secs video");
				driver.findElementByAccessibilityId("Stop Recording Video").click();
				driver.findElementByAccessibilityId("Use Video").click();
			}
		}
		
	}
	
	@FindBy(xpath="(//*[@*[contains(.,'Advanced DVR value should be more than 200')]])")
	//@AndroidFindBy(xpath="//*[@text='Advanced DVR value should be more than 200']")
	private WebElement eleChecklistAdvanceDVR;
	public WebElement geteleChecklistAdvanceDVR()
	{
		return eleChecklistAdvanceDVR;
	}
	
	@FindBy(xpath="(//*[@*[contains(.,'Date Should not be Today')]])")
	private WebElement eleChecklistDVRtxt;
	public WebElement geteleChecklistDVRtxt()
	{
		return eleChecklistDVRtxt;
	}
	
	@FindBy(xpath="//*[@*[contains(.,'Number Cannot be 10')]]")
	private WebElement eleChecklistDVRConfirmationtxt;
	public WebElement geteleChecklistDVRConfirmationtxt()
	{
		return eleChecklistDVRConfirmationtxt;
	}
	
	@FindBy(xpath="//*[@*[contains(.,'Number cannot be greater than 100')]]")
	//@FindBy(xpath="//*[@*[contains(.,'Number Cannot be greater than 100')]]")
	private WebElement eleChecklistNumberDVR;
	public WebElement geteleChecklistNumberDVR()
	{
		return eleChecklistNumberDVR;
	}
	
	@FindBy(xpath="(//*[@*[contains(.,'Confirm')]])[last()]")
	private WebElement eleConfirm;
	public WebElement getEleConfirm()
	{
		return eleConfirm;
	}
	
	public String get_device_date(CommonUtility commonUtility) throws Exception
	{
		String sDeviceDateTUF = commonUtility.getDeviceDate();
		System.out.println("Device Date" + sDeviceDateTUF);
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		Date date = (Date) formatter.parse(sDeviceDateTUF);
		SimpleDateFormat formatter1 = new SimpleDateFormat("d/M/yyyy");
		formatter1.setTimeZone(TimeZone.getTimeZone("GMT"));
		String sTodayDate = formatter1.format(date);
		
		return sTodayDate;
	}
	
			private WebElement eleGenericList;
	public List<WebElement> geteleGenericList(String sChecklistQ)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElements(By.xpath("//*[@text='"+sChecklistQ+"']"));
		}
		else
		{																											
			//return driver.findElements(By.xpath("//*[@*[contains(.,'"+sChecklistQ+"')]]"));
			return driver.findElements(By.xpath("//*[@*='"+sChecklistQ+"']"));
		}	
		
		
		
	}
	
	
	private WebElement eleGeneric;
	public WebElement geteleGeneric(String sChecklistQ)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@text='"+sChecklistQ+"']"));
		}
		else
		{																											
			return driver.findElement(By.xpath("//*[@*[contains(.,'"+sChecklistQ+"')]]"));
		}	
		
		
		
	}
	
	
	private WebElement eleChecklistNextButton;
	public WebElement geteleChecklistNextButton()
	{
	return driver.findElementByAccessibilityId("SFM.CHECKLIST.NEXT");
		
	}
	
	private WebElement eleSection;
	public WebElement getElementSection(Integer nos)
	{
	return driver.findElementByAccessibilityId("SFM.CHECKLIST.SECTION-"+nos+"");
		
	}
	@FindBy(xpath="//*[@*[contains(.,'1 in progress')]]")
	private WebElement ele1inProgress;
	public WebElement getEle1Progess()
	{
		return ele1inProgress;
	}
	
	@FindBy(xpath="//*[@*='Finalize']")
	private WebElement eleFinalize;
	public WebElement geteleFinalize()
	{
		return eleFinalize;
	}
	
	public WebElement geteleCheckListQAns(String sPicklistQuestion)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return elechecklistPickListQAns = driver.findElement(By.xpath("//*[@text='"+sPicklistQuestion+"'][@class='android.widget.TextView']/following-sibling::*"));
		}
		else
		{
			return elechecklistPickListQAns = driver.findElement(By.xpath("//*[@name='"+sPicklistQuestion+"']/following-sibling::*"));
		}		
	}
	
}
