package com.datapolling;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.datapolling.driver.Driver;
import com.datapolling.elementservice.ElementService;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RealTimeMinData {

    List<String> realTimeValues = new ArrayList<>();
    List<String> tempStorage = new ArrayList<>();
    RabbitTemplate rabbitTemplate = new RabbitTemplate(); 

    // @Scheduled(fixedDelay = 1)
    public void collectData() {

        String xpath = "//*[@id='quote-market-notice']//parent::div/span";


        try {
            new FluentWait<>(Driver.getFireFoxDriver()).ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class).pollingEvery(Duration.ofSeconds(1))
                    .withTimeout(Duration.ofSeconds(3600)).until(input -> {
                        WebElement obj = ElementService.findElementByCssOrXpath(xpath, Driver.getFireFoxDriver(), 1);
                        realTimeValues.add(obj.getText());
                        tempStorage = realTimeValues;
                        // System.out.println(obj.getText());
                        return null;

                    });
        } catch (Exception ex) {
        }
        Driver.getFireFoxDriver().quit();
    }


}