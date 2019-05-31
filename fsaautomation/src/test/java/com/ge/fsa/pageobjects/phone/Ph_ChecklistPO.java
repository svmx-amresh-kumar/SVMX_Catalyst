package com.ge.fsa.pageobjects.phone;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.offset.PointOption;



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
			return elecheckliststartnew = driver.findElement(By.xpath("//*[@text='"+sChecklistName+"'][@class='android.widget.TextView']/..//*[@text='Start New']"));

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
	
	
	@FindBy(xpath="//*[@*='APP.BACK_BUTTON']")
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
	
	@AndroidFindBy(xpath="//*[@*='Completed']")
	@iOSXCUITFindBy(xpath="(//*[@*[contains(.,'Checklist Section Title COMPLETED')]])[last()]")
	private WebElement eleChecklistCompleted;
	public WebElement geteleChecklistCompleted()
	{
		return eleChecklistCompleted;
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
	public WebElement getelechecklistDateQAnsValue(String sTextQuestion,String sDateAnswer)
	{
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return elechecklistDateQAnsValue = driver.findElement(By.xpath("//*[@text='"+sTextQuestion+"']//following-sibling::*[@class='android.view.ViewGroup']//*[@class='android.widget.EditText'][@text='"+sDateAnswer+"']"));
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
			return elechecklistNumberQAns = driver.findElement(By.xpath("//*[@name='"+sNumberQuestion+"']//XCUIElementTypeTextField"));
		}	
	}
	
	private WebElement elechecklistinstance;
	public WebElement getelechecklistinstance() 
	{
		return elechecklistinstance = driver.findElementByAccessibilityId("CHECKLIST.INSTANCE.ITEM.0");
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
	
	
}
