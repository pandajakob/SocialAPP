package socialapp.api.shared.initializers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import socialapp.api.location.LocationService;
import socialapp.api.authentication.PasswordEncoder;
import socialapp.api.categories.Category;
import socialapp.api.categories.CategoryRepository;
import socialapp.api.posts.Post;
import socialapp.api.posts.PostRepository;
import socialapp.api.security.SecurityProperties;
import socialapp.api.shared.AppProperties;
import socialapp.api.shared.domain_primitives.Email;
import socialapp.api.shared.domain_primitives.EncodedPassword;
import socialapp.api.shared.domain_primitives.Password;
import socialapp.api.shared.domain_primitives.PhoneNumber;
import socialapp.api.users.User;
import socialapp.api.users.UserRepository;

import java.util.List;

@Component
@Order(2)
public class AdminInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LocationService locationService;
    private final PasswordEncoder passwordEncoder;

    private final SecurityProperties securityProperties;
    private final AppProperties appProperties;

    public AdminInitializer(CategoryRepository categoryRepository, UserRepository userRepository, PostRepository postRepository, LocationService locationService, PasswordEncoder passwordEncoder, SecurityProperties securityProperties, AppProperties appProperties) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.locationService = locationService;
        this.passwordEncoder = passwordEncoder;
        this.securityProperties = securityProperties;
        this.userRepository = userRepository;
        this.appProperties = appProperties;
    }

    @Override
    public void run(String... args) {
        Email email = securityProperties.getAdminEmail();
        Password password = new Password(securityProperties.getAdminPassword());
        EncodedPassword encryptedPassword = passwordEncoder.encodePassword(password);

        if (!userRepository.existsByEmail(email.getValue())) {
            User user = new User(
                    "ADMIN",
                    "ADMIN",
                    35,
                    encryptedPassword,
                    email,
                    new PhoneNumber("00000000")
            );
            user.promoteToAdmin();
            userRepository.save(user);
        }

        if (appProperties.isDevMode()) {
            User admin = userRepository.findByEmail(email.getValue()).get();
            if (postRepository.findAllByUserId(admin.getId()).isEmpty()) {
                addPostsForUser(admin, 50);
            }
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