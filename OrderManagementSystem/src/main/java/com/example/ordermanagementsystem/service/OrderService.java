package com.example.ordermanagementsystem.service;

import com.example.ordermanagementsystem.model.Order;
import com.example.ordermanagementsystem.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    // ✅ Save Order (handles orderId and orderDate)
    public Order saveOrder(Order order) {
        if (order.getOrderId() == null || order.getOrderId().isEmpty()) {
            order.setOrderId(UUID.randomUUID().toString());
        }

        if (order.getOrderDate() == null || order.getOrderDate().isEmpty()) {
            order.setOrderDate(LocalDate.now().toString());
        }

        repository.save(order);
        return order;
    }

    // ✅ Get all orders
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    // ✅ Find order by ID
    public Optional<Order> getOrder(String id) {
        return Optional.ofNullable(repository.findById(id));
    }

    // ✅ Delete order
    public String deleteOrder(String id) {
        repository.deleteById(id);
        return "Order with ID " + id + " deleted.";
    }

    // ✅ Update order (overwrites the existing record)
    public Optional<Order> updateOrder(String id, Order updatedOrder) {
        Order existing = repository.findById(id);
        if (existing != null) {
            updatedOrder.setOrderId(id);  // retain same ID
            updatedOrder.setOrderDate(existing.getOrderDate()); // preserve original date
            repository.save(updatedOrder);
            return Optional.of(updatedOrder);
        } else {
            return Optional.empty();
        }
    }
}
