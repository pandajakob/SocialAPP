package socialapp.backend.shared.domain_primitives.converters;

import jakarta.persistence.AttributeConverter;
import socialapp.backend.shared.domain_primitives.DomainPrimitive;

public class StringConverter implements AttributeConverter<DomainPrimitive<String>, String> {

    @Override
    public String convertToDatabaseColumn(DomainPrimitive<String> dp) {
        return dp.getValue();
    }

    @Override
    public DomainPrimitive<String> convertToEntityAttribute(String value) {
        return value == null ? null : new DomainPrimitive<String>(value);
    }


}
