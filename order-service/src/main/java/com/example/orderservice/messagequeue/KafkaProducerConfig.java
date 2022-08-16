package com.example.orderservice.messagequeue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory <String, String> producerFactory () {
        Map<String, Object> properties = new HashMap<>();

        return new DefaultKafkaProducerFactory<>(properties);
    }
}
