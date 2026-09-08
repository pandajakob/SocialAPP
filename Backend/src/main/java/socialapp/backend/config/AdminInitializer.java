package socialapp.backend.config;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import socialapp.backend.Location.LocationService;
import socialapp.backend.categories.Category;
import socialapp.backend.categories.CategoryRepository;
import socialapp.backend.posts.Post;
import socialapp.backend.posts.PostRepository;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.users.User;
import socialapp.backend.users.UserRepository;

import java.util.List;

@Component
public class AdminInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LocationService locationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    private final Configuration configuration;

    public AdminInitializer(CategoryRepository categoryRepository, UserRepository userRepository, PostRepository postRepository, LocationService locationService, Configuration configuration) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.locationService = locationService;
        this.configuration = configuration;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        Email email = configuration.getAdminEmail();
        Password password = new Password(bCryptPasswordEncoder.encode(configuration.getAdminPassword()));

        if (!userRepository.existsByEmail(email)) {
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
        User admin = userRepository.findByEmail(email).get();
        if (postRepository.findAllByUserId(admin.getId()).isEmpty()) {
            addPostsForUser(admin, 50);
        }

    }
    private void addPostsForUser(User user, int amount) {
        List<Category> categories = categoryRepository.findAll();

        String[] titles = {
                "Bowling Night",
                "Padel Tennis",
                "Dungeons and Dragons",
                "Coffee Meetup",
                "Running Group",
                "Board Game Night",
                "Football Match",
                "Study Session",
                "Movie Night",
                "Dinner Meetup",
                "Beach Volleyball",
                "Gym Session",
                "City Walk",
                "Photography Walk",
                "Coding Meetup"
        };

        for (int i = 0; i < amount; i++) {
            Post post = new Post();

            post.setTitle(titles[i % titles.length] + " #" + (i + 1));
            post.setDescription("Come join us! This is test post #" + (i + 1));

            post.setAgeFrom(18 + (i % 5));
            post.setAgeTo(30 + (i % 20));

            if (!categories.isEmpty()) {
                int categoryIndex = i % categories.size();
                post.setCategories(List.of(categories.get(categoryIndex)));
            }

            // Spread posts around Copenhagen
            double latitude = 55.67594 + ((Math.random() - 0.5) * 0.15);
            double longitude = 12.56553 + ((Math.random() - 0.5) * 0.20);

            post.setLocation(
                    locationService.createLocation(latitude, longitude)
            );

            post.setCreatedBy(user);

            postRepository.save(post);
        }
    }
}