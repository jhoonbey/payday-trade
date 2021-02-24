package az.ibar.accountservice.service.impl;

import az.ibar.accountservice.error.AccountNotFoundException;
import az.ibar.accountservice.error.InsufficientBalanceException;
import az.ibar.accountservice.mapper.AccountMapper;
import az.ibar.accountservice.model.dto.AccountBalanceResponseDto;
import az.ibar.accountservice.model.dto.AccountCreateDto;
import az.ibar.accountservice.model.dto.AccountDepositDto;
import az.ibar.accountservice.model.dto.AccountHoldDto;
import az.ibar.accountservice.model.dto.AccountResponseDto;
import az.ibar.accountservice.model.dto.AccountStockDto;
import az.ibar.accountservice.model.entity.AccountEntity;
import az.ibar.accountservice.model.entity.StockEntity;
import az.ibar.accountservice.repository.AccountRepository;
import az.ibar.accountservice.service.AccountService;
import az.ibar.accountservice.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static az.ibar.accountservice.util.AccountUtil.generateNumericString;
import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final StockService stockService;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponseDto createAccount(AccountCreateDto dto) {
        var account = AccountEntity.builder()
                .customerId(dto.getCustomerId())
                .accountNumber(generateNumericString(20))
                .build();

        var createdAccount = accountRepository.save(account);

        return accountMapper.toAccountResponseDto(createdAccount);
    }

    @Override
    @Transactional
    public void updateAccount(AccountStockDto dto) {
        accountRepository.findByAccountNumber(dto.getAccountNumber())
                .ifPresent(account -> updateAccount(account, dto));
    }

    @Override
    public void holdAmount(String accountNumber, AccountHoldDto dto) {
        AccountEntity account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(AccountNotFoundException::new);

        account.setHoldAmount(Optional.ofNullable(account.getHoldAmount())
                .orElse(ZERO)
                .add(dto.getHoldAmount()));

        accountRepository.save(account);
    }

    @Override
    public AccountBalanceResponseDto getBalance(String accountNumber) {
        BigDecimal balance = accountRepository.findByAccountNumber(accountNumber)
                .map(account -> Optional.ofNullable(account.getBalance())
                        .orElse(ZERO)
                        .subtract(Optional.ofNullable(account.getHoldAmount())
                                .orElse(ZERO)))
                .orElseThrow(AccountNotFoundException::new);

        return AccountBalanceResponseDto.of(balance);
    }

    @Override
    public List<AccountStockDto> getStocks(String accountNumber) {
        Optional<AccountEntity> byAccountNumber = accountRepository.findByAccountNumber(accountNumber);
        log.info("byAccountNumber: {}", byAccountNumber);
        return byAccountNumber
                .map(AccountEntity::getAccountStockEntities)
                .map(accountMapper::toAccountStockDtoList)
                .orElse(Collections.emptyList());
    }

    @Override
    public BigDecimal getTotalBalance(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(account -> {
                    var cashBalance = account.getBalance().subtract(account.getHoldAmount());
                    var stockBalance = getAccountLiveBalance(account);
                    return cashBalance.add(stockBalance);
                })
                .orElse(ZERO);
    }

    @Override
    public void depositAccount(String accountNumber, AccountDepositDto dto) {
        var account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(AccountNotFoundException::new);

        account.setBalance(Optional.ofNullable(account.getBalance())
                .orElse(ZERO)
                .add(dto.getAmount()));

        accountRepository.save(account);
    }

    private BigDecimal getAccountLiveBalance(AccountEntity account) {
        List<StockEntity> stocks = stockService.getStocks();

        return account.getAccountStockEntities().stream()
                .map(accountStock -> new BigDecimal(accountStock.getQuantity())
                        .multiply(getStockPrice(stocks, accountStock.getStockId())))
                .reduce(BigDecimal::add)
                .orElse(ZERO);
    }

    private BigDecimal getStockPrice(List<StockEntity> stocks, Long stockId) {
        return stocks.stream()
                .filter(stock -> stock.getId().equals(stockId))
                .map(StockEntity::getPrice)
                .findFirst()
                .orElse(ZERO);
    }

    private void updateAccount(AccountEntity account, AccountStockDto dto) {
        log.info("account:{}", account);
        log.info("dto:{}", dto);
        var orderAmount = dto.getOrderAmount().multiply(new BigDecimal(dto.getQuantity()));

        if (CollectionUtils.isEmpty(account.getAccountStockEntities())) {
            var accountStock = accountMapper.toAccountStockEntity(dto);
            accountStock.setAccount(account);
            account.addAccountStock(accountStock);
        }

        switch (dto.getOrderType()) {
            case BUY:
                account.setBalance(account.getBalance().subtract(orderAmount));
                account.setHoldAmount(account.getHoldAmount().subtract(orderAmount));

                if (!CollectionUtils.isEmpty(account.getAccountStockEntities())) {
                    account.getAccountStockEntities().stream()
                            .filter(item -> item.getStockId().equals(dto.getStockId()))
                            .forEach(stock -> stock.setQuantity(stock.getQuantity() + dto.getQuantity()));
                }

                accountRepository.save(account);
                break;

            case SELL:
                account.setBalance(account.getBalance().add(orderAmount));

                if (!Objects.isNull(account.getAccountStockEntities())) {
                    account.getAccountStockEntities().stream()
                            .filter(item -> item.getStockId().equals(dto.getStockId()))
                            .forEach(stock -> stock.setQuantity(stock.getQuantity() - dto.getQuantity()));
                }

                accountRepository.save(account);
                break;

            default:
                log.warn("Incorrect orderType: {}", dto.getOrderType());
        }
    }
}