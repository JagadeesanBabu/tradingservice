package com.datapolling.rabbitmq.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ConsumerSource {
    @Input("yahooTradeDataInput")
    SubscribableChannel input();

}
