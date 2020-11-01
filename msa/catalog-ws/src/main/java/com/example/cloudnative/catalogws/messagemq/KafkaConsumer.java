package com.example.cloudnative.catalogws.messagemq;

import com.example.cloudnative.catalogws.entity.CatalogEntity;
import com.example.cloudnative.catalogws.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {
 
	@Autowired
	CatalogRepository repository;
	
	@KafkaListener(topics="example-kafka-test")
    public void processMessage(String kafkaMessage) {
		log.info("kafkaMessage : =====> " + kafkaMessage);

		Map<Object, Object> map = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			map = mapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>(){});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		CatalogEntity entity = repository.findByProductId((String)map.get("productId"));
		entity.setStock(entity.getStock() - (Integer)map.get("qty"));

		repository.save(entity);
    }
}