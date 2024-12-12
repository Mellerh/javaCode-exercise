package javacode.wallet.dto;

import javacode.wallet.model.Wallet;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WalletMapper {

    public static Wallet toWalletModel(WalletCreateDto dto) {
        return Wallet.builder()
                .walletId(UUID.fromString(dto.getWalletId()))
                .amount(dto.getAmount())
                .build();
    }

    public static Wallet toWalletModel(WalletUpdateDto dto) {
        return Wallet.builder()
                .walletId(UUID.fromString(dto.getWalletId()))
                .amount(dto.getAmount())
                .build();
    }

    public static WalletResponseDto toWalletResponseDto(Wallet wallet) {
        return WalletResponseDto.builder()
                .walletId(String.valueOf(wallet.getWalletId()))
                .amount(wallet.getAmount())
                .build();

    }

}
