package br.com.qalab.service;

import br.com.qalab.dto.ProductRequest;
import br.com.qalab.dto.ProductResponse;
import br.com.qalab.entity.Product;
import br.com.qalab.exception.BusinessException;
import br.com.qalab.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock ProductRepository repository;
    @InjectMocks ProductService service;

    @Test
    void shouldListOnlyActiveProducts() {
        when(repository.findByActiveTrueOrderByNameAsc())
                .thenReturn(List.of(new Product("Mouse", "MOUSE-001", new BigDecimal("99.90"), 10)));
        List<ProductResponse> result = service.list();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().sku()).isEqualTo("MOUSE-001");
    }

    @Test
    void shouldRejectDuplicatedSku() {
        ProductRequest request = new ProductRequest("Mouse", "MOUSE-001", new BigDecimal("99.90"), 10);
        when(repository.existsBySkuIgnoreCase("MOUSE-001")).thenReturn(true);
        assertThatThrownBy(() -> service.create(request))
                .isInstanceOf(BusinessException.class)
                .hasMessage("SKU already exists");
        verify(repository, never()).save(any());
    }
}
