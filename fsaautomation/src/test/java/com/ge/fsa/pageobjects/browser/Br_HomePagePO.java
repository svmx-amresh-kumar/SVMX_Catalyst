package com.ge.fsa.pageobjects.browser;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class Br_HomePagePO {
	
	WebDriver driver;
	public Br_HomePagePO(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
		}
	
@FindBy(xpath="//div[@class='b-sch-clockwrap b-sch-clock-hour b-sch-tooltip-startdate']") private WebElement startDateTime;
	
	@FindBy(xpath="//span[text()='Delete event']") private WebElement deleteEventBtn;
	
	@FindBy(xpath="//span[text()='Edit event']") private WebElement editEventBtn;
	
	@FindBy(xpath="//input[@name='name']") private WebElement eventNameTxt;
	
	@FindBy(xpath="//input[@name='resource']") private WebElement resourceTxt;
	
	@FindBy(xpath="//button[text()='Cancel']") private WebElement eventCancelBtn;
	
	@FindBy(xpath="//button[text()='Save']") private WebElement eventSaveBtn;
	
	@FindBy(xpath="//label[text()='Start']/..//input") private WebElement startDateTxt;
	
	@FindBy(xpath="//label[text()='End']/..//input") private WebElement endDateTxt;
	
	@FindBy(xpath="(//input[@name='startDate'])[2]") private WebElement startTimeTxt;
	
	@FindBy(xpath="(//input[@name='endDate'])[2]") private WebElement endTimeTxt;
	
	@FindBy(xpath="//div[text()='Edit event']") private WebElement eventWindowHeader;
	
	@FindBy(xpath="//div[@class='b-icon b-align-end b-icon-remove b-widget b-fieldtrigger']") private WebElement clearBtn;
	
	public WebElement getTechnician(String sTechnician) {
		return driver.findElement(By.xpath("//div[normalize-space()='"+sTechnician+"']"));
	}
	
	@FindBy(xpath="//div[normalize-space()='Jong']") private WebElement jong;
	
	public WebElement getTime(String sTime) {
		return driver.findElement(By.xpath("//div[text()='"+sTime+"']"));
	}
	
	public WebElement getTech(String sTechName) {
		return driver.findElement(By.xpath("//dt[text()='"+sTechName+"']/ancestor::div[@class='b-grid-cell']"));
	}
	
	public WebElement getMeeting(String sMeetingName) {
//		return driver.findElement(By.xpath("//div[text()='"+sMeetingName+"']/parent::div"));
		return driver.findElement(By.xpath("//*[contains(text(),'"+sMeetingName+"')]/parent::div"));
	}
	
	public WebElement getMenuOption(String sOptionName) {
		return driver.findElement(By.xpath("//span[text()='"+sOptionName+"']"));
	}
	
	public void moveMeeting(String sTime,String sTechName,String sMeetingName,Actions actionTest) {
		int xTime = getTime(sTime).getLocation().getX();
		int yTechnician = getTech(sTechName).getLocation().getY();
		int xMeeting = getMeeting(sMeetingName).getLocation().getX();
		int yMeeting = getMeeting(sMeetingName).getLocation().getY();
		actionTest.dragAndDropBy(getMeeting(sMeetingName), xTime-xMeeting,yTechnician-yMeeting).build().perform();
	}
	
	public void resizeMeeting(String sMeetingName,JavascriptExecutor js,String sPxSize,Actions actionTest) throws InterruptedException {
		String id = getMeeting(sMeetingName).getAttribute("id");
		js.executeScript("document.getElementById('"+id+"').style.width ='"+sPxSize+"';");
		Thread.sleep(3000);
		actionTest.moveToElement(getMeeting(sMeetingName)).build().perform();
	}
	
	public void rightClick(String sTime,String sTechName,String sMeetingName,Actions actionTest) throws InterruptedException {
		int xTime = getTime(sTime).getLocation().getX();
		int yTechnician = getTech(sTechName).getLocation().getY();
		int xMeeting = getMeeting(sMeetingName).getLocation().getX();
		int yMeeting = getMeeting(sMeetingName).getLocation().getY();
		getMeeting(sMeetingName).click();
		actionTest.moveByOffset(xTime-xMeeting,yTechnician-yMeeting).contextClick().sendKeys(Keys.DOWN).perform();
		
		
	}
	
	public void rightClick(String sMeetingName,int flag,Actions actionTest) {
		
		if(flag==1) {
			actionTest.moveToElement(getMeeting(sMeetingName)).contextClick().sendKeys(Keys.DOWN).sendKeys(Keys.ENTER).perform();
		}
		else {
		actionTest.moveToElement(getMeeting(sMeetingName)).contextClick().sendKeys(Keys.DOWN).moveToElement(deleteEventBtn).sendKeys(Keys.ENTER).perform();
		}
	}
	
	public String getStartDateTime() {
		return startDateTime.getText();
	}
	
	public void setEventDetails(String sName,String sTechnician, String sStartDate,String sStartTime,String sEndDate,String sEndTime) throws InterruptedException {
		clearBtn.click();
		eventNameTxt.sendKeys(sName);
		resourceTxt.click();
		getTechnician(sTechnician).click();
//		Thread.sleep(5000);
//		startDateTxt.sendKeys(sStartDate);
//		Thread.sleep(5000);
//		startTimeTxt.clear();
//		Thread.sleep(5000);
//		startTimeTxt.sendKeys(sStartTime);
//		Thread.sleep(5000);
//		endDateTxt.clear();
//		Thread.sleep(5000);
//		endDateTxt.sendKeys(sEndDate);
//		Thread.sleep(5000);
//		endTimeTxt.clear();
//		Thread.sleep(5000);
//		endTimeTxt.sendKeys(sEndTime);
//		eventSaveBtn.click();
//		Thread.sleep(5000);
	}
}
