package socialapp.backend.Location.DTO;

import java.util.List;

public record GoogleResult(
        String formattedAddress,
        List<GoogleAddressComponent> addressComponents
) {}

