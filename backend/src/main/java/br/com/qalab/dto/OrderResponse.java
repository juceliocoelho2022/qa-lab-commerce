package br.com.qalab.dto;

import br.com.qalab.entity.CustomerOrder;
import br.com.qalab.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record OrderResponse(Long id, String customerEmail, OrderStatus status, BigDecimal total,
                            OffsetDateTime createdAt, List<Item> items) {
    public record Item(Long productId, String productName, BigDecimal unitPrice, int quantity) {}

    public static OrderResponse from(CustomerOrder order) {
        return new OrderResponse(order.getId(), order.getCustomerEmail(), order.getStatus(), order.getTotal(),
                order.getCreatedAt(), order.getItems().stream()
                .map(i -> new Item(i.getProductId(), i.getProductName(), i.getUnitPrice(), i.getQuantity()))
                .toList());
    }
}
