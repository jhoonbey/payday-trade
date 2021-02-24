package az.ibar.accountservice.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "account_stock")
@NoArgsConstructor
@AllArgsConstructor
public class AccountStockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_stock_seq")
    @SequenceGenerator(name = "account_stock_seq", sequenceName = "account_stock_seq", allocationSize = 1)
    private Long id;

    private Long stockId;
    private Integer quantity;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private AccountEntity account;
}
