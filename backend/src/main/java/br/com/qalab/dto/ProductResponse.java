package br.com.qalab.dto;

import br.com.qalab.entity.Product;
import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String sku, BigDecimal price, int stock, boolean active) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getSku(),
                product.getPrice(), product.getStock(), product.isActive());
    }
}
