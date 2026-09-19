package socialapp.backend.categories;

import jakarta.persistence.*;


@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String key;

    private String name;

    private String emoji;

    @JoinColumn(name = "parent_category_id")
    private Long parentCategoryId;

    public Category(String key, String name, String emoji, Long parentCategoryId) {
        this.key = key;
        this.name = name;
        this.emoji = emoji;
        this.parentCategoryId = parentCategoryId;
    }

    protected Category() {}

    public void changeName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public Long getParentCategoryId() {
        return parentCategoryId;
    }
}
