package com.example.ordermanagementsystem.repository;

import com.example.ordermanagementsystem.model.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.Key;


@Repository
public class DynamoDBRepository {

    private final DynamoDbEnhancedClient enhancedClient;
    private DynamoDbTable<Order> orderTable;

    @Autowired
    public DynamoDBRepository(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @PostConstruct
    public void init() {
        orderTable = enhancedClient.table("Orders", TableSchema.fromBean(Order.class));
        // orderTable.createTable(); // Uncomment only for testing if local table doesn’t exist
    }

    public void saveOrder(Order order) {
        orderTable.putItem(order);
    }

    public Order getOrderById(String orderId) {
        return orderTable.getItem(Key.builder().partitionValue(orderId).build());
    }
}
