package com.datapolling;

import javax.sound.midi.Receiver;

import com.datapolling.driver.Driver;
import com.datapolling.elementservice.ElementService;
import com.datapolling.listener.RealTimeDataListener;
import com.datapolling.rabbitmq.producer.YahooTradeDataEmitter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	// public final static String REAL_TIME_QUEUE = "real-time-queue";

	// @Bean
	// Queue queue() {
	// 	return new Queue(REAL_TIME_QUEUE, false);
	// }

	// @Bean
	// TopicExchange exchange() {
	// 	return new TopicExchange("spring-boot-exchange");
	// }

	// @Bean
	// Binding binding(Queue queue, TopicExchange exchange ){
	// 	return BindingBuilder.bind(queue).to(exchange).with(REAL_TIME_QUEUE);
	// }

	// @Bean
	// SimpleMessageListenerContainer container(final ConnectionFactory factory,
	// 		final MessageListenerAdapter listenerAdaper) {
	// 	final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	// 	container.setConnectionFactory(factory);
	// 	container.setQueueNames(REAL_TIME_QUEUE);
	// 	container.setMessageListener(listenerAdaper);
	// 	return container;

	// }

	// @Bean
	// MessageListenerAdapter listenerAdapter(RealTimeDataListener listener){
	// 	return new MessageListenerAdapter(listener, "receiveMessage");
	// }

	public static void main(final String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		final WebDriver driver = Driver.getFireFoxDriver();
		driver.navigate().to("https://finance.yahoo.com/quote/%5ENSEBANK/");
		final WebElement agree = ElementService.findElementByCssOrXpath("//button[text()='I agree']", driver, 2);
		if (agree != null) {
			ElementService.findElementByCssOrXpath("//button[text()='I agree']", driver, 2).click();
		}

		YahooTradeDataEmitter trade = context.getBean(YahooTradeDataEmitter.class);
		trade.emitData();	
		System.out.println("teststignnn");
	}

}
