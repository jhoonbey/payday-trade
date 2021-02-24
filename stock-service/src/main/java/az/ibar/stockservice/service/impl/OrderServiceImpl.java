package az.ibar.stockservice.service.impl;

import az.ibar.stockservice.error.StockNotFoundException;
import az.ibar.stockservice.messages.MessageSender;
import az.ibar.stockservice.messages.model.AccountStockDto;
import az.ibar.stockservice.messages.model.Message;
import az.ibar.stockservice.messages.model.OrderMessage;
import az.ibar.stockservice.model.OrderDto;
import az.ibar.stockservice.model.StockDto;
import az.ibar.stockservice.repository.OrderRepository;
import az.ibar.stockservice.service.OrderService;
import az.ibar.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

import static az.ibar.stockservice.util.StockUtil.ACCOUNT_STOCK_UPDATE_KEY;
import static az.ibar.stockservice.util.StockUtil.ORDER_COMPLETE_KEY;
import static az.ibar.stockservice.util.StockUtil.ORDER_COMPLETE_NOTIFICATION_KEY;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final StockService stockService;
    private final MessageSender<Long> messageSender;
    private final MessageSender<AccountStockDto> accountMessageSender;
    private final MessageSender<OrderMessage> notificationSender;

    @Override
    public void completeOrder() {
        var orders = orderRepository.getOrders();
        var stocks = stockService.getStocks();
        orders.forEach(order -> completeOrder(order, getStockPrice(stocks, order)));
    }

    private void completeOrder(OrderDto order, StockDto stockDto) {
        switch (order.getOrderType()) {
            case BUY:
                buyStock(order, stockDto);
                break;
            case SELL:
                sellStock(order, stockDto);
                break;
            default:
                log.info("Wrong order type!");
        }
    }

    private void sellStock(OrderDto order, StockDto stockDto) {
        if (stockDto.getPrice().compareTo(order.getTargetPrice()) >= 0) {

            orderRepository.deleteOrder(order);

            messageSender.send(new Message<>(order.getOrderId(), ORDER_COMPLETE_KEY));

            accountMessageSender.send(buildAccountStockDto(order));

            notificationSender.send(getOrderNotificationMessage(order, stockDto));
        }
    }

    private void buyStock(OrderDto order, StockDto stockDto) {
        if (stockDto.getPrice().compareTo(order.getTargetPrice()) <= 0) {
            orderRepository.deleteOrder(order);

            accountMessageSender.send(buildAccountStockDto(order));

            messageSender.send(new Message<>(order.getOrderId(), ORDER_COMPLETE_KEY));

            notificationSender.send(getOrderNotificationMessage(order, stockDto));
        }
    }

    private Message<AccountStockDto> buildAccountStockDto(OrderDto order) {
        var accountStockDto = AccountStockDto.builder()
                .accountNumber(order.getAccountNumber())
                .orderAmount(order.getTargetPrice())
                .stockId(order.getStockId())
                .orderType(order.getOrderType())
                .quantity(order.getQuantity())
                .build();
        return new Message<>(accountStockDto, ACCOUNT_STOCK_UPDATE_KEY);
    }

    private StockDto getStockPrice(List<StockDto> stocks, OrderDto order) {
        return stocks.stream()
                .filter(stock -> stock.getId().equals(order.getStockId()))
                .findFirst()
                .orElseThrow(StockNotFoundException::new);
    }

    private Message<OrderMessage> getOrderNotificationMessage(OrderDto order, StockDto stockDto) {
        String pattern = "Order completed!" + "\n" +
                "Quantity :   {0}" + "\n" +
                "Stock name:  {1}" + "\n" +
                "Price:       {2}" + "\n" +
                "Order type:  {3}";
        String textMessage = MessageFormat.format(pattern,
                order.getQuantity(), stockDto.getName(), order.getTargetPrice(), order.getOrderType().getMessageAz());

        var orderMessage = OrderMessage.of(order.getCustomerId(), textMessage);

        return new Message<>(orderMessage, ORDER_COMPLETE_NOTIFICATION_KEY);
    }
}