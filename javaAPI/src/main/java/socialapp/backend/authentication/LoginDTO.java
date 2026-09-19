package socialapp.backend.authentication;

import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;

public record LoginDTO(String email, String password)
{ }
