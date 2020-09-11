package com.datapolling.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(ConsumerSource.class)
@Slf4j
public class Consumer {

    /**
     * Listener
     */
    @StreamListener(target = "yahooTradeDataInput")
    public void consume(String message) {
        log.info("Received message {} " , message);
    }



}
