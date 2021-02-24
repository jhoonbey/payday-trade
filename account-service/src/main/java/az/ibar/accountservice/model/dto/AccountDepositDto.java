package az.ibar.accountservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDepositDto {
    private BigDecimal amount;
}