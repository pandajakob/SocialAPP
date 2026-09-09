package socialapp.backend.location.DTO;

import java.util.List;

public record GoogleResult(
        String formattedAddress,
        List<GoogleAddressComponent> addressComponents
) {}

