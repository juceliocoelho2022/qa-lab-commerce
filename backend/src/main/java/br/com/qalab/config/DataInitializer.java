package br.com.qalab.config;

import br.com.qalab.entity.*;
import br.com.qalab.repository.AppUserRepository;
import br.com.qalab.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(AppUserRepository users, ProductRepository products, PasswordEncoder encoder) {
        return args -> {
            if (users.count() == 0) {
                users.save(new AppUser("qa.admin@qalab.dev", encoder.encode("admin123"), Role.ADMIN));
                users.save(new AppUser("qa.user@qalab.dev", encoder.encode("user123"), Role.CUSTOMER));
            }
            if (products.count() == 0) {
                products.save(new Product("Notebook QA Pro", "NOTE-QA-001", new BigDecimal("4599.90"), 8));
                products.save(new Product("Mouse Automation", "MOUSE-AUTO-002", new BigDecimal("149.90"), 25));
                products.save(new Product("Teclado Test Runner", "KEY-TEST-003", new BigDecimal("329.90"), 12));
            }
        };
    }
}
