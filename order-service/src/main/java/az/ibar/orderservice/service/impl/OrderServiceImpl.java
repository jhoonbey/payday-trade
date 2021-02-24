package az.ibar.orderservice.service.impl;

import az.ibar.orderservice.client.model.AccountStock;
import az.ibar.orderservice.error.InsufficientBalanceException;
import az.ibar.orderservice.error.OrderNotFoundException;
import az.ibar.orderservice.mapper.OrderMapper;
import az.ibar.orderservice.messages.MessageSender;
import az.ibar.orderservice.messages.model.Message;
import az.ibar.orderservice.model.OrderState;
import az.ibar.orderservice.model.OrderType;
import az.ibar.orderservice.model.dto.OrderCreateDto;
import az.ibar.orderservice.model.entity.OrderEntity;
import az.ibar.orderservice.repository.OrderRepository;
import az.ibar.orderservice.service.AccountService;
import az.ibar.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static az.ibar.orderservice.util.OrderUtil.ORDER_CREATE;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final AccountService accountService;
    private final MessageSender<OrderCreateDto> messageSender;
    private final OrderMapper orderMapper;

    @Override
    public void createOrder(OrderCreateDto dto) {
        createValidation(dto);

        OrderEntity entity = orderMapper.toOrderEntity(dto);
        OrderEntity createdEntity = orderRepository.save(entity);

        accountService.holdAccountAmount(orderMapper.toOrderDto(dto));

        Message<OrderCreateDto> message = new Message<>(createdEntity.getId(), ORDER_CREATE, dto);
        messageSender.send(message);
    }

    @Override
    @Transactional
    public void completeOrder(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        order.setOrderState(OrderState.COMPLETED);

        orderRepository.save(order);
    }

    private void createValidation(OrderCreateDto dto) {

        if (dto.getOrderType() == OrderType.BUY) {

            BigDecimal accountBalance = accountService.getBalance(dto.getAccountNumber());
            if (dto.getTargetPrice().compareTo(accountBalance) > 0)
                throw new InsufficientBalanceException();

        } else if (dto.getOrderType() == OrderType.SELL) {
            Optional<Integer> first = accountService.getStocks(dto.getAccountNumber()).stream()
                    .filter(accountStock -> accountStock.getStockId().equals(dto.getStockId()))
                    .map(AccountStock::getQuantity)
                    .findFirst();
            first
                    .filter(stockQuantity -> stockQuantity > dto.getQuantity())
                    .orElseThrow(() -> new InsufficientBalanceException("You do not have enough stock in your account balance"));
        }
    }
}