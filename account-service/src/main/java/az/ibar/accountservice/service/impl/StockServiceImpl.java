package az.ibar.accountservice.service.impl;

import az.ibar.accountservice.model.entity.StockEntity;
import az.ibar.accountservice.repository.StockRepository;
import az.ibar.accountservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public List<StockEntity> getStocks() {
        return stockRepository.getStocks();
    }
}