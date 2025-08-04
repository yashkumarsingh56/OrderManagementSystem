package com.example.ordermanagementsystem.controller;

import com.example.ordermanagementsystem.model.Order;
import com.example.ordermanagementsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*") // Allow all origins for frontend testing
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ✅ CREATE Order with file upload (POST /orders/upload)
    @PostMapping("/upload")
    public ResponseEntity<Order> createOrderWithFile(
            @RequestParam("customerName") String customerName,
            @RequestParam("item") String item,
            @RequestParam("quantity") int quantity,
            @RequestParam("orderAmount") double orderAmount,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setCustomerName(customerName);
        order.setItem(item);
        order.setQuantity(quantity);
        order.setOrderAmount(orderAmount);
        order.setOrderDate(LocalDate.now().toString());

        // Simulated file upload (replace with real S3 upload if needed)
        if (file != null && !file.isEmpty()) {
            order.setInvoiceFileUrl("https://dummy-s3.com/invoices/" + file.getOriginalFilename());
        }

        Order saved = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ✅ UPDATE Order (PUT /orders/{id})
    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrderWithFile(
            @PathVariable String id,
            @RequestParam("customerName") String customerName,
            @RequestParam("item") String item,
            @RequestParam("quantity") int quantity,
            @RequestParam("orderAmount") double orderAmount,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        Order updatedOrder = new Order();
        updatedOrder.setCustomerName(customerName);
        updatedOrder.setItem(item);
        updatedOrder.setQuantity(quantity);
        updatedOrder.setOrderAmount(orderAmount);
        updatedOrder.setOrderDate(LocalDate.now().toString());

        if (file != null && !file.isEmpty()) {
            updatedOrder.setInvoiceFileUrl("https://dummy-s3.com/invoices/" + file.getOriginalFilename());
        }

        Optional<Order> updated = orderService.updateOrder(id, updatedOrder);
        if (updated.isPresent()) {
            return ResponseEntity.ok("Order with ID " + id + " updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
    }

    // ✅ GET All Orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ✅ GET Order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Optional<Order> order = orderService.getOrder(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ✅ DELETE Order
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }
}
