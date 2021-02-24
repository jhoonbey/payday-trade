package az.ibar.stockservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockDto implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private Long id;
    private BigDecimal price;
    private String name;
}

