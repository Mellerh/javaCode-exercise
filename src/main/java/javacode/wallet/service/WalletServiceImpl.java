package javacode.wallet.service;

import javacode.wallet.dto.WalletCreateDto;
import javacode.wallet.dto.WalletMapper;
import javacode.wallet.dto.WalletResponseDto;
import javacode.wallet.dto.WalletUpdateDto;
import javacode.wallet.exceptions.BadRequestException;
import javacode.wallet.exceptions.NotFoundException;
import javacode.wallet.model.OperationType;
import javacode.wallet.model.Wallet;
import javacode.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public WalletResponseDto getWalletByUUID(String uuidString) {

        UUID uuid = UUID.fromString(uuidString);

        Wallet wallet = walletRepository.findById(uuid).orElseThrow(()
                -> new NotFoundException("Wallet с uuid " + uuid + " не найден."));

        return WalletMapper.toWalletResponseDto(wallet);
    }

    @Override
    public WalletResponseDto createNewWallet(WalletCreateDto walletRequestDto) {
        Wallet wallet = WalletMapper.toWalletModel(walletRequestDto);

        if (walletRepository.findById(wallet.getWalletId()).isPresent()) {
            throw new BadRequestException("Wallet с uuid " + wallet.getWalletId() + " уже существует.");
        }

        return WalletMapper.toWalletResponseDto(walletRepository.save(wallet));
    }

    @Override
    public WalletResponseDto updateWallet(String uuidString, WalletUpdateDto walletUpdateDto) {
        UUID uuid = UUID.fromString(uuidString);

        Wallet wallet = walletRepository.findById(uuid).orElseThrow(()
                -> new NotFoundException("Wallet с uuid " + uuid + " не найден."));


        if (walletUpdateDto.getOperationType() == OperationType.DEPOSIT) {
            wallet.setAmount(wallet.getAmount().add(walletUpdateDto.getAmount()));
            return WalletMapper.toWalletResponseDto(walletRepository.save(wallet));

        } else if (walletUpdateDto.getOperationType() == OperationType.WITHDRAW) {

            if (wallet.getAmount().compareTo(walletUpdateDto.getAmount()) < 1) {
                throw new BadRequestException("Сумма, которую вы пытаетесь снять больше той, что лежит на счёте. " +
                        "Текущая сумма на счёте " + wallet.getAmount());
            }

            wallet.setAmount(wallet.getAmount().subtract(walletUpdateDto.getAmount()));

            return WalletMapper.toWalletResponseDto(walletRepository.save(wallet));

        } else {
            throw new BadRequestException("Неккоректный метод по работе со счётом.");
        }

    }
}
