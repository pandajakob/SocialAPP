package socialapp.backend.shared.domain_primitives.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import socialapp.backend.shared.domain_primitives.DomainPrimitive;

@Converter
public class StringConverter implements AttributeConverter<DomainPrimitive<String>, String> {

    @Override
    public String convertToDatabaseColumn(DomainPrimitive<String> attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public DomainPrimitive<String> convertToEntityAttribute(String value) {
        return value == null ? null : new DomainPrimitive<String>(value);
    }
}
