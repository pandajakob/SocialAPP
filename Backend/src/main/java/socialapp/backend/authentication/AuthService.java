package socialapp.backend.authentication;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import socialapp.backend.security.CustomUserDetailsService;
import socialapp.backend.security.JWTService;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.shared.domain_primitives.PhotoURL;
import socialapp.backend.users.DTO.StandardUserResponseDTO;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;
import socialapp.backend.authentication.exceptions.UserAlreadyRegisteredException;

@Service
public class AuthService {
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);


    public AuthService(UserRepository userRepository, CustomUserDetailsService userDetailsService, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ResponseCookie login(LoginDTO loginDetails) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDetails.email(),
                        loginDetails.password()
                )
        );


        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDetails.email());

        String token = jwtService.generateToken(userDetails);

        ResponseCookie cookie = ResponseCookie.from("auth", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(daysToSeconds(30))
                .sameSite("None")
                .build();


        return cookie;
    }

    private int daysToSeconds(int days) {
        return days/24*60*60;

    }

    public StandardUserResponseDTO register(RegisterDTO registerDTO) {
        User user = new User();

        Password password = new Password(bCryptPasswordEncoder.encode(registerDTO.password()));

        user.setEmail(new Email(registerDTO.email()));
        user.setFirstName(registerDTO.firstName());
        user.setLastName(registerDTO.lastName());

        user.setAge(registerDTO.age());
        user.setRole(registerDTO.role());
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
