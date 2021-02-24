package az.ibar.accountservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq", sequenceName = "account_seq", allocationSize = 1)
    private Long id;

    private String accountNumber;
    private Long customerId;

    private BigDecimal balance;
    private BigDecimal holdAmount;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "account", cascade = {CascadeType.ALL})
    private List<AccountStockEntity> accountStockEntities = new ArrayList<>();

    public void addAccountStock(AccountStockEntity accountStockEntity) {
        accountStockEntities.add(accountStockEntity);
        accountStockEntity.setAccount(this);
    }
}
