package com.example.ordermanagementsystem.repository;

import com.example.ordermanagementsystem.model.Order;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

    private final DynamoDbTable<Order> orderTable;

    public OrderRepository(DynamoDbEnhancedClient enhancedClient) {
        this.orderTable = enhancedClient.table("orders", TableSchema.fromBean(Order.class));
    }

    public void save(Order order) {
        orderTable.putItem(order);
    }

    public Order findById(String id) {
        return orderTable.getItem(r -> r.key(k -> k.partitionValue(id)));
    }

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        orderTable.scan().items().forEach(orders::add);
        return orders;
    }

    public void deleteById(String id) {
        orderTable.deleteItem(r -> r.key(k -> k.partitionValue(id)));
    }
}
