package id.ac.ui.cs.advprog.authenticationuserprofile.repository;

import id.ac.ui.cs.advprog.authenticationuserprofile.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setPassword("securePassword");
        user.setFullName("Test User");
        user.setPhoneNumber("1234567890");
        user.setAddress("123 Test Ave");
    }

    @Test
    void shouldSaveUser() {
        User savedUser = userRepository.save(user);
        assertThat(savedUser.getId()).isInstanceOf(UUID.class);
        assertThat(savedUser.getFullName()).isEqualTo("Test User");
    }

    @Test
    void shouldFindUserByEmail() {
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void shouldNotAllowDuplicateEmail() {
        userRepository.save(user);
        User duplicateUser = new User();
        duplicateUser.setId(UUID.randomUUID());
        duplicateUser.setEmail(user.getEmail());
        duplicateUser.setPassword("anotherPassword");
        duplicateUser.setFullName("Another User");
        duplicateUser.setPhoneNumber("0987654321");
        duplicateUser.setAddress("456 Another St");

        assertThatThrownBy(() -> userRepository.saveAndFlush(duplicateUser))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void shouldUpdateUser() {
        User savedUser = userRepository.save(user);
        savedUser.setFullName("Updated Test User");
        User updatedUser = userRepository.save(savedUser);

        assertThat(updatedUser.getFullName()).isEqualTo("Updated Test User");
    }

    @Test
    void shouldDeleteUser() {
        User savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getId());
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());

        assertThat(deletedUser).isNotPresent();
    }
}