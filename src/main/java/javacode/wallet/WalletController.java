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
@RequestMapping("api/v1")
@Validated
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/wallets/{WALLET_UUID}")
    public WalletResponseDto getWalletByUUID(@PathVariable String uuid) {
        return walletService.getWalletByUUID(uuid);
    }

    @PostMapping("/wallet")
    public WalletResponseDto createNewWallet(@Valid @RequestBody WalletCreateDto walletRequestDto) {
        return walletService.createNewWallet(walletRequestDto);
    }

    @PatchMapping("/wallets/{WALLET_UUID}")
    public WalletResponseDto updateWallet(@PathVariable String uuid,
                                          @Valid @RequestBody WalletUpdateDto walletUpdateDto) {
        return walletService.updateWallet(uuid, walletUpdateDto);
    }

}
