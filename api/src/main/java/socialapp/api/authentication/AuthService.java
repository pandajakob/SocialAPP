package socialapp.api.authentication;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialapp.api.security.SecurityProperties;
import socialapp.api.security.CustomUserDetailsService;
import socialapp.api.security.JwtAuthenticationService;
import socialapp.api.shared.domain_primitives.Email;
import socialapp.api.shared.domain_primitives.Password;
import socialapp.api.shared.domain_primitives.PhoneNumber;
import socialapp.api.users.DTO.StandardUserResponseDTO;
import socialapp.api.users.User;
import socialapp.api.users.UserRepository;
import socialapp.api.authentication.exceptions.UserAlreadyRegisteredException;

import java.util.Optional;

@Service
public class AuthService {
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final AuthenticationManager authenticationManager;
    private final SecurityProperties securityProperties;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, CustomUserDetailsService userDetailsService, AuthenticationManager authenticationManager, JwtAuthenticationService jwtAuthenticationService, SecurityProperties securityProperties, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.securityProperties = securityProperties;
        this.authenticationManager = authenticationManager;
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseCookie login(LoginDTO loginDetails) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDetails.email(),
                        loginDetails.password()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDetails.email());
        String token = jwtAuthenticationService.generateToken(userDetails);
        ResponseCookie cookie = ResponseCookie.from(securityProperties.getJWTName(), token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(securityProperties.getTokenValiditySeconds())
                .sameSite("None")
                .build();
        return cookie;
    }

    public ResponseCookie logout() {
        ResponseCookie cookie = ResponseCookie.from(securityProperties.getJWTName(), "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(securityProperties.getTokenValiditySeconds())
                .sameSite("None")
                .build();
        return cookie;

    }

    public StandardUserResponseDTO register(RegisterDTO registerDTO) {
        Password password = new Password(registerDTO.password());

        User user = new User(
                registerDTO.firstName(),
                registerDTO.lastName(),
                registerDTO.age(),
                passwordEncoder.encodePassword(password),
                new Email(registerDTO.email()),
                new PhoneNumber(registerDTO.phoneNumber()));

        user.promoteToAdmin();
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyRegisteredException("User already exists: " + e.getMessage());
        }
        return new StandardUserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().getValue(),
                user.getAge(),
                user.getInterests(),
                user.getPhoneNumber().getValue());
    }

    public User getUserFromAuth(Authentication authentication) {
        Email email = new Email(authentication.getName());
        Optional<User> user = userRepository.findByEmail(email.getValue());
        if (user.isPresent()) {
            return user.get();
        } else   {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
