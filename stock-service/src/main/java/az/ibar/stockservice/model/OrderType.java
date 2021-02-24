package az.ibar.stockservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {

    SELL("satis"), BUY("alis");
    private final String messageAz;
}