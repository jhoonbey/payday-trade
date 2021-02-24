package az.ibar.stockservice.service;

import az.ibar.stockservice.model.StockDto;

import java.util.List;

public interface StockService {
    void updateStock();

    List<StockDto> getStocks();
}