package socialapp.backend.users;

import org.springframework.stereotype.Component;
import socialapp.backend.users.DTO.StandardUserResponseDTO;

@Component
public class UserMapper {
    public StandardUserResponseDTO toDTO(User user) {
        return new StandardUserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().getValue(),
                user.getAge(),
                user.getInterests(),
                user.getPhoneNumber().getValue()
        );
    }
}
