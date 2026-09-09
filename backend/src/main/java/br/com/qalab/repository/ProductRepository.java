package br.com.qalab.repository;

import br.com.qalab.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrueOrderByNameAsc();
    boolean existsBySkuIgnoreCase(String sku);
}
