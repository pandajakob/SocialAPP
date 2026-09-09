package socialapp.backend.location.DTO;

import java.util.List;

public record GoogleGeocodeResponse(
        List<GoogleResult> results
) {}

