package socialapp.backend.location.googleMaps.DTO;

import java.util.List;

public record GoogleResult(
        String formattedAddress,
        List<GoogleAddressComponent> addressComponents
) {}

