package socialapp.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    Configuration configuration;

    public AdminInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        Email email = configuration.getAdminEmail();
        Password password = new Password(bCryptPasswordEncoder.encode(configuration.getAdminPassword()));

        if (userRepository.findByEmail(email).isEmpty()) {
            User admin = new User();
            admin.setEmail(email);
            admin.setFirstName("ADMIN");
            admin.setLastName("ADMIN");
            admin.setPassword(password);
            admin.setPhoneNumber(new PhoneNumber("0000000000"));
            admin.setAge(35);
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
        }
    }
}