package az.ibar.stockservice.messages.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage {

    private Long customerId;
    private String message;

    public static OrderMessage of(Long customerId, String message) {
        return new OrderMessage(customerId, message);
    }
}
