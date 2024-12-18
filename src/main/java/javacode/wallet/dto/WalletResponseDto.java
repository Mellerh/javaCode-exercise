package javacode.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class WalletResponseDto {

    private String walletId;
    private Long amount;

}
