package socialapp.backend.shared.domain_primitives.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import socialapp.backend.shared.domain_primitives.Password;
import socialapp.backend.shared.domain_primitives.PhoneNumber;

@Converter
public class PhoneNumberConverter implements AttributeConverter<PhoneNumber, String> {

    @Override
    public String convertToDatabaseColumn(PhoneNumber attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public PhoneNumber convertToEntityAttribute(String value) {
        return value == null ? null : new PhoneNumber(value);
    }
}
