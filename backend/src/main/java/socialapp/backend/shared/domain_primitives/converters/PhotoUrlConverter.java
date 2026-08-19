package socialapp.backend.shared.domain_primitives.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import socialapp.backend.shared.domain_primitives.PhoneNumber;
import socialapp.backend.shared.domain_primitives.PhotoURL;

@Converter
public class PhotoUrlConverter implements AttributeConverter<PhotoURL, String> {

    @Override
    public String convertToDatabaseColumn(PhotoURL attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public PhotoURL convertToEntityAttribute(String value) {
        return value == null ? null : new PhotoURL(value);
    }
}
