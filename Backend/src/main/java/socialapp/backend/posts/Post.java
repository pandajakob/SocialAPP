package socialapp.backend.posts;

import jakarta.persistence.*;

import org.locationtech.jts.geom.Point;
import socialapp.backend.categories.Category;
import socialapp.backend.users.User;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity

@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date date = new Date();

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    private String title;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "post_categories",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    private int ageFrom;

    private int ageTo;

    private String photoUrl;

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAgeFrom(int ageFrom) {
        this.ageFrom = ageFrom;
    }

    public void setAgeTo(int ageTo) {
        this.ageTo = ageTo;
    }
    
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Point getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public int getAgeFrom() {
        return ageFrom;
    }

    public int getAgeTo() {
        return ageTo;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
