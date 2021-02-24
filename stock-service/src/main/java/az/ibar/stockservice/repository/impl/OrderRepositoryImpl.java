package az.ibar.stockservice.repository.impl;

import az.ibar.stockservice.model.OrderDto;
import az.ibar.stockservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import java.util.List;

import static az.ibar.stockservice.util.StockUtil.ORDER_LIST_KEY;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

    private final RedissonClient redissonClient;

    @Override
    public void saveOrder(OrderDto order) {
        redissonClient.getList(ORDER_LIST_KEY).add(order);
    }

    public void deleteOrder(OrderDto order) {
        redissonClient.getList(ORDER_LIST_KEY).remove(order);
    }

    @Override
    public List<OrderDto> getOrders() {
        return redissonClient.getList(ORDER_LIST_KEY);
    }
}