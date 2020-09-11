package com.datapolling.elementservice;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;


public class ElementService {
    public static WebElement findElementByCssOrXpath(String cssOrXpath, WebDriver driver, long duration) {
        final WebElement[] element = new WebElement[1];

        //Forming script for CSS or Xpath
        String script = getScriptToExecute(cssOrXpath);
        try {
            new FluentWait<>(driver).withTimeout(Duration.ofSeconds(duration)).ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class).pollingEvery(Duration.ofSeconds(1))
                    .until(input -> {
                        WebElement obj = (WebElement) ((JavascriptExecutor) driver).executeScript(script);
                        element[0] = obj;
                        return obj != null && !obj.getTagName().isEmpty();
                    });
        } catch (Exception ex) {
        }
        return element[0];
    }


    private static String getScriptToExecute(String cssOrXpath) {
        String script;
        String escapedCss;
        if (cssOrXpath.startsWith("//")) {
            String nonFormattedScript = "return document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue";
            script = String.format(nonFormattedScript, cssOrXpath);
        } else {
            escapedCss = getEscapedCss(cssOrXpath);
            script = "return document.querySelector(\'" + escapedCss + "\')";
        }
        return script;
    }

    public static String getEscapedCss(String css) {
        String escapedCss;
        String temp;
        if (css.contains("=\'")) {
            temp = css.replace("=\'", "=\\\"");
            temp = temp.replace("\']", "\\\"]");
        } else if (css.contains("='")) {
            temp = css.replaceAll("='", "=\\\"");
            temp = temp.replace("']", "\\\"]");
        } else {
            temp = css;
        }
        escapedCss = temp.replaceAll("'", "\\\\'");
        return escapedCss;
    }
    
}