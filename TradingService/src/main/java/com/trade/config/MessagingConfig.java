package com.trade.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;

@Configuration
public class MessagingConfig {

    public static final String JSON_DATE_CONFIG = "yyyy/MM/dd";

    public static final String MESSAGE_PARSER = "messagingMessageParser";

    /**
     * JSON builder configuration
     *
     * @return configured message builder
     */
    @Bean(MESSAGE_PARSER)
    @Primary
    public ObjectMapper messageParser() {
        return Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .serializationInclusion(Include.NON_NULL)
                .dateFormat(new SimpleDateFormat(JSON_DATE_CONFIG))
                .build();
    }

}
