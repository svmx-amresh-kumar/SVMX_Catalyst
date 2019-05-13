package com.ge.fsa.pageobjects.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class Br_LoginPagePO {
	
	WebDriver driver;
	public Br_LoginPagePO(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 15), this);
		}
	
@FindBy(id="username") private WebElement txtUserName;
	
	@FindBy(id="password") private WebElement txtPassword;
	
	@FindBy(id="submitFrm") private WebElement btnLogin;
	
	public void geLogin(String sUserName,String sPassword) {
		
		txtUserName.sendKeys(sUserName);
		txtPassword.sendKeys(sPassword);
		btnLogin.click();
		
	}

}
