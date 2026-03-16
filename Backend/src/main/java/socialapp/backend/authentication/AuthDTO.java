package socialapp.backend.authentication;

import socialapp.backend.categories.Category;
import socialapp.backend.users.User;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhotoURL;

import java.util.List;
import java.util.UUID;

public class AuthDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<Category> interests;
    private String phoneNumber;
    private PhotoURL profilePhotoURL;
    private Email email;
    private User.Role role;

    public static record loginDTO(Email email, Password password){};


    public AuthDTO() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Category> getInterests() {
        return interests;
    }

    public void setInterests(List<Category> interests) {
        this.interests = interests;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhotoURL getProfilePhoto() {
        return profilePhotoURL;
    }

    public void setProfilePhoto(PhotoURL profilePhotoURL) {
        this.profilePhotoURL = profilePhotoURL;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
