package az.ibar.stockservice.service.impl;

import az.ibar.stockservice.model.StockDto;
import az.ibar.stockservice.repository.StockRepository;
import az.ibar.stockservice.service.StockService;
import az.ibar.stockservice.util.StockMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static az.ibar.stockservice.util.StockUtil.DEFAULT_PRICE_MAX;
import static az.ibar.stockservice.util.StockUtil.DEFAULT_PRICE_MIN;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    @Override
    public void updateStock() {
        stockRepository.saveStock(getLiveStocks());
    }

    @Override
    public List<StockDto> getStocks() {
        return stockRepository.getStocks();
    }

    private List<StockDto> getLiveStocks() {
        ArrayList<StockDto> list = new ArrayList<>();
        for (Map.Entry<Long, String> entry : StockMapping.getStocks().entrySet()) {
            list.add(StockDto.builder()
                    .id(entry.getKey())
                    .name(entry.getValue())
                    .price(generateRandomBigDecimal(DEFAULT_PRICE_MIN, DEFAULT_PRICE_MAX))
                    .build());
        }
        return list;
    }

    public static BigDecimal generateRandomBigDecimal(BigDecimal min, BigDecimal max) {
        var randomBigDecimal = DEFAULT_PRICE_MIN
                .add(BigDecimal.valueOf(Math.random())
                        .multiply(DEFAULT_PRICE_MAX.subtract(DEFAULT_PRICE_MIN)));
        return randomBigDecimal.setScale(2, RoundingMode.HALF_EVEN);
    }
}