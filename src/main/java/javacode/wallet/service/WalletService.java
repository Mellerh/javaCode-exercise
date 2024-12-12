package javacode.wallet.service;

import javacode.wallet.dto.WalletCreateDto;
import javacode.wallet.dto.WalletResponseDto;
import javacode.wallet.dto.WalletUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface WalletService {

    WalletResponseDto getWalletByUUID(String uuid);

    WalletResponseDto createNewWallet(WalletCreateDto walletRequestDto);

    WalletResponseDto updateWallet(String uuid, WalletUpdateDto walletUpdateDto);

}
