package javacode.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
public class WalletResponseDto {

    private String walletId;
    private BigDecimal amount;

}
