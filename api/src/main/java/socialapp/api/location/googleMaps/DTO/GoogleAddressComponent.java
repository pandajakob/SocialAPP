package socialapp.api.location.googleMaps.DTO;

import java.util.List;

public record GoogleAddressComponent(
        String longText,
        String shortText,
        List<String> types
) {}