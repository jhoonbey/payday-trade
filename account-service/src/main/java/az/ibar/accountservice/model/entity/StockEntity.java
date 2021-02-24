package az.ibar.accountservice.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class StockEntity implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private Long id;
    private BigDecimal price;
    private String name;
}