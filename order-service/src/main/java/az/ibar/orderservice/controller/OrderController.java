package az.ibar.orderservice.controller;

import az.ibar.orderservice.model.dto.OrderCreateDto;
import az.ibar.orderservice.model.dto.OrderDto;
import az.ibar.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void createOrder(@RequestBody OrderCreateDto order) {
        orderService.createOrder(order);
    }
}
