package javacode.wallet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "walletId")
@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    @Column(name = "wallet_uuid")
    private UUID walletId;

    @Column(name = "amount")
    private BigDecimal amount;

}
