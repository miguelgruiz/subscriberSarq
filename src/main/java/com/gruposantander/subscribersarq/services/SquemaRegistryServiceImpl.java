package com.gruposantander.subscribersarq.services;

import java.io.IOException;
import java.util.HashMap;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gruposantander.subscribersarq.dtos.CustodianInputDto;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;

@Service
public class SquemaRegistryServiceImpl {

	@Value("${spring.kafka.properties.schema.registry.url}")
	private String URL_SQUEMA_REGISTRY;

	public GenericRecord createGenericRecord(CustodianInputDto custodianInputDto) {
		HashMap<String, Object> hashMap = this.toHashMap(custodianInputDto);
		Schema schema = this.getSchema("com.gruposantander.sarq.schemas.Custodian");
		return this.buildGenericRecord(hashMap, schema);
	}

	private HashMap<String, Object> toHashMap(CustodianInputDto custodianInputDto) {
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("hash", custodianInputDto.getHash());
		hashMap.put("uri", custodianInputDto.getUri());
		hashMap.put("proc", custodianInputDto.getProc());
		hashMap.put("version", custodianInputDto.getVersion());
		hashMap.put("comment", custodianInputDto.getComment());
		hashMap.put("origins", null);
		return hashMap;
	}

	private Schema getSchema(String namespaceSquemaRegistry) {
		CachedSchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(URL_SQUEMA_REGISTRY, 1000);
		SchemaMetadata metadata = null;
		Schema schema = null;
		try {
			metadata = schemaRegistryClient.getLatestSchemaMetadata(namespaceSquemaRegistry);
			schema = schemaRegistryClient.getById(metadata.getId());
		} catch (RestClientException | IOException e) {
			e.printStackTrace();
		}
		return schema;
	}

	private GenericRecord buildGenericRecord(HashMap<String, Object> object, Schema schema) {
		GenericRecordBuilder builder = new GenericRecordBuilder(schema);
		GenericRecord record = builder.build();
		schema.getFields().forEach(r -> record.put(r.name(), object.get(r.name())));
		return record;
	}
}
