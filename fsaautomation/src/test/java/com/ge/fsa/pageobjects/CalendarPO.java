/*
 *  @author MeghanaRao
 */
package com.ge.fsa.pageobjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.RestServices;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class CalendarPO 
{
	public CalendarPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	
	@FindBy(xpath="//div[@class='svmx-menu-icon-label'][text()='Calendar']")
	private WebElement elecalendarClick;
	public WebElement getEleCalendarClick()
	{
		return elecalendarClick;
	}
	
	private WebElement eleProductNameTxt;
	public WebElement getEleProductNameTxt(String sProductName)
	{
		eleProductNameTxt=driver.findElement(By.xpath("//div[@class='x-inner-el'][text()='"+sProductName+"']"));
		return eleProductNameTxt;
	}
	
	private WebElement eleworkordernumonCalendar;
	public WebElement getEleworkordernumonCalendar(String sWorkOrdernumber)
	{
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		eleworkordernumonCalendar=driver.findElement(By.xpath("//div[contains(.,'\" "+ sWorkOrdernumber +" \"')]/div[@class='sfmevent-location-container']"));
		
		return eleworkordernumonCalendar;
	}
	
	private WebElement eleworkordernumonCalendarWeek;
	public WebElement getEleworkordernumonCalendarWeek(String sWorkOrdernumber)
	{
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		eleworkordernumonCalendarWeek=driver.findElement(By.xpath("(//div[@class='sfmevent-day']//div[@class='sfmevent-location-container']//div[contains(text(),'"+sWorkOrdernumber+"')]/../div[contains(@class,'sfmevent-account')])[1]"));
		//eleworkordernumonCalendarWeek=driver.findElement(By.xpath("(//div[@class='sfmevent-day']//div[@class='sfmevent-location-container']//div[contains(text(),'"+sWorkOrdernumber+"')])[1]"));
		//eleworkordernumonCalendarWeek=driver.findElement(By.xpath("(//div[@class='sfmevent-day']//div[@class='sfmevent-content']//div[@class='sfmevent-location-container']//div[contains(text(),'"+sWorkOrdernumber+"')])"));
		//eleworkordernumonCalendarWeek=driver.findElement(By.xpath("(//div[contains(.,'" + sWorkOrdernumber + "')]/div[@class='sfmevent-location-container'])[1]"));
		return eleworkordernumonCalendarWeek;
	}

	@FindBy(xpath="//span[@class='x-button-label'][text()='Week']")
	private WebElement elecalendarWeektap;
	public WebElement getElecalendarWeektap()
	{
		return elecalendarWeektap;
	}
	

	
	@FindBy(xpath="//span[@class='x-button-label'][text()='Day']")
	private WebElement elecalendarDaytap;
	public WebElement getElecalendarDaytap()
	{
		return elecalendarDaytap;
	}
	/**
	 * Author - Meghana Rao
	 * @param commonsPo - Passing CommonsPO Function
	 * @param workordername - Passing the Work Order number
	 * @throws Exception - Throwing Required Exception
	 */
	public void openWofromCalendar(CommonsPO commonsPo, String workordername) throws Exception 
	{
		commonsPo.tap(getEleCalendarClick());
		Thread.sleep(3000);

		commonsPo.tap(getEleCalendarClick());
		Thread.sleep(3000);
		commonsPo.waitforElement(getEleworkordernumonCalendarWeek(workordername), 5000);
		
	
		if(getEleworkordernumonCalendarWeek(workordername) != null){
			System.out.println("Found WO " + workordername);
			commonsPo.tap(getEleworkordernumonCalendarWeek(workordername));
			
			}
				
		else
		{
			System.out.println("Did not Find WO " + workordername);
			throw new Exception("WorkOrder not found on the Calendar");	
		
	}

	}
	
}
