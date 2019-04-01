package com.ge.fsa.pageobjects.phone;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;

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
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ph_ChecklistPO
{
	public Ph_ChecklistPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	

	@FindAll({@FindBy(xpath="//*[@text='More']"),
	@FindBy(id="More, tab, 4 of 4")})
	private WebElement eleMoreBtn;
	public WebElement getEleMoreBtn()
	{
		return eleMoreBtn;
	}
	
	//*[@text='Sanity2_Checklist']
	private WebElement eleChecklistName;
	public WebElement getEleChecklistName(String sChecklistName)
	{
		return eleChecklistName = driver.findElement(By.xpath("//*[@text='"+sChecklistName+"']"));
		
		
	}
	
	private WebElement elecheckliststartnew;
	public WebElement getelecheckliststartnew(String sChecklistName)
	{
		return elecheckliststartnew = driver.findElement(By.xpath("//*[@text='"+sChecklistName+"'][@class='android.widget.TextView']/..//*[@text='Start New']"));
	}
	
	/*@FindBy(xpath="//*[@text='Start New']")
	private WebElement eleStartNew;
	public WebElement geteeleStartNew()
	{
		return eleStartNew;
	}*/
	
	@FindBy(xpath="//*[@content-desc='Back']")
	private WebElement eleBackbutton;
	public WebElement geteleBackbutton()
	{
		return eleBackbutton;
	}
	
	@FindBy(xpath="//*[@text='In Progress']")
	private WebElement eleInProgress;
	public WebElement geteleInProgress()
	{
		return eleInProgress;
	}
	
	private WebElement elechecklistinprogress;
	public WebElement getelechecklistinprogress(String sChecklistName)
	{
		return elechecklistinprogress = driver.findElement(By.xpath("//*[@text='"+sChecklistName+"'][@class='android.widget.TextView']/..//*[@text='In Progress']"));
	}
	
	private WebElement elechecklistPickListQAns;
	public WebElement getelechecklistPickListQAns(String sPicklistQuestion, String sPicklistValue)
	{
		return elechecklistPickListQAns = driver.findElement(By.xpath("//*[@text='"+sPicklistQuestion+"'][@class='android.widget.TextView']/..//*[@text='"+sPicklistValue+"']"));
	}
	
	private WebElement eleChecklistGenericText;
	public WebElement geteleChecklistGenericText(String sGenericText)
	{
		return eleChecklistGenericText = driver.findElement(By.xpath("//*[@text='"+sGenericText+"']"));
	}

	private WebElement elechecklistRadioButtonQAns;
	public WebElement elechecklistRadioButtonQAns(String sRadioQuestion, String sRadiovalue)
	{
		return elechecklistRadioButtonQAns = driver.findElement(By.xpath("//*[@text='"+sRadioQuestion+"'][@class='android.widget.TextView']/..//*[@text='"+sRadiovalue+"']"));

	}

	private WebElement elechecklistcheckboxQAns;
	public WebElement getelechecklistcheckboxQAns(String sCheckboxQuestion, String sRadiovalue)
	{
		return elechecklistRadioButtonQAns = driver.findElement(By.xpath("//*[@text='"+sCheckboxQuestion+"'][@class='android.widget.TextView']/..//*[@text='"+sRadiovalue+"']"));

	}
	
	private WebElement elechecklistMultiPicklistQAns;
	public WebElement getelechecklistMultiPicklistQAns(String sMultiPickQuestion, String sMultivalue)
	{
		return elechecklistMultiPicklistQAns = driver.findElement(By.xpath("//*[@text='"+sMultiPickQuestion+"'][@class='android.widget.TextView']/..//*[@text='"+sMultivalue+"']"));

	}
	
	private WebElement elechecklistTextQAns;
	public WebElement getelechecklistTextQAns(String sTextQuestion)
	{
		return elechecklistTextQAns = driver.findElement(By.xpath("//*[@text='"+sTextQuestion+"']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"));

	}
	
	private WebElement elechecklistNumberQAns;
	public WebElement getelechecklistNumberQAns(String sNumberQuestion)
	{
		return elechecklistNumberQAns = driver.findElement(By.xpath("//*[@text='"+sNumberQuestion+"']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"));

	}
	
	private WebElement elegenericscroll;
	public WebElement getelegenericscroll(String sString)
	{
		return elegenericscroll = driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""+sString+"\"))"));

	}
	
	
	
}
