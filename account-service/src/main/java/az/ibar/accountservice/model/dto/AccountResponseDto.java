package az.ibar.accountservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponseDto {
    private Long id;
    private Long customerId;
    private String accountNumber;
    private BigDecimal balance;
}