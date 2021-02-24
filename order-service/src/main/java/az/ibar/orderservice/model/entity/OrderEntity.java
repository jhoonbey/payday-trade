package az.ibar.orderservice.model.entity;

import az.ibar.orderservice.model.OrderState;
import az.ibar.orderservice.model.OrderType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "order_table")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    private Long id;

    @Column
    private Long stockId;

    private String accountNumber;

    private Integer quantity;

    @Enumerated(EnumType.ORDINAL)
    private OrderType orderType;

    @Enumerated(EnumType.ORDINAL)
    private OrderState orderState;

    private BigDecimal targetPrice;
}