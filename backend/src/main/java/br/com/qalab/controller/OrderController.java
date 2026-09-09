package br.com.qalab.controller;

import br.com.qalab.dto.*;
import br.com.qalab.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) { this.orderService = orderService; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@AuthenticationPrincipal Jwt jwt, @Valid @RequestBody CreateOrderRequest request) {
        return orderService.create(jwt.getSubject(), request);
    }

    @GetMapping("/mine")
    public List<OrderResponse> mine(@AuthenticationPrincipal Jwt jwt) {
        return orderService.listMine(jwt.getSubject());
    }

    @PostMapping("/{id}/payment")
    public OrderResponse pay(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id,
                             @Valid @RequestBody PaymentRequest request) {
        return orderService.pay(jwt.getSubject(), id, request);
    }
}
