package socialapp.api.location.googleMaps.DTO;

import java.util.List;

public record GoogleResult(
        String formattedAddress,
        List<GoogleAddressComponent> addressComponents
) {}

