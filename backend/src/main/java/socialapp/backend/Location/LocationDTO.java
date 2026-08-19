package socialapp.backend.Location;

public record LocationDTO(
    Double longitude,
    Double latitude,
    String country,
    String city,
    String formattedAddress
){}
