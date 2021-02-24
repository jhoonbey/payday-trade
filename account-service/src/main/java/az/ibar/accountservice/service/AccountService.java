package az.ibar.accountservice.service;

import az.ibar.accountservice.model.dto.AccountBalanceResponseDto;
import az.ibar.accountservice.model.dto.AccountDepositDto;
import az.ibar.accountservice.model.dto.AccountHoldDto;
import az.ibar.accountservice.model.dto.AccountCreateDto;
import az.ibar.accountservice.model.dto.AccountResponseDto;
import az.ibar.accountservice.model.dto.AccountStockDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResponseDto createAccount(AccountCreateDto requestDto);

    void updateAccount(AccountStockDto dto);

    void holdAmount(String accountNumber, AccountHoldDto dto);

    AccountBalanceResponseDto getBalance(String accountNumber);

    List<AccountStockDto> getStocks(String accountNumber);

    BigDecimal getTotalBalance(String accountNumber);

    void depositAccount(String accountNumber, AccountDepositDto dto);
}
