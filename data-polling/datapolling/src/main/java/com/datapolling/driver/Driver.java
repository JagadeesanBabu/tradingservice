package com.datapolling.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Driver {
    public static WebDriver driver;

    
    public static WebDriver getFireFoxDriver() {
        if (driver == null) {
            System.setProperty("webdriver.gecko.driver", "C:\\Users\\kelay\\Selenium\\geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
            options.setCapability("max_duration", 14405);
            driver = new FirefoxDriver(options);
            return driver;
        }
        return driver;

    }

    public static JavascriptExecutor getJsExecutor() {
        return (JavascriptExecutor) driver;
    }

    public static WebDriver instantiateNewDriver(){
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\kelay\\Selenium\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("max_duration", 14400);
        return new FirefoxDriver(options);
    }
    
}