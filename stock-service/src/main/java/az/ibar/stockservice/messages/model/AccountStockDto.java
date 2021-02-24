package az.ibar.stockservice.messages.model;

import az.ibar.stockservice.model.OrderType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountStockDto {

    private Long stockId;
    private String accountNumber;
    private Integer quantity;
    private OrderType orderType;
    private BigDecimal orderAmount;

}
