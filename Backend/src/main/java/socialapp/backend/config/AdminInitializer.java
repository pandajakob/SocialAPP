package socialapp.backend.config;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import socialapp.backend.posts.Post;
import socialapp.backend.posts.PostRepository;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    private final Configuration configuration;

    public AdminInitializer(UserRepository userRepository, PostRepository postRepository, Configuration configuration) {
        this.postRepository = postRepository;
        this.configuration = configuration;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        Email email = configuration.getAdminEmail();
        Password password = new Password(bCryptPasswordEncoder.encode(configuration.getAdminPassword()));

        if (userRepository.existsByEmail(email)) {
            System.out.println("User with email " + email + " already exists");
            userRepository.deleteById(userRepository.findByEmail(email).get().getId());
        }
        User admin = new User();
        admin.setEmail(email);
        admin.setFirstName("ADMIN");
        admin.setLastName("ADMIN");
        admin.setPassword(password);
        admin.setPhoneNumber(new PhoneNumber("0000000000"));
        admin.setAge(35);
        admin.setRole(User.Role.ADMIN);
        userRepository.save(admin);


        addPostForUser(admin, "Bowling");
        addPostForUser(admin, "Padel Tennis");
        addPostForUser(admin, "Dungeons and dragons");

    }
    private void addPostForUser(User user, String title) {
        Post post = new Post();
        GeometryFactory factory = new GeometryFactory();
        Point location = factory.createPoint(new Coordinate(12.56553, 55.67594));

        post.setTitle(title);
        post.setDescription("This is a description...");
        post.setAgeFrom(18);
        post.setAgeTo(45);
        post.setLocation(location);
        post.setCreatedBy(user);

        postRepository.save(post);

    }
}