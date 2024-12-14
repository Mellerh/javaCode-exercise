import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javacode.JavaCodeApp;
import javacode.wallet.dto.WalletCreateDto;
import javacode.wallet.dto.WalletMapper;
import javacode.wallet.dto.WalletResponseDto;
import javacode.wallet.dto.WalletUpdateDto;
import javacode.wallet.model.OperationType;
import javacode.wallet.model.Wallet;
import javacode.wallet.repository.WalletRepository;
import javacode.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureDataJpa
@AutoConfigureCache
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest(classes = JavaCodeApp.class)
public class WalletApplicationTest {

    private static Validator validator;
    private final WalletService walletService;
    private final WalletRepository repository;
    private final WalletMapper walletMapper;
    private Wallet wallet;
    private WalletCreateDto createDtoCorrect;
    private WalletCreateDto createDtoUnCorrect;
    private WalletUpdateDto updateDto;


    @BeforeAll
    @DisplayName("Общий валидатор")
    static void setUpValidator() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }


    @BeforeEach
    @DisplayName("Удаляем wallet из базы")
    void deleteWallet() {
        repository.deleteById(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
    }

    @Test
    @DisplayName("Тестируем валидацию DTO")
    public void validateWalletDto() {

        initCorrectDto();
        Set<ConstraintViolation<WalletCreateDto>> violationsCorrect = validator.validate(createDtoCorrect);
        Assertions.assertTrue(violationsCorrect.isEmpty(), "Ожидается, что валидация пройдёт успешно");

        initUnCorrectDto();
        Set<ConstraintViolation<WalletCreateDto>> violationsUnCorrect = validator.validate(createDtoUnCorrect);
        Assertions.assertEquals(1, violationsUnCorrect.size(),
                "Ожидается, что валидация не пройдёт успешно");


    }

    @Test
    @DisplayName("Проверяем работу сервиса и репозитория")
    public void validateServiceAndRepository() {

        initCorrectDto();

        walletService.createNewWallet(createDtoCorrect);
        Optional<WalletResponseDto> responseOptional = Optional.ofNullable(
                walletService.getWalletByUUID(createDtoCorrect.getWalletId()));

        Assertions.assertNotNull(responseOptional, "walletService работает неккоректно");

        assertThat(responseOptional).hasValueSatisfying(
                wallet -> assertThat(wallet)
                        .hasFieldOrPropertyWithValue("walletId", createDtoCorrect.getWalletId())
                        .hasFieldOrPropertyWithValue("amount", createDtoCorrect.getAmount()));
    }

    @Test
    @DisplayName("Начисляем и снимаем средства")
    public void testUpdateWallet() {

        initCorrectDto();
        walletService.createNewWallet(createDtoCorrect);

        // зачисляем средства
        initUpdateDtoDeposit();
        walletService.updateWallet("550e8400-e29b-41d4-a716-446655440000", updateDto);
        Optional<WalletResponseDto> responseOptional = Optional.ofNullable(
                walletService.getWalletByUUID(createDtoCorrect.getWalletId()));

        assertThat(responseOptional).hasValueSatisfying(
                wallet -> assertThat(wallet)
                        .hasFieldOrPropertyWithValue("amount", 1500L));


        // снимаем средства
        initUpdateDtoWithdraw();
        walletService.updateWallet("550e8400-e29b-41d4-a716-446655440000", updateDto);
        Optional<WalletResponseDto> responseOptionalUpdatedWithdraw = Optional.ofNullable(
                walletService.getWalletByUUID(createDtoCorrect.getWalletId()));

        assertThat(responseOptionalUpdatedWithdraw).hasValueSatisfying(
                wallet -> assertThat(wallet)
                        .hasFieldOrPropertyWithValue("amount", 0L));


    }

//    @Test
//    @DisplayName("Создаём пользователя с такиж же ID и пытаемся снять сумму больше, чем есть на счёте")


    private void initCorrectDto() {
        createDtoCorrect = WalletCreateDto.builder()
                .walletId("550e8400-e29b-41d4-a716-446655440000")
                .amount(1000L)
                .build();
    }

    private void initUnCorrectDto() {
        createDtoUnCorrect = WalletCreateDto.builder()
                .walletId("550e8400-e29b")
                .amount(1000L)
                .build();
    }

    private void initUpdateDtoDeposit() {
        updateDto = WalletUpdateDto.builder()
                .walletId("550e8400-e29b-41d4-a716-446655440000")
                .operationType(OperationType.DEPOSIT)
                .amount(500L)
                .build();
    }

    private void initUpdateDtoWithdraw() {
        updateDto = WalletUpdateDto.builder()
                .walletId("550e8400-e29b-41d4-a716-446655440000")
                .operationType(OperationType.WITHDRAW)
                .amount(1500L)
                .build();
    }


}
