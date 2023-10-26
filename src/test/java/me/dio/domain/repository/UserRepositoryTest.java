package me.dio.domain.repository;

import me.dio.domain.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for User Repository")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Save persists user when Successful")
    void save_PersistUser_WhenSuccessful() {
        User userToBeSaved = createUser();
        User userSaved = this.userRepository.save(userToBeSaved);

        compareUserWithUserInDataBase(userToBeSaved, userSaved);
    }

    @Test
    @DisplayName("Save throw DataIntegrityViolationException when Account Number Already Exists")
    void save_ThrowDataIntegrityViolationException_WhenAccountNumberAlreadyExists() {
        userRepository.save(createUser());

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> this.userRepository.save(createUser()));
    }

    @Test
    @DisplayName("Find By Id returns user when Successful")
    void findById_ReturnUser_WhenSuccessful() {
        User userToBeSaved = createUser();
        User userSaved = this.userRepository.save(userToBeSaved);

        Long id = userSaved.getId();
        User userFindById = this.userRepository.findById(id).get();

        compareUserWithUserInDataBase(userSaved, userFindById);
    }

    private User createUser() {
        return new User.UserBuilder()
                .name("Diego")
                .addAccountNumber("00000000-00")
                .addAccountAgency("0000")
                .addAccountBalance(new BigDecimal("1324.64"))
                .addAccountLimit(new BigDecimal("1000.00"))
                .addCardNumber("xxxx xxxx xxxx 1111")
                .addCardLimit(new BigDecimal("2000.00"))
                .addFeature("https://digitalinnovationone.github.io/santander-dev-week-2023-api/icons/pix.sv",
                        "PIX")
                .addNews("https://digitalinnovationone.github.io/santander-dev-week-2023-api/icons/insurance.svg",
                        "Santander Seguro Casa, seu faz-tudo. Mais de 50 serviços pra você. Confira!")
                .build();
    }

    private void compareUserWithUserInDataBase(User userToBeSaved, User userSaved) {
        assertThat(userSaved).isNotNull();
        assertThat(userSaved.getId()).isNotNull();
        assertThat(userSaved.getName()).isEqualTo(userToBeSaved.getName());

        assertThat(userSaved.getAccount().getId()).isNotNull();
        assertThat(userSaved.getAccount().getNumber()).isEqualTo(userToBeSaved.getAccount().getNumber());
        assertThat(userSaved.getAccount().getAgency()).isEqualTo(userToBeSaved.getAccount().getAgency());
        assertThat(userSaved.getAccount().getBalance()).isEqualTo(userToBeSaved.getAccount().getBalance());
        assertThat(userSaved.getAccount().getLimit()).isEqualTo(userToBeSaved.getAccount().getLimit());

        assertThat(userSaved.getCard().getId()).isNotNull();
        assertThat(userSaved.getCard().getNumber()).isEqualTo(userToBeSaved.getCard().getNumber());
        assertThat(userSaved.getCard().getLimit()).isEqualTo(userToBeSaved.getCard().getLimit());

        assertThat(userSaved.getFeatures().size()).isOne();
        assertThat(userSaved.getFeatures().get(0).getIcon()).isEqualTo(userToBeSaved.getFeatures().get(0).getIcon());
        assertThat(userSaved.getFeatures().get(0).getDescription()).isEqualTo(userToBeSaved.getFeatures().get(0).getDescription());

        assertThat(userSaved.getNews().size()).isOne();
        assertThat(userSaved.getNews().get(0).getIcon()).isEqualTo(userToBeSaved.getNews().get(0).getIcon());
        assertThat(userSaved.getNews().get(0).getDescription()).isEqualTo(userToBeSaved.getNews().get(0).getDescription());
    }
}