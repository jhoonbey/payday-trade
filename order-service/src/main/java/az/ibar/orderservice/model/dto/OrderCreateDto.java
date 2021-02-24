package az.ibar.orderservice.model.dto;

import az.ibar.orderservice.model.OrderType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderCreateDto implements Serializable {
    private Long customerId;
    private Long stockId;
    private String accountNumber;
    private Integer quantity;
    private BigDecimal targetPrice;
    private OrderType orderType;
}