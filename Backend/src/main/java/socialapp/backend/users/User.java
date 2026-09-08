package socialapp.backend.users;

import jakarta.persistence.*;
import socialapp.backend.categories.Category;
import socialapp.backend.posts.Post;
import socialapp.backend.shared.domain_primitives.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Integer age;

    @ManyToMany
    @JoinTable(
            name = "user_interests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> interests;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "saved_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> savedPosts;

    public enum Role {
        ADMIN,
        USER
    }

    protected User() {}

    public User(String firstName, String lastName, Integer age, EncodedPassword password, Email email, PhoneNumber phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.password = password.getValue();
        this.email = email.getValue();
        this.phoneNumber = phoneNumber.getValue();
        this.role = User.Role.USER;
    }

    public void promoteToAdmin() {
        this.role = User.Role.ADMIN;
    }

    public void addSavedPost(Post post) {
        savedPosts.add(post);
    }

    public void addInterest(Category category) {
        interests.add(category);
    }

    public void addInterests(Collection<Category> category) {
        interests.addAll(category);
    }

    public void removeInterest(Category category) {
        interests.remove(category);
    }

    public void removeSavedPost(Post post) {
        savedPosts.remove(post);
    }

    public void changePhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber.getValue();
    }

    public void changeEmail(Email email) {
        this.email = email.getValue();
    }

    public void changePassword(EncodedPassword encodedPassword) {
        this.password = encodedPassword.getValue();
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public Email getEmail() {
        return new Email(email);
    }

    public EncodedPassword getPassword() {
        return new EncodedPassword(password);
    }

    public PhoneNumber getPhoneNumber() {
        return new PhoneNumber(phoneNumber);
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public Role getRole() {
        return role;
    }

    public List<Category> getInterests() {
        return interests;
    }

    public List<Post> getSavedPosts() {
        return savedPosts;
    }

    public void setInterests(List<Category> interests) {
        this.interests = interests;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public void setSavedPosts(List<Post> savedPosts) {
        this.savedPosts = savedPosts;
    }
}
