package az.ibar.accountservice.controller;

import az.ibar.accountservice.error.model.RestResponse;
import az.ibar.accountservice.model.dto.AccountBalanceResponseDto;
import az.ibar.accountservice.model.dto.AccountCreateDto;
import az.ibar.accountservice.model.dto.AccountDepositDto;
import az.ibar.accountservice.model.dto.AccountHoldDto;
import az.ibar.accountservice.model.dto.AccountResponseDto;
import az.ibar.accountservice.model.dto.AccountStockDto;
import az.ibar.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountResponseDto createAccount(@RequestBody AccountCreateDto dto) {
        return accountService.createAccount(dto);
    }

    @PutMapping("/{accountNumber}/hold-amount")
    public void holdAmount(@PathVariable("accountNumber") @NotNull String accountNumber,
                           @RequestBody AccountHoldDto dto) {
        accountService.holdAmount(accountNumber, dto);
    }

    @GetMapping("/{accountNumber}/cash-balance")
    public RestResponse<AccountBalanceResponseDto> getAccountCashBalance(@PathVariable("accountNumber") String accountNumber) {
        return new RestResponse<>(accountService.getBalance(accountNumber));
    }

    @GetMapping("/{accountNumber}/stocks")
    public List<AccountStockDto> getStocks(@PathVariable("accountNumber") String accountNumber) {
        return accountService.getStocks(accountNumber);
    }

    @GetMapping("/{accountNumber}/total-balance")
    public RestResponse<BigDecimal> getTotalBalance(@PathVariable("accountNumber") String accountNumber) {
        return new RestResponse<>(accountService.getTotalBalance(accountNumber));
    }


    @PutMapping("/{accountNumber}/deposit")
    public void depositAccount(@PathVariable("accountNumber") @NotNull String accountNumber,
                               @RequestBody AccountDepositDto dto) {
        accountService.depositAccount(accountNumber, dto);
    }

}
