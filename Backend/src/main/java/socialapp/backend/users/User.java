package socialapp.backend.users;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import socialapp.backend.categories.Category;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.shared.domain_primitives.PhotoURL;
import socialapp.backend.shared.domain_primitives.converters.StringConverter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
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
    @Convert(converter = StringConverter.class)
    private PhoneNumber phoneNumber;

    @Column(name = "profile_photo_url")
    @Convert(converter = StringConverter.class)
    private PhotoURL profilePhotoUrl;

    @Column(nullable = false, unique = true)
    @Convert(converter = StringConverter.class)
    private Email email;

    @Column(nullable = false)
    @Convert(converter = StringConverter.class)
    private Password password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role {
        ADMIN,
        USER
    }

}
