package by.tami.userservice;

import by.tami.userservice.model.Role;
import by.tami.userservice.model.User;
import by.tami.userservice.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    @Disabled
    void insertAdmin() {
        var user = new User();
        user.setPhoneNumber("+77007007070");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRole(Role.ADMIN);

        userRepository.save(user);
    }
}
