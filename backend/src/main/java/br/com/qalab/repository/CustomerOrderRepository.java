package br.com.qalab.repository;

import br.com.qalab.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByCustomerEmailOrderByCreatedAtDesc(String customerEmail);
}
