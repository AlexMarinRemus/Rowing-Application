package nl.tudelft.sem.template.authentication.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
// activate profiles to have spring use mocks during auto-injection of certain beans.
@ActiveProfiles({"test", "mockPasswordEncoder"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RegistrationServiceTests {

    @Autowired
    private transient RegistrationService registrationService;

    @Autowired
    private transient PasswordHashingService mockPasswordEncoder;

    @Autowired
    private transient UserRepository userRepository;

    @Test
    public void createUser_withValidData_worksCorrectly() throws Exception {
        // Arrange
        final NetId testUser = new NetId("SomeUser");
        final Password testPassword = new Password("password123");
        final HashedPassword testHashedPassword = new HashedPassword("hashedTestPassword");
        when(mockPasswordEncoder.hash(testPassword)).thenReturn(testHashedPassword);

        // Act
        registrationService.registerUser(testUser, testPassword);

        // Assert
        AppUser savedUser = userRepository.findByNetId(testUser).orElseThrow();

        assertThat(savedUser.getNetId()).isEqualTo(testUser);
        assertThat(savedUser.getPassword()).isEqualTo(testHashedPassword);
    }

    @Test
    public void createUser_withInvalidNetId_throwsException() throws Exception {
        final NetId testUser = new NetId("SuperGigaMegaExtremelyVeryVeryVeryLOngId");
        final Password testPassword = new Password("password123");
        final HashedPassword testHashedPassword = new HashedPassword("hashedTestPassword");
        when(mockPasswordEncoder.hash(testPassword)).thenReturn(testHashedPassword);

        assertThrows(NetIdIsInvalidException.class, () -> {
            registrationService.registerUser(testUser, testPassword);
        });
    }

    @Test
    public void createUser_withExistingUser_throwsException() {
        // Arrange
        final NetId testUser = new NetId("SomeUser");
        final HashedPassword existingTestPassword = new HashedPassword("password123");
        final Password newTestPassword = new Password("password456");

        AppUser existingAppUser = new AppUser(testUser, existingTestPassword);
        userRepository.save(existingAppUser);

        // Act
        ThrowingCallable action = () -> registrationService.registerUser(testUser, newTestPassword);

        // Assert
        assertThatExceptionOfType(Exception.class)
                .isThrownBy(action);

        AppUser savedUser = userRepository.findByNetId(testUser).orElseThrow();

        assertThat(savedUser.getNetId()).isEqualTo(testUser);
        assertThat(savedUser.getPassword()).isEqualTo(existingTestPassword);
    }

    @Test
    public void createUser_withNullNetId_throwsException() {
        final NetId testUser = null;
        final Password testPassword = new Password("password123");
        final HashedPassword testHashedPassword = new HashedPassword("hashedTestPassword");
        when(mockPasswordEncoder.hash(testPassword)).thenReturn(testHashedPassword);

        ThrowingCallable action = () -> registrationService.registerUser(testUser, testPassword);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(action);
    }

    @Test
    public void createUser_withNullPassword_throwsException() {
        final NetId testUser = new NetId("SomeUser");
        final Password testPassword = null;
        final HashedPassword testHashedPassword = new HashedPassword("hashedTestPassword");
        when(mockPasswordEncoder.hash(testPassword)).thenReturn(testHashedPassword);

        ThrowingCallable action = () -> registrationService.registerUser(testUser, testPassword);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(action);
    }

    @Test
    public void createUser_withEmptyNetId_throwsException() {
        final NetId testUser = new NetId("");
        final Password testPassword = new Password("password123");
        final HashedPassword testHashedPassword = new HashedPassword("hashedTestPassword");
        when(mockPasswordEncoder.hash(testPassword)).thenReturn(testHashedPassword);

        ThrowingCallable action = () -> registrationService.registerUser(testUser, testPassword);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(action);
    }

    @Test
    public void createUser_withEmptyPassword_throwsException() {
        final NetId testUser = new NetId("SomeUser");
        final Password testPassword = new Password("");
        final HashedPassword testHashedPassword = new HashedPassword("hashedTestPassword");
        when(mockPasswordEncoder.hash(testPassword)).thenReturn(testHashedPassword);

        ThrowingCallable action = () -> registrationService.registerUser(testUser, testPassword);

        assertThatExceptionOfType(Exception.class)
                .isThrownBy(action);
    }
}
