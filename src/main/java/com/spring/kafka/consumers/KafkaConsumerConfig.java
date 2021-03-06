package com.spring.kafka.consumers;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.spring.trade.entities.ZerodhaTick;
import com.spring.trade.entities.ZerodhaTickMongoRepository;

@EnableKafka
@Configuration
@EnableMongoRepositories("com.spring.trade.entities")

public class KafkaConsumerConfig {
	
	@Value("${kafka.bootstrap.host}")
	String bootstrap_server;
	
	@Value("${spring.data.mongodb.uri}")
	String connectionString;
	


	 
	@Bean
	   public ConsumerFactory<String, String> consumerFactory() {
	      Map<String, Object> props = new HashMap<>();
	      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_server+":9092");
	      props.put(ConsumerConfig.GROUP_ID_CONFIG, "full-read");
	      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	      return new DefaultKafkaConsumerFactory<>(props);
	   }
	   @Bean
	   public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
	      ConcurrentKafkaListenerContainerFactory<String, String> 
	      factory = new ConcurrentKafkaListenerContainerFactory<>();
	      factory.setConsumerFactory(consumerFactory());
	      factory.setConcurrency(5);
	      return factory;
	   }
	   
	

}
