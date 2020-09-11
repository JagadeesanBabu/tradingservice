package com.datapolling.rabbitmq.producer;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface YahooTradeBindings {

    @Output("yahooTradeDataOutput")
    MessageChannel output();
    
}