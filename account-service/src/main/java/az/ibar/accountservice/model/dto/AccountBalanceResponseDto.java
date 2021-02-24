package az.ibar.accountservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class AccountBalanceResponseDto {

    private final BigDecimal balance;

    public static AccountBalanceResponseDto of(BigDecimal balance) {
        return new AccountBalanceResponseDto(balance);
    }
}
