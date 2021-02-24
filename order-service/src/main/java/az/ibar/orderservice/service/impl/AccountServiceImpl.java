package az.ibar.orderservice.service.impl;

import az.ibar.orderservice.client.MsAccountClient;
import az.ibar.orderservice.client.model.AccountBalanceResponseDto;
import az.ibar.orderservice.client.model.AccountHoldDto;
import az.ibar.orderservice.client.model.AccountStock;
import az.ibar.orderservice.error.model.RestResponse;
import az.ibar.orderservice.model.OrderType;
import az.ibar.orderservice.model.dto.OrderDto;
import az.ibar.orderservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final MsAccountClient msAccountClient;

    @Override
    public void holdAccountAmount(OrderDto dto) {
        if (dto.getOrderType() == OrderType.BUY) {
            var holdAmount = dto.getTargetPrice().multiply(new BigDecimal(dto.getQuantity()));
            msAccountClient.holdAmount(dto.getAccountNumber(), AccountHoldDto.of(holdAmount));
        }
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {
        return Optional.ofNullable(msAccountClient.getBalance(accountNumber))
                .map(RestResponse::getData)
                .map(AccountBalanceResponseDto::getBalance)
                .orElse(ZERO);
    }

    @Override
    public List<AccountStock> getStocks(String accountNumber) {
        return msAccountClient.getStocks(accountNumber);
    }
}