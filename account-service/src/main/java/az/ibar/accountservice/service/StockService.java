package az.ibar.accountservice.service;

import az.ibar.accountservice.model.entity.StockEntity;

import java.util.List;

public interface StockService {
    List<StockEntity> getStocks();
}
