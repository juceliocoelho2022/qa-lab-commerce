package br.com.qalab.service;

import br.com.qalab.dto.*;
import br.com.qalab.entity.*;
import br.com.qalab.exception.BusinessException;
import br.com.qalab.repository.CustomerOrderRepository;
import br.com.qalab.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {
    private final ProductRepository products;
    private final CustomerOrderRepository orders;
    private final boolean allowNegativeStock;

    public OrderService(ProductRepository products, CustomerOrderRepository orders,
                        @Value("${qa-lab.bugs.allow-negative-stock:false}") boolean allowNegativeStock) {
        this.products = products;
        this.orders = orders;
        this.allowNegativeStock = allowNegativeStock;
    }

    @Transactional
    public OrderResponse create(String email, CreateOrderRequest request) {
        CustomerOrder order = new CustomerOrder(email);
        for (OrderItemRequest requested : request.items()) {
            Product product = products.findById(requested.productId())
                    .filter(Product::isActive)
                    .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Product not found: " + requested.productId()));
            if (!allowNegativeStock && product.getStock() < requested.quantity()) {
                throw new BusinessException(HttpStatus.CONFLICT, "Insufficient stock for SKU " + product.getSku());
            }
            product.removeStock(requested.quantity());
            order.addItem(product, requested.quantity());
        }
        return OrderResponse.from(orders.save(order));
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> listMine(String email) {
        return orders.findByCustomerEmailOrderByCreatedAtDesc(email).stream().map(OrderResponse::from).toList();
    }

    @Transactional
    public OrderResponse pay(String email, Long id, PaymentRequest request) {
        CustomerOrder order = orders.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Order not found"));
        if (!order.getCustomerEmail().equalsIgnoreCase(email)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "Order belongs to another customer");
        }
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BusinessException(HttpStatus.CONFLICT, "Order has already been processed");
        }
        if (request.cardNumber().endsWith("0")) {
            order.markDeclined();
        } else {
            order.markPaid();
        }
        return OrderResponse.from(order);
    }
}
