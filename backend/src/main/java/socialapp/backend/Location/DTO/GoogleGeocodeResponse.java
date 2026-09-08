package socialapp.backend.Location.DTO;

import java.util.List;

public record GoogleGeocodeResponse(
        List<GoogleResult> results
) {}

