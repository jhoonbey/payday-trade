package az.ibar.stockservice.repository.impl;

import az.ibar.stockservice.model.StockDto;
import az.ibar.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import java.util.List;

import static az.ibar.stockservice.util.StockUtil.STOCK_LIST_KEY;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final RedissonClient redissonClient;

    @Override
    public void saveStock(List<StockDto> stockDtoList) {
        redissonClient.getList(STOCK_LIST_KEY).clear();
        redissonClient.getList(STOCK_LIST_KEY).addAll(stockDtoList);
    }

    @Override
    public List<StockDto> getStocks() {
        return redissonClient.getList(STOCK_LIST_KEY);
    }
}