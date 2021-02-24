package az.ibar.stockservice.repository;

import az.ibar.stockservice.model.StockDto;

import java.util.List;

public interface StockRepository {

    void saveStock(List<StockDto> stockDtos);

    List<StockDto> getStocks();
}