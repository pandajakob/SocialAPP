package socialapp.backend.categories;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class CategoryList extends ArrayList<Category> {
    public CategoryList() {
        super(categories);
    }

    public Category getCategoryByName(String name) {
        for (Category c : this)  {
            if (c.getName().equalsIgnoreCase(name)) {
                return  c;
            }
        }
        throw new InputMismatchException("Category with name: " + name + " does not exists");
    }

    public List<Category> getAllMainCategories() {
        return this.stream().filter((c)-> c.getParentCategory() == null).toList();
    }

    public List<Category> getAllSubCategories(String name) {
        return this.stream().filter((c)-> {
            if (c.getParentCategory() == null) {
                return false;
            }
            return c.getParentCategory().getName().equalsIgnoreCase(name);

        }).toList();
    }

    static Category sports = new Category("Sports", null);
    static Category music = new Category("Music", null);
    static Category gaming = new Category("Gaming", null);
    static Category outdoor = new Category("Outdoor", null);
    static Category arts = new Category("Creativity", null);
    static Category food = new Category("Food", null);
    static Category tech = new Category("Tech", null);
    static Category social = new Category("Social", null);

    public static List<Category> categories = List.of(
            sports,
            new Category("Football", sports),
            new Category("Basketball", sports),
            new Category("Tennis", sports),
            new Category("Running", sports),
            new Category("Cycling", sports),

            music,
            new Category("Playing", music),
            new Category("Singing", music),
            new Category("Concerts", music),

            gaming,
            new Category("Video Games", gaming),
            new Category("Board Games", gaming),
            new Category("Tabletop RPG", gaming),

            outdoor,
            new Category("Hiking", outdoor),
            new Category("Camping", outdoor),
            new Category("Fishing", outdoor),

            arts,
            new Category("Drawing", arts),
            new Category("Painting", arts),
            new Category("Photography", arts),
            new Category("DIY & Crafts", arts),

            food,
            new Category("Cooking", food),
            new Category("Baking", food),
            new Category("Coffee & Cafés", food),

            tech,
            new Category("Programming", tech),
            new Category("Robotics", tech),
            new Category("Game Development", tech),

            social,
            new Category("Language Exchange", social),
            new Category("Volunteering", social),
            new Category("Networking", social),
            new Category("Book Clubs", social)
    );
}
