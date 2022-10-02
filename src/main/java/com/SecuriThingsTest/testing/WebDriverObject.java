package com.SecuriThingsTest.testing;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverObject {
	
	private static WebDriver driver = null;
	
	private WebDriverObject() {
	}
	
	 public static WebDriver getInstance(eBrowser browserChoice)
	    {
	        if (driver == null)
	            driver = chooseBrowser(browserChoice);
	  
	        return driver;
	    }
	
	private static WebDriver chooseBrowser(eBrowser browserChoice) {
		switch(browserChoice) {
		case CHROME:
		{
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		}
		case FIREFOX:
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		}
		case EDGE:
		{
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		}
		}
		return driver;
	}

}
