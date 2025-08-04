package com.example.ordermanagementsystem.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.List;

@Service
public class DynamoDBService {

    private final DynamoDbClient dynamoDbClient;

    public DynamoDBService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @PostConstruct
    public void init() {
        createOrdersTableIfNotExists();
    }

    public void createOrdersTableIfNotExists() {
        ListTablesResponse listTablesResponse = dynamoDbClient.listTables();
        if (!listTablesResponse.tableNames().contains("Orders")) {
            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName("Orders")
                    .keySchema(
                            KeySchemaElement.builder()
                                    .attributeName("orderId")  // ✅ FIXED
                                    .keyType(KeyType.HASH)
                                    .build()
                    )
                    .attributeDefinitions(
                            AttributeDefinition.builder()
                                    .attributeName("orderId")  // ✅ FIXED
                                    .attributeType(ScalarAttributeType.S)
                                    .build()
                    )
                    .provisionedThroughput(
                            ProvisionedThroughput.builder()
                                    .readCapacityUnits(5L)
                                    .writeCapacityUnits(5L)
                                    .build()
                    )
                    .build();

            dynamoDbClient.createTable(request);
            System.out.println("'Orders' table created successfully.");
        } else {
            System.out.println("'Orders' table already exists.");
        }
    }
}
