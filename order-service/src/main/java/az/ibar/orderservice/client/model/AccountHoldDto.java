package az.ibar.orderservice.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHoldDto {

    private BigDecimal holdAmount;

    public static AccountHoldDto of(BigDecimal holdAmount) {
        return new AccountHoldDto(holdAmount);
    }
}
