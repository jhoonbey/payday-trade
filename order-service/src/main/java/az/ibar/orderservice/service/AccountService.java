package az.ibar.orderservice.service;

import az.ibar.orderservice.client.model.AccountStock;
import az.ibar.orderservice.model.dto.OrderDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    void holdAccountAmount(OrderDto orderDto);

    BigDecimal getBalance(String accountNumber);

    List<AccountStock> getStocks(String accountNumber);
}
