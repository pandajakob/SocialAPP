package socialapp.backend.shared.domain_primitives;

public class Password extends DomainPrimitive<String>{
    public Password(String value) {
        super(value);
        if (value.length() < 6) {
            throw new IllegalArgumentException("Password length should be at least 6 characters");
        }
    }
}
