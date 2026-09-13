package socialapp.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import socialapp.backend.categories.Category;
import socialapp.backend.categories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;


@Component
@Order(1)
public class CategoryInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoryInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {

        if (categoryRepository.count() > 0) {
            return;
        }

        // Main categories
        Category sports = save("sports", "Sports", "⚽", null);
        Category gaming = save("gaming", "Gaming", "🎮", null);
        Category music = save("music", "Music", "🎵", null);
        Category outdoors = save("outdoors", "Outdoors", "🌲", null);
        Category fitness = save("fitness", "Fitness", "💪", null);
        Category food = save("food", "Food & Drinks", "🍕", null);
        Category creative = save("creative", "Creative", "🎨", null);
        Category social = save("social", "Social", "🎉", null);
        Category learning = save("learning", "Learning", "📚", null);
        Category tech = save("tech", "Tech", "💻", null);

        List<Category> categories = new ArrayList<>();

        // Sports
        categories.add(category("football", "Football", "⚽", sports));
        categories.add(category("basketball", "Basketball", "🏀", sports));
        categories.add(category("tennis", "Tennis", "🎾", sports));
        categories.add(category("badminton", "Badminton", "🏸", sports));
        categories.add(category("volleyball", "Volleyball", "🏐", sports));
        categories.add(category("handball", "Handball", "🤾", sports));
        categories.add(category("padel", "Padel", "🎾", sports));
        categories.add(category("table-tennis", "Table Tennis", "🏓", sports));
        categories.add(category("golf", "Golf", "⛳", sports));

        // Gaming
        categories.add(category("pc-gaming", "PC Gaming", "🖥️", gaming));
        categories.add(category("console-gaming", "Console Gaming", "🎮", gaming));
        categories.add(category("mobile-gaming", "Mobile Gaming", "📱", gaming));
        categories.add(category("board-games", "Board Games", "🎲", gaming));
        categories.add(category("card-games", "Card Games", "🃏", gaming));
        categories.add(category("dnd", "Dungeons & Dragons", "🐉", gaming));
        categories.add(category("esports", "Esports", "🏆", gaming));
        categories.add(category("chess", "Chess", "♟️", gaming));
        categories.add(category("pokemon", "Pokémon", "⚡", gaming));

        // Music
        categories.add(category("concerts", "Concerts", "🎤", music));
        categories.add(category("guitar", "Guitar", "🎸", music));
        categories.add(category("piano", "Piano", "🎹", music));
        categories.add(category("singing", "Singing", "🎙️", music));
        categories.add(category("dj", "DJ", "🎧", music));
        categories.add(category("rock", "Rock", "🤘", music));
        categories.add(category("pop", "Pop", "🎶", music));
        categories.add(category("hip-hop", "Hip Hop", "🎵", music));
        categories.add(category("electronic", "Electronic Music", "🎛️", music));

        // Outdoors
        categories.add(category("hiking", "Hiking", "🥾", outdoors));
        categories.add(category("camping", "Camping", "⛺", outdoors));
        categories.add(category("cycling", "Cycling", "🚴", outdoors));
        categories.add(category("running", "Running", "🏃", outdoors));
        categories.add(category("fishing", "Fishing", "🎣", outdoors));
        categories.add(category("kayaking", "Kayaking", "🛶", outdoors));
        categories.add(category("climbing", "Climbing", "🧗", outdoors));
        categories.add(category("beach", "Beach", "🏖️", outdoors));
        categories.add(category("nature", "Nature", "🌿", outdoors));

        // Fitness
        categories.add(category("gym", "Gym", "🏋️", fitness));
        categories.add(category("crossfit", "CrossFit", "🏋️", fitness));
        categories.add(category("yoga", "Yoga", "🧘", fitness));
        categories.add(category("boxing", "Boxing", "🥊", fitness));
        categories.add(category("martial-arts", "Martial Arts", "🥋", fitness));
        categories.add(category("swimming", "Swimming", "🏊", fitness));
        categories.add(category("calisthenics", "Calisthenics", "💪", fitness));
        categories.add(category("pilates", "Pilates", "🧘‍♀️", fitness));
        categories.add(category("dance-fitness", "Dance Fitness", "💃", fitness));

        // Food
        categories.add(category("restaurants", "Restaurants", "🍽️", food));
        categories.add(category("cafes", "Cafés", "☕", food));
        categories.add(category("cooking", "Cooking", "👨‍🍳", food));
        categories.add(category("baking", "Baking", "🧁", food));
        categories.add(category("street-food", "Street Food", "🌮", food));
        categories.add(category("brunch", "Brunch", "🥞", food));
        categories.add(category("coffee", "Coffee", "☕", food));
        categories.add(category("vegetarian", "Vegetarian", "🥗", food));
        categories.add(category("food-events", "Food Events", "🍴", food));

        // Creative
        categories.add(category("drawing", "Drawing", "✏️", creative));
        categories.add(category("painting", "Painting", "🎨", creative));
        categories.add(category("photography", "Photography", "📷", creative));
        categories.add(category("filmmaking", "Filmmaking", "🎬", creative));
        categories.add(category("writing", "Writing", "✍️", creative));
        categories.add(category("crafts", "Crafts", "🧶", creative));
        categories.add(category("design", "Design", "🖌️", creative));
        categories.add(category("fashion", "Fashion", "👗", creative));
        categories.add(category("theatre", "Theatre", "🎭", creative));

        // Social
        categories.add(category("parties", "Parties", "🎉", social));
        categories.add(category("nightlife", "Nightlife", "🌙", social));
        categories.add(category("meetups", "Meetups", "🤝", social));
        categories.add(category("dating", "Dating", "❤️", social));
        categories.add(category("networking", "Networking", "💼", social));
        categories.add(category("language-exchange", "Language Exchange", "🗣️", social));
        categories.add(category("volunteering", "Volunteering", "🤲", social));
        categories.add(category("festivals", "Festivals", "🎪", social));
        categories.add(category("movies", "Movies", "🍿", social));

        // Learning
        categories.add(category("languages", "Languages", "🌍", learning));
        categories.add(category("study-groups", "Study Groups", "📖", learning));
        categories.add(category("math", "Math", "➗", learning));
        categories.add(category("science", "Science", "🔬", learning));
        categories.add(category("history", "History", "🏛️", learning));
        categories.add(category("books", "Books", "📚", learning));
        categories.add(category("philosophy", "Philosophy", "🤔", learning));
        categories.add(category("workshops", "Workshops", "🛠️", learning));
        categories.add(category("entrepreneurship", "Entrepreneurship", "🚀", learning));

        // Tech
        categories.add(category("programming", "Programming", "💻", tech));
        categories.add(category("web-development", "Web Development", "🌐", tech));
        categories.add(category("app-development", "App Development", "📱", tech));
        categories.add(category("ai", "Artificial Intelligence", "🤖", tech));
        categories.add(category("cybersecurity", "Cybersecurity", "🔐", tech));
        categories.add(category("robotics", "Robotics", "🦾", tech));
        categories.add(category("startups", "Startups", "🚀", tech));
        categories.add(category("linux", "Linux", "🐧", tech));
        categories.add(category("tech-events", "Tech Events", "⚙️", tech));

        categoryRepository.saveAll(categories);
    }

    private Category save(String key, String name, String emoji, Long parentCategoryId) {
        return categoryRepository.save(
                new Category(key, name, emoji, parentCategoryId)
        );
    }

    private Category category(String key, String name, String emoji, Category parent) {
        return new Category(
                key,
                name,
                emoji,
                parent.getId()
        );
    }
}