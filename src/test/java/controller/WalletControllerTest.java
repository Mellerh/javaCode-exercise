package controller;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javacode.wallet.dto.WalletCreateDto;
import javacode.wallet.repository.WalletRepository;
import javacode.wallet.service.WalletService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;


public class WalletControllerTest {

    private static Validator validator;

    private WalletService walletService;
    private WalletRepository repository;
    private WalletCreateDto wallet;

    @BeforeAll
    @DisplayName("Общий валидатор")
    static void setUpValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void validateWalletWithCorrectDate() {
        defaultWalletSettings();

        Set<ConstraintViolation<WalletCreateDto>> violations = validator.validate(wallet);
        Assertions.assertTrue(violations.isEmpty(), "Ожидается, что валидация пройдёт успешно");

    }


    private void defaultWalletSettings() {
        wallet = WalletCreateDto.builder()
                .walletId("550e8400-e29b-41d4-a716-446655440000")
                .amount(1000L)
                .build();
    }

}
