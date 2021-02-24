package az.ibar.stockservice.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderDto implements Serializable {
    private Long orderId;
    private Long customerId;
    private Long stockId;
    private String accountNumber;
    private BigDecimal targetPrice;
    private OrderType orderType;
    private Integer quantity;
}