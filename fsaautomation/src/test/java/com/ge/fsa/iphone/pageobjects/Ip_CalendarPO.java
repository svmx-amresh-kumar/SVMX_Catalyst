package com.ge.fsa.iphone.pageobjects;


import static org.testng.Assert.assertFalse;

import java.util.Iterator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.ExploreSearchPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ip_CalendarPO
{
	public Ip_CalendarPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	

	@FindBy(id="Calendar, tab, 1 of 4")
	private WebElement eleCalendarBtn;
	public WebElement getEleCalendarBtn()
	{
		return eleCalendarBtn;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"March 2019 \"])[4]/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther[1]")
	private WebElement eleCalendarViewMenu;
	public WebElement getEleCalendarViewMenu()
	{
		return eleCalendarViewMenu;
	}


	
}
