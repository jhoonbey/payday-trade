package az.ibar.accountservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountStockDto {
    private Long stockId;
    private String accountNumber;
    private Integer quantity;
    private OrderType orderType;
    private BigDecimal orderAmount;
}
