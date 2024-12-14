package javacode.wallet;

import jakarta.validation.Valid;
import javacode.wallet.dto.WalletCreateDto;
import javacode.wallet.dto.WalletResponseDto;
import javacode.wallet.dto.WalletUpdateDto;
import javacode.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@Validated
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/wallet")
    public WalletResponseDto createNewWallet(@Valid @RequestBody WalletCreateDto walletRequestDto) {
        return walletService.createNewWallet(walletRequestDto);
    }

    @GetMapping("/wallets/{WALLET_UUID}")
    public WalletResponseDto getWalletByUUID(@PathVariable String WALLET_UUID) {
        return walletService.getWalletByUUID(WALLET_UUID);
    }


    @PatchMapping("/wallets/{WALLET_UUID}")
    public WalletResponseDto updateWallet(@PathVariable String WALLET_UUID,
                                          @Valid @RequestBody WalletUpdateDto walletUpdateDto) {
        return walletService.updateWallet(WALLET_UUID, walletUpdateDto);
    }

}


//{
//        "walletId": "550e8400-e29b-41d4-a716-446655440000",
//        "operationType": "DEPOSIT",
//        "amount": 1000
//}