package socialapp.backend.shared.domain_primitives.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import socialapp.backend.shared.domain_primitives.DomainPrimitive;
import socialapp.backend.shared.domain_primitives.Email;

@Converter
public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public Email convertToEntityAttribute(String value) {
        return value == null ? null : new Email(value);
    }
}
