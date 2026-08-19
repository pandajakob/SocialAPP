package socialapp.backend.shared.domain_primitives.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import socialapp.backend.shared.domain_primitives.Email;
import socialapp.backend.shared.domain_primitives.Password;

@Converter
public class PasswordConverter implements AttributeConverter<Password, String> {

    @Override
    public String convertToDatabaseColumn(Password attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public Password convertToEntityAttribute(String value) {
        return value == null ? null : new Password(value);
    }
}
