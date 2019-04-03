
package com.ge.fsa.pageobjects.browser;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.ge.fsa.lib.CommonUtility;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class Br_CalendarPO
{
	public Br_CalendarPO(WebDriver chromeDriver)
	{
		this.chromeDriver = chromeDriver;
		PageFactory.initElements(chromeDriver, this);
	}
	WebDriver chromeDriver = null;
	TouchAction touchAction = null;
	
	@FindBy(xpath="//div[@class='svmx-menu-icon-label'][text()='Calendar']")
	private WebElement elecalendarClick;
	public WebElement getEleCalendarClick()
	{
		return elecalendarClick;
	}
	
	
	
}






