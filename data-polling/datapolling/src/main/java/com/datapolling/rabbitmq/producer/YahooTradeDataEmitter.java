package com.datapolling.rabbitmq.producer;

import java.time.Duration;

import com.datapolling.driver.Driver;
import com.datapolling.elementservice.ElementService;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBinding(YahooTradeBindings.class)
@Component
@RequiredArgsConstructor
public class YahooTradeDataEmitter {

    private final YahooTradeBindings yahooTradeBindings;
  

    public void emitData() {

        String xpath = "//*[@id='quote-market-notice']//parent::div/span";

        try {
            new FluentWait<>(Driver.getFireFoxDriver()).ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class).pollingEvery(Duration.ofSeconds(1))
                    .withTimeout(Duration.ofSeconds(3600)).until(input -> {
                        WebElement obj = ElementService.findElementByCssOrXpath(xpath, Driver.getFireFoxDriver(), 1);
                        String text = obj.getText();
                        yahooTradeBindings.output().send(MessageBuilder.withPayload(text).build());
                        return null;

                    });
        } catch (Exception ex) {
        }
        Driver.getFireFoxDriver().quit();
    }

    
}