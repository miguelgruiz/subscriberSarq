package com.gruposantander.subscribersarq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.gruposantander.subscribersarq.channels.KafkaStreamChannel;

@EnableBinding(KafkaStreamChannel.class)
public class KafkaStreamConfig {
	
	@Value("${spring.cloud.stream.schemaRegistryClient.endpoint}")
    private String endpoint;

    @Bean
    public SchemaRegistryClient confluentSchemaRegistryClient() {
        ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
        client.setEndpoint(endpoint);
        return client;
    }
	
}
