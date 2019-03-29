package com.gruposantander.subscribersarq.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer2;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value(value = "${spring.kafka.consumer.group-id}")
	private String groupId;
	
	@Value(value = "${spring.kafka.consumer.auto-offset-reset}")
	private String autoOffsetReset;
	
	@Value(value = "${spring.kafka.properties.schema.registry.url}")
	private String schemaRegistryUrl;

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer2.class);
		props.put(ErrorHandlingDeserializer2.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
		props.put(ErrorHandlingDeserializer2.VALUE_DESERIALIZER_CLASS, KafkaAvroDeserializer.class);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		props.put("schema.registry.url", schemaRegistryUrl);
		return props;
	}

	@Bean
	public ConsumerFactory<String, GenericRecord> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, GenericRecord>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, GenericRecord> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());

		return factory;
	}

}
