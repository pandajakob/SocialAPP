package socialapp.backend.location.googleMaps.DTO;

import java.util.List;

public record GoogleGeocodeResponse(
        List<GoogleResult> results
) {}

