package az.ibar.orderservice.client;

import az.ibar.orderservice.client.model.AccountBalanceResponseDto;
import az.ibar.orderservice.client.model.AccountHoldDto;
import az.ibar.orderservice.client.model.AccountStock;
import az.ibar.orderservice.error.model.RestResponse;
import feign.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@FeignClient(
        name = "account-service",
        configuration = MsAccountClient.FeignConfiguration.class,
        primary = false)
public interface MsAccountClient {

    @GetMapping("/accounts/{accountNumber}/cash-balance")
    RestResponse<AccountBalanceResponseDto> getBalance(@PathVariable String accountNumber);

    @PutMapping("/accounts/{accountNumber}/hold-amount")
    void holdAmount(@PathVariable("accountNumber") @NotNull String accountNumber,
                    @RequestBody AccountHoldDto dto);

    @GetMapping("/accounts/{accountNumber}/stocks")
    List<AccountStock> getStocks(@PathVariable("accountNumber") @NotNull String accountNumber);

    class FeignConfiguration {

        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }
}
