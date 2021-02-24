package az.ibar.stockservice.repository;

import az.ibar.stockservice.model.OrderDto;

import java.util.List;

public interface OrderRepository {

    void saveOrder(OrderDto order);

    void deleteOrder(OrderDto order);

    List<OrderDto> getOrders();

}
