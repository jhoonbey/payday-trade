package az.ibar.accountservice.repository;

import az.ibar.accountservice.model.entity.StockEntity;

import java.util.List;

public interface StockRepository {
    List<StockEntity> getStocks();
}