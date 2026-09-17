package socialapp.backend.location.googleMaps.DTO;

import java.util.List;

public record GoogleAddressComponent(
        String longText,
        String shortText,
        List<String> types
) {}