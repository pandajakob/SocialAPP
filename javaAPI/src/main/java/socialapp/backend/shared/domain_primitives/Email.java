package socialapp.backend.shared.domain_primitives;

public class Email extends DomainPrimitive<String> {
    public Email(String value){
        super(value);
        if (!value.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    @Override
    public String getValue() {
        return super.getValue();
    }
}
