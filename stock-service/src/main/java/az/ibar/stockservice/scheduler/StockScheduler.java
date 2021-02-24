package az.ibar.stockservice.scheduler;

import az.ibar.stockservice.service.OrderService;
import az.ibar.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class StockScheduler {

    private final StockService stockService;
    private final OrderService orderService;

    @Scheduled(fixedDelay = 15000)
    public void scheduleFixedDelayTask() {
        stockService.updateStock();
        orderService.completeOrder();
    }
}