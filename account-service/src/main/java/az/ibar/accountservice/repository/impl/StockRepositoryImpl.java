package az.ibar.accountservice.repository.impl;

import az.ibar.accountservice.model.entity.StockEntity;
import az.ibar.accountservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import java.util.List;

import static az.ibar.accountservice.util.AccountUtil.STOCKS;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final RedissonClient redissonClient;

    @Override
    public List<StockEntity> getStocks() {
        return redissonClient.getList(STOCKS);
    }
}