package socialapp.backend.authentication;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialapp.backend.config.SecurityConfig;
import socialapp.backend.security.CustomUserDetailsService;
import socialapp.backend.security.JwtAuthenticationService;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.users.DTO.StandardUserResponseDTO;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;
import socialapp.backend.authentication.exceptions.UserAlreadyRegisteredException;

import java.util.Optional;

@Service
public class AuthService {
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final AuthenticationManager authenticationManager;
    private final SecurityConfig securityConfig;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, CustomUserDetailsService userDetailsService, AuthenticationManager authenticationManager, JwtAuthenticationService jwtAuthenticationService, SecurityConfig securityConfig, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.securityConfig = securityConfig;
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
        ResponseCookie cookie = ResponseCookie.from(securityConfig.getJWTName(), token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(securityConfig.getTokenValiditySeconds())
                .sameSite("None")
                .build();
        return cookie;
    }

    public ResponseCookie logout() {
        ResponseCookie cookie = ResponseCookie.from(securityConfig.getJWTName(), "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(securityConfig.getTokenValiditySeconds())
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
