package socialapp.backend.users;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import socialapp.backend.categories.Category;
import socialapp.backend.posts.Post;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.shared.domain_primitives.PhotoURL;
import socialapp.backend.shared.domain_primitives.converters.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements UserDetails {

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
    @Convert(converter = PhoneNumberConverter.class)
    private PhoneNumber phoneNumber;

    @Column(name = "profile_photo_url")
    @Convert(converter = PhotoUrlConverter.class)
    private PhotoURL profilePhotoUrl;

    @Column(nullable = false, unique = true)
    @Convert(converter = EmailConverter.class)
    private Email email;

    @Column(nullable = false)
    @Convert(converter = PasswordConverter.class)
    private Password password;

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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public List<Category> getInterests() {
        return interests;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password.getValue();
    }

    @Override
    public String getUsername() {
        return this.email.getValue();
    }

    public UUID getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Email getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public PhotoURL getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public Role getRole() {
        return role;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setInterests(List<Category> interests) {
        this.interests = interests;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(Email email) {
        this.email = email;
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

    public void setProfilePhotoUrl(PhotoURL profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Post> getSavedPosts() {
        return savedPosts;
    }

    public void setSavedPosts(List<Post> savedPosts) {
        this.savedPosts = savedPosts;
    }
}
