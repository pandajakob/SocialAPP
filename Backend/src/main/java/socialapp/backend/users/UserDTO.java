package socialapp.backend.users;

import socialapp.backend.categories.Category;

import java.util.List;
import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<Category> interests;
    private String phoneNumber;
    private ProfilePhotoDTO profilePhoto;
    private String email;
    private User.Role role;

    public static class ProfilePhotoDTO {
        private String url;

        public ProfilePhotoDTO() {}

        public ProfilePhotoDTO(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public UserDTO() {}

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

    public List<String> getInterests() {
        return interests.stream().map((c)-> c.getName()).toList();
    }

    public void setInterests(List<Category> categories) {
        this.interests = categories;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ProfilePhotoDTO getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(ProfilePhotoDTO profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
