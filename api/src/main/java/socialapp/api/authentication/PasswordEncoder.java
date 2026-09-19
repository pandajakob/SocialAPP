package socialapp.api.authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import socialapp.api.shared.domain_primitives.EncodedPassword;
import socialapp.api.shared.domain_primitives.Password;

@Service
public class PasswordEncoder {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public EncodedPassword encodePassword(Password password) {
        return new EncodedPassword(bCryptPasswordEncoder.encode(password.getValue()));
    }
}
