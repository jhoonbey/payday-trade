package az.ibar.orderservice.client.model;

import az.ibar.orderservice.model.OrderType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountStock {

    private Long stockId;
    private String accountNumber;
    private Integer quantity;
    private OrderType orderType;
    private BigDecimal amount;
}