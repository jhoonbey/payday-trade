package az.ibar.orderservice.service;

import az.ibar.orderservice.model.dto.OrderCreateDto;

public interface OrderService {
    void createOrder(OrderCreateDto orderDto);

    void completeOrder(Long id);
}
