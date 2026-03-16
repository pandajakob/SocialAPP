package socialapp.backend.shared.domain_primitives.converters;

import jakarta.persistence.AttributeConverter;
import socialapp.backend.shared.domain_primitives.Email;

public class EmailConverter implements AttributeConverter<Email, String> {

    @Override
    public String convertToDatabaseColumn(Email dp) {
        return dp.getValue();
    }

    @Override
    public Email convertToEntityAttribute(String value) {
        return value == null ? null : new Email(value);
    }


}
