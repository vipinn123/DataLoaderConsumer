package com.spring.kafka.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;

import com.google.gson.Gson;
import com.spring.trade.entities.ZerodhaTick;
import com.spring.trade.entities.ZerodhaTickDepth;
import com.spring.trade.entities.ZerodhaTickMongoRepository;
import com.zerodhatech.models.Tick;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DataLoaderConsumerSpringApplication {
	
	private static Logger logger = LoggerFactory.getLogger(DataLoaderConsumerSpringApplication.class);
	
	@Autowired
	private ZerodhaTickMongoRepository zerodhaTickMongoRepository;
	

	
	public static void main(String[] args) {
		
		SpringApplication.run(DataLoaderConsumerSpringApplication.class, args);
		
		logger.info("DataLoaderConsumer Application Started");
	}
	

	
	@KafkaListener(topics = "${TICK_DATA_TOPIC}", groupId = "full-read")
	public void listen(String message) {
		
		logger.info("Received Messasge in group - full-read: " + message);
		
		
		try {
			
			
			ZerodhaTick zerodhaTick = new Gson().fromJson(message, ZerodhaTick.class);
			
			logger.info("Ticker data Average Trade Price:" + zerodhaTick.getLastTradedPrice());
			
			ZerodhaTickDepth depth = zerodhaTick.getDepth().get("buy").get(0);
			
			if(depth != null) {
				zerodhaTick.computeDepthStats();
				
				
			}
			
			zerodhaTickMongoRepository.save(zerodhaTick);
			String zerodhaTickJson = new Gson().toJson(zerodhaTick, ZerodhaTick.class);
			
			
			
			logger.info("Ticker data Detail converted to JSON:" + zerodhaTickJson);
			
			
			
		}catch(Exception ex) {
			logger.info(ex.toString());
		}
		
	}
	
	

}
