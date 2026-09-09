package br.com.qalab.service;

import br.com.qalab.dto.ProductRequest;
import br.com.qalab.dto.ProductResponse;
import br.com.qalab.entity.Product;
import br.com.qalab.exception.BusinessException;
import br.com.qalab.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository products;

    public ProductService(ProductRepository products) { this.products = products; }

    public List<ProductResponse> list() {
        return products.findByActiveTrueOrderByNameAsc().stream().map(ProductResponse::from).toList();
    }

    public ProductResponse create(ProductRequest request) {
        if (products.existsBySkuIgnoreCase(request.sku())) {
            throw new BusinessException(HttpStatus.CONFLICT, "SKU already exists");
        }
        Product product = products.save(new Product(request.name(), request.sku(), request.price(), request.stock()));
        return ProductResponse.from(product);
    }
}
