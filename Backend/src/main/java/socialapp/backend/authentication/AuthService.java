package socialapp.backend.authentication;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import socialapp.backend.config.Configuration;
import socialapp.backend.security.CustomUserDetailsService;
import socialapp.backend.security.JwtAuthenticationService;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.users.DTO.StandardUserResponseDTO;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;
import socialapp.backend.authentication.exceptions.UserAlreadyRegisteredException;

@Service
public class AuthService {
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtAuthenticationService jwtAuthenticationService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    private final Configuration configuration;

    public AuthService(UserRepository userRepository, CustomUserDetailsService userDetailsService, AuthenticationManager authenticationManager, JwtAuthenticationService jwtAuthenticationService, Configuration configuration) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.configuration = configuration;
        this.authenticationManager = authenticationManager;
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    public ResponseCookie login(LoginDTO loginDetails) {
        System.out.println(configuration.getAdminEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDetails.email(),
                        loginDetails.password()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDetails.email());
        String token = jwtAuthenticationService.generateToken(userDetails);
        ResponseCookie cookie = ResponseCookie.from(configuration.getJWTName(), token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(configuration.getTokenValiditySeconds())
                .sameSite("None")
                .build();
        return cookie;
    }

    public StandardUserResponseDTO register(RegisterDTO registerDTO) {
        User user = new User();

        Password password = new Password(bCryptPasswordEncoder.encode(registerDTO.password()));

        user.setEmail(new Email(registerDTO.email()));
        user.setFirstName(registerDTO.firstName());
        user.setLastName(registerDTO.lastName());

        user.setAge(registerDTO.age());
        user.setPassword(password);
        user.setPhoneNumber(new PhoneNumber(registerDTO.phoneNumber()));

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
}
