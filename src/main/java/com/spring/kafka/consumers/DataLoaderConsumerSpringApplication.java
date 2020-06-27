package com.spring.kafka.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;


@SpringBootApplication
public class DataLoaderConsumerSpringApplication {
	
	private static Logger logger = LoggerFactory.getLogger(DataLoaderConsumerSpringApplication.class);
	

	
	public static void main(String[] args) {
		
		SpringApplication.run(DataLoaderConsumerSpringApplication.class, args);
		
		logger.info("DataLoaderConsumer Application Started");
	}
	

	
	@KafkaListener(topics = "${kafka.bootstrap.host}", groupId = "full-read")
	public void listen(String message) {
		
		logger.info("Received Messasge in group - full-read: " + message);
		
		//System.out.println(message);
	
	}
	
	

}
